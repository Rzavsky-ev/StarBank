package org.skypro.starbank.model.dynamicRule.dynamicRuleRecommendationSet;

import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.QueryType;
import org.skypro.starbank.model.recommendation.DynamicRuleRecommendationDTO;
import org.skypro.starbank.model.recommendation.RequestTypeDTO;
import org.skypro.starbank.model.rule.Rule;
import org.skypro.starbank.repository.jdbc.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Component
public class DynamicRuleRecommendationSet {
    private final TransactionRepository transactionRepository;

    public DynamicRuleRecommendationSet(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Rule<UUID> createCombinedRule(DynamicRuleRecommendationDTO dynamicRule) {
        return dynamicRule.getRuleConditions().stream()
                .map(this::createRuleFromRequestType)
                .reduce(Rule::and)
                .orElse(Rule.alwaysTrue());
    }


    private Rule<UUID> createRuleFromRequestType(RequestTypeDTO requestType) {
        Predicate<UUID> predicate = createPredicateFromRequestType(requestType);
        String description = generateDescription(requestType);

        Rule<UUID> rule = new Rule<>(description, predicate);

        return requestType.isNegate()
                ? rule.negate("Не " + description.toLowerCase())
                : rule;
    }

    private Predicate<UUID> createPredicateFromRequestType(RequestTypeDTO requestType) {
        List<String> arguments = requestType.getArguments();
        QueryType queryType = requestType.getQuery();

        return switch (queryType) {
            case USER_OF -> userId ->
                    transactionRepository.checkProductAvailability(userId, arguments.get(0));

            case ACTIVE_USER_OF -> userId -> {
                int transactionCount = transactionRepository.countTransactionsByProduct(
                        userId, arguments.get(0));
                return transactionCount >= 5;
            };

            case TRANSACTION_SUM_COMPARE -> userId -> {
                int sum = transactionRepository.sumTransactions(userId,
                        arguments.get(0), arguments.get(1));
                ComparisonOperator operator = ComparisonOperator.fromSymbol(arguments.get(2));
                int compareValue = Integer.parseInt(arguments.get(3));

                return switch (operator) {
                    case GREATER_THAN -> sum > compareValue;
                    case LESS_THAN -> sum < compareValue;
                    case EQUALS -> sum == compareValue;
                    case GREATER_OR_EQUAL -> sum >= compareValue;
                    case LESS_OR_EQUAL -> sum <= compareValue;
                };
            };

            case TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW -> userId -> {
                int depositSum = transactionRepository.sumTransactions(userId,
                        arguments.get(0), TransactionType.DEPOSIT.name());
                int withdrawalSum = transactionRepository.sumTransactions(userId,
                        arguments.get(0), TransactionType.WITHDRAWAL.name());
                ComparisonOperator operator = ComparisonOperator.fromSymbol(arguments.get(1));

                return switch (operator) {
                    case GREATER_THAN -> depositSum > withdrawalSum;
                    case LESS_THAN -> depositSum < withdrawalSum;
                    case EQUALS -> depositSum == withdrawalSum;
                    case GREATER_OR_EQUAL -> depositSum >= withdrawalSum;
                    case LESS_OR_EQUAL -> depositSum <= withdrawalSum;
                };
            };
        };
    }

    private String generateDescription(RequestTypeDTO requestType) {
        List<String> arguments = requestType.getArguments();
        QueryType queryType = requestType.getQuery();

        return switch (queryType) {
            case USER_OF -> String.format("Пользователь является клиентом продукта %s", arguments.get(0));
            case ACTIVE_USER_OF ->
                    String.format("Пользователь является активным клиентом продукта %s", arguments.get(0));
            case TRANSACTION_SUM_COMPARE -> String.format(
                    "Сумма транзакций типа %s по продукту %s %s %s",
                    arguments.get(1), arguments.get(0), arguments.get(2), arguments.get(3));
            case TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW -> String.format(
                    "Сумма депозитов по продукту %s %s суммы выводов",
                    arguments.get(0), arguments.get(1));
        };
    }

}
