package de.doubleslash.tt293.springapp.exception;


public class ExceptionUtils {

    private static final String PUBLISHER_EXISTS_MESSAGE = "Publisher with name %s already exists.";
    private static final String AUTHOR_EXISTS_MESSAGE = "Author with name %s already exists.";
    public static final String BOOK_ALREADY_EXISTS_MESSAGE = "Book with ISBN %s already exists.";

    public static final String BOOK_NOT_FOUND_MESSAGE = "Book with ISBN %s was not found.";

    public static final String PUBLISHER_NOT_FOUND_MESSAGE = "Publisher with name %s was not found.";
    private static final String AUTHOR_NOT_FOUND_MESSAGE = "Author with name %s was not found.";


    public static AlreadyExistsException createBookAlreadyExistsException(String isbn) {
        return new AlreadyExistsException(String.format(BOOK_ALREADY_EXISTS_MESSAGE, isbn));
    }

    public static AlreadyExistsException createAuthorExistsException(String name) {
        return new AlreadyExistsException(String.format(AUTHOR_EXISTS_MESSAGE, name));
    }

    public static AlreadyExistsException createPublisherAlreadyExistsException(String name) {
        return new AlreadyExistsException(String.format(PUBLISHER_EXISTS_MESSAGE, name));
    }

    public static NotFoundException createBookNotFoundException(String isbn) {
        return new NotFoundException(String.format(BOOK_NOT_FOUND_MESSAGE, isbn));
    }

    public static NotFoundException createPublisherNotFoundException(String name) {
        return new NotFoundException(String.format(PUBLISHER_NOT_FOUND_MESSAGE, name));
    }

    public static NotFoundException createAuthorNotFoundException(String name) {
        return new NotFoundException(String.format(AUTHOR_NOT_FOUND_MESSAGE, name));
    }


}
