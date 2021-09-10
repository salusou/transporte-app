package com.genesisoft.transporte.service.dto;

import com.genesisoft.transporte.domain.enumeration.InspectionPositions;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.VehicleInspectionsImagens} entity.
 */
public class VehicleInspectionsImagensDTO implements Serializable {

    private Long id;

    @NotNull
    private InspectionPositions vehicleInspectionsImagensPositions;

    @NotNull
    private VehicleStatus vehicleInspectionsImagensStatus;

    @Size(max = 500)
    private String vehicleInspectionsImagensObs;

    @Lob
    private byte[] vehicleInspectionsImagensPhoto;

    private String vehicleInspectionsImagensPhotoContentType;

    @NotNull
    private Float vehicleInspectionsImagensPositionsX;

    @NotNull
    private Float vehicleInspectionsImagensPositionsY;

    private VehicleInspectionsDTO vehicleInspections;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InspectionPositions getVehicleInspectionsImagensPositions() {
        return vehicleInspectionsImagensPositions;
    }

    public void setVehicleInspectionsImagensPositions(InspectionPositions vehicleInspectionsImagensPositions) {
        this.vehicleInspectionsImagensPositions = vehicleInspectionsImagensPositions;
    }

    public VehicleStatus getVehicleInspectionsImagensStatus() {
        return vehicleInspectionsImagensStatus;
    }

    public void setVehicleInspectionsImagensStatus(VehicleStatus vehicleInspectionsImagensStatus) {
        this.vehicleInspectionsImagensStatus = vehicleInspectionsImagensStatus;
    }

    public String getVehicleInspectionsImagensObs() {
        return vehicleInspectionsImagensObs;
    }

    public void setVehicleInspectionsImagensObs(String vehicleInspectionsImagensObs) {
        this.vehicleInspectionsImagensObs = vehicleInspectionsImagensObs;
    }

    public byte[] getVehicleInspectionsImagensPhoto() {
        return vehicleInspectionsImagensPhoto;
    }

    public void setVehicleInspectionsImagensPhoto(byte[] vehicleInspectionsImagensPhoto) {
        this.vehicleInspectionsImagensPhoto = vehicleInspectionsImagensPhoto;
    }

    public String getVehicleInspectionsImagensPhotoContentType() {
        return vehicleInspectionsImagensPhotoContentType;
    }

    public void setVehicleInspectionsImagensPhotoContentType(String vehicleInspectionsImagensPhotoContentType) {
        this.vehicleInspectionsImagensPhotoContentType = vehicleInspectionsImagensPhotoContentType;
    }

    public Float getVehicleInspectionsImagensPositionsX() {
        return vehicleInspectionsImagensPositionsX;
    }

    public void setVehicleInspectionsImagensPositionsX(Float vehicleInspectionsImagensPositionsX) {
        this.vehicleInspectionsImagensPositionsX = vehicleInspectionsImagensPositionsX;
    }

    public Float getVehicleInspectionsImagensPositionsY() {
        return vehicleInspectionsImagensPositionsY;
    }

    public void setVehicleInspectionsImagensPositionsY(Float vehicleInspectionsImagensPositionsY) {
        this.vehicleInspectionsImagensPositionsY = vehicleInspectionsImagensPositionsY;
    }

    public VehicleInspectionsDTO getVehicleInspections() {
        return vehicleInspections;
    }

    public void setVehicleInspections(VehicleInspectionsDTO vehicleInspections) {
        this.vehicleInspections = vehicleInspections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleInspectionsImagensDTO)) {
            return false;
        }

        VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO = (VehicleInspectionsImagensDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vehicleInspectionsImagensDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleInspectionsImagensDTO{" +
            "id=" + getId() +
            ", vehicleInspectionsImagensPositions='" + getVehicleInspectionsImagensPositions() + "'" +
            ", vehicleInspectionsImagensStatus='" + getVehicleInspectionsImagensStatus() + "'" +
            ", vehicleInspectionsImagensObs='" + getVehicleInspectionsImagensObs() + "'" +
            ", vehicleInspectionsImagensPhoto='" + getVehicleInspectionsImagensPhoto() + "'" +
            ", vehicleInspectionsImagensPositionsX=" + getVehicleInspectionsImagensPositionsX() +
            ", vehicleInspectionsImagensPositionsY=" + getVehicleInspectionsImagensPositionsY() +
            ", vehicleInspections=" + getVehicleInspections() +
            "}";
    }
}
