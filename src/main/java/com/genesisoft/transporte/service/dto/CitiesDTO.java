package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.Cities} entity.
 */
@ApiModel(description = "Cities of the State Province.\n@author Samuel Souza")
public class CitiesDTO implements Serializable {

    private Long id;

    /**
     * City Name. Example: São Paulo\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "City Name. Example: São Paulo\n@type String", required = true)
    private String cityName;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private StateProvincesDTO stateProvinces;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public StateProvincesDTO getStateProvinces() {
        return stateProvinces;
    }

    public void setStateProvinces(StateProvincesDTO stateProvinces) {
        this.stateProvinces = stateProvinces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CitiesDTO)) {
            return false;
        }

        CitiesDTO citiesDTO = (CitiesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, citiesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CitiesDTO{" +
            "id=" + getId() +
            ", cityName='" + getCityName() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", stateProvinces=" + getStateProvinces() +
            "}";
    }
}
