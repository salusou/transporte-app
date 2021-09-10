package com.genesisoft.transporte.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.VehicleLocationStatus} entity.
 */
public class VehicleLocationStatusDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime vehicleLocationStatusDate;

    @NotNull
    @Size(max = 500)
    private String vehicleLocationStatusDescription;

    private VehicleControlsDTO vehicleControls;

    private CitiesDTO cities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getVehicleLocationStatusDate() {
        return vehicleLocationStatusDate;
    }

    public void setVehicleLocationStatusDate(ZonedDateTime vehicleLocationStatusDate) {
        this.vehicleLocationStatusDate = vehicleLocationStatusDate;
    }

    public String getVehicleLocationStatusDescription() {
        return vehicleLocationStatusDescription;
    }

    public void setVehicleLocationStatusDescription(String vehicleLocationStatusDescription) {
        this.vehicleLocationStatusDescription = vehicleLocationStatusDescription;
    }

    public VehicleControlsDTO getVehicleControls() {
        return vehicleControls;
    }

    public void setVehicleControls(VehicleControlsDTO vehicleControls) {
        this.vehicleControls = vehicleControls;
    }

    public CitiesDTO getCities() {
        return cities;
    }

    public void setCities(CitiesDTO cities) {
        this.cities = cities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleLocationStatusDTO)) {
            return false;
        }

        VehicleLocationStatusDTO vehicleLocationStatusDTO = (VehicleLocationStatusDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vehicleLocationStatusDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleLocationStatusDTO{" +
            "id=" + getId() +
            ", vehicleLocationStatusDate='" + getVehicleLocationStatusDate() + "'" +
            ", vehicleLocationStatusDescription='" + getVehicleLocationStatusDescription() + "'" +
            ", vehicleControls=" + getVehicleControls() +
            ", cities=" + getCities() +
            "}";
    }
}
