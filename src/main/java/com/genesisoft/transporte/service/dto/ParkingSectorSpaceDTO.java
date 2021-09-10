package com.genesisoft.transporte.service.dto;

import com.genesisoft.transporte.domain.enumeration.ParkingSpaceStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.ParkingSectorSpace} entity.
 */
@ApiModel(
    description = "Parking Sector Space\nThis class is the table Parking Sector Space shows the vacancies occupied.\n@author Samuel Souza"
)
public class ParkingSectorSpaceDTO implements Serializable {

    private Long id;

    /**
     * Number of the vacancy occupied. This number must be in the range established in the related sector\n@type Integer
     */
    @NotNull
    @ApiModelProperty(
        value = "Number of the vacancy occupied. This number must be in the range established in the related sector\n@type Integer",
        required = true
    )
    private Integer parkingNumber;

    @NotNull
    private ParkingSpaceStatus parkingStatus;

    private LocalDate parkingEntryDate;

    private LocalDate parkingDepartureDate;

    private Long parkingHousingItemId;

    private ParkingSectorDTO parkingSector;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getParkingNumber() {
        return parkingNumber;
    }

    public void setParkingNumber(Integer parkingNumber) {
        this.parkingNumber = parkingNumber;
    }

    public ParkingSpaceStatus getParkingStatus() {
        return parkingStatus;
    }

    public void setParkingStatus(ParkingSpaceStatus parkingStatus) {
        this.parkingStatus = parkingStatus;
    }

    public LocalDate getParkingEntryDate() {
        return parkingEntryDate;
    }

    public void setParkingEntryDate(LocalDate parkingEntryDate) {
        this.parkingEntryDate = parkingEntryDate;
    }

    public LocalDate getParkingDepartureDate() {
        return parkingDepartureDate;
    }

    public void setParkingDepartureDate(LocalDate parkingDepartureDate) {
        this.parkingDepartureDate = parkingDepartureDate;
    }

    public Long getParkingHousingItemId() {
        return parkingHousingItemId;
    }

    public void setParkingHousingItemId(Long parkingHousingItemId) {
        this.parkingHousingItemId = parkingHousingItemId;
    }

    public ParkingSectorDTO getParkingSector() {
        return parkingSector;
    }

    public void setParkingSector(ParkingSectorDTO parkingSector) {
        this.parkingSector = parkingSector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParkingSectorSpaceDTO)) {
            return false;
        }

        ParkingSectorSpaceDTO parkingSectorSpaceDTO = (ParkingSectorSpaceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, parkingSectorSpaceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParkingSectorSpaceDTO{" +
            "id=" + getId() +
            ", parkingNumber=" + getParkingNumber() +
            ", parkingStatus='" + getParkingStatus() + "'" +
            ", parkingEntryDate='" + getParkingEntryDate() + "'" +
            ", parkingDepartureDate='" + getParkingDepartureDate() + "'" +
            ", parkingHousingItemId=" + getParkingHousingItemId() +
            ", parkingSector=" + getParkingSector() +
            "}";
    }
}
