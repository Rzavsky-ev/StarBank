package org.skypro.starbank.telegramBot.modelBot;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

/**
 * Сущность, представляющая клиента в системе.
 * Соответствует таблице "clients" в базе данных.
 *
 * <p>Содержит основную информацию о клиенте Telegram бота.</p>
 */
@Data
@Entity
@Table(name = "clients")
public class Clients {

    /**
     * Уникальный идентификатор чата в Telegram.
     * Является первичным ключом в таблице.
     */
    @Id
    @Column(name = "chatId", nullable = false)
    private Long chatId;

    /**
     * Уникальный идентификатор клиента в системе.
     */
    @Column(name = "id", nullable = false)
    private UUID id;

    /**
     * Имя клиента.
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * Фамилия клиента.
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    public Clients() {
    }
}


