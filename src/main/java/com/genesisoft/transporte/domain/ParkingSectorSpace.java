package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.genesisoft.transporte.domain.enumeration.ParkingSpaceStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Parking Sector Space\nThis class is the table Parking Sector Space shows the vacancies occupied.\n@author Samuel Souza
 */
@Entity
@Table(name = "parking_sector_space")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ParkingSectorSpace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Number of the vacancy occupied. This number must be in the range established in the related sector\n@type Integer
     */
    @NotNull
    @Column(name = "parking_number", nullable = false)
    private Integer parkingNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "parking_status", nullable = false)
    private ParkingSpaceStatus parkingStatus;

    @Column(name = "parking_entry_date")
    private LocalDate parkingEntryDate;

    @Column(name = "parking_departure_date")
    private LocalDate parkingDepartureDate;

    @Column(name = "parking_housing_item_id")
    private Long parkingHousingItemId;

    @OneToMany(mappedBy = "parkingSectorSpace")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "housing", "parkingSector", "parkingSectorSpace" }, allowSetters = true)
    private Set<HousingVehicleItem> housingVehicleItems = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "parkingSectorSpaces", "housingVehicleItems", "parking" }, allowSetters = true)
    private ParkingSector parkingSector;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParkingSectorSpace id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getParkingNumber() {
        return this.parkingNumber;
    }

    public ParkingSectorSpace parkingNumber(Integer parkingNumber) {
        this.parkingNumber = parkingNumber;
        return this;
    }

    public void setParkingNumber(Integer parkingNumber) {
        this.parkingNumber = parkingNumber;
    }

    public ParkingSpaceStatus getParkingStatus() {
        return this.parkingStatus;
    }

    public ParkingSectorSpace parkingStatus(ParkingSpaceStatus parkingStatus) {
        this.parkingStatus = parkingStatus;
        return this;
    }

    public void setParkingStatus(ParkingSpaceStatus parkingStatus) {
        this.parkingStatus = parkingStatus;
    }

    public LocalDate getParkingEntryDate() {
        return this.parkingEntryDate;
    }

    public ParkingSectorSpace parkingEntryDate(LocalDate parkingEntryDate) {
        this.parkingEntryDate = parkingEntryDate;
        return this;
    }

    public void setParkingEntryDate(LocalDate parkingEntryDate) {
        this.parkingEntryDate = parkingEntryDate;
    }

    public LocalDate getParkingDepartureDate() {
        return this.parkingDepartureDate;
    }

    public ParkingSectorSpace parkingDepartureDate(LocalDate parkingDepartureDate) {
        this.parkingDepartureDate = parkingDepartureDate;
        return this;
    }

    public void setParkingDepartureDate(LocalDate parkingDepartureDate) {
        this.parkingDepartureDate = parkingDepartureDate;
    }

    public Long getParkingHousingItemId() {
        return this.parkingHousingItemId;
    }

    public ParkingSectorSpace parkingHousingItemId(Long parkingHousingItemId) {
        this.parkingHousingItemId = parkingHousingItemId;
        return this;
    }

    public void setParkingHousingItemId(Long parkingHousingItemId) {
        this.parkingHousingItemId = parkingHousingItemId;
    }

    public Set<HousingVehicleItem> getHousingVehicleItems() {
        return this.housingVehicleItems;
    }

    public ParkingSectorSpace housingVehicleItems(Set<HousingVehicleItem> housingVehicleItems) {
        this.setHousingVehicleItems(housingVehicleItems);
        return this;
    }

    public ParkingSectorSpace addHousingVehicleItem(HousingVehicleItem housingVehicleItem) {
        this.housingVehicleItems.add(housingVehicleItem);
        housingVehicleItem.setParkingSectorSpace(this);
        return this;
    }

    public ParkingSectorSpace removeHousingVehicleItem(HousingVehicleItem housingVehicleItem) {
        this.housingVehicleItems.remove(housingVehicleItem);
        housingVehicleItem.setParkingSectorSpace(null);
        return this;
    }

    public void setHousingVehicleItems(Set<HousingVehicleItem> housingVehicleItems) {
        if (this.housingVehicleItems != null) {
            this.housingVehicleItems.forEach(i -> i.setParkingSectorSpace(null));
        }
        if (housingVehicleItems != null) {
            housingVehicleItems.forEach(i -> i.setParkingSectorSpace(this));
        }
        this.housingVehicleItems = housingVehicleItems;
    }

    public ParkingSector getParkingSector() {
        return this.parkingSector;
    }

    public ParkingSectorSpace parkingSector(ParkingSector parkingSector) {
        this.setParkingSector(parkingSector);
        return this;
    }

    public void setParkingSector(ParkingSector parkingSector) {
        this.parkingSector = parkingSector;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParkingSectorSpace)) {
            return false;
        }
        return id != null && id.equals(((ParkingSectorSpace) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParkingSectorSpace{" +
            "id=" + getId() +
            ", parkingNumber=" + getParkingNumber() +
            ", parkingStatus='" + getParkingStatus() + "'" +
            ", parkingEntryDate='" + getParkingEntryDate() + "'" +
            ", parkingDepartureDate='" + getParkingDepartureDate() + "'" +
            ", parkingHousingItemId=" + getParkingHousingItemId() +
            "}";
    }
}
