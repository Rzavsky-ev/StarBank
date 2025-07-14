package org.skypro.starbank.service;

import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRule;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRuleRequest;

import java.util.List;

/**
 * Сервис для работы с динамическими правилами системы.
 * Обеспечивает создание, удаление и получение правил, а также управление их кэшированием.
 */
public interface DynamicRuleRequestService {

    /**
     * Создает новое динамическое правило на основе запроса.
     *
     * @param request DTO с данными для создания правила
     * @return созданное динамическое правило
     * @throws org.skypro.starbank.exception.InvalidRuleException       если данные запроса некорректны
     * @throws org.skypro.starbank.exception.DatabaseOperationException при ошибках сохранения
     */
    DynamicRule createDynamicRule(DynamicRuleRequest request);

    /**
     * Удаляет динамическое правило по идентификатору.
     *
     * @param id идентификатор правила для удаления
     * @throws org.skypro.starbank.exception.RuleNotFoundException      если правило не найдено
     * @throws org.skypro.starbank.exception.DatabaseOperationException при ошибках удаления
     */
    void removeDynamicRule(Long id);

    /**
     * Возвращает список всех динамических правил системы.
     *
     * @return список всех правил (может быть пустым)
     * @throws org.skypro.starbank.exception.DatabaseOperationException при ошибках чтения
     */
    List<DynamicRule> showAllDynamicRules();

    /**
     * Очищает кэши динамических правил.
     * Используется для принудительного обновления данных из БД.
     */
    void clearCaches();
}
