package de.doubleslash.tt293.springapp.restapi;

import de.doubleslash.tt293.springapp.publisher.PublisherEntity;
import de.doubleslash.tt293.springapp.publisher.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * @SpringBootTest lets us use the whole SpringApplication Context.
 * @AutoConfigureMockMvc only mocks the Web Layer (Controller Layer) that way the whole application can be tested
 * if no other class is mocked.
 */

@SpringBootTest
@AutoConfigureMockMvc
public class PublisherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    /*
     * Mock the Repository so that no actual action on the Database is performed.
     */
    @MockBean
    private PublisherRepository publisherRepository;

    @Test
    public void test_getAllPublishers() throws Exception {
        // given
        List<PublisherEntity> publisherEntities = new ArrayList<>();
        PublisherEntity publisherEntity1 = new PublisherEntity();
        publisherEntity1.setName("Test Publisher 1");
        publisherEntity1.setId(1);
        PublisherEntity publisherEntity2 = new PublisherEntity();
        publisherEntity2.setName("Test Publisher 2");
        publisherEntity2.setId(2);
        publisherEntities.add(publisherEntity1);
        publisherEntities.add(publisherEntity2);

        Mockito.when(publisherRepository.findAll()).thenReturn(publisherEntities);

        // when
        ResultActions resultActions = this.mockMvc.perform(get("/publishers/"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.[?(@.id == 1 && @.name == \"Test Publisher 1\")]").exists())
                .andExpect(jsonPath("$.[?(@.id == 2 && @.name == \"Test Publisher 2\")]").exists());
    }
}
