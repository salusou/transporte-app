package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.genesisoft.transporte.domain.enumeration.FuelLevel;
import com.genesisoft.transporte.domain.enumeration.InspectionStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VehicleInspections.
 */
@Entity
@Table(name = "vehicle_inspections")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VehicleInspections implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "vehicle_inspection_date", nullable = false)
    private ZonedDateTime vehicleInspectionDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_status", nullable = false)
    private InspectionStatus vehicleInspectionStatus;

    @NotNull
    @Column(name = "vehicle_inspection_model", nullable = false)
    private String vehicleInspectionModel;

    @NotNull
    @Column(name = "vehicle_inspection_license_plate", nullable = false)
    private String vehicleInspectionLicensePlate;

    @Column(name = "vehicle_inspection_km")
    private Float vehicleInspectionKm;

    @Column(name = "vehicle_inspection_license_year")
    private Float vehicleInspectionLicenseYear;

    @Column(name = "vehicle_inspection_has_manual")
    private Boolean vehicleInspectionHasManual;

    @Column(name = "vehicle_inspection_has_extra_key")
    private Boolean vehicleInspectionHasExtraKey;

    @Column(name = "vehicle_inspection_has_stickers")
    private Boolean vehicleInspectionHasStickers;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_gas")
    private FuelLevel vehicleInspectionGas;

    @Column(name = "vehicle_inspection_rear_view")
    private Boolean vehicleInspectionRearView;

    @Column(name = "vehicle_inspection_horn")
    private Boolean vehicleInspectionHorn;

    @Column(name = "vehicle_inspection_windshield_wiper")
    private Boolean vehicleInspectionWindshieldWiper;

    @Column(name = "vehicle_inspection_squirt")
    private Boolean vehicleInspectionSquirt;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_internal_light")
    private VehicleStatus vehicleInspectionInternalLight;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_panel_light")
    private VehicleStatus vehicleInspectionPanelLight;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_high_light")
    private VehicleStatus vehicleInspectionHighLight;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_low_light")
    private VehicleStatus vehicleInspectionLowLight;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_taillight")
    private VehicleStatus vehicleInspectionTaillight;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_indicator")
    private VehicleStatus vehicleInspectionIndicator;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_beacons")
    private VehicleStatus vehicleInspectionBeacons;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_break_light")
    private VehicleStatus vehicleInspectionBreakLight;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_plate_light")
    private VehicleStatus vehicleInspectionPlateLight;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_speedometer")
    private VehicleStatus vehicleInspectionSpeedometer;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_temperature")
    private VehicleStatus vehicleInspectionTemperature;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_tires")
    private VehicleStatus vehicleInspectionTires;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_step")
    private VehicleStatus vehicleInspectionStep;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_fire_extinguisher")
    private VehicleStatus vehicleInspectionFireExtinguisher;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_seat_belts")
    private VehicleStatus vehicleInspectionSeatBelts;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_monkey")
    private VehicleStatus vehicleInspectionMonkey;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_tire_iron")
    private VehicleStatus vehicleInspectionTireIron;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_radiator_cap")
    private VehicleStatus vehicleInspectionRadiatorCap;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_triangle")
    private VehicleStatus vehicleInspectionTriangle;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_service_brake")
    private VehicleStatus vehicleInspectionServiceBrake;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_parking_brake")
    private VehicleStatus vehicleInspectionParkingBrake;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_oil_leaks")
    private VehicleStatus vehicleInspectionOilLeaks;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_glass_actuator")
    private VehicleStatus vehicleInspectionGlassActuator;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_vehicle_cleaning")
    private VehicleStatus vehicleInspectionVehicleCleaning;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_seat_state")
    private VehicleStatus vehicleInspectionSeatState;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_inspection_exhausts")
    private VehicleStatus vehicleInspectionExhausts;

    @Size(max = 500)
    @Column(name = "vehicle_inspections_obs", length = 500)
    private String vehicleInspectionsObs;

    @Column(name = "vehicle_inspections_signed_url")
    private String vehicleInspectionsSignedUrl;

    @OneToMany(mappedBy = "vehicleInspections")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehicleInspections" }, allowSetters = true)
    private Set<VehicleInspectionsImagens> vehicleInspectionsImagens = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "vehicleInspections", "vehicleControlExpenses", "vehicleControls" }, allowSetters = true)
    private VehicleControlItem vehicleControls;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleInspections id(Long id) {
        this.id = id;
        return this;
    }

    public ZonedDateTime getVehicleInspectionDate() {
        return this.vehicleInspectionDate;
    }

    public VehicleInspections vehicleInspectionDate(ZonedDateTime vehicleInspectionDate) {
        this.vehicleInspectionDate = vehicleInspectionDate;
        return this;
    }

    public void setVehicleInspectionDate(ZonedDateTime vehicleInspectionDate) {
        this.vehicleInspectionDate = vehicleInspectionDate;
    }

    public InspectionStatus getVehicleInspectionStatus() {
        return this.vehicleInspectionStatus;
    }

    public VehicleInspections vehicleInspectionStatus(InspectionStatus vehicleInspectionStatus) {
        this.vehicleInspectionStatus = vehicleInspectionStatus;
        return this;
    }

    public void setVehicleInspectionStatus(InspectionStatus vehicleInspectionStatus) {
        this.vehicleInspectionStatus = vehicleInspectionStatus;
    }

    public String getVehicleInspectionModel() {
        return this.vehicleInspectionModel;
    }

    public VehicleInspections vehicleInspectionModel(String vehicleInspectionModel) {
        this.vehicleInspectionModel = vehicleInspectionModel;
        return this;
    }

    public void setVehicleInspectionModel(String vehicleInspectionModel) {
        this.vehicleInspectionModel = vehicleInspectionModel;
    }

    public String getVehicleInspectionLicensePlate() {
        return this.vehicleInspectionLicensePlate;
    }

    public VehicleInspections vehicleInspectionLicensePlate(String vehicleInspectionLicensePlate) {
        this.vehicleInspectionLicensePlate = vehicleInspectionLicensePlate;
        return this;
    }

    public void setVehicleInspectionLicensePlate(String vehicleInspectionLicensePlate) {
        this.vehicleInspectionLicensePlate = vehicleInspectionLicensePlate;
    }

    public Float getVehicleInspectionKm() {
        return this.vehicleInspectionKm;
    }

    public VehicleInspections vehicleInspectionKm(Float vehicleInspectionKm) {
        this.vehicleInspectionKm = vehicleInspectionKm;
        return this;
    }

    public void setVehicleInspectionKm(Float vehicleInspectionKm) {
        this.vehicleInspectionKm = vehicleInspectionKm;
    }

    public Float getVehicleInspectionLicenseYear() {
        return this.vehicleInspectionLicenseYear;
    }

    public VehicleInspections vehicleInspectionLicenseYear(Float vehicleInspectionLicenseYear) {
        this.vehicleInspectionLicenseYear = vehicleInspectionLicenseYear;
        return this;
    }

    public void setVehicleInspectionLicenseYear(Float vehicleInspectionLicenseYear) {
        this.vehicleInspectionLicenseYear = vehicleInspectionLicenseYear;
    }

    public Boolean getVehicleInspectionHasManual() {
        return this.vehicleInspectionHasManual;
    }

    public VehicleInspections vehicleInspectionHasManual(Boolean vehicleInspectionHasManual) {
        this.vehicleInspectionHasManual = vehicleInspectionHasManual;
        return this;
    }

    public void setVehicleInspectionHasManual(Boolean vehicleInspectionHasManual) {
        this.vehicleInspectionHasManual = vehicleInspectionHasManual;
    }

    public Boolean getVehicleInspectionHasExtraKey() {
        return this.vehicleInspectionHasExtraKey;
    }

    public VehicleInspections vehicleInspectionHasExtraKey(Boolean vehicleInspectionHasExtraKey) {
        this.vehicleInspectionHasExtraKey = vehicleInspectionHasExtraKey;
        return this;
    }

    public void setVehicleInspectionHasExtraKey(Boolean vehicleInspectionHasExtraKey) {
        this.vehicleInspectionHasExtraKey = vehicleInspectionHasExtraKey;
    }

    public Boolean getVehicleInspectionHasStickers() {
        return this.vehicleInspectionHasStickers;
    }

    public VehicleInspections vehicleInspectionHasStickers(Boolean vehicleInspectionHasStickers) {
        this.vehicleInspectionHasStickers = vehicleInspectionHasStickers;
        return this;
    }

    public void setVehicleInspectionHasStickers(Boolean vehicleInspectionHasStickers) {
        this.vehicleInspectionHasStickers = vehicleInspectionHasStickers;
    }

    public FuelLevel getVehicleInspectionGas() {
        return this.vehicleInspectionGas;
    }

    public VehicleInspections vehicleInspectionGas(FuelLevel vehicleInspectionGas) {
        this.vehicleInspectionGas = vehicleInspectionGas;
        return this;
    }

    public void setVehicleInspectionGas(FuelLevel vehicleInspectionGas) {
        this.vehicleInspectionGas = vehicleInspectionGas;
    }

    public Boolean getVehicleInspectionRearView() {
        return this.vehicleInspectionRearView;
    }

    public VehicleInspections vehicleInspectionRearView(Boolean vehicleInspectionRearView) {
        this.vehicleInspectionRearView = vehicleInspectionRearView;
        return this;
    }

    public void setVehicleInspectionRearView(Boolean vehicleInspectionRearView) {
        this.vehicleInspectionRearView = vehicleInspectionRearView;
    }

    public Boolean getVehicleInspectionHorn() {
        return this.vehicleInspectionHorn;
    }

    public VehicleInspections vehicleInspectionHorn(Boolean vehicleInspectionHorn) {
        this.vehicleInspectionHorn = vehicleInspectionHorn;
        return this;
    }

    public void setVehicleInspectionHorn(Boolean vehicleInspectionHorn) {
        this.vehicleInspectionHorn = vehicleInspectionHorn;
    }

    public Boolean getVehicleInspectionWindshieldWiper() {
        return this.vehicleInspectionWindshieldWiper;
    }

    public VehicleInspections vehicleInspectionWindshieldWiper(Boolean vehicleInspectionWindshieldWiper) {
        this.vehicleInspectionWindshieldWiper = vehicleInspectionWindshieldWiper;
        return this;
    }

    public void setVehicleInspectionWindshieldWiper(Boolean vehicleInspectionWindshieldWiper) {
        this.vehicleInspectionWindshieldWiper = vehicleInspectionWindshieldWiper;
    }

    public Boolean getVehicleInspectionSquirt() {
        return this.vehicleInspectionSquirt;
    }

    public VehicleInspections vehicleInspectionSquirt(Boolean vehicleInspectionSquirt) {
        this.vehicleInspectionSquirt = vehicleInspectionSquirt;
        return this;
    }

    public void setVehicleInspectionSquirt(Boolean vehicleInspectionSquirt) {
        this.vehicleInspectionSquirt = vehicleInspectionSquirt;
    }

    public VehicleStatus getVehicleInspectionInternalLight() {
        return this.vehicleInspectionInternalLight;
    }

    public VehicleInspections vehicleInspectionInternalLight(VehicleStatus vehicleInspectionInternalLight) {
        this.vehicleInspectionInternalLight = vehicleInspectionInternalLight;
        return this;
    }

    public void setVehicleInspectionInternalLight(VehicleStatus vehicleInspectionInternalLight) {
        this.vehicleInspectionInternalLight = vehicleInspectionInternalLight;
    }

    public VehicleStatus getVehicleInspectionPanelLight() {
        return this.vehicleInspectionPanelLight;
    }

    public VehicleInspections vehicleInspectionPanelLight(VehicleStatus vehicleInspectionPanelLight) {
        this.vehicleInspectionPanelLight = vehicleInspectionPanelLight;
        return this;
    }

    public void setVehicleInspectionPanelLight(VehicleStatus vehicleInspectionPanelLight) {
        this.vehicleInspectionPanelLight = vehicleInspectionPanelLight;
    }

    public VehicleStatus getVehicleInspectionHighLight() {
        return this.vehicleInspectionHighLight;
    }

    public VehicleInspections vehicleInspectionHighLight(VehicleStatus vehicleInspectionHighLight) {
        this.vehicleInspectionHighLight = vehicleInspectionHighLight;
        return this;
    }

    public void setVehicleInspectionHighLight(VehicleStatus vehicleInspectionHighLight) {
        this.vehicleInspectionHighLight = vehicleInspectionHighLight;
    }

    public VehicleStatus getVehicleInspectionLowLight() {
        return this.vehicleInspectionLowLight;
    }

    public VehicleInspections vehicleInspectionLowLight(VehicleStatus vehicleInspectionLowLight) {
        this.vehicleInspectionLowLight = vehicleInspectionLowLight;
        return this;
    }

    public void setVehicleInspectionLowLight(VehicleStatus vehicleInspectionLowLight) {
        this.vehicleInspectionLowLight = vehicleInspectionLowLight;
    }

    public VehicleStatus getVehicleInspectionTaillight() {
        return this.vehicleInspectionTaillight;
    }

    public VehicleInspections vehicleInspectionTaillight(VehicleStatus vehicleInspectionTaillight) {
        this.vehicleInspectionTaillight = vehicleInspectionTaillight;
        return this;
    }

    public void setVehicleInspectionTaillight(VehicleStatus vehicleInspectionTaillight) {
        this.vehicleInspectionTaillight = vehicleInspectionTaillight;
    }

    public VehicleStatus getVehicleInspectionIndicator() {
        return this.vehicleInspectionIndicator;
    }

    public VehicleInspections vehicleInspectionIndicator(VehicleStatus vehicleInspectionIndicator) {
        this.vehicleInspectionIndicator = vehicleInspectionIndicator;
        return this;
    }

    public void setVehicleInspectionIndicator(VehicleStatus vehicleInspectionIndicator) {
        this.vehicleInspectionIndicator = vehicleInspectionIndicator;
    }

    public VehicleStatus getVehicleInspectionBeacons() {
        return this.vehicleInspectionBeacons;
    }

    public VehicleInspections vehicleInspectionBeacons(VehicleStatus vehicleInspectionBeacons) {
        this.vehicleInspectionBeacons = vehicleInspectionBeacons;
        return this;
    }

    public void setVehicleInspectionBeacons(VehicleStatus vehicleInspectionBeacons) {
        this.vehicleInspectionBeacons = vehicleInspectionBeacons;
    }

    public VehicleStatus getVehicleInspectionBreakLight() {
        return this.vehicleInspectionBreakLight;
    }

    public VehicleInspections vehicleInspectionBreakLight(VehicleStatus vehicleInspectionBreakLight) {
        this.vehicleInspectionBreakLight = vehicleInspectionBreakLight;
        return this;
    }

    public void setVehicleInspectionBreakLight(VehicleStatus vehicleInspectionBreakLight) {
        this.vehicleInspectionBreakLight = vehicleInspectionBreakLight;
    }

    public VehicleStatus getVehicleInspectionPlateLight() {
        return this.vehicleInspectionPlateLight;
    }

    public VehicleInspections vehicleInspectionPlateLight(VehicleStatus vehicleInspectionPlateLight) {
        this.vehicleInspectionPlateLight = vehicleInspectionPlateLight;
        return this;
    }

    public void setVehicleInspectionPlateLight(VehicleStatus vehicleInspectionPlateLight) {
        this.vehicleInspectionPlateLight = vehicleInspectionPlateLight;
    }

    public VehicleStatus getVehicleInspectionSpeedometer() {
        return this.vehicleInspectionSpeedometer;
    }

    public VehicleInspections vehicleInspectionSpeedometer(VehicleStatus vehicleInspectionSpeedometer) {
        this.vehicleInspectionSpeedometer = vehicleInspectionSpeedometer;
        return this;
    }

    public void setVehicleInspectionSpeedometer(VehicleStatus vehicleInspectionSpeedometer) {
        this.vehicleInspectionSpeedometer = vehicleInspectionSpeedometer;
    }

    public VehicleStatus getVehicleInspectionTemperature() {
        return this.vehicleInspectionTemperature;
    }

    public VehicleInspections vehicleInspectionTemperature(VehicleStatus vehicleInspectionTemperature) {
        this.vehicleInspectionTemperature = vehicleInspectionTemperature;
        return this;
    }

    public void setVehicleInspectionTemperature(VehicleStatus vehicleInspectionTemperature) {
        this.vehicleInspectionTemperature = vehicleInspectionTemperature;
    }

    public VehicleStatus getVehicleInspectionTires() {
        return this.vehicleInspectionTires;
    }

    public VehicleInspections vehicleInspectionTires(VehicleStatus vehicleInspectionTires) {
        this.vehicleInspectionTires = vehicleInspectionTires;
        return this;
    }

    public void setVehicleInspectionTires(VehicleStatus vehicleInspectionTires) {
        this.vehicleInspectionTires = vehicleInspectionTires;
    }

    public VehicleStatus getVehicleInspectionStep() {
        return this.vehicleInspectionStep;
    }

    public VehicleInspections vehicleInspectionStep(VehicleStatus vehicleInspectionStep) {
        this.vehicleInspectionStep = vehicleInspectionStep;
        return this;
    }

    public void setVehicleInspectionStep(VehicleStatus vehicleInspectionStep) {
        this.vehicleInspectionStep = vehicleInspectionStep;
    }

    public VehicleStatus getVehicleInspectionFireExtinguisher() {
        return this.vehicleInspectionFireExtinguisher;
    }

    public VehicleInspections vehicleInspectionFireExtinguisher(VehicleStatus vehicleInspectionFireExtinguisher) {
        this.vehicleInspectionFireExtinguisher = vehicleInspectionFireExtinguisher;
        return this;
    }

    public void setVehicleInspectionFireExtinguisher(VehicleStatus vehicleInspectionFireExtinguisher) {
        this.vehicleInspectionFireExtinguisher = vehicleInspectionFireExtinguisher;
    }

    public VehicleStatus getVehicleInspectionSeatBelts() {
        return this.vehicleInspectionSeatBelts;
    }

    public VehicleInspections vehicleInspectionSeatBelts(VehicleStatus vehicleInspectionSeatBelts) {
        this.vehicleInspectionSeatBelts = vehicleInspectionSeatBelts;
        return this;
    }

    public void setVehicleInspectionSeatBelts(VehicleStatus vehicleInspectionSeatBelts) {
        this.vehicleInspectionSeatBelts = vehicleInspectionSeatBelts;
    }

    public VehicleStatus getVehicleInspectionMonkey() {
        return this.vehicleInspectionMonkey;
    }

    public VehicleInspections vehicleInspectionMonkey(VehicleStatus vehicleInspectionMonkey) {
        this.vehicleInspectionMonkey = vehicleInspectionMonkey;
        return this;
    }

    public void setVehicleInspectionMonkey(VehicleStatus vehicleInspectionMonkey) {
        this.vehicleInspectionMonkey = vehicleInspectionMonkey;
    }

    public VehicleStatus getVehicleInspectionTireIron() {
        return this.vehicleInspectionTireIron;
    }

    public VehicleInspections vehicleInspectionTireIron(VehicleStatus vehicleInspectionTireIron) {
        this.vehicleInspectionTireIron = vehicleInspectionTireIron;
        return this;
    }

    public void setVehicleInspectionTireIron(VehicleStatus vehicleInspectionTireIron) {
        this.vehicleInspectionTireIron = vehicleInspectionTireIron;
    }

    public VehicleStatus getVehicleInspectionRadiatorCap() {
        return this.vehicleInspectionRadiatorCap;
    }

    public VehicleInspections vehicleInspectionRadiatorCap(VehicleStatus vehicleInspectionRadiatorCap) {
        this.vehicleInspectionRadiatorCap = vehicleInspectionRadiatorCap;
        return this;
    }

    public void setVehicleInspectionRadiatorCap(VehicleStatus vehicleInspectionRadiatorCap) {
        this.vehicleInspectionRadiatorCap = vehicleInspectionRadiatorCap;
    }

    public VehicleStatus getVehicleInspectionTriangle() {
        return this.vehicleInspectionTriangle;
    }

    public VehicleInspections vehicleInspectionTriangle(VehicleStatus vehicleInspectionTriangle) {
        this.vehicleInspectionTriangle = vehicleInspectionTriangle;
        return this;
    }

    public void setVehicleInspectionTriangle(VehicleStatus vehicleInspectionTriangle) {
        this.vehicleInspectionTriangle = vehicleInspectionTriangle;
    }

    public VehicleStatus getVehicleInspectionServiceBrake() {
        return this.vehicleInspectionServiceBrake;
    }

    public VehicleInspections vehicleInspectionServiceBrake(VehicleStatus vehicleInspectionServiceBrake) {
        this.vehicleInspectionServiceBrake = vehicleInspectionServiceBrake;
        return this;
    }

    public void setVehicleInspectionServiceBrake(VehicleStatus vehicleInspectionServiceBrake) {
        this.vehicleInspectionServiceBrake = vehicleInspectionServiceBrake;
    }

    public VehicleStatus getVehicleInspectionParkingBrake() {
        return this.vehicleInspectionParkingBrake;
    }

    public VehicleInspections vehicleInspectionParkingBrake(VehicleStatus vehicleInspectionParkingBrake) {
        this.vehicleInspectionParkingBrake = vehicleInspectionParkingBrake;
        return this;
    }

    public void setVehicleInspectionParkingBrake(VehicleStatus vehicleInspectionParkingBrake) {
        this.vehicleInspectionParkingBrake = vehicleInspectionParkingBrake;
    }

    public VehicleStatus getVehicleInspectionOilLeaks() {
        return this.vehicleInspectionOilLeaks;
    }

    public VehicleInspections vehicleInspectionOilLeaks(VehicleStatus vehicleInspectionOilLeaks) {
        this.vehicleInspectionOilLeaks = vehicleInspectionOilLeaks;
        return this;
    }

    public void setVehicleInspectionOilLeaks(VehicleStatus vehicleInspectionOilLeaks) {
        this.vehicleInspectionOilLeaks = vehicleInspectionOilLeaks;
    }

    public VehicleStatus getVehicleInspectionGlassActuator() {
        return this.vehicleInspectionGlassActuator;
    }

    public VehicleInspections vehicleInspectionGlassActuator(VehicleStatus vehicleInspectionGlassActuator) {
        this.vehicleInspectionGlassActuator = vehicleInspectionGlassActuator;
        return this;
    }

    public void setVehicleInspectionGlassActuator(VehicleStatus vehicleInspectionGlassActuator) {
        this.vehicleInspectionGlassActuator = vehicleInspectionGlassActuator;
    }

    public VehicleStatus getVehicleInspectionVehicleCleaning() {
        return this.vehicleInspectionVehicleCleaning;
    }

    public VehicleInspections vehicleInspectionVehicleCleaning(VehicleStatus vehicleInspectionVehicleCleaning) {
        this.vehicleInspectionVehicleCleaning = vehicleInspectionVehicleCleaning;
        return this;
    }

    public void setVehicleInspectionVehicleCleaning(VehicleStatus vehicleInspectionVehicleCleaning) {
        this.vehicleInspectionVehicleCleaning = vehicleInspectionVehicleCleaning;
    }

    public VehicleStatus getVehicleInspectionSeatState() {
        return this.vehicleInspectionSeatState;
    }

    public VehicleInspections vehicleInspectionSeatState(VehicleStatus vehicleInspectionSeatState) {
        this.vehicleInspectionSeatState = vehicleInspectionSeatState;
        return this;
    }

    public void setVehicleInspectionSeatState(VehicleStatus vehicleInspectionSeatState) {
        this.vehicleInspectionSeatState = vehicleInspectionSeatState;
    }

    public VehicleStatus getVehicleInspectionExhausts() {
        return this.vehicleInspectionExhausts;
    }

    public VehicleInspections vehicleInspectionExhausts(VehicleStatus vehicleInspectionExhausts) {
        this.vehicleInspectionExhausts = vehicleInspectionExhausts;
        return this;
    }

    public void setVehicleInspectionExhausts(VehicleStatus vehicleInspectionExhausts) {
        this.vehicleInspectionExhausts = vehicleInspectionExhausts;
    }

    public String getVehicleInspectionsObs() {
        return this.vehicleInspectionsObs;
    }

    public VehicleInspections vehicleInspectionsObs(String vehicleInspectionsObs) {
        this.vehicleInspectionsObs = vehicleInspectionsObs;
        return this;
    }

    public void setVehicleInspectionsObs(String vehicleInspectionsObs) {
        this.vehicleInspectionsObs = vehicleInspectionsObs;
    }

    public String getVehicleInspectionsSignedUrl() {
        return this.vehicleInspectionsSignedUrl;
    }

    public VehicleInspections vehicleInspectionsSignedUrl(String vehicleInspectionsSignedUrl) {
        this.vehicleInspectionsSignedUrl = vehicleInspectionsSignedUrl;
        return this;
    }

    public void setVehicleInspectionsSignedUrl(String vehicleInspectionsSignedUrl) {
        this.vehicleInspectionsSignedUrl = vehicleInspectionsSignedUrl;
    }

    public Set<VehicleInspectionsImagens> getVehicleInspectionsImagens() {
        return this.vehicleInspectionsImagens;
    }

    public VehicleInspections vehicleInspectionsImagens(Set<VehicleInspectionsImagens> vehicleInspectionsImagens) {
        this.setVehicleInspectionsImagens(vehicleInspectionsImagens);
        return this;
    }

    public VehicleInspections addVehicleInspectionsImagens(VehicleInspectionsImagens vehicleInspectionsImagens) {
        this.vehicleInspectionsImagens.add(vehicleInspectionsImagens);
        vehicleInspectionsImagens.setVehicleInspections(this);
        return this;
    }

    public VehicleInspections removeVehicleInspectionsImagens(VehicleInspectionsImagens vehicleInspectionsImagens) {
        this.vehicleInspectionsImagens.remove(vehicleInspectionsImagens);
        vehicleInspectionsImagens.setVehicleInspections(null);
        return this;
    }

    public void setVehicleInspectionsImagens(Set<VehicleInspectionsImagens> vehicleInspectionsImagens) {
        if (this.vehicleInspectionsImagens != null) {
            this.vehicleInspectionsImagens.forEach(i -> i.setVehicleInspections(null));
        }
        if (vehicleInspectionsImagens != null) {
            vehicleInspectionsImagens.forEach(i -> i.setVehicleInspections(this));
        }
        this.vehicleInspectionsImagens = vehicleInspectionsImagens;
    }

    public VehicleControlItem getVehicleControls() {
        return this.vehicleControls;
    }

    public VehicleInspections vehicleControls(VehicleControlItem vehicleControlItem) {
        this.setVehicleControls(vehicleControlItem);
        return this;
    }

    public void setVehicleControls(VehicleControlItem vehicleControlItem) {
        this.vehicleControls = vehicleControlItem;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleInspections)) {
            return false;
        }
        return id != null && id.equals(((VehicleInspections) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleInspections{" +
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
            "}";
    }
}
