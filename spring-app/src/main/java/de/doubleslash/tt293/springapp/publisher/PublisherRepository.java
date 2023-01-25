package de.doubleslash.tt293.springapp.publisher;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PublisherRepository extends CrudRepository<PublisherEntity, Integer> {

    boolean existsByName(String name);

    Optional<PublisherEntity> findByName(String name);

}
