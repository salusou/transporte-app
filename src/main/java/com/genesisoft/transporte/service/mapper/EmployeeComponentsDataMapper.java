package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.EmployeeComponentsDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeComponentsData} and its DTO {@link EmployeeComponentsDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { EmployeesMapper.class })
public interface EmployeeComponentsDataMapper extends EntityMapper<EmployeeComponentsDataDTO, EmployeeComponentsData> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeFullName")
    EmployeeComponentsDataDTO toDto(EmployeeComponentsData s);
}
