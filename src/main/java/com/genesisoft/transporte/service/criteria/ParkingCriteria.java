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
 * Criteria class for the {@link com.genesisoft.transporte.domain.Parking} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.ParkingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /parkings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ParkingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter active;

    private StringFilter parkingName;

    private StringFilter parkingTradeName;

    private StringFilter parkingNumber;

    private StringFilter parkingPostalCode;

    private StringFilter parkingAddress;

    private StringFilter parkingAddressComplement;

    private IntegerFilter parkingAddressNumber;

    private StringFilter parkingAddressNeighborhood;

    private StringFilter parkingTelephone;

    private StringFilter parkingEmail;

    private StringFilter parkingContactName;

    private LongFilter parkingSectorId;

    private LongFilter housingId;

    private LongFilter affiliatesId;

    private LongFilter citiesId;

    public ParkingCriteria() {}

    public ParkingCriteria(ParkingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.parkingName = other.parkingName == null ? null : other.parkingName.copy();
        this.parkingTradeName = other.parkingTradeName == null ? null : other.parkingTradeName.copy();
        this.parkingNumber = other.parkingNumber == null ? null : other.parkingNumber.copy();
        this.parkingPostalCode = other.parkingPostalCode == null ? null : other.parkingPostalCode.copy();
        this.parkingAddress = other.parkingAddress == null ? null : other.parkingAddress.copy();
        this.parkingAddressComplement = other.parkingAddressComplement == null ? null : other.parkingAddressComplement.copy();
        this.parkingAddressNumber = other.parkingAddressNumber == null ? null : other.parkingAddressNumber.copy();
        this.parkingAddressNeighborhood = other.parkingAddressNeighborhood == null ? null : other.parkingAddressNeighborhood.copy();
        this.parkingTelephone = other.parkingTelephone == null ? null : other.parkingTelephone.copy();
        this.parkingEmail = other.parkingEmail == null ? null : other.parkingEmail.copy();
        this.parkingContactName = other.parkingContactName == null ? null : other.parkingContactName.copy();
        this.parkingSectorId = other.parkingSectorId == null ? null : other.parkingSectorId.copy();
        this.housingId = other.housingId == null ? null : other.housingId.copy();
        this.affiliatesId = other.affiliatesId == null ? null : other.affiliatesId.copy();
        this.citiesId = other.citiesId == null ? null : other.citiesId.copy();
    }

    @Override
    public ParkingCriteria copy() {
        return new ParkingCriteria(this);
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

    public StringFilter getParkingName() {
        return parkingName;
    }

    public StringFilter parkingName() {
        if (parkingName == null) {
            parkingName = new StringFilter();
        }
        return parkingName;
    }

    public void setParkingName(StringFilter parkingName) {
        this.parkingName = parkingName;
    }

    public StringFilter getParkingTradeName() {
        return parkingTradeName;
    }

    public StringFilter parkingTradeName() {
        if (parkingTradeName == null) {
            parkingTradeName = new StringFilter();
        }
        return parkingTradeName;
    }

    public void setParkingTradeName(StringFilter parkingTradeName) {
        this.parkingTradeName = parkingTradeName;
    }

    public StringFilter getParkingNumber() {
        return parkingNumber;
    }

    public StringFilter parkingNumber() {
        if (parkingNumber == null) {
            parkingNumber = new StringFilter();
        }
        return parkingNumber;
    }

    public void setParkingNumber(StringFilter parkingNumber) {
        this.parkingNumber = parkingNumber;
    }

    public StringFilter getParkingPostalCode() {
        return parkingPostalCode;
    }

    public StringFilter parkingPostalCode() {
        if (parkingPostalCode == null) {
            parkingPostalCode = new StringFilter();
        }
        return parkingPostalCode;
    }

    public void setParkingPostalCode(StringFilter parkingPostalCode) {
        this.parkingPostalCode = parkingPostalCode;
    }

    public StringFilter getParkingAddress() {
        return parkingAddress;
    }

    public StringFilter parkingAddress() {
        if (parkingAddress == null) {
            parkingAddress = new StringFilter();
        }
        return parkingAddress;
    }

    public void setParkingAddress(StringFilter parkingAddress) {
        this.parkingAddress = parkingAddress;
    }

    public StringFilter getParkingAddressComplement() {
        return parkingAddressComplement;
    }

    public StringFilter parkingAddressComplement() {
        if (parkingAddressComplement == null) {
            parkingAddressComplement = new StringFilter();
        }
        return parkingAddressComplement;
    }

    public void setParkingAddressComplement(StringFilter parkingAddressComplement) {
        this.parkingAddressComplement = parkingAddressComplement;
    }

    public IntegerFilter getParkingAddressNumber() {
        return parkingAddressNumber;
    }

    public IntegerFilter parkingAddressNumber() {
        if (parkingAddressNumber == null) {
            parkingAddressNumber = new IntegerFilter();
        }
        return parkingAddressNumber;
    }

    public void setParkingAddressNumber(IntegerFilter parkingAddressNumber) {
        this.parkingAddressNumber = parkingAddressNumber;
    }

    public StringFilter getParkingAddressNeighborhood() {
        return parkingAddressNeighborhood;
    }

    public StringFilter parkingAddressNeighborhood() {
        if (parkingAddressNeighborhood == null) {
            parkingAddressNeighborhood = new StringFilter();
        }
        return parkingAddressNeighborhood;
    }

    public void setParkingAddressNeighborhood(StringFilter parkingAddressNeighborhood) {
        this.parkingAddressNeighborhood = parkingAddressNeighborhood;
    }

    public StringFilter getParkingTelephone() {
        return parkingTelephone;
    }

    public StringFilter parkingTelephone() {
        if (parkingTelephone == null) {
            parkingTelephone = new StringFilter();
        }
        return parkingTelephone;
    }

    public void setParkingTelephone(StringFilter parkingTelephone) {
        this.parkingTelephone = parkingTelephone;
    }

    public StringFilter getParkingEmail() {
        return parkingEmail;
    }

    public StringFilter parkingEmail() {
        if (parkingEmail == null) {
            parkingEmail = new StringFilter();
        }
        return parkingEmail;
    }

    public void setParkingEmail(StringFilter parkingEmail) {
        this.parkingEmail = parkingEmail;
    }

    public StringFilter getParkingContactName() {
        return parkingContactName;
    }

    public StringFilter parkingContactName() {
        if (parkingContactName == null) {
            parkingContactName = new StringFilter();
        }
        return parkingContactName;
    }

    public void setParkingContactName(StringFilter parkingContactName) {
        this.parkingContactName = parkingContactName;
    }

    public LongFilter getParkingSectorId() {
        return parkingSectorId;
    }

    public LongFilter parkingSectorId() {
        if (parkingSectorId == null) {
            parkingSectorId = new LongFilter();
        }
        return parkingSectorId;
    }

    public void setParkingSectorId(LongFilter parkingSectorId) {
        this.parkingSectorId = parkingSectorId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ParkingCriteria that = (ParkingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(active, that.active) &&
            Objects.equals(parkingName, that.parkingName) &&
            Objects.equals(parkingTradeName, that.parkingTradeName) &&
            Objects.equals(parkingNumber, that.parkingNumber) &&
            Objects.equals(parkingPostalCode, that.parkingPostalCode) &&
            Objects.equals(parkingAddress, that.parkingAddress) &&
            Objects.equals(parkingAddressComplement, that.parkingAddressComplement) &&
            Objects.equals(parkingAddressNumber, that.parkingAddressNumber) &&
            Objects.equals(parkingAddressNeighborhood, that.parkingAddressNeighborhood) &&
            Objects.equals(parkingTelephone, that.parkingTelephone) &&
            Objects.equals(parkingEmail, that.parkingEmail) &&
            Objects.equals(parkingContactName, that.parkingContactName) &&
            Objects.equals(parkingSectorId, that.parkingSectorId) &&
            Objects.equals(housingId, that.housingId) &&
            Objects.equals(affiliatesId, that.affiliatesId) &&
            Objects.equals(citiesId, that.citiesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            active,
            parkingName,
            parkingTradeName,
            parkingNumber,
            parkingPostalCode,
            parkingAddress,
            parkingAddressComplement,
            parkingAddressNumber,
            parkingAddressNeighborhood,
            parkingTelephone,
            parkingEmail,
            parkingContactName,
            parkingSectorId,
            housingId,
            affiliatesId,
            citiesId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParkingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (active != null ? "active=" + active + ", " : "") +
            (parkingName != null ? "parkingName=" + parkingName + ", " : "") +
            (parkingTradeName != null ? "parkingTradeName=" + parkingTradeName + ", " : "") +
            (parkingNumber != null ? "parkingNumber=" + parkingNumber + ", " : "") +
            (parkingPostalCode != null ? "parkingPostalCode=" + parkingPostalCode + ", " : "") +
            (parkingAddress != null ? "parkingAddress=" + parkingAddress + ", " : "") +
            (parkingAddressComplement != null ? "parkingAddressComplement=" + parkingAddressComplement + ", " : "") +
            (parkingAddressNumber != null ? "parkingAddressNumber=" + parkingAddressNumber + ", " : "") +
            (parkingAddressNeighborhood != null ? "parkingAddressNeighborhood=" + parkingAddressNeighborhood + ", " : "") +
            (parkingTelephone != null ? "parkingTelephone=" + parkingTelephone + ", " : "") +
            (parkingEmail != null ? "parkingEmail=" + parkingEmail + ", " : "") +
            (parkingContactName != null ? "parkingContactName=" + parkingContactName + ", " : "") +
            (parkingSectorId != null ? "parkingSectorId=" + parkingSectorId + ", " : "") +
            (housingId != null ? "housingId=" + housingId + ", " : "") +
            (affiliatesId != null ? "affiliatesId=" + affiliatesId + ", " : "") +
            (citiesId != null ? "citiesId=" + citiesId + ", " : "") +
            "}";
    }
}
