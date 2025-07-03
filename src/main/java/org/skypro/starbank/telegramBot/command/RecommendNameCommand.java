package org.skypro.starbank.telegramBot.command;

import com.pengrad.telegrambot.request.SendMessage;
import org.skypro.starbank.exception.ClientNotFoundException;
import org.skypro.starbank.telegramBot.serviceBot.TelegramBotServiceImpl;
import org.springframework.stereotype.Component;


@Component
public class RecommendNameCommand implements Command {

    private final TelegramBotServiceImpl botService;

    public RecommendNameCommand(TelegramBotServiceImpl botService) {
        this.botService = botService;
    }

    @Override
    public SendMessage execute(Long chatId) {
        try {
            String sendMessage = "Здравствуйте, "
                    + botService.getFirstName(chatId) + " "
                    + botService.getLastName(chatId) + ". "
                    + "Новые продукты для вас: "
                    + botService.getBotRecommendation(chatId) + ".";
            return new SendMessage(chatId, sendMessage);
        } catch (ClientNotFoundException e) {
            String sendMessage = "Пользователь не найден";
            return new SendMessage(chatId, sendMessage);
        }
    }

    @Override
    public NamesCommand getNameCommand() {
        return NamesCommand.RECOMMEND_COMMAND;
    }
}
