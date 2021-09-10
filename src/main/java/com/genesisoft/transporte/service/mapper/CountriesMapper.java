package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.CountriesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Countries} and its DTO {@link CountriesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CountriesMapper extends EntityMapper<CountriesDTO, Countries> {
    @Named("countryName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "countryName", source = "countryName")
    CountriesDTO toDtoCountryName(Countries countries);
}
