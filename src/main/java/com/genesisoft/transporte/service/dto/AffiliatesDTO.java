package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.Affiliates} entity.
 */
@ApiModel(description = "List of Affiliates.\n@author Samuel Souza")
public class AffiliatesDTO implements Serializable {

    private Long id;

    @NotNull
    private String branchName;

    /**
     * Company Number is like a CNPJ, EIN\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "Company Number is like a CNPJ, EIN\n@type String", required = true)
    private String branchNumber;

    /**
     * If true get all information of company.\n@type Boolean
     */
    @ApiModelProperty(value = "If true get all information of company.\n@type Boolean")
    private Boolean useSameCompanyAddress;

    /**
     * Postal code of address the matrix company.\n@type String
     */
    @Size(max = 9)
    @ApiModelProperty(value = "Postal code of address the matrix company.\n@type String")
    private String branchPostalCode;

    /**
     * Branch Address.\n@type String
     */
    @ApiModelProperty(value = "Branch Address.\n@type String")
    private String branchAddress;

    /**
     * Branch Address Complement.\n@type String
     */
    @ApiModelProperty(value = "Branch Address Complement.\n@type String")
    private String branchAddressComplement;

    /**
     * Number of address.\n@type Integer
     */
    @ApiModelProperty(value = "Number of address.\n@type Integer")
    private Integer branchAddressNumber;

    /**
     * Neighborhood, District.\n@type String
     */
    @ApiModelProperty(value = "Neighborhood, District.\n@type String")
    private String branchAddressNeighborhood;

    /**
     * Phone of branch\n@type String
     */
    @ApiModelProperty(value = "Phone of branch\n@type String")
    private String branchTelephone;

    /**
     * E-mail of the branch\n@type String
     */
    @ApiModelProperty(value = "E-mail of the branch\n@type String")
    private String branchEmail;

    /**
     * Responsible Contact to call or send e-mail\n@type String
     */
    @ApiModelProperty(value = "Responsible Contact to call or send e-mail\n@type String")
    private String responsibleContact;

    private StateProvincesDTO stateProvinces;

    private CitiesDTO cities;

    private CompaniesDTO companies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    public Boolean getUseSameCompanyAddress() {
        return useSameCompanyAddress;
    }

    public void setUseSameCompanyAddress(Boolean useSameCompanyAddress) {
        this.useSameCompanyAddress = useSameCompanyAddress;
    }

    public String getBranchPostalCode() {
        return branchPostalCode;
    }

    public void setBranchPostalCode(String branchPostalCode) {
        this.branchPostalCode = branchPostalCode;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getBranchAddressComplement() {
        return branchAddressComplement;
    }

    public void setBranchAddressComplement(String branchAddressComplement) {
        this.branchAddressComplement = branchAddressComplement;
    }

    public Integer getBranchAddressNumber() {
        return branchAddressNumber;
    }

    public void setBranchAddressNumber(Integer branchAddressNumber) {
        this.branchAddressNumber = branchAddressNumber;
    }

    public String getBranchAddressNeighborhood() {
        return branchAddressNeighborhood;
    }

    public void setBranchAddressNeighborhood(String branchAddressNeighborhood) {
        this.branchAddressNeighborhood = branchAddressNeighborhood;
    }

    public String getBranchTelephone() {
        return branchTelephone;
    }

    public void setBranchTelephone(String branchTelephone) {
        this.branchTelephone = branchTelephone;
    }

    public String getBranchEmail() {
        return branchEmail;
    }

    public void setBranchEmail(String branchEmail) {
        this.branchEmail = branchEmail;
    }

    public String getResponsibleContact() {
        return responsibleContact;
    }

    public void setResponsibleContact(String responsibleContact) {
        this.responsibleContact = responsibleContact;
    }

    public StateProvincesDTO getStateProvinces() {
        return stateProvinces;
    }

    public void setStateProvinces(StateProvincesDTO stateProvinces) {
        this.stateProvinces = stateProvinces;
    }

    public CitiesDTO getCities() {
        return cities;
    }

    public void setCities(CitiesDTO cities) {
        this.cities = cities;
    }

    public CompaniesDTO getCompanies() {
        return companies;
    }

    public void setCompanies(CompaniesDTO companies) {
        this.companies = companies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AffiliatesDTO)) {
            return false;
        }

        AffiliatesDTO affiliatesDTO = (AffiliatesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, affiliatesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AffiliatesDTO{" +
            "id=" + getId() +
            ", branchName='" + getBranchName() + "'" +
            ", branchNumber='" + getBranchNumber() + "'" +
            ", useSameCompanyAddress='" + getUseSameCompanyAddress() + "'" +
            ", branchPostalCode='" + getBranchPostalCode() + "'" +
            ", branchAddress='" + getBranchAddress() + "'" +
            ", branchAddressComplement='" + getBranchAddressComplement() + "'" +
            ", branchAddressNumber=" + getBranchAddressNumber() +
            ", branchAddressNeighborhood='" + getBranchAddressNeighborhood() + "'" +
            ", branchTelephone='" + getBranchTelephone() + "'" +
            ", branchEmail='" + getBranchEmail() + "'" +
            ", responsibleContact='" + getResponsibleContact() + "'" +
            ", stateProvinces=" + getStateProvinces() +
            ", cities=" + getCities() +
            ", companies=" + getCompanies() +
            "}";
    }
}
