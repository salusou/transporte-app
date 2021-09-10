package com.genesisoft.transporte.service.dto;

import com.genesisoft.transporte.domain.enumeration.ScreenType;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.Status} entity.
 */
public class StatusDTO implements Serializable {

    private Long id;

    @NotNull
    private String statusName;

    @NotNull
    private ScreenType screenType;

    private String statusDescriptions;

    private AffiliatesDTO affiliates;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public ScreenType getScreenType() {
        return screenType;
    }

    public void setScreenType(ScreenType screenType) {
        this.screenType = screenType;
    }

    public String getStatusDescriptions() {
        return statusDescriptions;
    }

    public void setStatusDescriptions(String statusDescriptions) {
        this.statusDescriptions = statusDescriptions;
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
        if (!(o instanceof StatusDTO)) {
            return false;
        }

        StatusDTO statusDTO = (StatusDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, statusDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatusDTO{" +
            "id=" + getId() +
            ", statusName='" + getStatusName() + "'" +
            ", screenType='" + getScreenType() + "'" +
            ", statusDescriptions='" + getStatusDescriptions() + "'" +
            ", affiliates=" + getAffiliates() +
            "}";
    }
}
