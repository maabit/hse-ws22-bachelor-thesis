package de.doubleslash.tt293.springapp.author;

import de.doubleslash.tt293.springapp.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    AuthorMapper authorMapper;

    public List<AuthorDto> getAllAuthors() {
        Iterable<AuthorEntity> authorEntities = authorRepository.findAll();
        return StreamSupport.stream(authorEntities.spliterator(), false)
                .map(authorEntity -> authorMapper.authorEntityToAuthorDto(authorEntity))
                .collect(Collectors.toList());
    }

    public void createAuthor(AuthorPostDto authorPostDto) {
        if (authorRepository.existsByName(authorPostDto.getName())) {
            throw ExceptionUtils.createAuthorExistsException(authorPostDto.getName());
        }
        authorRepository.save(authorMapper.authorPostDtoToAuthorEntity(authorPostDto));
    }
}
