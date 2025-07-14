package org.skypro.starbank.controller;

import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRule;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRuleRequest;
import org.skypro.starbank.model.mapper.RuleStatsResponse;
import org.skypro.starbank.service.CounterDynamicRuleService;
import org.skypro.starbank.service.DynamicRuleRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для управления динамическими правилами и статистикой их использования.
 * Предоставляет REST API для:
 * - Создания и удаления динамических правил
 * - Получения списка всех правил
 * - Получения статистики использования правил
 * - Очистки кэшей
 */
@RestController
public class DynamicRuleController {

    private final DynamicRuleRequestService dynamicRuleService;

    private final CounterDynamicRuleService counterDynamicRuleService;

    /**
     * Конструктор контроллера.
     *
     * @param dynamicRuleService        сервис для работы с динамическими правилами
     * @param counterDynamicRuleService сервис для сбора статистики по правилам
     */
    public DynamicRuleController(DynamicRuleRequestService dynamicRuleService,
                                 CounterDynamicRuleService counterDynamicRuleService) {
        this.dynamicRuleService = dynamicRuleService;
        this.counterDynamicRuleService = counterDynamicRuleService;
    }

    /**
     * Создает новое динамическое правило.
     *
     * @param dynamicRuleRequest запрос с данными для создания правила
     * @return созданное динамическое правило
     */
    @PostMapping(path = "/rule")
    public DynamicRule createDynamicRule(@RequestBody DynamicRuleRequest dynamicRuleRequest) {
        return dynamicRuleService.createDynamicRule(dynamicRuleRequest);
    }

    /**
     * Возвращает список всех динамических правил.
     *
     * @return список всех динамических правил
     */
    @GetMapping(path = "/rule")
    public List<DynamicRule> showAllDynamicRules() {
        return dynamicRuleService.showAllDynamicRules();
    }

    /**
     * Возвращает статистику использования динамических правил.
     *
     * @return объект с собранной статистикой
     */
    @GetMapping(path = "/rule/stats")
    public RuleStatsResponse getStats() {
        return counterDynamicRuleService.getStats();
    }

    /**
     * Очищает кэши динамических правил.
     * Доступно через endpoint /management/clear-caches
     */
    @PostMapping("/management/clear-caches")
    public void clearCaches() {
        dynamicRuleService.clearCaches();
    }

    /**
     * Удаляет динамическое правило по указанному идентификатору.
     *
     * @param id идентификатор правила для удаления
     */
    @DeleteMapping("{id}")
    public void removeDynamicRule(@PathVariable("id") Long id) {
        dynamicRuleService.removeDynamicRule(id);
    }
}
