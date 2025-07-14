package org.skypro.starbank.model.dynamicRule.dynamicRuleRecommendationSet;

import lombok.Getter;
import org.skypro.starbank.exception.UnknownOperatorException;

/**
 * Перечисление операторов сравнения, используемых в условиях динамических правил.
 * Предоставляет символьное представление операторов и методы для их обработки.
 */
@Getter
public enum ComparisonOperator {
    GREATER_THAN(">"),
    LESS_THAN("<"),
    EQUALS("="),
    GREATER_OR_EQUAL(">="),
    LESS_OR_EQUAL("<=");

    private final String symbol;

    /**
     * Конструктор оператора сравнения.
     *
     * @param symbol строковое представление оператора
     */
    ComparisonOperator(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Возвращает оператор сравнения по его символьному представлению.
     *
     * @param symbol символьное представление оператора
     * @return соответствующий оператор сравнения
     * @throws UnknownOperatorException если передан неизвестный символ оператора
     * @apiNote Список поддерживаемых операторов:
     * >, <, =, >=, <=
     */
    public static ComparisonOperator fromSymbol(String symbol) {
        for (ComparisonOperator operator : values()) {
            if (operator.symbol.equals(symbol)) {
                return operator;
            }
        }
        throw new UnknownOperatorException("Неизвестный оператор: " + symbol);
    }
}
