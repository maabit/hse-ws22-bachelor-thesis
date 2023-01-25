package de.doubleslash.tt293.springapp.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.doubleslash.tt293.springapp.author.AuthorPostDto;
import de.doubleslash.tt293.springapp.publisher.PublisherPostDto;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookPostDto {

    private String isbn;

    private String title;

    private PublisherPostDto publisher;

    private List<AuthorPostDto> authors;

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

    public PublisherPostDto getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherPostDto publisher) {
        this.publisher = publisher;
    }

    public List<AuthorPostDto> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorPostDto> authors) {
        this.authors = authors;
    }

}
