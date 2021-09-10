package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.CompaniesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Companies} and its DTO {@link CompaniesDTO}.
 */
@Mapper(componentModel = "spring", uses = { CitiesMapper.class, StateProvincesMapper.class })
public interface CompaniesMapper extends EntityMapper<CompaniesDTO, Companies> {
    @Mapping(target = "cities", source = "cities", qualifiedByName = "cityName")
    @Mapping(target = "stateProvinces", source = "stateProvinces", qualifiedByName = "stateName")
    CompaniesDTO toDto(Companies s);

    @Named("companyName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "companyName", source = "companyName")
    CompaniesDTO toDtoCompanyName(Companies companies);
}
