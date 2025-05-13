package org.skypro.starbank.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
