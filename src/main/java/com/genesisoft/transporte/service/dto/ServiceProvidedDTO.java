package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.ServiceProvided} entity.
 */
@ApiModel(description = "Service Provided.\nThis class list all services provided to suppliers.\n@author Samuel Souza")
public class ServiceProvidedDTO implements Serializable {

    private Long id;

    @NotNull
    private String serviceName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceProvidedDTO)) {
            return false;
        }

        ServiceProvidedDTO serviceProvidedDTO = (ServiceProvidedDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, serviceProvidedDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceProvidedDTO{" +
            "id=" + getId() +
            ", serviceName='" + getServiceName() + "'" +
            "}";
    }
}
