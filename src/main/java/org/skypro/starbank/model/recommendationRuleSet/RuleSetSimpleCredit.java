package org.skypro.starbank.model.recommendationRuleSet;

import org.skypro.starbank.model.recommendation.Recommendation;
import org.skypro.starbank.model.recommendation.RecommendationDTO;
import org.skypro.starbank.model.rule.CollectionRules;
import org.skypro.starbank.model.rule.Rule;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RuleSetSimpleCredit implements RecommendationRuleSet {

    private final CollectionRules collectionRules;

    private final int minDebitWithdraw = 100_000;

    public RuleSetSimpleCredit(CollectionRules collectionRules) {
        this.collectionRules = collectionRules;
    }

    private Rule<UUID> meetsSimpleCreditConditions() {
        return collectionRules.hasNoCreditProduct()
                .and(collectionRules.hasDebitDepositsOverWithdraw())
                .and(collectionRules.hasDebitWithdrawOver100000(minDebitWithdraw));
    }

    public Optional<RecommendationDTO> getRecommendation(UUID userId) {
        if (meetsSimpleCreditConditions().check(userId)) {
            return Optional.of(Recommendation.SIMPLE_CREDIT.toDto());
        }
        return Optional.empty();
    }
}

