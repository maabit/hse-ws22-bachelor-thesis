package de.doubleslash.tt293.vertxapp.bookauthor;

public class BookAuthorEntity {

  private String isbn;

  private Integer authorId;

  public static BookAuthorEntity of(String isbn, Integer author_id) {
    BookAuthorEntity bookAuthorEntity = new BookAuthorEntity();
    bookAuthorEntity.setIsbn(isbn);
    bookAuthorEntity.setAuthorId(author_id);
    return bookAuthorEntity;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public Integer getAuthorId() {
    return authorId;
  }

  public void setAuthorId(Integer authorId) {
    this.authorId = authorId;
  }

}
