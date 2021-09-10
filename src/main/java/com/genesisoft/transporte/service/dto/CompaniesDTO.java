package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.Companies} entity.
 */
@ApiModel(description = "List of Company Hire this software.\n@author Samuel Souza")
public class CompaniesDTO implements Serializable {

    private Long id;

    /**
     * Matrix Companies Name\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "Matrix Companies Name\n@type String", required = true)
    private String companyName;

    /**
     * TradName\n@type String
     */
    @ApiModelProperty(value = "TradName\n@type String")
    private String tradeName;

    /**
     * Company Number is like a CNPJ, EIN\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "Company Number is like a CNPJ, EIN\n@type String", required = true)
    private String companyNumber;

    /**
     * Postal code of address the matrix company.\n@type String
     */
    @Size(max = 9)
    @ApiModelProperty(value = "Postal code of address the matrix company.\n@type String")
    private String postalCode;

    /**
     * Company Address\n@type String
     */
    @ApiModelProperty(value = "Company Address\n@type String")
    private String companyAddress;

    /**
     * Company Address Complement.\n@type String
     */
    @ApiModelProperty(value = "Company Address Complement.\n@type String")
    private String companyAddressComplement;

    /**
     * Number of address.\n@type Integer
     */
    @ApiModelProperty(value = "Number of address.\n@type Integer")
    private Integer companyAddressNumber;

    /**
     * Neighborhood, District.\n@type String
     */
    @ApiModelProperty(value = "Neighborhood, District.\n@type String")
    private String companyAddressNeighborhood;

    /**
     * Phone of company\n@type String
     */
    @ApiModelProperty(value = "Phone of company\n@type String")
    private String companyTelephone;

    /**
     * E-mail of the company\n@type String
     */
    @ApiModelProperty(value = "E-mail of the company\n@type String")
    private String companyEmail;

    /**
     * Responsible Contact to call or send e-mail\n@type String
     */
    @ApiModelProperty(value = "Responsible Contact to call or send e-mail\n@type String")
    private String responsibleContact;

    private CitiesDTO cities;

    private StateProvincesDTO stateProvinces;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyAddressComplement() {
        return companyAddressComplement;
    }

    public void setCompanyAddressComplement(String companyAddressComplement) {
        this.companyAddressComplement = companyAddressComplement;
    }

    public Integer getCompanyAddressNumber() {
        return companyAddressNumber;
    }

    public void setCompanyAddressNumber(Integer companyAddressNumber) {
        this.companyAddressNumber = companyAddressNumber;
    }

    public String getCompanyAddressNeighborhood() {
        return companyAddressNeighborhood;
    }

    public void setCompanyAddressNeighborhood(String companyAddressNeighborhood) {
        this.companyAddressNeighborhood = companyAddressNeighborhood;
    }

    public String getCompanyTelephone() {
        return companyTelephone;
    }

    public void setCompanyTelephone(String companyTelephone) {
        this.companyTelephone = companyTelephone;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getResponsibleContact() {
        return responsibleContact;
    }

    public void setResponsibleContact(String responsibleContact) {
        this.responsibleContact = responsibleContact;
    }

    public CitiesDTO getCities() {
        return cities;
    }

    public void setCities(CitiesDTO cities) {
        this.cities = cities;
    }

    public StateProvincesDTO getStateProvinces() {
        return stateProvinces;
    }

    public void setStateProvinces(StateProvincesDTO stateProvinces) {
        this.stateProvinces = stateProvinces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompaniesDTO)) {
            return false;
        }

        CompaniesDTO companiesDTO = (CompaniesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, companiesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompaniesDTO{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", tradeName='" + getTradeName() + "'" +
            ", companyNumber='" + getCompanyNumber() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", companyAddress='" + getCompanyAddress() + "'" +
            ", companyAddressComplement='" + getCompanyAddressComplement() + "'" +
            ", companyAddressNumber=" + getCompanyAddressNumber() +
            ", companyAddressNeighborhood='" + getCompanyAddressNeighborhood() + "'" +
            ", companyTelephone='" + getCompanyTelephone() + "'" +
            ", companyEmail='" + getCompanyEmail() + "'" +
            ", responsibleContact='" + getResponsibleContact() + "'" +
            ", cities=" + getCities() +
            ", stateProvinces=" + getStateProvinces() +
            "}";
    }
}
