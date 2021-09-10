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
 * PositionsName\nThis class is used to filter the cities.\n@author Samuel Souza
 */
@Entity
@Table(name = "positions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Positions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "position_name", nullable = false)
    private String positionName;

    @OneToMany(mappedBy = "positions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Employees> employees = new HashSet<>();

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

    public Positions id(Long id) {
        this.id = id;
        return this;
    }

    public String getPositionName() {
        return this.positionName;
    }

    public Positions positionName(String positionName) {
        this.positionName = positionName;
        return this;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Set<Employees> getEmployees() {
        return this.employees;
    }

    public Positions employees(Set<Employees> employees) {
        this.setEmployees(employees);
        return this;
    }

    public Positions addEmployees(Employees employees) {
        this.employees.add(employees);
        employees.setPositions(this);
        return this;
    }

    public Positions removeEmployees(Employees employees) {
        this.employees.remove(employees);
        employees.setPositions(null);
        return this;
    }

    public void setEmployees(Set<Employees> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.setPositions(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setPositions(this));
        }
        this.employees = employees;
    }

    public Affiliates getAffiliates() {
        return this.affiliates;
    }

    public Positions affiliates(Affiliates affiliates) {
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
        if (!(o instanceof Positions)) {
            return false;
        }
        return id != null && id.equals(((Positions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Positions{" +
            "id=" + getId() +
            ", positionName='" + getPositionName() + "'" +
            "}";
    }
}
