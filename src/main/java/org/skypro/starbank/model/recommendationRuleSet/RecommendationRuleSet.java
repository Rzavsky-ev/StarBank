package org.skypro.starbank.model.recommendationRuleSet;

import org.skypro.starbank.model.recommendation.RuleRecommendationDTO;


import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<RuleRecommendationDTO> getRecommendation(UUID id);
}

