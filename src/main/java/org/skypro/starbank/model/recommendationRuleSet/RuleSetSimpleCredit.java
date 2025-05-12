package org.skypro.starbank.model.recommendationRuleSet;

import org.skypro.starbank.model.recommendation.RecommendationDTO;
import org.skypro.starbank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RuleSetSimpleCredit implements RecommendationRuleSet {

    private final TransactionRepository transactionRepository;

    private final RecommendationDTO recommendation;

    private final int summaExpensesDebit = 100_000;

    public RuleSetSimpleCredit(@Qualifier("simpleCreditDto")
                               RecommendationDTO recommendation, TransactionRepository transactionRepository) {
        this.recommendation = recommendation;
        this.transactionRepository = transactionRepository;
    }

    public Optional<RecommendationDTO> getRecommendation(UUID userId) {
        boolean creditCheck = transactionRepository.creditCheck(userId);
        int summaOfDepositDebit = transactionRepository.summaOfDepositDebit(userId);
        int summaOfWithdrawDebit = transactionRepository.summaOfWithdrawDebit(userId);
        if (!creditCheck && summaOfDepositDebit > summaOfWithdrawDebit &&
                summaOfWithdrawDebit > summaExpensesDebit) {
            return Optional.of(recommendation);
        }
        return Optional.empty();
    }
}
