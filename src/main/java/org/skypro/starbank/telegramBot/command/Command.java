package org.skypro.starbank.telegramBot.command;

import com.pengrad.telegrambot.request.SendMessage;

/**
 * Интерфейс команд Telegram бота.
 * Определяет базовый контракт для всех команд бота.
 */
public interface Command {

    /**
     * Выполняет основную логику команды.
     *
     * @param chatId идентификатор чата, в который будет отправлен ответ
     * @return объект SendMessage с подготовленным ответом для пользователя
     */
    SendMessage execute(Long chatId);

    /**
     * Возвращает имя команды.
     *
     * @return элемент перечисления NamesCommand, соответствующий данной команде
     * @see NamesCommand
     */
    NamesCommand getNameCommand();
}
