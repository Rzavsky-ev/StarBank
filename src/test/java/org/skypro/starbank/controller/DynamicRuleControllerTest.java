package org.skypro.starbank.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRule;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRuleRequest;
import org.skypro.starbank.model.mapper.RuleStatsResponse;
import org.skypro.starbank.service.CounterDynamicRuleService;
import org.skypro.starbank.service.DynamicRuleRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DynamicRuleController.class)
public class DynamicRuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DynamicRuleRequestService dynamicRuleService;

    @MockitoBean
    private CounterDynamicRuleService counterDynamicRuleService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Должен создать динамическое правило и вернуть его при валидном запросе")
    @Test
    public void createDynamicRuleValidRequestReturnsCreatedRule() throws Exception {
        UUID id = UUID.randomUUID();
        DynamicRuleRequest request = new DynamicRuleRequest();
        request.setProductName("Test Product");
        request.setProductId(id);
        request.setProductText("Test Description");

        DynamicRule createdRule = new DynamicRule();
        createdRule.setId(1L);
        createdRule.setProductName("Test Product");
        createdRule.setProductId(id);
        createdRule.setProductText("Test Description");

        Mockito.when(dynamicRuleService.createDynamicRule(any(DynamicRuleRequest.class)))
                .thenReturn(createdRule);

        mockMvc.perform(post("/rule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.productName").value("Test Product"))
                .andExpect(jsonPath("$.productId").value(id.toString()))
                .andExpect(jsonPath("$.productText").value("Test Description"));
    }

    @DisplayName("Должен вернуть ошибку 400 при попытке создания правила с null запросом")
    @Test
    public void createDynamicRuleNullRequestReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/rule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("null"))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Должен вернуть список всех динамических правил")
    @Test
    public void showAllDynamicRulesReturnAllRules() throws Exception {

        DynamicRule rule1 = new DynamicRule();
        rule1.setId(1L);
        rule1.setProductName("Rule 1");

        DynamicRule rule2 = new DynamicRule();
        rule2.setId(2L);
        rule2.setProductName("Rule 2");

        List<DynamicRule> rules = Arrays.asList(rule1, rule2);

        when(dynamicRuleService.showAllDynamicRules()).thenReturn(rules);

        mockMvc.perform(get("/rule"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].productName").value("Rule 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].productName").value("Rule 2"));
    }

    @DisplayName("Должен вернуть пустой список при отсутствии динамических правил")
    @Test
    public void showAllDynamicRulesWhenNoRulesReturnEmptyList() throws Exception {

        when(dynamicRuleService.showAllDynamicRules()).thenReturn(List.of());

        mockMvc.perform(get("/rule"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @DisplayName("Должен вернуть статистику по нескольким правилам")
    @Test
    public void getStatsReturnStatsWithMultipleRules() throws Exception {

        List<RuleStatsResponse.RuleStat> ruleStats = Arrays.asList(
                new RuleStatsResponse.RuleStat("1", 10L),
                new RuleStatsResponse.RuleStat("2", 5L),
                new RuleStatsResponse.RuleStat("3", 15L)
        );
        RuleStatsResponse mockResponse = new RuleStatsResponse(ruleStats);

        when(counterDynamicRuleService.getStats()).thenReturn(mockResponse);

        mockMvc.perform(get("/rule/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stats").isArray())
                .andExpect(jsonPath("$.stats.length()").value(3))
                .andExpect(jsonPath("$.stats[0].ruleId").value("1"))
                .andExpect(jsonPath("$.stats[0].count").value(10L))
                .andExpect(jsonPath("$.stats[1].ruleId").value("2"))
                .andExpect(jsonPath("$.stats[1].count").value(5L))
                .andExpect(jsonPath("$.stats[2].ruleId").value("3"))
                .andExpect(jsonPath("$.stats[2].count").value(15L));
    }

    @DisplayName("Должен вернуть пустую статистику при отсутствии правил")
    @Test
    public void getStatsReturnEmptyStatsWhenNoRulesExist() throws Exception {

        RuleStatsResponse mockResponse = new RuleStatsResponse(List.of());

        when(counterDynamicRuleService.getStats()).thenReturn(mockResponse);

        mockMvc.perform(get("/rule/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stats").isArray())
                .andExpect(jsonPath("$.stats.length()").value(0));
    }

    @DisplayName("Должен вернуть статистику по одному правилу")
    @Test
    public void getStatsReturnSingleRuleStat() throws Exception {

        List<RuleStatsResponse.RuleStat> ruleStats = List.of(
                new RuleStatsResponse.RuleStat("5", 7L)
        );
        RuleStatsResponse mockResponse = new RuleStatsResponse(ruleStats);

        when(counterDynamicRuleService.getStats()).thenReturn(mockResponse);

        mockMvc.perform(get("/rule/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stats.length()").value(1))
                .andExpect(jsonPath("$.stats[0].ruleId").value("5"))
                .andExpect(jsonPath("$.stats[0].count").value(7));
    }

    @DisplayName("Должен очистить кэш и вернуть статус OK")
    @Test
    public void clearCachesCallServiceMethodAndReturnOk() throws Exception {

        doNothing().when(dynamicRuleService).clearCaches();

        mockMvc.perform(post("/management/clear-caches"))
                .andExpect(status().isOk());

        verify(dynamicRuleService, times(1)).clearCaches();
    }

    @DisplayName("Должен вернуть OK при успешном удалении существующего правила")
    @Test
    public void removeDynamicRuleReturnOkWhenRuleExists() throws Exception {
        mockMvc.perform(delete("/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("Должен вернуть ошибку 400 при попытке удаления с невалидным ID")
    @Test
    public void removeDynamicRuleReturnBadRequestWhenIdIsInvalid() throws Exception {
        mockMvc.perform(delete("/invalid-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}


