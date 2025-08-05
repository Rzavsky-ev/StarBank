package org.skypro.starbank.model.recommendationRuleSet;

import org.skypro.starbank.model.recommendation.RuleRecommendationDTO;


import java.util.Optional;
import java.util.UUID;

/**
 * Интерфейс, определяющий набор правил для генерации рекомендаций.
 * Предоставляет метод для получения рекомендаций на основе идентификатора.
 */
public interface RecommendationRuleSet {
    /**
     * Возвращает рекомендацию, соответствующую заданному идентификатору.
     *
     * @param id уникальный идентификатор, для которого требуется получить рекомендацию
     * @return объект {@link Optional}, содержащий {@link RuleRecommendationDTO}, если рекомендация найдена,
     * или пустой {@link Optional}, если рекомендация отсутствует
     */
    Optional<RuleRecommendationDTO> getRecommendation(UUID id);
}

