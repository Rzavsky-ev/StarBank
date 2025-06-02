package org.skypro.starbank.model.recommendation;

import lombok.Getter;
import lombok.Setter;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.QueryType;

import java.util.List;

@Getter
@Setter
public class RequestTypeDTO {
    private Long id;
    private QueryType query;
    private List<String> arguments;
    private boolean negate;

}
