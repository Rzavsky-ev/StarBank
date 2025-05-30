package org.skypro.starbank.service;

import org.skypro.starbank.model.recommendation.RecommendationDTO;
import java.util.List;
import java.util.UUID;


public interface StarBankService {

    List<RecommendationDTO> defineRecommendations(UUID userId);

}
