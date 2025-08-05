package org.skypro.starbank.exception;

/**
 * Исключение, выбрасываемое при попытке доступа к несуществующему правилу.
 * <p>
 * Возникает в случаях, когда:
 * <ul>
 *   <li>Правило с указанным ID не найдено в системе</li>
 *   <li>Запрашиваемое правило было удалено</li>
 *   <li>Правило существует, но недоступно для текущего контекста</li>
 * </ul>
 * <p>
 * Наследуется от {@link RuntimeException}, что делает его unchecked-исключением.
 */
public class RuleNotFoundException extends RuntimeException {
    public RuleNotFoundException(String message) {
        super(message);
    }
}
