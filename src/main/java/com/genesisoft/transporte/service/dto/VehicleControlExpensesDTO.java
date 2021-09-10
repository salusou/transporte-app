package com.genesisoft.transporte.service.dto;

import com.genesisoft.transporte.domain.enumeration.DriverType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.VehicleControlExpenses} entity.
 */
public class VehicleControlExpensesDTO implements Serializable {

    private Long id;

    @NotNull
    private String vehicleControlExpensesDescription;

    private DriverType vehicleControlExpensesDriverType;

    private String vehicleControlExpensesPurchaseOrder;

    private LocalDate vehicleControlExpensesDueDate;

    private LocalDate vehicleControlExpensesPaymentDate;

    private Float vehicleControlExpensesBillingTotalValue;

    private Boolean vehicleControlExpensesDriverCommission;

    private VehicleControlsDTO vehicleControls;

    private SuppliersDTO suppliers;

    private CitiesDTO origin;

    private CitiesDTO destination;

    private VehicleControlItemDTO vehicleControlItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleControlExpensesDescription() {
        return vehicleControlExpensesDescription;
    }

    public void setVehicleControlExpensesDescription(String vehicleControlExpensesDescription) {
        this.vehicleControlExpensesDescription = vehicleControlExpensesDescription;
    }

    public DriverType getVehicleControlExpensesDriverType() {
        return vehicleControlExpensesDriverType;
    }

    public void setVehicleControlExpensesDriverType(DriverType vehicleControlExpensesDriverType) {
        this.vehicleControlExpensesDriverType = vehicleControlExpensesDriverType;
    }

    public String getVehicleControlExpensesPurchaseOrder() {
        return vehicleControlExpensesPurchaseOrder;
    }

    public void setVehicleControlExpensesPurchaseOrder(String vehicleControlExpensesPurchaseOrder) {
        this.vehicleControlExpensesPurchaseOrder = vehicleControlExpensesPurchaseOrder;
    }

    public LocalDate getVehicleControlExpensesDueDate() {
        return vehicleControlExpensesDueDate;
    }

    public void setVehicleControlExpensesDueDate(LocalDate vehicleControlExpensesDueDate) {
        this.vehicleControlExpensesDueDate = vehicleControlExpensesDueDate;
    }

    public LocalDate getVehicleControlExpensesPaymentDate() {
        return vehicleControlExpensesPaymentDate;
    }

    public void setVehicleControlExpensesPaymentDate(LocalDate vehicleControlExpensesPaymentDate) {
        this.vehicleControlExpensesPaymentDate = vehicleControlExpensesPaymentDate;
    }

    public Float getVehicleControlExpensesBillingTotalValue() {
        return vehicleControlExpensesBillingTotalValue;
    }

    public void setVehicleControlExpensesBillingTotalValue(Float vehicleControlExpensesBillingTotalValue) {
        this.vehicleControlExpensesBillingTotalValue = vehicleControlExpensesBillingTotalValue;
    }

    public Boolean getVehicleControlExpensesDriverCommission() {
        return vehicleControlExpensesDriverCommission;
    }

    public void setVehicleControlExpensesDriverCommission(Boolean vehicleControlExpensesDriverCommission) {
        this.vehicleControlExpensesDriverCommission = vehicleControlExpensesDriverCommission;
    }

    public VehicleControlsDTO getVehicleControls() {
        return vehicleControls;
    }

    public void setVehicleControls(VehicleControlsDTO vehicleControls) {
        this.vehicleControls = vehicleControls;
    }

    public SuppliersDTO getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(SuppliersDTO suppliers) {
        this.suppliers = suppliers;
    }

    public CitiesDTO getOrigin() {
        return origin;
    }

    public void setOrigin(CitiesDTO origin) {
        this.origin = origin;
    }

    public CitiesDTO getDestination() {
        return destination;
    }

    public void setDestination(CitiesDTO destination) {
        this.destination = destination;
    }

    public VehicleControlItemDTO getVehicleControlItem() {
        return vehicleControlItem;
    }

    public void setVehicleControlItem(VehicleControlItemDTO vehicleControlItem) {
        this.vehicleControlItem = vehicleControlItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleControlExpensesDTO)) {
            return false;
        }

        VehicleControlExpensesDTO vehicleControlExpensesDTO = (VehicleControlExpensesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vehicleControlExpensesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleControlExpensesDTO{" +
            "id=" + getId() +
            ", vehicleControlExpensesDescription='" + getVehicleControlExpensesDescription() + "'" +
            ", vehicleControlExpensesDriverType='" + getVehicleControlExpensesDriverType() + "'" +
            ", vehicleControlExpensesPurchaseOrder='" + getVehicleControlExpensesPurchaseOrder() + "'" +
            ", vehicleControlExpensesDueDate='" + getVehicleControlExpensesDueDate() + "'" +
            ", vehicleControlExpensesPaymentDate='" + getVehicleControlExpensesPaymentDate() + "'" +
            ", vehicleControlExpensesBillingTotalValue=" + getVehicleControlExpensesBillingTotalValue() +
            ", vehicleControlExpensesDriverCommission='" + getVehicleControlExpensesDriverCommission() + "'" +
            ", vehicleControls=" + getVehicleControls() +
            ", suppliers=" + getSuppliers() +
            ", origin=" + getOrigin() +
            ", destination=" + getDestination() +
            ", vehicleControlItem=" + getVehicleControlItem() +
            "}";
    }
}
