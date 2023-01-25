package de.doubleslash.tt293.springapp.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.doubleslash.tt293.springapp.author.AuthorPostDto;
import de.doubleslash.tt293.springapp.book.BookPostDto;
import de.doubleslash.tt293.springapp.publisher.PublisherPostDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * @SpringBootTest lets us use the whole SpringApplication Context.
 * @AutoConfigureMockMvc only mocks the Web Layer (Controller Layer) that way the whole application can be tested
 * if no other class is mocked.
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_getAllBooks() throws Exception {
        // given

        // when
        ResultActions resultActions = this.mockMvc.perform(get("/books/"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].isbn", containsInAnyOrder("9783826697005","9783446455122")));
    }

    @Test
    public void test_getBookById() throws Exception {
        // given

        // when
        ResultActions resultActions = this.mockMvc.perform(get("/books/9783826697005"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("isbn", is("9783826697005")))
                .andExpect(jsonPath("title", is("Design Patterns")))
                .andExpect(jsonPath("publisher.id", is(2)))
                .andExpect(jsonPath("publisher.name", is("mitp")))
                .andExpect(jsonPath("authors.[?(@.id == 2 && @.name == \"Erich Gamma\")]").exists())
                .andExpect(jsonPath("authors.[?(@.id == 3 && @.name == \"Richard Helm\")]").exists())
                .andExpect(jsonPath("authors.[?(@.id == 4 && @.name == \"Ralph Johnson\")]").exists())
                .andExpect(jsonPath("authors.[?(@.id == 5 && @.name == \"John Vlissides\")]").exists());
    }

    /*
     * Methods Annotated with @Transactional rollback afterward if no Commit is performed.
     * Possible configuration of AutoCommit could be needed.
     * Google if the desired effect of @Transactional does not occur.
     */
    @Test
    @Transactional
    public void test_createBook() throws Exception {
        // given
        AuthorPostDto authorPostDto1 = new AuthorPostDto();
        authorPostDto1.setName("Erich Gamma");

        AuthorPostDto authorPostDto2 = new AuthorPostDto();
        authorPostDto2.setName("Richard Helm");

        List<AuthorPostDto> authorPostDtos = new ArrayList<>(Arrays.asList(authorPostDto1, authorPostDto2));

        PublisherPostDto publisherPostDto = new PublisherPostDto();
        publisherPostDto.setName("mitp");

        BookPostDto bookPostDto = new BookPostDto();
        bookPostDto.setIsbn("0000000000000");
        bookPostDto.setTitle("Test Title");
        bookPostDto.setPublisher(publisherPostDto);
        bookPostDto.setAuthors(authorPostDtos);

        ObjectMapper mapper = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String request = mapper.writeValueAsString(bookPostDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(post("/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.message", is("Book with ISBN 0000000000000 was created.")));
    }

    /*
     * Methods Annotated with @Transactional rollback afterward if no Commit is performed.
     * Possible configuration of AutoCommit could be needed.
     * Google if the desired effect of @Transactional does not occur.
     */
    @Test
    @Transactional
    public void test_deleteBook() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(delete("/books/9783826697005"));

        // then
        resultActions.andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.message", is("Book with ISBN 9783826697005 was deleted.")));
    }

}
