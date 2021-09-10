package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.VehicleControlBillingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VehicleControlBilling} and its DTO {@link VehicleControlBillingDTO}.
 */
@Mapper(componentModel = "spring", uses = { VehicleControlsMapper.class, FeesMapper.class })
public interface VehicleControlBillingMapper extends EntityMapper<VehicleControlBillingDTO, VehicleControlBilling> {
    @Mapping(target = "vehicleControls", source = "vehicleControls", qualifiedByName = "id")
    @Mapping(target = "fees", source = "fees", qualifiedByName = "id")
    VehicleControlBillingDTO toDto(VehicleControlBilling s);
}
