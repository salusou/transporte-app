package com.genesisoft.transporte.web.rest;

import static com.genesisoft.transporte.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Cities;
import com.genesisoft.transporte.domain.VehicleControls;
import com.genesisoft.transporte.domain.VehicleLocationStatus;
import com.genesisoft.transporte.repository.VehicleLocationStatusRepository;
import com.genesisoft.transporte.service.criteria.VehicleLocationStatusCriteria;
import com.genesisoft.transporte.service.dto.VehicleLocationStatusDTO;
import com.genesisoft.transporte.service.mapper.VehicleLocationStatusMapper;
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
 * Integration tests for the {@link VehicleLocationStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VehicleLocationStatusResourceIT {

    private static final ZonedDateTime DEFAULT_VEHICLE_LOCATION_STATUS_DATE = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(0L),
        ZoneOffset.UTC
    );
    private static final ZonedDateTime UPDATED_VEHICLE_LOCATION_STATUS_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_VEHICLE_LOCATION_STATUS_DATE = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(-1L),
        ZoneOffset.UTC
    );

    private static final String DEFAULT_VEHICLE_LOCATION_STATUS_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_LOCATION_STATUS_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vehicle-location-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VehicleLocationStatusRepository vehicleLocationStatusRepository;

    @Autowired
    private VehicleLocationStatusMapper vehicleLocationStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehicleLocationStatusMockMvc;

    private VehicleLocationStatus vehicleLocationStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleLocationStatus createEntity(EntityManager em) {
        VehicleLocationStatus vehicleLocationStatus = new VehicleLocationStatus()
            .vehicleLocationStatusDate(DEFAULT_VEHICLE_LOCATION_STATUS_DATE)
            .vehicleLocationStatusDescription(DEFAULT_VEHICLE_LOCATION_STATUS_DESCRIPTION);
        // Add required entity
        VehicleControls vehicleControls;
        if (TestUtil.findAll(em, VehicleControls.class).isEmpty()) {
            vehicleControls = VehicleControlsResourceIT.createEntity(em);
            em.persist(vehicleControls);
            em.flush();
        } else {
            vehicleControls = TestUtil.findAll(em, VehicleControls.class).get(0);
        }
        vehicleLocationStatus.setVehicleControls(vehicleControls);
        // Add required entity
        Cities cities;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            cities = CitiesResourceIT.createEntity(em);
            em.persist(cities);
            em.flush();
        } else {
            cities = TestUtil.findAll(em, Cities.class).get(0);
        }
        vehicleLocationStatus.setCities(cities);
        return vehicleLocationStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleLocationStatus createUpdatedEntity(EntityManager em) {
        VehicleLocationStatus vehicleLocationStatus = new VehicleLocationStatus()
            .vehicleLocationStatusDate(UPDATED_VEHICLE_LOCATION_STATUS_DATE)
            .vehicleLocationStatusDescription(UPDATED_VEHICLE_LOCATION_STATUS_DESCRIPTION);
        // Add required entity
        VehicleControls vehicleControls;
        if (TestUtil.findAll(em, VehicleControls.class).isEmpty()) {
            vehicleControls = VehicleControlsResourceIT.createUpdatedEntity(em);
            em.persist(vehicleControls);
            em.flush();
        } else {
            vehicleControls = TestUtil.findAll(em, VehicleControls.class).get(0);
        }
        vehicleLocationStatus.setVehicleControls(vehicleControls);
        // Add required entity
        Cities cities;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            cities = CitiesResourceIT.createUpdatedEntity(em);
            em.persist(cities);
            em.flush();
        } else {
            cities = TestUtil.findAll(em, Cities.class).get(0);
        }
        vehicleLocationStatus.setCities(cities);
        return vehicleLocationStatus;
    }

    @BeforeEach
    public void initTest() {
        vehicleLocationStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createVehicleLocationStatus() throws Exception {
        int databaseSizeBeforeCreate = vehicleLocationStatusRepository.findAll().size();
        // Create the VehicleLocationStatus
        VehicleLocationStatusDTO vehicleLocationStatusDTO = vehicleLocationStatusMapper.toDto(vehicleLocationStatus);
        restVehicleLocationStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLocationStatusDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VehicleLocationStatus in the database
        List<VehicleLocationStatus> vehicleLocationStatusList = vehicleLocationStatusRepository.findAll();
        assertThat(vehicleLocationStatusList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleLocationStatus testVehicleLocationStatus = vehicleLocationStatusList.get(vehicleLocationStatusList.size() - 1);
        assertThat(testVehicleLocationStatus.getVehicleLocationStatusDate()).isEqualTo(DEFAULT_VEHICLE_LOCATION_STATUS_DATE);
        assertThat(testVehicleLocationStatus.getVehicleLocationStatusDescription()).isEqualTo(DEFAULT_VEHICLE_LOCATION_STATUS_DESCRIPTION);
    }

    @Test
    @Transactional
    void createVehicleLocationStatusWithExistingId() throws Exception {
        // Create the VehicleLocationStatus with an existing ID
        vehicleLocationStatus.setId(1L);
        VehicleLocationStatusDTO vehicleLocationStatusDTO = vehicleLocationStatusMapper.toDto(vehicleLocationStatus);

        int databaseSizeBeforeCreate = vehicleLocationStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleLocationStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLocationStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleLocationStatus in the database
        List<VehicleLocationStatus> vehicleLocationStatusList = vehicleLocationStatusRepository.findAll();
        assertThat(vehicleLocationStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVehicleLocationStatusDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleLocationStatusRepository.findAll().size();
        // set the field null
        vehicleLocationStatus.setVehicleLocationStatusDate(null);

        // Create the VehicleLocationStatus, which fails.
        VehicleLocationStatusDTO vehicleLocationStatusDTO = vehicleLocationStatusMapper.toDto(vehicleLocationStatus);

        restVehicleLocationStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLocationStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleLocationStatus> vehicleLocationStatusList = vehicleLocationStatusRepository.findAll();
        assertThat(vehicleLocationStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVehicleLocationStatusDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleLocationStatusRepository.findAll().size();
        // set the field null
        vehicleLocationStatus.setVehicleLocationStatusDescription(null);

        // Create the VehicleLocationStatus, which fails.
        VehicleLocationStatusDTO vehicleLocationStatusDTO = vehicleLocationStatusMapper.toDto(vehicleLocationStatus);

        restVehicleLocationStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLocationStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleLocationStatus> vehicleLocationStatusList = vehicleLocationStatusRepository.findAll();
        assertThat(vehicleLocationStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVehicleLocationStatuses() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        // Get all the vehicleLocationStatusList
        restVehicleLocationStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleLocationStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleLocationStatusDate").value(hasItem(sameInstant(DEFAULT_VEHICLE_LOCATION_STATUS_DATE))))
            .andExpect(jsonPath("$.[*].vehicleLocationStatusDescription").value(hasItem(DEFAULT_VEHICLE_LOCATION_STATUS_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getVehicleLocationStatus() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        // Get the vehicleLocationStatus
        restVehicleLocationStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, vehicleLocationStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleLocationStatus.getId().intValue()))
            .andExpect(jsonPath("$.vehicleLocationStatusDate").value(sameInstant(DEFAULT_VEHICLE_LOCATION_STATUS_DATE)))
            .andExpect(jsonPath("$.vehicleLocationStatusDescription").value(DEFAULT_VEHICLE_LOCATION_STATUS_DESCRIPTION));
    }

    @Test
    @Transactional
    void getVehicleLocationStatusesByIdFiltering() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        Long id = vehicleLocationStatus.getId();

        defaultVehicleLocationStatusShouldBeFound("id.equals=" + id);
        defaultVehicleLocationStatusShouldNotBeFound("id.notEquals=" + id);

        defaultVehicleLocationStatusShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVehicleLocationStatusShouldNotBeFound("id.greaterThan=" + id);

        defaultVehicleLocationStatusShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVehicleLocationStatusShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVehicleLocationStatusesByVehicleLocationStatusDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDate equals to DEFAULT_VEHICLE_LOCATION_STATUS_DATE
        defaultVehicleLocationStatusShouldBeFound("vehicleLocationStatusDate.equals=" + DEFAULT_VEHICLE_LOCATION_STATUS_DATE);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDate equals to UPDATED_VEHICLE_LOCATION_STATUS_DATE
        defaultVehicleLocationStatusShouldNotBeFound("vehicleLocationStatusDate.equals=" + UPDATED_VEHICLE_LOCATION_STATUS_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleLocationStatusesByVehicleLocationStatusDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDate not equals to DEFAULT_VEHICLE_LOCATION_STATUS_DATE
        defaultVehicleLocationStatusShouldNotBeFound("vehicleLocationStatusDate.notEquals=" + DEFAULT_VEHICLE_LOCATION_STATUS_DATE);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDate not equals to UPDATED_VEHICLE_LOCATION_STATUS_DATE
        defaultVehicleLocationStatusShouldBeFound("vehicleLocationStatusDate.notEquals=" + UPDATED_VEHICLE_LOCATION_STATUS_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleLocationStatusesByVehicleLocationStatusDateIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDate in DEFAULT_VEHICLE_LOCATION_STATUS_DATE or UPDATED_VEHICLE_LOCATION_STATUS_DATE
        defaultVehicleLocationStatusShouldBeFound(
            "vehicleLocationStatusDate.in=" + DEFAULT_VEHICLE_LOCATION_STATUS_DATE + "," + UPDATED_VEHICLE_LOCATION_STATUS_DATE
        );

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDate equals to UPDATED_VEHICLE_LOCATION_STATUS_DATE
        defaultVehicleLocationStatusShouldNotBeFound("vehicleLocationStatusDate.in=" + UPDATED_VEHICLE_LOCATION_STATUS_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleLocationStatusesByVehicleLocationStatusDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDate is not null
        defaultVehicleLocationStatusShouldBeFound("vehicleLocationStatusDate.specified=true");

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDate is null
        defaultVehicleLocationStatusShouldNotBeFound("vehicleLocationStatusDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleLocationStatusesByVehicleLocationStatusDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDate is greater than or equal to DEFAULT_VEHICLE_LOCATION_STATUS_DATE
        defaultVehicleLocationStatusShouldBeFound("vehicleLocationStatusDate.greaterThanOrEqual=" + DEFAULT_VEHICLE_LOCATION_STATUS_DATE);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDate is greater than or equal to UPDATED_VEHICLE_LOCATION_STATUS_DATE
        defaultVehicleLocationStatusShouldNotBeFound(
            "vehicleLocationStatusDate.greaterThanOrEqual=" + UPDATED_VEHICLE_LOCATION_STATUS_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleLocationStatusesByVehicleLocationStatusDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDate is less than or equal to DEFAULT_VEHICLE_LOCATION_STATUS_DATE
        defaultVehicleLocationStatusShouldBeFound("vehicleLocationStatusDate.lessThanOrEqual=" + DEFAULT_VEHICLE_LOCATION_STATUS_DATE);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDate is less than or equal to SMALLER_VEHICLE_LOCATION_STATUS_DATE
        defaultVehicleLocationStatusShouldNotBeFound("vehicleLocationStatusDate.lessThanOrEqual=" + SMALLER_VEHICLE_LOCATION_STATUS_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleLocationStatusesByVehicleLocationStatusDateIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDate is less than DEFAULT_VEHICLE_LOCATION_STATUS_DATE
        defaultVehicleLocationStatusShouldNotBeFound("vehicleLocationStatusDate.lessThan=" + DEFAULT_VEHICLE_LOCATION_STATUS_DATE);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDate is less than UPDATED_VEHICLE_LOCATION_STATUS_DATE
        defaultVehicleLocationStatusShouldBeFound("vehicleLocationStatusDate.lessThan=" + UPDATED_VEHICLE_LOCATION_STATUS_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleLocationStatusesByVehicleLocationStatusDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDate is greater than DEFAULT_VEHICLE_LOCATION_STATUS_DATE
        defaultVehicleLocationStatusShouldNotBeFound("vehicleLocationStatusDate.greaterThan=" + DEFAULT_VEHICLE_LOCATION_STATUS_DATE);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDate is greater than SMALLER_VEHICLE_LOCATION_STATUS_DATE
        defaultVehicleLocationStatusShouldBeFound("vehicleLocationStatusDate.greaterThan=" + SMALLER_VEHICLE_LOCATION_STATUS_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleLocationStatusesByVehicleLocationStatusDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDescription equals to DEFAULT_VEHICLE_LOCATION_STATUS_DESCRIPTION
        defaultVehicleLocationStatusShouldBeFound("vehicleLocationStatusDescription.equals=" + DEFAULT_VEHICLE_LOCATION_STATUS_DESCRIPTION);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDescription equals to UPDATED_VEHICLE_LOCATION_STATUS_DESCRIPTION
        defaultVehicleLocationStatusShouldNotBeFound(
            "vehicleLocationStatusDescription.equals=" + UPDATED_VEHICLE_LOCATION_STATUS_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllVehicleLocationStatusesByVehicleLocationStatusDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDescription not equals to DEFAULT_VEHICLE_LOCATION_STATUS_DESCRIPTION
        defaultVehicleLocationStatusShouldNotBeFound(
            "vehicleLocationStatusDescription.notEquals=" + DEFAULT_VEHICLE_LOCATION_STATUS_DESCRIPTION
        );

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDescription not equals to UPDATED_VEHICLE_LOCATION_STATUS_DESCRIPTION
        defaultVehicleLocationStatusShouldBeFound(
            "vehicleLocationStatusDescription.notEquals=" + UPDATED_VEHICLE_LOCATION_STATUS_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllVehicleLocationStatusesByVehicleLocationStatusDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDescription in DEFAULT_VEHICLE_LOCATION_STATUS_DESCRIPTION or UPDATED_VEHICLE_LOCATION_STATUS_DESCRIPTION
        defaultVehicleLocationStatusShouldBeFound(
            "vehicleLocationStatusDescription.in=" +
            DEFAULT_VEHICLE_LOCATION_STATUS_DESCRIPTION +
            "," +
            UPDATED_VEHICLE_LOCATION_STATUS_DESCRIPTION
        );

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDescription equals to UPDATED_VEHICLE_LOCATION_STATUS_DESCRIPTION
        defaultVehicleLocationStatusShouldNotBeFound("vehicleLocationStatusDescription.in=" + UPDATED_VEHICLE_LOCATION_STATUS_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllVehicleLocationStatusesByVehicleLocationStatusDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDescription is not null
        defaultVehicleLocationStatusShouldBeFound("vehicleLocationStatusDescription.specified=true");

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDescription is null
        defaultVehicleLocationStatusShouldNotBeFound("vehicleLocationStatusDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleLocationStatusesByVehicleLocationStatusDescriptionContainsSomething() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDescription contains DEFAULT_VEHICLE_LOCATION_STATUS_DESCRIPTION
        defaultVehicleLocationStatusShouldBeFound(
            "vehicleLocationStatusDescription.contains=" + DEFAULT_VEHICLE_LOCATION_STATUS_DESCRIPTION
        );

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDescription contains UPDATED_VEHICLE_LOCATION_STATUS_DESCRIPTION
        defaultVehicleLocationStatusShouldNotBeFound(
            "vehicleLocationStatusDescription.contains=" + UPDATED_VEHICLE_LOCATION_STATUS_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllVehicleLocationStatusesByVehicleLocationStatusDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDescription does not contain DEFAULT_VEHICLE_LOCATION_STATUS_DESCRIPTION
        defaultVehicleLocationStatusShouldNotBeFound(
            "vehicleLocationStatusDescription.doesNotContain=" + DEFAULT_VEHICLE_LOCATION_STATUS_DESCRIPTION
        );

        // Get all the vehicleLocationStatusList where vehicleLocationStatusDescription does not contain UPDATED_VEHICLE_LOCATION_STATUS_DESCRIPTION
        defaultVehicleLocationStatusShouldBeFound(
            "vehicleLocationStatusDescription.doesNotContain=" + UPDATED_VEHICLE_LOCATION_STATUS_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllVehicleLocationStatusesByVehicleControlsIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);
        VehicleControls vehicleControls = VehicleControlsResourceIT.createEntity(em);
        em.persist(vehicleControls);
        em.flush();
        vehicleLocationStatus.setVehicleControls(vehicleControls);
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);
        Long vehicleControlsId = vehicleControls.getId();

        // Get all the vehicleLocationStatusList where vehicleControls equals to vehicleControlsId
        defaultVehicleLocationStatusShouldBeFound("vehicleControlsId.equals=" + vehicleControlsId);

        // Get all the vehicleLocationStatusList where vehicleControls equals to (vehicleControlsId + 1)
        defaultVehicleLocationStatusShouldNotBeFound("vehicleControlsId.equals=" + (vehicleControlsId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleLocationStatusesByCitiesIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);
        Cities cities = CitiesResourceIT.createEntity(em);
        em.persist(cities);
        em.flush();
        vehicleLocationStatus.setCities(cities);
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);
        Long citiesId = cities.getId();

        // Get all the vehicleLocationStatusList where cities equals to citiesId
        defaultVehicleLocationStatusShouldBeFound("citiesId.equals=" + citiesId);

        // Get all the vehicleLocationStatusList where cities equals to (citiesId + 1)
        defaultVehicleLocationStatusShouldNotBeFound("citiesId.equals=" + (citiesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVehicleLocationStatusShouldBeFound(String filter) throws Exception {
        restVehicleLocationStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleLocationStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleLocationStatusDate").value(hasItem(sameInstant(DEFAULT_VEHICLE_LOCATION_STATUS_DATE))))
            .andExpect(jsonPath("$.[*].vehicleLocationStatusDescription").value(hasItem(DEFAULT_VEHICLE_LOCATION_STATUS_DESCRIPTION)));

        // Check, that the count call also returns 1
        restVehicleLocationStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVehicleLocationStatusShouldNotBeFound(String filter) throws Exception {
        restVehicleLocationStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVehicleLocationStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVehicleLocationStatus() throws Exception {
        // Get the vehicleLocationStatus
        restVehicleLocationStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVehicleLocationStatus() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        int databaseSizeBeforeUpdate = vehicleLocationStatusRepository.findAll().size();

        // Update the vehicleLocationStatus
        VehicleLocationStatus updatedVehicleLocationStatus = vehicleLocationStatusRepository.findById(vehicleLocationStatus.getId()).get();
        // Disconnect from session so that the updates on updatedVehicleLocationStatus are not directly saved in db
        em.detach(updatedVehicleLocationStatus);
        updatedVehicleLocationStatus
            .vehicleLocationStatusDate(UPDATED_VEHICLE_LOCATION_STATUS_DATE)
            .vehicleLocationStatusDescription(UPDATED_VEHICLE_LOCATION_STATUS_DESCRIPTION);
        VehicleLocationStatusDTO vehicleLocationStatusDTO = vehicleLocationStatusMapper.toDto(updatedVehicleLocationStatus);

        restVehicleLocationStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleLocationStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLocationStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the VehicleLocationStatus in the database
        List<VehicleLocationStatus> vehicleLocationStatusList = vehicleLocationStatusRepository.findAll();
        assertThat(vehicleLocationStatusList).hasSize(databaseSizeBeforeUpdate);
        VehicleLocationStatus testVehicleLocationStatus = vehicleLocationStatusList.get(vehicleLocationStatusList.size() - 1);
        assertThat(testVehicleLocationStatus.getVehicleLocationStatusDate()).isEqualTo(UPDATED_VEHICLE_LOCATION_STATUS_DATE);
        assertThat(testVehicleLocationStatus.getVehicleLocationStatusDescription()).isEqualTo(UPDATED_VEHICLE_LOCATION_STATUS_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingVehicleLocationStatus() throws Exception {
        int databaseSizeBeforeUpdate = vehicleLocationStatusRepository.findAll().size();
        vehicleLocationStatus.setId(count.incrementAndGet());

        // Create the VehicleLocationStatus
        VehicleLocationStatusDTO vehicleLocationStatusDTO = vehicleLocationStatusMapper.toDto(vehicleLocationStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleLocationStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleLocationStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLocationStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleLocationStatus in the database
        List<VehicleLocationStatus> vehicleLocationStatusList = vehicleLocationStatusRepository.findAll();
        assertThat(vehicleLocationStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVehicleLocationStatus() throws Exception {
        int databaseSizeBeforeUpdate = vehicleLocationStatusRepository.findAll().size();
        vehicleLocationStatus.setId(count.incrementAndGet());

        // Create the VehicleLocationStatus
        VehicleLocationStatusDTO vehicleLocationStatusDTO = vehicleLocationStatusMapper.toDto(vehicleLocationStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleLocationStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLocationStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleLocationStatus in the database
        List<VehicleLocationStatus> vehicleLocationStatusList = vehicleLocationStatusRepository.findAll();
        assertThat(vehicleLocationStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVehicleLocationStatus() throws Exception {
        int databaseSizeBeforeUpdate = vehicleLocationStatusRepository.findAll().size();
        vehicleLocationStatus.setId(count.incrementAndGet());

        // Create the VehicleLocationStatus
        VehicleLocationStatusDTO vehicleLocationStatusDTO = vehicleLocationStatusMapper.toDto(vehicleLocationStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleLocationStatusMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLocationStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleLocationStatus in the database
        List<VehicleLocationStatus> vehicleLocationStatusList = vehicleLocationStatusRepository.findAll();
        assertThat(vehicleLocationStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVehicleLocationStatusWithPatch() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        int databaseSizeBeforeUpdate = vehicleLocationStatusRepository.findAll().size();

        // Update the vehicleLocationStatus using partial update
        VehicleLocationStatus partialUpdatedVehicleLocationStatus = new VehicleLocationStatus();
        partialUpdatedVehicleLocationStatus.setId(vehicleLocationStatus.getId());

        partialUpdatedVehicleLocationStatus.vehicleLocationStatusDescription(UPDATED_VEHICLE_LOCATION_STATUS_DESCRIPTION);

        restVehicleLocationStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleLocationStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleLocationStatus))
            )
            .andExpect(status().isOk());

        // Validate the VehicleLocationStatus in the database
        List<VehicleLocationStatus> vehicleLocationStatusList = vehicleLocationStatusRepository.findAll();
        assertThat(vehicleLocationStatusList).hasSize(databaseSizeBeforeUpdate);
        VehicleLocationStatus testVehicleLocationStatus = vehicleLocationStatusList.get(vehicleLocationStatusList.size() - 1);
        assertThat(testVehicleLocationStatus.getVehicleLocationStatusDate()).isEqualTo(DEFAULT_VEHICLE_LOCATION_STATUS_DATE);
        assertThat(testVehicleLocationStatus.getVehicleLocationStatusDescription()).isEqualTo(UPDATED_VEHICLE_LOCATION_STATUS_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateVehicleLocationStatusWithPatch() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        int databaseSizeBeforeUpdate = vehicleLocationStatusRepository.findAll().size();

        // Update the vehicleLocationStatus using partial update
        VehicleLocationStatus partialUpdatedVehicleLocationStatus = new VehicleLocationStatus();
        partialUpdatedVehicleLocationStatus.setId(vehicleLocationStatus.getId());

        partialUpdatedVehicleLocationStatus
            .vehicleLocationStatusDate(UPDATED_VEHICLE_LOCATION_STATUS_DATE)
            .vehicleLocationStatusDescription(UPDATED_VEHICLE_LOCATION_STATUS_DESCRIPTION);

        restVehicleLocationStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleLocationStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleLocationStatus))
            )
            .andExpect(status().isOk());

        // Validate the VehicleLocationStatus in the database
        List<VehicleLocationStatus> vehicleLocationStatusList = vehicleLocationStatusRepository.findAll();
        assertThat(vehicleLocationStatusList).hasSize(databaseSizeBeforeUpdate);
        VehicleLocationStatus testVehicleLocationStatus = vehicleLocationStatusList.get(vehicleLocationStatusList.size() - 1);
        assertThat(testVehicleLocationStatus.getVehicleLocationStatusDate()).isEqualTo(UPDATED_VEHICLE_LOCATION_STATUS_DATE);
        assertThat(testVehicleLocationStatus.getVehicleLocationStatusDescription()).isEqualTo(UPDATED_VEHICLE_LOCATION_STATUS_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingVehicleLocationStatus() throws Exception {
        int databaseSizeBeforeUpdate = vehicleLocationStatusRepository.findAll().size();
        vehicleLocationStatus.setId(count.incrementAndGet());

        // Create the VehicleLocationStatus
        VehicleLocationStatusDTO vehicleLocationStatusDTO = vehicleLocationStatusMapper.toDto(vehicleLocationStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleLocationStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vehicleLocationStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLocationStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleLocationStatus in the database
        List<VehicleLocationStatus> vehicleLocationStatusList = vehicleLocationStatusRepository.findAll();
        assertThat(vehicleLocationStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVehicleLocationStatus() throws Exception {
        int databaseSizeBeforeUpdate = vehicleLocationStatusRepository.findAll().size();
        vehicleLocationStatus.setId(count.incrementAndGet());

        // Create the VehicleLocationStatus
        VehicleLocationStatusDTO vehicleLocationStatusDTO = vehicleLocationStatusMapper.toDto(vehicleLocationStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleLocationStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLocationStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleLocationStatus in the database
        List<VehicleLocationStatus> vehicleLocationStatusList = vehicleLocationStatusRepository.findAll();
        assertThat(vehicleLocationStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVehicleLocationStatus() throws Exception {
        int databaseSizeBeforeUpdate = vehicleLocationStatusRepository.findAll().size();
        vehicleLocationStatus.setId(count.incrementAndGet());

        // Create the VehicleLocationStatus
        VehicleLocationStatusDTO vehicleLocationStatusDTO = vehicleLocationStatusMapper.toDto(vehicleLocationStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleLocationStatusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleLocationStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleLocationStatus in the database
        List<VehicleLocationStatus> vehicleLocationStatusList = vehicleLocationStatusRepository.findAll();
        assertThat(vehicleLocationStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVehicleLocationStatus() throws Exception {
        // Initialize the database
        vehicleLocationStatusRepository.saveAndFlush(vehicleLocationStatus);

        int databaseSizeBeforeDelete = vehicleLocationStatusRepository.findAll().size();

        // Delete the vehicleLocationStatus
        restVehicleLocationStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, vehicleLocationStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehicleLocationStatus> vehicleLocationStatusList = vehicleLocationStatusRepository.findAll();
        assertThat(vehicleLocationStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
