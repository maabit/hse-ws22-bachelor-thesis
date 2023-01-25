package de.doubleslash.tt293.vertxapp.messages;

import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;

public class ResponseMessage {

  static final String TIMESTAMP_KEY = "timestamp";

  static final String MESSAGE_KEY = "message";

  protected JsonObject jsonObject;

  public ResponseMessage(final JsonObject jsonObject) {
    this.jsonObject = jsonObject;
    this.setTimestamp(LocalDateTime.now());
  }

  public JsonObject getJson() {
    return jsonObject;
  }

  private void setTimestamp(LocalDateTime localDateTime) {
    jsonObject.put(ResponseMessage.TIMESTAMP_KEY, localDateTime.toString());
  }

  public String getMessage() {
    return jsonObject.getValue(ResponseMessage.MESSAGE_KEY).toString();
  }

  public void setMessage(String message) {
    jsonObject.put(ResponseMessage.MESSAGE_KEY, message);
  }

}
