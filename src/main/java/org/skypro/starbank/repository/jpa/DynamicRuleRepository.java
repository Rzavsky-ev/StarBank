package org.skypro.starbank.repository.jpa;

import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamicRuleRepository extends JpaRepository<DynamicRule, Long> {

}
