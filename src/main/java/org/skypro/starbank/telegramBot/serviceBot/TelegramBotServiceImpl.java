package org.skypro.starbank.telegramBot.serviceBot;

import org.skypro.starbank.exception.ClientNotFoundException;
import org.skypro.starbank.model.recommendation.RecommendationDTO;
import org.skypro.starbank.telegramBot.repositoryBot.ClientRepository;
import org.skypro.starbank.service.DynamicRuleRecommendationService;
import org.skypro.starbank.service.StarBankService;
import org.skypro.starbank.telegramBot.modelBot.Clients;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TelegramBotServiceImpl implements TelegramBotService {

    private final StarBankService starBankService;
    private final DynamicRuleRecommendationService dynamicRuleRecommendationService;
    private final ClientRepository clientRepository;

    public TelegramBotServiceImpl(StarBankService starBankService,
                                  DynamicRuleRecommendationService dynamicRuleRecommendationService,
                                  ClientRepository clientRepository) {
        this.starBankService = starBankService;
        this.dynamicRuleRecommendationService = dynamicRuleRecommendationService;
        this.clientRepository = clientRepository;
    }

    @Transactional
    @Override
    public String getFirstName(Long chatId) throws ClientNotFoundException {
        return getClient(chatId).getFirstName();
    }

    @Transactional
    @Override
    public String getLastName(Long chatId) throws ClientNotFoundException {
        return getClient(chatId).getLastName();
    }

    @Transactional
    @Override
    public List<RecommendationDTO> getBotRecommendation(Long chatId) {
        UUID id = getClient(chatId).getId();
        List<RecommendationDTO> recommendation = new ArrayList<>();
        recommendation.addAll(starBankService.defineRecommendations(id));
        recommendation.addAll(dynamicRuleRecommendationService.checkUserAgainstAllDynamicRules(id));
        return recommendation;
    }

    private Clients getClient(Long chatId) {
        Clients client = clientRepository.getReferenceById(chatId);
        if (client == null) {
            throw new ClientNotFoundException("В базе данных нет такого клиента");
        }
        return client;
    }

}
