package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.AffiliatesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Affiliates} and its DTO {@link AffiliatesDTO}.
 */
@Mapper(componentModel = "spring", uses = { StateProvincesMapper.class, CitiesMapper.class, CompaniesMapper.class })
public interface AffiliatesMapper extends EntityMapper<AffiliatesDTO, Affiliates> {
    @Mapping(target = "stateProvinces", source = "stateProvinces", qualifiedByName = "stateName")
    @Mapping(target = "cities", source = "cities", qualifiedByName = "cityName")
    @Mapping(target = "companies", source = "companies", qualifiedByName = "companyName")
    AffiliatesDTO toDto(Affiliates s);

    @Named("branchName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "branchName", source = "branchName")
    AffiliatesDTO toDtoBranchName(Affiliates affiliates);
}
