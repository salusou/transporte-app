package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.HousingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Housing} and its DTO {@link HousingDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        AffiliatesMapper.class,
        StatusMapper.class,
        CustomersMapper.class,
        EmployeesMapper.class,
        ParkingMapper.class,
        CostCenterMapper.class,
        SuppliersMapper.class,
        CitiesMapper.class,
    }
)
public interface HousingMapper extends EntityMapper<HousingDTO, Housing> {
    @Mapping(target = "affiliates", source = "affiliates", qualifiedByName = "branchName")
    @Mapping(target = "status", source = "status", qualifiedByName = "statusName")
    @Mapping(target = "customers", source = "customers", qualifiedByName = "customerName")
    @Mapping(target = "employees", source = "employees", qualifiedByName = "employeeFullName")
    @Mapping(target = "parking", source = "parking", qualifiedByName = "parkingName")
    @Mapping(target = "costCenter", source = "costCenter", qualifiedByName = "costCenterName")
    @Mapping(target = "suppliers", source = "suppliers", qualifiedByName = "supplierName")
    @Mapping(target = "cities", source = "cities", qualifiedByName = "cityName")
    HousingDTO toDto(Housing s);

    @Named("housingReceiptNumber")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "housingReceiptNumber", source = "housingReceiptNumber")
    HousingDTO toDtoHousingReceiptNumber(Housing housing);
}
