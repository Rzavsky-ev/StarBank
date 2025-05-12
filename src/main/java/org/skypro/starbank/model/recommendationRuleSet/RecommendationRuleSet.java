package org.skypro.starbank.model.recommendationRuleSet;

import org.skypro.starbank.model.recommendation.RecommendationDTO;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<RecommendationDTO> getRecommendation(UUID id);
}
