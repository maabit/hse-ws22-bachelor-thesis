package de.doubleslash.tt293.springapp.publisher;

import de.doubleslash.tt293.springapp.messages.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    PublisherService publisherService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<PublisherDto> getAll() {
        return publisherService.getAllPublishers();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage create(@RequestBody PublisherPostDto publisherPostDto) {
        publisherService.createPublisher(publisherPostDto);
        return new ResponseMessage(String.format("Publisher with name %s was created", publisherPostDto.getName()));
    }

}
