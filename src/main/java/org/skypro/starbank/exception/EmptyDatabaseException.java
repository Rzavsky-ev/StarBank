package org.skypro.starbank.exception;

/**
 * Исключение, выбрасываемое при попытке работы с пустой базой данных.
 * Возникает в ситуациях, когда ожидаются данные в базе, но таблица или результирующий набор пусты.
 * Наследуется от {@link RuntimeException}, что делает его unchecked-исключением.
 */
public class EmptyDatabaseException extends RuntimeException {
    public EmptyDatabaseException(String message) {
        super(message);
    }
}
