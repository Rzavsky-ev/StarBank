package org.skypro.starbank.configuration;

import org.springframework.boot.actuate.info.BuildInfoContributor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурационный класс для настройки информации о сборке приложения.
 * Предоставляет данные о версии, времени сборки и другой информации через Actuator.
 */
@Configuration
public class AppInfoConfig {

    /**
     * Создает билд-информацию для отображения в Actuator (/actuator/info).
     * Использует свойства сборки из Maven/Gradle для предоставления информации:
     * - версия приложения
     * - время сборки
     * - артефакт и группа
     * - другие свойства из build-info.properties
     *
     * @param buildProperties автоматически внедряемые свойства сборки
     * @return компонент BuildInfoContributor для интеграции со Spring Boot Actuator
     */
    @Bean
    public BuildInfoContributor customBuildInfoContributor(BuildProperties buildProperties) {
        return new BuildInfoContributor(buildProperties);
    }
}
