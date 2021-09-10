package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.CustomersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customers} and its DTO {@link CustomersDTO}.
 */
@Mapper(componentModel = "spring", uses = { AffiliatesMapper.class, CitiesMapper.class, CustomersGroupsMapper.class })
public interface CustomersMapper extends EntityMapper<CustomersDTO, Customers> {
    @Mapping(target = "affiliates", source = "affiliates", qualifiedByName = "branchName")
    @Mapping(target = "cities", source = "cities", qualifiedByName = "cityName")
    @Mapping(target = "customersGroups", source = "customersGroups", qualifiedByName = "groupName")
    CustomersDTO toDto(Customers s);

    @Named("customerName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "customerName", source = "customerName")
    CustomersDTO toDtoCustomerName(Customers customers);
}
