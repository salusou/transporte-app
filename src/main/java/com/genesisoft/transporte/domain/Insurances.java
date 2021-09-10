package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Insurances to clients\n@author Samuel Souza
 */
@Entity
@Table(name = "insurances")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Insurances implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "insurances_percent", precision = 21, scale = 2, nullable = false)
    private BigDecimal insurancesPercent;

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
        value = { "cities", "companies", "affiliates", "toInsurances", "forInsurances", "countries" },
        allowSetters = true
    )
    private StateProvinces toStateProvince;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "cities", "companies", "affiliates", "toInsurances", "forInsurances", "countries" },
        allowSetters = true
    )
    private StateProvinces forStateProvince;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Insurances id(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getInsurancesPercent() {
        return this.insurancesPercent;
    }

    public Insurances insurancesPercent(BigDecimal insurancesPercent) {
        this.insurancesPercent = insurancesPercent;
        return this;
    }

    public void setInsurancesPercent(BigDecimal insurancesPercent) {
        this.insurancesPercent = insurancesPercent;
    }

    public Affiliates getAffiliates() {
        return this.affiliates;
    }

    public Insurances affiliates(Affiliates affiliates) {
        this.setAffiliates(affiliates);
        return this;
    }

    public void setAffiliates(Affiliates affiliates) {
        this.affiliates = affiliates;
    }

    public StateProvinces getToStateProvince() {
        return this.toStateProvince;
    }

    public Insurances toStateProvince(StateProvinces stateProvinces) {
        this.setToStateProvince(stateProvinces);
        return this;
    }

    public void setToStateProvince(StateProvinces stateProvinces) {
        this.toStateProvince = stateProvinces;
    }

    public StateProvinces getForStateProvince() {
        return this.forStateProvince;
    }

    public Insurances forStateProvince(StateProvinces stateProvinces) {
        this.setForStateProvince(stateProvinces);
        return this;
    }

    public void setForStateProvince(StateProvinces stateProvinces) {
        this.forStateProvince = stateProvinces;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Insurances)) {
            return false;
        }
        return id != null && id.equals(((Insurances) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Insurances{" +
            "id=" + getId() +
            ", insurancesPercent=" + getInsurancesPercent() +
            "}";
    }
}
