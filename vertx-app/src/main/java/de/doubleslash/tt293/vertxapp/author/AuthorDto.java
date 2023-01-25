package de.doubleslash.tt293.vertxapp.author;

import io.vertx.core.json.JsonObject;

public class AuthorDto {

  static final String ID_KEY = "id";

  static final String NAME_KEY = "name";

  protected JsonObject jsonObject;

  public AuthorDto() {
  }

  public AuthorDto(final JsonObject jsonObject) {
    this.jsonObject = jsonObject;
  }

  public JsonObject getJson() {
    return jsonObject;
  }

  public String getId() {
    return jsonObject.getValue(AuthorDto.ID_KEY).toString();
  }

  public void setId(String id) {
    jsonObject.put(AuthorDto.ID_KEY, id);
  }

  public String getName() {
    return jsonObject.getValue(AuthorDto.NAME_KEY).toString();
  }

  public void setName(String name) {
    jsonObject.put(AuthorDto.NAME_KEY, name);
  }

}
