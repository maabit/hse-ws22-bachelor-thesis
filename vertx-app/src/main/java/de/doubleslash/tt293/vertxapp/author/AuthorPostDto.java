package de.doubleslash.tt293.vertxapp.author;

import io.vertx.core.json.JsonObject;

public class AuthorPostDto {

  static final String NAME_KEY = "name";

  protected JsonObject jsonObject;

  public AuthorPostDto() {
  }

  public AuthorPostDto(final JsonObject jsonObject) {
    this.jsonObject = jsonObject;
  }

  public JsonObject getJson() {
    return jsonObject;
  }

  public String getName() {
    return jsonObject.getValue(AuthorPostDto.NAME_KEY).toString();
  }

  public void setName(String name) {
    jsonObject.put(AuthorPostDto.NAME_KEY, name);
  }

}
