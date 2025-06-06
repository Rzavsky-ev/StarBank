package org.skypro.starbank.repository.jdbc;

import org.skypro.starbank.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(TransactionRepository.class);

    public TransactionRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
