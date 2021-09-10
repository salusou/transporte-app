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
 * Criteria class for the {@link com.genesisoft.transporte.domain.Employees} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.EmployeesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter active;

    private StringFilter employeeFullName;

    private StringFilter employeeEmail;

    private StringFilter employeeIdentificationNumber;

    private StringFilter employeeNumber;

    private StringFilter employeePostalCode;

    private StringFilter employeeAddress;

    private StringFilter employeeAddressComplement;

    private IntegerFilter employeeAddressNumber;

    private StringFilter employeeAddressNeighborhood;

    private LocalDateFilter employeeBirthday;

    private LongFilter employeeAttachmentsId;

    private LongFilter employeeComponentsDataId;

    private LongFilter vehicleControlsId;

    private LongFilter vehicleControlHistoryId;

    private LongFilter housingId;

    private LongFilter companiesId;

    private LongFilter affiliatesId;

    private LongFilter citiesId;

    private LongFilter positionsId;

    public EmployeesCriteria() {}

    public EmployeesCriteria(EmployeesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.employeeFullName = other.employeeFullName == null ? null : other.employeeFullName.copy();
        this.employeeEmail = other.employeeEmail == null ? null : other.employeeEmail.copy();
        this.employeeIdentificationNumber = other.employeeIdentificationNumber == null ? null : other.employeeIdentificationNumber.copy();
        this.employeeNumber = other.employeeNumber == null ? null : other.employeeNumber.copy();
        this.employeePostalCode = other.employeePostalCode == null ? null : other.employeePostalCode.copy();
        this.employeeAddress = other.employeeAddress == null ? null : other.employeeAddress.copy();
        this.employeeAddressComplement = other.employeeAddressComplement == null ? null : other.employeeAddressComplement.copy();
        this.employeeAddressNumber = other.employeeAddressNumber == null ? null : other.employeeAddressNumber.copy();
        this.employeeAddressNeighborhood = other.employeeAddressNeighborhood == null ? null : other.employeeAddressNeighborhood.copy();
        this.employeeBirthday = other.employeeBirthday == null ? null : other.employeeBirthday.copy();
        this.employeeAttachmentsId = other.employeeAttachmentsId == null ? null : other.employeeAttachmentsId.copy();
        this.employeeComponentsDataId = other.employeeComponentsDataId == null ? null : other.employeeComponentsDataId.copy();
        this.vehicleControlsId = other.vehicleControlsId == null ? null : other.vehicleControlsId.copy();
        this.vehicleControlHistoryId = other.vehicleControlHistoryId == null ? null : other.vehicleControlHistoryId.copy();
        this.housingId = other.housingId == null ? null : other.housingId.copy();
        this.companiesId = other.companiesId == null ? null : other.companiesId.copy();
        this.affiliatesId = other.affiliatesId == null ? null : other.affiliatesId.copy();
        this.citiesId = other.citiesId == null ? null : other.citiesId.copy();
        this.positionsId = other.positionsId == null ? null : other.positionsId.copy();
    }

    @Override
    public EmployeesCriteria copy() {
        return new EmployeesCriteria(this);
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

    public StringFilter getEmployeeFullName() {
        return employeeFullName;
    }

    public StringFilter employeeFullName() {
        if (employeeFullName == null) {
            employeeFullName = new StringFilter();
        }
        return employeeFullName;
    }

    public void setEmployeeFullName(StringFilter employeeFullName) {
        this.employeeFullName = employeeFullName;
    }

    public StringFilter getEmployeeEmail() {
        return employeeEmail;
    }

    public StringFilter employeeEmail() {
        if (employeeEmail == null) {
            employeeEmail = new StringFilter();
        }
        return employeeEmail;
    }

    public void setEmployeeEmail(StringFilter employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public StringFilter getEmployeeIdentificationNumber() {
        return employeeIdentificationNumber;
    }

    public StringFilter employeeIdentificationNumber() {
        if (employeeIdentificationNumber == null) {
            employeeIdentificationNumber = new StringFilter();
        }
        return employeeIdentificationNumber;
    }

    public void setEmployeeIdentificationNumber(StringFilter employeeIdentificationNumber) {
        this.employeeIdentificationNumber = employeeIdentificationNumber;
    }

    public StringFilter getEmployeeNumber() {
        return employeeNumber;
    }

    public StringFilter employeeNumber() {
        if (employeeNumber == null) {
            employeeNumber = new StringFilter();
        }
        return employeeNumber;
    }

    public void setEmployeeNumber(StringFilter employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public StringFilter getEmployeePostalCode() {
        return employeePostalCode;
    }

    public StringFilter employeePostalCode() {
        if (employeePostalCode == null) {
            employeePostalCode = new StringFilter();
        }
        return employeePostalCode;
    }

    public void setEmployeePostalCode(StringFilter employeePostalCode) {
        this.employeePostalCode = employeePostalCode;
    }

    public StringFilter getEmployeeAddress() {
        return employeeAddress;
    }

    public StringFilter employeeAddress() {
        if (employeeAddress == null) {
            employeeAddress = new StringFilter();
        }
        return employeeAddress;
    }

    public void setEmployeeAddress(StringFilter employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public StringFilter getEmployeeAddressComplement() {
        return employeeAddressComplement;
    }

    public StringFilter employeeAddressComplement() {
        if (employeeAddressComplement == null) {
            employeeAddressComplement = new StringFilter();
        }
        return employeeAddressComplement;
    }

    public void setEmployeeAddressComplement(StringFilter employeeAddressComplement) {
        this.employeeAddressComplement = employeeAddressComplement;
    }

    public IntegerFilter getEmployeeAddressNumber() {
        return employeeAddressNumber;
    }

    public IntegerFilter employeeAddressNumber() {
        if (employeeAddressNumber == null) {
            employeeAddressNumber = new IntegerFilter();
        }
        return employeeAddressNumber;
    }

    public void setEmployeeAddressNumber(IntegerFilter employeeAddressNumber) {
        this.employeeAddressNumber = employeeAddressNumber;
    }

    public StringFilter getEmployeeAddressNeighborhood() {
        return employeeAddressNeighborhood;
    }

    public StringFilter employeeAddressNeighborhood() {
        if (employeeAddressNeighborhood == null) {
            employeeAddressNeighborhood = new StringFilter();
        }
        return employeeAddressNeighborhood;
    }

    public void setEmployeeAddressNeighborhood(StringFilter employeeAddressNeighborhood) {
        this.employeeAddressNeighborhood = employeeAddressNeighborhood;
    }

    public LocalDateFilter getEmployeeBirthday() {
        return employeeBirthday;
    }

    public LocalDateFilter employeeBirthday() {
        if (employeeBirthday == null) {
            employeeBirthday = new LocalDateFilter();
        }
        return employeeBirthday;
    }

    public void setEmployeeBirthday(LocalDateFilter employeeBirthday) {
        this.employeeBirthday = employeeBirthday;
    }

    public LongFilter getEmployeeAttachmentsId() {
        return employeeAttachmentsId;
    }

    public LongFilter employeeAttachmentsId() {
        if (employeeAttachmentsId == null) {
            employeeAttachmentsId = new LongFilter();
        }
        return employeeAttachmentsId;
    }

    public void setEmployeeAttachmentsId(LongFilter employeeAttachmentsId) {
        this.employeeAttachmentsId = employeeAttachmentsId;
    }

    public LongFilter getEmployeeComponentsDataId() {
        return employeeComponentsDataId;
    }

    public LongFilter employeeComponentsDataId() {
        if (employeeComponentsDataId == null) {
            employeeComponentsDataId = new LongFilter();
        }
        return employeeComponentsDataId;
    }

    public void setEmployeeComponentsDataId(LongFilter employeeComponentsDataId) {
        this.employeeComponentsDataId = employeeComponentsDataId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeesCriteria that = (EmployeesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(active, that.active) &&
            Objects.equals(employeeFullName, that.employeeFullName) &&
            Objects.equals(employeeEmail, that.employeeEmail) &&
            Objects.equals(employeeIdentificationNumber, that.employeeIdentificationNumber) &&
            Objects.equals(employeeNumber, that.employeeNumber) &&
            Objects.equals(employeePostalCode, that.employeePostalCode) &&
            Objects.equals(employeeAddress, that.employeeAddress) &&
            Objects.equals(employeeAddressComplement, that.employeeAddressComplement) &&
            Objects.equals(employeeAddressNumber, that.employeeAddressNumber) &&
            Objects.equals(employeeAddressNeighborhood, that.employeeAddressNeighborhood) &&
            Objects.equals(employeeBirthday, that.employeeBirthday) &&
            Objects.equals(employeeAttachmentsId, that.employeeAttachmentsId) &&
            Objects.equals(employeeComponentsDataId, that.employeeComponentsDataId) &&
            Objects.equals(vehicleControlsId, that.vehicleControlsId) &&
            Objects.equals(vehicleControlHistoryId, that.vehicleControlHistoryId) &&
            Objects.equals(housingId, that.housingId) &&
            Objects.equals(companiesId, that.companiesId) &&
            Objects.equals(affiliatesId, that.affiliatesId) &&
            Objects.equals(citiesId, that.citiesId) &&
            Objects.equals(positionsId, that.positionsId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            active,
            employeeFullName,
            employeeEmail,
            employeeIdentificationNumber,
            employeeNumber,
            employeePostalCode,
            employeeAddress,
            employeeAddressComplement,
            employeeAddressNumber,
            employeeAddressNeighborhood,
            employeeBirthday,
            employeeAttachmentsId,
            employeeComponentsDataId,
            vehicleControlsId,
            vehicleControlHistoryId,
            housingId,
            companiesId,
            affiliatesId,
            citiesId,
            positionsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (active != null ? "active=" + active + ", " : "") +
            (employeeFullName != null ? "employeeFullName=" + employeeFullName + ", " : "") +
            (employeeEmail != null ? "employeeEmail=" + employeeEmail + ", " : "") +
            (employeeIdentificationNumber != null ? "employeeIdentificationNumber=" + employeeIdentificationNumber + ", " : "") +
            (employeeNumber != null ? "employeeNumber=" + employeeNumber + ", " : "") +
            (employeePostalCode != null ? "employeePostalCode=" + employeePostalCode + ", " : "") +
            (employeeAddress != null ? "employeeAddress=" + employeeAddress + ", " : "") +
            (employeeAddressComplement != null ? "employeeAddressComplement=" + employeeAddressComplement + ", " : "") +
            (employeeAddressNumber != null ? "employeeAddressNumber=" + employeeAddressNumber + ", " : "") +
            (employeeAddressNeighborhood != null ? "employeeAddressNeighborhood=" + employeeAddressNeighborhood + ", " : "") +
            (employeeBirthday != null ? "employeeBirthday=" + employeeBirthday + ", " : "") +
            (employeeAttachmentsId != null ? "employeeAttachmentsId=" + employeeAttachmentsId + ", " : "") +
            (employeeComponentsDataId != null ? "employeeComponentsDataId=" + employeeComponentsDataId + ", " : "") +
            (vehicleControlsId != null ? "vehicleControlsId=" + vehicleControlsId + ", " : "") +
            (vehicleControlHistoryId != null ? "vehicleControlHistoryId=" + vehicleControlHistoryId + ", " : "") +
            (housingId != null ? "housingId=" + housingId + ", " : "") +
            (companiesId != null ? "companiesId=" + companiesId + ", " : "") +
            (affiliatesId != null ? "affiliatesId=" + affiliatesId + ", " : "") +
            (citiesId != null ? "citiesId=" + citiesId + ", " : "") +
            (positionsId != null ? "positionsId=" + positionsId + ", " : "") +
            "}";
    }
}
