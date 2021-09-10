package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.ParkingSectorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParkingSector} and its DTO {@link ParkingSectorDTO}.
 */
@Mapper(componentModel = "spring", uses = { ParkingMapper.class })
public interface ParkingSectorMapper extends EntityMapper<ParkingSectorDTO, ParkingSector> {
    @Mapping(target = "parking", source = "parking", qualifiedByName = "parkingName")
    ParkingSectorDTO toDto(ParkingSector s);

    @Named("sectorName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "sectorName", source = "sectorName")
    ParkingSectorDTO toDtoSectorName(ParkingSector parkingSector);
}
