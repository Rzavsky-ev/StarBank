package org.skypro.starbank.model.recommendation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
public class DynamicRuleRecommendationDTO implements RecommendationDTO {
    private Long id;
    private String productName;
    private UUID productId;
    private String productText;
    private List<RequestTypeDTO> ruleConditions;
}
