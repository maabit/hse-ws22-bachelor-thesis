package de.doubleslash.tt293.vertxapp.book;

import de.doubleslash.tt293.vertxapp.author.AuthorRepository;
import de.doubleslash.tt293.vertxapp.bookauthor.BookAuthorEntity;
import de.doubleslash.tt293.vertxapp.bookauthor.BookAuthorRepository;
import de.doubleslash.tt293.vertxapp.exceptions.ExceptionUtils;
import de.doubleslash.tt293.vertxapp.utils.FutureUtils;
import io.vertx.core.Future;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlResult;
import io.vertx.sqlclient.Tuple;

import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BookRepository {

  private static final Logger LOGGER = Logger.getLogger(BookRepository.class.getName());
  private final PgPool pgPool;

  private final AuthorRepository authorRepository;

  private final BookAuthorRepository bookAuthorRepository;

  public BookRepository(PgPool pgPool,
                        AuthorRepository authorRepository,
                        BookAuthorRepository bookAuthorRepository) {
    this.pgPool = pgPool;
    this.authorRepository = authorRepository;
    this.bookAuthorRepository = bookAuthorRepository;
  }

  private static final Function<Row, BookEntity> MAPPER = (row) -> BookEntity.of(row.getString("isbn"),
    row.getString("title"),
    row.getInteger("publisher_id"),
    row.getString("publisher_name"));


  public Future<Boolean> existsByIsbn(String isbn) {
    String preparedQuery = "SELECT * FROM book WHERE isbn=$1";
    Tuple parameter = Tuple.of(isbn);
    LOGGER.info(preparedQuery + " parameter " + parameter.deepToString());

    return pgPool.preparedQuery(preparedQuery)
      .execute(parameter)
      .map(rows -> rows.rowCount() > 0);
  }

  public Future<BookEntity> findByIsbn(String isbn) {
    String preparedQuery =
      "SELECT b.isbn, " +
        "b.title, " +
        "p.id AS publisher_id, " +
        "p.name AS publisher_name " +
        "FROM book AS b " +
        "INNER JOIN publisher AS p " +
        "ON b.publisher_id = p.id " +
        "WHERE b.isbn = $1";

    Tuple parameter = Tuple.of(isbn);
    LOGGER.info(preparedQuery + " parameter " + parameter.deepToString());

    return pgPool.preparedQuery(preparedQuery)
      .execute(parameter)
      .map(RowSet::iterator)
      .flatMap(iterator -> {
        if (iterator.hasNext()) {
          BookEntity bookEntity = MAPPER.apply(iterator.next());
          return authorRepository.findAllAuthorsByIsbn(isbn).map(authorEntities -> {
            bookEntity.setAuthorEntities(authorEntities);
            return bookEntity;
          });
        }
        throw ExceptionUtils.createBookNotFoundException(isbn);
      });
  }

  public Future<List<BookEntity>> findAll() {
    String query =
      "SELECT b.isbn, " +
        "b.title, " +
        "p.id AS publisher_id, " +
        "p.name AS publisher_name " +
        "FROM book AS b " +
        "INNER JOIN publisher AS p " +
        "ON b.publisher_id = p.id ";

    LOGGER.info(query);

    return pgPool.query(query)
      .execute()
      .flatMap(rows -> FutureUtils.allOfFutures(StreamSupport.stream(rows.spliterator(), false)
        .map(row -> {
          BookEntity bookEntity = MAPPER.apply(row);
          return authorRepository.findAllAuthorsByIsbn(bookEntity.getIsbn())
            .map(authorEntities -> {
              bookEntity.setAuthorEntities(authorEntities);
              return bookEntity;
            });
        }).collect(Collectors.toList())));

  }

  public Future<List<Integer>> save(BookEntity bookEntity) {
    String preparedQuery = "INSERT INTO book(isbn,title, publisher_id) " +
      "VALUES ($1,$2, $3)";
    Tuple parameter = Tuple.of(bookEntity.getIsbn(),
      bookEntity.getTitle(),
      bookEntity.getPublisher().getId());
    LOGGER.info(preparedQuery + " parameter " + parameter.deepToString());

    return pgPool.preparedQuery(preparedQuery)
      .execute(parameter)
      .flatMap(rows -> FutureUtils.allOfFutures(bookEntity.getAuthorEntities()
        .stream()
        .map(authorEntity -> bookAuthorRepository.save(BookAuthorEntity.of(bookEntity.getIsbn(), authorEntity.getId()))
        ).collect(Collectors.toList())));
  }

  public Future<Integer> delete(BookEntity bookEntity) {
    String preparedQuery = "DELETE FROM book " +
      "WHERE isbn = $1";
    Tuple parameter = Tuple.of(bookEntity.getIsbn());

    return bookAuthorRepository.deleteByIsbn(bookEntity.getIsbn())
      .flatMap(rowCunt -> {
        LOGGER.info(preparedQuery + " parameter " + parameter.deepToString());
        return pgPool.preparedQuery(preparedQuery)
          .execute(parameter)
          .map(SqlResult::rowCount);
      });
  }
}
