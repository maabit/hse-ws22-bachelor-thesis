package de.doubleslash.tt293.vertxapp.bookauthor;

import io.vertx.core.Future;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlResult;
import io.vertx.sqlclient.Tuple;

import java.util.function.Function;
import java.util.logging.Logger;

public class BookAuthorRepository {

  private final PgPool pgPool;

  private static final Logger LOGGER = Logger.getLogger(BookAuthorRepository.class.getName());


  public BookAuthorRepository(PgPool pgPool) {
    this.pgPool = pgPool;
  }

  private static final Function<Row, BookAuthorEntity> MAPPER = (row) -> BookAuthorEntity.of(row.getString("isbn"), row.getInteger("author_id"));

  public Future<Integer> save(BookAuthorEntity bookAuthorEntity) {
    String preparedQuery = "INSERT INTO book_author(isbn,author_id) " +
      "VALUES($1,$2)";
    Tuple parameter = Tuple.of(bookAuthorEntity.getIsbn(), bookAuthorEntity.getAuthorId());
    LOGGER.info(preparedQuery + " parameter " + parameter.deepToString());

    return pgPool.preparedQuery(preparedQuery)
      .execute(parameter)
      .map(RowSet::rowCount);
  }

  public Future<Integer> deleteByIsbn(String isbn) {
    String preparedQuery = "DELETE FROM book_author WHERE isbn = $1";
    Tuple parameter = Tuple.of(isbn);
    LOGGER.info(preparedQuery + " parameter " + parameter.deepToString());

    return pgPool.preparedQuery(preparedQuery)
      .execute(parameter)
      .map(SqlResult::rowCount);
  }

}
