package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.VehicleControlAttachmentsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VehicleControlAttachments} and its DTO {@link VehicleControlAttachmentsDTO}.
 */
@Mapper(componentModel = "spring", uses = { VehicleControlsMapper.class })
public interface VehicleControlAttachmentsMapper extends EntityMapper<VehicleControlAttachmentsDTO, VehicleControlAttachments> {
    @Mapping(target = "vehicleControls", source = "vehicleControls", qualifiedByName = "id")
    VehicleControlAttachmentsDTO toDto(VehicleControlAttachments s);
}
