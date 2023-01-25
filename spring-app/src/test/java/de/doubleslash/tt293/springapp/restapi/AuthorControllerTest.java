package de.doubleslash.tt293.springapp.restapi;

import de.doubleslash.tt293.springapp.author.AuthorController;
import de.doubleslash.tt293.springapp.author.AuthorDto;
import de.doubleslash.tt293.springapp.author.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

/*
 * Without the @SpringBootTest the SpringApplication is not loaded.
 * therefore all Layers have to be Mocked.
 * Alternative to @SpringBootTest, @WebMvcTest can be used.
 * but it only mocks the Web Layer all other Layers still have to be mocked.
 * Mind that if the Web Layer is mocked the tested Methods behave as if they were called from a Web Client.
 * The return value of the Controller Methods change from the actual Returned value to a JSON value.
 */
@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {

    @InjectMocks
    private AuthorController authorController;

    @Mock
    private AuthorService authorService;

    @Test
    public void test_getAllAuthors() {
        // given
        List<AuthorDto> authorDtos = new ArrayList<>();
        AuthorDto authorDto1 = new AuthorDto();
        authorDto1.setId(1);
        authorDto1.setName("Test Author 1");
        AuthorDto authorDto2 = new AuthorDto();
        authorDto2.setId(2);
        authorDto2.setName("Test Author 2");
        authorDtos.add(authorDto1);
        authorDtos.add(authorDto2);

        Mockito.when(authorService.getAllAuthors()).thenReturn(authorDtos);

        // when

        List<AuthorDto> authorDtoList = authorController.getAll();

        // then
        Assertions.assertEquals(authorDtoList, authorDtos);
    }
}
