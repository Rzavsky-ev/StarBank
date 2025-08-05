package org.skypro.starbank.model.recommendationRuleSet;


import org.skypro.starbank.model.mapper.RuleRecommendationMapper;
import org.skypro.starbank.model.recommendation.Recommendation;
import org.skypro.starbank.model.recommendation.RuleRecommendationDTO;
import org.skypro.starbank.model.rule.CollectionRules;
import org.skypro.starbank.model.rule.Rule;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Реализация набора правил для рекомендации инвестиционного продукта "INVEST_500".
 * Проверяет условия для предложения инвестиционного продукта клиенту.
 */
@Component
public class RuleSetInvest500 implements RecommendationRuleSet {

    private final CollectionRules collectionRules;

    private final RuleRecommendationMapper ruleRecommendationMapper;

    private final int minDebitDeposit = 1000;

    /**
     * Конструктор класса.
     *
     * @param collectionRules          набор правил для проверки условий
     * @param ruleRecommendationMapper маппер для преобразования рекомендации в DTO
     */
    public RuleSetInvest500(CollectionRules collectionRules,
                            RuleRecommendationMapper ruleRecommendationMapper) {
        this.collectionRules = collectionRules;
        this.ruleRecommendationMapper = ruleRecommendationMapper;
    }

    /**
     * Создает составное правило, проверяющее условия для рекомендации INVEST_500:
     * 1. Наличие дебетового продукта
     * 2. Отсутствие инвестиционного продукта
     * 3. Наличие дебетовых депозитов на сумму более 1000 (значение minDebitDeposit)
     *
     * @return составное правило для проверки условий
     */
    private Rule<UUID> meetsInvest500Conditions() {
        return collectionRules.hasDebitProduct()
                .and(collectionRules.hasNoInvestProduct())
                .and(collectionRules.hasDebitDepositsOver1000(minDebitDeposit));
    }

    /**
     * Возвращает рекомендацию INVEST_500, если пользователь удовлетворяет условиям.
     *
     * @param userId идентификатор пользователя для проверки
     * @return Optional с DTO рекомендации, если условия выполнены,
     * или пустой Optional, если условия не выполнены
     */
    @Override
    public Optional<RuleRecommendationDTO> getRecommendation(UUID userId) {
        if (meetsInvest500Conditions().check(userId)) {
            return Optional.of(ruleRecommendationMapper.toDTO(Recommendation.INVEST_500));
        }
        return Optional.empty();
    }
}
