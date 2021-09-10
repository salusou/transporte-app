package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.VehicleControlHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VehicleControlHistory} and its DTO {@link VehicleControlHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = { VehicleControlsMapper.class, EmployeesMapper.class })
public interface VehicleControlHistoryMapper extends EntityMapper<VehicleControlHistoryDTO, VehicleControlHistory> {
    @Mapping(target = "vehicleControls", source = "vehicleControls", qualifiedByName = "id")
    @Mapping(target = "employees", source = "employees", qualifiedByName = "employeeFullName")
    VehicleControlHistoryDTO toDto(VehicleControlHistory s);
}
