package org.skypro.starbank.model.dynamicRule.dynamicRuleRequest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

@Getter
@JsonDeserialize(using = QueryTypeDeserializer.class)
public enum QueryType {
    USER_OF("""
            Является ли пользователь, для которого ведется поиск рекомендаций, пользователем продукта X,
            где X — это первый аргумент запроса. Пользователь продукта X — этот пользователь, у которого есть хотя бы
            одна транзакция по продуктам данного типа X"""),
    ACTIVE_USER_OF("""
            Является ли пользователь, для которого ведется поиск рекомендаций, активным пользователем продукта X,
            где X — это первый аргумент запроса. Активный пользователь продукта X — это пользователь, у которого есть
             хотя бы пять транзакций по продуктам данного типа X.
            """),
    TRANSACTION_SUM_COMPARE("""
             Этот запрос сравнивает сумму всех транзакций типа Y по продуктам типа X с некоторой константой C.
            Где X — первый аргумент запроса, Y — второй аргумент запроса, а C — четвертый аргумент запроса."""),
    TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW("""
            Этот запрос сравнивает сумму всех транзакций типа DEPOSIT с суммой всех транзакций типа WITHDRAW
            по продукту X. Где X — первый аргумент запроса, а операция сравнения — второй аргумент запроса.""");

    private String description;

    QueryType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name() + " - " + description;
    }

}
