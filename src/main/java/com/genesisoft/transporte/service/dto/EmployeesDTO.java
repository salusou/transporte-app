package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.Employees} entity.
 */
public class EmployeesDTO implements Serializable {

    private Long id;

    /**
     * Employee Active. Check if employee is active\n@type Boolean
     */
    @NotNull
    @ApiModelProperty(value = "Employee Active. Check if employee is active\n@type Boolean", required = true)
    private Boolean active;

    /**
     * Employee Full Name.\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "Employee Full Name.\n@type String", required = true)
    private String employeeFullName;

    /**
     * Employee E-mail\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "Employee E-mail\n@type String", required = true)
    private String employeeEmail;

    /**
     * Employee Supplier Identification Number. Identidade.\n@type String
     */
    @ApiModelProperty(value = "Employee Supplier Identification Number. Identidade.\n@type String")
    private String employeeIdentificationNumber;

    /**
     * Employee Number. CPF.\n@type String
     */
    @ApiModelProperty(value = "Employee Number. CPF.\n@type String")
    private String employeeNumber;

    /**
     * Postal code employee.\n@type String
     */
    @Size(max = 9)
    @ApiModelProperty(value = "Postal code employee.\n@type String")
    private String employeePostalCode;

    /**
     * Branch Address.\n@type String
     */
    @ApiModelProperty(value = "Branch Address.\n@type String")
    private String employeeAddress;

    /**
     * Employee Address Complement.\n@type String
     */
    @ApiModelProperty(value = "Employee Address Complement.\n@type String")
    private String employeeAddressComplement;

    /**
     * Number of address.\n@type Integer
     */
    @ApiModelProperty(value = "Number of address.\n@type Integer")
    private Integer employeeAddressNumber;

    /**
     * Neighborhood, District.\n@type String
     */
    @ApiModelProperty(value = "Neighborhood, District.\n@type String")
    private String employeeAddressNeighborhood;

    /**
     * Employee Birthday.\n@type String
     */
    @ApiModelProperty(value = "Employee Birthday.\n@type String")
    private LocalDate employeeBirthday;

    private CompaniesDTO companies;

    private AffiliatesDTO affiliates;

    private CitiesDTO cities;

    private PositionsDTO positions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeIdentificationNumber() {
        return employeeIdentificationNumber;
    }

    public void setEmployeeIdentificationNumber(String employeeIdentificationNumber) {
        this.employeeIdentificationNumber = employeeIdentificationNumber;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getEmployeePostalCode() {
        return employeePostalCode;
    }

    public void setEmployeePostalCode(String employeePostalCode) {
        this.employeePostalCode = employeePostalCode;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeeAddressComplement() {
        return employeeAddressComplement;
    }

    public void setEmployeeAddressComplement(String employeeAddressComplement) {
        this.employeeAddressComplement = employeeAddressComplement;
    }

    public Integer getEmployeeAddressNumber() {
        return employeeAddressNumber;
    }

    public void setEmployeeAddressNumber(Integer employeeAddressNumber) {
        this.employeeAddressNumber = employeeAddressNumber;
    }

    public String getEmployeeAddressNeighborhood() {
        return employeeAddressNeighborhood;
    }

    public void setEmployeeAddressNeighborhood(String employeeAddressNeighborhood) {
        this.employeeAddressNeighborhood = employeeAddressNeighborhood;
    }

    public LocalDate getEmployeeBirthday() {
        return employeeBirthday;
    }

    public void setEmployeeBirthday(LocalDate employeeBirthday) {
        this.employeeBirthday = employeeBirthday;
    }

    public CompaniesDTO getCompanies() {
        return companies;
    }

    public void setCompanies(CompaniesDTO companies) {
        this.companies = companies;
    }

    public AffiliatesDTO getAffiliates() {
        return affiliates;
    }

    public void setAffiliates(AffiliatesDTO affiliates) {
        this.affiliates = affiliates;
    }

    public CitiesDTO getCities() {
        return cities;
    }

    public void setCities(CitiesDTO cities) {
        this.cities = cities;
    }

    public PositionsDTO getPositions() {
        return positions;
    }

    public void setPositions(PositionsDTO positions) {
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeesDTO)) {
            return false;
        }

        EmployeesDTO employeesDTO = (EmployeesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, employeesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeesDTO{" +
            "id=" + getId() +
            ", active='" + getActive() + "'" +
            ", employeeFullName='" + getEmployeeFullName() + "'" +
            ", employeeEmail='" + getEmployeeEmail() + "'" +
            ", employeeIdentificationNumber='" + getEmployeeIdentificationNumber() + "'" +
            ", employeeNumber='" + getEmployeeNumber() + "'" +
            ", employeePostalCode='" + getEmployeePostalCode() + "'" +
            ", employeeAddress='" + getEmployeeAddress() + "'" +
            ", employeeAddressComplement='" + getEmployeeAddressComplement() + "'" +
            ", employeeAddressNumber=" + getEmployeeAddressNumber() +
            ", employeeAddressNeighborhood='" + getEmployeeAddressNeighborhood() + "'" +
            ", employeeBirthday='" + getEmployeeBirthday() + "'" +
            ", companies=" + getCompanies() +
            ", affiliates=" + getAffiliates() +
            ", cities=" + getCities() +
            ", positions=" + getPositions() +
            "}";
    }
}
