package org.skypro.starbank.service;

import org.skypro.starbank.dynamicRule.DynamicRule;
import org.skypro.starbank.dynamicRule.DynamicRuleRequest;

import java.util.List;

public interface DynamicRuleService {

    DynamicRule createDynamicRule(DynamicRuleRequest request);

    void removeDynamicRule(Long id);

    List<DynamicRule> showAllDynamicRules();
}
