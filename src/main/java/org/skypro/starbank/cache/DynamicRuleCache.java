package org.skypro.starbank.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRule;
import org.skypro.starbank.repository.jpa.DynamicRuleRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Класс для кэширования динамических правил с использованием Caffeine.
 * Предоставляет методы для работы с кэшем: поиск, сохранение, удаление правил.
 * Кэш разделен на два уровня:
 * 1. Кэш отдельных правил по их ID
 * 2. Кэш всех правил
 */
@Component
public class DynamicRuleCache {
    private final DynamicRuleRepository dynamicRuleRepository;

    private final Cache<Long, Optional<DynamicRule>> ruleCache;

    private final Cache<String, List<DynamicRule>> allRuleCache;

    private static final String ALL_RULES_KEY = "ALL_RULES";

    /**
     * Конструктор класса DynamicRuleCache.
     *
     * @param dynamicRuleRepository репозиторий для работы с динамическими правилами в БД
     */
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

    /**
     * Находит правило по ID, используя кэш.
     * Если правило отсутствует в кэше, загружает его из репозитория.
     *
     * @param id идентификатор правила
     * @return Optional с найденным правилом или пустой, если правило не найдено
     */
    public Optional<DynamicRule> findById(Long id) {
        return ruleCache.get(id, dynamicRuleRepository::findById);
    }

    /**
     * Возвращает все правила, используя кэш.
     * Если правила отсутствуют в кэше, загружает их из репозитория.
     *
     * @return неизменяемый список всех правил
     */
    public List<DynamicRule> findAll() {
        return allRuleCache.get(ALL_RULES_KEY, key ->
                Collections.unmodifiableList(dynamicRuleRepository.findAll()));
    }


    /**
     * Удаляет правило по ID из репозитория и инвалидирует соответствующие записи в кэшах.
     *
     * @param id идентификатор правила для удаления
     */
    public void deleteById(Long id) {
        dynamicRuleRepository.deleteById(id);

        ruleCache.invalidate(id);

        allRuleCache.invalidate(ALL_RULES_KEY);
    }

    /**
     * Сохраняет правило в репозитории и обновляет соответствующие кэши.
     *
     * @param dynamicRule правило для сохранения
     * @return сохраненное правило
     */
    public DynamicRule save(DynamicRule dynamicRule) {
        DynamicRule dynamicRuleSave = dynamicRuleRepository.save(dynamicRule);

        ruleCache.put(dynamicRuleSave.getId(), Optional.of(dynamicRuleSave));

        allRuleCache.invalidate(ALL_RULES_KEY);

        return dynamicRuleSave;
    }

    /**
     * Инвалидирует все кэши (очищает их).
     * После вызова этого метода следующие запросы будут загружать данные из репозитория.
     */
    public void invalidateAllCaches() {
        ruleCache.invalidateAll();
        allRuleCache.invalidateAll();
    }
}

