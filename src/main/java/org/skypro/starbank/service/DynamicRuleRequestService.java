package org.skypro.starbank.service;

import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRule;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRuleRequest;

import java.util.List;

public interface DynamicRuleRequestService {

    DynamicRule createDynamicRule(DynamicRuleRequest request);

    void removeDynamicRule(Long id);

    List<DynamicRule> showAllDynamicRules();

    void clearCaches();
}
