package org.skypro.starbank.telegramBot.repositoryBot;

import org.skypro.starbank.telegramBot.modelBot.Clients;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью {@link Clients} в базе данных.
 *
 * <p>Предоставляет стандартные CRUD-операции для работы с клиентами Telegram бота,
 * а также все методы, определенные в {@link JpaRepository}.</p>
 *
 * <p>Использует {@link Long} в качестве типа идентификатора,
 * что соответствует типу поля chatId в сущности {@link Clients}.</p>
 */
public interface ClientRepository extends JpaRepository<Clients, Long> {
}
