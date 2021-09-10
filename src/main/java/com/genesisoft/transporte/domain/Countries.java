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
 * This table is the list of all countries of World.\nThis class is used to filter country of the company.\n@author Samuel Souza
 */
@Entity
@Table(name = "countries")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Countries implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Country Name example Brazil\n@type String
     */
    @NotNull
    @Column(name = "country_name", nullable = false)
    private String countryName;

    /**
     * This is a abbrev about country. Example: br\n@type String
     */
    @NotNull
    @Column(name = "iso_2", nullable = false)
    private String iso2;

    /**
     * To phone call. Example of Brazil: 55\n@type Integer
     */
    @Column(name = "code_area")
    private Integer codeArea;

    /**
     * Language of this Country: Example: pt_br\n@type String
     */
    @Size(max = 6)
    @Column(name = "language", length = 6)
    private String language;

    @OneToMany(mappedBy = "countries")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "cities", "companies", "affiliates", "toInsurances", "forInsurances", "countries" },
        allowSetters = true
    )
    private Set<StateProvinces> stateProvinces = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Countries id(Long id) {
        this.id = id;
        return this;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public Countries countryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getIso2() {
        return this.iso2;
    }

    public Countries iso2(String iso2) {
        this.iso2 = iso2;
        return this;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public Integer getCodeArea() {
        return this.codeArea;
    }

    public Countries codeArea(Integer codeArea) {
        this.codeArea = codeArea;
        return this;
    }

    public void setCodeArea(Integer codeArea) {
        this.codeArea = codeArea;
    }

    public String getLanguage() {
        return this.language;
    }

    public Countries language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Set<StateProvinces> getStateProvinces() {
        return this.stateProvinces;
    }

    public Countries stateProvinces(Set<StateProvinces> stateProvinces) {
        this.setStateProvinces(stateProvinces);
        return this;
    }

    public Countries addStateProvinces(StateProvinces stateProvinces) {
        this.stateProvinces.add(stateProvinces);
        stateProvinces.setCountries(this);
        return this;
    }

    public Countries removeStateProvinces(StateProvinces stateProvinces) {
        this.stateProvinces.remove(stateProvinces);
        stateProvinces.setCountries(null);
        return this;
    }

    public void setStateProvinces(Set<StateProvinces> stateProvinces) {
        if (this.stateProvinces != null) {
            this.stateProvinces.forEach(i -> i.setCountries(null));
        }
        if (stateProvinces != null) {
            stateProvinces.forEach(i -> i.setCountries(this));
        }
        this.stateProvinces = stateProvinces;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Countries)) {
            return false;
        }
        return id != null && id.equals(((Countries) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Countries{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            ", iso2='" + getIso2() + "'" +
            ", codeArea=" + getCodeArea() +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
