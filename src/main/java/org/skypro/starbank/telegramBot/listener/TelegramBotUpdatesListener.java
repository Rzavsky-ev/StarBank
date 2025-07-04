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

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;

    private final ExecuteServiceImpl executeService;

    private final Map<NamesCommand, Command> commands;

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

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            executeService.execute(update);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}

