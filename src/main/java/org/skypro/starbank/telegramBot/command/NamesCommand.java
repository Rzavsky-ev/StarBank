package org.skypro.starbank.telegramBot.command;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * Перечисление команд Telegram бота с их описаниями.
 * Каждая команда содержит:
 * <ul>
 *   <li>Формат команды (с синтаксисом)</li>
 *   <li>Описание назначения команды</li>
 * </ul>
 */
@Getter
public enum NamesCommand {

    RECOMMEND_COMMAND
            ("/recommend username",
                    "Выдает рекомендацию");

    private final String nameCommand;

    private final String descriptionCommand;

    /**
     * Конструктор перечисления.
     *
     * @param nameCommand        текстовое представление команды
     * @param descriptionCommand описание команды
     */
    NamesCommand(String nameCommand, String descriptionCommand) {
        this.nameCommand = nameCommand;
        this.descriptionCommand = descriptionCommand;
    }

    /**
     * Поиск команды по текстовому представлению.
     *
     * @param text текст команды для поиска
     * @return {@link Optional} с найденной командой, если соответствие найдено,
     * или пустой {@link Optional}, если соответствия нет
     */
    public static Optional<NamesCommand> fromString(String text) {
        return Arrays.stream(values())
                .filter(nc -> nc.nameCommand.equalsIgnoreCase(text))
                .findFirst();
    }
}
