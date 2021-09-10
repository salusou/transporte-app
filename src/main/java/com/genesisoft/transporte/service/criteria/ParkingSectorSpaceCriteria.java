package com.genesisoft.transporte.service.criteria;

import com.genesisoft.transporte.domain.enumeration.ParkingSpaceStatus;
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
 * Criteria class for the {@link com.genesisoft.transporte.domain.ParkingSectorSpace} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.ParkingSectorSpaceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /parking-sector-spaces?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ParkingSectorSpaceCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ParkingSpaceStatus
     */
    public static class ParkingSpaceStatusFilter extends Filter<ParkingSpaceStatus> {

        public ParkingSpaceStatusFilter() {}

        public ParkingSpaceStatusFilter(ParkingSpaceStatusFilter filter) {
            super(filter);
        }

        @Override
        public ParkingSpaceStatusFilter copy() {
            return new ParkingSpaceStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter parkingNumber;

    private ParkingSpaceStatusFilter parkingStatus;

    private LocalDateFilter parkingEntryDate;

    private LocalDateFilter parkingDepartureDate;

    private LongFilter parkingHousingItemId;

    private LongFilter housingVehicleItemId;

    private LongFilter parkingSectorId;

    public ParkingSectorSpaceCriteria() {}

    public ParkingSectorSpaceCriteria(ParkingSectorSpaceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.parkingNumber = other.parkingNumber == null ? null : other.parkingNumber.copy();
        this.parkingStatus = other.parkingStatus == null ? null : other.parkingStatus.copy();
        this.parkingEntryDate = other.parkingEntryDate == null ? null : other.parkingEntryDate.copy();
        this.parkingDepartureDate = other.parkingDepartureDate == null ? null : other.parkingDepartureDate.copy();
        this.parkingHousingItemId = other.parkingHousingItemId == null ? null : other.parkingHousingItemId.copy();
        this.housingVehicleItemId = other.housingVehicleItemId == null ? null : other.housingVehicleItemId.copy();
        this.parkingSectorId = other.parkingSectorId == null ? null : other.parkingSectorId.copy();
    }

    @Override
    public ParkingSectorSpaceCriteria copy() {
        return new ParkingSectorSpaceCriteria(this);
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

    public IntegerFilter getParkingNumber() {
        return parkingNumber;
    }

    public IntegerFilter parkingNumber() {
        if (parkingNumber == null) {
            parkingNumber = new IntegerFilter();
        }
        return parkingNumber;
    }

    public void setParkingNumber(IntegerFilter parkingNumber) {
        this.parkingNumber = parkingNumber;
    }

    public ParkingSpaceStatusFilter getParkingStatus() {
        return parkingStatus;
    }

    public ParkingSpaceStatusFilter parkingStatus() {
        if (parkingStatus == null) {
            parkingStatus = new ParkingSpaceStatusFilter();
        }
        return parkingStatus;
    }

    public void setParkingStatus(ParkingSpaceStatusFilter parkingStatus) {
        this.parkingStatus = parkingStatus;
    }

    public LocalDateFilter getParkingEntryDate() {
        return parkingEntryDate;
    }

    public LocalDateFilter parkingEntryDate() {
        if (parkingEntryDate == null) {
            parkingEntryDate = new LocalDateFilter();
        }
        return parkingEntryDate;
    }

    public void setParkingEntryDate(LocalDateFilter parkingEntryDate) {
        this.parkingEntryDate = parkingEntryDate;
    }

    public LocalDateFilter getParkingDepartureDate() {
        return parkingDepartureDate;
    }

    public LocalDateFilter parkingDepartureDate() {
        if (parkingDepartureDate == null) {
            parkingDepartureDate = new LocalDateFilter();
        }
        return parkingDepartureDate;
    }

    public void setParkingDepartureDate(LocalDateFilter parkingDepartureDate) {
        this.parkingDepartureDate = parkingDepartureDate;
    }

    public LongFilter getParkingHousingItemId() {
        return parkingHousingItemId;
    }

    public LongFilter parkingHousingItemId() {
        if (parkingHousingItemId == null) {
            parkingHousingItemId = new LongFilter();
        }
        return parkingHousingItemId;
    }

    public void setParkingHousingItemId(LongFilter parkingHousingItemId) {
        this.parkingHousingItemId = parkingHousingItemId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ParkingSectorSpaceCriteria that = (ParkingSectorSpaceCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(parkingNumber, that.parkingNumber) &&
            Objects.equals(parkingStatus, that.parkingStatus) &&
            Objects.equals(parkingEntryDate, that.parkingEntryDate) &&
            Objects.equals(parkingDepartureDate, that.parkingDepartureDate) &&
            Objects.equals(parkingHousingItemId, that.parkingHousingItemId) &&
            Objects.equals(housingVehicleItemId, that.housingVehicleItemId) &&
            Objects.equals(parkingSectorId, that.parkingSectorId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            parkingNumber,
            parkingStatus,
            parkingEntryDate,
            parkingDepartureDate,
            parkingHousingItemId,
            housingVehicleItemId,
            parkingSectorId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParkingSectorSpaceCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (parkingNumber != null ? "parkingNumber=" + parkingNumber + ", " : "") +
            (parkingStatus != null ? "parkingStatus=" + parkingStatus + ", " : "") +
            (parkingEntryDate != null ? "parkingEntryDate=" + parkingEntryDate + ", " : "") +
            (parkingDepartureDate != null ? "parkingDepartureDate=" + parkingDepartureDate + ", " : "") +
            (parkingHousingItemId != null ? "parkingHousingItemId=" + parkingHousingItemId + ", " : "") +
            (housingVehicleItemId != null ? "housingVehicleItemId=" + housingVehicleItemId + ", " : "") +
            (parkingSectorId != null ? "parkingSectorId=" + parkingSectorId + ", " : "") +
            "}";
    }
}
