package org.skypro.starbank.telegramBot.configuration;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурационный класс для настройки Telegram бота.
 * Создает и настраивает экземпляр TelegramBot с использованием токена из свойств приложения.
 */
@Configuration
public class TelegramBotConfiguration {

    @Value("${telegram.bot.token}")
    private String token;

    /**
     * Создает и настраивает экземпляр Telegram бота.
     *
     * <p>Выполняет следующие действия:
     * <ol>
     *   <li>Создает экземпляр бота с указанным токеном</li>
     *   <li>Удаляет все ранее установленные команды бота</li>
     *   <li>Возвращает настроенный экземпляр бота</li>
     * </ol>
     *
     * @return настроенный экземпляр TelegramBot
     */
    @Bean
    public TelegramBot telegramBot() {
        TelegramBot bot = new TelegramBot(token);
        bot.execute(new DeleteMyCommands());
        return bot;
    }

}
