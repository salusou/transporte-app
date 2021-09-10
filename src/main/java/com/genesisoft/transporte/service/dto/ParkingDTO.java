package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.Parking} entity.
 */
@ApiModel(description = "Parking\nThis class is the table parking.\n@author Samuel Souza")
public class ParkingDTO implements Serializable {

    private Long id;

    /**
     * Release the parking to run.\n@type Boolean
     */
    @NotNull
    @ApiModelProperty(value = "Release the parking to run.\n@type Boolean", required = true)
    private Boolean active;

    /**
     * Parking Name\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "Parking Name\n@type String", required = true)
    private String parkingName;

    /**
     * Parking Trade Name\n@type String
     */
    @ApiModelProperty(value = "Parking Trade Name\n@type String")
    private String parkingTradeName;

    /**
     * Customer Number is like a CNPJ, EIN, CPF, IDENTIFIED\n@type String
     */
    @ApiModelProperty(value = "Customer Number is like a CNPJ, EIN, CPF, IDENTIFIED\n@type String")
    private String parkingNumber;

    /**
     * Postal code of address the matrix company.\n@type String
     */
    @Size(max = 9)
    @ApiModelProperty(value = "Postal code of address the matrix company.\n@type String")
    private String parkingPostalCode;

    /**
     * Branch Address.\n@type String
     */
    @ApiModelProperty(value = "Branch Address.\n@type String")
    private String parkingAddress;

    /**
     * Parking Address Complement.\n@type String
     */
    @ApiModelProperty(value = "Parking Address Complement.\n@type String")
    private String parkingAddressComplement;

    /**
     * Number of address.\n@type Integer
     */
    @ApiModelProperty(value = "Number of address.\n@type Integer")
    private Integer parkingAddressNumber;

    /**
     * Neighborhood, District.\n@type String
     */
    @ApiModelProperty(value = "Neighborhood, District.\n@type String")
    private String parkingAddressNeighborhood;

    /**
     * Parking Contact Telephone\n@type String
     */
    @ApiModelProperty(value = "Parking Contact Telephone\n@type String")
    private String parkingTelephone;

    /**
     * Parking Contact Email\n@type String
     */
    @ApiModelProperty(value = "Parking Contact Email\n@type String")
    private String parkingEmail;

    /**
     * Parking Contact Name\n@type String
     */
    @ApiModelProperty(value = "Parking Contact Name\n@type String")
    private String parkingContactName;

    private AffiliatesDTO affiliates;

    private CitiesDTO cities;

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

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getParkingTradeName() {
        return parkingTradeName;
    }

    public void setParkingTradeName(String parkingTradeName) {
        this.parkingTradeName = parkingTradeName;
    }

    public String getParkingNumber() {
        return parkingNumber;
    }

    public void setParkingNumber(String parkingNumber) {
        this.parkingNumber = parkingNumber;
    }

    public String getParkingPostalCode() {
        return parkingPostalCode;
    }

    public void setParkingPostalCode(String parkingPostalCode) {
        this.parkingPostalCode = parkingPostalCode;
    }

    public String getParkingAddress() {
        return parkingAddress;
    }

    public void setParkingAddress(String parkingAddress) {
        this.parkingAddress = parkingAddress;
    }

    public String getParkingAddressComplement() {
        return parkingAddressComplement;
    }

    public void setParkingAddressComplement(String parkingAddressComplement) {
        this.parkingAddressComplement = parkingAddressComplement;
    }

    public Integer getParkingAddressNumber() {
        return parkingAddressNumber;
    }

    public void setParkingAddressNumber(Integer parkingAddressNumber) {
        this.parkingAddressNumber = parkingAddressNumber;
    }

    public String getParkingAddressNeighborhood() {
        return parkingAddressNeighborhood;
    }

    public void setParkingAddressNeighborhood(String parkingAddressNeighborhood) {
        this.parkingAddressNeighborhood = parkingAddressNeighborhood;
    }

    public String getParkingTelephone() {
        return parkingTelephone;
    }

    public void setParkingTelephone(String parkingTelephone) {
        this.parkingTelephone = parkingTelephone;
    }

    public String getParkingEmail() {
        return parkingEmail;
    }

    public void setParkingEmail(String parkingEmail) {
        this.parkingEmail = parkingEmail;
    }

    public String getParkingContactName() {
        return parkingContactName;
    }

    public void setParkingContactName(String parkingContactName) {
        this.parkingContactName = parkingContactName;
    }

    public AffiliatesDTO getAffiliates() {
        return affiliates;
    }

    public void setAffiliates(AffiliatesDTO affiliates) {
        this.affiliates = affiliates;
    }

    public CitiesDTO getCities() {
        return cities;
    }

    public void setCities(CitiesDTO cities) {
        this.cities = cities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParkingDTO)) {
            return false;
        }

        ParkingDTO parkingDTO = (ParkingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, parkingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParkingDTO{" +
            "id=" + getId() +
            ", active='" + getActive() + "'" +
            ", parkingName='" + getParkingName() + "'" +
            ", parkingTradeName='" + getParkingTradeName() + "'" +
            ", parkingNumber='" + getParkingNumber() + "'" +
            ", parkingPostalCode='" + getParkingPostalCode() + "'" +
            ", parkingAddress='" + getParkingAddress() + "'" +
            ", parkingAddressComplement='" + getParkingAddressComplement() + "'" +
            ", parkingAddressNumber=" + getParkingAddressNumber() +
            ", parkingAddressNeighborhood='" + getParkingAddressNeighborhood() + "'" +
            ", parkingTelephone='" + getParkingTelephone() + "'" +
            ", parkingEmail='" + getParkingEmail() + "'" +
            ", parkingContactName='" + getParkingContactName() + "'" +
            ", affiliates=" + getAffiliates() +
            ", cities=" + getCities() +
            "}";
    }
}
