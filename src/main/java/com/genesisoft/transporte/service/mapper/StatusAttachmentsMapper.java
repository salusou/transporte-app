package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.StatusAttachmentsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StatusAttachments} and its DTO {@link StatusAttachmentsDTO}.
 */
@Mapper(componentModel = "spring", uses = { AffiliatesMapper.class })
public interface StatusAttachmentsMapper extends EntityMapper<StatusAttachmentsDTO, StatusAttachments> {
    @Mapping(target = "affiliates", source = "affiliates", qualifiedByName = "branchName")
    StatusAttachmentsDTO toDto(StatusAttachments s);

    @Named("statusName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "statusName", source = "statusName")
    StatusAttachmentsDTO toDtoStatusName(StatusAttachments statusAttachments);
}
