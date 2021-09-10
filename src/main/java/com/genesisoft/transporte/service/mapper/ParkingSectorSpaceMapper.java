package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.ParkingSectorSpaceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParkingSectorSpace} and its DTO {@link ParkingSectorSpaceDTO}.
 */
@Mapper(componentModel = "spring", uses = { ParkingSectorMapper.class })
public interface ParkingSectorSpaceMapper extends EntityMapper<ParkingSectorSpaceDTO, ParkingSectorSpace> {
    @Mapping(target = "parkingSector", source = "parkingSector", qualifiedByName = "sectorName")
    ParkingSectorSpaceDTO toDto(ParkingSectorSpace s);

    @Named("parkingNumber")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "parkingNumber", source = "parkingNumber")
    ParkingSectorSpaceDTO toDtoParkingNumber(ParkingSectorSpace parkingSectorSpace);
}
