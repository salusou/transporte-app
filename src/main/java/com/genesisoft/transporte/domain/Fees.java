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
 * Fees for all systems\nThis class are the customers group.\n@author Samuel Souza
 */
@Entity
@Table(name = "fees")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Fees implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "fee_date", nullable = false)
    private LocalDate feeDate;

    @NotNull
    @Column(name = "fee_driver_commission", nullable = false)
    private Float feeDriverCommission;

    @NotNull
    @Column(name = "fee_financial_cost", nullable = false)
    private Float feeFinancialCost;

    @NotNull
    @Column(name = "fee_taxes", nullable = false)
    private Float feeTaxes;

    @Column(name = "fee_descriptions")
    private String feeDescriptions;

    @OneToMany(mappedBy = "fees")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehicleControls", "fees" }, allowSetters = true)
    private Set<VehicleControlBilling> vehicleControlBillings = new HashSet<>();

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

    public Fees id(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getFeeDate() {
        return this.feeDate;
    }

    public Fees feeDate(LocalDate feeDate) {
        this.feeDate = feeDate;
        return this;
    }

    public void setFeeDate(LocalDate feeDate) {
        this.feeDate = feeDate;
    }

    public Float getFeeDriverCommission() {
        return this.feeDriverCommission;
    }

    public Fees feeDriverCommission(Float feeDriverCommission) {
        this.feeDriverCommission = feeDriverCommission;
        return this;
    }

    public void setFeeDriverCommission(Float feeDriverCommission) {
        this.feeDriverCommission = feeDriverCommission;
    }

    public Float getFeeFinancialCost() {
        return this.feeFinancialCost;
    }

    public Fees feeFinancialCost(Float feeFinancialCost) {
        this.feeFinancialCost = feeFinancialCost;
        return this;
    }

    public void setFeeFinancialCost(Float feeFinancialCost) {
        this.feeFinancialCost = feeFinancialCost;
    }

    public Float getFeeTaxes() {
        return this.feeTaxes;
    }

    public Fees feeTaxes(Float feeTaxes) {
        this.feeTaxes = feeTaxes;
        return this;
    }

    public void setFeeTaxes(Float feeTaxes) {
        this.feeTaxes = feeTaxes;
    }

    public String getFeeDescriptions() {
        return this.feeDescriptions;
    }

    public Fees feeDescriptions(String feeDescriptions) {
        this.feeDescriptions = feeDescriptions;
        return this;
    }

    public void setFeeDescriptions(String feeDescriptions) {
        this.feeDescriptions = feeDescriptions;
    }

    public Set<VehicleControlBilling> getVehicleControlBillings() {
        return this.vehicleControlBillings;
    }

    public Fees vehicleControlBillings(Set<VehicleControlBilling> vehicleControlBillings) {
        this.setVehicleControlBillings(vehicleControlBillings);
        return this;
    }

    public Fees addVehicleControlBilling(VehicleControlBilling vehicleControlBilling) {
        this.vehicleControlBillings.add(vehicleControlBilling);
        vehicleControlBilling.setFees(this);
        return this;
    }

    public Fees removeVehicleControlBilling(VehicleControlBilling vehicleControlBilling) {
        this.vehicleControlBillings.remove(vehicleControlBilling);
        vehicleControlBilling.setFees(null);
        return this;
    }

    public void setVehicleControlBillings(Set<VehicleControlBilling> vehicleControlBillings) {
        if (this.vehicleControlBillings != null) {
            this.vehicleControlBillings.forEach(i -> i.setFees(null));
        }
        if (vehicleControlBillings != null) {
            vehicleControlBillings.forEach(i -> i.setFees(this));
        }
        this.vehicleControlBillings = vehicleControlBillings;
    }

    public Affiliates getAffiliates() {
        return this.affiliates;
    }

    public Fees affiliates(Affiliates affiliates) {
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
        if (!(o instanceof Fees)) {
            return false;
        }
        return id != null && id.equals(((Fees) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fees{" +
            "id=" + getId() +
            ", feeDate='" + getFeeDate() + "'" +
            ", feeDriverCommission=" + getFeeDriverCommission() +
            ", feeFinancialCost=" + getFeeFinancialCost() +
            ", feeTaxes=" + getFeeTaxes() +
            ", feeDescriptions='" + getFeeDescriptions() + "'" +
            "}";
    }
}
