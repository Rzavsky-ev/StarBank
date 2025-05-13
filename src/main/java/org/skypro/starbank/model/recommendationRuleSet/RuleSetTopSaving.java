package org.skypro.starbank.model.recommendationRuleSet;

import org.skypro.starbank.model.recommendation.Recommendation;
import org.skypro.starbank.model.recommendation.RecommendationDTO;
import org.skypro.starbank.model.recommendation.RecommendationDtoFactory;
import org.skypro.starbank.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RuleSetTopSaving implements RecommendationRuleSet {

    private final TransactionRepository transactionRepository;

    private final RecommendationDtoFactory recommendationDtoFactory;

    private final int amountOfReplenishments = 50_000;

    public RuleSetTopSaving(RecommendationDtoFactory recommendationDtoFactory,
                            TransactionRepository transactionRepository) {
        this.recommendationDtoFactory = recommendationDtoFactory;
        this.transactionRepository = transactionRepository;
    }

    public Optional<RecommendationDTO> getRecommendation(UUID userId) {
        boolean debitCheck = transactionRepository.checkProductAvailability(userId, "DEBIT");
        int summaOfDepositDebit = transactionRepository.sumTransactions
                (userId, "DEBIT", "DEPOSIT");
        int summaOfWithdrawDebit = transactionRepository.sumTransactions
                (userId, "DEBIT", "WITHDRAW");
        int summaOfDepositSaving = transactionRepository.sumTransactions
                (userId, "SAVING", "DEPOSIT");
        if (debitCheck &&
                (summaOfDepositDebit >= amountOfReplenishments || summaOfDepositSaving >= amountOfReplenishments) &&
                (summaOfDepositDebit > summaOfWithdrawDebit)) {
            return Optional.of(recommendationDtoFactory.fromEnum(Recommendation.TOP_SAVING));
        }
        return Optional.empty();
    }
}

