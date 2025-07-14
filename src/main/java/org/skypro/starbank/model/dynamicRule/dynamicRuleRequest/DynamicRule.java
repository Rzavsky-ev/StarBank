package org.skypro.starbank.model.dynamicRule.dynamicRuleRequest;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.UUID;

/**
 * Сущность, представляющая динамическое правило в системе рекомендаций.
 * Содержит условия применения правила и связанную информацию о продукте.
 */
@Data
@Entity
@Table(name = "dynamic_rules")
public class DynamicRule {

    /**
     * Уникальный идентификатор правила в базе данных.
     * Генерируется автоматически при сохранении.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Название продукта, к которому относится правило.
     * Обязательное поле, не может быть null.
     */
    @Column(name = "product_name", nullable = false)
    private String productName;

    /**
     * Идентификатор продукта в формате UUID.
     * Обязательное поле, не может быть null.
     */
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    /**
     * Текстовое описание продукта.
     * Обязательное поле, не может быть null.
     */
    @Column(name = "product_text", nullable = false)
    private String productText;

    /**
     * Список условий применения правила.
     * Каскадные операции и автоматическое удаление "сирот".
     * Связаны через колонку rule_id в таблице условий.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "rule_id")
    private List<RequestType> ruleConditions;

    public DynamicRule() {
    }
}
