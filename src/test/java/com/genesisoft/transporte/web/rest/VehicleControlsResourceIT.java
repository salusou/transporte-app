package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Affiliates;
import com.genesisoft.transporte.domain.Cities;
import com.genesisoft.transporte.domain.Customers;
import com.genesisoft.transporte.domain.CustomersGroups;
import com.genesisoft.transporte.domain.Employees;
import com.genesisoft.transporte.domain.Status;
import com.genesisoft.transporte.domain.VehicleControlAttachments;
import com.genesisoft.transporte.domain.VehicleControlBilling;
import com.genesisoft.transporte.domain.VehicleControlExpenses;
import com.genesisoft.transporte.domain.VehicleControlHistory;
import com.genesisoft.transporte.domain.VehicleControlItem;
import com.genesisoft.transporte.domain.VehicleControls;
import com.genesisoft.transporte.domain.VehicleLocationStatus;
import com.genesisoft.transporte.repository.VehicleControlsRepository;
import com.genesisoft.transporte.service.criteria.VehicleControlsCriteria;
import com.genesisoft.transporte.service.dto.VehicleControlsDTO;
import com.genesisoft.transporte.service.mapper.VehicleControlsMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link VehicleControlsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VehicleControlsResourceIT {

    private static final String DEFAULT_VEHICLE_CONTROL_AUTHORIZED_ORDER = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_CONTROL_AUTHORIZED_ORDER = "BBBBBBBBBB";

    private static final String DEFAULT_VEHICLE_CONTROL_REQUEST = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_CONTROL_REQUEST = "BBBBBBBBBB";

    private static final String DEFAULT_VEHICLE_CONTROL_SINISTER = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_CONTROL_SINISTER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VEHICLE_CONTROL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VEHICLE_CONTROL_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VEHICLE_CONTROL_DATE = LocalDate.ofEpochDay(-1L);

    private static final Float DEFAULT_VEHICLE_CONTROL_KM = 1F;
    private static final Float UPDATED_VEHICLE_CONTROL_KM = 2F;
    private static final Float SMALLER_VEHICLE_CONTROL_KM = 1F - 1F;

    private static final String DEFAULT_VEHICLE_CONTROL_PLATE = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_CONTROL_PLATE = "BBBBBBBBBB";

    private static final Float DEFAULT_VEHICLE_CONTROL_AMOUNT = 1F;
    private static final Float UPDATED_VEHICLE_CONTROL_AMOUNT = 2F;
    private static final Float SMALLER_VEHICLE_CONTROL_AMOUNT = 1F - 1F;

    private static final Float DEFAULT_VEHICLE_CONTROL_PRICE = 1F;
    private static final Float UPDATED_VEHICLE_CONTROL_PRICE = 2F;
    private static final Float SMALLER_VEHICLE_CONTROL_PRICE = 1F - 1F;

    private static final LocalDate DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VEHICLE_CONTROL_COLLECTION_FORECAST = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VEHICLE_CONTROL_COLLECTION_FORECAST = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VEHICLE_CONTROL_DATE_COLLECTED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VEHICLE_CONTROL_DATE_COLLECTED = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_VEHICLE_CONTROL_DELIVERY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VEHICLE_CONTROL_DELIVERY_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VEHICLE_CONTROL_DELIVERY_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/vehicle-controls";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VehicleControlsRepository vehicleControlsRepository;

    @Autowired
    private VehicleControlsMapper vehicleControlsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehicleControlsMockMvc;

    private VehicleControls vehicleControls;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleControls createEntity(EntityManager em) {
        VehicleControls vehicleControls = new VehicleControls()
            .vehicleControlAuthorizedOrder(DEFAULT_VEHICLE_CONTROL_AUTHORIZED_ORDER)
            .vehicleControlRequest(DEFAULT_VEHICLE_CONTROL_REQUEST)
            .vehicleControlSinister(DEFAULT_VEHICLE_CONTROL_SINISTER)
            .vehicleControlDate(DEFAULT_VEHICLE_CONTROL_DATE)
            .vehicleControlKm(DEFAULT_VEHICLE_CONTROL_KM)
            .vehicleControlPlate(DEFAULT_VEHICLE_CONTROL_PLATE)
            .vehicleControlAmount(DEFAULT_VEHICLE_CONTROL_AMOUNT)
            .vehicleControlPrice(DEFAULT_VEHICLE_CONTROL_PRICE)
            .vehicleControlMaximumDeliveryDate(DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE)
            .vehicleControlCollectionForecast(DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST)
            .vehicleControlCollectionDeliveryForecast(DEFAULT_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST)
            .vehicleControlDateCollected(DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED)
            .vehicleControlDeliveryDate(DEFAULT_VEHICLE_CONTROL_DELIVERY_DATE);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        vehicleControls.setAffiliates(affiliates);
        // Add required entity
        Customers customers;
        if (TestUtil.findAll(em, Customers.class).isEmpty()) {
            customers = CustomersResourceIT.createEntity(em);
            em.persist(customers);
            em.flush();
        } else {
            customers = TestUtil.findAll(em, Customers.class).get(0);
        }
        vehicleControls.setCustomers(customers);
        // Add required entity
        CustomersGroups customersGroups;
        if (TestUtil.findAll(em, CustomersGroups.class).isEmpty()) {
            customersGroups = CustomersGroupsResourceIT.createEntity(em);
            em.persist(customersGroups);
            em.flush();
        } else {
            customersGroups = TestUtil.findAll(em, CustomersGroups.class).get(0);
        }
        vehicleControls.setCustomersGroups(customersGroups);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        vehicleControls.setEmployees(employees);
        // Add required entity
        Cities cities;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            cities = CitiesResourceIT.createEntity(em);
            em.persist(cities);
            em.flush();
        } else {
            cities = TestUtil.findAll(em, Cities.class).get(0);
        }
        vehicleControls.setOrigin(cities);
        // Add required entity
        vehicleControls.setDestination(cities);
        // Add required entity
        Status status;
        if (TestUtil.findAll(em, Status.class).isEmpty()) {
            status = StatusResourceIT.createEntity(em);
            em.persist(status);
            em.flush();
        } else {
            status = TestUtil.findAll(em, Status.class).get(0);
        }
        vehicleControls.setStatus(status);
        return vehicleControls;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleControls createUpdatedEntity(EntityManager em) {
        VehicleControls vehicleControls = new VehicleControls()
            .vehicleControlAuthorizedOrder(UPDATED_VEHICLE_CONTROL_AUTHORIZED_ORDER)
            .vehicleControlRequest(UPDATED_VEHICLE_CONTROL_REQUEST)
            .vehicleControlSinister(UPDATED_VEHICLE_CONTROL_SINISTER)
            .vehicleControlDate(UPDATED_VEHICLE_CONTROL_DATE)
            .vehicleControlKm(UPDATED_VEHICLE_CONTROL_KM)
            .vehicleControlPlate(UPDATED_VEHICLE_CONTROL_PLATE)
            .vehicleControlAmount(UPDATED_VEHICLE_CONTROL_AMOUNT)
            .vehicleControlPrice(UPDATED_VEHICLE_CONTROL_PRICE)
            .vehicleControlMaximumDeliveryDate(UPDATED_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE)
            .vehicleControlCollectionForecast(UPDATED_VEHICLE_CONTROL_COLLECTION_FORECAST)
            .vehicleControlCollectionDeliveryForecast(UPDATED_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST)
            .vehicleControlDateCollected(UPDATED_VEHICLE_CONTROL_DATE_COLLECTED)
            .vehicleControlDeliveryDate(UPDATED_VEHICLE_CONTROL_DELIVERY_DATE);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createUpdatedEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        vehicleControls.setAffiliates(affiliates);
        // Add required entity
        Customers customers;
        if (TestUtil.findAll(em, Customers.class).isEmpty()) {
            customers = CustomersResourceIT.createUpdatedEntity(em);
            em.persist(customers);
            em.flush();
        } else {
            customers = TestUtil.findAll(em, Customers.class).get(0);
        }
        vehicleControls.setCustomers(customers);
        // Add required entity
        CustomersGroups customersGroups;
        if (TestUtil.findAll(em, CustomersGroups.class).isEmpty()) {
            customersGroups = CustomersGroupsResourceIT.createUpdatedEntity(em);
            em.persist(customersGroups);
            em.flush();
        } else {
            customersGroups = TestUtil.findAll(em, CustomersGroups.class).get(0);
        }
        vehicleControls.setCustomersGroups(customersGroups);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        vehicleControls.setEmployees(employees);
        // Add required entity
        Cities cities;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            cities = CitiesResourceIT.createUpdatedEntity(em);
            em.persist(cities);
            em.flush();
        } else {
            cities = TestUtil.findAll(em, Cities.class).get(0);
        }
        vehicleControls.setOrigin(cities);
        // Add required entity
        vehicleControls.setDestination(cities);
        // Add required entity
        Status status;
        if (TestUtil.findAll(em, Status.class).isEmpty()) {
            status = StatusResourceIT.createUpdatedEntity(em);
            em.persist(status);
            em.flush();
        } else {
            status = TestUtil.findAll(em, Status.class).get(0);
        }
        vehicleControls.setStatus(status);
        return vehicleControls;
    }

    @BeforeEach
    public void initTest() {
        vehicleControls = createEntity(em);
    }

    @Test
    @Transactional
    void createVehicleControls() throws Exception {
        int databaseSizeBeforeCreate = vehicleControlsRepository.findAll().size();
        // Create the VehicleControls
        VehicleControlsDTO vehicleControlsDTO = vehicleControlsMapper.toDto(vehicleControls);
        restVehicleControlsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleControlsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VehicleControls in the database
        List<VehicleControls> vehicleControlsList = vehicleControlsRepository.findAll();
        assertThat(vehicleControlsList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleControls testVehicleControls = vehicleControlsList.get(vehicleControlsList.size() - 1);
        assertThat(testVehicleControls.getVehicleControlAuthorizedOrder()).isEqualTo(DEFAULT_VEHICLE_CONTROL_AUTHORIZED_ORDER);
        assertThat(testVehicleControls.getVehicleControlRequest()).isEqualTo(DEFAULT_VEHICLE_CONTROL_REQUEST);
        assertThat(testVehicleControls.getVehicleControlSinister()).isEqualTo(DEFAULT_VEHICLE_CONTROL_SINISTER);
        assertThat(testVehicleControls.getVehicleControlDate()).isEqualTo(DEFAULT_VEHICLE_CONTROL_DATE);
        assertThat(testVehicleControls.getVehicleControlKm()).isEqualTo(DEFAULT_VEHICLE_CONTROL_KM);
        assertThat(testVehicleControls.getVehicleControlPlate()).isEqualTo(DEFAULT_VEHICLE_CONTROL_PLATE);
        assertThat(testVehicleControls.getVehicleControlAmount()).isEqualTo(DEFAULT_VEHICLE_CONTROL_AMOUNT);
        assertThat(testVehicleControls.getVehicleControlPrice()).isEqualTo(DEFAULT_VEHICLE_CONTROL_PRICE);
        assertThat(testVehicleControls.getVehicleControlMaximumDeliveryDate()).isEqualTo(DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE);
        assertThat(testVehicleControls.getVehicleControlCollectionForecast()).isEqualTo(DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST);
        assertThat(testVehicleControls.getVehicleControlCollectionDeliveryForecast())
            .isEqualTo(DEFAULT_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST);
        assertThat(testVehicleControls.getVehicleControlDateCollected()).isEqualTo(DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED);
        assertThat(testVehicleControls.getVehicleControlDeliveryDate()).isEqualTo(DEFAULT_VEHICLE_CONTROL_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void createVehicleControlsWithExistingId() throws Exception {
        // Create the VehicleControls with an existing ID
        vehicleControls.setId(1L);
        VehicleControlsDTO vehicleControlsDTO = vehicleControlsMapper.toDto(vehicleControls);

        int databaseSizeBeforeCreate = vehicleControlsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleControlsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleControlsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControls in the database
        List<VehicleControls> vehicleControlsList = vehicleControlsRepository.findAll();
        assertThat(vehicleControlsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVehicleControlDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleControlsRepository.findAll().size();
        // set the field null
        vehicleControls.setVehicleControlDate(null);

        // Create the VehicleControls, which fails.
        VehicleControlsDTO vehicleControlsDTO = vehicleControlsMapper.toDto(vehicleControls);

        restVehicleControlsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleControlsDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleControls> vehicleControlsList = vehicleControlsRepository.findAll();
        assertThat(vehicleControlsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVehicleControls() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList
        restVehicleControlsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleControls.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleControlAuthorizedOrder").value(hasItem(DEFAULT_VEHICLE_CONTROL_AUTHORIZED_ORDER)))
            .andExpect(jsonPath("$.[*].vehicleControlRequest").value(hasItem(DEFAULT_VEHICLE_CONTROL_REQUEST)))
            .andExpect(jsonPath("$.[*].vehicleControlSinister").value(hasItem(DEFAULT_VEHICLE_CONTROL_SINISTER)))
            .andExpect(jsonPath("$.[*].vehicleControlDate").value(hasItem(DEFAULT_VEHICLE_CONTROL_DATE.toString())))
            .andExpect(jsonPath("$.[*].vehicleControlKm").value(hasItem(DEFAULT_VEHICLE_CONTROL_KM.doubleValue())))
            .andExpect(jsonPath("$.[*].vehicleControlPlate").value(hasItem(DEFAULT_VEHICLE_CONTROL_PLATE)))
            .andExpect(jsonPath("$.[*].vehicleControlAmount").value(hasItem(DEFAULT_VEHICLE_CONTROL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].vehicleControlPrice").value(hasItem(DEFAULT_VEHICLE_CONTROL_PRICE.doubleValue())))
            .andExpect(
                jsonPath("$.[*].vehicleControlMaximumDeliveryDate").value(hasItem(DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE.toString()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleControlCollectionForecast").value(hasItem(DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST.toString()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleControlCollectionDeliveryForecast")
                    .value(hasItem(DEFAULT_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST.toString()))
            )
            .andExpect(jsonPath("$.[*].vehicleControlDateCollected").value(hasItem(DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED.toString())))
            .andExpect(jsonPath("$.[*].vehicleControlDeliveryDate").value(hasItem(DEFAULT_VEHICLE_CONTROL_DELIVERY_DATE.toString())));
    }

    @Test
    @Transactional
    void getVehicleControls() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get the vehicleControls
        restVehicleControlsMockMvc
            .perform(get(ENTITY_API_URL_ID, vehicleControls.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleControls.getId().intValue()))
            .andExpect(jsonPath("$.vehicleControlAuthorizedOrder").value(DEFAULT_VEHICLE_CONTROL_AUTHORIZED_ORDER))
            .andExpect(jsonPath("$.vehicleControlRequest").value(DEFAULT_VEHICLE_CONTROL_REQUEST))
            .andExpect(jsonPath("$.vehicleControlSinister").value(DEFAULT_VEHICLE_CONTROL_SINISTER))
            .andExpect(jsonPath("$.vehicleControlDate").value(DEFAULT_VEHICLE_CONTROL_DATE.toString()))
            .andExpect(jsonPath("$.vehicleControlKm").value(DEFAULT_VEHICLE_CONTROL_KM.doubleValue()))
            .andExpect(jsonPath("$.vehicleControlPlate").value(DEFAULT_VEHICLE_CONTROL_PLATE))
            .andExpect(jsonPath("$.vehicleControlAmount").value(DEFAULT_VEHICLE_CONTROL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.vehicleControlPrice").value(DEFAULT_VEHICLE_CONTROL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.vehicleControlMaximumDeliveryDate").value(DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.vehicleControlCollectionForecast").value(DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST.toString()))
            .andExpect(
                jsonPath("$.vehicleControlCollectionDeliveryForecast")
                    .value(DEFAULT_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST.toString())
            )
            .andExpect(jsonPath("$.vehicleControlDateCollected").value(DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED.toString()))
            .andExpect(jsonPath("$.vehicleControlDeliveryDate").value(DEFAULT_VEHICLE_CONTROL_DELIVERY_DATE.toString()));
    }

    @Test
    @Transactional
    void getVehicleControlsByIdFiltering() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        Long id = vehicleControls.getId();

        defaultVehicleControlsShouldBeFound("id.equals=" + id);
        defaultVehicleControlsShouldNotBeFound("id.notEquals=" + id);

        defaultVehicleControlsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVehicleControlsShouldNotBeFound("id.greaterThan=" + id);

        defaultVehicleControlsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVehicleControlsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlAuthorizedOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlAuthorizedOrder equals to DEFAULT_VEHICLE_CONTROL_AUTHORIZED_ORDER
        defaultVehicleControlsShouldBeFound("vehicleControlAuthorizedOrder.equals=" + DEFAULT_VEHICLE_CONTROL_AUTHORIZED_ORDER);

        // Get all the vehicleControlsList where vehicleControlAuthorizedOrder equals to UPDATED_VEHICLE_CONTROL_AUTHORIZED_ORDER
        defaultVehicleControlsShouldNotBeFound("vehicleControlAuthorizedOrder.equals=" + UPDATED_VEHICLE_CONTROL_AUTHORIZED_ORDER);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlAuthorizedOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlAuthorizedOrder not equals to DEFAULT_VEHICLE_CONTROL_AUTHORIZED_ORDER
        defaultVehicleControlsShouldNotBeFound("vehicleControlAuthorizedOrder.notEquals=" + DEFAULT_VEHICLE_CONTROL_AUTHORIZED_ORDER);

        // Get all the vehicleControlsList where vehicleControlAuthorizedOrder not equals to UPDATED_VEHICLE_CONTROL_AUTHORIZED_ORDER
        defaultVehicleControlsShouldBeFound("vehicleControlAuthorizedOrder.notEquals=" + UPDATED_VEHICLE_CONTROL_AUTHORIZED_ORDER);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlAuthorizedOrderIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlAuthorizedOrder in DEFAULT_VEHICLE_CONTROL_AUTHORIZED_ORDER or UPDATED_VEHICLE_CONTROL_AUTHORIZED_ORDER
        defaultVehicleControlsShouldBeFound(
            "vehicleControlAuthorizedOrder.in=" + DEFAULT_VEHICLE_CONTROL_AUTHORIZED_ORDER + "," + UPDATED_VEHICLE_CONTROL_AUTHORIZED_ORDER
        );

        // Get all the vehicleControlsList where vehicleControlAuthorizedOrder equals to UPDATED_VEHICLE_CONTROL_AUTHORIZED_ORDER
        defaultVehicleControlsShouldNotBeFound("vehicleControlAuthorizedOrder.in=" + UPDATED_VEHICLE_CONTROL_AUTHORIZED_ORDER);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlAuthorizedOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlAuthorizedOrder is not null
        defaultVehicleControlsShouldBeFound("vehicleControlAuthorizedOrder.specified=true");

        // Get all the vehicleControlsList where vehicleControlAuthorizedOrder is null
        defaultVehicleControlsShouldNotBeFound("vehicleControlAuthorizedOrder.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlAuthorizedOrderContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlAuthorizedOrder contains DEFAULT_VEHICLE_CONTROL_AUTHORIZED_ORDER
        defaultVehicleControlsShouldBeFound("vehicleControlAuthorizedOrder.contains=" + DEFAULT_VEHICLE_CONTROL_AUTHORIZED_ORDER);

        // Get all the vehicleControlsList where vehicleControlAuthorizedOrder contains UPDATED_VEHICLE_CONTROL_AUTHORIZED_ORDER
        defaultVehicleControlsShouldNotBeFound("vehicleControlAuthorizedOrder.contains=" + UPDATED_VEHICLE_CONTROL_AUTHORIZED_ORDER);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlAuthorizedOrderNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlAuthorizedOrder does not contain DEFAULT_VEHICLE_CONTROL_AUTHORIZED_ORDER
        defaultVehicleControlsShouldNotBeFound("vehicleControlAuthorizedOrder.doesNotContain=" + DEFAULT_VEHICLE_CONTROL_AUTHORIZED_ORDER);

        // Get all the vehicleControlsList where vehicleControlAuthorizedOrder does not contain UPDATED_VEHICLE_CONTROL_AUTHORIZED_ORDER
        defaultVehicleControlsShouldBeFound("vehicleControlAuthorizedOrder.doesNotContain=" + UPDATED_VEHICLE_CONTROL_AUTHORIZED_ORDER);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlRequestIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlRequest equals to DEFAULT_VEHICLE_CONTROL_REQUEST
        defaultVehicleControlsShouldBeFound("vehicleControlRequest.equals=" + DEFAULT_VEHICLE_CONTROL_REQUEST);

        // Get all the vehicleControlsList where vehicleControlRequest equals to UPDATED_VEHICLE_CONTROL_REQUEST
        defaultVehicleControlsShouldNotBeFound("vehicleControlRequest.equals=" + UPDATED_VEHICLE_CONTROL_REQUEST);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlRequestIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlRequest not equals to DEFAULT_VEHICLE_CONTROL_REQUEST
        defaultVehicleControlsShouldNotBeFound("vehicleControlRequest.notEquals=" + DEFAULT_VEHICLE_CONTROL_REQUEST);

        // Get all the vehicleControlsList where vehicleControlRequest not equals to UPDATED_VEHICLE_CONTROL_REQUEST
        defaultVehicleControlsShouldBeFound("vehicleControlRequest.notEquals=" + UPDATED_VEHICLE_CONTROL_REQUEST);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlRequestIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlRequest in DEFAULT_VEHICLE_CONTROL_REQUEST or UPDATED_VEHICLE_CONTROL_REQUEST
        defaultVehicleControlsShouldBeFound(
            "vehicleControlRequest.in=" + DEFAULT_VEHICLE_CONTROL_REQUEST + "," + UPDATED_VEHICLE_CONTROL_REQUEST
        );

        // Get all the vehicleControlsList where vehicleControlRequest equals to UPDATED_VEHICLE_CONTROL_REQUEST
        defaultVehicleControlsShouldNotBeFound("vehicleControlRequest.in=" + UPDATED_VEHICLE_CONTROL_REQUEST);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlRequestIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlRequest is not null
        defaultVehicleControlsShouldBeFound("vehicleControlRequest.specified=true");

        // Get all the vehicleControlsList where vehicleControlRequest is null
        defaultVehicleControlsShouldNotBeFound("vehicleControlRequest.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlRequestContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlRequest contains DEFAULT_VEHICLE_CONTROL_REQUEST
        defaultVehicleControlsShouldBeFound("vehicleControlRequest.contains=" + DEFAULT_VEHICLE_CONTROL_REQUEST);

        // Get all the vehicleControlsList where vehicleControlRequest contains UPDATED_VEHICLE_CONTROL_REQUEST
        defaultVehicleControlsShouldNotBeFound("vehicleControlRequest.contains=" + UPDATED_VEHICLE_CONTROL_REQUEST);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlRequestNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlRequest does not contain DEFAULT_VEHICLE_CONTROL_REQUEST
        defaultVehicleControlsShouldNotBeFound("vehicleControlRequest.doesNotContain=" + DEFAULT_VEHICLE_CONTROL_REQUEST);

        // Get all the vehicleControlsList where vehicleControlRequest does not contain UPDATED_VEHICLE_CONTROL_REQUEST
        defaultVehicleControlsShouldBeFound("vehicleControlRequest.doesNotContain=" + UPDATED_VEHICLE_CONTROL_REQUEST);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlSinisterIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlSinister equals to DEFAULT_VEHICLE_CONTROL_SINISTER
        defaultVehicleControlsShouldBeFound("vehicleControlSinister.equals=" + DEFAULT_VEHICLE_CONTROL_SINISTER);

        // Get all the vehicleControlsList where vehicleControlSinister equals to UPDATED_VEHICLE_CONTROL_SINISTER
        defaultVehicleControlsShouldNotBeFound("vehicleControlSinister.equals=" + UPDATED_VEHICLE_CONTROL_SINISTER);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlSinisterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlSinister not equals to DEFAULT_VEHICLE_CONTROL_SINISTER
        defaultVehicleControlsShouldNotBeFound("vehicleControlSinister.notEquals=" + DEFAULT_VEHICLE_CONTROL_SINISTER);

        // Get all the vehicleControlsList where vehicleControlSinister not equals to UPDATED_VEHICLE_CONTROL_SINISTER
        defaultVehicleControlsShouldBeFound("vehicleControlSinister.notEquals=" + UPDATED_VEHICLE_CONTROL_SINISTER);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlSinisterIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlSinister in DEFAULT_VEHICLE_CONTROL_SINISTER or UPDATED_VEHICLE_CONTROL_SINISTER
        defaultVehicleControlsShouldBeFound(
            "vehicleControlSinister.in=" + DEFAULT_VEHICLE_CONTROL_SINISTER + "," + UPDATED_VEHICLE_CONTROL_SINISTER
        );

        // Get all the vehicleControlsList where vehicleControlSinister equals to UPDATED_VEHICLE_CONTROL_SINISTER
        defaultVehicleControlsShouldNotBeFound("vehicleControlSinister.in=" + UPDATED_VEHICLE_CONTROL_SINISTER);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlSinisterIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlSinister is not null
        defaultVehicleControlsShouldBeFound("vehicleControlSinister.specified=true");

        // Get all the vehicleControlsList where vehicleControlSinister is null
        defaultVehicleControlsShouldNotBeFound("vehicleControlSinister.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlSinisterContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlSinister contains DEFAULT_VEHICLE_CONTROL_SINISTER
        defaultVehicleControlsShouldBeFound("vehicleControlSinister.contains=" + DEFAULT_VEHICLE_CONTROL_SINISTER);

        // Get all the vehicleControlsList where vehicleControlSinister contains UPDATED_VEHICLE_CONTROL_SINISTER
        defaultVehicleControlsShouldNotBeFound("vehicleControlSinister.contains=" + UPDATED_VEHICLE_CONTROL_SINISTER);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlSinisterNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlSinister does not contain DEFAULT_VEHICLE_CONTROL_SINISTER
        defaultVehicleControlsShouldNotBeFound("vehicleControlSinister.doesNotContain=" + DEFAULT_VEHICLE_CONTROL_SINISTER);

        // Get all the vehicleControlsList where vehicleControlSinister does not contain UPDATED_VEHICLE_CONTROL_SINISTER
        defaultVehicleControlsShouldBeFound("vehicleControlSinister.doesNotContain=" + UPDATED_VEHICLE_CONTROL_SINISTER);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDate equals to DEFAULT_VEHICLE_CONTROL_DATE
        defaultVehicleControlsShouldBeFound("vehicleControlDate.equals=" + DEFAULT_VEHICLE_CONTROL_DATE);

        // Get all the vehicleControlsList where vehicleControlDate equals to UPDATED_VEHICLE_CONTROL_DATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlDate.equals=" + UPDATED_VEHICLE_CONTROL_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDate not equals to DEFAULT_VEHICLE_CONTROL_DATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlDate.notEquals=" + DEFAULT_VEHICLE_CONTROL_DATE);

        // Get all the vehicleControlsList where vehicleControlDate not equals to UPDATED_VEHICLE_CONTROL_DATE
        defaultVehicleControlsShouldBeFound("vehicleControlDate.notEquals=" + UPDATED_VEHICLE_CONTROL_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDateIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDate in DEFAULT_VEHICLE_CONTROL_DATE or UPDATED_VEHICLE_CONTROL_DATE
        defaultVehicleControlsShouldBeFound("vehicleControlDate.in=" + DEFAULT_VEHICLE_CONTROL_DATE + "," + UPDATED_VEHICLE_CONTROL_DATE);

        // Get all the vehicleControlsList where vehicleControlDate equals to UPDATED_VEHICLE_CONTROL_DATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlDate.in=" + UPDATED_VEHICLE_CONTROL_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDate is not null
        defaultVehicleControlsShouldBeFound("vehicleControlDate.specified=true");

        // Get all the vehicleControlsList where vehicleControlDate is null
        defaultVehicleControlsShouldNotBeFound("vehicleControlDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDate is greater than or equal to DEFAULT_VEHICLE_CONTROL_DATE
        defaultVehicleControlsShouldBeFound("vehicleControlDate.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_DATE);

        // Get all the vehicleControlsList where vehicleControlDate is greater than or equal to UPDATED_VEHICLE_CONTROL_DATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlDate.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDate is less than or equal to DEFAULT_VEHICLE_CONTROL_DATE
        defaultVehicleControlsShouldBeFound("vehicleControlDate.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_DATE);

        // Get all the vehicleControlsList where vehicleControlDate is less than or equal to SMALLER_VEHICLE_CONTROL_DATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlDate.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDateIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDate is less than DEFAULT_VEHICLE_CONTROL_DATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlDate.lessThan=" + DEFAULT_VEHICLE_CONTROL_DATE);

        // Get all the vehicleControlsList where vehicleControlDate is less than UPDATED_VEHICLE_CONTROL_DATE
        defaultVehicleControlsShouldBeFound("vehicleControlDate.lessThan=" + UPDATED_VEHICLE_CONTROL_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDate is greater than DEFAULT_VEHICLE_CONTROL_DATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlDate.greaterThan=" + DEFAULT_VEHICLE_CONTROL_DATE);

        // Get all the vehicleControlsList where vehicleControlDate is greater than SMALLER_VEHICLE_CONTROL_DATE
        defaultVehicleControlsShouldBeFound("vehicleControlDate.greaterThan=" + SMALLER_VEHICLE_CONTROL_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlKmIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlKm equals to DEFAULT_VEHICLE_CONTROL_KM
        defaultVehicleControlsShouldBeFound("vehicleControlKm.equals=" + DEFAULT_VEHICLE_CONTROL_KM);

        // Get all the vehicleControlsList where vehicleControlKm equals to UPDATED_VEHICLE_CONTROL_KM
        defaultVehicleControlsShouldNotBeFound("vehicleControlKm.equals=" + UPDATED_VEHICLE_CONTROL_KM);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlKmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlKm not equals to DEFAULT_VEHICLE_CONTROL_KM
        defaultVehicleControlsShouldNotBeFound("vehicleControlKm.notEquals=" + DEFAULT_VEHICLE_CONTROL_KM);

        // Get all the vehicleControlsList where vehicleControlKm not equals to UPDATED_VEHICLE_CONTROL_KM
        defaultVehicleControlsShouldBeFound("vehicleControlKm.notEquals=" + UPDATED_VEHICLE_CONTROL_KM);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlKmIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlKm in DEFAULT_VEHICLE_CONTROL_KM or UPDATED_VEHICLE_CONTROL_KM
        defaultVehicleControlsShouldBeFound("vehicleControlKm.in=" + DEFAULT_VEHICLE_CONTROL_KM + "," + UPDATED_VEHICLE_CONTROL_KM);

        // Get all the vehicleControlsList where vehicleControlKm equals to UPDATED_VEHICLE_CONTROL_KM
        defaultVehicleControlsShouldNotBeFound("vehicleControlKm.in=" + UPDATED_VEHICLE_CONTROL_KM);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlKmIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlKm is not null
        defaultVehicleControlsShouldBeFound("vehicleControlKm.specified=true");

        // Get all the vehicleControlsList where vehicleControlKm is null
        defaultVehicleControlsShouldNotBeFound("vehicleControlKm.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlKmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlKm is greater than or equal to DEFAULT_VEHICLE_CONTROL_KM
        defaultVehicleControlsShouldBeFound("vehicleControlKm.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_KM);

        // Get all the vehicleControlsList where vehicleControlKm is greater than or equal to UPDATED_VEHICLE_CONTROL_KM
        defaultVehicleControlsShouldNotBeFound("vehicleControlKm.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_KM);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlKmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlKm is less than or equal to DEFAULT_VEHICLE_CONTROL_KM
        defaultVehicleControlsShouldBeFound("vehicleControlKm.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_KM);

        // Get all the vehicleControlsList where vehicleControlKm is less than or equal to SMALLER_VEHICLE_CONTROL_KM
        defaultVehicleControlsShouldNotBeFound("vehicleControlKm.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_KM);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlKmIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlKm is less than DEFAULT_VEHICLE_CONTROL_KM
        defaultVehicleControlsShouldNotBeFound("vehicleControlKm.lessThan=" + DEFAULT_VEHICLE_CONTROL_KM);

        // Get all the vehicleControlsList where vehicleControlKm is less than UPDATED_VEHICLE_CONTROL_KM
        defaultVehicleControlsShouldBeFound("vehicleControlKm.lessThan=" + UPDATED_VEHICLE_CONTROL_KM);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlKmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlKm is greater than DEFAULT_VEHICLE_CONTROL_KM
        defaultVehicleControlsShouldNotBeFound("vehicleControlKm.greaterThan=" + DEFAULT_VEHICLE_CONTROL_KM);

        // Get all the vehicleControlsList where vehicleControlKm is greater than SMALLER_VEHICLE_CONTROL_KM
        defaultVehicleControlsShouldBeFound("vehicleControlKm.greaterThan=" + SMALLER_VEHICLE_CONTROL_KM);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlPlateIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlPlate equals to DEFAULT_VEHICLE_CONTROL_PLATE
        defaultVehicleControlsShouldBeFound("vehicleControlPlate.equals=" + DEFAULT_VEHICLE_CONTROL_PLATE);

        // Get all the vehicleControlsList where vehicleControlPlate equals to UPDATED_VEHICLE_CONTROL_PLATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlPlate.equals=" + UPDATED_VEHICLE_CONTROL_PLATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlPlateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlPlate not equals to DEFAULT_VEHICLE_CONTROL_PLATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlPlate.notEquals=" + DEFAULT_VEHICLE_CONTROL_PLATE);

        // Get all the vehicleControlsList where vehicleControlPlate not equals to UPDATED_VEHICLE_CONTROL_PLATE
        defaultVehicleControlsShouldBeFound("vehicleControlPlate.notEquals=" + UPDATED_VEHICLE_CONTROL_PLATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlPlateIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlPlate in DEFAULT_VEHICLE_CONTROL_PLATE or UPDATED_VEHICLE_CONTROL_PLATE
        defaultVehicleControlsShouldBeFound(
            "vehicleControlPlate.in=" + DEFAULT_VEHICLE_CONTROL_PLATE + "," + UPDATED_VEHICLE_CONTROL_PLATE
        );

        // Get all the vehicleControlsList where vehicleControlPlate equals to UPDATED_VEHICLE_CONTROL_PLATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlPlate.in=" + UPDATED_VEHICLE_CONTROL_PLATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlPlateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlPlate is not null
        defaultVehicleControlsShouldBeFound("vehicleControlPlate.specified=true");

        // Get all the vehicleControlsList where vehicleControlPlate is null
        defaultVehicleControlsShouldNotBeFound("vehicleControlPlate.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlPlateContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlPlate contains DEFAULT_VEHICLE_CONTROL_PLATE
        defaultVehicleControlsShouldBeFound("vehicleControlPlate.contains=" + DEFAULT_VEHICLE_CONTROL_PLATE);

        // Get all the vehicleControlsList where vehicleControlPlate contains UPDATED_VEHICLE_CONTROL_PLATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlPlate.contains=" + UPDATED_VEHICLE_CONTROL_PLATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlPlateNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlPlate does not contain DEFAULT_VEHICLE_CONTROL_PLATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlPlate.doesNotContain=" + DEFAULT_VEHICLE_CONTROL_PLATE);

        // Get all the vehicleControlsList where vehicleControlPlate does not contain UPDATED_VEHICLE_CONTROL_PLATE
        defaultVehicleControlsShouldBeFound("vehicleControlPlate.doesNotContain=" + UPDATED_VEHICLE_CONTROL_PLATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlAmount equals to DEFAULT_VEHICLE_CONTROL_AMOUNT
        defaultVehicleControlsShouldBeFound("vehicleControlAmount.equals=" + DEFAULT_VEHICLE_CONTROL_AMOUNT);

        // Get all the vehicleControlsList where vehicleControlAmount equals to UPDATED_VEHICLE_CONTROL_AMOUNT
        defaultVehicleControlsShouldNotBeFound("vehicleControlAmount.equals=" + UPDATED_VEHICLE_CONTROL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlAmount not equals to DEFAULT_VEHICLE_CONTROL_AMOUNT
        defaultVehicleControlsShouldNotBeFound("vehicleControlAmount.notEquals=" + DEFAULT_VEHICLE_CONTROL_AMOUNT);

        // Get all the vehicleControlsList where vehicleControlAmount not equals to UPDATED_VEHICLE_CONTROL_AMOUNT
        defaultVehicleControlsShouldBeFound("vehicleControlAmount.notEquals=" + UPDATED_VEHICLE_CONTROL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlAmountIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlAmount in DEFAULT_VEHICLE_CONTROL_AMOUNT or UPDATED_VEHICLE_CONTROL_AMOUNT
        defaultVehicleControlsShouldBeFound(
            "vehicleControlAmount.in=" + DEFAULT_VEHICLE_CONTROL_AMOUNT + "," + UPDATED_VEHICLE_CONTROL_AMOUNT
        );

        // Get all the vehicleControlsList where vehicleControlAmount equals to UPDATED_VEHICLE_CONTROL_AMOUNT
        defaultVehicleControlsShouldNotBeFound("vehicleControlAmount.in=" + UPDATED_VEHICLE_CONTROL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlAmount is not null
        defaultVehicleControlsShouldBeFound("vehicleControlAmount.specified=true");

        // Get all the vehicleControlsList where vehicleControlAmount is null
        defaultVehicleControlsShouldNotBeFound("vehicleControlAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlAmount is greater than or equal to DEFAULT_VEHICLE_CONTROL_AMOUNT
        defaultVehicleControlsShouldBeFound("vehicleControlAmount.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_AMOUNT);

        // Get all the vehicleControlsList where vehicleControlAmount is greater than or equal to UPDATED_VEHICLE_CONTROL_AMOUNT
        defaultVehicleControlsShouldNotBeFound("vehicleControlAmount.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlAmount is less than or equal to DEFAULT_VEHICLE_CONTROL_AMOUNT
        defaultVehicleControlsShouldBeFound("vehicleControlAmount.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_AMOUNT);

        // Get all the vehicleControlsList where vehicleControlAmount is less than or equal to SMALLER_VEHICLE_CONTROL_AMOUNT
        defaultVehicleControlsShouldNotBeFound("vehicleControlAmount.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlAmount is less than DEFAULT_VEHICLE_CONTROL_AMOUNT
        defaultVehicleControlsShouldNotBeFound("vehicleControlAmount.lessThan=" + DEFAULT_VEHICLE_CONTROL_AMOUNT);

        // Get all the vehicleControlsList where vehicleControlAmount is less than UPDATED_VEHICLE_CONTROL_AMOUNT
        defaultVehicleControlsShouldBeFound("vehicleControlAmount.lessThan=" + UPDATED_VEHICLE_CONTROL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlAmount is greater than DEFAULT_VEHICLE_CONTROL_AMOUNT
        defaultVehicleControlsShouldNotBeFound("vehicleControlAmount.greaterThan=" + DEFAULT_VEHICLE_CONTROL_AMOUNT);

        // Get all the vehicleControlsList where vehicleControlAmount is greater than SMALLER_VEHICLE_CONTROL_AMOUNT
        defaultVehicleControlsShouldBeFound("vehicleControlAmount.greaterThan=" + SMALLER_VEHICLE_CONTROL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlPrice equals to DEFAULT_VEHICLE_CONTROL_PRICE
        defaultVehicleControlsShouldBeFound("vehicleControlPrice.equals=" + DEFAULT_VEHICLE_CONTROL_PRICE);

        // Get all the vehicleControlsList where vehicleControlPrice equals to UPDATED_VEHICLE_CONTROL_PRICE
        defaultVehicleControlsShouldNotBeFound("vehicleControlPrice.equals=" + UPDATED_VEHICLE_CONTROL_PRICE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlPrice not equals to DEFAULT_VEHICLE_CONTROL_PRICE
        defaultVehicleControlsShouldNotBeFound("vehicleControlPrice.notEquals=" + DEFAULT_VEHICLE_CONTROL_PRICE);

        // Get all the vehicleControlsList where vehicleControlPrice not equals to UPDATED_VEHICLE_CONTROL_PRICE
        defaultVehicleControlsShouldBeFound("vehicleControlPrice.notEquals=" + UPDATED_VEHICLE_CONTROL_PRICE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlPriceIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlPrice in DEFAULT_VEHICLE_CONTROL_PRICE or UPDATED_VEHICLE_CONTROL_PRICE
        defaultVehicleControlsShouldBeFound(
            "vehicleControlPrice.in=" + DEFAULT_VEHICLE_CONTROL_PRICE + "," + UPDATED_VEHICLE_CONTROL_PRICE
        );

        // Get all the vehicleControlsList where vehicleControlPrice equals to UPDATED_VEHICLE_CONTROL_PRICE
        defaultVehicleControlsShouldNotBeFound("vehicleControlPrice.in=" + UPDATED_VEHICLE_CONTROL_PRICE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlPrice is not null
        defaultVehicleControlsShouldBeFound("vehicleControlPrice.specified=true");

        // Get all the vehicleControlsList where vehicleControlPrice is null
        defaultVehicleControlsShouldNotBeFound("vehicleControlPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlPrice is greater than or equal to DEFAULT_VEHICLE_CONTROL_PRICE
        defaultVehicleControlsShouldBeFound("vehicleControlPrice.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_PRICE);

        // Get all the vehicleControlsList where vehicleControlPrice is greater than or equal to UPDATED_VEHICLE_CONTROL_PRICE
        defaultVehicleControlsShouldNotBeFound("vehicleControlPrice.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_PRICE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlPrice is less than or equal to DEFAULT_VEHICLE_CONTROL_PRICE
        defaultVehicleControlsShouldBeFound("vehicleControlPrice.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_PRICE);

        // Get all the vehicleControlsList where vehicleControlPrice is less than or equal to SMALLER_VEHICLE_CONTROL_PRICE
        defaultVehicleControlsShouldNotBeFound("vehicleControlPrice.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_PRICE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlPrice is less than DEFAULT_VEHICLE_CONTROL_PRICE
        defaultVehicleControlsShouldNotBeFound("vehicleControlPrice.lessThan=" + DEFAULT_VEHICLE_CONTROL_PRICE);

        // Get all the vehicleControlsList where vehicleControlPrice is less than UPDATED_VEHICLE_CONTROL_PRICE
        defaultVehicleControlsShouldBeFound("vehicleControlPrice.lessThan=" + UPDATED_VEHICLE_CONTROL_PRICE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlPrice is greater than DEFAULT_VEHICLE_CONTROL_PRICE
        defaultVehicleControlsShouldNotBeFound("vehicleControlPrice.greaterThan=" + DEFAULT_VEHICLE_CONTROL_PRICE);

        // Get all the vehicleControlsList where vehicleControlPrice is greater than SMALLER_VEHICLE_CONTROL_PRICE
        defaultVehicleControlsShouldBeFound("vehicleControlPrice.greaterThan=" + SMALLER_VEHICLE_CONTROL_PRICE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlMaximumDeliveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlMaximumDeliveryDate equals to DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        defaultVehicleControlsShouldBeFound("vehicleControlMaximumDeliveryDate.equals=" + DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE);

        // Get all the vehicleControlsList where vehicleControlMaximumDeliveryDate equals to UPDATED_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlMaximumDeliveryDate.equals=" + UPDATED_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlMaximumDeliveryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlMaximumDeliveryDate not equals to DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        defaultVehicleControlsShouldNotBeFound(
            "vehicleControlMaximumDeliveryDate.notEquals=" + DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        );

        // Get all the vehicleControlsList where vehicleControlMaximumDeliveryDate not equals to UPDATED_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        defaultVehicleControlsShouldBeFound("vehicleControlMaximumDeliveryDate.notEquals=" + UPDATED_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlMaximumDeliveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlMaximumDeliveryDate in DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE or UPDATED_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        defaultVehicleControlsShouldBeFound(
            "vehicleControlMaximumDeliveryDate.in=" +
            DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE +
            "," +
            UPDATED_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        );

        // Get all the vehicleControlsList where vehicleControlMaximumDeliveryDate equals to UPDATED_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlMaximumDeliveryDate.in=" + UPDATED_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlMaximumDeliveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlMaximumDeliveryDate is not null
        defaultVehicleControlsShouldBeFound("vehicleControlMaximumDeliveryDate.specified=true");

        // Get all the vehicleControlsList where vehicleControlMaximumDeliveryDate is null
        defaultVehicleControlsShouldNotBeFound("vehicleControlMaximumDeliveryDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlMaximumDeliveryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlMaximumDeliveryDate is greater than or equal to DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        defaultVehicleControlsShouldBeFound(
            "vehicleControlMaximumDeliveryDate.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        );

        // Get all the vehicleControlsList where vehicleControlMaximumDeliveryDate is greater than or equal to UPDATED_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        defaultVehicleControlsShouldNotBeFound(
            "vehicleControlMaximumDeliveryDate.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlMaximumDeliveryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlMaximumDeliveryDate is less than or equal to DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        defaultVehicleControlsShouldBeFound(
            "vehicleControlMaximumDeliveryDate.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        );

        // Get all the vehicleControlsList where vehicleControlMaximumDeliveryDate is less than or equal to SMALLER_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        defaultVehicleControlsShouldNotBeFound(
            "vehicleControlMaximumDeliveryDate.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlMaximumDeliveryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlMaximumDeliveryDate is less than DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        defaultVehicleControlsShouldNotBeFound(
            "vehicleControlMaximumDeliveryDate.lessThan=" + DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        );

        // Get all the vehicleControlsList where vehicleControlMaximumDeliveryDate is less than UPDATED_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        defaultVehicleControlsShouldBeFound("vehicleControlMaximumDeliveryDate.lessThan=" + UPDATED_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlMaximumDeliveryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlMaximumDeliveryDate is greater than DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        defaultVehicleControlsShouldNotBeFound(
            "vehicleControlMaximumDeliveryDate.greaterThan=" + DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        );

        // Get all the vehicleControlsList where vehicleControlMaximumDeliveryDate is greater than SMALLER_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        defaultVehicleControlsShouldBeFound(
            "vehicleControlMaximumDeliveryDate.greaterThan=" + SMALLER_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlCollectionForecastIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlCollectionForecast equals to DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST
        defaultVehicleControlsShouldBeFound("vehicleControlCollectionForecast.equals=" + DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST);

        // Get all the vehicleControlsList where vehicleControlCollectionForecast equals to UPDATED_VEHICLE_CONTROL_COLLECTION_FORECAST
        defaultVehicleControlsShouldNotBeFound("vehicleControlCollectionForecast.equals=" + UPDATED_VEHICLE_CONTROL_COLLECTION_FORECAST);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlCollectionForecastIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlCollectionForecast not equals to DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST
        defaultVehicleControlsShouldNotBeFound("vehicleControlCollectionForecast.notEquals=" + DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST);

        // Get all the vehicleControlsList where vehicleControlCollectionForecast not equals to UPDATED_VEHICLE_CONTROL_COLLECTION_FORECAST
        defaultVehicleControlsShouldBeFound("vehicleControlCollectionForecast.notEquals=" + UPDATED_VEHICLE_CONTROL_COLLECTION_FORECAST);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlCollectionForecastIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlCollectionForecast in DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST or UPDATED_VEHICLE_CONTROL_COLLECTION_FORECAST
        defaultVehicleControlsShouldBeFound(
            "vehicleControlCollectionForecast.in=" +
            DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST +
            "," +
            UPDATED_VEHICLE_CONTROL_COLLECTION_FORECAST
        );

        // Get all the vehicleControlsList where vehicleControlCollectionForecast equals to UPDATED_VEHICLE_CONTROL_COLLECTION_FORECAST
        defaultVehicleControlsShouldNotBeFound("vehicleControlCollectionForecast.in=" + UPDATED_VEHICLE_CONTROL_COLLECTION_FORECAST);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlCollectionForecastIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlCollectionForecast is not null
        defaultVehicleControlsShouldBeFound("vehicleControlCollectionForecast.specified=true");

        // Get all the vehicleControlsList where vehicleControlCollectionForecast is null
        defaultVehicleControlsShouldNotBeFound("vehicleControlCollectionForecast.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlCollectionForecastIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlCollectionForecast is greater than or equal to DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST
        defaultVehicleControlsShouldBeFound(
            "vehicleControlCollectionForecast.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST
        );

        // Get all the vehicleControlsList where vehicleControlCollectionForecast is greater than or equal to UPDATED_VEHICLE_CONTROL_COLLECTION_FORECAST
        defaultVehicleControlsShouldNotBeFound(
            "vehicleControlCollectionForecast.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_COLLECTION_FORECAST
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlCollectionForecastIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlCollectionForecast is less than or equal to DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST
        defaultVehicleControlsShouldBeFound(
            "vehicleControlCollectionForecast.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST
        );

        // Get all the vehicleControlsList where vehicleControlCollectionForecast is less than or equal to SMALLER_VEHICLE_CONTROL_COLLECTION_FORECAST
        defaultVehicleControlsShouldNotBeFound(
            "vehicleControlCollectionForecast.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_COLLECTION_FORECAST
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlCollectionForecastIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlCollectionForecast is less than DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST
        defaultVehicleControlsShouldNotBeFound("vehicleControlCollectionForecast.lessThan=" + DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST);

        // Get all the vehicleControlsList where vehicleControlCollectionForecast is less than UPDATED_VEHICLE_CONTROL_COLLECTION_FORECAST
        defaultVehicleControlsShouldBeFound("vehicleControlCollectionForecast.lessThan=" + UPDATED_VEHICLE_CONTROL_COLLECTION_FORECAST);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlCollectionForecastIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlCollectionForecast is greater than DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST
        defaultVehicleControlsShouldNotBeFound(
            "vehicleControlCollectionForecast.greaterThan=" + DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST
        );

        // Get all the vehicleControlsList where vehicleControlCollectionForecast is greater than SMALLER_VEHICLE_CONTROL_COLLECTION_FORECAST
        defaultVehicleControlsShouldBeFound("vehicleControlCollectionForecast.greaterThan=" + SMALLER_VEHICLE_CONTROL_COLLECTION_FORECAST);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlCollectionDeliveryForecastIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlCollectionDeliveryForecast equals to DEFAULT_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        defaultVehicleControlsShouldBeFound(
            "vehicleControlCollectionDeliveryForecast.equals=" + DEFAULT_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        );

        // Get all the vehicleControlsList where vehicleControlCollectionDeliveryForecast equals to UPDATED_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        defaultVehicleControlsShouldNotBeFound(
            "vehicleControlCollectionDeliveryForecast.equals=" + UPDATED_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlCollectionDeliveryForecastIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlCollectionDeliveryForecast not equals to DEFAULT_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        defaultVehicleControlsShouldNotBeFound(
            "vehicleControlCollectionDeliveryForecast.notEquals=" + DEFAULT_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        );

        // Get all the vehicleControlsList where vehicleControlCollectionDeliveryForecast not equals to UPDATED_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        defaultVehicleControlsShouldBeFound(
            "vehicleControlCollectionDeliveryForecast.notEquals=" + UPDATED_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlCollectionDeliveryForecastIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlCollectionDeliveryForecast in DEFAULT_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST or UPDATED_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        defaultVehicleControlsShouldBeFound(
            "vehicleControlCollectionDeliveryForecast.in=" +
            DEFAULT_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST +
            "," +
            UPDATED_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        );

        // Get all the vehicleControlsList where vehicleControlCollectionDeliveryForecast equals to UPDATED_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        defaultVehicleControlsShouldNotBeFound(
            "vehicleControlCollectionDeliveryForecast.in=" + UPDATED_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlCollectionDeliveryForecastIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlCollectionDeliveryForecast is not null
        defaultVehicleControlsShouldBeFound("vehicleControlCollectionDeliveryForecast.specified=true");

        // Get all the vehicleControlsList where vehicleControlCollectionDeliveryForecast is null
        defaultVehicleControlsShouldNotBeFound("vehicleControlCollectionDeliveryForecast.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlCollectionDeliveryForecastIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlCollectionDeliveryForecast is greater than or equal to DEFAULT_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        defaultVehicleControlsShouldBeFound(
            "vehicleControlCollectionDeliveryForecast.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        );

        // Get all the vehicleControlsList where vehicleControlCollectionDeliveryForecast is greater than or equal to UPDATED_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        defaultVehicleControlsShouldNotBeFound(
            "vehicleControlCollectionDeliveryForecast.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlCollectionDeliveryForecastIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlCollectionDeliveryForecast is less than or equal to DEFAULT_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        defaultVehicleControlsShouldBeFound(
            "vehicleControlCollectionDeliveryForecast.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        );

        // Get all the vehicleControlsList where vehicleControlCollectionDeliveryForecast is less than or equal to SMALLER_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        defaultVehicleControlsShouldNotBeFound(
            "vehicleControlCollectionDeliveryForecast.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlCollectionDeliveryForecastIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlCollectionDeliveryForecast is less than DEFAULT_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        defaultVehicleControlsShouldNotBeFound(
            "vehicleControlCollectionDeliveryForecast.lessThan=" + DEFAULT_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        );

        // Get all the vehicleControlsList where vehicleControlCollectionDeliveryForecast is less than UPDATED_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        defaultVehicleControlsShouldBeFound(
            "vehicleControlCollectionDeliveryForecast.lessThan=" + UPDATED_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlCollectionDeliveryForecastIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlCollectionDeliveryForecast is greater than DEFAULT_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        defaultVehicleControlsShouldNotBeFound(
            "vehicleControlCollectionDeliveryForecast.greaterThan=" + DEFAULT_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        );

        // Get all the vehicleControlsList where vehicleControlCollectionDeliveryForecast is greater than SMALLER_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        defaultVehicleControlsShouldBeFound(
            "vehicleControlCollectionDeliveryForecast.greaterThan=" + SMALLER_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDateCollectedIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDateCollected equals to DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED
        defaultVehicleControlsShouldBeFound("vehicleControlDateCollected.equals=" + DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED);

        // Get all the vehicleControlsList where vehicleControlDateCollected equals to UPDATED_VEHICLE_CONTROL_DATE_COLLECTED
        defaultVehicleControlsShouldNotBeFound("vehicleControlDateCollected.equals=" + UPDATED_VEHICLE_CONTROL_DATE_COLLECTED);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDateCollectedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDateCollected not equals to DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED
        defaultVehicleControlsShouldNotBeFound("vehicleControlDateCollected.notEquals=" + DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED);

        // Get all the vehicleControlsList where vehicleControlDateCollected not equals to UPDATED_VEHICLE_CONTROL_DATE_COLLECTED
        defaultVehicleControlsShouldBeFound("vehicleControlDateCollected.notEquals=" + UPDATED_VEHICLE_CONTROL_DATE_COLLECTED);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDateCollectedIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDateCollected in DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED or UPDATED_VEHICLE_CONTROL_DATE_COLLECTED
        defaultVehicleControlsShouldBeFound(
            "vehicleControlDateCollected.in=" + DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED + "," + UPDATED_VEHICLE_CONTROL_DATE_COLLECTED
        );

        // Get all the vehicleControlsList where vehicleControlDateCollected equals to UPDATED_VEHICLE_CONTROL_DATE_COLLECTED
        defaultVehicleControlsShouldNotBeFound("vehicleControlDateCollected.in=" + UPDATED_VEHICLE_CONTROL_DATE_COLLECTED);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDateCollectedIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDateCollected is not null
        defaultVehicleControlsShouldBeFound("vehicleControlDateCollected.specified=true");

        // Get all the vehicleControlsList where vehicleControlDateCollected is null
        defaultVehicleControlsShouldNotBeFound("vehicleControlDateCollected.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDateCollectedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDateCollected is greater than or equal to DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED
        defaultVehicleControlsShouldBeFound("vehicleControlDateCollected.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED);

        // Get all the vehicleControlsList where vehicleControlDateCollected is greater than or equal to UPDATED_VEHICLE_CONTROL_DATE_COLLECTED
        defaultVehicleControlsShouldNotBeFound("vehicleControlDateCollected.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_DATE_COLLECTED);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDateCollectedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDateCollected is less than or equal to DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED
        defaultVehicleControlsShouldBeFound("vehicleControlDateCollected.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED);

        // Get all the vehicleControlsList where vehicleControlDateCollected is less than or equal to SMALLER_VEHICLE_CONTROL_DATE_COLLECTED
        defaultVehicleControlsShouldNotBeFound("vehicleControlDateCollected.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_DATE_COLLECTED);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDateCollectedIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDateCollected is less than DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED
        defaultVehicleControlsShouldNotBeFound("vehicleControlDateCollected.lessThan=" + DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED);

        // Get all the vehicleControlsList where vehicleControlDateCollected is less than UPDATED_VEHICLE_CONTROL_DATE_COLLECTED
        defaultVehicleControlsShouldBeFound("vehicleControlDateCollected.lessThan=" + UPDATED_VEHICLE_CONTROL_DATE_COLLECTED);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDateCollectedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDateCollected is greater than DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED
        defaultVehicleControlsShouldNotBeFound("vehicleControlDateCollected.greaterThan=" + DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED);

        // Get all the vehicleControlsList where vehicleControlDateCollected is greater than SMALLER_VEHICLE_CONTROL_DATE_COLLECTED
        defaultVehicleControlsShouldBeFound("vehicleControlDateCollected.greaterThan=" + SMALLER_VEHICLE_CONTROL_DATE_COLLECTED);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDeliveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDeliveryDate equals to DEFAULT_VEHICLE_CONTROL_DELIVERY_DATE
        defaultVehicleControlsShouldBeFound("vehicleControlDeliveryDate.equals=" + DEFAULT_VEHICLE_CONTROL_DELIVERY_DATE);

        // Get all the vehicleControlsList where vehicleControlDeliveryDate equals to UPDATED_VEHICLE_CONTROL_DELIVERY_DATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlDeliveryDate.equals=" + UPDATED_VEHICLE_CONTROL_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDeliveryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDeliveryDate not equals to DEFAULT_VEHICLE_CONTROL_DELIVERY_DATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlDeliveryDate.notEquals=" + DEFAULT_VEHICLE_CONTROL_DELIVERY_DATE);

        // Get all the vehicleControlsList where vehicleControlDeliveryDate not equals to UPDATED_VEHICLE_CONTROL_DELIVERY_DATE
        defaultVehicleControlsShouldBeFound("vehicleControlDeliveryDate.notEquals=" + UPDATED_VEHICLE_CONTROL_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDeliveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDeliveryDate in DEFAULT_VEHICLE_CONTROL_DELIVERY_DATE or UPDATED_VEHICLE_CONTROL_DELIVERY_DATE
        defaultVehicleControlsShouldBeFound(
            "vehicleControlDeliveryDate.in=" + DEFAULT_VEHICLE_CONTROL_DELIVERY_DATE + "," + UPDATED_VEHICLE_CONTROL_DELIVERY_DATE
        );

        // Get all the vehicleControlsList where vehicleControlDeliveryDate equals to UPDATED_VEHICLE_CONTROL_DELIVERY_DATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlDeliveryDate.in=" + UPDATED_VEHICLE_CONTROL_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDeliveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDeliveryDate is not null
        defaultVehicleControlsShouldBeFound("vehicleControlDeliveryDate.specified=true");

        // Get all the vehicleControlsList where vehicleControlDeliveryDate is null
        defaultVehicleControlsShouldNotBeFound("vehicleControlDeliveryDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDeliveryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDeliveryDate is greater than or equal to DEFAULT_VEHICLE_CONTROL_DELIVERY_DATE
        defaultVehicleControlsShouldBeFound("vehicleControlDeliveryDate.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_DELIVERY_DATE);

        // Get all the vehicleControlsList where vehicleControlDeliveryDate is greater than or equal to UPDATED_VEHICLE_CONTROL_DELIVERY_DATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlDeliveryDate.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDeliveryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDeliveryDate is less than or equal to DEFAULT_VEHICLE_CONTROL_DELIVERY_DATE
        defaultVehicleControlsShouldBeFound("vehicleControlDeliveryDate.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_DELIVERY_DATE);

        // Get all the vehicleControlsList where vehicleControlDeliveryDate is less than or equal to SMALLER_VEHICLE_CONTROL_DELIVERY_DATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlDeliveryDate.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDeliveryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDeliveryDate is less than DEFAULT_VEHICLE_CONTROL_DELIVERY_DATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlDeliveryDate.lessThan=" + DEFAULT_VEHICLE_CONTROL_DELIVERY_DATE);

        // Get all the vehicleControlsList where vehicleControlDeliveryDate is less than UPDATED_VEHICLE_CONTROL_DELIVERY_DATE
        defaultVehicleControlsShouldBeFound("vehicleControlDeliveryDate.lessThan=" + UPDATED_VEHICLE_CONTROL_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlDeliveryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        // Get all the vehicleControlsList where vehicleControlDeliveryDate is greater than DEFAULT_VEHICLE_CONTROL_DELIVERY_DATE
        defaultVehicleControlsShouldNotBeFound("vehicleControlDeliveryDate.greaterThan=" + DEFAULT_VEHICLE_CONTROL_DELIVERY_DATE);

        // Get all the vehicleControlsList where vehicleControlDeliveryDate is greater than SMALLER_VEHICLE_CONTROL_DELIVERY_DATE
        defaultVehicleControlsShouldBeFound("vehicleControlDeliveryDate.greaterThan=" + SMALLER_VEHICLE_CONTROL_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleLocationStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        VehicleLocationStatus vehicleLocationStatus = VehicleLocationStatusResourceIT.createEntity(em);
        em.persist(vehicleLocationStatus);
        em.flush();
        vehicleControls.addVehicleLocationStatus(vehicleLocationStatus);
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        Long vehicleLocationStatusId = vehicleLocationStatus.getId();

        // Get all the vehicleControlsList where vehicleLocationStatus equals to vehicleLocationStatusId
        defaultVehicleControlsShouldBeFound("vehicleLocationStatusId.equals=" + vehicleLocationStatusId);

        // Get all the vehicleControlsList where vehicleLocationStatus equals to (vehicleLocationStatusId + 1)
        defaultVehicleControlsShouldNotBeFound("vehicleLocationStatusId.equals=" + (vehicleLocationStatusId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlHistoryIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        VehicleControlHistory vehicleControlHistory = VehicleControlHistoryResourceIT.createEntity(em);
        em.persist(vehicleControlHistory);
        em.flush();
        vehicleControls.addVehicleControlHistory(vehicleControlHistory);
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        Long vehicleControlHistoryId = vehicleControlHistory.getId();

        // Get all the vehicleControlsList where vehicleControlHistory equals to vehicleControlHistoryId
        defaultVehicleControlsShouldBeFound("vehicleControlHistoryId.equals=" + vehicleControlHistoryId);

        // Get all the vehicleControlsList where vehicleControlHistory equals to (vehicleControlHistoryId + 1)
        defaultVehicleControlsShouldNotBeFound("vehicleControlHistoryId.equals=" + (vehicleControlHistoryId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlBillingIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        VehicleControlBilling vehicleControlBilling = VehicleControlBillingResourceIT.createEntity(em);
        em.persist(vehicleControlBilling);
        em.flush();
        vehicleControls.addVehicleControlBilling(vehicleControlBilling);
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        Long vehicleControlBillingId = vehicleControlBilling.getId();

        // Get all the vehicleControlsList where vehicleControlBilling equals to vehicleControlBillingId
        defaultVehicleControlsShouldBeFound("vehicleControlBillingId.equals=" + vehicleControlBillingId);

        // Get all the vehicleControlsList where vehicleControlBilling equals to (vehicleControlBillingId + 1)
        defaultVehicleControlsShouldNotBeFound("vehicleControlBillingId.equals=" + (vehicleControlBillingId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlItemIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        VehicleControlItem vehicleControlItem = VehicleControlItemResourceIT.createEntity(em);
        em.persist(vehicleControlItem);
        em.flush();
        vehicleControls.addVehicleControlItem(vehicleControlItem);
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        Long vehicleControlItemId = vehicleControlItem.getId();

        // Get all the vehicleControlsList where vehicleControlItem equals to vehicleControlItemId
        defaultVehicleControlsShouldBeFound("vehicleControlItemId.equals=" + vehicleControlItemId);

        // Get all the vehicleControlsList where vehicleControlItem equals to (vehicleControlItemId + 1)
        defaultVehicleControlsShouldNotBeFound("vehicleControlItemId.equals=" + (vehicleControlItemId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlAttachmentsIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        VehicleControlAttachments vehicleControlAttachments = VehicleControlAttachmentsResourceIT.createEntity(em);
        em.persist(vehicleControlAttachments);
        em.flush();
        vehicleControls.addVehicleControlAttachments(vehicleControlAttachments);
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        Long vehicleControlAttachmentsId = vehicleControlAttachments.getId();

        // Get all the vehicleControlsList where vehicleControlAttachments equals to vehicleControlAttachmentsId
        defaultVehicleControlsShouldBeFound("vehicleControlAttachmentsId.equals=" + vehicleControlAttachmentsId);

        // Get all the vehicleControlsList where vehicleControlAttachments equals to (vehicleControlAttachmentsId + 1)
        defaultVehicleControlsShouldNotBeFound("vehicleControlAttachmentsId.equals=" + (vehicleControlAttachmentsId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleControlsByVehicleControlExpensesIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        VehicleControlExpenses vehicleControlExpenses = VehicleControlExpensesResourceIT.createEntity(em);
        em.persist(vehicleControlExpenses);
        em.flush();
        vehicleControls.addVehicleControlExpenses(vehicleControlExpenses);
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        Long vehicleControlExpensesId = vehicleControlExpenses.getId();

        // Get all the vehicleControlsList where vehicleControlExpenses equals to vehicleControlExpensesId
        defaultVehicleControlsShouldBeFound("vehicleControlExpensesId.equals=" + vehicleControlExpensesId);

        // Get all the vehicleControlsList where vehicleControlExpenses equals to (vehicleControlExpensesId + 1)
        defaultVehicleControlsShouldNotBeFound("vehicleControlExpensesId.equals=" + (vehicleControlExpensesId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleControlsByAffiliatesIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        Affiliates affiliates = AffiliatesResourceIT.createEntity(em);
        em.persist(affiliates);
        em.flush();
        vehicleControls.setAffiliates(affiliates);
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        Long affiliatesId = affiliates.getId();

        // Get all the vehicleControlsList where affiliates equals to affiliatesId
        defaultVehicleControlsShouldBeFound("affiliatesId.equals=" + affiliatesId);

        // Get all the vehicleControlsList where affiliates equals to (affiliatesId + 1)
        defaultVehicleControlsShouldNotBeFound("affiliatesId.equals=" + (affiliatesId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleControlsByCustomersIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        Customers customers = CustomersResourceIT.createEntity(em);
        em.persist(customers);
        em.flush();
        vehicleControls.setCustomers(customers);
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        Long customersId = customers.getId();

        // Get all the vehicleControlsList where customers equals to customersId
        defaultVehicleControlsShouldBeFound("customersId.equals=" + customersId);

        // Get all the vehicleControlsList where customers equals to (customersId + 1)
        defaultVehicleControlsShouldNotBeFound("customersId.equals=" + (customersId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleControlsByCustomersGroupsIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        CustomersGroups customersGroups = CustomersGroupsResourceIT.createEntity(em);
        em.persist(customersGroups);
        em.flush();
        vehicleControls.setCustomersGroups(customersGroups);
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        Long customersGroupsId = customersGroups.getId();

        // Get all the vehicleControlsList where customersGroups equals to customersGroupsId
        defaultVehicleControlsShouldBeFound("customersGroupsId.equals=" + customersGroupsId);

        // Get all the vehicleControlsList where customersGroups equals to (customersGroupsId + 1)
        defaultVehicleControlsShouldNotBeFound("customersGroupsId.equals=" + (customersGroupsId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleControlsByEmployeesIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        Employees employees = EmployeesResourceIT.createEntity(em);
        em.persist(employees);
        em.flush();
        vehicleControls.setEmployees(employees);
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        Long employeesId = employees.getId();

        // Get all the vehicleControlsList where employees equals to employeesId
        defaultVehicleControlsShouldBeFound("employeesId.equals=" + employeesId);

        // Get all the vehicleControlsList where employees equals to (employeesId + 1)
        defaultVehicleControlsShouldNotBeFound("employeesId.equals=" + (employeesId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleControlsByOriginIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        Cities origin = CitiesResourceIT.createEntity(em);
        em.persist(origin);
        em.flush();
        vehicleControls.setOrigin(origin);
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        Long originId = origin.getId();

        // Get all the vehicleControlsList where origin equals to originId
        defaultVehicleControlsShouldBeFound("originId.equals=" + originId);

        // Get all the vehicleControlsList where origin equals to (originId + 1)
        defaultVehicleControlsShouldNotBeFound("originId.equals=" + (originId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleControlsByDestinationIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        Cities destination = CitiesResourceIT.createEntity(em);
        em.persist(destination);
        em.flush();
        vehicleControls.setDestination(destination);
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        Long destinationId = destination.getId();

        // Get all the vehicleControlsList where destination equals to destinationId
        defaultVehicleControlsShouldBeFound("destinationId.equals=" + destinationId);

        // Get all the vehicleControlsList where destination equals to (destinationId + 1)
        defaultVehicleControlsShouldNotBeFound("destinationId.equals=" + (destinationId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleControlsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        Status status = StatusResourceIT.createEntity(em);
        em.persist(status);
        em.flush();
        vehicleControls.setStatus(status);
        vehicleControlsRepository.saveAndFlush(vehicleControls);
        Long statusId = status.getId();

        // Get all the vehicleControlsList where status equals to statusId
        defaultVehicleControlsShouldBeFound("statusId.equals=" + statusId);

        // Get all the vehicleControlsList where status equals to (statusId + 1)
        defaultVehicleControlsShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVehicleControlsShouldBeFound(String filter) throws Exception {
        restVehicleControlsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleControls.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleControlAuthorizedOrder").value(hasItem(DEFAULT_VEHICLE_CONTROL_AUTHORIZED_ORDER)))
            .andExpect(jsonPath("$.[*].vehicleControlRequest").value(hasItem(DEFAULT_VEHICLE_CONTROL_REQUEST)))
            .andExpect(jsonPath("$.[*].vehicleControlSinister").value(hasItem(DEFAULT_VEHICLE_CONTROL_SINISTER)))
            .andExpect(jsonPath("$.[*].vehicleControlDate").value(hasItem(DEFAULT_VEHICLE_CONTROL_DATE.toString())))
            .andExpect(jsonPath("$.[*].vehicleControlKm").value(hasItem(DEFAULT_VEHICLE_CONTROL_KM.doubleValue())))
            .andExpect(jsonPath("$.[*].vehicleControlPlate").value(hasItem(DEFAULT_VEHICLE_CONTROL_PLATE)))
            .andExpect(jsonPath("$.[*].vehicleControlAmount").value(hasItem(DEFAULT_VEHICLE_CONTROL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].vehicleControlPrice").value(hasItem(DEFAULT_VEHICLE_CONTROL_PRICE.doubleValue())))
            .andExpect(
                jsonPath("$.[*].vehicleControlMaximumDeliveryDate").value(hasItem(DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE.toString()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleControlCollectionForecast").value(hasItem(DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST.toString()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleControlCollectionDeliveryForecast")
                    .value(hasItem(DEFAULT_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST.toString()))
            )
            .andExpect(jsonPath("$.[*].vehicleControlDateCollected").value(hasItem(DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED.toString())))
            .andExpect(jsonPath("$.[*].vehicleControlDeliveryDate").value(hasItem(DEFAULT_VEHICLE_CONTROL_DELIVERY_DATE.toString())));

        // Check, that the count call also returns 1
        restVehicleControlsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVehicleControlsShouldNotBeFound(String filter) throws Exception {
        restVehicleControlsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVehicleControlsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVehicleControls() throws Exception {
        // Get the vehicleControls
        restVehicleControlsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVehicleControls() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        int databaseSizeBeforeUpdate = vehicleControlsRepository.findAll().size();

        // Update the vehicleControls
        VehicleControls updatedVehicleControls = vehicleControlsRepository.findById(vehicleControls.getId()).get();
        // Disconnect from session so that the updates on updatedVehicleControls are not directly saved in db
        em.detach(updatedVehicleControls);
        updatedVehicleControls
            .vehicleControlAuthorizedOrder(UPDATED_VEHICLE_CONTROL_AUTHORIZED_ORDER)
            .vehicleControlRequest(UPDATED_VEHICLE_CONTROL_REQUEST)
            .vehicleControlSinister(UPDATED_VEHICLE_CONTROL_SINISTER)
            .vehicleControlDate(UPDATED_VEHICLE_CONTROL_DATE)
            .vehicleControlKm(UPDATED_VEHICLE_CONTROL_KM)
            .vehicleControlPlate(UPDATED_VEHICLE_CONTROL_PLATE)
            .vehicleControlAmount(UPDATED_VEHICLE_CONTROL_AMOUNT)
            .vehicleControlPrice(UPDATED_VEHICLE_CONTROL_PRICE)
            .vehicleControlMaximumDeliveryDate(UPDATED_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE)
            .vehicleControlCollectionForecast(UPDATED_VEHICLE_CONTROL_COLLECTION_FORECAST)
            .vehicleControlCollectionDeliveryForecast(UPDATED_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST)
            .vehicleControlDateCollected(UPDATED_VEHICLE_CONTROL_DATE_COLLECTED)
            .vehicleControlDeliveryDate(UPDATED_VEHICLE_CONTROL_DELIVERY_DATE);
        VehicleControlsDTO vehicleControlsDTO = vehicleControlsMapper.toDto(updatedVehicleControls);

        restVehicleControlsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleControlsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlsDTO))
            )
            .andExpect(status().isOk());

        // Validate the VehicleControls in the database
        List<VehicleControls> vehicleControlsList = vehicleControlsRepository.findAll();
        assertThat(vehicleControlsList).hasSize(databaseSizeBeforeUpdate);
        VehicleControls testVehicleControls = vehicleControlsList.get(vehicleControlsList.size() - 1);
        assertThat(testVehicleControls.getVehicleControlAuthorizedOrder()).isEqualTo(UPDATED_VEHICLE_CONTROL_AUTHORIZED_ORDER);
        assertThat(testVehicleControls.getVehicleControlRequest()).isEqualTo(UPDATED_VEHICLE_CONTROL_REQUEST);
        assertThat(testVehicleControls.getVehicleControlSinister()).isEqualTo(UPDATED_VEHICLE_CONTROL_SINISTER);
        assertThat(testVehicleControls.getVehicleControlDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_DATE);
        assertThat(testVehicleControls.getVehicleControlKm()).isEqualTo(UPDATED_VEHICLE_CONTROL_KM);
        assertThat(testVehicleControls.getVehicleControlPlate()).isEqualTo(UPDATED_VEHICLE_CONTROL_PLATE);
        assertThat(testVehicleControls.getVehicleControlAmount()).isEqualTo(UPDATED_VEHICLE_CONTROL_AMOUNT);
        assertThat(testVehicleControls.getVehicleControlPrice()).isEqualTo(UPDATED_VEHICLE_CONTROL_PRICE);
        assertThat(testVehicleControls.getVehicleControlMaximumDeliveryDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE);
        assertThat(testVehicleControls.getVehicleControlCollectionForecast()).isEqualTo(UPDATED_VEHICLE_CONTROL_COLLECTION_FORECAST);
        assertThat(testVehicleControls.getVehicleControlCollectionDeliveryForecast())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST);
        assertThat(testVehicleControls.getVehicleControlDateCollected()).isEqualTo(UPDATED_VEHICLE_CONTROL_DATE_COLLECTED);
        assertThat(testVehicleControls.getVehicleControlDeliveryDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void putNonExistingVehicleControls() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlsRepository.findAll().size();
        vehicleControls.setId(count.incrementAndGet());

        // Create the VehicleControls
        VehicleControlsDTO vehicleControlsDTO = vehicleControlsMapper.toDto(vehicleControls);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleControlsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleControlsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControls in the database
        List<VehicleControls> vehicleControlsList = vehicleControlsRepository.findAll();
        assertThat(vehicleControlsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVehicleControls() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlsRepository.findAll().size();
        vehicleControls.setId(count.incrementAndGet());

        // Create the VehicleControls
        VehicleControlsDTO vehicleControlsDTO = vehicleControlsMapper.toDto(vehicleControls);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControls in the database
        List<VehicleControls> vehicleControlsList = vehicleControlsRepository.findAll();
        assertThat(vehicleControlsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVehicleControls() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlsRepository.findAll().size();
        vehicleControls.setId(count.incrementAndGet());

        // Create the VehicleControls
        VehicleControlsDTO vehicleControlsDTO = vehicleControlsMapper.toDto(vehicleControls);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleControlsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleControls in the database
        List<VehicleControls> vehicleControlsList = vehicleControlsRepository.findAll();
        assertThat(vehicleControlsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVehicleControlsWithPatch() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        int databaseSizeBeforeUpdate = vehicleControlsRepository.findAll().size();

        // Update the vehicleControls using partial update
        VehicleControls partialUpdatedVehicleControls = new VehicleControls();
        partialUpdatedVehicleControls.setId(vehicleControls.getId());

        partialUpdatedVehicleControls
            .vehicleControlDate(UPDATED_VEHICLE_CONTROL_DATE)
            .vehicleControlKm(UPDATED_VEHICLE_CONTROL_KM)
            .vehicleControlPlate(UPDATED_VEHICLE_CONTROL_PLATE)
            .vehicleControlAmount(UPDATED_VEHICLE_CONTROL_AMOUNT)
            .vehicleControlPrice(UPDATED_VEHICLE_CONTROL_PRICE)
            .vehicleControlCollectionDeliveryForecast(UPDATED_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST)
            .vehicleControlDeliveryDate(UPDATED_VEHICLE_CONTROL_DELIVERY_DATE);

        restVehicleControlsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleControls.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleControls))
            )
            .andExpect(status().isOk());

        // Validate the VehicleControls in the database
        List<VehicleControls> vehicleControlsList = vehicleControlsRepository.findAll();
        assertThat(vehicleControlsList).hasSize(databaseSizeBeforeUpdate);
        VehicleControls testVehicleControls = vehicleControlsList.get(vehicleControlsList.size() - 1);
        assertThat(testVehicleControls.getVehicleControlAuthorizedOrder()).isEqualTo(DEFAULT_VEHICLE_CONTROL_AUTHORIZED_ORDER);
        assertThat(testVehicleControls.getVehicleControlRequest()).isEqualTo(DEFAULT_VEHICLE_CONTROL_REQUEST);
        assertThat(testVehicleControls.getVehicleControlSinister()).isEqualTo(DEFAULT_VEHICLE_CONTROL_SINISTER);
        assertThat(testVehicleControls.getVehicleControlDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_DATE);
        assertThat(testVehicleControls.getVehicleControlKm()).isEqualTo(UPDATED_VEHICLE_CONTROL_KM);
        assertThat(testVehicleControls.getVehicleControlPlate()).isEqualTo(UPDATED_VEHICLE_CONTROL_PLATE);
        assertThat(testVehicleControls.getVehicleControlAmount()).isEqualTo(UPDATED_VEHICLE_CONTROL_AMOUNT);
        assertThat(testVehicleControls.getVehicleControlPrice()).isEqualTo(UPDATED_VEHICLE_CONTROL_PRICE);
        assertThat(testVehicleControls.getVehicleControlMaximumDeliveryDate()).isEqualTo(DEFAULT_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE);
        assertThat(testVehicleControls.getVehicleControlCollectionForecast()).isEqualTo(DEFAULT_VEHICLE_CONTROL_COLLECTION_FORECAST);
        assertThat(testVehicleControls.getVehicleControlCollectionDeliveryForecast())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST);
        assertThat(testVehicleControls.getVehicleControlDateCollected()).isEqualTo(DEFAULT_VEHICLE_CONTROL_DATE_COLLECTED);
        assertThat(testVehicleControls.getVehicleControlDeliveryDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void fullUpdateVehicleControlsWithPatch() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        int databaseSizeBeforeUpdate = vehicleControlsRepository.findAll().size();

        // Update the vehicleControls using partial update
        VehicleControls partialUpdatedVehicleControls = new VehicleControls();
        partialUpdatedVehicleControls.setId(vehicleControls.getId());

        partialUpdatedVehicleControls
            .vehicleControlAuthorizedOrder(UPDATED_VEHICLE_CONTROL_AUTHORIZED_ORDER)
            .vehicleControlRequest(UPDATED_VEHICLE_CONTROL_REQUEST)
            .vehicleControlSinister(UPDATED_VEHICLE_CONTROL_SINISTER)
            .vehicleControlDate(UPDATED_VEHICLE_CONTROL_DATE)
            .vehicleControlKm(UPDATED_VEHICLE_CONTROL_KM)
            .vehicleControlPlate(UPDATED_VEHICLE_CONTROL_PLATE)
            .vehicleControlAmount(UPDATED_VEHICLE_CONTROL_AMOUNT)
            .vehicleControlPrice(UPDATED_VEHICLE_CONTROL_PRICE)
            .vehicleControlMaximumDeliveryDate(UPDATED_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE)
            .vehicleControlCollectionForecast(UPDATED_VEHICLE_CONTROL_COLLECTION_FORECAST)
            .vehicleControlCollectionDeliveryForecast(UPDATED_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST)
            .vehicleControlDateCollected(UPDATED_VEHICLE_CONTROL_DATE_COLLECTED)
            .vehicleControlDeliveryDate(UPDATED_VEHICLE_CONTROL_DELIVERY_DATE);

        restVehicleControlsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleControls.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleControls))
            )
            .andExpect(status().isOk());

        // Validate the VehicleControls in the database
        List<VehicleControls> vehicleControlsList = vehicleControlsRepository.findAll();
        assertThat(vehicleControlsList).hasSize(databaseSizeBeforeUpdate);
        VehicleControls testVehicleControls = vehicleControlsList.get(vehicleControlsList.size() - 1);
        assertThat(testVehicleControls.getVehicleControlAuthorizedOrder()).isEqualTo(UPDATED_VEHICLE_CONTROL_AUTHORIZED_ORDER);
        assertThat(testVehicleControls.getVehicleControlRequest()).isEqualTo(UPDATED_VEHICLE_CONTROL_REQUEST);
        assertThat(testVehicleControls.getVehicleControlSinister()).isEqualTo(UPDATED_VEHICLE_CONTROL_SINISTER);
        assertThat(testVehicleControls.getVehicleControlDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_DATE);
        assertThat(testVehicleControls.getVehicleControlKm()).isEqualTo(UPDATED_VEHICLE_CONTROL_KM);
        assertThat(testVehicleControls.getVehicleControlPlate()).isEqualTo(UPDATED_VEHICLE_CONTROL_PLATE);
        assertThat(testVehicleControls.getVehicleControlAmount()).isEqualTo(UPDATED_VEHICLE_CONTROL_AMOUNT);
        assertThat(testVehicleControls.getVehicleControlPrice()).isEqualTo(UPDATED_VEHICLE_CONTROL_PRICE);
        assertThat(testVehicleControls.getVehicleControlMaximumDeliveryDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_MAXIMUM_DELIVERY_DATE);
        assertThat(testVehicleControls.getVehicleControlCollectionForecast()).isEqualTo(UPDATED_VEHICLE_CONTROL_COLLECTION_FORECAST);
        assertThat(testVehicleControls.getVehicleControlCollectionDeliveryForecast())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_COLLECTION_DELIVERY_FORECAST);
        assertThat(testVehicleControls.getVehicleControlDateCollected()).isEqualTo(UPDATED_VEHICLE_CONTROL_DATE_COLLECTED);
        assertThat(testVehicleControls.getVehicleControlDeliveryDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingVehicleControls() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlsRepository.findAll().size();
        vehicleControls.setId(count.incrementAndGet());

        // Create the VehicleControls
        VehicleControlsDTO vehicleControlsDTO = vehicleControlsMapper.toDto(vehicleControls);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleControlsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vehicleControlsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControls in the database
        List<VehicleControls> vehicleControlsList = vehicleControlsRepository.findAll();
        assertThat(vehicleControlsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVehicleControls() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlsRepository.findAll().size();
        vehicleControls.setId(count.incrementAndGet());

        // Create the VehicleControls
        VehicleControlsDTO vehicleControlsDTO = vehicleControlsMapper.toDto(vehicleControls);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControls in the database
        List<VehicleControls> vehicleControlsList = vehicleControlsRepository.findAll();
        assertThat(vehicleControlsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVehicleControls() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlsRepository.findAll().size();
        vehicleControls.setId(count.incrementAndGet());

        // Create the VehicleControls
        VehicleControlsDTO vehicleControlsDTO = vehicleControlsMapper.toDto(vehicleControls);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleControls in the database
        List<VehicleControls> vehicleControlsList = vehicleControlsRepository.findAll();
        assertThat(vehicleControlsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVehicleControls() throws Exception {
        // Initialize the database
        vehicleControlsRepository.saveAndFlush(vehicleControls);

        int databaseSizeBeforeDelete = vehicleControlsRepository.findAll().size();

        // Delete the vehicleControls
        restVehicleControlsMockMvc
            .perform(delete(ENTITY_API_URL_ID, vehicleControls.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehicleControls> vehicleControlsList = vehicleControlsRepository.findAll();
        assertThat(vehicleControlsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
