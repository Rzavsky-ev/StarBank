package org.skypro.starbank.repository.jdbc;

import org.skypro.starbank.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий для работы с транзакциями в базе данных.
 * Обеспечивает доступ к данным о транзакциях пользователей и связанным продуктам.
 */
@Repository
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(TransactionRepository.class);

    /**
     * Конструктор репозитория.
     *
     * @param jdbcTemplate JdbcTemplate для работы с рекомендательной БД,
     *                     должен быть квалифицирован как "recommendationsJdbcTemplate"
     */
    public TransactionRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Проверяет наличие продукта определенного типа у пользователя.
     *
     * @param userId      идентификатор пользователя
     * @param productType тип продукта для проверки
     * @return true если у пользователя есть хотя бы одна транзакция с указанным типом продукта,
     * false в противном случае
     * @throws DatabaseOperationException если произошла ошибка при работе с базой данных
     */
    public Boolean checkProductAvailability(UUID userId, String productType) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT EXISTS (SELECT 1 FROM TRANSACTIONS INNER JOIN PRODUCTS " +
                            "ON TRANSACTIONS.PRODUCT_ID=PRODUCTS.ID WHERE TRANSACTIONS.USER_ID = ?" +
                            " AND PRODUCTS.TYPE = ?)",
                    Boolean.class,
                    userId, productType
            );
        } catch (DataAccessException e) {
            logger.error("Ошибка базы данных при проверке транзакций для пользователя: {}", userId, e);
            throw new DatabaseOperationException("Не удалось проверить транзакции", e);
        }
    }

    /**
     * Суммирует транзакции пользователя по заданным критериям.
     *
     * @param userId          идентификатор пользователя
     * @param productType     тип продукта
     * @param transactionType тип транзакции
     * @return сумма всех транзакций, удовлетворяющих условиям (0 если транзакций нет)
     * @throws DatabaseOperationException если произошла ошибка при работе с базой данных
     */
    public Integer sumTransactions(UUID userId, String productType, String transactionType) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT COALESCE(SUM(AMOUNT), 0) FROM TRANSACTIONS INNER JOIN PRODUCTS ON TRANSACTIONS.PRODUCT_ID=PRODUCTS.ID" +
                            " WHERE TRANSACTIONS.USER_ID = ? AND PRODUCTS.TYPE = ? AND " +
                            "TRANSACTIONS.TYPE = ?",
                    Integer.class,
                    userId, productType, transactionType
            );
        } catch (DataAccessException e) {
            logger.error("Ошибка базы данных при суммировании транзакций для пользователя: {}", userId, e);
            throw new DatabaseOperationException("Не удалось суммировать транзакции", e);
        }
    }

    /**
     * Подсчитывает количество транзакций пользователя для определенного типа продукта.
     *
     * @param userId      идентификатор пользователя
     * @param productType тип продукта
     * @return количество транзакций (0 если транзакций нет)
     * @throws DatabaseOperationException если произошла ошибка при работе с базой данных
     */
    public int countTransactionsByProduct(UUID userId, String productType) {
        try {
            Integer res = jdbcTemplate.queryForObject(
                    "SELECT COALESCE(COUNT(*), 0) FROM TRANSACTIONS " +
                            "INNER JOIN PRODUCTS ON TRANSACTIONS.PRODUCT_ID = PRODUCTS.ID " +
                            "WHERE TRANSACTIONS.USER_ID = ? " +
                            "AND PRODUCTS.TYPE = ?",
                    Integer.class,
                    userId, productType

            );
            return res != null ? res : 0;
        } catch (DataAccessException e) {
            logger.error("Ошибка базы данных при суммировании транзакций для пользователя: {}", userId, e);
            throw new DatabaseOperationException("Не удалось суммировать транзакции", e);
        }
    }
}
