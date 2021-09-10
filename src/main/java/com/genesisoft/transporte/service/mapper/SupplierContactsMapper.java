package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.SupplierContactsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SupplierContacts} and its DTO {@link SupplierContactsDTO}.
 */
@Mapper(componentModel = "spring", uses = { SuppliersMapper.class })
public interface SupplierContactsMapper extends EntityMapper<SupplierContactsDTO, SupplierContacts> {
    @Mapping(target = "suppliers", source = "suppliers", qualifiedByName = "supplierName")
    SupplierContactsDTO toDto(SupplierContacts s);
}
