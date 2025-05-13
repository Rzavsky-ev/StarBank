package org.skypro.starbank.exception;

public class InvalidTransactionDataException extends RuntimeException {
    public InvalidTransactionDataException(String message) {
        super(message);
    }
}
