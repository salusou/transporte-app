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
 * Parking\nThis class is the table parking.\n@author Samuel Souza
 */
@Entity
@Table(name = "parking")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Parking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Release the parking to run.\n@type Boolean
     */
    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    /**
     * Parking Name\n@type String
     */
    @NotNull
    @Column(name = "parking_name", nullable = false)
    private String parkingName;

    /**
     * Parking Trade Name\n@type String
     */
    @Column(name = "parking_trade_name")
    private String parkingTradeName;

    /**
     * Customer Number is like a CNPJ, EIN, CPF, IDENTIFIED\n@type String
     */
    @Column(name = "parking_number")
    private String parkingNumber;

    /**
     * Postal code of address the matrix company.\n@type String
     */
    @Size(max = 9)
    @Column(name = "parking_postal_code", length = 9)
    private String parkingPostalCode;

    /**
     * Branch Address.\n@type String
     */
    @Column(name = "parking_address")
    private String parkingAddress;

    /**
     * Parking Address Complement.\n@type String
     */
    @Column(name = "parking_address_complement")
    private String parkingAddressComplement;

    /**
     * Number of address.\n@type Integer
     */
    @Column(name = "parking_address_number")
    private Integer parkingAddressNumber;

    /**
     * Neighborhood, District.\n@type String
     */
    @Column(name = "parking_address_neighborhood")
    private String parkingAddressNeighborhood;

    /**
     * Parking Contact Telephone\n@type String
     */
    @Column(name = "parking_telephone")
    private String parkingTelephone;

    /**
     * Parking Contact Email\n@type String
     */
    @Column(name = "parking_email")
    private String parkingEmail;

    /**
     * Parking Contact Name\n@type String
     */
    @Column(name = "parking_contact_name")
    private String parkingContactName;

    @OneToMany(mappedBy = "parking")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parkingSectorSpaces", "housingVehicleItems", "parking" }, allowSetters = true)
    private Set<ParkingSector> parkingSectors = new HashSet<>();

    @OneToMany(mappedBy = "parking")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "housingVehicleItems", "affiliates", "status", "customers", "employees", "parking", "costCenter", "suppliers", "cities" },
        allowSetters = true
    )
    private Set<Housing> housings = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "insurances",
            "positions",
            "costCenters",
            "administrativeFeesRanges",
            "customersGroups",
            "fees",
            "customers",
            "statusAttachments",
            "statuses",
            "parkings",
            "suppliers",
            "employees",
            "vehicleControls",
            "housings",
            "stateProvinces",
            "cities",
            "companies",
        },
        allowSetters = true
    )
    private Affiliates affiliates;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "companies",
            "affiliates",
            "customers",
            "parkings",
            "suppliers",
            "employees",
            "originVehicleControls",
            "destinationVehicleControls",
            "vehicleLocationStatuses",
            "originVehicleControlExpenses",
            "destinationVehicleControlExpenses",
            "housings",
            "stateProvinces",
        },
        allowSetters = true
    )
    private Cities cities;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Parking id(Long id) {
        this.id = id;
        return this;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Parking active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getParkingName() {
        return this.parkingName;
    }

    public Parking parkingName(String parkingName) {
        this.parkingName = parkingName;
        return this;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getParkingTradeName() {
        return this.parkingTradeName;
    }

    public Parking parkingTradeName(String parkingTradeName) {
        this.parkingTradeName = parkingTradeName;
        return this;
    }

    public void setParkingTradeName(String parkingTradeName) {
        this.parkingTradeName = parkingTradeName;
    }

    public String getParkingNumber() {
        return this.parkingNumber;
    }

    public Parking parkingNumber(String parkingNumber) {
        this.parkingNumber = parkingNumber;
        return this;
    }

    public void setParkingNumber(String parkingNumber) {
        this.parkingNumber = parkingNumber;
    }

    public String getParkingPostalCode() {
        return this.parkingPostalCode;
    }

    public Parking parkingPostalCode(String parkingPostalCode) {
        this.parkingPostalCode = parkingPostalCode;
        return this;
    }

    public void setParkingPostalCode(String parkingPostalCode) {
        this.parkingPostalCode = parkingPostalCode;
    }

    public String getParkingAddress() {
        return this.parkingAddress;
    }

    public Parking parkingAddress(String parkingAddress) {
        this.parkingAddress = parkingAddress;
        return this;
    }

    public void setParkingAddress(String parkingAddress) {
        this.parkingAddress = parkingAddress;
    }

    public String getParkingAddressComplement() {
        return this.parkingAddressComplement;
    }

    public Parking parkingAddressComplement(String parkingAddressComplement) {
        this.parkingAddressComplement = parkingAddressComplement;
        return this;
    }

    public void setParkingAddressComplement(String parkingAddressComplement) {
        this.parkingAddressComplement = parkingAddressComplement;
    }

    public Integer getParkingAddressNumber() {
        return this.parkingAddressNumber;
    }

    public Parking parkingAddressNumber(Integer parkingAddressNumber) {
        this.parkingAddressNumber = parkingAddressNumber;
        return this;
    }

    public void setParkingAddressNumber(Integer parkingAddressNumber) {
        this.parkingAddressNumber = parkingAddressNumber;
    }

    public String getParkingAddressNeighborhood() {
        return this.parkingAddressNeighborhood;
    }

    public Parking parkingAddressNeighborhood(String parkingAddressNeighborhood) {
        this.parkingAddressNeighborhood = parkingAddressNeighborhood;
        return this;
    }

    public void setParkingAddressNeighborhood(String parkingAddressNeighborhood) {
        this.parkingAddressNeighborhood = parkingAddressNeighborhood;
    }

    public String getParkingTelephone() {
        return this.parkingTelephone;
    }

    public Parking parkingTelephone(String parkingTelephone) {
        this.parkingTelephone = parkingTelephone;
        return this;
    }

    public void setParkingTelephone(String parkingTelephone) {
        this.parkingTelephone = parkingTelephone;
    }

    public String getParkingEmail() {
        return this.parkingEmail;
    }

    public Parking parkingEmail(String parkingEmail) {
        this.parkingEmail = parkingEmail;
        return this;
    }

    public void setParkingEmail(String parkingEmail) {
        this.parkingEmail = parkingEmail;
    }

    public String getParkingContactName() {
        return this.parkingContactName;
    }

    public Parking parkingContactName(String parkingContactName) {
        this.parkingContactName = parkingContactName;
        return this;
    }

    public void setParkingContactName(String parkingContactName) {
        this.parkingContactName = parkingContactName;
    }

    public Set<ParkingSector> getParkingSectors() {
        return this.parkingSectors;
    }

    public Parking parkingSectors(Set<ParkingSector> parkingSectors) {
        this.setParkingSectors(parkingSectors);
        return this;
    }

    public Parking addParkingSector(ParkingSector parkingSector) {
        this.parkingSectors.add(parkingSector);
        parkingSector.setParking(this);
        return this;
    }

    public Parking removeParkingSector(ParkingSector parkingSector) {
        this.parkingSectors.remove(parkingSector);
        parkingSector.setParking(null);
        return this;
    }

    public void setParkingSectors(Set<ParkingSector> parkingSectors) {
        if (this.parkingSectors != null) {
            this.parkingSectors.forEach(i -> i.setParking(null));
        }
        if (parkingSectors != null) {
            parkingSectors.forEach(i -> i.setParking(this));
        }
        this.parkingSectors = parkingSectors;
    }

    public Set<Housing> getHousings() {
        return this.housings;
    }

    public Parking housings(Set<Housing> housings) {
        this.setHousings(housings);
        return this;
    }

    public Parking addHousing(Housing housing) {
        this.housings.add(housing);
        housing.setParking(this);
        return this;
    }

    public Parking removeHousing(Housing housing) {
        this.housings.remove(housing);
        housing.setParking(null);
        return this;
    }

    public void setHousings(Set<Housing> housings) {
        if (this.housings != null) {
            this.housings.forEach(i -> i.setParking(null));
        }
        if (housings != null) {
            housings.forEach(i -> i.setParking(this));
        }
        this.housings = housings;
    }

    public Affiliates getAffiliates() {
        return this.affiliates;
    }

    public Parking affiliates(Affiliates affiliates) {
        this.setAffiliates(affiliates);
        return this;
    }

    public void setAffiliates(Affiliates affiliates) {
        this.affiliates = affiliates;
    }

    public Cities getCities() {
        return this.cities;
    }

    public Parking cities(Cities cities) {
        this.setCities(cities);
        return this;
    }

    public void setCities(Cities cities) {
        this.cities = cities;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parking)) {
            return false;
        }
        return id != null && id.equals(((Parking) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Parking{" +
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
            "}";
    }
}
