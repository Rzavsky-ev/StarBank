package org.skypro.starbank.service;

import org.skypro.starbank.model.recommendation.RuleRecommendationDTO;
import org.skypro.starbank.model.recommendationRuleSet.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для генерации базовых рекомендаций пользователям.
 * Анализирует пользовательские данные через набор правил и формирует персонализированные предложения.
 */
@Service
public class StarBankServiceImpl implements StarBankService {

    private final List<RecommendationRuleSet> recommendationRuleSets;

    /**
     * Конструктор сервиса.
     *
     * @param recommendationRuleSets список наборов правил для генерации рекомендаций
     *                               (внедряется Spring'ом автоматически)
     */
    public StarBankServiceImpl(List<RecommendationRuleSet> recommendationRuleSets) {
        this.recommendationRuleSets = new ArrayList<>(recommendationRuleSets);
    }

    /**
     * Генерирует список рекомендаций для пользователя, проверяя все доступные правила.
     *
     * @param userId идентификатор пользователя
     * @return список подходящих рекомендаций (может быть пустым)
     * @implNote Алгоритм работы:
     * 1. Последовательно проверяет все наборы правил
     * 2. Для каждого правила получает рекомендацию (если применимо)
     * 3. Фильтрует только присутствующие рекомендации
     * 4. Собирает итоговый список
     * Особенности:
     * - Использует потоковую обработку для эффективности
     * - Возвращает только релевантные рекомендации
     * - Не возвращает null (только пустой список)
     */
    @Override
    public List<RuleRecommendationDTO> defineRecommendations(UUID userId) {
        return recommendationRuleSets.stream()
                .map(rule -> rule.getRecommendation(userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}
