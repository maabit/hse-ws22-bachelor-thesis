package de.doubleslash.tt293.vertxapp.messages;

import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;

public class ErrorMessage {

  static final String HTTP_STATUS_KEY = "httpStatus";

  static final String TIMESTAMP_KEY = "timestamp";

  static final String MESSAGE_KEY = "message";

  protected JsonObject jsonObject;

  public ErrorMessage(final JsonObject jsonObject) {
    this.jsonObject = jsonObject;
    this.setTimestamp(LocalDateTime.now());
  }

  public ErrorMessage(final JsonObject jsonObject, String status) {
    this.jsonObject = jsonObject;
    this.setTimestamp(LocalDateTime.now());
    this.setMessage("Unexpected Error.");
    this.setHttpStatus(status);
  }

  public ErrorMessage(final JsonObject jsonObject, String message, String status) {
    this.jsonObject = jsonObject;
    this.setTimestamp(LocalDateTime.now());
    this.setMessage(message);
    this.setHttpStatus(status);
  }

//  public ErrorMessage(String status) {
//    this();
//    this.httpStatus = status;
//  }
//
//  public ErrorMessage(HttpStatus status, Throwable ex) {
//    this();
//    this.httpStatus = status;
//    this.message = "Unexpected error";
//  }
//
//  public ErrorMessage(HttpStatus status, String message, Throwable ex) {
//    this();
//    this.httpStatus = status;
//    this.message = message;
//  }

  public JsonObject getJson() {
    return jsonObject;
  }

  private void setTimestamp(LocalDateTime localDateTime) {
    jsonObject.put(ErrorMessage.TIMESTAMP_KEY, localDateTime.toString());
  }

  public String getMessage() {
    return jsonObject.getValue(ErrorMessage.MESSAGE_KEY).toString();
  }

  public void setMessage(String message) {
    jsonObject.put(ErrorMessage.MESSAGE_KEY, message);
  }

  public String getHttpStatus() {
    return jsonObject.getValue(ErrorMessage.HTTP_STATUS_KEY).toString();
  }

  public void setHttpStatus(String status) {
    jsonObject.put(ErrorMessage.HTTP_STATUS_KEY, status);
  }

}
