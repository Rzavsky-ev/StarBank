package org.skypro.starbank.model.recommendationRuleSet;

import org.skypro.starbank.model.recommendation.Recommendation;
import org.skypro.starbank.model.recommendation.RecommendationDTO;
import org.skypro.starbank.model.recommendation.RecommendationDtoFactory;
import org.skypro.starbank.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RuleSetSimpleCredit implements RecommendationRuleSet {

    private final TransactionRepository transactionRepository;

    private final RecommendationDtoFactory recommendationDtoFactory;

    private final int summaExpensesDebit = 100_000;

    public RuleSetSimpleCredit(RecommendationDtoFactory recommendationDtoFactory,
                               TransactionRepository transactionRepository) {
        this.recommendationDtoFactory = recommendationDtoFactory;
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
            return Optional.of(recommendationDtoFactory.fromEnum(Recommendation.SIMPLE_CREDIT));
        }
        return Optional.empty();
    }
}
