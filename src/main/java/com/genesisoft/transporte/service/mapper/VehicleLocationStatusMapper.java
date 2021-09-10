package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.VehicleLocationStatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VehicleLocationStatus} and its DTO {@link VehicleLocationStatusDTO}.
 */
@Mapper(componentModel = "spring", uses = { VehicleControlsMapper.class, CitiesMapper.class })
public interface VehicleLocationStatusMapper extends EntityMapper<VehicleLocationStatusDTO, VehicleLocationStatus> {
    @Mapping(target = "vehicleControls", source = "vehicleControls", qualifiedByName = "id")
    @Mapping(target = "cities", source = "cities", qualifiedByName = "cityName")
    VehicleLocationStatusDTO toDto(VehicleLocationStatus s);
}
