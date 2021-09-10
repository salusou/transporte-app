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
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.genesisoft.transporte.domain.HousingVehicleItem} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.HousingVehicleItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /housing-vehicle-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HousingVehicleItemCriteria implements Serializable, Criteria {

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

    private StatusTypeFilter housingVehicleItemStatus;

    private StringFilter housingVehicleItemPlate;

    private VehicleTypeFilter housingVehicleItemType;

    private StringFilter housingVehicleItemFipeCode;

    private StringFilter housingVehicleItemYear;

    private StringFilter housingVehicleItemFuel;

    private StringFilter housingVehicleItemBranch;

    private StringFilter housingVehicleItemModel;

    private StringFilter housingVehicleItemFuelAbbreviation;

    private StringFilter housingVehicleItemReferenceMonth;

    private FloatFilter housingVehicleItemValue;

    private FloatFilter housingVehicleItemShippingValue;

    private LongFilter housingId;

    private LongFilter parkingSectorId;

    private LongFilter parkingSectorSpaceId;

    public HousingVehicleItemCriteria() {}

    public HousingVehicleItemCriteria(HousingVehicleItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.housingVehicleItemStatus = other.housingVehicleItemStatus == null ? null : other.housingVehicleItemStatus.copy();
        this.housingVehicleItemPlate = other.housingVehicleItemPlate == null ? null : other.housingVehicleItemPlate.copy();
        this.housingVehicleItemType = other.housingVehicleItemType == null ? null : other.housingVehicleItemType.copy();
        this.housingVehicleItemFipeCode = other.housingVehicleItemFipeCode == null ? null : other.housingVehicleItemFipeCode.copy();
        this.housingVehicleItemYear = other.housingVehicleItemYear == null ? null : other.housingVehicleItemYear.copy();
        this.housingVehicleItemFuel = other.housingVehicleItemFuel == null ? null : other.housingVehicleItemFuel.copy();
        this.housingVehicleItemBranch = other.housingVehicleItemBranch == null ? null : other.housingVehicleItemBranch.copy();
        this.housingVehicleItemModel = other.housingVehicleItemModel == null ? null : other.housingVehicleItemModel.copy();
        this.housingVehicleItemFuelAbbreviation =
            other.housingVehicleItemFuelAbbreviation == null ? null : other.housingVehicleItemFuelAbbreviation.copy();
        this.housingVehicleItemReferenceMonth =
            other.housingVehicleItemReferenceMonth == null ? null : other.housingVehicleItemReferenceMonth.copy();
        this.housingVehicleItemValue = other.housingVehicleItemValue == null ? null : other.housingVehicleItemValue.copy();
        this.housingVehicleItemShippingValue =
            other.housingVehicleItemShippingValue == null ? null : other.housingVehicleItemShippingValue.copy();
        this.housingId = other.housingId == null ? null : other.housingId.copy();
        this.parkingSectorId = other.parkingSectorId == null ? null : other.parkingSectorId.copy();
        this.parkingSectorSpaceId = other.parkingSectorSpaceId == null ? null : other.parkingSectorSpaceId.copy();
    }

    @Override
    public HousingVehicleItemCriteria copy() {
        return new HousingVehicleItemCriteria(this);
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

    public StatusTypeFilter getHousingVehicleItemStatus() {
        return housingVehicleItemStatus;
    }

    public StatusTypeFilter housingVehicleItemStatus() {
        if (housingVehicleItemStatus == null) {
            housingVehicleItemStatus = new StatusTypeFilter();
        }
        return housingVehicleItemStatus;
    }

    public void setHousingVehicleItemStatus(StatusTypeFilter housingVehicleItemStatus) {
        this.housingVehicleItemStatus = housingVehicleItemStatus;
    }

    public StringFilter getHousingVehicleItemPlate() {
        return housingVehicleItemPlate;
    }

    public StringFilter housingVehicleItemPlate() {
        if (housingVehicleItemPlate == null) {
            housingVehicleItemPlate = new StringFilter();
        }
        return housingVehicleItemPlate;
    }

    public void setHousingVehicleItemPlate(StringFilter housingVehicleItemPlate) {
        this.housingVehicleItemPlate = housingVehicleItemPlate;
    }

    public VehicleTypeFilter getHousingVehicleItemType() {
        return housingVehicleItemType;
    }

    public VehicleTypeFilter housingVehicleItemType() {
        if (housingVehicleItemType == null) {
            housingVehicleItemType = new VehicleTypeFilter();
        }
        return housingVehicleItemType;
    }

    public void setHousingVehicleItemType(VehicleTypeFilter housingVehicleItemType) {
        this.housingVehicleItemType = housingVehicleItemType;
    }

    public StringFilter getHousingVehicleItemFipeCode() {
        return housingVehicleItemFipeCode;
    }

    public StringFilter housingVehicleItemFipeCode() {
        if (housingVehicleItemFipeCode == null) {
            housingVehicleItemFipeCode = new StringFilter();
        }
        return housingVehicleItemFipeCode;
    }

    public void setHousingVehicleItemFipeCode(StringFilter housingVehicleItemFipeCode) {
        this.housingVehicleItemFipeCode = housingVehicleItemFipeCode;
    }

    public StringFilter getHousingVehicleItemYear() {
        return housingVehicleItemYear;
    }

    public StringFilter housingVehicleItemYear() {
        if (housingVehicleItemYear == null) {
            housingVehicleItemYear = new StringFilter();
        }
        return housingVehicleItemYear;
    }

    public void setHousingVehicleItemYear(StringFilter housingVehicleItemYear) {
        this.housingVehicleItemYear = housingVehicleItemYear;
    }

    public StringFilter getHousingVehicleItemFuel() {
        return housingVehicleItemFuel;
    }

    public StringFilter housingVehicleItemFuel() {
        if (housingVehicleItemFuel == null) {
            housingVehicleItemFuel = new StringFilter();
        }
        return housingVehicleItemFuel;
    }

    public void setHousingVehicleItemFuel(StringFilter housingVehicleItemFuel) {
        this.housingVehicleItemFuel = housingVehicleItemFuel;
    }

    public StringFilter getHousingVehicleItemBranch() {
        return housingVehicleItemBranch;
    }

    public StringFilter housingVehicleItemBranch() {
        if (housingVehicleItemBranch == null) {
            housingVehicleItemBranch = new StringFilter();
        }
        return housingVehicleItemBranch;
    }

    public void setHousingVehicleItemBranch(StringFilter housingVehicleItemBranch) {
        this.housingVehicleItemBranch = housingVehicleItemBranch;
    }

    public StringFilter getHousingVehicleItemModel() {
        return housingVehicleItemModel;
    }

    public StringFilter housingVehicleItemModel() {
        if (housingVehicleItemModel == null) {
            housingVehicleItemModel = new StringFilter();
        }
        return housingVehicleItemModel;
    }

    public void setHousingVehicleItemModel(StringFilter housingVehicleItemModel) {
        this.housingVehicleItemModel = housingVehicleItemModel;
    }

    public StringFilter getHousingVehicleItemFuelAbbreviation() {
        return housingVehicleItemFuelAbbreviation;
    }

    public StringFilter housingVehicleItemFuelAbbreviation() {
        if (housingVehicleItemFuelAbbreviation == null) {
            housingVehicleItemFuelAbbreviation = new StringFilter();
        }
        return housingVehicleItemFuelAbbreviation;
    }

    public void setHousingVehicleItemFuelAbbreviation(StringFilter housingVehicleItemFuelAbbreviation) {
        this.housingVehicleItemFuelAbbreviation = housingVehicleItemFuelAbbreviation;
    }

    public StringFilter getHousingVehicleItemReferenceMonth() {
        return housingVehicleItemReferenceMonth;
    }

    public StringFilter housingVehicleItemReferenceMonth() {
        if (housingVehicleItemReferenceMonth == null) {
            housingVehicleItemReferenceMonth = new StringFilter();
        }
        return housingVehicleItemReferenceMonth;
    }

    public void setHousingVehicleItemReferenceMonth(StringFilter housingVehicleItemReferenceMonth) {
        this.housingVehicleItemReferenceMonth = housingVehicleItemReferenceMonth;
    }

    public FloatFilter getHousingVehicleItemValue() {
        return housingVehicleItemValue;
    }

    public FloatFilter housingVehicleItemValue() {
        if (housingVehicleItemValue == null) {
            housingVehicleItemValue = new FloatFilter();
        }
        return housingVehicleItemValue;
    }

    public void setHousingVehicleItemValue(FloatFilter housingVehicleItemValue) {
        this.housingVehicleItemValue = housingVehicleItemValue;
    }

    public FloatFilter getHousingVehicleItemShippingValue() {
        return housingVehicleItemShippingValue;
    }

    public FloatFilter housingVehicleItemShippingValue() {
        if (housingVehicleItemShippingValue == null) {
            housingVehicleItemShippingValue = new FloatFilter();
        }
        return housingVehicleItemShippingValue;
    }

    public void setHousingVehicleItemShippingValue(FloatFilter housingVehicleItemShippingValue) {
        this.housingVehicleItemShippingValue = housingVehicleItemShippingValue;
    }

    public LongFilter getHousingId() {
        return housingId;
    }

    public LongFilter housingId() {
        if (housingId == null) {
            housingId = new LongFilter();
        }
        return housingId;
    }

    public void setHousingId(LongFilter housingId) {
        this.housingId = housingId;
    }

    public LongFilter getParkingSectorId() {
        return parkingSectorId;
    }

    public LongFilter parkingSectorId() {
        if (parkingSectorId == null) {
            parkingSectorId = new LongFilter();
        }
        return parkingSectorId;
    }

    public void setParkingSectorId(LongFilter parkingSectorId) {
        this.parkingSectorId = parkingSectorId;
    }

    public LongFilter getParkingSectorSpaceId() {
        return parkingSectorSpaceId;
    }

    public LongFilter parkingSectorSpaceId() {
        if (parkingSectorSpaceId == null) {
            parkingSectorSpaceId = new LongFilter();
        }
        return parkingSectorSpaceId;
    }

    public void setParkingSectorSpaceId(LongFilter parkingSectorSpaceId) {
        this.parkingSectorSpaceId = parkingSectorSpaceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HousingVehicleItemCriteria that = (HousingVehicleItemCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(housingVehicleItemStatus, that.housingVehicleItemStatus) &&
            Objects.equals(housingVehicleItemPlate, that.housingVehicleItemPlate) &&
            Objects.equals(housingVehicleItemType, that.housingVehicleItemType) &&
            Objects.equals(housingVehicleItemFipeCode, that.housingVehicleItemFipeCode) &&
            Objects.equals(housingVehicleItemYear, that.housingVehicleItemYear) &&
            Objects.equals(housingVehicleItemFuel, that.housingVehicleItemFuel) &&
            Objects.equals(housingVehicleItemBranch, that.housingVehicleItemBranch) &&
            Objects.equals(housingVehicleItemModel, that.housingVehicleItemModel) &&
            Objects.equals(housingVehicleItemFuelAbbreviation, that.housingVehicleItemFuelAbbreviation) &&
            Objects.equals(housingVehicleItemReferenceMonth, that.housingVehicleItemReferenceMonth) &&
            Objects.equals(housingVehicleItemValue, that.housingVehicleItemValue) &&
            Objects.equals(housingVehicleItemShippingValue, that.housingVehicleItemShippingValue) &&
            Objects.equals(housingId, that.housingId) &&
            Objects.equals(parkingSectorId, that.parkingSectorId) &&
            Objects.equals(parkingSectorSpaceId, that.parkingSectorSpaceId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            housingVehicleItemStatus,
            housingVehicleItemPlate,
            housingVehicleItemType,
            housingVehicleItemFipeCode,
            housingVehicleItemYear,
            housingVehicleItemFuel,
            housingVehicleItemBranch,
            housingVehicleItemModel,
            housingVehicleItemFuelAbbreviation,
            housingVehicleItemReferenceMonth,
            housingVehicleItemValue,
            housingVehicleItemShippingValue,
            housingId,
            parkingSectorId,
            parkingSectorSpaceId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HousingVehicleItemCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (housingVehicleItemStatus != null ? "housingVehicleItemStatus=" + housingVehicleItemStatus + ", " : "") +
            (housingVehicleItemPlate != null ? "housingVehicleItemPlate=" + housingVehicleItemPlate + ", " : "") +
            (housingVehicleItemType != null ? "housingVehicleItemType=" + housingVehicleItemType + ", " : "") +
            (housingVehicleItemFipeCode != null ? "housingVehicleItemFipeCode=" + housingVehicleItemFipeCode + ", " : "") +
            (housingVehicleItemYear != null ? "housingVehicleItemYear=" + housingVehicleItemYear + ", " : "") +
            (housingVehicleItemFuel != null ? "housingVehicleItemFuel=" + housingVehicleItemFuel + ", " : "") +
            (housingVehicleItemBranch != null ? "housingVehicleItemBranch=" + housingVehicleItemBranch + ", " : "") +
            (housingVehicleItemModel != null ? "housingVehicleItemModel=" + housingVehicleItemModel + ", " : "") +
            (housingVehicleItemFuelAbbreviation != null ? "housingVehicleItemFuelAbbreviation=" + housingVehicleItemFuelAbbreviation + ", " : "") +
            (housingVehicleItemReferenceMonth != null ? "housingVehicleItemReferenceMonth=" + housingVehicleItemReferenceMonth + ", " : "") +
            (housingVehicleItemValue != null ? "housingVehicleItemValue=" + housingVehicleItemValue + ", " : "") +
            (housingVehicleItemShippingValue != null ? "housingVehicleItemShippingValue=" + housingVehicleItemShippingValue + ", " : "") +
            (housingId != null ? "housingId=" + housingId + ", " : "") +
            (parkingSectorId != null ? "parkingSectorId=" + parkingSectorId + ", " : "") +
            (parkingSectorSpaceId != null ? "parkingSectorSpaceId=" + parkingSectorSpaceId + ", " : "") +
            "}";
    }
}
