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

    public Rule<T> negate(String negatedDescription) {
        return new Rule<>(
                negatedDescription,
                data -> !this.condition.test(data)
        );
    }

    public static <T> Rule<T> alwaysTrue() {
        return new Rule<>("Всегда истина", data -> true);
    }

    @Override
    public String toString() {
        return descriptionOfRule;
    }
}
