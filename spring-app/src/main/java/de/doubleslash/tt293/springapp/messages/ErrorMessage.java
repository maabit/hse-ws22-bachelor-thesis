package de.doubleslash.tt293.springapp.messages;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorMessage {

    @JsonProperty("status")
    private HttpStatus httpStatus;

    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private final LocalDateTime localDateTime;
    private String message;
    private String debugMessage;

    private ErrorMessage() {
        localDateTime = LocalDateTime.now();
    }

    public ErrorMessage(HttpStatus status) {
        this();
        this.httpStatus = status;
    }

    public ErrorMessage(HttpStatus status, Throwable ex) {
        this();
        this.httpStatus = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ErrorMessage(HttpStatus status, String message, Throwable ex) {
        this();
        this.httpStatus = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }
}
