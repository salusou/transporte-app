package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.StateProvincesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StateProvinces} and its DTO {@link StateProvincesDTO}.
 */
@Mapper(componentModel = "spring", uses = { CountriesMapper.class })
public interface StateProvincesMapper extends EntityMapper<StateProvincesDTO, StateProvinces> {
    @Mapping(target = "countries", source = "countries", qualifiedByName = "countryName")
    StateProvincesDTO toDto(StateProvinces s);

    @Named("stateName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "stateName", source = "stateName")
    StateProvincesDTO toDtoStateName(StateProvinces stateProvinces);
}
