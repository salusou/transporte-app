package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.HousingVehicleItem;
import com.genesisoft.transporte.domain.Parking;
import com.genesisoft.transporte.domain.ParkingSector;
import com.genesisoft.transporte.domain.ParkingSectorSpace;
import com.genesisoft.transporte.repository.ParkingSectorRepository;
import com.genesisoft.transporte.service.criteria.ParkingSectorCriteria;
import com.genesisoft.transporte.service.dto.ParkingSectorDTO;
import com.genesisoft.transporte.service.mapper.ParkingSectorMapper;
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
 * Integration tests for the {@link ParkingSectorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParkingSectorResourceIT {

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_SECTOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SECTOR_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PARKING_SPACE = 1;
    private static final Integer UPDATED_PARKING_SPACE = 2;
    private static final Integer SMALLER_PARKING_SPACE = 1 - 1;

    private static final Integer DEFAULT_PARKING_NUMBERS_BEGIN = 1;
    private static final Integer UPDATED_PARKING_NUMBERS_BEGIN = 2;
    private static final Integer SMALLER_PARKING_NUMBERS_BEGIN = 1 - 1;

    private static final Integer DEFAULT_PARKING_NUMBERS_FINAL = 1;
    private static final Integer UPDATED_PARKING_NUMBERS_FINAL = 2;
    private static final Integer SMALLER_PARKING_NUMBERS_FINAL = 1 - 1;

    private static final String ENTITY_API_URL = "/api/parking-sectors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ParkingSectorRepository parkingSectorRepository;

    @Autowired
    private ParkingSectorMapper parkingSectorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParkingSectorMockMvc;

    private ParkingSector parkingSector;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParkingSector createEntity(EntityManager em) {
        ParkingSector parkingSector = new ParkingSector()
            .active(DEFAULT_ACTIVE)
            .sectorName(DEFAULT_SECTOR_NAME)
            .parkingSpace(DEFAULT_PARKING_SPACE)
            .parkingNumbersBegin(DEFAULT_PARKING_NUMBERS_BEGIN)
            .parkingNumbersFinal(DEFAULT_PARKING_NUMBERS_FINAL);
        // Add required entity
        Parking parking;
        if (TestUtil.findAll(em, Parking.class).isEmpty()) {
            parking = ParkingResourceIT.createEntity(em);
            em.persist(parking);
            em.flush();
        } else {
            parking = TestUtil.findAll(em, Parking.class).get(0);
        }
        parkingSector.setParking(parking);
        return parkingSector;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParkingSector createUpdatedEntity(EntityManager em) {
        ParkingSector parkingSector = new ParkingSector()
            .active(UPDATED_ACTIVE)
            .sectorName(UPDATED_SECTOR_NAME)
            .parkingSpace(UPDATED_PARKING_SPACE)
            .parkingNumbersBegin(UPDATED_PARKING_NUMBERS_BEGIN)
            .parkingNumbersFinal(UPDATED_PARKING_NUMBERS_FINAL);
        // Add required entity
        Parking parking;
        if (TestUtil.findAll(em, Parking.class).isEmpty()) {
            parking = ParkingResourceIT.createUpdatedEntity(em);
            em.persist(parking);
            em.flush();
        } else {
            parking = TestUtil.findAll(em, Parking.class).get(0);
        }
        parkingSector.setParking(parking);
        return parkingSector;
    }

    @BeforeEach
    public void initTest() {
        parkingSector = createEntity(em);
    }

    @Test
    @Transactional
    void createParkingSector() throws Exception {
        int databaseSizeBeforeCreate = parkingSectorRepository.findAll().size();
        // Create the ParkingSector
        ParkingSectorDTO parkingSectorDTO = parkingSectorMapper.toDto(parkingSector);
        restParkingSectorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parkingSectorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ParkingSector in the database
        List<ParkingSector> parkingSectorList = parkingSectorRepository.findAll();
        assertThat(parkingSectorList).hasSize(databaseSizeBeforeCreate + 1);
        ParkingSector testParkingSector = parkingSectorList.get(parkingSectorList.size() - 1);
        assertThat(testParkingSector.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testParkingSector.getSectorName()).isEqualTo(DEFAULT_SECTOR_NAME);
        assertThat(testParkingSector.getParkingSpace()).isEqualTo(DEFAULT_PARKING_SPACE);
        assertThat(testParkingSector.getParkingNumbersBegin()).isEqualTo(DEFAULT_PARKING_NUMBERS_BEGIN);
        assertThat(testParkingSector.getParkingNumbersFinal()).isEqualTo(DEFAULT_PARKING_NUMBERS_FINAL);
    }

    @Test
    @Transactional
    void createParkingSectorWithExistingId() throws Exception {
        // Create the ParkingSector with an existing ID
        parkingSector.setId(1L);
        ParkingSectorDTO parkingSectorDTO = parkingSectorMapper.toDto(parkingSector);

        int databaseSizeBeforeCreate = parkingSectorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParkingSectorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parkingSectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParkingSector in the database
        List<ParkingSector> parkingSectorList = parkingSectorRepository.findAll();
        assertThat(parkingSectorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = parkingSectorRepository.findAll().size();
        // set the field null
        parkingSector.setActive(null);

        // Create the ParkingSector, which fails.
        ParkingSectorDTO parkingSectorDTO = parkingSectorMapper.toDto(parkingSector);

        restParkingSectorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parkingSectorDTO))
            )
            .andExpect(status().isBadRequest());

        List<ParkingSector> parkingSectorList = parkingSectorRepository.findAll();
        assertThat(parkingSectorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSectorNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = parkingSectorRepository.findAll().size();
        // set the field null
        parkingSector.setSectorName(null);

        // Create the ParkingSector, which fails.
        ParkingSectorDTO parkingSectorDTO = parkingSectorMapper.toDto(parkingSector);

        restParkingSectorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parkingSectorDTO))
            )
            .andExpect(status().isBadRequest());

        List<ParkingSector> parkingSectorList = parkingSectorRepository.findAll();
        assertThat(parkingSectorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkParkingSpaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = parkingSectorRepository.findAll().size();
        // set the field null
        parkingSector.setParkingSpace(null);

        // Create the ParkingSector, which fails.
        ParkingSectorDTO parkingSectorDTO = parkingSectorMapper.toDto(parkingSector);

        restParkingSectorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parkingSectorDTO))
            )
            .andExpect(status().isBadRequest());

        List<ParkingSector> parkingSectorList = parkingSectorRepository.findAll();
        assertThat(parkingSectorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllParkingSectors() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList
        restParkingSectorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parkingSector.getId().intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].sectorName").value(hasItem(DEFAULT_SECTOR_NAME)))
            .andExpect(jsonPath("$.[*].parkingSpace").value(hasItem(DEFAULT_PARKING_SPACE)))
            .andExpect(jsonPath("$.[*].parkingNumbersBegin").value(hasItem(DEFAULT_PARKING_NUMBERS_BEGIN)))
            .andExpect(jsonPath("$.[*].parkingNumbersFinal").value(hasItem(DEFAULT_PARKING_NUMBERS_FINAL)));
    }

    @Test
    @Transactional
    void getParkingSector() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get the parkingSector
        restParkingSectorMockMvc
            .perform(get(ENTITY_API_URL_ID, parkingSector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parkingSector.getId().intValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.sectorName").value(DEFAULT_SECTOR_NAME))
            .andExpect(jsonPath("$.parkingSpace").value(DEFAULT_PARKING_SPACE))
            .andExpect(jsonPath("$.parkingNumbersBegin").value(DEFAULT_PARKING_NUMBERS_BEGIN))
            .andExpect(jsonPath("$.parkingNumbersFinal").value(DEFAULT_PARKING_NUMBERS_FINAL));
    }

    @Test
    @Transactional
    void getParkingSectorsByIdFiltering() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        Long id = parkingSector.getId();

        defaultParkingSectorShouldBeFound("id.equals=" + id);
        defaultParkingSectorShouldNotBeFound("id.notEquals=" + id);

        defaultParkingSectorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultParkingSectorShouldNotBeFound("id.greaterThan=" + id);

        defaultParkingSectorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultParkingSectorShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where active equals to DEFAULT_ACTIVE
        defaultParkingSectorShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the parkingSectorList where active equals to UPDATED_ACTIVE
        defaultParkingSectorShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where active not equals to DEFAULT_ACTIVE
        defaultParkingSectorShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the parkingSectorList where active not equals to UPDATED_ACTIVE
        defaultParkingSectorShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultParkingSectorShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the parkingSectorList where active equals to UPDATED_ACTIVE
        defaultParkingSectorShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where active is not null
        defaultParkingSectorShouldBeFound("active.specified=true");

        // Get all the parkingSectorList where active is null
        defaultParkingSectorShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingSectorsBySectorNameIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where sectorName equals to DEFAULT_SECTOR_NAME
        defaultParkingSectorShouldBeFound("sectorName.equals=" + DEFAULT_SECTOR_NAME);

        // Get all the parkingSectorList where sectorName equals to UPDATED_SECTOR_NAME
        defaultParkingSectorShouldNotBeFound("sectorName.equals=" + UPDATED_SECTOR_NAME);
    }

    @Test
    @Transactional
    void getAllParkingSectorsBySectorNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where sectorName not equals to DEFAULT_SECTOR_NAME
        defaultParkingSectorShouldNotBeFound("sectorName.notEquals=" + DEFAULT_SECTOR_NAME);

        // Get all the parkingSectorList where sectorName not equals to UPDATED_SECTOR_NAME
        defaultParkingSectorShouldBeFound("sectorName.notEquals=" + UPDATED_SECTOR_NAME);
    }

    @Test
    @Transactional
    void getAllParkingSectorsBySectorNameIsInShouldWork() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where sectorName in DEFAULT_SECTOR_NAME or UPDATED_SECTOR_NAME
        defaultParkingSectorShouldBeFound("sectorName.in=" + DEFAULT_SECTOR_NAME + "," + UPDATED_SECTOR_NAME);

        // Get all the parkingSectorList where sectorName equals to UPDATED_SECTOR_NAME
        defaultParkingSectorShouldNotBeFound("sectorName.in=" + UPDATED_SECTOR_NAME);
    }

    @Test
    @Transactional
    void getAllParkingSectorsBySectorNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where sectorName is not null
        defaultParkingSectorShouldBeFound("sectorName.specified=true");

        // Get all the parkingSectorList where sectorName is null
        defaultParkingSectorShouldNotBeFound("sectorName.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingSectorsBySectorNameContainsSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where sectorName contains DEFAULT_SECTOR_NAME
        defaultParkingSectorShouldBeFound("sectorName.contains=" + DEFAULT_SECTOR_NAME);

        // Get all the parkingSectorList where sectorName contains UPDATED_SECTOR_NAME
        defaultParkingSectorShouldNotBeFound("sectorName.contains=" + UPDATED_SECTOR_NAME);
    }

    @Test
    @Transactional
    void getAllParkingSectorsBySectorNameNotContainsSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where sectorName does not contain DEFAULT_SECTOR_NAME
        defaultParkingSectorShouldNotBeFound("sectorName.doesNotContain=" + DEFAULT_SECTOR_NAME);

        // Get all the parkingSectorList where sectorName does not contain UPDATED_SECTOR_NAME
        defaultParkingSectorShouldBeFound("sectorName.doesNotContain=" + UPDATED_SECTOR_NAME);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingSpaceIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingSpace equals to DEFAULT_PARKING_SPACE
        defaultParkingSectorShouldBeFound("parkingSpace.equals=" + DEFAULT_PARKING_SPACE);

        // Get all the parkingSectorList where parkingSpace equals to UPDATED_PARKING_SPACE
        defaultParkingSectorShouldNotBeFound("parkingSpace.equals=" + UPDATED_PARKING_SPACE);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingSpaceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingSpace not equals to DEFAULT_PARKING_SPACE
        defaultParkingSectorShouldNotBeFound("parkingSpace.notEquals=" + DEFAULT_PARKING_SPACE);

        // Get all the parkingSectorList where parkingSpace not equals to UPDATED_PARKING_SPACE
        defaultParkingSectorShouldBeFound("parkingSpace.notEquals=" + UPDATED_PARKING_SPACE);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingSpaceIsInShouldWork() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingSpace in DEFAULT_PARKING_SPACE or UPDATED_PARKING_SPACE
        defaultParkingSectorShouldBeFound("parkingSpace.in=" + DEFAULT_PARKING_SPACE + "," + UPDATED_PARKING_SPACE);

        // Get all the parkingSectorList where parkingSpace equals to UPDATED_PARKING_SPACE
        defaultParkingSectorShouldNotBeFound("parkingSpace.in=" + UPDATED_PARKING_SPACE);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingSpaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingSpace is not null
        defaultParkingSectorShouldBeFound("parkingSpace.specified=true");

        // Get all the parkingSectorList where parkingSpace is null
        defaultParkingSectorShouldNotBeFound("parkingSpace.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingSpaceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingSpace is greater than or equal to DEFAULT_PARKING_SPACE
        defaultParkingSectorShouldBeFound("parkingSpace.greaterThanOrEqual=" + DEFAULT_PARKING_SPACE);

        // Get all the parkingSectorList where parkingSpace is greater than or equal to UPDATED_PARKING_SPACE
        defaultParkingSectorShouldNotBeFound("parkingSpace.greaterThanOrEqual=" + UPDATED_PARKING_SPACE);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingSpaceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingSpace is less than or equal to DEFAULT_PARKING_SPACE
        defaultParkingSectorShouldBeFound("parkingSpace.lessThanOrEqual=" + DEFAULT_PARKING_SPACE);

        // Get all the parkingSectorList where parkingSpace is less than or equal to SMALLER_PARKING_SPACE
        defaultParkingSectorShouldNotBeFound("parkingSpace.lessThanOrEqual=" + SMALLER_PARKING_SPACE);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingSpaceIsLessThanSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingSpace is less than DEFAULT_PARKING_SPACE
        defaultParkingSectorShouldNotBeFound("parkingSpace.lessThan=" + DEFAULT_PARKING_SPACE);

        // Get all the parkingSectorList where parkingSpace is less than UPDATED_PARKING_SPACE
        defaultParkingSectorShouldBeFound("parkingSpace.lessThan=" + UPDATED_PARKING_SPACE);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingSpaceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingSpace is greater than DEFAULT_PARKING_SPACE
        defaultParkingSectorShouldNotBeFound("parkingSpace.greaterThan=" + DEFAULT_PARKING_SPACE);

        // Get all the parkingSectorList where parkingSpace is greater than SMALLER_PARKING_SPACE
        defaultParkingSectorShouldBeFound("parkingSpace.greaterThan=" + SMALLER_PARKING_SPACE);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingNumbersBeginIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingNumbersBegin equals to DEFAULT_PARKING_NUMBERS_BEGIN
        defaultParkingSectorShouldBeFound("parkingNumbersBegin.equals=" + DEFAULT_PARKING_NUMBERS_BEGIN);

        // Get all the parkingSectorList where parkingNumbersBegin equals to UPDATED_PARKING_NUMBERS_BEGIN
        defaultParkingSectorShouldNotBeFound("parkingNumbersBegin.equals=" + UPDATED_PARKING_NUMBERS_BEGIN);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingNumbersBeginIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingNumbersBegin not equals to DEFAULT_PARKING_NUMBERS_BEGIN
        defaultParkingSectorShouldNotBeFound("parkingNumbersBegin.notEquals=" + DEFAULT_PARKING_NUMBERS_BEGIN);

        // Get all the parkingSectorList where parkingNumbersBegin not equals to UPDATED_PARKING_NUMBERS_BEGIN
        defaultParkingSectorShouldBeFound("parkingNumbersBegin.notEquals=" + UPDATED_PARKING_NUMBERS_BEGIN);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingNumbersBeginIsInShouldWork() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingNumbersBegin in DEFAULT_PARKING_NUMBERS_BEGIN or UPDATED_PARKING_NUMBERS_BEGIN
        defaultParkingSectorShouldBeFound("parkingNumbersBegin.in=" + DEFAULT_PARKING_NUMBERS_BEGIN + "," + UPDATED_PARKING_NUMBERS_BEGIN);

        // Get all the parkingSectorList where parkingNumbersBegin equals to UPDATED_PARKING_NUMBERS_BEGIN
        defaultParkingSectorShouldNotBeFound("parkingNumbersBegin.in=" + UPDATED_PARKING_NUMBERS_BEGIN);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingNumbersBeginIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingNumbersBegin is not null
        defaultParkingSectorShouldBeFound("parkingNumbersBegin.specified=true");

        // Get all the parkingSectorList where parkingNumbersBegin is null
        defaultParkingSectorShouldNotBeFound("parkingNumbersBegin.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingNumbersBeginIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingNumbersBegin is greater than or equal to DEFAULT_PARKING_NUMBERS_BEGIN
        defaultParkingSectorShouldBeFound("parkingNumbersBegin.greaterThanOrEqual=" + DEFAULT_PARKING_NUMBERS_BEGIN);

        // Get all the parkingSectorList where parkingNumbersBegin is greater than or equal to UPDATED_PARKING_NUMBERS_BEGIN
        defaultParkingSectorShouldNotBeFound("parkingNumbersBegin.greaterThanOrEqual=" + UPDATED_PARKING_NUMBERS_BEGIN);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingNumbersBeginIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingNumbersBegin is less than or equal to DEFAULT_PARKING_NUMBERS_BEGIN
        defaultParkingSectorShouldBeFound("parkingNumbersBegin.lessThanOrEqual=" + DEFAULT_PARKING_NUMBERS_BEGIN);

        // Get all the parkingSectorList where parkingNumbersBegin is less than or equal to SMALLER_PARKING_NUMBERS_BEGIN
        defaultParkingSectorShouldNotBeFound("parkingNumbersBegin.lessThanOrEqual=" + SMALLER_PARKING_NUMBERS_BEGIN);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingNumbersBeginIsLessThanSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingNumbersBegin is less than DEFAULT_PARKING_NUMBERS_BEGIN
        defaultParkingSectorShouldNotBeFound("parkingNumbersBegin.lessThan=" + DEFAULT_PARKING_NUMBERS_BEGIN);

        // Get all the parkingSectorList where parkingNumbersBegin is less than UPDATED_PARKING_NUMBERS_BEGIN
        defaultParkingSectorShouldBeFound("parkingNumbersBegin.lessThan=" + UPDATED_PARKING_NUMBERS_BEGIN);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingNumbersBeginIsGreaterThanSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingNumbersBegin is greater than DEFAULT_PARKING_NUMBERS_BEGIN
        defaultParkingSectorShouldNotBeFound("parkingNumbersBegin.greaterThan=" + DEFAULT_PARKING_NUMBERS_BEGIN);

        // Get all the parkingSectorList where parkingNumbersBegin is greater than SMALLER_PARKING_NUMBERS_BEGIN
        defaultParkingSectorShouldBeFound("parkingNumbersBegin.greaterThan=" + SMALLER_PARKING_NUMBERS_BEGIN);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingNumbersFinalIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingNumbersFinal equals to DEFAULT_PARKING_NUMBERS_FINAL
        defaultParkingSectorShouldBeFound("parkingNumbersFinal.equals=" + DEFAULT_PARKING_NUMBERS_FINAL);

        // Get all the parkingSectorList where parkingNumbersFinal equals to UPDATED_PARKING_NUMBERS_FINAL
        defaultParkingSectorShouldNotBeFound("parkingNumbersFinal.equals=" + UPDATED_PARKING_NUMBERS_FINAL);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingNumbersFinalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingNumbersFinal not equals to DEFAULT_PARKING_NUMBERS_FINAL
        defaultParkingSectorShouldNotBeFound("parkingNumbersFinal.notEquals=" + DEFAULT_PARKING_NUMBERS_FINAL);

        // Get all the parkingSectorList where parkingNumbersFinal not equals to UPDATED_PARKING_NUMBERS_FINAL
        defaultParkingSectorShouldBeFound("parkingNumbersFinal.notEquals=" + UPDATED_PARKING_NUMBERS_FINAL);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingNumbersFinalIsInShouldWork() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingNumbersFinal in DEFAULT_PARKING_NUMBERS_FINAL or UPDATED_PARKING_NUMBERS_FINAL
        defaultParkingSectorShouldBeFound("parkingNumbersFinal.in=" + DEFAULT_PARKING_NUMBERS_FINAL + "," + UPDATED_PARKING_NUMBERS_FINAL);

        // Get all the parkingSectorList where parkingNumbersFinal equals to UPDATED_PARKING_NUMBERS_FINAL
        defaultParkingSectorShouldNotBeFound("parkingNumbersFinal.in=" + UPDATED_PARKING_NUMBERS_FINAL);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingNumbersFinalIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingNumbersFinal is not null
        defaultParkingSectorShouldBeFound("parkingNumbersFinal.specified=true");

        // Get all the parkingSectorList where parkingNumbersFinal is null
        defaultParkingSectorShouldNotBeFound("parkingNumbersFinal.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingNumbersFinalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingNumbersFinal is greater than or equal to DEFAULT_PARKING_NUMBERS_FINAL
        defaultParkingSectorShouldBeFound("parkingNumbersFinal.greaterThanOrEqual=" + DEFAULT_PARKING_NUMBERS_FINAL);

        // Get all the parkingSectorList where parkingNumbersFinal is greater than or equal to UPDATED_PARKING_NUMBERS_FINAL
        defaultParkingSectorShouldNotBeFound("parkingNumbersFinal.greaterThanOrEqual=" + UPDATED_PARKING_NUMBERS_FINAL);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingNumbersFinalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingNumbersFinal is less than or equal to DEFAULT_PARKING_NUMBERS_FINAL
        defaultParkingSectorShouldBeFound("parkingNumbersFinal.lessThanOrEqual=" + DEFAULT_PARKING_NUMBERS_FINAL);

        // Get all the parkingSectorList where parkingNumbersFinal is less than or equal to SMALLER_PARKING_NUMBERS_FINAL
        defaultParkingSectorShouldNotBeFound("parkingNumbersFinal.lessThanOrEqual=" + SMALLER_PARKING_NUMBERS_FINAL);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingNumbersFinalIsLessThanSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingNumbersFinal is less than DEFAULT_PARKING_NUMBERS_FINAL
        defaultParkingSectorShouldNotBeFound("parkingNumbersFinal.lessThan=" + DEFAULT_PARKING_NUMBERS_FINAL);

        // Get all the parkingSectorList where parkingNumbersFinal is less than UPDATED_PARKING_NUMBERS_FINAL
        defaultParkingSectorShouldBeFound("parkingNumbersFinal.lessThan=" + UPDATED_PARKING_NUMBERS_FINAL);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingNumbersFinalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        // Get all the parkingSectorList where parkingNumbersFinal is greater than DEFAULT_PARKING_NUMBERS_FINAL
        defaultParkingSectorShouldNotBeFound("parkingNumbersFinal.greaterThan=" + DEFAULT_PARKING_NUMBERS_FINAL);

        // Get all the parkingSectorList where parkingNumbersFinal is greater than SMALLER_PARKING_NUMBERS_FINAL
        defaultParkingSectorShouldBeFound("parkingNumbersFinal.greaterThan=" + SMALLER_PARKING_NUMBERS_FINAL);
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingSectorSpaceIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);
        ParkingSectorSpace parkingSectorSpace = ParkingSectorSpaceResourceIT.createEntity(em);
        em.persist(parkingSectorSpace);
        em.flush();
        parkingSector.addParkingSectorSpace(parkingSectorSpace);
        parkingSectorRepository.saveAndFlush(parkingSector);
        Long parkingSectorSpaceId = parkingSectorSpace.getId();

        // Get all the parkingSectorList where parkingSectorSpace equals to parkingSectorSpaceId
        defaultParkingSectorShouldBeFound("parkingSectorSpaceId.equals=" + parkingSectorSpaceId);

        // Get all the parkingSectorList where parkingSectorSpace equals to (parkingSectorSpaceId + 1)
        defaultParkingSectorShouldNotBeFound("parkingSectorSpaceId.equals=" + (parkingSectorSpaceId + 1));
    }

    @Test
    @Transactional
    void getAllParkingSectorsByHousingVehicleItemIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);
        HousingVehicleItem housingVehicleItem = HousingVehicleItemResourceIT.createEntity(em);
        em.persist(housingVehicleItem);
        em.flush();
        parkingSector.addHousingVehicleItem(housingVehicleItem);
        parkingSectorRepository.saveAndFlush(parkingSector);
        Long housingVehicleItemId = housingVehicleItem.getId();

        // Get all the parkingSectorList where housingVehicleItem equals to housingVehicleItemId
        defaultParkingSectorShouldBeFound("housingVehicleItemId.equals=" + housingVehicleItemId);

        // Get all the parkingSectorList where housingVehicleItem equals to (housingVehicleItemId + 1)
        defaultParkingSectorShouldNotBeFound("housingVehicleItemId.equals=" + (housingVehicleItemId + 1));
    }

    @Test
    @Transactional
    void getAllParkingSectorsByParkingIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);
        Parking parking = ParkingResourceIT.createEntity(em);
        em.persist(parking);
        em.flush();
        parkingSector.setParking(parking);
        parkingSectorRepository.saveAndFlush(parkingSector);
        Long parkingId = parking.getId();

        // Get all the parkingSectorList where parking equals to parkingId
        defaultParkingSectorShouldBeFound("parkingId.equals=" + parkingId);

        // Get all the parkingSectorList where parking equals to (parkingId + 1)
        defaultParkingSectorShouldNotBeFound("parkingId.equals=" + (parkingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultParkingSectorShouldBeFound(String filter) throws Exception {
        restParkingSectorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parkingSector.getId().intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].sectorName").value(hasItem(DEFAULT_SECTOR_NAME)))
            .andExpect(jsonPath("$.[*].parkingSpace").value(hasItem(DEFAULT_PARKING_SPACE)))
            .andExpect(jsonPath("$.[*].parkingNumbersBegin").value(hasItem(DEFAULT_PARKING_NUMBERS_BEGIN)))
            .andExpect(jsonPath("$.[*].parkingNumbersFinal").value(hasItem(DEFAULT_PARKING_NUMBERS_FINAL)));

        // Check, that the count call also returns 1
        restParkingSectorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultParkingSectorShouldNotBeFound(String filter) throws Exception {
        restParkingSectorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParkingSectorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingParkingSector() throws Exception {
        // Get the parkingSector
        restParkingSectorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewParkingSector() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        int databaseSizeBeforeUpdate = parkingSectorRepository.findAll().size();

        // Update the parkingSector
        ParkingSector updatedParkingSector = parkingSectorRepository.findById(parkingSector.getId()).get();
        // Disconnect from session so that the updates on updatedParkingSector are not directly saved in db
        em.detach(updatedParkingSector);
        updatedParkingSector
            .active(UPDATED_ACTIVE)
            .sectorName(UPDATED_SECTOR_NAME)
            .parkingSpace(UPDATED_PARKING_SPACE)
            .parkingNumbersBegin(UPDATED_PARKING_NUMBERS_BEGIN)
            .parkingNumbersFinal(UPDATED_PARKING_NUMBERS_FINAL);
        ParkingSectorDTO parkingSectorDTO = parkingSectorMapper.toDto(updatedParkingSector);

        restParkingSectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parkingSectorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parkingSectorDTO))
            )
            .andExpect(status().isOk());

        // Validate the ParkingSector in the database
        List<ParkingSector> parkingSectorList = parkingSectorRepository.findAll();
        assertThat(parkingSectorList).hasSize(databaseSizeBeforeUpdate);
        ParkingSector testParkingSector = parkingSectorList.get(parkingSectorList.size() - 1);
        assertThat(testParkingSector.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testParkingSector.getSectorName()).isEqualTo(UPDATED_SECTOR_NAME);
        assertThat(testParkingSector.getParkingSpace()).isEqualTo(UPDATED_PARKING_SPACE);
        assertThat(testParkingSector.getParkingNumbersBegin()).isEqualTo(UPDATED_PARKING_NUMBERS_BEGIN);
        assertThat(testParkingSector.getParkingNumbersFinal()).isEqualTo(UPDATED_PARKING_NUMBERS_FINAL);
    }

    @Test
    @Transactional
    void putNonExistingParkingSector() throws Exception {
        int databaseSizeBeforeUpdate = parkingSectorRepository.findAll().size();
        parkingSector.setId(count.incrementAndGet());

        // Create the ParkingSector
        ParkingSectorDTO parkingSectorDTO = parkingSectorMapper.toDto(parkingSector);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParkingSectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parkingSectorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parkingSectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParkingSector in the database
        List<ParkingSector> parkingSectorList = parkingSectorRepository.findAll();
        assertThat(parkingSectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParkingSector() throws Exception {
        int databaseSizeBeforeUpdate = parkingSectorRepository.findAll().size();
        parkingSector.setId(count.incrementAndGet());

        // Create the ParkingSector
        ParkingSectorDTO parkingSectorDTO = parkingSectorMapper.toDto(parkingSector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkingSectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parkingSectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParkingSector in the database
        List<ParkingSector> parkingSectorList = parkingSectorRepository.findAll();
        assertThat(parkingSectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParkingSector() throws Exception {
        int databaseSizeBeforeUpdate = parkingSectorRepository.findAll().size();
        parkingSector.setId(count.incrementAndGet());

        // Create the ParkingSector
        ParkingSectorDTO parkingSectorDTO = parkingSectorMapper.toDto(parkingSector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkingSectorMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parkingSectorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParkingSector in the database
        List<ParkingSector> parkingSectorList = parkingSectorRepository.findAll();
        assertThat(parkingSectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParkingSectorWithPatch() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        int databaseSizeBeforeUpdate = parkingSectorRepository.findAll().size();

        // Update the parkingSector using partial update
        ParkingSector partialUpdatedParkingSector = new ParkingSector();
        partialUpdatedParkingSector.setId(parkingSector.getId());

        partialUpdatedParkingSector.parkingSpace(UPDATED_PARKING_SPACE);

        restParkingSectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParkingSector.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParkingSector))
            )
            .andExpect(status().isOk());

        // Validate the ParkingSector in the database
        List<ParkingSector> parkingSectorList = parkingSectorRepository.findAll();
        assertThat(parkingSectorList).hasSize(databaseSizeBeforeUpdate);
        ParkingSector testParkingSector = parkingSectorList.get(parkingSectorList.size() - 1);
        assertThat(testParkingSector.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testParkingSector.getSectorName()).isEqualTo(DEFAULT_SECTOR_NAME);
        assertThat(testParkingSector.getParkingSpace()).isEqualTo(UPDATED_PARKING_SPACE);
        assertThat(testParkingSector.getParkingNumbersBegin()).isEqualTo(DEFAULT_PARKING_NUMBERS_BEGIN);
        assertThat(testParkingSector.getParkingNumbersFinal()).isEqualTo(DEFAULT_PARKING_NUMBERS_FINAL);
    }

    @Test
    @Transactional
    void fullUpdateParkingSectorWithPatch() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        int databaseSizeBeforeUpdate = parkingSectorRepository.findAll().size();

        // Update the parkingSector using partial update
        ParkingSector partialUpdatedParkingSector = new ParkingSector();
        partialUpdatedParkingSector.setId(parkingSector.getId());

        partialUpdatedParkingSector
            .active(UPDATED_ACTIVE)
            .sectorName(UPDATED_SECTOR_NAME)
            .parkingSpace(UPDATED_PARKING_SPACE)
            .parkingNumbersBegin(UPDATED_PARKING_NUMBERS_BEGIN)
            .parkingNumbersFinal(UPDATED_PARKING_NUMBERS_FINAL);

        restParkingSectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParkingSector.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParkingSector))
            )
            .andExpect(status().isOk());

        // Validate the ParkingSector in the database
        List<ParkingSector> parkingSectorList = parkingSectorRepository.findAll();
        assertThat(parkingSectorList).hasSize(databaseSizeBeforeUpdate);
        ParkingSector testParkingSector = parkingSectorList.get(parkingSectorList.size() - 1);
        assertThat(testParkingSector.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testParkingSector.getSectorName()).isEqualTo(UPDATED_SECTOR_NAME);
        assertThat(testParkingSector.getParkingSpace()).isEqualTo(UPDATED_PARKING_SPACE);
        assertThat(testParkingSector.getParkingNumbersBegin()).isEqualTo(UPDATED_PARKING_NUMBERS_BEGIN);
        assertThat(testParkingSector.getParkingNumbersFinal()).isEqualTo(UPDATED_PARKING_NUMBERS_FINAL);
    }

    @Test
    @Transactional
    void patchNonExistingParkingSector() throws Exception {
        int databaseSizeBeforeUpdate = parkingSectorRepository.findAll().size();
        parkingSector.setId(count.incrementAndGet());

        // Create the ParkingSector
        ParkingSectorDTO parkingSectorDTO = parkingSectorMapper.toDto(parkingSector);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParkingSectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parkingSectorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parkingSectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParkingSector in the database
        List<ParkingSector> parkingSectorList = parkingSectorRepository.findAll();
        assertThat(parkingSectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParkingSector() throws Exception {
        int databaseSizeBeforeUpdate = parkingSectorRepository.findAll().size();
        parkingSector.setId(count.incrementAndGet());

        // Create the ParkingSector
        ParkingSectorDTO parkingSectorDTO = parkingSectorMapper.toDto(parkingSector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkingSectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parkingSectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParkingSector in the database
        List<ParkingSector> parkingSectorList = parkingSectorRepository.findAll();
        assertThat(parkingSectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParkingSector() throws Exception {
        int databaseSizeBeforeUpdate = parkingSectorRepository.findAll().size();
        parkingSector.setId(count.incrementAndGet());

        // Create the ParkingSector
        ParkingSectorDTO parkingSectorDTO = parkingSectorMapper.toDto(parkingSector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkingSectorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parkingSectorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParkingSector in the database
        List<ParkingSector> parkingSectorList = parkingSectorRepository.findAll();
        assertThat(parkingSectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParkingSector() throws Exception {
        // Initialize the database
        parkingSectorRepository.saveAndFlush(parkingSector);

        int databaseSizeBeforeDelete = parkingSectorRepository.findAll().size();

        // Delete the parkingSector
        restParkingSectorMockMvc
            .perform(delete(ENTITY_API_URL_ID, parkingSector.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ParkingSector> parkingSectorList = parkingSectorRepository.findAll();
        assertThat(parkingSectorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
