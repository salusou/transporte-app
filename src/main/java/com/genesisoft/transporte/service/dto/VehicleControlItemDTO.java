package com.genesisoft.transporte.service.dto;

import com.genesisoft.transporte.domain.enumeration.StatusType;
import com.genesisoft.transporte.domain.enumeration.VehicleType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.VehicleControlItem} entity.
 */
public class VehicleControlItemDTO implements Serializable {

    private Long id;

    @NotNull
    private StatusType vehicleControlStatus;

    @NotNull
    private String vehicleControlItemPlate;

    @NotNull
    private VehicleType vehicleControlItemType;

    private String vehicleControlItemFipeCode;

    private String vehicleControlItemYear;

    private String vehicleControlItemFuel;

    private String vehicleControlItemBranch;

    private String vehicleControlItemModel;

    private String vehicleControlItemFuelAbbreviation;

    private String vehicleControlItemReferenceMonth;

    @NotNull
    private Float vehicleControlItemValue;

    @NotNull
    private Float vehicleControlItemShippingValue;

    private String vehicleControlItemCTE;

    private LocalDate vehicleControlItemCTEDate;

    private VehicleControlsDTO vehicleControls;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusType getVehicleControlStatus() {
        return vehicleControlStatus;
    }

    public void setVehicleControlStatus(StatusType vehicleControlStatus) {
        this.vehicleControlStatus = vehicleControlStatus;
    }

    public String getVehicleControlItemPlate() {
        return vehicleControlItemPlate;
    }

    public void setVehicleControlItemPlate(String vehicleControlItemPlate) {
        this.vehicleControlItemPlate = vehicleControlItemPlate;
    }

    public VehicleType getVehicleControlItemType() {
        return vehicleControlItemType;
    }

    public void setVehicleControlItemType(VehicleType vehicleControlItemType) {
        this.vehicleControlItemType = vehicleControlItemType;
    }

    public String getVehicleControlItemFipeCode() {
        return vehicleControlItemFipeCode;
    }

    public void setVehicleControlItemFipeCode(String vehicleControlItemFipeCode) {
        this.vehicleControlItemFipeCode = vehicleControlItemFipeCode;
    }

    public String getVehicleControlItemYear() {
        return vehicleControlItemYear;
    }

    public void setVehicleControlItemYear(String vehicleControlItemYear) {
        this.vehicleControlItemYear = vehicleControlItemYear;
    }

    public String getVehicleControlItemFuel() {
        return vehicleControlItemFuel;
    }

    public void setVehicleControlItemFuel(String vehicleControlItemFuel) {
        this.vehicleControlItemFuel = vehicleControlItemFuel;
    }

    public String getVehicleControlItemBranch() {
        return vehicleControlItemBranch;
    }

    public void setVehicleControlItemBranch(String vehicleControlItemBranch) {
        this.vehicleControlItemBranch = vehicleControlItemBranch;
    }

    public String getVehicleControlItemModel() {
        return vehicleControlItemModel;
    }

    public void setVehicleControlItemModel(String vehicleControlItemModel) {
        this.vehicleControlItemModel = vehicleControlItemModel;
    }

    public String getVehicleControlItemFuelAbbreviation() {
        return vehicleControlItemFuelAbbreviation;
    }

    public void setVehicleControlItemFuelAbbreviation(String vehicleControlItemFuelAbbreviation) {
        this.vehicleControlItemFuelAbbreviation = vehicleControlItemFuelAbbreviation;
    }

    public String getVehicleControlItemReferenceMonth() {
        return vehicleControlItemReferenceMonth;
    }

    public void setVehicleControlItemReferenceMonth(String vehicleControlItemReferenceMonth) {
        this.vehicleControlItemReferenceMonth = vehicleControlItemReferenceMonth;
    }

    public Float getVehicleControlItemValue() {
        return vehicleControlItemValue;
    }

    public void setVehicleControlItemValue(Float vehicleControlItemValue) {
        this.vehicleControlItemValue = vehicleControlItemValue;
    }

    public Float getVehicleControlItemShippingValue() {
        return vehicleControlItemShippingValue;
    }

    public void setVehicleControlItemShippingValue(Float vehicleControlItemShippingValue) {
        this.vehicleControlItemShippingValue = vehicleControlItemShippingValue;
    }

    public String getVehicleControlItemCTE() {
        return vehicleControlItemCTE;
    }

    public void setVehicleControlItemCTE(String vehicleControlItemCTE) {
        this.vehicleControlItemCTE = vehicleControlItemCTE;
    }

    public LocalDate getVehicleControlItemCTEDate() {
        return vehicleControlItemCTEDate;
    }

    public void setVehicleControlItemCTEDate(LocalDate vehicleControlItemCTEDate) {
        this.vehicleControlItemCTEDate = vehicleControlItemCTEDate;
    }

    public VehicleControlsDTO getVehicleControls() {
        return vehicleControls;
    }

    public void setVehicleControls(VehicleControlsDTO vehicleControls) {
        this.vehicleControls = vehicleControls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleControlItemDTO)) {
            return false;
        }

        VehicleControlItemDTO vehicleControlItemDTO = (VehicleControlItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vehicleControlItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleControlItemDTO{" +
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
            ", vehicleControls=" + getVehicleControls() +
            "}";
    }
}
