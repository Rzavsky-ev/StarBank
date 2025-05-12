package org.skypro.starbank.model.recommendationRuleSet;

import org.skypro.starbank.model.recommendation.RecommendationDTO;
import org.skypro.starbank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RuleSetTopSaving implements RecommendationRuleSet {

    private final TransactionRepository transactionRepository;

    private final RecommendationDTO recommendation;

    private final int amountOfReplenishments = 50_000;

    public RuleSetTopSaving(@Qualifier("topSavingDto")
                            RecommendationDTO recommendation, TransactionRepository transactionRepository) {
        this.recommendation = recommendation;
        this.transactionRepository = transactionRepository;
    }

    public Optional<RecommendationDTO> getRecommendation(UUID userId) {
        boolean debitCheck = transactionRepository.debitCheck(userId);
        int summaOfDepositDebit = transactionRepository.summaOfDepositDebit(userId);
        int summaOfWithdrawDebit = transactionRepository.summaOfWithdrawDebit(userId);
        int summaOfDepositSaving = transactionRepository.summaOfDepositSaving(userId);
        if (debitCheck &&
                (summaOfDepositDebit >= amountOfReplenishments || summaOfDepositSaving >= amountOfReplenishments) &&
                (summaOfDepositDebit > summaOfWithdrawDebit)) {
            return Optional.of(recommendation);
        }
        return Optional.empty();
    }
}

