package de.doubleslash.tt293.springapp.author;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDto authorEntityToAuthorDto(AuthorEntity authorEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookEntities", ignore = true)
    AuthorEntity authorPostDtoToAuthorEntity(AuthorPostDto authorPostDto);

}
