package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.SupplierBanksInfo} entity.
 */
@ApiModel(description = "Supplier Banks Info\nThis class are the list of the suppliers banks.\n@author Samuel Souza")
public class SupplierBanksInfoDTO implements Serializable {

    private Long id;

    /**
     * Supplier Bank Code is the number of bank\n@type Integer
     */
    @NotNull
    @ApiModelProperty(value = "Supplier Bank Code is the number of bank\n@type Integer", required = true)
    private Integer supplierBankCode;

    /**
     * Supplier Bank Name is the name of bank\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "Supplier Bank Name is the name of bank\n@type String", required = true)
    private String supplierBankName;

    /**
     * Supplier Bank Branch Code is the number of agency.\n@type String
     */
    @ApiModelProperty(value = "Supplier Bank Branch Code is the number of agency.\n@type String")
    private String supplierBankBranchCode;

    /**
     * Supplier Bank Account Number.\n@type String
     */
    @ApiModelProperty(value = "Supplier Bank Account Number.\n@type String")
    private String supplierBankAccountNumber;

    /**
     * Supplier Bank Name.\n@type String
     */
    @ApiModelProperty(value = "Supplier Bank Name.\n@type String")
    private String supplierBankUserName;

    /**
     * Supplier Bank Pix .\n@type String
     */
    @ApiModelProperty(value = "Supplier Bank Pix .\n@type String")
    private String supplierBankPixKey;

    /**
     * Supplier Bank User Number.\n@type String
     */
    @ApiModelProperty(value = "Supplier Bank User Number.\n@type String")
    private String supplierBankUserNumber;

    private SuppliersDTO suppliers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSupplierBankCode() {
        return supplierBankCode;
    }

    public void setSupplierBankCode(Integer supplierBankCode) {
        this.supplierBankCode = supplierBankCode;
    }

    public String getSupplierBankName() {
        return supplierBankName;
    }

    public void setSupplierBankName(String supplierBankName) {
        this.supplierBankName = supplierBankName;
    }

    public String getSupplierBankBranchCode() {
        return supplierBankBranchCode;
    }

    public void setSupplierBankBranchCode(String supplierBankBranchCode) {
        this.supplierBankBranchCode = supplierBankBranchCode;
    }

    public String getSupplierBankAccountNumber() {
        return supplierBankAccountNumber;
    }

    public void setSupplierBankAccountNumber(String supplierBankAccountNumber) {
        this.supplierBankAccountNumber = supplierBankAccountNumber;
    }

    public String getSupplierBankUserName() {
        return supplierBankUserName;
    }

    public void setSupplierBankUserName(String supplierBankUserName) {
        this.supplierBankUserName = supplierBankUserName;
    }

    public String getSupplierBankPixKey() {
        return supplierBankPixKey;
    }

    public void setSupplierBankPixKey(String supplierBankPixKey) {
        this.supplierBankPixKey = supplierBankPixKey;
    }

    public String getSupplierBankUserNumber() {
        return supplierBankUserNumber;
    }

    public void setSupplierBankUserNumber(String supplierBankUserNumber) {
        this.supplierBankUserNumber = supplierBankUserNumber;
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
        if (!(o instanceof SupplierBanksInfoDTO)) {
            return false;
        }

        SupplierBanksInfoDTO supplierBanksInfoDTO = (SupplierBanksInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, supplierBanksInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupplierBanksInfoDTO{" +
            "id=" + getId() +
            ", supplierBankCode=" + getSupplierBankCode() +
            ", supplierBankName='" + getSupplierBankName() + "'" +
            ", supplierBankBranchCode='" + getSupplierBankBranchCode() + "'" +
            ", supplierBankAccountNumber='" + getSupplierBankAccountNumber() + "'" +
            ", supplierBankUserName='" + getSupplierBankUserName() + "'" +
            ", supplierBankPixKey='" + getSupplierBankPixKey() + "'" +
            ", supplierBankUserNumber='" + getSupplierBankUserNumber() + "'" +
            ", suppliers=" + getSuppliers() +
            "}";
    }
}
