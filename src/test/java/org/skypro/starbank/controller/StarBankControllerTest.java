package org.skypro.starbank.controller;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skypro.starbank.model.recommendation.RuleRecommendationDTO;
import org.skypro.starbank.model.recommendation.DynamicRuleRecommendationDTO;
import org.skypro.starbank.service.DynamicRuleRecommendationService;
import org.skypro.starbank.service.StarBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StarBankController.class)
public class StarBankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StarBankService starBankService;

    @MockitoBean
    private DynamicRuleRecommendationService dynamicRuleRecommendationService;

    @DisplayName("Должен возвращать объединенные рекомендации (базовые и динамические) для пользователя")
    @Test
    public void getRecommendationReturnCombinedRecommendations() throws Exception {

        UUID userId = UUID.randomUUID();
        UUID recommendation1Id = UUID.randomUUID();
        UUID recommendation2Id = UUID.randomUUID();

        RuleRecommendationDTO basicRecommendation1 = new RuleRecommendationDTO
                (recommendation1Id, "Name1", "Description 1");
        RuleRecommendationDTO basicRecommendation2 = new RuleRecommendationDTO
                (recommendation2Id, "Name2", "Description 2");
        List<RuleRecommendationDTO> basicRecommendations = Arrays.asList(basicRecommendation1, basicRecommendation2);

        DynamicRuleRecommendationDTO dynamicRecommendation1 = new DynamicRuleRecommendationDTO();
        dynamicRecommendation1.setId(1L);
        DynamicRuleRecommendationDTO dynamicRecommendation2 = new DynamicRuleRecommendationDTO();
        dynamicRecommendation2.setId(2L);
        List<DynamicRuleRecommendationDTO> dynamicRecommendations = Arrays.asList(dynamicRecommendation1, dynamicRecommendation2);

        when(starBankService.defineRecommendations(userId)).thenReturn(basicRecommendations);
        when(dynamicRuleRecommendationService.checkUserAgainstAllDynamicRules(userId)).thenReturn(dynamicRecommendations);

        mockMvc.perform(get("/recommendation/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))

                .andExpect(jsonPath("$[0].id").value(recommendation1Id.toString()))
                .andExpect(jsonPath("$[0].name").value("Name1"))
                .andExpect(jsonPath("$[0].description").value("Description 1"))
                .andExpect(jsonPath("$[1].id").value(recommendation2Id.toString()))
                .andExpect(jsonPath("$[1].name").value("Name2"))
                .andExpect(jsonPath("$[1].description").value("Description 2"))
                .andExpect(jsonPath("$[2].id").value(1))
                .andExpect(jsonPath("$[3].id").value(2));
    }

    @DisplayName("Должен возвращать пустой список, когда нет рекомендаций")
    @Test
    public void getRecommendationReturnEmptyListWhenNoRecommendations() throws Exception {

        UUID userId = UUID.randomUUID();

        when(starBankService.defineRecommendations(userId)).thenReturn(List.of());
        when(dynamicRuleRecommendationService.checkUserAgainstAllDynamicRules(userId)).thenReturn(List.of());

        mockMvc.perform(get("/recommendation/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @DisplayName("Должен возвращать только базовые рекомендации, когда динамических нет")
    @Test
    public void getRecommendationReturnOnlyBasicRecommendations() throws Exception {

        UUID userId = UUID.randomUUID();
        UUID recommendationId = UUID.randomUUID();

        RuleRecommendationDTO basicRecommendation = new RuleRecommendationDTO(recommendationId, "Name1", "Description");
        List<RuleRecommendationDTO> basicRecommendations = List.of(basicRecommendation);

        when(starBankService.defineRecommendations(userId)).thenReturn(basicRecommendations);
        when(dynamicRuleRecommendationService.checkUserAgainstAllDynamicRules(userId)).thenReturn(List.of());
        mockMvc.perform(get("/recommendation/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(recommendationId.toString()))
                .andExpect(jsonPath("$[0].name").value("Name1"))
                .andExpect(jsonPath("$[0].description").value("Description"));
    }

    @DisplayName("Должен возвращать только динамические рекомендации, когда базовых нет")
    @Test
    public void getRecommendationReturnOnlyDynamicRecommendations() throws Exception {

        UUID userId = UUID.randomUUID();

        DynamicRuleRecommendationDTO dynamicRecommendation = new DynamicRuleRecommendationDTO();
        dynamicRecommendation.setId(1L);
        List<DynamicRuleRecommendationDTO> dynamicRecommendations = List.of(dynamicRecommendation);

        when(starBankService.defineRecommendations(userId)).thenReturn(List.of());
        when(dynamicRuleRecommendationService.checkUserAgainstAllDynamicRules(userId)).thenReturn(dynamicRecommendations);

        mockMvc.perform(get("/recommendation/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].productName").isEmpty())
                .andExpect(jsonPath("$[0].productId").isEmpty())
                .andExpect(jsonPath("$[0].productText").isEmpty())
                .andExpect(jsonPath("$[0].ruleConditions").isEmpty());
    }

    @DisplayName("Должен возвращать статус BadRequest при неверном формате UUID")
    @Test
    public void getRecommendationHandleInvalidUUIDFormat() throws Exception {

        mockMvc.perform(get("/recommendation/{id}", "invalid-uuid"))
                .andExpect(status().isBadRequest());
    }
}
