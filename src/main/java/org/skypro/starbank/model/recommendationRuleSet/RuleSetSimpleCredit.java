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
public class RuleSetSimpleCredit implements RecommendationRuleSet {

    private final CollectionRules collectionRules;

    private final RuleRecommendationMapper ruleRecommendationMapper;

    private final int minDebitWithdraw = 100_000;

    public RuleSetSimpleCredit(CollectionRules collectionRules,
                               RuleRecommendationMapper ruleRecommendationMapper) {
        this.collectionRules = collectionRules;
        this.ruleRecommendationMapper = ruleRecommendationMapper;
    }

    private Rule<UUID> meetsSimpleCreditConditions() {
        return collectionRules.hasNoCreditProduct()
                .and(collectionRules.hasDebitDepositsOverWithdraw())
                .and(collectionRules.hasDebitWithdrawOver100000(minDebitWithdraw));
    }

    @Override
    public Optional<RuleRecommendationDTO> getRecommendation(UUID userId) {
        if (meetsSimpleCreditConditions().check(userId)) {
            return Optional.of(ruleRecommendationMapper.toDTO(Recommendation.SIMPLE_CREDIT));
        }
        return Optional.empty();
    }
}

