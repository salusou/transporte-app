package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Service Provided.\nThis class list all services provided to suppliers.\n@author Samuel Souza
 */
@Entity
@Table(name = "service_provided")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceProvided implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "service_name", nullable = false)
    private String serviceName;

    @OneToMany(mappedBy = "serviceProvided")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "supplierBanksInfos", "supplierContacts", "vehicleControlExpenses", "housings", "affiliates", "cities", "serviceProvided",
        },
        allowSetters = true
    )
    private Set<Suppliers> suppliers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceProvided id(Long id) {
        this.id = id;
        return this;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public ServiceProvided serviceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Set<Suppliers> getSuppliers() {
        return this.suppliers;
    }

    public ServiceProvided suppliers(Set<Suppliers> suppliers) {
        this.setSuppliers(suppliers);
        return this;
    }

    public ServiceProvided addSuppliers(Suppliers suppliers) {
        this.suppliers.add(suppliers);
        suppliers.setServiceProvided(this);
        return this;
    }

    public ServiceProvided removeSuppliers(Suppliers suppliers) {
        this.suppliers.remove(suppliers);
        suppliers.setServiceProvided(null);
        return this;
    }

    public void setSuppliers(Set<Suppliers> suppliers) {
        if (this.suppliers != null) {
            this.suppliers.forEach(i -> i.setServiceProvided(null));
        }
        if (suppliers != null) {
            suppliers.forEach(i -> i.setServiceProvided(this));
        }
        this.suppliers = suppliers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceProvided)) {
            return false;
        }
        return id != null && id.equals(((ServiceProvided) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceProvided{" +
            "id=" + getId() +
            ", serviceName='" + getServiceName() + "'" +
            "}";
    }
}
