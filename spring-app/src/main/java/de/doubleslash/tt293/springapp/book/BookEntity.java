package de.doubleslash.tt293.springapp.book;


import de.doubleslash.tt293.springapp.author.AuthorEntity;
import de.doubleslash.tt293.springapp.publisher.PublisherEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
public class BookEntity {
    @Id
    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "title", nullable = false)
    private String title;
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private PublisherEntity publisherEntity;

    @ManyToMany
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "isbn"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<AuthorEntity> authorEntities = new ArrayList<>();

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

    public PublisherEntity getPublisherEntity() {
        return publisherEntity;
    }

    public void setPublisherEntity(PublisherEntity publisherEntity) {
        this.publisherEntity = publisherEntity;
    }

    public List<AuthorEntity> getAuthorEntities() {
        return authorEntities;
    }

    public void setAuthorEntities(List<AuthorEntity> authorEntities) {
        this.authorEntities = authorEntities;
    }

    /*
     * the add/remove utility methods are mandatory if you use bidirectional associations
     * so that you can make sure that both sides of the association are in sync
     */
    public void addAuthorEntity(AuthorEntity authorEntity) {
        authorEntities.add(authorEntity);
        authorEntity.getBookEntities().add(this);
    }

    public void removeAuthorEntity(AuthorEntity authorEntity) {
        authorEntities.remove(authorEntity);
        authorEntity.getBookEntities().remove(this);
    }

}
