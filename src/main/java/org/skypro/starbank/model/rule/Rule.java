//заготовка
//package org.skypro.starbank.model.rule;
//
//import java.util.function.Predicate;
//
//public class Rule<T> {
//    private final String DescriptionOfRule; // Описание правила
//    private final Predicate<T> condition; // Условие проверки
//
//    public Rule(String description, Predicate<T> condition) {
//        this.DescriptionOfRule = description;
//        this.condition = condition;
//    }
//
//    public boolean check(T data) {
//        return condition.test(data);
//    }
//
//    @Override
//    public String toString() {
//        return DescriptionOfRule;
//    }
//}
