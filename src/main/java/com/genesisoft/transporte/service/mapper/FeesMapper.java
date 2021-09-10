package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.FeesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fees} and its DTO {@link FeesDTO}.
 */
@Mapper(componentModel = "spring", uses = { AffiliatesMapper.class })
public interface FeesMapper extends EntityMapper<FeesDTO, Fees> {
    @Mapping(target = "affiliates", source = "affiliates", qualifiedByName = "branchName")
    FeesDTO toDto(Fees s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FeesDTO toDtoId(Fees fees);
}
