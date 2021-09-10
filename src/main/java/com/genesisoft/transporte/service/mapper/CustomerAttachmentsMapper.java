package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.CustomerAttachmentsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerAttachments} and its DTO {@link CustomerAttachmentsDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustomersMapper.class, StatusAttachmentsMapper.class })
public interface CustomerAttachmentsMapper extends EntityMapper<CustomerAttachmentsDTO, CustomerAttachments> {
    @Mapping(target = "customers", source = "customers", qualifiedByName = "customerName")
    @Mapping(target = "statusAttachments", source = "statusAttachments", qualifiedByName = "statusName")
    CustomerAttachmentsDTO toDto(CustomerAttachments s);
}
