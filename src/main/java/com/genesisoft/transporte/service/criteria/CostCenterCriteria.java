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
 * Criteria class for the {@link com.genesisoft.transporte.domain.CostCenter} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.CostCenterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cost-centers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CostCenterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter costCenterName;

    private LongFilter housingId;

    private LongFilter affiliatesId;

    public CostCenterCriteria() {}

    public CostCenterCriteria(CostCenterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.costCenterName = other.costCenterName == null ? null : other.costCenterName.copy();
        this.housingId = other.housingId == null ? null : other.housingId.copy();
        this.affiliatesId = other.affiliatesId == null ? null : other.affiliatesId.copy();
    }

    @Override
    public CostCenterCriteria copy() {
        return new CostCenterCriteria(this);
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

    public StringFilter getCostCenterName() {
        return costCenterName;
    }

    public StringFilter costCenterName() {
        if (costCenterName == null) {
            costCenterName = new StringFilter();
        }
        return costCenterName;
    }

    public void setCostCenterName(StringFilter costCenterName) {
        this.costCenterName = costCenterName;
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
        final CostCenterCriteria that = (CostCenterCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(costCenterName, that.costCenterName) &&
            Objects.equals(housingId, that.housingId) &&
            Objects.equals(affiliatesId, that.affiliatesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, costCenterName, housingId, affiliatesId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CostCenterCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (costCenterName != null ? "costCenterName=" + costCenterName + ", " : "") +
            (housingId != null ? "housingId=" + housingId + ", " : "") +
            (affiliatesId != null ? "affiliatesId=" + affiliatesId + ", " : "") +
            "}";
    }
}
