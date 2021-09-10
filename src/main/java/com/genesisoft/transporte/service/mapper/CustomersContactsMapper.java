package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.CustomersContactsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomersContacts} and its DTO {@link CustomersContactsDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustomersMapper.class })
public interface CustomersContactsMapper extends EntityMapper<CustomersContactsDTO, CustomersContacts> {
    @Mapping(target = "customers", source = "customers", qualifiedByName = "customerName")
    CustomersContactsDTO toDto(CustomersContacts s);
}
