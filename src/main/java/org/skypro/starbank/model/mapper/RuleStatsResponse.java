package org.skypro.starbank.model.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@RequiredArgsConstructor
public class RuleStatsResponse {
    private final List<RuleStat> stats;

    @Data
    @AllArgsConstructor
    public static class RuleStat {
        private String rule_id;
        private Long count;
    }
}

