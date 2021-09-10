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
 * List of suppliers\nThis class is the list of suppliers.\n@author Samuel Souza
 */
@Entity
@Table(name = "suppliers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Suppliers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Supplier Name\n@type String
     */
    @NotNull
    @Column(name = "supplier_name", nullable = false)
    private String supplierName;

    /**
     * Customer Number is like a CNPJ, EIN, CPF, IDENTIFIED\n@type String
     */
    @Column(name = "supplier_number")
    private String supplierNumber;

    /**
     * Postal code of address the matrix company.\n@type String
     */
    @Size(max = 9)
    @Column(name = "supplier_postal_code", length = 9)
    private String supplierPostalCode;

    /**
     * Branch Address.\n@type String
     */
    @Column(name = "supplier_address")
    private String supplierAddress;

    /**
     * Parking Address Complement.\n@type String
     */
    @Column(name = "supplier_address_complement")
    private String supplierAddressComplement;

    /**
     * Number of address.\n@type Integer
     */
    @Column(name = "supplier_address_number")
    private Integer supplierAddressNumber;

    /**
     * Neighborhood, District.\n@type String
     */
    @Column(name = "supplier_address_neighborhood")
    private String supplierAddressNeighborhood;

    /**
     * Parking Contact Telephone\n@type String
     */
    @Column(name = "supplier_telephone")
    private String supplierTelephone;

    /**
     * Parking Contact Email\n@type String
     */
    @Column(name = "supplier_email")
    private String supplierEmail;

    /**
     * Parking Contact Name\n@type String
     */
    @Column(name = "supplier_contact_name")
    private String supplierContactName;

    @OneToMany(mappedBy = "suppliers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "suppliers" }, allowSetters = true)
    private Set<SupplierBanksInfo> supplierBanksInfos = new HashSet<>();

    @OneToMany(mappedBy = "suppliers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "suppliers" }, allowSetters = true)
    private Set<SupplierContacts> supplierContacts = new HashSet<>();

    @OneToMany(mappedBy = "suppliers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehicleControls", "suppliers", "origin", "destination", "vehicleControlItem" }, allowSetters = true)
    private Set<VehicleControlExpenses> vehicleControlExpenses = new HashSet<>();

    @OneToMany(mappedBy = "suppliers")
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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "suppliers" }, allowSetters = true)
    private ServiceProvided serviceProvided;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Suppliers id(Long id) {
        this.id = id;
        return this;
    }

    public String getSupplierName() {
        return this.supplierName;
    }

    public Suppliers supplierName(String supplierName) {
        this.supplierName = supplierName;
        return this;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierNumber() {
        return this.supplierNumber;
    }

    public Suppliers supplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
        return this;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public String getSupplierPostalCode() {
        return this.supplierPostalCode;
    }

    public Suppliers supplierPostalCode(String supplierPostalCode) {
        this.supplierPostalCode = supplierPostalCode;
        return this;
    }

    public void setSupplierPostalCode(String supplierPostalCode) {
        this.supplierPostalCode = supplierPostalCode;
    }

    public String getSupplierAddress() {
        return this.supplierAddress;
    }

    public Suppliers supplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
        return this;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierAddressComplement() {
        return this.supplierAddressComplement;
    }

    public Suppliers supplierAddressComplement(String supplierAddressComplement) {
        this.supplierAddressComplement = supplierAddressComplement;
        return this;
    }

    public void setSupplierAddressComplement(String supplierAddressComplement) {
        this.supplierAddressComplement = supplierAddressComplement;
    }

    public Integer getSupplierAddressNumber() {
        return this.supplierAddressNumber;
    }

    public Suppliers supplierAddressNumber(Integer supplierAddressNumber) {
        this.supplierAddressNumber = supplierAddressNumber;
        return this;
    }

    public void setSupplierAddressNumber(Integer supplierAddressNumber) {
        this.supplierAddressNumber = supplierAddressNumber;
    }

    public String getSupplierAddressNeighborhood() {
        return this.supplierAddressNeighborhood;
    }

    public Suppliers supplierAddressNeighborhood(String supplierAddressNeighborhood) {
        this.supplierAddressNeighborhood = supplierAddressNeighborhood;
        return this;
    }

    public void setSupplierAddressNeighborhood(String supplierAddressNeighborhood) {
        this.supplierAddressNeighborhood = supplierAddressNeighborhood;
    }

    public String getSupplierTelephone() {
        return this.supplierTelephone;
    }

    public Suppliers supplierTelephone(String supplierTelephone) {
        this.supplierTelephone = supplierTelephone;
        return this;
    }

    public void setSupplierTelephone(String supplierTelephone) {
        this.supplierTelephone = supplierTelephone;
    }

    public String getSupplierEmail() {
        return this.supplierEmail;
    }

    public Suppliers supplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
        return this;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public String getSupplierContactName() {
        return this.supplierContactName;
    }

    public Suppliers supplierContactName(String supplierContactName) {
        this.supplierContactName = supplierContactName;
        return this;
    }

    public void setSupplierContactName(String supplierContactName) {
        this.supplierContactName = supplierContactName;
    }

    public Set<SupplierBanksInfo> getSupplierBanksInfos() {
        return this.supplierBanksInfos;
    }

    public Suppliers supplierBanksInfos(Set<SupplierBanksInfo> supplierBanksInfos) {
        this.setSupplierBanksInfos(supplierBanksInfos);
        return this;
    }

    public Suppliers addSupplierBanksInfo(SupplierBanksInfo supplierBanksInfo) {
        this.supplierBanksInfos.add(supplierBanksInfo);
        supplierBanksInfo.setSuppliers(this);
        return this;
    }

    public Suppliers removeSupplierBanksInfo(SupplierBanksInfo supplierBanksInfo) {
        this.supplierBanksInfos.remove(supplierBanksInfo);
        supplierBanksInfo.setSuppliers(null);
        return this;
    }

    public void setSupplierBanksInfos(Set<SupplierBanksInfo> supplierBanksInfos) {
        if (this.supplierBanksInfos != null) {
            this.supplierBanksInfos.forEach(i -> i.setSuppliers(null));
        }
        if (supplierBanksInfos != null) {
            supplierBanksInfos.forEach(i -> i.setSuppliers(this));
        }
        this.supplierBanksInfos = supplierBanksInfos;
    }

    public Set<SupplierContacts> getSupplierContacts() {
        return this.supplierContacts;
    }

    public Suppliers supplierContacts(Set<SupplierContacts> supplierContacts) {
        this.setSupplierContacts(supplierContacts);
        return this;
    }

    public Suppliers addSupplierContacts(SupplierContacts supplierContacts) {
        this.supplierContacts.add(supplierContacts);
        supplierContacts.setSuppliers(this);
        return this;
    }

    public Suppliers removeSupplierContacts(SupplierContacts supplierContacts) {
        this.supplierContacts.remove(supplierContacts);
        supplierContacts.setSuppliers(null);
        return this;
    }

    public void setSupplierContacts(Set<SupplierContacts> supplierContacts) {
        if (this.supplierContacts != null) {
            this.supplierContacts.forEach(i -> i.setSuppliers(null));
        }
        if (supplierContacts != null) {
            supplierContacts.forEach(i -> i.setSuppliers(this));
        }
        this.supplierContacts = supplierContacts;
    }

    public Set<VehicleControlExpenses> getVehicleControlExpenses() {
        return this.vehicleControlExpenses;
    }

    public Suppliers vehicleControlExpenses(Set<VehicleControlExpenses> vehicleControlExpenses) {
        this.setVehicleControlExpenses(vehicleControlExpenses);
        return this;
    }

    public Suppliers addVehicleControlExpenses(VehicleControlExpenses vehicleControlExpenses) {
        this.vehicleControlExpenses.add(vehicleControlExpenses);
        vehicleControlExpenses.setSuppliers(this);
        return this;
    }

    public Suppliers removeVehicleControlExpenses(VehicleControlExpenses vehicleControlExpenses) {
        this.vehicleControlExpenses.remove(vehicleControlExpenses);
        vehicleControlExpenses.setSuppliers(null);
        return this;
    }

    public void setVehicleControlExpenses(Set<VehicleControlExpenses> vehicleControlExpenses) {
        if (this.vehicleControlExpenses != null) {
            this.vehicleControlExpenses.forEach(i -> i.setSuppliers(null));
        }
        if (vehicleControlExpenses != null) {
            vehicleControlExpenses.forEach(i -> i.setSuppliers(this));
        }
        this.vehicleControlExpenses = vehicleControlExpenses;
    }

    public Set<Housing> getHousings() {
        return this.housings;
    }

    public Suppliers housings(Set<Housing> housings) {
        this.setHousings(housings);
        return this;
    }

    public Suppliers addHousing(Housing housing) {
        this.housings.add(housing);
        housing.setSuppliers(this);
        return this;
    }

    public Suppliers removeHousing(Housing housing) {
        this.housings.remove(housing);
        housing.setSuppliers(null);
        return this;
    }

    public void setHousings(Set<Housing> housings) {
        if (this.housings != null) {
            this.housings.forEach(i -> i.setSuppliers(null));
        }
        if (housings != null) {
            housings.forEach(i -> i.setSuppliers(this));
        }
        this.housings = housings;
    }

    public Affiliates getAffiliates() {
        return this.affiliates;
    }

    public Suppliers affiliates(Affiliates affiliates) {
        this.setAffiliates(affiliates);
        return this;
    }

    public void setAffiliates(Affiliates affiliates) {
        this.affiliates = affiliates;
    }

    public Cities getCities() {
        return this.cities;
    }

    public Suppliers cities(Cities cities) {
        this.setCities(cities);
        return this;
    }

    public void setCities(Cities cities) {
        this.cities = cities;
    }

    public ServiceProvided getServiceProvided() {
        return this.serviceProvided;
    }

    public Suppliers serviceProvided(ServiceProvided serviceProvided) {
        this.setServiceProvided(serviceProvided);
        return this;
    }

    public void setServiceProvided(ServiceProvided serviceProvided) {
        this.serviceProvided = serviceProvided;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Suppliers)) {
            return false;
        }
        return id != null && id.equals(((Suppliers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Suppliers{" +
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
            "}";
    }
}
