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
 * Criteria class for the {@link com.genesisoft.transporte.domain.Customers} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.CustomersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomersCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter customerName;

    private BooleanFilter active;

    private StringFilter customerNumber;

    private StringFilter customerPostalCode;

    private StringFilter customerAddress;

    private StringFilter customerAddressComplement;

    private IntegerFilter customerAddressNumber;

    private StringFilter customerAddressNeighborhood;

    private StringFilter customerTelephone;

    private IntegerFilter paymentTerm;

    private LongFilter customersContactsId;

    private LongFilter customerAttachmentsId;

    private LongFilter vehicleControlsId;

    private LongFilter housingId;

    private LongFilter affiliatesId;

    private LongFilter citiesId;

    private LongFilter customersGroupsId;

    public CustomersCriteria() {}

    public CustomersCriteria(CustomersCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.customerName = other.customerName == null ? null : other.customerName.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.customerNumber = other.customerNumber == null ? null : other.customerNumber.copy();
        this.customerPostalCode = other.customerPostalCode == null ? null : other.customerPostalCode.copy();
        this.customerAddress = other.customerAddress == null ? null : other.customerAddress.copy();
        this.customerAddressComplement = other.customerAddressComplement == null ? null : other.customerAddressComplement.copy();
        this.customerAddressNumber = other.customerAddressNumber == null ? null : other.customerAddressNumber.copy();
        this.customerAddressNeighborhood = other.customerAddressNeighborhood == null ? null : other.customerAddressNeighborhood.copy();
        this.customerTelephone = other.customerTelephone == null ? null : other.customerTelephone.copy();
        this.paymentTerm = other.paymentTerm == null ? null : other.paymentTerm.copy();
        this.customersContactsId = other.customersContactsId == null ? null : other.customersContactsId.copy();
        this.customerAttachmentsId = other.customerAttachmentsId == null ? null : other.customerAttachmentsId.copy();
        this.vehicleControlsId = other.vehicleControlsId == null ? null : other.vehicleControlsId.copy();
        this.housingId = other.housingId == null ? null : other.housingId.copy();
        this.affiliatesId = other.affiliatesId == null ? null : other.affiliatesId.copy();
        this.citiesId = other.citiesId == null ? null : other.citiesId.copy();
        this.customersGroupsId = other.customersGroupsId == null ? null : other.customersGroupsId.copy();
    }

    @Override
    public CustomersCriteria copy() {
        return new CustomersCriteria(this);
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

    public StringFilter getCustomerName() {
        return customerName;
    }

    public StringFilter customerName() {
        if (customerName == null) {
            customerName = new StringFilter();
        }
        return customerName;
    }

    public void setCustomerName(StringFilter customerName) {
        this.customerName = customerName;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public BooleanFilter active() {
        if (active == null) {
            active = new BooleanFilter();
        }
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public StringFilter getCustomerNumber() {
        return customerNumber;
    }

    public StringFilter customerNumber() {
        if (customerNumber == null) {
            customerNumber = new StringFilter();
        }
        return customerNumber;
    }

    public void setCustomerNumber(StringFilter customerNumber) {
        this.customerNumber = customerNumber;
    }

    public StringFilter getCustomerPostalCode() {
        return customerPostalCode;
    }

    public StringFilter customerPostalCode() {
        if (customerPostalCode == null) {
            customerPostalCode = new StringFilter();
        }
        return customerPostalCode;
    }

    public void setCustomerPostalCode(StringFilter customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public StringFilter getCustomerAddress() {
        return customerAddress;
    }

    public StringFilter customerAddress() {
        if (customerAddress == null) {
            customerAddress = new StringFilter();
        }
        return customerAddress;
    }

    public void setCustomerAddress(StringFilter customerAddress) {
        this.customerAddress = customerAddress;
    }

    public StringFilter getCustomerAddressComplement() {
        return customerAddressComplement;
    }

    public StringFilter customerAddressComplement() {
        if (customerAddressComplement == null) {
            customerAddressComplement = new StringFilter();
        }
        return customerAddressComplement;
    }

    public void setCustomerAddressComplement(StringFilter customerAddressComplement) {
        this.customerAddressComplement = customerAddressComplement;
    }

    public IntegerFilter getCustomerAddressNumber() {
        return customerAddressNumber;
    }

    public IntegerFilter customerAddressNumber() {
        if (customerAddressNumber == null) {
            customerAddressNumber = new IntegerFilter();
        }
        return customerAddressNumber;
    }

    public void setCustomerAddressNumber(IntegerFilter customerAddressNumber) {
        this.customerAddressNumber = customerAddressNumber;
    }

    public StringFilter getCustomerAddressNeighborhood() {
        return customerAddressNeighborhood;
    }

    public StringFilter customerAddressNeighborhood() {
        if (customerAddressNeighborhood == null) {
            customerAddressNeighborhood = new StringFilter();
        }
        return customerAddressNeighborhood;
    }

    public void setCustomerAddressNeighborhood(StringFilter customerAddressNeighborhood) {
        this.customerAddressNeighborhood = customerAddressNeighborhood;
    }

    public StringFilter getCustomerTelephone() {
        return customerTelephone;
    }

    public StringFilter customerTelephone() {
        if (customerTelephone == null) {
            customerTelephone = new StringFilter();
        }
        return customerTelephone;
    }

    public void setCustomerTelephone(StringFilter customerTelephone) {
        this.customerTelephone = customerTelephone;
    }

    public IntegerFilter getPaymentTerm() {
        return paymentTerm;
    }

    public IntegerFilter paymentTerm() {
        if (paymentTerm == null) {
            paymentTerm = new IntegerFilter();
        }
        return paymentTerm;
    }

    public void setPaymentTerm(IntegerFilter paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public LongFilter getCustomersContactsId() {
        return customersContactsId;
    }

    public LongFilter customersContactsId() {
        if (customersContactsId == null) {
            customersContactsId = new LongFilter();
        }
        return customersContactsId;
    }

    public void setCustomersContactsId(LongFilter customersContactsId) {
        this.customersContactsId = customersContactsId;
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

    public LongFilter getHousingId() {
        return housingId;
    }

    public LongFilter housingId() {
        if (housingId == null) {
            housingId = new LongFilter();
        }
        return housingId;
    }

    public void setHousingId(LongFilter housingId) {
        this.housingId = housingId;
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

    public LongFilter getCitiesId() {
        return citiesId;
    }

    public LongFilter citiesId() {
        if (citiesId == null) {
            citiesId = new LongFilter();
        }
        return citiesId;
    }

    public void setCitiesId(LongFilter citiesId) {
        this.citiesId = citiesId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomersCriteria that = (CustomersCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(customerName, that.customerName) &&
            Objects.equals(active, that.active) &&
            Objects.equals(customerNumber, that.customerNumber) &&
            Objects.equals(customerPostalCode, that.customerPostalCode) &&
            Objects.equals(customerAddress, that.customerAddress) &&
            Objects.equals(customerAddressComplement, that.customerAddressComplement) &&
            Objects.equals(customerAddressNumber, that.customerAddressNumber) &&
            Objects.equals(customerAddressNeighborhood, that.customerAddressNeighborhood) &&
            Objects.equals(customerTelephone, that.customerTelephone) &&
            Objects.equals(paymentTerm, that.paymentTerm) &&
            Objects.equals(customersContactsId, that.customersContactsId) &&
            Objects.equals(customerAttachmentsId, that.customerAttachmentsId) &&
            Objects.equals(vehicleControlsId, that.vehicleControlsId) &&
            Objects.equals(housingId, that.housingId) &&
            Objects.equals(affiliatesId, that.affiliatesId) &&
            Objects.equals(citiesId, that.citiesId) &&
            Objects.equals(customersGroupsId, that.customersGroupsId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            customerName,
            active,
            customerNumber,
            customerPostalCode,
            customerAddress,
            customerAddressComplement,
            customerAddressNumber,
            customerAddressNeighborhood,
            customerTelephone,
            paymentTerm,
            customersContactsId,
            customerAttachmentsId,
            vehicleControlsId,
            housingId,
            affiliatesId,
            citiesId,
            customersGroupsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomersCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (customerName != null ? "customerName=" + customerName + ", " : "") +
            (active != null ? "active=" + active + ", " : "") +
            (customerNumber != null ? "customerNumber=" + customerNumber + ", " : "") +
            (customerPostalCode != null ? "customerPostalCode=" + customerPostalCode + ", " : "") +
            (customerAddress != null ? "customerAddress=" + customerAddress + ", " : "") +
            (customerAddressComplement != null ? "customerAddressComplement=" + customerAddressComplement + ", " : "") +
            (customerAddressNumber != null ? "customerAddressNumber=" + customerAddressNumber + ", " : "") +
            (customerAddressNeighborhood != null ? "customerAddressNeighborhood=" + customerAddressNeighborhood + ", " : "") +
            (customerTelephone != null ? "customerTelephone=" + customerTelephone + ", " : "") +
            (paymentTerm != null ? "paymentTerm=" + paymentTerm + ", " : "") +
            (customersContactsId != null ? "customersContactsId=" + customersContactsId + ", " : "") +
            (customerAttachmentsId != null ? "customerAttachmentsId=" + customerAttachmentsId + ", " : "") +
            (vehicleControlsId != null ? "vehicleControlsId=" + vehicleControlsId + ", " : "") +
            (housingId != null ? "housingId=" + housingId + ", " : "") +
            (affiliatesId != null ? "affiliatesId=" + affiliatesId + ", " : "") +
            (citiesId != null ? "citiesId=" + citiesId + ", " : "") +
            (customersGroupsId != null ? "customersGroupsId=" + customersGroupsId + ", " : "") +
            "}";
    }
}
