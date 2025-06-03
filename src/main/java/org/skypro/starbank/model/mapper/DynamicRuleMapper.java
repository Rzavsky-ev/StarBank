package org.skypro.starbank.model.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRule;
import org.skypro.starbank.model.recommendation.DynamicRuleRecommendationDTO;

@Mapper(componentModel = "spring", uses = RequestTypeMapper.class)
public interface DynamicRuleMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "productName", source = "productName")
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "productText", source = "productText")
    @Mapping(target = "ruleConditions", source = "ruleConditions")
    DynamicRuleRecommendationDTO toDTO(DynamicRule entity);
}