package com.genesisoft.transporte.service.dto;

import com.genesisoft.transporte.domain.enumeration.FuelLevel;
import com.genesisoft.transporte.domain.enumeration.InspectionStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.VehicleInspections} entity.
 */
public class VehicleInspectionsDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime vehicleInspectionDate;

    @NotNull
    private InspectionStatus vehicleInspectionStatus;

    @NotNull
    private String vehicleInspectionModel;

    @NotNull
    private String vehicleInspectionLicensePlate;

    private Float vehicleInspectionKm;

    private Float vehicleInspectionLicenseYear;

    private Boolean vehicleInspectionHasManual;

    private Boolean vehicleInspectionHasExtraKey;

    private Boolean vehicleInspectionHasStickers;

    private FuelLevel vehicleInspectionGas;

    private Boolean vehicleInspectionRearView;

    private Boolean vehicleInspectionHorn;

    private Boolean vehicleInspectionWindshieldWiper;

    private Boolean vehicleInspectionSquirt;

    private VehicleStatus vehicleInspectionInternalLight;

    private VehicleStatus vehicleInspectionPanelLight;

    private VehicleStatus vehicleInspectionHighLight;

    private VehicleStatus vehicleInspectionLowLight;

    private VehicleStatus vehicleInspectionTaillight;

    private VehicleStatus vehicleInspectionIndicator;

    private VehicleStatus vehicleInspectionBeacons;

    private VehicleStatus vehicleInspectionBreakLight;

    private VehicleStatus vehicleInspectionPlateLight;

    private VehicleStatus vehicleInspectionSpeedometer;

    private VehicleStatus vehicleInspectionTemperature;

    private VehicleStatus vehicleInspectionTires;

    private VehicleStatus vehicleInspectionStep;

    private VehicleStatus vehicleInspectionFireExtinguisher;

    private VehicleStatus vehicleInspectionSeatBelts;

    private VehicleStatus vehicleInspectionMonkey;

    private VehicleStatus vehicleInspectionTireIron;

    private VehicleStatus vehicleInspectionRadiatorCap;

    private VehicleStatus vehicleInspectionTriangle;

    private VehicleStatus vehicleInspectionServiceBrake;

    private VehicleStatus vehicleInspectionParkingBrake;

    private VehicleStatus vehicleInspectionOilLeaks;

    private VehicleStatus vehicleInspectionGlassActuator;

    private VehicleStatus vehicleInspectionVehicleCleaning;

    private VehicleStatus vehicleInspectionSeatState;

    private VehicleStatus vehicleInspectionExhausts;

    @Size(max = 500)
    private String vehicleInspectionsObs;

    private String vehicleInspectionsSignedUrl;

    private VehicleControlItemDTO vehicleControls;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getVehicleInspectionDate() {
        return vehicleInspectionDate;
    }

    public void setVehicleInspectionDate(ZonedDateTime vehicleInspectionDate) {
        this.vehicleInspectionDate = vehicleInspectionDate;
    }

    public InspectionStatus getVehicleInspectionStatus() {
        return vehicleInspectionStatus;
    }

    public void setVehicleInspectionStatus(InspectionStatus vehicleInspectionStatus) {
        this.vehicleInspectionStatus = vehicleInspectionStatus;
    }

    public String getVehicleInspectionModel() {
        return vehicleInspectionModel;
    }

    public void setVehicleInspectionModel(String vehicleInspectionModel) {
        this.vehicleInspectionModel = vehicleInspectionModel;
    }

    public String getVehicleInspectionLicensePlate() {
        return vehicleInspectionLicensePlate;
    }

    public void setVehicleInspectionLicensePlate(String vehicleInspectionLicensePlate) {
        this.vehicleInspectionLicensePlate = vehicleInspectionLicensePlate;
    }

    public Float getVehicleInspectionKm() {
        return vehicleInspectionKm;
    }

    public void setVehicleInspectionKm(Float vehicleInspectionKm) {
        this.vehicleInspectionKm = vehicleInspectionKm;
    }

    public Float getVehicleInspectionLicenseYear() {
        return vehicleInspectionLicenseYear;
    }

    public void setVehicleInspectionLicenseYear(Float vehicleInspectionLicenseYear) {
        this.vehicleInspectionLicenseYear = vehicleInspectionLicenseYear;
    }

    public Boolean getVehicleInspectionHasManual() {
        return vehicleInspectionHasManual;
    }

    public void setVehicleInspectionHasManual(Boolean vehicleInspectionHasManual) {
        this.vehicleInspectionHasManual = vehicleInspectionHasManual;
    }

    public Boolean getVehicleInspectionHasExtraKey() {
        return vehicleInspectionHasExtraKey;
    }

    public void setVehicleInspectionHasExtraKey(Boolean vehicleInspectionHasExtraKey) {
        this.vehicleInspectionHasExtraKey = vehicleInspectionHasExtraKey;
    }

    public Boolean getVehicleInspectionHasStickers() {
        return vehicleInspectionHasStickers;
    }

    public void setVehicleInspectionHasStickers(Boolean vehicleInspectionHasStickers) {
        this.vehicleInspectionHasStickers = vehicleInspectionHasStickers;
    }

    public FuelLevel getVehicleInspectionGas() {
        return vehicleInspectionGas;
    }

    public void setVehicleInspectionGas(FuelLevel vehicleInspectionGas) {
        this.vehicleInspectionGas = vehicleInspectionGas;
    }

    public Boolean getVehicleInspectionRearView() {
        return vehicleInspectionRearView;
    }

    public void setVehicleInspectionRearView(Boolean vehicleInspectionRearView) {
        this.vehicleInspectionRearView = vehicleInspectionRearView;
    }

    public Boolean getVehicleInspectionHorn() {
        return vehicleInspectionHorn;
    }

    public void setVehicleInspectionHorn(Boolean vehicleInspectionHorn) {
        this.vehicleInspectionHorn = vehicleInspectionHorn;
    }

    public Boolean getVehicleInspectionWindshieldWiper() {
        return vehicleInspectionWindshieldWiper;
    }

    public void setVehicleInspectionWindshieldWiper(Boolean vehicleInspectionWindshieldWiper) {
        this.vehicleInspectionWindshieldWiper = vehicleInspectionWindshieldWiper;
    }

    public Boolean getVehicleInspectionSquirt() {
        return vehicleInspectionSquirt;
    }

    public void setVehicleInspectionSquirt(Boolean vehicleInspectionSquirt) {
        this.vehicleInspectionSquirt = vehicleInspectionSquirt;
    }

    public VehicleStatus getVehicleInspectionInternalLight() {
        return vehicleInspectionInternalLight;
    }

    public void setVehicleInspectionInternalLight(VehicleStatus vehicleInspectionInternalLight) {
        this.vehicleInspectionInternalLight = vehicleInspectionInternalLight;
    }

    public VehicleStatus getVehicleInspectionPanelLight() {
        return vehicleInspectionPanelLight;
    }

    public void setVehicleInspectionPanelLight(VehicleStatus vehicleInspectionPanelLight) {
        this.vehicleInspectionPanelLight = vehicleInspectionPanelLight;
    }

    public VehicleStatus getVehicleInspectionHighLight() {
        return vehicleInspectionHighLight;
    }

    public void setVehicleInspectionHighLight(VehicleStatus vehicleInspectionHighLight) {
        this.vehicleInspectionHighLight = vehicleInspectionHighLight;
    }

    public VehicleStatus getVehicleInspectionLowLight() {
        return vehicleInspectionLowLight;
    }

    public void setVehicleInspectionLowLight(VehicleStatus vehicleInspectionLowLight) {
        this.vehicleInspectionLowLight = vehicleInspectionLowLight;
    }

    public VehicleStatus getVehicleInspectionTaillight() {
        return vehicleInspectionTaillight;
    }

    public void setVehicleInspectionTaillight(VehicleStatus vehicleInspectionTaillight) {
        this.vehicleInspectionTaillight = vehicleInspectionTaillight;
    }

    public VehicleStatus getVehicleInspectionIndicator() {
        return vehicleInspectionIndicator;
    }

    public void setVehicleInspectionIndicator(VehicleStatus vehicleInspectionIndicator) {
        this.vehicleInspectionIndicator = vehicleInspectionIndicator;
    }

    public VehicleStatus getVehicleInspectionBeacons() {
        return vehicleInspectionBeacons;
    }

    public void setVehicleInspectionBeacons(VehicleStatus vehicleInspectionBeacons) {
        this.vehicleInspectionBeacons = vehicleInspectionBeacons;
    }

    public VehicleStatus getVehicleInspectionBreakLight() {
        return vehicleInspectionBreakLight;
    }

    public void setVehicleInspectionBreakLight(VehicleStatus vehicleInspectionBreakLight) {
        this.vehicleInspectionBreakLight = vehicleInspectionBreakLight;
    }

    public VehicleStatus getVehicleInspectionPlateLight() {
        return vehicleInspectionPlateLight;
    }

    public void setVehicleInspectionPlateLight(VehicleStatus vehicleInspectionPlateLight) {
        this.vehicleInspectionPlateLight = vehicleInspectionPlateLight;
    }

    public VehicleStatus getVehicleInspectionSpeedometer() {
        return vehicleInspectionSpeedometer;
    }

    public void setVehicleInspectionSpeedometer(VehicleStatus vehicleInspectionSpeedometer) {
        this.vehicleInspectionSpeedometer = vehicleInspectionSpeedometer;
    }

    public VehicleStatus getVehicleInspectionTemperature() {
        return vehicleInspectionTemperature;
    }

    public void setVehicleInspectionTemperature(VehicleStatus vehicleInspectionTemperature) {
        this.vehicleInspectionTemperature = vehicleInspectionTemperature;
    }

    public VehicleStatus getVehicleInspectionTires() {
        return vehicleInspectionTires;
    }

    public void setVehicleInspectionTires(VehicleStatus vehicleInspectionTires) {
        this.vehicleInspectionTires = vehicleInspectionTires;
    }

    public VehicleStatus getVehicleInspectionStep() {
        return vehicleInspectionStep;
    }

    public void setVehicleInspectionStep(VehicleStatus vehicleInspectionStep) {
        this.vehicleInspectionStep = vehicleInspectionStep;
    }

    public VehicleStatus getVehicleInspectionFireExtinguisher() {
        return vehicleInspectionFireExtinguisher;
    }

    public void setVehicleInspectionFireExtinguisher(VehicleStatus vehicleInspectionFireExtinguisher) {
        this.vehicleInspectionFireExtinguisher = vehicleInspectionFireExtinguisher;
    }

    public VehicleStatus getVehicleInspectionSeatBelts() {
        return vehicleInspectionSeatBelts;
    }

    public void setVehicleInspectionSeatBelts(VehicleStatus vehicleInspectionSeatBelts) {
        this.vehicleInspectionSeatBelts = vehicleInspectionSeatBelts;
    }

    public VehicleStatus getVehicleInspectionMonkey() {
        return vehicleInspectionMonkey;
    }

    public void setVehicleInspectionMonkey(VehicleStatus vehicleInspectionMonkey) {
        this.vehicleInspectionMonkey = vehicleInspectionMonkey;
    }

    public VehicleStatus getVehicleInspectionTireIron() {
        return vehicleInspectionTireIron;
    }

    public void setVehicleInspectionTireIron(VehicleStatus vehicleInspectionTireIron) {
        this.vehicleInspectionTireIron = vehicleInspectionTireIron;
    }

    public VehicleStatus getVehicleInspectionRadiatorCap() {
        return vehicleInspectionRadiatorCap;
    }

    public void setVehicleInspectionRadiatorCap(VehicleStatus vehicleInspectionRadiatorCap) {
        this.vehicleInspectionRadiatorCap = vehicleInspectionRadiatorCap;
    }

    public VehicleStatus getVehicleInspectionTriangle() {
        return vehicleInspectionTriangle;
    }

    public void setVehicleInspectionTriangle(VehicleStatus vehicleInspectionTriangle) {
        this.vehicleInspectionTriangle = vehicleInspectionTriangle;
    }

    public VehicleStatus getVehicleInspectionServiceBrake() {
        return vehicleInspectionServiceBrake;
    }

    public void setVehicleInspectionServiceBrake(VehicleStatus vehicleInspectionServiceBrake) {
        this.vehicleInspectionServiceBrake = vehicleInspectionServiceBrake;
    }

    public VehicleStatus getVehicleInspectionParkingBrake() {
        return vehicleInspectionParkingBrake;
    }

    public void setVehicleInspectionParkingBrake(VehicleStatus vehicleInspectionParkingBrake) {
        this.vehicleInspectionParkingBrake = vehicleInspectionParkingBrake;
    }

    public VehicleStatus getVehicleInspectionOilLeaks() {
        return vehicleInspectionOilLeaks;
    }

    public void setVehicleInspectionOilLeaks(VehicleStatus vehicleInspectionOilLeaks) {
        this.vehicleInspectionOilLeaks = vehicleInspectionOilLeaks;
    }

    public VehicleStatus getVehicleInspectionGlassActuator() {
        return vehicleInspectionGlassActuator;
    }

    public void setVehicleInspectionGlassActuator(VehicleStatus vehicleInspectionGlassActuator) {
        this.vehicleInspectionGlassActuator = vehicleInspectionGlassActuator;
    }

    public VehicleStatus getVehicleInspectionVehicleCleaning() {
        return vehicleInspectionVehicleCleaning;
    }

    public void setVehicleInspectionVehicleCleaning(VehicleStatus vehicleInspectionVehicleCleaning) {
        this.vehicleInspectionVehicleCleaning = vehicleInspectionVehicleCleaning;
    }

    public VehicleStatus getVehicleInspectionSeatState() {
        return vehicleInspectionSeatState;
    }

    public void setVehicleInspectionSeatState(VehicleStatus vehicleInspectionSeatState) {
        this.vehicleInspectionSeatState = vehicleInspectionSeatState;
    }

    public VehicleStatus getVehicleInspectionExhausts() {
        return vehicleInspectionExhausts;
    }

    public void setVehicleInspectionExhausts(VehicleStatus vehicleInspectionExhausts) {
        this.vehicleInspectionExhausts = vehicleInspectionExhausts;
    }

    public String getVehicleInspectionsObs() {
        return vehicleInspectionsObs;
    }

    public void setVehicleInspectionsObs(String vehicleInspectionsObs) {
        this.vehicleInspectionsObs = vehicleInspectionsObs;
    }

    public String getVehicleInspectionsSignedUrl() {
        return vehicleInspectionsSignedUrl;
    }

    public void setVehicleInspectionsSignedUrl(String vehicleInspectionsSignedUrl) {
        this.vehicleInspectionsSignedUrl = vehicleInspectionsSignedUrl;
    }

    public VehicleControlItemDTO getVehicleControls() {
        return vehicleControls;
    }

    public void setVehicleControls(VehicleControlItemDTO vehicleControls) {
        this.vehicleControls = vehicleControls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleInspectionsDTO)) {
            return false;
        }

        VehicleInspectionsDTO vehicleInspectionsDTO = (VehicleInspectionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vehicleInspectionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleInspectionsDTO{" +
            "id=" + getId() +
            ", vehicleInspectionDate='" + getVehicleInspectionDate() + "'" +
            ", vehicleInspectionStatus='" + getVehicleInspectionStatus() + "'" +
            ", vehicleInspectionModel='" + getVehicleInspectionModel() + "'" +
            ", vehicleInspectionLicensePlate='" + getVehicleInspectionLicensePlate() + "'" +
            ", vehicleInspectionKm=" + getVehicleInspectionKm() +
            ", vehicleInspectionLicenseYear=" + getVehicleInspectionLicenseYear() +
            ", vehicleInspectionHasManual='" + getVehicleInspectionHasManual() + "'" +
            ", vehicleInspectionHasExtraKey='" + getVehicleInspectionHasExtraKey() + "'" +
            ", vehicleInspectionHasStickers='" + getVehicleInspectionHasStickers() + "'" +
            ", vehicleInspectionGas='" + getVehicleInspectionGas() + "'" +
            ", vehicleInspectionRearView='" + getVehicleInspectionRearView() + "'" +
            ", vehicleInspectionHorn='" + getVehicleInspectionHorn() + "'" +
            ", vehicleInspectionWindshieldWiper='" + getVehicleInspectionWindshieldWiper() + "'" +
            ", vehicleInspectionSquirt='" + getVehicleInspectionSquirt() + "'" +
            ", vehicleInspectionInternalLight='" + getVehicleInspectionInternalLight() + "'" +
            ", vehicleInspectionPanelLight='" + getVehicleInspectionPanelLight() + "'" +
            ", vehicleInspectionHighLight='" + getVehicleInspectionHighLight() + "'" +
            ", vehicleInspectionLowLight='" + getVehicleInspectionLowLight() + "'" +
            ", vehicleInspectionTaillight='" + getVehicleInspectionTaillight() + "'" +
            ", vehicleInspectionIndicator='" + getVehicleInspectionIndicator() + "'" +
            ", vehicleInspectionBeacons='" + getVehicleInspectionBeacons() + "'" +
            ", vehicleInspectionBreakLight='" + getVehicleInspectionBreakLight() + "'" +
            ", vehicleInspectionPlateLight='" + getVehicleInspectionPlateLight() + "'" +
            ", vehicleInspectionSpeedometer='" + getVehicleInspectionSpeedometer() + "'" +
            ", vehicleInspectionTemperature='" + getVehicleInspectionTemperature() + "'" +
            ", vehicleInspectionTires='" + getVehicleInspectionTires() + "'" +
            ", vehicleInspectionStep='" + getVehicleInspectionStep() + "'" +
            ", vehicleInspectionFireExtinguisher='" + getVehicleInspectionFireExtinguisher() + "'" +
            ", vehicleInspectionSeatBelts='" + getVehicleInspectionSeatBelts() + "'" +
            ", vehicleInspectionMonkey='" + getVehicleInspectionMonkey() + "'" +
            ", vehicleInspectionTireIron='" + getVehicleInspectionTireIron() + "'" +
            ", vehicleInspectionRadiatorCap='" + getVehicleInspectionRadiatorCap() + "'" +
            ", vehicleInspectionTriangle='" + getVehicleInspectionTriangle() + "'" +
            ", vehicleInspectionServiceBrake='" + getVehicleInspectionServiceBrake() + "'" +
            ", vehicleInspectionParkingBrake='" + getVehicleInspectionParkingBrake() + "'" +
            ", vehicleInspectionOilLeaks='" + getVehicleInspectionOilLeaks() + "'" +
            ", vehicleInspectionGlassActuator='" + getVehicleInspectionGlassActuator() + "'" +
            ", vehicleInspectionVehicleCleaning='" + getVehicleInspectionVehicleCleaning() + "'" +
            ", vehicleInspectionSeatState='" + getVehicleInspectionSeatState() + "'" +
            ", vehicleInspectionExhausts='" + getVehicleInspectionExhausts() + "'" +
            ", vehicleInspectionsObs='" + getVehicleInspectionsObs() + "'" +
            ", vehicleInspectionsSignedUrl='" + getVehicleInspectionsSignedUrl() + "'" +
            ", vehicleControls=" + getVehicleControls() +
            "}";
    }
}
