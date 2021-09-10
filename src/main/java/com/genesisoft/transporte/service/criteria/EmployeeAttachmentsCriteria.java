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
 * Criteria class for the {@link com.genesisoft.transporte.domain.EmployeeAttachments} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.EmployeeAttachmentsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-attachments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeAttachmentsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter attachUrl;

    private StringFilter attachDescription;

    private ZonedDateTimeFilter attachedDate;

    private LongFilter employeesId;

    public EmployeeAttachmentsCriteria() {}

    public EmployeeAttachmentsCriteria(EmployeeAttachmentsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.attachUrl = other.attachUrl == null ? null : other.attachUrl.copy();
        this.attachDescription = other.attachDescription == null ? null : other.attachDescription.copy();
        this.attachedDate = other.attachedDate == null ? null : other.attachedDate.copy();
        this.employeesId = other.employeesId == null ? null : other.employeesId.copy();
    }

    @Override
    public EmployeeAttachmentsCriteria copy() {
        return new EmployeeAttachmentsCriteria(this);
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

    public LongFilter getEmployeesId() {
        return employeesId;
    }

    public LongFilter employeesId() {
        if (employeesId == null) {
            employeesId = new LongFilter();
        }
        return employeesId;
    }

    public void setEmployeesId(LongFilter employeesId) {
        this.employeesId = employeesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeAttachmentsCriteria that = (EmployeeAttachmentsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(attachUrl, that.attachUrl) &&
            Objects.equals(attachDescription, that.attachDescription) &&
            Objects.equals(attachedDate, that.attachedDate) &&
            Objects.equals(employeesId, that.employeesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attachUrl, attachDescription, attachedDate, employeesId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeAttachmentsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (attachUrl != null ? "attachUrl=" + attachUrl + ", " : "") +
            (attachDescription != null ? "attachDescription=" + attachDescription + ", " : "") +
            (attachedDate != null ? "attachedDate=" + attachedDate + ", " : "") +
            (employeesId != null ? "employeesId=" + employeesId + ", " : "") +
            "}";
    }
}
