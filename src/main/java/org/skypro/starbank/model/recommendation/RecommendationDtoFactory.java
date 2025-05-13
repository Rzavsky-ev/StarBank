package org.skypro.starbank.model.recommendation;

import org.springframework.stereotype.Component;

@Component
public class RecommendationDtoFactory {
    public RecommendationDTO fromEnum(Recommendation recommendation) {
        return recommendation.toDto();
    }
}
