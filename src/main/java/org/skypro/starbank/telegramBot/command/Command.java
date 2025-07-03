package org.skypro.starbank.telegramBot.command;

import com.pengrad.telegrambot.request.SendMessage;

public interface Command {

    SendMessage execute(Long chatId);

    NamesCommand getNameCommand();
}
