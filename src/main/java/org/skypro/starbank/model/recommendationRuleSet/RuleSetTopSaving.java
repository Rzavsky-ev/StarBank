package org.skypro.starbank.model.recommendationRuleSet;

import org.skypro.starbank.model.mapper.RuleRecommendationMapper;
import org.skypro.starbank.model.recommendation.Recommendation;
import org.skypro.starbank.model.recommendation.RuleRecommendationDTO;
import org.skypro.starbank.model.rule.CollectionRules;
import org.skypro.starbank.model.rule.Rule;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RuleSetTopSaving implements RecommendationRuleSet {

    private final CollectionRules collectionRules;

    private final RuleRecommendationMapper ruleRecommendationMapper;

    private final int minDebitDeposit = 50_000;

    private final int minSavingDeposit = 50_000;

    public RuleSetTopSaving(CollectionRules collectionRules,
                            RuleRecommendationMapper ruleRecommendationMapper) {
        this.collectionRules = collectionRules;
        this.ruleRecommendationMapper = ruleRecommendationMapper;
    }

    private Rule<UUID> meetsTopSavingConditions() {
        return collectionRules.hasDebitProduct()
                .and(collectionRules.hasDebitDepositsOverOrHasSavingDepositsOver
                        (minDebitDeposit, minSavingDeposit))
                .and(collectionRules.hasDebitDepositsOverWithdraw());
    }

    @Override
    public Optional<RuleRecommendationDTO> getRecommendation(UUID userId) {
        if (meetsTopSavingConditions().check(userId)) {
            return Optional.of(ruleRecommendationMapper.toDTO(Recommendation.TOP_SAVING));
        }
        return Optional.empty();
    }
}
