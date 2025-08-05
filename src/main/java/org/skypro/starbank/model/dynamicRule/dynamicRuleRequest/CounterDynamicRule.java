package org.skypro.starbank.model.dynamicRule.dynamicRuleRequest;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Сущность для учета количества использований динамических правил.
 * Хранит счетчик обращений к каждому правилу рекомендательной системы.
 */
@Data
@Entity
@Table(name = "dynamic_rules_counter")
public class CounterDynamicRule {

    /**
     * Уникальный идентификатор счетчика в базе данных.
     * Генерируется автоматически при сохранении.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Динамическое правило, для которого ведется учет.
     * Связь Many-to-One с сущностью DynamicRule.
     * Обязательное поле, уникальное значение.
     */
    @ManyToOne
    @JoinColumn(name = "dynamic_rule_id", nullable = false, unique = true)
    private DynamicRule dynamicRule;

    /**
     * Счетчик использования правила.
     * Не может быть null, инициализируется при создании.
     */
    @Column(name = "counter", nullable = false)
    private Long counter;

    public CounterDynamicRule() {
    }
}
