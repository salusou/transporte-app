package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Range Fees\nThis class is to records fees.\n@author Samuel Souza
 */
@Entity
@Table(name = "administrative_fees_ranges")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdministrativeFeesRanges implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "min_range", nullable = false)
    private Float minRange;

    @NotNull
    @Column(name = "max_range", nullable = false)
    private Float maxRange;

    @NotNull
    @Column(name = "aliquot", nullable = false)
    private Float aliquot;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdministrativeFeesRanges id(Long id) {
        this.id = id;
        return this;
    }

    public Float getMinRange() {
        return this.minRange;
    }

    public AdministrativeFeesRanges minRange(Float minRange) {
        this.minRange = minRange;
        return this;
    }

    public void setMinRange(Float minRange) {
        this.minRange = minRange;
    }

    public Float getMaxRange() {
        return this.maxRange;
    }

    public AdministrativeFeesRanges maxRange(Float maxRange) {
        this.maxRange = maxRange;
        return this;
    }

    public void setMaxRange(Float maxRange) {
        this.maxRange = maxRange;
    }

    public Float getAliquot() {
        return this.aliquot;
    }

    public AdministrativeFeesRanges aliquot(Float aliquot) {
        this.aliquot = aliquot;
        return this;
    }

    public void setAliquot(Float aliquot) {
        this.aliquot = aliquot;
    }

    public Affiliates getAffiliates() {
        return this.affiliates;
    }

    public AdministrativeFeesRanges affiliates(Affiliates affiliates) {
        this.setAffiliates(affiliates);
        return this;
    }

    public void setAffiliates(Affiliates affiliates) {
        this.affiliates = affiliates;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdministrativeFeesRanges)) {
            return false;
        }
        return id != null && id.equals(((AdministrativeFeesRanges) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdministrativeFeesRanges{" +
            "id=" + getId() +
            ", minRange=" + getMinRange() +
            ", maxRange=" + getMaxRange() +
            ", aliquot=" + getAliquot() +
            "}";
    }
}
