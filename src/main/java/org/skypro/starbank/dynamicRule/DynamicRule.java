package org.skypro.starbank.dynamicRule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "dynamic_rules")
public class DynamicRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "product_text", nullable = false)
    private String productText;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "rule_id")
    private List<RequestType> ruleConditions;

    public DynamicRule() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DynamicRule other)) return false;
        return Objects.equals(id, other.id) &&
                Objects.equals(productName, other.productName) && Objects.equals(productId, other.productId);
    }

    @Override
    public String toString() {
        return "DynamicRule{" +
                "id=" + id +
                ", productName=" + productName + '\'' +
                ", productId=" + productId +
                '}';
    }
}
