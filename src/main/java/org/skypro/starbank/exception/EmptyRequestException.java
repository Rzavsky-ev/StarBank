package org.skypro.starbank.exception;

public class EmptyRequestException extends RuntimeException {
    public EmptyRequestException(String message) {
        super(message);
    }
}
