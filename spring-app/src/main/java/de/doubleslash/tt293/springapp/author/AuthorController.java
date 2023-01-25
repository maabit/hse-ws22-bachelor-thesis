package de.doubleslash.tt293.springapp.author;

import de.doubleslash.tt293.springapp.messages.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorDto> getAll() {
        return authorService.getAllAuthors();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage create(@RequestBody AuthorPostDto authorPostDto) {
        authorService.createAuthor(authorPostDto);
        return new ResponseMessage(String.format("Author with name %s was created.", authorPostDto.getName()));
    }
}
