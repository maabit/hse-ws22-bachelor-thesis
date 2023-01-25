package de.doubleslash.tt293.vertxapp.utils;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;

import java.util.List;
import java.util.stream.Collectors;

public final class FutureUtils {

  /*
   * Private Utils Constructor
   */
  private FutureUtils() {
  }

  public static <R> Future<List<R>> allOfFutures(List<Future<R>> futures) {
    return CompositeFuture.all((List) futures).map(v -> futures.stream().map(Future::result).collect(Collectors.toList()));
  }

}
