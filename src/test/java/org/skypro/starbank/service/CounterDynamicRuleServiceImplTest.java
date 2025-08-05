package org.skypro.starbank.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.CounterDynamicRule;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRule;
import org.skypro.starbank.repository.jpa.CounterDynamicRuleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CounterDynamicRuleServiceImplTest {

    @InjectMocks
    private CounterDynamicRuleServiceImpl serviceTest;

    @Mock
    private CounterDynamicRuleRepository repositoryMock;

    @DisplayName("Должен сохранять новое правило в репозитории")
    @Test
    public void createCounterDynamicRuleSaveNewCounterDynamicRule() {

        DynamicRule rule = new DynamicRule();

        serviceTest.createCounterDynamicRule(rule);

        verify(repositoryMock, times(1)).save(any(CounterDynamicRule.class));
    }

    @DisplayName("Должен корректно устанавливать DynamicRule для нового объекта")
    @Test
    public void createCounterDynamicRuleSetDynamicRuleForNewCounterDynamicRule() {

        DynamicRule rule = new DynamicRule();

        rule.setProductName("test product");

        serviceTest.createCounterDynamicRule(rule);

        ArgumentCaptor<CounterDynamicRule> captor = ArgumentCaptor.forClass(CounterDynamicRule.class);
        verify(repositoryMock).save(captor.capture());

        CounterDynamicRule savedRule = captor.getValue();
        assertThat(savedRule.getDynamicRule()).isEqualTo(rule);
    }

    @DisplayName("Метод должен быть помечен как транзакционный")
    @Test
    public void createCounterDynamicRuleBeTransactional() throws NoSuchMethodException {

        Method method = CounterDynamicRuleServiceImpl.class.getMethod("createCounterDynamicRule", DynamicRule.class);

        Transactional transactional = method.getAnnotation(Transactional.class);

        assertThat(transactional).isNotNull();
    }

}
