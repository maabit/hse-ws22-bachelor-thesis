package de.doubleslash.tt293.springapp.publisher;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PublisherMapper {

    //    @Mapping(source = "publisherEntity.name", target = "name")
    PublisherDto publisherEntityToPublisherDto(PublisherEntity publisherEntity);

    @Mapping(target = "id", ignore = true)
    PublisherEntity publisherPostDtoToPublisherEntity(PublisherPostDto publisherPostDto);

}
