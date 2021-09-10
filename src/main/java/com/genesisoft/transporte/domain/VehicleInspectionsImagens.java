package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.genesisoft.transporte.domain.enumeration.InspectionPositions;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VehicleInspectionsImagens.
 */
@Entity
@Table(name = "vehicle_inspections_imagens")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VehicleInspectionsImagens implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspections_imagens_positions", nullable = false)
    private InspectionPositions vehicleInspectionsImagensPositions;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspections_imagens_status", nullable = false)
    private VehicleStatus vehicleInspectionsImagensStatus;

    @Size(max = 500)
    @Column(name = "vehicle_inspections_imagens_obs", length = 500)
    private String vehicleInspectionsImagensObs;

    @Lob
    @Column(name = "vehicle_inspections_imagens_photo", nullable = false)
    private byte[] vehicleInspectionsImagensPhoto;

    @Column(name = "vehicle_inspections_imagens_photo_content_type", nullable = false)
    private String vehicleInspectionsImagensPhotoContentType;

    @NotNull
    @Column(name = "vehicle_inspections_imagens_positions_x", nullable = false)
    private Float vehicleInspectionsImagensPositionsX;

    @NotNull
    @Column(name = "vehicle_inspections_imagens_positions_y", nullable = false)
    private Float vehicleInspectionsImagensPositionsY;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "vehicleInspectionsImagens", "vehicleControls" }, allowSetters = true)
    private VehicleInspections vehicleInspections;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleInspectionsImagens id(Long id) {
        this.id = id;
        return this;
    }

    public InspectionPositions getVehicleInspectionsImagensPositions() {
        return this.vehicleInspectionsImagensPositions;
    }

    public VehicleInspectionsImagens vehicleInspectionsImagensPositions(InspectionPositions vehicleInspectionsImagensPositions) {
        this.vehicleInspectionsImagensPositions = vehicleInspectionsImagensPositions;
        return this;
    }

    public void setVehicleInspectionsImagensPositions(InspectionPositions vehicleInspectionsImagensPositions) {
        this.vehicleInspectionsImagensPositions = vehicleInspectionsImagensPositions;
    }

    public VehicleStatus getVehicleInspectionsImagensStatus() {
        return this.vehicleInspectionsImagensStatus;
    }

    public VehicleInspectionsImagens vehicleInspectionsImagensStatus(VehicleStatus vehicleInspectionsImagensStatus) {
        this.vehicleInspectionsImagensStatus = vehicleInspectionsImagensStatus;
        return this;
    }

    public void setVehicleInspectionsImagensStatus(VehicleStatus vehicleInspectionsImagensStatus) {
        this.vehicleInspectionsImagensStatus = vehicleInspectionsImagensStatus;
    }

    public String getVehicleInspectionsImagensObs() {
        return this.vehicleInspectionsImagensObs;
    }

    public VehicleInspectionsImagens vehicleInspectionsImagensObs(String vehicleInspectionsImagensObs) {
        this.vehicleInspectionsImagensObs = vehicleInspectionsImagensObs;
        return this;
    }

    public void setVehicleInspectionsImagensObs(String vehicleInspectionsImagensObs) {
        this.vehicleInspectionsImagensObs = vehicleInspectionsImagensObs;
    }

    public byte[] getVehicleInspectionsImagensPhoto() {
        return this.vehicleInspectionsImagensPhoto;
    }

    public VehicleInspectionsImagens vehicleInspectionsImagensPhoto(byte[] vehicleInspectionsImagensPhoto) {
        this.vehicleInspectionsImagensPhoto = vehicleInspectionsImagensPhoto;
        return this;
    }

    public void setVehicleInspectionsImagensPhoto(byte[] vehicleInspectionsImagensPhoto) {
        this.vehicleInspectionsImagensPhoto = vehicleInspectionsImagensPhoto;
    }

    public String getVehicleInspectionsImagensPhotoContentType() {
        return this.vehicleInspectionsImagensPhotoContentType;
    }

    public VehicleInspectionsImagens vehicleInspectionsImagensPhotoContentType(String vehicleInspectionsImagensPhotoContentType) {
        this.vehicleInspectionsImagensPhotoContentType = vehicleInspectionsImagensPhotoContentType;
        return this;
    }

    public void setVehicleInspectionsImagensPhotoContentType(String vehicleInspectionsImagensPhotoContentType) {
        this.vehicleInspectionsImagensPhotoContentType = vehicleInspectionsImagensPhotoContentType;
    }

    public Float getVehicleInspectionsImagensPositionsX() {
        return this.vehicleInspectionsImagensPositionsX;
    }

    public VehicleInspectionsImagens vehicleInspectionsImagensPositionsX(Float vehicleInspectionsImagensPositionsX) {
        this.vehicleInspectionsImagensPositionsX = vehicleInspectionsImagensPositionsX;
        return this;
    }

    public void setVehicleInspectionsImagensPositionsX(Float vehicleInspectionsImagensPositionsX) {
        this.vehicleInspectionsImagensPositionsX = vehicleInspectionsImagensPositionsX;
    }

    public Float getVehicleInspectionsImagensPositionsY() {
        return this.vehicleInspectionsImagensPositionsY;
    }

    public VehicleInspectionsImagens vehicleInspectionsImagensPositionsY(Float vehicleInspectionsImagensPositionsY) {
        this.vehicleInspectionsImagensPositionsY = vehicleInspectionsImagensPositionsY;
        return this;
    }

    public void setVehicleInspectionsImagensPositionsY(Float vehicleInspectionsImagensPositionsY) {
        this.vehicleInspectionsImagensPositionsY = vehicleInspectionsImagensPositionsY;
    }

    public VehicleInspections getVehicleInspections() {
        return this.vehicleInspections;
    }

    public VehicleInspectionsImagens vehicleInspections(VehicleInspections vehicleInspections) {
        this.setVehicleInspections(vehicleInspections);
        return this;
    }

    public void setVehicleInspections(VehicleInspections vehicleInspections) {
        this.vehicleInspections = vehicleInspections;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleInspectionsImagens)) {
            return false;
        }
        return id != null && id.equals(((VehicleInspectionsImagens) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleInspectionsImagens{" +
            "id=" + getId() +
            ", vehicleInspectionsImagensPositions='" + getVehicleInspectionsImagensPositions() + "'" +
            ", vehicleInspectionsImagensStatus='" + getVehicleInspectionsImagensStatus() + "'" +
            ", vehicleInspectionsImagensObs='" + getVehicleInspectionsImagensObs() + "'" +
            ", vehicleInspectionsImagensPhoto='" + getVehicleInspectionsImagensPhoto() + "'" +
            ", vehicleInspectionsImagensPhotoContentType='" + getVehicleInspectionsImagensPhotoContentType() + "'" +
            ", vehicleInspectionsImagensPositionsX=" + getVehicleInspectionsImagensPositionsX() +
            ", vehicleInspectionsImagensPositionsY=" + getVehicleInspectionsImagensPositionsY() +
            "}";
    }
}
