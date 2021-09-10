package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.CostCenterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CostCenter} and its DTO {@link CostCenterDTO}.
 */
@Mapper(componentModel = "spring", uses = { AffiliatesMapper.class })
public interface CostCenterMapper extends EntityMapper<CostCenterDTO, CostCenter> {
    @Mapping(target = "affiliates", source = "affiliates", qualifiedByName = "branchName")
    CostCenterDTO toDto(CostCenter s);

    @Named("costCenterName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "costCenterName", source = "costCenterName")
    CostCenterDTO toDtoCostCenterName(CostCenter costCenter);
}
