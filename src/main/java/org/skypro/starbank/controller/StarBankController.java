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

@RestController
public class StarBankController {

    private final StarBankService starBankService;
    private final DynamicRuleRecommendationService dynamicRuleRecommendationService;

    public StarBankController(StarBankService starBankService,
                              DynamicRuleRecommendationService dynamicRuleRecommendationService) {
        this.starBankService = starBankService;
        this.dynamicRuleRecommendationService = dynamicRuleRecommendationService;
    }

    @GetMapping("/recommendation/{id}")
    public List<RecommendationDTO> getRecommendation(@PathVariable("id") UUID id) {
        List<RecommendationDTO> recommendation = new ArrayList<>();
        recommendation.addAll(starBankService.defineRecommendations(id));
        recommendation.addAll(dynamicRuleRecommendationService.checkUserAgainstAllDynamicRules(id));
        return recommendation;
    }


}


