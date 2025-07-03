package org.skypro.starbank.telegramBot.command;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum NamesCommand {

    RECOMMEND_COMMAND
            ("/recommend username",
                    "Выдает рекомендацию");

    private final String nameCommand;

    private final String descriptionCommand;

    NamesCommand(String nameCommand, String descriptionCommand) {
        this.nameCommand = nameCommand;
        this.descriptionCommand = descriptionCommand;
    }

    public static Optional<NamesCommand> fromString(String text) {
        return Arrays.stream(values())
                .filter(nc -> nc.nameCommand.equalsIgnoreCase(text))
                .findFirst();
    }
}
