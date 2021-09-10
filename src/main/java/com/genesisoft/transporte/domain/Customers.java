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
 * Customers.\nThis class are the customers group.\n@author Samuel Souza
 */
@Entity
@Table(name = "customers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * CLIENT NAME\n@type String
     */
    @NotNull
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    /**
     * active customers account.\n@type String
     */
    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    /**
     * Customer Number is like a CNPJ, EIN, CPF, IDENTIFIED\n@type String
     */
    @NotNull
    @Column(name = "customer_number", nullable = false)
    private String customerNumber;

    /**
     * Postal code of address the matrix company.\n@type String
     */
    @Size(max = 9)
    @Column(name = "customer_postal_code", length = 9)
    private String customerPostalCode;

    /**
     * Branch Address.\n@type String
     */
    @Column(name = "customer_address")
    private String customerAddress;

    /**
     * Branch Address Complement.\n@type String
     */
    @Column(name = "customer_address_complement")
    private String customerAddressComplement;

    /**
     * Number of address.\n@type Integer
     */
    @Column(name = "customer_address_number")
    private Integer customerAddressNumber;

    /**
     * Neighborhood, District.\n@type String
     */
    @Column(name = "customer_address_neighborhood")
    private String customerAddressNeighborhood;

    /**
     * Contact Telephone\n@type String
     */
    @Column(name = "customer_telephone")
    private String customerTelephone;

    /**
     * Payment Terms\n@type Integer
     */
    @NotNull
    @Column(name = "payment_term", nullable = false)
    private Integer paymentTerm;

    @OneToMany(mappedBy = "customers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customers" }, allowSetters = true)
    private Set<CustomersContacts> customersContacts = new HashSet<>();

    @OneToMany(mappedBy = "customers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customers", "statusAttachments" }, allowSetters = true)
    private Set<CustomerAttachments> customerAttachments = new HashSet<>();

    @OneToMany(mappedBy = "customers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "vehicleLocationStatuses",
            "vehicleControlHistories",
            "vehicleControlBillings",
            "vehicleControlItems",
            "vehicleControlAttachments",
            "vehicleControlExpenses",
            "affiliates",
            "customers",
            "customersGroups",
            "employees",
            "origin",
            "destination",
            "status",
        },
        allowSetters = true
    )
    private Set<VehicleControls> vehicleControls = new HashSet<>();

    @OneToMany(mappedBy = "customers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "housingVehicleItems", "affiliates", "status", "customers", "employees", "parking", "costCenter", "suppliers", "cities" },
        allowSetters = true
    )
    private Set<Housing> housings = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "insurances",
            "positions",
            "costCenters",
            "administrativeFeesRanges",
            "customersGroups",
            "fees",
            "customers",
            "statusAttachments",
            "statuses",
            "parkings",
            "suppliers",
            "employees",
            "vehicleControls",
            "housings",
            "stateProvinces",
            "cities",
            "companies",
        },
        allowSetters = true
    )
    private Affiliates affiliates;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "companies",
            "affiliates",
            "customers",
            "parkings",
            "suppliers",
            "employees",
            "originVehicleControls",
            "destinationVehicleControls",
            "vehicleLocationStatuses",
            "originVehicleControlExpenses",
            "destinationVehicleControlExpenses",
            "housings",
            "stateProvinces",
        },
        allowSetters = true
    )
    private Cities cities;

    @ManyToOne
    @JsonIgnoreProperties(value = { "customers", "vehicleControls", "affiliates" }, allowSetters = true)
    private CustomersGroups customersGroups;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customers id(Long id) {
        this.id = id;
        return this;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public Customers customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Customers active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCustomerNumber() {
        return this.customerNumber;
    }

    public Customers customerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
        return this;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerPostalCode() {
        return this.customerPostalCode;
    }

    public Customers customerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
        return this;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public String getCustomerAddress() {
        return this.customerAddress;
    }

    public Customers customerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
        return this;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerAddressComplement() {
        return this.customerAddressComplement;
    }

    public Customers customerAddressComplement(String customerAddressComplement) {
        this.customerAddressComplement = customerAddressComplement;
        return this;
    }

    public void setCustomerAddressComplement(String customerAddressComplement) {
        this.customerAddressComplement = customerAddressComplement;
    }

    public Integer getCustomerAddressNumber() {
        return this.customerAddressNumber;
    }

    public Customers customerAddressNumber(Integer customerAddressNumber) {
        this.customerAddressNumber = customerAddressNumber;
        return this;
    }

    public void setCustomerAddressNumber(Integer customerAddressNumber) {
        this.customerAddressNumber = customerAddressNumber;
    }

    public String getCustomerAddressNeighborhood() {
        return this.customerAddressNeighborhood;
    }

    public Customers customerAddressNeighborhood(String customerAddressNeighborhood) {
        this.customerAddressNeighborhood = customerAddressNeighborhood;
        return this;
    }

    public void setCustomerAddressNeighborhood(String customerAddressNeighborhood) {
        this.customerAddressNeighborhood = customerAddressNeighborhood;
    }

    public String getCustomerTelephone() {
        return this.customerTelephone;
    }

    public Customers customerTelephone(String customerTelephone) {
        this.customerTelephone = customerTelephone;
        return this;
    }

    public void setCustomerTelephone(String customerTelephone) {
        this.customerTelephone = customerTelephone;
    }

    public Integer getPaymentTerm() {
        return this.paymentTerm;
    }

    public Customers paymentTerm(Integer paymentTerm) {
        this.paymentTerm = paymentTerm;
        return this;
    }

    public void setPaymentTerm(Integer paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public Set<CustomersContacts> getCustomersContacts() {
        return this.customersContacts;
    }

    public Customers customersContacts(Set<CustomersContacts> customersContacts) {
        this.setCustomersContacts(customersContacts);
        return this;
    }

    public Customers addCustomersContacts(CustomersContacts customersContacts) {
        this.customersContacts.add(customersContacts);
        customersContacts.setCustomers(this);
        return this;
    }

    public Customers removeCustomersContacts(CustomersContacts customersContacts) {
        this.customersContacts.remove(customersContacts);
        customersContacts.setCustomers(null);
        return this;
    }

    public void setCustomersContacts(Set<CustomersContacts> customersContacts) {
        if (this.customersContacts != null) {
            this.customersContacts.forEach(i -> i.setCustomers(null));
        }
        if (customersContacts != null) {
            customersContacts.forEach(i -> i.setCustomers(this));
        }
        this.customersContacts = customersContacts;
    }

    public Set<CustomerAttachments> getCustomerAttachments() {
        return this.customerAttachments;
    }

    public Customers customerAttachments(Set<CustomerAttachments> customerAttachments) {
        this.setCustomerAttachments(customerAttachments);
        return this;
    }

    public Customers addCustomerAttachments(CustomerAttachments customerAttachments) {
        this.customerAttachments.add(customerAttachments);
        customerAttachments.setCustomers(this);
        return this;
    }

    public Customers removeCustomerAttachments(CustomerAttachments customerAttachments) {
        this.customerAttachments.remove(customerAttachments);
        customerAttachments.setCustomers(null);
        return this;
    }

    public void setCustomerAttachments(Set<CustomerAttachments> customerAttachments) {
        if (this.customerAttachments != null) {
            this.customerAttachments.forEach(i -> i.setCustomers(null));
        }
        if (customerAttachments != null) {
            customerAttachments.forEach(i -> i.setCustomers(this));
        }
        this.customerAttachments = customerAttachments;
    }

    public Set<VehicleControls> getVehicleControls() {
        return this.vehicleControls;
    }

    public Customers vehicleControls(Set<VehicleControls> vehicleControls) {
        this.setVehicleControls(vehicleControls);
        return this;
    }

    public Customers addVehicleControls(VehicleControls vehicleControls) {
        this.vehicleControls.add(vehicleControls);
        vehicleControls.setCustomers(this);
        return this;
    }

    public Customers removeVehicleControls(VehicleControls vehicleControls) {
        this.vehicleControls.remove(vehicleControls);
        vehicleControls.setCustomers(null);
        return this;
    }

    public void setVehicleControls(Set<VehicleControls> vehicleControls) {
        if (this.vehicleControls != null) {
            this.vehicleControls.forEach(i -> i.setCustomers(null));
        }
        if (vehicleControls != null) {
            vehicleControls.forEach(i -> i.setCustomers(this));
        }
        this.vehicleControls = vehicleControls;
    }

    public Set<Housing> getHousings() {
        return this.housings;
    }

    public Customers housings(Set<Housing> housings) {
        this.setHousings(housings);
        return this;
    }

    public Customers addHousing(Housing housing) {
        this.housings.add(housing);
        housing.setCustomers(this);
        return this;
    }

    public Customers removeHousing(Housing housing) {
        this.housings.remove(housing);
        housing.setCustomers(null);
        return this;
    }

    public void setHousings(Set<Housing> housings) {
        if (this.housings != null) {
            this.housings.forEach(i -> i.setCustomers(null));
        }
        if (housings != null) {
            housings.forEach(i -> i.setCustomers(this));
        }
        this.housings = housings;
    }

    public Affiliates getAffiliates() {
        return this.affiliates;
    }

    public Customers affiliates(Affiliates affiliates) {
        this.setAffiliates(affiliates);
        return this;
    }

    public void setAffiliates(Affiliates affiliates) {
        this.affiliates = affiliates;
    }

    public Cities getCities() {
        return this.cities;
    }

    public Customers cities(Cities cities) {
        this.setCities(cities);
        return this;
    }

    public void setCities(Cities cities) {
        this.cities = cities;
    }

    public CustomersGroups getCustomersGroups() {
        return this.customersGroups;
    }

    public Customers customersGroups(CustomersGroups customersGroups) {
        this.setCustomersGroups(customersGroups);
        return this;
    }

    public void setCustomersGroups(CustomersGroups customersGroups) {
        this.customersGroups = customersGroups;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customers)) {
            return false;
        }
        return id != null && id.equals(((Customers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customers{" +
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
            "}";
    }
}
