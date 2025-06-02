package org.skypro.starbank.model.mapper;

import org.mapstruct.Mapper;
import org.skypro.starbank.model.recommendation.Recommendation;
import org.skypro.starbank.model.recommendation.RuleRecommendationDTO;

@Mapper(componentModel = "spring")
public interface RuleRecommendationMapper {
    RuleRecommendationDTO toDTO(Recommendation entity);
}
