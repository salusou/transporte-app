package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.SuppliersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Suppliers} and its DTO {@link SuppliersDTO}.
 */
@Mapper(componentModel = "spring", uses = { AffiliatesMapper.class, CitiesMapper.class, ServiceProvidedMapper.class })
public interface SuppliersMapper extends EntityMapper<SuppliersDTO, Suppliers> {
    @Mapping(target = "affiliates", source = "affiliates", qualifiedByName = "branchName")
    @Mapping(target = "cities", source = "cities", qualifiedByName = "cityName")
    @Mapping(target = "serviceProvided", source = "serviceProvided", qualifiedByName = "serviceName")
    SuppliersDTO toDto(Suppliers s);

    @Named("supplierName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "supplierName", source = "supplierName")
    SuppliersDTO toDtoSupplierName(Suppliers suppliers);
}
