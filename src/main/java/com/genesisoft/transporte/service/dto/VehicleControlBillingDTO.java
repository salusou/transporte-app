package com.genesisoft.transporte.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.VehicleControlBilling} entity.
 */
public class VehicleControlBillingDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate vehicleControlBillingDate;

    private LocalDate vehicleControlBillingExpirationDate;

    private LocalDate vehicleControlBillingPaymentDate;

    private Boolean vehicleControlBillingSellerCommission;

    private Boolean vehicleControlBillingDriverCommission;

    @NotNull
    private Integer vehicleControlBillingAmount;

    @NotNull
    private Float vehicleControlBillingTotalValue;

    private Float vehicleControlBillingInsuranceDiscount;

    private String vehicleControlBillingCustomerBank;

    private Boolean vehicleControlBillingAnticipate;

    private String vehicleControlBillingManifest;

    private VehicleControlsDTO vehicleControls;

    private FeesDTO fees;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getVehicleControlBillingDate() {
        return vehicleControlBillingDate;
    }

    public void setVehicleControlBillingDate(LocalDate vehicleControlBillingDate) {
        this.vehicleControlBillingDate = vehicleControlBillingDate;
    }

    public LocalDate getVehicleControlBillingExpirationDate() {
        return vehicleControlBillingExpirationDate;
    }

    public void setVehicleControlBillingExpirationDate(LocalDate vehicleControlBillingExpirationDate) {
        this.vehicleControlBillingExpirationDate = vehicleControlBillingExpirationDate;
    }

    public LocalDate getVehicleControlBillingPaymentDate() {
        return vehicleControlBillingPaymentDate;
    }

    public void setVehicleControlBillingPaymentDate(LocalDate vehicleControlBillingPaymentDate) {
        this.vehicleControlBillingPaymentDate = vehicleControlBillingPaymentDate;
    }

    public Boolean getVehicleControlBillingSellerCommission() {
        return vehicleControlBillingSellerCommission;
    }

    public void setVehicleControlBillingSellerCommission(Boolean vehicleControlBillingSellerCommission) {
        this.vehicleControlBillingSellerCommission = vehicleControlBillingSellerCommission;
    }

    public Boolean getVehicleControlBillingDriverCommission() {
        return vehicleControlBillingDriverCommission;
    }

    public void setVehicleControlBillingDriverCommission(Boolean vehicleControlBillingDriverCommission) {
        this.vehicleControlBillingDriverCommission = vehicleControlBillingDriverCommission;
    }

    public Integer getVehicleControlBillingAmount() {
        return vehicleControlBillingAmount;
    }

    public void setVehicleControlBillingAmount(Integer vehicleControlBillingAmount) {
        this.vehicleControlBillingAmount = vehicleControlBillingAmount;
    }

    public Float getVehicleControlBillingTotalValue() {
        return vehicleControlBillingTotalValue;
    }

    public void setVehicleControlBillingTotalValue(Float vehicleControlBillingTotalValue) {
        this.vehicleControlBillingTotalValue = vehicleControlBillingTotalValue;
    }

    public Float getVehicleControlBillingInsuranceDiscount() {
        return vehicleControlBillingInsuranceDiscount;
    }

    public void setVehicleControlBillingInsuranceDiscount(Float vehicleControlBillingInsuranceDiscount) {
        this.vehicleControlBillingInsuranceDiscount = vehicleControlBillingInsuranceDiscount;
    }

    public String getVehicleControlBillingCustomerBank() {
        return vehicleControlBillingCustomerBank;
    }

    public void setVehicleControlBillingCustomerBank(String vehicleControlBillingCustomerBank) {
        this.vehicleControlBillingCustomerBank = vehicleControlBillingCustomerBank;
    }

    public Boolean getVehicleControlBillingAnticipate() {
        return vehicleControlBillingAnticipate;
    }

    public void setVehicleControlBillingAnticipate(Boolean vehicleControlBillingAnticipate) {
        this.vehicleControlBillingAnticipate = vehicleControlBillingAnticipate;
    }

    public String getVehicleControlBillingManifest() {
        return vehicleControlBillingManifest;
    }

    public void setVehicleControlBillingManifest(String vehicleControlBillingManifest) {
        this.vehicleControlBillingManifest = vehicleControlBillingManifest;
    }

    public VehicleControlsDTO getVehicleControls() {
        return vehicleControls;
    }

    public void setVehicleControls(VehicleControlsDTO vehicleControls) {
        this.vehicleControls = vehicleControls;
    }

    public FeesDTO getFees() {
        return fees;
    }

    public void setFees(FeesDTO fees) {
        this.fees = fees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleControlBillingDTO)) {
            return false;
        }

        VehicleControlBillingDTO vehicleControlBillingDTO = (VehicleControlBillingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vehicleControlBillingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleControlBillingDTO{" +
            "id=" + getId() +
            ", vehicleControlBillingDate='" + getVehicleControlBillingDate() + "'" +
            ", vehicleControlBillingExpirationDate='" + getVehicleControlBillingExpirationDate() + "'" +
            ", vehicleControlBillingPaymentDate='" + getVehicleControlBillingPaymentDate() + "'" +
            ", vehicleControlBillingSellerCommission='" + getVehicleControlBillingSellerCommission() + "'" +
            ", vehicleControlBillingDriverCommission='" + getVehicleControlBillingDriverCommission() + "'" +
            ", vehicleControlBillingAmount=" + getVehicleControlBillingAmount() +
            ", vehicleControlBillingTotalValue=" + getVehicleControlBillingTotalValue() +
            ", vehicleControlBillingInsuranceDiscount=" + getVehicleControlBillingInsuranceDiscount() +
            ", vehicleControlBillingCustomerBank='" + getVehicleControlBillingCustomerBank() + "'" +
            ", vehicleControlBillingAnticipate='" + getVehicleControlBillingAnticipate() + "'" +
            ", vehicleControlBillingManifest='" + getVehicleControlBillingManifest() + "'" +
            ", vehicleControls=" + getVehicleControls() +
            ", fees=" + getFees() +
            "}";
    }
}
