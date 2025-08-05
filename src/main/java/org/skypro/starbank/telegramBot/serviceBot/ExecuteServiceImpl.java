package org.skypro.starbank.telegramBot.serviceBot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.skypro.starbank.telegramBot.command.Command;
import org.skypro.starbank.telegramBot.command.NamesCommand;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Сервисный класс для обработки и выполнения команд Telegram-бота.
 * Обеспечивает выполнение команд, полученных от пользователя, и отправку ответов.
 */
@Service
public class ExecuteServiceImpl implements ExecuteService {

    private final TelegramBot telegramBot;

    private final Map<NamesCommand, Command> commands;

    /**
     * Конструктор класса, инициализирующий TelegramBot и доступные команды.
     *
     * @param telegramBot экземпляр TelegramBot для взаимодействия с Telegram API
     * @param commandList список доступных команд, которые будут зарегистрированы в сервисе
     */
    public ExecuteServiceImpl(TelegramBot telegramBot, List<Command> commandList) {
        this.telegramBot = telegramBot;
        this.commands = commandList.stream()
                .collect(Collectors.toMap(
                        Command::getNameCommand,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));
    }

    /**
     * Основной метод выполнения команд.
     * Обрабатывает входящее обновление от Telegram, извлекает команду и выполняет соответствующее действие.
     * Если команда неизвестна, отправляет сообщение об ошибке.
     *
     * @param update объект Update, содержащий информацию о входящем сообщении/команде
     */
    @Override
    public void execute(Update update) {
        if (update.message() != null && update.message().text() != null) {
            String text = update.message().text();
            Long chatId = update.message().chat().id();
            if (text.equals("/recommend username")) {
                NamesCommand namesCommand = NamesCommand.fromString(text).
                        orElse(null);
                Command command = commands.get(namesCommand);
                telegramBot.execute(command.execute(chatId));
            } else {
                telegramBot.execute(new SendMessage(chatId, "Неизвестная команда"));
            }
        }
    }
}
