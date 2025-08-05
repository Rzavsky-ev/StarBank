package org.skypro.starbank.model.mapper;

import lombok.Data;

/**
 * Data Transfer Object (DTO) для предоставления информации о сервисе.
 * <p>
 * Используется для передачи клиенту основных метаданных о сервисе,
 * таких как название и версия приложения.
 */
@Data
public class ServiceInfoDto {
    private String name;
    private String version;
}