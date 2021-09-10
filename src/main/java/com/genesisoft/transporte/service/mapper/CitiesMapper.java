package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.CitiesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cities} and its DTO {@link CitiesDTO}.
 */
@Mapper(componentModel = "spring", uses = { StateProvincesMapper.class })
public interface CitiesMapper extends EntityMapper<CitiesDTO, Cities> {
    @Mapping(target = "stateProvinces", source = "stateProvinces", qualifiedByName = "stateName")
    CitiesDTO toDto(Cities s);

    @Named("cityName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "cityName", source = "cityName")
    CitiesDTO toDtoCityName(Cities cities);
}
