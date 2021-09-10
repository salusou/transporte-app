package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.genesisoft.transporte.domain.enumeration.StatusType;
import com.genesisoft.transporte.domain.enumeration.VehicleType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VehicleControlItem.
 */
@Entity
@Table(name = "vehicle_control_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VehicleControlItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_control_status", nullable = false)
    private StatusType vehicleControlStatus;

    @NotNull
    @Column(name = "vehicle_control_item_plate", nullable = false)
    private String vehicleControlItemPlate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_control_item_type", nullable = false)
    private VehicleType vehicleControlItemType;

    @Column(name = "vehicle_control_item_fipe_code")
    private String vehicleControlItemFipeCode;

    @Column(name = "vehicle_control_item_year")
    private String vehicleControlItemYear;

    @Column(name = "vehicle_control_item_fuel")
    private String vehicleControlItemFuel;

    @Column(name = "vehicle_control_item_branch")
    private String vehicleControlItemBranch;

    @Column(name = "vehicle_control_item_model")
    private String vehicleControlItemModel;

    @Column(name = "vehicle_control_item_fuel_abbreviation")
    private String vehicleControlItemFuelAbbreviation;

    @Column(name = "vehicle_control_item_reference_month")
    private String vehicleControlItemReferenceMonth;

    @NotNull
    @Column(name = "vehicle_control_item_value", nullable = false)
    private Float vehicleControlItemValue;

    @NotNull
    @Column(name = "vehicle_control_item_shipping_value", nullable = false)
    private Float vehicleControlItemShippingValue;

    @Column(name = "vehicle_control_item_cte")
    private String vehicleControlItemCTE;

    @Column(name = "vehicle_control_item_cte_date")
    private LocalDate vehicleControlItemCTEDate;

    @OneToMany(mappedBy = "vehicleControls")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehicleInspectionsImagens", "vehicleControls" }, allowSetters = true)
    private Set<VehicleInspections> vehicleInspections = new HashSet<>();

    @OneToMany(mappedBy = "vehicleControlItem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehicleControls", "suppliers", "origin", "destination", "vehicleControlItem" }, allowSetters = true)
    private Set<VehicleControlExpenses> vehicleControlExpenses = new HashSet<>();

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

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleControlItem id(Long id) {
        this.id = id;
        return this;
    }

    public StatusType getVehicleControlStatus() {
        return this.vehicleControlStatus;
    }

    public VehicleControlItem vehicleControlStatus(StatusType vehicleControlStatus) {
        this.vehicleControlStatus = vehicleControlStatus;
        return this;
    }

    public void setVehicleControlStatus(StatusType vehicleControlStatus) {
        this.vehicleControlStatus = vehicleControlStatus;
    }

    public String getVehicleControlItemPlate() {
        return this.vehicleControlItemPlate;
    }

    public VehicleControlItem vehicleControlItemPlate(String vehicleControlItemPlate) {
        this.vehicleControlItemPlate = vehicleControlItemPlate;
        return this;
    }

    public void setVehicleControlItemPlate(String vehicleControlItemPlate) {
        this.vehicleControlItemPlate = vehicleControlItemPlate;
    }

    public VehicleType getVehicleControlItemType() {
        return this.vehicleControlItemType;
    }

    public VehicleControlItem vehicleControlItemType(VehicleType vehicleControlItemType) {
        this.vehicleControlItemType = vehicleControlItemType;
        return this;
    }

    public void setVehicleControlItemType(VehicleType vehicleControlItemType) {
        this.vehicleControlItemType = vehicleControlItemType;
    }

    public String getVehicleControlItemFipeCode() {
        return this.vehicleControlItemFipeCode;
    }

    public VehicleControlItem vehicleControlItemFipeCode(String vehicleControlItemFipeCode) {
        this.vehicleControlItemFipeCode = vehicleControlItemFipeCode;
        return this;
    }

    public void setVehicleControlItemFipeCode(String vehicleControlItemFipeCode) {
        this.vehicleControlItemFipeCode = vehicleControlItemFipeCode;
    }

    public String getVehicleControlItemYear() {
        return this.vehicleControlItemYear;
    }

    public VehicleControlItem vehicleControlItemYear(String vehicleControlItemYear) {
        this.vehicleControlItemYear = vehicleControlItemYear;
        return this;
    }

    public void setVehicleControlItemYear(String vehicleControlItemYear) {
        this.vehicleControlItemYear = vehicleControlItemYear;
    }

    public String getVehicleControlItemFuel() {
        return this.vehicleControlItemFuel;
    }

    public VehicleControlItem vehicleControlItemFuel(String vehicleControlItemFuel) {
        this.vehicleControlItemFuel = vehicleControlItemFuel;
        return this;
    }

    public void setVehicleControlItemFuel(String vehicleControlItemFuel) {
        this.vehicleControlItemFuel = vehicleControlItemFuel;
    }

    public String getVehicleControlItemBranch() {
        return this.vehicleControlItemBranch;
    }

    public VehicleControlItem vehicleControlItemBranch(String vehicleControlItemBranch) {
        this.vehicleControlItemBranch = vehicleControlItemBranch;
        return this;
    }

    public void setVehicleControlItemBranch(String vehicleControlItemBranch) {
        this.vehicleControlItemBranch = vehicleControlItemBranch;
    }

    public String getVehicleControlItemModel() {
        return this.vehicleControlItemModel;
    }

    public VehicleControlItem vehicleControlItemModel(String vehicleControlItemModel) {
        this.vehicleControlItemModel = vehicleControlItemModel;
        return this;
    }

    public void setVehicleControlItemModel(String vehicleControlItemModel) {
        this.vehicleControlItemModel = vehicleControlItemModel;
    }

    public String getVehicleControlItemFuelAbbreviation() {
        return this.vehicleControlItemFuelAbbreviation;
    }

    public VehicleControlItem vehicleControlItemFuelAbbreviation(String vehicleControlItemFuelAbbreviation) {
        this.vehicleControlItemFuelAbbreviation = vehicleControlItemFuelAbbreviation;
        return this;
    }

    public void setVehicleControlItemFuelAbbreviation(String vehicleControlItemFuelAbbreviation) {
        this.vehicleControlItemFuelAbbreviation = vehicleControlItemFuelAbbreviation;
    }

    public String getVehicleControlItemReferenceMonth() {
        return this.vehicleControlItemReferenceMonth;
    }

    public VehicleControlItem vehicleControlItemReferenceMonth(String vehicleControlItemReferenceMonth) {
        this.vehicleControlItemReferenceMonth = vehicleControlItemReferenceMonth;
        return this;
    }

    public void setVehicleControlItemReferenceMonth(String vehicleControlItemReferenceMonth) {
        this.vehicleControlItemReferenceMonth = vehicleControlItemReferenceMonth;
    }

    public Float getVehicleControlItemValue() {
        return this.vehicleControlItemValue;
    }

    public VehicleControlItem vehicleControlItemValue(Float vehicleControlItemValue) {
        this.vehicleControlItemValue = vehicleControlItemValue;
        return this;
    }

    public void setVehicleControlItemValue(Float vehicleControlItemValue) {
        this.vehicleControlItemValue = vehicleControlItemValue;
    }

    public Float getVehicleControlItemShippingValue() {
        return this.vehicleControlItemShippingValue;
    }

    public VehicleControlItem vehicleControlItemShippingValue(Float vehicleControlItemShippingValue) {
        this.vehicleControlItemShippingValue = vehicleControlItemShippingValue;
        return this;
    }

    public void setVehicleControlItemShippingValue(Float vehicleControlItemShippingValue) {
        this.vehicleControlItemShippingValue = vehicleControlItemShippingValue;
    }

    public String getVehicleControlItemCTE() {
        return this.vehicleControlItemCTE;
    }

    public VehicleControlItem vehicleControlItemCTE(String vehicleControlItemCTE) {
        this.vehicleControlItemCTE = vehicleControlItemCTE;
        return this;
    }

    public void setVehicleControlItemCTE(String vehicleControlItemCTE) {
        this.vehicleControlItemCTE = vehicleControlItemCTE;
    }

    public LocalDate getVehicleControlItemCTEDate() {
        return this.vehicleControlItemCTEDate;
    }

    public VehicleControlItem vehicleControlItemCTEDate(LocalDate vehicleControlItemCTEDate) {
        this.vehicleControlItemCTEDate = vehicleControlItemCTEDate;
        return this;
    }

    public void setVehicleControlItemCTEDate(LocalDate vehicleControlItemCTEDate) {
        this.vehicleControlItemCTEDate = vehicleControlItemCTEDate;
    }

    public Set<VehicleInspections> getVehicleInspections() {
        return this.vehicleInspections;
    }

    public VehicleControlItem vehicleInspections(Set<VehicleInspections> vehicleInspections) {
        this.setVehicleInspections(vehicleInspections);
        return this;
    }

    public VehicleControlItem addVehicleInspections(VehicleInspections vehicleInspections) {
        this.vehicleInspections.add(vehicleInspections);
        vehicleInspections.setVehicleControls(this);
        return this;
    }

    public VehicleControlItem removeVehicleInspections(VehicleInspections vehicleInspections) {
        this.vehicleInspections.remove(vehicleInspections);
        vehicleInspections.setVehicleControls(null);
        return this;
    }

    public void setVehicleInspections(Set<VehicleInspections> vehicleInspections) {
        if (this.vehicleInspections != null) {
            this.vehicleInspections.forEach(i -> i.setVehicleControls(null));
        }
        if (vehicleInspections != null) {
            vehicleInspections.forEach(i -> i.setVehicleControls(this));
        }
        this.vehicleInspections = vehicleInspections;
    }

    public Set<VehicleControlExpenses> getVehicleControlExpenses() {
        return this.vehicleControlExpenses;
    }

    public VehicleControlItem vehicleControlExpenses(Set<VehicleControlExpenses> vehicleControlExpenses) {
        this.setVehicleControlExpenses(vehicleControlExpenses);
        return this;
    }

    public VehicleControlItem addVehicleControlExpenses(VehicleControlExpenses vehicleControlExpenses) {
        this.vehicleControlExpenses.add(vehicleControlExpenses);
        vehicleControlExpenses.setVehicleControlItem(this);
        return this;
    }

    public VehicleControlItem removeVehicleControlExpenses(VehicleControlExpenses vehicleControlExpenses) {
        this.vehicleControlExpenses.remove(vehicleControlExpenses);
        vehicleControlExpenses.setVehicleControlItem(null);
        return this;
    }

    public void setVehicleControlExpenses(Set<VehicleControlExpenses> vehicleControlExpenses) {
        if (this.vehicleControlExpenses != null) {
            this.vehicleControlExpenses.forEach(i -> i.setVehicleControlItem(null));
        }
        if (vehicleControlExpenses != null) {
            vehicleControlExpenses.forEach(i -> i.setVehicleControlItem(this));
        }
        this.vehicleControlExpenses = vehicleControlExpenses;
    }

    public VehicleControls getVehicleControls() {
        return this.vehicleControls;
    }

    public VehicleControlItem vehicleControls(VehicleControls vehicleControls) {
        this.setVehicleControls(vehicleControls);
        return this;
    }

    public void setVehicleControls(VehicleControls vehicleControls) {
        this.vehicleControls = vehicleControls;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleControlItem)) {
            return false;
        }
        return id != null && id.equals(((VehicleControlItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleControlItem{" +
            "id=" + getId() +
            ", vehicleControlStatus='" + getVehicleControlStatus() + "'" +
            ", vehicleControlItemPlate='" + getVehicleControlItemPlate() + "'" +
            ", vehicleControlItemType='" + getVehicleControlItemType() + "'" +
            ", vehicleControlItemFipeCode='" + getVehicleControlItemFipeCode() + "'" +
            ", vehicleControlItemYear='" + getVehicleControlItemYear() + "'" +
            ", vehicleControlItemFuel='" + getVehicleControlItemFuel() + "'" +
            ", vehicleControlItemBranch='" + getVehicleControlItemBranch() + "'" +
            ", vehicleControlItemModel='" + getVehicleControlItemModel() + "'" +
            ", vehicleControlItemFuelAbbreviation='" + getVehicleControlItemFuelAbbreviation() + "'" +
            ", vehicleControlItemReferenceMonth='" + getVehicleControlItemReferenceMonth() + "'" +
            ", vehicleControlItemValue=" + getVehicleControlItemValue() +
            ", vehicleControlItemShippingValue=" + getVehicleControlItemShippingValue() +
            ", vehicleControlItemCTE='" + getVehicleControlItemCTE() + "'" +
            ", vehicleControlItemCTEDate='" + getVehicleControlItemCTEDate() + "'" +
            "}";
    }
}
