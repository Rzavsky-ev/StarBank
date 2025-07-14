package org.skypro.starbank.model.mapper;

import org.mapstruct.Mapper;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.RequestType;
import org.skypro.starbank.model.recommendation.RequestTypeDTO;

/**
 * Маппер для преобразования между сущностью {@link RequestType} и DTO {@link RequestTypeDTO}.
 * <p>
 * Реализует одностороннее преобразование из сущности в DTO.
 * <p>
 * Использует Spring в качестве модели компонента, что позволяет внедрять маппер как Spring-бин.
 */
@Mapper(componentModel = "spring")
public interface RequestTypeMapper {

    /**
     * Преобразует сущность {@link RequestType} в транспортный объект {@link RequestTypeDTO}.
     * <p>
     * Автоматически маппит все поля с одинаковыми именами. Для кастомного маппинга полей
     * следует использовать аннотацию {@code @Mapping}.
     *
     * @param entity сущность типа запроса для преобразования
     * @return транспортный объект типа запроса
     */
    RequestTypeDTO toDTO(RequestType entity);

}
