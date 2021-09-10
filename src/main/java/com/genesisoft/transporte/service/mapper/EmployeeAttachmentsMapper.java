package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.EmployeeAttachmentsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeAttachments} and its DTO {@link EmployeeAttachmentsDTO}.
 */
@Mapper(componentModel = "spring", uses = { EmployeesMapper.class })
public interface EmployeeAttachmentsMapper extends EntityMapper<EmployeeAttachmentsDTO, EmployeeAttachments> {
    @Mapping(target = "employees", source = "employees", qualifiedByName = "employeeFullName")
    EmployeeAttachmentsDTO toDto(EmployeeAttachments s);
}
