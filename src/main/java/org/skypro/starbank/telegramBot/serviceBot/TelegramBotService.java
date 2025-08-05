package org.skypro.starbank.telegramBot.serviceBot;

import org.skypro.starbank.exception.ClientNotFoundException;
import org.skypro.starbank.model.recommendation.RecommendationDTO;

import java.util.List;

/**
 * Интерфейс сервиса для работы с Telegram-ботом.
 * Предоставляет методы для получения информации о клиенте и рекомендаций через Telegram-бота.
 */
public interface TelegramBotService {

    /**
     * Получает имя клиента по идентификатору чата.
     *
     * @param chatId уникальный идентификатор чата с клиентом
     * @return имя клиента
     */
    String getFirstName(Long chatId) throws ClientNotFoundException;

    /**
     * Получает фамилию клиента по идентификатору чата.
     *
     * @param chatId уникальный идентификатор чата с клиентом
     * @return фамилия клиента
     */
    String getLastName(Long chatId) throws ClientNotFoundException;

    /**
     * Получает список рекомендаций для клиента по идентификатору чата.
     *
     * @param chatId уникальный идентификатор чата с клиентом
     * @return список DTO с рекомендациями для клиента
     */
    List<RecommendationDTO> getBotRecommendation(Long chatId);
}
