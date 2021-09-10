package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Supplier Banks Info\nThis class are the list of the suppliers banks.\n@author Samuel Souza
 */
@Entity
@Table(name = "supplier_banks_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SupplierBanksInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Supplier Bank Code is the number of bank\n@type Integer
     */
    @NotNull
    @Column(name = "supplier_bank_code", nullable = false)
    private Integer supplierBankCode;

    /**
     * Supplier Bank Name is the name of bank\n@type String
     */
    @NotNull
    @Column(name = "supplier_bank_name", nullable = false)
    private String supplierBankName;

    /**
     * Supplier Bank Branch Code is the number of agency.\n@type String
     */
    @Column(name = "supplier_bank_branch_code")
    private String supplierBankBranchCode;

    /**
     * Supplier Bank Account Number.\n@type String
     */
    @Column(name = "supplier_bank_account_number")
    private String supplierBankAccountNumber;

    /**
     * Supplier Bank Name.\n@type String
     */
    @Column(name = "supplier_bank_user_name")
    private String supplierBankUserName;

    /**
     * Supplier Bank Pix .\n@type String
     */
    @Column(name = "supplier_bank_pix_key")
    private String supplierBankPixKey;

    /**
     * Supplier Bank User Number.\n@type String
     */
    @Column(name = "supplier_bank_user_number")
    private String supplierBankUserNumber;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "supplierBanksInfos", "supplierContacts", "vehicleControlExpenses", "housings", "affiliates", "cities", "serviceProvided",
        },
        allowSetters = true
    )
    private Suppliers suppliers;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SupplierBanksInfo id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getSupplierBankCode() {
        return this.supplierBankCode;
    }

    public SupplierBanksInfo supplierBankCode(Integer supplierBankCode) {
        this.supplierBankCode = supplierBankCode;
        return this;
    }

    public void setSupplierBankCode(Integer supplierBankCode) {
        this.supplierBankCode = supplierBankCode;
    }

    public String getSupplierBankName() {
        return this.supplierBankName;
    }

    public SupplierBanksInfo supplierBankName(String supplierBankName) {
        this.supplierBankName = supplierBankName;
        return this;
    }

    public void setSupplierBankName(String supplierBankName) {
        this.supplierBankName = supplierBankName;
    }

    public String getSupplierBankBranchCode() {
        return this.supplierBankBranchCode;
    }

    public SupplierBanksInfo supplierBankBranchCode(String supplierBankBranchCode) {
        this.supplierBankBranchCode = supplierBankBranchCode;
        return this;
    }

    public void setSupplierBankBranchCode(String supplierBankBranchCode) {
        this.supplierBankBranchCode = supplierBankBranchCode;
    }

    public String getSupplierBankAccountNumber() {
        return this.supplierBankAccountNumber;
    }

    public SupplierBanksInfo supplierBankAccountNumber(String supplierBankAccountNumber) {
        this.supplierBankAccountNumber = supplierBankAccountNumber;
        return this;
    }

    public void setSupplierBankAccountNumber(String supplierBankAccountNumber) {
        this.supplierBankAccountNumber = supplierBankAccountNumber;
    }

    public String getSupplierBankUserName() {
        return this.supplierBankUserName;
    }

    public SupplierBanksInfo supplierBankUserName(String supplierBankUserName) {
        this.supplierBankUserName = supplierBankUserName;
        return this;
    }

    public void setSupplierBankUserName(String supplierBankUserName) {
        this.supplierBankUserName = supplierBankUserName;
    }

    public String getSupplierBankPixKey() {
        return this.supplierBankPixKey;
    }

    public SupplierBanksInfo supplierBankPixKey(String supplierBankPixKey) {
        this.supplierBankPixKey = supplierBankPixKey;
        return this;
    }

    public void setSupplierBankPixKey(String supplierBankPixKey) {
        this.supplierBankPixKey = supplierBankPixKey;
    }

    public String getSupplierBankUserNumber() {
        return this.supplierBankUserNumber;
    }

    public SupplierBanksInfo supplierBankUserNumber(String supplierBankUserNumber) {
        this.supplierBankUserNumber = supplierBankUserNumber;
        return this;
    }

    public void setSupplierBankUserNumber(String supplierBankUserNumber) {
        this.supplierBankUserNumber = supplierBankUserNumber;
    }

    public Suppliers getSuppliers() {
        return this.suppliers;
    }

    public SupplierBanksInfo suppliers(Suppliers suppliers) {
        this.setSuppliers(suppliers);
        return this;
    }

    public void setSuppliers(Suppliers suppliers) {
        this.suppliers = suppliers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SupplierBanksInfo)) {
            return false;
        }
        return id != null && id.equals(((SupplierBanksInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupplierBanksInfo{" +
            "id=" + getId() +
            ", supplierBankCode=" + getSupplierBankCode() +
            ", supplierBankName='" + getSupplierBankName() + "'" +
            ", supplierBankBranchCode='" + getSupplierBankBranchCode() + "'" +
            ", supplierBankAccountNumber='" + getSupplierBankAccountNumber() + "'" +
            ", supplierBankUserName='" + getSupplierBankUserName() + "'" +
            ", supplierBankPixKey='" + getSupplierBankPixKey() + "'" +
            ", supplierBankUserNumber='" + getSupplierBankUserNumber() + "'" +
            "}";
    }
}
