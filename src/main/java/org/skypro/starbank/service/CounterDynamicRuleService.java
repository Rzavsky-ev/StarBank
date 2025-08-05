package org.skypro.starbank.service;

import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRule;
import org.skypro.starbank.model.mapper.RuleStatsResponse;

/**
 * Сервис для работы со статистикой использования динамических правил.
 * Обеспечивает учет и предоставление информации о частоте применения правил.
 */
public interface CounterDynamicRuleService {

    /**
     * Создает новый счетчик для динамического правила.
     *
     * @param rule динамическое правило, для которого создается счетчик
     */
    void createCounterDynamicRule(DynamicRule rule);

    /**
     * Увеличивает счетчик использования динамического правила на 1.
     *
     * @param dynamicRuleId идентификатор динамического правила
     */
    void incrementCounter(Long dynamicRuleId);

    /**
     * Возвращает статистику по всем динамическим правилам.
     *
     * @return объект {@link RuleStatsResponse} с собранной статистикой,
     * включая информацию о частоте использования каждого правила
     */
    RuleStatsResponse getStats();
}
