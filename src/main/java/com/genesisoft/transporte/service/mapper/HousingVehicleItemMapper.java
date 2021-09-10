package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.HousingVehicleItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HousingVehicleItem} and its DTO {@link HousingVehicleItemDTO}.
 */
@Mapper(componentModel = "spring", uses = { HousingMapper.class, ParkingSectorMapper.class, ParkingSectorSpaceMapper.class })
public interface HousingVehicleItemMapper extends EntityMapper<HousingVehicleItemDTO, HousingVehicleItem> {
    @Mapping(target = "housing", source = "housing", qualifiedByName = "housingReceiptNumber")
    @Mapping(target = "parkingSector", source = "parkingSector", qualifiedByName = "sectorName")
    @Mapping(target = "parkingSectorSpace", source = "parkingSectorSpace", qualifiedByName = "parkingNumber")
    HousingVehicleItemDTO toDto(HousingVehicleItem s);
}
