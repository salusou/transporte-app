package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Employees.
 */
@Entity
@Table(name = "employees")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employees implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Employee Active. Check if employee is active\n@type Boolean
     */
    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    /**
     * Employee Full Name.\n@type String
     */
    @NotNull
    @Column(name = "employee_full_name", nullable = false)
    private String employeeFullName;

    /**
     * Employee E-mail\n@type String
     */
    @NotNull
    @Column(name = "employee_email", nullable = false)
    private String employeeEmail;

    /**
     * Employee Supplier Identification Number. Identidade.\n@type String
     */
    @Column(name = "employee_identification_number")
    private String employeeIdentificationNumber;

    /**
     * Employee Number. CPF.\n@type String
     */
    @Column(name = "employee_number")
    private String employeeNumber;

    /**
     * Postal code employee.\n@type String
     */
    @Size(max = 9)
    @Column(name = "employee_postal_code", length = 9)
    private String employeePostalCode;

    /**
     * Branch Address.\n@type String
     */
    @Column(name = "employee_address")
    private String employeeAddress;

    /**
     * Employee Address Complement.\n@type String
     */
    @Column(name = "employee_address_complement")
    private String employeeAddressComplement;

    /**
     * Number of address.\n@type Integer
     */
    @Column(name = "employee_address_number")
    private Integer employeeAddressNumber;

    /**
     * Neighborhood, District.\n@type String
     */
    @Column(name = "employee_address_neighborhood")
    private String employeeAddressNeighborhood;

    /**
     * Employee Birthday.\n@type String
     */
    @Column(name = "employee_birthday")
    private LocalDate employeeBirthday;

    @OneToMany(mappedBy = "employees")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "employees" }, allowSetters = true)
    private Set<EmployeeAttachments> employeeAttachments = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "employee" }, allowSetters = true)
    private Set<EmployeeComponentsData> employeeComponentsData = new HashSet<>();

    @OneToMany(mappedBy = "employees")
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

    @OneToMany(mappedBy = "employees")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehicleControls", "employees" }, allowSetters = true)
    private Set<VehicleControlHistory> vehicleControlHistories = new HashSet<>();

    @OneToMany(mappedBy = "employees")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "housingVehicleItems", "affiliates", "status", "customers", "employees", "parking", "costCenter", "suppliers", "cities" },
        allowSetters = true
    )
    private Set<Housing> housings = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "affiliates", "employees", "cities", "stateProvinces" }, allowSetters = true)
    private Companies companies;

    @ManyToOne
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
    @JsonIgnoreProperties(value = { "employees", "affiliates" }, allowSetters = true)
    private Positions positions;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employees id(Long id) {
        this.id = id;
        return this;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Employees active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEmployeeFullName() {
        return this.employeeFullName;
    }

    public Employees employeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
        return this;
    }

    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
    }

    public String getEmployeeEmail() {
        return this.employeeEmail;
    }

    public Employees employeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
        return this;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeIdentificationNumber() {
        return this.employeeIdentificationNumber;
    }

    public Employees employeeIdentificationNumber(String employeeIdentificationNumber) {
        this.employeeIdentificationNumber = employeeIdentificationNumber;
        return this;
    }

    public void setEmployeeIdentificationNumber(String employeeIdentificationNumber) {
        this.employeeIdentificationNumber = employeeIdentificationNumber;
    }

    public String getEmployeeNumber() {
        return this.employeeNumber;
    }

    public Employees employeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
        return this;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getEmployeePostalCode() {
        return this.employeePostalCode;
    }

    public Employees employeePostalCode(String employeePostalCode) {
        this.employeePostalCode = employeePostalCode;
        return this;
    }

    public void setEmployeePostalCode(String employeePostalCode) {
        this.employeePostalCode = employeePostalCode;
    }

    public String getEmployeeAddress() {
        return this.employeeAddress;
    }

    public Employees employeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
        return this;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeeAddressComplement() {
        return this.employeeAddressComplement;
    }

    public Employees employeeAddressComplement(String employeeAddressComplement) {
        this.employeeAddressComplement = employeeAddressComplement;
        return this;
    }

    public void setEmployeeAddressComplement(String employeeAddressComplement) {
        this.employeeAddressComplement = employeeAddressComplement;
    }

    public Integer getEmployeeAddressNumber() {
        return this.employeeAddressNumber;
    }

    public Employees employeeAddressNumber(Integer employeeAddressNumber) {
        this.employeeAddressNumber = employeeAddressNumber;
        return this;
    }

    public void setEmployeeAddressNumber(Integer employeeAddressNumber) {
        this.employeeAddressNumber = employeeAddressNumber;
    }

    public String getEmployeeAddressNeighborhood() {
        return this.employeeAddressNeighborhood;
    }

    public Employees employeeAddressNeighborhood(String employeeAddressNeighborhood) {
        this.employeeAddressNeighborhood = employeeAddressNeighborhood;
        return this;
    }

    public void setEmployeeAddressNeighborhood(String employeeAddressNeighborhood) {
        this.employeeAddressNeighborhood = employeeAddressNeighborhood;
    }

    public LocalDate getEmployeeBirthday() {
        return this.employeeBirthday;
    }

    public Employees employeeBirthday(LocalDate employeeBirthday) {
        this.employeeBirthday = employeeBirthday;
        return this;
    }

    public void setEmployeeBirthday(LocalDate employeeBirthday) {
        this.employeeBirthday = employeeBirthday;
    }

    public Set<EmployeeAttachments> getEmployeeAttachments() {
        return this.employeeAttachments;
    }

    public Employees employeeAttachments(Set<EmployeeAttachments> employeeAttachments) {
        this.setEmployeeAttachments(employeeAttachments);
        return this;
    }

    public Employees addEmployeeAttachments(EmployeeAttachments employeeAttachments) {
        this.employeeAttachments.add(employeeAttachments);
        employeeAttachments.setEmployees(this);
        return this;
    }

    public Employees removeEmployeeAttachments(EmployeeAttachments employeeAttachments) {
        this.employeeAttachments.remove(employeeAttachments);
        employeeAttachments.setEmployees(null);
        return this;
    }

    public void setEmployeeAttachments(Set<EmployeeAttachments> employeeAttachments) {
        if (this.employeeAttachments != null) {
            this.employeeAttachments.forEach(i -> i.setEmployees(null));
        }
        if (employeeAttachments != null) {
            employeeAttachments.forEach(i -> i.setEmployees(this));
        }
        this.employeeAttachments = employeeAttachments;
    }

    public Set<EmployeeComponentsData> getEmployeeComponentsData() {
        return this.employeeComponentsData;
    }

    public Employees employeeComponentsData(Set<EmployeeComponentsData> employeeComponentsData) {
        this.setEmployeeComponentsData(employeeComponentsData);
        return this;
    }

    public Employees addEmployeeComponentsData(EmployeeComponentsData employeeComponentsData) {
        this.employeeComponentsData.add(employeeComponentsData);
        employeeComponentsData.setEmployee(this);
        return this;
    }

    public Employees removeEmployeeComponentsData(EmployeeComponentsData employeeComponentsData) {
        this.employeeComponentsData.remove(employeeComponentsData);
        employeeComponentsData.setEmployee(null);
        return this;
    }

    public void setEmployeeComponentsData(Set<EmployeeComponentsData> employeeComponentsData) {
        if (this.employeeComponentsData != null) {
            this.employeeComponentsData.forEach(i -> i.setEmployee(null));
        }
        if (employeeComponentsData != null) {
            employeeComponentsData.forEach(i -> i.setEmployee(this));
        }
        this.employeeComponentsData = employeeComponentsData;
    }

    public Set<VehicleControls> getVehicleControls() {
        return this.vehicleControls;
    }

    public Employees vehicleControls(Set<VehicleControls> vehicleControls) {
        this.setVehicleControls(vehicleControls);
        return this;
    }

    public Employees addVehicleControls(VehicleControls vehicleControls) {
        this.vehicleControls.add(vehicleControls);
        vehicleControls.setEmployees(this);
        return this;
    }

    public Employees removeVehicleControls(VehicleControls vehicleControls) {
        this.vehicleControls.remove(vehicleControls);
        vehicleControls.setEmployees(null);
        return this;
    }

    public void setVehicleControls(Set<VehicleControls> vehicleControls) {
        if (this.vehicleControls != null) {
            this.vehicleControls.forEach(i -> i.setEmployees(null));
        }
        if (vehicleControls != null) {
            vehicleControls.forEach(i -> i.setEmployees(this));
        }
        this.vehicleControls = vehicleControls;
    }

    public Set<VehicleControlHistory> getVehicleControlHistories() {
        return this.vehicleControlHistories;
    }

    public Employees vehicleControlHistories(Set<VehicleControlHistory> vehicleControlHistories) {
        this.setVehicleControlHistories(vehicleControlHistories);
        return this;
    }

    public Employees addVehicleControlHistory(VehicleControlHistory vehicleControlHistory) {
        this.vehicleControlHistories.add(vehicleControlHistory);
        vehicleControlHistory.setEmployees(this);
        return this;
    }

    public Employees removeVehicleControlHistory(VehicleControlHistory vehicleControlHistory) {
        this.vehicleControlHistories.remove(vehicleControlHistory);
        vehicleControlHistory.setEmployees(null);
        return this;
    }

    public void setVehicleControlHistories(Set<VehicleControlHistory> vehicleControlHistories) {
        if (this.vehicleControlHistories != null) {
            this.vehicleControlHistories.forEach(i -> i.setEmployees(null));
        }
        if (vehicleControlHistories != null) {
            vehicleControlHistories.forEach(i -> i.setEmployees(this));
        }
        this.vehicleControlHistories = vehicleControlHistories;
    }

    public Set<Housing> getHousings() {
        return this.housings;
    }

    public Employees housings(Set<Housing> housings) {
        this.setHousings(housings);
        return this;
    }

    public Employees addHousing(Housing housing) {
        this.housings.add(housing);
        housing.setEmployees(this);
        return this;
    }

    public Employees removeHousing(Housing housing) {
        this.housings.remove(housing);
        housing.setEmployees(null);
        return this;
    }

    public void setHousings(Set<Housing> housings) {
        if (this.housings != null) {
            this.housings.forEach(i -> i.setEmployees(null));
        }
        if (housings != null) {
            housings.forEach(i -> i.setEmployees(this));
        }
        this.housings = housings;
    }

    public Companies getCompanies() {
        return this.companies;
    }

    public Employees companies(Companies companies) {
        this.setCompanies(companies);
        return this;
    }

    public void setCompanies(Companies companies) {
        this.companies = companies;
    }

    public Affiliates getAffiliates() {
        return this.affiliates;
    }

    public Employees affiliates(Affiliates affiliates) {
        this.setAffiliates(affiliates);
        return this;
    }

    public void setAffiliates(Affiliates affiliates) {
        this.affiliates = affiliates;
    }

    public Cities getCities() {
        return this.cities;
    }

    public Employees cities(Cities cities) {
        this.setCities(cities);
        return this;
    }

    public void setCities(Cities cities) {
        this.cities = cities;
    }

    public Positions getPositions() {
        return this.positions;
    }

    public Employees positions(Positions positions) {
        this.setPositions(positions);
        return this;
    }

    public void setPositions(Positions positions) {
        this.positions = positions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employees)) {
            return false;
        }
        return id != null && id.equals(((Employees) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employees{" +
            "id=" + getId() +
            ", active='" + getActive() + "'" +
            ", employeeFullName='" + getEmployeeFullName() + "'" +
            ", employeeEmail='" + getEmployeeEmail() + "'" +
            ", employeeIdentificationNumber='" + getEmployeeIdentificationNumber() + "'" +
            ", employeeNumber='" + getEmployeeNumber() + "'" +
            ", employeePostalCode='" + getEmployeePostalCode() + "'" +
            ", employeeAddress='" + getEmployeeAddress() + "'" +
            ", employeeAddressComplement='" + getEmployeeAddressComplement() + "'" +
            ", employeeAddressNumber=" + getEmployeeAddressNumber() +
            ", employeeAddressNeighborhood='" + getEmployeeAddressNeighborhood() + "'" +
            ", employeeBirthday='" + getEmployeeBirthday() + "'" +
            "}";
    }
}
