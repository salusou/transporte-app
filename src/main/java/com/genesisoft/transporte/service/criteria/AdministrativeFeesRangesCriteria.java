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
 * Criteria class for the {@link com.genesisoft.transporte.domain.AdministrativeFeesRanges} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.AdministrativeFeesRangesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /administrative-fees-ranges?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AdministrativeFeesRangesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FloatFilter minRange;

    private FloatFilter maxRange;

    private FloatFilter aliquot;

    private LongFilter affiliatesId;

    public AdministrativeFeesRangesCriteria() {}

    public AdministrativeFeesRangesCriteria(AdministrativeFeesRangesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.minRange = other.minRange == null ? null : other.minRange.copy();
        this.maxRange = other.maxRange == null ? null : other.maxRange.copy();
        this.aliquot = other.aliquot == null ? null : other.aliquot.copy();
        this.affiliatesId = other.affiliatesId == null ? null : other.affiliatesId.copy();
    }

    @Override
    public AdministrativeFeesRangesCriteria copy() {
        return new AdministrativeFeesRangesCriteria(this);
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

    public FloatFilter getMinRange() {
        return minRange;
    }

    public FloatFilter minRange() {
        if (minRange == null) {
            minRange = new FloatFilter();
        }
        return minRange;
    }

    public void setMinRange(FloatFilter minRange) {
        this.minRange = minRange;
    }

    public FloatFilter getMaxRange() {
        return maxRange;
    }

    public FloatFilter maxRange() {
        if (maxRange == null) {
            maxRange = new FloatFilter();
        }
        return maxRange;
    }

    public void setMaxRange(FloatFilter maxRange) {
        this.maxRange = maxRange;
    }

    public FloatFilter getAliquot() {
        return aliquot;
    }

    public FloatFilter aliquot() {
        if (aliquot == null) {
            aliquot = new FloatFilter();
        }
        return aliquot;
    }

    public void setAliquot(FloatFilter aliquot) {
        this.aliquot = aliquot;
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
        final AdministrativeFeesRangesCriteria that = (AdministrativeFeesRangesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(minRange, that.minRange) &&
            Objects.equals(maxRange, that.maxRange) &&
            Objects.equals(aliquot, that.aliquot) &&
            Objects.equals(affiliatesId, that.affiliatesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, minRange, maxRange, aliquot, affiliatesId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdministrativeFeesRangesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (minRange != null ? "minRange=" + minRange + ", " : "") +
            (maxRange != null ? "maxRange=" + maxRange + ", " : "") +
            (aliquot != null ? "aliquot=" + aliquot + ", " : "") +
            (affiliatesId != null ? "affiliatesId=" + affiliatesId + ", " : "") +
            "}";
    }
}
