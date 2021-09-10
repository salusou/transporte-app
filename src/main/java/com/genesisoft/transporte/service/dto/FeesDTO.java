package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.Fees} entity.
 */
@ApiModel(description = "Fees for all systems\nThis class are the customers group.\n@author Samuel Souza")
public class FeesDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate feeDate;

    @NotNull
    private Float feeDriverCommission;

    @NotNull
    private Float feeFinancialCost;

    @NotNull
    private Float feeTaxes;

    private String feeDescriptions;

    private AffiliatesDTO affiliates;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(LocalDate feeDate) {
        this.feeDate = feeDate;
    }

    public Float getFeeDriverCommission() {
        return feeDriverCommission;
    }

    public void setFeeDriverCommission(Float feeDriverCommission) {
        this.feeDriverCommission = feeDriverCommission;
    }

    public Float getFeeFinancialCost() {
        return feeFinancialCost;
    }

    public void setFeeFinancialCost(Float feeFinancialCost) {
        this.feeFinancialCost = feeFinancialCost;
    }

    public Float getFeeTaxes() {
        return feeTaxes;
    }

    public void setFeeTaxes(Float feeTaxes) {
        this.feeTaxes = feeTaxes;
    }

    public String getFeeDescriptions() {
        return feeDescriptions;
    }

    public void setFeeDescriptions(String feeDescriptions) {
        this.feeDescriptions = feeDescriptions;
    }

    public AffiliatesDTO getAffiliates() {
        return affiliates;
    }

    public void setAffiliates(AffiliatesDTO affiliates) {
        this.affiliates = affiliates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeesDTO)) {
            return false;
        }

        FeesDTO feesDTO = (FeesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, feesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeesDTO{" +
            "id=" + getId() +
            ", feeDate='" + getFeeDate() + "'" +
            ", feeDriverCommission=" + getFeeDriverCommission() +
            ", feeFinancialCost=" + getFeeFinancialCost() +
            ", feeTaxes=" + getFeeTaxes() +
            ", feeDescriptions='" + getFeeDescriptions() + "'" +
            ", affiliates=" + getAffiliates() +
            "}";
    }
}
