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
 * List of Company Hire this software.\n@author Samuel Souza
 */
@Entity
@Table(name = "companies")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Companies implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Matrix Companies Name\n@type String
     */
    @NotNull
    @Column(name = "company_name", nullable = false)
    private String companyName;

    /**
     * TradName\n@type String
     */
    @Column(name = "trade_name")
    private String tradeName;

    /**
     * Company Number is like a CNPJ, EIN\n@type String
     */
    @NotNull
    @Column(name = "company_number", nullable = false)
    private String companyNumber;

    /**
     * Postal code of address the matrix company.\n@type String
     */
    @Size(max = 9)
    @Column(name = "postal_code", length = 9)
    private String postalCode;

    /**
     * Company Address\n@type String
     */
    @Column(name = "company_address")
    private String companyAddress;

    /**
     * Company Address Complement.\n@type String
     */
    @Column(name = "company_address_complement")
    private String companyAddressComplement;

    /**
     * Number of address.\n@type Integer
     */
    @Column(name = "company_address_number")
    private Integer companyAddressNumber;

    /**
     * Neighborhood, District.\n@type String
     */
    @Column(name = "company_address_neighborhood")
    private String companyAddressNeighborhood;

    /**
     * Phone of company\n@type String
     */
    @Column(name = "company_telephone")
    private String companyTelephone;

    /**
     * E-mail of the company\n@type String
     */
    @Column(name = "company_email")
    private String companyEmail;

    /**
     * Responsible Contact to call or send e-mail\n@type String
     */
    @Column(name = "responsible_contact")
    private String responsibleContact;

    @OneToMany(mappedBy = "companies")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Affiliates> affiliates = new HashSet<>();

    @OneToMany(mappedBy = "companies")
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
    @JsonIgnoreProperties(
        value = { "cities", "companies", "affiliates", "toInsurances", "forInsurances", "countries" },
        allowSetters = true
    )
    private StateProvinces stateProvinces;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Companies id(Long id) {
        this.id = id;
        return this;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public Companies companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTradeName() {
        return this.tradeName;
    }

    public Companies tradeName(String tradeName) {
        this.tradeName = tradeName;
        return this;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getCompanyNumber() {
        return this.companyNumber;
    }

    public Companies companyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
        return this;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public Companies postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCompanyAddress() {
        return this.companyAddress;
    }

    public Companies companyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
        return this;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyAddressComplement() {
        return this.companyAddressComplement;
    }

    public Companies companyAddressComplement(String companyAddressComplement) {
        this.companyAddressComplement = companyAddressComplement;
        return this;
    }

    public void setCompanyAddressComplement(String companyAddressComplement) {
        this.companyAddressComplement = companyAddressComplement;
    }

    public Integer getCompanyAddressNumber() {
        return this.companyAddressNumber;
    }

    public Companies companyAddressNumber(Integer companyAddressNumber) {
        this.companyAddressNumber = companyAddressNumber;
        return this;
    }

    public void setCompanyAddressNumber(Integer companyAddressNumber) {
        this.companyAddressNumber = companyAddressNumber;
    }

    public String getCompanyAddressNeighborhood() {
        return this.companyAddressNeighborhood;
    }

    public Companies companyAddressNeighborhood(String companyAddressNeighborhood) {
        this.companyAddressNeighborhood = companyAddressNeighborhood;
        return this;
    }

    public void setCompanyAddressNeighborhood(String companyAddressNeighborhood) {
        this.companyAddressNeighborhood = companyAddressNeighborhood;
    }

    public String getCompanyTelephone() {
        return this.companyTelephone;
    }

    public Companies companyTelephone(String companyTelephone) {
        this.companyTelephone = companyTelephone;
        return this;
    }

    public void setCompanyTelephone(String companyTelephone) {
        this.companyTelephone = companyTelephone;
    }

    public String getCompanyEmail() {
        return this.companyEmail;
    }

    public Companies companyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
        return this;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getResponsibleContact() {
        return this.responsibleContact;
    }

    public Companies responsibleContact(String responsibleContact) {
        this.responsibleContact = responsibleContact;
        return this;
    }

    public void setResponsibleContact(String responsibleContact) {
        this.responsibleContact = responsibleContact;
    }

    public Set<Affiliates> getAffiliates() {
        return this.affiliates;
    }

    public Companies affiliates(Set<Affiliates> affiliates) {
        this.setAffiliates(affiliates);
        return this;
    }

    public Companies addAffiliates(Affiliates affiliates) {
        this.affiliates.add(affiliates);
        affiliates.setCompanies(this);
        return this;
    }

    public Companies removeAffiliates(Affiliates affiliates) {
        this.affiliates.remove(affiliates);
        affiliates.setCompanies(null);
        return this;
    }

    public void setAffiliates(Set<Affiliates> affiliates) {
        if (this.affiliates != null) {
            this.affiliates.forEach(i -> i.setCompanies(null));
        }
        if (affiliates != null) {
            affiliates.forEach(i -> i.setCompanies(this));
        }
        this.affiliates = affiliates;
    }

    public Set<Employees> getEmployees() {
        return this.employees;
    }

    public Companies employees(Set<Employees> employees) {
        this.setEmployees(employees);
        return this;
    }

    public Companies addEmployees(Employees employees) {
        this.employees.add(employees);
        employees.setCompanies(this);
        return this;
    }

    public Companies removeEmployees(Employees employees) {
        this.employees.remove(employees);
        employees.setCompanies(null);
        return this;
    }

    public void setEmployees(Set<Employees> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.setCompanies(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setCompanies(this));
        }
        this.employees = employees;
    }

    public Cities getCities() {
        return this.cities;
    }

    public Companies cities(Cities cities) {
        this.setCities(cities);
        return this;
    }

    public void setCities(Cities cities) {
        this.cities = cities;
    }

    public StateProvinces getStateProvinces() {
        return this.stateProvinces;
    }

    public Companies stateProvinces(StateProvinces stateProvinces) {
        this.setStateProvinces(stateProvinces);
        return this;
    }

    public void setStateProvinces(StateProvinces stateProvinces) {
        this.stateProvinces = stateProvinces;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Companies)) {
            return false;
        }
        return id != null && id.equals(((Companies) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Companies{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", tradeName='" + getTradeName() + "'" +
            ", companyNumber='" + getCompanyNumber() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", companyAddress='" + getCompanyAddress() + "'" +
            ", companyAddressComplement='" + getCompanyAddressComplement() + "'" +
            ", companyAddressNumber=" + getCompanyAddressNumber() +
            ", companyAddressNeighborhood='" + getCompanyAddressNeighborhood() + "'" +
            ", companyTelephone='" + getCompanyTelephone() + "'" +
            ", companyEmail='" + getCompanyEmail() + "'" +
            ", responsibleContact='" + getResponsibleContact() + "'" +
            "}";
    }
}
