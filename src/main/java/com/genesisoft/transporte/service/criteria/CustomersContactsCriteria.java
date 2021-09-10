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
 * Criteria class for the {@link com.genesisoft.transporte.domain.CustomersContacts} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.CustomersContactsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customers-contacts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomersContactsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter contactName;

    private StringFilter contactTelephone;

    private StringFilter contactEmail;

    private LongFilter customersId;

    public CustomersContactsCriteria() {}

    public CustomersContactsCriteria(CustomersContactsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.contactName = other.contactName == null ? null : other.contactName.copy();
        this.contactTelephone = other.contactTelephone == null ? null : other.contactTelephone.copy();
        this.contactEmail = other.contactEmail == null ? null : other.contactEmail.copy();
        this.customersId = other.customersId == null ? null : other.customersId.copy();
    }

    @Override
    public CustomersContactsCriteria copy() {
        return new CustomersContactsCriteria(this);
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

    public StringFilter getContactName() {
        return contactName;
    }

    public StringFilter contactName() {
        if (contactName == null) {
            contactName = new StringFilter();
        }
        return contactName;
    }

    public void setContactName(StringFilter contactName) {
        this.contactName = contactName;
    }

    public StringFilter getContactTelephone() {
        return contactTelephone;
    }

    public StringFilter contactTelephone() {
        if (contactTelephone == null) {
            contactTelephone = new StringFilter();
        }
        return contactTelephone;
    }

    public void setContactTelephone(StringFilter contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    public StringFilter getContactEmail() {
        return contactEmail;
    }

    public StringFilter contactEmail() {
        if (contactEmail == null) {
            contactEmail = new StringFilter();
        }
        return contactEmail;
    }

    public void setContactEmail(StringFilter contactEmail) {
        this.contactEmail = contactEmail;
    }

    public LongFilter getCustomersId() {
        return customersId;
    }

    public LongFilter customersId() {
        if (customersId == null) {
            customersId = new LongFilter();
        }
        return customersId;
    }

    public void setCustomersId(LongFilter customersId) {
        this.customersId = customersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomersContactsCriteria that = (CustomersContactsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(contactName, that.contactName) &&
            Objects.equals(contactTelephone, that.contactTelephone) &&
            Objects.equals(contactEmail, that.contactEmail) &&
            Objects.equals(customersId, that.customersId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contactName, contactTelephone, contactEmail, customersId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomersContactsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (contactName != null ? "contactName=" + contactName + ", " : "") +
            (contactTelephone != null ? "contactTelephone=" + contactTelephone + ", " : "") +
            (contactEmail != null ? "contactEmail=" + contactEmail + ", " : "") +
            (customersId != null ? "customersId=" + customersId + ", " : "") +
            "}";
    }
}
