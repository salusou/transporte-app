package com.genesisoft.transporte.service.criteria;

import com.genesisoft.transporte.domain.enumeration.ScreenType;
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
 * Criteria class for the {@link com.genesisoft.transporte.domain.Status} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.StatusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /statuses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StatusCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ScreenType
     */
    public static class ScreenTypeFilter extends Filter<ScreenType> {

        public ScreenTypeFilter() {}

        public ScreenTypeFilter(ScreenTypeFilter filter) {
            super(filter);
        }

        @Override
        public ScreenTypeFilter copy() {
            return new ScreenTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter statusName;

    private ScreenTypeFilter screenType;

    private StringFilter statusDescriptions;

    private LongFilter vehicleControlsId;

    private LongFilter housingId;

    private LongFilter affiliatesId;

    public StatusCriteria() {}

    public StatusCriteria(StatusCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.statusName = other.statusName == null ? null : other.statusName.copy();
        this.screenType = other.screenType == null ? null : other.screenType.copy();
        this.statusDescriptions = other.statusDescriptions == null ? null : other.statusDescriptions.copy();
        this.vehicleControlsId = other.vehicleControlsId == null ? null : other.vehicleControlsId.copy();
        this.housingId = other.housingId == null ? null : other.housingId.copy();
        this.affiliatesId = other.affiliatesId == null ? null : other.affiliatesId.copy();
    }

    @Override
    public StatusCriteria copy() {
        return new StatusCriteria(this);
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

    public StringFilter getStatusName() {
        return statusName;
    }

    public StringFilter statusName() {
        if (statusName == null) {
            statusName = new StringFilter();
        }
        return statusName;
    }

    public void setStatusName(StringFilter statusName) {
        this.statusName = statusName;
    }

    public ScreenTypeFilter getScreenType() {
        return screenType;
    }

    public ScreenTypeFilter screenType() {
        if (screenType == null) {
            screenType = new ScreenTypeFilter();
        }
        return screenType;
    }

    public void setScreenType(ScreenTypeFilter screenType) {
        this.screenType = screenType;
    }

    public StringFilter getStatusDescriptions() {
        return statusDescriptions;
    }

    public StringFilter statusDescriptions() {
        if (statusDescriptions == null) {
            statusDescriptions = new StringFilter();
        }
        return statusDescriptions;
    }

    public void setStatusDescriptions(StringFilter statusDescriptions) {
        this.statusDescriptions = statusDescriptions;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StatusCriteria that = (StatusCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(statusName, that.statusName) &&
            Objects.equals(screenType, that.screenType) &&
            Objects.equals(statusDescriptions, that.statusDescriptions) &&
            Objects.equals(vehicleControlsId, that.vehicleControlsId) &&
            Objects.equals(housingId, that.housingId) &&
            Objects.equals(affiliatesId, that.affiliatesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, statusName, screenType, statusDescriptions, vehicleControlsId, housingId, affiliatesId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatusCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (statusName != null ? "statusName=" + statusName + ", " : "") +
            (screenType != null ? "screenType=" + screenType + ", " : "") +
            (statusDescriptions != null ? "statusDescriptions=" + statusDescriptions + ", " : "") +
            (vehicleControlsId != null ? "vehicleControlsId=" + vehicleControlsId + ", " : "") +
            (housingId != null ? "housingId=" + housingId + ", " : "") +
            (affiliatesId != null ? "affiliatesId=" + affiliatesId + ", " : "") +
            "}";
    }
}
