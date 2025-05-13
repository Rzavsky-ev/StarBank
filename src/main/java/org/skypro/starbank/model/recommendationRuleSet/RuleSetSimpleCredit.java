package org.skypro.starbank.model.recommendationRuleSet;

import org.skypro.starbank.model.recommendation.Recommendation;
import org.skypro.starbank.model.recommendation.RecommendationDTO;
import org.skypro.starbank.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RuleSetSimpleCredit implements RecommendationRuleSet {

    private final TransactionRepository transactionRepository;

    private final int summaExpensesDebit = 100_000;

    public RuleSetSimpleCredit(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Optional<RecommendationDTO> getRecommendation(UUID userId) {
        boolean creditCheck = transactionRepository.checkProductAvailability(userId, "CREDIT");
        int summaOfDepositDebit = transactionRepository.sumTransactions
                (userId, "DEBIT", "DEPOSIT");
        int summaOfWithdrawDebit = transactionRepository.sumTransactions
                (userId, "DEBIT", "WITHDRAW");
        if (!creditCheck && summaOfDepositDebit > summaOfWithdrawDebit &&
                summaOfWithdrawDebit > summaExpensesDebit) {
            return Optional.of(Recommendation.SIMPLE_CREDIT.toDto());
        }
        return Optional.empty();
    }
}
