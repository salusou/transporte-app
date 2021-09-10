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
 * Criteria class for the {@link com.genesisoft.transporte.domain.VehicleControlHistory} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.VehicleControlHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vehicle-control-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VehicleControlHistoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter vehicleControlHistoryDate;

    private StringFilter vehicleControlHistoryDescription;

    private LongFilter vehicleControlsId;

    private LongFilter employeesId;

    public VehicleControlHistoryCriteria() {}

    public VehicleControlHistoryCriteria(VehicleControlHistoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.vehicleControlHistoryDate = other.vehicleControlHistoryDate == null ? null : other.vehicleControlHistoryDate.copy();
        this.vehicleControlHistoryDescription =
            other.vehicleControlHistoryDescription == null ? null : other.vehicleControlHistoryDescription.copy();
        this.vehicleControlsId = other.vehicleControlsId == null ? null : other.vehicleControlsId.copy();
        this.employeesId = other.employeesId == null ? null : other.employeesId.copy();
    }

    @Override
    public VehicleControlHistoryCriteria copy() {
        return new VehicleControlHistoryCriteria(this);
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

    public ZonedDateTimeFilter getVehicleControlHistoryDate() {
        return vehicleControlHistoryDate;
    }

    public ZonedDateTimeFilter vehicleControlHistoryDate() {
        if (vehicleControlHistoryDate == null) {
            vehicleControlHistoryDate = new ZonedDateTimeFilter();
        }
        return vehicleControlHistoryDate;
    }

    public void setVehicleControlHistoryDate(ZonedDateTimeFilter vehicleControlHistoryDate) {
        this.vehicleControlHistoryDate = vehicleControlHistoryDate;
    }

    public StringFilter getVehicleControlHistoryDescription() {
        return vehicleControlHistoryDescription;
    }

    public StringFilter vehicleControlHistoryDescription() {
        if (vehicleControlHistoryDescription == null) {
            vehicleControlHistoryDescription = new StringFilter();
        }
        return vehicleControlHistoryDescription;
    }

    public void setVehicleControlHistoryDescription(StringFilter vehicleControlHistoryDescription) {
        this.vehicleControlHistoryDescription = vehicleControlHistoryDescription;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VehicleControlHistoryCriteria that = (VehicleControlHistoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(vehicleControlHistoryDate, that.vehicleControlHistoryDate) &&
            Objects.equals(vehicleControlHistoryDescription, that.vehicleControlHistoryDescription) &&
            Objects.equals(vehicleControlsId, that.vehicleControlsId) &&
            Objects.equals(employeesId, that.employeesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vehicleControlHistoryDate, vehicleControlHistoryDescription, vehicleControlsId, employeesId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleControlHistoryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (vehicleControlHistoryDate != null ? "vehicleControlHistoryDate=" + vehicleControlHistoryDate + ", " : "") +
            (vehicleControlHistoryDescription != null ? "vehicleControlHistoryDescription=" + vehicleControlHistoryDescription + ", " : "") +
            (vehicleControlsId != null ? "vehicleControlsId=" + vehicleControlsId + ", " : "") +
            (employeesId != null ? "employeesId=" + employeesId + ", " : "") +
            "}";
    }
}
