package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.AdministrativeFeesRanges} entity.
 */
@ApiModel(description = "Range Fees\nThis class is to records fees.\n@author Samuel Souza")
public class AdministrativeFeesRangesDTO implements Serializable {

    private Long id;

    @NotNull
    private Float minRange;

    @NotNull
    private Float maxRange;

    @NotNull
    private Float aliquot;

    private AffiliatesDTO affiliates;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMinRange() {
        return minRange;
    }

    public void setMinRange(Float minRange) {
        this.minRange = minRange;
    }

    public Float getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(Float maxRange) {
        this.maxRange = maxRange;
    }

    public Float getAliquot() {
        return aliquot;
    }

    public void setAliquot(Float aliquot) {
        this.aliquot = aliquot;
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
        if (!(o instanceof AdministrativeFeesRangesDTO)) {
            return false;
        }

        AdministrativeFeesRangesDTO administrativeFeesRangesDTO = (AdministrativeFeesRangesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, administrativeFeesRangesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdministrativeFeesRangesDTO{" +
            "id=" + getId() +
            ", minRange=" + getMinRange() +
            ", maxRange=" + getMaxRange() +
            ", aliquot=" + getAliquot() +
            ", affiliates=" + getAffiliates() +
            "}";
    }
}
