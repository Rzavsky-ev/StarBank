package org.skypro.starbank.repository;

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

    public Boolean debitCheck(UUID userId) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT EXISTS (SELECT 1 FROM TRANSACTIONS INNER JOIN PRODUCTS " +
                            "ON TRANSACTIONS.PRODUCT_ID=PRODUCTS.ID WHERE TRANSACTIONS.USER_ID = ?" +
                            " AND PRODUCTS.TYPE = 'DEBIT')",
                    Boolean.class,
                    userId
            );
        } catch (DataAccessException e) {
            logger.error("Database error while checking debit transactions for user: {}", userId, e);
            throw new DatabaseOperationException("Failed to check debit transactions", e);
        }
    }

    public Boolean investCheck(UUID userId) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT EXISTS (SELECT 1 FROM TRANSACTIONS INNER JOIN PRODUCTS " +
                            "ON TRANSACTIONS.PRODUCT_ID=PRODUCTS.ID WHERE TRANSACTIONS.USER_ID = ?" +
                            " AND PRODUCTS.TYPE = 'INVEST')",
                    Boolean.class,
                    userId
            );
        } catch (DataAccessException e) {
            logger.error("Database error while checking invest transactions for user: {}", userId, e);
            throw new DatabaseOperationException("Failed to check invest transactions", e);
        }
    }

    public Integer summaOfDepositSaving(UUID userId) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT COALESCE(SUM(AMOUNT), 0) FROM TRANSACTIONS INNER JOIN PRODUCTS ON TRANSACTIONS.PRODUCT_ID=PRODUCTS.ID" +
                            " WHERE TRANSACTIONS.USER_ID = ? AND PRODUCTS.TYPE = 'SAVING' AND " +
                            "TRANSACTIONS.TYPE = 'DEPOSIT'",
                    Integer.class,
                    userId
            );
        } catch (DataAccessException e) {
            logger.error("Database error while summing saving transactions for user: {}", userId, e);
            throw new DatabaseOperationException("Failed to sum saving transactions", e);
        }
    }

    public Integer summaOfDepositDebit(UUID userId) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT COALESCE(SUM(AMOUNT), 0) FROM TRANSACTIONS INNER JOIN PRODUCTS ON TRANSACTIONS.PRODUCT_ID=PRODUCTS.ID" +
                            " WHERE TRANSACTIONS.USER_ID = ? AND PRODUCTS.TYPE = 'DEBIT' AND " +
                            "TRANSACTIONS.TYPE = 'DEPOSIT'",
                    Integer.class,
                    userId
            );
        } catch (DataAccessException e) {
            logger.error("Database error while summing debit deposit transactions for user: {}", userId, e);
            throw new DatabaseOperationException("Failed to sum debit deposit transactions", e);
        }
    }

    public Integer summaOfWithdrawDebit(UUID userId) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT COALESCE(SUM(AMOUNT), 0) FROM TRANSACTIONS INNER JOIN PRODUCTS ON TRANSACTIONS.PRODUCT_ID=PRODUCTS.ID" +
                            " WHERE TRANSACTIONS.USER_ID = ? AND PRODUCTS.TYPE = 'DEBIT' AND " +
                            "TRANSACTIONS.TYPE = 'WITHDRAW'",
                    Integer.class,
                    userId
            );
        } catch (DataAccessException e) {
            logger.error("Database error while summing debit withdraw transactions for user: {}", userId, e);
            throw new DatabaseOperationException("Failed to sum debit withdraw transactions", e);
        }
    }

    public Boolean creditCheck(UUID userId) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT EXISTS (SELECT 1 FROM TRANSACTIONS INNER JOIN PRODUCTS " +
                            "ON TRANSACTIONS.PRODUCT_ID=PRODUCTS.ID WHERE TRANSACTIONS.USER_ID = ?" +
                            " AND PRODUCTS.TYPE = 'CREDIT')",
                    Boolean.class,
                    userId
            );
        } catch (DataAccessException e) {
            logger.error("Database error while checking credit transactions for user: {}", userId, e);
            throw new DatabaseOperationException("Failed to credit debit transactions", e);
        }
    }


}
 /* //Гетеры для bankingEntities (наверное, пригодятся)
    public User getUser(UUID id) {
        try {
            return jdbcTemplate.queryForObject("SELECT USERNAME, FIRST_NAME, LAST_NAME FROM USERS WHERE id=?",
                    (rs, rowNum) -> {
                        String userName = rs.getString("USERNAME");
                        String firstName = rs.getString("FIRST_NAME");
                        String lastName = rs.getString("LAST_NAME");
                        if (userName == null || firstName == null || lastName == null) {
                            logger.error("Failed to fetch Full Name for user: {}", id);
                            throw new InvalidUserDataException("NULL value in user name with id-" + id);
                        }
                        return new User(id, userName, firstName, lastName);
                    },
                    id);
        } catch (EmptyResultDataAccessException e) {
            logger.error("User not found with id: {}", id, e);
            throw new UserNotFoundException("User not found with id-" + id, e);
        } catch (DataAccessException e) {
            logger.error("Database access error for user id: {}", id, e);
            throw new DatabaseOperationException("Database access error for user id:" + id, e);
        }
    }

    public Product getProduct(UUID id) {
        try {
            return jdbcTemplate.queryForObject("SELECT TYPE, NAME FROM PRODUCTS WHERE id=?",
                    (rs, rowNum) -> {
                        String type = rs.getString("TYPE");
                        String name = rs.getString("NAME");
                        if (type == null || name == null) {
                            logger.error("Failed to get data for product: {}", id);
                            throw new InvalidProductDataException("NULL value in product data with ID-" + id);
                        }
                        return new Product(id, type, name);
                    },
                    id);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Product not found with id: {}", id, e);
            throw new ProductNotFoundException("Product not found with id-" + id, e);
        } catch (DataAccessException e) {
            logger.error("Database access error for product id: {}", id, e);
            throw new DatabaseOperationException("Database access error for product id:" + id, e);
        }
    }

    public Transaction getTransaction(UUID id) {
        try {
            return jdbcTemplate.queryForObject("SELECT PRODUCT_ID, USER_ID, TYPE, AMOUNT FROM TRANSACTIONS " +
                            "WHERE id=?",
                    (rs, rowNum) -> {
                        UUID productId = rs.getObject("PRODUCT_ID", UUID.class);
                        UUID userId = rs.getObject("USER_ID", UUID.class);
                        String type = rs.getString("TYPE");
                        BigDecimal amount = rs.getBigDecimal("AMOUNT");
                        if (productId == null || userId == null || type == null || amount == null) {
                            logger.error("Failed to get data for transaction: {}", id);
                            throw new InvalidTransactionDataException("NULL value in transaction data with ID-" + id);
                        }
                        return new Transaction(id, productId, userId, type, amount);
                    },
                    id);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Transaction not found with id: {}", id, e);
            throw new TransactionNotFoundException("Transaction not found with id-" + id, e);
        } catch (DataAccessException e) {
            logger.error("Database access error for product id: {}", id, e);
            throw new DatabaseOperationException("Database access error for transaction id:" + id, e);
        }
    }

    public List<String> allProductUser(UUID userId) {
        try {
            return jdbcTemplate.query(
            "SELECT DISTINCT PRODUCTS.TYPE FROM TRANSACTIONS " +
                    "INNER JOIN PRODUCTS ON TRANSACTIONS.PRODUCT_ID = PRODUCTS.ID " +
                    "WHERE TRANSACTIONS.USER_ID = ?",
                    (rs, rowNum) -> rs.getString("TYPE"),
                    userId
        );
        } catch (DataAccessException e) {
            logger.error("Database error while checking credit transactions for user: {}", userId, e);
            throw new DatabaseOperationException("Failed to credit debit transactions", e);
        }
    }
*/
