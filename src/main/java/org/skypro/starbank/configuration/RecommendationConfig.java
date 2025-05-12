package org.skypro.starbank.configuration;

import org.skypro.starbank.model.recommendation.Recommendation;
import org.skypro.starbank.model.recommendation.RecommendationDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecommendationConfig {

    @Bean(name = "invest500Dto")
    public RecommendationDTO invest500Dto() {
        return Recommendation.INVEST_500.toDto();
    }

    @Bean(name = "simpleCreditDto")
    public RecommendationDTO simpleCreditDto() {
        return Recommendation.SIMPLE_CREDIT.toDto();
    }

    @Bean(name = "topSavingDto")
    public RecommendationDTO topSavingDto() {
        return Recommendation.TOP_SAVING.toDto();
    }
}

