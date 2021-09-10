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
import tech.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.genesisoft.transporte.domain.VehicleLocationStatus} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.VehicleLocationStatusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vehicle-location-statuses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VehicleLocationStatusCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter vehicleLocationStatusDate;

    private StringFilter vehicleLocationStatusDescription;

    private LongFilter vehicleControlsId;

    private LongFilter citiesId;

    public VehicleLocationStatusCriteria() {}

    public VehicleLocationStatusCriteria(VehicleLocationStatusCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.vehicleLocationStatusDate = other.vehicleLocationStatusDate == null ? null : other.vehicleLocationStatusDate.copy();
        this.vehicleLocationStatusDescription =
            other.vehicleLocationStatusDescription == null ? null : other.vehicleLocationStatusDescription.copy();
        this.vehicleControlsId = other.vehicleControlsId == null ? null : other.vehicleControlsId.copy();
        this.citiesId = other.citiesId == null ? null : other.citiesId.copy();
    }

    @Override
    public VehicleLocationStatusCriteria copy() {
        return new VehicleLocationStatusCriteria(this);
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

    public ZonedDateTimeFilter getVehicleLocationStatusDate() {
        return vehicleLocationStatusDate;
    }

    public ZonedDateTimeFilter vehicleLocationStatusDate() {
        if (vehicleLocationStatusDate == null) {
            vehicleLocationStatusDate = new ZonedDateTimeFilter();
        }
        return vehicleLocationStatusDate;
    }

    public void setVehicleLocationStatusDate(ZonedDateTimeFilter vehicleLocationStatusDate) {
        this.vehicleLocationStatusDate = vehicleLocationStatusDate;
    }

    public StringFilter getVehicleLocationStatusDescription() {
        return vehicleLocationStatusDescription;
    }

    public StringFilter vehicleLocationStatusDescription() {
        if (vehicleLocationStatusDescription == null) {
            vehicleLocationStatusDescription = new StringFilter();
        }
        return vehicleLocationStatusDescription;
    }

    public void setVehicleLocationStatusDescription(StringFilter vehicleLocationStatusDescription) {
        this.vehicleLocationStatusDescription = vehicleLocationStatusDescription;
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
        final VehicleLocationStatusCriteria that = (VehicleLocationStatusCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(vehicleLocationStatusDate, that.vehicleLocationStatusDate) &&
            Objects.equals(vehicleLocationStatusDescription, that.vehicleLocationStatusDescription) &&
            Objects.equals(vehicleControlsId, that.vehicleControlsId) &&
            Objects.equals(citiesId, that.citiesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vehicleLocationStatusDate, vehicleLocationStatusDescription, vehicleControlsId, citiesId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleLocationStatusCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (vehicleLocationStatusDate != null ? "vehicleLocationStatusDate=" + vehicleLocationStatusDate + ", " : "") +
            (vehicleLocationStatusDescription != null ? "vehicleLocationStatusDescription=" + vehicleLocationStatusDescription + ", " : "") +
            (vehicleControlsId != null ? "vehicleControlsId=" + vehicleControlsId + ", " : "") +
            (citiesId != null ? "citiesId=" + citiesId + ", " : "") +
            "}";
    }
}
