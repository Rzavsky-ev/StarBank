package org.skypro.starbank.model.recommendation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) для передачи данных о рекомендациях,
 * основанных на динамических правилах.
 * <p>
 * Реализует интерфейс {@link RecommendationDTO} и содержит информацию о продукте
 * и условиях правил, которые привели к рекомендации.
 */
@Getter
@Setter
public class DynamicRuleRecommendationDTO implements RecommendationDTO {
    private Long id;
    private String productName;
    private UUID productId;
    private String productText;
    private List<RequestTypeDTO> ruleConditions;
}
