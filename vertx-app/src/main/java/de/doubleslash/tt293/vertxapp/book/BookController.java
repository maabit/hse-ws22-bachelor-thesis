package de.doubleslash.tt293.vertxapp.book;

import de.doubleslash.tt293.vertxapp.exceptions.RestExceptionHandler;
import de.doubleslash.tt293.vertxapp.messages.ResponseMessage;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class BookController {

  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  public String mountPoint() {
    return "/books/*";
  }

  public Router router(Vertx vertx) {
    Router router = Router.router(vertx);
    router.post("/")
      .consumes("application/json")
      .handler(this::add)
      .failureHandler(RestExceptionHandler::catchExceptions);
    router.get("/")
      .produces("application/json")
      .handler(this::getAll)
      .failureHandler(RestExceptionHandler::catchExceptions);
    router.get("/:id")
      .produces("application/json")
      .handler(this::getById)
      .failureHandler(RestExceptionHandler::catchExceptions);
    router.delete("/:id")
      .handler(this::deleteById)
      .failureHandler(RestExceptionHandler::catchExceptions);

    return router;
  }

  private void add(RoutingContext context) {
    context.request().bodyHandler(buffer -> {
      JsonObject body = buffer.toJsonObject();
      BookPostDto bookPostDto = new BookPostDto(body);
      bookService.create(bookPostDto)
        .onSuccess(rowCount -> {
          ResponseMessage responseMessage = new ResponseMessage(new JsonObject());
          responseMessage.setMessage(String.format("Book with ISBN %s was created.", bookPostDto.getIsbn()));
          context.response().setStatusCode(201).end(responseMessage.getJson().toString());
        })
        .onFailure(context::fail);
    });
  }

  private void getAll(RoutingContext context) {
    bookService.getAll().onSuccess(bookDtos -> {
        JsonArray jsonArray = new JsonArray();
        for (BookDto bookDto : bookDtos) {
          jsonArray.add(bookDto.getJson());
        }
        context.response().setStatusCode(200).end(jsonArray.toString());
      })
      .onFailure(context::fail);
  }

  private void getById(RoutingContext context) {
    String pathParamId = context.pathParam("id");
    bookService.getOne(pathParamId)
      .onSuccess(bookDto -> context.response()
        .setStatusCode(200)
        .end(bookDto.getJson().toString()))
      .onFailure(context::fail);
  }

  private void deleteById(RoutingContext context) {
    String pathParamId = context.pathParam("id");
    bookService.delete(pathParamId).onSuccess(rowCount -> {
        ResponseMessage responseMessage = new ResponseMessage(new JsonObject());
        responseMessage.setMessage(String.format("Book with ISBN %s was deleted", pathParamId));
        context.response()
          .setStatusCode(200)
          .end(responseMessage.getJson().toString());
      })
      .onFailure(context::fail);
  }

}
