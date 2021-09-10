package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.ParkingSector} entity.
 */
@ApiModel(description = "Parking Sector\nThis class is the table Parking Sector to show the space in parking.\n@author Samuel Souza")
public class ParkingSectorDTO implements Serializable {

    private Long id;

    /**
     * Active the sector to run\n@type Boolean
     */
    @NotNull
    @ApiModelProperty(value = "Active the sector to run\n@type Boolean", required = true)
    private Boolean active;

    /**
     * Sector Name\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "Sector Name\n@type String", required = true)
    private String sectorName;

    /**
     * Parking Space the amount vacation available.\n@type Integer
     */
    @NotNull
    @ApiModelProperty(value = "Parking Space the amount vacation available.\n@type Integer", required = true)
    private Integer parkingSpace;

    /**
     * Number to range initial of the vacancies\n@type Integer
     */
    @ApiModelProperty(value = "Number to range initial of the vacancies\n@type Integer")
    private Integer parkingNumbersBegin;

    /**
     * Number to range end of the vacancies\n@type Integer
     */
    @ApiModelProperty(value = "Number to range end of the vacancies\n@type Integer")
    private Integer parkingNumbersFinal;

    private ParkingDTO parking;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public Integer getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(Integer parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public Integer getParkingNumbersBegin() {
        return parkingNumbersBegin;
    }

    public void setParkingNumbersBegin(Integer parkingNumbersBegin) {
        this.parkingNumbersBegin = parkingNumbersBegin;
    }

    public Integer getParkingNumbersFinal() {
        return parkingNumbersFinal;
    }

    public void setParkingNumbersFinal(Integer parkingNumbersFinal) {
        this.parkingNumbersFinal = parkingNumbersFinal;
    }

    public ParkingDTO getParking() {
        return parking;
    }

    public void setParking(ParkingDTO parking) {
        this.parking = parking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParkingSectorDTO)) {
            return false;
        }

        ParkingSectorDTO parkingSectorDTO = (ParkingSectorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, parkingSectorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParkingSectorDTO{" +
            "id=" + getId() +
            ", active='" + getActive() + "'" +
            ", sectorName='" + getSectorName() + "'" +
            ", parkingSpace=" + getParkingSpace() +
            ", parkingNumbersBegin=" + getParkingNumbersBegin() +
            ", parkingNumbersFinal=" + getParkingNumbersFinal() +
            ", parking=" + getParking() +
            "}";
    }
}
