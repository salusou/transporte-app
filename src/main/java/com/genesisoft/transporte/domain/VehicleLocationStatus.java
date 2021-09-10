package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VehicleLocationStatus.
 */
@Entity
@Table(name = "vehicle_location_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VehicleLocationStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "vehicle_location_status_date", nullable = false)
    private ZonedDateTime vehicleLocationStatusDate;

    @NotNull
    @Size(max = 500)
    @Column(name = "vehicle_location_status_description", length = 500, nullable = false)
    private String vehicleLocationStatusDescription;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "vehicleLocationStatuses",
            "vehicleControlHistories",
            "vehicleControlBillings",
            "vehicleControlItems",
            "vehicleControlAttachments",
            "vehicleControlExpenses",
            "affiliates",
            "customers",
            "customersGroups",
            "employees",
            "origin",
            "destination",
            "status",
        },
        allowSetters = true
    )
    private VehicleControls vehicleControls;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "companies",
            "affiliates",
            "customers",
            "parkings",
            "suppliers",
            "employees",
            "originVehicleControls",
            "destinationVehicleControls",
            "vehicleLocationStatuses",
            "originVehicleControlExpenses",
            "destinationVehicleControlExpenses",
            "housings",
            "stateProvinces",
        },
        allowSetters = true
    )
    private Cities cities;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleLocationStatus id(Long id) {
        this.id = id;
        return this;
    }

    public ZonedDateTime getVehicleLocationStatusDate() {
        return this.vehicleLocationStatusDate;
    }

    public VehicleLocationStatus vehicleLocationStatusDate(ZonedDateTime vehicleLocationStatusDate) {
        this.vehicleLocationStatusDate = vehicleLocationStatusDate;
        return this;
    }

    public void setVehicleLocationStatusDate(ZonedDateTime vehicleLocationStatusDate) {
        this.vehicleLocationStatusDate = vehicleLocationStatusDate;
    }

    public String getVehicleLocationStatusDescription() {
        return this.vehicleLocationStatusDescription;
    }

    public VehicleLocationStatus vehicleLocationStatusDescription(String vehicleLocationStatusDescription) {
        this.vehicleLocationStatusDescription = vehicleLocationStatusDescription;
        return this;
    }

    public void setVehicleLocationStatusDescription(String vehicleLocationStatusDescription) {
        this.vehicleLocationStatusDescription = vehicleLocationStatusDescription;
    }

    public VehicleControls getVehicleControls() {
        return this.vehicleControls;
    }

    public VehicleLocationStatus vehicleControls(VehicleControls vehicleControls) {
        this.setVehicleControls(vehicleControls);
        return this;
    }

    public void setVehicleControls(VehicleControls vehicleControls) {
        this.vehicleControls = vehicleControls;
    }

    public Cities getCities() {
        return this.cities;
    }

    public VehicleLocationStatus cities(Cities cities) {
        this.setCities(cities);
        return this;
    }

    public void setCities(Cities cities) {
        this.cities = cities;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleLocationStatus)) {
            return false;
        }
        return id != null && id.equals(((VehicleLocationStatus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleLocationStatus{" +
            "id=" + getId() +
            ", vehicleLocationStatusDate='" + getVehicleLocationStatusDate() + "'" +
            ", vehicleLocationStatusDescription='" + getVehicleLocationStatusDescription() + "'" +
            "}";
    }
}
