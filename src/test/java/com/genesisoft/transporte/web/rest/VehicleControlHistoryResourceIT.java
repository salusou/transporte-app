package com.genesisoft.transporte.web.rest;

import static com.genesisoft.transporte.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Employees;
import com.genesisoft.transporte.domain.VehicleControlHistory;
import com.genesisoft.transporte.domain.VehicleControls;
import com.genesisoft.transporte.repository.VehicleControlHistoryRepository;
import com.genesisoft.transporte.service.criteria.VehicleControlHistoryCriteria;
import com.genesisoft.transporte.service.dto.VehicleControlHistoryDTO;
import com.genesisoft.transporte.service.mapper.VehicleControlHistoryMapper;
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
 * Integration tests for the {@link VehicleControlHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VehicleControlHistoryResourceIT {

    private static final ZonedDateTime DEFAULT_VEHICLE_CONTROL_HISTORY_DATE = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(0L),
        ZoneOffset.UTC
    );
    private static final ZonedDateTime UPDATED_VEHICLE_CONTROL_HISTORY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_VEHICLE_CONTROL_HISTORY_DATE = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(-1L),
        ZoneOffset.UTC
    );

    private static final String DEFAULT_VEHICLE_CONTROL_HISTORY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_CONTROL_HISTORY_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vehicle-control-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VehicleControlHistoryRepository vehicleControlHistoryRepository;

    @Autowired
    private VehicleControlHistoryMapper vehicleControlHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehicleControlHistoryMockMvc;

    private VehicleControlHistory vehicleControlHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleControlHistory createEntity(EntityManager em) {
        VehicleControlHistory vehicleControlHistory = new VehicleControlHistory()
            .vehicleControlHistoryDate(DEFAULT_VEHICLE_CONTROL_HISTORY_DATE)
            .vehicleControlHistoryDescription(DEFAULT_VEHICLE_CONTROL_HISTORY_DESCRIPTION);
        // Add required entity
        VehicleControls vehicleControls;
        if (TestUtil.findAll(em, VehicleControls.class).isEmpty()) {
            vehicleControls = VehicleControlsResourceIT.createEntity(em);
            em.persist(vehicleControls);
            em.flush();
        } else {
            vehicleControls = TestUtil.findAll(em, VehicleControls.class).get(0);
        }
        vehicleControlHistory.setVehicleControls(vehicleControls);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        vehicleControlHistory.setEmployees(employees);
        return vehicleControlHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleControlHistory createUpdatedEntity(EntityManager em) {
        VehicleControlHistory vehicleControlHistory = new VehicleControlHistory()
            .vehicleControlHistoryDate(UPDATED_VEHICLE_CONTROL_HISTORY_DATE)
            .vehicleControlHistoryDescription(UPDATED_VEHICLE_CONTROL_HISTORY_DESCRIPTION);
        // Add required entity
        VehicleControls vehicleControls;
        if (TestUtil.findAll(em, VehicleControls.class).isEmpty()) {
            vehicleControls = VehicleControlsResourceIT.createUpdatedEntity(em);
            em.persist(vehicleControls);
            em.flush();
        } else {
            vehicleControls = TestUtil.findAll(em, VehicleControls.class).get(0);
        }
        vehicleControlHistory.setVehicleControls(vehicleControls);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        vehicleControlHistory.setEmployees(employees);
        return vehicleControlHistory;
    }

    @BeforeEach
    public void initTest() {
        vehicleControlHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createVehicleControlHistory() throws Exception {
        int databaseSizeBeforeCreate = vehicleControlHistoryRepository.findAll().size();
        // Create the VehicleControlHistory
        VehicleControlHistoryDTO vehicleControlHistoryDTO = vehicleControlHistoryMapper.toDto(vehicleControlHistory);
        restVehicleControlHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlHistoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VehicleControlHistory in the database
        List<VehicleControlHistory> vehicleControlHistoryList = vehicleControlHistoryRepository.findAll();
        assertThat(vehicleControlHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleControlHistory testVehicleControlHistory = vehicleControlHistoryList.get(vehicleControlHistoryList.size() - 1);
        assertThat(testVehicleControlHistory.getVehicleControlHistoryDate()).isEqualTo(DEFAULT_VEHICLE_CONTROL_HISTORY_DATE);
        assertThat(testVehicleControlHistory.getVehicleControlHistoryDescription()).isEqualTo(DEFAULT_VEHICLE_CONTROL_HISTORY_DESCRIPTION);
    }

    @Test
    @Transactional
    void createVehicleControlHistoryWithExistingId() throws Exception {
        // Create the VehicleControlHistory with an existing ID
        vehicleControlHistory.setId(1L);
        VehicleControlHistoryDTO vehicleControlHistoryDTO = vehicleControlHistoryMapper.toDto(vehicleControlHistory);

        int databaseSizeBeforeCreate = vehicleControlHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleControlHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlHistory in the database
        List<VehicleControlHistory> vehicleControlHistoryList = vehicleControlHistoryRepository.findAll();
        assertThat(vehicleControlHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVehicleControlHistoryDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleControlHistoryRepository.findAll().size();
        // set the field null
        vehicleControlHistory.setVehicleControlHistoryDate(null);

        // Create the VehicleControlHistory, which fails.
        VehicleControlHistoryDTO vehicleControlHistoryDTO = vehicleControlHistoryMapper.toDto(vehicleControlHistory);

        restVehicleControlHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleControlHistory> vehicleControlHistoryList = vehicleControlHistoryRepository.findAll();
        assertThat(vehicleControlHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVehicleControlHistoryDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleControlHistoryRepository.findAll().size();
        // set the field null
        vehicleControlHistory.setVehicleControlHistoryDescription(null);

        // Create the VehicleControlHistory, which fails.
        VehicleControlHistoryDTO vehicleControlHistoryDTO = vehicleControlHistoryMapper.toDto(vehicleControlHistory);

        restVehicleControlHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleControlHistory> vehicleControlHistoryList = vehicleControlHistoryRepository.findAll();
        assertThat(vehicleControlHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVehicleControlHistories() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        // Get all the vehicleControlHistoryList
        restVehicleControlHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleControlHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleControlHistoryDate").value(hasItem(sameInstant(DEFAULT_VEHICLE_CONTROL_HISTORY_DATE))))
            .andExpect(jsonPath("$.[*].vehicleControlHistoryDescription").value(hasItem(DEFAULT_VEHICLE_CONTROL_HISTORY_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getVehicleControlHistory() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        // Get the vehicleControlHistory
        restVehicleControlHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, vehicleControlHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleControlHistory.getId().intValue()))
            .andExpect(jsonPath("$.vehicleControlHistoryDate").value(sameInstant(DEFAULT_VEHICLE_CONTROL_HISTORY_DATE)))
            .andExpect(jsonPath("$.vehicleControlHistoryDescription").value(DEFAULT_VEHICLE_CONTROL_HISTORY_DESCRIPTION));
    }

    @Test
    @Transactional
    void getVehicleControlHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        Long id = vehicleControlHistory.getId();

        defaultVehicleControlHistoryShouldBeFound("id.equals=" + id);
        defaultVehicleControlHistoryShouldNotBeFound("id.notEquals=" + id);

        defaultVehicleControlHistoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVehicleControlHistoryShouldNotBeFound("id.greaterThan=" + id);

        defaultVehicleControlHistoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVehicleControlHistoryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVehicleControlHistoriesByVehicleControlHistoryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDate equals to DEFAULT_VEHICLE_CONTROL_HISTORY_DATE
        defaultVehicleControlHistoryShouldBeFound("vehicleControlHistoryDate.equals=" + DEFAULT_VEHICLE_CONTROL_HISTORY_DATE);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDate equals to UPDATED_VEHICLE_CONTROL_HISTORY_DATE
        defaultVehicleControlHistoryShouldNotBeFound("vehicleControlHistoryDate.equals=" + UPDATED_VEHICLE_CONTROL_HISTORY_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlHistoriesByVehicleControlHistoryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDate not equals to DEFAULT_VEHICLE_CONTROL_HISTORY_DATE
        defaultVehicleControlHistoryShouldNotBeFound("vehicleControlHistoryDate.notEquals=" + DEFAULT_VEHICLE_CONTROL_HISTORY_DATE);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDate not equals to UPDATED_VEHICLE_CONTROL_HISTORY_DATE
        defaultVehicleControlHistoryShouldBeFound("vehicleControlHistoryDate.notEquals=" + UPDATED_VEHICLE_CONTROL_HISTORY_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlHistoriesByVehicleControlHistoryDateIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDate in DEFAULT_VEHICLE_CONTROL_HISTORY_DATE or UPDATED_VEHICLE_CONTROL_HISTORY_DATE
        defaultVehicleControlHistoryShouldBeFound(
            "vehicleControlHistoryDate.in=" + DEFAULT_VEHICLE_CONTROL_HISTORY_DATE + "," + UPDATED_VEHICLE_CONTROL_HISTORY_DATE
        );

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDate equals to UPDATED_VEHICLE_CONTROL_HISTORY_DATE
        defaultVehicleControlHistoryShouldNotBeFound("vehicleControlHistoryDate.in=" + UPDATED_VEHICLE_CONTROL_HISTORY_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlHistoriesByVehicleControlHistoryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDate is not null
        defaultVehicleControlHistoryShouldBeFound("vehicleControlHistoryDate.specified=true");

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDate is null
        defaultVehicleControlHistoryShouldNotBeFound("vehicleControlHistoryDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlHistoriesByVehicleControlHistoryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDate is greater than or equal to DEFAULT_VEHICLE_CONTROL_HISTORY_DATE
        defaultVehicleControlHistoryShouldBeFound("vehicleControlHistoryDate.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_HISTORY_DATE);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDate is greater than or equal to UPDATED_VEHICLE_CONTROL_HISTORY_DATE
        defaultVehicleControlHistoryShouldNotBeFound(
            "vehicleControlHistoryDate.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_HISTORY_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlHistoriesByVehicleControlHistoryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDate is less than or equal to DEFAULT_VEHICLE_CONTROL_HISTORY_DATE
        defaultVehicleControlHistoryShouldBeFound("vehicleControlHistoryDate.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_HISTORY_DATE);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDate is less than or equal to SMALLER_VEHICLE_CONTROL_HISTORY_DATE
        defaultVehicleControlHistoryShouldNotBeFound("vehicleControlHistoryDate.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_HISTORY_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlHistoriesByVehicleControlHistoryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDate is less than DEFAULT_VEHICLE_CONTROL_HISTORY_DATE
        defaultVehicleControlHistoryShouldNotBeFound("vehicleControlHistoryDate.lessThan=" + DEFAULT_VEHICLE_CONTROL_HISTORY_DATE);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDate is less than UPDATED_VEHICLE_CONTROL_HISTORY_DATE
        defaultVehicleControlHistoryShouldBeFound("vehicleControlHistoryDate.lessThan=" + UPDATED_VEHICLE_CONTROL_HISTORY_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlHistoriesByVehicleControlHistoryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDate is greater than DEFAULT_VEHICLE_CONTROL_HISTORY_DATE
        defaultVehicleControlHistoryShouldNotBeFound("vehicleControlHistoryDate.greaterThan=" + DEFAULT_VEHICLE_CONTROL_HISTORY_DATE);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDate is greater than SMALLER_VEHICLE_CONTROL_HISTORY_DATE
        defaultVehicleControlHistoryShouldBeFound("vehicleControlHistoryDate.greaterThan=" + SMALLER_VEHICLE_CONTROL_HISTORY_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlHistoriesByVehicleControlHistoryDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDescription equals to DEFAULT_VEHICLE_CONTROL_HISTORY_DESCRIPTION
        defaultVehicleControlHistoryShouldBeFound("vehicleControlHistoryDescription.equals=" + DEFAULT_VEHICLE_CONTROL_HISTORY_DESCRIPTION);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDescription equals to UPDATED_VEHICLE_CONTROL_HISTORY_DESCRIPTION
        defaultVehicleControlHistoryShouldNotBeFound(
            "vehicleControlHistoryDescription.equals=" + UPDATED_VEHICLE_CONTROL_HISTORY_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlHistoriesByVehicleControlHistoryDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDescription not equals to DEFAULT_VEHICLE_CONTROL_HISTORY_DESCRIPTION
        defaultVehicleControlHistoryShouldNotBeFound(
            "vehicleControlHistoryDescription.notEquals=" + DEFAULT_VEHICLE_CONTROL_HISTORY_DESCRIPTION
        );

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDescription not equals to UPDATED_VEHICLE_CONTROL_HISTORY_DESCRIPTION
        defaultVehicleControlHistoryShouldBeFound(
            "vehicleControlHistoryDescription.notEquals=" + UPDATED_VEHICLE_CONTROL_HISTORY_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlHistoriesByVehicleControlHistoryDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDescription in DEFAULT_VEHICLE_CONTROL_HISTORY_DESCRIPTION or UPDATED_VEHICLE_CONTROL_HISTORY_DESCRIPTION
        defaultVehicleControlHistoryShouldBeFound(
            "vehicleControlHistoryDescription.in=" +
            DEFAULT_VEHICLE_CONTROL_HISTORY_DESCRIPTION +
            "," +
            UPDATED_VEHICLE_CONTROL_HISTORY_DESCRIPTION
        );

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDescription equals to UPDATED_VEHICLE_CONTROL_HISTORY_DESCRIPTION
        defaultVehicleControlHistoryShouldNotBeFound("vehicleControlHistoryDescription.in=" + UPDATED_VEHICLE_CONTROL_HISTORY_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllVehicleControlHistoriesByVehicleControlHistoryDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDescription is not null
        defaultVehicleControlHistoryShouldBeFound("vehicleControlHistoryDescription.specified=true");

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDescription is null
        defaultVehicleControlHistoryShouldNotBeFound("vehicleControlHistoryDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlHistoriesByVehicleControlHistoryDescriptionContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDescription contains DEFAULT_VEHICLE_CONTROL_HISTORY_DESCRIPTION
        defaultVehicleControlHistoryShouldBeFound(
            "vehicleControlHistoryDescription.contains=" + DEFAULT_VEHICLE_CONTROL_HISTORY_DESCRIPTION
        );

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDescription contains UPDATED_VEHICLE_CONTROL_HISTORY_DESCRIPTION
        defaultVehicleControlHistoryShouldNotBeFound(
            "vehicleControlHistoryDescription.contains=" + UPDATED_VEHICLE_CONTROL_HISTORY_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlHistoriesByVehicleControlHistoryDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDescription does not contain DEFAULT_VEHICLE_CONTROL_HISTORY_DESCRIPTION
        defaultVehicleControlHistoryShouldNotBeFound(
            "vehicleControlHistoryDescription.doesNotContain=" + DEFAULT_VEHICLE_CONTROL_HISTORY_DESCRIPTION
        );

        // Get all the vehicleControlHistoryList where vehicleControlHistoryDescription does not contain UPDATED_VEHICLE_CONTROL_HISTORY_DESCRIPTION
        defaultVehicleControlHistoryShouldBeFound(
            "vehicleControlHistoryDescription.doesNotContain=" + UPDATED_VEHICLE_CONTROL_HISTORY_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlHistoriesByVehicleControlsIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);
        VehicleControls vehicleControls = VehicleControlsResourceIT.createEntity(em);
        em.persist(vehicleControls);
        em.flush();
        vehicleControlHistory.setVehicleControls(vehicleControls);
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);
        Long vehicleControlsId = vehicleControls.getId();

        // Get all the vehicleControlHistoryList where vehicleControls equals to vehicleControlsId
        defaultVehicleControlHistoryShouldBeFound("vehicleControlsId.equals=" + vehicleControlsId);

        // Get all the vehicleControlHistoryList where vehicleControls equals to (vehicleControlsId + 1)
        defaultVehicleControlHistoryShouldNotBeFound("vehicleControlsId.equals=" + (vehicleControlsId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleControlHistoriesByEmployeesIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);
        Employees employees = EmployeesResourceIT.createEntity(em);
        em.persist(employees);
        em.flush();
        vehicleControlHistory.setEmployees(employees);
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);
        Long employeesId = employees.getId();

        // Get all the vehicleControlHistoryList where employees equals to employeesId
        defaultVehicleControlHistoryShouldBeFound("employeesId.equals=" + employeesId);

        // Get all the vehicleControlHistoryList where employees equals to (employeesId + 1)
        defaultVehicleControlHistoryShouldNotBeFound("employeesId.equals=" + (employeesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVehicleControlHistoryShouldBeFound(String filter) throws Exception {
        restVehicleControlHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleControlHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleControlHistoryDate").value(hasItem(sameInstant(DEFAULT_VEHICLE_CONTROL_HISTORY_DATE))))
            .andExpect(jsonPath("$.[*].vehicleControlHistoryDescription").value(hasItem(DEFAULT_VEHICLE_CONTROL_HISTORY_DESCRIPTION)));

        // Check, that the count call also returns 1
        restVehicleControlHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVehicleControlHistoryShouldNotBeFound(String filter) throws Exception {
        restVehicleControlHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVehicleControlHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVehicleControlHistory() throws Exception {
        // Get the vehicleControlHistory
        restVehicleControlHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVehicleControlHistory() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        int databaseSizeBeforeUpdate = vehicleControlHistoryRepository.findAll().size();

        // Update the vehicleControlHistory
        VehicleControlHistory updatedVehicleControlHistory = vehicleControlHistoryRepository.findById(vehicleControlHistory.getId()).get();
        // Disconnect from session so that the updates on updatedVehicleControlHistory are not directly saved in db
        em.detach(updatedVehicleControlHistory);
        updatedVehicleControlHistory
            .vehicleControlHistoryDate(UPDATED_VEHICLE_CONTROL_HISTORY_DATE)
            .vehicleControlHistoryDescription(UPDATED_VEHICLE_CONTROL_HISTORY_DESCRIPTION);
        VehicleControlHistoryDTO vehicleControlHistoryDTO = vehicleControlHistoryMapper.toDto(updatedVehicleControlHistory);

        restVehicleControlHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleControlHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the VehicleControlHistory in the database
        List<VehicleControlHistory> vehicleControlHistoryList = vehicleControlHistoryRepository.findAll();
        assertThat(vehicleControlHistoryList).hasSize(databaseSizeBeforeUpdate);
        VehicleControlHistory testVehicleControlHistory = vehicleControlHistoryList.get(vehicleControlHistoryList.size() - 1);
        assertThat(testVehicleControlHistory.getVehicleControlHistoryDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_HISTORY_DATE);
        assertThat(testVehicleControlHistory.getVehicleControlHistoryDescription()).isEqualTo(UPDATED_VEHICLE_CONTROL_HISTORY_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingVehicleControlHistory() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlHistoryRepository.findAll().size();
        vehicleControlHistory.setId(count.incrementAndGet());

        // Create the VehicleControlHistory
        VehicleControlHistoryDTO vehicleControlHistoryDTO = vehicleControlHistoryMapper.toDto(vehicleControlHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleControlHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleControlHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlHistory in the database
        List<VehicleControlHistory> vehicleControlHistoryList = vehicleControlHistoryRepository.findAll();
        assertThat(vehicleControlHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVehicleControlHistory() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlHistoryRepository.findAll().size();
        vehicleControlHistory.setId(count.incrementAndGet());

        // Create the VehicleControlHistory
        VehicleControlHistoryDTO vehicleControlHistoryDTO = vehicleControlHistoryMapper.toDto(vehicleControlHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlHistory in the database
        List<VehicleControlHistory> vehicleControlHistoryList = vehicleControlHistoryRepository.findAll();
        assertThat(vehicleControlHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVehicleControlHistory() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlHistoryRepository.findAll().size();
        vehicleControlHistory.setId(count.incrementAndGet());

        // Create the VehicleControlHistory
        VehicleControlHistoryDTO vehicleControlHistoryDTO = vehicleControlHistoryMapper.toDto(vehicleControlHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlHistoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleControlHistory in the database
        List<VehicleControlHistory> vehicleControlHistoryList = vehicleControlHistoryRepository.findAll();
        assertThat(vehicleControlHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVehicleControlHistoryWithPatch() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        int databaseSizeBeforeUpdate = vehicleControlHistoryRepository.findAll().size();

        // Update the vehicleControlHistory using partial update
        VehicleControlHistory partialUpdatedVehicleControlHistory = new VehicleControlHistory();
        partialUpdatedVehicleControlHistory.setId(vehicleControlHistory.getId());

        partialUpdatedVehicleControlHistory
            .vehicleControlHistoryDate(UPDATED_VEHICLE_CONTROL_HISTORY_DATE)
            .vehicleControlHistoryDescription(UPDATED_VEHICLE_CONTROL_HISTORY_DESCRIPTION);

        restVehicleControlHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleControlHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleControlHistory))
            )
            .andExpect(status().isOk());

        // Validate the VehicleControlHistory in the database
        List<VehicleControlHistory> vehicleControlHistoryList = vehicleControlHistoryRepository.findAll();
        assertThat(vehicleControlHistoryList).hasSize(databaseSizeBeforeUpdate);
        VehicleControlHistory testVehicleControlHistory = vehicleControlHistoryList.get(vehicleControlHistoryList.size() - 1);
        assertThat(testVehicleControlHistory.getVehicleControlHistoryDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_HISTORY_DATE);
        assertThat(testVehicleControlHistory.getVehicleControlHistoryDescription()).isEqualTo(UPDATED_VEHICLE_CONTROL_HISTORY_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateVehicleControlHistoryWithPatch() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        int databaseSizeBeforeUpdate = vehicleControlHistoryRepository.findAll().size();

        // Update the vehicleControlHistory using partial update
        VehicleControlHistory partialUpdatedVehicleControlHistory = new VehicleControlHistory();
        partialUpdatedVehicleControlHistory.setId(vehicleControlHistory.getId());

        partialUpdatedVehicleControlHistory
            .vehicleControlHistoryDate(UPDATED_VEHICLE_CONTROL_HISTORY_DATE)
            .vehicleControlHistoryDescription(UPDATED_VEHICLE_CONTROL_HISTORY_DESCRIPTION);

        restVehicleControlHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleControlHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleControlHistory))
            )
            .andExpect(status().isOk());

        // Validate the VehicleControlHistory in the database
        List<VehicleControlHistory> vehicleControlHistoryList = vehicleControlHistoryRepository.findAll();
        assertThat(vehicleControlHistoryList).hasSize(databaseSizeBeforeUpdate);
        VehicleControlHistory testVehicleControlHistory = vehicleControlHistoryList.get(vehicleControlHistoryList.size() - 1);
        assertThat(testVehicleControlHistory.getVehicleControlHistoryDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_HISTORY_DATE);
        assertThat(testVehicleControlHistory.getVehicleControlHistoryDescription()).isEqualTo(UPDATED_VEHICLE_CONTROL_HISTORY_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingVehicleControlHistory() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlHistoryRepository.findAll().size();
        vehicleControlHistory.setId(count.incrementAndGet());

        // Create the VehicleControlHistory
        VehicleControlHistoryDTO vehicleControlHistoryDTO = vehicleControlHistoryMapper.toDto(vehicleControlHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleControlHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vehicleControlHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlHistory in the database
        List<VehicleControlHistory> vehicleControlHistoryList = vehicleControlHistoryRepository.findAll();
        assertThat(vehicleControlHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVehicleControlHistory() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlHistoryRepository.findAll().size();
        vehicleControlHistory.setId(count.incrementAndGet());

        // Create the VehicleControlHistory
        VehicleControlHistoryDTO vehicleControlHistoryDTO = vehicleControlHistoryMapper.toDto(vehicleControlHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlHistory in the database
        List<VehicleControlHistory> vehicleControlHistoryList = vehicleControlHistoryRepository.findAll();
        assertThat(vehicleControlHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVehicleControlHistory() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlHistoryRepository.findAll().size();
        vehicleControlHistory.setId(count.incrementAndGet());

        // Create the VehicleControlHistory
        VehicleControlHistoryDTO vehicleControlHistoryDTO = vehicleControlHistoryMapper.toDto(vehicleControlHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleControlHistory in the database
        List<VehicleControlHistory> vehicleControlHistoryList = vehicleControlHistoryRepository.findAll();
        assertThat(vehicleControlHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVehicleControlHistory() throws Exception {
        // Initialize the database
        vehicleControlHistoryRepository.saveAndFlush(vehicleControlHistory);

        int databaseSizeBeforeDelete = vehicleControlHistoryRepository.findAll().size();

        // Delete the vehicleControlHistory
        restVehicleControlHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, vehicleControlHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehicleControlHistory> vehicleControlHistoryList = vehicleControlHistoryRepository.findAll();
        assertThat(vehicleControlHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
