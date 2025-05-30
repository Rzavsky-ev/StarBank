package org.skypro.starbank.model.recommendationRuleSet;


import org.skypro.starbank.model.recommendation.Recommendation;
import org.skypro.starbank.model.recommendation.RecommendationDTO;
import org.skypro.starbank.model.rule.CollectionRules;
import org.skypro.starbank.model.rule.Rule;
import org.skypro.starbank.repository.jdbc.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RuleSetInvest500 implements RecommendationRuleSet {

    private final CollectionRules collectionRules;

    private final int minDebitDeposit = 1000;

    public RuleSetInvest500(TransactionRepository transactionRepository,
                            CollectionRules collectionRules) {
        this.collectionRules = collectionRules;
    }

    public Rule<UUID> meetsInvest500Conditions() {
        return collectionRules.hasDebitProduct()
                .and(collectionRules.hasNoInvestProduct())
                .and(collectionRules.hasDebitDepositsOver1000(minDebitDeposit));
    }

    @Override
    public Optional<RecommendationDTO> getRecommendation(UUID userId) {
        if (meetsInvest500Conditions().check(userId)) {
            return Optional.of(Recommendation.INVEST_500.toDto());
        }
        return Optional.empty();
    }
}
