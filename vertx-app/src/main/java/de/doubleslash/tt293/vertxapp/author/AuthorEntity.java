package de.doubleslash.tt293.vertxapp.author;

public class AuthorEntity {

  private Integer id;

  private String name;

  public static AuthorEntity of(Integer id, String name) {
    AuthorEntity authorEntity = new AuthorEntity();
    authorEntity.setId(id);
    authorEntity.setName(name);
    return authorEntity;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "AuthorEntity{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
