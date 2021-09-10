package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.VehicleInspectionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VehicleInspections} and its DTO {@link VehicleInspectionsDTO}.
 */
@Mapper(componentModel = "spring", uses = { VehicleControlItemMapper.class })
public interface VehicleInspectionsMapper extends EntityMapper<VehicleInspectionsDTO, VehicleInspections> {
    @Mapping(target = "vehicleControls", source = "vehicleControls", qualifiedByName = "id")
    VehicleInspectionsDTO toDto(VehicleInspections s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VehicleInspectionsDTO toDtoId(VehicleInspections vehicleInspections);
}
