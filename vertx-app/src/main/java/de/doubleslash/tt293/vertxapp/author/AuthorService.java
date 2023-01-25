package de.doubleslash.tt293.vertxapp.author;

import de.doubleslash.tt293.vertxapp.exceptions.ExceptionUtils;
import io.vertx.core.Future;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorService {

  AuthorRepository authorRepository;

  AuthorMapper authorMapper;

  public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
    this.authorRepository = authorRepository;
    this.authorMapper = authorMapper;
  }

  public Future<List<AuthorDto>> getAllAuthors() {
    return authorRepository.findAll()
      .map(authorEntities -> authorEntities.stream()
        .map(authorMapper::authorEntityToAuthorDto)
        .collect(Collectors.toList()));
  }

  public Future<Integer> create(AuthorPostDto authorPostDto) {
    return authorRepository.existsByName(authorPostDto.getName())
      .flatMap(exists -> {
        if (exists) throw ExceptionUtils.createAuthorAlreadyExistsException(authorPostDto.getName());
        return authorRepository.save(authorMapper.authorPostDtoToAuthorEntity(authorPostDto));
      });
  }
}
