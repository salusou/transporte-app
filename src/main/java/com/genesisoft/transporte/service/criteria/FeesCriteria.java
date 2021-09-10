package com.genesisoft.transporte.service.criteria;

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
 * Criteria class for the {@link com.genesisoft.transporte.domain.Fees} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.FeesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FeesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter feeDate;

    private FloatFilter feeDriverCommission;

    private FloatFilter feeFinancialCost;

    private FloatFilter feeTaxes;

    private StringFilter feeDescriptions;

    private LongFilter vehicleControlBillingId;

    private LongFilter affiliatesId;

    public FeesCriteria() {}

    public FeesCriteria(FeesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.feeDate = other.feeDate == null ? null : other.feeDate.copy();
        this.feeDriverCommission = other.feeDriverCommission == null ? null : other.feeDriverCommission.copy();
        this.feeFinancialCost = other.feeFinancialCost == null ? null : other.feeFinancialCost.copy();
        this.feeTaxes = other.feeTaxes == null ? null : other.feeTaxes.copy();
        this.feeDescriptions = other.feeDescriptions == null ? null : other.feeDescriptions.copy();
        this.vehicleControlBillingId = other.vehicleControlBillingId == null ? null : other.vehicleControlBillingId.copy();
        this.affiliatesId = other.affiliatesId == null ? null : other.affiliatesId.copy();
    }

    @Override
    public FeesCriteria copy() {
        return new FeesCriteria(this);
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

    public LocalDateFilter getFeeDate() {
        return feeDate;
    }

    public LocalDateFilter feeDate() {
        if (feeDate == null) {
            feeDate = new LocalDateFilter();
        }
        return feeDate;
    }

    public void setFeeDate(LocalDateFilter feeDate) {
        this.feeDate = feeDate;
    }

    public FloatFilter getFeeDriverCommission() {
        return feeDriverCommission;
    }

    public FloatFilter feeDriverCommission() {
        if (feeDriverCommission == null) {
            feeDriverCommission = new FloatFilter();
        }
        return feeDriverCommission;
    }

    public void setFeeDriverCommission(FloatFilter feeDriverCommission) {
        this.feeDriverCommission = feeDriverCommission;
    }

    public FloatFilter getFeeFinancialCost() {
        return feeFinancialCost;
    }

    public FloatFilter feeFinancialCost() {
        if (feeFinancialCost == null) {
            feeFinancialCost = new FloatFilter();
        }
        return feeFinancialCost;
    }

    public void setFeeFinancialCost(FloatFilter feeFinancialCost) {
        this.feeFinancialCost = feeFinancialCost;
    }

    public FloatFilter getFeeTaxes() {
        return feeTaxes;
    }

    public FloatFilter feeTaxes() {
        if (feeTaxes == null) {
            feeTaxes = new FloatFilter();
        }
        return feeTaxes;
    }

    public void setFeeTaxes(FloatFilter feeTaxes) {
        this.feeTaxes = feeTaxes;
    }

    public StringFilter getFeeDescriptions() {
        return feeDescriptions;
    }

    public StringFilter feeDescriptions() {
        if (feeDescriptions == null) {
            feeDescriptions = new StringFilter();
        }
        return feeDescriptions;
    }

    public void setFeeDescriptions(StringFilter feeDescriptions) {
        this.feeDescriptions = feeDescriptions;
    }

    public LongFilter getVehicleControlBillingId() {
        return vehicleControlBillingId;
    }

    public LongFilter vehicleControlBillingId() {
        if (vehicleControlBillingId == null) {
            vehicleControlBillingId = new LongFilter();
        }
        return vehicleControlBillingId;
    }

    public void setVehicleControlBillingId(LongFilter vehicleControlBillingId) {
        this.vehicleControlBillingId = vehicleControlBillingId;
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
        final FeesCriteria that = (FeesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(feeDate, that.feeDate) &&
            Objects.equals(feeDriverCommission, that.feeDriverCommission) &&
            Objects.equals(feeFinancialCost, that.feeFinancialCost) &&
            Objects.equals(feeTaxes, that.feeTaxes) &&
            Objects.equals(feeDescriptions, that.feeDescriptions) &&
            Objects.equals(vehicleControlBillingId, that.vehicleControlBillingId) &&
            Objects.equals(affiliatesId, that.affiliatesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            feeDate,
            feeDriverCommission,
            feeFinancialCost,
            feeTaxes,
            feeDescriptions,
            vehicleControlBillingId,
            affiliatesId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (feeDate != null ? "feeDate=" + feeDate + ", " : "") +
            (feeDriverCommission != null ? "feeDriverCommission=" + feeDriverCommission + ", " : "") +
            (feeFinancialCost != null ? "feeFinancialCost=" + feeFinancialCost + ", " : "") +
            (feeTaxes != null ? "feeTaxes=" + feeTaxes + ", " : "") +
            (feeDescriptions != null ? "feeDescriptions=" + feeDescriptions + ", " : "") +
            (vehicleControlBillingId != null ? "vehicleControlBillingId=" + vehicleControlBillingId + ", " : "") +
            (affiliatesId != null ? "affiliatesId=" + affiliatesId + ", " : "") +
            "}";
    }
}
