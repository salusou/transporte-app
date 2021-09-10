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
 * Criteria class for the {@link com.genesisoft.transporte.domain.VehicleControls} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.VehicleControlsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vehicle-controls?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VehicleControlsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter vehicleControlAuthorizedOrder;

    private StringFilter vehicleControlRequest;

    private StringFilter vehicleControlSinister;

    private LocalDateFilter vehicleControlDate;

    private FloatFilter vehicleControlKm;

    private StringFilter vehicleControlPlate;

    private FloatFilter vehicleControlAmount;

    private FloatFilter vehicleControlPrice;

    private LocalDateFilter vehicleControlMaximumDeliveryDate;

    private LocalDateFilter vehicleControlCollectionForecast;

    private LocalDateFilter vehicleControlCollectionDeliveryForecast;

    private LocalDateFilter vehicleControlDateCollected;

    private LocalDateFilter vehicleControlDeliveryDate;

    private LongFilter vehicleLocationStatusId;

    private LongFilter vehicleControlHistoryId;

    private LongFilter vehicleControlBillingId;

    private LongFilter vehicleControlItemId;

    private LongFilter vehicleControlAttachmentsId;

    private LongFilter vehicleControlExpensesId;

    private LongFilter affiliatesId;

    private LongFilter customersId;

    private LongFilter customersGroupsId;

    private LongFilter employeesId;

    private LongFilter originId;

    private LongFilter destinationId;

    private LongFilter statusId;

    public VehicleControlsCriteria() {}

    public VehicleControlsCriteria(VehicleControlsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.vehicleControlAuthorizedOrder =
            other.vehicleControlAuthorizedOrder == null ? null : other.vehicleControlAuthorizedOrder.copy();
        this.vehicleControlRequest = other.vehicleControlRequest == null ? null : other.vehicleControlRequest.copy();
        this.vehicleControlSinister = other.vehicleControlSinister == null ? null : other.vehicleControlSinister.copy();
        this.vehicleControlDate = other.vehicleControlDate == null ? null : other.vehicleControlDate.copy();
        this.vehicleControlKm = other.vehicleControlKm == null ? null : other.vehicleControlKm.copy();
        this.vehicleControlPlate = other.vehicleControlPlate == null ? null : other.vehicleControlPlate.copy();
        this.vehicleControlAmount = other.vehicleControlAmount == null ? null : other.vehicleControlAmount.copy();
        this.vehicleControlPrice = other.vehicleControlPrice == null ? null : other.vehicleControlPrice.copy();
        this.vehicleControlMaximumDeliveryDate =
            other.vehicleControlMaximumDeliveryDate == null ? null : other.vehicleControlMaximumDeliveryDate.copy();
        this.vehicleControlCollectionForecast =
            other.vehicleControlCollectionForecast == null ? null : other.vehicleControlCollectionForecast.copy();
        this.vehicleControlCollectionDeliveryForecast =
            other.vehicleControlCollectionDeliveryForecast == null ? null : other.vehicleControlCollectionDeliveryForecast.copy();
        this.vehicleControlDateCollected = other.vehicleControlDateCollected == null ? null : other.vehicleControlDateCollected.copy();
        this.vehicleControlDeliveryDate = other.vehicleControlDeliveryDate == null ? null : other.vehicleControlDeliveryDate.copy();
        this.vehicleLocationStatusId = other.vehicleLocationStatusId == null ? null : other.vehicleLocationStatusId.copy();
        this.vehicleControlHistoryId = other.vehicleControlHistoryId == null ? null : other.vehicleControlHistoryId.copy();
        this.vehicleControlBillingId = other.vehicleControlBillingId == null ? null : other.vehicleControlBillingId.copy();
        this.vehicleControlItemId = other.vehicleControlItemId == null ? null : other.vehicleControlItemId.copy();
        this.vehicleControlAttachmentsId = other.vehicleControlAttachmentsId == null ? null : other.vehicleControlAttachmentsId.copy();
        this.vehicleControlExpensesId = other.vehicleControlExpensesId == null ? null : other.vehicleControlExpensesId.copy();
        this.affiliatesId = other.affiliatesId == null ? null : other.affiliatesId.copy();
        this.customersId = other.customersId == null ? null : other.customersId.copy();
        this.customersGroupsId = other.customersGroupsId == null ? null : other.customersGroupsId.copy();
        this.employeesId = other.employeesId == null ? null : other.employeesId.copy();
        this.originId = other.originId == null ? null : other.originId.copy();
        this.destinationId = other.destinationId == null ? null : other.destinationId.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
    }

    @Override
    public VehicleControlsCriteria copy() {
        return new VehicleControlsCriteria(this);
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

    public StringFilter getVehicleControlAuthorizedOrder() {
        return vehicleControlAuthorizedOrder;
    }

    public StringFilter vehicleControlAuthorizedOrder() {
        if (vehicleControlAuthorizedOrder == null) {
            vehicleControlAuthorizedOrder = new StringFilter();
        }
        return vehicleControlAuthorizedOrder;
    }

    public void setVehicleControlAuthorizedOrder(StringFilter vehicleControlAuthorizedOrder) {
        this.vehicleControlAuthorizedOrder = vehicleControlAuthorizedOrder;
    }

    public StringFilter getVehicleControlRequest() {
        return vehicleControlRequest;
    }

    public StringFilter vehicleControlRequest() {
        if (vehicleControlRequest == null) {
            vehicleControlRequest = new StringFilter();
        }
        return vehicleControlRequest;
    }

    public void setVehicleControlRequest(StringFilter vehicleControlRequest) {
        this.vehicleControlRequest = vehicleControlRequest;
    }

    public StringFilter getVehicleControlSinister() {
        return vehicleControlSinister;
    }

    public StringFilter vehicleControlSinister() {
        if (vehicleControlSinister == null) {
            vehicleControlSinister = new StringFilter();
        }
        return vehicleControlSinister;
    }

    public void setVehicleControlSinister(StringFilter vehicleControlSinister) {
        this.vehicleControlSinister = vehicleControlSinister;
    }

    public LocalDateFilter getVehicleControlDate() {
        return vehicleControlDate;
    }

    public LocalDateFilter vehicleControlDate() {
        if (vehicleControlDate == null) {
            vehicleControlDate = new LocalDateFilter();
        }
        return vehicleControlDate;
    }

    public void setVehicleControlDate(LocalDateFilter vehicleControlDate) {
        this.vehicleControlDate = vehicleControlDate;
    }

    public FloatFilter getVehicleControlKm() {
        return vehicleControlKm;
    }

    public FloatFilter vehicleControlKm() {
        if (vehicleControlKm == null) {
            vehicleControlKm = new FloatFilter();
        }
        return vehicleControlKm;
    }

    public void setVehicleControlKm(FloatFilter vehicleControlKm) {
        this.vehicleControlKm = vehicleControlKm;
    }

    public StringFilter getVehicleControlPlate() {
        return vehicleControlPlate;
    }

    public StringFilter vehicleControlPlate() {
        if (vehicleControlPlate == null) {
            vehicleControlPlate = new StringFilter();
        }
        return vehicleControlPlate;
    }

    public void setVehicleControlPlate(StringFilter vehicleControlPlate) {
        this.vehicleControlPlate = vehicleControlPlate;
    }

    public FloatFilter getVehicleControlAmount() {
        return vehicleControlAmount;
    }

    public FloatFilter vehicleControlAmount() {
        if (vehicleControlAmount == null) {
            vehicleControlAmount = new FloatFilter();
        }
        return vehicleControlAmount;
    }

    public void setVehicleControlAmount(FloatFilter vehicleControlAmount) {
        this.vehicleControlAmount = vehicleControlAmount;
    }

    public FloatFilter getVehicleControlPrice() {
        return vehicleControlPrice;
    }

    public FloatFilter vehicleControlPrice() {
        if (vehicleControlPrice == null) {
            vehicleControlPrice = new FloatFilter();
        }
        return vehicleControlPrice;
    }

    public void setVehicleControlPrice(FloatFilter vehicleControlPrice) {
        this.vehicleControlPrice = vehicleControlPrice;
    }

    public LocalDateFilter getVehicleControlMaximumDeliveryDate() {
        return vehicleControlMaximumDeliveryDate;
    }

    public LocalDateFilter vehicleControlMaximumDeliveryDate() {
        if (vehicleControlMaximumDeliveryDate == null) {
            vehicleControlMaximumDeliveryDate = new LocalDateFilter();
        }
        return vehicleControlMaximumDeliveryDate;
    }

    public void setVehicleControlMaximumDeliveryDate(LocalDateFilter vehicleControlMaximumDeliveryDate) {
        this.vehicleControlMaximumDeliveryDate = vehicleControlMaximumDeliveryDate;
    }

    public LocalDateFilter getVehicleControlCollectionForecast() {
        return vehicleControlCollectionForecast;
    }

    public LocalDateFilter vehicleControlCollectionForecast() {
        if (vehicleControlCollectionForecast == null) {
            vehicleControlCollectionForecast = new LocalDateFilter();
        }
        return vehicleControlCollectionForecast;
    }

    public void setVehicleControlCollectionForecast(LocalDateFilter vehicleControlCollectionForecast) {
        this.vehicleControlCollectionForecast = vehicleControlCollectionForecast;
    }

    public LocalDateFilter getVehicleControlCollectionDeliveryForecast() {
        return vehicleControlCollectionDeliveryForecast;
    }

    public LocalDateFilter vehicleControlCollectionDeliveryForecast() {
        if (vehicleControlCollectionDeliveryForecast == null) {
            vehicleControlCollectionDeliveryForecast = new LocalDateFilter();
        }
        return vehicleControlCollectionDeliveryForecast;
    }

    public void setVehicleControlCollectionDeliveryForecast(LocalDateFilter vehicleControlCollectionDeliveryForecast) {
        this.vehicleControlCollectionDeliveryForecast = vehicleControlCollectionDeliveryForecast;
    }

    public LocalDateFilter getVehicleControlDateCollected() {
        return vehicleControlDateCollected;
    }

    public LocalDateFilter vehicleControlDateCollected() {
        if (vehicleControlDateCollected == null) {
            vehicleControlDateCollected = new LocalDateFilter();
        }
        return vehicleControlDateCollected;
    }

    public void setVehicleControlDateCollected(LocalDateFilter vehicleControlDateCollected) {
        this.vehicleControlDateCollected = vehicleControlDateCollected;
    }

    public LocalDateFilter getVehicleControlDeliveryDate() {
        return vehicleControlDeliveryDate;
    }

    public LocalDateFilter vehicleControlDeliveryDate() {
        if (vehicleControlDeliveryDate == null) {
            vehicleControlDeliveryDate = new LocalDateFilter();
        }
        return vehicleControlDeliveryDate;
    }

    public void setVehicleControlDeliveryDate(LocalDateFilter vehicleControlDeliveryDate) {
        this.vehicleControlDeliveryDate = vehicleControlDeliveryDate;
    }

    public LongFilter getVehicleLocationStatusId() {
        return vehicleLocationStatusId;
    }

    public LongFilter vehicleLocationStatusId() {
        if (vehicleLocationStatusId == null) {
            vehicleLocationStatusId = new LongFilter();
        }
        return vehicleLocationStatusId;
    }

    public void setVehicleLocationStatusId(LongFilter vehicleLocationStatusId) {
        this.vehicleLocationStatusId = vehicleLocationStatusId;
    }

    public LongFilter getVehicleControlHistoryId() {
        return vehicleControlHistoryId;
    }

    public LongFilter vehicleControlHistoryId() {
        if (vehicleControlHistoryId == null) {
            vehicleControlHistoryId = new LongFilter();
        }
        return vehicleControlHistoryId;
    }

    public void setVehicleControlHistoryId(LongFilter vehicleControlHistoryId) {
        this.vehicleControlHistoryId = vehicleControlHistoryId;
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

    public LongFilter getVehicleControlItemId() {
        return vehicleControlItemId;
    }

    public LongFilter vehicleControlItemId() {
        if (vehicleControlItemId == null) {
            vehicleControlItemId = new LongFilter();
        }
        return vehicleControlItemId;
    }

    public void setVehicleControlItemId(LongFilter vehicleControlItemId) {
        this.vehicleControlItemId = vehicleControlItemId;
    }

    public LongFilter getVehicleControlAttachmentsId() {
        return vehicleControlAttachmentsId;
    }

    public LongFilter vehicleControlAttachmentsId() {
        if (vehicleControlAttachmentsId == null) {
            vehicleControlAttachmentsId = new LongFilter();
        }
        return vehicleControlAttachmentsId;
    }

    public void setVehicleControlAttachmentsId(LongFilter vehicleControlAttachmentsId) {
        this.vehicleControlAttachmentsId = vehicleControlAttachmentsId;
    }

    public LongFilter getVehicleControlExpensesId() {
        return vehicleControlExpensesId;
    }

    public LongFilter vehicleControlExpensesId() {
        if (vehicleControlExpensesId == null) {
            vehicleControlExpensesId = new LongFilter();
        }
        return vehicleControlExpensesId;
    }

    public void setVehicleControlExpensesId(LongFilter vehicleControlExpensesId) {
        this.vehicleControlExpensesId = vehicleControlExpensesId;
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

    public LongFilter getCustomersGroupsId() {
        return customersGroupsId;
    }

    public LongFilter customersGroupsId() {
        if (customersGroupsId == null) {
            customersGroupsId = new LongFilter();
        }
        return customersGroupsId;
    }

    public void setCustomersGroupsId(LongFilter customersGroupsId) {
        this.customersGroupsId = customersGroupsId;
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

    public LongFilter getOriginId() {
        return originId;
    }

    public LongFilter originId() {
        if (originId == null) {
            originId = new LongFilter();
        }
        return originId;
    }

    public void setOriginId(LongFilter originId) {
        this.originId = originId;
    }

    public LongFilter getDestinationId() {
        return destinationId;
    }

    public LongFilter destinationId() {
        if (destinationId == null) {
            destinationId = new LongFilter();
        }
        return destinationId;
    }

    public void setDestinationId(LongFilter destinationId) {
        this.destinationId = destinationId;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public LongFilter statusId() {
        if (statusId == null) {
            statusId = new LongFilter();
        }
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VehicleControlsCriteria that = (VehicleControlsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(vehicleControlAuthorizedOrder, that.vehicleControlAuthorizedOrder) &&
            Objects.equals(vehicleControlRequest, that.vehicleControlRequest) &&
            Objects.equals(vehicleControlSinister, that.vehicleControlSinister) &&
            Objects.equals(vehicleControlDate, that.vehicleControlDate) &&
            Objects.equals(vehicleControlKm, that.vehicleControlKm) &&
            Objects.equals(vehicleControlPlate, that.vehicleControlPlate) &&
            Objects.equals(vehicleControlAmount, that.vehicleControlAmount) &&
            Objects.equals(vehicleControlPrice, that.vehicleControlPrice) &&
            Objects.equals(vehicleControlMaximumDeliveryDate, that.vehicleControlMaximumDeliveryDate) &&
            Objects.equals(vehicleControlCollectionForecast, that.vehicleControlCollectionForecast) &&
            Objects.equals(vehicleControlCollectionDeliveryForecast, that.vehicleControlCollectionDeliveryForecast) &&
            Objects.equals(vehicleControlDateCollected, that.vehicleControlDateCollected) &&
            Objects.equals(vehicleControlDeliveryDate, that.vehicleControlDeliveryDate) &&
            Objects.equals(vehicleLocationStatusId, that.vehicleLocationStatusId) &&
            Objects.equals(vehicleControlHistoryId, that.vehicleControlHistoryId) &&
            Objects.equals(vehicleControlBillingId, that.vehicleControlBillingId) &&
            Objects.equals(vehicleControlItemId, that.vehicleControlItemId) &&
            Objects.equals(vehicleControlAttachmentsId, that.vehicleControlAttachmentsId) &&
            Objects.equals(vehicleControlExpensesId, that.vehicleControlExpensesId) &&
            Objects.equals(affiliatesId, that.affiliatesId) &&
            Objects.equals(customersId, that.customersId) &&
            Objects.equals(customersGroupsId, that.customersGroupsId) &&
            Objects.equals(employeesId, that.employeesId) &&
            Objects.equals(originId, that.originId) &&
            Objects.equals(destinationId, that.destinationId) &&
            Objects.equals(statusId, that.statusId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            vehicleControlAuthorizedOrder,
            vehicleControlRequest,
            vehicleControlSinister,
            vehicleControlDate,
            vehicleControlKm,
            vehicleControlPlate,
            vehicleControlAmount,
            vehicleControlPrice,
            vehicleControlMaximumDeliveryDate,
            vehicleControlCollectionForecast,
            vehicleControlCollectionDeliveryForecast,
            vehicleControlDateCollected,
            vehicleControlDeliveryDate,
            vehicleLocationStatusId,
            vehicleControlHistoryId,
            vehicleControlBillingId,
            vehicleControlItemId,
            vehicleControlAttachmentsId,
            vehicleControlExpensesId,
            affiliatesId,
            customersId,
            customersGroupsId,
            employeesId,
            originId,
            destinationId,
            statusId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleControlsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (vehicleControlAuthorizedOrder != null ? "vehicleControlAuthorizedOrder=" + vehicleControlAuthorizedOrder + ", " : "") +
            (vehicleControlRequest != null ? "vehicleControlRequest=" + vehicleControlRequest + ", " : "") +
            (vehicleControlSinister != null ? "vehicleControlSinister=" + vehicleControlSinister + ", " : "") +
            (vehicleControlDate != null ? "vehicleControlDate=" + vehicleControlDate + ", " : "") +
            (vehicleControlKm != null ? "vehicleControlKm=" + vehicleControlKm + ", " : "") +
            (vehicleControlPlate != null ? "vehicleControlPlate=" + vehicleControlPlate + ", " : "") +
            (vehicleControlAmount != null ? "vehicleControlAmount=" + vehicleControlAmount + ", " : "") +
            (vehicleControlPrice != null ? "vehicleControlPrice=" + vehicleControlPrice + ", " : "") +
            (vehicleControlMaximumDeliveryDate != null ? "vehicleControlMaximumDeliveryDate=" + vehicleControlMaximumDeliveryDate + ", " : "") +
            (vehicleControlCollectionForecast != null ? "vehicleControlCollectionForecast=" + vehicleControlCollectionForecast + ", " : "") +
            (vehicleControlCollectionDeliveryForecast != null ? "vehicleControlCollectionDeliveryForecast=" + vehicleControlCollectionDeliveryForecast + ", " : "") +
            (vehicleControlDateCollected != null ? "vehicleControlDateCollected=" + vehicleControlDateCollected + ", " : "") +
            (vehicleControlDeliveryDate != null ? "vehicleControlDeliveryDate=" + vehicleControlDeliveryDate + ", " : "") +
            (vehicleLocationStatusId != null ? "vehicleLocationStatusId=" + vehicleLocationStatusId + ", " : "") +
            (vehicleControlHistoryId != null ? "vehicleControlHistoryId=" + vehicleControlHistoryId + ", " : "") +
            (vehicleControlBillingId != null ? "vehicleControlBillingId=" + vehicleControlBillingId + ", " : "") +
            (vehicleControlItemId != null ? "vehicleControlItemId=" + vehicleControlItemId + ", " : "") +
            (vehicleControlAttachmentsId != null ? "vehicleControlAttachmentsId=" + vehicleControlAttachmentsId + ", " : "") +
            (vehicleControlExpensesId != null ? "vehicleControlExpensesId=" + vehicleControlExpensesId + ", " : "") +
            (affiliatesId != null ? "affiliatesId=" + affiliatesId + ", " : "") +
            (customersId != null ? "customersId=" + customersId + ", " : "") +
            (customersGroupsId != null ? "customersGroupsId=" + customersGroupsId + ", " : "") +
            (employeesId != null ? "employeesId=" + employeesId + ", " : "") +
            (originId != null ? "originId=" + originId + ", " : "") +
            (destinationId != null ? "destinationId=" + destinationId + ", " : "") +
            (statusId != null ? "statusId=" + statusId + ", " : "") +
            "}";
    }
}
