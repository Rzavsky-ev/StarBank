package org.skypro.starbank.controller;

import org.skypro.starbank.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Глобальный обработчик исключений для контроллеров, работающих с динамическими правилами.
 * Перехватывает исключения, возникающие в процессе работы с правилами, и возвращает
 * соответствующие HTTP-ответы с описанием ошибок.
 */
@ControllerAdvice
public class DynamicRuleControllerAdvice {

    /**
     * Обрабатывает исключения, связанные с операциями в базе данных.
     *
     * @param e перехваченное исключение DatabaseOperationException
     * @return ответ с сообщением об ошибке и статусом 500 (Internal Server Error)
     */
    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<String> handleDatabaseOperationException(DatabaseOperationException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Обрабатывает исключения при отсутствии динамического правила в базе данных.
     *
     * @param e перехваченное исключение DynamicRuleNotFoundInDatabaseException
     * @return ответ с сообщением об ошибке и статусом 500 (Internal Server Error)
     */
    @ExceptionHandler(DynamicRuleNotFoundInDatabaseException.class)
    public ResponseEntity<String> handleDynamicRuleNotFoundException(
            DynamicRuleNotFoundInDatabaseException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Обрабатывает исключения при работе с пустой базой данных.
     *
     * @param e перехваченное исключение EmptyDatabaseException
     * @return ответ с сообщением об ошибке и статусом 500 (Internal Server Error)
     */
    @ExceptionHandler(EmptyDatabaseException.class)
    public ResponseEntity<String> handleEmptyDatabaseException(
            EmptyDatabaseException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Обрабатывает исключения при получении пустого запроса.
     *
     * @param e перехваченное исключение EmptyRequestException
     * @return ответ с сообщением об ошибке и статусом 400 (Bad Request)
     */
    @ExceptionHandler(EmptyRequestException.class)
    public ResponseEntity<String> handleEmptyRequestException(
            EmptyRequestException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает исключения при использовании неизвестного оператора в правилах.
     *
     * @param e перехваченное исключение UnknownOperatorException
     * @return ответ с сообщением об ошибке и статусом 400 (Bad Request)
     */
    @ExceptionHandler(UnknownOperatorException.class)
    public ResponseEntity<String> handleUnknownOperatorException(
            UnknownOperatorException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает исключения при отсутствии запрошенного правила.
     *
     * @param e перехваченное исключение RuleNotFoundException
     * @return ответ с сообщением об ошибке и статусом 404 (Not Found)
     */
    @ExceptionHandler(RuleNotFoundException.class)
    public ResponseEntity<String> handleRuleNotFoundException(
            RuleNotFoundException e) {
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
