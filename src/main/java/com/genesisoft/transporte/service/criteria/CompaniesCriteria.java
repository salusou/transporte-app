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
 * Criteria class for the {@link com.genesisoft.transporte.domain.Companies} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.CompaniesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /companies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompaniesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter companyName;

    private StringFilter tradeName;

    private StringFilter companyNumber;

    private StringFilter postalCode;

    private StringFilter companyAddress;

    private StringFilter companyAddressComplement;

    private IntegerFilter companyAddressNumber;

    private StringFilter companyAddressNeighborhood;

    private StringFilter companyTelephone;

    private StringFilter companyEmail;

    private StringFilter responsibleContact;

    private LongFilter affiliatesId;

    private LongFilter employeesId;

    private LongFilter citiesId;

    private LongFilter stateProvincesId;

    public CompaniesCriteria() {}

    public CompaniesCriteria(CompaniesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.companyName = other.companyName == null ? null : other.companyName.copy();
        this.tradeName = other.tradeName == null ? null : other.tradeName.copy();
        this.companyNumber = other.companyNumber == null ? null : other.companyNumber.copy();
        this.postalCode = other.postalCode == null ? null : other.postalCode.copy();
        this.companyAddress = other.companyAddress == null ? null : other.companyAddress.copy();
        this.companyAddressComplement = other.companyAddressComplement == null ? null : other.companyAddressComplement.copy();
        this.companyAddressNumber = other.companyAddressNumber == null ? null : other.companyAddressNumber.copy();
        this.companyAddressNeighborhood = other.companyAddressNeighborhood == null ? null : other.companyAddressNeighborhood.copy();
        this.companyTelephone = other.companyTelephone == null ? null : other.companyTelephone.copy();
        this.companyEmail = other.companyEmail == null ? null : other.companyEmail.copy();
        this.responsibleContact = other.responsibleContact == null ? null : other.responsibleContact.copy();
        this.affiliatesId = other.affiliatesId == null ? null : other.affiliatesId.copy();
        this.employeesId = other.employeesId == null ? null : other.employeesId.copy();
        this.citiesId = other.citiesId == null ? null : other.citiesId.copy();
        this.stateProvincesId = other.stateProvincesId == null ? null : other.stateProvincesId.copy();
    }

    @Override
    public CompaniesCriteria copy() {
        return new CompaniesCriteria(this);
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

    public StringFilter getCompanyName() {
        return companyName;
    }

    public StringFilter companyName() {
        if (companyName == null) {
            companyName = new StringFilter();
        }
        return companyName;
    }

    public void setCompanyName(StringFilter companyName) {
        this.companyName = companyName;
    }

    public StringFilter getTradeName() {
        return tradeName;
    }

    public StringFilter tradeName() {
        if (tradeName == null) {
            tradeName = new StringFilter();
        }
        return tradeName;
    }

    public void setTradeName(StringFilter tradeName) {
        this.tradeName = tradeName;
    }

    public StringFilter getCompanyNumber() {
        return companyNumber;
    }

    public StringFilter companyNumber() {
        if (companyNumber == null) {
            companyNumber = new StringFilter();
        }
        return companyNumber;
    }

    public void setCompanyNumber(StringFilter companyNumber) {
        this.companyNumber = companyNumber;
    }

    public StringFilter getPostalCode() {
        return postalCode;
    }

    public StringFilter postalCode() {
        if (postalCode == null) {
            postalCode = new StringFilter();
        }
        return postalCode;
    }

    public void setPostalCode(StringFilter postalCode) {
        this.postalCode = postalCode;
    }

    public StringFilter getCompanyAddress() {
        return companyAddress;
    }

    public StringFilter companyAddress() {
        if (companyAddress == null) {
            companyAddress = new StringFilter();
        }
        return companyAddress;
    }

    public void setCompanyAddress(StringFilter companyAddress) {
        this.companyAddress = companyAddress;
    }

    public StringFilter getCompanyAddressComplement() {
        return companyAddressComplement;
    }

    public StringFilter companyAddressComplement() {
        if (companyAddressComplement == null) {
            companyAddressComplement = new StringFilter();
        }
        return companyAddressComplement;
    }

    public void setCompanyAddressComplement(StringFilter companyAddressComplement) {
        this.companyAddressComplement = companyAddressComplement;
    }

    public IntegerFilter getCompanyAddressNumber() {
        return companyAddressNumber;
    }

    public IntegerFilter companyAddressNumber() {
        if (companyAddressNumber == null) {
            companyAddressNumber = new IntegerFilter();
        }
        return companyAddressNumber;
    }

    public void setCompanyAddressNumber(IntegerFilter companyAddressNumber) {
        this.companyAddressNumber = companyAddressNumber;
    }

    public StringFilter getCompanyAddressNeighborhood() {
        return companyAddressNeighborhood;
    }

    public StringFilter companyAddressNeighborhood() {
        if (companyAddressNeighborhood == null) {
            companyAddressNeighborhood = new StringFilter();
        }
        return companyAddressNeighborhood;
    }

    public void setCompanyAddressNeighborhood(StringFilter companyAddressNeighborhood) {
        this.companyAddressNeighborhood = companyAddressNeighborhood;
    }

    public StringFilter getCompanyTelephone() {
        return companyTelephone;
    }

    public StringFilter companyTelephone() {
        if (companyTelephone == null) {
            companyTelephone = new StringFilter();
        }
        return companyTelephone;
    }

    public void setCompanyTelephone(StringFilter companyTelephone) {
        this.companyTelephone = companyTelephone;
    }

    public StringFilter getCompanyEmail() {
        return companyEmail;
    }

    public StringFilter companyEmail() {
        if (companyEmail == null) {
            companyEmail = new StringFilter();
        }
        return companyEmail;
    }

    public void setCompanyEmail(StringFilter companyEmail) {
        this.companyEmail = companyEmail;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CompaniesCriteria that = (CompaniesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(companyName, that.companyName) &&
            Objects.equals(tradeName, that.tradeName) &&
            Objects.equals(companyNumber, that.companyNumber) &&
            Objects.equals(postalCode, that.postalCode) &&
            Objects.equals(companyAddress, that.companyAddress) &&
            Objects.equals(companyAddressComplement, that.companyAddressComplement) &&
            Objects.equals(companyAddressNumber, that.companyAddressNumber) &&
            Objects.equals(companyAddressNeighborhood, that.companyAddressNeighborhood) &&
            Objects.equals(companyTelephone, that.companyTelephone) &&
            Objects.equals(companyEmail, that.companyEmail) &&
            Objects.equals(responsibleContact, that.responsibleContact) &&
            Objects.equals(affiliatesId, that.affiliatesId) &&
            Objects.equals(employeesId, that.employeesId) &&
            Objects.equals(citiesId, that.citiesId) &&
            Objects.equals(stateProvincesId, that.stateProvincesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            companyName,
            tradeName,
            companyNumber,
            postalCode,
            companyAddress,
            companyAddressComplement,
            companyAddressNumber,
            companyAddressNeighborhood,
            companyTelephone,
            companyEmail,
            responsibleContact,
            affiliatesId,
            employeesId,
            citiesId,
            stateProvincesId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompaniesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (companyName != null ? "companyName=" + companyName + ", " : "") +
            (tradeName != null ? "tradeName=" + tradeName + ", " : "") +
            (companyNumber != null ? "companyNumber=" + companyNumber + ", " : "") +
            (postalCode != null ? "postalCode=" + postalCode + ", " : "") +
            (companyAddress != null ? "companyAddress=" + companyAddress + ", " : "") +
            (companyAddressComplement != null ? "companyAddressComplement=" + companyAddressComplement + ", " : "") +
            (companyAddressNumber != null ? "companyAddressNumber=" + companyAddressNumber + ", " : "") +
            (companyAddressNeighborhood != null ? "companyAddressNeighborhood=" + companyAddressNeighborhood + ", " : "") +
            (companyTelephone != null ? "companyTelephone=" + companyTelephone + ", " : "") +
            (companyEmail != null ? "companyEmail=" + companyEmail + ", " : "") +
            (responsibleContact != null ? "responsibleContact=" + responsibleContact + ", " : "") +
            (affiliatesId != null ? "affiliatesId=" + affiliatesId + ", " : "") +
            (employeesId != null ? "employeesId=" + employeesId + ", " : "") +
            (citiesId != null ? "citiesId=" + citiesId + ", " : "") +
            (stateProvincesId != null ? "stateProvincesId=" + stateProvincesId + ", " : "") +
            "}";
    }
}
