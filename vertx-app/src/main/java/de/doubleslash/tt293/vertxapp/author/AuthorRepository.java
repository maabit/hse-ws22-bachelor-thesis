package de.doubleslash.tt293.vertxapp.author;

import de.doubleslash.tt293.vertxapp.exceptions.ExceptionUtils;
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

public class AuthorRepository {

  private static final Logger LOGGER = Logger.getLogger(AuthorRepository.class.getName());

  private final PgPool pgPool;

  public AuthorRepository(PgPool pgPool) {
    this.pgPool = pgPool;
  }

  private static final Function<Row, AuthorEntity> MAPPER = (row) -> AuthorEntity.of(row.getInteger("id"), row.getString("name"));

  public Future<AuthorEntity> findByName(String name) {
    String preparedQuery = "SELECT * FROM author WHERE name=$1";
    Tuple parameter = Tuple.of(name);
    LOGGER.info(preparedQuery + " parameter " + parameter.deepToString());

    return pgPool.preparedQuery(preparedQuery).execute(parameter)
      .map(RowSet::iterator)
      .map(iterator -> {
        if (iterator.hasNext()) {
          return MAPPER.apply(iterator.next());
        }
        throw ExceptionUtils.createAuthorNotFoundException(name);
      });
  }

  public Future<List<AuthorEntity>> findAll() {
    String query = "SELECT * FROM author";
    LOGGER.info(query);

    return pgPool
      .query("SELECT * FROM author")
      .execute()
      .map(rows -> StreamSupport.stream(rows.spliterator(), false)
        .map(MAPPER)
        .collect(Collectors.toList()));
  }

  public Future<Boolean> existsByName(String name) {
    String preparedQuery = "SELECT * FROM author WHERE name=$1";
    Tuple parameter = Tuple.of(name);
    LOGGER.info(preparedQuery + " parameter " + parameter.deepToString());

    return pgPool.preparedQuery(preparedQuery)
      .execute(parameter)
      .map(rows -> rows.rowCount() > 0);
  }

  public Future<Integer> save(AuthorEntity authorEntity) {
    String preparedQuery = "INSERT INTO author(name) VALUES($1)";
    Tuple parameter = Tuple.of(authorEntity.getName());
    LOGGER.info(preparedQuery + " parameter " + parameter.deepToString());

    return pgPool.preparedQuery(preparedQuery)
      .execute(parameter)
      .map(SqlResult::rowCount);
  }

  public Future<List<AuthorEntity>> findAllAuthorsByIsbn(String isbn) {
    String preparedQuery =
      "SELECT a.id, " +
        "a.name " +
        "FROM author AS a " +
        "INNER JOIN book_author AS ba ON a.id = ba.author_id " +
        "WHERE ba.isbn = $1";
    Tuple parameter = Tuple.of(isbn);

    LOGGER.info(preparedQuery + " parameter " + parameter);

    return pgPool.preparedQuery(preparedQuery)
      .execute(parameter)
      .map(rows -> StreamSupport.stream(rows.spliterator(), false)
        .map(MAPPER)
        .collect(Collectors.toList()));
  }
}
