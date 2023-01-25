package de.doubleslash.tt293.springapp.book;


import de.doubleslash.tt293.springapp.author.AuthorDto;
import de.doubleslash.tt293.springapp.publisher.PublisherDto;

import java.util.List;

public class BookDto {

    private String isbn;

    private String title;

    private PublisherDto publisher;

    private List<AuthorDto> authors;

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

    public PublisherDto getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherDto publisher) {
        this.publisher = publisher;
    }

    public List<AuthorDto> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDto> authors) {
        this.authors = authors;
    }

}
