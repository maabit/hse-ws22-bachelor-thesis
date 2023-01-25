package de.doubleslash.tt293.vertxapp.publisher;

import de.doubleslash.tt293.vertxapp.exceptions.RestExceptionHandler;
import de.doubleslash.tt293.vertxapp.messages.ResponseMessage;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class PublisherController {

  private final PublisherService publisherService;

  public PublisherController(PublisherService publisherService) {
    this.publisherService = publisherService;
  }

  public String mountPoint() {
    return "/publishers/*";
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
      PublisherPostDto publisherPostDto = new PublisherPostDto(body);

      publisherService.create(publisherPostDto)
        .onSuccess(rowCount -> {
          ResponseMessage responseMessage = new ResponseMessage(new JsonObject());
          responseMessage.setMessage(String.format("Publisher with name %s was created.", publisherPostDto.getName()));
          context.response().setStatusCode(201).end(responseMessage.getJson().toString());
        })
        .onFailure(context::fail);
    });
  }

  private void getAll(RoutingContext context) {
    publisherService.getAllPublishers()
      .onSuccess(publisherDtos -> {
        JsonArray jsonArray = new JsonArray();
        for (PublisherDto publisherDto : publisherDtos) {
          jsonArray.add(publisherDto.getJson());
        }
        context.response().setStatusCode(200).end(jsonArray.toString());
      })
      .onFailure(context::fail);
  }
}
