package com.genesisoft.transporte.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.VehicleControls} entity.
 */
public class VehicleControlsDTO implements Serializable {

    private Long id;

    private String vehicleControlAuthorizedOrder;

    private String vehicleControlRequest;

    private String vehicleControlSinister;

    @NotNull
    private LocalDate vehicleControlDate;

    private Float vehicleControlKm;

    @Size(max = 10)
    private String vehicleControlPlate;

    private Float vehicleControlAmount;

    private Float vehicleControlPrice;

    private LocalDate vehicleControlMaximumDeliveryDate;

    private LocalDate vehicleControlCollectionForecast;

    private LocalDate vehicleControlCollectionDeliveryForecast;

    private LocalDate vehicleControlDateCollected;

    private LocalDate vehicleControlDeliveryDate;

    private AffiliatesDTO affiliates;

    private CustomersDTO customers;

    private CustomersGroupsDTO customersGroups;

    private EmployeesDTO employees;

    private CitiesDTO origin;

    private CitiesDTO destination;

    private StatusDTO status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleControlAuthorizedOrder() {
        return vehicleControlAuthorizedOrder;
    }

    public void setVehicleControlAuthorizedOrder(String vehicleControlAuthorizedOrder) {
        this.vehicleControlAuthorizedOrder = vehicleControlAuthorizedOrder;
    }

    public String getVehicleControlRequest() {
        return vehicleControlRequest;
    }

    public void setVehicleControlRequest(String vehicleControlRequest) {
        this.vehicleControlRequest = vehicleControlRequest;
    }

    public String getVehicleControlSinister() {
        return vehicleControlSinister;
    }

    public void setVehicleControlSinister(String vehicleControlSinister) {
        this.vehicleControlSinister = vehicleControlSinister;
    }

    public LocalDate getVehicleControlDate() {
        return vehicleControlDate;
    }

    public void setVehicleControlDate(LocalDate vehicleControlDate) {
        this.vehicleControlDate = vehicleControlDate;
    }

    public Float getVehicleControlKm() {
        return vehicleControlKm;
    }

    public void setVehicleControlKm(Float vehicleControlKm) {
        this.vehicleControlKm = vehicleControlKm;
    }

    public String getVehicleControlPlate() {
        return vehicleControlPlate;
    }

    public void setVehicleControlPlate(String vehicleControlPlate) {
        this.vehicleControlPlate = vehicleControlPlate;
    }

    public Float getVehicleControlAmount() {
        return vehicleControlAmount;
    }

    public void setVehicleControlAmount(Float vehicleControlAmount) {
        this.vehicleControlAmount = vehicleControlAmount;
    }

    public Float getVehicleControlPrice() {
        return vehicleControlPrice;
    }

    public void setVehicleControlPrice(Float vehicleControlPrice) {
        this.vehicleControlPrice = vehicleControlPrice;
    }

    public LocalDate getVehicleControlMaximumDeliveryDate() {
        return vehicleControlMaximumDeliveryDate;
    }

    public void setVehicleControlMaximumDeliveryDate(LocalDate vehicleControlMaximumDeliveryDate) {
        this.vehicleControlMaximumDeliveryDate = vehicleControlMaximumDeliveryDate;
    }

    public LocalDate getVehicleControlCollectionForecast() {
        return vehicleControlCollectionForecast;
    }

    public void setVehicleControlCollectionForecast(LocalDate vehicleControlCollectionForecast) {
        this.vehicleControlCollectionForecast = vehicleControlCollectionForecast;
    }

    public LocalDate getVehicleControlCollectionDeliveryForecast() {
        return vehicleControlCollectionDeliveryForecast;
    }

    public void setVehicleControlCollectionDeliveryForecast(LocalDate vehicleControlCollectionDeliveryForecast) {
        this.vehicleControlCollectionDeliveryForecast = vehicleControlCollectionDeliveryForecast;
    }

    public LocalDate getVehicleControlDateCollected() {
        return vehicleControlDateCollected;
    }

    public void setVehicleControlDateCollected(LocalDate vehicleControlDateCollected) {
        this.vehicleControlDateCollected = vehicleControlDateCollected;
    }

    public LocalDate getVehicleControlDeliveryDate() {
        return vehicleControlDeliveryDate;
    }

    public void setVehicleControlDeliveryDate(LocalDate vehicleControlDeliveryDate) {
        this.vehicleControlDeliveryDate = vehicleControlDeliveryDate;
    }

    public AffiliatesDTO getAffiliates() {
        return affiliates;
    }

    public void setAffiliates(AffiliatesDTO affiliates) {
        this.affiliates = affiliates;
    }

    public CustomersDTO getCustomers() {
        return customers;
    }

    public void setCustomers(CustomersDTO customers) {
        this.customers = customers;
    }

    public CustomersGroupsDTO getCustomersGroups() {
        return customersGroups;
    }

    public void setCustomersGroups(CustomersGroupsDTO customersGroups) {
        this.customersGroups = customersGroups;
    }

    public EmployeesDTO getEmployees() {
        return employees;
    }

    public void setEmployees(EmployeesDTO employees) {
        this.employees = employees;
    }

    public CitiesDTO getOrigin() {
        return origin;
    }

    public void setOrigin(CitiesDTO origin) {
        this.origin = origin;
    }

    public CitiesDTO getDestination() {
        return destination;
    }

    public void setDestination(CitiesDTO destination) {
        this.destination = destination;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleControlsDTO)) {
            return false;
        }

        VehicleControlsDTO vehicleControlsDTO = (VehicleControlsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vehicleControlsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleControlsDTO{" +
            "id=" + getId() +
            ", vehicleControlAuthorizedOrder='" + getVehicleControlAuthorizedOrder() + "'" +
            ", vehicleControlRequest='" + getVehicleControlRequest() + "'" +
            ", vehicleControlSinister='" + getVehicleControlSinister() + "'" +
            ", vehicleControlDate='" + getVehicleControlDate() + "'" +
            ", vehicleControlKm=" + getVehicleControlKm() +
            ", vehicleControlPlate='" + getVehicleControlPlate() + "'" +
            ", vehicleControlAmount=" + getVehicleControlAmount() +
            ", vehicleControlPrice=" + getVehicleControlPrice() +
            ", vehicleControlMaximumDeliveryDate='" + getVehicleControlMaximumDeliveryDate() + "'" +
            ", vehicleControlCollectionForecast='" + getVehicleControlCollectionForecast() + "'" +
            ", vehicleControlCollectionDeliveryForecast='" + getVehicleControlCollectionDeliveryForecast() + "'" +
            ", vehicleControlDateCollected='" + getVehicleControlDateCollected() + "'" +
            ", vehicleControlDeliveryDate='" + getVehicleControlDeliveryDate() + "'" +
            ", affiliates=" + getAffiliates() +
            ", customers=" + getCustomers() +
            ", customersGroups=" + getCustomersGroups() +
            ", employees=" + getEmployees() +
            ", origin=" + getOrigin() +
            ", destination=" + getDestination() +
            ", status=" + getStatus() +
            "}";
    }
}
