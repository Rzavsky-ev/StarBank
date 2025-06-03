package org.skypro.starbank.model.recommendation;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class RuleRecommendationDTO implements RecommendationDTO {
    private UUID id;
    private String name;
    private String description;

    public RuleRecommendationDTO(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
