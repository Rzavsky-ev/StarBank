package org.skypro.starbank.controller;

import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRule;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRuleRequest;
import org.skypro.starbank.model.mapper.RuleStatsResponse;
import org.skypro.starbank.service.CounterDynamicRuleService;
import org.skypro.starbank.service.DynamicRuleRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class DynamicRuleController {

    private final DynamicRuleRequestService dynamicRuleService;

    private final CounterDynamicRuleService counterDynamicRuleService;

    public DynamicRuleController(DynamicRuleRequestService dynamicRuleService,
                                 CounterDynamicRuleService counterDynamicRuleService) {
        this.dynamicRuleService = dynamicRuleService;
        this.counterDynamicRuleService = counterDynamicRuleService;
    }

    @PostMapping(path = "/rule")
    public DynamicRule createDynamicRule(@RequestBody DynamicRuleRequest dynamicRuleRequest) {
        return dynamicRuleService.createDynamicRule(dynamicRuleRequest);
    }

    @GetMapping(path = "/rule")
    public List<DynamicRule> showAllDynamicRules() {
        return dynamicRuleService.showAllDynamicRules();
    }

    @GetMapping(path = "/rule/stats")
    public RuleStatsResponse getStats() {
        return counterDynamicRuleService.getStats();
    }

    @PostMapping("/management/clear-caches")
    public void clearCaches() {
        dynamicRuleService.clearCaches();
    }

    @DeleteMapping("{id}")
    public void removeDynamicRule(@PathVariable("id") Long id) {
        dynamicRuleService.removeDynamicRule(id);
    }
}
