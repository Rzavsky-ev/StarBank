package org.skypro.starbank.model.dynamicRule.dynamicRuleRecommendationSet;

import lombok.Getter;
import org.skypro.starbank.exception.UnknownOperatorException;

@Getter
public enum ComparisonOperator {
    GREATER_THAN(">"),
    LESS_THAN("<"),
    EQUALS("="),
    GREATER_OR_EQUAL(">="),
    LESS_OR_EQUAL("<=");

    private final String symbol;

    ComparisonOperator(String symbol) {
        this.symbol = symbol;
    }

    public static ComparisonOperator fromSymbol(String symbol) {
        for (ComparisonOperator operator : values()) {
            if (operator.symbol.equals(symbol)) {
                return operator;
            }
        }
        throw new UnknownOperatorException("Неизвестный оператор: " + symbol);
    }
}
