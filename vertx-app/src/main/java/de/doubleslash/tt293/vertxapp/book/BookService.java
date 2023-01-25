package de.doubleslash.tt293.vertxapp.book;

import de.doubleslash.tt293.vertxapp.author.AuthorRepository;
import de.doubleslash.tt293.vertxapp.exceptions.ExceptionUtils;
import de.doubleslash.tt293.vertxapp.publisher.PublisherRepository;
import de.doubleslash.tt293.vertxapp.utils.FutureUtils;
import io.vertx.core.Future;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BookService {

  private final BookRepository bookRepository;
  private final PublisherRepository publisherRepository;
  private final AuthorRepository authorRepository;

  private final BookMapper bookMapper;

  public BookService(BookRepository bookRepository,
                     PublisherRepository publisherRepository,
                     AuthorRepository authorRepository,
                     BookMapper bookMapper) {
    this.bookRepository = bookRepository;
    this.publisherRepository = publisherRepository;
    this.authorRepository = authorRepository;
    this.bookMapper = bookMapper;
  }

  public Future<BookDto> getOne(String isbn) {
    return bookRepository.findByIsbn(isbn)
      .map(bookMapper::bookEntityToBookDto);
  }

  public Future<List<BookDto>> getAll() {
    return bookRepository.findAll()
      .map(bookEntities -> bookEntities.stream()
        .map(bookMapper::bookEntityToBookDto)
        .collect(Collectors.toList()));
  }

  public Future<List<Integer>> create(BookPostDto bookPostDto) {
    return bookRepository.existsByIsbn(bookPostDto.getIsbn())
      .flatMap(exists -> {
        if (exists) throw ExceptionUtils.createBookAlreadyExistsException(bookPostDto.getIsbn());
        BookEntity bookEntity = bookMapper.bookPostDtoToBookEntity(bookPostDto);
        return publisherRepository.findByName(bookPostDto.getPublisher().getString("name"))
          .flatMap(publisherEntity -> {
            bookEntity.setPublisher(publisherEntity);
            return FutureUtils.allOfFutures(IntStream.range(0, bookPostDto.getAuthors().size())
              .mapToObj(i -> authorRepository.findByName(bookPostDto.getAuthors().getJsonObject(i).getString("name")))
              .collect(Collectors.toList()));
          }).flatMap(authorEntities -> {
            bookEntity.setAuthorEntities(authorEntities);
            return bookRepository.save(bookEntity);
          });
      });
  }

  public Future<Integer> delete(String isbn) {
    return bookRepository.findByIsbn(isbn)
      .flatMap(bookRepository::delete);
  }
}
