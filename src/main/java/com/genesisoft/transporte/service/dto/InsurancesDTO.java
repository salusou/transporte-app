package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.Insurances} entity.
 */
@ApiModel(description = "Insurances to clients\n@author Samuel Souza")
public class InsurancesDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal insurancesPercent;

    private AffiliatesDTO affiliates;

    private StateProvincesDTO toStateProvince;

    private StateProvincesDTO forStateProvince;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getInsurancesPercent() {
        return insurancesPercent;
    }

    public void setInsurancesPercent(BigDecimal insurancesPercent) {
        this.insurancesPercent = insurancesPercent;
    }

    public AffiliatesDTO getAffiliates() {
        return affiliates;
    }

    public void setAffiliates(AffiliatesDTO affiliates) {
        this.affiliates = affiliates;
    }

    public StateProvincesDTO getToStateProvince() {
        return toStateProvince;
    }

    public void setToStateProvince(StateProvincesDTO toStateProvince) {
        this.toStateProvince = toStateProvince;
    }

    public StateProvincesDTO getForStateProvince() {
        return forStateProvince;
    }

    public void setForStateProvince(StateProvincesDTO forStateProvince) {
        this.forStateProvince = forStateProvince;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InsurancesDTO)) {
            return false;
        }

        InsurancesDTO insurancesDTO = (InsurancesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, insurancesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InsurancesDTO{" +
            "id=" + getId() +
            ", insurancesPercent=" + getInsurancesPercent() +
            ", affiliates=" + getAffiliates() +
            ", toStateProvince=" + getToStateProvince() +
            ", forStateProvince=" + getForStateProvince() +
            "}";
    }
}
