package de.doubleslash.tt293.vertxapp.publisher;


import io.vertx.core.json.JsonObject;

public class PublisherDto {

  static final String ID_KEY = "id";

  static final String NAME_KEY = "name";

  protected JsonObject jsonObject;

  public PublisherDto() {
  }

  public PublisherDto(final JsonObject jsonObject) {
    this.jsonObject = jsonObject;
  }

  public JsonObject getJson() {
    return jsonObject;
  }

  public String getId() {
    return jsonObject.getValue(PublisherDto.ID_KEY).toString();
  }

  public void setId(String id) {
    jsonObject.put(PublisherDto.ID_KEY, id);
  }

  public String getName() {
    return jsonObject.getValue(PublisherDto.NAME_KEY).toString();
  }

  public void setName(String name) {
    jsonObject.put(PublisherDto.NAME_KEY, name);
  }

}
