package de.doubleslash.tt293.springapp.book;

import de.doubleslash.tt293.springapp.author.AuthorMapper;
import de.doubleslash.tt293.springapp.publisher.PublisherMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PublisherMapper.class, AuthorMapper.class})
public interface BookMapper {

    @Mapping(source = "bookEntity.publisherEntity", target = "publisher")
    @Mapping(source = "bookEntity.authorEntities", target = "authors")
    BookDto bookEntityToBookDto(BookEntity bookEntity);

    /*
     * Do not map the Authors or Publisher to an Entity
     * each Author andPublisher has to be added through
     * manually because we do not allow to add a non-existing
     * Author or Publisher each of them have to be found in
     * the Database first.
     */
    @Mapping(target = "publisherEntity", ignore = true)
    @Mapping(target = "authorEntities", ignore = true)
    BookEntity bookPostDtoToBookEntity(BookPostDto bookPostDto);

}
