package com.genesisoft.transporte.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.VehicleControlHistory} entity.
 */
public class VehicleControlHistoryDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime vehicleControlHistoryDate;

    @NotNull
    @Size(max = 500)
    private String vehicleControlHistoryDescription;

    private VehicleControlsDTO vehicleControls;

    private EmployeesDTO employees;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getVehicleControlHistoryDate() {
        return vehicleControlHistoryDate;
    }

    public void setVehicleControlHistoryDate(ZonedDateTime vehicleControlHistoryDate) {
        this.vehicleControlHistoryDate = vehicleControlHistoryDate;
    }

    public String getVehicleControlHistoryDescription() {
        return vehicleControlHistoryDescription;
    }

    public void setVehicleControlHistoryDescription(String vehicleControlHistoryDescription) {
        this.vehicleControlHistoryDescription = vehicleControlHistoryDescription;
    }

    public VehicleControlsDTO getVehicleControls() {
        return vehicleControls;
    }

    public void setVehicleControls(VehicleControlsDTO vehicleControls) {
        this.vehicleControls = vehicleControls;
    }

    public EmployeesDTO getEmployees() {
        return employees;
    }

    public void setEmployees(EmployeesDTO employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleControlHistoryDTO)) {
            return false;
        }

        VehicleControlHistoryDTO vehicleControlHistoryDTO = (VehicleControlHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vehicleControlHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleControlHistoryDTO{" +
            "id=" + getId() +
            ", vehicleControlHistoryDate='" + getVehicleControlHistoryDate() + "'" +
            ", vehicleControlHistoryDescription='" + getVehicleControlHistoryDescription() + "'" +
            ", vehicleControls=" + getVehicleControls() +
            ", employees=" + getEmployees() +
            "}";
    }
}
