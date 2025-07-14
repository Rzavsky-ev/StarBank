package org.skypro.starbank.service;

import org.skypro.starbank.model.dynamicRule.dynamicRuleRecommendationSet.DynamicRuleRecommendationSet;
import org.skypro.starbank.model.mapper.DynamicRuleMapper;
import org.skypro.starbank.model.recommendation.DynamicRuleRecommendationDTO;
import org.skypro.starbank.model.rule.Rule;
import org.skypro.starbank.repository.jpa.DynamicRuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для проверки пользователей на соответствие динамическим правилам
 * и генерации персонализированных рекомендаций.
 */
@Service
public class DynamicRuleRecommendationServiceImpl implements DynamicRuleRecommendationService {
    private final DynamicRuleRepository dynamicRuleRepository;
    private final DynamicRuleRecommendationSet dynamicRuleRecommendationSet;
    private final DynamicRuleMapper dynamicRuleMapper;
    private final CounterDynamicRuleServiceImpl counterDynamicRuleService;

    /**
     * Конструктор сервиса.
     *
     * @param dynamicRuleRepository        репозиторий для работы с динамическими правилами
     * @param dynamicRuleRecommendationSet фабрика для создания комбинированных правил
     * @param dynamicRuleMapper            маппер для преобразования сущностей в DTO
     * @param counterDynamicRuleService    сервис для учета статистики использования правил
     */
    public DynamicRuleRecommendationServiceImpl(DynamicRuleRepository dynamicRuleRepository,
                                                DynamicRuleRecommendationSet dynamicRuleRecommendationSet,
                                                DynamicRuleMapper dynamicRuleMapper,
                                                CounterDynamicRuleServiceImpl counterDynamicRuleService) {
        this.dynamicRuleRepository = dynamicRuleRepository;
        this.dynamicRuleRecommendationSet = dynamicRuleRecommendationSet;
        this.dynamicRuleMapper = dynamicRuleMapper;
        this.counterDynamicRuleService = counterDynamicRuleService;
    }

    /**
     * Получает все динамические правила из репозитория и преобразует их в DTO.
     *
     * @return список всех динамических правил в формате DTO
     * @throws org.springframework.dao.DataAccessException при ошибках доступа к данным
     */
    private List<DynamicRuleRecommendationDTO> getAllRules() {
        return dynamicRuleRepository.findAll().stream()
                .map(dynamicRuleMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Увеличивает счетчики использования для списка правил.
     *
     * @param rules список правил, для которых нужно увеличить счетчики
     */
    private void incrementCounter(List<DynamicRuleRecommendationDTO> rules) {
        rules.forEach(rule -> {
            counterDynamicRuleService.incrementCounter(rule.getId());
        });
    }

    /**
     * Проверяет пользователя на соответствие всем динамическим правилам системы
     * и возвращает подходящие рекомендации.
     *
     * @param userId идентификатор пользователя для проверки
     * @return список подходящих рекомендаций (может быть пустым)
     * @throws org.skypro.starbank.exception.DatabaseOperationException при ошибках работы с БД
     * @throws org.skypro.starbank.exception.ClientNotFoundException    если пользователь не найден
     * @implNote Алгоритм работы:
     * 1. Получение всех правил из БД
     * 2. Фильтрация правил по соответствию пользователю
     * 3. Обновление счетчиков использования для подходящих правил
     * 4. Возврат результата
     */
    @Override
    public List<DynamicRuleRecommendationDTO> checkUserAgainstAllDynamicRules(UUID userId) {
        List<DynamicRuleRecommendationDTO> allRules = getAllRules();
        List<DynamicRuleRecommendationDTO> suitableRules = allRules.stream()
                .filter(rule -> {
                    Rule<UUID> combinedRule = dynamicRuleRecommendationSet.createCombinedRule(rule);
                    return combinedRule.check(userId);
                })
                .toList();
        incrementCounter(suitableRules);
        return suitableRules;
    }
}
