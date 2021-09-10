package com.genesisoft.transporte.service.criteria;

import com.genesisoft.transporte.domain.enumeration.InspectionPositions;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
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
 * Criteria class for the {@link com.genesisoft.transporte.domain.VehicleInspectionsImagens} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.VehicleInspectionsImagensResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vehicle-inspections-imagens?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VehicleInspectionsImagensCriteria implements Serializable, Criteria {

    /**
     * Class for filtering InspectionPositions
     */
    public static class InspectionPositionsFilter extends Filter<InspectionPositions> {

        public InspectionPositionsFilter() {}

        public InspectionPositionsFilter(InspectionPositionsFilter filter) {
            super(filter);
        }

        @Override
        public InspectionPositionsFilter copy() {
            return new InspectionPositionsFilter(this);
        }
    }

    /**
     * Class for filtering VehicleStatus
     */
    public static class VehicleStatusFilter extends Filter<VehicleStatus> {

        public VehicleStatusFilter() {}

        public VehicleStatusFilter(VehicleStatusFilter filter) {
            super(filter);
        }

        @Override
        public VehicleStatusFilter copy() {
            return new VehicleStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InspectionPositionsFilter vehicleInspectionsImagensPositions;

    private VehicleStatusFilter vehicleInspectionsImagensStatus;

    private StringFilter vehicleInspectionsImagensObs;

    private FloatFilter vehicleInspectionsImagensPositionsX;

    private FloatFilter vehicleInspectionsImagensPositionsY;

    private LongFilter vehicleInspectionsId;

    public VehicleInspectionsImagensCriteria() {}

    public VehicleInspectionsImagensCriteria(VehicleInspectionsImagensCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.vehicleInspectionsImagensPositions =
            other.vehicleInspectionsImagensPositions == null ? null : other.vehicleInspectionsImagensPositions.copy();
        this.vehicleInspectionsImagensStatus =
            other.vehicleInspectionsImagensStatus == null ? null : other.vehicleInspectionsImagensStatus.copy();
        this.vehicleInspectionsImagensObs = other.vehicleInspectionsImagensObs == null ? null : other.vehicleInspectionsImagensObs.copy();
        this.vehicleInspectionsImagensPositionsX =
            other.vehicleInspectionsImagensPositionsX == null ? null : other.vehicleInspectionsImagensPositionsX.copy();
        this.vehicleInspectionsImagensPositionsY =
            other.vehicleInspectionsImagensPositionsY == null ? null : other.vehicleInspectionsImagensPositionsY.copy();
        this.vehicleInspectionsId = other.vehicleInspectionsId == null ? null : other.vehicleInspectionsId.copy();
    }

    @Override
    public VehicleInspectionsImagensCriteria copy() {
        return new VehicleInspectionsImagensCriteria(this);
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

    public InspectionPositionsFilter getVehicleInspectionsImagensPositions() {
        return vehicleInspectionsImagensPositions;
    }

    public InspectionPositionsFilter vehicleInspectionsImagensPositions() {
        if (vehicleInspectionsImagensPositions == null) {
            vehicleInspectionsImagensPositions = new InspectionPositionsFilter();
        }
        return vehicleInspectionsImagensPositions;
    }

    public void setVehicleInspectionsImagensPositions(InspectionPositionsFilter vehicleInspectionsImagensPositions) {
        this.vehicleInspectionsImagensPositions = vehicleInspectionsImagensPositions;
    }

    public VehicleStatusFilter getVehicleInspectionsImagensStatus() {
        return vehicleInspectionsImagensStatus;
    }

    public VehicleStatusFilter vehicleInspectionsImagensStatus() {
        if (vehicleInspectionsImagensStatus == null) {
            vehicleInspectionsImagensStatus = new VehicleStatusFilter();
        }
        return vehicleInspectionsImagensStatus;
    }

    public void setVehicleInspectionsImagensStatus(VehicleStatusFilter vehicleInspectionsImagensStatus) {
        this.vehicleInspectionsImagensStatus = vehicleInspectionsImagensStatus;
    }

    public StringFilter getVehicleInspectionsImagensObs() {
        return vehicleInspectionsImagensObs;
    }

    public StringFilter vehicleInspectionsImagensObs() {
        if (vehicleInspectionsImagensObs == null) {
            vehicleInspectionsImagensObs = new StringFilter();
        }
        return vehicleInspectionsImagensObs;
    }

    public void setVehicleInspectionsImagensObs(StringFilter vehicleInspectionsImagensObs) {
        this.vehicleInspectionsImagensObs = vehicleInspectionsImagensObs;
    }

    public FloatFilter getVehicleInspectionsImagensPositionsX() {
        return vehicleInspectionsImagensPositionsX;
    }

    public FloatFilter vehicleInspectionsImagensPositionsX() {
        if (vehicleInspectionsImagensPositionsX == null) {
            vehicleInspectionsImagensPositionsX = new FloatFilter();
        }
        return vehicleInspectionsImagensPositionsX;
    }

    public void setVehicleInspectionsImagensPositionsX(FloatFilter vehicleInspectionsImagensPositionsX) {
        this.vehicleInspectionsImagensPositionsX = vehicleInspectionsImagensPositionsX;
    }

    public FloatFilter getVehicleInspectionsImagensPositionsY() {
        return vehicleInspectionsImagensPositionsY;
    }

    public FloatFilter vehicleInspectionsImagensPositionsY() {
        if (vehicleInspectionsImagensPositionsY == null) {
            vehicleInspectionsImagensPositionsY = new FloatFilter();
        }
        return vehicleInspectionsImagensPositionsY;
    }

    public void setVehicleInspectionsImagensPositionsY(FloatFilter vehicleInspectionsImagensPositionsY) {
        this.vehicleInspectionsImagensPositionsY = vehicleInspectionsImagensPositionsY;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VehicleInspectionsImagensCriteria that = (VehicleInspectionsImagensCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(vehicleInspectionsImagensPositions, that.vehicleInspectionsImagensPositions) &&
            Objects.equals(vehicleInspectionsImagensStatus, that.vehicleInspectionsImagensStatus) &&
            Objects.equals(vehicleInspectionsImagensObs, that.vehicleInspectionsImagensObs) &&
            Objects.equals(vehicleInspectionsImagensPositionsX, that.vehicleInspectionsImagensPositionsX) &&
            Objects.equals(vehicleInspectionsImagensPositionsY, that.vehicleInspectionsImagensPositionsY) &&
            Objects.equals(vehicleInspectionsId, that.vehicleInspectionsId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            vehicleInspectionsImagensPositions,
            vehicleInspectionsImagensStatus,
            vehicleInspectionsImagensObs,
            vehicleInspectionsImagensPositionsX,
            vehicleInspectionsImagensPositionsY,
            vehicleInspectionsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleInspectionsImagensCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (vehicleInspectionsImagensPositions != null ? "vehicleInspectionsImagensPositions=" + vehicleInspectionsImagensPositions + ", " : "") +
            (vehicleInspectionsImagensStatus != null ? "vehicleInspectionsImagensStatus=" + vehicleInspectionsImagensStatus + ", " : "") +
            (vehicleInspectionsImagensObs != null ? "vehicleInspectionsImagensObs=" + vehicleInspectionsImagensObs + ", " : "") +
            (vehicleInspectionsImagensPositionsX != null ? "vehicleInspectionsImagensPositionsX=" + vehicleInspectionsImagensPositionsX + ", " : "") +
            (vehicleInspectionsImagensPositionsY != null ? "vehicleInspectionsImagensPositionsY=" + vehicleInspectionsImagensPositionsY + ", " : "") +
            (vehicleInspectionsId != null ? "vehicleInspectionsId=" + vehicleInspectionsId + ", " : "") +
            "}";
    }
}
