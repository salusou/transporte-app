package com.genesisoft.transporte.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.genesisoft.transporte.domain.Insurances} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.InsurancesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /insurances?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InsurancesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter insurancesPercent;

    private LongFilter affiliatesId;

    private LongFilter toStateProvinceId;

    private LongFilter forStateProvinceId;

    public InsurancesCriteria() {}

    public InsurancesCriteria(InsurancesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.insurancesPercent = other.insurancesPercent == null ? null : other.insurancesPercent.copy();
        this.affiliatesId = other.affiliatesId == null ? null : other.affiliatesId.copy();
        this.toStateProvinceId = other.toStateProvinceId == null ? null : other.toStateProvinceId.copy();
        this.forStateProvinceId = other.forStateProvinceId == null ? null : other.forStateProvinceId.copy();
    }

    @Override
    public InsurancesCriteria copy() {
        return new InsurancesCriteria(this);
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

    public BigDecimalFilter getInsurancesPercent() {
        return insurancesPercent;
    }

    public BigDecimalFilter insurancesPercent() {
        if (insurancesPercent == null) {
            insurancesPercent = new BigDecimalFilter();
        }
        return insurancesPercent;
    }

    public void setInsurancesPercent(BigDecimalFilter insurancesPercent) {
        this.insurancesPercent = insurancesPercent;
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

    public LongFilter getToStateProvinceId() {
        return toStateProvinceId;
    }

    public LongFilter toStateProvinceId() {
        if (toStateProvinceId == null) {
            toStateProvinceId = new LongFilter();
        }
        return toStateProvinceId;
    }

    public void setToStateProvinceId(LongFilter toStateProvinceId) {
        this.toStateProvinceId = toStateProvinceId;
    }

    public LongFilter getForStateProvinceId() {
        return forStateProvinceId;
    }

    public LongFilter forStateProvinceId() {
        if (forStateProvinceId == null) {
            forStateProvinceId = new LongFilter();
        }
        return forStateProvinceId;
    }

    public void setForStateProvinceId(LongFilter forStateProvinceId) {
        this.forStateProvinceId = forStateProvinceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InsurancesCriteria that = (InsurancesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(insurancesPercent, that.insurancesPercent) &&
            Objects.equals(affiliatesId, that.affiliatesId) &&
            Objects.equals(toStateProvinceId, that.toStateProvinceId) &&
            Objects.equals(forStateProvinceId, that.forStateProvinceId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, insurancesPercent, affiliatesId, toStateProvinceId, forStateProvinceId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InsurancesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (insurancesPercent != null ? "insurancesPercent=" + insurancesPercent + ", " : "") +
            (affiliatesId != null ? "affiliatesId=" + affiliatesId + ", " : "") +
            (toStateProvinceId != null ? "toStateProvinceId=" + toStateProvinceId + ", " : "") +
            (forStateProvinceId != null ? "forStateProvinceId=" + forStateProvinceId + ", " : "") +
            "}";
    }
}
