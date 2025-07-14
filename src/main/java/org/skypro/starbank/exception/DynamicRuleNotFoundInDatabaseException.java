package org.skypro.starbank.exception;

/**
 * Исключение, выбрасываемое при попытке доступа к несуществующему динамическому правилу в базе данных.
 * Указывает на ситуацию, когда запрошенное динамическое правило не найдено в хранилище данных.
 * Наследует {@link RuntimeException}, что делает его unchecked-исключением.
 */
public class DynamicRuleNotFoundInDatabaseException extends RuntimeException {
    public DynamicRuleNotFoundInDatabaseException(String message) {
        super(message);
    }
}
