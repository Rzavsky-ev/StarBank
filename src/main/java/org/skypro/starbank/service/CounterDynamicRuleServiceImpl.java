package org.skypro.starbank.service;


import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.CounterDynamicRule;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRule;
import org.skypro.starbank.model.mapper.RuleStatsResponse;
import org.skypro.starbank.repository.jpa.CounterDynamicRuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для учета статистики использования динамических правил.
 * Обеспечивает создание счетчиков, обновление статистики и формирование отчетов.
 */
@Service
public class CounterDynamicRuleServiceImpl implements CounterDynamicRuleService {

    private final CounterDynamicRuleRepository counterDynamicRuleRepository;

    /**
     * Конструктор сервиса.
     *
     * @param counterDynamicRuleRepository репозиторий для работы со счетчиками правил
     */
    public CounterDynamicRuleServiceImpl(CounterDynamicRuleRepository counterDynamicRuleRepository) {
        this.counterDynamicRuleRepository = counterDynamicRuleRepository;
    }

    /**
     * Создает и сохраняет новый счетчик для динамического правила.
     * Выполняется в транзакции.
     *
     * @param rule динамическое правило, для которого создается счетчик
     */
    @Override
    @Transactional
    public void createCounterDynamicRule(DynamicRule rule) {
        CounterDynamicRule counterDynamicRule = new CounterDynamicRule();
        counterDynamicRule.setDynamicRule(rule);
        counterDynamicRuleRepository.save(counterDynamicRule);
    }

    /**
     * Увеличивает счетчик использования правила на 1.
     * Выполняется в транзакции.
     *
     * @param dynamicRuleId ID динамического правила
     * @throws org.springframework.dao.DataAccessException при ошибках доступа к БД
     */
    @Override
    @Transactional
    public void incrementCounter(Long dynamicRuleId) {
        counterDynamicRuleRepository.incrementCounter(dynamicRuleId);
    }

    /**
     * Формирует отчет по статистике использования всех правил.
     *
     * @return объект RuleStatsResponse с собранной статистикой,
     * содержащий список всех правил с количеством их использований
     */
    @Override
    public RuleStatsResponse getStats() {
        List<CounterDynamicRule> counters = counterDynamicRuleRepository.findAll();

        List<RuleStatsResponse.RuleStat> stats = counters.stream()
                .map(counter -> new RuleStatsResponse.RuleStat(
                        counter.getDynamicRule().getId().toString(),
                        counter.getCounter()
                ))
                .collect(Collectors.toList());

        return new RuleStatsResponse(stats);
    }
}
