package org.skypro.starbank.model.recommendationRuleSet;


import org.skypro.starbank.model.recommendation.Recommendation;
import org.skypro.starbank.model.recommendation.RecommendationDTO;
import org.skypro.starbank.model.recommendation.RecommendationDtoFactory;
import org.skypro.starbank.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RuleSetInvest500 implements RecommendationRuleSet {

    private final TransactionRepository transactionRepository;

    private final RecommendationDtoFactory recommendationDtoFactory;

    private final int amountOfReplenishments = 1000;

    public RuleSetInvest500(TransactionRepository transactionRepository,
                            RecommendationDtoFactory recommendationDtoFactory) {
        this.recommendationDtoFactory = recommendationDtoFactory;
        this.transactionRepository = transactionRepository;
    }

    public Optional<RecommendationDTO> getRecommendation(UUID userId) {
        boolean debitCheck = transactionRepository.checkProductAvailability(userId, "DEBIT");
        boolean investCheck = transactionRepository.checkProductAvailability(userId, "INVEST");
        int summaOfDepositDebit = transactionRepository.sumTransactions
                (userId, "DEBIT", "DEPOSIT");
        if (debitCheck && !investCheck && summaOfDepositDebit > amountOfReplenishments) {
            return Optional.of(recommendationDtoFactory.fromEnum(Recommendation.INVEST_500));
        }
        return Optional.empty();
    }
}
