package de.doubleslash.tt293.vertxapp.publisher;

import de.doubleslash.tt293.vertxapp.exceptions.ExceptionUtils;
import io.vertx.core.Future;

import java.util.List;
import java.util.stream.Collectors;

public class PublisherService {

  private final PublisherRepository publisherRepository;

  private final PublisherMapper publisherMapper;

  public PublisherService(PublisherRepository publisherRepository, PublisherMapper publisherMapper) {
    this.publisherRepository = publisherRepository;
    this.publisherMapper = publisherMapper;
  }

  public Future<List<PublisherDto>> getAllPublishers() {
    return publisherRepository.findAll()
      .map(publisherEntities -> publisherEntities.stream()
        .map(publisherMapper::publisherEntityToPublisherDto)
        .collect(Collectors.toList()));
  }

  public Future<Integer> create(PublisherPostDto publisherPostDto) {
    return publisherRepository.existsByName(publisherPostDto.getName())
      .flatMap(exists -> {
        if (exists) throw ExceptionUtils.createPublisherAlreadyExistsException(publisherPostDto.getName());
        return publisherRepository.save(publisherMapper.publisherPostDtoToPublisherEntity(publisherPostDto));
      });
  }
}
