package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VehicleControlHistory.
 */
@Entity
@Table(name = "vehicle_control_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VehicleControlHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "vehicle_control_history_date", nullable = false)
    private ZonedDateTime vehicleControlHistoryDate;

    @NotNull
    @Size(max = 500)
    @Column(name = "vehicle_control_history_description", length = 500, nullable = false)
    private String vehicleControlHistoryDescription;

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
            "employeeAttachments",
            "employeeComponentsData",
            "vehicleControls",
            "vehicleControlHistories",
            "housings",
            "companies",
            "affiliates",
            "cities",
            "positions",
        },
        allowSetters = true
    )
    private Employees employees;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleControlHistory id(Long id) {
        this.id = id;
        return this;
    }

    public ZonedDateTime getVehicleControlHistoryDate() {
        return this.vehicleControlHistoryDate;
    }

    public VehicleControlHistory vehicleControlHistoryDate(ZonedDateTime vehicleControlHistoryDate) {
        this.vehicleControlHistoryDate = vehicleControlHistoryDate;
        return this;
    }

    public void setVehicleControlHistoryDate(ZonedDateTime vehicleControlHistoryDate) {
        this.vehicleControlHistoryDate = vehicleControlHistoryDate;
    }

    public String getVehicleControlHistoryDescription() {
        return this.vehicleControlHistoryDescription;
    }

    public VehicleControlHistory vehicleControlHistoryDescription(String vehicleControlHistoryDescription) {
        this.vehicleControlHistoryDescription = vehicleControlHistoryDescription;
        return this;
    }

    public void setVehicleControlHistoryDescription(String vehicleControlHistoryDescription) {
        this.vehicleControlHistoryDescription = vehicleControlHistoryDescription;
    }

    public VehicleControls getVehicleControls() {
        return this.vehicleControls;
    }

    public VehicleControlHistory vehicleControls(VehicleControls vehicleControls) {
        this.setVehicleControls(vehicleControls);
        return this;
    }

    public void setVehicleControls(VehicleControls vehicleControls) {
        this.vehicleControls = vehicleControls;
    }

    public Employees getEmployees() {
        return this.employees;
    }

    public VehicleControlHistory employees(Employees employees) {
        this.setEmployees(employees);
        return this;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleControlHistory)) {
            return false;
        }
        return id != null && id.equals(((VehicleControlHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleControlHistory{" +
            "id=" + getId() +
            ", vehicleControlHistoryDate='" + getVehicleControlHistoryDate() + "'" +
            ", vehicleControlHistoryDescription='" + getVehicleControlHistoryDescription() + "'" +
            "}";
    }
}
