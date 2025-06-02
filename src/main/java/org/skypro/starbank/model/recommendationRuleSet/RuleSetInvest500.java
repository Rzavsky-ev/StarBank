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
public class RuleSetInvest500 implements RecommendationRuleSet {

    private final CollectionRules collectionRules;

    private final RuleRecommendationMapper ruleRecommendationMapper;

    private final int minDebitDeposit = 1000;

    public RuleSetInvest500(CollectionRules collectionRules,
                            RuleRecommendationMapper ruleRecommendationMapper) {
        this.collectionRules = collectionRules;
        this.ruleRecommendationMapper = ruleRecommendationMapper;
    }

    private Rule<UUID> meetsInvest500Conditions() {
        return collectionRules.hasDebitProduct()
                .and(collectionRules.hasNoInvestProduct())
                .and(collectionRules.hasDebitDepositsOver1000(minDebitDeposit));
    }

    @Override
    public Optional<RuleRecommendationDTO> getRecommendation(UUID userId) {
        if (meetsInvest500Conditions().check(userId)) {
            return Optional.of(ruleRecommendationMapper.toDTO(Recommendation.INVEST_500));
        }
        return Optional.empty();
    }

}
