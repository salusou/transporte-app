package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.VehicleInspectionsImagensDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VehicleInspectionsImagens} and its DTO {@link VehicleInspectionsImagensDTO}.
 */
@Mapper(componentModel = "spring", uses = { VehicleInspectionsMapper.class })
public interface VehicleInspectionsImagensMapper extends EntityMapper<VehicleInspectionsImagensDTO, VehicleInspectionsImagens> {
    @Mapping(target = "vehicleInspections", source = "vehicleInspections", qualifiedByName = "id")
    VehicleInspectionsImagensDTO toDto(VehicleInspectionsImagens s);
}
