package org.skypro.starbank.service;

import org.skypro.starbank.cache.DynamicRuleCache;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRule;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRuleRequest;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.RequestType;
import org.skypro.starbank.exception.DynamicRuleNotFoundInDatabaseException;
import org.skypro.starbank.exception.EmptyDatabaseException;
import org.skypro.starbank.exception.EmptyRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Реализация сервиса для работы с динамическими правилами системы.
 * Обеспечивает кэширование и транзакционное управление операциями с правилами.
 */
@Service
public class DynamicRuleRequestServiceImpl implements DynamicRuleRequestService {

    private final DynamicRuleCache dynamicRuleCache;

    private final CounterDynamicRuleServiceImpl counterDynamicRuleService;

    /**
     * Конструктор сервиса.
     *
     * @param dynamicRuleCache          кэш динамических правил
     * @param counterDynamicRuleService сервис для учета статистики использования правил
     */
    public DynamicRuleRequestServiceImpl(DynamicRuleCache dynamicRuleCache,
                                         CounterDynamicRuleServiceImpl counterDynamicRuleService) {
        this.dynamicRuleCache = dynamicRuleCache;
        this.counterDynamicRuleService = counterDynamicRuleService;
    }

    /**
     * Создает новое динамическое правило на основе запроса.
     *
     * @param request DTO с данными для создания правила
     * @return сохраненное динамическое правило
     * @throws EmptyRequestException если запрос пустой
     * @implNote Процесс создания:
     * 1. Валидация входящего запроса
     * 2. Создание объекта правила
     * 3. Преобразование условий правила
     * 4. Создание счетчика использования
     * 5. Сохранение в кэш и БД
     */
    @Override
    @Transactional
    public DynamicRule createDynamicRule(DynamicRuleRequest request) {
        if (request == null) {
            throw new EmptyRequestException("Пустой запрос");
        }
        DynamicRule dynamicRule = new DynamicRule();
        dynamicRule.setProductName(request.getProductName());
        dynamicRule.setProductId(request.getProductId());
        dynamicRule.setProductText(request.getProductText());
        List<RequestType> requestTypes = request.getRule().stream().map(
                dto -> {
                    RequestType requestType = new RequestType();
                    requestType.setQuery(dto.getQuery());
                    requestType.setArguments(dto.getArguments());
                    requestType.setNegate(dto.getNegate());
                    return requestType;
                }
        ).toList();
        dynamicRule.setRuleConditions(requestTypes);
        counterDynamicRuleService.createCounterDynamicRule(dynamicRule);
        return dynamicRuleCache.save(dynamicRule);
    }

    /**
     * Удаляет динамическое правило по идентификатору.
     *
     * @param id идентификатор правила для удаления
     * @throws DynamicRuleNotFoundInDatabaseException если правило не найдено
     * @implNote Процесс удаления:
     * 1. Проверка существования правила
     * 2. Удаление из кэша и БД
     */
    @Override
    @Transactional
    public void removeDynamicRule(Long id) {
        DynamicRule rule = dynamicRuleCache.findById(id)
                .orElseThrow(() -> new DynamicRuleNotFoundInDatabaseException(
                        "Динамическое правило в базе данных не найдено"));
        dynamicRuleCache.deleteById(id);
    }

    /**
     * Возвращает список всех динамических правил.
     *
     * @return список всех правил
     * @throws EmptyDatabaseException если в БД нет правил
     */
    @Override
    @Transactional
    public List<DynamicRule> showAllDynamicRules() {
        List<DynamicRule> rules = dynamicRuleCache.findAll();
        if (rules.isEmpty()) {
            throw new EmptyDatabaseException("База данных пуста");
        }
        return rules;
    }

    /**
     * Очищает кэши динамических правил.
     * Используется для принудительного обновления данных из БД.
     */
    @Override
    @Transactional
    public void clearCaches() {
        dynamicRuleCache.invalidateAllCaches();
    }
}


