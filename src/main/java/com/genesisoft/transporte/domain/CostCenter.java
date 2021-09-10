package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Cost Center\nThis class is to separate the cost center.\n@author Samuel Souza
 */
@Entity
@Table(name = "cost_center")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CostCenter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cost_center_name", nullable = false)
    private String costCenterName;

    @OneToMany(mappedBy = "costCenter")
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

    public CostCenter id(Long id) {
        this.id = id;
        return this;
    }

    public String getCostCenterName() {
        return this.costCenterName;
    }

    public CostCenter costCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
        return this;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
    }

    public Set<Housing> getHousings() {
        return this.housings;
    }

    public CostCenter housings(Set<Housing> housings) {
        this.setHousings(housings);
        return this;
    }

    public CostCenter addHousing(Housing housing) {
        this.housings.add(housing);
        housing.setCostCenter(this);
        return this;
    }

    public CostCenter removeHousing(Housing housing) {
        this.housings.remove(housing);
        housing.setCostCenter(null);
        return this;
    }

    public void setHousings(Set<Housing> housings) {
        if (this.housings != null) {
            this.housings.forEach(i -> i.setCostCenter(null));
        }
        if (housings != null) {
            housings.forEach(i -> i.setCostCenter(this));
        }
        this.housings = housings;
    }

    public Affiliates getAffiliates() {
        return this.affiliates;
    }

    public CostCenter affiliates(Affiliates affiliates) {
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
        if (!(o instanceof CostCenter)) {
            return false;
        }
        return id != null && id.equals(((CostCenter) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CostCenter{" +
            "id=" + getId() +
            ", costCenterName='" + getCostCenterName() + "'" +
            "}";
    }
}
