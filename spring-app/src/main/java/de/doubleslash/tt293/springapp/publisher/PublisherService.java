package de.doubleslash.tt293.springapp.publisher;

import de.doubleslash.tt293.springapp.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PublisherService {

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    PublisherMapper publisherMapper;

    public List<PublisherDto> getAllPublishers() {
        Iterable<PublisherEntity> publisherEntities = publisherRepository.findAll();
        return StreamSupport.stream(publisherEntities.spliterator(), false)
                .map(publisherEntity -> publisherMapper.publisherEntityToPublisherDto(publisherEntity))
                .collect(Collectors.toList());
    }

    public void createPublisher(PublisherPostDto publisherPostDto) {
        if (publisherRepository.existsByName(publisherPostDto.getName())) {
            throw ExceptionUtils.createPublisherAlreadyExistsException(publisherPostDto.getName());
        }
        publisherRepository.save(publisherMapper.publisherPostDtoToPublisherEntity(publisherPostDto));
    }
}
