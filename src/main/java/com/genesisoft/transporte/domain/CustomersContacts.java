package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Customers Contacts.\nThis class list all customers.\n@author Samuel Souza
 */
@Entity
@Table(name = "customers_contacts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomersContacts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Contact Name\n@type String
     */
    @NotNull
    @Column(name = "contact_name", nullable = false)
    private String contactName;

    /**
     * Contact Telephone\n@type String
     */
    @NotNull
    @Column(name = "contact_telephone", nullable = false)
    private String contactTelephone;

    /**
     * contact E-mail\n@type String
     */
    @Column(name = "contact_email")
    private String contactEmail;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "customersContacts", "customerAttachments", "vehicleControls", "housings", "affiliates", "cities", "customersGroups" },
        allowSetters = true
    )
    private Customers customers;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomersContacts id(Long id) {
        this.id = id;
        return this;
    }

    public String getContactName() {
        return this.contactName;
    }

    public CustomersContacts contactName(String contactName) {
        this.contactName = contactName;
        return this;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTelephone() {
        return this.contactTelephone;
    }

    public CustomersContacts contactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
        return this;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    public String getContactEmail() {
        return this.contactEmail;
    }

    public CustomersContacts contactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Customers getCustomers() {
        return this.customers;
    }

    public CustomersContacts customers(Customers customers) {
        this.setCustomers(customers);
        return this;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomersContacts)) {
            return false;
        }
        return id != null && id.equals(((CustomersContacts) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomersContacts{" +
            "id=" + getId() +
            ", contactName='" + getContactName() + "'" +
            ", contactTelephone='" + getContactTelephone() + "'" +
            ", contactEmail='" + getContactEmail() + "'" +
            "}";
    }
}
