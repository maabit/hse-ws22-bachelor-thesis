package de.doubleslash.tt293.vertxapp.exceptions;

public class AlreadyExistsException extends IllegalArgumentException {

  public AlreadyExistsException(String message) {
    super(message);
  }

}
