package com.genesisoft.transporte.service.criteria;

import com.genesisoft.transporte.domain.enumeration.DriverType;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.genesisoft.transporte.domain.VehicleControlExpenses} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.VehicleControlExpensesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vehicle-control-expenses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VehicleControlExpensesCriteria implements Serializable, Criteria {

    /**
     * Class for filtering DriverType
     */
    public static class DriverTypeFilter extends Filter<DriverType> {

        public DriverTypeFilter() {}

        public DriverTypeFilter(DriverTypeFilter filter) {
            super(filter);
        }

        @Override
        public DriverTypeFilter copy() {
            return new DriverTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter vehicleControlExpensesDescription;

    private DriverTypeFilter vehicleControlExpensesDriverType;

    private StringFilter vehicleControlExpensesPurchaseOrder;

    private LocalDateFilter vehicleControlExpensesDueDate;

    private LocalDateFilter vehicleControlExpensesPaymentDate;

    private FloatFilter vehicleControlExpensesBillingTotalValue;

    private BooleanFilter vehicleControlExpensesDriverCommission;

    private LongFilter vehicleControlsId;

    private LongFilter suppliersId;

    private LongFilter originId;

    private LongFilter destinationId;

    private LongFilter vehicleControlItemId;

    public VehicleControlExpensesCriteria() {}

    public VehicleControlExpensesCriteria(VehicleControlExpensesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.vehicleControlExpensesDescription =
            other.vehicleControlExpensesDescription == null ? null : other.vehicleControlExpensesDescription.copy();
        this.vehicleControlExpensesDriverType =
            other.vehicleControlExpensesDriverType == null ? null : other.vehicleControlExpensesDriverType.copy();
        this.vehicleControlExpensesPurchaseOrder =
            other.vehicleControlExpensesPurchaseOrder == null ? null : other.vehicleControlExpensesPurchaseOrder.copy();
        this.vehicleControlExpensesDueDate =
            other.vehicleControlExpensesDueDate == null ? null : other.vehicleControlExpensesDueDate.copy();
        this.vehicleControlExpensesPaymentDate =
            other.vehicleControlExpensesPaymentDate == null ? null : other.vehicleControlExpensesPaymentDate.copy();
        this.vehicleControlExpensesBillingTotalValue =
            other.vehicleControlExpensesBillingTotalValue == null ? null : other.vehicleControlExpensesBillingTotalValue.copy();
        this.vehicleControlExpensesDriverCommission =
            other.vehicleControlExpensesDriverCommission == null ? null : other.vehicleControlExpensesDriverCommission.copy();
        this.vehicleControlsId = other.vehicleControlsId == null ? null : other.vehicleControlsId.copy();
        this.suppliersId = other.suppliersId == null ? null : other.suppliersId.copy();
        this.originId = other.originId == null ? null : other.originId.copy();
        this.destinationId = other.destinationId == null ? null : other.destinationId.copy();
        this.vehicleControlItemId = other.vehicleControlItemId == null ? null : other.vehicleControlItemId.copy();
    }

    @Override
    public VehicleControlExpensesCriteria copy() {
        return new VehicleControlExpensesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getVehicleControlExpensesDescription() {
        return vehicleControlExpensesDescription;
    }

    public StringFilter vehicleControlExpensesDescription() {
        if (vehicleControlExpensesDescription == null) {
            vehicleControlExpensesDescription = new StringFilter();
        }
        return vehicleControlExpensesDescription;
    }

    public void setVehicleControlExpensesDescription(StringFilter vehicleControlExpensesDescription) {
        this.vehicleControlExpensesDescription = vehicleControlExpensesDescription;
    }

    public DriverTypeFilter getVehicleControlExpensesDriverType() {
        return vehicleControlExpensesDriverType;
    }

    public DriverTypeFilter vehicleControlExpensesDriverType() {
        if (vehicleControlExpensesDriverType == null) {
            vehicleControlExpensesDriverType = new DriverTypeFilter();
        }
        return vehicleControlExpensesDriverType;
    }

    public void setVehicleControlExpensesDriverType(DriverTypeFilter vehicleControlExpensesDriverType) {
        this.vehicleControlExpensesDriverType = vehicleControlExpensesDriverType;
    }

    public StringFilter getVehicleControlExpensesPurchaseOrder() {
        return vehicleControlExpensesPurchaseOrder;
    }

    public StringFilter vehicleControlExpensesPurchaseOrder() {
        if (vehicleControlExpensesPurchaseOrder == null) {
            vehicleControlExpensesPurchaseOrder = new StringFilter();
        }
        return vehicleControlExpensesPurchaseOrder;
    }

    public void setVehicleControlExpensesPurchaseOrder(StringFilter vehicleControlExpensesPurchaseOrder) {
        this.vehicleControlExpensesPurchaseOrder = vehicleControlExpensesPurchaseOrder;
    }

    public LocalDateFilter getVehicleControlExpensesDueDate() {
        return vehicleControlExpensesDueDate;
    }

    public LocalDateFilter vehicleControlExpensesDueDate() {
        if (vehicleControlExpensesDueDate == null) {
            vehicleControlExpensesDueDate = new LocalDateFilter();
        }
        return vehicleControlExpensesDueDate;
    }

    public void setVehicleControlExpensesDueDate(LocalDateFilter vehicleControlExpensesDueDate) {
        this.vehicleControlExpensesDueDate = vehicleControlExpensesDueDate;
    }

    public LocalDateFilter getVehicleControlExpensesPaymentDate() {
        return vehicleControlExpensesPaymentDate;
    }

    public LocalDateFilter vehicleControlExpensesPaymentDate() {
        if (vehicleControlExpensesPaymentDate == null) {
            vehicleControlExpensesPaymentDate = new LocalDateFilter();
        }
        return vehicleControlExpensesPaymentDate;
    }

    public void setVehicleControlExpensesPaymentDate(LocalDateFilter vehicleControlExpensesPaymentDate) {
        this.vehicleControlExpensesPaymentDate = vehicleControlExpensesPaymentDate;
    }

    public FloatFilter getVehicleControlExpensesBillingTotalValue() {
        return vehicleControlExpensesBillingTotalValue;
    }

    public FloatFilter vehicleControlExpensesBillingTotalValue() {
        if (vehicleControlExpensesBillingTotalValue == null) {
            vehicleControlExpensesBillingTotalValue = new FloatFilter();
        }
        return vehicleControlExpensesBillingTotalValue;
    }

    public void setVehicleControlExpensesBillingTotalValue(FloatFilter vehicleControlExpensesBillingTotalValue) {
        this.vehicleControlExpensesBillingTotalValue = vehicleControlExpensesBillingTotalValue;
    }

    public BooleanFilter getVehicleControlExpensesDriverCommission() {
        return vehicleControlExpensesDriverCommission;
    }

    public BooleanFilter vehicleControlExpensesDriverCommission() {
        if (vehicleControlExpensesDriverCommission == null) {
            vehicleControlExpensesDriverCommission = new BooleanFilter();
        }
        return vehicleControlExpensesDriverCommission;
    }

    public void setVehicleControlExpensesDriverCommission(BooleanFilter vehicleControlExpensesDriverCommission) {
        this.vehicleControlExpensesDriverCommission = vehicleControlExpensesDriverCommission;
    }

    public LongFilter getVehicleControlsId() {
        return vehicleControlsId;
    }

    public LongFilter vehicleControlsId() {
        if (vehicleControlsId == null) {
            vehicleControlsId = new LongFilter();
        }
        return vehicleControlsId;
    }

    public void setVehicleControlsId(LongFilter vehicleControlsId) {
        this.vehicleControlsId = vehicleControlsId;
    }

    public LongFilter getSuppliersId() {
        return suppliersId;
    }

    public LongFilter suppliersId() {
        if (suppliersId == null) {
            suppliersId = new LongFilter();
        }
        return suppliersId;
    }

    public void setSuppliersId(LongFilter suppliersId) {
        this.suppliersId = suppliersId;
    }

    public LongFilter getOriginId() {
        return originId;
    }

    public LongFilter originId() {
        if (originId == null) {
            originId = new LongFilter();
        }
        return originId;
    }

    public void setOriginId(LongFilter originId) {
        this.originId = originId;
    }

    public LongFilter getDestinationId() {
        return destinationId;
    }

    public LongFilter destinationId() {
        if (destinationId == null) {
            destinationId = new LongFilter();
        }
        return destinationId;
    }

    public void setDestinationId(LongFilter destinationId) {
        this.destinationId = destinationId;
    }

    public LongFilter getVehicleControlItemId() {
        return vehicleControlItemId;
    }

    public LongFilter vehicleControlItemId() {
        if (vehicleControlItemId == null) {
            vehicleControlItemId = new LongFilter();
        }
        return vehicleControlItemId;
    }

    public void setVehicleControlItemId(LongFilter vehicleControlItemId) {
        this.vehicleControlItemId = vehicleControlItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VehicleControlExpensesCriteria that = (VehicleControlExpensesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(vehicleControlExpensesDescription, that.vehicleControlExpensesDescription) &&
            Objects.equals(vehicleControlExpensesDriverType, that.vehicleControlExpensesDriverType) &&
            Objects.equals(vehicleControlExpensesPurchaseOrder, that.vehicleControlExpensesPurchaseOrder) &&
            Objects.equals(vehicleControlExpensesDueDate, that.vehicleControlExpensesDueDate) &&
            Objects.equals(vehicleControlExpensesPaymentDate, that.vehicleControlExpensesPaymentDate) &&
            Objects.equals(vehicleControlExpensesBillingTotalValue, that.vehicleControlExpensesBillingTotalValue) &&
            Objects.equals(vehicleControlExpensesDriverCommission, that.vehicleControlExpensesDriverCommission) &&
            Objects.equals(vehicleControlsId, that.vehicleControlsId) &&
            Objects.equals(suppliersId, that.suppliersId) &&
            Objects.equals(originId, that.originId) &&
            Objects.equals(destinationId, that.destinationId) &&
            Objects.equals(vehicleControlItemId, that.vehicleControlItemId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            vehicleControlExpensesDescription,
            vehicleControlExpensesDriverType,
            vehicleControlExpensesPurchaseOrder,
            vehicleControlExpensesDueDate,
            vehicleControlExpensesPaymentDate,
            vehicleControlExpensesBillingTotalValue,
            vehicleControlExpensesDriverCommission,
            vehicleControlsId,
            suppliersId,
            originId,
            destinationId,
            vehicleControlItemId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleControlExpensesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (vehicleControlExpensesDescription != null ? "vehicleControlExpensesDescription=" + vehicleControlExpensesDescription + ", " : "") +
            (vehicleControlExpensesDriverType != null ? "vehicleControlExpensesDriverType=" + vehicleControlExpensesDriverType + ", " : "") +
            (vehicleControlExpensesPurchaseOrder != null ? "vehicleControlExpensesPurchaseOrder=" + vehicleControlExpensesPurchaseOrder + ", " : "") +
            (vehicleControlExpensesDueDate != null ? "vehicleControlExpensesDueDate=" + vehicleControlExpensesDueDate + ", " : "") +
            (vehicleControlExpensesPaymentDate != null ? "vehicleControlExpensesPaymentDate=" + vehicleControlExpensesPaymentDate + ", " : "") +
            (vehicleControlExpensesBillingTotalValue != null ? "vehicleControlExpensesBillingTotalValue=" + vehicleControlExpensesBillingTotalValue + ", " : "") +
            (vehicleControlExpensesDriverCommission != null ? "vehicleControlExpensesDriverCommission=" + vehicleControlExpensesDriverCommission + ", " : "") +
            (vehicleControlsId != null ? "vehicleControlsId=" + vehicleControlsId + ", " : "") +
            (suppliersId != null ? "suppliersId=" + suppliersId + ", " : "") +
            (originId != null ? "originId=" + originId + ", " : "") +
            (destinationId != null ? "destinationId=" + destinationId + ", " : "") +
            (vehicleControlItemId != null ? "vehicleControlItemId=" + vehicleControlItemId + ", " : "") +
            "}";
    }
}
