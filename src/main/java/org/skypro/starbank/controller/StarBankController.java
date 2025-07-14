package org.skypro.starbank.controller;

import org.skypro.starbank.model.recommendation.RecommendationDTO;
import org.skypro.starbank.service.DynamicRuleRecommendationService;
import org.skypro.starbank.service.StarBankService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Контроллер для работы с рекомендациями банковского сервиса StarBank.
 * Предоставляет API для получения персональных рекомендаций для клиентов.
 */
@RestController
public class StarBankController {

    private final StarBankService starBankService;
    private final DynamicRuleRecommendationService dynamicRuleRecommendationService;

    /**
     * Конструктор контроллера.
     *
     * @param starBankService                  сервис для работы с базовыми рекомендациями
     * @param dynamicRuleRecommendationService сервис для работы с динамическими правилами рекомендаций
     */
    public StarBankController(StarBankService starBankService,
                              DynamicRuleRecommendationService dynamicRuleRecommendationService) {
        this.starBankService = starBankService;
        this.dynamicRuleRecommendationService = dynamicRuleRecommendationService;
    }

    /**
     * Получает персональные рекомендации для клиента по его идентификатору.
     * Комбинирует базовые рекомендации и рекомендации на основе динамических правил.
     *
     * @param id UUID клиента, для которого запрашиваются рекомендации
     * @return список рекомендаций в формате DTO
     * @apiNote GET /recommendation/{id}
     * @implNote Метод объединяет результаты из двух сервисов:
     * 1. Базовые рекомендации от StarBankService
     * 2. Рекомендации на основе динамических правил от DynamicRuleRecommendationService
     */
    @GetMapping("/recommendation/{id}")
    public List<RecommendationDTO> getRecommendation(@PathVariable("id") UUID id) {
        List<RecommendationDTO> recommendation = new ArrayList<>();
        recommendation.addAll(starBankService.defineRecommendations(id));
        recommendation.addAll(dynamicRuleRecommendationService.checkUserAgainstAllDynamicRules(id));
        return recommendation;
    }
}


