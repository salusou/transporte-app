package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.Positions} entity.
 */
@ApiModel(description = "PositionsName\nThis class is used to filter the cities.\n@author Samuel Souza")
public class PositionsDTO implements Serializable {

    private Long id;

    @NotNull
    private String positionName;

    private AffiliatesDTO affiliates;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
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
        if (!(o instanceof PositionsDTO)) {
            return false;
        }

        PositionsDTO positionsDTO = (PositionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, positionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PositionsDTO{" +
            "id=" + getId() +
            ", positionName='" + getPositionName() + "'" +
            ", affiliates=" + getAffiliates() +
            "}";
    }
}
