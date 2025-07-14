package org.skypro.starbank.exception;

/**
 * Исключение, выбрасываемое при попытке выполнения операции с несуществующим клиентом.
 * Используется для обработки случаев, когда клиент с указанным идентификатором не найден в системе.
 */
public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String message) {
        super(message);
    }
}
