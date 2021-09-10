package com.genesisoft.transporte.service.mapper;

import com.genesisoft.transporte.domain.*;
import com.genesisoft.transporte.service.dto.ServiceProvidedDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceProvided} and its DTO {@link ServiceProvidedDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceProvidedMapper extends EntityMapper<ServiceProvidedDTO, ServiceProvided> {
    @Named("serviceName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "serviceName", source = "serviceName")
    ServiceProvidedDTO toDtoServiceName(ServiceProvided serviceProvided);
}
