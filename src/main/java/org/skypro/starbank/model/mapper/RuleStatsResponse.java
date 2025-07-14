package org.skypro.starbank.model.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Класс-обертка для предоставления статистики по правилам.
 * <p>
 * Содержит список объектов статистики {@link RuleStat} с количеством срабатываний каждого правила.
 * Используется для передачи данных о статистике правил между слоями приложения.
 */
@Data
@RequiredArgsConstructor
public class RuleStatsResponse {

    /**
     * Список объектов статистики по правилам.
     * <p>
     * Каждый элемент списка содержит идентификатор правила и количество его срабатываний.
     * Поле обязательно для заполнения (final).
     */
    private final List<RuleStat> stats;

    /**
     * Вложенный класс, представляющий статистику по одному правилу.
     * <p>
     * Содержит идентификатор правила и количество его применений.
     */
    @Data
    @AllArgsConstructor
    public static class RuleStat {
        /**
         * Идентификатор правила.
         * <p>
         * Формат и содержание идентификатора зависят от бизнес-логики приложения.
         */
        private String rule_id;
        /**
         * Количество срабатываний/применений данного правила.
         * <p>
         * Значение должно быть неотрицательным. null не допускается.
         */
        private Long count;
    }
}

