package org.skypro.starbank.model.mapper;

import org.mapstruct.Mapper;
import org.skypro.starbank.model.recommendation.Recommendation;
import org.skypro.starbank.model.recommendation.RuleRecommendationDTO;

/**
 * Маппер для преобразования объектов рекомендаций между сущностью и DTO.
 * <p>
 * Обеспечивает конвертацию объектов типа {@link Recommendation} в транспортные объекты
 * типа {@link RuleRecommendationDTO} для безопасной передачи данных между слоями приложения.
 * <p>
 * Регистрируется как Spring-бин благодаря указанию componentModel = "spring".
 */
@Mapper(componentModel = "spring")
public interface RuleRecommendationMapper {

    /**
     * Преобразует сущность рекомендации в соответствующий DTO объект.
     * <p>
     * Выполняет автоматическое отображение всех полей с одинаковыми именами.
     * Для нестандартных преобразований можно добавить аннотацию {@code @Mapping}.
     *
     * @param entity сущность рекомендации из базы данных
     * @return DTO объект рекомендации, готовый для передачи клиенту
     * @see org.mapstruct.Mapping
     */
    RuleRecommendationDTO toDTO(Recommendation entity);
}
