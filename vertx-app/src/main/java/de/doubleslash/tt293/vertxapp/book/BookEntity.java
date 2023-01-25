package de.doubleslash.tt293.vertxapp.book;

import de.doubleslash.tt293.vertxapp.author.AuthorEntity;
import de.doubleslash.tt293.vertxapp.publisher.PublisherEntity;

import java.util.List;

public class BookEntity {

  private String isbn;

  private String title;

  private PublisherEntity publisher;

  private List<AuthorEntity> authorEntities;

  public static BookEntity of(String isbn, String title, Integer publisherId, String publisherName) {
    BookEntity bookEntity = new BookEntity();
    bookEntity.setIsbn(isbn);
    bookEntity.setTitle(title);
    bookEntity.setPublisher(PublisherEntity.of(publisherId, publisherName));
    return bookEntity;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public PublisherEntity getPublisher() {
    return publisher;
  }

  public void setPublisher(PublisherEntity publisher) {
    this.publisher = publisher;
  }

  public List<AuthorEntity> getAuthorEntities() {
    return authorEntities;
  }

  public void setAuthorEntities(List<AuthorEntity> authorEntities) {
    this.authorEntities = authorEntities;
  }

  @Override
  public String toString() {
    return "BookEntity{" +
      "isbn='" + isbn + '\'' +
      ", title='" + title + '\'' +
      ", publisher=" + publisher +
      ", authorEntities=" + authorEntities +
      '}';
  }
}
