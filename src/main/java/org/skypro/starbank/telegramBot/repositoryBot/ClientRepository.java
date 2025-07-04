package org.skypro.starbank.telegramBot.repositoryBot;

import org.skypro.starbank.telegramBot.modelBot.Clients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Clients, Long> {
}
