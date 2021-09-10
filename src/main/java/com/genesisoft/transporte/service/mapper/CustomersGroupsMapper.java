package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.CustomersGroupsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomersGroups} and its DTO {@link CustomersGroupsDTO}.
 */
@Mapper(componentModel = "spring", uses = { AffiliatesMapper.class })
public interface CustomersGroupsMapper extends EntityMapper<CustomersGroupsDTO, CustomersGroups> {
    @Mapping(target = "affiliates", source = "affiliates", qualifiedByName = "branchName")
    CustomersGroupsDTO toDto(CustomersGroups s);

    @Named("groupName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "groupName", source = "groupName")
    CustomersGroupsDTO toDtoGroupName(CustomersGroups customersGroups);
}
