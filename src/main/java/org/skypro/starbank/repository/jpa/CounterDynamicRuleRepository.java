package org.skypro.starbank.repository.jpa;

import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.CounterDynamicRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterDynamicRuleRepository extends
        JpaRepository<CounterDynamicRule, Integer> {

    @Modifying
    @Query("UPDATE CounterDynamicRule c SET c.counter = " +
            "c.counter + 1 WHERE c.dynamicRule.id = :ruleId")
    void incrementCounter(@Param("ruleId") Long ruleId);

}
