package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.VehicleControlExpensesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VehicleControlExpenses} and its DTO {@link VehicleControlExpensesDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = { VehicleControlsMapper.class, SuppliersMapper.class, CitiesMapper.class, VehicleControlItemMapper.class }
)
public interface VehicleControlExpensesMapper extends EntityMapper<VehicleControlExpensesDTO, VehicleControlExpenses> {
    @Mapping(target = "vehicleControls", source = "vehicleControls", qualifiedByName = "id")
    @Mapping(target = "suppliers", source = "suppliers", qualifiedByName = "supplierName")
    @Mapping(target = "origin", source = "origin", qualifiedByName = "cityName")
    @Mapping(target = "destination", source = "destination", qualifiedByName = "cityName")
    @Mapping(target = "vehicleControlItem", source = "vehicleControlItem", qualifiedByName = "vehicleControlItemPlate")
    VehicleControlExpensesDTO toDto(VehicleControlExpenses s);
}
