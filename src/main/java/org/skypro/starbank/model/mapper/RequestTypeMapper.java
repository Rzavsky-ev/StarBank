package org.skypro.starbank.model.mapper;

import org.mapstruct.Mapper;
import org.skypro.starbank.model.dynamicRule.dynamicRuleRequest.RequestType;
import org.skypro.starbank.model.recommendation.RequestTypeDTO;

@Mapper(componentModel = "spring")
public interface RequestTypeMapper {
    RequestTypeDTO toDTO(RequestType entity);

}
