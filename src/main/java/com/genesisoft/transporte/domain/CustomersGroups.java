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
 * Customers Groups\nThis class are the customers group.\n@author Samuel Souza
 */
@Entity
@Table(name = "customers_groups")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomersGroups implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "group_name", nullable = false)
    private String groupName;

    @OneToMany(mappedBy = "customersGroups")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "customersContacts", "customerAttachments", "vehicleControls", "housings", "affiliates", "cities", "customersGroups" },
        allowSetters = true
    )
    private Set<Customers> customers = new HashSet<>();

    @OneToMany(mappedBy = "customersGroups")
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

    public CustomersGroups id(Long id) {
        this.id = id;
        return this;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public CustomersGroups groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set<Customers> getCustomers() {
        return this.customers;
    }

    public CustomersGroups customers(Set<Customers> customers) {
        this.setCustomers(customers);
        return this;
    }

    public CustomersGroups addCustomers(Customers customers) {
        this.customers.add(customers);
        customers.setCustomersGroups(this);
        return this;
    }

    public CustomersGroups removeCustomers(Customers customers) {
        this.customers.remove(customers);
        customers.setCustomersGroups(null);
        return this;
    }

    public void setCustomers(Set<Customers> customers) {
        if (this.customers != null) {
            this.customers.forEach(i -> i.setCustomersGroups(null));
        }
        if (customers != null) {
            customers.forEach(i -> i.setCustomersGroups(this));
        }
        this.customers = customers;
    }

    public Set<VehicleControls> getVehicleControls() {
        return this.vehicleControls;
    }

    public CustomersGroups vehicleControls(Set<VehicleControls> vehicleControls) {
        this.setVehicleControls(vehicleControls);
        return this;
    }

    public CustomersGroups addVehicleControls(VehicleControls vehicleControls) {
        this.vehicleControls.add(vehicleControls);
        vehicleControls.setCustomersGroups(this);
        return this;
    }

    public CustomersGroups removeVehicleControls(VehicleControls vehicleControls) {
        this.vehicleControls.remove(vehicleControls);
        vehicleControls.setCustomersGroups(null);
        return this;
    }

    public void setVehicleControls(Set<VehicleControls> vehicleControls) {
        if (this.vehicleControls != null) {
            this.vehicleControls.forEach(i -> i.setCustomersGroups(null));
        }
        if (vehicleControls != null) {
            vehicleControls.forEach(i -> i.setCustomersGroups(this));
        }
        this.vehicleControls = vehicleControls;
    }

    public Affiliates getAffiliates() {
        return this.affiliates;
    }

    public CustomersGroups affiliates(Affiliates affiliates) {
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
        if (!(o instanceof CustomersGroups)) {
            return false;
        }
        return id != null && id.equals(((CustomersGroups) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomersGroups{" +
            "id=" + getId() +
            ", groupName='" + getGroupName() + "'" +
            "}";
    }
}
