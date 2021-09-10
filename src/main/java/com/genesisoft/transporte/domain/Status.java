package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.genesisoft.transporte.domain.enumeration.ScreenType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Status.
 */
@Entity
@Table(name = "status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "status_name", nullable = false)
    private String statusName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "screen_type", nullable = false)
    private ScreenType screenType;

    @Column(name = "status_descriptions")
    private String statusDescriptions;

    @OneToMany(mappedBy = "status")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<VehicleControls> vehicleControls = new HashSet<>();

    @OneToMany(mappedBy = "status")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "housingVehicleItems", "affiliates", "status", "customers", "employees", "parking", "costCenter", "suppliers", "cities" },
        allowSetters = true
    )
    private Set<Housing> housings = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "insurances",
            "positions",
            "costCenters",
            "administrativeFeesRanges",
            "customersGroups",
            "fees",
            "customers",
            "statusAttachments",
            "statuses",
            "parkings",
            "suppliers",
            "employees",
            "vehicleControls",
            "housings",
            "stateProvinces",
            "cities",
            "companies",
        },
        allowSetters = true
    )
    private Affiliates affiliates;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status id(Long id) {
        this.id = id;
        return this;
    }

    public String getStatusName() {
        return this.statusName;
    }

    public Status statusName(String statusName) {
        this.statusName = statusName;
        return this;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public ScreenType getScreenType() {
        return this.screenType;
    }

    public Status screenType(ScreenType screenType) {
        this.screenType = screenType;
        return this;
    }

    public void setScreenType(ScreenType screenType) {
        this.screenType = screenType;
    }

    public String getStatusDescriptions() {
        return this.statusDescriptions;
    }

    public Status statusDescriptions(String statusDescriptions) {
        this.statusDescriptions = statusDescriptions;
        return this;
    }

    public void setStatusDescriptions(String statusDescriptions) {
        this.statusDescriptions = statusDescriptions;
    }

    public Set<VehicleControls> getVehicleControls() {
        return this.vehicleControls;
    }

    public Status vehicleControls(Set<VehicleControls> vehicleControls) {
        this.setVehicleControls(vehicleControls);
        return this;
    }

    public Status addVehicleControls(VehicleControls vehicleControls) {
        this.vehicleControls.add(vehicleControls);
        vehicleControls.setStatus(this);
        return this;
    }

    public Status removeVehicleControls(VehicleControls vehicleControls) {
        this.vehicleControls.remove(vehicleControls);
        vehicleControls.setStatus(null);
        return this;
    }

    public void setVehicleControls(Set<VehicleControls> vehicleControls) {
        if (this.vehicleControls != null) {
            this.vehicleControls.forEach(i -> i.setStatus(null));
        }
        if (vehicleControls != null) {
            vehicleControls.forEach(i -> i.setStatus(this));
        }
        this.vehicleControls = vehicleControls;
    }

    public Set<Housing> getHousings() {
        return this.housings;
    }

    public Status housings(Set<Housing> housings) {
        this.setHousings(housings);
        return this;
    }

    public Status addHousing(Housing housing) {
        this.housings.add(housing);
        housing.setStatus(this);
        return this;
    }

    public Status removeHousing(Housing housing) {
        this.housings.remove(housing);
        housing.setStatus(null);
        return this;
    }

    public void setHousings(Set<Housing> housings) {
        if (this.housings != null) {
            this.housings.forEach(i -> i.setStatus(null));
        }
        if (housings != null) {
            housings.forEach(i -> i.setStatus(this));
        }
        this.housings = housings;
    }

    public Affiliates getAffiliates() {
        return this.affiliates;
    }

    public Status affiliates(Affiliates affiliates) {
        this.setAffiliates(affiliates);
        return this;
    }

    public void setAffiliates(Affiliates affiliates) {
        this.affiliates = affiliates;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Status)) {
            return false;
        }
        return id != null && id.equals(((Status) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Status{" +
            "id=" + getId() +
            ", statusName='" + getStatusName() + "'" +
            ", screenType='" + getScreenType() + "'" +
            ", statusDescriptions='" + getStatusDescriptions() + "'" +
            "}";
    }
}
