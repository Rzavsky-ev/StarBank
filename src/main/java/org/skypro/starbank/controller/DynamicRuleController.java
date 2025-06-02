package org.skypro.starbank.controller;

import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRule;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRuleRequest;
import org.skypro.starbank.service.DynamicRuleRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DynamicRuleController {

    private final DynamicRuleRequestService dynamicRuleService;

    public DynamicRuleController(DynamicRuleRequestService dynamicRuleService) {
        this.dynamicRuleService = dynamicRuleService;
    }

    @PostMapping(path = "/rule")
    public DynamicRule createDynamicRule(@RequestBody DynamicRuleRequest dynamicRuleRequest) {
        return dynamicRuleService.createDynamicRule(dynamicRuleRequest);
    }

    @GetMapping(path = "/rule")
    public List<DynamicRule> showAllDynamicRules() {
        return dynamicRuleService.showAllDynamicRules();
    }

    @DeleteMapping("{id}")
    public void removeDynamicRule(@PathVariable("id") Long id) {
        dynamicRuleService.removeDynamicRule(id);
    }
}
