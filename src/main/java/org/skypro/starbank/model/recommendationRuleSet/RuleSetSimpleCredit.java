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
 * Реализация набора правил для рекомендации кредитного продукта "SIMPLE_CREDIT".
 * Проверяет условия для предложения кредитного продукта клиенту.
 *
 * <p>Класс является Spring-компонентом и автоматически подхватывается контейнером Spring.</p>
 */
@Component
public class RuleSetSimpleCredit implements RecommendationRuleSet {

    private final CollectionRules collectionRules;

    private final RuleRecommendationMapper ruleRecommendationMapper;

    private final int minDebitWithdraw = 100_000;

    /**
     * Конструктор класса.
     *
     * @param collectionRules          набор правил для проверки условий
     * @param ruleRecommendationMapper маппер для преобразования рекомендации в DTO
     */
    public RuleSetSimpleCredit(CollectionRules collectionRules,
                               RuleRecommendationMapper ruleRecommendationMapper) {
        this.collectionRules = collectionRules;
        this.ruleRecommendationMapper = ruleRecommendationMapper;
    }

    /**
     * Создает составное правило, проверяющее условия для рекомендации SIMPLE_CREDIT:
     * <ol>
     *   <li>Отсутствие кредитных продуктов у клиента</li>
     *   <li>Наличие дебетовых депозитов, превышающих снятия</li>
     *   <li>Сумма снятий по дебетовым продуктам превышает 100 000 (значение minDebitWithdraw)</li>
     * </ol>
     *
     * @return составное правило для проверки условий
     */
    private Rule<UUID> meetsSimpleCreditConditions() {
        return collectionRules.hasNoCreditProduct()
                .and(collectionRules.hasDebitDepositsOverWithdraw())
                .and(collectionRules.hasDebitWithdrawOver100000(minDebitWithdraw));
    }

    /**
     * Возвращает рекомендацию SIMPLE_CREDIT, если пользователь удовлетворяет условиям.
     *
     * @param userId идентификатор пользователя для проверки
     * @return {@code Optional} с DTO рекомендации, если условия выполнены,
     * или пустой {@code Optional}, если условия не выполнены
     * @see Optional
     * @see RuleRecommendationDTO
     */
    @Override
    public Optional<RuleRecommendationDTO> getRecommendation(UUID userId) {
        if (meetsSimpleCreditConditions().check(userId)) {
            return Optional.of(ruleRecommendationMapper.toDTO(Recommendation.SIMPLE_CREDIT));
        }
        return Optional.empty();
    }
}

