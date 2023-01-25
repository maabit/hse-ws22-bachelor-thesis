package de.doubleslash.tt293.vertxapp.exceptions;

import de.doubleslash.tt293.vertxapp.messages.ErrorMessage;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.Arrays;
import java.util.logging.Logger;

public class RestExceptionHandler {

  private static final Logger LOGGER = Logger.getLogger(RestExceptionHandler.class.getName());


  public static void catchExceptions(RoutingContext context) {

    Throwable failure = context.failure();

    if (failure instanceof AlreadyExistsException) {
      ErrorMessage errorMessage = new ErrorMessage(new JsonObject(), context.failure().getMessage(), "UNPROCESSABLE_ENTITY");
      context.response().setStatusCode(422).end(errorMessage.getJson().toString());
      return;
    }

    if (failure instanceof NotFoundException) {
      ErrorMessage errorMessage = new ErrorMessage(new JsonObject(), context.failure().getMessage(), "NOT_FOUND");
      context.response().setStatusCode(404).end(errorMessage.getJson().toString());
      return;
    }

    if (failure instanceof Exception) {
      ErrorMessage errorMessage = new ErrorMessage(new JsonObject(), "INTERNAL_SERVER_ERROR");
      LOGGER.warning(Arrays.toString(failure.getStackTrace()));
      context.response().setStatusCode(500).end(errorMessage.getJson().toString());
    }

  }
}
