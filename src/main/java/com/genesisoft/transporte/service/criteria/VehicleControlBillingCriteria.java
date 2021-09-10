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
 * Criteria class for the {@link com.genesisoft.transporte.domain.VehicleControlBilling} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.VehicleControlBillingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vehicle-control-billings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VehicleControlBillingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter vehicleControlBillingDate;

    private LocalDateFilter vehicleControlBillingExpirationDate;

    private LocalDateFilter vehicleControlBillingPaymentDate;

    private BooleanFilter vehicleControlBillingSellerCommission;

    private BooleanFilter vehicleControlBillingDriverCommission;

    private IntegerFilter vehicleControlBillingAmount;

    private FloatFilter vehicleControlBillingTotalValue;

    private FloatFilter vehicleControlBillingInsuranceDiscount;

    private StringFilter vehicleControlBillingCustomerBank;

    private BooleanFilter vehicleControlBillingAnticipate;

    private StringFilter vehicleControlBillingManifest;

    private LongFilter vehicleControlsId;

    private LongFilter feesId;

    public VehicleControlBillingCriteria() {}

    public VehicleControlBillingCriteria(VehicleControlBillingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.vehicleControlBillingDate = other.vehicleControlBillingDate == null ? null : other.vehicleControlBillingDate.copy();
        this.vehicleControlBillingExpirationDate =
            other.vehicleControlBillingExpirationDate == null ? null : other.vehicleControlBillingExpirationDate.copy();
        this.vehicleControlBillingPaymentDate =
            other.vehicleControlBillingPaymentDate == null ? null : other.vehicleControlBillingPaymentDate.copy();
        this.vehicleControlBillingSellerCommission =
            other.vehicleControlBillingSellerCommission == null ? null : other.vehicleControlBillingSellerCommission.copy();
        this.vehicleControlBillingDriverCommission =
            other.vehicleControlBillingDriverCommission == null ? null : other.vehicleControlBillingDriverCommission.copy();
        this.vehicleControlBillingAmount = other.vehicleControlBillingAmount == null ? null : other.vehicleControlBillingAmount.copy();
        this.vehicleControlBillingTotalValue =
            other.vehicleControlBillingTotalValue == null ? null : other.vehicleControlBillingTotalValue.copy();
        this.vehicleControlBillingInsuranceDiscount =
            other.vehicleControlBillingInsuranceDiscount == null ? null : other.vehicleControlBillingInsuranceDiscount.copy();
        this.vehicleControlBillingCustomerBank =
            other.vehicleControlBillingCustomerBank == null ? null : other.vehicleControlBillingCustomerBank.copy();
        this.vehicleControlBillingAnticipate =
            other.vehicleControlBillingAnticipate == null ? null : other.vehicleControlBillingAnticipate.copy();
        this.vehicleControlBillingManifest =
            other.vehicleControlBillingManifest == null ? null : other.vehicleControlBillingManifest.copy();
        this.vehicleControlsId = other.vehicleControlsId == null ? null : other.vehicleControlsId.copy();
        this.feesId = other.feesId == null ? null : other.feesId.copy();
    }

    @Override
    public VehicleControlBillingCriteria copy() {
        return new VehicleControlBillingCriteria(this);
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

    public LocalDateFilter getVehicleControlBillingDate() {
        return vehicleControlBillingDate;
    }

    public LocalDateFilter vehicleControlBillingDate() {
        if (vehicleControlBillingDate == null) {
            vehicleControlBillingDate = new LocalDateFilter();
        }
        return vehicleControlBillingDate;
    }

    public void setVehicleControlBillingDate(LocalDateFilter vehicleControlBillingDate) {
        this.vehicleControlBillingDate = vehicleControlBillingDate;
    }

    public LocalDateFilter getVehicleControlBillingExpirationDate() {
        return vehicleControlBillingExpirationDate;
    }

    public LocalDateFilter vehicleControlBillingExpirationDate() {
        if (vehicleControlBillingExpirationDate == null) {
            vehicleControlBillingExpirationDate = new LocalDateFilter();
        }
        return vehicleControlBillingExpirationDate;
    }

    public void setVehicleControlBillingExpirationDate(LocalDateFilter vehicleControlBillingExpirationDate) {
        this.vehicleControlBillingExpirationDate = vehicleControlBillingExpirationDate;
    }

    public LocalDateFilter getVehicleControlBillingPaymentDate() {
        return vehicleControlBillingPaymentDate;
    }

    public LocalDateFilter vehicleControlBillingPaymentDate() {
        if (vehicleControlBillingPaymentDate == null) {
            vehicleControlBillingPaymentDate = new LocalDateFilter();
        }
        return vehicleControlBillingPaymentDate;
    }

    public void setVehicleControlBillingPaymentDate(LocalDateFilter vehicleControlBillingPaymentDate) {
        this.vehicleControlBillingPaymentDate = vehicleControlBillingPaymentDate;
    }

    public BooleanFilter getVehicleControlBillingSellerCommission() {
        return vehicleControlBillingSellerCommission;
    }

    public BooleanFilter vehicleControlBillingSellerCommission() {
        if (vehicleControlBillingSellerCommission == null) {
            vehicleControlBillingSellerCommission = new BooleanFilter();
        }
        return vehicleControlBillingSellerCommission;
    }

    public void setVehicleControlBillingSellerCommission(BooleanFilter vehicleControlBillingSellerCommission) {
        this.vehicleControlBillingSellerCommission = vehicleControlBillingSellerCommission;
    }

    public BooleanFilter getVehicleControlBillingDriverCommission() {
        return vehicleControlBillingDriverCommission;
    }

    public BooleanFilter vehicleControlBillingDriverCommission() {
        if (vehicleControlBillingDriverCommission == null) {
            vehicleControlBillingDriverCommission = new BooleanFilter();
        }
        return vehicleControlBillingDriverCommission;
    }

    public void setVehicleControlBillingDriverCommission(BooleanFilter vehicleControlBillingDriverCommission) {
        this.vehicleControlBillingDriverCommission = vehicleControlBillingDriverCommission;
    }

    public IntegerFilter getVehicleControlBillingAmount() {
        return vehicleControlBillingAmount;
    }

    public IntegerFilter vehicleControlBillingAmount() {
        if (vehicleControlBillingAmount == null) {
            vehicleControlBillingAmount = new IntegerFilter();
        }
        return vehicleControlBillingAmount;
    }

    public void setVehicleControlBillingAmount(IntegerFilter vehicleControlBillingAmount) {
        this.vehicleControlBillingAmount = vehicleControlBillingAmount;
    }

    public FloatFilter getVehicleControlBillingTotalValue() {
        return vehicleControlBillingTotalValue;
    }

    public FloatFilter vehicleControlBillingTotalValue() {
        if (vehicleControlBillingTotalValue == null) {
            vehicleControlBillingTotalValue = new FloatFilter();
        }
        return vehicleControlBillingTotalValue;
    }

    public void setVehicleControlBillingTotalValue(FloatFilter vehicleControlBillingTotalValue) {
        this.vehicleControlBillingTotalValue = vehicleControlBillingTotalValue;
    }

    public FloatFilter getVehicleControlBillingInsuranceDiscount() {
        return vehicleControlBillingInsuranceDiscount;
    }

    public FloatFilter vehicleControlBillingInsuranceDiscount() {
        if (vehicleControlBillingInsuranceDiscount == null) {
            vehicleControlBillingInsuranceDiscount = new FloatFilter();
        }
        return vehicleControlBillingInsuranceDiscount;
    }

    public void setVehicleControlBillingInsuranceDiscount(FloatFilter vehicleControlBillingInsuranceDiscount) {
        this.vehicleControlBillingInsuranceDiscount = vehicleControlBillingInsuranceDiscount;
    }

    public StringFilter getVehicleControlBillingCustomerBank() {
        return vehicleControlBillingCustomerBank;
    }

    public StringFilter vehicleControlBillingCustomerBank() {
        if (vehicleControlBillingCustomerBank == null) {
            vehicleControlBillingCustomerBank = new StringFilter();
        }
        return vehicleControlBillingCustomerBank;
    }

    public void setVehicleControlBillingCustomerBank(StringFilter vehicleControlBillingCustomerBank) {
        this.vehicleControlBillingCustomerBank = vehicleControlBillingCustomerBank;
    }

    public BooleanFilter getVehicleControlBillingAnticipate() {
        return vehicleControlBillingAnticipate;
    }

    public BooleanFilter vehicleControlBillingAnticipate() {
        if (vehicleControlBillingAnticipate == null) {
            vehicleControlBillingAnticipate = new BooleanFilter();
        }
        return vehicleControlBillingAnticipate;
    }

    public void setVehicleControlBillingAnticipate(BooleanFilter vehicleControlBillingAnticipate) {
        this.vehicleControlBillingAnticipate = vehicleControlBillingAnticipate;
    }

    public StringFilter getVehicleControlBillingManifest() {
        return vehicleControlBillingManifest;
    }

    public StringFilter vehicleControlBillingManifest() {
        if (vehicleControlBillingManifest == null) {
            vehicleControlBillingManifest = new StringFilter();
        }
        return vehicleControlBillingManifest;
    }

    public void setVehicleControlBillingManifest(StringFilter vehicleControlBillingManifest) {
        this.vehicleControlBillingManifest = vehicleControlBillingManifest;
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

    public LongFilter getFeesId() {
        return feesId;
    }

    public LongFilter feesId() {
        if (feesId == null) {
            feesId = new LongFilter();
        }
        return feesId;
    }

    public void setFeesId(LongFilter feesId) {
        this.feesId = feesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VehicleControlBillingCriteria that = (VehicleControlBillingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(vehicleControlBillingDate, that.vehicleControlBillingDate) &&
            Objects.equals(vehicleControlBillingExpirationDate, that.vehicleControlBillingExpirationDate) &&
            Objects.equals(vehicleControlBillingPaymentDate, that.vehicleControlBillingPaymentDate) &&
            Objects.equals(vehicleControlBillingSellerCommission, that.vehicleControlBillingSellerCommission) &&
            Objects.equals(vehicleControlBillingDriverCommission, that.vehicleControlBillingDriverCommission) &&
            Objects.equals(vehicleControlBillingAmount, that.vehicleControlBillingAmount) &&
            Objects.equals(vehicleControlBillingTotalValue, that.vehicleControlBillingTotalValue) &&
            Objects.equals(vehicleControlBillingInsuranceDiscount, that.vehicleControlBillingInsuranceDiscount) &&
            Objects.equals(vehicleControlBillingCustomerBank, that.vehicleControlBillingCustomerBank) &&
            Objects.equals(vehicleControlBillingAnticipate, that.vehicleControlBillingAnticipate) &&
            Objects.equals(vehicleControlBillingManifest, that.vehicleControlBillingManifest) &&
            Objects.equals(vehicleControlsId, that.vehicleControlsId) &&
            Objects.equals(feesId, that.feesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            vehicleControlBillingDate,
            vehicleControlBillingExpirationDate,
            vehicleControlBillingPaymentDate,
            vehicleControlBillingSellerCommission,
            vehicleControlBillingDriverCommission,
            vehicleControlBillingAmount,
            vehicleControlBillingTotalValue,
            vehicleControlBillingInsuranceDiscount,
            vehicleControlBillingCustomerBank,
            vehicleControlBillingAnticipate,
            vehicleControlBillingManifest,
            vehicleControlsId,
            feesId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleControlBillingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (vehicleControlBillingDate != null ? "vehicleControlBillingDate=" + vehicleControlBillingDate + ", " : "") +
            (vehicleControlBillingExpirationDate != null ? "vehicleControlBillingExpirationDate=" + vehicleControlBillingExpirationDate + ", " : "") +
            (vehicleControlBillingPaymentDate != null ? "vehicleControlBillingPaymentDate=" + vehicleControlBillingPaymentDate + ", " : "") +
            (vehicleControlBillingSellerCommission != null ? "vehicleControlBillingSellerCommission=" + vehicleControlBillingSellerCommission + ", " : "") +
            (vehicleControlBillingDriverCommission != null ? "vehicleControlBillingDriverCommission=" + vehicleControlBillingDriverCommission + ", " : "") +
            (vehicleControlBillingAmount != null ? "vehicleControlBillingAmount=" + vehicleControlBillingAmount + ", " : "") +
            (vehicleControlBillingTotalValue != null ? "vehicleControlBillingTotalValue=" + vehicleControlBillingTotalValue + ", " : "") +
            (vehicleControlBillingInsuranceDiscount != null ? "vehicleControlBillingInsuranceDiscount=" + vehicleControlBillingInsuranceDiscount + ", " : "") +
            (vehicleControlBillingCustomerBank != null ? "vehicleControlBillingCustomerBank=" + vehicleControlBillingCustomerBank + ", " : "") +
            (vehicleControlBillingAnticipate != null ? "vehicleControlBillingAnticipate=" + vehicleControlBillingAnticipate + ", " : "") +
            (vehicleControlBillingManifest != null ? "vehicleControlBillingManifest=" + vehicleControlBillingManifest + ", " : "") +
            (vehicleControlsId != null ? "vehicleControlsId=" + vehicleControlsId + ", " : "") +
            (feesId != null ? "feesId=" + feesId + ", " : "") +
            "}";
    }
}
