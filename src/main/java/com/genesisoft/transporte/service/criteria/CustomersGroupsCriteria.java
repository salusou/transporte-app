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
 * Criteria class for the {@link com.genesisoft.transporte.domain.CustomersGroups} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.CustomersGroupsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customers-groups?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomersGroupsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter groupName;

    private LongFilter customersId;

    private LongFilter vehicleControlsId;

    private LongFilter affiliatesId;

    public CustomersGroupsCriteria() {}

    public CustomersGroupsCriteria(CustomersGroupsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.groupName = other.groupName == null ? null : other.groupName.copy();
        this.customersId = other.customersId == null ? null : other.customersId.copy();
        this.vehicleControlsId = other.vehicleControlsId == null ? null : other.vehicleControlsId.copy();
        this.affiliatesId = other.affiliatesId == null ? null : other.affiliatesId.copy();
    }

    @Override
    public CustomersGroupsCriteria copy() {
        return new CustomersGroupsCriteria(this);
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

    public StringFilter getGroupName() {
        return groupName;
    }

    public StringFilter groupName() {
        if (groupName == null) {
            groupName = new StringFilter();
        }
        return groupName;
    }

    public void setGroupName(StringFilter groupName) {
        this.groupName = groupName;
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
        final CustomersGroupsCriteria that = (CustomersGroupsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(groupName, that.groupName) &&
            Objects.equals(customersId, that.customersId) &&
            Objects.equals(vehicleControlsId, that.vehicleControlsId) &&
            Objects.equals(affiliatesId, that.affiliatesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupName, customersId, vehicleControlsId, affiliatesId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomersGroupsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (groupName != null ? "groupName=" + groupName + ", " : "") +
            (customersId != null ? "customersId=" + customersId + ", " : "") +
            (vehicleControlsId != null ? "vehicleControlsId=" + vehicleControlsId + ", " : "") +
            (affiliatesId != null ? "affiliatesId=" + affiliatesId + ", " : "") +
            "}";
    }
}
