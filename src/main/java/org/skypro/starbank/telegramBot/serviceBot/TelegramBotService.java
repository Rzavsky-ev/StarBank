package org.skypro.starbank.telegramBot.serviceBot;

import org.skypro.starbank.exception.ClientNotFoundException;
import org.skypro.starbank.model.recommendation.RecommendationDTO;

import java.util.List;

public interface TelegramBotService {
    String getFirstName(Long chatId) throws ClientNotFoundException;

    String getLastName(Long chatId) throws ClientNotFoundException;

    List<RecommendationDTO> getBotRecommendation(Long chatId);
}
