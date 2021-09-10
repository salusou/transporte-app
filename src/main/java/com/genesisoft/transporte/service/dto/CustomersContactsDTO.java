package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.CustomersContacts} entity.
 */
@ApiModel(description = "Customers Contacts.\nThis class list all customers.\n@author Samuel Souza")
public class CustomersContactsDTO implements Serializable {

    private Long id;

    /**
     * Contact Name\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "Contact Name\n@type String", required = true)
    private String contactName;

    /**
     * Contact Telephone\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "Contact Telephone\n@type String", required = true)
    private String contactTelephone;

    /**
     * contact E-mail\n@type String
     */
    @ApiModelProperty(value = "contact E-mail\n@type String")
    private String contactEmail;

    private CustomersDTO customers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTelephone() {
        return contactTelephone;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public CustomersDTO getCustomers() {
        return customers;
    }

    public void setCustomers(CustomersDTO customers) {
        this.customers = customers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomersContactsDTO)) {
            return false;
        }

        CustomersContactsDTO customersContactsDTO = (CustomersContactsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, customersContactsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomersContactsDTO{" +
            "id=" + getId() +
            ", contactName='" + getContactName() + "'" +
            ", contactTelephone='" + getContactTelephone() + "'" +
            ", contactEmail='" + getContactEmail() + "'" +
            ", customers=" + getCustomers() +
            "}";
    }
}
