package org.skypro.starbank.model.recommendationRuleSet;

import org.skypro.starbank.model.recommendation.Recommendation;
import org.skypro.starbank.model.recommendation.RecommendationDTO;
import org.skypro.starbank.model.rule.CollectionRules;
import org.skypro.starbank.model.rule.Rule;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RuleSetTopSaving implements RecommendationRuleSet {

    private final CollectionRules collectionRules;

    private final int minDebitDeposit = 50_000;

    private final int minSavingDeposit = 50_000;

    public RuleSetTopSaving(CollectionRules collectionRules) {
        this.collectionRules = collectionRules;
    }

    private Rule<UUID> meetsTopSavingConditions() {
        return collectionRules.hasDebitProduct()
                .and(collectionRules.hasDebitDepositsOverOrHasSavingDepositsOver
                        (minDebitDeposit, minSavingDeposit))
                .and(collectionRules.hasDebitDepositsOverWithdraw());
    }

    public Optional<RecommendationDTO> getRecommendation(UUID userId) {
        if (meetsTopSavingConditions().check(userId)) {
            return Optional.of(Recommendation.TOP_SAVING.toDto());
        }
        return Optional.empty();
    }
}
