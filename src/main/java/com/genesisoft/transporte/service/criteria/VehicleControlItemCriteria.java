package com.genesisoft.transporte.service.criteria;

import com.genesisoft.transporte.domain.enumeration.StatusType;
import com.genesisoft.transporte.domain.enumeration.VehicleType;
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
 * Criteria class for the {@link com.genesisoft.transporte.domain.VehicleControlItem} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.VehicleControlItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vehicle-control-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VehicleControlItemCriteria implements Serializable, Criteria {

    /**
     * Class for filtering StatusType
     */
    public static class StatusTypeFilter extends Filter<StatusType> {

        public StatusTypeFilter() {}

        public StatusTypeFilter(StatusTypeFilter filter) {
            super(filter);
        }

        @Override
        public StatusTypeFilter copy() {
            return new StatusTypeFilter(this);
        }
    }

    /**
     * Class for filtering VehicleType
     */
    public static class VehicleTypeFilter extends Filter<VehicleType> {

        public VehicleTypeFilter() {}

        public VehicleTypeFilter(VehicleTypeFilter filter) {
            super(filter);
        }

        @Override
        public VehicleTypeFilter copy() {
            return new VehicleTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StatusTypeFilter vehicleControlStatus;

    private StringFilter vehicleControlItemPlate;

    private VehicleTypeFilter vehicleControlItemType;

    private StringFilter vehicleControlItemFipeCode;

    private StringFilter vehicleControlItemYear;

    private StringFilter vehicleControlItemFuel;

    private StringFilter vehicleControlItemBranch;

    private StringFilter vehicleControlItemModel;

    private StringFilter vehicleControlItemFuelAbbreviation;

    private StringFilter vehicleControlItemReferenceMonth;

    private FloatFilter vehicleControlItemValue;

    private FloatFilter vehicleControlItemShippingValue;

    private StringFilter vehicleControlItemCTE;

    private LocalDateFilter vehicleControlItemCTEDate;

    private LongFilter vehicleInspectionsId;

    private LongFilter vehicleControlExpensesId;

    private LongFilter vehicleControlsId;

    public VehicleControlItemCriteria() {}

    public VehicleControlItemCriteria(VehicleControlItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.vehicleControlStatus = other.vehicleControlStatus == null ? null : other.vehicleControlStatus.copy();
        this.vehicleControlItemPlate = other.vehicleControlItemPlate == null ? null : other.vehicleControlItemPlate.copy();
        this.vehicleControlItemType = other.vehicleControlItemType == null ? null : other.vehicleControlItemType.copy();
        this.vehicleControlItemFipeCode = other.vehicleControlItemFipeCode == null ? null : other.vehicleControlItemFipeCode.copy();
        this.vehicleControlItemYear = other.vehicleControlItemYear == null ? null : other.vehicleControlItemYear.copy();
        this.vehicleControlItemFuel = other.vehicleControlItemFuel == null ? null : other.vehicleControlItemFuel.copy();
        this.vehicleControlItemBranch = other.vehicleControlItemBranch == null ? null : other.vehicleControlItemBranch.copy();
        this.vehicleControlItemModel = other.vehicleControlItemModel == null ? null : other.vehicleControlItemModel.copy();
        this.vehicleControlItemFuelAbbreviation =
            other.vehicleControlItemFuelAbbreviation == null ? null : other.vehicleControlItemFuelAbbreviation.copy();
        this.vehicleControlItemReferenceMonth =
            other.vehicleControlItemReferenceMonth == null ? null : other.vehicleControlItemReferenceMonth.copy();
        this.vehicleControlItemValue = other.vehicleControlItemValue == null ? null : other.vehicleControlItemValue.copy();
        this.vehicleControlItemShippingValue =
            other.vehicleControlItemShippingValue == null ? null : other.vehicleControlItemShippingValue.copy();
        this.vehicleControlItemCTE = other.vehicleControlItemCTE == null ? null : other.vehicleControlItemCTE.copy();
        this.vehicleControlItemCTEDate = other.vehicleControlItemCTEDate == null ? null : other.vehicleControlItemCTEDate.copy();
        this.vehicleInspectionsId = other.vehicleInspectionsId == null ? null : other.vehicleInspectionsId.copy();
        this.vehicleControlExpensesId = other.vehicleControlExpensesId == null ? null : other.vehicleControlExpensesId.copy();
        this.vehicleControlsId = other.vehicleControlsId == null ? null : other.vehicleControlsId.copy();
    }

    @Override
    public VehicleControlItemCriteria copy() {
        return new VehicleControlItemCriteria(this);
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

    public StatusTypeFilter getVehicleControlStatus() {
        return vehicleControlStatus;
    }

    public StatusTypeFilter vehicleControlStatus() {
        if (vehicleControlStatus == null) {
            vehicleControlStatus = new StatusTypeFilter();
        }
        return vehicleControlStatus;
    }

    public void setVehicleControlStatus(StatusTypeFilter vehicleControlStatus) {
        this.vehicleControlStatus = vehicleControlStatus;
    }

    public StringFilter getVehicleControlItemPlate() {
        return vehicleControlItemPlate;
    }

    public StringFilter vehicleControlItemPlate() {
        if (vehicleControlItemPlate == null) {
            vehicleControlItemPlate = new StringFilter();
        }
        return vehicleControlItemPlate;
    }

    public void setVehicleControlItemPlate(StringFilter vehicleControlItemPlate) {
        this.vehicleControlItemPlate = vehicleControlItemPlate;
    }

    public VehicleTypeFilter getVehicleControlItemType() {
        return vehicleControlItemType;
    }

    public VehicleTypeFilter vehicleControlItemType() {
        if (vehicleControlItemType == null) {
            vehicleControlItemType = new VehicleTypeFilter();
        }
        return vehicleControlItemType;
    }

    public void setVehicleControlItemType(VehicleTypeFilter vehicleControlItemType) {
        this.vehicleControlItemType = vehicleControlItemType;
    }

    public StringFilter getVehicleControlItemFipeCode() {
        return vehicleControlItemFipeCode;
    }

    public StringFilter vehicleControlItemFipeCode() {
        if (vehicleControlItemFipeCode == null) {
            vehicleControlItemFipeCode = new StringFilter();
        }
        return vehicleControlItemFipeCode;
    }

    public void setVehicleControlItemFipeCode(StringFilter vehicleControlItemFipeCode) {
        this.vehicleControlItemFipeCode = vehicleControlItemFipeCode;
    }

    public StringFilter getVehicleControlItemYear() {
        return vehicleControlItemYear;
    }

    public StringFilter vehicleControlItemYear() {
        if (vehicleControlItemYear == null) {
            vehicleControlItemYear = new StringFilter();
        }
        return vehicleControlItemYear;
    }

    public void setVehicleControlItemYear(StringFilter vehicleControlItemYear) {
        this.vehicleControlItemYear = vehicleControlItemYear;
    }

    public StringFilter getVehicleControlItemFuel() {
        return vehicleControlItemFuel;
    }

    public StringFilter vehicleControlItemFuel() {
        if (vehicleControlItemFuel == null) {
            vehicleControlItemFuel = new StringFilter();
        }
        return vehicleControlItemFuel;
    }

    public void setVehicleControlItemFuel(StringFilter vehicleControlItemFuel) {
        this.vehicleControlItemFuel = vehicleControlItemFuel;
    }

    public StringFilter getVehicleControlItemBranch() {
        return vehicleControlItemBranch;
    }

    public StringFilter vehicleControlItemBranch() {
        if (vehicleControlItemBranch == null) {
            vehicleControlItemBranch = new StringFilter();
        }
        return vehicleControlItemBranch;
    }

    public void setVehicleControlItemBranch(StringFilter vehicleControlItemBranch) {
        this.vehicleControlItemBranch = vehicleControlItemBranch;
    }

    public StringFilter getVehicleControlItemModel() {
        return vehicleControlItemModel;
    }

    public StringFilter vehicleControlItemModel() {
        if (vehicleControlItemModel == null) {
            vehicleControlItemModel = new StringFilter();
        }
        return vehicleControlItemModel;
    }

    public void setVehicleControlItemModel(StringFilter vehicleControlItemModel) {
        this.vehicleControlItemModel = vehicleControlItemModel;
    }

    public StringFilter getVehicleControlItemFuelAbbreviation() {
        return vehicleControlItemFuelAbbreviation;
    }

    public StringFilter vehicleControlItemFuelAbbreviation() {
        if (vehicleControlItemFuelAbbreviation == null) {
            vehicleControlItemFuelAbbreviation = new StringFilter();
        }
        return vehicleControlItemFuelAbbreviation;
    }

    public void setVehicleControlItemFuelAbbreviation(StringFilter vehicleControlItemFuelAbbreviation) {
        this.vehicleControlItemFuelAbbreviation = vehicleControlItemFuelAbbreviation;
    }

    public StringFilter getVehicleControlItemReferenceMonth() {
        return vehicleControlItemReferenceMonth;
    }

    public StringFilter vehicleControlItemReferenceMonth() {
        if (vehicleControlItemReferenceMonth == null) {
            vehicleControlItemReferenceMonth = new StringFilter();
        }
        return vehicleControlItemReferenceMonth;
    }

    public void setVehicleControlItemReferenceMonth(StringFilter vehicleControlItemReferenceMonth) {
        this.vehicleControlItemReferenceMonth = vehicleControlItemReferenceMonth;
    }

    public FloatFilter getVehicleControlItemValue() {
        return vehicleControlItemValue;
    }

    public FloatFilter vehicleControlItemValue() {
        if (vehicleControlItemValue == null) {
            vehicleControlItemValue = new FloatFilter();
        }
        return vehicleControlItemValue;
    }

    public void setVehicleControlItemValue(FloatFilter vehicleControlItemValue) {
        this.vehicleControlItemValue = vehicleControlItemValue;
    }

    public FloatFilter getVehicleControlItemShippingValue() {
        return vehicleControlItemShippingValue;
    }

    public FloatFilter vehicleControlItemShippingValue() {
        if (vehicleControlItemShippingValue == null) {
            vehicleControlItemShippingValue = new FloatFilter();
        }
        return vehicleControlItemShippingValue;
    }

    public void setVehicleControlItemShippingValue(FloatFilter vehicleControlItemShippingValue) {
        this.vehicleControlItemShippingValue = vehicleControlItemShippingValue;
    }

    public StringFilter getVehicleControlItemCTE() {
        return vehicleControlItemCTE;
    }

    public StringFilter vehicleControlItemCTE() {
        if (vehicleControlItemCTE == null) {
            vehicleControlItemCTE = new StringFilter();
        }
        return vehicleControlItemCTE;
    }

    public void setVehicleControlItemCTE(StringFilter vehicleControlItemCTE) {
        this.vehicleControlItemCTE = vehicleControlItemCTE;
    }

    public LocalDateFilter getVehicleControlItemCTEDate() {
        return vehicleControlItemCTEDate;
    }

    public LocalDateFilter vehicleControlItemCTEDate() {
        if (vehicleControlItemCTEDate == null) {
            vehicleControlItemCTEDate = new LocalDateFilter();
        }
        return vehicleControlItemCTEDate;
    }

    public void setVehicleControlItemCTEDate(LocalDateFilter vehicleControlItemCTEDate) {
        this.vehicleControlItemCTEDate = vehicleControlItemCTEDate;
    }

    public LongFilter getVehicleInspectionsId() {
        return vehicleInspectionsId;
    }

    public LongFilter vehicleInspectionsId() {
        if (vehicleInspectionsId == null) {
            vehicleInspectionsId = new LongFilter();
        }
        return vehicleInspectionsId;
    }

    public void setVehicleInspectionsId(LongFilter vehicleInspectionsId) {
        this.vehicleInspectionsId = vehicleInspectionsId;
    }

    public LongFilter getVehicleControlExpensesId() {
        return vehicleControlExpensesId;
    }

    public LongFilter vehicleControlExpensesId() {
        if (vehicleControlExpensesId == null) {
            vehicleControlExpensesId = new LongFilter();
        }
        return vehicleControlExpensesId;
    }

    public void setVehicleControlExpensesId(LongFilter vehicleControlExpensesId) {
        this.vehicleControlExpensesId = vehicleControlExpensesId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VehicleControlItemCriteria that = (VehicleControlItemCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(vehicleControlStatus, that.vehicleControlStatus) &&
            Objects.equals(vehicleControlItemPlate, that.vehicleControlItemPlate) &&
            Objects.equals(vehicleControlItemType, that.vehicleControlItemType) &&
            Objects.equals(vehicleControlItemFipeCode, that.vehicleControlItemFipeCode) &&
            Objects.equals(vehicleControlItemYear, that.vehicleControlItemYear) &&
            Objects.equals(vehicleControlItemFuel, that.vehicleControlItemFuel) &&
            Objects.equals(vehicleControlItemBranch, that.vehicleControlItemBranch) &&
            Objects.equals(vehicleControlItemModel, that.vehicleControlItemModel) &&
            Objects.equals(vehicleControlItemFuelAbbreviation, that.vehicleControlItemFuelAbbreviation) &&
            Objects.equals(vehicleControlItemReferenceMonth, that.vehicleControlItemReferenceMonth) &&
            Objects.equals(vehicleControlItemValue, that.vehicleControlItemValue) &&
            Objects.equals(vehicleControlItemShippingValue, that.vehicleControlItemShippingValue) &&
            Objects.equals(vehicleControlItemCTE, that.vehicleControlItemCTE) &&
            Objects.equals(vehicleControlItemCTEDate, that.vehicleControlItemCTEDate) &&
            Objects.equals(vehicleInspectionsId, that.vehicleInspectionsId) &&
            Objects.equals(vehicleControlExpensesId, that.vehicleControlExpensesId) &&
            Objects.equals(vehicleControlsId, that.vehicleControlsId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            vehicleControlStatus,
            vehicleControlItemPlate,
            vehicleControlItemType,
            vehicleControlItemFipeCode,
            vehicleControlItemYear,
            vehicleControlItemFuel,
            vehicleControlItemBranch,
            vehicleControlItemModel,
            vehicleControlItemFuelAbbreviation,
            vehicleControlItemReferenceMonth,
            vehicleControlItemValue,
            vehicleControlItemShippingValue,
            vehicleControlItemCTE,
            vehicleControlItemCTEDate,
            vehicleInspectionsId,
            vehicleControlExpensesId,
            vehicleControlsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleControlItemCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (vehicleControlStatus != null ? "vehicleControlStatus=" + vehicleControlStatus + ", " : "") +
            (vehicleControlItemPlate != null ? "vehicleControlItemPlate=" + vehicleControlItemPlate + ", " : "") +
            (vehicleControlItemType != null ? "vehicleControlItemType=" + vehicleControlItemType + ", " : "") +
            (vehicleControlItemFipeCode != null ? "vehicleControlItemFipeCode=" + vehicleControlItemFipeCode + ", " : "") +
            (vehicleControlItemYear != null ? "vehicleControlItemYear=" + vehicleControlItemYear + ", " : "") +
            (vehicleControlItemFuel != null ? "vehicleControlItemFuel=" + vehicleControlItemFuel + ", " : "") +
            (vehicleControlItemBranch != null ? "vehicleControlItemBranch=" + vehicleControlItemBranch + ", " : "") +
            (vehicleControlItemModel != null ? "vehicleControlItemModel=" + vehicleControlItemModel + ", " : "") +
            (vehicleControlItemFuelAbbreviation != null ? "vehicleControlItemFuelAbbreviation=" + vehicleControlItemFuelAbbreviation + ", " : "") +
            (vehicleControlItemReferenceMonth != null ? "vehicleControlItemReferenceMonth=" + vehicleControlItemReferenceMonth + ", " : "") +
            (vehicleControlItemValue != null ? "vehicleControlItemValue=" + vehicleControlItemValue + ", " : "") +
            (vehicleControlItemShippingValue != null ? "vehicleControlItemShippingValue=" + vehicleControlItemShippingValue + ", " : "") +
            (vehicleControlItemCTE != null ? "vehicleControlItemCTE=" + vehicleControlItemCTE + ", " : "") +
            (vehicleControlItemCTEDate != null ? "vehicleControlItemCTEDate=" + vehicleControlItemCTEDate + ", " : "") +
            (vehicleInspectionsId != null ? "vehicleInspectionsId=" + vehicleInspectionsId + ", " : "") +
            (vehicleControlExpensesId != null ? "vehicleControlExpensesId=" + vehicleControlExpensesId + ", " : "") +
            (vehicleControlsId != null ? "vehicleControlsId=" + vehicleControlsId + ", " : "") +
            "}";
    }
}
