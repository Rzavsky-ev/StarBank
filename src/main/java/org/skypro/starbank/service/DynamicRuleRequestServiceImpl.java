package org.skypro.starbank.service;

import org.skypro.starbank.cache.DynamicRuleCache;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRule;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.DynamicRuleRequest;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.RequestType;
import org.skypro.starbank.exception.DynamicRuleNotFoundInDatabaseException;
import org.skypro.starbank.exception.EmptyDatabaseException;
import org.skypro.starbank.exception.EmptyRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DynamicRuleRequestServiceImpl implements DynamicRuleRequestService {

    private final DynamicRuleCache dynamicRuleCache;

    private final CounterDynamicRuleServiceImpl counterDynamicRuleService;

    public DynamicRuleRequestServiceImpl(DynamicRuleCache dynamicRuleCache,
                                         CounterDynamicRuleServiceImpl counterDynamicRuleService) {
        this.dynamicRuleCache = dynamicRuleCache;
        this.counterDynamicRuleService = counterDynamicRuleService;
    }

    @Transactional
    public DynamicRule createDynamicRule(DynamicRuleRequest request) {
        if (request == null) {
            throw new EmptyRequestException("Пустой запрос");
        }
        DynamicRule dynamicRule = new DynamicRule();
        dynamicRule.setProductName(request.getProductName());
        dynamicRule.setProductId(request.getProductId());
        dynamicRule.setProductText(request.getProductText());
        List<RequestType> requestTypes = request.getRule().stream().map(
                dto -> {
                    RequestType requestType = new RequestType();
                    requestType.setQuery(dto.getQuery());
                    requestType.setArguments(dto.getArguments());
                    requestType.setNegate(dto.getNegate());
                    return requestType;
                }
        ).toList();
        dynamicRule.setRuleConditions(requestTypes);
        counterDynamicRuleService.createCounterDynamicRule(dynamicRule);
        return dynamicRuleCache.save(dynamicRule);
    }

    @Transactional
    public void removeDynamicRule(Long id) {
        DynamicRule rule = dynamicRuleCache.findById(id)
                .orElseThrow(() -> new DynamicRuleNotFoundInDatabaseException(
                        "Динамическое правило в базе данных не найдено"));
        dynamicRuleCache.deleteById(id);
    }

    @Transactional
    public List<DynamicRule> showAllDynamicRules() {
        List<DynamicRule> rules = dynamicRuleCache.findAll();
        if (rules.isEmpty()) {
            throw new EmptyDatabaseException("База данных пуста");
        }
        return rules;
    }

    @Transactional
    public void clearCaches() {
        dynamicRuleCache.invalidateAllCaches();
    }
}


