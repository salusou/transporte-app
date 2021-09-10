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
 * Criteria class for the {@link com.genesisoft.transporte.domain.Suppliers} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.SuppliersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /suppliers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SuppliersCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter supplierName;

    private StringFilter supplierNumber;

    private StringFilter supplierPostalCode;

    private StringFilter supplierAddress;

    private StringFilter supplierAddressComplement;

    private IntegerFilter supplierAddressNumber;

    private StringFilter supplierAddressNeighborhood;

    private StringFilter supplierTelephone;

    private StringFilter supplierEmail;

    private StringFilter supplierContactName;

    private LongFilter supplierBanksInfoId;

    private LongFilter supplierContactsId;

    private LongFilter vehicleControlExpensesId;

    private LongFilter housingId;

    private LongFilter affiliatesId;

    private LongFilter citiesId;

    private LongFilter serviceProvidedId;

    public SuppliersCriteria() {}

    public SuppliersCriteria(SuppliersCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.supplierName = other.supplierName == null ? null : other.supplierName.copy();
        this.supplierNumber = other.supplierNumber == null ? null : other.supplierNumber.copy();
        this.supplierPostalCode = other.supplierPostalCode == null ? null : other.supplierPostalCode.copy();
        this.supplierAddress = other.supplierAddress == null ? null : other.supplierAddress.copy();
        this.supplierAddressComplement = other.supplierAddressComplement == null ? null : other.supplierAddressComplement.copy();
        this.supplierAddressNumber = other.supplierAddressNumber == null ? null : other.supplierAddressNumber.copy();
        this.supplierAddressNeighborhood = other.supplierAddressNeighborhood == null ? null : other.supplierAddressNeighborhood.copy();
        this.supplierTelephone = other.supplierTelephone == null ? null : other.supplierTelephone.copy();
        this.supplierEmail = other.supplierEmail == null ? null : other.supplierEmail.copy();
        this.supplierContactName = other.supplierContactName == null ? null : other.supplierContactName.copy();
        this.supplierBanksInfoId = other.supplierBanksInfoId == null ? null : other.supplierBanksInfoId.copy();
        this.supplierContactsId = other.supplierContactsId == null ? null : other.supplierContactsId.copy();
        this.vehicleControlExpensesId = other.vehicleControlExpensesId == null ? null : other.vehicleControlExpensesId.copy();
        this.housingId = other.housingId == null ? null : other.housingId.copy();
        this.affiliatesId = other.affiliatesId == null ? null : other.affiliatesId.copy();
        this.citiesId = other.citiesId == null ? null : other.citiesId.copy();
        this.serviceProvidedId = other.serviceProvidedId == null ? null : other.serviceProvidedId.copy();
    }

    @Override
    public SuppliersCriteria copy() {
        return new SuppliersCriteria(this);
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

    public StringFilter getSupplierName() {
        return supplierName;
    }

    public StringFilter supplierName() {
        if (supplierName == null) {
            supplierName = new StringFilter();
        }
        return supplierName;
    }

    public void setSupplierName(StringFilter supplierName) {
        this.supplierName = supplierName;
    }

    public StringFilter getSupplierNumber() {
        return supplierNumber;
    }

    public StringFilter supplierNumber() {
        if (supplierNumber == null) {
            supplierNumber = new StringFilter();
        }
        return supplierNumber;
    }

    public void setSupplierNumber(StringFilter supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public StringFilter getSupplierPostalCode() {
        return supplierPostalCode;
    }

    public StringFilter supplierPostalCode() {
        if (supplierPostalCode == null) {
            supplierPostalCode = new StringFilter();
        }
        return supplierPostalCode;
    }

    public void setSupplierPostalCode(StringFilter supplierPostalCode) {
        this.supplierPostalCode = supplierPostalCode;
    }

    public StringFilter getSupplierAddress() {
        return supplierAddress;
    }

    public StringFilter supplierAddress() {
        if (supplierAddress == null) {
            supplierAddress = new StringFilter();
        }
        return supplierAddress;
    }

    public void setSupplierAddress(StringFilter supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public StringFilter getSupplierAddressComplement() {
        return supplierAddressComplement;
    }

    public StringFilter supplierAddressComplement() {
        if (supplierAddressComplement == null) {
            supplierAddressComplement = new StringFilter();
        }
        return supplierAddressComplement;
    }

    public void setSupplierAddressComplement(StringFilter supplierAddressComplement) {
        this.supplierAddressComplement = supplierAddressComplement;
    }

    public IntegerFilter getSupplierAddressNumber() {
        return supplierAddressNumber;
    }

    public IntegerFilter supplierAddressNumber() {
        if (supplierAddressNumber == null) {
            supplierAddressNumber = new IntegerFilter();
        }
        return supplierAddressNumber;
    }

    public void setSupplierAddressNumber(IntegerFilter supplierAddressNumber) {
        this.supplierAddressNumber = supplierAddressNumber;
    }

    public StringFilter getSupplierAddressNeighborhood() {
        return supplierAddressNeighborhood;
    }

    public StringFilter supplierAddressNeighborhood() {
        if (supplierAddressNeighborhood == null) {
            supplierAddressNeighborhood = new StringFilter();
        }
        return supplierAddressNeighborhood;
    }

    public void setSupplierAddressNeighborhood(StringFilter supplierAddressNeighborhood) {
        this.supplierAddressNeighborhood = supplierAddressNeighborhood;
    }

    public StringFilter getSupplierTelephone() {
        return supplierTelephone;
    }

    public StringFilter supplierTelephone() {
        if (supplierTelephone == null) {
            supplierTelephone = new StringFilter();
        }
        return supplierTelephone;
    }

    public void setSupplierTelephone(StringFilter supplierTelephone) {
        this.supplierTelephone = supplierTelephone;
    }

    public StringFilter getSupplierEmail() {
        return supplierEmail;
    }

    public StringFilter supplierEmail() {
        if (supplierEmail == null) {
            supplierEmail = new StringFilter();
        }
        return supplierEmail;
    }

    public void setSupplierEmail(StringFilter supplierEmail) {
        this.supplierEmail = supplierEmail;
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

    public LongFilter getSupplierBanksInfoId() {
        return supplierBanksInfoId;
    }

    public LongFilter supplierBanksInfoId() {
        if (supplierBanksInfoId == null) {
            supplierBanksInfoId = new LongFilter();
        }
        return supplierBanksInfoId;
    }

    public void setSupplierBanksInfoId(LongFilter supplierBanksInfoId) {
        this.supplierBanksInfoId = supplierBanksInfoId;
    }

    public LongFilter getSupplierContactsId() {
        return supplierContactsId;
    }

    public LongFilter supplierContactsId() {
        if (supplierContactsId == null) {
            supplierContactsId = new LongFilter();
        }
        return supplierContactsId;
    }

    public void setSupplierContactsId(LongFilter supplierContactsId) {
        this.supplierContactsId = supplierContactsId;
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

    public LongFilter getServiceProvidedId() {
        return serviceProvidedId;
    }

    public LongFilter serviceProvidedId() {
        if (serviceProvidedId == null) {
            serviceProvidedId = new LongFilter();
        }
        return serviceProvidedId;
    }

    public void setServiceProvidedId(LongFilter serviceProvidedId) {
        this.serviceProvidedId = serviceProvidedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SuppliersCriteria that = (SuppliersCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(supplierName, that.supplierName) &&
            Objects.equals(supplierNumber, that.supplierNumber) &&
            Objects.equals(supplierPostalCode, that.supplierPostalCode) &&
            Objects.equals(supplierAddress, that.supplierAddress) &&
            Objects.equals(supplierAddressComplement, that.supplierAddressComplement) &&
            Objects.equals(supplierAddressNumber, that.supplierAddressNumber) &&
            Objects.equals(supplierAddressNeighborhood, that.supplierAddressNeighborhood) &&
            Objects.equals(supplierTelephone, that.supplierTelephone) &&
            Objects.equals(supplierEmail, that.supplierEmail) &&
            Objects.equals(supplierContactName, that.supplierContactName) &&
            Objects.equals(supplierBanksInfoId, that.supplierBanksInfoId) &&
            Objects.equals(supplierContactsId, that.supplierContactsId) &&
            Objects.equals(vehicleControlExpensesId, that.vehicleControlExpensesId) &&
            Objects.equals(housingId, that.housingId) &&
            Objects.equals(affiliatesId, that.affiliatesId) &&
            Objects.equals(citiesId, that.citiesId) &&
            Objects.equals(serviceProvidedId, that.serviceProvidedId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            supplierName,
            supplierNumber,
            supplierPostalCode,
            supplierAddress,
            supplierAddressComplement,
            supplierAddressNumber,
            supplierAddressNeighborhood,
            supplierTelephone,
            supplierEmail,
            supplierContactName,
            supplierBanksInfoId,
            supplierContactsId,
            vehicleControlExpensesId,
            housingId,
            affiliatesId,
            citiesId,
            serviceProvidedId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SuppliersCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (supplierName != null ? "supplierName=" + supplierName + ", " : "") +
            (supplierNumber != null ? "supplierNumber=" + supplierNumber + ", " : "") +
            (supplierPostalCode != null ? "supplierPostalCode=" + supplierPostalCode + ", " : "") +
            (supplierAddress != null ? "supplierAddress=" + supplierAddress + ", " : "") +
            (supplierAddressComplement != null ? "supplierAddressComplement=" + supplierAddressComplement + ", " : "") +
            (supplierAddressNumber != null ? "supplierAddressNumber=" + supplierAddressNumber + ", " : "") +
            (supplierAddressNeighborhood != null ? "supplierAddressNeighborhood=" + supplierAddressNeighborhood + ", " : "") +
            (supplierTelephone != null ? "supplierTelephone=" + supplierTelephone + ", " : "") +
            (supplierEmail != null ? "supplierEmail=" + supplierEmail + ", " : "") +
            (supplierContactName != null ? "supplierContactName=" + supplierContactName + ", " : "") +
            (supplierBanksInfoId != null ? "supplierBanksInfoId=" + supplierBanksInfoId + ", " : "") +
            (supplierContactsId != null ? "supplierContactsId=" + supplierContactsId + ", " : "") +
            (vehicleControlExpensesId != null ? "vehicleControlExpensesId=" + vehicleControlExpensesId + ", " : "") +
            (housingId != null ? "housingId=" + housingId + ", " : "") +
            (affiliatesId != null ? "affiliatesId=" + affiliatesId + ", " : "") +
            (citiesId != null ? "citiesId=" + citiesId + ", " : "") +
            (serviceProvidedId != null ? "serviceProvidedId=" + serviceProvidedId + ", " : "") +
            "}";
    }
}
