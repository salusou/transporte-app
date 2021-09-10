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
 * Criteria class for the {@link com.genesisoft.transporte.domain.SupplierContacts} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.SupplierContactsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /supplier-contacts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SupplierContactsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter supplierContactName;

    private StringFilter supplierContactPhone;

    private StringFilter supplierContactEmail;

    private LongFilter suppliersId;

    public SupplierContactsCriteria() {}

    public SupplierContactsCriteria(SupplierContactsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.supplierContactName = other.supplierContactName == null ? null : other.supplierContactName.copy();
        this.supplierContactPhone = other.supplierContactPhone == null ? null : other.supplierContactPhone.copy();
        this.supplierContactEmail = other.supplierContactEmail == null ? null : other.supplierContactEmail.copy();
        this.suppliersId = other.suppliersId == null ? null : other.suppliersId.copy();
    }

    @Override
    public SupplierContactsCriteria copy() {
        return new SupplierContactsCriteria(this);
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

    public StringFilter getSupplierContactName() {
        return supplierContactName;
    }

    public StringFilter supplierContactName() {
        if (supplierContactName == null) {
            supplierContactName = new StringFilter();
        }
        return supplierContactName;
    }

    public void setSupplierContactName(StringFilter supplierContactName) {
        this.supplierContactName = supplierContactName;
    }

    public StringFilter getSupplierContactPhone() {
        return supplierContactPhone;
    }

    public StringFilter supplierContactPhone() {
        if (supplierContactPhone == null) {
            supplierContactPhone = new StringFilter();
        }
        return supplierContactPhone;
    }

    public void setSupplierContactPhone(StringFilter supplierContactPhone) {
        this.supplierContactPhone = supplierContactPhone;
    }

    public StringFilter getSupplierContactEmail() {
        return supplierContactEmail;
    }

    public StringFilter supplierContactEmail() {
        if (supplierContactEmail == null) {
            supplierContactEmail = new StringFilter();
        }
        return supplierContactEmail;
    }

    public void setSupplierContactEmail(StringFilter supplierContactEmail) {
        this.supplierContactEmail = supplierContactEmail;
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
        final SupplierContactsCriteria that = (SupplierContactsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(supplierContactName, that.supplierContactName) &&
            Objects.equals(supplierContactPhone, that.supplierContactPhone) &&
            Objects.equals(supplierContactEmail, that.supplierContactEmail) &&
            Objects.equals(suppliersId, that.suppliersId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, supplierContactName, supplierContactPhone, supplierContactEmail, suppliersId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupplierContactsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (supplierContactName != null ? "supplierContactName=" + supplierContactName + ", " : "") +
            (supplierContactPhone != null ? "supplierContactPhone=" + supplierContactPhone + ", " : "") +
            (supplierContactEmail != null ? "supplierContactEmail=" + supplierContactEmail + ", " : "") +
            (suppliersId != null ? "suppliersId=" + suppliersId + ", " : "") +
            "}";
    }
}
