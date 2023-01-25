package de.doubleslash.tt293.vertxapp.author;

import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class AuthorMapper {

  public AuthorDto authorEntityToAuthorDto(AuthorEntity authorEntity) {
    AuthorDto authorDto = new AuthorDto(new JsonObject());
    authorDto.setId(String.valueOf(authorEntity.getId()));
    authorDto.setName(authorEntity.getName());
    return authorDto;
  }

  public AuthorEntity authorPostDtoToAuthorEntity(AuthorPostDto authorPostDto) {
    AuthorEntity authorEntity = new AuthorEntity();
    authorEntity.setName(authorPostDto.getName());
    return authorEntity;
  }

  public List<AuthorDto> authorEntityListToAuthorDtoList(List<AuthorEntity> authorEntityList) {
    List<AuthorDto> authorDtoList = new ArrayList<>();
    for (AuthorEntity authorEntity : authorEntityList) {
      authorDtoList.add(authorEntityToAuthorDto(authorEntity));
    }
    return authorDtoList;
  }
}
