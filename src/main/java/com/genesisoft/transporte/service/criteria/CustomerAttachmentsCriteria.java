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
import tech.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.genesisoft.transporte.domain.CustomerAttachments} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.CustomerAttachmentsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customer-attachments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomerAttachmentsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter attachUrl;

    private StringFilter attachDescription;

    private ZonedDateTimeFilter attachedDate;

    private LongFilter customersId;

    private LongFilter statusAttachmentsId;

    public CustomerAttachmentsCriteria() {}

    public CustomerAttachmentsCriteria(CustomerAttachmentsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.attachUrl = other.attachUrl == null ? null : other.attachUrl.copy();
        this.attachDescription = other.attachDescription == null ? null : other.attachDescription.copy();
        this.attachedDate = other.attachedDate == null ? null : other.attachedDate.copy();
        this.customersId = other.customersId == null ? null : other.customersId.copy();
        this.statusAttachmentsId = other.statusAttachmentsId == null ? null : other.statusAttachmentsId.copy();
    }

    @Override
    public CustomerAttachmentsCriteria copy() {
        return new CustomerAttachmentsCriteria(this);
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

    public StringFilter getAttachUrl() {
        return attachUrl;
    }

    public StringFilter attachUrl() {
        if (attachUrl == null) {
            attachUrl = new StringFilter();
        }
        return attachUrl;
    }

    public void setAttachUrl(StringFilter attachUrl) {
        this.attachUrl = attachUrl;
    }

    public StringFilter getAttachDescription() {
        return attachDescription;
    }

    public StringFilter attachDescription() {
        if (attachDescription == null) {
            attachDescription = new StringFilter();
        }
        return attachDescription;
    }

    public void setAttachDescription(StringFilter attachDescription) {
        this.attachDescription = attachDescription;
    }

    public ZonedDateTimeFilter getAttachedDate() {
        return attachedDate;
    }

    public ZonedDateTimeFilter attachedDate() {
        if (attachedDate == null) {
            attachedDate = new ZonedDateTimeFilter();
        }
        return attachedDate;
    }

    public void setAttachedDate(ZonedDateTimeFilter attachedDate) {
        this.attachedDate = attachedDate;
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

    public LongFilter getStatusAttachmentsId() {
        return statusAttachmentsId;
    }

    public LongFilter statusAttachmentsId() {
        if (statusAttachmentsId == null) {
            statusAttachmentsId = new LongFilter();
        }
        return statusAttachmentsId;
    }

    public void setStatusAttachmentsId(LongFilter statusAttachmentsId) {
        this.statusAttachmentsId = statusAttachmentsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomerAttachmentsCriteria that = (CustomerAttachmentsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(attachUrl, that.attachUrl) &&
            Objects.equals(attachDescription, that.attachDescription) &&
            Objects.equals(attachedDate, that.attachedDate) &&
            Objects.equals(customersId, that.customersId) &&
            Objects.equals(statusAttachmentsId, that.statusAttachmentsId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attachUrl, attachDescription, attachedDate, customersId, statusAttachmentsId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerAttachmentsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (attachUrl != null ? "attachUrl=" + attachUrl + ", " : "") +
            (attachDescription != null ? "attachDescription=" + attachDescription + ", " : "") +
            (attachedDate != null ? "attachedDate=" + attachedDate + ", " : "") +
            (customersId != null ? "customersId=" + customersId + ", " : "") +
            (statusAttachmentsId != null ? "statusAttachmentsId=" + statusAttachmentsId + ", " : "") +
            "}";
    }
}
