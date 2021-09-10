package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.CostCenter} entity.
 */
@ApiModel(description = "Cost Center\nThis class is to separate the cost center.\n@author Samuel Souza")
public class CostCenterDTO implements Serializable {

    private Long id;

    @NotNull
    private String costCenterName;

    private AffiliatesDTO affiliates;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
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
        if (!(o instanceof CostCenterDTO)) {
            return false;
        }

        CostCenterDTO costCenterDTO = (CostCenterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, costCenterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CostCenterDTO{" +
            "id=" + getId() +
            ", costCenterName='" + getCostCenterName() + "'" +
            ", affiliates=" + getAffiliates() +
            "}";
    }
}
