package org.skypro.starbank.model.recommendation;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) для передачи данных о рекомендациях правил.
 * <p>
 * Реализует интерфейс {@link RecommendationDTO} и содержит базовую информацию
 * о рекомендации продукта для отображения клиенту.
 */
@Setter
@Getter
public class RuleRecommendationDTO implements RecommendationDTO {
    private UUID id;
    private String name;
    private String description;

    /**
     * Конструктор с параметрами для создания DTO.
     *
     * @param id          уникальный идентификатор рекомендации
     * @param name        название рекомендуемого продукта
     * @param description детальное описание рекомендации
     */
    public RuleRecommendationDTO(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
