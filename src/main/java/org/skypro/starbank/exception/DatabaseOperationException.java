package org.skypro.starbank.exception;

/**
 * Исключение, выбрасываемое при возникновении ошибок во время операций с базой данных.
 * Содержит информацию как об ошибке (сообщение), так и о первопричине (cause).
 * Наследуется от {@link RuntimeException}, что делает его unchecked-исключением.
 */
public class DatabaseOperationException extends RuntimeException {
    public DatabaseOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
