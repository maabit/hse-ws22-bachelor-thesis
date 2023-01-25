package de.doubleslash.tt293.vertxapp.book;

import de.doubleslash.tt293.vertxapp.author.AuthorPostDto;
import de.doubleslash.tt293.vertxapp.publisher.PublisherPostDto;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;

public class BookPostDto {

  static final String ISBN_KEY = "isbn";

  static final String TITLE_KEY = "title";

  static final String PUBLISHER_KEY = "publisher";

  static final String AUTHORS_KEY = "authors";

  private final JsonObject jsonObject;

  public BookPostDto(final JsonObject jsonObject) {
    this.jsonObject = jsonObject;
  }

  public JsonObject getJson() {
    return jsonObject;
  }

  public String getIsbn() {
    return jsonObject.getValue(BookPostDto.ISBN_KEY).toString();
  }

  public void setIsbn(String isbn) {
    jsonObject.put(BookPostDto.ISBN_KEY, isbn);
  }

  public String getTitle() {
    return jsonObject.getValue(BookPostDto.TITLE_KEY).toString();
  }

  public void setTitle(String title) {
    jsonObject.put(BookPostDto.TITLE_KEY, title);
  }

  public JsonObject getPublisher() {
    return jsonObject.getJsonObject(BookPostDto.PUBLISHER_KEY);
  }

  public void setPublisher(PublisherPostDto publisher) {
    jsonObject.put(BookPostDto.PUBLISHER_KEY, publisher.getJson());
  }

  public JsonArray getAuthors() {
    return jsonObject.getJsonArray(BookPostDto.AUTHORS_KEY);
  }

  public void setAuthors(List<AuthorPostDto> authors) {
    JsonArray jsonArray = new JsonArray();
    for (AuthorPostDto authorPostDto : authors) {
      jsonArray.add(authorPostDto.getJson());
    }
    jsonObject.put(BookPostDto.AUTHORS_KEY, jsonArray);
  }
}
