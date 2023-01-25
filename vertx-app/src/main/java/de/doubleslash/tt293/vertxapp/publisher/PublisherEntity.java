package de.doubleslash.tt293.vertxapp.publisher;

public class PublisherEntity {

  private Integer id;

  private String name;

  public static PublisherEntity of(Integer id, String name) {
    PublisherEntity publisherEntity = new PublisherEntity();
    publisherEntity.setId(id);
    publisherEntity.setName(name);
    return publisherEntity;
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
    return "PublisherEntity{" +
      "id=" + id +
      ", name='" + name + '\'' +
      '}';
  }
}
