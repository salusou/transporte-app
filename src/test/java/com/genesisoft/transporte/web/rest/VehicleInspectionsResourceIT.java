package com.genesisoft.transporte.web.rest;

import static com.genesisoft.transporte.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.VehicleControlItem;
import com.genesisoft.transporte.domain.VehicleInspections;
import com.genesisoft.transporte.domain.VehicleInspectionsImagens;
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
import com.genesisoft.transporte.repository.VehicleInspectionsRepository;
import com.genesisoft.transporte.service.criteria.VehicleInspectionsCriteria;
import com.genesisoft.transporte.service.dto.VehicleInspectionsDTO;
import com.genesisoft.transporte.service.mapper.VehicleInspectionsMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VehicleInspectionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VehicleInspectionsResourceIT {

    private static final ZonedDateTime DEFAULT_VEHICLE_INSPECTION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_VEHICLE_INSPECTION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_VEHICLE_INSPECTION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final InspectionStatus DEFAULT_VEHICLE_INSPECTION_STATUS = InspectionStatus.ARRIVAL;
    private static final InspectionStatus UPDATED_VEHICLE_INSPECTION_STATUS = InspectionStatus.DEPARTURE;

    private static final String DEFAULT_VEHICLE_INSPECTION_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_INSPECTION_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_VEHICLE_INSPECTION_LICENSE_PLATE = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_INSPECTION_LICENSE_PLATE = "BBBBBBBBBB";

    private static final Float DEFAULT_VEHICLE_INSPECTION_KM = 1F;
    private static final Float UPDATED_VEHICLE_INSPECTION_KM = 2F;
    private static final Float SMALLER_VEHICLE_INSPECTION_KM = 1F - 1F;

    private static final Float DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR = 1F;
    private static final Float UPDATED_VEHICLE_INSPECTION_LICENSE_YEAR = 2F;
    private static final Float SMALLER_VEHICLE_INSPECTION_LICENSE_YEAR = 1F - 1F;

    private static final Boolean DEFAULT_VEHICLE_INSPECTION_HAS_MANUAL = false;
    private static final Boolean UPDATED_VEHICLE_INSPECTION_HAS_MANUAL = true;

    private static final Boolean DEFAULT_VEHICLE_INSPECTION_HAS_EXTRA_KEY = false;
    private static final Boolean UPDATED_VEHICLE_INSPECTION_HAS_EXTRA_KEY = true;

    private static final Boolean DEFAULT_VEHICLE_INSPECTION_HAS_STICKERS = false;
    private static final Boolean UPDATED_VEHICLE_INSPECTION_HAS_STICKERS = true;

    private static final FuelLevel DEFAULT_VEHICLE_INSPECTION_GAS = FuelLevel.RESERVE;
    private static final FuelLevel UPDATED_VEHICLE_INSPECTION_GAS = FuelLevel.LITTLE;

    private static final Boolean DEFAULT_VEHICLE_INSPECTION_REAR_VIEW = false;
    private static final Boolean UPDATED_VEHICLE_INSPECTION_REAR_VIEW = true;

    private static final Boolean DEFAULT_VEHICLE_INSPECTION_HORN = false;
    private static final Boolean UPDATED_VEHICLE_INSPECTION_HORN = true;

    private static final Boolean DEFAULT_VEHICLE_INSPECTION_WINDSHIELD_WIPER = false;
    private static final Boolean UPDATED_VEHICLE_INSPECTION_WINDSHIELD_WIPER = true;

    private static final Boolean DEFAULT_VEHICLE_INSPECTION_SQUIRT = false;
    private static final Boolean UPDATED_VEHICLE_INSPECTION_SQUIRT = true;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_INTERNAL_LIGHT = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_INTERNAL_LIGHT = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_PANEL_LIGHT = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_PANEL_LIGHT = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_HIGH_LIGHT = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_HIGH_LIGHT = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_LOW_LIGHT = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_LOW_LIGHT = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_TAILLIGHT = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_TAILLIGHT = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_INDICATOR = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_INDICATOR = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_BEACONS = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_BEACONS = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_BREAK_LIGHT = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_BREAK_LIGHT = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_PLATE_LIGHT = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_PLATE_LIGHT = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_SPEEDOMETER = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_SPEEDOMETER = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_TEMPERATURE = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_TEMPERATURE = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_TIRES = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_TIRES = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_STEP = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_STEP = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_FIRE_EXTINGUISHER = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_FIRE_EXTINGUISHER = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_SEAT_BELTS = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_SEAT_BELTS = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_MONKEY = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_MONKEY = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_TIRE_IRON = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_TIRE_IRON = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_RADIATOR_CAP = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_RADIATOR_CAP = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_TRIANGLE = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_TRIANGLE = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_SERVICE_BRAKE = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_SERVICE_BRAKE = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_PARKING_BRAKE = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_PARKING_BRAKE = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_OIL_LEAKS = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_OIL_LEAKS = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_GLASS_ACTUATOR = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_GLASS_ACTUATOR = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_VEHICLE_CLEANING = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_VEHICLE_CLEANING = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_SEAT_STATE = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_SEAT_STATE = VehicleStatus.CLEAN;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTION_EXHAUSTS = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTION_EXHAUSTS = VehicleStatus.CLEAN;

    private static final String DEFAULT_VEHICLE_INSPECTIONS_OBS = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_INSPECTIONS_OBS = "BBBBBBBBBB";

    private static final String DEFAULT_VEHICLE_INSPECTIONS_SIGNED_URL = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_INSPECTIONS_SIGNED_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vehicle-inspections";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VehicleInspectionsRepository vehicleInspectionsRepository;

    @Autowired
    private VehicleInspectionsMapper vehicleInspectionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehicleInspectionsMockMvc;

    private VehicleInspections vehicleInspections;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleInspections createEntity(EntityManager em) {
        VehicleInspections vehicleInspections = new VehicleInspections()
            .vehicleInspectionDate(DEFAULT_VEHICLE_INSPECTION_DATE)
            .vehicleInspectionStatus(DEFAULT_VEHICLE_INSPECTION_STATUS)
            .vehicleInspectionModel(DEFAULT_VEHICLE_INSPECTION_MODEL)
            .vehicleInspectionLicensePlate(DEFAULT_VEHICLE_INSPECTION_LICENSE_PLATE)
            .vehicleInspectionKm(DEFAULT_VEHICLE_INSPECTION_KM)
            .vehicleInspectionLicenseYear(DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR)
            .vehicleInspectionHasManual(DEFAULT_VEHICLE_INSPECTION_HAS_MANUAL)
            .vehicleInspectionHasExtraKey(DEFAULT_VEHICLE_INSPECTION_HAS_EXTRA_KEY)
            .vehicleInspectionHasStickers(DEFAULT_VEHICLE_INSPECTION_HAS_STICKERS)
            .vehicleInspectionGas(DEFAULT_VEHICLE_INSPECTION_GAS)
            .vehicleInspectionRearView(DEFAULT_VEHICLE_INSPECTION_REAR_VIEW)
            .vehicleInspectionHorn(DEFAULT_VEHICLE_INSPECTION_HORN)
            .vehicleInspectionWindshieldWiper(DEFAULT_VEHICLE_INSPECTION_WINDSHIELD_WIPER)
            .vehicleInspectionSquirt(DEFAULT_VEHICLE_INSPECTION_SQUIRT)
            .vehicleInspectionInternalLight(DEFAULT_VEHICLE_INSPECTION_INTERNAL_LIGHT)
            .vehicleInspectionPanelLight(DEFAULT_VEHICLE_INSPECTION_PANEL_LIGHT)
            .vehicleInspectionHighLight(DEFAULT_VEHICLE_INSPECTION_HIGH_LIGHT)
            .vehicleInspectionLowLight(DEFAULT_VEHICLE_INSPECTION_LOW_LIGHT)
            .vehicleInspectionTaillight(DEFAULT_VEHICLE_INSPECTION_TAILLIGHT)
            .vehicleInspectionIndicator(DEFAULT_VEHICLE_INSPECTION_INDICATOR)
            .vehicleInspectionBeacons(DEFAULT_VEHICLE_INSPECTION_BEACONS)
            .vehicleInspectionBreakLight(DEFAULT_VEHICLE_INSPECTION_BREAK_LIGHT)
            .vehicleInspectionPlateLight(DEFAULT_VEHICLE_INSPECTION_PLATE_LIGHT)
            .vehicleInspectionSpeedometer(DEFAULT_VEHICLE_INSPECTION_SPEEDOMETER)
            .vehicleInspectionTemperature(DEFAULT_VEHICLE_INSPECTION_TEMPERATURE)
            .vehicleInspectionTires(DEFAULT_VEHICLE_INSPECTION_TIRES)
            .vehicleInspectionStep(DEFAULT_VEHICLE_INSPECTION_STEP)
            .vehicleInspectionFireExtinguisher(DEFAULT_VEHICLE_INSPECTION_FIRE_EXTINGUISHER)
            .vehicleInspectionSeatBelts(DEFAULT_VEHICLE_INSPECTION_SEAT_BELTS)
            .vehicleInspectionMonkey(DEFAULT_VEHICLE_INSPECTION_MONKEY)
            .vehicleInspectionTireIron(DEFAULT_VEHICLE_INSPECTION_TIRE_IRON)
            .vehicleInspectionRadiatorCap(DEFAULT_VEHICLE_INSPECTION_RADIATOR_CAP)
            .vehicleInspectionTriangle(DEFAULT_VEHICLE_INSPECTION_TRIANGLE)
            .vehicleInspectionServiceBrake(DEFAULT_VEHICLE_INSPECTION_SERVICE_BRAKE)
            .vehicleInspectionParkingBrake(DEFAULT_VEHICLE_INSPECTION_PARKING_BRAKE)
            .vehicleInspectionOilLeaks(DEFAULT_VEHICLE_INSPECTION_OIL_LEAKS)
            .vehicleInspectionGlassActuator(DEFAULT_VEHICLE_INSPECTION_GLASS_ACTUATOR)
            .vehicleInspectionVehicleCleaning(DEFAULT_VEHICLE_INSPECTION_VEHICLE_CLEANING)
            .vehicleInspectionSeatState(DEFAULT_VEHICLE_INSPECTION_SEAT_STATE)
            .vehicleInspectionExhausts(DEFAULT_VEHICLE_INSPECTION_EXHAUSTS)
            .vehicleInspectionsObs(DEFAULT_VEHICLE_INSPECTIONS_OBS)
            .vehicleInspectionsSignedUrl(DEFAULT_VEHICLE_INSPECTIONS_SIGNED_URL);
        return vehicleInspections;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleInspections createUpdatedEntity(EntityManager em) {
        VehicleInspections vehicleInspections = new VehicleInspections()
            .vehicleInspectionDate(UPDATED_VEHICLE_INSPECTION_DATE)
            .vehicleInspectionStatus(UPDATED_VEHICLE_INSPECTION_STATUS)
            .vehicleInspectionModel(UPDATED_VEHICLE_INSPECTION_MODEL)
            .vehicleInspectionLicensePlate(UPDATED_VEHICLE_INSPECTION_LICENSE_PLATE)
            .vehicleInspectionKm(UPDATED_VEHICLE_INSPECTION_KM)
            .vehicleInspectionLicenseYear(UPDATED_VEHICLE_INSPECTION_LICENSE_YEAR)
            .vehicleInspectionHasManual(UPDATED_VEHICLE_INSPECTION_HAS_MANUAL)
            .vehicleInspectionHasExtraKey(UPDATED_VEHICLE_INSPECTION_HAS_EXTRA_KEY)
            .vehicleInspectionHasStickers(UPDATED_VEHICLE_INSPECTION_HAS_STICKERS)
            .vehicleInspectionGas(UPDATED_VEHICLE_INSPECTION_GAS)
            .vehicleInspectionRearView(UPDATED_VEHICLE_INSPECTION_REAR_VIEW)
            .vehicleInspectionHorn(UPDATED_VEHICLE_INSPECTION_HORN)
            .vehicleInspectionWindshieldWiper(UPDATED_VEHICLE_INSPECTION_WINDSHIELD_WIPER)
            .vehicleInspectionSquirt(UPDATED_VEHICLE_INSPECTION_SQUIRT)
            .vehicleInspectionInternalLight(UPDATED_VEHICLE_INSPECTION_INTERNAL_LIGHT)
            .vehicleInspectionPanelLight(UPDATED_VEHICLE_INSPECTION_PANEL_LIGHT)
            .vehicleInspectionHighLight(UPDATED_VEHICLE_INSPECTION_HIGH_LIGHT)
            .vehicleInspectionLowLight(UPDATED_VEHICLE_INSPECTION_LOW_LIGHT)
            .vehicleInspectionTaillight(UPDATED_VEHICLE_INSPECTION_TAILLIGHT)
            .vehicleInspectionIndicator(UPDATED_VEHICLE_INSPECTION_INDICATOR)
            .vehicleInspectionBeacons(UPDATED_VEHICLE_INSPECTION_BEACONS)
            .vehicleInspectionBreakLight(UPDATED_VEHICLE_INSPECTION_BREAK_LIGHT)
            .vehicleInspectionPlateLight(UPDATED_VEHICLE_INSPECTION_PLATE_LIGHT)
            .vehicleInspectionSpeedometer(UPDATED_VEHICLE_INSPECTION_SPEEDOMETER)
            .vehicleInspectionTemperature(UPDATED_VEHICLE_INSPECTION_TEMPERATURE)
            .vehicleInspectionTires(UPDATED_VEHICLE_INSPECTION_TIRES)
            .vehicleInspectionStep(UPDATED_VEHICLE_INSPECTION_STEP)
            .vehicleInspectionFireExtinguisher(UPDATED_VEHICLE_INSPECTION_FIRE_EXTINGUISHER)
            .vehicleInspectionSeatBelts(UPDATED_VEHICLE_INSPECTION_SEAT_BELTS)
            .vehicleInspectionMonkey(UPDATED_VEHICLE_INSPECTION_MONKEY)
            .vehicleInspectionTireIron(UPDATED_VEHICLE_INSPECTION_TIRE_IRON)
            .vehicleInspectionRadiatorCap(UPDATED_VEHICLE_INSPECTION_RADIATOR_CAP)
            .vehicleInspectionTriangle(UPDATED_VEHICLE_INSPECTION_TRIANGLE)
            .vehicleInspectionServiceBrake(UPDATED_VEHICLE_INSPECTION_SERVICE_BRAKE)
            .vehicleInspectionParkingBrake(UPDATED_VEHICLE_INSPECTION_PARKING_BRAKE)
            .vehicleInspectionOilLeaks(UPDATED_VEHICLE_INSPECTION_OIL_LEAKS)
            .vehicleInspectionGlassActuator(UPDATED_VEHICLE_INSPECTION_GLASS_ACTUATOR)
            .vehicleInspectionVehicleCleaning(UPDATED_VEHICLE_INSPECTION_VEHICLE_CLEANING)
            .vehicleInspectionSeatState(UPDATED_VEHICLE_INSPECTION_SEAT_STATE)
            .vehicleInspectionExhausts(UPDATED_VEHICLE_INSPECTION_EXHAUSTS)
            .vehicleInspectionsObs(UPDATED_VEHICLE_INSPECTIONS_OBS)
            .vehicleInspectionsSignedUrl(UPDATED_VEHICLE_INSPECTIONS_SIGNED_URL);
        return vehicleInspections;
    }

    @BeforeEach
    public void initTest() {
        vehicleInspections = createEntity(em);
    }

    @Test
    @Transactional
    void createVehicleInspections() throws Exception {
        int databaseSizeBeforeCreate = vehicleInspectionsRepository.findAll().size();
        // Create the VehicleInspections
        VehicleInspectionsDTO vehicleInspectionsDTO = vehicleInspectionsMapper.toDto(vehicleInspections);
        restVehicleInspectionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VehicleInspections in the database
        List<VehicleInspections> vehicleInspectionsList = vehicleInspectionsRepository.findAll();
        assertThat(vehicleInspectionsList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleInspections testVehicleInspections = vehicleInspectionsList.get(vehicleInspectionsList.size() - 1);
        assertThat(testVehicleInspections.getVehicleInspectionDate()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_DATE);
        assertThat(testVehicleInspections.getVehicleInspectionStatus()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_STATUS);
        assertThat(testVehicleInspections.getVehicleInspectionModel()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_MODEL);
        assertThat(testVehicleInspections.getVehicleInspectionLicensePlate()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_LICENSE_PLATE);
        assertThat(testVehicleInspections.getVehicleInspectionKm()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_KM);
        assertThat(testVehicleInspections.getVehicleInspectionLicenseYear()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR);
        assertThat(testVehicleInspections.getVehicleInspectionHasManual()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_HAS_MANUAL);
        assertThat(testVehicleInspections.getVehicleInspectionHasExtraKey()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_HAS_EXTRA_KEY);
        assertThat(testVehicleInspections.getVehicleInspectionHasStickers()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_HAS_STICKERS);
        assertThat(testVehicleInspections.getVehicleInspectionGas()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_GAS);
        assertThat(testVehicleInspections.getVehicleInspectionRearView()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_REAR_VIEW);
        assertThat(testVehicleInspections.getVehicleInspectionHorn()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_HORN);
        assertThat(testVehicleInspections.getVehicleInspectionWindshieldWiper()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_WINDSHIELD_WIPER);
        assertThat(testVehicleInspections.getVehicleInspectionSquirt()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_SQUIRT);
        assertThat(testVehicleInspections.getVehicleInspectionInternalLight()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_INTERNAL_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionPanelLight()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_PANEL_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionHighLight()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_HIGH_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionLowLight()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_LOW_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionTaillight()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_TAILLIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionIndicator()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_INDICATOR);
        assertThat(testVehicleInspections.getVehicleInspectionBeacons()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_BEACONS);
        assertThat(testVehicleInspections.getVehicleInspectionBreakLight()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_BREAK_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionPlateLight()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_PLATE_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionSpeedometer()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_SPEEDOMETER);
        assertThat(testVehicleInspections.getVehicleInspectionTemperature()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_TEMPERATURE);
        assertThat(testVehicleInspections.getVehicleInspectionTires()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_TIRES);
        assertThat(testVehicleInspections.getVehicleInspectionStep()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_STEP);
        assertThat(testVehicleInspections.getVehicleInspectionFireExtinguisher()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_FIRE_EXTINGUISHER);
        assertThat(testVehicleInspections.getVehicleInspectionSeatBelts()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_SEAT_BELTS);
        assertThat(testVehicleInspections.getVehicleInspectionMonkey()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_MONKEY);
        assertThat(testVehicleInspections.getVehicleInspectionTireIron()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_TIRE_IRON);
        assertThat(testVehicleInspections.getVehicleInspectionRadiatorCap()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_RADIATOR_CAP);
        assertThat(testVehicleInspections.getVehicleInspectionTriangle()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_TRIANGLE);
        assertThat(testVehicleInspections.getVehicleInspectionServiceBrake()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_SERVICE_BRAKE);
        assertThat(testVehicleInspections.getVehicleInspectionParkingBrake()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_PARKING_BRAKE);
        assertThat(testVehicleInspections.getVehicleInspectionOilLeaks()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_OIL_LEAKS);
        assertThat(testVehicleInspections.getVehicleInspectionGlassActuator()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_GLASS_ACTUATOR);
        assertThat(testVehicleInspections.getVehicleInspectionVehicleCleaning()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_VEHICLE_CLEANING);
        assertThat(testVehicleInspections.getVehicleInspectionSeatState()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_SEAT_STATE);
        assertThat(testVehicleInspections.getVehicleInspectionExhausts()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_EXHAUSTS);
        assertThat(testVehicleInspections.getVehicleInspectionsObs()).isEqualTo(DEFAULT_VEHICLE_INSPECTIONS_OBS);
        assertThat(testVehicleInspections.getVehicleInspectionsSignedUrl()).isEqualTo(DEFAULT_VEHICLE_INSPECTIONS_SIGNED_URL);
    }

    @Test
    @Transactional
    void createVehicleInspectionsWithExistingId() throws Exception {
        // Create the VehicleInspections with an existing ID
        vehicleInspections.setId(1L);
        VehicleInspectionsDTO vehicleInspectionsDTO = vehicleInspectionsMapper.toDto(vehicleInspections);

        int databaseSizeBeforeCreate = vehicleInspectionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleInspectionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleInspections in the database
        List<VehicleInspections> vehicleInspectionsList = vehicleInspectionsRepository.findAll();
        assertThat(vehicleInspectionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVehicleInspectionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInspectionsRepository.findAll().size();
        // set the field null
        vehicleInspections.setVehicleInspectionDate(null);

        // Create the VehicleInspections, which fails.
        VehicleInspectionsDTO vehicleInspectionsDTO = vehicleInspectionsMapper.toDto(vehicleInspections);

        restVehicleInspectionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleInspections> vehicleInspectionsList = vehicleInspectionsRepository.findAll();
        assertThat(vehicleInspectionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVehicleInspectionStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInspectionsRepository.findAll().size();
        // set the field null
        vehicleInspections.setVehicleInspectionStatus(null);

        // Create the VehicleInspections, which fails.
        VehicleInspectionsDTO vehicleInspectionsDTO = vehicleInspectionsMapper.toDto(vehicleInspections);

        restVehicleInspectionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleInspections> vehicleInspectionsList = vehicleInspectionsRepository.findAll();
        assertThat(vehicleInspectionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVehicleInspectionModelIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInspectionsRepository.findAll().size();
        // set the field null
        vehicleInspections.setVehicleInspectionModel(null);

        // Create the VehicleInspections, which fails.
        VehicleInspectionsDTO vehicleInspectionsDTO = vehicleInspectionsMapper.toDto(vehicleInspections);

        restVehicleInspectionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleInspections> vehicleInspectionsList = vehicleInspectionsRepository.findAll();
        assertThat(vehicleInspectionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVehicleInspectionLicensePlateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInspectionsRepository.findAll().size();
        // set the field null
        vehicleInspections.setVehicleInspectionLicensePlate(null);

        // Create the VehicleInspections, which fails.
        VehicleInspectionsDTO vehicleInspectionsDTO = vehicleInspectionsMapper.toDto(vehicleInspections);

        restVehicleInspectionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleInspections> vehicleInspectionsList = vehicleInspectionsRepository.findAll();
        assertThat(vehicleInspectionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVehicleInspections() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList
        restVehicleInspectionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleInspections.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleInspectionDate").value(hasItem(sameInstant(DEFAULT_VEHICLE_INSPECTION_DATE))))
            .andExpect(jsonPath("$.[*].vehicleInspectionStatus").value(hasItem(DEFAULT_VEHICLE_INSPECTION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionModel").value(hasItem(DEFAULT_VEHICLE_INSPECTION_MODEL)))
            .andExpect(jsonPath("$.[*].vehicleInspectionLicensePlate").value(hasItem(DEFAULT_VEHICLE_INSPECTION_LICENSE_PLATE)))
            .andExpect(jsonPath("$.[*].vehicleInspectionKm").value(hasItem(DEFAULT_VEHICLE_INSPECTION_KM.doubleValue())))
            .andExpect(jsonPath("$.[*].vehicleInspectionLicenseYear").value(hasItem(DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR.doubleValue())))
            .andExpect(jsonPath("$.[*].vehicleInspectionHasManual").value(hasItem(DEFAULT_VEHICLE_INSPECTION_HAS_MANUAL.booleanValue())))
            .andExpect(
                jsonPath("$.[*].vehicleInspectionHasExtraKey").value(hasItem(DEFAULT_VEHICLE_INSPECTION_HAS_EXTRA_KEY.booleanValue()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleInspectionHasStickers").value(hasItem(DEFAULT_VEHICLE_INSPECTION_HAS_STICKERS.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].vehicleInspectionGas").value(hasItem(DEFAULT_VEHICLE_INSPECTION_GAS.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionRearView").value(hasItem(DEFAULT_VEHICLE_INSPECTION_REAR_VIEW.booleanValue())))
            .andExpect(jsonPath("$.[*].vehicleInspectionHorn").value(hasItem(DEFAULT_VEHICLE_INSPECTION_HORN.booleanValue())))
            .andExpect(
                jsonPath("$.[*].vehicleInspectionWindshieldWiper")
                    .value(hasItem(DEFAULT_VEHICLE_INSPECTION_WINDSHIELD_WIPER.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].vehicleInspectionSquirt").value(hasItem(DEFAULT_VEHICLE_INSPECTION_SQUIRT.booleanValue())))
            .andExpect(
                jsonPath("$.[*].vehicleInspectionInternalLight").value(hasItem(DEFAULT_VEHICLE_INSPECTION_INTERNAL_LIGHT.toString()))
            )
            .andExpect(jsonPath("$.[*].vehicleInspectionPanelLight").value(hasItem(DEFAULT_VEHICLE_INSPECTION_PANEL_LIGHT.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionHighLight").value(hasItem(DEFAULT_VEHICLE_INSPECTION_HIGH_LIGHT.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionLowLight").value(hasItem(DEFAULT_VEHICLE_INSPECTION_LOW_LIGHT.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionTaillight").value(hasItem(DEFAULT_VEHICLE_INSPECTION_TAILLIGHT.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionIndicator").value(hasItem(DEFAULT_VEHICLE_INSPECTION_INDICATOR.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionBeacons").value(hasItem(DEFAULT_VEHICLE_INSPECTION_BEACONS.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionBreakLight").value(hasItem(DEFAULT_VEHICLE_INSPECTION_BREAK_LIGHT.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionPlateLight").value(hasItem(DEFAULT_VEHICLE_INSPECTION_PLATE_LIGHT.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionSpeedometer").value(hasItem(DEFAULT_VEHICLE_INSPECTION_SPEEDOMETER.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionTemperature").value(hasItem(DEFAULT_VEHICLE_INSPECTION_TEMPERATURE.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionTires").value(hasItem(DEFAULT_VEHICLE_INSPECTION_TIRES.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionStep").value(hasItem(DEFAULT_VEHICLE_INSPECTION_STEP.toString())))
            .andExpect(
                jsonPath("$.[*].vehicleInspectionFireExtinguisher").value(hasItem(DEFAULT_VEHICLE_INSPECTION_FIRE_EXTINGUISHER.toString()))
            )
            .andExpect(jsonPath("$.[*].vehicleInspectionSeatBelts").value(hasItem(DEFAULT_VEHICLE_INSPECTION_SEAT_BELTS.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionMonkey").value(hasItem(DEFAULT_VEHICLE_INSPECTION_MONKEY.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionTireIron").value(hasItem(DEFAULT_VEHICLE_INSPECTION_TIRE_IRON.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionRadiatorCap").value(hasItem(DEFAULT_VEHICLE_INSPECTION_RADIATOR_CAP.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionTriangle").value(hasItem(DEFAULT_VEHICLE_INSPECTION_TRIANGLE.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionServiceBrake").value(hasItem(DEFAULT_VEHICLE_INSPECTION_SERVICE_BRAKE.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionParkingBrake").value(hasItem(DEFAULT_VEHICLE_INSPECTION_PARKING_BRAKE.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionOilLeaks").value(hasItem(DEFAULT_VEHICLE_INSPECTION_OIL_LEAKS.toString())))
            .andExpect(
                jsonPath("$.[*].vehicleInspectionGlassActuator").value(hasItem(DEFAULT_VEHICLE_INSPECTION_GLASS_ACTUATOR.toString()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleInspectionVehicleCleaning").value(hasItem(DEFAULT_VEHICLE_INSPECTION_VEHICLE_CLEANING.toString()))
            )
            .andExpect(jsonPath("$.[*].vehicleInspectionSeatState").value(hasItem(DEFAULT_VEHICLE_INSPECTION_SEAT_STATE.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionExhausts").value(hasItem(DEFAULT_VEHICLE_INSPECTION_EXHAUSTS.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionsObs").value(hasItem(DEFAULT_VEHICLE_INSPECTIONS_OBS)))
            .andExpect(jsonPath("$.[*].vehicleInspectionsSignedUrl").value(hasItem(DEFAULT_VEHICLE_INSPECTIONS_SIGNED_URL)));
    }

    @Test
    @Transactional
    void getVehicleInspections() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get the vehicleInspections
        restVehicleInspectionsMockMvc
            .perform(get(ENTITY_API_URL_ID, vehicleInspections.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleInspections.getId().intValue()))
            .andExpect(jsonPath("$.vehicleInspectionDate").value(sameInstant(DEFAULT_VEHICLE_INSPECTION_DATE)))
            .andExpect(jsonPath("$.vehicleInspectionStatus").value(DEFAULT_VEHICLE_INSPECTION_STATUS.toString()))
            .andExpect(jsonPath("$.vehicleInspectionModel").value(DEFAULT_VEHICLE_INSPECTION_MODEL))
            .andExpect(jsonPath("$.vehicleInspectionLicensePlate").value(DEFAULT_VEHICLE_INSPECTION_LICENSE_PLATE))
            .andExpect(jsonPath("$.vehicleInspectionKm").value(DEFAULT_VEHICLE_INSPECTION_KM.doubleValue()))
            .andExpect(jsonPath("$.vehicleInspectionLicenseYear").value(DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR.doubleValue()))
            .andExpect(jsonPath("$.vehicleInspectionHasManual").value(DEFAULT_VEHICLE_INSPECTION_HAS_MANUAL.booleanValue()))
            .andExpect(jsonPath("$.vehicleInspectionHasExtraKey").value(DEFAULT_VEHICLE_INSPECTION_HAS_EXTRA_KEY.booleanValue()))
            .andExpect(jsonPath("$.vehicleInspectionHasStickers").value(DEFAULT_VEHICLE_INSPECTION_HAS_STICKERS.booleanValue()))
            .andExpect(jsonPath("$.vehicleInspectionGas").value(DEFAULT_VEHICLE_INSPECTION_GAS.toString()))
            .andExpect(jsonPath("$.vehicleInspectionRearView").value(DEFAULT_VEHICLE_INSPECTION_REAR_VIEW.booleanValue()))
            .andExpect(jsonPath("$.vehicleInspectionHorn").value(DEFAULT_VEHICLE_INSPECTION_HORN.booleanValue()))
            .andExpect(jsonPath("$.vehicleInspectionWindshieldWiper").value(DEFAULT_VEHICLE_INSPECTION_WINDSHIELD_WIPER.booleanValue()))
            .andExpect(jsonPath("$.vehicleInspectionSquirt").value(DEFAULT_VEHICLE_INSPECTION_SQUIRT.booleanValue()))
            .andExpect(jsonPath("$.vehicleInspectionInternalLight").value(DEFAULT_VEHICLE_INSPECTION_INTERNAL_LIGHT.toString()))
            .andExpect(jsonPath("$.vehicleInspectionPanelLight").value(DEFAULT_VEHICLE_INSPECTION_PANEL_LIGHT.toString()))
            .andExpect(jsonPath("$.vehicleInspectionHighLight").value(DEFAULT_VEHICLE_INSPECTION_HIGH_LIGHT.toString()))
            .andExpect(jsonPath("$.vehicleInspectionLowLight").value(DEFAULT_VEHICLE_INSPECTION_LOW_LIGHT.toString()))
            .andExpect(jsonPath("$.vehicleInspectionTaillight").value(DEFAULT_VEHICLE_INSPECTION_TAILLIGHT.toString()))
            .andExpect(jsonPath("$.vehicleInspectionIndicator").value(DEFAULT_VEHICLE_INSPECTION_INDICATOR.toString()))
            .andExpect(jsonPath("$.vehicleInspectionBeacons").value(DEFAULT_VEHICLE_INSPECTION_BEACONS.toString()))
            .andExpect(jsonPath("$.vehicleInspectionBreakLight").value(DEFAULT_VEHICLE_INSPECTION_BREAK_LIGHT.toString()))
            .andExpect(jsonPath("$.vehicleInspectionPlateLight").value(DEFAULT_VEHICLE_INSPECTION_PLATE_LIGHT.toString()))
            .andExpect(jsonPath("$.vehicleInspectionSpeedometer").value(DEFAULT_VEHICLE_INSPECTION_SPEEDOMETER.toString()))
            .andExpect(jsonPath("$.vehicleInspectionTemperature").value(DEFAULT_VEHICLE_INSPECTION_TEMPERATURE.toString()))
            .andExpect(jsonPath("$.vehicleInspectionTires").value(DEFAULT_VEHICLE_INSPECTION_TIRES.toString()))
            .andExpect(jsonPath("$.vehicleInspectionStep").value(DEFAULT_VEHICLE_INSPECTION_STEP.toString()))
            .andExpect(jsonPath("$.vehicleInspectionFireExtinguisher").value(DEFAULT_VEHICLE_INSPECTION_FIRE_EXTINGUISHER.toString()))
            .andExpect(jsonPath("$.vehicleInspectionSeatBelts").value(DEFAULT_VEHICLE_INSPECTION_SEAT_BELTS.toString()))
            .andExpect(jsonPath("$.vehicleInspectionMonkey").value(DEFAULT_VEHICLE_INSPECTION_MONKEY.toString()))
            .andExpect(jsonPath("$.vehicleInspectionTireIron").value(DEFAULT_VEHICLE_INSPECTION_TIRE_IRON.toString()))
            .andExpect(jsonPath("$.vehicleInspectionRadiatorCap").value(DEFAULT_VEHICLE_INSPECTION_RADIATOR_CAP.toString()))
            .andExpect(jsonPath("$.vehicleInspectionTriangle").value(DEFAULT_VEHICLE_INSPECTION_TRIANGLE.toString()))
            .andExpect(jsonPath("$.vehicleInspectionServiceBrake").value(DEFAULT_VEHICLE_INSPECTION_SERVICE_BRAKE.toString()))
            .andExpect(jsonPath("$.vehicleInspectionParkingBrake").value(DEFAULT_VEHICLE_INSPECTION_PARKING_BRAKE.toString()))
            .andExpect(jsonPath("$.vehicleInspectionOilLeaks").value(DEFAULT_VEHICLE_INSPECTION_OIL_LEAKS.toString()))
            .andExpect(jsonPath("$.vehicleInspectionGlassActuator").value(DEFAULT_VEHICLE_INSPECTION_GLASS_ACTUATOR.toString()))
            .andExpect(jsonPath("$.vehicleInspectionVehicleCleaning").value(DEFAULT_VEHICLE_INSPECTION_VEHICLE_CLEANING.toString()))
            .andExpect(jsonPath("$.vehicleInspectionSeatState").value(DEFAULT_VEHICLE_INSPECTION_SEAT_STATE.toString()))
            .andExpect(jsonPath("$.vehicleInspectionExhausts").value(DEFAULT_VEHICLE_INSPECTION_EXHAUSTS.toString()))
            .andExpect(jsonPath("$.vehicleInspectionsObs").value(DEFAULT_VEHICLE_INSPECTIONS_OBS))
            .andExpect(jsonPath("$.vehicleInspectionsSignedUrl").value(DEFAULT_VEHICLE_INSPECTIONS_SIGNED_URL));
    }

    @Test
    @Transactional
    void getVehicleInspectionsByIdFiltering() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        Long id = vehicleInspections.getId();

        defaultVehicleInspectionsShouldBeFound("id.equals=" + id);
        defaultVehicleInspectionsShouldNotBeFound("id.notEquals=" + id);

        defaultVehicleInspectionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVehicleInspectionsShouldNotBeFound("id.greaterThan=" + id);

        defaultVehicleInspectionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVehicleInspectionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionDate equals to DEFAULT_VEHICLE_INSPECTION_DATE
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionDate.equals=" + DEFAULT_VEHICLE_INSPECTION_DATE);

        // Get all the vehicleInspectionsList where vehicleInspectionDate equals to UPDATED_VEHICLE_INSPECTION_DATE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionDate.equals=" + UPDATED_VEHICLE_INSPECTION_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionDate not equals to DEFAULT_VEHICLE_INSPECTION_DATE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionDate.notEquals=" + DEFAULT_VEHICLE_INSPECTION_DATE);

        // Get all the vehicleInspectionsList where vehicleInspectionDate not equals to UPDATED_VEHICLE_INSPECTION_DATE
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionDate.notEquals=" + UPDATED_VEHICLE_INSPECTION_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionDateIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionDate in DEFAULT_VEHICLE_INSPECTION_DATE or UPDATED_VEHICLE_INSPECTION_DATE
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionDate.in=" + DEFAULT_VEHICLE_INSPECTION_DATE + "," + UPDATED_VEHICLE_INSPECTION_DATE
        );

        // Get all the vehicleInspectionsList where vehicleInspectionDate equals to UPDATED_VEHICLE_INSPECTION_DATE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionDate.in=" + UPDATED_VEHICLE_INSPECTION_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionDate is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionDate.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionDate is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionDate is greater than or equal to DEFAULT_VEHICLE_INSPECTION_DATE
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionDate.greaterThanOrEqual=" + DEFAULT_VEHICLE_INSPECTION_DATE);

        // Get all the vehicleInspectionsList where vehicleInspectionDate is greater than or equal to UPDATED_VEHICLE_INSPECTION_DATE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionDate.greaterThanOrEqual=" + UPDATED_VEHICLE_INSPECTION_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionDate is less than or equal to DEFAULT_VEHICLE_INSPECTION_DATE
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionDate.lessThanOrEqual=" + DEFAULT_VEHICLE_INSPECTION_DATE);

        // Get all the vehicleInspectionsList where vehicleInspectionDate is less than or equal to SMALLER_VEHICLE_INSPECTION_DATE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionDate.lessThanOrEqual=" + SMALLER_VEHICLE_INSPECTION_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionDateIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionDate is less than DEFAULT_VEHICLE_INSPECTION_DATE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionDate.lessThan=" + DEFAULT_VEHICLE_INSPECTION_DATE);

        // Get all the vehicleInspectionsList where vehicleInspectionDate is less than UPDATED_VEHICLE_INSPECTION_DATE
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionDate.lessThan=" + UPDATED_VEHICLE_INSPECTION_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionDate is greater than DEFAULT_VEHICLE_INSPECTION_DATE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionDate.greaterThan=" + DEFAULT_VEHICLE_INSPECTION_DATE);

        // Get all the vehicleInspectionsList where vehicleInspectionDate is greater than SMALLER_VEHICLE_INSPECTION_DATE
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionDate.greaterThan=" + SMALLER_VEHICLE_INSPECTION_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionStatus equals to DEFAULT_VEHICLE_INSPECTION_STATUS
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionStatus.equals=" + DEFAULT_VEHICLE_INSPECTION_STATUS);

        // Get all the vehicleInspectionsList where vehicleInspectionStatus equals to UPDATED_VEHICLE_INSPECTION_STATUS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionStatus.equals=" + UPDATED_VEHICLE_INSPECTION_STATUS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionStatus not equals to DEFAULT_VEHICLE_INSPECTION_STATUS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionStatus.notEquals=" + DEFAULT_VEHICLE_INSPECTION_STATUS);

        // Get all the vehicleInspectionsList where vehicleInspectionStatus not equals to UPDATED_VEHICLE_INSPECTION_STATUS
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionStatus.notEquals=" + UPDATED_VEHICLE_INSPECTION_STATUS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionStatusIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionStatus in DEFAULT_VEHICLE_INSPECTION_STATUS or UPDATED_VEHICLE_INSPECTION_STATUS
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionStatus.in=" + DEFAULT_VEHICLE_INSPECTION_STATUS + "," + UPDATED_VEHICLE_INSPECTION_STATUS
        );

        // Get all the vehicleInspectionsList where vehicleInspectionStatus equals to UPDATED_VEHICLE_INSPECTION_STATUS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionStatus.in=" + UPDATED_VEHICLE_INSPECTION_STATUS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionStatus is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionStatus.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionStatus is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionModelIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionModel equals to DEFAULT_VEHICLE_INSPECTION_MODEL
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionModel.equals=" + DEFAULT_VEHICLE_INSPECTION_MODEL);

        // Get all the vehicleInspectionsList where vehicleInspectionModel equals to UPDATED_VEHICLE_INSPECTION_MODEL
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionModel.equals=" + UPDATED_VEHICLE_INSPECTION_MODEL);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionModelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionModel not equals to DEFAULT_VEHICLE_INSPECTION_MODEL
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionModel.notEquals=" + DEFAULT_VEHICLE_INSPECTION_MODEL);

        // Get all the vehicleInspectionsList where vehicleInspectionModel not equals to UPDATED_VEHICLE_INSPECTION_MODEL
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionModel.notEquals=" + UPDATED_VEHICLE_INSPECTION_MODEL);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionModelIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionModel in DEFAULT_VEHICLE_INSPECTION_MODEL or UPDATED_VEHICLE_INSPECTION_MODEL
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionModel.in=" + DEFAULT_VEHICLE_INSPECTION_MODEL + "," + UPDATED_VEHICLE_INSPECTION_MODEL
        );

        // Get all the vehicleInspectionsList where vehicleInspectionModel equals to UPDATED_VEHICLE_INSPECTION_MODEL
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionModel.in=" + UPDATED_VEHICLE_INSPECTION_MODEL);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionModelIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionModel is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionModel.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionModel is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionModel.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionModelContainsSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionModel contains DEFAULT_VEHICLE_INSPECTION_MODEL
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionModel.contains=" + DEFAULT_VEHICLE_INSPECTION_MODEL);

        // Get all the vehicleInspectionsList where vehicleInspectionModel contains UPDATED_VEHICLE_INSPECTION_MODEL
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionModel.contains=" + UPDATED_VEHICLE_INSPECTION_MODEL);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionModelNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionModel does not contain DEFAULT_VEHICLE_INSPECTION_MODEL
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionModel.doesNotContain=" + DEFAULT_VEHICLE_INSPECTION_MODEL);

        // Get all the vehicleInspectionsList where vehicleInspectionModel does not contain UPDATED_VEHICLE_INSPECTION_MODEL
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionModel.doesNotContain=" + UPDATED_VEHICLE_INSPECTION_MODEL);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionLicensePlateIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionLicensePlate equals to DEFAULT_VEHICLE_INSPECTION_LICENSE_PLATE
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionLicensePlate.equals=" + DEFAULT_VEHICLE_INSPECTION_LICENSE_PLATE);

        // Get all the vehicleInspectionsList where vehicleInspectionLicensePlate equals to UPDATED_VEHICLE_INSPECTION_LICENSE_PLATE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionLicensePlate.equals=" + UPDATED_VEHICLE_INSPECTION_LICENSE_PLATE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionLicensePlateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionLicensePlate not equals to DEFAULT_VEHICLE_INSPECTION_LICENSE_PLATE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionLicensePlate.notEquals=" + DEFAULT_VEHICLE_INSPECTION_LICENSE_PLATE);

        // Get all the vehicleInspectionsList where vehicleInspectionLicensePlate not equals to UPDATED_VEHICLE_INSPECTION_LICENSE_PLATE
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionLicensePlate.notEquals=" + UPDATED_VEHICLE_INSPECTION_LICENSE_PLATE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionLicensePlateIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionLicensePlate in DEFAULT_VEHICLE_INSPECTION_LICENSE_PLATE or UPDATED_VEHICLE_INSPECTION_LICENSE_PLATE
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionLicensePlate.in=" + DEFAULT_VEHICLE_INSPECTION_LICENSE_PLATE + "," + UPDATED_VEHICLE_INSPECTION_LICENSE_PLATE
        );

        // Get all the vehicleInspectionsList where vehicleInspectionLicensePlate equals to UPDATED_VEHICLE_INSPECTION_LICENSE_PLATE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionLicensePlate.in=" + UPDATED_VEHICLE_INSPECTION_LICENSE_PLATE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionLicensePlateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionLicensePlate is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionLicensePlate.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionLicensePlate is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionLicensePlate.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionLicensePlateContainsSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionLicensePlate contains DEFAULT_VEHICLE_INSPECTION_LICENSE_PLATE
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionLicensePlate.contains=" + DEFAULT_VEHICLE_INSPECTION_LICENSE_PLATE);

        // Get all the vehicleInspectionsList where vehicleInspectionLicensePlate contains UPDATED_VEHICLE_INSPECTION_LICENSE_PLATE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionLicensePlate.contains=" + UPDATED_VEHICLE_INSPECTION_LICENSE_PLATE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionLicensePlateNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionLicensePlate does not contain DEFAULT_VEHICLE_INSPECTION_LICENSE_PLATE
        defaultVehicleInspectionsShouldNotBeFound(
            "vehicleInspectionLicensePlate.doesNotContain=" + DEFAULT_VEHICLE_INSPECTION_LICENSE_PLATE
        );

        // Get all the vehicleInspectionsList where vehicleInspectionLicensePlate does not contain UPDATED_VEHICLE_INSPECTION_LICENSE_PLATE
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionLicensePlate.doesNotContain=" + UPDATED_VEHICLE_INSPECTION_LICENSE_PLATE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionKmIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionKm equals to DEFAULT_VEHICLE_INSPECTION_KM
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionKm.equals=" + DEFAULT_VEHICLE_INSPECTION_KM);

        // Get all the vehicleInspectionsList where vehicleInspectionKm equals to UPDATED_VEHICLE_INSPECTION_KM
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionKm.equals=" + UPDATED_VEHICLE_INSPECTION_KM);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionKmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionKm not equals to DEFAULT_VEHICLE_INSPECTION_KM
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionKm.notEquals=" + DEFAULT_VEHICLE_INSPECTION_KM);

        // Get all the vehicleInspectionsList where vehicleInspectionKm not equals to UPDATED_VEHICLE_INSPECTION_KM
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionKm.notEquals=" + UPDATED_VEHICLE_INSPECTION_KM);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionKmIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionKm in DEFAULT_VEHICLE_INSPECTION_KM or UPDATED_VEHICLE_INSPECTION_KM
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionKm.in=" + DEFAULT_VEHICLE_INSPECTION_KM + "," + UPDATED_VEHICLE_INSPECTION_KM
        );

        // Get all the vehicleInspectionsList where vehicleInspectionKm equals to UPDATED_VEHICLE_INSPECTION_KM
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionKm.in=" + UPDATED_VEHICLE_INSPECTION_KM);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionKmIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionKm is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionKm.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionKm is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionKm.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionKmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionKm is greater than or equal to DEFAULT_VEHICLE_INSPECTION_KM
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionKm.greaterThanOrEqual=" + DEFAULT_VEHICLE_INSPECTION_KM);

        // Get all the vehicleInspectionsList where vehicleInspectionKm is greater than or equal to UPDATED_VEHICLE_INSPECTION_KM
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionKm.greaterThanOrEqual=" + UPDATED_VEHICLE_INSPECTION_KM);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionKmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionKm is less than or equal to DEFAULT_VEHICLE_INSPECTION_KM
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionKm.lessThanOrEqual=" + DEFAULT_VEHICLE_INSPECTION_KM);

        // Get all the vehicleInspectionsList where vehicleInspectionKm is less than or equal to SMALLER_VEHICLE_INSPECTION_KM
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionKm.lessThanOrEqual=" + SMALLER_VEHICLE_INSPECTION_KM);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionKmIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionKm is less than DEFAULT_VEHICLE_INSPECTION_KM
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionKm.lessThan=" + DEFAULT_VEHICLE_INSPECTION_KM);

        // Get all the vehicleInspectionsList where vehicleInspectionKm is less than UPDATED_VEHICLE_INSPECTION_KM
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionKm.lessThan=" + UPDATED_VEHICLE_INSPECTION_KM);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionKmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionKm is greater than DEFAULT_VEHICLE_INSPECTION_KM
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionKm.greaterThan=" + DEFAULT_VEHICLE_INSPECTION_KM);

        // Get all the vehicleInspectionsList where vehicleInspectionKm is greater than SMALLER_VEHICLE_INSPECTION_KM
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionKm.greaterThan=" + SMALLER_VEHICLE_INSPECTION_KM);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionLicenseYearIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionLicenseYear equals to DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionLicenseYear.equals=" + DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR);

        // Get all the vehicleInspectionsList where vehicleInspectionLicenseYear equals to UPDATED_VEHICLE_INSPECTION_LICENSE_YEAR
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionLicenseYear.equals=" + UPDATED_VEHICLE_INSPECTION_LICENSE_YEAR);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionLicenseYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionLicenseYear not equals to DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionLicenseYear.notEquals=" + DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR);

        // Get all the vehicleInspectionsList where vehicleInspectionLicenseYear not equals to UPDATED_VEHICLE_INSPECTION_LICENSE_YEAR
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionLicenseYear.notEquals=" + UPDATED_VEHICLE_INSPECTION_LICENSE_YEAR);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionLicenseYearIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionLicenseYear in DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR or UPDATED_VEHICLE_INSPECTION_LICENSE_YEAR
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionLicenseYear.in=" + DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR + "," + UPDATED_VEHICLE_INSPECTION_LICENSE_YEAR
        );

        // Get all the vehicleInspectionsList where vehicleInspectionLicenseYear equals to UPDATED_VEHICLE_INSPECTION_LICENSE_YEAR
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionLicenseYear.in=" + UPDATED_VEHICLE_INSPECTION_LICENSE_YEAR);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionLicenseYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionLicenseYear is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionLicenseYear.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionLicenseYear is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionLicenseYear.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionLicenseYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionLicenseYear is greater than or equal to DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionLicenseYear.greaterThanOrEqual=" + DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR
        );

        // Get all the vehicleInspectionsList where vehicleInspectionLicenseYear is greater than or equal to UPDATED_VEHICLE_INSPECTION_LICENSE_YEAR
        defaultVehicleInspectionsShouldNotBeFound(
            "vehicleInspectionLicenseYear.greaterThanOrEqual=" + UPDATED_VEHICLE_INSPECTION_LICENSE_YEAR
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionLicenseYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionLicenseYear is less than or equal to DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionLicenseYear.lessThanOrEqual=" + DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR);

        // Get all the vehicleInspectionsList where vehicleInspectionLicenseYear is less than or equal to SMALLER_VEHICLE_INSPECTION_LICENSE_YEAR
        defaultVehicleInspectionsShouldNotBeFound(
            "vehicleInspectionLicenseYear.lessThanOrEqual=" + SMALLER_VEHICLE_INSPECTION_LICENSE_YEAR
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionLicenseYearIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionLicenseYear is less than DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionLicenseYear.lessThan=" + DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR);

        // Get all the vehicleInspectionsList where vehicleInspectionLicenseYear is less than UPDATED_VEHICLE_INSPECTION_LICENSE_YEAR
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionLicenseYear.lessThan=" + UPDATED_VEHICLE_INSPECTION_LICENSE_YEAR);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionLicenseYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionLicenseYear is greater than DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionLicenseYear.greaterThan=" + DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR);

        // Get all the vehicleInspectionsList where vehicleInspectionLicenseYear is greater than SMALLER_VEHICLE_INSPECTION_LICENSE_YEAR
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionLicenseYear.greaterThan=" + SMALLER_VEHICLE_INSPECTION_LICENSE_YEAR);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionHasManualIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionHasManual equals to DEFAULT_VEHICLE_INSPECTION_HAS_MANUAL
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionHasManual.equals=" + DEFAULT_VEHICLE_INSPECTION_HAS_MANUAL);

        // Get all the vehicleInspectionsList where vehicleInspectionHasManual equals to UPDATED_VEHICLE_INSPECTION_HAS_MANUAL
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionHasManual.equals=" + UPDATED_VEHICLE_INSPECTION_HAS_MANUAL);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionHasManualIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionHasManual not equals to DEFAULT_VEHICLE_INSPECTION_HAS_MANUAL
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionHasManual.notEquals=" + DEFAULT_VEHICLE_INSPECTION_HAS_MANUAL);

        // Get all the vehicleInspectionsList where vehicleInspectionHasManual not equals to UPDATED_VEHICLE_INSPECTION_HAS_MANUAL
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionHasManual.notEquals=" + UPDATED_VEHICLE_INSPECTION_HAS_MANUAL);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionHasManualIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionHasManual in DEFAULT_VEHICLE_INSPECTION_HAS_MANUAL or UPDATED_VEHICLE_INSPECTION_HAS_MANUAL
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionHasManual.in=" + DEFAULT_VEHICLE_INSPECTION_HAS_MANUAL + "," + UPDATED_VEHICLE_INSPECTION_HAS_MANUAL
        );

        // Get all the vehicleInspectionsList where vehicleInspectionHasManual equals to UPDATED_VEHICLE_INSPECTION_HAS_MANUAL
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionHasManual.in=" + UPDATED_VEHICLE_INSPECTION_HAS_MANUAL);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionHasManualIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionHasManual is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionHasManual.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionHasManual is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionHasManual.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionHasExtraKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionHasExtraKey equals to DEFAULT_VEHICLE_INSPECTION_HAS_EXTRA_KEY
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionHasExtraKey.equals=" + DEFAULT_VEHICLE_INSPECTION_HAS_EXTRA_KEY);

        // Get all the vehicleInspectionsList where vehicleInspectionHasExtraKey equals to UPDATED_VEHICLE_INSPECTION_HAS_EXTRA_KEY
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionHasExtraKey.equals=" + UPDATED_VEHICLE_INSPECTION_HAS_EXTRA_KEY);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionHasExtraKeyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionHasExtraKey not equals to DEFAULT_VEHICLE_INSPECTION_HAS_EXTRA_KEY
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionHasExtraKey.notEquals=" + DEFAULT_VEHICLE_INSPECTION_HAS_EXTRA_KEY);

        // Get all the vehicleInspectionsList where vehicleInspectionHasExtraKey not equals to UPDATED_VEHICLE_INSPECTION_HAS_EXTRA_KEY
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionHasExtraKey.notEquals=" + UPDATED_VEHICLE_INSPECTION_HAS_EXTRA_KEY);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionHasExtraKeyIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionHasExtraKey in DEFAULT_VEHICLE_INSPECTION_HAS_EXTRA_KEY or UPDATED_VEHICLE_INSPECTION_HAS_EXTRA_KEY
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionHasExtraKey.in=" + DEFAULT_VEHICLE_INSPECTION_HAS_EXTRA_KEY + "," + UPDATED_VEHICLE_INSPECTION_HAS_EXTRA_KEY
        );

        // Get all the vehicleInspectionsList where vehicleInspectionHasExtraKey equals to UPDATED_VEHICLE_INSPECTION_HAS_EXTRA_KEY
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionHasExtraKey.in=" + UPDATED_VEHICLE_INSPECTION_HAS_EXTRA_KEY);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionHasExtraKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionHasExtraKey is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionHasExtraKey.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionHasExtraKey is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionHasExtraKey.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionHasStickersIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionHasStickers equals to DEFAULT_VEHICLE_INSPECTION_HAS_STICKERS
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionHasStickers.equals=" + DEFAULT_VEHICLE_INSPECTION_HAS_STICKERS);

        // Get all the vehicleInspectionsList where vehicleInspectionHasStickers equals to UPDATED_VEHICLE_INSPECTION_HAS_STICKERS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionHasStickers.equals=" + UPDATED_VEHICLE_INSPECTION_HAS_STICKERS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionHasStickersIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionHasStickers not equals to DEFAULT_VEHICLE_INSPECTION_HAS_STICKERS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionHasStickers.notEquals=" + DEFAULT_VEHICLE_INSPECTION_HAS_STICKERS);

        // Get all the vehicleInspectionsList where vehicleInspectionHasStickers not equals to UPDATED_VEHICLE_INSPECTION_HAS_STICKERS
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionHasStickers.notEquals=" + UPDATED_VEHICLE_INSPECTION_HAS_STICKERS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionHasStickersIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionHasStickers in DEFAULT_VEHICLE_INSPECTION_HAS_STICKERS or UPDATED_VEHICLE_INSPECTION_HAS_STICKERS
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionHasStickers.in=" + DEFAULT_VEHICLE_INSPECTION_HAS_STICKERS + "," + UPDATED_VEHICLE_INSPECTION_HAS_STICKERS
        );

        // Get all the vehicleInspectionsList where vehicleInspectionHasStickers equals to UPDATED_VEHICLE_INSPECTION_HAS_STICKERS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionHasStickers.in=" + UPDATED_VEHICLE_INSPECTION_HAS_STICKERS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionHasStickersIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionHasStickers is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionHasStickers.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionHasStickers is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionHasStickers.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionGasIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionGas equals to DEFAULT_VEHICLE_INSPECTION_GAS
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionGas.equals=" + DEFAULT_VEHICLE_INSPECTION_GAS);

        // Get all the vehicleInspectionsList where vehicleInspectionGas equals to UPDATED_VEHICLE_INSPECTION_GAS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionGas.equals=" + UPDATED_VEHICLE_INSPECTION_GAS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionGasIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionGas not equals to DEFAULT_VEHICLE_INSPECTION_GAS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionGas.notEquals=" + DEFAULT_VEHICLE_INSPECTION_GAS);

        // Get all the vehicleInspectionsList where vehicleInspectionGas not equals to UPDATED_VEHICLE_INSPECTION_GAS
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionGas.notEquals=" + UPDATED_VEHICLE_INSPECTION_GAS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionGasIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionGas in DEFAULT_VEHICLE_INSPECTION_GAS or UPDATED_VEHICLE_INSPECTION_GAS
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionGas.in=" + DEFAULT_VEHICLE_INSPECTION_GAS + "," + UPDATED_VEHICLE_INSPECTION_GAS
        );

        // Get all the vehicleInspectionsList where vehicleInspectionGas equals to UPDATED_VEHICLE_INSPECTION_GAS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionGas.in=" + UPDATED_VEHICLE_INSPECTION_GAS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionGasIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionGas is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionGas.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionGas is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionGas.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionRearViewIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionRearView equals to DEFAULT_VEHICLE_INSPECTION_REAR_VIEW
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionRearView.equals=" + DEFAULT_VEHICLE_INSPECTION_REAR_VIEW);

        // Get all the vehicleInspectionsList where vehicleInspectionRearView equals to UPDATED_VEHICLE_INSPECTION_REAR_VIEW
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionRearView.equals=" + UPDATED_VEHICLE_INSPECTION_REAR_VIEW);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionRearViewIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionRearView not equals to DEFAULT_VEHICLE_INSPECTION_REAR_VIEW
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionRearView.notEquals=" + DEFAULT_VEHICLE_INSPECTION_REAR_VIEW);

        // Get all the vehicleInspectionsList where vehicleInspectionRearView not equals to UPDATED_VEHICLE_INSPECTION_REAR_VIEW
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionRearView.notEquals=" + UPDATED_VEHICLE_INSPECTION_REAR_VIEW);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionRearViewIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionRearView in DEFAULT_VEHICLE_INSPECTION_REAR_VIEW or UPDATED_VEHICLE_INSPECTION_REAR_VIEW
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionRearView.in=" + DEFAULT_VEHICLE_INSPECTION_REAR_VIEW + "," + UPDATED_VEHICLE_INSPECTION_REAR_VIEW
        );

        // Get all the vehicleInspectionsList where vehicleInspectionRearView equals to UPDATED_VEHICLE_INSPECTION_REAR_VIEW
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionRearView.in=" + UPDATED_VEHICLE_INSPECTION_REAR_VIEW);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionRearViewIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionRearView is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionRearView.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionRearView is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionRearView.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionHornIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionHorn equals to DEFAULT_VEHICLE_INSPECTION_HORN
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionHorn.equals=" + DEFAULT_VEHICLE_INSPECTION_HORN);

        // Get all the vehicleInspectionsList where vehicleInspectionHorn equals to UPDATED_VEHICLE_INSPECTION_HORN
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionHorn.equals=" + UPDATED_VEHICLE_INSPECTION_HORN);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionHornIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionHorn not equals to DEFAULT_VEHICLE_INSPECTION_HORN
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionHorn.notEquals=" + DEFAULT_VEHICLE_INSPECTION_HORN);

        // Get all the vehicleInspectionsList where vehicleInspectionHorn not equals to UPDATED_VEHICLE_INSPECTION_HORN
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionHorn.notEquals=" + UPDATED_VEHICLE_INSPECTION_HORN);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionHornIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionHorn in DEFAULT_VEHICLE_INSPECTION_HORN or UPDATED_VEHICLE_INSPECTION_HORN
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionHorn.in=" + DEFAULT_VEHICLE_INSPECTION_HORN + "," + UPDATED_VEHICLE_INSPECTION_HORN
        );

        // Get all the vehicleInspectionsList where vehicleInspectionHorn equals to UPDATED_VEHICLE_INSPECTION_HORN
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionHorn.in=" + UPDATED_VEHICLE_INSPECTION_HORN);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionHornIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionHorn is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionHorn.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionHorn is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionHorn.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionWindshieldWiperIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionWindshieldWiper equals to DEFAULT_VEHICLE_INSPECTION_WINDSHIELD_WIPER
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionWindshieldWiper.equals=" + DEFAULT_VEHICLE_INSPECTION_WINDSHIELD_WIPER);

        // Get all the vehicleInspectionsList where vehicleInspectionWindshieldWiper equals to UPDATED_VEHICLE_INSPECTION_WINDSHIELD_WIPER
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionWindshieldWiper.equals=" + UPDATED_VEHICLE_INSPECTION_WINDSHIELD_WIPER);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionWindshieldWiperIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionWindshieldWiper not equals to DEFAULT_VEHICLE_INSPECTION_WINDSHIELD_WIPER
        defaultVehicleInspectionsShouldNotBeFound(
            "vehicleInspectionWindshieldWiper.notEquals=" + DEFAULT_VEHICLE_INSPECTION_WINDSHIELD_WIPER
        );

        // Get all the vehicleInspectionsList where vehicleInspectionWindshieldWiper not equals to UPDATED_VEHICLE_INSPECTION_WINDSHIELD_WIPER
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionWindshieldWiper.notEquals=" + UPDATED_VEHICLE_INSPECTION_WINDSHIELD_WIPER);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionWindshieldWiperIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionWindshieldWiper in DEFAULT_VEHICLE_INSPECTION_WINDSHIELD_WIPER or UPDATED_VEHICLE_INSPECTION_WINDSHIELD_WIPER
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionWindshieldWiper.in=" +
            DEFAULT_VEHICLE_INSPECTION_WINDSHIELD_WIPER +
            "," +
            UPDATED_VEHICLE_INSPECTION_WINDSHIELD_WIPER
        );

        // Get all the vehicleInspectionsList where vehicleInspectionWindshieldWiper equals to UPDATED_VEHICLE_INSPECTION_WINDSHIELD_WIPER
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionWindshieldWiper.in=" + UPDATED_VEHICLE_INSPECTION_WINDSHIELD_WIPER);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionWindshieldWiperIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionWindshieldWiper is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionWindshieldWiper.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionWindshieldWiper is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionWindshieldWiper.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionSquirtIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionSquirt equals to DEFAULT_VEHICLE_INSPECTION_SQUIRT
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionSquirt.equals=" + DEFAULT_VEHICLE_INSPECTION_SQUIRT);

        // Get all the vehicleInspectionsList where vehicleInspectionSquirt equals to UPDATED_VEHICLE_INSPECTION_SQUIRT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionSquirt.equals=" + UPDATED_VEHICLE_INSPECTION_SQUIRT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionSquirtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionSquirt not equals to DEFAULT_VEHICLE_INSPECTION_SQUIRT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionSquirt.notEquals=" + DEFAULT_VEHICLE_INSPECTION_SQUIRT);

        // Get all the vehicleInspectionsList where vehicleInspectionSquirt not equals to UPDATED_VEHICLE_INSPECTION_SQUIRT
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionSquirt.notEquals=" + UPDATED_VEHICLE_INSPECTION_SQUIRT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionSquirtIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionSquirt in DEFAULT_VEHICLE_INSPECTION_SQUIRT or UPDATED_VEHICLE_INSPECTION_SQUIRT
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionSquirt.in=" + DEFAULT_VEHICLE_INSPECTION_SQUIRT + "," + UPDATED_VEHICLE_INSPECTION_SQUIRT
        );

        // Get all the vehicleInspectionsList where vehicleInspectionSquirt equals to UPDATED_VEHICLE_INSPECTION_SQUIRT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionSquirt.in=" + UPDATED_VEHICLE_INSPECTION_SQUIRT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionSquirtIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionSquirt is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionSquirt.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionSquirt is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionSquirt.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionInternalLightIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionInternalLight equals to DEFAULT_VEHICLE_INSPECTION_INTERNAL_LIGHT
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionInternalLight.equals=" + DEFAULT_VEHICLE_INSPECTION_INTERNAL_LIGHT);

        // Get all the vehicleInspectionsList where vehicleInspectionInternalLight equals to UPDATED_VEHICLE_INSPECTION_INTERNAL_LIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionInternalLight.equals=" + UPDATED_VEHICLE_INSPECTION_INTERNAL_LIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionInternalLightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionInternalLight not equals to DEFAULT_VEHICLE_INSPECTION_INTERNAL_LIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionInternalLight.notEquals=" + DEFAULT_VEHICLE_INSPECTION_INTERNAL_LIGHT);

        // Get all the vehicleInspectionsList where vehicleInspectionInternalLight not equals to UPDATED_VEHICLE_INSPECTION_INTERNAL_LIGHT
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionInternalLight.notEquals=" + UPDATED_VEHICLE_INSPECTION_INTERNAL_LIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionInternalLightIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionInternalLight in DEFAULT_VEHICLE_INSPECTION_INTERNAL_LIGHT or UPDATED_VEHICLE_INSPECTION_INTERNAL_LIGHT
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionInternalLight.in=" +
            DEFAULT_VEHICLE_INSPECTION_INTERNAL_LIGHT +
            "," +
            UPDATED_VEHICLE_INSPECTION_INTERNAL_LIGHT
        );

        // Get all the vehicleInspectionsList where vehicleInspectionInternalLight equals to UPDATED_VEHICLE_INSPECTION_INTERNAL_LIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionInternalLight.in=" + UPDATED_VEHICLE_INSPECTION_INTERNAL_LIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionInternalLightIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionInternalLight is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionInternalLight.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionInternalLight is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionInternalLight.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionPanelLightIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionPanelLight equals to DEFAULT_VEHICLE_INSPECTION_PANEL_LIGHT
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionPanelLight.equals=" + DEFAULT_VEHICLE_INSPECTION_PANEL_LIGHT);

        // Get all the vehicleInspectionsList where vehicleInspectionPanelLight equals to UPDATED_VEHICLE_INSPECTION_PANEL_LIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionPanelLight.equals=" + UPDATED_VEHICLE_INSPECTION_PANEL_LIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionPanelLightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionPanelLight not equals to DEFAULT_VEHICLE_INSPECTION_PANEL_LIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionPanelLight.notEquals=" + DEFAULT_VEHICLE_INSPECTION_PANEL_LIGHT);

        // Get all the vehicleInspectionsList where vehicleInspectionPanelLight not equals to UPDATED_VEHICLE_INSPECTION_PANEL_LIGHT
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionPanelLight.notEquals=" + UPDATED_VEHICLE_INSPECTION_PANEL_LIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionPanelLightIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionPanelLight in DEFAULT_VEHICLE_INSPECTION_PANEL_LIGHT or UPDATED_VEHICLE_INSPECTION_PANEL_LIGHT
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionPanelLight.in=" + DEFAULT_VEHICLE_INSPECTION_PANEL_LIGHT + "," + UPDATED_VEHICLE_INSPECTION_PANEL_LIGHT
        );

        // Get all the vehicleInspectionsList where vehicleInspectionPanelLight equals to UPDATED_VEHICLE_INSPECTION_PANEL_LIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionPanelLight.in=" + UPDATED_VEHICLE_INSPECTION_PANEL_LIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionPanelLightIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionPanelLight is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionPanelLight.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionPanelLight is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionPanelLight.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionHighLightIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionHighLight equals to DEFAULT_VEHICLE_INSPECTION_HIGH_LIGHT
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionHighLight.equals=" + DEFAULT_VEHICLE_INSPECTION_HIGH_LIGHT);

        // Get all the vehicleInspectionsList where vehicleInspectionHighLight equals to UPDATED_VEHICLE_INSPECTION_HIGH_LIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionHighLight.equals=" + UPDATED_VEHICLE_INSPECTION_HIGH_LIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionHighLightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionHighLight not equals to DEFAULT_VEHICLE_INSPECTION_HIGH_LIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionHighLight.notEquals=" + DEFAULT_VEHICLE_INSPECTION_HIGH_LIGHT);

        // Get all the vehicleInspectionsList where vehicleInspectionHighLight not equals to UPDATED_VEHICLE_INSPECTION_HIGH_LIGHT
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionHighLight.notEquals=" + UPDATED_VEHICLE_INSPECTION_HIGH_LIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionHighLightIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionHighLight in DEFAULT_VEHICLE_INSPECTION_HIGH_LIGHT or UPDATED_VEHICLE_INSPECTION_HIGH_LIGHT
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionHighLight.in=" + DEFAULT_VEHICLE_INSPECTION_HIGH_LIGHT + "," + UPDATED_VEHICLE_INSPECTION_HIGH_LIGHT
        );

        // Get all the vehicleInspectionsList where vehicleInspectionHighLight equals to UPDATED_VEHICLE_INSPECTION_HIGH_LIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionHighLight.in=" + UPDATED_VEHICLE_INSPECTION_HIGH_LIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionHighLightIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionHighLight is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionHighLight.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionHighLight is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionHighLight.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionLowLightIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionLowLight equals to DEFAULT_VEHICLE_INSPECTION_LOW_LIGHT
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionLowLight.equals=" + DEFAULT_VEHICLE_INSPECTION_LOW_LIGHT);

        // Get all the vehicleInspectionsList where vehicleInspectionLowLight equals to UPDATED_VEHICLE_INSPECTION_LOW_LIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionLowLight.equals=" + UPDATED_VEHICLE_INSPECTION_LOW_LIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionLowLightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionLowLight not equals to DEFAULT_VEHICLE_INSPECTION_LOW_LIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionLowLight.notEquals=" + DEFAULT_VEHICLE_INSPECTION_LOW_LIGHT);

        // Get all the vehicleInspectionsList where vehicleInspectionLowLight not equals to UPDATED_VEHICLE_INSPECTION_LOW_LIGHT
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionLowLight.notEquals=" + UPDATED_VEHICLE_INSPECTION_LOW_LIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionLowLightIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionLowLight in DEFAULT_VEHICLE_INSPECTION_LOW_LIGHT or UPDATED_VEHICLE_INSPECTION_LOW_LIGHT
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionLowLight.in=" + DEFAULT_VEHICLE_INSPECTION_LOW_LIGHT + "," + UPDATED_VEHICLE_INSPECTION_LOW_LIGHT
        );

        // Get all the vehicleInspectionsList where vehicleInspectionLowLight equals to UPDATED_VEHICLE_INSPECTION_LOW_LIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionLowLight.in=" + UPDATED_VEHICLE_INSPECTION_LOW_LIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionLowLightIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionLowLight is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionLowLight.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionLowLight is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionLowLight.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionTaillightIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionTaillight equals to DEFAULT_VEHICLE_INSPECTION_TAILLIGHT
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionTaillight.equals=" + DEFAULT_VEHICLE_INSPECTION_TAILLIGHT);

        // Get all the vehicleInspectionsList where vehicleInspectionTaillight equals to UPDATED_VEHICLE_INSPECTION_TAILLIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionTaillight.equals=" + UPDATED_VEHICLE_INSPECTION_TAILLIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionTaillightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionTaillight not equals to DEFAULT_VEHICLE_INSPECTION_TAILLIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionTaillight.notEquals=" + DEFAULT_VEHICLE_INSPECTION_TAILLIGHT);

        // Get all the vehicleInspectionsList where vehicleInspectionTaillight not equals to UPDATED_VEHICLE_INSPECTION_TAILLIGHT
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionTaillight.notEquals=" + UPDATED_VEHICLE_INSPECTION_TAILLIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionTaillightIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionTaillight in DEFAULT_VEHICLE_INSPECTION_TAILLIGHT or UPDATED_VEHICLE_INSPECTION_TAILLIGHT
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionTaillight.in=" + DEFAULT_VEHICLE_INSPECTION_TAILLIGHT + "," + UPDATED_VEHICLE_INSPECTION_TAILLIGHT
        );

        // Get all the vehicleInspectionsList where vehicleInspectionTaillight equals to UPDATED_VEHICLE_INSPECTION_TAILLIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionTaillight.in=" + UPDATED_VEHICLE_INSPECTION_TAILLIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionTaillightIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionTaillight is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionTaillight.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionTaillight is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionTaillight.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionIndicatorIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionIndicator equals to DEFAULT_VEHICLE_INSPECTION_INDICATOR
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionIndicator.equals=" + DEFAULT_VEHICLE_INSPECTION_INDICATOR);

        // Get all the vehicleInspectionsList where vehicleInspectionIndicator equals to UPDATED_VEHICLE_INSPECTION_INDICATOR
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionIndicator.equals=" + UPDATED_VEHICLE_INSPECTION_INDICATOR);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionIndicatorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionIndicator not equals to DEFAULT_VEHICLE_INSPECTION_INDICATOR
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionIndicator.notEquals=" + DEFAULT_VEHICLE_INSPECTION_INDICATOR);

        // Get all the vehicleInspectionsList where vehicleInspectionIndicator not equals to UPDATED_VEHICLE_INSPECTION_INDICATOR
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionIndicator.notEquals=" + UPDATED_VEHICLE_INSPECTION_INDICATOR);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionIndicatorIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionIndicator in DEFAULT_VEHICLE_INSPECTION_INDICATOR or UPDATED_VEHICLE_INSPECTION_INDICATOR
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionIndicator.in=" + DEFAULT_VEHICLE_INSPECTION_INDICATOR + "," + UPDATED_VEHICLE_INSPECTION_INDICATOR
        );

        // Get all the vehicleInspectionsList where vehicleInspectionIndicator equals to UPDATED_VEHICLE_INSPECTION_INDICATOR
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionIndicator.in=" + UPDATED_VEHICLE_INSPECTION_INDICATOR);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionIndicatorIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionIndicator is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionIndicator.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionIndicator is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionIndicator.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionBeaconsIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionBeacons equals to DEFAULT_VEHICLE_INSPECTION_BEACONS
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionBeacons.equals=" + DEFAULT_VEHICLE_INSPECTION_BEACONS);

        // Get all the vehicleInspectionsList where vehicleInspectionBeacons equals to UPDATED_VEHICLE_INSPECTION_BEACONS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionBeacons.equals=" + UPDATED_VEHICLE_INSPECTION_BEACONS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionBeaconsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionBeacons not equals to DEFAULT_VEHICLE_INSPECTION_BEACONS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionBeacons.notEquals=" + DEFAULT_VEHICLE_INSPECTION_BEACONS);

        // Get all the vehicleInspectionsList where vehicleInspectionBeacons not equals to UPDATED_VEHICLE_INSPECTION_BEACONS
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionBeacons.notEquals=" + UPDATED_VEHICLE_INSPECTION_BEACONS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionBeaconsIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionBeacons in DEFAULT_VEHICLE_INSPECTION_BEACONS or UPDATED_VEHICLE_INSPECTION_BEACONS
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionBeacons.in=" + DEFAULT_VEHICLE_INSPECTION_BEACONS + "," + UPDATED_VEHICLE_INSPECTION_BEACONS
        );

        // Get all the vehicleInspectionsList where vehicleInspectionBeacons equals to UPDATED_VEHICLE_INSPECTION_BEACONS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionBeacons.in=" + UPDATED_VEHICLE_INSPECTION_BEACONS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionBeaconsIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionBeacons is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionBeacons.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionBeacons is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionBeacons.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionBreakLightIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionBreakLight equals to DEFAULT_VEHICLE_INSPECTION_BREAK_LIGHT
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionBreakLight.equals=" + DEFAULT_VEHICLE_INSPECTION_BREAK_LIGHT);

        // Get all the vehicleInspectionsList where vehicleInspectionBreakLight equals to UPDATED_VEHICLE_INSPECTION_BREAK_LIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionBreakLight.equals=" + UPDATED_VEHICLE_INSPECTION_BREAK_LIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionBreakLightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionBreakLight not equals to DEFAULT_VEHICLE_INSPECTION_BREAK_LIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionBreakLight.notEquals=" + DEFAULT_VEHICLE_INSPECTION_BREAK_LIGHT);

        // Get all the vehicleInspectionsList where vehicleInspectionBreakLight not equals to UPDATED_VEHICLE_INSPECTION_BREAK_LIGHT
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionBreakLight.notEquals=" + UPDATED_VEHICLE_INSPECTION_BREAK_LIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionBreakLightIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionBreakLight in DEFAULT_VEHICLE_INSPECTION_BREAK_LIGHT or UPDATED_VEHICLE_INSPECTION_BREAK_LIGHT
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionBreakLight.in=" + DEFAULT_VEHICLE_INSPECTION_BREAK_LIGHT + "," + UPDATED_VEHICLE_INSPECTION_BREAK_LIGHT
        );

        // Get all the vehicleInspectionsList where vehicleInspectionBreakLight equals to UPDATED_VEHICLE_INSPECTION_BREAK_LIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionBreakLight.in=" + UPDATED_VEHICLE_INSPECTION_BREAK_LIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionBreakLightIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionBreakLight is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionBreakLight.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionBreakLight is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionBreakLight.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionPlateLightIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionPlateLight equals to DEFAULT_VEHICLE_INSPECTION_PLATE_LIGHT
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionPlateLight.equals=" + DEFAULT_VEHICLE_INSPECTION_PLATE_LIGHT);

        // Get all the vehicleInspectionsList where vehicleInspectionPlateLight equals to UPDATED_VEHICLE_INSPECTION_PLATE_LIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionPlateLight.equals=" + UPDATED_VEHICLE_INSPECTION_PLATE_LIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionPlateLightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionPlateLight not equals to DEFAULT_VEHICLE_INSPECTION_PLATE_LIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionPlateLight.notEquals=" + DEFAULT_VEHICLE_INSPECTION_PLATE_LIGHT);

        // Get all the vehicleInspectionsList where vehicleInspectionPlateLight not equals to UPDATED_VEHICLE_INSPECTION_PLATE_LIGHT
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionPlateLight.notEquals=" + UPDATED_VEHICLE_INSPECTION_PLATE_LIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionPlateLightIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionPlateLight in DEFAULT_VEHICLE_INSPECTION_PLATE_LIGHT or UPDATED_VEHICLE_INSPECTION_PLATE_LIGHT
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionPlateLight.in=" + DEFAULT_VEHICLE_INSPECTION_PLATE_LIGHT + "," + UPDATED_VEHICLE_INSPECTION_PLATE_LIGHT
        );

        // Get all the vehicleInspectionsList where vehicleInspectionPlateLight equals to UPDATED_VEHICLE_INSPECTION_PLATE_LIGHT
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionPlateLight.in=" + UPDATED_VEHICLE_INSPECTION_PLATE_LIGHT);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionPlateLightIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionPlateLight is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionPlateLight.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionPlateLight is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionPlateLight.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionSpeedometerIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionSpeedometer equals to DEFAULT_VEHICLE_INSPECTION_SPEEDOMETER
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionSpeedometer.equals=" + DEFAULT_VEHICLE_INSPECTION_SPEEDOMETER);

        // Get all the vehicleInspectionsList where vehicleInspectionSpeedometer equals to UPDATED_VEHICLE_INSPECTION_SPEEDOMETER
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionSpeedometer.equals=" + UPDATED_VEHICLE_INSPECTION_SPEEDOMETER);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionSpeedometerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionSpeedometer not equals to DEFAULT_VEHICLE_INSPECTION_SPEEDOMETER
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionSpeedometer.notEquals=" + DEFAULT_VEHICLE_INSPECTION_SPEEDOMETER);

        // Get all the vehicleInspectionsList where vehicleInspectionSpeedometer not equals to UPDATED_VEHICLE_INSPECTION_SPEEDOMETER
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionSpeedometer.notEquals=" + UPDATED_VEHICLE_INSPECTION_SPEEDOMETER);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionSpeedometerIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionSpeedometer in DEFAULT_VEHICLE_INSPECTION_SPEEDOMETER or UPDATED_VEHICLE_INSPECTION_SPEEDOMETER
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionSpeedometer.in=" + DEFAULT_VEHICLE_INSPECTION_SPEEDOMETER + "," + UPDATED_VEHICLE_INSPECTION_SPEEDOMETER
        );

        // Get all the vehicleInspectionsList where vehicleInspectionSpeedometer equals to UPDATED_VEHICLE_INSPECTION_SPEEDOMETER
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionSpeedometer.in=" + UPDATED_VEHICLE_INSPECTION_SPEEDOMETER);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionSpeedometerIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionSpeedometer is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionSpeedometer.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionSpeedometer is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionSpeedometer.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionTemperatureIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionTemperature equals to DEFAULT_VEHICLE_INSPECTION_TEMPERATURE
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionTemperature.equals=" + DEFAULT_VEHICLE_INSPECTION_TEMPERATURE);

        // Get all the vehicleInspectionsList where vehicleInspectionTemperature equals to UPDATED_VEHICLE_INSPECTION_TEMPERATURE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionTemperature.equals=" + UPDATED_VEHICLE_INSPECTION_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionTemperatureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionTemperature not equals to DEFAULT_VEHICLE_INSPECTION_TEMPERATURE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionTemperature.notEquals=" + DEFAULT_VEHICLE_INSPECTION_TEMPERATURE);

        // Get all the vehicleInspectionsList where vehicleInspectionTemperature not equals to UPDATED_VEHICLE_INSPECTION_TEMPERATURE
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionTemperature.notEquals=" + UPDATED_VEHICLE_INSPECTION_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionTemperatureIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionTemperature in DEFAULT_VEHICLE_INSPECTION_TEMPERATURE or UPDATED_VEHICLE_INSPECTION_TEMPERATURE
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionTemperature.in=" + DEFAULT_VEHICLE_INSPECTION_TEMPERATURE + "," + UPDATED_VEHICLE_INSPECTION_TEMPERATURE
        );

        // Get all the vehicleInspectionsList where vehicleInspectionTemperature equals to UPDATED_VEHICLE_INSPECTION_TEMPERATURE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionTemperature.in=" + UPDATED_VEHICLE_INSPECTION_TEMPERATURE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionTemperatureIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionTemperature is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionTemperature.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionTemperature is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionTemperature.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionTiresIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionTires equals to DEFAULT_VEHICLE_INSPECTION_TIRES
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionTires.equals=" + DEFAULT_VEHICLE_INSPECTION_TIRES);

        // Get all the vehicleInspectionsList where vehicleInspectionTires equals to UPDATED_VEHICLE_INSPECTION_TIRES
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionTires.equals=" + UPDATED_VEHICLE_INSPECTION_TIRES);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionTiresIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionTires not equals to DEFAULT_VEHICLE_INSPECTION_TIRES
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionTires.notEquals=" + DEFAULT_VEHICLE_INSPECTION_TIRES);

        // Get all the vehicleInspectionsList where vehicleInspectionTires not equals to UPDATED_VEHICLE_INSPECTION_TIRES
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionTires.notEquals=" + UPDATED_VEHICLE_INSPECTION_TIRES);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionTiresIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionTires in DEFAULT_VEHICLE_INSPECTION_TIRES or UPDATED_VEHICLE_INSPECTION_TIRES
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionTires.in=" + DEFAULT_VEHICLE_INSPECTION_TIRES + "," + UPDATED_VEHICLE_INSPECTION_TIRES
        );

        // Get all the vehicleInspectionsList where vehicleInspectionTires equals to UPDATED_VEHICLE_INSPECTION_TIRES
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionTires.in=" + UPDATED_VEHICLE_INSPECTION_TIRES);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionTiresIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionTires is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionTires.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionTires is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionTires.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionStepIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionStep equals to DEFAULT_VEHICLE_INSPECTION_STEP
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionStep.equals=" + DEFAULT_VEHICLE_INSPECTION_STEP);

        // Get all the vehicleInspectionsList where vehicleInspectionStep equals to UPDATED_VEHICLE_INSPECTION_STEP
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionStep.equals=" + UPDATED_VEHICLE_INSPECTION_STEP);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionStepIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionStep not equals to DEFAULT_VEHICLE_INSPECTION_STEP
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionStep.notEquals=" + DEFAULT_VEHICLE_INSPECTION_STEP);

        // Get all the vehicleInspectionsList where vehicleInspectionStep not equals to UPDATED_VEHICLE_INSPECTION_STEP
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionStep.notEquals=" + UPDATED_VEHICLE_INSPECTION_STEP);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionStepIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionStep in DEFAULT_VEHICLE_INSPECTION_STEP or UPDATED_VEHICLE_INSPECTION_STEP
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionStep.in=" + DEFAULT_VEHICLE_INSPECTION_STEP + "," + UPDATED_VEHICLE_INSPECTION_STEP
        );

        // Get all the vehicleInspectionsList where vehicleInspectionStep equals to UPDATED_VEHICLE_INSPECTION_STEP
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionStep.in=" + UPDATED_VEHICLE_INSPECTION_STEP);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionStepIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionStep is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionStep.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionStep is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionStep.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionFireExtinguisherIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionFireExtinguisher equals to DEFAULT_VEHICLE_INSPECTION_FIRE_EXTINGUISHER
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionFireExtinguisher.equals=" + DEFAULT_VEHICLE_INSPECTION_FIRE_EXTINGUISHER);

        // Get all the vehicleInspectionsList where vehicleInspectionFireExtinguisher equals to UPDATED_VEHICLE_INSPECTION_FIRE_EXTINGUISHER
        defaultVehicleInspectionsShouldNotBeFound(
            "vehicleInspectionFireExtinguisher.equals=" + UPDATED_VEHICLE_INSPECTION_FIRE_EXTINGUISHER
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionFireExtinguisherIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionFireExtinguisher not equals to DEFAULT_VEHICLE_INSPECTION_FIRE_EXTINGUISHER
        defaultVehicleInspectionsShouldNotBeFound(
            "vehicleInspectionFireExtinguisher.notEquals=" + DEFAULT_VEHICLE_INSPECTION_FIRE_EXTINGUISHER
        );

        // Get all the vehicleInspectionsList where vehicleInspectionFireExtinguisher not equals to UPDATED_VEHICLE_INSPECTION_FIRE_EXTINGUISHER
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionFireExtinguisher.notEquals=" + UPDATED_VEHICLE_INSPECTION_FIRE_EXTINGUISHER
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionFireExtinguisherIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionFireExtinguisher in DEFAULT_VEHICLE_INSPECTION_FIRE_EXTINGUISHER or UPDATED_VEHICLE_INSPECTION_FIRE_EXTINGUISHER
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionFireExtinguisher.in=" +
            DEFAULT_VEHICLE_INSPECTION_FIRE_EXTINGUISHER +
            "," +
            UPDATED_VEHICLE_INSPECTION_FIRE_EXTINGUISHER
        );

        // Get all the vehicleInspectionsList where vehicleInspectionFireExtinguisher equals to UPDATED_VEHICLE_INSPECTION_FIRE_EXTINGUISHER
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionFireExtinguisher.in=" + UPDATED_VEHICLE_INSPECTION_FIRE_EXTINGUISHER);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionFireExtinguisherIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionFireExtinguisher is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionFireExtinguisher.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionFireExtinguisher is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionFireExtinguisher.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionSeatBeltsIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionSeatBelts equals to DEFAULT_VEHICLE_INSPECTION_SEAT_BELTS
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionSeatBelts.equals=" + DEFAULT_VEHICLE_INSPECTION_SEAT_BELTS);

        // Get all the vehicleInspectionsList where vehicleInspectionSeatBelts equals to UPDATED_VEHICLE_INSPECTION_SEAT_BELTS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionSeatBelts.equals=" + UPDATED_VEHICLE_INSPECTION_SEAT_BELTS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionSeatBeltsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionSeatBelts not equals to DEFAULT_VEHICLE_INSPECTION_SEAT_BELTS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionSeatBelts.notEquals=" + DEFAULT_VEHICLE_INSPECTION_SEAT_BELTS);

        // Get all the vehicleInspectionsList where vehicleInspectionSeatBelts not equals to UPDATED_VEHICLE_INSPECTION_SEAT_BELTS
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionSeatBelts.notEquals=" + UPDATED_VEHICLE_INSPECTION_SEAT_BELTS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionSeatBeltsIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionSeatBelts in DEFAULT_VEHICLE_INSPECTION_SEAT_BELTS or UPDATED_VEHICLE_INSPECTION_SEAT_BELTS
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionSeatBelts.in=" + DEFAULT_VEHICLE_INSPECTION_SEAT_BELTS + "," + UPDATED_VEHICLE_INSPECTION_SEAT_BELTS
        );

        // Get all the vehicleInspectionsList where vehicleInspectionSeatBelts equals to UPDATED_VEHICLE_INSPECTION_SEAT_BELTS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionSeatBelts.in=" + UPDATED_VEHICLE_INSPECTION_SEAT_BELTS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionSeatBeltsIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionSeatBelts is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionSeatBelts.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionSeatBelts is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionSeatBelts.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionMonkeyIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionMonkey equals to DEFAULT_VEHICLE_INSPECTION_MONKEY
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionMonkey.equals=" + DEFAULT_VEHICLE_INSPECTION_MONKEY);

        // Get all the vehicleInspectionsList where vehicleInspectionMonkey equals to UPDATED_VEHICLE_INSPECTION_MONKEY
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionMonkey.equals=" + UPDATED_VEHICLE_INSPECTION_MONKEY);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionMonkeyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionMonkey not equals to DEFAULT_VEHICLE_INSPECTION_MONKEY
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionMonkey.notEquals=" + DEFAULT_VEHICLE_INSPECTION_MONKEY);

        // Get all the vehicleInspectionsList where vehicleInspectionMonkey not equals to UPDATED_VEHICLE_INSPECTION_MONKEY
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionMonkey.notEquals=" + UPDATED_VEHICLE_INSPECTION_MONKEY);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionMonkeyIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionMonkey in DEFAULT_VEHICLE_INSPECTION_MONKEY or UPDATED_VEHICLE_INSPECTION_MONKEY
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionMonkey.in=" + DEFAULT_VEHICLE_INSPECTION_MONKEY + "," + UPDATED_VEHICLE_INSPECTION_MONKEY
        );

        // Get all the vehicleInspectionsList where vehicleInspectionMonkey equals to UPDATED_VEHICLE_INSPECTION_MONKEY
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionMonkey.in=" + UPDATED_VEHICLE_INSPECTION_MONKEY);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionMonkeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionMonkey is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionMonkey.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionMonkey is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionMonkey.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionTireIronIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionTireIron equals to DEFAULT_VEHICLE_INSPECTION_TIRE_IRON
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionTireIron.equals=" + DEFAULT_VEHICLE_INSPECTION_TIRE_IRON);

        // Get all the vehicleInspectionsList where vehicleInspectionTireIron equals to UPDATED_VEHICLE_INSPECTION_TIRE_IRON
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionTireIron.equals=" + UPDATED_VEHICLE_INSPECTION_TIRE_IRON);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionTireIronIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionTireIron not equals to DEFAULT_VEHICLE_INSPECTION_TIRE_IRON
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionTireIron.notEquals=" + DEFAULT_VEHICLE_INSPECTION_TIRE_IRON);

        // Get all the vehicleInspectionsList where vehicleInspectionTireIron not equals to UPDATED_VEHICLE_INSPECTION_TIRE_IRON
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionTireIron.notEquals=" + UPDATED_VEHICLE_INSPECTION_TIRE_IRON);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionTireIronIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionTireIron in DEFAULT_VEHICLE_INSPECTION_TIRE_IRON or UPDATED_VEHICLE_INSPECTION_TIRE_IRON
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionTireIron.in=" + DEFAULT_VEHICLE_INSPECTION_TIRE_IRON + "," + UPDATED_VEHICLE_INSPECTION_TIRE_IRON
        );

        // Get all the vehicleInspectionsList where vehicleInspectionTireIron equals to UPDATED_VEHICLE_INSPECTION_TIRE_IRON
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionTireIron.in=" + UPDATED_VEHICLE_INSPECTION_TIRE_IRON);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionTireIronIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionTireIron is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionTireIron.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionTireIron is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionTireIron.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionRadiatorCapIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionRadiatorCap equals to DEFAULT_VEHICLE_INSPECTION_RADIATOR_CAP
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionRadiatorCap.equals=" + DEFAULT_VEHICLE_INSPECTION_RADIATOR_CAP);

        // Get all the vehicleInspectionsList where vehicleInspectionRadiatorCap equals to UPDATED_VEHICLE_INSPECTION_RADIATOR_CAP
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionRadiatorCap.equals=" + UPDATED_VEHICLE_INSPECTION_RADIATOR_CAP);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionRadiatorCapIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionRadiatorCap not equals to DEFAULT_VEHICLE_INSPECTION_RADIATOR_CAP
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionRadiatorCap.notEquals=" + DEFAULT_VEHICLE_INSPECTION_RADIATOR_CAP);

        // Get all the vehicleInspectionsList where vehicleInspectionRadiatorCap not equals to UPDATED_VEHICLE_INSPECTION_RADIATOR_CAP
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionRadiatorCap.notEquals=" + UPDATED_VEHICLE_INSPECTION_RADIATOR_CAP);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionRadiatorCapIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionRadiatorCap in DEFAULT_VEHICLE_INSPECTION_RADIATOR_CAP or UPDATED_VEHICLE_INSPECTION_RADIATOR_CAP
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionRadiatorCap.in=" + DEFAULT_VEHICLE_INSPECTION_RADIATOR_CAP + "," + UPDATED_VEHICLE_INSPECTION_RADIATOR_CAP
        );

        // Get all the vehicleInspectionsList where vehicleInspectionRadiatorCap equals to UPDATED_VEHICLE_INSPECTION_RADIATOR_CAP
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionRadiatorCap.in=" + UPDATED_VEHICLE_INSPECTION_RADIATOR_CAP);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionRadiatorCapIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionRadiatorCap is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionRadiatorCap.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionRadiatorCap is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionRadiatorCap.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionTriangleIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionTriangle equals to DEFAULT_VEHICLE_INSPECTION_TRIANGLE
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionTriangle.equals=" + DEFAULT_VEHICLE_INSPECTION_TRIANGLE);

        // Get all the vehicleInspectionsList where vehicleInspectionTriangle equals to UPDATED_VEHICLE_INSPECTION_TRIANGLE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionTriangle.equals=" + UPDATED_VEHICLE_INSPECTION_TRIANGLE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionTriangleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionTriangle not equals to DEFAULT_VEHICLE_INSPECTION_TRIANGLE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionTriangle.notEquals=" + DEFAULT_VEHICLE_INSPECTION_TRIANGLE);

        // Get all the vehicleInspectionsList where vehicleInspectionTriangle not equals to UPDATED_VEHICLE_INSPECTION_TRIANGLE
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionTriangle.notEquals=" + UPDATED_VEHICLE_INSPECTION_TRIANGLE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionTriangleIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionTriangle in DEFAULT_VEHICLE_INSPECTION_TRIANGLE or UPDATED_VEHICLE_INSPECTION_TRIANGLE
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionTriangle.in=" + DEFAULT_VEHICLE_INSPECTION_TRIANGLE + "," + UPDATED_VEHICLE_INSPECTION_TRIANGLE
        );

        // Get all the vehicleInspectionsList where vehicleInspectionTriangle equals to UPDATED_VEHICLE_INSPECTION_TRIANGLE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionTriangle.in=" + UPDATED_VEHICLE_INSPECTION_TRIANGLE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionTriangleIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionTriangle is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionTriangle.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionTriangle is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionTriangle.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionServiceBrakeIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionServiceBrake equals to DEFAULT_VEHICLE_INSPECTION_SERVICE_BRAKE
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionServiceBrake.equals=" + DEFAULT_VEHICLE_INSPECTION_SERVICE_BRAKE);

        // Get all the vehicleInspectionsList where vehicleInspectionServiceBrake equals to UPDATED_VEHICLE_INSPECTION_SERVICE_BRAKE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionServiceBrake.equals=" + UPDATED_VEHICLE_INSPECTION_SERVICE_BRAKE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionServiceBrakeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionServiceBrake not equals to DEFAULT_VEHICLE_INSPECTION_SERVICE_BRAKE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionServiceBrake.notEquals=" + DEFAULT_VEHICLE_INSPECTION_SERVICE_BRAKE);

        // Get all the vehicleInspectionsList where vehicleInspectionServiceBrake not equals to UPDATED_VEHICLE_INSPECTION_SERVICE_BRAKE
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionServiceBrake.notEquals=" + UPDATED_VEHICLE_INSPECTION_SERVICE_BRAKE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionServiceBrakeIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionServiceBrake in DEFAULT_VEHICLE_INSPECTION_SERVICE_BRAKE or UPDATED_VEHICLE_INSPECTION_SERVICE_BRAKE
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionServiceBrake.in=" + DEFAULT_VEHICLE_INSPECTION_SERVICE_BRAKE + "," + UPDATED_VEHICLE_INSPECTION_SERVICE_BRAKE
        );

        // Get all the vehicleInspectionsList where vehicleInspectionServiceBrake equals to UPDATED_VEHICLE_INSPECTION_SERVICE_BRAKE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionServiceBrake.in=" + UPDATED_VEHICLE_INSPECTION_SERVICE_BRAKE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionServiceBrakeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionServiceBrake is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionServiceBrake.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionServiceBrake is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionServiceBrake.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionParkingBrakeIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionParkingBrake equals to DEFAULT_VEHICLE_INSPECTION_PARKING_BRAKE
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionParkingBrake.equals=" + DEFAULT_VEHICLE_INSPECTION_PARKING_BRAKE);

        // Get all the vehicleInspectionsList where vehicleInspectionParkingBrake equals to UPDATED_VEHICLE_INSPECTION_PARKING_BRAKE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionParkingBrake.equals=" + UPDATED_VEHICLE_INSPECTION_PARKING_BRAKE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionParkingBrakeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionParkingBrake not equals to DEFAULT_VEHICLE_INSPECTION_PARKING_BRAKE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionParkingBrake.notEquals=" + DEFAULT_VEHICLE_INSPECTION_PARKING_BRAKE);

        // Get all the vehicleInspectionsList where vehicleInspectionParkingBrake not equals to UPDATED_VEHICLE_INSPECTION_PARKING_BRAKE
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionParkingBrake.notEquals=" + UPDATED_VEHICLE_INSPECTION_PARKING_BRAKE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionParkingBrakeIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionParkingBrake in DEFAULT_VEHICLE_INSPECTION_PARKING_BRAKE or UPDATED_VEHICLE_INSPECTION_PARKING_BRAKE
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionParkingBrake.in=" + DEFAULT_VEHICLE_INSPECTION_PARKING_BRAKE + "," + UPDATED_VEHICLE_INSPECTION_PARKING_BRAKE
        );

        // Get all the vehicleInspectionsList where vehicleInspectionParkingBrake equals to UPDATED_VEHICLE_INSPECTION_PARKING_BRAKE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionParkingBrake.in=" + UPDATED_VEHICLE_INSPECTION_PARKING_BRAKE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionParkingBrakeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionParkingBrake is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionParkingBrake.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionParkingBrake is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionParkingBrake.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionOilLeaksIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionOilLeaks equals to DEFAULT_VEHICLE_INSPECTION_OIL_LEAKS
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionOilLeaks.equals=" + DEFAULT_VEHICLE_INSPECTION_OIL_LEAKS);

        // Get all the vehicleInspectionsList where vehicleInspectionOilLeaks equals to UPDATED_VEHICLE_INSPECTION_OIL_LEAKS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionOilLeaks.equals=" + UPDATED_VEHICLE_INSPECTION_OIL_LEAKS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionOilLeaksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionOilLeaks not equals to DEFAULT_VEHICLE_INSPECTION_OIL_LEAKS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionOilLeaks.notEquals=" + DEFAULT_VEHICLE_INSPECTION_OIL_LEAKS);

        // Get all the vehicleInspectionsList where vehicleInspectionOilLeaks not equals to UPDATED_VEHICLE_INSPECTION_OIL_LEAKS
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionOilLeaks.notEquals=" + UPDATED_VEHICLE_INSPECTION_OIL_LEAKS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionOilLeaksIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionOilLeaks in DEFAULT_VEHICLE_INSPECTION_OIL_LEAKS or UPDATED_VEHICLE_INSPECTION_OIL_LEAKS
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionOilLeaks.in=" + DEFAULT_VEHICLE_INSPECTION_OIL_LEAKS + "," + UPDATED_VEHICLE_INSPECTION_OIL_LEAKS
        );

        // Get all the vehicleInspectionsList where vehicleInspectionOilLeaks equals to UPDATED_VEHICLE_INSPECTION_OIL_LEAKS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionOilLeaks.in=" + UPDATED_VEHICLE_INSPECTION_OIL_LEAKS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionOilLeaksIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionOilLeaks is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionOilLeaks.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionOilLeaks is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionOilLeaks.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionGlassActuatorIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionGlassActuator equals to DEFAULT_VEHICLE_INSPECTION_GLASS_ACTUATOR
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionGlassActuator.equals=" + DEFAULT_VEHICLE_INSPECTION_GLASS_ACTUATOR);

        // Get all the vehicleInspectionsList where vehicleInspectionGlassActuator equals to UPDATED_VEHICLE_INSPECTION_GLASS_ACTUATOR
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionGlassActuator.equals=" + UPDATED_VEHICLE_INSPECTION_GLASS_ACTUATOR);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionGlassActuatorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionGlassActuator not equals to DEFAULT_VEHICLE_INSPECTION_GLASS_ACTUATOR
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionGlassActuator.notEquals=" + DEFAULT_VEHICLE_INSPECTION_GLASS_ACTUATOR);

        // Get all the vehicleInspectionsList where vehicleInspectionGlassActuator not equals to UPDATED_VEHICLE_INSPECTION_GLASS_ACTUATOR
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionGlassActuator.notEquals=" + UPDATED_VEHICLE_INSPECTION_GLASS_ACTUATOR);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionGlassActuatorIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionGlassActuator in DEFAULT_VEHICLE_INSPECTION_GLASS_ACTUATOR or UPDATED_VEHICLE_INSPECTION_GLASS_ACTUATOR
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionGlassActuator.in=" +
            DEFAULT_VEHICLE_INSPECTION_GLASS_ACTUATOR +
            "," +
            UPDATED_VEHICLE_INSPECTION_GLASS_ACTUATOR
        );

        // Get all the vehicleInspectionsList where vehicleInspectionGlassActuator equals to UPDATED_VEHICLE_INSPECTION_GLASS_ACTUATOR
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionGlassActuator.in=" + UPDATED_VEHICLE_INSPECTION_GLASS_ACTUATOR);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionGlassActuatorIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionGlassActuator is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionGlassActuator.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionGlassActuator is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionGlassActuator.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionVehicleCleaningIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionVehicleCleaning equals to DEFAULT_VEHICLE_INSPECTION_VEHICLE_CLEANING
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionVehicleCleaning.equals=" + DEFAULT_VEHICLE_INSPECTION_VEHICLE_CLEANING);

        // Get all the vehicleInspectionsList where vehicleInspectionVehicleCleaning equals to UPDATED_VEHICLE_INSPECTION_VEHICLE_CLEANING
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionVehicleCleaning.equals=" + UPDATED_VEHICLE_INSPECTION_VEHICLE_CLEANING);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionVehicleCleaningIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionVehicleCleaning not equals to DEFAULT_VEHICLE_INSPECTION_VEHICLE_CLEANING
        defaultVehicleInspectionsShouldNotBeFound(
            "vehicleInspectionVehicleCleaning.notEquals=" + DEFAULT_VEHICLE_INSPECTION_VEHICLE_CLEANING
        );

        // Get all the vehicleInspectionsList where vehicleInspectionVehicleCleaning not equals to UPDATED_VEHICLE_INSPECTION_VEHICLE_CLEANING
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionVehicleCleaning.notEquals=" + UPDATED_VEHICLE_INSPECTION_VEHICLE_CLEANING);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionVehicleCleaningIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionVehicleCleaning in DEFAULT_VEHICLE_INSPECTION_VEHICLE_CLEANING or UPDATED_VEHICLE_INSPECTION_VEHICLE_CLEANING
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionVehicleCleaning.in=" +
            DEFAULT_VEHICLE_INSPECTION_VEHICLE_CLEANING +
            "," +
            UPDATED_VEHICLE_INSPECTION_VEHICLE_CLEANING
        );

        // Get all the vehicleInspectionsList where vehicleInspectionVehicleCleaning equals to UPDATED_VEHICLE_INSPECTION_VEHICLE_CLEANING
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionVehicleCleaning.in=" + UPDATED_VEHICLE_INSPECTION_VEHICLE_CLEANING);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionVehicleCleaningIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionVehicleCleaning is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionVehicleCleaning.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionVehicleCleaning is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionVehicleCleaning.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionSeatStateIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionSeatState equals to DEFAULT_VEHICLE_INSPECTION_SEAT_STATE
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionSeatState.equals=" + DEFAULT_VEHICLE_INSPECTION_SEAT_STATE);

        // Get all the vehicleInspectionsList where vehicleInspectionSeatState equals to UPDATED_VEHICLE_INSPECTION_SEAT_STATE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionSeatState.equals=" + UPDATED_VEHICLE_INSPECTION_SEAT_STATE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionSeatStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionSeatState not equals to DEFAULT_VEHICLE_INSPECTION_SEAT_STATE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionSeatState.notEquals=" + DEFAULT_VEHICLE_INSPECTION_SEAT_STATE);

        // Get all the vehicleInspectionsList where vehicleInspectionSeatState not equals to UPDATED_VEHICLE_INSPECTION_SEAT_STATE
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionSeatState.notEquals=" + UPDATED_VEHICLE_INSPECTION_SEAT_STATE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionSeatStateIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionSeatState in DEFAULT_VEHICLE_INSPECTION_SEAT_STATE or UPDATED_VEHICLE_INSPECTION_SEAT_STATE
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionSeatState.in=" + DEFAULT_VEHICLE_INSPECTION_SEAT_STATE + "," + UPDATED_VEHICLE_INSPECTION_SEAT_STATE
        );

        // Get all the vehicleInspectionsList where vehicleInspectionSeatState equals to UPDATED_VEHICLE_INSPECTION_SEAT_STATE
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionSeatState.in=" + UPDATED_VEHICLE_INSPECTION_SEAT_STATE);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionSeatStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionSeatState is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionSeatState.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionSeatState is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionSeatState.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionExhaustsIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionExhausts equals to DEFAULT_VEHICLE_INSPECTION_EXHAUSTS
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionExhausts.equals=" + DEFAULT_VEHICLE_INSPECTION_EXHAUSTS);

        // Get all the vehicleInspectionsList where vehicleInspectionExhausts equals to UPDATED_VEHICLE_INSPECTION_EXHAUSTS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionExhausts.equals=" + UPDATED_VEHICLE_INSPECTION_EXHAUSTS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionExhaustsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionExhausts not equals to DEFAULT_VEHICLE_INSPECTION_EXHAUSTS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionExhausts.notEquals=" + DEFAULT_VEHICLE_INSPECTION_EXHAUSTS);

        // Get all the vehicleInspectionsList where vehicleInspectionExhausts not equals to UPDATED_VEHICLE_INSPECTION_EXHAUSTS
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionExhausts.notEquals=" + UPDATED_VEHICLE_INSPECTION_EXHAUSTS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionExhaustsIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionExhausts in DEFAULT_VEHICLE_INSPECTION_EXHAUSTS or UPDATED_VEHICLE_INSPECTION_EXHAUSTS
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionExhausts.in=" + DEFAULT_VEHICLE_INSPECTION_EXHAUSTS + "," + UPDATED_VEHICLE_INSPECTION_EXHAUSTS
        );

        // Get all the vehicleInspectionsList where vehicleInspectionExhausts equals to UPDATED_VEHICLE_INSPECTION_EXHAUSTS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionExhausts.in=" + UPDATED_VEHICLE_INSPECTION_EXHAUSTS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionExhaustsIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionExhausts is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionExhausts.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionExhausts is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionExhausts.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionsObsIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionsObs equals to DEFAULT_VEHICLE_INSPECTIONS_OBS
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionsObs.equals=" + DEFAULT_VEHICLE_INSPECTIONS_OBS);

        // Get all the vehicleInspectionsList where vehicleInspectionsObs equals to UPDATED_VEHICLE_INSPECTIONS_OBS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionsObs.equals=" + UPDATED_VEHICLE_INSPECTIONS_OBS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionsObsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionsObs not equals to DEFAULT_VEHICLE_INSPECTIONS_OBS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionsObs.notEquals=" + DEFAULT_VEHICLE_INSPECTIONS_OBS);

        // Get all the vehicleInspectionsList where vehicleInspectionsObs not equals to UPDATED_VEHICLE_INSPECTIONS_OBS
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionsObs.notEquals=" + UPDATED_VEHICLE_INSPECTIONS_OBS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionsObsIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionsObs in DEFAULT_VEHICLE_INSPECTIONS_OBS or UPDATED_VEHICLE_INSPECTIONS_OBS
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionsObs.in=" + DEFAULT_VEHICLE_INSPECTIONS_OBS + "," + UPDATED_VEHICLE_INSPECTIONS_OBS
        );

        // Get all the vehicleInspectionsList where vehicleInspectionsObs equals to UPDATED_VEHICLE_INSPECTIONS_OBS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionsObs.in=" + UPDATED_VEHICLE_INSPECTIONS_OBS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionsObsIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionsObs is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionsObs.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionsObs is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionsObs.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionsObsContainsSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionsObs contains DEFAULT_VEHICLE_INSPECTIONS_OBS
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionsObs.contains=" + DEFAULT_VEHICLE_INSPECTIONS_OBS);

        // Get all the vehicleInspectionsList where vehicleInspectionsObs contains UPDATED_VEHICLE_INSPECTIONS_OBS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionsObs.contains=" + UPDATED_VEHICLE_INSPECTIONS_OBS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionsObsNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionsObs does not contain DEFAULT_VEHICLE_INSPECTIONS_OBS
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionsObs.doesNotContain=" + DEFAULT_VEHICLE_INSPECTIONS_OBS);

        // Get all the vehicleInspectionsList where vehicleInspectionsObs does not contain UPDATED_VEHICLE_INSPECTIONS_OBS
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionsObs.doesNotContain=" + UPDATED_VEHICLE_INSPECTIONS_OBS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionsSignedUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionsSignedUrl equals to DEFAULT_VEHICLE_INSPECTIONS_SIGNED_URL
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionsSignedUrl.equals=" + DEFAULT_VEHICLE_INSPECTIONS_SIGNED_URL);

        // Get all the vehicleInspectionsList where vehicleInspectionsSignedUrl equals to UPDATED_VEHICLE_INSPECTIONS_SIGNED_URL
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionsSignedUrl.equals=" + UPDATED_VEHICLE_INSPECTIONS_SIGNED_URL);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionsSignedUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionsSignedUrl not equals to DEFAULT_VEHICLE_INSPECTIONS_SIGNED_URL
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionsSignedUrl.notEquals=" + DEFAULT_VEHICLE_INSPECTIONS_SIGNED_URL);

        // Get all the vehicleInspectionsList where vehicleInspectionsSignedUrl not equals to UPDATED_VEHICLE_INSPECTIONS_SIGNED_URL
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionsSignedUrl.notEquals=" + UPDATED_VEHICLE_INSPECTIONS_SIGNED_URL);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionsSignedUrlIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionsSignedUrl in DEFAULT_VEHICLE_INSPECTIONS_SIGNED_URL or UPDATED_VEHICLE_INSPECTIONS_SIGNED_URL
        defaultVehicleInspectionsShouldBeFound(
            "vehicleInspectionsSignedUrl.in=" + DEFAULT_VEHICLE_INSPECTIONS_SIGNED_URL + "," + UPDATED_VEHICLE_INSPECTIONS_SIGNED_URL
        );

        // Get all the vehicleInspectionsList where vehicleInspectionsSignedUrl equals to UPDATED_VEHICLE_INSPECTIONS_SIGNED_URL
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionsSignedUrl.in=" + UPDATED_VEHICLE_INSPECTIONS_SIGNED_URL);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionsSignedUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionsSignedUrl is not null
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionsSignedUrl.specified=true");

        // Get all the vehicleInspectionsList where vehicleInspectionsSignedUrl is null
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionsSignedUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionsSignedUrlContainsSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionsSignedUrl contains DEFAULT_VEHICLE_INSPECTIONS_SIGNED_URL
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionsSignedUrl.contains=" + DEFAULT_VEHICLE_INSPECTIONS_SIGNED_URL);

        // Get all the vehicleInspectionsList where vehicleInspectionsSignedUrl contains UPDATED_VEHICLE_INSPECTIONS_SIGNED_URL
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionsSignedUrl.contains=" + UPDATED_VEHICLE_INSPECTIONS_SIGNED_URL);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionsSignedUrlNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        // Get all the vehicleInspectionsList where vehicleInspectionsSignedUrl does not contain DEFAULT_VEHICLE_INSPECTIONS_SIGNED_URL
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionsSignedUrl.doesNotContain=" + DEFAULT_VEHICLE_INSPECTIONS_SIGNED_URL);

        // Get all the vehicleInspectionsList where vehicleInspectionsSignedUrl does not contain UPDATED_VEHICLE_INSPECTIONS_SIGNED_URL
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionsSignedUrl.doesNotContain=" + UPDATED_VEHICLE_INSPECTIONS_SIGNED_URL);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleInspectionsImagensIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);
        VehicleInspectionsImagens vehicleInspectionsImagens = VehicleInspectionsImagensResourceIT.createEntity(em);
        em.persist(vehicleInspectionsImagens);
        em.flush();
        vehicleInspections.addVehicleInspectionsImagens(vehicleInspectionsImagens);
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);
        Long vehicleInspectionsImagensId = vehicleInspectionsImagens.getId();

        // Get all the vehicleInspectionsList where vehicleInspectionsImagens equals to vehicleInspectionsImagensId
        defaultVehicleInspectionsShouldBeFound("vehicleInspectionsImagensId.equals=" + vehicleInspectionsImagensId);

        // Get all the vehicleInspectionsList where vehicleInspectionsImagens equals to (vehicleInspectionsImagensId + 1)
        defaultVehicleInspectionsShouldNotBeFound("vehicleInspectionsImagensId.equals=" + (vehicleInspectionsImagensId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsByVehicleControlsIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);
        VehicleControlItem vehicleControls = VehicleControlItemResourceIT.createEntity(em);
        em.persist(vehicleControls);
        em.flush();
        vehicleInspections.setVehicleControls(vehicleControls);
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);
        Long vehicleControlsId = vehicleControls.getId();

        // Get all the vehicleInspectionsList where vehicleControls equals to vehicleControlsId
        defaultVehicleInspectionsShouldBeFound("vehicleControlsId.equals=" + vehicleControlsId);

        // Get all the vehicleInspectionsList where vehicleControls equals to (vehicleControlsId + 1)
        defaultVehicleInspectionsShouldNotBeFound("vehicleControlsId.equals=" + (vehicleControlsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVehicleInspectionsShouldBeFound(String filter) throws Exception {
        restVehicleInspectionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleInspections.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleInspectionDate").value(hasItem(sameInstant(DEFAULT_VEHICLE_INSPECTION_DATE))))
            .andExpect(jsonPath("$.[*].vehicleInspectionStatus").value(hasItem(DEFAULT_VEHICLE_INSPECTION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionModel").value(hasItem(DEFAULT_VEHICLE_INSPECTION_MODEL)))
            .andExpect(jsonPath("$.[*].vehicleInspectionLicensePlate").value(hasItem(DEFAULT_VEHICLE_INSPECTION_LICENSE_PLATE)))
            .andExpect(jsonPath("$.[*].vehicleInspectionKm").value(hasItem(DEFAULT_VEHICLE_INSPECTION_KM.doubleValue())))
            .andExpect(jsonPath("$.[*].vehicleInspectionLicenseYear").value(hasItem(DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR.doubleValue())))
            .andExpect(jsonPath("$.[*].vehicleInspectionHasManual").value(hasItem(DEFAULT_VEHICLE_INSPECTION_HAS_MANUAL.booleanValue())))
            .andExpect(
                jsonPath("$.[*].vehicleInspectionHasExtraKey").value(hasItem(DEFAULT_VEHICLE_INSPECTION_HAS_EXTRA_KEY.booleanValue()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleInspectionHasStickers").value(hasItem(DEFAULT_VEHICLE_INSPECTION_HAS_STICKERS.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].vehicleInspectionGas").value(hasItem(DEFAULT_VEHICLE_INSPECTION_GAS.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionRearView").value(hasItem(DEFAULT_VEHICLE_INSPECTION_REAR_VIEW.booleanValue())))
            .andExpect(jsonPath("$.[*].vehicleInspectionHorn").value(hasItem(DEFAULT_VEHICLE_INSPECTION_HORN.booleanValue())))
            .andExpect(
                jsonPath("$.[*].vehicleInspectionWindshieldWiper")
                    .value(hasItem(DEFAULT_VEHICLE_INSPECTION_WINDSHIELD_WIPER.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].vehicleInspectionSquirt").value(hasItem(DEFAULT_VEHICLE_INSPECTION_SQUIRT.booleanValue())))
            .andExpect(
                jsonPath("$.[*].vehicleInspectionInternalLight").value(hasItem(DEFAULT_VEHICLE_INSPECTION_INTERNAL_LIGHT.toString()))
            )
            .andExpect(jsonPath("$.[*].vehicleInspectionPanelLight").value(hasItem(DEFAULT_VEHICLE_INSPECTION_PANEL_LIGHT.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionHighLight").value(hasItem(DEFAULT_VEHICLE_INSPECTION_HIGH_LIGHT.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionLowLight").value(hasItem(DEFAULT_VEHICLE_INSPECTION_LOW_LIGHT.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionTaillight").value(hasItem(DEFAULT_VEHICLE_INSPECTION_TAILLIGHT.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionIndicator").value(hasItem(DEFAULT_VEHICLE_INSPECTION_INDICATOR.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionBeacons").value(hasItem(DEFAULT_VEHICLE_INSPECTION_BEACONS.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionBreakLight").value(hasItem(DEFAULT_VEHICLE_INSPECTION_BREAK_LIGHT.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionPlateLight").value(hasItem(DEFAULT_VEHICLE_INSPECTION_PLATE_LIGHT.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionSpeedometer").value(hasItem(DEFAULT_VEHICLE_INSPECTION_SPEEDOMETER.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionTemperature").value(hasItem(DEFAULT_VEHICLE_INSPECTION_TEMPERATURE.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionTires").value(hasItem(DEFAULT_VEHICLE_INSPECTION_TIRES.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionStep").value(hasItem(DEFAULT_VEHICLE_INSPECTION_STEP.toString())))
            .andExpect(
                jsonPath("$.[*].vehicleInspectionFireExtinguisher").value(hasItem(DEFAULT_VEHICLE_INSPECTION_FIRE_EXTINGUISHER.toString()))
            )
            .andExpect(jsonPath("$.[*].vehicleInspectionSeatBelts").value(hasItem(DEFAULT_VEHICLE_INSPECTION_SEAT_BELTS.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionMonkey").value(hasItem(DEFAULT_VEHICLE_INSPECTION_MONKEY.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionTireIron").value(hasItem(DEFAULT_VEHICLE_INSPECTION_TIRE_IRON.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionRadiatorCap").value(hasItem(DEFAULT_VEHICLE_INSPECTION_RADIATOR_CAP.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionTriangle").value(hasItem(DEFAULT_VEHICLE_INSPECTION_TRIANGLE.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionServiceBrake").value(hasItem(DEFAULT_VEHICLE_INSPECTION_SERVICE_BRAKE.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionParkingBrake").value(hasItem(DEFAULT_VEHICLE_INSPECTION_PARKING_BRAKE.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionOilLeaks").value(hasItem(DEFAULT_VEHICLE_INSPECTION_OIL_LEAKS.toString())))
            .andExpect(
                jsonPath("$.[*].vehicleInspectionGlassActuator").value(hasItem(DEFAULT_VEHICLE_INSPECTION_GLASS_ACTUATOR.toString()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleInspectionVehicleCleaning").value(hasItem(DEFAULT_VEHICLE_INSPECTION_VEHICLE_CLEANING.toString()))
            )
            .andExpect(jsonPath("$.[*].vehicleInspectionSeatState").value(hasItem(DEFAULT_VEHICLE_INSPECTION_SEAT_STATE.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionExhausts").value(hasItem(DEFAULT_VEHICLE_INSPECTION_EXHAUSTS.toString())))
            .andExpect(jsonPath("$.[*].vehicleInspectionsObs").value(hasItem(DEFAULT_VEHICLE_INSPECTIONS_OBS)))
            .andExpect(jsonPath("$.[*].vehicleInspectionsSignedUrl").value(hasItem(DEFAULT_VEHICLE_INSPECTIONS_SIGNED_URL)));

        // Check, that the count call also returns 1
        restVehicleInspectionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVehicleInspectionsShouldNotBeFound(String filter) throws Exception {
        restVehicleInspectionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVehicleInspectionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVehicleInspections() throws Exception {
        // Get the vehicleInspections
        restVehicleInspectionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVehicleInspections() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        int databaseSizeBeforeUpdate = vehicleInspectionsRepository.findAll().size();

        // Update the vehicleInspections
        VehicleInspections updatedVehicleInspections = vehicleInspectionsRepository.findById(vehicleInspections.getId()).get();
        // Disconnect from session so that the updates on updatedVehicleInspections are not directly saved in db
        em.detach(updatedVehicleInspections);
        updatedVehicleInspections
            .vehicleInspectionDate(UPDATED_VEHICLE_INSPECTION_DATE)
            .vehicleInspectionStatus(UPDATED_VEHICLE_INSPECTION_STATUS)
            .vehicleInspectionModel(UPDATED_VEHICLE_INSPECTION_MODEL)
            .vehicleInspectionLicensePlate(UPDATED_VEHICLE_INSPECTION_LICENSE_PLATE)
            .vehicleInspectionKm(UPDATED_VEHICLE_INSPECTION_KM)
            .vehicleInspectionLicenseYear(UPDATED_VEHICLE_INSPECTION_LICENSE_YEAR)
            .vehicleInspectionHasManual(UPDATED_VEHICLE_INSPECTION_HAS_MANUAL)
            .vehicleInspectionHasExtraKey(UPDATED_VEHICLE_INSPECTION_HAS_EXTRA_KEY)
            .vehicleInspectionHasStickers(UPDATED_VEHICLE_INSPECTION_HAS_STICKERS)
            .vehicleInspectionGas(UPDATED_VEHICLE_INSPECTION_GAS)
            .vehicleInspectionRearView(UPDATED_VEHICLE_INSPECTION_REAR_VIEW)
            .vehicleInspectionHorn(UPDATED_VEHICLE_INSPECTION_HORN)
            .vehicleInspectionWindshieldWiper(UPDATED_VEHICLE_INSPECTION_WINDSHIELD_WIPER)
            .vehicleInspectionSquirt(UPDATED_VEHICLE_INSPECTION_SQUIRT)
            .vehicleInspectionInternalLight(UPDATED_VEHICLE_INSPECTION_INTERNAL_LIGHT)
            .vehicleInspectionPanelLight(UPDATED_VEHICLE_INSPECTION_PANEL_LIGHT)
            .vehicleInspectionHighLight(UPDATED_VEHICLE_INSPECTION_HIGH_LIGHT)
            .vehicleInspectionLowLight(UPDATED_VEHICLE_INSPECTION_LOW_LIGHT)
            .vehicleInspectionTaillight(UPDATED_VEHICLE_INSPECTION_TAILLIGHT)
            .vehicleInspectionIndicator(UPDATED_VEHICLE_INSPECTION_INDICATOR)
            .vehicleInspectionBeacons(UPDATED_VEHICLE_INSPECTION_BEACONS)
            .vehicleInspectionBreakLight(UPDATED_VEHICLE_INSPECTION_BREAK_LIGHT)
            .vehicleInspectionPlateLight(UPDATED_VEHICLE_INSPECTION_PLATE_LIGHT)
            .vehicleInspectionSpeedometer(UPDATED_VEHICLE_INSPECTION_SPEEDOMETER)
            .vehicleInspectionTemperature(UPDATED_VEHICLE_INSPECTION_TEMPERATURE)
            .vehicleInspectionTires(UPDATED_VEHICLE_INSPECTION_TIRES)
            .vehicleInspectionStep(UPDATED_VEHICLE_INSPECTION_STEP)
            .vehicleInspectionFireExtinguisher(UPDATED_VEHICLE_INSPECTION_FIRE_EXTINGUISHER)
            .vehicleInspectionSeatBelts(UPDATED_VEHICLE_INSPECTION_SEAT_BELTS)
            .vehicleInspectionMonkey(UPDATED_VEHICLE_INSPECTION_MONKEY)
            .vehicleInspectionTireIron(UPDATED_VEHICLE_INSPECTION_TIRE_IRON)
            .vehicleInspectionRadiatorCap(UPDATED_VEHICLE_INSPECTION_RADIATOR_CAP)
            .vehicleInspectionTriangle(UPDATED_VEHICLE_INSPECTION_TRIANGLE)
            .vehicleInspectionServiceBrake(UPDATED_VEHICLE_INSPECTION_SERVICE_BRAKE)
            .vehicleInspectionParkingBrake(UPDATED_VEHICLE_INSPECTION_PARKING_BRAKE)
            .vehicleInspectionOilLeaks(UPDATED_VEHICLE_INSPECTION_OIL_LEAKS)
            .vehicleInspectionGlassActuator(UPDATED_VEHICLE_INSPECTION_GLASS_ACTUATOR)
            .vehicleInspectionVehicleCleaning(UPDATED_VEHICLE_INSPECTION_VEHICLE_CLEANING)
            .vehicleInspectionSeatState(UPDATED_VEHICLE_INSPECTION_SEAT_STATE)
            .vehicleInspectionExhausts(UPDATED_VEHICLE_INSPECTION_EXHAUSTS)
            .vehicleInspectionsObs(UPDATED_VEHICLE_INSPECTIONS_OBS)
            .vehicleInspectionsSignedUrl(UPDATED_VEHICLE_INSPECTIONS_SIGNED_URL);
        VehicleInspectionsDTO vehicleInspectionsDTO = vehicleInspectionsMapper.toDto(updatedVehicleInspections);

        restVehicleInspectionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleInspectionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the VehicleInspections in the database
        List<VehicleInspections> vehicleInspectionsList = vehicleInspectionsRepository.findAll();
        assertThat(vehicleInspectionsList).hasSize(databaseSizeBeforeUpdate);
        VehicleInspections testVehicleInspections = vehicleInspectionsList.get(vehicleInspectionsList.size() - 1);
        assertThat(testVehicleInspections.getVehicleInspectionDate()).isEqualTo(UPDATED_VEHICLE_INSPECTION_DATE);
        assertThat(testVehicleInspections.getVehicleInspectionStatus()).isEqualTo(UPDATED_VEHICLE_INSPECTION_STATUS);
        assertThat(testVehicleInspections.getVehicleInspectionModel()).isEqualTo(UPDATED_VEHICLE_INSPECTION_MODEL);
        assertThat(testVehicleInspections.getVehicleInspectionLicensePlate()).isEqualTo(UPDATED_VEHICLE_INSPECTION_LICENSE_PLATE);
        assertThat(testVehicleInspections.getVehicleInspectionKm()).isEqualTo(UPDATED_VEHICLE_INSPECTION_KM);
        assertThat(testVehicleInspections.getVehicleInspectionLicenseYear()).isEqualTo(UPDATED_VEHICLE_INSPECTION_LICENSE_YEAR);
        assertThat(testVehicleInspections.getVehicleInspectionHasManual()).isEqualTo(UPDATED_VEHICLE_INSPECTION_HAS_MANUAL);
        assertThat(testVehicleInspections.getVehicleInspectionHasExtraKey()).isEqualTo(UPDATED_VEHICLE_INSPECTION_HAS_EXTRA_KEY);
        assertThat(testVehicleInspections.getVehicleInspectionHasStickers()).isEqualTo(UPDATED_VEHICLE_INSPECTION_HAS_STICKERS);
        assertThat(testVehicleInspections.getVehicleInspectionGas()).isEqualTo(UPDATED_VEHICLE_INSPECTION_GAS);
        assertThat(testVehicleInspections.getVehicleInspectionRearView()).isEqualTo(UPDATED_VEHICLE_INSPECTION_REAR_VIEW);
        assertThat(testVehicleInspections.getVehicleInspectionHorn()).isEqualTo(UPDATED_VEHICLE_INSPECTION_HORN);
        assertThat(testVehicleInspections.getVehicleInspectionWindshieldWiper()).isEqualTo(UPDATED_VEHICLE_INSPECTION_WINDSHIELD_WIPER);
        assertThat(testVehicleInspections.getVehicleInspectionSquirt()).isEqualTo(UPDATED_VEHICLE_INSPECTION_SQUIRT);
        assertThat(testVehicleInspections.getVehicleInspectionInternalLight()).isEqualTo(UPDATED_VEHICLE_INSPECTION_INTERNAL_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionPanelLight()).isEqualTo(UPDATED_VEHICLE_INSPECTION_PANEL_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionHighLight()).isEqualTo(UPDATED_VEHICLE_INSPECTION_HIGH_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionLowLight()).isEqualTo(UPDATED_VEHICLE_INSPECTION_LOW_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionTaillight()).isEqualTo(UPDATED_VEHICLE_INSPECTION_TAILLIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionIndicator()).isEqualTo(UPDATED_VEHICLE_INSPECTION_INDICATOR);
        assertThat(testVehicleInspections.getVehicleInspectionBeacons()).isEqualTo(UPDATED_VEHICLE_INSPECTION_BEACONS);
        assertThat(testVehicleInspections.getVehicleInspectionBreakLight()).isEqualTo(UPDATED_VEHICLE_INSPECTION_BREAK_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionPlateLight()).isEqualTo(UPDATED_VEHICLE_INSPECTION_PLATE_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionSpeedometer()).isEqualTo(UPDATED_VEHICLE_INSPECTION_SPEEDOMETER);
        assertThat(testVehicleInspections.getVehicleInspectionTemperature()).isEqualTo(UPDATED_VEHICLE_INSPECTION_TEMPERATURE);
        assertThat(testVehicleInspections.getVehicleInspectionTires()).isEqualTo(UPDATED_VEHICLE_INSPECTION_TIRES);
        assertThat(testVehicleInspections.getVehicleInspectionStep()).isEqualTo(UPDATED_VEHICLE_INSPECTION_STEP);
        assertThat(testVehicleInspections.getVehicleInspectionFireExtinguisher()).isEqualTo(UPDATED_VEHICLE_INSPECTION_FIRE_EXTINGUISHER);
        assertThat(testVehicleInspections.getVehicleInspectionSeatBelts()).isEqualTo(UPDATED_VEHICLE_INSPECTION_SEAT_BELTS);
        assertThat(testVehicleInspections.getVehicleInspectionMonkey()).isEqualTo(UPDATED_VEHICLE_INSPECTION_MONKEY);
        assertThat(testVehicleInspections.getVehicleInspectionTireIron()).isEqualTo(UPDATED_VEHICLE_INSPECTION_TIRE_IRON);
        assertThat(testVehicleInspections.getVehicleInspectionRadiatorCap()).isEqualTo(UPDATED_VEHICLE_INSPECTION_RADIATOR_CAP);
        assertThat(testVehicleInspections.getVehicleInspectionTriangle()).isEqualTo(UPDATED_VEHICLE_INSPECTION_TRIANGLE);
        assertThat(testVehicleInspections.getVehicleInspectionServiceBrake()).isEqualTo(UPDATED_VEHICLE_INSPECTION_SERVICE_BRAKE);
        assertThat(testVehicleInspections.getVehicleInspectionParkingBrake()).isEqualTo(UPDATED_VEHICLE_INSPECTION_PARKING_BRAKE);
        assertThat(testVehicleInspections.getVehicleInspectionOilLeaks()).isEqualTo(UPDATED_VEHICLE_INSPECTION_OIL_LEAKS);
        assertThat(testVehicleInspections.getVehicleInspectionGlassActuator()).isEqualTo(UPDATED_VEHICLE_INSPECTION_GLASS_ACTUATOR);
        assertThat(testVehicleInspections.getVehicleInspectionVehicleCleaning()).isEqualTo(UPDATED_VEHICLE_INSPECTION_VEHICLE_CLEANING);
        assertThat(testVehicleInspections.getVehicleInspectionSeatState()).isEqualTo(UPDATED_VEHICLE_INSPECTION_SEAT_STATE);
        assertThat(testVehicleInspections.getVehicleInspectionExhausts()).isEqualTo(UPDATED_VEHICLE_INSPECTION_EXHAUSTS);
        assertThat(testVehicleInspections.getVehicleInspectionsObs()).isEqualTo(UPDATED_VEHICLE_INSPECTIONS_OBS);
        assertThat(testVehicleInspections.getVehicleInspectionsSignedUrl()).isEqualTo(UPDATED_VEHICLE_INSPECTIONS_SIGNED_URL);
    }

    @Test
    @Transactional
    void putNonExistingVehicleInspections() throws Exception {
        int databaseSizeBeforeUpdate = vehicleInspectionsRepository.findAll().size();
        vehicleInspections.setId(count.incrementAndGet());

        // Create the VehicleInspections
        VehicleInspectionsDTO vehicleInspectionsDTO = vehicleInspectionsMapper.toDto(vehicleInspections);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleInspectionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleInspectionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleInspections in the database
        List<VehicleInspections> vehicleInspectionsList = vehicleInspectionsRepository.findAll();
        assertThat(vehicleInspectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVehicleInspections() throws Exception {
        int databaseSizeBeforeUpdate = vehicleInspectionsRepository.findAll().size();
        vehicleInspections.setId(count.incrementAndGet());

        // Create the VehicleInspections
        VehicleInspectionsDTO vehicleInspectionsDTO = vehicleInspectionsMapper.toDto(vehicleInspections);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleInspectionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleInspections in the database
        List<VehicleInspections> vehicleInspectionsList = vehicleInspectionsRepository.findAll();
        assertThat(vehicleInspectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVehicleInspections() throws Exception {
        int databaseSizeBeforeUpdate = vehicleInspectionsRepository.findAll().size();
        vehicleInspections.setId(count.incrementAndGet());

        // Create the VehicleInspections
        VehicleInspectionsDTO vehicleInspectionsDTO = vehicleInspectionsMapper.toDto(vehicleInspections);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleInspectionsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleInspections in the database
        List<VehicleInspections> vehicleInspectionsList = vehicleInspectionsRepository.findAll();
        assertThat(vehicleInspectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVehicleInspectionsWithPatch() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        int databaseSizeBeforeUpdate = vehicleInspectionsRepository.findAll().size();

        // Update the vehicleInspections using partial update
        VehicleInspections partialUpdatedVehicleInspections = new VehicleInspections();
        partialUpdatedVehicleInspections.setId(vehicleInspections.getId());

        partialUpdatedVehicleInspections
            .vehicleInspectionKm(UPDATED_VEHICLE_INSPECTION_KM)
            .vehicleInspectionHasStickers(UPDATED_VEHICLE_INSPECTION_HAS_STICKERS)
            .vehicleInspectionRearView(UPDATED_VEHICLE_INSPECTION_REAR_VIEW)
            .vehicleInspectionHorn(UPDATED_VEHICLE_INSPECTION_HORN)
            .vehicleInspectionWindshieldWiper(UPDATED_VEHICLE_INSPECTION_WINDSHIELD_WIPER)
            .vehicleInspectionInternalLight(UPDATED_VEHICLE_INSPECTION_INTERNAL_LIGHT)
            .vehicleInspectionPanelLight(UPDATED_VEHICLE_INSPECTION_PANEL_LIGHT)
            .vehicleInspectionLowLight(UPDATED_VEHICLE_INSPECTION_LOW_LIGHT)
            .vehicleInspectionTaillight(UPDATED_VEHICLE_INSPECTION_TAILLIGHT)
            .vehicleInspectionBeacons(UPDATED_VEHICLE_INSPECTION_BEACONS)
            .vehicleInspectionBreakLight(UPDATED_VEHICLE_INSPECTION_BREAK_LIGHT)
            .vehicleInspectionPlateLight(UPDATED_VEHICLE_INSPECTION_PLATE_LIGHT)
            .vehicleInspectionTemperature(UPDATED_VEHICLE_INSPECTION_TEMPERATURE)
            .vehicleInspectionTires(UPDATED_VEHICLE_INSPECTION_TIRES)
            .vehicleInspectionStep(UPDATED_VEHICLE_INSPECTION_STEP)
            .vehicleInspectionFireExtinguisher(UPDATED_VEHICLE_INSPECTION_FIRE_EXTINGUISHER)
            .vehicleInspectionSeatBelts(UPDATED_VEHICLE_INSPECTION_SEAT_BELTS)
            .vehicleInspectionMonkey(UPDATED_VEHICLE_INSPECTION_MONKEY)
            .vehicleInspectionRadiatorCap(UPDATED_VEHICLE_INSPECTION_RADIATOR_CAP)
            .vehicleInspectionTriangle(UPDATED_VEHICLE_INSPECTION_TRIANGLE)
            .vehicleInspectionParkingBrake(UPDATED_VEHICLE_INSPECTION_PARKING_BRAKE)
            .vehicleInspectionSeatState(UPDATED_VEHICLE_INSPECTION_SEAT_STATE)
            .vehicleInspectionsObs(UPDATED_VEHICLE_INSPECTIONS_OBS)
            .vehicleInspectionsSignedUrl(UPDATED_VEHICLE_INSPECTIONS_SIGNED_URL);

        restVehicleInspectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleInspections.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleInspections))
            )
            .andExpect(status().isOk());

        // Validate the VehicleInspections in the database
        List<VehicleInspections> vehicleInspectionsList = vehicleInspectionsRepository.findAll();
        assertThat(vehicleInspectionsList).hasSize(databaseSizeBeforeUpdate);
        VehicleInspections testVehicleInspections = vehicleInspectionsList.get(vehicleInspectionsList.size() - 1);
        assertThat(testVehicleInspections.getVehicleInspectionDate()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_DATE);
        assertThat(testVehicleInspections.getVehicleInspectionStatus()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_STATUS);
        assertThat(testVehicleInspections.getVehicleInspectionModel()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_MODEL);
        assertThat(testVehicleInspections.getVehicleInspectionLicensePlate()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_LICENSE_PLATE);
        assertThat(testVehicleInspections.getVehicleInspectionKm()).isEqualTo(UPDATED_VEHICLE_INSPECTION_KM);
        assertThat(testVehicleInspections.getVehicleInspectionLicenseYear()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_LICENSE_YEAR);
        assertThat(testVehicleInspections.getVehicleInspectionHasManual()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_HAS_MANUAL);
        assertThat(testVehicleInspections.getVehicleInspectionHasExtraKey()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_HAS_EXTRA_KEY);
        assertThat(testVehicleInspections.getVehicleInspectionHasStickers()).isEqualTo(UPDATED_VEHICLE_INSPECTION_HAS_STICKERS);
        assertThat(testVehicleInspections.getVehicleInspectionGas()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_GAS);
        assertThat(testVehicleInspections.getVehicleInspectionRearView()).isEqualTo(UPDATED_VEHICLE_INSPECTION_REAR_VIEW);
        assertThat(testVehicleInspections.getVehicleInspectionHorn()).isEqualTo(UPDATED_VEHICLE_INSPECTION_HORN);
        assertThat(testVehicleInspections.getVehicleInspectionWindshieldWiper()).isEqualTo(UPDATED_VEHICLE_INSPECTION_WINDSHIELD_WIPER);
        assertThat(testVehicleInspections.getVehicleInspectionSquirt()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_SQUIRT);
        assertThat(testVehicleInspections.getVehicleInspectionInternalLight()).isEqualTo(UPDATED_VEHICLE_INSPECTION_INTERNAL_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionPanelLight()).isEqualTo(UPDATED_VEHICLE_INSPECTION_PANEL_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionHighLight()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_HIGH_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionLowLight()).isEqualTo(UPDATED_VEHICLE_INSPECTION_LOW_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionTaillight()).isEqualTo(UPDATED_VEHICLE_INSPECTION_TAILLIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionIndicator()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_INDICATOR);
        assertThat(testVehicleInspections.getVehicleInspectionBeacons()).isEqualTo(UPDATED_VEHICLE_INSPECTION_BEACONS);
        assertThat(testVehicleInspections.getVehicleInspectionBreakLight()).isEqualTo(UPDATED_VEHICLE_INSPECTION_BREAK_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionPlateLight()).isEqualTo(UPDATED_VEHICLE_INSPECTION_PLATE_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionSpeedometer()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_SPEEDOMETER);
        assertThat(testVehicleInspections.getVehicleInspectionTemperature()).isEqualTo(UPDATED_VEHICLE_INSPECTION_TEMPERATURE);
        assertThat(testVehicleInspections.getVehicleInspectionTires()).isEqualTo(UPDATED_VEHICLE_INSPECTION_TIRES);
        assertThat(testVehicleInspections.getVehicleInspectionStep()).isEqualTo(UPDATED_VEHICLE_INSPECTION_STEP);
        assertThat(testVehicleInspections.getVehicleInspectionFireExtinguisher()).isEqualTo(UPDATED_VEHICLE_INSPECTION_FIRE_EXTINGUISHER);
        assertThat(testVehicleInspections.getVehicleInspectionSeatBelts()).isEqualTo(UPDATED_VEHICLE_INSPECTION_SEAT_BELTS);
        assertThat(testVehicleInspections.getVehicleInspectionMonkey()).isEqualTo(UPDATED_VEHICLE_INSPECTION_MONKEY);
        assertThat(testVehicleInspections.getVehicleInspectionTireIron()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_TIRE_IRON);
        assertThat(testVehicleInspections.getVehicleInspectionRadiatorCap()).isEqualTo(UPDATED_VEHICLE_INSPECTION_RADIATOR_CAP);
        assertThat(testVehicleInspections.getVehicleInspectionTriangle()).isEqualTo(UPDATED_VEHICLE_INSPECTION_TRIANGLE);
        assertThat(testVehicleInspections.getVehicleInspectionServiceBrake()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_SERVICE_BRAKE);
        assertThat(testVehicleInspections.getVehicleInspectionParkingBrake()).isEqualTo(UPDATED_VEHICLE_INSPECTION_PARKING_BRAKE);
        assertThat(testVehicleInspections.getVehicleInspectionOilLeaks()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_OIL_LEAKS);
        assertThat(testVehicleInspections.getVehicleInspectionGlassActuator()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_GLASS_ACTUATOR);
        assertThat(testVehicleInspections.getVehicleInspectionVehicleCleaning()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_VEHICLE_CLEANING);
        assertThat(testVehicleInspections.getVehicleInspectionSeatState()).isEqualTo(UPDATED_VEHICLE_INSPECTION_SEAT_STATE);
        assertThat(testVehicleInspections.getVehicleInspectionExhausts()).isEqualTo(DEFAULT_VEHICLE_INSPECTION_EXHAUSTS);
        assertThat(testVehicleInspections.getVehicleInspectionsObs()).isEqualTo(UPDATED_VEHICLE_INSPECTIONS_OBS);
        assertThat(testVehicleInspections.getVehicleInspectionsSignedUrl()).isEqualTo(UPDATED_VEHICLE_INSPECTIONS_SIGNED_URL);
    }

    @Test
    @Transactional
    void fullUpdateVehicleInspectionsWithPatch() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        int databaseSizeBeforeUpdate = vehicleInspectionsRepository.findAll().size();

        // Update the vehicleInspections using partial update
        VehicleInspections partialUpdatedVehicleInspections = new VehicleInspections();
        partialUpdatedVehicleInspections.setId(vehicleInspections.getId());

        partialUpdatedVehicleInspections
            .vehicleInspectionDate(UPDATED_VEHICLE_INSPECTION_DATE)
            .vehicleInspectionStatus(UPDATED_VEHICLE_INSPECTION_STATUS)
            .vehicleInspectionModel(UPDATED_VEHICLE_INSPECTION_MODEL)
            .vehicleInspectionLicensePlate(UPDATED_VEHICLE_INSPECTION_LICENSE_PLATE)
            .vehicleInspectionKm(UPDATED_VEHICLE_INSPECTION_KM)
            .vehicleInspectionLicenseYear(UPDATED_VEHICLE_INSPECTION_LICENSE_YEAR)
            .vehicleInspectionHasManual(UPDATED_VEHICLE_INSPECTION_HAS_MANUAL)
            .vehicleInspectionHasExtraKey(UPDATED_VEHICLE_INSPECTION_HAS_EXTRA_KEY)
            .vehicleInspectionHasStickers(UPDATED_VEHICLE_INSPECTION_HAS_STICKERS)
            .vehicleInspectionGas(UPDATED_VEHICLE_INSPECTION_GAS)
            .vehicleInspectionRearView(UPDATED_VEHICLE_INSPECTION_REAR_VIEW)
            .vehicleInspectionHorn(UPDATED_VEHICLE_INSPECTION_HORN)
            .vehicleInspectionWindshieldWiper(UPDATED_VEHICLE_INSPECTION_WINDSHIELD_WIPER)
            .vehicleInspectionSquirt(UPDATED_VEHICLE_INSPECTION_SQUIRT)
            .vehicleInspectionInternalLight(UPDATED_VEHICLE_INSPECTION_INTERNAL_LIGHT)
            .vehicleInspectionPanelLight(UPDATED_VEHICLE_INSPECTION_PANEL_LIGHT)
            .vehicleInspectionHighLight(UPDATED_VEHICLE_INSPECTION_HIGH_LIGHT)
            .vehicleInspectionLowLight(UPDATED_VEHICLE_INSPECTION_LOW_LIGHT)
            .vehicleInspectionTaillight(UPDATED_VEHICLE_INSPECTION_TAILLIGHT)
            .vehicleInspectionIndicator(UPDATED_VEHICLE_INSPECTION_INDICATOR)
            .vehicleInspectionBeacons(UPDATED_VEHICLE_INSPECTION_BEACONS)
            .vehicleInspectionBreakLight(UPDATED_VEHICLE_INSPECTION_BREAK_LIGHT)
            .vehicleInspectionPlateLight(UPDATED_VEHICLE_INSPECTION_PLATE_LIGHT)
            .vehicleInspectionSpeedometer(UPDATED_VEHICLE_INSPECTION_SPEEDOMETER)
            .vehicleInspectionTemperature(UPDATED_VEHICLE_INSPECTION_TEMPERATURE)
            .vehicleInspectionTires(UPDATED_VEHICLE_INSPECTION_TIRES)
            .vehicleInspectionStep(UPDATED_VEHICLE_INSPECTION_STEP)
            .vehicleInspectionFireExtinguisher(UPDATED_VEHICLE_INSPECTION_FIRE_EXTINGUISHER)
            .vehicleInspectionSeatBelts(UPDATED_VEHICLE_INSPECTION_SEAT_BELTS)
            .vehicleInspectionMonkey(UPDATED_VEHICLE_INSPECTION_MONKEY)
            .vehicleInspectionTireIron(UPDATED_VEHICLE_INSPECTION_TIRE_IRON)
            .vehicleInspectionRadiatorCap(UPDATED_VEHICLE_INSPECTION_RADIATOR_CAP)
            .vehicleInspectionTriangle(UPDATED_VEHICLE_INSPECTION_TRIANGLE)
            .vehicleInspectionServiceBrake(UPDATED_VEHICLE_INSPECTION_SERVICE_BRAKE)
            .vehicleInspectionParkingBrake(UPDATED_VEHICLE_INSPECTION_PARKING_BRAKE)
            .vehicleInspectionOilLeaks(UPDATED_VEHICLE_INSPECTION_OIL_LEAKS)
            .vehicleInspectionGlassActuator(UPDATED_VEHICLE_INSPECTION_GLASS_ACTUATOR)
            .vehicleInspectionVehicleCleaning(UPDATED_VEHICLE_INSPECTION_VEHICLE_CLEANING)
            .vehicleInspectionSeatState(UPDATED_VEHICLE_INSPECTION_SEAT_STATE)
            .vehicleInspectionExhausts(UPDATED_VEHICLE_INSPECTION_EXHAUSTS)
            .vehicleInspectionsObs(UPDATED_VEHICLE_INSPECTIONS_OBS)
            .vehicleInspectionsSignedUrl(UPDATED_VEHICLE_INSPECTIONS_SIGNED_URL);

        restVehicleInspectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleInspections.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleInspections))
            )
            .andExpect(status().isOk());

        // Validate the VehicleInspections in the database
        List<VehicleInspections> vehicleInspectionsList = vehicleInspectionsRepository.findAll();
        assertThat(vehicleInspectionsList).hasSize(databaseSizeBeforeUpdate);
        VehicleInspections testVehicleInspections = vehicleInspectionsList.get(vehicleInspectionsList.size() - 1);
        assertThat(testVehicleInspections.getVehicleInspectionDate()).isEqualTo(UPDATED_VEHICLE_INSPECTION_DATE);
        assertThat(testVehicleInspections.getVehicleInspectionStatus()).isEqualTo(UPDATED_VEHICLE_INSPECTION_STATUS);
        assertThat(testVehicleInspections.getVehicleInspectionModel()).isEqualTo(UPDATED_VEHICLE_INSPECTION_MODEL);
        assertThat(testVehicleInspections.getVehicleInspectionLicensePlate()).isEqualTo(UPDATED_VEHICLE_INSPECTION_LICENSE_PLATE);
        assertThat(testVehicleInspections.getVehicleInspectionKm()).isEqualTo(UPDATED_VEHICLE_INSPECTION_KM);
        assertThat(testVehicleInspections.getVehicleInspectionLicenseYear()).isEqualTo(UPDATED_VEHICLE_INSPECTION_LICENSE_YEAR);
        assertThat(testVehicleInspections.getVehicleInspectionHasManual()).isEqualTo(UPDATED_VEHICLE_INSPECTION_HAS_MANUAL);
        assertThat(testVehicleInspections.getVehicleInspectionHasExtraKey()).isEqualTo(UPDATED_VEHICLE_INSPECTION_HAS_EXTRA_KEY);
        assertThat(testVehicleInspections.getVehicleInspectionHasStickers()).isEqualTo(UPDATED_VEHICLE_INSPECTION_HAS_STICKERS);
        assertThat(testVehicleInspections.getVehicleInspectionGas()).isEqualTo(UPDATED_VEHICLE_INSPECTION_GAS);
        assertThat(testVehicleInspections.getVehicleInspectionRearView()).isEqualTo(UPDATED_VEHICLE_INSPECTION_REAR_VIEW);
        assertThat(testVehicleInspections.getVehicleInspectionHorn()).isEqualTo(UPDATED_VEHICLE_INSPECTION_HORN);
        assertThat(testVehicleInspections.getVehicleInspectionWindshieldWiper()).isEqualTo(UPDATED_VEHICLE_INSPECTION_WINDSHIELD_WIPER);
        assertThat(testVehicleInspections.getVehicleInspectionSquirt()).isEqualTo(UPDATED_VEHICLE_INSPECTION_SQUIRT);
        assertThat(testVehicleInspections.getVehicleInspectionInternalLight()).isEqualTo(UPDATED_VEHICLE_INSPECTION_INTERNAL_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionPanelLight()).isEqualTo(UPDATED_VEHICLE_INSPECTION_PANEL_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionHighLight()).isEqualTo(UPDATED_VEHICLE_INSPECTION_HIGH_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionLowLight()).isEqualTo(UPDATED_VEHICLE_INSPECTION_LOW_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionTaillight()).isEqualTo(UPDATED_VEHICLE_INSPECTION_TAILLIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionIndicator()).isEqualTo(UPDATED_VEHICLE_INSPECTION_INDICATOR);
        assertThat(testVehicleInspections.getVehicleInspectionBeacons()).isEqualTo(UPDATED_VEHICLE_INSPECTION_BEACONS);
        assertThat(testVehicleInspections.getVehicleInspectionBreakLight()).isEqualTo(UPDATED_VEHICLE_INSPECTION_BREAK_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionPlateLight()).isEqualTo(UPDATED_VEHICLE_INSPECTION_PLATE_LIGHT);
        assertThat(testVehicleInspections.getVehicleInspectionSpeedometer()).isEqualTo(UPDATED_VEHICLE_INSPECTION_SPEEDOMETER);
        assertThat(testVehicleInspections.getVehicleInspectionTemperature()).isEqualTo(UPDATED_VEHICLE_INSPECTION_TEMPERATURE);
        assertThat(testVehicleInspections.getVehicleInspectionTires()).isEqualTo(UPDATED_VEHICLE_INSPECTION_TIRES);
        assertThat(testVehicleInspections.getVehicleInspectionStep()).isEqualTo(UPDATED_VEHICLE_INSPECTION_STEP);
        assertThat(testVehicleInspections.getVehicleInspectionFireExtinguisher()).isEqualTo(UPDATED_VEHICLE_INSPECTION_FIRE_EXTINGUISHER);
        assertThat(testVehicleInspections.getVehicleInspectionSeatBelts()).isEqualTo(UPDATED_VEHICLE_INSPECTION_SEAT_BELTS);
        assertThat(testVehicleInspections.getVehicleInspectionMonkey()).isEqualTo(UPDATED_VEHICLE_INSPECTION_MONKEY);
        assertThat(testVehicleInspections.getVehicleInspectionTireIron()).isEqualTo(UPDATED_VEHICLE_INSPECTION_TIRE_IRON);
        assertThat(testVehicleInspections.getVehicleInspectionRadiatorCap()).isEqualTo(UPDATED_VEHICLE_INSPECTION_RADIATOR_CAP);
        assertThat(testVehicleInspections.getVehicleInspectionTriangle()).isEqualTo(UPDATED_VEHICLE_INSPECTION_TRIANGLE);
        assertThat(testVehicleInspections.getVehicleInspectionServiceBrake()).isEqualTo(UPDATED_VEHICLE_INSPECTION_SERVICE_BRAKE);
        assertThat(testVehicleInspections.getVehicleInspectionParkingBrake()).isEqualTo(UPDATED_VEHICLE_INSPECTION_PARKING_BRAKE);
        assertThat(testVehicleInspections.getVehicleInspectionOilLeaks()).isEqualTo(UPDATED_VEHICLE_INSPECTION_OIL_LEAKS);
        assertThat(testVehicleInspections.getVehicleInspectionGlassActuator()).isEqualTo(UPDATED_VEHICLE_INSPECTION_GLASS_ACTUATOR);
        assertThat(testVehicleInspections.getVehicleInspectionVehicleCleaning()).isEqualTo(UPDATED_VEHICLE_INSPECTION_VEHICLE_CLEANING);
        assertThat(testVehicleInspections.getVehicleInspectionSeatState()).isEqualTo(UPDATED_VEHICLE_INSPECTION_SEAT_STATE);
        assertThat(testVehicleInspections.getVehicleInspectionExhausts()).isEqualTo(UPDATED_VEHICLE_INSPECTION_EXHAUSTS);
        assertThat(testVehicleInspections.getVehicleInspectionsObs()).isEqualTo(UPDATED_VEHICLE_INSPECTIONS_OBS);
        assertThat(testVehicleInspections.getVehicleInspectionsSignedUrl()).isEqualTo(UPDATED_VEHICLE_INSPECTIONS_SIGNED_URL);
    }

    @Test
    @Transactional
    void patchNonExistingVehicleInspections() throws Exception {
        int databaseSizeBeforeUpdate = vehicleInspectionsRepository.findAll().size();
        vehicleInspections.setId(count.incrementAndGet());

        // Create the VehicleInspections
        VehicleInspectionsDTO vehicleInspectionsDTO = vehicleInspectionsMapper.toDto(vehicleInspections);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleInspectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vehicleInspectionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleInspections in the database
        List<VehicleInspections> vehicleInspectionsList = vehicleInspectionsRepository.findAll();
        assertThat(vehicleInspectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVehicleInspections() throws Exception {
        int databaseSizeBeforeUpdate = vehicleInspectionsRepository.findAll().size();
        vehicleInspections.setId(count.incrementAndGet());

        // Create the VehicleInspections
        VehicleInspectionsDTO vehicleInspectionsDTO = vehicleInspectionsMapper.toDto(vehicleInspections);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleInspectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleInspections in the database
        List<VehicleInspections> vehicleInspectionsList = vehicleInspectionsRepository.findAll();
        assertThat(vehicleInspectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVehicleInspections() throws Exception {
        int databaseSizeBeforeUpdate = vehicleInspectionsRepository.findAll().size();
        vehicleInspections.setId(count.incrementAndGet());

        // Create the VehicleInspections
        VehicleInspectionsDTO vehicleInspectionsDTO = vehicleInspectionsMapper.toDto(vehicleInspections);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleInspectionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleInspections in the database
        List<VehicleInspections> vehicleInspectionsList = vehicleInspectionsRepository.findAll();
        assertThat(vehicleInspectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVehicleInspections() throws Exception {
        // Initialize the database
        vehicleInspectionsRepository.saveAndFlush(vehicleInspections);

        int databaseSizeBeforeDelete = vehicleInspectionsRepository.findAll().size();

        // Delete the vehicleInspections
        restVehicleInspectionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, vehicleInspections.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehicleInspections> vehicleInspectionsList = vehicleInspectionsRepository.findAll();
        assertThat(vehicleInspectionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
