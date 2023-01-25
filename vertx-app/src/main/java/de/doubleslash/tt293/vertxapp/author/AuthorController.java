package de.doubleslash.tt293.vertxapp.author;

import de.doubleslash.tt293.vertxapp.exceptions.RestExceptionHandler;
import de.doubleslash.tt293.vertxapp.messages.ResponseMessage;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class AuthorController {

  AuthorService authorService;

  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  public String mountPoint() {
    return "/authors/*";
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
    return router;
  }

  private void add(RoutingContext context) {
    context.request().bodyHandler(buffer -> {
      JsonObject body = buffer.toJsonObject();
      AuthorPostDto authorPostDto = new AuthorPostDto(body);

      authorService.create(authorPostDto)
        .onSuccess(rowCount -> {
          ResponseMessage responseMessage = new ResponseMessage(new JsonObject());
          responseMessage.setMessage(String.format("Author with name %s was created.", authorPostDto.getName()));
          context.response().setStatusCode(201).end(responseMessage.getJson().toString());
        })
        .onFailure(context::fail);
    });
  }

  private void getAll(RoutingContext context) {
    authorService.getAllAuthors()
      .onSuccess(authorDtos -> {
        JsonArray jsonArray = new JsonArray();
        for (AuthorDto authorDto : authorDtos) {
          jsonArray.add(authorDto.getJson());
        }
        context.response().setStatusCode(200).end(jsonArray.toString());
      })
      .onFailure(context::fail);
  }
}
