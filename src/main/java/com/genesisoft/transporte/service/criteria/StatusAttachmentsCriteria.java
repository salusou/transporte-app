package com.genesisoft.transporte.service.criteria;

import com.genesisoft.transporte.domain.enumeration.AttachmentType;
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
 * Criteria class for the {@link com.genesisoft.transporte.domain.StatusAttachments} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.StatusAttachmentsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /status-attachments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StatusAttachmentsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering AttachmentType
     */
    public static class AttachmentTypeFilter extends Filter<AttachmentType> {

        public AttachmentTypeFilter() {}

        public AttachmentTypeFilter(AttachmentTypeFilter filter) {
            super(filter);
        }

        @Override
        public AttachmentTypeFilter copy() {
            return new AttachmentTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter statusName;

    private StringFilter statusDescriptions;

    private StringFilter statusObs;

    private AttachmentTypeFilter attachmentType;

    private LongFilter customerAttachmentsId;

    private LongFilter affiliatesId;

    public StatusAttachmentsCriteria() {}

    public StatusAttachmentsCriteria(StatusAttachmentsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.statusName = other.statusName == null ? null : other.statusName.copy();
        this.statusDescriptions = other.statusDescriptions == null ? null : other.statusDescriptions.copy();
        this.statusObs = other.statusObs == null ? null : other.statusObs.copy();
        this.attachmentType = other.attachmentType == null ? null : other.attachmentType.copy();
        this.customerAttachmentsId = other.customerAttachmentsId == null ? null : other.customerAttachmentsId.copy();
        this.affiliatesId = other.affiliatesId == null ? null : other.affiliatesId.copy();
    }

    @Override
    public StatusAttachmentsCriteria copy() {
        return new StatusAttachmentsCriteria(this);
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

    public StringFilter getStatusName() {
        return statusName;
    }

    public StringFilter statusName() {
        if (statusName == null) {
            statusName = new StringFilter();
        }
        return statusName;
    }

    public void setStatusName(StringFilter statusName) {
        this.statusName = statusName;
    }

    public StringFilter getStatusDescriptions() {
        return statusDescriptions;
    }

    public StringFilter statusDescriptions() {
        if (statusDescriptions == null) {
            statusDescriptions = new StringFilter();
        }
        return statusDescriptions;
    }

    public void setStatusDescriptions(StringFilter statusDescriptions) {
        this.statusDescriptions = statusDescriptions;
    }

    public StringFilter getStatusObs() {
        return statusObs;
    }

    public StringFilter statusObs() {
        if (statusObs == null) {
            statusObs = new StringFilter();
        }
        return statusObs;
    }

    public void setStatusObs(StringFilter statusObs) {
        this.statusObs = statusObs;
    }

    public AttachmentTypeFilter getAttachmentType() {
        return attachmentType;
    }

    public AttachmentTypeFilter attachmentType() {
        if (attachmentType == null) {
            attachmentType = new AttachmentTypeFilter();
        }
        return attachmentType;
    }

    public void setAttachmentType(AttachmentTypeFilter attachmentType) {
        this.attachmentType = attachmentType;
    }

    public LongFilter getCustomerAttachmentsId() {
        return customerAttachmentsId;
    }

    public LongFilter customerAttachmentsId() {
        if (customerAttachmentsId == null) {
            customerAttachmentsId = new LongFilter();
        }
        return customerAttachmentsId;
    }

    public void setCustomerAttachmentsId(LongFilter customerAttachmentsId) {
        this.customerAttachmentsId = customerAttachmentsId;
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
        final StatusAttachmentsCriteria that = (StatusAttachmentsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(statusName, that.statusName) &&
            Objects.equals(statusDescriptions, that.statusDescriptions) &&
            Objects.equals(statusObs, that.statusObs) &&
            Objects.equals(attachmentType, that.attachmentType) &&
            Objects.equals(customerAttachmentsId, that.customerAttachmentsId) &&
            Objects.equals(affiliatesId, that.affiliatesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, statusName, statusDescriptions, statusObs, attachmentType, customerAttachmentsId, affiliatesId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatusAttachmentsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (statusName != null ? "statusName=" + statusName + ", " : "") +
            (statusDescriptions != null ? "statusDescriptions=" + statusDescriptions + ", " : "") +
            (statusObs != null ? "statusObs=" + statusObs + ", " : "") +
            (attachmentType != null ? "attachmentType=" + attachmentType + ", " : "") +
            (customerAttachmentsId != null ? "customerAttachmentsId=" + customerAttachmentsId + ", " : "") +
            (affiliatesId != null ? "affiliatesId=" + affiliatesId + ", " : "") +
            "}";
    }
}
