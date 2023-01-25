package de.doubleslash.tt293.vertxapp.book;

import de.doubleslash.tt293.vertxapp.author.AuthorMapper;
import de.doubleslash.tt293.vertxapp.author.AuthorPostDto;
import de.doubleslash.tt293.vertxapp.publisher.PublisherMapper;
import io.vertx.core.json.JsonObject;

import java.util.stream.Collectors;

public class BookMapper {

  private final AuthorMapper authorMapper;
  private final PublisherMapper publisherMapper;

  public BookMapper(AuthorMapper authorMapper, PublisherMapper publisherMapper) {
    this.authorMapper = authorMapper;
    this.publisherMapper = publisherMapper;
  }

  public BookDto bookEntityToBookDto(BookEntity bookEntity) {
    BookDto bookDto = new BookDto(new JsonObject());
    bookDto.setIsbn(bookEntity.getIsbn());
    bookDto.setTitle(bookEntity.getTitle());
    bookDto.setPublisher(publisherMapper.publisherEntityToPublisherDto(bookEntity.getPublisher()));
    bookDto.setAuthors(authorMapper.authorEntityListToAuthorDtoList(bookEntity.getAuthorEntities()));
    return bookDto;
  }


  public BookEntity bookPostDtoToBookEntity(BookPostDto bookPostDto) {
    BookEntity bookEntity = new BookEntity();
    bookEntity.setIsbn(bookPostDto.getIsbn());
    bookEntity.setTitle(bookPostDto.getTitle());
    return bookEntity;
  }
}
