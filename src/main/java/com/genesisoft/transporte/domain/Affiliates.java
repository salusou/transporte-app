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
 * List of Affiliates.\n@author Samuel Souza
 */
@Entity
@Table(name = "affiliates")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Affiliates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "branch_name", nullable = false)
    private String branchName;

    /**
     * Company Number is like a CNPJ, EIN\n@type String
     */
    @NotNull
    @Column(name = "branch_number", nullable = false)
    private String branchNumber;

    /**
     * If true get all information of company.\n@type Boolean
     */
    @Column(name = "use_same_company_address")
    private Boolean useSameCompanyAddress;

    /**
     * Postal code of address the matrix company.\n@type String
     */
    @Size(max = 9)
    @Column(name = "branch_postal_code", length = 9)
    private String branchPostalCode;

    /**
     * Branch Address.\n@type String
     */
    @Column(name = "branch_address")
    private String branchAddress;

    /**
     * Branch Address Complement.\n@type String
     */
    @Column(name = "branch_address_complement")
    private String branchAddressComplement;

    /**
     * Number of address.\n@type Integer
     */
    @Column(name = "branch_address_number")
    private Integer branchAddressNumber;

    /**
     * Neighborhood, District.\n@type String
     */
    @Column(name = "branch_address_neighborhood")
    private String branchAddressNeighborhood;

    /**
     * Phone of branch\n@type String
     */
    @Column(name = "branch_telephone")
    private String branchTelephone;

    /**
     * E-mail of the branch\n@type String
     */
    @Column(name = "branch_email")
    private String branchEmail;

    /**
     * Responsible Contact to call or send e-mail\n@type String
     */
    @Column(name = "responsible_contact")
    private String responsibleContact;

    @OneToMany(mappedBy = "affiliates")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "affiliates", "toStateProvince", "forStateProvince" }, allowSetters = true)
    private Set<Insurances> insurances = new HashSet<>();

    @OneToMany(mappedBy = "affiliates")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "employees", "affiliates" }, allowSetters = true)
    private Set<Positions> positions = new HashSet<>();

    @OneToMany(mappedBy = "affiliates")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "housings", "affiliates" }, allowSetters = true)
    private Set<CostCenter> costCenters = new HashSet<>();

    @OneToMany(mappedBy = "affiliates")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "affiliates" }, allowSetters = true)
    private Set<AdministrativeFeesRanges> administrativeFeesRanges = new HashSet<>();

    @OneToMany(mappedBy = "affiliates")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customers", "vehicleControls", "affiliates" }, allowSetters = true)
    private Set<CustomersGroups> customersGroups = new HashSet<>();

    @OneToMany(mappedBy = "affiliates")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehicleControlBillings", "affiliates" }, allowSetters = true)
    private Set<Fees> fees = new HashSet<>();

    @OneToMany(mappedBy = "affiliates")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "customersContacts", "customerAttachments", "vehicleControls", "housings", "affiliates", "cities", "customersGroups" },
        allowSetters = true
    )
    private Set<Customers> customers = new HashSet<>();

    @OneToMany(mappedBy = "affiliates")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customerAttachments", "affiliates" }, allowSetters = true)
    private Set<StatusAttachments> statusAttachments = new HashSet<>();

    @OneToMany(mappedBy = "affiliates")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehicleControls", "housings", "affiliates" }, allowSetters = true)
    private Set<Status> statuses = new HashSet<>();

    @OneToMany(mappedBy = "affiliates")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parkingSectors", "housings", "affiliates", "cities" }, allowSetters = true)
    private Set<Parking> parkings = new HashSet<>();

    @OneToMany(mappedBy = "affiliates")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "supplierBanksInfos", "supplierContacts", "vehicleControlExpenses", "housings", "affiliates", "cities", "serviceProvided",
        },
        allowSetters = true
    )
    private Set<Suppliers> suppliers = new HashSet<>();

    @OneToMany(mappedBy = "affiliates")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "employeeAttachments",
            "employeeComponentsData",
            "vehicleControls",
            "vehicleControlHistories",
            "housings",
            "companies",
            "affiliates",
            "cities",
            "positions",
        },
        allowSetters = true
    )
    private Set<Employees> employees = new HashSet<>();

    @OneToMany(mappedBy = "affiliates")
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

    @OneToMany(mappedBy = "affiliates")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "housingVehicleItems", "affiliates", "status", "customers", "employees", "parking", "costCenter", "suppliers", "cities" },
        allowSetters = true
    )
    private Set<Housing> housings = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "cities", "companies", "affiliates", "toInsurances", "forInsurances", "countries" },
        allowSetters = true
    )
    private StateProvinces stateProvinces;

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
    @JsonIgnoreProperties(value = { "affiliates", "employees", "cities", "stateProvinces" }, allowSetters = true)
    private Companies companies;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Affiliates id(Long id) {
        this.id = id;
        return this;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public Affiliates branchName(String branchName) {
        this.branchName = branchName;
        return this;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchNumber() {
        return this.branchNumber;
    }

    public Affiliates branchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
        return this;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    public Boolean getUseSameCompanyAddress() {
        return this.useSameCompanyAddress;
    }

    public Affiliates useSameCompanyAddress(Boolean useSameCompanyAddress) {
        this.useSameCompanyAddress = useSameCompanyAddress;
        return this;
    }

    public void setUseSameCompanyAddress(Boolean useSameCompanyAddress) {
        this.useSameCompanyAddress = useSameCompanyAddress;
    }

    public String getBranchPostalCode() {
        return this.branchPostalCode;
    }

    public Affiliates branchPostalCode(String branchPostalCode) {
        this.branchPostalCode = branchPostalCode;
        return this;
    }

    public void setBranchPostalCode(String branchPostalCode) {
        this.branchPostalCode = branchPostalCode;
    }

    public String getBranchAddress() {
        return this.branchAddress;
    }

    public Affiliates branchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
        return this;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getBranchAddressComplement() {
        return this.branchAddressComplement;
    }

    public Affiliates branchAddressComplement(String branchAddressComplement) {
        this.branchAddressComplement = branchAddressComplement;
        return this;
    }

    public void setBranchAddressComplement(String branchAddressComplement) {
        this.branchAddressComplement = branchAddressComplement;
    }

    public Integer getBranchAddressNumber() {
        return this.branchAddressNumber;
    }

    public Affiliates branchAddressNumber(Integer branchAddressNumber) {
        this.branchAddressNumber = branchAddressNumber;
        return this;
    }

    public void setBranchAddressNumber(Integer branchAddressNumber) {
        this.branchAddressNumber = branchAddressNumber;
    }

    public String getBranchAddressNeighborhood() {
        return this.branchAddressNeighborhood;
    }

    public Affiliates branchAddressNeighborhood(String branchAddressNeighborhood) {
        this.branchAddressNeighborhood = branchAddressNeighborhood;
        return this;
    }

    public void setBranchAddressNeighborhood(String branchAddressNeighborhood) {
        this.branchAddressNeighborhood = branchAddressNeighborhood;
    }

    public String getBranchTelephone() {
        return this.branchTelephone;
    }

    public Affiliates branchTelephone(String branchTelephone) {
        this.branchTelephone = branchTelephone;
        return this;
    }

    public void setBranchTelephone(String branchTelephone) {
        this.branchTelephone = branchTelephone;
    }

    public String getBranchEmail() {
        return this.branchEmail;
    }

    public Affiliates branchEmail(String branchEmail) {
        this.branchEmail = branchEmail;
        return this;
    }

    public void setBranchEmail(String branchEmail) {
        this.branchEmail = branchEmail;
    }

    public String getResponsibleContact() {
        return this.responsibleContact;
    }

    public Affiliates responsibleContact(String responsibleContact) {
        this.responsibleContact = responsibleContact;
        return this;
    }

    public void setResponsibleContact(String responsibleContact) {
        this.responsibleContact = responsibleContact;
    }

    public Set<Insurances> getInsurances() {
        return this.insurances;
    }

    public Affiliates insurances(Set<Insurances> insurances) {
        this.setInsurances(insurances);
        return this;
    }

    public Affiliates addInsurances(Insurances insurances) {
        this.insurances.add(insurances);
        insurances.setAffiliates(this);
        return this;
    }

    public Affiliates removeInsurances(Insurances insurances) {
        this.insurances.remove(insurances);
        insurances.setAffiliates(null);
        return this;
    }

    public void setInsurances(Set<Insurances> insurances) {
        if (this.insurances != null) {
            this.insurances.forEach(i -> i.setAffiliates(null));
        }
        if (insurances != null) {
            insurances.forEach(i -> i.setAffiliates(this));
        }
        this.insurances = insurances;
    }

    public Set<Positions> getPositions() {
        return this.positions;
    }

    public Affiliates positions(Set<Positions> positions) {
        this.setPositions(positions);
        return this;
    }

    public Affiliates addPositions(Positions positions) {
        this.positions.add(positions);
        positions.setAffiliates(this);
        return this;
    }

    public Affiliates removePositions(Positions positions) {
        this.positions.remove(positions);
        positions.setAffiliates(null);
        return this;
    }

    public void setPositions(Set<Positions> positions) {
        if (this.positions != null) {
            this.positions.forEach(i -> i.setAffiliates(null));
        }
        if (positions != null) {
            positions.forEach(i -> i.setAffiliates(this));
        }
        this.positions = positions;
    }

    public Set<CostCenter> getCostCenters() {
        return this.costCenters;
    }

    public Affiliates costCenters(Set<CostCenter> costCenters) {
        this.setCostCenters(costCenters);
        return this;
    }

    public Affiliates addCostCenter(CostCenter costCenter) {
        this.costCenters.add(costCenter);
        costCenter.setAffiliates(this);
        return this;
    }

    public Affiliates removeCostCenter(CostCenter costCenter) {
        this.costCenters.remove(costCenter);
        costCenter.setAffiliates(null);
        return this;
    }

    public void setCostCenters(Set<CostCenter> costCenters) {
        if (this.costCenters != null) {
            this.costCenters.forEach(i -> i.setAffiliates(null));
        }
        if (costCenters != null) {
            costCenters.forEach(i -> i.setAffiliates(this));
        }
        this.costCenters = costCenters;
    }

    public Set<AdministrativeFeesRanges> getAdministrativeFeesRanges() {
        return this.administrativeFeesRanges;
    }

    public Affiliates administrativeFeesRanges(Set<AdministrativeFeesRanges> administrativeFeesRanges) {
        this.setAdministrativeFeesRanges(administrativeFeesRanges);
        return this;
    }

    public Affiliates addAdministrativeFeesRanges(AdministrativeFeesRanges administrativeFeesRanges) {
        this.administrativeFeesRanges.add(administrativeFeesRanges);
        administrativeFeesRanges.setAffiliates(this);
        return this;
    }

    public Affiliates removeAdministrativeFeesRanges(AdministrativeFeesRanges administrativeFeesRanges) {
        this.administrativeFeesRanges.remove(administrativeFeesRanges);
        administrativeFeesRanges.setAffiliates(null);
        return this;
    }

    public void setAdministrativeFeesRanges(Set<AdministrativeFeesRanges> administrativeFeesRanges) {
        if (this.administrativeFeesRanges != null) {
            this.administrativeFeesRanges.forEach(i -> i.setAffiliates(null));
        }
        if (administrativeFeesRanges != null) {
            administrativeFeesRanges.forEach(i -> i.setAffiliates(this));
        }
        this.administrativeFeesRanges = administrativeFeesRanges;
    }

    public Set<CustomersGroups> getCustomersGroups() {
        return this.customersGroups;
    }

    public Affiliates customersGroups(Set<CustomersGroups> customersGroups) {
        this.setCustomersGroups(customersGroups);
        return this;
    }

    public Affiliates addCustomersGroups(CustomersGroups customersGroups) {
        this.customersGroups.add(customersGroups);
        customersGroups.setAffiliates(this);
        return this;
    }

    public Affiliates removeCustomersGroups(CustomersGroups customersGroups) {
        this.customersGroups.remove(customersGroups);
        customersGroups.setAffiliates(null);
        return this;
    }

    public void setCustomersGroups(Set<CustomersGroups> customersGroups) {
        if (this.customersGroups != null) {
            this.customersGroups.forEach(i -> i.setAffiliates(null));
        }
        if (customersGroups != null) {
            customersGroups.forEach(i -> i.setAffiliates(this));
        }
        this.customersGroups = customersGroups;
    }

    public Set<Fees> getFees() {
        return this.fees;
    }

    public Affiliates fees(Set<Fees> fees) {
        this.setFees(fees);
        return this;
    }

    public Affiliates addFees(Fees fees) {
        this.fees.add(fees);
        fees.setAffiliates(this);
        return this;
    }

    public Affiliates removeFees(Fees fees) {
        this.fees.remove(fees);
        fees.setAffiliates(null);
        return this;
    }

    public void setFees(Set<Fees> fees) {
        if (this.fees != null) {
            this.fees.forEach(i -> i.setAffiliates(null));
        }
        if (fees != null) {
            fees.forEach(i -> i.setAffiliates(this));
        }
        this.fees = fees;
    }

    public Set<Customers> getCustomers() {
        return this.customers;
    }

    public Affiliates customers(Set<Customers> customers) {
        this.setCustomers(customers);
        return this;
    }

    public Affiliates addCustomers(Customers customers) {
        this.customers.add(customers);
        customers.setAffiliates(this);
        return this;
    }

    public Affiliates removeCustomers(Customers customers) {
        this.customers.remove(customers);
        customers.setAffiliates(null);
        return this;
    }

    public void setCustomers(Set<Customers> customers) {
        if (this.customers != null) {
            this.customers.forEach(i -> i.setAffiliates(null));
        }
        if (customers != null) {
            customers.forEach(i -> i.setAffiliates(this));
        }
        this.customers = customers;
    }

    public Set<StatusAttachments> getStatusAttachments() {
        return this.statusAttachments;
    }

    public Affiliates statusAttachments(Set<StatusAttachments> statusAttachments) {
        this.setStatusAttachments(statusAttachments);
        return this;
    }

    public Affiliates addStatusAttachments(StatusAttachments statusAttachments) {
        this.statusAttachments.add(statusAttachments);
        statusAttachments.setAffiliates(this);
        return this;
    }

    public Affiliates removeStatusAttachments(StatusAttachments statusAttachments) {
        this.statusAttachments.remove(statusAttachments);
        statusAttachments.setAffiliates(null);
        return this;
    }

    public void setStatusAttachments(Set<StatusAttachments> statusAttachments) {
        if (this.statusAttachments != null) {
            this.statusAttachments.forEach(i -> i.setAffiliates(null));
        }
        if (statusAttachments != null) {
            statusAttachments.forEach(i -> i.setAffiliates(this));
        }
        this.statusAttachments = statusAttachments;
    }

    public Set<Status> getStatuses() {
        return this.statuses;
    }

    public Affiliates statuses(Set<Status> statuses) {
        this.setStatuses(statuses);
        return this;
    }

    public Affiliates addStatus(Status status) {
        this.statuses.add(status);
        status.setAffiliates(this);
        return this;
    }

    public Affiliates removeStatus(Status status) {
        this.statuses.remove(status);
        status.setAffiliates(null);
        return this;
    }

    public void setStatuses(Set<Status> statuses) {
        if (this.statuses != null) {
            this.statuses.forEach(i -> i.setAffiliates(null));
        }
        if (statuses != null) {
            statuses.forEach(i -> i.setAffiliates(this));
        }
        this.statuses = statuses;
    }

    public Set<Parking> getParkings() {
        return this.parkings;
    }

    public Affiliates parkings(Set<Parking> parkings) {
        this.setParkings(parkings);
        return this;
    }

    public Affiliates addParking(Parking parking) {
        this.parkings.add(parking);
        parking.setAffiliates(this);
        return this;
    }

    public Affiliates removeParking(Parking parking) {
        this.parkings.remove(parking);
        parking.setAffiliates(null);
        return this;
    }

    public void setParkings(Set<Parking> parkings) {
        if (this.parkings != null) {
            this.parkings.forEach(i -> i.setAffiliates(null));
        }
        if (parkings != null) {
            parkings.forEach(i -> i.setAffiliates(this));
        }
        this.parkings = parkings;
    }

    public Set<Suppliers> getSuppliers() {
        return this.suppliers;
    }

    public Affiliates suppliers(Set<Suppliers> suppliers) {
        this.setSuppliers(suppliers);
        return this;
    }

    public Affiliates addSuppliers(Suppliers suppliers) {
        this.suppliers.add(suppliers);
        suppliers.setAffiliates(this);
        return this;
    }

    public Affiliates removeSuppliers(Suppliers suppliers) {
        this.suppliers.remove(suppliers);
        suppliers.setAffiliates(null);
        return this;
    }

    public void setSuppliers(Set<Suppliers> suppliers) {
        if (this.suppliers != null) {
            this.suppliers.forEach(i -> i.setAffiliates(null));
        }
        if (suppliers != null) {
            suppliers.forEach(i -> i.setAffiliates(this));
        }
        this.suppliers = suppliers;
    }

    public Set<Employees> getEmployees() {
        return this.employees;
    }

    public Affiliates employees(Set<Employees> employees) {
        this.setEmployees(employees);
        return this;
    }

    public Affiliates addEmployees(Employees employees) {
        this.employees.add(employees);
        employees.setAffiliates(this);
        return this;
    }

    public Affiliates removeEmployees(Employees employees) {
        this.employees.remove(employees);
        employees.setAffiliates(null);
        return this;
    }

    public void setEmployees(Set<Employees> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.setAffiliates(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setAffiliates(this));
        }
        this.employees = employees;
    }

    public Set<VehicleControls> getVehicleControls() {
        return this.vehicleControls;
    }

    public Affiliates vehicleControls(Set<VehicleControls> vehicleControls) {
        this.setVehicleControls(vehicleControls);
        return this;
    }

    public Affiliates addVehicleControls(VehicleControls vehicleControls) {
        this.vehicleControls.add(vehicleControls);
        vehicleControls.setAffiliates(this);
        return this;
    }

    public Affiliates removeVehicleControls(VehicleControls vehicleControls) {
        this.vehicleControls.remove(vehicleControls);
        vehicleControls.setAffiliates(null);
        return this;
    }

    public void setVehicleControls(Set<VehicleControls> vehicleControls) {
        if (this.vehicleControls != null) {
            this.vehicleControls.forEach(i -> i.setAffiliates(null));
        }
        if (vehicleControls != null) {
            vehicleControls.forEach(i -> i.setAffiliates(this));
        }
        this.vehicleControls = vehicleControls;
    }

    public Set<Housing> getHousings() {
        return this.housings;
    }

    public Affiliates housings(Set<Housing> housings) {
        this.setHousings(housings);
        return this;
    }

    public Affiliates addHousing(Housing housing) {
        this.housings.add(housing);
        housing.setAffiliates(this);
        return this;
    }

    public Affiliates removeHousing(Housing housing) {
        this.housings.remove(housing);
        housing.setAffiliates(null);
        return this;
    }

    public void setHousings(Set<Housing> housings) {
        if (this.housings != null) {
            this.housings.forEach(i -> i.setAffiliates(null));
        }
        if (housings != null) {
            housings.forEach(i -> i.setAffiliates(this));
        }
        this.housings = housings;
    }

    public StateProvinces getStateProvinces() {
        return this.stateProvinces;
    }

    public Affiliates stateProvinces(StateProvinces stateProvinces) {
        this.setStateProvinces(stateProvinces);
        return this;
    }

    public void setStateProvinces(StateProvinces stateProvinces) {
        this.stateProvinces = stateProvinces;
    }

    public Cities getCities() {
        return this.cities;
    }

    public Affiliates cities(Cities cities) {
        this.setCities(cities);
        return this;
    }

    public void setCities(Cities cities) {
        this.cities = cities;
    }

    public Companies getCompanies() {
        return this.companies;
    }

    public Affiliates companies(Companies companies) {
        this.setCompanies(companies);
        return this;
    }

    public void setCompanies(Companies companies) {
        this.companies = companies;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Affiliates)) {
            return false;
        }
        return id != null && id.equals(((Affiliates) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Affiliates{" +
            "id=" + getId() +
            ", branchName='" + getBranchName() + "'" +
            ", branchNumber='" + getBranchNumber() + "'" +
            ", useSameCompanyAddress='" + getUseSameCompanyAddress() + "'" +
            ", branchPostalCode='" + getBranchPostalCode() + "'" +
            ", branchAddress='" + getBranchAddress() + "'" +
            ", branchAddressComplement='" + getBranchAddressComplement() + "'" +
            ", branchAddressNumber=" + getBranchAddressNumber() +
            ", branchAddressNeighborhood='" + getBranchAddressNeighborhood() + "'" +
            ", branchTelephone='" + getBranchTelephone() + "'" +
            ", branchEmail='" + getBranchEmail() + "'" +
            ", responsibleContact='" + getResponsibleContact() + "'" +
            "}";
    }
}
