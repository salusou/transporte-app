package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.StateProvinces} entity.
 */
@ApiModel(description = "State Providence\nThis class is used to filter the providence of city.\n@author Samuel Souza")
public class StateProvincesDTO implements Serializable {

    private Long id;

    /**
     * Name of the State Example: California\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "Name of the State Example: California\n@type String", required = true)
    private String stateName;

    /**
     * Abbreviation of state example: CA\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "Abbreviation of state example: CA\n@type String", required = true)
    private String abbreviation;

    private CountriesDTO countries;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public CountriesDTO getCountries() {
        return countries;
    }

    public void setCountries(CountriesDTO countries) {
        this.countries = countries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StateProvincesDTO)) {
            return false;
        }

        StateProvincesDTO stateProvincesDTO = (StateProvincesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, stateProvincesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StateProvincesDTO{" +
            "id=" + getId() +
            ", stateName='" + getStateName() + "'" +
            ", abbreviation='" + getAbbreviation() + "'" +
            ", countries=" + getCountries() +
            "}";
    }
}
