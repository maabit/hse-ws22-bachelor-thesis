package de.doubleslash.tt293.springapp.book;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<BookEntity, String> {

}
