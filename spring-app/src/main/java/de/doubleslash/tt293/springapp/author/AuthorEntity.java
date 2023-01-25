package de.doubleslash.tt293.springapp.author;

import de.doubleslash.tt293.springapp.book.BookEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /*
     * The mappedBy attribute marks that, in this bidirectional relationship,
     * the Book entity owns the association.
     * This is needed since only one side can own a relationship,
     * and changes are only propagated to the database from this particular side
     */
    @ManyToMany(mappedBy = "authorEntities")
    private List<BookEntity> bookEntities = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookEntity> getBookEntities() {
        return bookEntities;
    }

    public void setBookEntities(List<BookEntity> bookEntities) {
        this.bookEntities = bookEntities;
    }

}
