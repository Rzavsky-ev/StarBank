package org.skypro.starbank.service;

import org.skypro.starbank.model.recommendation.RuleRecommendationDTO;
import org.skypro.starbank.model.recommendationRuleSet.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StarBankServiceImpl implements StarBankService{

    private final List<RecommendationRuleSet> recommendationRuleSets;

    public StarBankServiceImpl(List<RecommendationRuleSet> recommendationRuleSets) {
        this.recommendationRuleSets = new ArrayList<>(recommendationRuleSets);
    }

    public List<RuleRecommendationDTO> defineRecommendations(UUID userId) {
        return recommendationRuleSets.stream()
                .map(rule -> rule.getRecommendation(userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}
