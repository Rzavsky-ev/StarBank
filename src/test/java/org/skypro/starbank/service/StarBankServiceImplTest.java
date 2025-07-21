package org.skypro.starbank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.starbank.model.recommendation.RuleRecommendationDTO;
import org.skypro.starbank.model.recommendationRuleSet.RecommendationRuleSet;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StarBankServiceImplTest {

    @Mock
    private RecommendationRuleSet ruleSet1;

    @Mock
    private RecommendationRuleSet ruleSet2;

    @Mock
    private RecommendationRuleSet ruleSet3;

    private StarBankServiceImpl service;

    private final UUID testUserId = UUID.randomUUID();
    private final UUID recommendationId1 = UUID.randomUUID();
    private final UUID recommendationId2 = UUID.randomUUID();
    private final RuleRecommendationDTO recommendation1 = new RuleRecommendationDTO(
            recommendationId1, "Кредитная карта", "Оформите кредитную карту с льготным периодом");
    private final RuleRecommendationDTO recommendation2 = new RuleRecommendationDTO(
            recommendationId2, "Инвестиции", "Откройте инвестиционный счет");

    @BeforeEach
    void setUp() {
        service = new StarBankServiceImpl(Arrays.asList(ruleSet1, ruleSet2, ruleSet3));
    }

    @DisplayName("Должен возвращать пустой список, когда ни одно правило не предоставляет рекомендаций")
    @Test
    public void defineRecommendationsReturnEmptyListWhenNoRulesProvideRecommendations() {

        when(ruleSet1.getRecommendation(testUserId)).thenReturn(Optional.empty());
        when(ruleSet2.getRecommendation(testUserId)).thenReturn(Optional.empty());
        when(ruleSet3.getRecommendation(testUserId)).thenReturn(Optional.empty());

        List<RuleRecommendationDTO> result = service.defineRecommendations(testUserId);

        assertTrue(result.isEmpty());
        verify(ruleSet1).getRecommendation(testUserId);
        verify(ruleSet2).getRecommendation(testUserId);
        verify(ruleSet3).getRecommendation(testUserId);
    }

    @DisplayName("Должен возвращать все рекомендации, когда все правила их предоставляют")
    @Test
    public void defineRecommendationsReturnAllRecommendationsWhenAllRulesProvideThem() {

        RuleRecommendationDTO recommendation3 = new RuleRecommendationDTO(
                UUID.randomUUID(), "Вклад", "Откройте накопительный вклад под 8% годовых");

        when(ruleSet1.getRecommendation(testUserId)).thenReturn(Optional.of(recommendation1));
        when(ruleSet2.getRecommendation(testUserId)).thenReturn(Optional.of(recommendation2));
        when(ruleSet3.getRecommendation(testUserId)).thenReturn(Optional.of(recommendation3));

        List<RuleRecommendationDTO> result = service.defineRecommendations(testUserId);

        assertTrue(result.stream().anyMatch(r -> r.getId().equals(recommendationId1)));
        assertTrue(result.stream().anyMatch(r -> r.getId().equals(recommendationId2)));
        assertTrue(result.stream().anyMatch(r -> r.getName().equals("Вклад")));
    }

    @DisplayName("Должен возвращать некоторые рекомендации, когда только некоторые правила их предоставляют")
    @Test
    public void defineRecommendationsReturnSomeRecommendationsWhenSomeRulesProvideThem() {

        when(ruleSet1.getRecommendation(testUserId)).thenReturn(Optional.empty());
        when(ruleSet2.getRecommendation(testUserId)).thenReturn(Optional.of(recommendation1));
        when(ruleSet3.getRecommendation(testUserId)).thenReturn(Optional.empty());

        List<RuleRecommendationDTO> result = service.defineRecommendations(testUserId);

        assertEquals(1, result.size());
        assertEquals(recommendationId1, result.get(0).getId());
        assertEquals("Кредитная карта", result.get(0).getName());
        assertEquals("Оформите кредитную карту с льготным периодом", result.get(0).getDescription());
    }

    @DisplayName("Должен корректно обрабатывать пустой список правил")
    @Test
    public void defineRecommendationsHandleEmptyRulesList() {

        StarBankServiceImpl emptyService = new StarBankServiceImpl(Collections.emptyList());

        List<RuleRecommendationDTO> result = emptyService.defineRecommendations(testUserId);

        assertTrue(result.isEmpty());
    }

    @DisplayName("Должен вызывать каждое правило только один раз")
    @Test
    public void defineRecommendationsCallEachRuleSetOnlyOnce() {

        when(ruleSet1.getRecommendation(testUserId)).thenReturn(Optional.empty());
        when(ruleSet2.getRecommendation(testUserId)).thenReturn(Optional.empty());
        when(ruleSet3.getRecommendation(testUserId)).thenReturn(Optional.empty());

        service.defineRecommendations(testUserId);

        verify(ruleSet1, times(1)).getRecommendation(testUserId);
        verify(ruleSet2, times(1)).getRecommendation(testUserId);
        verify(ruleSet3, times(1)).getRecommendation(testUserId);
        verifyNoMoreInteractions(ruleSet1, ruleSet2, ruleSet3);
    }

    @DisplayName("Должен возвращать полные данные рекомендации")
    @Test
    public void defineRecommendationsReturnCompleteRecommendationData() {

        when(ruleSet1.getRecommendation(testUserId)).thenReturn(Optional.of(recommendation1));

        RuleRecommendationDTO result = service.defineRecommendations(testUserId).get(0);

        assertNotNull(result.getId());
        assertEquals("Кредитная карта", result.getName());
        assertEquals("Оформите кредитную карту с льготным периодом", result.getDescription());
    }


}
