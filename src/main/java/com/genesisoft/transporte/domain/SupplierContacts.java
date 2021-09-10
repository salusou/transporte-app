package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Supplier Contacts\nThis class are the list of the suppliers contacts.\n@author Samuel Souza
 */
@Entity
@Table(name = "supplier_contacts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SupplierContacts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Supplier Contact Name.\n@type String
     */
    @NotNull
    @Column(name = "supplier_contact_name", nullable = false)
    private String supplierContactName;

    /**
     * Supplier Contact Phone.\n@type String
     */
    @Column(name = "supplier_contact_phone")
    private String supplierContactPhone;

    /**
     * Supplier Contact E-mail.\n@type String
     */
    @Column(name = "supplier_contact_email")
    private String supplierContactEmail;

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

    public SupplierContacts id(Long id) {
        this.id = id;
        return this;
    }

    public String getSupplierContactName() {
        return this.supplierContactName;
    }

    public SupplierContacts supplierContactName(String supplierContactName) {
        this.supplierContactName = supplierContactName;
        return this;
    }

    public void setSupplierContactName(String supplierContactName) {
        this.supplierContactName = supplierContactName;
    }

    public String getSupplierContactPhone() {
        return this.supplierContactPhone;
    }

    public SupplierContacts supplierContactPhone(String supplierContactPhone) {
        this.supplierContactPhone = supplierContactPhone;
        return this;
    }

    public void setSupplierContactPhone(String supplierContactPhone) {
        this.supplierContactPhone = supplierContactPhone;
    }

    public String getSupplierContactEmail() {
        return this.supplierContactEmail;
    }

    public SupplierContacts supplierContactEmail(String supplierContactEmail) {
        this.supplierContactEmail = supplierContactEmail;
        return this;
    }

    public void setSupplierContactEmail(String supplierContactEmail) {
        this.supplierContactEmail = supplierContactEmail;
    }

    public Suppliers getSuppliers() {
        return this.suppliers;
    }

    public SupplierContacts suppliers(Suppliers suppliers) {
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
        if (!(o instanceof SupplierContacts)) {
            return false;
        }
        return id != null && id.equals(((SupplierContacts) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupplierContacts{" +
            "id=" + getId() +
            ", supplierContactName='" + getSupplierContactName() + "'" +
            ", supplierContactPhone='" + getSupplierContactPhone() + "'" +
            ", supplierContactEmail='" + getSupplierContactEmail() + "'" +
            "}";
    }
}
