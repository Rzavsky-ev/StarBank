package org.skypro.starbank.service;

import org.skypro.starbank.model.recommendation.DynamicRuleRecommendationDTO;

import java.util.List;
import java.util.UUID;

public interface DynamicRuleRecommendationService {

    List<DynamicRuleRecommendationDTO> checkUserAgainstAllDynamicRules(UUID userId);

}
