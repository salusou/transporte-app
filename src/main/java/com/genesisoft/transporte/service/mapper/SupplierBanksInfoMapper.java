package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.SupplierBanksInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SupplierBanksInfo} and its DTO {@link SupplierBanksInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = { SuppliersMapper.class })
public interface SupplierBanksInfoMapper extends EntityMapper<SupplierBanksInfoDTO, SupplierBanksInfo> {
    @Mapping(target = "suppliers", source = "suppliers", qualifiedByName = "supplierName")
    SupplierBanksInfoDTO toDto(SupplierBanksInfo s);
}
