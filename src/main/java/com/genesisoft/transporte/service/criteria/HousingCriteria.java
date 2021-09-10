package com.genesisoft.transporte.service.criteria;

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
import tech.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.genesisoft.transporte.domain.Housing} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.HousingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /housings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HousingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter housingDate;

    private ZonedDateTimeFilter housingEntranceDate;

    private ZonedDateTimeFilter housingExit;

    private IntegerFilter housingReceiptNumber;

    private FloatFilter housingDailyPrice;

    private StringFilter housingDescription;

    private LongFilter housingVehicleItemId;

    private LongFilter affiliatesId;

    private LongFilter statusId;

    private LongFilter customersId;

    private LongFilter employeesId;

    private LongFilter parkingId;

    private LongFilter costCenterId;

    private LongFilter suppliersId;

    private LongFilter citiesId;

    public HousingCriteria() {}

    public HousingCriteria(HousingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.housingDate = other.housingDate == null ? null : other.housingDate.copy();
        this.housingEntranceDate = other.housingEntranceDate == null ? null : other.housingEntranceDate.copy();
        this.housingExit = other.housingExit == null ? null : other.housingExit.copy();
        this.housingReceiptNumber = other.housingReceiptNumber == null ? null : other.housingReceiptNumber.copy();
        this.housingDailyPrice = other.housingDailyPrice == null ? null : other.housingDailyPrice.copy();
        this.housingDescription = other.housingDescription == null ? null : other.housingDescription.copy();
        this.housingVehicleItemId = other.housingVehicleItemId == null ? null : other.housingVehicleItemId.copy();
        this.affiliatesId = other.affiliatesId == null ? null : other.affiliatesId.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
        this.customersId = other.customersId == null ? null : other.customersId.copy();
        this.employeesId = other.employeesId == null ? null : other.employeesId.copy();
        this.parkingId = other.parkingId == null ? null : other.parkingId.copy();
        this.costCenterId = other.costCenterId == null ? null : other.costCenterId.copy();
        this.suppliersId = other.suppliersId == null ? null : other.suppliersId.copy();
        this.citiesId = other.citiesId == null ? null : other.citiesId.copy();
    }

    @Override
    public HousingCriteria copy() {
        return new HousingCriteria(this);
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

    public LocalDateFilter getHousingDate() {
        return housingDate;
    }

    public LocalDateFilter housingDate() {
        if (housingDate == null) {
            housingDate = new LocalDateFilter();
        }
        return housingDate;
    }

    public void setHousingDate(LocalDateFilter housingDate) {
        this.housingDate = housingDate;
    }

    public ZonedDateTimeFilter getHousingEntranceDate() {
        return housingEntranceDate;
    }

    public ZonedDateTimeFilter housingEntranceDate() {
        if (housingEntranceDate == null) {
            housingEntranceDate = new ZonedDateTimeFilter();
        }
        return housingEntranceDate;
    }

    public void setHousingEntranceDate(ZonedDateTimeFilter housingEntranceDate) {
        this.housingEntranceDate = housingEntranceDate;
    }

    public ZonedDateTimeFilter getHousingExit() {
        return housingExit;
    }

    public ZonedDateTimeFilter housingExit() {
        if (housingExit == null) {
            housingExit = new ZonedDateTimeFilter();
        }
        return housingExit;
    }

    public void setHousingExit(ZonedDateTimeFilter housingExit) {
        this.housingExit = housingExit;
    }

    public IntegerFilter getHousingReceiptNumber() {
        return housingReceiptNumber;
    }

    public IntegerFilter housingReceiptNumber() {
        if (housingReceiptNumber == null) {
            housingReceiptNumber = new IntegerFilter();
        }
        return housingReceiptNumber;
    }

    public void setHousingReceiptNumber(IntegerFilter housingReceiptNumber) {
        this.housingReceiptNumber = housingReceiptNumber;
    }

    public FloatFilter getHousingDailyPrice() {
        return housingDailyPrice;
    }

    public FloatFilter housingDailyPrice() {
        if (housingDailyPrice == null) {
            housingDailyPrice = new FloatFilter();
        }
        return housingDailyPrice;
    }

    public void setHousingDailyPrice(FloatFilter housingDailyPrice) {
        this.housingDailyPrice = housingDailyPrice;
    }

    public StringFilter getHousingDescription() {
        return housingDescription;
    }

    public StringFilter housingDescription() {
        if (housingDescription == null) {
            housingDescription = new StringFilter();
        }
        return housingDescription;
    }

    public void setHousingDescription(StringFilter housingDescription) {
        this.housingDescription = housingDescription;
    }

    public LongFilter getHousingVehicleItemId() {
        return housingVehicleItemId;
    }

    public LongFilter housingVehicleItemId() {
        if (housingVehicleItemId == null) {
            housingVehicleItemId = new LongFilter();
        }
        return housingVehicleItemId;
    }

    public void setHousingVehicleItemId(LongFilter housingVehicleItemId) {
        this.housingVehicleItemId = housingVehicleItemId;
    }

    public LongFilter getAffiliatesId() {
        return affiliatesId;
    }

    public LongFilter affiliatesId() {
        if (affiliatesId == null) {
            affiliatesId = new LongFilter();
        }
        return affiliatesId;
    }

    public void setAffiliatesId(LongFilter affiliatesId) {
        this.affiliatesId = affiliatesId;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public LongFilter statusId() {
        if (statusId == null) {
            statusId = new LongFilter();
        }
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    public LongFilter getCustomersId() {
        return customersId;
    }

    public LongFilter customersId() {
        if (customersId == null) {
            customersId = new LongFilter();
        }
        return customersId;
    }

    public void setCustomersId(LongFilter customersId) {
        this.customersId = customersId;
    }

    public LongFilter getEmployeesId() {
        return employeesId;
    }

    public LongFilter employeesId() {
        if (employeesId == null) {
            employeesId = new LongFilter();
        }
        return employeesId;
    }

    public void setEmployeesId(LongFilter employeesId) {
        this.employeesId = employeesId;
    }

    public LongFilter getParkingId() {
        return parkingId;
    }

    public LongFilter parkingId() {
        if (parkingId == null) {
            parkingId = new LongFilter();
        }
        return parkingId;
    }

    public void setParkingId(LongFilter parkingId) {
        this.parkingId = parkingId;
    }

    public LongFilter getCostCenterId() {
        return costCenterId;
    }

    public LongFilter costCenterId() {
        if (costCenterId == null) {
            costCenterId = new LongFilter();
        }
        return costCenterId;
    }

    public void setCostCenterId(LongFilter costCenterId) {
        this.costCenterId = costCenterId;
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

    public LongFilter getCitiesId() {
        return citiesId;
    }

    public LongFilter citiesId() {
        if (citiesId == null) {
            citiesId = new LongFilter();
        }
        return citiesId;
    }

    public void setCitiesId(LongFilter citiesId) {
        this.citiesId = citiesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HousingCriteria that = (HousingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(housingDate, that.housingDate) &&
            Objects.equals(housingEntranceDate, that.housingEntranceDate) &&
            Objects.equals(housingExit, that.housingExit) &&
            Objects.equals(housingReceiptNumber, that.housingReceiptNumber) &&
            Objects.equals(housingDailyPrice, that.housingDailyPrice) &&
            Objects.equals(housingDescription, that.housingDescription) &&
            Objects.equals(housingVehicleItemId, that.housingVehicleItemId) &&
            Objects.equals(affiliatesId, that.affiliatesId) &&
            Objects.equals(statusId, that.statusId) &&
            Objects.equals(customersId, that.customersId) &&
            Objects.equals(employeesId, that.employeesId) &&
            Objects.equals(parkingId, that.parkingId) &&
            Objects.equals(costCenterId, that.costCenterId) &&
            Objects.equals(suppliersId, that.suppliersId) &&
            Objects.equals(citiesId, that.citiesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            housingDate,
            housingEntranceDate,
            housingExit,
            housingReceiptNumber,
            housingDailyPrice,
            housingDescription,
            housingVehicleItemId,
            affiliatesId,
            statusId,
            customersId,
            employeesId,
            parkingId,
            costCenterId,
            suppliersId,
            citiesId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HousingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (housingDate != null ? "housingDate=" + housingDate + ", " : "") +
            (housingEntranceDate != null ? "housingEntranceDate=" + housingEntranceDate + ", " : "") +
            (housingExit != null ? "housingExit=" + housingExit + ", " : "") +
            (housingReceiptNumber != null ? "housingReceiptNumber=" + housingReceiptNumber + ", " : "") +
            (housingDailyPrice != null ? "housingDailyPrice=" + housingDailyPrice + ", " : "") +
            (housingDescription != null ? "housingDescription=" + housingDescription + ", " : "") +
            (housingVehicleItemId != null ? "housingVehicleItemId=" + housingVehicleItemId + ", " : "") +
            (affiliatesId != null ? "affiliatesId=" + affiliatesId + ", " : "") +
            (statusId != null ? "statusId=" + statusId + ", " : "") +
            (customersId != null ? "customersId=" + customersId + ", " : "") +
            (employeesId != null ? "employeesId=" + employeesId + ", " : "") +
            (parkingId != null ? "parkingId=" + parkingId + ", " : "") +
            (costCenterId != null ? "costCenterId=" + costCenterId + ", " : "") +
            (suppliersId != null ? "suppliersId=" + suppliersId + ", " : "") +
            (citiesId != null ? "citiesId=" + citiesId + ", " : "") +
            "}";
    }
}
