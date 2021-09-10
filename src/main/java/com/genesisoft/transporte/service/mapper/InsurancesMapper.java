package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.InsurancesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Insurances} and its DTO {@link InsurancesDTO}.
 */
@Mapper(componentModel = "spring", uses = { AffiliatesMapper.class, StateProvincesMapper.class })
public interface InsurancesMapper extends EntityMapper<InsurancesDTO, Insurances> {
    @Mapping(target = "affiliates", source = "affiliates", qualifiedByName = "branchName")
    @Mapping(target = "toStateProvince", source = "toStateProvince", qualifiedByName = "stateName")
    @Mapping(target = "forStateProvince", source = "forStateProvince", qualifiedByName = "stateName")
    InsurancesDTO toDto(Insurances s);
}
