package de.doubleslash.tt293.vertxapp.publisher;

import io.vertx.core.json.JsonObject;

public class PublisherMapper {


  public PublisherDto publisherEntityToPublisherDto(PublisherEntity publisherEntity) {
    PublisherDto publisherDto = new PublisherDto(new JsonObject());
    publisherDto.setId(String.valueOf(publisherEntity.getId()));
    publisherDto.setName(publisherEntity.getName());
    return publisherDto;
  }

  public PublisherEntity publisherPostDtoToPublisherEntity(PublisherPostDto publisherPostDto) {
    PublisherEntity publisherEntity = new PublisherEntity();
    publisherEntity.setName(publisherPostDto.getName());
    return publisherEntity;
  }

}
