package org.skypro.starbank.service;


import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.CounterDynamicRule;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRule;
import org.skypro.starbank.model.mapper.RuleStatsResponse;
import org.skypro.starbank.repository.jpa.CounterDynamicRuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CounterDynamicRuleServiceImpl implements CounterDynamicRuleService {

    private final CounterDynamicRuleRepository counterDynamicRuleRepository;

    public CounterDynamicRuleServiceImpl(CounterDynamicRuleRepository counterDynamicRuleRepository) {
        this.counterDynamicRuleRepository = counterDynamicRuleRepository;
    }

    @Transactional
    public void createCounterDynamicRule(DynamicRule rule) {
        CounterDynamicRule counterDynamicRule = new CounterDynamicRule();
        counterDynamicRule.setDynamicRule(rule);
        counterDynamicRuleRepository.save(counterDynamicRule);
    }

    @Transactional
    public void incrementCounter(Long dynamicRuleId) {
        counterDynamicRuleRepository.incrementCounter(dynamicRuleId);
    }

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
