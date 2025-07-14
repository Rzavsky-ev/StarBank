package org.skypro.starbank.service;

import org.skypro.starbank.model.recommendation.RuleRecommendationDTO;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для определения базовых рекомендаций для пользователей банка.
 * Предоставляет API для получения персонализированных предложений на основе
 * данных о пользователе и его финансовой активности.
 */
public interface StarBankService {

    /**
     * Генерирует список рекомендаций для указанного пользователя.
     *
     * @param userId уникальный идентификатор пользователя
     * @return список рекомендаций в формате DTO (может быть пустым, если нет подходящих предложений)
     * @implNote Реализации этого метода должны:
     * - Анализировать финансовый профиль пользователя
     * - Проверять соответствие критериям рекомендаций
     * - Возвращать только релевантные предложения
     */
    List<RuleRecommendationDTO> defineRecommendations(UUID userId);

}
