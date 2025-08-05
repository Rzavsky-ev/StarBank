package org.skypro.starbank.exception;

/**
 * Исключение, выбрасываемое при получении пустого или некорректно заполненного запроса.
 * <p>
 * Используется для обработки случаев, когда:
 * <ul>
 *   <li>Тело запроса отсутствует</li>
 *   <li>Обязательные поля запроса не заполнены</li>
 *   <li>Запрос содержит только null-значения</li>
 * </ul>
 * Наследуется от {@link RuntimeException}, что делает его unchecked-исключением.
 */
public class EmptyRequestException extends RuntimeException {
    public EmptyRequestException(String message) {
        super(message);
    }
}
