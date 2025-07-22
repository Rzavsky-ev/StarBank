# Диаграмма вариантов использования системы рекомендаций

```mermaid
useCaseDiagram
    title Рекомендательная система банка "Стар"
    left to right direction

    actor Клиент as client
    actor СотрудникБанка as employee
    actor ТелеграмБот as bot
    actor Администратор as admin

    rectangle Система {
        usecase "Получить рекомендации" as get_recommendations
        usecase "Управлять динамическими правилами" as manage_rules
        usecase "Просматривать статистику" as view_stats
        usecase "Сбросить кеш" as clear_cache
        usecase "Получить информацию о сервисе" as get_info

        client --> get_recommendations
        employee --> get_recommendations
        bot --> get_recommendations
        admin --> manage_rules
        admin --> view_stats
        admin --> clear_cache
        admin --> get_info
        employee --> get_info
    }

    note right of admin: Администратор управляет\nправилами и мониторит\nработу системы