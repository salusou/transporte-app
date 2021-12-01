package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CompaniesXUsers.
 */
@Entity
@Table(name = "companiesxusers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompaniesXUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "affiliates", "employees", "cities", "stateProvinces" }, allowSetters = true)
    private Companies companies;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompaniesXUsers id(Long id) {
        this.id = id;
        return this;
    }

    public Companies getCompanies() {
        return this.companies;
    }

    public CompaniesXUsers companies(Companies companies) {
        this.setCompanies(companies);
        return this;
    }

    public void setCompanies(Companies companies) {
        this.companies = companies;
    }

    public User getUser() {
        return this.user;
    }

    public CompaniesXUsers user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompaniesXUsers)) {
            return false;
        }
        return id != null && id.equals(((CompaniesXUsers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompaniesXUsers{" +
            "id=" + getId() +
            "}";
    }
}
