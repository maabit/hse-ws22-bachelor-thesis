package de.doubleslash.tt293.springapp.book;

import de.doubleslash.tt293.springapp.author.AuthorEntity;
import de.doubleslash.tt293.springapp.author.AuthorPostDto;
import de.doubleslash.tt293.springapp.author.AuthorRepository;
import de.doubleslash.tt293.springapp.exception.AlreadyExistsException;
import de.doubleslash.tt293.springapp.exception.ExceptionUtils;
import de.doubleslash.tt293.springapp.exception.NotFoundException;
import de.doubleslash.tt293.springapp.publisher.PublisherEntity;
import de.doubleslash.tt293.springapp.publisher.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookMapper bookMapper;

    public List<BookDto> getAllBooks() {
        Iterable<BookEntity> bookEntities = bookRepository.findAll();
        return StreamSupport.stream(bookEntities.spliterator(), false)
                .map(bookEntity -> bookMapper.bookEntityToBookDto(bookEntity))
                .collect(Collectors.toList());
    }

    public BookDto getOne(String isbn) throws NotFoundException {
        Optional<BookEntity> bookEntity = bookRepository.findById(isbn);
        if (bookEntity.isEmpty()) {
            throw ExceptionUtils.createBookNotFoundException(isbn);
        }
        return bookMapper.bookEntityToBookDto(bookEntity.get());
    }

    public void create(BookPostDto bookPostDto) throws AlreadyExistsException, NotFoundException {
        if (bookRepository.existsById(bookPostDto.getIsbn())) {
            throw ExceptionUtils.createBookAlreadyExistsException(bookPostDto.getIsbn());
        }
        BookEntity bookEntity = bookMapper.bookPostDtoToBookEntity(bookPostDto);

        Optional<PublisherEntity> publisherEntity = publisherRepository.findByName(bookPostDto.getPublisher().getName());
        if (publisherEntity.isEmpty()) {
            throw ExceptionUtils.createPublisherNotFoundException(bookPostDto.getPublisher().getName());
        }

        bookEntity.setPublisherEntity(publisherEntity.get());

        for (AuthorPostDto author : bookPostDto.getAuthors()) {
            Optional<AuthorEntity> authorEntity = authorRepository.findByName(author.getName());
            if (authorEntity.isEmpty()) {
                throw ExceptionUtils.createAuthorNotFoundException(author.getName());
            }
            bookEntity.addAuthorEntity(authorEntity.get());
        }

        bookRepository.save(bookEntity);
    }

    public void delete(String isbn) throws NotFoundException {
        Optional<BookEntity> bookEntity = bookRepository.findById(isbn);
        if (bookEntity.isEmpty()) {
            throw ExceptionUtils.createBookNotFoundException(isbn);
        }
        bookRepository.delete(bookEntity.get());
    }

}
