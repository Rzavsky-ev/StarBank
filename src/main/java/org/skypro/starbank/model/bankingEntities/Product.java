package org.skypro.starbank.model.bankingEntities;

import java.util.Objects;
import java.util.UUID;

public class Product {
    private UUID id;
    private String type;
    private String name;

    Product() {
    }

    public Product(UUID id, String type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product other)) {
            return false;
        }

        return (this.id == other.id && this.type.equals(other.type) && this.name.equals(other.name));
    }

    @Override
    public String toString() {
        return getId() + " " + getType() + " " + getName();
    }
}
