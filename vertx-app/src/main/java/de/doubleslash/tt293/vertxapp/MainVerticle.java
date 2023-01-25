package de.doubleslash.tt293.vertxapp;

import de.doubleslash.tt293.vertxapp.author.AuthorController;
import de.doubleslash.tt293.vertxapp.author.AuthorMapper;
import de.doubleslash.tt293.vertxapp.author.AuthorRepository;
import de.doubleslash.tt293.vertxapp.author.AuthorService;
import de.doubleslash.tt293.vertxapp.book.BookController;
import de.doubleslash.tt293.vertxapp.book.BookMapper;
import de.doubleslash.tt293.vertxapp.book.BookRepository;
import de.doubleslash.tt293.vertxapp.book.BookService;
import de.doubleslash.tt293.vertxapp.bookauthor.BookAuthorRepository;
import de.doubleslash.tt293.vertxapp.publisher.PublisherController;
import de.doubleslash.tt293.vertxapp.publisher.PublisherMapper;
import de.doubleslash.tt293.vertxapp.publisher.PublisherRepository;
import de.doubleslash.tt293.vertxapp.publisher.PublisherService;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;

import java.util.logging.Logger;

public class MainVerticle extends AbstractVerticle {
  private static final Logger LOGGER = Logger.getLogger(MainVerticle.class.getName());

  public static void main(final String[] args) {
    Launcher.executeCommand("run", MainVerticle.class.getName());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    Router mainRouter = Router.router(vertx);

    PgPool pgPool = pgPool();

    //Init Mapper
    PublisherMapper publisherMapper = new PublisherMapper();
    AuthorMapper authorMapper = new AuthorMapper();
    BookMapper bookMapper = new BookMapper(authorMapper,
      publisherMapper);

    // Init Repositories
    PublisherRepository publisherRepository = new PublisherRepository(pgPool);
    AuthorRepository authorRepository = new AuthorRepository(pgPool);
    BookAuthorRepository bookAuthorRepository = new BookAuthorRepository(pgPool);
    BookRepository bookRepository = new BookRepository(pgPool,
      authorRepository,
      bookAuthorRepository);


    // Init Services
    PublisherService publisherService = new PublisherService(publisherRepository,
      publisherMapper);

    AuthorService authorService = new AuthorService(authorRepository,
      authorMapper);

    BookService bookService = new BookService(bookRepository,
      publisherRepository,
      authorRepository,
      bookMapper);

    // Init Controllers
    PublisherController publisherController = new PublisherController(publisherService);
    AuthorController authorController = new AuthorController(authorService);
    BookController bookController = new BookController(bookService);

    // Routers
    mainRouter.route(bookController.mountPoint()).subRouter(bookController.router(vertx));
    mainRouter.route(publisherController.mountPoint()).subRouter(publisherController.router(vertx));
    mainRouter.route(authorController.mountPoint()).subRouter(authorController.router(vertx));

    ConfigStoreOptions configStoreOptions = new ConfigStoreOptions()
      .setFormat("properties")
      .setType("file")
      .setConfig(new JsonObject().put("path", "application.properties"));

    ConfigRetrieverOptions configRetrieverOptions = new ConfigRetrieverOptions()
      .addStore(configStoreOptions);

    ConfigRetriever configRetriever = ConfigRetriever.create(vertx, configRetrieverOptions);

    configRetriever.getConfig(asyncResult -> {
      if (asyncResult.succeeded()) {

        JsonObject config = asyncResult.result();
        Integer port = config.getInteger("http.port");
        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(mainRouter);
        httpServer.listen(port, http -> {
          if (http.succeeded()) {
            startPromise.complete();
            System.out.println("HTTP server started on port 8080");
          } else {
            startPromise.fail(http.cause());
          }
        });
      } else {
        startPromise.fail("Unable to load Configuration File.");
      }
    });
  }

  private PgPool pgPool() {
    PgConnectOptions connectOptions = new PgConnectOptions()
      .setPort(5432)
      .setHost("localhost")
      .setDatabase("postgres")
      .setUser("postgres")
      .setPassword("postgres");

    PoolOptions poolOptions = new PoolOptions().setMaxSize(5);

    return PgPool.pool(vertx, connectOptions, poolOptions);
  }
}
