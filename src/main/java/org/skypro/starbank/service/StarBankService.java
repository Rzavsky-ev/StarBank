package org.skypro.starbank.service;

import org.skypro.starbank.model.recommendation.RuleRecommendationDTO;
import java.util.List;
import java.util.UUID;


public interface StarBankService {

    List<RuleRecommendationDTO> defineRecommendations(UUID userId);

}
