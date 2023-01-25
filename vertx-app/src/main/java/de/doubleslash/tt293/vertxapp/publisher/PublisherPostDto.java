package de.doubleslash.tt293.vertxapp.publisher;

import io.vertx.core.json.JsonObject;

public class PublisherPostDto {

  static final String NAME_KEY = "name";

  protected JsonObject jsonObject;

  public PublisherPostDto() {
  }

  public PublisherPostDto(final JsonObject jsonObject) {
    this.jsonObject = jsonObject;
  }

  public JsonObject getJson() {
    return jsonObject;
  }

  public String getName() {
    return jsonObject.getValue(PublisherPostDto.NAME_KEY).toString();
  }

  public void setName(String name) {
    jsonObject.put(PublisherPostDto.NAME_KEY, name);
  }
}
