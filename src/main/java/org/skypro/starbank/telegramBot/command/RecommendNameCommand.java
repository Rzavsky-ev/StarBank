package org.skypro.starbank.telegramBot.command;

import com.pengrad.telegrambot.request.SendMessage;
import org.skypro.starbank.exception.ClientNotFoundException;
import org.skypro.starbank.telegramBot.serviceBot.TelegramBotServiceImpl;
import org.springframework.stereotype.Component;

/**
 * Реализация команды для получения персональных рекомендаций.
 * Формирует приветственное сообщение с рекомендациями продуктов для пользователя.
 *
 * <p>Компонент Spring, автоматически регистрируется в контексте приложения.</p>
 */
@Component
public class RecommendNameCommand implements Command {

    private final TelegramBotServiceImpl botService;

    /**
     * Конструктор с внедрением зависимости сервиса бота.
     *
     * @param botService сервис для взаимодействия с Telegram API
     */
    public RecommendNameCommand(TelegramBotServiceImpl botService) {
        this.botService = botService;
    }

    /**
     * Выполняет команду рекомендации продуктов.
     * Формирует персонализированное сообщение с рекомендациями.
     *
     * <p>В случае успеха возвращает сообщение формата:<br>
     * "Здравствуйте, [Имя] [Фамилия]. Новые продукты для вас: [рекомендации]."</p>
     *
     * <p>При отсутствии пользователя возвращает сообщение об ошибке.</p>
     *
     * @param chatId идентификатор чата в Telegram
     * @return SendMessage сформированное сообщение для отправки
     * @see TelegramBotServiceImpl#getFirstName(Long)
     * @see TelegramBotServiceImpl#getLastName(Long)
     * @see TelegramBotServiceImpl#getBotRecommendation(Long)
     */
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

    /**
     * Возвращает имя команды.
     *
     * @return элемент перечисления NamesCommand.RECOMMEND_COMMAND
     */
    @Override
    public NamesCommand getNameCommand() {
        return NamesCommand.RECOMMEND_COMMAND;
    }
}
