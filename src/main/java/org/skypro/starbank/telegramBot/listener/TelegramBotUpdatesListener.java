package org.skypro.starbank.telegramBot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.skypro.starbank.telegramBot.command.Command;
import org.skypro.starbank.telegramBot.command.NamesCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import jakarta.annotation.PostConstruct;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;

    private final Map<NamesCommand, Command> commands;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, List<Command> commandList) {
        this.telegramBot = telegramBot;
        this.commands = commandList.stream()
                .collect(Collectors.toMap(
                        Command::getNameCommand,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
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
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}

