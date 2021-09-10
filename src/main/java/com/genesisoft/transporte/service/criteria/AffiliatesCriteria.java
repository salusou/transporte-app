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
 * Criteria class for the {@link com.genesisoft.transporte.domain.Affiliates} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.AffiliatesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /affiliates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AffiliatesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter branchName;

    private StringFilter branchNumber;

    private BooleanFilter useSameCompanyAddress;

    private StringFilter branchPostalCode;

    private StringFilter branchAddress;

    private StringFilter branchAddressComplement;

    private IntegerFilter branchAddressNumber;

    private StringFilter branchAddressNeighborhood;

    private StringFilter branchTelephone;

    private StringFilter branchEmail;

    private StringFilter responsibleContact;

    private LongFilter insurancesId;

    private LongFilter positionsId;

    private LongFilter costCenterId;

    private LongFilter administrativeFeesRangesId;

    private LongFilter customersGroupsId;

    private LongFilter feesId;

    private LongFilter customersId;

    private LongFilter statusAttachmentsId;

    private LongFilter statusId;

    private LongFilter parkingId;

    private LongFilter suppliersId;

    private LongFilter employeesId;

    private LongFilter vehicleControlsId;

    private LongFilter housingId;

    private LongFilter stateProvincesId;

    private LongFilter citiesId;

    private LongFilter companiesId;

    public AffiliatesCriteria() {}

    public AffiliatesCriteria(AffiliatesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.branchName = other.branchName == null ? null : other.branchName.copy();
        this.branchNumber = other.branchNumber == null ? null : other.branchNumber.copy();
        this.useSameCompanyAddress = other.useSameCompanyAddress == null ? null : other.useSameCompanyAddress.copy();
        this.branchPostalCode = other.branchPostalCode == null ? null : other.branchPostalCode.copy();
        this.branchAddress = other.branchAddress == null ? null : other.branchAddress.copy();
        this.branchAddressComplement = other.branchAddressComplement == null ? null : other.branchAddressComplement.copy();
        this.branchAddressNumber = other.branchAddressNumber == null ? null : other.branchAddressNumber.copy();
        this.branchAddressNeighborhood = other.branchAddressNeighborhood == null ? null : other.branchAddressNeighborhood.copy();
        this.branchTelephone = other.branchTelephone == null ? null : other.branchTelephone.copy();
        this.branchEmail = other.branchEmail == null ? null : other.branchEmail.copy();
        this.responsibleContact = other.responsibleContact == null ? null : other.responsibleContact.copy();
        this.insurancesId = other.insurancesId == null ? null : other.insurancesId.copy();
        this.positionsId = other.positionsId == null ? null : other.positionsId.copy();
        this.costCenterId = other.costCenterId == null ? null : other.costCenterId.copy();
        this.administrativeFeesRangesId = other.administrativeFeesRangesId == null ? null : other.administrativeFeesRangesId.copy();
        this.customersGroupsId = other.customersGroupsId == null ? null : other.customersGroupsId.copy();
        this.feesId = other.feesId == null ? null : other.feesId.copy();
        this.customersId = other.customersId == null ? null : other.customersId.copy();
        this.statusAttachmentsId = other.statusAttachmentsId == null ? null : other.statusAttachmentsId.copy();
        this.statusId = other.statusId == null ? null : other.statusId.copy();
        this.parkingId = other.parkingId == null ? null : other.parkingId.copy();
        this.suppliersId = other.suppliersId == null ? null : other.suppliersId.copy();
        this.employeesId = other.employeesId == null ? null : other.employeesId.copy();
        this.vehicleControlsId = other.vehicleControlsId == null ? null : other.vehicleControlsId.copy();
        this.housingId = other.housingId == null ? null : other.housingId.copy();
        this.stateProvincesId = other.stateProvincesId == null ? null : other.stateProvincesId.copy();
        this.citiesId = other.citiesId == null ? null : other.citiesId.copy();
        this.companiesId = other.companiesId == null ? null : other.companiesId.copy();
    }

    @Override
    public AffiliatesCriteria copy() {
        return new AffiliatesCriteria(this);
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

    public StringFilter getBranchName() {
        return branchName;
    }

    public StringFilter branchName() {
        if (branchName == null) {
            branchName = new StringFilter();
        }
        return branchName;
    }

    public void setBranchName(StringFilter branchName) {
        this.branchName = branchName;
    }

    public StringFilter getBranchNumber() {
        return branchNumber;
    }

    public StringFilter branchNumber() {
        if (branchNumber == null) {
            branchNumber = new StringFilter();
        }
        return branchNumber;
    }

    public void setBranchNumber(StringFilter branchNumber) {
        this.branchNumber = branchNumber;
    }

    public BooleanFilter getUseSameCompanyAddress() {
        return useSameCompanyAddress;
    }

    public BooleanFilter useSameCompanyAddress() {
        if (useSameCompanyAddress == null) {
            useSameCompanyAddress = new BooleanFilter();
        }
        return useSameCompanyAddress;
    }

    public void setUseSameCompanyAddress(BooleanFilter useSameCompanyAddress) {
        this.useSameCompanyAddress = useSameCompanyAddress;
    }

    public StringFilter getBranchPostalCode() {
        return branchPostalCode;
    }

    public StringFilter branchPostalCode() {
        if (branchPostalCode == null) {
            branchPostalCode = new StringFilter();
        }
        return branchPostalCode;
    }

    public void setBranchPostalCode(StringFilter branchPostalCode) {
        this.branchPostalCode = branchPostalCode;
    }

    public StringFilter getBranchAddress() {
        return branchAddress;
    }

    public StringFilter branchAddress() {
        if (branchAddress == null) {
            branchAddress = new StringFilter();
        }
        return branchAddress;
    }

    public void setBranchAddress(StringFilter branchAddress) {
        this.branchAddress = branchAddress;
    }

    public StringFilter getBranchAddressComplement() {
        return branchAddressComplement;
    }

    public StringFilter branchAddressComplement() {
        if (branchAddressComplement == null) {
            branchAddressComplement = new StringFilter();
        }
        return branchAddressComplement;
    }

    public void setBranchAddressComplement(StringFilter branchAddressComplement) {
        this.branchAddressComplement = branchAddressComplement;
    }

    public IntegerFilter getBranchAddressNumber() {
        return branchAddressNumber;
    }

    public IntegerFilter branchAddressNumber() {
        if (branchAddressNumber == null) {
            branchAddressNumber = new IntegerFilter();
        }
        return branchAddressNumber;
    }

    public void setBranchAddressNumber(IntegerFilter branchAddressNumber) {
        this.branchAddressNumber = branchAddressNumber;
    }

    public StringFilter getBranchAddressNeighborhood() {
        return branchAddressNeighborhood;
    }

    public StringFilter branchAddressNeighborhood() {
        if (branchAddressNeighborhood == null) {
            branchAddressNeighborhood = new StringFilter();
        }
        return branchAddressNeighborhood;
    }

    public void setBranchAddressNeighborhood(StringFilter branchAddressNeighborhood) {
        this.branchAddressNeighborhood = branchAddressNeighborhood;
    }

    public StringFilter getBranchTelephone() {
        return branchTelephone;
    }

    public StringFilter branchTelephone() {
        if (branchTelephone == null) {
            branchTelephone = new StringFilter();
        }
        return branchTelephone;
    }

    public void setBranchTelephone(StringFilter branchTelephone) {
        this.branchTelephone = branchTelephone;
    }

    public StringFilter getBranchEmail() {
        return branchEmail;
    }

    public StringFilter branchEmail() {
        if (branchEmail == null) {
            branchEmail = new StringFilter();
        }
        return branchEmail;
    }

    public void setBranchEmail(StringFilter branchEmail) {
        this.branchEmail = branchEmail;
    }

    public StringFilter getResponsibleContact() {
        return responsibleContact;
    }

    public StringFilter responsibleContact() {
        if (responsibleContact == null) {
            responsibleContact = new StringFilter();
        }
        return responsibleContact;
    }

    public void setResponsibleContact(StringFilter responsibleContact) {
        this.responsibleContact = responsibleContact;
    }

    public LongFilter getInsurancesId() {
        return insurancesId;
    }

    public LongFilter insurancesId() {
        if (insurancesId == null) {
            insurancesId = new LongFilter();
        }
        return insurancesId;
    }

    public void setInsurancesId(LongFilter insurancesId) {
        this.insurancesId = insurancesId;
    }

    public LongFilter getPositionsId() {
        return positionsId;
    }

    public LongFilter positionsId() {
        if (positionsId == null) {
            positionsId = new LongFilter();
        }
        return positionsId;
    }

    public void setPositionsId(LongFilter positionsId) {
        this.positionsId = positionsId;
    }

    public LongFilter getCostCenterId() {
        return costCenterId;
    }

    public LongFilter costCenterId() {
        if (costCenterId == null) {
            costCenterId = new LongFilter();
        }
        return costCenterId;
    }

    public void setCostCenterId(LongFilter costCenterId) {
        this.costCenterId = costCenterId;
    }

    public LongFilter getAdministrativeFeesRangesId() {
        return administrativeFeesRangesId;
    }

    public LongFilter administrativeFeesRangesId() {
        if (administrativeFeesRangesId == null) {
            administrativeFeesRangesId = new LongFilter();
        }
        return administrativeFeesRangesId;
    }

    public void setAdministrativeFeesRangesId(LongFilter administrativeFeesRangesId) {
        this.administrativeFeesRangesId = administrativeFeesRangesId;
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

    public LongFilter getFeesId() {
        return feesId;
    }

    public LongFilter feesId() {
        if (feesId == null) {
            feesId = new LongFilter();
        }
        return feesId;
    }

    public void setFeesId(LongFilter feesId) {
        this.feesId = feesId;
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

    public LongFilter getParkingId() {
        return parkingId;
    }

    public LongFilter parkingId() {
        if (parkingId == null) {
            parkingId = new LongFilter();
        }
        return parkingId;
    }

    public void setParkingId(LongFilter parkingId) {
        this.parkingId = parkingId;
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

    public LongFilter getStateProvincesId() {
        return stateProvincesId;
    }

    public LongFilter stateProvincesId() {
        if (stateProvincesId == null) {
            stateProvincesId = new LongFilter();
        }
        return stateProvincesId;
    }

    public void setStateProvincesId(LongFilter stateProvincesId) {
        this.stateProvincesId = stateProvincesId;
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

    public LongFilter getCompaniesId() {
        return companiesId;
    }

    public LongFilter companiesId() {
        if (companiesId == null) {
            companiesId = new LongFilter();
        }
        return companiesId;
    }

    public void setCompaniesId(LongFilter companiesId) {
        this.companiesId = companiesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AffiliatesCriteria that = (AffiliatesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(branchName, that.branchName) &&
            Objects.equals(branchNumber, that.branchNumber) &&
            Objects.equals(useSameCompanyAddress, that.useSameCompanyAddress) &&
            Objects.equals(branchPostalCode, that.branchPostalCode) &&
            Objects.equals(branchAddress, that.branchAddress) &&
            Objects.equals(branchAddressComplement, that.branchAddressComplement) &&
            Objects.equals(branchAddressNumber, that.branchAddressNumber) &&
            Objects.equals(branchAddressNeighborhood, that.branchAddressNeighborhood) &&
            Objects.equals(branchTelephone, that.branchTelephone) &&
            Objects.equals(branchEmail, that.branchEmail) &&
            Objects.equals(responsibleContact, that.responsibleContact) &&
            Objects.equals(insurancesId, that.insurancesId) &&
            Objects.equals(positionsId, that.positionsId) &&
            Objects.equals(costCenterId, that.costCenterId) &&
            Objects.equals(administrativeFeesRangesId, that.administrativeFeesRangesId) &&
            Objects.equals(customersGroupsId, that.customersGroupsId) &&
            Objects.equals(feesId, that.feesId) &&
            Objects.equals(customersId, that.customersId) &&
            Objects.equals(statusAttachmentsId, that.statusAttachmentsId) &&
            Objects.equals(statusId, that.statusId) &&
            Objects.equals(parkingId, that.parkingId) &&
            Objects.equals(suppliersId, that.suppliersId) &&
            Objects.equals(employeesId, that.employeesId) &&
            Objects.equals(vehicleControlsId, that.vehicleControlsId) &&
            Objects.equals(housingId, that.housingId) &&
            Objects.equals(stateProvincesId, that.stateProvincesId) &&
            Objects.equals(citiesId, that.citiesId) &&
            Objects.equals(companiesId, that.companiesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            branchName,
            branchNumber,
            useSameCompanyAddress,
            branchPostalCode,
            branchAddress,
            branchAddressComplement,
            branchAddressNumber,
            branchAddressNeighborhood,
            branchTelephone,
            branchEmail,
            responsibleContact,
            insurancesId,
            positionsId,
            costCenterId,
            administrativeFeesRangesId,
            customersGroupsId,
            feesId,
            customersId,
            statusAttachmentsId,
            statusId,
            parkingId,
            suppliersId,
            employeesId,
            vehicleControlsId,
            housingId,
            stateProvincesId,
            citiesId,
            companiesId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AffiliatesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (branchName != null ? "branchName=" + branchName + ", " : "") +
            (branchNumber != null ? "branchNumber=" + branchNumber + ", " : "") +
            (useSameCompanyAddress != null ? "useSameCompanyAddress=" + useSameCompanyAddress + ", " : "") +
            (branchPostalCode != null ? "branchPostalCode=" + branchPostalCode + ", " : "") +
            (branchAddress != null ? "branchAddress=" + branchAddress + ", " : "") +
            (branchAddressComplement != null ? "branchAddressComplement=" + branchAddressComplement + ", " : "") +
            (branchAddressNumber != null ? "branchAddressNumber=" + branchAddressNumber + ", " : "") +
            (branchAddressNeighborhood != null ? "branchAddressNeighborhood=" + branchAddressNeighborhood + ", " : "") +
            (branchTelephone != null ? "branchTelephone=" + branchTelephone + ", " : "") +
            (branchEmail != null ? "branchEmail=" + branchEmail + ", " : "") +
            (responsibleContact != null ? "responsibleContact=" + responsibleContact + ", " : "") +
            (insurancesId != null ? "insurancesId=" + insurancesId + ", " : "") +
            (positionsId != null ? "positionsId=" + positionsId + ", " : "") +
            (costCenterId != null ? "costCenterId=" + costCenterId + ", " : "") +
            (administrativeFeesRangesId != null ? "administrativeFeesRangesId=" + administrativeFeesRangesId + ", " : "") +
            (customersGroupsId != null ? "customersGroupsId=" + customersGroupsId + ", " : "") +
            (feesId != null ? "feesId=" + feesId + ", " : "") +
            (customersId != null ? "customersId=" + customersId + ", " : "") +
            (statusAttachmentsId != null ? "statusAttachmentsId=" + statusAttachmentsId + ", " : "") +
            (statusId != null ? "statusId=" + statusId + ", " : "") +
            (parkingId != null ? "parkingId=" + parkingId + ", " : "") +
            (suppliersId != null ? "suppliersId=" + suppliersId + ", " : "") +
            (employeesId != null ? "employeesId=" + employeesId + ", " : "") +
            (vehicleControlsId != null ? "vehicleControlsId=" + vehicleControlsId + ", " : "") +
            (housingId != null ? "housingId=" + housingId + ", " : "") +
            (stateProvincesId != null ? "stateProvincesId=" + stateProvincesId + ", " : "") +
            (citiesId != null ? "citiesId=" + citiesId + ", " : "") +
            (companiesId != null ? "companiesId=" + companiesId + ", " : "") +
            "}";
    }
}
