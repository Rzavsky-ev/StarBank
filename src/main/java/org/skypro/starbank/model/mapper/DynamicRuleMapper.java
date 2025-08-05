package org.skypro.starbank.model.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRule;
import org.skypro.starbank.model.recommendation.DynamicRuleRecommendationDTO;

/**
 * Маппер для преобразования между сущностью {@link DynamicRule} и DTO {@link DynamicRuleRecommendationDTO}.
 * <p>
 * Использует Spring в качестве модели компонента и делегирует маппинг условий правила
 * к {@link RequestTypeMapper}.
 */
@Mapper(componentModel = "spring", uses = RequestTypeMapper.class)
public interface DynamicRuleMapper {

    /**
     * Преобразует сущность {@link DynamicRule} в {@link DynamicRuleRecommendationDTO}.
     *
     * @param entity сущность динамического правила для преобразования
     * @return DTO с рекомендацией динамического правила
     * @mapping id → id
     * @mapping productName → productName
     * @mapping productId → productId
     * @mapping productText → productText
     * @mapping ruleConditions → ruleConditions
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "productName", source = "productName")
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "productText", source = "productText")
    @Mapping(target = "ruleConditions", source = "ruleConditions")
    DynamicRuleRecommendationDTO toDTO(DynamicRule entity);
}