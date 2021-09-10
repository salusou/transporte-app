package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VehicleControls.
 */
@Entity
@Table(name = "vehicle_controls")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VehicleControls implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "vehicle_control_authorized_order")
    private String vehicleControlAuthorizedOrder;

    @Column(name = "vehicle_control_request")
    private String vehicleControlRequest;

    @Column(name = "vehicle_control_sinister")
    private String vehicleControlSinister;

    @NotNull
    @Column(name = "vehicle_control_date", nullable = false)
    private LocalDate vehicleControlDate;

    @Column(name = "vehicle_control_km")
    private Float vehicleControlKm;

    @Size(max = 10)
    @Column(name = "vehicle_control_plate", length = 10)
    private String vehicleControlPlate;

    @Column(name = "vehicle_control_amount")
    private Float vehicleControlAmount;

    @Column(name = "vehicle_control_price")
    private Float vehicleControlPrice;

    @Column(name = "vehicle_control_maximum_delivery_date")
    private LocalDate vehicleControlMaximumDeliveryDate;

    @Column(name = "vehicle_control_collection_forecast")
    private LocalDate vehicleControlCollectionForecast;

    @Column(name = "vehicle_control_collection_delivery_forecast")
    private LocalDate vehicleControlCollectionDeliveryForecast;

    @Column(name = "vehicle_control_date_collected")
    private LocalDate vehicleControlDateCollected;

    @Column(name = "vehicle_control_delivery_date")
    private LocalDate vehicleControlDeliveryDate;

    @OneToMany(mappedBy = "vehicleControls")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehicleControls", "cities" }, allowSetters = true)
    private Set<VehicleLocationStatus> vehicleLocationStatuses = new HashSet<>();

    @OneToMany(mappedBy = "vehicleControls")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehicleControls", "employees" }, allowSetters = true)
    private Set<VehicleControlHistory> vehicleControlHistories = new HashSet<>();

    @OneToMany(mappedBy = "vehicleControls")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehicleControls", "fees" }, allowSetters = true)
    private Set<VehicleControlBilling> vehicleControlBillings = new HashSet<>();

    @OneToMany(mappedBy = "vehicleControls")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehicleInspections", "vehicleControlExpenses", "vehicleControls" }, allowSetters = true)
    private Set<VehicleControlItem> vehicleControlItems = new HashSet<>();

    @OneToMany(mappedBy = "vehicleControls")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehicleControls" }, allowSetters = true)
    private Set<VehicleControlAttachments> vehicleControlAttachments = new HashSet<>();

    @OneToMany(mappedBy = "vehicleControls")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehicleControls", "suppliers", "origin", "destination", "vehicleControlItem" }, allowSetters = true)
    private Set<VehicleControlExpenses> vehicleControlExpenses = new HashSet<>();

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
    @JsonIgnoreProperties(
        value = { "customersContacts", "customerAttachments", "vehicleControls", "housings", "affiliates", "cities", "customersGroups" },
        allowSetters = true
    )
    private Customers customers;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "customers", "vehicleControls", "affiliates" }, allowSetters = true)
    private CustomersGroups customersGroups;

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
    private Cities origin;

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
    private Cities destination;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "vehicleControls", "housings", "affiliates" }, allowSetters = true)
    private Status status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleControls id(Long id) {
        this.id = id;
        return this;
    }

    public String getVehicleControlAuthorizedOrder() {
        return this.vehicleControlAuthorizedOrder;
    }

    public VehicleControls vehicleControlAuthorizedOrder(String vehicleControlAuthorizedOrder) {
        this.vehicleControlAuthorizedOrder = vehicleControlAuthorizedOrder;
        return this;
    }

    public void setVehicleControlAuthorizedOrder(String vehicleControlAuthorizedOrder) {
        this.vehicleControlAuthorizedOrder = vehicleControlAuthorizedOrder;
    }

    public String getVehicleControlRequest() {
        return this.vehicleControlRequest;
    }

    public VehicleControls vehicleControlRequest(String vehicleControlRequest) {
        this.vehicleControlRequest = vehicleControlRequest;
        return this;
    }

    public void setVehicleControlRequest(String vehicleControlRequest) {
        this.vehicleControlRequest = vehicleControlRequest;
    }

    public String getVehicleControlSinister() {
        return this.vehicleControlSinister;
    }

    public VehicleControls vehicleControlSinister(String vehicleControlSinister) {
        this.vehicleControlSinister = vehicleControlSinister;
        return this;
    }

    public void setVehicleControlSinister(String vehicleControlSinister) {
        this.vehicleControlSinister = vehicleControlSinister;
    }

    public LocalDate getVehicleControlDate() {
        return this.vehicleControlDate;
    }

    public VehicleControls vehicleControlDate(LocalDate vehicleControlDate) {
        this.vehicleControlDate = vehicleControlDate;
        return this;
    }

    public void setVehicleControlDate(LocalDate vehicleControlDate) {
        this.vehicleControlDate = vehicleControlDate;
    }

    public Float getVehicleControlKm() {
        return this.vehicleControlKm;
    }

    public VehicleControls vehicleControlKm(Float vehicleControlKm) {
        this.vehicleControlKm = vehicleControlKm;
        return this;
    }

    public void setVehicleControlKm(Float vehicleControlKm) {
        this.vehicleControlKm = vehicleControlKm;
    }

    public String getVehicleControlPlate() {
        return this.vehicleControlPlate;
    }

    public VehicleControls vehicleControlPlate(String vehicleControlPlate) {
        this.vehicleControlPlate = vehicleControlPlate;
        return this;
    }

    public void setVehicleControlPlate(String vehicleControlPlate) {
        this.vehicleControlPlate = vehicleControlPlate;
    }

    public Float getVehicleControlAmount() {
        return this.vehicleControlAmount;
    }

    public VehicleControls vehicleControlAmount(Float vehicleControlAmount) {
        this.vehicleControlAmount = vehicleControlAmount;
        return this;
    }

    public void setVehicleControlAmount(Float vehicleControlAmount) {
        this.vehicleControlAmount = vehicleControlAmount;
    }

    public Float getVehicleControlPrice() {
        return this.vehicleControlPrice;
    }

    public VehicleControls vehicleControlPrice(Float vehicleControlPrice) {
        this.vehicleControlPrice = vehicleControlPrice;
        return this;
    }

    public void setVehicleControlPrice(Float vehicleControlPrice) {
        this.vehicleControlPrice = vehicleControlPrice;
    }

    public LocalDate getVehicleControlMaximumDeliveryDate() {
        return this.vehicleControlMaximumDeliveryDate;
    }

    public VehicleControls vehicleControlMaximumDeliveryDate(LocalDate vehicleControlMaximumDeliveryDate) {
        this.vehicleControlMaximumDeliveryDate = vehicleControlMaximumDeliveryDate;
        return this;
    }

    public void setVehicleControlMaximumDeliveryDate(LocalDate vehicleControlMaximumDeliveryDate) {
        this.vehicleControlMaximumDeliveryDate = vehicleControlMaximumDeliveryDate;
    }

    public LocalDate getVehicleControlCollectionForecast() {
        return this.vehicleControlCollectionForecast;
    }

    public VehicleControls vehicleControlCollectionForecast(LocalDate vehicleControlCollectionForecast) {
        this.vehicleControlCollectionForecast = vehicleControlCollectionForecast;
        return this;
    }

    public void setVehicleControlCollectionForecast(LocalDate vehicleControlCollectionForecast) {
        this.vehicleControlCollectionForecast = vehicleControlCollectionForecast;
    }

    public LocalDate getVehicleControlCollectionDeliveryForecast() {
        return this.vehicleControlCollectionDeliveryForecast;
    }

    public VehicleControls vehicleControlCollectionDeliveryForecast(LocalDate vehicleControlCollectionDeliveryForecast) {
        this.vehicleControlCollectionDeliveryForecast = vehicleControlCollectionDeliveryForecast;
        return this;
    }

    public void setVehicleControlCollectionDeliveryForecast(LocalDate vehicleControlCollectionDeliveryForecast) {
        this.vehicleControlCollectionDeliveryForecast = vehicleControlCollectionDeliveryForecast;
    }

    public LocalDate getVehicleControlDateCollected() {
        return this.vehicleControlDateCollected;
    }

    public VehicleControls vehicleControlDateCollected(LocalDate vehicleControlDateCollected) {
        this.vehicleControlDateCollected = vehicleControlDateCollected;
        return this;
    }

    public void setVehicleControlDateCollected(LocalDate vehicleControlDateCollected) {
        this.vehicleControlDateCollected = vehicleControlDateCollected;
    }

    public LocalDate getVehicleControlDeliveryDate() {
        return this.vehicleControlDeliveryDate;
    }

    public VehicleControls vehicleControlDeliveryDate(LocalDate vehicleControlDeliveryDate) {
        this.vehicleControlDeliveryDate = vehicleControlDeliveryDate;
        return this;
    }

    public void setVehicleControlDeliveryDate(LocalDate vehicleControlDeliveryDate) {
        this.vehicleControlDeliveryDate = vehicleControlDeliveryDate;
    }

    public Set<VehicleLocationStatus> getVehicleLocationStatuses() {
        return this.vehicleLocationStatuses;
    }

    public VehicleControls vehicleLocationStatuses(Set<VehicleLocationStatus> vehicleLocationStatuses) {
        this.setVehicleLocationStatuses(vehicleLocationStatuses);
        return this;
    }

    public VehicleControls addVehicleLocationStatus(VehicleLocationStatus vehicleLocationStatus) {
        this.vehicleLocationStatuses.add(vehicleLocationStatus);
        vehicleLocationStatus.setVehicleControls(this);
        return this;
    }

    public VehicleControls removeVehicleLocationStatus(VehicleLocationStatus vehicleLocationStatus) {
        this.vehicleLocationStatuses.remove(vehicleLocationStatus);
        vehicleLocationStatus.setVehicleControls(null);
        return this;
    }

    public void setVehicleLocationStatuses(Set<VehicleLocationStatus> vehicleLocationStatuses) {
        if (this.vehicleLocationStatuses != null) {
            this.vehicleLocationStatuses.forEach(i -> i.setVehicleControls(null));
        }
        if (vehicleLocationStatuses != null) {
            vehicleLocationStatuses.forEach(i -> i.setVehicleControls(this));
        }
        this.vehicleLocationStatuses = vehicleLocationStatuses;
    }

    public Set<VehicleControlHistory> getVehicleControlHistories() {
        return this.vehicleControlHistories;
    }

    public VehicleControls vehicleControlHistories(Set<VehicleControlHistory> vehicleControlHistories) {
        this.setVehicleControlHistories(vehicleControlHistories);
        return this;
    }

    public VehicleControls addVehicleControlHistory(VehicleControlHistory vehicleControlHistory) {
        this.vehicleControlHistories.add(vehicleControlHistory);
        vehicleControlHistory.setVehicleControls(this);
        return this;
    }

    public VehicleControls removeVehicleControlHistory(VehicleControlHistory vehicleControlHistory) {
        this.vehicleControlHistories.remove(vehicleControlHistory);
        vehicleControlHistory.setVehicleControls(null);
        return this;
    }

    public void setVehicleControlHistories(Set<VehicleControlHistory> vehicleControlHistories) {
        if (this.vehicleControlHistories != null) {
            this.vehicleControlHistories.forEach(i -> i.setVehicleControls(null));
        }
        if (vehicleControlHistories != null) {
            vehicleControlHistories.forEach(i -> i.setVehicleControls(this));
        }
        this.vehicleControlHistories = vehicleControlHistories;
    }

    public Set<VehicleControlBilling> getVehicleControlBillings() {
        return this.vehicleControlBillings;
    }

    public VehicleControls vehicleControlBillings(Set<VehicleControlBilling> vehicleControlBillings) {
        this.setVehicleControlBillings(vehicleControlBillings);
        return this;
    }

    public VehicleControls addVehicleControlBilling(VehicleControlBilling vehicleControlBilling) {
        this.vehicleControlBillings.add(vehicleControlBilling);
        vehicleControlBilling.setVehicleControls(this);
        return this;
    }

    public VehicleControls removeVehicleControlBilling(VehicleControlBilling vehicleControlBilling) {
        this.vehicleControlBillings.remove(vehicleControlBilling);
        vehicleControlBilling.setVehicleControls(null);
        return this;
    }

    public void setVehicleControlBillings(Set<VehicleControlBilling> vehicleControlBillings) {
        if (this.vehicleControlBillings != null) {
            this.vehicleControlBillings.forEach(i -> i.setVehicleControls(null));
        }
        if (vehicleControlBillings != null) {
            vehicleControlBillings.forEach(i -> i.setVehicleControls(this));
        }
        this.vehicleControlBillings = vehicleControlBillings;
    }

    public Set<VehicleControlItem> getVehicleControlItems() {
        return this.vehicleControlItems;
    }

    public VehicleControls vehicleControlItems(Set<VehicleControlItem> vehicleControlItems) {
        this.setVehicleControlItems(vehicleControlItems);
        return this;
    }

    public VehicleControls addVehicleControlItem(VehicleControlItem vehicleControlItem) {
        this.vehicleControlItems.add(vehicleControlItem);
        vehicleControlItem.setVehicleControls(this);
        return this;
    }

    public VehicleControls removeVehicleControlItem(VehicleControlItem vehicleControlItem) {
        this.vehicleControlItems.remove(vehicleControlItem);
        vehicleControlItem.setVehicleControls(null);
        return this;
    }

    public void setVehicleControlItems(Set<VehicleControlItem> vehicleControlItems) {
        if (this.vehicleControlItems != null) {
            this.vehicleControlItems.forEach(i -> i.setVehicleControls(null));
        }
        if (vehicleControlItems != null) {
            vehicleControlItems.forEach(i -> i.setVehicleControls(this));
        }
        this.vehicleControlItems = vehicleControlItems;
    }

    public Set<VehicleControlAttachments> getVehicleControlAttachments() {
        return this.vehicleControlAttachments;
    }

    public VehicleControls vehicleControlAttachments(Set<VehicleControlAttachments> vehicleControlAttachments) {
        this.setVehicleControlAttachments(vehicleControlAttachments);
        return this;
    }

    public VehicleControls addVehicleControlAttachments(VehicleControlAttachments vehicleControlAttachments) {
        this.vehicleControlAttachments.add(vehicleControlAttachments);
        vehicleControlAttachments.setVehicleControls(this);
        return this;
    }

    public VehicleControls removeVehicleControlAttachments(VehicleControlAttachments vehicleControlAttachments) {
        this.vehicleControlAttachments.remove(vehicleControlAttachments);
        vehicleControlAttachments.setVehicleControls(null);
        return this;
    }

    public void setVehicleControlAttachments(Set<VehicleControlAttachments> vehicleControlAttachments) {
        if (this.vehicleControlAttachments != null) {
            this.vehicleControlAttachments.forEach(i -> i.setVehicleControls(null));
        }
        if (vehicleControlAttachments != null) {
            vehicleControlAttachments.forEach(i -> i.setVehicleControls(this));
        }
        this.vehicleControlAttachments = vehicleControlAttachments;
    }

    public Set<VehicleControlExpenses> getVehicleControlExpenses() {
        return this.vehicleControlExpenses;
    }

    public VehicleControls vehicleControlExpenses(Set<VehicleControlExpenses> vehicleControlExpenses) {
        this.setVehicleControlExpenses(vehicleControlExpenses);
        return this;
    }

    public VehicleControls addVehicleControlExpenses(VehicleControlExpenses vehicleControlExpenses) {
        this.vehicleControlExpenses.add(vehicleControlExpenses);
        vehicleControlExpenses.setVehicleControls(this);
        return this;
    }

    public VehicleControls removeVehicleControlExpenses(VehicleControlExpenses vehicleControlExpenses) {
        this.vehicleControlExpenses.remove(vehicleControlExpenses);
        vehicleControlExpenses.setVehicleControls(null);
        return this;
    }

    public void setVehicleControlExpenses(Set<VehicleControlExpenses> vehicleControlExpenses) {
        if (this.vehicleControlExpenses != null) {
            this.vehicleControlExpenses.forEach(i -> i.setVehicleControls(null));
        }
        if (vehicleControlExpenses != null) {
            vehicleControlExpenses.forEach(i -> i.setVehicleControls(this));
        }
        this.vehicleControlExpenses = vehicleControlExpenses;
    }

    public Affiliates getAffiliates() {
        return this.affiliates;
    }

    public VehicleControls affiliates(Affiliates affiliates) {
        this.setAffiliates(affiliates);
        return this;
    }

    public void setAffiliates(Affiliates affiliates) {
        this.affiliates = affiliates;
    }

    public Customers getCustomers() {
        return this.customers;
    }

    public VehicleControls customers(Customers customers) {
        this.setCustomers(customers);
        return this;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public CustomersGroups getCustomersGroups() {
        return this.customersGroups;
    }

    public VehicleControls customersGroups(CustomersGroups customersGroups) {
        this.setCustomersGroups(customersGroups);
        return this;
    }

    public void setCustomersGroups(CustomersGroups customersGroups) {
        this.customersGroups = customersGroups;
    }

    public Employees getEmployees() {
        return this.employees;
    }

    public VehicleControls employees(Employees employees) {
        this.setEmployees(employees);
        return this;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public Cities getOrigin() {
        return this.origin;
    }

    public VehicleControls origin(Cities cities) {
        this.setOrigin(cities);
        return this;
    }

    public void setOrigin(Cities cities) {
        this.origin = cities;
    }

    public Cities getDestination() {
        return this.destination;
    }

    public VehicleControls destination(Cities cities) {
        this.setDestination(cities);
        return this;
    }

    public void setDestination(Cities cities) {
        this.destination = cities;
    }

    public Status getStatus() {
        return this.status;
    }

    public VehicleControls status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleControls)) {
            return false;
        }
        return id != null && id.equals(((VehicleControls) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleControls{" +
            "id=" + getId() +
            ", vehicleControlAuthorizedOrder='" + getVehicleControlAuthorizedOrder() + "'" +
            ", vehicleControlRequest='" + getVehicleControlRequest() + "'" +
            ", vehicleControlSinister='" + getVehicleControlSinister() + "'" +
            ", vehicleControlDate='" + getVehicleControlDate() + "'" +
            ", vehicleControlKm=" + getVehicleControlKm() +
            ", vehicleControlPlate='" + getVehicleControlPlate() + "'" +
            ", vehicleControlAmount=" + getVehicleControlAmount() +
            ", vehicleControlPrice=" + getVehicleControlPrice() +
            ", vehicleControlMaximumDeliveryDate='" + getVehicleControlMaximumDeliveryDate() + "'" +
            ", vehicleControlCollectionForecast='" + getVehicleControlCollectionForecast() + "'" +
            ", vehicleControlCollectionDeliveryForecast='" + getVehicleControlCollectionDeliveryForecast() + "'" +
            ", vehicleControlDateCollected='" + getVehicleControlDateCollected() + "'" +
            ", vehicleControlDeliveryDate='" + getVehicleControlDeliveryDate() + "'" +
            "}";
    }
}
