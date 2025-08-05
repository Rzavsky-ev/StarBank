package org.skypro.starbank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.starbank.cache.DynamicRuleCache;
import org.skypro.starbank.exception.DynamicRuleNotFoundInDatabaseException;
import org.skypro.starbank.exception.EmptyDatabaseException;
import org.skypro.starbank.exception.EmptyRequestException;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRule;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRuleRequest;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.QueryType;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.RequestType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class DynamicRuleRequestServiceImplTest {

    @InjectMocks
    private DynamicRuleRequestServiceImpl serviceTest;

    @Mock
    private DynamicRuleCache cacheMock;

    @Mock
    private CounterDynamicRuleServiceImpl serviceMock;

    @BeforeEach
    void setUp() {
        serviceTest = new DynamicRuleRequestServiceImpl(cacheMock, serviceMock);
    }

    @DisplayName("Должен выбросить EmptyRequestException при получении null запроса")
    @Test
    public void createDynamicRuleThrowEmptyRequestException_whenRequestIsNull() {
        assertThrows(EmptyRequestException.class,
                () -> serviceTest.createDynamicRule(null),
                "Ожидалось исключение EmptyRequestException при null запросе");
    }

    @DisplayName("Должен корректно создавать правило с правильными полями")
    @Test
    public void createDynamicRuleCreateRuleWithCorrectFields() {

        UUID productId = UUID.randomUUID();
        DynamicRuleRequest request = new DynamicRuleRequest();
        request.setProductName("Test Product");
        request.setProductId(productId);
        request.setProductText("Test Description");

        DynamicRuleRequest.RuleDTO ruleDto = new DynamicRuleRequest.RuleDTO();
        ruleDto.setQuery(QueryType.ACTIVE_USER_OF);
        ruleDto.setArguments(List.of("18"));
        ruleDto.setNegate(false);
        request.setRule(List.of(ruleDto));

        doNothing().when(serviceMock).createCounterDynamicRule(any(DynamicRule.class));

        when(cacheMock.save(any(DynamicRule.class))).thenAnswer(invocation -> {
            DynamicRule rule = invocation.getArgument(0);
            rule.setId(1L);
            return rule;
        });

        DynamicRule result = serviceTest.createDynamicRule(request);

        assertNotNull(result);
        assertEquals("Test Product", result.getProductName());
        assertEquals(productId, result.getProductId());
        assertEquals("Test Description", result.getProductText());

        assertEquals(1, result.getRuleConditions().size());
        RequestType condition = result.getRuleConditions().get(0);
        assertEquals(QueryType.ACTIVE_USER_OF, condition.getQuery());
        assertEquals(List.of("18"), condition.getArguments());
        assertFalse(condition.isNegate());

        verify(serviceMock).createCounterDynamicRule(any(DynamicRule.class));
        verify(cacheMock).save(any(DynamicRule.class));
    }

    @DisplayName("Должен корректно обрабатывать несколько условий правила")
    @Test
    public void createDynamicRuleHandleMultipleRuleConditions() {

        DynamicRuleRequest request = new DynamicRuleRequest();
        request.setProductName("Test Product");
        request.setProductId(UUID.randomUUID());
        request.setProductText("Test Description");

        DynamicRuleRequest.RuleDTO ruleDto1 = new DynamicRuleRequest.RuleDTO();
        ruleDto1.setQuery(QueryType.ACTIVE_USER_OF);
        ruleDto1.setArguments(List.of("18"));
        ruleDto1.setNegate(false);

        DynamicRuleRequest.RuleDTO ruleDto2 = new DynamicRuleRequest.RuleDTO();
        ruleDto2.setQuery(QueryType.TRANSACTION_SUM_COMPARE);
        ruleDto2.setArguments(List.of("1000"));
        ruleDto2.setNegate(true);

        request.setRule(List.of(ruleDto1, ruleDto2));

        when(cacheMock.save(any(DynamicRule.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DynamicRule result = serviceTest.createDynamicRule(request);

        List<RequestType> conditions = result.getRuleConditions();
        assertEquals(2, conditions.size(), "Должно быть два условия правила");

        assertEquals(QueryType.ACTIVE_USER_OF, conditions.get(0).getQuery());
        assertEquals(List.of("18"), conditions.get(0).getArguments());
        assertFalse(conditions.get(0).isNegate());

        assertEquals(QueryType.TRANSACTION_SUM_COMPARE, conditions.get(1).getQuery());
        assertEquals(List.of("1000"), conditions.get(1).getArguments());
        assertTrue(conditions.get(1).isNegate());
    }

    @DisplayName("Должен успешно удалить существующее динамическое правило")
    @Test
    public void removeDynamicRuleDeleteExistingRule() {

        Long ruleId = 1L;
        DynamicRule mockRule = new DynamicRule();
        when(cacheMock.findById(ruleId)).thenReturn(Optional.of(mockRule));

        serviceTest.removeDynamicRule(ruleId);

        verify(cacheMock, times(1)).findById(ruleId);
        verify(cacheMock, times(1)).deleteById(ruleId);
    }

    @DisplayName("Должен выбросить исключение при попытке удалить несуществующее правило")
    @Test
    public void removeDynamicRuleThrowExceptionWhenRuleNotFound() {

        Long nonExistentRuleId = 999L;
        when(cacheMock.findById(nonExistentRuleId)).thenReturn(Optional.empty());

        assertThrows(DynamicRuleNotFoundInDatabaseException.class,
                () -> serviceTest.removeDynamicRule(nonExistentRuleId));

        verify(cacheMock, times(1)).findById(nonExistentRuleId);
        verify(cacheMock, never()).deleteById(any());
    }

    @DisplayName("Должен иметь аннотацию @Transactional для метода удаления")
    @Test
    public void removeDynamicRuleVerifyTransactionAnnotation() throws NoSuchMethodException {

        var method = DynamicRuleRequestServiceImpl.class
                .getMethod("removeDynamicRule", Long.class);
        assertNotNull(method.getAnnotation(Transactional.class),
                "Метод должен быть аннотирован @Transactional");
    }

    @DisplayName("Должен отменить удаление при возникновении исключения во время операции")
    @Test
    public void removeDynamicRuleNotDeleteWhenExceptionThrown() {

        Long ruleId = 1L;
        when(cacheMock.findById(ruleId))
                .thenThrow(new RuntimeException("Ошибка доступа к кэшу"));


        assertThrows(RuntimeException.class,
                () -> serviceTest.removeDynamicRule(ruleId));

        verify(cacheMock, never()).deleteById(any());
    }

    @DisplayName("Должен вернуть список всех динамических правил при непустой БД")
    @Test
    public void showAllDynamicRulesReturnListOfRulesWhenDatabaseIsNotEmpty() {

        DynamicRule rule1 = createTestRule(1L, "Product 1");
        DynamicRule rule2 = createTestRule(2L, "Product 2");
        List<DynamicRule> expectedRules = List.of(rule1, rule2);

        when(cacheMock.findAll()).thenReturn(expectedRules);

        List<DynamicRule> actualRules = serviceTest.showAllDynamicRules();

        assertNotNull(actualRules);
        assertEquals(2, actualRules.size());
        assertEquals(expectedRules, actualRules);
        verify(cacheMock, times(1)).findAll();
    }

    @DisplayName("Должен выбросить исключение при попытке получить правила из пустой БД")
    @Test
    public void showAllDynamicRulesThrowEmptyDatabaseExceptionWhenDatabaseIsEmpty() {

        when(cacheMock.findAll()).thenReturn(Collections.emptyList());

        EmptyDatabaseException exception = assertThrows(
                EmptyDatabaseException.class,
                () -> serviceTest.showAllDynamicRules()
        );

        assertEquals("База данных пуста", exception.getMessage());
        verify(cacheMock, times(1)).findAll();
    }

    private DynamicRule createTestRule(Long id, String productName) {
        UUID productId = UUID.randomUUID();
        DynamicRule rule = new DynamicRule();
        rule.setId(id);
        rule.setProductName(productName);
        rule.setProductId(productId);
        rule.setProductText("Description for " + productName);

        RequestType requestType = new RequestType();
        requestType.setArguments(List.of("arg1", "arg2"));
        requestType.setNegate(false);

        rule.setRuleConditions(List.of(requestType));
        return rule;
    }

    @DisplayName("Должен вызвать метод invalidateAllCaches при очистке кэша")
    @Test
    public void clearCachesCallInvalidateAllCaches() {

        serviceTest.clearCaches();

        verify(cacheMock, times(1)).invalidateAllCaches();
        verifyNoMoreInteractions(cacheMock);
        verifyNoInteractions(serviceMock);
    }

    @DisplayName("Должен выполнить очистку кэша без исключений")
    @Test
    public void clearCachesNotThrowExceptions() {

        doNothing().when(cacheMock).invalidateAllCaches();

        assertDoesNotThrow(() -> serviceTest.clearCaches());
    }
}




