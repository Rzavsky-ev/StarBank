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

/**
 * Реализация сервиса для работы с Telegram-ботом.
 * Предоставляет методы для взаимодействия с клиентскими данными и генерации рекомендаций.
 */
@Service
public class TelegramBotServiceImpl implements TelegramBotService {

    private final StarBankService starBankService;
    private final DynamicRuleRecommendationService dynamicRuleRecommendationService;
    private final ClientRepository clientRepository;

    /**
     * Конструктор класса для внедрения зависимостей.
     *
     * @param starBankService                  сервис для работы с основными функциями банка
     * @param dynamicRuleRecommendationService сервис для работы с динамическими правилами рекомендаций
     * @param clientRepository                 репозиторий для работы с данными клиентов
     */
    public TelegramBotServiceImpl(StarBankService starBankService,
                                  DynamicRuleRecommendationService dynamicRuleRecommendationService,
                                  ClientRepository clientRepository) {
        this.starBankService = starBankService;
        this.dynamicRuleRecommendationService = dynamicRuleRecommendationService;
        this.clientRepository = clientRepository;
    }

    /**
     * Получает имя клиента по идентификатору чата.
     * Работает в транзакции для обеспечения целостности данных.
     *
     * @param chatId уникальный идентификатор чата с клиентом
     * @return имя клиента
     * @throws ClientNotFoundException если клиент с указанным chatId не найден
     */
    @Transactional
    @Override
    public String getFirstName(Long chatId) throws ClientNotFoundException {
        return getClient(chatId).getFirstName();
    }

    /**
     * Получает фамилию клиента по идентификатору чата.
     * Работает в транзакции для обеспечения целостности данных.
     *
     * @param chatId уникальный идентификатор чата с клиентом
     * @return фамилия клиента
     * @throws ClientNotFoundException если клиент с указанным chatId не найден
     */
    @Transactional
    @Override
    public String getLastName(Long chatId) throws ClientNotFoundException {
        return getClient(chatId).getLastName();
    }

    /**
     * Получает список рекомендаций для клиента.
     * Комбинирует рекомендации от основного сервиса банка и динамических правил.
     * Работает в транзакции для обеспечения целостности данных.
     *
     * @param chatId уникальный идентификатор чата с клиентом
     * @return список DTO с рекомендациями для клиента
     */
    @Transactional
    @Override
    public List<RecommendationDTO> getBotRecommendation(Long chatId) {
        UUID id = getClient(chatId).getId();
        List<RecommendationDTO> recommendation = new ArrayList<>();
        recommendation.addAll(starBankService.defineRecommendations(id));
        recommendation.addAll(dynamicRuleRecommendationService.checkUserAgainstAllDynamicRules(id));
        return recommendation;
    }

    /**
     * Вспомогательный метод для получения клиента по идентификатору чата.
     *
     * @param chatId уникальный идентификатор чата с клиентом
     * @return сущность клиента
     * @throws ClientNotFoundException если клиент с указанным chatId не найден
     */
    private Clients getClient(Long chatId) {
        Clients client = clientRepository.getReferenceById(chatId);
        if (client == null) {
            throw new ClientNotFoundException("В базе данных нет такого клиента");
        }
        return client;
    }

}
