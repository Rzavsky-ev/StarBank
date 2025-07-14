package org.skypro.starbank.service;

import org.skypro.starbank.model.recommendation.DynamicRuleRecommendationDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Сервис для проверки пользователей на соответствие динамическим правилам
 * и генерации рекомендаций на основе этих проверок.
 */
public interface DynamicRuleRecommendationService {

    /**
     * Проверяет пользователя на соответствие всем динамическим правилам системы
     * и возвращает список соответствующих рекомендаций.
     *
     * @param userId идентификатор пользователя для проверки
     * @return список DTO с рекомендациями, которые соответствуют пользователю.
     * Если ни одно правило не применимо, возвращается пустой список.
     * @throws org.skypro.starbank.exception.DatabaseOperationException при ошибках доступа к данным правил
     * @throws org.skypro.starbank.exception.ClientNotFoundException    если пользователь с указанным ID не найден
     */
    List<DynamicRuleRecommendationDTO> checkUserAgainstAllDynamicRules(UUID userId);

}
