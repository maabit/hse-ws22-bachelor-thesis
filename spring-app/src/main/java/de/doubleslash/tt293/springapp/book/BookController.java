package de.doubleslash.tt293.springapp.book;

import de.doubleslash.tt293.springapp.exception.AlreadyExistsException;
import de.doubleslash.tt293.springapp.exception.NotFoundException;
import de.doubleslash.tt293.springapp.messages.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAll() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto getOne(@PathVariable String isbn) throws NotFoundException {
        return bookService.getOne(isbn);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage add(@RequestBody BookPostDto bookPostDto) throws AlreadyExistsException, NotFoundException {
        bookService.create(bookPostDto);
        return new ResponseMessage(String.format("Book with ISBN %s was created.", bookPostDto.getIsbn()));
    }

    @PutMapping("/{isbn}")
    public void update() {
    }

    @DeleteMapping("/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage delete(@PathVariable String isbn) throws NotFoundException {
        bookService.delete(isbn);
        return new ResponseMessage(String.format("Book with ISBN %s was deleted.", isbn));
    }

}

