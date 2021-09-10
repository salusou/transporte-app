package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.PositionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Positions} and its DTO {@link PositionsDTO}.
 */
@Mapper(componentModel = "spring", uses = { AffiliatesMapper.class })
public interface PositionsMapper extends EntityMapper<PositionsDTO, Positions> {
    @Mapping(target = "affiliates", source = "affiliates", qualifiedByName = "branchName")
    PositionsDTO toDto(Positions s);

    @Named("positionName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "positionName", source = "positionName")
    PositionsDTO toDtoPositionName(Positions positions);
}
