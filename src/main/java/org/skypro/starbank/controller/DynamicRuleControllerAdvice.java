package org.skypro.starbank.controller;

import org.skypro.starbank.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DynamicRuleControllerAdvice {

    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<String> handleDatabaseOperationException(DatabaseOperationException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DynamicRuleNotFoundInDatabaseException.class)
    public ResponseEntity<String> handleDynamicRuleNotFoundException(
            DynamicRuleNotFoundInDatabaseException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmptyDatabaseException.class)
    public ResponseEntity<String> handleEmptyDatabaseException(
            EmptyDatabaseException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmptyRequestException.class)
    public ResponseEntity<String> handleEmptyRequestException(
            EmptyRequestException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnknownOperatorException.class)
    public ResponseEntity<String> handleUnknownOperatorException(
            UnknownOperatorException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuleNotFoundException.class)
    public ResponseEntity<String> handleRuleNotFoundException(
            RuleNotFoundException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
