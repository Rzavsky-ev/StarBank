package org.skypro.starbank.service;

import org.skypro.starbank.model.dynamicRule.dynamicRuleRecommendationSet.DynamicRuleRecommendationSet;
import org.skypro.starbank.model.mapper.DynamicRuleMapper;
import org.skypro.starbank.model.recommendation.DynamicRuleRecommendationDTO;
import org.skypro.starbank.model.rule.Rule;
import org.skypro.starbank.repository.jpa.DynamicRuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DynamicRuleRecommendationServiceImpl implements DynamicRuleRecommendationService {
    private final DynamicRuleRepository dynamicRuleRepository;
    private final DynamicRuleRecommendationSet dynamicRuleRecommendationSet;
    private final DynamicRuleMapper dynamicRuleMapper;
    private final CounterDynamicRuleServiceImpl counterDynamicRuleService;

    public DynamicRuleRecommendationServiceImpl(DynamicRuleRepository dynamicRuleRepository,
                                                DynamicRuleRecommendationSet dynamicRuleRecommendationSet,
                                                DynamicRuleMapper dynamicRuleMapper,
                                                CounterDynamicRuleServiceImpl counterDynamicRuleService) {
        this.dynamicRuleRepository = dynamicRuleRepository;
        this.dynamicRuleRecommendationSet = dynamicRuleRecommendationSet;
        this.dynamicRuleMapper = dynamicRuleMapper;
        this.counterDynamicRuleService = counterDynamicRuleService;
    }

    private List<DynamicRuleRecommendationDTO> getAllRules() {
        return dynamicRuleRepository.findAll().stream()
                .map(dynamicRuleMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void incrementCounter(List<DynamicRuleRecommendationDTO> rules) {
        rules.forEach(rule -> {
            counterDynamicRuleService.incrementCounter(rule.getId());
        });
    }

    @Override
    public List<DynamicRuleRecommendationDTO> checkUserAgainstAllDynamicRules(UUID userId) {
        List<DynamicRuleRecommendationDTO> allRules = getAllRules();
        List<DynamicRuleRecommendationDTO> suitableRules = allRules.stream()
                .filter(rule -> {
                    Rule<UUID> combinedRule = dynamicRuleRecommendationSet.createCombinedRule(rule);
                    return combinedRule.check(userId);
                })
                .toList();
        incrementCounter(suitableRules);
        return suitableRules;
    }
}
