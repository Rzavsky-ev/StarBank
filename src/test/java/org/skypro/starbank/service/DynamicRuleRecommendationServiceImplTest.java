package org.skypro.starbank.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRecommendationSet.DynamicRuleRecommendationSet;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRule;
import org.skypro.starbank.model.mapper.DynamicRuleMapper;
import org.skypro.starbank.model.recommendation.DynamicRuleRecommendationDTO;
import org.skypro.starbank.model.rule.Rule;
import org.skypro.starbank.repository.jpa.DynamicRuleRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class DynamicRuleRecommendationServiceImplTest {

    @InjectMocks
    private DynamicRuleRecommendationServiceImpl serviceTest;

    @Mock
    private DynamicRuleRepository dynamicRuleRepository;

    @Mock
    private DynamicRuleRecommendationSet dynamicRuleRecommendationSet;

    @Mock
    private DynamicRuleMapper dynamicRuleMapper;

    @Mock
    private CounterDynamicRuleServiceImpl counterDynamicRuleService;

    private final UUID testUserId = UUID.randomUUID();

    @DisplayName("Должен вернуть пустой список, когда нет правил в репозитории")
    @Test
    public void checkUserAgainstAllDynamicRulesReturnEmptyListWhenNoRulesExist() {

        when(dynamicRuleRepository.findAll()).thenReturn(Collections.emptyList());

        List<DynamicRuleRecommendationDTO> result = serviceTest.checkUserAgainstAllDynamicRules(testUserId);

        assertTrue(result.isEmpty());
        verify(dynamicRuleRepository).findAll();
        verifyNoMoreInteractions(dynamicRuleMapper, dynamicRuleRecommendationSet, counterDynamicRuleService);
    }

    @DisplayName("Должен вернуть только подходящие правила для пользователя")
    @Test
    public void checkUserAgainstAllDynamicRulesReturnOnlySuitableRules() {

        DynamicRuleRecommendationDTO rule1 = new DynamicRuleRecommendationDTO();
        rule1.setId(1L);
        DynamicRuleRecommendationDTO rule2 = new DynamicRuleRecommendationDTO();
        rule2.setId(2L);

        Rule<UUID> mockRule1 = mock(Rule.class);
        Rule<UUID> mockRule2 = mock(Rule.class);

        when(dynamicRuleRepository.findAll()).thenReturn(Arrays.asList(
                mock(DynamicRule.class),
                mock(DynamicRule.class)
        ));
        when(dynamicRuleMapper.toDTO(any())).thenReturn(rule1).thenReturn(rule2);
        when(dynamicRuleRecommendationSet.createCombinedRule(rule1)).thenReturn(mockRule1);
        when(dynamicRuleRecommendationSet.createCombinedRule(rule2)).thenReturn(mockRule2);
        when(mockRule1.check(testUserId)).thenReturn(true);
        when(mockRule2.check(testUserId)).thenReturn(false);

        List<DynamicRuleRecommendationDTO> result = serviceTest.checkUserAgainstAllDynamicRules(testUserId);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(counterDynamicRuleService).incrementCounter(1L);
        verify(counterDynamicRuleService, never()).incrementCounter(2L);
    }

    @DisplayName("Должен увеличить счетчики для всех подходящих правил")
    @Test
    public void checkUserAgainstAllDynamicRulesIncrementCountersForAllSuitableRules() {

        DynamicRuleRecommendationDTO rule1 = new DynamicRuleRecommendationDTO();
        rule1.setId(1L);
        DynamicRuleRecommendationDTO rule2 = new DynamicRuleRecommendationDTO();
        rule2.setId(2L);

        Rule<UUID> mockRule1 = mock(Rule.class);
        Rule<UUID> mockRule2 = mock(Rule.class);

        when(dynamicRuleRepository.findAll()).thenReturn(Arrays.asList(
                mock(DynamicRule.class),
                mock(DynamicRule.class)
        ));
        when(dynamicRuleMapper.toDTO(any())).thenReturn(rule1).thenReturn(rule2);
        when(dynamicRuleRecommendationSet.createCombinedRule(rule1)).thenReturn(mockRule1);
        when(dynamicRuleRecommendationSet.createCombinedRule(rule2)).thenReturn(mockRule2);
        when(mockRule1.check(testUserId)).thenReturn(true);
        when(mockRule2.check(testUserId)).thenReturn(true);

        List<DynamicRuleRecommendationDTO> result = serviceTest.checkUserAgainstAllDynamicRules(testUserId);

        assertEquals(2, result.size());
        verify(counterDynamicRuleService).incrementCounter(1L);
        verify(counterDynamicRuleService).incrementCounter(2L);
    }

    @DisplayName("Не должен увеличивать счетчики, если нет подходящих правил")
    @Test
    public void checkUserAgainstAllDynamicRulesNotIncrementCountersWhenNoSuitableRules() {

        DynamicRuleRecommendationDTO rule1 = new DynamicRuleRecommendationDTO();
        rule1.setId(1L);
        DynamicRuleRecommendationDTO rule2 = new DynamicRuleRecommendationDTO();
        rule2.setId(2L);

        Rule<UUID> mockRule1 = mock(Rule.class);
        Rule<UUID> mockRule2 = mock(Rule.class);

        when(dynamicRuleRepository.findAll()).thenReturn(Arrays.asList(
                mock(DynamicRule.class),
                mock(DynamicRule.class)
        ));
        when(dynamicRuleMapper.toDTO(any())).thenReturn(rule1).thenReturn(rule2);
        when(dynamicRuleRecommendationSet.createCombinedRule(rule1)).thenReturn(mockRule1);
        when(dynamicRuleRecommendationSet.createCombinedRule(rule2)).thenReturn(mockRule2);
        when(mockRule1.check(testUserId)).thenReturn(false);
        when(mockRule2.check(testUserId)).thenReturn(false);

        List<DynamicRuleRecommendationDTO> result = serviceTest.checkUserAgainstAllDynamicRules(testUserId);

        assertTrue(result.isEmpty());
        verify(counterDynamicRuleService, never()).incrementCounter(anyLong());
    }

    @DisplayName("Должен корректно обрабатывать исключение из репозитория")
    @Test
    public void checkUserAgainstAllDynamicRulesHandleRepositoryExceptionGracefully() {

        when(dynamicRuleRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> {
            serviceTest.checkUserAgainstAllDynamicRules(testUserId);
        });
    }
}


