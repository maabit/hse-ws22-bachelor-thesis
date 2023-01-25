package de.doubleslash.tt293.springapp.exception;

import de.doubleslash.tt293.springapp.messages.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorMessage> handleException(Exception exception) {
        LOGGER.error(exception.getMessage());
        return buildResponseEntity(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception));
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException exception) {
        return buildResponseEntity(new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage(), exception));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    protected ResponseEntity<ErrorMessage> handleAlreadyExistsException(AlreadyExistsException exception) {
        return buildResponseEntity(new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage(), exception));
    }

    private ResponseEntity<ErrorMessage> buildResponseEntity(ErrorMessage errorMessage) {
        return new ResponseEntity<>(errorMessage, errorMessage.getHttpStatus());
    }
}
