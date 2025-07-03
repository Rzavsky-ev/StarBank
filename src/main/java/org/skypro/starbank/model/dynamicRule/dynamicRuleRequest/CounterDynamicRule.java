package org.skypro.starbank.model.dynamicRule.dynamicRuleRequest;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Entity
@Table(name = "dynamic_rules_counter")
public class CounterDynamicRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "dynamic_rule_id", nullable = false, unique = true)
    private DynamicRule dynamicRule;


    @Column(name = "counter", nullable = false)
    private Long counter;

    public CounterDynamicRule() {
    }
}
