package com.genesisoft.transporte.service.criteria;

import com.genesisoft.transporte.domain.enumeration.FuelLevel;
import com.genesisoft.transporte.domain.enumeration.InspectionStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.genesisoft.transporte.domain.VehicleInspections} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.VehicleInspectionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vehicle-inspections?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VehicleInspectionsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering InspectionStatus
     */
    public static class InspectionStatusFilter extends Filter<InspectionStatus> {

        public InspectionStatusFilter() {}

        public InspectionStatusFilter(InspectionStatusFilter filter) {
            super(filter);
        }

        @Override
        public InspectionStatusFilter copy() {
            return new InspectionStatusFilter(this);
        }
    }

    /**
     * Class for filtering FuelLevel
     */
    public static class FuelLevelFilter extends Filter<FuelLevel> {

        public FuelLevelFilter() {}

        public FuelLevelFilter(FuelLevelFilter filter) {
            super(filter);
        }

        @Override
        public FuelLevelFilter copy() {
            return new FuelLevelFilter(this);
        }
    }

    /**
     * Class for filtering VehicleStatus
     */
    public static class VehicleStatusFilter extends Filter<VehicleStatus> {

        public VehicleStatusFilter() {}

        public VehicleStatusFilter(VehicleStatusFilter filter) {
            super(filter);
        }

        @Override
        public VehicleStatusFilter copy() {
            return new VehicleStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter vehicleInspectionDate;

    private InspectionStatusFilter vehicleInspectionStatus;

    private StringFilter vehicleInspectionModel;

    private StringFilter vehicleInspectionLicensePlate;

    private FloatFilter vehicleInspectionKm;

    private FloatFilter vehicleInspectionLicenseYear;

    private BooleanFilter vehicleInspectionHasManual;

    private BooleanFilter vehicleInspectionHasExtraKey;

    private BooleanFilter vehicleInspectionHasStickers;

    private FuelLevelFilter vehicleInspectionGas;

    private BooleanFilter vehicleInspectionRearView;

    private BooleanFilter vehicleInspectionHorn;

    private BooleanFilter vehicleInspectionWindshieldWiper;

    private BooleanFilter vehicleInspectionSquirt;

    private VehicleStatusFilter vehicleInspectionInternalLight;

    private VehicleStatusFilter vehicleInspectionPanelLight;

    private VehicleStatusFilter vehicleInspectionHighLight;

    private VehicleStatusFilter vehicleInspectionLowLight;

    private VehicleStatusFilter vehicleInspectionTaillight;

    private VehicleStatusFilter vehicleInspectionIndicator;

    private VehicleStatusFilter vehicleInspectionBeacons;

    private VehicleStatusFilter vehicleInspectionBreakLight;

    private VehicleStatusFilter vehicleInspectionPlateLight;

    private VehicleStatusFilter vehicleInspectionSpeedometer;

    private VehicleStatusFilter vehicleInspectionTemperature;

    private VehicleStatusFilter vehicleInspectionTires;

    private VehicleStatusFilter vehicleInspectionStep;

    private VehicleStatusFilter vehicleInspectionFireExtinguisher;

    private VehicleStatusFilter vehicleInspectionSeatBelts;

    private VehicleStatusFilter vehicleInspectionMonkey;

    private VehicleStatusFilter vehicleInspectionTireIron;

    private VehicleStatusFilter vehicleInspectionRadiatorCap;

    private VehicleStatusFilter vehicleInspectionTriangle;

    private VehicleStatusFilter vehicleInspectionServiceBrake;

    private VehicleStatusFilter vehicleInspectionParkingBrake;

    private VehicleStatusFilter vehicleInspectionOilLeaks;

    private VehicleStatusFilter vehicleInspectionGlassActuator;

    private VehicleStatusFilter vehicleInspectionVehicleCleaning;

    private VehicleStatusFilter vehicleInspectionSeatState;

    private VehicleStatusFilter vehicleInspectionExhausts;

    private StringFilter vehicleInspectionsObs;

    private StringFilter vehicleInspectionsSignedUrl;

    private LongFilter vehicleInspectionsImagensId;

    private LongFilter vehicleControlsId;

    public VehicleInspectionsCriteria() {}

    public VehicleInspectionsCriteria(VehicleInspectionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.vehicleInspectionDate = other.vehicleInspectionDate == null ? null : other.vehicleInspectionDate.copy();
        this.vehicleInspectionStatus = other.vehicleInspectionStatus == null ? null : other.vehicleInspectionStatus.copy();
        this.vehicleInspectionModel = other.vehicleInspectionModel == null ? null : other.vehicleInspectionModel.copy();
        this.vehicleInspectionLicensePlate =
            other.vehicleInspectionLicensePlate == null ? null : other.vehicleInspectionLicensePlate.copy();
        this.vehicleInspectionKm = other.vehicleInspectionKm == null ? null : other.vehicleInspectionKm.copy();
        this.vehicleInspectionLicenseYear = other.vehicleInspectionLicenseYear == null ? null : other.vehicleInspectionLicenseYear.copy();
        this.vehicleInspectionHasManual = other.vehicleInspectionHasManual == null ? null : other.vehicleInspectionHasManual.copy();
        this.vehicleInspectionHasExtraKey = other.vehicleInspectionHasExtraKey == null ? null : other.vehicleInspectionHasExtraKey.copy();
        this.vehicleInspectionHasStickers = other.vehicleInspectionHasStickers == null ? null : other.vehicleInspectionHasStickers.copy();
        this.vehicleInspectionGas = other.vehicleInspectionGas == null ? null : other.vehicleInspectionGas.copy();
        this.vehicleInspectionRearView = other.vehicleInspectionRearView == null ? null : other.vehicleInspectionRearView.copy();
        this.vehicleInspectionHorn = other.vehicleInspectionHorn == null ? null : other.vehicleInspectionHorn.copy();
        this.vehicleInspectionWindshieldWiper =
            other.vehicleInspectionWindshieldWiper == null ? null : other.vehicleInspectionWindshieldWiper.copy();
        this.vehicleInspectionSquirt = other.vehicleInspectionSquirt == null ? null : other.vehicleInspectionSquirt.copy();
        this.vehicleInspectionInternalLight =
            other.vehicleInspectionInternalLight == null ? null : other.vehicleInspectionInternalLight.copy();
        this.vehicleInspectionPanelLight = other.vehicleInspectionPanelLight == null ? null : other.vehicleInspectionPanelLight.copy();
        this.vehicleInspectionHighLight = other.vehicleInspectionHighLight == null ? null : other.vehicleInspectionHighLight.copy();
        this.vehicleInspectionLowLight = other.vehicleInspectionLowLight == null ? null : other.vehicleInspectionLowLight.copy();
        this.vehicleInspectionTaillight = other.vehicleInspectionTaillight == null ? null : other.vehicleInspectionTaillight.copy();
        this.vehicleInspectionIndicator = other.vehicleInspectionIndicator == null ? null : other.vehicleInspectionIndicator.copy();
        this.vehicleInspectionBeacons = other.vehicleInspectionBeacons == null ? null : other.vehicleInspectionBeacons.copy();
        this.vehicleInspectionBreakLight = other.vehicleInspectionBreakLight == null ? null : other.vehicleInspectionBreakLight.copy();
        this.vehicleInspectionPlateLight = other.vehicleInspectionPlateLight == null ? null : other.vehicleInspectionPlateLight.copy();
        this.vehicleInspectionSpeedometer = other.vehicleInspectionSpeedometer == null ? null : other.vehicleInspectionSpeedometer.copy();
        this.vehicleInspectionTemperature = other.vehicleInspectionTemperature == null ? null : other.vehicleInspectionTemperature.copy();
        this.vehicleInspectionTires = other.vehicleInspectionTires == null ? null : other.vehicleInspectionTires.copy();
        this.vehicleInspectionStep = other.vehicleInspectionStep == null ? null : other.vehicleInspectionStep.copy();
        this.vehicleInspectionFireExtinguisher =
            other.vehicleInspectionFireExtinguisher == null ? null : other.vehicleInspectionFireExtinguisher.copy();
        this.vehicleInspectionSeatBelts = other.vehicleInspectionSeatBelts == null ? null : other.vehicleInspectionSeatBelts.copy();
        this.vehicleInspectionMonkey = other.vehicleInspectionMonkey == null ? null : other.vehicleInspectionMonkey.copy();
        this.vehicleInspectionTireIron = other.vehicleInspectionTireIron == null ? null : other.vehicleInspectionTireIron.copy();
        this.vehicleInspectionRadiatorCap = other.vehicleInspectionRadiatorCap == null ? null : other.vehicleInspectionRadiatorCap.copy();
        this.vehicleInspectionTriangle = other.vehicleInspectionTriangle == null ? null : other.vehicleInspectionTriangle.copy();
        this.vehicleInspectionServiceBrake =
            other.vehicleInspectionServiceBrake == null ? null : other.vehicleInspectionServiceBrake.copy();
        this.vehicleInspectionParkingBrake =
            other.vehicleInspectionParkingBrake == null ? null : other.vehicleInspectionParkingBrake.copy();
        this.vehicleInspectionOilLeaks = other.vehicleInspectionOilLeaks == null ? null : other.vehicleInspectionOilLeaks.copy();
        this.vehicleInspectionGlassActuator =
            other.vehicleInspectionGlassActuator == null ? null : other.vehicleInspectionGlassActuator.copy();
        this.vehicleInspectionVehicleCleaning =
            other.vehicleInspectionVehicleCleaning == null ? null : other.vehicleInspectionVehicleCleaning.copy();
        this.vehicleInspectionSeatState = other.vehicleInspectionSeatState == null ? null : other.vehicleInspectionSeatState.copy();
        this.vehicleInspectionExhausts = other.vehicleInspectionExhausts == null ? null : other.vehicleInspectionExhausts.copy();
        this.vehicleInspectionsObs = other.vehicleInspectionsObs == null ? null : other.vehicleInspectionsObs.copy();
        this.vehicleInspectionsSignedUrl = other.vehicleInspectionsSignedUrl == null ? null : other.vehicleInspectionsSignedUrl.copy();
        this.vehicleInspectionsImagensId = other.vehicleInspectionsImagensId == null ? null : other.vehicleInspectionsImagensId.copy();
        this.vehicleControlsId = other.vehicleControlsId == null ? null : other.vehicleControlsId.copy();
    }

    @Override
    public VehicleInspectionsCriteria copy() {
        return new VehicleInspectionsCriteria(this);
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

    public ZonedDateTimeFilter getVehicleInspectionDate() {
        return vehicleInspectionDate;
    }

    public ZonedDateTimeFilter vehicleInspectionDate() {
        if (vehicleInspectionDate == null) {
            vehicleInspectionDate = new ZonedDateTimeFilter();
        }
        return vehicleInspectionDate;
    }

    public void setVehicleInspectionDate(ZonedDateTimeFilter vehicleInspectionDate) {
        this.vehicleInspectionDate = vehicleInspectionDate;
    }

    public InspectionStatusFilter getVehicleInspectionStatus() {
        return vehicleInspectionStatus;
    }

    public InspectionStatusFilter vehicleInspectionStatus() {
        if (vehicleInspectionStatus == null) {
            vehicleInspectionStatus = new InspectionStatusFilter();
        }
        return vehicleInspectionStatus;
    }

    public void setVehicleInspectionStatus(InspectionStatusFilter vehicleInspectionStatus) {
        this.vehicleInspectionStatus = vehicleInspectionStatus;
    }

    public StringFilter getVehicleInspectionModel() {
        return vehicleInspectionModel;
    }

    public StringFilter vehicleInspectionModel() {
        if (vehicleInspectionModel == null) {
            vehicleInspectionModel = new StringFilter();
        }
        return vehicleInspectionModel;
    }

    public void setVehicleInspectionModel(StringFilter vehicleInspectionModel) {
        this.vehicleInspectionModel = vehicleInspectionModel;
    }

    public StringFilter getVehicleInspectionLicensePlate() {
        return vehicleInspectionLicensePlate;
    }

    public StringFilter vehicleInspectionLicensePlate() {
        if (vehicleInspectionLicensePlate == null) {
            vehicleInspectionLicensePlate = new StringFilter();
        }
        return vehicleInspectionLicensePlate;
    }

    public void setVehicleInspectionLicensePlate(StringFilter vehicleInspectionLicensePlate) {
        this.vehicleInspectionLicensePlate = vehicleInspectionLicensePlate;
    }

    public FloatFilter getVehicleInspectionKm() {
        return vehicleInspectionKm;
    }

    public FloatFilter vehicleInspectionKm() {
        if (vehicleInspectionKm == null) {
            vehicleInspectionKm = new FloatFilter();
        }
        return vehicleInspectionKm;
    }

    public void setVehicleInspectionKm(FloatFilter vehicleInspectionKm) {
        this.vehicleInspectionKm = vehicleInspectionKm;
    }

    public FloatFilter getVehicleInspectionLicenseYear() {
        return vehicleInspectionLicenseYear;
    }

    public FloatFilter vehicleInspectionLicenseYear() {
        if (vehicleInspectionLicenseYear == null) {
            vehicleInspectionLicenseYear = new FloatFilter();
        }
        return vehicleInspectionLicenseYear;
    }

    public void setVehicleInspectionLicenseYear(FloatFilter vehicleInspectionLicenseYear) {
        this.vehicleInspectionLicenseYear = vehicleInspectionLicenseYear;
    }

    public BooleanFilter getVehicleInspectionHasManual() {
        return vehicleInspectionHasManual;
    }

    public BooleanFilter vehicleInspectionHasManual() {
        if (vehicleInspectionHasManual == null) {
            vehicleInspectionHasManual = new BooleanFilter();
        }
        return vehicleInspectionHasManual;
    }

    public void setVehicleInspectionHasManual(BooleanFilter vehicleInspectionHasManual) {
        this.vehicleInspectionHasManual = vehicleInspectionHasManual;
    }

    public BooleanFilter getVehicleInspectionHasExtraKey() {
        return vehicleInspectionHasExtraKey;
    }

    public BooleanFilter vehicleInspectionHasExtraKey() {
        if (vehicleInspectionHasExtraKey == null) {
            vehicleInspectionHasExtraKey = new BooleanFilter();
        }
        return vehicleInspectionHasExtraKey;
    }

    public void setVehicleInspectionHasExtraKey(BooleanFilter vehicleInspectionHasExtraKey) {
        this.vehicleInspectionHasExtraKey = vehicleInspectionHasExtraKey;
    }

    public BooleanFilter getVehicleInspectionHasStickers() {
        return vehicleInspectionHasStickers;
    }

    public BooleanFilter vehicleInspectionHasStickers() {
        if (vehicleInspectionHasStickers == null) {
            vehicleInspectionHasStickers = new BooleanFilter();
        }
        return vehicleInspectionHasStickers;
    }

    public void setVehicleInspectionHasStickers(BooleanFilter vehicleInspectionHasStickers) {
        this.vehicleInspectionHasStickers = vehicleInspectionHasStickers;
    }

    public FuelLevelFilter getVehicleInspectionGas() {
        return vehicleInspectionGas;
    }

    public FuelLevelFilter vehicleInspectionGas() {
        if (vehicleInspectionGas == null) {
            vehicleInspectionGas = new FuelLevelFilter();
        }
        return vehicleInspectionGas;
    }

    public void setVehicleInspectionGas(FuelLevelFilter vehicleInspectionGas) {
        this.vehicleInspectionGas = vehicleInspectionGas;
    }

    public BooleanFilter getVehicleInspectionRearView() {
        return vehicleInspectionRearView;
    }

    public BooleanFilter vehicleInspectionRearView() {
        if (vehicleInspectionRearView == null) {
            vehicleInspectionRearView = new BooleanFilter();
        }
        return vehicleInspectionRearView;
    }

    public void setVehicleInspectionRearView(BooleanFilter vehicleInspectionRearView) {
        this.vehicleInspectionRearView = vehicleInspectionRearView;
    }

    public BooleanFilter getVehicleInspectionHorn() {
        return vehicleInspectionHorn;
    }

    public BooleanFilter vehicleInspectionHorn() {
        if (vehicleInspectionHorn == null) {
            vehicleInspectionHorn = new BooleanFilter();
        }
        return vehicleInspectionHorn;
    }

    public void setVehicleInspectionHorn(BooleanFilter vehicleInspectionHorn) {
        this.vehicleInspectionHorn = vehicleInspectionHorn;
    }

    public BooleanFilter getVehicleInspectionWindshieldWiper() {
        return vehicleInspectionWindshieldWiper;
    }

    public BooleanFilter vehicleInspectionWindshieldWiper() {
        if (vehicleInspectionWindshieldWiper == null) {
            vehicleInspectionWindshieldWiper = new BooleanFilter();
        }
        return vehicleInspectionWindshieldWiper;
    }

    public void setVehicleInspectionWindshieldWiper(BooleanFilter vehicleInspectionWindshieldWiper) {
        this.vehicleInspectionWindshieldWiper = vehicleInspectionWindshieldWiper;
    }

    public BooleanFilter getVehicleInspectionSquirt() {
        return vehicleInspectionSquirt;
    }

    public BooleanFilter vehicleInspectionSquirt() {
        if (vehicleInspectionSquirt == null) {
            vehicleInspectionSquirt = new BooleanFilter();
        }
        return vehicleInspectionSquirt;
    }

    public void setVehicleInspectionSquirt(BooleanFilter vehicleInspectionSquirt) {
        this.vehicleInspectionSquirt = vehicleInspectionSquirt;
    }

    public VehicleStatusFilter getVehicleInspectionInternalLight() {
        return vehicleInspectionInternalLight;
    }

    public VehicleStatusFilter vehicleInspectionInternalLight() {
        if (vehicleInspectionInternalLight == null) {
            vehicleInspectionInternalLight = new VehicleStatusFilter();
        }
        return vehicleInspectionInternalLight;
    }

    public void setVehicleInspectionInternalLight(VehicleStatusFilter vehicleInspectionInternalLight) {
        this.vehicleInspectionInternalLight = vehicleInspectionInternalLight;
    }

    public VehicleStatusFilter getVehicleInspectionPanelLight() {
        return vehicleInspectionPanelLight;
    }

    public VehicleStatusFilter vehicleInspectionPanelLight() {
        if (vehicleInspectionPanelLight == null) {
            vehicleInspectionPanelLight = new VehicleStatusFilter();
        }
        return vehicleInspectionPanelLight;
    }

    public void setVehicleInspectionPanelLight(VehicleStatusFilter vehicleInspectionPanelLight) {
        this.vehicleInspectionPanelLight = vehicleInspectionPanelLight;
    }

    public VehicleStatusFilter getVehicleInspectionHighLight() {
        return vehicleInspectionHighLight;
    }

    public VehicleStatusFilter vehicleInspectionHighLight() {
        if (vehicleInspectionHighLight == null) {
            vehicleInspectionHighLight = new VehicleStatusFilter();
        }
        return vehicleInspectionHighLight;
    }

    public void setVehicleInspectionHighLight(VehicleStatusFilter vehicleInspectionHighLight) {
        this.vehicleInspectionHighLight = vehicleInspectionHighLight;
    }

    public VehicleStatusFilter getVehicleInspectionLowLight() {
        return vehicleInspectionLowLight;
    }

    public VehicleStatusFilter vehicleInspectionLowLight() {
        if (vehicleInspectionLowLight == null) {
            vehicleInspectionLowLight = new VehicleStatusFilter();
        }
        return vehicleInspectionLowLight;
    }

    public void setVehicleInspectionLowLight(VehicleStatusFilter vehicleInspectionLowLight) {
        this.vehicleInspectionLowLight = vehicleInspectionLowLight;
    }

    public VehicleStatusFilter getVehicleInspectionTaillight() {
        return vehicleInspectionTaillight;
    }

    public VehicleStatusFilter vehicleInspectionTaillight() {
        if (vehicleInspectionTaillight == null) {
            vehicleInspectionTaillight = new VehicleStatusFilter();
        }
        return vehicleInspectionTaillight;
    }

    public void setVehicleInspectionTaillight(VehicleStatusFilter vehicleInspectionTaillight) {
        this.vehicleInspectionTaillight = vehicleInspectionTaillight;
    }

    public VehicleStatusFilter getVehicleInspectionIndicator() {
        return vehicleInspectionIndicator;
    }

    public VehicleStatusFilter vehicleInspectionIndicator() {
        if (vehicleInspectionIndicator == null) {
            vehicleInspectionIndicator = new VehicleStatusFilter();
        }
        return vehicleInspectionIndicator;
    }

    public void setVehicleInspectionIndicator(VehicleStatusFilter vehicleInspectionIndicator) {
        this.vehicleInspectionIndicator = vehicleInspectionIndicator;
    }

    public VehicleStatusFilter getVehicleInspectionBeacons() {
        return vehicleInspectionBeacons;
    }

    public VehicleStatusFilter vehicleInspectionBeacons() {
        if (vehicleInspectionBeacons == null) {
            vehicleInspectionBeacons = new VehicleStatusFilter();
        }
        return vehicleInspectionBeacons;
    }

    public void setVehicleInspectionBeacons(VehicleStatusFilter vehicleInspectionBeacons) {
        this.vehicleInspectionBeacons = vehicleInspectionBeacons;
    }

    public VehicleStatusFilter getVehicleInspectionBreakLight() {
        return vehicleInspectionBreakLight;
    }

    public VehicleStatusFilter vehicleInspectionBreakLight() {
        if (vehicleInspectionBreakLight == null) {
            vehicleInspectionBreakLight = new VehicleStatusFilter();
        }
        return vehicleInspectionBreakLight;
    }

    public void setVehicleInspectionBreakLight(VehicleStatusFilter vehicleInspectionBreakLight) {
        this.vehicleInspectionBreakLight = vehicleInspectionBreakLight;
    }

    public VehicleStatusFilter getVehicleInspectionPlateLight() {
        return vehicleInspectionPlateLight;
    }

    public VehicleStatusFilter vehicleInspectionPlateLight() {
        if (vehicleInspectionPlateLight == null) {
            vehicleInspectionPlateLight = new VehicleStatusFilter();
        }
        return vehicleInspectionPlateLight;
    }

    public void setVehicleInspectionPlateLight(VehicleStatusFilter vehicleInspectionPlateLight) {
        this.vehicleInspectionPlateLight = vehicleInspectionPlateLight;
    }

    public VehicleStatusFilter getVehicleInspectionSpeedometer() {
        return vehicleInspectionSpeedometer;
    }

    public VehicleStatusFilter vehicleInspectionSpeedometer() {
        if (vehicleInspectionSpeedometer == null) {
            vehicleInspectionSpeedometer = new VehicleStatusFilter();
        }
        return vehicleInspectionSpeedometer;
    }

    public void setVehicleInspectionSpeedometer(VehicleStatusFilter vehicleInspectionSpeedometer) {
        this.vehicleInspectionSpeedometer = vehicleInspectionSpeedometer;
    }

    public VehicleStatusFilter getVehicleInspectionTemperature() {
        return vehicleInspectionTemperature;
    }

    public VehicleStatusFilter vehicleInspectionTemperature() {
        if (vehicleInspectionTemperature == null) {
            vehicleInspectionTemperature = new VehicleStatusFilter();
        }
        return vehicleInspectionTemperature;
    }

    public void setVehicleInspectionTemperature(VehicleStatusFilter vehicleInspectionTemperature) {
        this.vehicleInspectionTemperature = vehicleInspectionTemperature;
    }

    public VehicleStatusFilter getVehicleInspectionTires() {
        return vehicleInspectionTires;
    }

    public VehicleStatusFilter vehicleInspectionTires() {
        if (vehicleInspectionTires == null) {
            vehicleInspectionTires = new VehicleStatusFilter();
        }
        return vehicleInspectionTires;
    }

    public void setVehicleInspectionTires(VehicleStatusFilter vehicleInspectionTires) {
        this.vehicleInspectionTires = vehicleInspectionTires;
    }

    public VehicleStatusFilter getVehicleInspectionStep() {
        return vehicleInspectionStep;
    }

    public VehicleStatusFilter vehicleInspectionStep() {
        if (vehicleInspectionStep == null) {
            vehicleInspectionStep = new VehicleStatusFilter();
        }
        return vehicleInspectionStep;
    }

    public void setVehicleInspectionStep(VehicleStatusFilter vehicleInspectionStep) {
        this.vehicleInspectionStep = vehicleInspectionStep;
    }

    public VehicleStatusFilter getVehicleInspectionFireExtinguisher() {
        return vehicleInspectionFireExtinguisher;
    }

    public VehicleStatusFilter vehicleInspectionFireExtinguisher() {
        if (vehicleInspectionFireExtinguisher == null) {
            vehicleInspectionFireExtinguisher = new VehicleStatusFilter();
        }
        return vehicleInspectionFireExtinguisher;
    }

    public void setVehicleInspectionFireExtinguisher(VehicleStatusFilter vehicleInspectionFireExtinguisher) {
        this.vehicleInspectionFireExtinguisher = vehicleInspectionFireExtinguisher;
    }

    public VehicleStatusFilter getVehicleInspectionSeatBelts() {
        return vehicleInspectionSeatBelts;
    }

    public VehicleStatusFilter vehicleInspectionSeatBelts() {
        if (vehicleInspectionSeatBelts == null) {
            vehicleInspectionSeatBelts = new VehicleStatusFilter();
        }
        return vehicleInspectionSeatBelts;
    }

    public void setVehicleInspectionSeatBelts(VehicleStatusFilter vehicleInspectionSeatBelts) {
        this.vehicleInspectionSeatBelts = vehicleInspectionSeatBelts;
    }

    public VehicleStatusFilter getVehicleInspectionMonkey() {
        return vehicleInspectionMonkey;
    }

    public VehicleStatusFilter vehicleInspectionMonkey() {
        if (vehicleInspectionMonkey == null) {
            vehicleInspectionMonkey = new VehicleStatusFilter();
        }
        return vehicleInspectionMonkey;
    }

    public void setVehicleInspectionMonkey(VehicleStatusFilter vehicleInspectionMonkey) {
        this.vehicleInspectionMonkey = vehicleInspectionMonkey;
    }

    public VehicleStatusFilter getVehicleInspectionTireIron() {
        return vehicleInspectionTireIron;
    }

    public VehicleStatusFilter vehicleInspectionTireIron() {
        if (vehicleInspectionTireIron == null) {
            vehicleInspectionTireIron = new VehicleStatusFilter();
        }
        return vehicleInspectionTireIron;
    }

    public void setVehicleInspectionTireIron(VehicleStatusFilter vehicleInspectionTireIron) {
        this.vehicleInspectionTireIron = vehicleInspectionTireIron;
    }

    public VehicleStatusFilter getVehicleInspectionRadiatorCap() {
        return vehicleInspectionRadiatorCap;
    }

    public VehicleStatusFilter vehicleInspectionRadiatorCap() {
        if (vehicleInspectionRadiatorCap == null) {
            vehicleInspectionRadiatorCap = new VehicleStatusFilter();
        }
        return vehicleInspectionRadiatorCap;
    }

    public void setVehicleInspectionRadiatorCap(VehicleStatusFilter vehicleInspectionRadiatorCap) {
        this.vehicleInspectionRadiatorCap = vehicleInspectionRadiatorCap;
    }

    public VehicleStatusFilter getVehicleInspectionTriangle() {
        return vehicleInspectionTriangle;
    }

    public VehicleStatusFilter vehicleInspectionTriangle() {
        if (vehicleInspectionTriangle == null) {
            vehicleInspectionTriangle = new VehicleStatusFilter();
        }
        return vehicleInspectionTriangle;
    }

    public void setVehicleInspectionTriangle(VehicleStatusFilter vehicleInspectionTriangle) {
        this.vehicleInspectionTriangle = vehicleInspectionTriangle;
    }

    public VehicleStatusFilter getVehicleInspectionServiceBrake() {
        return vehicleInspectionServiceBrake;
    }

    public VehicleStatusFilter vehicleInspectionServiceBrake() {
        if (vehicleInspectionServiceBrake == null) {
            vehicleInspectionServiceBrake = new VehicleStatusFilter();
        }
        return vehicleInspectionServiceBrake;
    }

    public void setVehicleInspectionServiceBrake(VehicleStatusFilter vehicleInspectionServiceBrake) {
        this.vehicleInspectionServiceBrake = vehicleInspectionServiceBrake;
    }

    public VehicleStatusFilter getVehicleInspectionParkingBrake() {
        return vehicleInspectionParkingBrake;
    }

    public VehicleStatusFilter vehicleInspectionParkingBrake() {
        if (vehicleInspectionParkingBrake == null) {
            vehicleInspectionParkingBrake = new VehicleStatusFilter();
        }
        return vehicleInspectionParkingBrake;
    }

    public void setVehicleInspectionParkingBrake(VehicleStatusFilter vehicleInspectionParkingBrake) {
        this.vehicleInspectionParkingBrake = vehicleInspectionParkingBrake;
    }

    public VehicleStatusFilter getVehicleInspectionOilLeaks() {
        return vehicleInspectionOilLeaks;
    }

    public VehicleStatusFilter vehicleInspectionOilLeaks() {
        if (vehicleInspectionOilLeaks == null) {
            vehicleInspectionOilLeaks = new VehicleStatusFilter();
        }
        return vehicleInspectionOilLeaks;
    }

    public void setVehicleInspectionOilLeaks(VehicleStatusFilter vehicleInspectionOilLeaks) {
        this.vehicleInspectionOilLeaks = vehicleInspectionOilLeaks;
    }

    public VehicleStatusFilter getVehicleInspectionGlassActuator() {
        return vehicleInspectionGlassActuator;
    }

    public VehicleStatusFilter vehicleInspectionGlassActuator() {
        if (vehicleInspectionGlassActuator == null) {
            vehicleInspectionGlassActuator = new VehicleStatusFilter();
        }
        return vehicleInspectionGlassActuator;
    }

    public void setVehicleInspectionGlassActuator(VehicleStatusFilter vehicleInspectionGlassActuator) {
        this.vehicleInspectionGlassActuator = vehicleInspectionGlassActuator;
    }

    public VehicleStatusFilter getVehicleInspectionVehicleCleaning() {
        return vehicleInspectionVehicleCleaning;
    }

    public VehicleStatusFilter vehicleInspectionVehicleCleaning() {
        if (vehicleInspectionVehicleCleaning == null) {
            vehicleInspectionVehicleCleaning = new VehicleStatusFilter();
        }
        return vehicleInspectionVehicleCleaning;
    }

    public void setVehicleInspectionVehicleCleaning(VehicleStatusFilter vehicleInspectionVehicleCleaning) {
        this.vehicleInspectionVehicleCleaning = vehicleInspectionVehicleCleaning;
    }

    public VehicleStatusFilter getVehicleInspectionSeatState() {
        return vehicleInspectionSeatState;
    }

    public VehicleStatusFilter vehicleInspectionSeatState() {
        if (vehicleInspectionSeatState == null) {
            vehicleInspectionSeatState = new VehicleStatusFilter();
        }
        return vehicleInspectionSeatState;
    }

    public void setVehicleInspectionSeatState(VehicleStatusFilter vehicleInspectionSeatState) {
        this.vehicleInspectionSeatState = vehicleInspectionSeatState;
    }

    public VehicleStatusFilter getVehicleInspectionExhausts() {
        return vehicleInspectionExhausts;
    }

    public VehicleStatusFilter vehicleInspectionExhausts() {
        if (vehicleInspectionExhausts == null) {
            vehicleInspectionExhausts = new VehicleStatusFilter();
        }
        return vehicleInspectionExhausts;
    }

    public void setVehicleInspectionExhausts(VehicleStatusFilter vehicleInspectionExhausts) {
        this.vehicleInspectionExhausts = vehicleInspectionExhausts;
    }

    public StringFilter getVehicleInspectionsObs() {
        return vehicleInspectionsObs;
    }

    public StringFilter vehicleInspectionsObs() {
        if (vehicleInspectionsObs == null) {
            vehicleInspectionsObs = new StringFilter();
        }
        return vehicleInspectionsObs;
    }

    public void setVehicleInspectionsObs(StringFilter vehicleInspectionsObs) {
        this.vehicleInspectionsObs = vehicleInspectionsObs;
    }

    public StringFilter getVehicleInspectionsSignedUrl() {
        return vehicleInspectionsSignedUrl;
    }

    public StringFilter vehicleInspectionsSignedUrl() {
        if (vehicleInspectionsSignedUrl == null) {
            vehicleInspectionsSignedUrl = new StringFilter();
        }
        return vehicleInspectionsSignedUrl;
    }

    public void setVehicleInspectionsSignedUrl(StringFilter vehicleInspectionsSignedUrl) {
        this.vehicleInspectionsSignedUrl = vehicleInspectionsSignedUrl;
    }

    public LongFilter getVehicleInspectionsImagensId() {
        return vehicleInspectionsImagensId;
    }

    public LongFilter vehicleInspectionsImagensId() {
        if (vehicleInspectionsImagensId == null) {
            vehicleInspectionsImagensId = new LongFilter();
        }
        return vehicleInspectionsImagensId;
    }

    public void setVehicleInspectionsImagensId(LongFilter vehicleInspectionsImagensId) {
        this.vehicleInspectionsImagensId = vehicleInspectionsImagensId;
    }

    public LongFilter getVehicleControlsId() {
        return vehicleControlsId;
    }

    public LongFilter vehicleControlsId() {
        if (vehicleControlsId == null) {
            vehicleControlsId = new LongFilter();
        }
        return vehicleControlsId;
    }

    public void setVehicleControlsId(LongFilter vehicleControlsId) {
        this.vehicleControlsId = vehicleControlsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VehicleInspectionsCriteria that = (VehicleInspectionsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(vehicleInspectionDate, that.vehicleInspectionDate) &&
            Objects.equals(vehicleInspectionStatus, that.vehicleInspectionStatus) &&
            Objects.equals(vehicleInspectionModel, that.vehicleInspectionModel) &&
            Objects.equals(vehicleInspectionLicensePlate, that.vehicleInspectionLicensePlate) &&
            Objects.equals(vehicleInspectionKm, that.vehicleInspectionKm) &&
            Objects.equals(vehicleInspectionLicenseYear, that.vehicleInspectionLicenseYear) &&
            Objects.equals(vehicleInspectionHasManual, that.vehicleInspectionHasManual) &&
            Objects.equals(vehicleInspectionHasExtraKey, that.vehicleInspectionHasExtraKey) &&
            Objects.equals(vehicleInspectionHasStickers, that.vehicleInspectionHasStickers) &&
            Objects.equals(vehicleInspectionGas, that.vehicleInspectionGas) &&
            Objects.equals(vehicleInspectionRearView, that.vehicleInspectionRearView) &&
            Objects.equals(vehicleInspectionHorn, that.vehicleInspectionHorn) &&
            Objects.equals(vehicleInspectionWindshieldWiper, that.vehicleInspectionWindshieldWiper) &&
            Objects.equals(vehicleInspectionSquirt, that.vehicleInspectionSquirt) &&
            Objects.equals(vehicleInspectionInternalLight, that.vehicleInspectionInternalLight) &&
            Objects.equals(vehicleInspectionPanelLight, that.vehicleInspectionPanelLight) &&
            Objects.equals(vehicleInspectionHighLight, that.vehicleInspectionHighLight) &&
            Objects.equals(vehicleInspectionLowLight, that.vehicleInspectionLowLight) &&
            Objects.equals(vehicleInspectionTaillight, that.vehicleInspectionTaillight) &&
            Objects.equals(vehicleInspectionIndicator, that.vehicleInspectionIndicator) &&
            Objects.equals(vehicleInspectionBeacons, that.vehicleInspectionBeacons) &&
            Objects.equals(vehicleInspectionBreakLight, that.vehicleInspectionBreakLight) &&
            Objects.equals(vehicleInspectionPlateLight, that.vehicleInspectionPlateLight) &&
            Objects.equals(vehicleInspectionSpeedometer, that.vehicleInspectionSpeedometer) &&
            Objects.equals(vehicleInspectionTemperature, that.vehicleInspectionTemperature) &&
            Objects.equals(vehicleInspectionTires, that.vehicleInspectionTires) &&
            Objects.equals(vehicleInspectionStep, that.vehicleInspectionStep) &&
            Objects.equals(vehicleInspectionFireExtinguisher, that.vehicleInspectionFireExtinguisher) &&
            Objects.equals(vehicleInspectionSeatBelts, that.vehicleInspectionSeatBelts) &&
            Objects.equals(vehicleInspectionMonkey, that.vehicleInspectionMonkey) &&
            Objects.equals(vehicleInspectionTireIron, that.vehicleInspectionTireIron) &&
            Objects.equals(vehicleInspectionRadiatorCap, that.vehicleInspectionRadiatorCap) &&
            Objects.equals(vehicleInspectionTriangle, that.vehicleInspectionTriangle) &&
            Objects.equals(vehicleInspectionServiceBrake, that.vehicleInspectionServiceBrake) &&
            Objects.equals(vehicleInspectionParkingBrake, that.vehicleInspectionParkingBrake) &&
            Objects.equals(vehicleInspectionOilLeaks, that.vehicleInspectionOilLeaks) &&
            Objects.equals(vehicleInspectionGlassActuator, that.vehicleInspectionGlassActuator) &&
            Objects.equals(vehicleInspectionVehicleCleaning, that.vehicleInspectionVehicleCleaning) &&
            Objects.equals(vehicleInspectionSeatState, that.vehicleInspectionSeatState) &&
            Objects.equals(vehicleInspectionExhausts, that.vehicleInspectionExhausts) &&
            Objects.equals(vehicleInspectionsObs, that.vehicleInspectionsObs) &&
            Objects.equals(vehicleInspectionsSignedUrl, that.vehicleInspectionsSignedUrl) &&
            Objects.equals(vehicleInspectionsImagensId, that.vehicleInspectionsImagensId) &&
            Objects.equals(vehicleControlsId, that.vehicleControlsId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            vehicleInspectionDate,
            vehicleInspectionStatus,
            vehicleInspectionModel,
            vehicleInspectionLicensePlate,
            vehicleInspectionKm,
            vehicleInspectionLicenseYear,
            vehicleInspectionHasManual,
            vehicleInspectionHasExtraKey,
            vehicleInspectionHasStickers,
            vehicleInspectionGas,
            vehicleInspectionRearView,
            vehicleInspectionHorn,
            vehicleInspectionWindshieldWiper,
            vehicleInspectionSquirt,
            vehicleInspectionInternalLight,
            vehicleInspectionPanelLight,
            vehicleInspectionHighLight,
            vehicleInspectionLowLight,
            vehicleInspectionTaillight,
            vehicleInspectionIndicator,
            vehicleInspectionBeacons,
            vehicleInspectionBreakLight,
            vehicleInspectionPlateLight,
            vehicleInspectionSpeedometer,
            vehicleInspectionTemperature,
            vehicleInspectionTires,
            vehicleInspectionStep,
            vehicleInspectionFireExtinguisher,
            vehicleInspectionSeatBelts,
            vehicleInspectionMonkey,
            vehicleInspectionTireIron,
            vehicleInspectionRadiatorCap,
            vehicleInspectionTriangle,
            vehicleInspectionServiceBrake,
            vehicleInspectionParkingBrake,
            vehicleInspectionOilLeaks,
            vehicleInspectionGlassActuator,
            vehicleInspectionVehicleCleaning,
            vehicleInspectionSeatState,
            vehicleInspectionExhausts,
            vehicleInspectionsObs,
            vehicleInspectionsSignedUrl,
            vehicleInspectionsImagensId,
            vehicleControlsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleInspectionsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (vehicleInspectionDate != null ? "vehicleInspectionDate=" + vehicleInspectionDate + ", " : "") +
            (vehicleInspectionStatus != null ? "vehicleInspectionStatus=" + vehicleInspectionStatus + ", " : "") +
            (vehicleInspectionModel != null ? "vehicleInspectionModel=" + vehicleInspectionModel + ", " : "") +
            (vehicleInspectionLicensePlate != null ? "vehicleInspectionLicensePlate=" + vehicleInspectionLicensePlate + ", " : "") +
            (vehicleInspectionKm != null ? "vehicleInspectionKm=" + vehicleInspectionKm + ", " : "") +
            (vehicleInspectionLicenseYear != null ? "vehicleInspectionLicenseYear=" + vehicleInspectionLicenseYear + ", " : "") +
            (vehicleInspectionHasManual != null ? "vehicleInspectionHasManual=" + vehicleInspectionHasManual + ", " : "") +
            (vehicleInspectionHasExtraKey != null ? "vehicleInspectionHasExtraKey=" + vehicleInspectionHasExtraKey + ", " : "") +
            (vehicleInspectionHasStickers != null ? "vehicleInspectionHasStickers=" + vehicleInspectionHasStickers + ", " : "") +
            (vehicleInspectionGas != null ? "vehicleInspectionGas=" + vehicleInspectionGas + ", " : "") +
            (vehicleInspectionRearView != null ? "vehicleInspectionRearView=" + vehicleInspectionRearView + ", " : "") +
            (vehicleInspectionHorn != null ? "vehicleInspectionHorn=" + vehicleInspectionHorn + ", " : "") +
            (vehicleInspectionWindshieldWiper != null ? "vehicleInspectionWindshieldWiper=" + vehicleInspectionWindshieldWiper + ", " : "") +
            (vehicleInspectionSquirt != null ? "vehicleInspectionSquirt=" + vehicleInspectionSquirt + ", " : "") +
            (vehicleInspectionInternalLight != null ? "vehicleInspectionInternalLight=" + vehicleInspectionInternalLight + ", " : "") +
            (vehicleInspectionPanelLight != null ? "vehicleInspectionPanelLight=" + vehicleInspectionPanelLight + ", " : "") +
            (vehicleInspectionHighLight != null ? "vehicleInspectionHighLight=" + vehicleInspectionHighLight + ", " : "") +
            (vehicleInspectionLowLight != null ? "vehicleInspectionLowLight=" + vehicleInspectionLowLight + ", " : "") +
            (vehicleInspectionTaillight != null ? "vehicleInspectionTaillight=" + vehicleInspectionTaillight + ", " : "") +
            (vehicleInspectionIndicator != null ? "vehicleInspectionIndicator=" + vehicleInspectionIndicator + ", " : "") +
            (vehicleInspectionBeacons != null ? "vehicleInspectionBeacons=" + vehicleInspectionBeacons + ", " : "") +
            (vehicleInspectionBreakLight != null ? "vehicleInspectionBreakLight=" + vehicleInspectionBreakLight + ", " : "") +
            (vehicleInspectionPlateLight != null ? "vehicleInspectionPlateLight=" + vehicleInspectionPlateLight + ", " : "") +
            (vehicleInspectionSpeedometer != null ? "vehicleInspectionSpeedometer=" + vehicleInspectionSpeedometer + ", " : "") +
            (vehicleInspectionTemperature != null ? "vehicleInspectionTemperature=" + vehicleInspectionTemperature + ", " : "") +
            (vehicleInspectionTires != null ? "vehicleInspectionTires=" + vehicleInspectionTires + ", " : "") +
            (vehicleInspectionStep != null ? "vehicleInspectionStep=" + vehicleInspectionStep + ", " : "") +
            (vehicleInspectionFireExtinguisher != null ? "vehicleInspectionFireExtinguisher=" + vehicleInspectionFireExtinguisher + ", " : "") +
            (vehicleInspectionSeatBelts != null ? "vehicleInspectionSeatBelts=" + vehicleInspectionSeatBelts + ", " : "") +
            (vehicleInspectionMonkey != null ? "vehicleInspectionMonkey=" + vehicleInspectionMonkey + ", " : "") +
            (vehicleInspectionTireIron != null ? "vehicleInspectionTireIron=" + vehicleInspectionTireIron + ", " : "") +
            (vehicleInspectionRadiatorCap != null ? "vehicleInspectionRadiatorCap=" + vehicleInspectionRadiatorCap + ", " : "") +
            (vehicleInspectionTriangle != null ? "vehicleInspectionTriangle=" + vehicleInspectionTriangle + ", " : "") +
            (vehicleInspectionServiceBrake != null ? "vehicleInspectionServiceBrake=" + vehicleInspectionServiceBrake + ", " : "") +
            (vehicleInspectionParkingBrake != null ? "vehicleInspectionParkingBrake=" + vehicleInspectionParkingBrake + ", " : "") +
            (vehicleInspectionOilLeaks != null ? "vehicleInspectionOilLeaks=" + vehicleInspectionOilLeaks + ", " : "") +
            (vehicleInspectionGlassActuator != null ? "vehicleInspectionGlassActuator=" + vehicleInspectionGlassActuator + ", " : "") +
            (vehicleInspectionVehicleCleaning != null ? "vehicleInspectionVehicleCleaning=" + vehicleInspectionVehicleCleaning + ", " : "") +
            (vehicleInspectionSeatState != null ? "vehicleInspectionSeatState=" + vehicleInspectionSeatState + ", " : "") +
            (vehicleInspectionExhausts != null ? "vehicleInspectionExhausts=" + vehicleInspectionExhausts + ", " : "") +
            (vehicleInspectionsObs != null ? "vehicleInspectionsObs=" + vehicleInspectionsObs + ", " : "") +
            (vehicleInspectionsSignedUrl != null ? "vehicleInspectionsSignedUrl=" + vehicleInspectionsSignedUrl + ", " : "") +
            (vehicleInspectionsImagensId != null ? "vehicleInspectionsImagensId=" + vehicleInspectionsImagensId + ", " : "") +
            (vehicleControlsId != null ? "vehicleControlsId=" + vehicleControlsId + ", " : "") +
            "}";
    }
}
