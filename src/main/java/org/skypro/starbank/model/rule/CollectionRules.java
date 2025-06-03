package org.skypro.starbank.model.rule;

import org.skypro.starbank.repository.jdbc.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CollectionRules {

    private final TransactionRepository transactionRepository;

    public CollectionRules(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Rule<UUID> hasDebitProduct() {
        return new Rule<>("Пользователь использует как минимум один продукт с типом DEBIT.", userId ->
                transactionRepository.checkProductAvailability(userId, "DEBIT"));
    }

    public Rule<UUID> hasNoInvestProduct() {
        return new Rule<>("Пользователь не использует продукты с типом INVEST.", userId ->
                !transactionRepository.checkProductAvailability(userId, "INVEST"));
    }

    public Rule<UUID> hasDebitDepositsOver1000(int minDebitDeposit) {
        return new Rule<>("Сумма пополнений продуктов с типом SAVING больше 1000 ₽.", userId ->
                transactionRepository.sumTransactions(userId, "DEBIT", "DEPOSIT")
                        > minDebitDeposit);
    }

    public Rule<UUID> hasNoCreditProduct() {
        return new Rule<>("Пользователь не использует продукты с типом CREDIT.",
                userId -> !transactionRepository.checkProductAvailability(userId, "CREDIT"));
    }

    public Rule<UUID> hasDebitDepositsOverWithdraw() {
        return new Rule<>("Сумма пополнений по всем продуктам типа DEBIT больше, чем " +
                "сумма трат по всем продуктам типа DEBIT.", userId ->
                transactionRepository.sumTransactions(userId, "DEBIT", "DEPOSIT") >
                        transactionRepository.sumTransactions(userId, "DEBIT", "WITHDRAW"));
    }

    public Rule<UUID> hasDebitWithdrawOver100000(int minDebitWithdraw) {
        return new Rule<>("Сумма трат по всем продуктам типа DEBIT больше, чем 100 000 ₽.",
                userId ->
                        transactionRepository.sumTransactions(userId, "DEBIT", "WITHDRAW") >
                                minDebitWithdraw);
    }

    public Rule<UUID> hasDebitDepositsOverOrHasSavingDepositsOver(int minDebitDeposit,
                                                                  int minSavingDeposit) {
        return new Rule<>("Сумма пополнений по всем продуктам типа DEBIT больше или равна" +
                " 50 000 ₽ ИЛИ Сумма пополнений по всем продуктам типа SAVING больше или равна 50 000 ₽.",
                userId -> transactionRepository.sumTransactions
                        (userId, "DEBIT", "DEPOSIT") >=
                        minDebitDeposit || transactionRepository.sumTransactions(userId, "SAVING",
                        "DEPOSIT") >=
                        minSavingDeposit);
    }

}
