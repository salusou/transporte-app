package com.genesisoft.transporte.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.genesisoft.transporte.domain.Cities} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.CitiesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CitiesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cityName;

    private BigDecimalFilter latitude;

    private BigDecimalFilter longitude;

    private LongFilter companiesId;

    private LongFilter affiliatesId;

    private LongFilter customersId;

    private LongFilter parkingId;

    private LongFilter suppliersId;

    private LongFilter employeesId;

    private LongFilter originVehicleControlsId;

    private LongFilter destinationVehicleControlsId;

    private LongFilter vehicleLocationStatusId;

    private LongFilter originVehicleControlExpensesId;

    private LongFilter destinationVehicleControlExpensesId;

    private LongFilter housingId;

    private LongFilter stateProvincesId;

    public CitiesCriteria() {}

    public CitiesCriteria(CitiesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cityName = other.cityName == null ? null : other.cityName.copy();
        this.latitude = other.latitude == null ? null : other.latitude.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.companiesId = other.companiesId == null ? null : other.companiesId.copy();
        this.affiliatesId = other.affiliatesId == null ? null : other.affiliatesId.copy();
        this.customersId = other.customersId == null ? null : other.customersId.copy();
        this.parkingId = other.parkingId == null ? null : other.parkingId.copy();
        this.suppliersId = other.suppliersId == null ? null : other.suppliersId.copy();
        this.employeesId = other.employeesId == null ? null : other.employeesId.copy();
        this.originVehicleControlsId = other.originVehicleControlsId == null ? null : other.originVehicleControlsId.copy();
        this.destinationVehicleControlsId = other.destinationVehicleControlsId == null ? null : other.destinationVehicleControlsId.copy();
        this.vehicleLocationStatusId = other.vehicleLocationStatusId == null ? null : other.vehicleLocationStatusId.copy();
        this.originVehicleControlExpensesId =
            other.originVehicleControlExpensesId == null ? null : other.originVehicleControlExpensesId.copy();
        this.destinationVehicleControlExpensesId =
            other.destinationVehicleControlExpensesId == null ? null : other.destinationVehicleControlExpensesId.copy();
        this.housingId = other.housingId == null ? null : other.housingId.copy();
        this.stateProvincesId = other.stateProvincesId == null ? null : other.stateProvincesId.copy();
    }

    @Override
    public CitiesCriteria copy() {
        return new CitiesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCityName() {
        return cityName;
    }

    public StringFilter cityName() {
        if (cityName == null) {
            cityName = new StringFilter();
        }
        return cityName;
    }

    public void setCityName(StringFilter cityName) {
        this.cityName = cityName;
    }

    public BigDecimalFilter getLatitude() {
        return latitude;
    }

    public BigDecimalFilter latitude() {
        if (latitude == null) {
            latitude = new BigDecimalFilter();
        }
        return latitude;
    }

    public void setLatitude(BigDecimalFilter latitude) {
        this.latitude = latitude;
    }

    public BigDecimalFilter getLongitude() {
        return longitude;
    }

    public BigDecimalFilter longitude() {
        if (longitude == null) {
            longitude = new BigDecimalFilter();
        }
        return longitude;
    }

    public void setLongitude(BigDecimalFilter longitude) {
        this.longitude = longitude;
    }

    public LongFilter getCompaniesId() {
        return companiesId;
    }

    public LongFilter companiesId() {
        if (companiesId == null) {
            companiesId = new LongFilter();
        }
        return companiesId;
    }

    public void setCompaniesId(LongFilter companiesId) {
        this.companiesId = companiesId;
    }

    public LongFilter getAffiliatesId() {
        return affiliatesId;
    }

    public LongFilter affiliatesId() {
        if (affiliatesId == null) {
            affiliatesId = new LongFilter();
        }
        return affiliatesId;
    }

    public void setAffiliatesId(LongFilter affiliatesId) {
        this.affiliatesId = affiliatesId;
    }

    public LongFilter getCustomersId() {
        return customersId;
    }

    public LongFilter customersId() {
        if (customersId == null) {
            customersId = new LongFilter();
        }
        return customersId;
    }

    public void setCustomersId(LongFilter customersId) {
        this.customersId = customersId;
    }

    public LongFilter getParkingId() {
        return parkingId;
    }

    public LongFilter parkingId() {
        if (parkingId == null) {
            parkingId = new LongFilter();
        }
        return parkingId;
    }

    public void setParkingId(LongFilter parkingId) {
        this.parkingId = parkingId;
    }

    public LongFilter getSuppliersId() {
        return suppliersId;
    }

    public LongFilter suppliersId() {
        if (suppliersId == null) {
            suppliersId = new LongFilter();
        }
        return suppliersId;
    }

    public void setSuppliersId(LongFilter suppliersId) {
        this.suppliersId = suppliersId;
    }

    public LongFilter getEmployeesId() {
        return employeesId;
    }

    public LongFilter employeesId() {
        if (employeesId == null) {
            employeesId = new LongFilter();
        }
        return employeesId;
    }

    public void setEmployeesId(LongFilter employeesId) {
        this.employeesId = employeesId;
    }

    public LongFilter getOriginVehicleControlsId() {
        return originVehicleControlsId;
    }

    public LongFilter originVehicleControlsId() {
        if (originVehicleControlsId == null) {
            originVehicleControlsId = new LongFilter();
        }
        return originVehicleControlsId;
    }

    public void setOriginVehicleControlsId(LongFilter originVehicleControlsId) {
        this.originVehicleControlsId = originVehicleControlsId;
    }

    public LongFilter getDestinationVehicleControlsId() {
        return destinationVehicleControlsId;
    }

    public LongFilter destinationVehicleControlsId() {
        if (destinationVehicleControlsId == null) {
            destinationVehicleControlsId = new LongFilter();
        }
        return destinationVehicleControlsId;
    }

    public void setDestinationVehicleControlsId(LongFilter destinationVehicleControlsId) {
        this.destinationVehicleControlsId = destinationVehicleControlsId;
    }

    public LongFilter getVehicleLocationStatusId() {
        return vehicleLocationStatusId;
    }

    public LongFilter vehicleLocationStatusId() {
        if (vehicleLocationStatusId == null) {
            vehicleLocationStatusId = new LongFilter();
        }
        return vehicleLocationStatusId;
    }

    public void setVehicleLocationStatusId(LongFilter vehicleLocationStatusId) {
        this.vehicleLocationStatusId = vehicleLocationStatusId;
    }

    public LongFilter getOriginVehicleControlExpensesId() {
        return originVehicleControlExpensesId;
    }

    public LongFilter originVehicleControlExpensesId() {
        if (originVehicleControlExpensesId == null) {
            originVehicleControlExpensesId = new LongFilter();
        }
        return originVehicleControlExpensesId;
    }

    public void setOriginVehicleControlExpensesId(LongFilter originVehicleControlExpensesId) {
        this.originVehicleControlExpensesId = originVehicleControlExpensesId;
    }

    public LongFilter getDestinationVehicleControlExpensesId() {
        return destinationVehicleControlExpensesId;
    }

    public LongFilter destinationVehicleControlExpensesId() {
        if (destinationVehicleControlExpensesId == null) {
            destinationVehicleControlExpensesId = new LongFilter();
        }
        return destinationVehicleControlExpensesId;
    }

    public void setDestinationVehicleControlExpensesId(LongFilter destinationVehicleControlExpensesId) {
        this.destinationVehicleControlExpensesId = destinationVehicleControlExpensesId;
    }

    public LongFilter getHousingId() {
        return housingId;
    }

    public LongFilter housingId() {
        if (housingId == null) {
            housingId = new LongFilter();
        }
        return housingId;
    }

    public void setHousingId(LongFilter housingId) {
        this.housingId = housingId;
    }

    public LongFilter getStateProvincesId() {
        return stateProvincesId;
    }

    public LongFilter stateProvincesId() {
        if (stateProvincesId == null) {
            stateProvincesId = new LongFilter();
        }
        return stateProvincesId;
    }

    public void setStateProvincesId(LongFilter stateProvincesId) {
        this.stateProvincesId = stateProvincesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CitiesCriteria that = (CitiesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cityName, that.cityName) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(companiesId, that.companiesId) &&
            Objects.equals(affiliatesId, that.affiliatesId) &&
            Objects.equals(customersId, that.customersId) &&
            Objects.equals(parkingId, that.parkingId) &&
            Objects.equals(suppliersId, that.suppliersId) &&
            Objects.equals(employeesId, that.employeesId) &&
            Objects.equals(originVehicleControlsId, that.originVehicleControlsId) &&
            Objects.equals(destinationVehicleControlsId, that.destinationVehicleControlsId) &&
            Objects.equals(vehicleLocationStatusId, that.vehicleLocationStatusId) &&
            Objects.equals(originVehicleControlExpensesId, that.originVehicleControlExpensesId) &&
            Objects.equals(destinationVehicleControlExpensesId, that.destinationVehicleControlExpensesId) &&
            Objects.equals(housingId, that.housingId) &&
            Objects.equals(stateProvincesId, that.stateProvincesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            cityName,
            latitude,
            longitude,
            companiesId,
            affiliatesId,
            customersId,
            parkingId,
            suppliersId,
            employeesId,
            originVehicleControlsId,
            destinationVehicleControlsId,
            vehicleLocationStatusId,
            originVehicleControlExpensesId,
            destinationVehicleControlExpensesId,
            housingId,
            stateProvincesId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CitiesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (cityName != null ? "cityName=" + cityName + ", " : "") +
            (latitude != null ? "latitude=" + latitude + ", " : "") +
            (longitude != null ? "longitude=" + longitude + ", " : "") +
            (companiesId != null ? "companiesId=" + companiesId + ", " : "") +
            (affiliatesId != null ? "affiliatesId=" + affiliatesId + ", " : "") +
            (customersId != null ? "customersId=" + customersId + ", " : "") +
            (parkingId != null ? "parkingId=" + parkingId + ", " : "") +
            (suppliersId != null ? "suppliersId=" + suppliersId + ", " : "") +
            (employeesId != null ? "employeesId=" + employeesId + ", " : "") +
            (originVehicleControlsId != null ? "originVehicleControlsId=" + originVehicleControlsId + ", " : "") +
            (destinationVehicleControlsId != null ? "destinationVehicleControlsId=" + destinationVehicleControlsId + ", " : "") +
            (vehicleLocationStatusId != null ? "vehicleLocationStatusId=" + vehicleLocationStatusId + ", " : "") +
            (originVehicleControlExpensesId != null ? "originVehicleControlExpensesId=" + originVehicleControlExpensesId + ", " : "") +
            (destinationVehicleControlExpensesId != null ? "destinationVehicleControlExpensesId=" + destinationVehicleControlExpensesId + ", " : "") +
            (housingId != null ? "housingId=" + housingId + ", " : "") +
            (stateProvincesId != null ? "stateProvincesId=" + stateProvincesId + ", " : "") +
            "}";
    }
}
