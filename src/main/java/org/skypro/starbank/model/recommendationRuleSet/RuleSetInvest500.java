package org.skypro.starbank.model.recommendationRuleSet;


import org.skypro.starbank.model.recommendation.RecommendationDTO;
import org.skypro.starbank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RuleSetInvest500 implements RecommendationRuleSet {

    private final TransactionRepository transactionRepository;

    private final RecommendationDTO recommendation;

    private final int amountOfReplenishments = 1000;

    public RuleSetInvest500(@Qualifier("invest500Dto")
                            RecommendationDTO recommendation, TransactionRepository transactionRepository) {
        this.recommendation = recommendation;
        this.transactionRepository = transactionRepository;
    }

    public Optional<RecommendationDTO> getRecommendation(UUID userId) {
        boolean debitCheck = transactionRepository.debitCheck(userId);
        boolean investCheck = transactionRepository.investCheck(userId);
        int summaOfDepositDebit = transactionRepository.summaOfDepositDebit(userId);
        if (debitCheck && !investCheck && summaOfDepositDebit > amountOfReplenishments) {
            return Optional.of(recommendation);
        }
        return Optional.empty();
    }
}
