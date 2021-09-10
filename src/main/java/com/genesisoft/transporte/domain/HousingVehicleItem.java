package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.genesisoft.transporte.domain.enumeration.StatusType;
import com.genesisoft.transporte.domain.enumeration.VehicleType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HousingVehicleItem.
 */
@Entity
@Table(name = "housing_vehicle_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HousingVehicleItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "housing_vehicle_item_status", nullable = false)
    private StatusType housingVehicleItemStatus;

    @NotNull
    @Column(name = "housing_vehicle_item_plate", nullable = false)
    private String housingVehicleItemPlate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "housing_vehicle_item_type", nullable = false)
    private VehicleType housingVehicleItemType;

    @Column(name = "housing_vehicle_item_fipe_code")
    private String housingVehicleItemFipeCode;

    @Column(name = "housing_vehicle_item_year")
    private String housingVehicleItemYear;

    @Column(name = "housing_vehicle_item_fuel")
    private String housingVehicleItemFuel;

    @Column(name = "housing_vehicle_item_branch")
    private String housingVehicleItemBranch;

    @Column(name = "housing_vehicle_item_model")
    private String housingVehicleItemModel;

    @Column(name = "housing_vehicle_item_fuel_abbreviation")
    private String housingVehicleItemFuelAbbreviation;

    @Column(name = "housing_vehicle_item_reference_month")
    private String housingVehicleItemReferenceMonth;

    @NotNull
    @Column(name = "housing_vehicle_item_value", nullable = false)
    private Float housingVehicleItemValue;

    @NotNull
    @Column(name = "housing_vehicle_item_shipping_value", nullable = false)
    private Float housingVehicleItemShippingValue;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "housingVehicleItems", "affiliates", "status", "customers", "employees", "parking", "costCenter", "suppliers", "cities" },
        allowSetters = true
    )
    private Housing housing;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parkingSectorSpaces", "housingVehicleItems", "parking" }, allowSetters = true)
    private ParkingSector parkingSector;

    @ManyToOne
    @JsonIgnoreProperties(value = { "housingVehicleItems", "parkingSector" }, allowSetters = true)
    private ParkingSectorSpace parkingSectorSpace;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HousingVehicleItem id(Long id) {
        this.id = id;
        return this;
    }

    public StatusType getHousingVehicleItemStatus() {
        return this.housingVehicleItemStatus;
    }

    public HousingVehicleItem housingVehicleItemStatus(StatusType housingVehicleItemStatus) {
        this.housingVehicleItemStatus = housingVehicleItemStatus;
        return this;
    }

    public void setHousingVehicleItemStatus(StatusType housingVehicleItemStatus) {
        this.housingVehicleItemStatus = housingVehicleItemStatus;
    }

    public String getHousingVehicleItemPlate() {
        return this.housingVehicleItemPlate;
    }

    public HousingVehicleItem housingVehicleItemPlate(String housingVehicleItemPlate) {
        this.housingVehicleItemPlate = housingVehicleItemPlate;
        return this;
    }

    public void setHousingVehicleItemPlate(String housingVehicleItemPlate) {
        this.housingVehicleItemPlate = housingVehicleItemPlate;
    }

    public VehicleType getHousingVehicleItemType() {
        return this.housingVehicleItemType;
    }

    public HousingVehicleItem housingVehicleItemType(VehicleType housingVehicleItemType) {
        this.housingVehicleItemType = housingVehicleItemType;
        return this;
    }

    public void setHousingVehicleItemType(VehicleType housingVehicleItemType) {
        this.housingVehicleItemType = housingVehicleItemType;
    }

    public String getHousingVehicleItemFipeCode() {
        return this.housingVehicleItemFipeCode;
    }

    public HousingVehicleItem housingVehicleItemFipeCode(String housingVehicleItemFipeCode) {
        this.housingVehicleItemFipeCode = housingVehicleItemFipeCode;
        return this;
    }

    public void setHousingVehicleItemFipeCode(String housingVehicleItemFipeCode) {
        this.housingVehicleItemFipeCode = housingVehicleItemFipeCode;
    }

    public String getHousingVehicleItemYear() {
        return this.housingVehicleItemYear;
    }

    public HousingVehicleItem housingVehicleItemYear(String housingVehicleItemYear) {
        this.housingVehicleItemYear = housingVehicleItemYear;
        return this;
    }

    public void setHousingVehicleItemYear(String housingVehicleItemYear) {
        this.housingVehicleItemYear = housingVehicleItemYear;
    }

    public String getHousingVehicleItemFuel() {
        return this.housingVehicleItemFuel;
    }

    public HousingVehicleItem housingVehicleItemFuel(String housingVehicleItemFuel) {
        this.housingVehicleItemFuel = housingVehicleItemFuel;
        return this;
    }

    public void setHousingVehicleItemFuel(String housingVehicleItemFuel) {
        this.housingVehicleItemFuel = housingVehicleItemFuel;
    }

    public String getHousingVehicleItemBranch() {
        return this.housingVehicleItemBranch;
    }

    public HousingVehicleItem housingVehicleItemBranch(String housingVehicleItemBranch) {
        this.housingVehicleItemBranch = housingVehicleItemBranch;
        return this;
    }

    public void setHousingVehicleItemBranch(String housingVehicleItemBranch) {
        this.housingVehicleItemBranch = housingVehicleItemBranch;
    }

    public String getHousingVehicleItemModel() {
        return this.housingVehicleItemModel;
    }

    public HousingVehicleItem housingVehicleItemModel(String housingVehicleItemModel) {
        this.housingVehicleItemModel = housingVehicleItemModel;
        return this;
    }

    public void setHousingVehicleItemModel(String housingVehicleItemModel) {
        this.housingVehicleItemModel = housingVehicleItemModel;
    }

    public String getHousingVehicleItemFuelAbbreviation() {
        return this.housingVehicleItemFuelAbbreviation;
    }

    public HousingVehicleItem housingVehicleItemFuelAbbreviation(String housingVehicleItemFuelAbbreviation) {
        this.housingVehicleItemFuelAbbreviation = housingVehicleItemFuelAbbreviation;
        return this;
    }

    public void setHousingVehicleItemFuelAbbreviation(String housingVehicleItemFuelAbbreviation) {
        this.housingVehicleItemFuelAbbreviation = housingVehicleItemFuelAbbreviation;
    }

    public String getHousingVehicleItemReferenceMonth() {
        return this.housingVehicleItemReferenceMonth;
    }

    public HousingVehicleItem housingVehicleItemReferenceMonth(String housingVehicleItemReferenceMonth) {
        this.housingVehicleItemReferenceMonth = housingVehicleItemReferenceMonth;
        return this;
    }

    public void setHousingVehicleItemReferenceMonth(String housingVehicleItemReferenceMonth) {
        this.housingVehicleItemReferenceMonth = housingVehicleItemReferenceMonth;
    }

    public Float getHousingVehicleItemValue() {
        return this.housingVehicleItemValue;
    }

    public HousingVehicleItem housingVehicleItemValue(Float housingVehicleItemValue) {
        this.housingVehicleItemValue = housingVehicleItemValue;
        return this;
    }

    public void setHousingVehicleItemValue(Float housingVehicleItemValue) {
        this.housingVehicleItemValue = housingVehicleItemValue;
    }

    public Float getHousingVehicleItemShippingValue() {
        return this.housingVehicleItemShippingValue;
    }

    public HousingVehicleItem housingVehicleItemShippingValue(Float housingVehicleItemShippingValue) {
        this.housingVehicleItemShippingValue = housingVehicleItemShippingValue;
        return this;
    }

    public void setHousingVehicleItemShippingValue(Float housingVehicleItemShippingValue) {
        this.housingVehicleItemShippingValue = housingVehicleItemShippingValue;
    }

    public Housing getHousing() {
        return this.housing;
    }

    public HousingVehicleItem housing(Housing housing) {
        this.setHousing(housing);
        return this;
    }

    public void setHousing(Housing housing) {
        this.housing = housing;
    }

    public ParkingSector getParkingSector() {
        return this.parkingSector;
    }

    public HousingVehicleItem parkingSector(ParkingSector parkingSector) {
        this.setParkingSector(parkingSector);
        return this;
    }

    public void setParkingSector(ParkingSector parkingSector) {
        this.parkingSector = parkingSector;
    }

    public ParkingSectorSpace getParkingSectorSpace() {
        return this.parkingSectorSpace;
    }

    public HousingVehicleItem parkingSectorSpace(ParkingSectorSpace parkingSectorSpace) {
        this.setParkingSectorSpace(parkingSectorSpace);
        return this;
    }

    public void setParkingSectorSpace(ParkingSectorSpace parkingSectorSpace) {
        this.parkingSectorSpace = parkingSectorSpace;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HousingVehicleItem)) {
            return false;
        }
        return id != null && id.equals(((HousingVehicleItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HousingVehicleItem{" +
            "id=" + getId() +
            ", housingVehicleItemStatus='" + getHousingVehicleItemStatus() + "'" +
            ", housingVehicleItemPlate='" + getHousingVehicleItemPlate() + "'" +
            ", housingVehicleItemType='" + getHousingVehicleItemType() + "'" +
            ", housingVehicleItemFipeCode='" + getHousingVehicleItemFipeCode() + "'" +
            ", housingVehicleItemYear='" + getHousingVehicleItemYear() + "'" +
            ", housingVehicleItemFuel='" + getHousingVehicleItemFuel() + "'" +
            ", housingVehicleItemBranch='" + getHousingVehicleItemBranch() + "'" +
            ", housingVehicleItemModel='" + getHousingVehicleItemModel() + "'" +
            ", housingVehicleItemFuelAbbreviation='" + getHousingVehicleItemFuelAbbreviation() + "'" +
            ", housingVehicleItemReferenceMonth='" + getHousingVehicleItemReferenceMonth() + "'" +
            ", housingVehicleItemValue=" + getHousingVehicleItemValue() +
            ", housingVehicleItemShippingValue=" + getHousingVehicleItemShippingValue() +
            "}";
    }
}
