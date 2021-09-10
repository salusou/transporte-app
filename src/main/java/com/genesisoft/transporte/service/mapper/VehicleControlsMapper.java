package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.VehicleControlsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VehicleControls} and its DTO {@link VehicleControlsDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        AffiliatesMapper.class,
        CustomersMapper.class,
        CustomersGroupsMapper.class,
        EmployeesMapper.class,
        CitiesMapper.class,
        StatusMapper.class,
    }
)
public interface VehicleControlsMapper extends EntityMapper<VehicleControlsDTO, VehicleControls> {
    @Mapping(target = "affiliates", source = "affiliates", qualifiedByName = "branchName")
    @Mapping(target = "customers", source = "customers", qualifiedByName = "customerName")
    @Mapping(target = "customersGroups", source = "customersGroups", qualifiedByName = "groupName")
    @Mapping(target = "employees", source = "employees", qualifiedByName = "employeeFullName")
    @Mapping(target = "origin", source = "origin", qualifiedByName = "cityName")
    @Mapping(target = "destination", source = "destination", qualifiedByName = "cityName")
    @Mapping(target = "status", source = "status", qualifiedByName = "statusName")
    VehicleControlsDTO toDto(VehicleControls s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VehicleControlsDTO toDtoId(VehicleControls vehicleControls);
}
