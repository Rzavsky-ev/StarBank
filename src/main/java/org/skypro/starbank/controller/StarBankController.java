package org.skypro.starbank.controller;

import org.skypro.starbank.model.recommendation.RecommendationDTO;
import org.skypro.starbank.service.StarBankService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class StarBankController {

    private final StarBankService starBankService;

    public StarBankController(StarBankService starBankService) {
        this.starBankService = starBankService;
    }

    @GetMapping("/recommendation/{id}")
    public List<RecommendationDTO> getRecommendation(@PathVariable("id") UUID id) {
        return starBankService.defineRecommendations(id);
    }

}


