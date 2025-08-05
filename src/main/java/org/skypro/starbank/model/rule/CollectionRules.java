package org.skypro.starbank.model.rule;

import org.skypro.starbank.repository.jdbc.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Сервис для работы с бизнес-правилами клиентов.
 * Содержит набор методов для проверки различных условий
 * по продуктам и транзакциям пользователей.
 *
 * <p>Является Spring-сервисом ({@code @Service})</p>
 */
@Service
public class CollectionRules {

    private final TransactionRepository transactionRepository;

    /**
     * Конструктор с внедрением зависимостей.
     *
     * @param transactionRepository репозиторий транзакций
     */
    public CollectionRules(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Проверяет наличие дебетового продукта у пользователя.
     *
     * @return правило с описанием и предикатом проверки
     */
    public Rule<UUID> hasDebitProduct() {
        return new Rule<>("Пользователь использует как минимум один продукт с типом DEBIT.", userId ->
                transactionRepository.checkProductAvailability(userId, "DEBIT"));
    }

    /**
     * Проверяет отсутствие инвестиционных продуктов у пользователя.
     *
     * @return правило с описанием и предикатом проверки
     */
    public Rule<UUID> hasNoInvestProduct() {
        return new Rule<>("Пользователь не использует продукты с типом INVEST.", userId ->
                !transactionRepository.checkProductAvailability(userId, "INVEST"));
    }

    /**
     * Проверяет, что сумма дебетовых депозитов превышает указанный минимум.
     *
     * @param minDebitDeposit минимальная сумма депозитов
     * @return правило с описанием и предикатом проверки
     */
    public Rule<UUID> hasDebitDepositsOver1000(int minDebitDeposit) {
        return new Rule<>("Сумма пополнений продуктов с типом SAVING больше 1000 ₽.", userId ->
                transactionRepository.sumTransactions(userId, "DEBIT", "DEPOSIT")
                        > minDebitDeposit);
    }

    /**
     * Проверяет отсутствие кредитных продуктов у пользователя.
     *
     * @return правило с описанием и предикатом проверки
     */
    public Rule<UUID> hasNoCreditProduct() {
        return new Rule<>("Пользователь не использует продукты с типом CREDIT.",
                userId -> !transactionRepository.checkProductAvailability(userId, "CREDIT"));
    }

    /**
     * Проверяет, что сумма дебетовых пополнений превышает сумму снятий.
     *
     * @return правило с описанием и предикатом проверки
     */
    public Rule<UUID> hasDebitDepositsOverWithdraw() {
        return new Rule<>("Сумма пополнений по всем продуктам типа DEBIT больше, чем " +
                "сумма трат по всем продуктам типа DEBIT.", userId ->
                transactionRepository.sumTransactions(userId, "DEBIT", "DEPOSIT") >
                        transactionRepository.sumTransactions(userId, "DEBIT", "WITHDRAW"));
    }

    /**
     * Проверяет, что сумма дебетовых снятий превышает указанный минимум.
     *
     * @param minDebitWithdraw минимальная сумма снятий (100 000 ₽)
     * @return правило с описанием и предикатом проверки
     */
    public Rule<UUID> hasDebitWithdrawOver100000(int minDebitWithdraw) {
        return new Rule<>("Сумма трат по всем продуктам типа DEBIT больше, чем 100 000 ₽.",
                userId ->
                        transactionRepository.sumTransactions(userId, "DEBIT", "WITHDRAW") >
                                minDebitWithdraw);
    }

    /**
     * Проверяет, что сумма дебетовых или накопительных депозитов превышает указанные минимумы.
     *
     * @param minDebitDeposit  минимальная сумма дебетовых депозитов (50 000 ₽)
     * @param minSavingDeposit минимальная сумма накопительных депозитов (50 000 ₽)
     * @return правило с описанием и предикатом проверки
     */
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
