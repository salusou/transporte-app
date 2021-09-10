package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.ParkingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Parking} and its DTO {@link ParkingDTO}.
 */
@Mapper(componentModel = "spring", uses = { AffiliatesMapper.class, CitiesMapper.class })
public interface ParkingMapper extends EntityMapper<ParkingDTO, Parking> {
    @Mapping(target = "affiliates", source = "affiliates", qualifiedByName = "branchName")
    @Mapping(target = "cities", source = "cities", qualifiedByName = "cityName")
    ParkingDTO toDto(Parking s);

    @Named("parkingName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "parkingName", source = "parkingName")
    ParkingDTO toDtoParkingName(Parking parking);
}
