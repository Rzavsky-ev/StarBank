package org.skypro.starbank.service;

import org.skypro.starbank.model.recommendation.RecommendationDTO;
import org.skypro.starbank.model.recommendationRuleSet.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StarBankService {

    private final List<RecommendationRuleSet> recommendationRuleSets;

    public StarBankService(List<RecommendationRuleSet> recommendationRuleSets) {
        this.recommendationRuleSets = new ArrayList<>(recommendationRuleSets);
    }

    public List<RecommendationDTO> defineRecommendations(UUID userId) {
        return recommendationRuleSets.stream()
                .map(rule -> rule.getRecommendation(userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}
