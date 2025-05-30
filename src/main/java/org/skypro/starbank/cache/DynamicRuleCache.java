package org.skypro.starbank.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import org.skypro.starbank.dynamicRule.DynamicRule;
import org.skypro.starbank.repository.jpa.DynamicRuleRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class DynamicRuleCache {
    private final DynamicRuleRepository dynamicRuleRepository;

    private final Cache<Long, Optional<DynamicRule>> ruleCache;

    private final Cache<String, List<DynamicRule>> allRuleCache;

    private static final String ALL_RULES_KEY = "ALL_RULES";

    public DynamicRuleCache(DynamicRuleRepository dynamicRuleRepository) {
        this.dynamicRuleRepository = dynamicRuleRepository;

        this.ruleCache = Caffeine.newBuilder().
                maximumSize(1000).
                expireAfterWrite(Duration.ofMinutes(30)).
                build();

        this.allRuleCache = Caffeine.newBuilder().
                maximumSize(1).
                expireAfterWrite(Duration.ofMinutes(10)).
                build();
    }

    public Optional<DynamicRule> findById(Long id) {
        return ruleCache.get(id, dynamicRuleRepository::findById);
    }

    public List<DynamicRule> findAll() {
        return allRuleCache.get(ALL_RULES_KEY, key ->
                Collections.unmodifiableList(dynamicRuleRepository.findAll()));
    }

    public void deleteById(Long id) {
        dynamicRuleRepository.deleteById(id);

        ruleCache.invalidate(id);

        allRuleCache.invalidate(ALL_RULES_KEY);
    }

    public DynamicRule save(DynamicRule dynamicRule) {
        DynamicRule dynamicRuleSave = dynamicRuleRepository.save(dynamicRule);

        ruleCache.put(dynamicRuleSave.getId(), Optional.of(dynamicRuleSave));

        allRuleCache.invalidate(ALL_RULES_KEY);

        return dynamicRuleSave;
    }

    public void invalidateAllCaches() {
        ruleCache.invalidateAll();
        allRuleCache.invalidateAll();
    }
}

