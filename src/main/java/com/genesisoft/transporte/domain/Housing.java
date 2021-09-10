package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Housing.
 */
@Entity
@Table(name = "housing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Housing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "housing_date", nullable = false)
    private LocalDate housingDate;

    @NotNull
    @Column(name = "housing_entrance_date", nullable = false)
    private ZonedDateTime housingEntranceDate;

    @Column(name = "housing_exit")
    private ZonedDateTime housingExit;

    @Column(name = "housing_receipt_number")
    private Integer housingReceiptNumber;

    @NotNull
    @Column(name = "housing_daily_price", nullable = false)
    private Float housingDailyPrice;

    @Size(max = 500)
    @Column(name = "housing_description", length = 500)
    private String housingDescription;

    @OneToMany(mappedBy = "housing")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "housing", "parkingSector", "parkingSectorSpace" }, allowSetters = true)
    private Set<HousingVehicleItem> housingVehicleItems = new HashSet<>();

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "vehicleControls", "housings", "affiliates" }, allowSetters = true)
    private Status status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "customersContacts", "customerAttachments", "vehicleControls", "housings", "affiliates", "cities", "customersGroups" },
        allowSetters = true
    )
    private Customers customers;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "parkingSectors", "housings", "affiliates", "cities" }, allowSetters = true)
    private Parking parking;

    @ManyToOne
    @JsonIgnoreProperties(value = { "housings", "affiliates" }, allowSetters = true)
    private CostCenter costCenter;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "supplierBanksInfos", "supplierContacts", "vehicleControlExpenses", "housings", "affiliates", "cities", "serviceProvided",
        },
        allowSetters = true
    )
    private Suppliers suppliers;

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

    public Housing id(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getHousingDate() {
        return this.housingDate;
    }

    public Housing housingDate(LocalDate housingDate) {
        this.housingDate = housingDate;
        return this;
    }

    public void setHousingDate(LocalDate housingDate) {
        this.housingDate = housingDate;
    }

    public ZonedDateTime getHousingEntranceDate() {
        return this.housingEntranceDate;
    }

    public Housing housingEntranceDate(ZonedDateTime housingEntranceDate) {
        this.housingEntranceDate = housingEntranceDate;
        return this;
    }

    public void setHousingEntranceDate(ZonedDateTime housingEntranceDate) {
        this.housingEntranceDate = housingEntranceDate;
    }

    public ZonedDateTime getHousingExit() {
        return this.housingExit;
    }

    public Housing housingExit(ZonedDateTime housingExit) {
        this.housingExit = housingExit;
        return this;
    }

    public void setHousingExit(ZonedDateTime housingExit) {
        this.housingExit = housingExit;
    }

    public Integer getHousingReceiptNumber() {
        return this.housingReceiptNumber;
    }

    public Housing housingReceiptNumber(Integer housingReceiptNumber) {
        this.housingReceiptNumber = housingReceiptNumber;
        return this;
    }

    public void setHousingReceiptNumber(Integer housingReceiptNumber) {
        this.housingReceiptNumber = housingReceiptNumber;
    }

    public Float getHousingDailyPrice() {
        return this.housingDailyPrice;
    }

    public Housing housingDailyPrice(Float housingDailyPrice) {
        this.housingDailyPrice = housingDailyPrice;
        return this;
    }

    public void setHousingDailyPrice(Float housingDailyPrice) {
        this.housingDailyPrice = housingDailyPrice;
    }

    public String getHousingDescription() {
        return this.housingDescription;
    }

    public Housing housingDescription(String housingDescription) {
        this.housingDescription = housingDescription;
        return this;
    }

    public void setHousingDescription(String housingDescription) {
        this.housingDescription = housingDescription;
    }

    public Set<HousingVehicleItem> getHousingVehicleItems() {
        return this.housingVehicleItems;
    }

    public Housing housingVehicleItems(Set<HousingVehicleItem> housingVehicleItems) {
        this.setHousingVehicleItems(housingVehicleItems);
        return this;
    }

    public Housing addHousingVehicleItem(HousingVehicleItem housingVehicleItem) {
        this.housingVehicleItems.add(housingVehicleItem);
        housingVehicleItem.setHousing(this);
        return this;
    }

    public Housing removeHousingVehicleItem(HousingVehicleItem housingVehicleItem) {
        this.housingVehicleItems.remove(housingVehicleItem);
        housingVehicleItem.setHousing(null);
        return this;
    }

    public void setHousingVehicleItems(Set<HousingVehicleItem> housingVehicleItems) {
        if (this.housingVehicleItems != null) {
            this.housingVehicleItems.forEach(i -> i.setHousing(null));
        }
        if (housingVehicleItems != null) {
            housingVehicleItems.forEach(i -> i.setHousing(this));
        }
        this.housingVehicleItems = housingVehicleItems;
    }

    public Affiliates getAffiliates() {
        return this.affiliates;
    }

    public Housing affiliates(Affiliates affiliates) {
        this.setAffiliates(affiliates);
        return this;
    }

    public void setAffiliates(Affiliates affiliates) {
        this.affiliates = affiliates;
    }

    public Status getStatus() {
        return this.status;
    }

    public Housing status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Customers getCustomers() {
        return this.customers;
    }

    public Housing customers(Customers customers) {
        this.setCustomers(customers);
        return this;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public Employees getEmployees() {
        return this.employees;
    }

    public Housing employees(Employees employees) {
        this.setEmployees(employees);
        return this;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public Parking getParking() {
        return this.parking;
    }

    public Housing parking(Parking parking) {
        this.setParking(parking);
        return this;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public CostCenter getCostCenter() {
        return this.costCenter;
    }

    public Housing costCenter(CostCenter costCenter) {
        this.setCostCenter(costCenter);
        return this;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public Suppliers getSuppliers() {
        return this.suppliers;
    }

    public Housing suppliers(Suppliers suppliers) {
        this.setSuppliers(suppliers);
        return this;
    }

    public void setSuppliers(Suppliers suppliers) {
        this.suppliers = suppliers;
    }

    public Cities getCities() {
        return this.cities;
    }

    public Housing cities(Cities cities) {
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
        if (!(o instanceof Housing)) {
            return false;
        }
        return id != null && id.equals(((Housing) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Housing{" +
            "id=" + getId() +
            ", housingDate='" + getHousingDate() + "'" +
            ", housingEntranceDate='" + getHousingEntranceDate() + "'" +
            ", housingExit='" + getHousingExit() + "'" +
            ", housingReceiptNumber=" + getHousingReceiptNumber() +
            ", housingDailyPrice=" + getHousingDailyPrice() +
            ", housingDescription='" + getHousingDescription() + "'" +
            "}";
    }
}
