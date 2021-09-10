package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.VehicleControlItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VehicleControlItem} and its DTO {@link VehicleControlItemDTO}.
 */
@Mapper(componentModel = "spring", uses = { VehicleControlsMapper.class })
public interface VehicleControlItemMapper extends EntityMapper<VehicleControlItemDTO, VehicleControlItem> {
    @Mapping(target = "vehicleControls", source = "vehicleControls", qualifiedByName = "id")
    VehicleControlItemDTO toDto(VehicleControlItem s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VehicleControlItemDTO toDtoId(VehicleControlItem vehicleControlItem);

    @Named("vehicleControlItemPlate")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "vehicleControlItemPlate", source = "vehicleControlItemPlate")
    VehicleControlItemDTO toDtoVehicleControlItemPlate(VehicleControlItem vehicleControlItem);
}
