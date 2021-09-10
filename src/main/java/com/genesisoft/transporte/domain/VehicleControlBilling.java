package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VehicleControlBilling.
 */
@Entity
@Table(name = "vehicle_control_billing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VehicleControlBilling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "vehicle_control_billing_date", nullable = false)
    private LocalDate vehicleControlBillingDate;

    @Column(name = "vehicle_control_billing_expiration_date")
    private LocalDate vehicleControlBillingExpirationDate;

    @Column(name = "vehicle_control_billing_payment_date")
    private LocalDate vehicleControlBillingPaymentDate;

    @Column(name = "vehicle_control_billing_seller_commission")
    private Boolean vehicleControlBillingSellerCommission;

    @Column(name = "vehicle_control_billing_driver_commission")
    private Boolean vehicleControlBillingDriverCommission;

    @NotNull
    @Column(name = "vehicle_control_billing_amount", nullable = false)
    private Integer vehicleControlBillingAmount;

    @NotNull
    @Column(name = "vehicle_control_billing_total_value", nullable = false)
    private Float vehicleControlBillingTotalValue;

    @Column(name = "vehicle_control_billing_insurance_discount")
    private Float vehicleControlBillingInsuranceDiscount;

    @Column(name = "vehicle_control_billing_customer_bank")
    private String vehicleControlBillingCustomerBank;

    @Column(name = "vehicle_control_billing_anticipate")
    private Boolean vehicleControlBillingAnticipate;

    @Column(name = "vehicle_control_billing_manifest")
    private String vehicleControlBillingManifest;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "vehicleLocationStatuses",
            "vehicleControlHistories",
            "vehicleControlBillings",
            "vehicleControlItems",
            "vehicleControlAttachments",
            "vehicleControlExpenses",
            "affiliates",
            "customers",
            "customersGroups",
            "employees",
            "origin",
            "destination",
            "status",
        },
        allowSetters = true
    )
    private VehicleControls vehicleControls;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "vehicleControlBillings", "affiliates" }, allowSetters = true)
    private Fees fees;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleControlBilling id(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getVehicleControlBillingDate() {
        return this.vehicleControlBillingDate;
    }

    public VehicleControlBilling vehicleControlBillingDate(LocalDate vehicleControlBillingDate) {
        this.vehicleControlBillingDate = vehicleControlBillingDate;
        return this;
    }

    public void setVehicleControlBillingDate(LocalDate vehicleControlBillingDate) {
        this.vehicleControlBillingDate = vehicleControlBillingDate;
    }

    public LocalDate getVehicleControlBillingExpirationDate() {
        return this.vehicleControlBillingExpirationDate;
    }

    public VehicleControlBilling vehicleControlBillingExpirationDate(LocalDate vehicleControlBillingExpirationDate) {
        this.vehicleControlBillingExpirationDate = vehicleControlBillingExpirationDate;
        return this;
    }

    public void setVehicleControlBillingExpirationDate(LocalDate vehicleControlBillingExpirationDate) {
        this.vehicleControlBillingExpirationDate = vehicleControlBillingExpirationDate;
    }

    public LocalDate getVehicleControlBillingPaymentDate() {
        return this.vehicleControlBillingPaymentDate;
    }

    public VehicleControlBilling vehicleControlBillingPaymentDate(LocalDate vehicleControlBillingPaymentDate) {
        this.vehicleControlBillingPaymentDate = vehicleControlBillingPaymentDate;
        return this;
    }

    public void setVehicleControlBillingPaymentDate(LocalDate vehicleControlBillingPaymentDate) {
        this.vehicleControlBillingPaymentDate = vehicleControlBillingPaymentDate;
    }

    public Boolean getVehicleControlBillingSellerCommission() {
        return this.vehicleControlBillingSellerCommission;
    }

    public VehicleControlBilling vehicleControlBillingSellerCommission(Boolean vehicleControlBillingSellerCommission) {
        this.vehicleControlBillingSellerCommission = vehicleControlBillingSellerCommission;
        return this;
    }

    public void setVehicleControlBillingSellerCommission(Boolean vehicleControlBillingSellerCommission) {
        this.vehicleControlBillingSellerCommission = vehicleControlBillingSellerCommission;
    }

    public Boolean getVehicleControlBillingDriverCommission() {
        return this.vehicleControlBillingDriverCommission;
    }

    public VehicleControlBilling vehicleControlBillingDriverCommission(Boolean vehicleControlBillingDriverCommission) {
        this.vehicleControlBillingDriverCommission = vehicleControlBillingDriverCommission;
        return this;
    }

    public void setVehicleControlBillingDriverCommission(Boolean vehicleControlBillingDriverCommission) {
        this.vehicleControlBillingDriverCommission = vehicleControlBillingDriverCommission;
    }

    public Integer getVehicleControlBillingAmount() {
        return this.vehicleControlBillingAmount;
    }

    public VehicleControlBilling vehicleControlBillingAmount(Integer vehicleControlBillingAmount) {
        this.vehicleControlBillingAmount = vehicleControlBillingAmount;
        return this;
    }

    public void setVehicleControlBillingAmount(Integer vehicleControlBillingAmount) {
        this.vehicleControlBillingAmount = vehicleControlBillingAmount;
    }

    public Float getVehicleControlBillingTotalValue() {
        return this.vehicleControlBillingTotalValue;
    }

    public VehicleControlBilling vehicleControlBillingTotalValue(Float vehicleControlBillingTotalValue) {
        this.vehicleControlBillingTotalValue = vehicleControlBillingTotalValue;
        return this;
    }

    public void setVehicleControlBillingTotalValue(Float vehicleControlBillingTotalValue) {
        this.vehicleControlBillingTotalValue = vehicleControlBillingTotalValue;
    }

    public Float getVehicleControlBillingInsuranceDiscount() {
        return this.vehicleControlBillingInsuranceDiscount;
    }

    public VehicleControlBilling vehicleControlBillingInsuranceDiscount(Float vehicleControlBillingInsuranceDiscount) {
        this.vehicleControlBillingInsuranceDiscount = vehicleControlBillingInsuranceDiscount;
        return this;
    }

    public void setVehicleControlBillingInsuranceDiscount(Float vehicleControlBillingInsuranceDiscount) {
        this.vehicleControlBillingInsuranceDiscount = vehicleControlBillingInsuranceDiscount;
    }

    public String getVehicleControlBillingCustomerBank() {
        return this.vehicleControlBillingCustomerBank;
    }

    public VehicleControlBilling vehicleControlBillingCustomerBank(String vehicleControlBillingCustomerBank) {
        this.vehicleControlBillingCustomerBank = vehicleControlBillingCustomerBank;
        return this;
    }

    public void setVehicleControlBillingCustomerBank(String vehicleControlBillingCustomerBank) {
        this.vehicleControlBillingCustomerBank = vehicleControlBillingCustomerBank;
    }

    public Boolean getVehicleControlBillingAnticipate() {
        return this.vehicleControlBillingAnticipate;
    }

    public VehicleControlBilling vehicleControlBillingAnticipate(Boolean vehicleControlBillingAnticipate) {
        this.vehicleControlBillingAnticipate = vehicleControlBillingAnticipate;
        return this;
    }

    public void setVehicleControlBillingAnticipate(Boolean vehicleControlBillingAnticipate) {
        this.vehicleControlBillingAnticipate = vehicleControlBillingAnticipate;
    }

    public String getVehicleControlBillingManifest() {
        return this.vehicleControlBillingManifest;
    }

    public VehicleControlBilling vehicleControlBillingManifest(String vehicleControlBillingManifest) {
        this.vehicleControlBillingManifest = vehicleControlBillingManifest;
        return this;
    }

    public void setVehicleControlBillingManifest(String vehicleControlBillingManifest) {
        this.vehicleControlBillingManifest = vehicleControlBillingManifest;
    }

    public VehicleControls getVehicleControls() {
        return this.vehicleControls;
    }

    public VehicleControlBilling vehicleControls(VehicleControls vehicleControls) {
        this.setVehicleControls(vehicleControls);
        return this;
    }

    public void setVehicleControls(VehicleControls vehicleControls) {
        this.vehicleControls = vehicleControls;
    }

    public Fees getFees() {
        return this.fees;
    }

    public VehicleControlBilling fees(Fees fees) {
        this.setFees(fees);
        return this;
    }

    public void setFees(Fees fees) {
        this.fees = fees;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleControlBilling)) {
            return false;
        }
        return id != null && id.equals(((VehicleControlBilling) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleControlBilling{" +
            "id=" + getId() +
            ", vehicleControlBillingDate='" + getVehicleControlBillingDate() + "'" +
            ", vehicleControlBillingExpirationDate='" + getVehicleControlBillingExpirationDate() + "'" +
            ", vehicleControlBillingPaymentDate='" + getVehicleControlBillingPaymentDate() + "'" +
            ", vehicleControlBillingSellerCommission='" + getVehicleControlBillingSellerCommission() + "'" +
            ", vehicleControlBillingDriverCommission='" + getVehicleControlBillingDriverCommission() + "'" +
            ", vehicleControlBillingAmount=" + getVehicleControlBillingAmount() +
            ", vehicleControlBillingTotalValue=" + getVehicleControlBillingTotalValue() +
            ", vehicleControlBillingInsuranceDiscount=" + getVehicleControlBillingInsuranceDiscount() +
            ", vehicleControlBillingCustomerBank='" + getVehicleControlBillingCustomerBank() + "'" +
            ", vehicleControlBillingAnticipate='" + getVehicleControlBillingAnticipate() + "'" +
            ", vehicleControlBillingManifest='" + getVehicleControlBillingManifest() + "'" +
            "}";
    }
}
