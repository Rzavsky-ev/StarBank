package org.skypro.starbank.configuration;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Конфигурационный класс для настройки источников данных и JDBC-шаблонов.
 * Содержит конфигурацию для основного источника данных и дополнительного источника
 * для рекомендаций (read-only).
 */
@Configuration
public class DatabaseConfig {

    /**
     * Создает источник данных для сервиса рекомендаций.
     * Использует HikariCP как пул соединений и H2 в качестве СУБД.
     * Источник данных работает в режиме только для чтения.
     *
     * @param recommendationsUrl URL для подключения к БД рекомендаций,
     *                           берется из свойства application.recommendations-db.url
     * @return настроенный источник данных для рекомендаций
     */
    @Bean(name = "recommendationsDataSource")
    public DataSource recommendationsDataSource(@Value("${application.recommendations-db.url}") String recommendationsUrl) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(recommendationsUrl);
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setReadOnly(true);
        return dataSource;
    }

    /**
     * Создает JDBC-шаблон для работы с источником данных рекомендаций.
     *
     * @param dataSource источник данных рекомендаций,
     *                   должен быть квалифицирован как "recommendationsDataSource"
     * @return настроенный JdbcTemplate для работы с БД рекомендаций
     */
    @Bean(name = "recommendationsJdbcTemplate")
    public JdbcTemplate recommendationsJdbcTemplate(
            @Qualifier("recommendationsDataSource") DataSource dataSource
    ) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * Создает основной источник данных приложения.
     * Использует настройки из конфигурации Spring Boot (application.properties/yml).
     * Помечен как @Primary, чтобы использоваться по умолчанию при автосвязывании.
     *
     * @param properties свойства источника данных из Spring Boot
     * @return основной источник данных приложения
     */
    @Primary
    @Bean(name = "defaultDataSource")
    public DataSource defaultDataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }
}
