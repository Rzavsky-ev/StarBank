package org.skypro.starbank.controller;

import org.skypro.starbank.exception.DatabaseOperationException;
import org.skypro.starbank.exception.DynamicRuleNotFoundInDatabaseException;
import org.skypro.starbank.exception.EmptyDatabaseException;
import org.skypro.starbank.exception.EmptyRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DynamicRuleControllerAdvice {

    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<String> databaseOperation(DatabaseOperationException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DynamicRuleNotFoundInDatabaseException.class)
    public ResponseEntity<String> databaseOperation(
            DynamicRuleNotFoundInDatabaseException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmptyDatabaseException.class)
    public ResponseEntity<String> databaseOperation(
            EmptyDatabaseException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmptyRequestException.class)
    public ResponseEntity<String> databaseOperation(
            EmptyRequestException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}