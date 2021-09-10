package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.SupplierContacts} entity.
 */
@ApiModel(description = "Supplier Contacts\nThis class are the list of the suppliers contacts.\n@author Samuel Souza")
public class SupplierContactsDTO implements Serializable {

    private Long id;

    /**
     * Supplier Contact Name.\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "Supplier Contact Name.\n@type String", required = true)
    private String supplierContactName;

    /**
     * Supplier Contact Phone.\n@type String
     */
    @ApiModelProperty(value = "Supplier Contact Phone.\n@type String")
    private String supplierContactPhone;

    /**
     * Supplier Contact E-mail.\n@type String
     */
    @ApiModelProperty(value = "Supplier Contact E-mail.\n@type String")
    private String supplierContactEmail;

    private SuppliersDTO suppliers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplierContactName() {
        return supplierContactName;
    }

    public void setSupplierContactName(String supplierContactName) {
        this.supplierContactName = supplierContactName;
    }

    public String getSupplierContactPhone() {
        return supplierContactPhone;
    }

    public void setSupplierContactPhone(String supplierContactPhone) {
        this.supplierContactPhone = supplierContactPhone;
    }

    public String getSupplierContactEmail() {
        return supplierContactEmail;
    }

    public void setSupplierContactEmail(String supplierContactEmail) {
        this.supplierContactEmail = supplierContactEmail;
    }

    public SuppliersDTO getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(SuppliersDTO suppliers) {
        this.suppliers = suppliers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SupplierContactsDTO)) {
            return false;
        }

        SupplierContactsDTO supplierContactsDTO = (SupplierContactsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, supplierContactsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupplierContactsDTO{" +
            "id=" + getId() +
            ", supplierContactName='" + getSupplierContactName() + "'" +
            ", supplierContactPhone='" + getSupplierContactPhone() + "'" +
            ", supplierContactEmail='" + getSupplierContactEmail() + "'" +
            ", suppliers=" + getSuppliers() +
            "}";
    }
}
