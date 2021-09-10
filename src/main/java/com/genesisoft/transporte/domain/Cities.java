package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Cities of the State Province.\n@author Samuel Souza
 */
@Entity
@Table(name = "cities")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cities implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * City Name. Example: São Paulo\n@type String
     */
    @NotNull
    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "latitude", precision = 21, scale = 2)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 21, scale = 2)
    private BigDecimal longitude;

    /**
     * City of the Company\n@type Cities
     */
    @OneToMany(mappedBy = "cities")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "affiliates", "employees", "cities", "stateProvinces" }, allowSetters = true)
    private Set<Companies> companies = new HashSet<>();

    @OneToMany(mappedBy = "cities")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Affiliates> affiliates = new HashSet<>();

    @OneToMany(mappedBy = "cities")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "customersContacts", "customerAttachments", "vehicleControls", "housings", "affiliates", "cities", "customersGroups" },
        allowSetters = true
    )
    private Set<Customers> customers = new HashSet<>();

    @OneToMany(mappedBy = "cities")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parkingSectors", "housings", "affiliates", "cities" }, allowSetters = true)
    private Set<Parking> parkings = new HashSet<>();

    @OneToMany(mappedBy = "cities")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "supplierBanksInfos", "supplierContacts", "vehicleControlExpenses", "housings", "affiliates", "cities", "serviceProvided",
        },
        allowSetters = true
    )
    private Set<Suppliers> suppliers = new HashSet<>();

    @OneToMany(mappedBy = "cities")
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

    @OneToMany(mappedBy = "origin")
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
    private Set<VehicleControls> originVehicleControls = new HashSet<>();

    @OneToMany(mappedBy = "destination")
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
    private Set<VehicleControls> destinationVehicleControls = new HashSet<>();

    @OneToMany(mappedBy = "cities")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehicleControls", "cities" }, allowSetters = true)
    private Set<VehicleLocationStatus> vehicleLocationStatuses = new HashSet<>();

    @OneToMany(mappedBy = "origin")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehicleControls", "suppliers", "origin", "destination", "vehicleControlItem" }, allowSetters = true)
    private Set<VehicleControlExpenses> originVehicleControlExpenses = new HashSet<>();

    @OneToMany(mappedBy = "destination")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehicleControls", "suppliers", "origin", "destination", "vehicleControlItem" }, allowSetters = true)
    private Set<VehicleControlExpenses> destinationVehicleControlExpenses = new HashSet<>();

    @OneToMany(mappedBy = "cities")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "housingVehicleItems", "affiliates", "status", "customers", "employees", "parking", "costCenter", "suppliers", "cities" },
        allowSetters = true
    )
    private Set<Housing> housings = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "cities", "companies", "affiliates", "toInsurances", "forInsurances", "countries" },
        allowSetters = true
    )
    private StateProvinces stateProvinces;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cities id(Long id) {
        this.id = id;
        return this;
    }

    public String getCityName() {
        return this.cityName;
    }

    public Cities cityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public BigDecimal getLatitude() {
        return this.latitude;
    }

    public Cities latitude(BigDecimal latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return this.longitude;
    }

    public Cities longitude(BigDecimal longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Set<Companies> getCompanies() {
        return this.companies;
    }

    public Cities companies(Set<Companies> companies) {
        this.setCompanies(companies);
        return this;
    }

    public Cities addCompanies(Companies companies) {
        this.companies.add(companies);
        companies.setCities(this);
        return this;
    }

    public Cities removeCompanies(Companies companies) {
        this.companies.remove(companies);
        companies.setCities(null);
        return this;
    }

    public void setCompanies(Set<Companies> companies) {
        if (this.companies != null) {
            this.companies.forEach(i -> i.setCities(null));
        }
        if (companies != null) {
            companies.forEach(i -> i.setCities(this));
        }
        this.companies = companies;
    }

    public Set<Affiliates> getAffiliates() {
        return this.affiliates;
    }

    public Cities affiliates(Set<Affiliates> affiliates) {
        this.setAffiliates(affiliates);
        return this;
    }

    public Cities addAffiliates(Affiliates affiliates) {
        this.affiliates.add(affiliates);
        affiliates.setCities(this);
        return this;
    }

    public Cities removeAffiliates(Affiliates affiliates) {
        this.affiliates.remove(affiliates);
        affiliates.setCities(null);
        return this;
    }

    public void setAffiliates(Set<Affiliates> affiliates) {
        if (this.affiliates != null) {
            this.affiliates.forEach(i -> i.setCities(null));
        }
        if (affiliates != null) {
            affiliates.forEach(i -> i.setCities(this));
        }
        this.affiliates = affiliates;
    }

    public Set<Customers> getCustomers() {
        return this.customers;
    }

    public Cities customers(Set<Customers> customers) {
        this.setCustomers(customers);
        return this;
    }

    public Cities addCustomers(Customers customers) {
        this.customers.add(customers);
        customers.setCities(this);
        return this;
    }

    public Cities removeCustomers(Customers customers) {
        this.customers.remove(customers);
        customers.setCities(null);
        return this;
    }

    public void setCustomers(Set<Customers> customers) {
        if (this.customers != null) {
            this.customers.forEach(i -> i.setCities(null));
        }
        if (customers != null) {
            customers.forEach(i -> i.setCities(this));
        }
        this.customers = customers;
    }

    public Set<Parking> getParkings() {
        return this.parkings;
    }

    public Cities parkings(Set<Parking> parkings) {
        this.setParkings(parkings);
        return this;
    }

    public Cities addParking(Parking parking) {
        this.parkings.add(parking);
        parking.setCities(this);
        return this;
    }

    public Cities removeParking(Parking parking) {
        this.parkings.remove(parking);
        parking.setCities(null);
        return this;
    }

    public void setParkings(Set<Parking> parkings) {
        if (this.parkings != null) {
            this.parkings.forEach(i -> i.setCities(null));
        }
        if (parkings != null) {
            parkings.forEach(i -> i.setCities(this));
        }
        this.parkings = parkings;
    }

    public Set<Suppliers> getSuppliers() {
        return this.suppliers;
    }

    public Cities suppliers(Set<Suppliers> suppliers) {
        this.setSuppliers(suppliers);
        return this;
    }

    public Cities addSuppliers(Suppliers suppliers) {
        this.suppliers.add(suppliers);
        suppliers.setCities(this);
        return this;
    }

    public Cities removeSuppliers(Suppliers suppliers) {
        this.suppliers.remove(suppliers);
        suppliers.setCities(null);
        return this;
    }

    public void setSuppliers(Set<Suppliers> suppliers) {
        if (this.suppliers != null) {
            this.suppliers.forEach(i -> i.setCities(null));
        }
        if (suppliers != null) {
            suppliers.forEach(i -> i.setCities(this));
        }
        this.suppliers = suppliers;
    }

    public Set<Employees> getEmployees() {
        return this.employees;
    }

    public Cities employees(Set<Employees> employees) {
        this.setEmployees(employees);
        return this;
    }

    public Cities addEmployees(Employees employees) {
        this.employees.add(employees);
        employees.setCities(this);
        return this;
    }

    public Cities removeEmployees(Employees employees) {
        this.employees.remove(employees);
        employees.setCities(null);
        return this;
    }

    public void setEmployees(Set<Employees> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.setCities(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setCities(this));
        }
        this.employees = employees;
    }

    public Set<VehicleControls> getOriginVehicleControls() {
        return this.originVehicleControls;
    }

    public Cities originVehicleControls(Set<VehicleControls> vehicleControls) {
        this.setOriginVehicleControls(vehicleControls);
        return this;
    }

    public Cities addOriginVehicleControls(VehicleControls vehicleControls) {
        this.originVehicleControls.add(vehicleControls);
        vehicleControls.setOrigin(this);
        return this;
    }

    public Cities removeOriginVehicleControls(VehicleControls vehicleControls) {
        this.originVehicleControls.remove(vehicleControls);
        vehicleControls.setOrigin(null);
        return this;
    }

    public void setOriginVehicleControls(Set<VehicleControls> vehicleControls) {
        if (this.originVehicleControls != null) {
            this.originVehicleControls.forEach(i -> i.setOrigin(null));
        }
        if (vehicleControls != null) {
            vehicleControls.forEach(i -> i.setOrigin(this));
        }
        this.originVehicleControls = vehicleControls;
    }

    public Set<VehicleControls> getDestinationVehicleControls() {
        return this.destinationVehicleControls;
    }

    public Cities destinationVehicleControls(Set<VehicleControls> vehicleControls) {
        this.setDestinationVehicleControls(vehicleControls);
        return this;
    }

    public Cities addDestinationVehicleControls(VehicleControls vehicleControls) {
        this.destinationVehicleControls.add(vehicleControls);
        vehicleControls.setDestination(this);
        return this;
    }

    public Cities removeDestinationVehicleControls(VehicleControls vehicleControls) {
        this.destinationVehicleControls.remove(vehicleControls);
        vehicleControls.setDestination(null);
        return this;
    }

    public void setDestinationVehicleControls(Set<VehicleControls> vehicleControls) {
        if (this.destinationVehicleControls != null) {
            this.destinationVehicleControls.forEach(i -> i.setDestination(null));
        }
        if (vehicleControls != null) {
            vehicleControls.forEach(i -> i.setDestination(this));
        }
        this.destinationVehicleControls = vehicleControls;
    }

    public Set<VehicleLocationStatus> getVehicleLocationStatuses() {
        return this.vehicleLocationStatuses;
    }

    public Cities vehicleLocationStatuses(Set<VehicleLocationStatus> vehicleLocationStatuses) {
        this.setVehicleLocationStatuses(vehicleLocationStatuses);
        return this;
    }

    public Cities addVehicleLocationStatus(VehicleLocationStatus vehicleLocationStatus) {
        this.vehicleLocationStatuses.add(vehicleLocationStatus);
        vehicleLocationStatus.setCities(this);
        return this;
    }

    public Cities removeVehicleLocationStatus(VehicleLocationStatus vehicleLocationStatus) {
        this.vehicleLocationStatuses.remove(vehicleLocationStatus);
        vehicleLocationStatus.setCities(null);
        return this;
    }

    public void setVehicleLocationStatuses(Set<VehicleLocationStatus> vehicleLocationStatuses) {
        if (this.vehicleLocationStatuses != null) {
            this.vehicleLocationStatuses.forEach(i -> i.setCities(null));
        }
        if (vehicleLocationStatuses != null) {
            vehicleLocationStatuses.forEach(i -> i.setCities(this));
        }
        this.vehicleLocationStatuses = vehicleLocationStatuses;
    }

    public Set<VehicleControlExpenses> getOriginVehicleControlExpenses() {
        return this.originVehicleControlExpenses;
    }

    public Cities originVehicleControlExpenses(Set<VehicleControlExpenses> vehicleControlExpenses) {
        this.setOriginVehicleControlExpenses(vehicleControlExpenses);
        return this;
    }

    public Cities addOriginVehicleControlExpenses(VehicleControlExpenses vehicleControlExpenses) {
        this.originVehicleControlExpenses.add(vehicleControlExpenses);
        vehicleControlExpenses.setOrigin(this);
        return this;
    }

    public Cities removeOriginVehicleControlExpenses(VehicleControlExpenses vehicleControlExpenses) {
        this.originVehicleControlExpenses.remove(vehicleControlExpenses);
        vehicleControlExpenses.setOrigin(null);
        return this;
    }

    public void setOriginVehicleControlExpenses(Set<VehicleControlExpenses> vehicleControlExpenses) {
        if (this.originVehicleControlExpenses != null) {
            this.originVehicleControlExpenses.forEach(i -> i.setOrigin(null));
        }
        if (vehicleControlExpenses != null) {
            vehicleControlExpenses.forEach(i -> i.setOrigin(this));
        }
        this.originVehicleControlExpenses = vehicleControlExpenses;
    }

    public Set<VehicleControlExpenses> getDestinationVehicleControlExpenses() {
        return this.destinationVehicleControlExpenses;
    }

    public Cities destinationVehicleControlExpenses(Set<VehicleControlExpenses> vehicleControlExpenses) {
        this.setDestinationVehicleControlExpenses(vehicleControlExpenses);
        return this;
    }

    public Cities addDestinationVehicleControlExpenses(VehicleControlExpenses vehicleControlExpenses) {
        this.destinationVehicleControlExpenses.add(vehicleControlExpenses);
        vehicleControlExpenses.setDestination(this);
        return this;
    }

    public Cities removeDestinationVehicleControlExpenses(VehicleControlExpenses vehicleControlExpenses) {
        this.destinationVehicleControlExpenses.remove(vehicleControlExpenses);
        vehicleControlExpenses.setDestination(null);
        return this;
    }

    public void setDestinationVehicleControlExpenses(Set<VehicleControlExpenses> vehicleControlExpenses) {
        if (this.destinationVehicleControlExpenses != null) {
            this.destinationVehicleControlExpenses.forEach(i -> i.setDestination(null));
        }
        if (vehicleControlExpenses != null) {
            vehicleControlExpenses.forEach(i -> i.setDestination(this));
        }
        this.destinationVehicleControlExpenses = vehicleControlExpenses;
    }

    public Set<Housing> getHousings() {
        return this.housings;
    }

    public Cities housings(Set<Housing> housings) {
        this.setHousings(housings);
        return this;
    }

    public Cities addHousing(Housing housing) {
        this.housings.add(housing);
        housing.setCities(this);
        return this;
    }

    public Cities removeHousing(Housing housing) {
        this.housings.remove(housing);
        housing.setCities(null);
        return this;
    }

    public void setHousings(Set<Housing> housings) {
        if (this.housings != null) {
            this.housings.forEach(i -> i.setCities(null));
        }
        if (housings != null) {
            housings.forEach(i -> i.setCities(this));
        }
        this.housings = housings;
    }

    public StateProvinces getStateProvinces() {
        return this.stateProvinces;
    }

    public Cities stateProvinces(StateProvinces stateProvinces) {
        this.setStateProvinces(stateProvinces);
        return this;
    }

    public void setStateProvinces(StateProvinces stateProvinces) {
        this.stateProvinces = stateProvinces;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cities)) {
            return false;
        }
        return id != null && id.equals(((Cities) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cities{" +
            "id=" + getId() +
            ", cityName='" + getCityName() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            "}";
    }
}
