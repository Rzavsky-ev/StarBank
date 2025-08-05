package org.skypro.starbank.exception;

/**
 * Исключение, возникающее при обнаружении неизвестного или неподдерживаемого оператора
 * в условиях динамических правил.
 * <p>
 * Типичные случаи возникновения:
 * <ul>
 *   <li>Использование оператора, не предусмотренного системой</li>
 *   <li>Опечатка в названии оператора</li>
 *   <li>Попытка использовать оператор в неподдерживаемом контексте</li>
 * </ul>
 * <p>
 * Наследуется от {@link RuntimeException}, что делает его unchecked-исключением.
 */
public class UnknownOperatorException extends RuntimeException {
    public UnknownOperatorException(String message) {
        super(message);
    }
}
