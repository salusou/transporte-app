package com.genesisoft.transporte.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.Housing} entity.
 */
public class HousingDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate housingDate;

    @NotNull
    private ZonedDateTime housingEntranceDate;

    private ZonedDateTime housingExit;

    private Integer housingReceiptNumber;

    @NotNull
    private Float housingDailyPrice;

    @Size(max = 500)
    private String housingDescription;

    private AffiliatesDTO affiliates;

    private StatusDTO status;

    private CustomersDTO customers;

    private EmployeesDTO employees;

    private ParkingDTO parking;

    private CostCenterDTO costCenter;

    private SuppliersDTO suppliers;

    private CitiesDTO cities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getHousingDate() {
        return housingDate;
    }

    public void setHousingDate(LocalDate housingDate) {
        this.housingDate = housingDate;
    }

    public ZonedDateTime getHousingEntranceDate() {
        return housingEntranceDate;
    }

    public void setHousingEntranceDate(ZonedDateTime housingEntranceDate) {
        this.housingEntranceDate = housingEntranceDate;
    }

    public ZonedDateTime getHousingExit() {
        return housingExit;
    }

    public void setHousingExit(ZonedDateTime housingExit) {
        this.housingExit = housingExit;
    }

    public Integer getHousingReceiptNumber() {
        return housingReceiptNumber;
    }

    public void setHousingReceiptNumber(Integer housingReceiptNumber) {
        this.housingReceiptNumber = housingReceiptNumber;
    }

    public Float getHousingDailyPrice() {
        return housingDailyPrice;
    }

    public void setHousingDailyPrice(Float housingDailyPrice) {
        this.housingDailyPrice = housingDailyPrice;
    }

    public String getHousingDescription() {
        return housingDescription;
    }

    public void setHousingDescription(String housingDescription) {
        this.housingDescription = housingDescription;
    }

    public AffiliatesDTO getAffiliates() {
        return affiliates;
    }

    public void setAffiliates(AffiliatesDTO affiliates) {
        this.affiliates = affiliates;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    public CustomersDTO getCustomers() {
        return customers;
    }

    public void setCustomers(CustomersDTO customers) {
        this.customers = customers;
    }

    public EmployeesDTO getEmployees() {
        return employees;
    }

    public void setEmployees(EmployeesDTO employees) {
        this.employees = employees;
    }

    public ParkingDTO getParking() {
        return parking;
    }

    public void setParking(ParkingDTO parking) {
        this.parking = parking;
    }

    public CostCenterDTO getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenterDTO costCenter) {
        this.costCenter = costCenter;
    }

    public SuppliersDTO getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(SuppliersDTO suppliers) {
        this.suppliers = suppliers;
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
        if (!(o instanceof HousingDTO)) {
            return false;
        }

        HousingDTO housingDTO = (HousingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, housingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HousingDTO{" +
            "id=" + getId() +
            ", housingDate='" + getHousingDate() + "'" +
            ", housingEntranceDate='" + getHousingEntranceDate() + "'" +
            ", housingExit='" + getHousingExit() + "'" +
            ", housingReceiptNumber=" + getHousingReceiptNumber() +
            ", housingDailyPrice=" + getHousingDailyPrice() +
            ", housingDescription='" + getHousingDescription() + "'" +
            ", affiliates=" + getAffiliates() +
            ", status=" + getStatus() +
            ", customers=" + getCustomers() +
            ", employees=" + getEmployees() +
            ", parking=" + getParking() +
            ", costCenter=" + getCostCenter() +
            ", suppliers=" + getSuppliers() +
            ", cities=" + getCities() +
            "}";
    }
}
