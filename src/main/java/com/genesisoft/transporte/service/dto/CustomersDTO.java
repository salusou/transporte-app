package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.Customers} entity.
 */
@ApiModel(description = "Customers.\nThis class are the customers group.\n@author Samuel Souza")
public class CustomersDTO implements Serializable {

    private Long id;

    /**
     * CLIENT NAME\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "CLIENT NAME\n@type String", required = true)
    private String customerName;

    /**
     * active customers account.\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "active customers account.\n@type String", required = true)
    private Boolean active;

    /**
     * Customer Number is like a CNPJ, EIN, CPF, IDENTIFIED\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "Customer Number is like a CNPJ, EIN, CPF, IDENTIFIED\n@type String", required = true)
    private String customerNumber;

    /**
     * Postal code of address the matrix company.\n@type String
     */
    @Size(max = 9)
    @ApiModelProperty(value = "Postal code of address the matrix company.\n@type String")
    private String customerPostalCode;

    /**
     * Branch Address.\n@type String
     */
    @ApiModelProperty(value = "Branch Address.\n@type String")
    private String customerAddress;

    /**
     * Branch Address Complement.\n@type String
     */
    @ApiModelProperty(value = "Branch Address Complement.\n@type String")
    private String customerAddressComplement;

    /**
     * Number of address.\n@type Integer
     */
    @ApiModelProperty(value = "Number of address.\n@type Integer")
    private Integer customerAddressNumber;

    /**
     * Neighborhood, District.\n@type String
     */
    @ApiModelProperty(value = "Neighborhood, District.\n@type String")
    private String customerAddressNeighborhood;

    /**
     * Contact Telephone\n@type String
     */
    @ApiModelProperty(value = "Contact Telephone\n@type String")
    private String customerTelephone;

    /**
     * Payment Terms\n@type Integer
     */
    @NotNull
    @ApiModelProperty(value = "Payment Terms\n@type Integer", required = true)
    private Integer paymentTerm;

    private AffiliatesDTO affiliates;

    private CitiesDTO cities;

    private CustomersGroupsDTO customersGroups;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerAddressComplement() {
        return customerAddressComplement;
    }

    public void setCustomerAddressComplement(String customerAddressComplement) {
        this.customerAddressComplement = customerAddressComplement;
    }

    public Integer getCustomerAddressNumber() {
        return customerAddressNumber;
    }

    public void setCustomerAddressNumber(Integer customerAddressNumber) {
        this.customerAddressNumber = customerAddressNumber;
    }

    public String getCustomerAddressNeighborhood() {
        return customerAddressNeighborhood;
    }

    public void setCustomerAddressNeighborhood(String customerAddressNeighborhood) {
        this.customerAddressNeighborhood = customerAddressNeighborhood;
    }

    public String getCustomerTelephone() {
        return customerTelephone;
    }

    public void setCustomerTelephone(String customerTelephone) {
        this.customerTelephone = customerTelephone;
    }

    public Integer getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(Integer paymentTerm) {
        this.paymentTerm = paymentTerm;
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

    public CustomersGroupsDTO getCustomersGroups() {
        return customersGroups;
    }

    public void setCustomersGroups(CustomersGroupsDTO customersGroups) {
        this.customersGroups = customersGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomersDTO)) {
            return false;
        }

        CustomersDTO customersDTO = (CustomersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, customersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomersDTO{" +
            "id=" + getId() +
            ", customerName='" + getCustomerName() + "'" +
            ", active='" + getActive() + "'" +
            ", customerNumber='" + getCustomerNumber() + "'" +
            ", customerPostalCode='" + getCustomerPostalCode() + "'" +
            ", customerAddress='" + getCustomerAddress() + "'" +
            ", customerAddressComplement='" + getCustomerAddressComplement() + "'" +
            ", customerAddressNumber=" + getCustomerAddressNumber() +
            ", customerAddressNeighborhood='" + getCustomerAddressNeighborhood() + "'" +
            ", customerTelephone='" + getCustomerTelephone() + "'" +
            ", paymentTerm=" + getPaymentTerm() +
            ", affiliates=" + getAffiliates() +
            ", cities=" + getCities() +
            ", customersGroups=" + getCustomersGroups() +
            "}";
    }
}
