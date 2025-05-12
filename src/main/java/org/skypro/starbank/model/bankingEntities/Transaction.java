package org.skypro.starbank.model.bankingEntities;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Transaction {

    private UUID id;
    private UUID productId;
    private UUID userId;
    private String type;
    private BigDecimal amount;

    public Transaction() {
    }

    public Transaction(UUID id, UUID productId, UUID userId,
                       String type, BigDecimal amount) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.type = type;
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public UUID getProductId() {
        return productId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, userId, type, amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction other)) {
            return false;
        }

        return (this.id == other.id && this.userId == other.userId
                && this.productId == other.productId && this.type.equals(other.type) &&
                this.amount.equals(other.amount));
    }

    @Override
    public String toString() {
        return getId() + " " + getProductId() + " " + getUserId() + " " + getType() + " "
                + getAmount();
    }
}
