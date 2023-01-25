package de.doubleslash.tt293.vertxapp.book;

import de.doubleslash.tt293.vertxapp.author.AuthorDto;
import de.doubleslash.tt293.vertxapp.publisher.PublisherDto;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;

public class BookDto {

  static final String ISBN_KEY = "isbn";

  static final String TITLE_KEY = "title";

  static final String PUBLISHER_KEY = "publisher";

  static final String AUTHORS_KEY = "authors";

  private final JsonObject jsonObject;

  public BookDto(final JsonObject jsonObject) {
    this.jsonObject = jsonObject;
  }

  public JsonObject getJson() {
    return jsonObject;
  }

  public String getIsbn() {
    return jsonObject.getValue(BookDto.ISBN_KEY).toString();
  }

  public void setIsbn(String isbn) {
    jsonObject.put(BookDto.ISBN_KEY, isbn);
  }

  public String getTitle() {
    return jsonObject.getValue(BookDto.TITLE_KEY).toString();
  }

  public void setTitle(String title) {
    jsonObject.put(BookDto.TITLE_KEY, title);
  }

  public JsonObject getPublisher() {
    return jsonObject.getJsonObject(BookDto.PUBLISHER_KEY);
  }

  public void setPublisher(PublisherDto publisher) {
    jsonObject.put(BookDto.PUBLISHER_KEY, publisher.getJson());
  }

  public JsonArray getAuthors() {
    return jsonObject.getJsonArray(BookDto.AUTHORS_KEY);
  }

  public void setAuthors(List<AuthorDto> authors) {
    JsonArray jsonArray = new JsonArray();
    for (AuthorDto authorDto : authors) {
      jsonArray.add(authorDto.getJson());
    }
    jsonObject.put(BookDto.AUTHORS_KEY, jsonArray);
  }

}
