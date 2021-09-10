package com.genesisoft.transporte.service.criteria;

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
 * Criteria class for the {@link com.genesisoft.transporte.domain.ParkingSector} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.ParkingSectorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /parking-sectors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ParkingSectorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter active;

    private StringFilter sectorName;

    private IntegerFilter parkingSpace;

    private IntegerFilter parkingNumbersBegin;

    private IntegerFilter parkingNumbersFinal;

    private LongFilter parkingSectorSpaceId;

    private LongFilter housingVehicleItemId;

    private LongFilter parkingId;

    public ParkingSectorCriteria() {}

    public ParkingSectorCriteria(ParkingSectorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.sectorName = other.sectorName == null ? null : other.sectorName.copy();
        this.parkingSpace = other.parkingSpace == null ? null : other.parkingSpace.copy();
        this.parkingNumbersBegin = other.parkingNumbersBegin == null ? null : other.parkingNumbersBegin.copy();
        this.parkingNumbersFinal = other.parkingNumbersFinal == null ? null : other.parkingNumbersFinal.copy();
        this.parkingSectorSpaceId = other.parkingSectorSpaceId == null ? null : other.parkingSectorSpaceId.copy();
        this.housingVehicleItemId = other.housingVehicleItemId == null ? null : other.housingVehicleItemId.copy();
        this.parkingId = other.parkingId == null ? null : other.parkingId.copy();
    }

    @Override
    public ParkingSectorCriteria copy() {
        return new ParkingSectorCriteria(this);
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

    public BooleanFilter getActive() {
        return active;
    }

    public BooleanFilter active() {
        if (active == null) {
            active = new BooleanFilter();
        }
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public StringFilter getSectorName() {
        return sectorName;
    }

    public StringFilter sectorName() {
        if (sectorName == null) {
            sectorName = new StringFilter();
        }
        return sectorName;
    }

    public void setSectorName(StringFilter sectorName) {
        this.sectorName = sectorName;
    }

    public IntegerFilter getParkingSpace() {
        return parkingSpace;
    }

    public IntegerFilter parkingSpace() {
        if (parkingSpace == null) {
            parkingSpace = new IntegerFilter();
        }
        return parkingSpace;
    }

    public void setParkingSpace(IntegerFilter parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public IntegerFilter getParkingNumbersBegin() {
        return parkingNumbersBegin;
    }

    public IntegerFilter parkingNumbersBegin() {
        if (parkingNumbersBegin == null) {
            parkingNumbersBegin = new IntegerFilter();
        }
        return parkingNumbersBegin;
    }

    public void setParkingNumbersBegin(IntegerFilter parkingNumbersBegin) {
        this.parkingNumbersBegin = parkingNumbersBegin;
    }

    public IntegerFilter getParkingNumbersFinal() {
        return parkingNumbersFinal;
    }

    public IntegerFilter parkingNumbersFinal() {
        if (parkingNumbersFinal == null) {
            parkingNumbersFinal = new IntegerFilter();
        }
        return parkingNumbersFinal;
    }

    public void setParkingNumbersFinal(IntegerFilter parkingNumbersFinal) {
        this.parkingNumbersFinal = parkingNumbersFinal;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ParkingSectorCriteria that = (ParkingSectorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(active, that.active) &&
            Objects.equals(sectorName, that.sectorName) &&
            Objects.equals(parkingSpace, that.parkingSpace) &&
            Objects.equals(parkingNumbersBegin, that.parkingNumbersBegin) &&
            Objects.equals(parkingNumbersFinal, that.parkingNumbersFinal) &&
            Objects.equals(parkingSectorSpaceId, that.parkingSectorSpaceId) &&
            Objects.equals(housingVehicleItemId, that.housingVehicleItemId) &&
            Objects.equals(parkingId, that.parkingId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            active,
            sectorName,
            parkingSpace,
            parkingNumbersBegin,
            parkingNumbersFinal,
            parkingSectorSpaceId,
            housingVehicleItemId,
            parkingId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParkingSectorCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (active != null ? "active=" + active + ", " : "") +
            (sectorName != null ? "sectorName=" + sectorName + ", " : "") +
            (parkingSpace != null ? "parkingSpace=" + parkingSpace + ", " : "") +
            (parkingNumbersBegin != null ? "parkingNumbersBegin=" + parkingNumbersBegin + ", " : "") +
            (parkingNumbersFinal != null ? "parkingNumbersFinal=" + parkingNumbersFinal + ", " : "") +
            (parkingSectorSpaceId != null ? "parkingSectorSpaceId=" + parkingSectorSpaceId + ", " : "") +
            (housingVehicleItemId != null ? "housingVehicleItemId=" + housingVehicleItemId + ", " : "") +
            (parkingId != null ? "parkingId=" + parkingId + ", " : "") +
            "}";
    }
}
