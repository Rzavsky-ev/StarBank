package org.skypro.starbank.telegramBot.modelBot;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Entity
@Table(name = "clients")
public class Clients {

    @Id
    @Column(name = "chatId", nullable = false)
    private Long chatId;

    @Column (name = "id", nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    public Clients() {
    }
}


