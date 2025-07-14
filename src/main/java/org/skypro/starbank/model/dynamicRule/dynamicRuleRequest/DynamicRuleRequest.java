package org.skypro.starbank.model.dynamicRule.dynamicRuleRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * Класс запроса для создания/обновления динамического правила.
 * Содержит информацию о продукте и список правил для применения.
 */
@Data
public class DynamicRuleRequest {

    /**
     * Название продукта, к которому применяются правила.
     * Не может быть null.
     */
    @NotNull
    @JsonProperty("product_name")
    private String productName;

    /**
     * Уникальный идентификатор продукта в формате UUID.
     * Не может быть null.
     */
    @NotNull
    @JsonProperty("product_id")
    private UUID productId;

    /**
     * Текстовое описание продукта.
     * Не может быть null.
     */
    @NotNull
    @JsonProperty("product_text")
    private String productText;

    /**
     * Список правил, применяемых к продукту.
     * Список не может быть пустым.
     */
    @NotEmpty
    @JsonProperty("rule")
    private List<RuleDTO> rule;

    /**
     * Вложенный класс, представляющий отдельное правило.
     */
    @Data
    public static class RuleDTO {

        /**
         * Тип запроса/условия для правила.
         * Не может быть null.
         */
        @NotNull
        @JsonProperty("query")
        private QueryType query;

        /**
         * Список аргументов для условия правила.
         * Список не может быть пустым.
         */
        @NotEmpty
        @JsonProperty("arguments")
        private List<String> arguments;

        /**
         * Флаг, указывающий на необходимость инвертировать условие правила.
         * Если true, условие правила будет инвертировано.
         * Необязательное поле (по умолчанию false).
         */
        @JsonProperty("negate")
        private Boolean negate;

    }
}
