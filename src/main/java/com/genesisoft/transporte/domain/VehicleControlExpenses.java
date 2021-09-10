package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.genesisoft.transporte.domain.enumeration.DriverType;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VehicleControlExpenses.
 */
@Entity
@Table(name = "vehicle_control_expenses")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VehicleControlExpenses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "vehicle_control_expenses_description", nullable = false)
    private String vehicleControlExpensesDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_control_expenses_driver_type")
    private DriverType vehicleControlExpensesDriverType;

    @Column(name = "vehicle_control_expenses_purchase_order")
    private String vehicleControlExpensesPurchaseOrder;

    @Column(name = "vehicle_control_expenses_due_date")
    private LocalDate vehicleControlExpensesDueDate;

    @Column(name = "vehicle_control_expenses_payment_date")
    private LocalDate vehicleControlExpensesPaymentDate;

    @Column(name = "vehicle_control_expenses_billing_total_value")
    private Float vehicleControlExpensesBillingTotalValue;

    @Column(name = "vehicle_control_expenses_driver_commission")
    private Boolean vehicleControlExpensesDriverCommission;

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
    @JsonIgnoreProperties(value = { "vehicleInspections", "vehicleControlExpenses", "vehicleControls" }, allowSetters = true)
    private VehicleControlItem vehicleControlItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleControlExpenses id(Long id) {
        this.id = id;
        return this;
    }

    public String getVehicleControlExpensesDescription() {
        return this.vehicleControlExpensesDescription;
    }

    public VehicleControlExpenses vehicleControlExpensesDescription(String vehicleControlExpensesDescription) {
        this.vehicleControlExpensesDescription = vehicleControlExpensesDescription;
        return this;
    }

    public void setVehicleControlExpensesDescription(String vehicleControlExpensesDescription) {
        this.vehicleControlExpensesDescription = vehicleControlExpensesDescription;
    }

    public DriverType getVehicleControlExpensesDriverType() {
        return this.vehicleControlExpensesDriverType;
    }

    public VehicleControlExpenses vehicleControlExpensesDriverType(DriverType vehicleControlExpensesDriverType) {
        this.vehicleControlExpensesDriverType = vehicleControlExpensesDriverType;
        return this;
    }

    public void setVehicleControlExpensesDriverType(DriverType vehicleControlExpensesDriverType) {
        this.vehicleControlExpensesDriverType = vehicleControlExpensesDriverType;
    }

    public String getVehicleControlExpensesPurchaseOrder() {
        return this.vehicleControlExpensesPurchaseOrder;
    }

    public VehicleControlExpenses vehicleControlExpensesPurchaseOrder(String vehicleControlExpensesPurchaseOrder) {
        this.vehicleControlExpensesPurchaseOrder = vehicleControlExpensesPurchaseOrder;
        return this;
    }

    public void setVehicleControlExpensesPurchaseOrder(String vehicleControlExpensesPurchaseOrder) {
        this.vehicleControlExpensesPurchaseOrder = vehicleControlExpensesPurchaseOrder;
    }

    public LocalDate getVehicleControlExpensesDueDate() {
        return this.vehicleControlExpensesDueDate;
    }

    public VehicleControlExpenses vehicleControlExpensesDueDate(LocalDate vehicleControlExpensesDueDate) {
        this.vehicleControlExpensesDueDate = vehicleControlExpensesDueDate;
        return this;
    }

    public void setVehicleControlExpensesDueDate(LocalDate vehicleControlExpensesDueDate) {
        this.vehicleControlExpensesDueDate = vehicleControlExpensesDueDate;
    }

    public LocalDate getVehicleControlExpensesPaymentDate() {
        return this.vehicleControlExpensesPaymentDate;
    }

    public VehicleControlExpenses vehicleControlExpensesPaymentDate(LocalDate vehicleControlExpensesPaymentDate) {
        this.vehicleControlExpensesPaymentDate = vehicleControlExpensesPaymentDate;
        return this;
    }

    public void setVehicleControlExpensesPaymentDate(LocalDate vehicleControlExpensesPaymentDate) {
        this.vehicleControlExpensesPaymentDate = vehicleControlExpensesPaymentDate;
    }

    public Float getVehicleControlExpensesBillingTotalValue() {
        return this.vehicleControlExpensesBillingTotalValue;
    }

    public VehicleControlExpenses vehicleControlExpensesBillingTotalValue(Float vehicleControlExpensesBillingTotalValue) {
        this.vehicleControlExpensesBillingTotalValue = vehicleControlExpensesBillingTotalValue;
        return this;
    }

    public void setVehicleControlExpensesBillingTotalValue(Float vehicleControlExpensesBillingTotalValue) {
        this.vehicleControlExpensesBillingTotalValue = vehicleControlExpensesBillingTotalValue;
    }

    public Boolean getVehicleControlExpensesDriverCommission() {
        return this.vehicleControlExpensesDriverCommission;
    }

    public VehicleControlExpenses vehicleControlExpensesDriverCommission(Boolean vehicleControlExpensesDriverCommission) {
        this.vehicleControlExpensesDriverCommission = vehicleControlExpensesDriverCommission;
        return this;
    }

    public void setVehicleControlExpensesDriverCommission(Boolean vehicleControlExpensesDriverCommission) {
        this.vehicleControlExpensesDriverCommission = vehicleControlExpensesDriverCommission;
    }

    public VehicleControls getVehicleControls() {
        return this.vehicleControls;
    }

    public VehicleControlExpenses vehicleControls(VehicleControls vehicleControls) {
        this.setVehicleControls(vehicleControls);
        return this;
    }

    public void setVehicleControls(VehicleControls vehicleControls) {
        this.vehicleControls = vehicleControls;
    }

    public Suppliers getSuppliers() {
        return this.suppliers;
    }

    public VehicleControlExpenses suppliers(Suppliers suppliers) {
        this.setSuppliers(suppliers);
        return this;
    }

    public void setSuppliers(Suppliers suppliers) {
        this.suppliers = suppliers;
    }

    public Cities getOrigin() {
        return this.origin;
    }

    public VehicleControlExpenses origin(Cities cities) {
        this.setOrigin(cities);
        return this;
    }

    public void setOrigin(Cities cities) {
        this.origin = cities;
    }

    public Cities getDestination() {
        return this.destination;
    }

    public VehicleControlExpenses destination(Cities cities) {
        this.setDestination(cities);
        return this;
    }

    public void setDestination(Cities cities) {
        this.destination = cities;
    }

    public VehicleControlItem getVehicleControlItem() {
        return this.vehicleControlItem;
    }

    public VehicleControlExpenses vehicleControlItem(VehicleControlItem vehicleControlItem) {
        this.setVehicleControlItem(vehicleControlItem);
        return this;
    }

    public void setVehicleControlItem(VehicleControlItem vehicleControlItem) {
        this.vehicleControlItem = vehicleControlItem;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleControlExpenses)) {
            return false;
        }
        return id != null && id.equals(((VehicleControlExpenses) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleControlExpenses{" +
            "id=" + getId() +
            ", vehicleControlExpensesDescription='" + getVehicleControlExpensesDescription() + "'" +
            ", vehicleControlExpensesDriverType='" + getVehicleControlExpensesDriverType() + "'" +
            ", vehicleControlExpensesPurchaseOrder='" + getVehicleControlExpensesPurchaseOrder() + "'" +
            ", vehicleControlExpensesDueDate='" + getVehicleControlExpensesDueDate() + "'" +
            ", vehicleControlExpensesPaymentDate='" + getVehicleControlExpensesPaymentDate() + "'" +
            ", vehicleControlExpensesBillingTotalValue=" + getVehicleControlExpensesBillingTotalValue() +
            ", vehicleControlExpensesDriverCommission='" + getVehicleControlExpensesDriverCommission() + "'" +
            "}";
    }
}
