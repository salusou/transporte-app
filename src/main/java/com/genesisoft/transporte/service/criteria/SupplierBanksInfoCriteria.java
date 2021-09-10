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
 * Criteria class for the {@link com.genesisoft.transporte.domain.SupplierBanksInfo} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.SupplierBanksInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /supplier-banks-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SupplierBanksInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter supplierBankCode;

    private StringFilter supplierBankName;

    private StringFilter supplierBankBranchCode;

    private StringFilter supplierBankAccountNumber;

    private StringFilter supplierBankUserName;

    private StringFilter supplierBankPixKey;

    private StringFilter supplierBankUserNumber;

    private LongFilter suppliersId;

    public SupplierBanksInfoCriteria() {}

    public SupplierBanksInfoCriteria(SupplierBanksInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.supplierBankCode = other.supplierBankCode == null ? null : other.supplierBankCode.copy();
        this.supplierBankName = other.supplierBankName == null ? null : other.supplierBankName.copy();
        this.supplierBankBranchCode = other.supplierBankBranchCode == null ? null : other.supplierBankBranchCode.copy();
        this.supplierBankAccountNumber = other.supplierBankAccountNumber == null ? null : other.supplierBankAccountNumber.copy();
        this.supplierBankUserName = other.supplierBankUserName == null ? null : other.supplierBankUserName.copy();
        this.supplierBankPixKey = other.supplierBankPixKey == null ? null : other.supplierBankPixKey.copy();
        this.supplierBankUserNumber = other.supplierBankUserNumber == null ? null : other.supplierBankUserNumber.copy();
        this.suppliersId = other.suppliersId == null ? null : other.suppliersId.copy();
    }

    @Override
    public SupplierBanksInfoCriteria copy() {
        return new SupplierBanksInfoCriteria(this);
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

    public IntegerFilter getSupplierBankCode() {
        return supplierBankCode;
    }

    public IntegerFilter supplierBankCode() {
        if (supplierBankCode == null) {
            supplierBankCode = new IntegerFilter();
        }
        return supplierBankCode;
    }

    public void setSupplierBankCode(IntegerFilter supplierBankCode) {
        this.supplierBankCode = supplierBankCode;
    }

    public StringFilter getSupplierBankName() {
        return supplierBankName;
    }

    public StringFilter supplierBankName() {
        if (supplierBankName == null) {
            supplierBankName = new StringFilter();
        }
        return supplierBankName;
    }

    public void setSupplierBankName(StringFilter supplierBankName) {
        this.supplierBankName = supplierBankName;
    }

    public StringFilter getSupplierBankBranchCode() {
        return supplierBankBranchCode;
    }

    public StringFilter supplierBankBranchCode() {
        if (supplierBankBranchCode == null) {
            supplierBankBranchCode = new StringFilter();
        }
        return supplierBankBranchCode;
    }

    public void setSupplierBankBranchCode(StringFilter supplierBankBranchCode) {
        this.supplierBankBranchCode = supplierBankBranchCode;
    }

    public StringFilter getSupplierBankAccountNumber() {
        return supplierBankAccountNumber;
    }

    public StringFilter supplierBankAccountNumber() {
        if (supplierBankAccountNumber == null) {
            supplierBankAccountNumber = new StringFilter();
        }
        return supplierBankAccountNumber;
    }

    public void setSupplierBankAccountNumber(StringFilter supplierBankAccountNumber) {
        this.supplierBankAccountNumber = supplierBankAccountNumber;
    }

    public StringFilter getSupplierBankUserName() {
        return supplierBankUserName;
    }

    public StringFilter supplierBankUserName() {
        if (supplierBankUserName == null) {
            supplierBankUserName = new StringFilter();
        }
        return supplierBankUserName;
    }

    public void setSupplierBankUserName(StringFilter supplierBankUserName) {
        this.supplierBankUserName = supplierBankUserName;
    }

    public StringFilter getSupplierBankPixKey() {
        return supplierBankPixKey;
    }

    public StringFilter supplierBankPixKey() {
        if (supplierBankPixKey == null) {
            supplierBankPixKey = new StringFilter();
        }
        return supplierBankPixKey;
    }

    public void setSupplierBankPixKey(StringFilter supplierBankPixKey) {
        this.supplierBankPixKey = supplierBankPixKey;
    }

    public StringFilter getSupplierBankUserNumber() {
        return supplierBankUserNumber;
    }

    public StringFilter supplierBankUserNumber() {
        if (supplierBankUserNumber == null) {
            supplierBankUserNumber = new StringFilter();
        }
        return supplierBankUserNumber;
    }

    public void setSupplierBankUserNumber(StringFilter supplierBankUserNumber) {
        this.supplierBankUserNumber = supplierBankUserNumber;
    }

    public LongFilter getSuppliersId() {
        return suppliersId;
    }

    public LongFilter suppliersId() {
        if (suppliersId == null) {
            suppliersId = new LongFilter();
        }
        return suppliersId;
    }

    public void setSuppliersId(LongFilter suppliersId) {
        this.suppliersId = suppliersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SupplierBanksInfoCriteria that = (SupplierBanksInfoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(supplierBankCode, that.supplierBankCode) &&
            Objects.equals(supplierBankName, that.supplierBankName) &&
            Objects.equals(supplierBankBranchCode, that.supplierBankBranchCode) &&
            Objects.equals(supplierBankAccountNumber, that.supplierBankAccountNumber) &&
            Objects.equals(supplierBankUserName, that.supplierBankUserName) &&
            Objects.equals(supplierBankPixKey, that.supplierBankPixKey) &&
            Objects.equals(supplierBankUserNumber, that.supplierBankUserNumber) &&
            Objects.equals(suppliersId, that.suppliersId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            supplierBankCode,
            supplierBankName,
            supplierBankBranchCode,
            supplierBankAccountNumber,
            supplierBankUserName,
            supplierBankPixKey,
            supplierBankUserNumber,
            suppliersId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupplierBanksInfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (supplierBankCode != null ? "supplierBankCode=" + supplierBankCode + ", " : "") +
            (supplierBankName != null ? "supplierBankName=" + supplierBankName + ", " : "") +
            (supplierBankBranchCode != null ? "supplierBankBranchCode=" + supplierBankBranchCode + ", " : "") +
            (supplierBankAccountNumber != null ? "supplierBankAccountNumber=" + supplierBankAccountNumber + ", " : "") +
            (supplierBankUserName != null ? "supplierBankUserName=" + supplierBankUserName + ", " : "") +
            (supplierBankPixKey != null ? "supplierBankPixKey=" + supplierBankPixKey + ", " : "") +
            (supplierBankUserNumber != null ? "supplierBankUserNumber=" + supplierBankUserNumber + ", " : "") +
            (suppliersId != null ? "suppliersId=" + suppliersId + ", " : "") +
            "}";
    }
}
