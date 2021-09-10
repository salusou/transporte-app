package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.StatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Status} and its DTO {@link StatusDTO}.
 */
@Mapper(componentModel = "spring", uses = { AffiliatesMapper.class })
public interface StatusMapper extends EntityMapper<StatusDTO, Status> {
    @Mapping(target = "affiliates", source = "affiliates", qualifiedByName = "branchName")
    StatusDTO toDto(Status s);

    @Named("statusName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "statusName", source = "statusName")
    StatusDTO toDtoStatusName(Status status);
}
