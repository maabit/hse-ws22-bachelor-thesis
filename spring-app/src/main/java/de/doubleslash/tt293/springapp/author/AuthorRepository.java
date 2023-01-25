package de.doubleslash.tt293.springapp.author;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends CrudRepository<AuthorEntity, Integer> {
    boolean existsByName(String authorName);

    Optional<AuthorEntity> findByName(String authorName);

    List<AuthorEntity> findAllByNameIn(List<String> authorNames);
}
