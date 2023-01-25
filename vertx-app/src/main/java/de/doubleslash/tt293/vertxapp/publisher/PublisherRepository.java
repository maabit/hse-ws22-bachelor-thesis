package de.doubleslash.tt293.vertxapp.publisher;

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


public class PublisherRepository {

  private static final Logger LOGGER = Logger.getLogger(PublisherRepository.class.getName());

  private final PgPool pgPool;

  public PublisherRepository(PgPool pgPool) {
    this.pgPool = pgPool;
  }

  private static final Function<Row, PublisherEntity> MAPPER = (row) -> PublisherEntity.of(row.getInteger("id"), row.getString("name"));

  public Future<PublisherEntity> findByName(String name) {
    String preparedQuery = "SELECT * FROM publisher WHERE name=$1";
    Tuple parameter = Tuple.of(name);
    LOGGER.info(preparedQuery + " parameter " + parameter.deepToString());

    return pgPool.preparedQuery(preparedQuery).execute(parameter)
      .map(RowSet::iterator)
      .map(iterator -> {
        if (iterator.hasNext()) {
          return MAPPER.apply(iterator.next());
        }
        throw ExceptionUtils.createPublisherNotFoundException(name);
      });
  }

  public Future<List<PublisherEntity>> findAll() {
    String query = "SELECT * from publisher";
    LOGGER.info(query);

    return pgPool
      .query(query)
      .execute()
      .map(rows -> StreamSupport.stream(rows.spliterator(), false)
        .map(MAPPER)
        .collect(Collectors.toList()));
  }

  public Future<Boolean> existsByName(String name) {
    String preparedQuery = "SELECT * FROM publisher WHERE name=$1";
    Tuple parameter = Tuple.of(name);
    LOGGER.info(preparedQuery + " parameter " + parameter.deepToString());

    return pgPool.preparedQuery(preparedQuery)
      .execute(parameter)
      .map(rows -> rows.rowCount() > 0);
  }

  public Future<Integer> save(PublisherEntity publisherEntity) {
    String preparedQuery = "INSERT INTO publisher(name) VALUES($1)";
    Tuple parameter = Tuple.of(publisherEntity.getName());
    LOGGER.info(preparedQuery + " parameter " + parameter.deepToString());

    return pgPool.preparedQuery(preparedQuery)
      .execute(parameter)
      .map(SqlResult::rowCount);
  }
}
