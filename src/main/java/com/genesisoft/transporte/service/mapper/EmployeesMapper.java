package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.EmployeesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employees} and its DTO {@link EmployeesDTO}.
 */
@Mapper(componentModel = "spring", uses = { CompaniesMapper.class, AffiliatesMapper.class, CitiesMapper.class, PositionsMapper.class })
public interface EmployeesMapper extends EntityMapper<EmployeesDTO, Employees> {
    @Mapping(target = "companies", source = "companies", qualifiedByName = "companyName")
    @Mapping(target = "affiliates", source = "affiliates", qualifiedByName = "branchName")
    @Mapping(target = "cities", source = "cities", qualifiedByName = "cityName")
    @Mapping(target = "positions", source = "positions", qualifiedByName = "positionName")
    EmployeesDTO toDto(Employees s);

    @Named("employeeFullName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "employeeFullName", source = "employeeFullName")
    EmployeesDTO toDtoEmployeeFullName(Employees employees);
}
