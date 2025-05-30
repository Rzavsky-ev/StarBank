package org.skypro.starbank.model.rule;

import java.util.function.Predicate;

public class Rule<T> {
    private final String descriptionOfRule;
    private final Predicate<T> condition;

    public Rule(String descriptionOfRule, Predicate<T> condition) {
        this.descriptionOfRule = descriptionOfRule;
        this.condition = condition;
    }

    public boolean check(T data) {
        return condition.test(data);
    }

    public Rule<T> and(Rule<T> other) {
        return new Rule<>(
                this.descriptionOfRule + " и " + other.descriptionOfRule,
                data -> this.check(data) && other.check(data)
        );
    }

    @Override
    public String toString() {
        return descriptionOfRule;
    }
}
