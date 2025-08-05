package org.skypro.starbank.model.recommendation;

import lombok.Getter;
import lombok.Setter;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.QueryType;

import java.util.List;

/**
 * Data Transfer Object (DTO) для передачи данных о типе запроса/условии правила.
 * <p>
 * Используется для сериализации/десериализации условий динамических правил
 * при передаче между слоями приложения и клиентом.
 */
@Getter
@Setter
public class RequestTypeDTO {
    private Long id;
    private QueryType query;
    private List<String> arguments;
    private boolean negate;

}
