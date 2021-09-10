package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Parking Sector\nThis class is the table Parking Sector to show the space in parking.\n@author Samuel Souza
 */
@Entity
@Table(name = "parking_sector")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ParkingSector implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Active the sector to run\n@type Boolean
     */
    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    /**
     * Sector Name\n@type String
     */
    @NotNull
    @Column(name = "sector_name", nullable = false)
    private String sectorName;

    /**
     * Parking Space the amount vacation available.\n@type Integer
     */
    @NotNull
    @Column(name = "parking_space", nullable = false)
    private Integer parkingSpace;

    /**
     * Number to range initial of the vacancies\n@type Integer
     */
    @Column(name = "parking_numbers_begin")
    private Integer parkingNumbersBegin;

    /**
     * Number to range end of the vacancies\n@type Integer
     */
    @Column(name = "parking_numbers_final")
    private Integer parkingNumbersFinal;

    @OneToMany(mappedBy = "parkingSector")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "housingVehicleItems", "parkingSector" }, allowSetters = true)
    private Set<ParkingSectorSpace> parkingSectorSpaces = new HashSet<>();

    @OneToMany(mappedBy = "parkingSector")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "housing", "parkingSector", "parkingSectorSpace" }, allowSetters = true)
    private Set<HousingVehicleItem> housingVehicleItems = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "parkingSectors", "housings", "affiliates", "cities" }, allowSetters = true)
    private Parking parking;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParkingSector id(Long id) {
        this.id = id;
        return this;
    }

    public Boolean getActive() {
        return this.active;
    }

    public ParkingSector active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getSectorName() {
        return this.sectorName;
    }

    public ParkingSector sectorName(String sectorName) {
        this.sectorName = sectorName;
        return this;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public Integer getParkingSpace() {
        return this.parkingSpace;
    }

    public ParkingSector parkingSpace(Integer parkingSpace) {
        this.parkingSpace = parkingSpace;
        return this;
    }

    public void setParkingSpace(Integer parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public Integer getParkingNumbersBegin() {
        return this.parkingNumbersBegin;
    }

    public ParkingSector parkingNumbersBegin(Integer parkingNumbersBegin) {
        this.parkingNumbersBegin = parkingNumbersBegin;
        return this;
    }

    public void setParkingNumbersBegin(Integer parkingNumbersBegin) {
        this.parkingNumbersBegin = parkingNumbersBegin;
    }

    public Integer getParkingNumbersFinal() {
        return this.parkingNumbersFinal;
    }

    public ParkingSector parkingNumbersFinal(Integer parkingNumbersFinal) {
        this.parkingNumbersFinal = parkingNumbersFinal;
        return this;
    }

    public void setParkingNumbersFinal(Integer parkingNumbersFinal) {
        this.parkingNumbersFinal = parkingNumbersFinal;
    }

    public Set<ParkingSectorSpace> getParkingSectorSpaces() {
        return this.parkingSectorSpaces;
    }

    public ParkingSector parkingSectorSpaces(Set<ParkingSectorSpace> parkingSectorSpaces) {
        this.setParkingSectorSpaces(parkingSectorSpaces);
        return this;
    }

    public ParkingSector addParkingSectorSpace(ParkingSectorSpace parkingSectorSpace) {
        this.parkingSectorSpaces.add(parkingSectorSpace);
        parkingSectorSpace.setParkingSector(this);
        return this;
    }

    public ParkingSector removeParkingSectorSpace(ParkingSectorSpace parkingSectorSpace) {
        this.parkingSectorSpaces.remove(parkingSectorSpace);
        parkingSectorSpace.setParkingSector(null);
        return this;
    }

    public void setParkingSectorSpaces(Set<ParkingSectorSpace> parkingSectorSpaces) {
        if (this.parkingSectorSpaces != null) {
            this.parkingSectorSpaces.forEach(i -> i.setParkingSector(null));
        }
        if (parkingSectorSpaces != null) {
            parkingSectorSpaces.forEach(i -> i.setParkingSector(this));
        }
        this.parkingSectorSpaces = parkingSectorSpaces;
    }

    public Set<HousingVehicleItem> getHousingVehicleItems() {
        return this.housingVehicleItems;
    }

    public ParkingSector housingVehicleItems(Set<HousingVehicleItem> housingVehicleItems) {
        this.setHousingVehicleItems(housingVehicleItems);
        return this;
    }

    public ParkingSector addHousingVehicleItem(HousingVehicleItem housingVehicleItem) {
        this.housingVehicleItems.add(housingVehicleItem);
        housingVehicleItem.setParkingSector(this);
        return this;
    }

    public ParkingSector removeHousingVehicleItem(HousingVehicleItem housingVehicleItem) {
        this.housingVehicleItems.remove(housingVehicleItem);
        housingVehicleItem.setParkingSector(null);
        return this;
    }

    public void setHousingVehicleItems(Set<HousingVehicleItem> housingVehicleItems) {
        if (this.housingVehicleItems != null) {
            this.housingVehicleItems.forEach(i -> i.setParkingSector(null));
        }
        if (housingVehicleItems != null) {
            housingVehicleItems.forEach(i -> i.setParkingSector(this));
        }
        this.housingVehicleItems = housingVehicleItems;
    }

    public Parking getParking() {
        return this.parking;
    }

    public ParkingSector parking(Parking parking) {
        this.setParking(parking);
        return this;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParkingSector)) {
            return false;
        }
        return id != null && id.equals(((ParkingSector) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParkingSector{" +
            "id=" + getId() +
            ", active='" + getActive() + "'" +
            ", sectorName='" + getSectorName() + "'" +
            ", parkingSpace=" + getParkingSpace() +
            ", parkingNumbersBegin=" + getParkingNumbersBegin() +
            ", parkingNumbersFinal=" + getParkingNumbersFinal() +
            "}";
    }
}
