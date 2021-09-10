package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.Suppliers} entity.
 */
@ApiModel(description = "List of suppliers\nThis class is the list of suppliers.\n@author Samuel Souza")
public class SuppliersDTO implements Serializable {

    private Long id;

    /**
     * Supplier Name\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "Supplier Name\n@type String", required = true)
    private String supplierName;

    /**
     * Customer Number is like a CNPJ, EIN, CPF, IDENTIFIED\n@type String
     */
    @ApiModelProperty(value = "Customer Number is like a CNPJ, EIN, CPF, IDENTIFIED\n@type String")
    private String supplierNumber;

    /**
     * Postal code of address the matrix company.\n@type String
     */
    @Size(max = 9)
    @ApiModelProperty(value = "Postal code of address the matrix company.\n@type String")
    private String supplierPostalCode;

    /**
     * Branch Address.\n@type String
     */
    @ApiModelProperty(value = "Branch Address.\n@type String")
    private String supplierAddress;

    /**
     * Parking Address Complement.\n@type String
     */
    @ApiModelProperty(value = "Parking Address Complement.\n@type String")
    private String supplierAddressComplement;

    /**
     * Number of address.\n@type Integer
     */
    @ApiModelProperty(value = "Number of address.\n@type Integer")
    private Integer supplierAddressNumber;

    /**
     * Neighborhood, District.\n@type String
     */
    @ApiModelProperty(value = "Neighborhood, District.\n@type String")
    private String supplierAddressNeighborhood;

    /**
     * Parking Contact Telephone\n@type String
     */
    @ApiModelProperty(value = "Parking Contact Telephone\n@type String")
    private String supplierTelephone;

    /**
     * Parking Contact Email\n@type String
     */
    @ApiModelProperty(value = "Parking Contact Email\n@type String")
    private String supplierEmail;

    /**
     * Parking Contact Name\n@type String
     */
    @ApiModelProperty(value = "Parking Contact Name\n@type String")
    private String supplierContactName;

    private AffiliatesDTO affiliates;

    private CitiesDTO cities;

    private ServiceProvidedDTO serviceProvided;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public String getSupplierPostalCode() {
        return supplierPostalCode;
    }

    public void setSupplierPostalCode(String supplierPostalCode) {
        this.supplierPostalCode = supplierPostalCode;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierAddressComplement() {
        return supplierAddressComplement;
    }

    public void setSupplierAddressComplement(String supplierAddressComplement) {
        this.supplierAddressComplement = supplierAddressComplement;
    }

    public Integer getSupplierAddressNumber() {
        return supplierAddressNumber;
    }

    public void setSupplierAddressNumber(Integer supplierAddressNumber) {
        this.supplierAddressNumber = supplierAddressNumber;
    }

    public String getSupplierAddressNeighborhood() {
        return supplierAddressNeighborhood;
    }

    public void setSupplierAddressNeighborhood(String supplierAddressNeighborhood) {
        this.supplierAddressNeighborhood = supplierAddressNeighborhood;
    }

    public String getSupplierTelephone() {
        return supplierTelephone;
    }

    public void setSupplierTelephone(String supplierTelephone) {
        this.supplierTelephone = supplierTelephone;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public String getSupplierContactName() {
        return supplierContactName;
    }

    public void setSupplierContactName(String supplierContactName) {
        this.supplierContactName = supplierContactName;
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

    public ServiceProvidedDTO getServiceProvided() {
        return serviceProvided;
    }

    public void setServiceProvided(ServiceProvidedDTO serviceProvided) {
        this.serviceProvided = serviceProvided;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SuppliersDTO)) {
            return false;
        }

        SuppliersDTO suppliersDTO = (SuppliersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, suppliersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SuppliersDTO{" +
            "id=" + getId() +
            ", supplierName='" + getSupplierName() + "'" +
            ", supplierNumber='" + getSupplierNumber() + "'" +
            ", supplierPostalCode='" + getSupplierPostalCode() + "'" +
            ", supplierAddress='" + getSupplierAddress() + "'" +
            ", supplierAddressComplement='" + getSupplierAddressComplement() + "'" +
            ", supplierAddressNumber=" + getSupplierAddressNumber() +
            ", supplierAddressNeighborhood='" + getSupplierAddressNeighborhood() + "'" +
            ", supplierTelephone='" + getSupplierTelephone() + "'" +
            ", supplierEmail='" + getSupplierEmail() + "'" +
            ", supplierContactName='" + getSupplierContactName() + "'" +
            ", affiliates=" + getAffiliates() +
            ", cities=" + getCities() +
            ", serviceProvided=" + getServiceProvided() +
            "}";
    }
}
