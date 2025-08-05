package org.skypro.starbank.telegramBot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.skypro.starbank.telegramBot.command.Command;
import org.skypro.starbank.telegramBot.command.NamesCommand;
import org.skypro.starbank.telegramBot.serviceBot.ExecuteServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import jakarta.annotation.PostConstruct;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Основной слушатель обновлений Telegram бота.
 * Обрабатывает входящие сообщения и делегирует выполнение команд сервису.
 *
 * <p>Реализует интерфейс {@link UpdatesListener} для получения обновлений от Telegram API.</p>
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;

    private final ExecuteServiceImpl executeService;

    private final Map<NamesCommand, Command> commands;

    /**
     * Конструктор с внедрением зависимостей.
     *
     * @param telegramBot    экземпляр Telegram бота
     * @param commandList    список всех доступных команд
     * @param executeService сервис для выполнения команд
     */
    public TelegramBotUpdatesListener(TelegramBot telegramBot,
                                      List<Command> commandList,
                                      ExecuteServiceImpl executeService) {
        this.telegramBot = telegramBot;
        this.executeService = executeService;
        this.commands = commandList.stream()
                .collect(Collectors.toMap(
                        Command::getNameCommand,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));
    }

    /**
     * Инициализация слушателя после создания бина.
     * Устанавливает текущий экземпляр в качестве обработчика обновлений бота.
     */
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Обрабатывает список обновлений от Telegram API.
     *
     * <p>Для каждого обновления:
     * <ol>
     *   <li>Логирует полученное обновление</li>
     *   <li>Передает обновление на обработку сервису выполнения команд</li>
     * </ol>
     *
     * @param updates список полученных обновлений
     * @return константа CONFIRMED_UPDATES_ALL, подтверждающая обработку всех обновлений
     */
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            executeService.execute(update);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}

