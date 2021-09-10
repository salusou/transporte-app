package com.genesisoft.transporte.service.dto;

import com.genesisoft.transporte.domain.enumeration.StatusType;
import com.genesisoft.transporte.domain.enumeration.VehicleType;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.HousingVehicleItem} entity.
 */
public class HousingVehicleItemDTO implements Serializable {

    private Long id;

    @NotNull
    private StatusType housingVehicleItemStatus;

    @NotNull
    private String housingVehicleItemPlate;

    @NotNull
    private VehicleType housingVehicleItemType;

    private String housingVehicleItemFipeCode;

    private String housingVehicleItemYear;

    private String housingVehicleItemFuel;

    private String housingVehicleItemBranch;

    private String housingVehicleItemModel;

    private String housingVehicleItemFuelAbbreviation;

    private String housingVehicleItemReferenceMonth;

    @NotNull
    private Float housingVehicleItemValue;

    @NotNull
    private Float housingVehicleItemShippingValue;

    private HousingDTO housing;

    private ParkingSectorDTO parkingSector;

    private ParkingSectorSpaceDTO parkingSectorSpace;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusType getHousingVehicleItemStatus() {
        return housingVehicleItemStatus;
    }

    public void setHousingVehicleItemStatus(StatusType housingVehicleItemStatus) {
        this.housingVehicleItemStatus = housingVehicleItemStatus;
    }

    public String getHousingVehicleItemPlate() {
        return housingVehicleItemPlate;
    }

    public void setHousingVehicleItemPlate(String housingVehicleItemPlate) {
        this.housingVehicleItemPlate = housingVehicleItemPlate;
    }

    public VehicleType getHousingVehicleItemType() {
        return housingVehicleItemType;
    }

    public void setHousingVehicleItemType(VehicleType housingVehicleItemType) {
        this.housingVehicleItemType = housingVehicleItemType;
    }

    public String getHousingVehicleItemFipeCode() {
        return housingVehicleItemFipeCode;
    }

    public void setHousingVehicleItemFipeCode(String housingVehicleItemFipeCode) {
        this.housingVehicleItemFipeCode = housingVehicleItemFipeCode;
    }

    public String getHousingVehicleItemYear() {
        return housingVehicleItemYear;
    }

    public void setHousingVehicleItemYear(String housingVehicleItemYear) {
        this.housingVehicleItemYear = housingVehicleItemYear;
    }

    public String getHousingVehicleItemFuel() {
        return housingVehicleItemFuel;
    }

    public void setHousingVehicleItemFuel(String housingVehicleItemFuel) {
        this.housingVehicleItemFuel = housingVehicleItemFuel;
    }

    public String getHousingVehicleItemBranch() {
        return housingVehicleItemBranch;
    }

    public void setHousingVehicleItemBranch(String housingVehicleItemBranch) {
        this.housingVehicleItemBranch = housingVehicleItemBranch;
    }

    public String getHousingVehicleItemModel() {
        return housingVehicleItemModel;
    }

    public void setHousingVehicleItemModel(String housingVehicleItemModel) {
        this.housingVehicleItemModel = housingVehicleItemModel;
    }

    public String getHousingVehicleItemFuelAbbreviation() {
        return housingVehicleItemFuelAbbreviation;
    }

    public void setHousingVehicleItemFuelAbbreviation(String housingVehicleItemFuelAbbreviation) {
        this.housingVehicleItemFuelAbbreviation = housingVehicleItemFuelAbbreviation;
    }

    public String getHousingVehicleItemReferenceMonth() {
        return housingVehicleItemReferenceMonth;
    }

    public void setHousingVehicleItemReferenceMonth(String housingVehicleItemReferenceMonth) {
        this.housingVehicleItemReferenceMonth = housingVehicleItemReferenceMonth;
    }

    public Float getHousingVehicleItemValue() {
        return housingVehicleItemValue;
    }

    public void setHousingVehicleItemValue(Float housingVehicleItemValue) {
        this.housingVehicleItemValue = housingVehicleItemValue;
    }

    public Float getHousingVehicleItemShippingValue() {
        return housingVehicleItemShippingValue;
    }

    public void setHousingVehicleItemShippingValue(Float housingVehicleItemShippingValue) {
        this.housingVehicleItemShippingValue = housingVehicleItemShippingValue;
    }

    public HousingDTO getHousing() {
        return housing;
    }

    public void setHousing(HousingDTO housing) {
        this.housing = housing;
    }

    public ParkingSectorDTO getParkingSector() {
        return parkingSector;
    }

    public void setParkingSector(ParkingSectorDTO parkingSector) {
        this.parkingSector = parkingSector;
    }

    public ParkingSectorSpaceDTO getParkingSectorSpace() {
        return parkingSectorSpace;
    }

    public void setParkingSectorSpace(ParkingSectorSpaceDTO parkingSectorSpace) {
        this.parkingSectorSpace = parkingSectorSpace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HousingVehicleItemDTO)) {
            return false;
        }

        HousingVehicleItemDTO housingVehicleItemDTO = (HousingVehicleItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, housingVehicleItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HousingVehicleItemDTO{" +
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
            ", housing=" + getHousing() +
            ", parkingSector=" + getParkingSector() +
            ", parkingSectorSpace=" + getParkingSectorSpace() +
            "}";
    }
}
