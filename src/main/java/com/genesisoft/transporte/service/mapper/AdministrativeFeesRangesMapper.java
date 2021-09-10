package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.AdministrativeFeesRangesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdministrativeFeesRanges} and its DTO {@link AdministrativeFeesRangesDTO}.
 */
@Mapper(componentModel = "spring", uses = { AffiliatesMapper.class })
public interface AdministrativeFeesRangesMapper extends EntityMapper<AdministrativeFeesRangesDTO, AdministrativeFeesRanges> {
    @Mapping(target = "affiliates", source = "affiliates", qualifiedByName = "branchName")
    AdministrativeFeesRangesDTO toDto(AdministrativeFeesRanges s);
}
