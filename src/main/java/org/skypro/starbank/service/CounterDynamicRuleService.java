package org.skypro.starbank.service;

import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRule;
import org.skypro.starbank.model.mapper.RuleStatsResponse;

public interface CounterDynamicRuleService {

    void createCounterDynamicRule(DynamicRule rule);

    void incrementCounter(Long dynamicRuleId);

    RuleStatsResponse getStats();
}
