package org.skypro.starbank.exception;

public class EmptyDatabaseException extends RuntimeException {
    public EmptyDatabaseException(String message) {
        super(message);
    }
}
