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
 * State Providence\nThis class is used to filter the providence of city.\n@author Samuel Souza
 */
@Entity
@Table(name = "state_provinces")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StateProvinces implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Name of the State Example: California\n@type String
     */
    @NotNull
    @Column(name = "state_name", nullable = false)
    private String stateName;

    /**
     * Abbreviation of state example: CA\n@type String
     */
    @NotNull
    @Column(name = "abbreviation", nullable = false)
    private String abbreviation;

    @OneToMany(mappedBy = "stateProvinces")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Cities> cities = new HashSet<>();

    @OneToMany(mappedBy = "stateProvinces")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "affiliates", "employees", "cities", "stateProvinces" }, allowSetters = true)
    private Set<Companies> companies = new HashSet<>();

    @OneToMany(mappedBy = "stateProvinces")
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

    @OneToMany(mappedBy = "toStateProvince")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "affiliates", "toStateProvince", "forStateProvince" }, allowSetters = true)
    private Set<Insurances> toInsurances = new HashSet<>();

    @OneToMany(mappedBy = "forStateProvince")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "affiliates", "toStateProvince", "forStateProvince" }, allowSetters = true)
    private Set<Insurances> forInsurances = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "stateProvinces" }, allowSetters = true)
    private Countries countries;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StateProvinces id(Long id) {
        this.id = id;
        return this;
    }

    public String getStateName() {
        return this.stateName;
    }

    public StateProvinces stateName(String stateName) {
        this.stateName = stateName;
        return this;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public StateProvinces abbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
        return this;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Set<Cities> getCities() {
        return this.cities;
    }

    public StateProvinces cities(Set<Cities> cities) {
        this.setCities(cities);
        return this;
    }

    public StateProvinces addCities(Cities cities) {
        this.cities.add(cities);
        cities.setStateProvinces(this);
        return this;
    }

    public StateProvinces removeCities(Cities cities) {
        this.cities.remove(cities);
        cities.setStateProvinces(null);
        return this;
    }

    public void setCities(Set<Cities> cities) {
        if (this.cities != null) {
            this.cities.forEach(i -> i.setStateProvinces(null));
        }
        if (cities != null) {
            cities.forEach(i -> i.setStateProvinces(this));
        }
        this.cities = cities;
    }

    public Set<Companies> getCompanies() {
        return this.companies;
    }

    public StateProvinces companies(Set<Companies> companies) {
        this.setCompanies(companies);
        return this;
    }

    public StateProvinces addCompanies(Companies companies) {
        this.companies.add(companies);
        companies.setStateProvinces(this);
        return this;
    }

    public StateProvinces removeCompanies(Companies companies) {
        this.companies.remove(companies);
        companies.setStateProvinces(null);
        return this;
    }

    public void setCompanies(Set<Companies> companies) {
        if (this.companies != null) {
            this.companies.forEach(i -> i.setStateProvinces(null));
        }
        if (companies != null) {
            companies.forEach(i -> i.setStateProvinces(this));
        }
        this.companies = companies;
    }

    public Set<Affiliates> getAffiliates() {
        return this.affiliates;
    }

    public StateProvinces affiliates(Set<Affiliates> affiliates) {
        this.setAffiliates(affiliates);
        return this;
    }

    public StateProvinces addAffiliates(Affiliates affiliates) {
        this.affiliates.add(affiliates);
        affiliates.setStateProvinces(this);
        return this;
    }

    public StateProvinces removeAffiliates(Affiliates affiliates) {
        this.affiliates.remove(affiliates);
        affiliates.setStateProvinces(null);
        return this;
    }

    public void setAffiliates(Set<Affiliates> affiliates) {
        if (this.affiliates != null) {
            this.affiliates.forEach(i -> i.setStateProvinces(null));
        }
        if (affiliates != null) {
            affiliates.forEach(i -> i.setStateProvinces(this));
        }
        this.affiliates = affiliates;
    }

    public Set<Insurances> getToInsurances() {
        return this.toInsurances;
    }

    public StateProvinces toInsurances(Set<Insurances> insurances) {
        this.setToInsurances(insurances);
        return this;
    }

    public StateProvinces addToInsurances(Insurances insurances) {
        this.toInsurances.add(insurances);
        insurances.setToStateProvince(this);
        return this;
    }

    public StateProvinces removeToInsurances(Insurances insurances) {
        this.toInsurances.remove(insurances);
        insurances.setToStateProvince(null);
        return this;
    }

    public void setToInsurances(Set<Insurances> insurances) {
        if (this.toInsurances != null) {
            this.toInsurances.forEach(i -> i.setToStateProvince(null));
        }
        if (insurances != null) {
            insurances.forEach(i -> i.setToStateProvince(this));
        }
        this.toInsurances = insurances;
    }

    public Set<Insurances> getForInsurances() {
        return this.forInsurances;
    }

    public StateProvinces forInsurances(Set<Insurances> insurances) {
        this.setForInsurances(insurances);
        return this;
    }

    public StateProvinces addForInsurances(Insurances insurances) {
        this.forInsurances.add(insurances);
        insurances.setForStateProvince(this);
        return this;
    }

    public StateProvinces removeForInsurances(Insurances insurances) {
        this.forInsurances.remove(insurances);
        insurances.setForStateProvince(null);
        return this;
    }

    public void setForInsurances(Set<Insurances> insurances) {
        if (this.forInsurances != null) {
            this.forInsurances.forEach(i -> i.setForStateProvince(null));
        }
        if (insurances != null) {
            insurances.forEach(i -> i.setForStateProvince(this));
        }
        this.forInsurances = insurances;
    }

    public Countries getCountries() {
        return this.countries;
    }

    public StateProvinces countries(Countries countries) {
        this.setCountries(countries);
        return this;
    }

    public void setCountries(Countries countries) {
        this.countries = countries;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StateProvinces)) {
            return false;
        }
        return id != null && id.equals(((StateProvinces) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StateProvinces{" +
            "id=" + getId() +
            ", stateName='" + getStateName() + "'" +
            ", abbreviation='" + getAbbreviation() + "'" +
            "}";
    }
}
