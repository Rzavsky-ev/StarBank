package org.skypro.starbank.controller;

import org.skypro.starbank.model.mapper.ServiceInfoDto;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для управления и мониторинга сервиса.
 * Предоставляет endpoint для получения информации о версии и названии сервиса.
 */
@RestController
@RequestMapping("/management")
public class ManagementController {

    private final BuildProperties buildProperties;

    /**
     * Конструктор контроллера.
     *
     * @param buildProperties свойства сборки приложения,
     *                        автоматически внедряемые Spring Boot
     */
    public ManagementController(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    /**
     * Возвращает основную информацию о сервисе.
     * Информация включает название и версию сервиса,
     * которые берутся из данных сборки приложения.
     *
     * @return DTO с информацией о сервисе (название и версия)
     */
    @GetMapping("/info")
    public ServiceInfoDto getServiceInfo() {
        ServiceInfoDto info = new ServiceInfoDto();
        info.setName(buildProperties.getName());
        info.setVersion(buildProperties.getVersion());
        return info;
    }
}