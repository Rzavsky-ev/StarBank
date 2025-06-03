package org.skypro.starbank.model.dynamicRule.dynamicRuleRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class DynamicRuleRequest {

    @NotNull
    @JsonProperty("product_name")
    private String productName;

    @NotNull
    @JsonProperty("product_id")
    private UUID productId;

    @NotNull
    @JsonProperty("product_text")
    private String productText;

    @NotEmpty
    @JsonProperty("rule")
    private List<RuleDTO> rule;

    @Data
    public static class RuleDTO {

        @NotNull
        @JsonProperty("query")
        private QueryType query;

        @NotEmpty
        @JsonProperty("arguments")
        private List<String> arguments;

        @JsonProperty("negate")
        private Boolean negate;

    }
}
