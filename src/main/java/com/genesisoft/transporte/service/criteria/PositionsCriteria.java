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
 * Criteria class for the {@link com.genesisoft.transporte.domain.Positions} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.PositionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /positions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PositionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter positionName;

    private LongFilter employeesId;

    private LongFilter affiliatesId;

    public PositionsCriteria() {}

    public PositionsCriteria(PositionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.positionName = other.positionName == null ? null : other.positionName.copy();
        this.employeesId = other.employeesId == null ? null : other.employeesId.copy();
        this.affiliatesId = other.affiliatesId == null ? null : other.affiliatesId.copy();
    }

    @Override
    public PositionsCriteria copy() {
        return new PositionsCriteria(this);
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

    public StringFilter getPositionName() {
        return positionName;
    }

    public StringFilter positionName() {
        if (positionName == null) {
            positionName = new StringFilter();
        }
        return positionName;
    }

    public void setPositionName(StringFilter positionName) {
        this.positionName = positionName;
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
        final PositionsCriteria that = (PositionsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(positionName, that.positionName) &&
            Objects.equals(employeesId, that.employeesId) &&
            Objects.equals(affiliatesId, that.affiliatesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, positionName, employeesId, affiliatesId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PositionsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (positionName != null ? "positionName=" + positionName + ", " : "") +
            (employeesId != null ? "employeesId=" + employeesId + ", " : "") +
            (affiliatesId != null ? "affiliatesId=" + affiliatesId + ", " : "") +
            "}";
    }
}
