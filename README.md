# Bank Recommendation Service

[![Java Version](https://img.shields.io/badge/Java-17%2B-blue)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1-green)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-yellow)](https://opensource.org/licenses/MIT)

Сервис для персонифицированных рекомендаций банковских продуктов на основе анализа транзакций пользователей.

## 🔍 Основные возможности

- **Динамические правила рекомендаций** (CRUD API)
- **Интеграция с Telegram-ботом**
- **Кеширование результатов** (Caffeine)
- **Статистика срабатываний правил**
- **Поддержка legacy-правил** из технического задания

## 📚 Документация

| Документ                          | Описание                                  |
|-----------------------------------|------------------------------------------|
| [Архитектура приложения](docs/ARCHITECTURE.md) | Диаграммы компонентов и алгоритмов       |
| [REST API](docs/api/openapi.yaml) | OpenAPI-спецификация                     |
| [Развертывание](docs/DEPLOYMENT.md) | Требования к инфраструктуре и настройки  |
| [User Stories](docs/REQUIREMENTS.md) | Функциональные и нефункциональные требования |

## 🚀 Быстрый старт

### Требования
- Java 17+
- PostgreSQL 12+
- Maven 3.8+

### Запуск в development-режиме
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Сборка и запуск
```bash
mvn clean package
```

## Конфигурация
### Основные настройки (через переменные среды):
#### PostgreSQL
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/recommendation_db
SPRING_DATASOURCE_USERNAME=Eduard_Rz
SPRING_DATASOURCE_PASSWORD=EdTelegramBot

##### H2 (только чтение)
SPRING_DATASOURCE_H2_URL=jdbc:h2:file:./transaction.mv.db


## API Endpoints
Swagger UI: http://localhost:8080/swagger-ui.html