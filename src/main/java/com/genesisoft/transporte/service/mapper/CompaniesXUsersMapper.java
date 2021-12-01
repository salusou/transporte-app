package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.CompaniesXUsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompaniesXUsers} and its DTO {@link CompaniesXUsersDTO}.
 */
@Mapper(componentModel = "spring", uses = { CompaniesMapper.class, UserMapper.class })
public interface CompaniesXUsersMapper extends EntityMapper<CompaniesXUsersDTO, CompaniesXUsers> {
    @Mapping(target = "companies", source = "companies", qualifiedByName = "companyName")
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    CompaniesXUsersDTO toDto(CompaniesXUsers s);
}
