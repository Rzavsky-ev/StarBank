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
 * Реализация набора правил для рекомендации накопительного продукта "TOP_SAVING".
 * Проверяет условия для предложения премиального накопительного продукта клиенту.
 *
 * <p>Данный компонент автоматически регистрируется в контейнере Spring благодаря аннотации {@code @Component}.</p>
 */
@Component
public class RuleSetTopSaving implements RecommendationRuleSet {

    private final CollectionRules collectionRules;

    private final RuleRecommendationMapper ruleRecommendationMapper;

    private final int minDebitDeposit = 50_000;

    private final int minSavingDeposit = 50_000;

    /**
     * Конструктор с внедрением зависимостей.
     *
     * @param collectionRules          сервис проверки правил
     * @param ruleRecommendationMapper преобразователь в DTO
     */
    public RuleSetTopSaving(CollectionRules collectionRules,
                            RuleRecommendationMapper ruleRecommendationMapper) {
        this.collectionRules = collectionRules;
        this.ruleRecommendationMapper = ruleRecommendationMapper;
    }

    /**
     * Формирует составное правило для проверки условий рекомендации TOP_SAVING:
     * <ul>
     *   <li>Наличие дебетового продукта у клиента</li>
     *   <li>Наличие дебетовых депозитов ≥ 50 000 ИЛИ накопительных депозитов ≥ 50 000</li>
     *   <li>Сумма дебетовых депозитов превышает сумму снятий</li>
     * </ul>
     *
     * @return составное правило для проверки условий
     */

    private Rule<UUID> meetsTopSavingConditions() {
        return collectionRules.hasDebitProduct()
                .and(collectionRules.hasDebitDepositsOverOrHasSavingDepositsOver
                        (minDebitDeposit, minSavingDeposit))
                .and(collectionRules.hasDebitDepositsOverWithdraw());
    }

    /**
     * Получает рекомендацию для указанного пользователя.
     *
     * @param userId идентификатор пользователя
     * @return {@code Optional} с рекомендацией в формате DTO, если условия выполнены,
     * или пустой {@code Optional}, если условия не выполнены
     * @see Optional
     * @see RuleRecommendationDTO
     */
    @Override
    public Optional<RuleRecommendationDTO> getRecommendation(UUID userId) {
        if (meetsTopSavingConditions().check(userId)) {
            return Optional.of(ruleRecommendationMapper.toDTO(Recommendation.TOP_SAVING));
        }
        return Optional.empty();
    }
}
