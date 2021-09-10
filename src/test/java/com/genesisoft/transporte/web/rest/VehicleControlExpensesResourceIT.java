package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Cities;
import com.genesisoft.transporte.domain.Suppliers;
import com.genesisoft.transporte.domain.VehicleControlExpenses;
import com.genesisoft.transporte.domain.VehicleControlItem;
import com.genesisoft.transporte.domain.VehicleControls;
import com.genesisoft.transporte.domain.enumeration.DriverType;
import com.genesisoft.transporte.repository.VehicleControlExpensesRepository;
import com.genesisoft.transporte.service.criteria.VehicleControlExpensesCriteria;
import com.genesisoft.transporte.service.dto.VehicleControlExpensesDTO;
import com.genesisoft.transporte.service.mapper.VehicleControlExpensesMapper;
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
 * Integration tests for the {@link VehicleControlExpensesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VehicleControlExpensesResourceIT {

    private static final String DEFAULT_VEHICLE_CONTROL_EXPENSES_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_CONTROL_EXPENSES_DESCRIPTION = "BBBBBBBBBB";

    private static final DriverType DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE = DriverType.EXTERNAL;
    private static final DriverType UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE = DriverType.INTERNAL;

    private static final String DEFAULT_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VEHICLE_CONTROL_EXPENSES_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VEHICLE_CONTROL_EXPENSES_DUE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VEHICLE_CONTROL_EXPENSES_DUE_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE = LocalDate.ofEpochDay(-1L);

    private static final Float DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE = 1F;
    private static final Float UPDATED_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE = 2F;
    private static final Float SMALLER_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE = 1F - 1F;

    private static final Boolean DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION = false;
    private static final Boolean UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION = true;

    private static final String ENTITY_API_URL = "/api/vehicle-control-expenses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VehicleControlExpensesRepository vehicleControlExpensesRepository;

    @Autowired
    private VehicleControlExpensesMapper vehicleControlExpensesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehicleControlExpensesMockMvc;

    private VehicleControlExpenses vehicleControlExpenses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleControlExpenses createEntity(EntityManager em) {
        VehicleControlExpenses vehicleControlExpenses = new VehicleControlExpenses()
            .vehicleControlExpensesDescription(DEFAULT_VEHICLE_CONTROL_EXPENSES_DESCRIPTION)
            .vehicleControlExpensesDriverType(DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE)
            .vehicleControlExpensesPurchaseOrder(DEFAULT_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER)
            .vehicleControlExpensesDueDate(DEFAULT_VEHICLE_CONTROL_EXPENSES_DUE_DATE)
            .vehicleControlExpensesPaymentDate(DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE)
            .vehicleControlExpensesBillingTotalValue(DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE)
            .vehicleControlExpensesDriverCommission(DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION);
        // Add required entity
        VehicleControls vehicleControls;
        if (TestUtil.findAll(em, VehicleControls.class).isEmpty()) {
            vehicleControls = VehicleControlsResourceIT.createEntity(em);
            em.persist(vehicleControls);
            em.flush();
        } else {
            vehicleControls = TestUtil.findAll(em, VehicleControls.class).get(0);
        }
        vehicleControlExpenses.setVehicleControls(vehicleControls);
        // Add required entity
        Suppliers suppliers;
        if (TestUtil.findAll(em, Suppliers.class).isEmpty()) {
            suppliers = SuppliersResourceIT.createEntity(em);
            em.persist(suppliers);
            em.flush();
        } else {
            suppliers = TestUtil.findAll(em, Suppliers.class).get(0);
        }
        vehicleControlExpenses.setSuppliers(suppliers);
        // Add required entity
        Cities cities;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            cities = CitiesResourceIT.createEntity(em);
            em.persist(cities);
            em.flush();
        } else {
            cities = TestUtil.findAll(em, Cities.class).get(0);
        }
        vehicleControlExpenses.setOrigin(cities);
        // Add required entity
        vehicleControlExpenses.setDestination(cities);
        // Add required entity
        VehicleControlItem vehicleControlItem;
        if (TestUtil.findAll(em, VehicleControlItem.class).isEmpty()) {
            vehicleControlItem = VehicleControlItemResourceIT.createEntity(em);
            em.persist(vehicleControlItem);
            em.flush();
        } else {
            vehicleControlItem = TestUtil.findAll(em, VehicleControlItem.class).get(0);
        }
        vehicleControlExpenses.setVehicleControlItem(vehicleControlItem);
        return vehicleControlExpenses;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleControlExpenses createUpdatedEntity(EntityManager em) {
        VehicleControlExpenses vehicleControlExpenses = new VehicleControlExpenses()
            .vehicleControlExpensesDescription(UPDATED_VEHICLE_CONTROL_EXPENSES_DESCRIPTION)
            .vehicleControlExpensesDriverType(UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE)
            .vehicleControlExpensesPurchaseOrder(UPDATED_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER)
            .vehicleControlExpensesDueDate(UPDATED_VEHICLE_CONTROL_EXPENSES_DUE_DATE)
            .vehicleControlExpensesPaymentDate(UPDATED_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE)
            .vehicleControlExpensesBillingTotalValue(UPDATED_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE)
            .vehicleControlExpensesDriverCommission(UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION);
        // Add required entity
        VehicleControls vehicleControls;
        if (TestUtil.findAll(em, VehicleControls.class).isEmpty()) {
            vehicleControls = VehicleControlsResourceIT.createUpdatedEntity(em);
            em.persist(vehicleControls);
            em.flush();
        } else {
            vehicleControls = TestUtil.findAll(em, VehicleControls.class).get(0);
        }
        vehicleControlExpenses.setVehicleControls(vehicleControls);
        // Add required entity
        Suppliers suppliers;
        if (TestUtil.findAll(em, Suppliers.class).isEmpty()) {
            suppliers = SuppliersResourceIT.createUpdatedEntity(em);
            em.persist(suppliers);
            em.flush();
        } else {
            suppliers = TestUtil.findAll(em, Suppliers.class).get(0);
        }
        vehicleControlExpenses.setSuppliers(suppliers);
        // Add required entity
        Cities cities;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            cities = CitiesResourceIT.createUpdatedEntity(em);
            em.persist(cities);
            em.flush();
        } else {
            cities = TestUtil.findAll(em, Cities.class).get(0);
        }
        vehicleControlExpenses.setOrigin(cities);
        // Add required entity
        vehicleControlExpenses.setDestination(cities);
        // Add required entity
        VehicleControlItem vehicleControlItem;
        if (TestUtil.findAll(em, VehicleControlItem.class).isEmpty()) {
            vehicleControlItem = VehicleControlItemResourceIT.createUpdatedEntity(em);
            em.persist(vehicleControlItem);
            em.flush();
        } else {
            vehicleControlItem = TestUtil.findAll(em, VehicleControlItem.class).get(0);
        }
        vehicleControlExpenses.setVehicleControlItem(vehicleControlItem);
        return vehicleControlExpenses;
    }

    @BeforeEach
    public void initTest() {
        vehicleControlExpenses = createEntity(em);
    }

    @Test
    @Transactional
    void createVehicleControlExpenses() throws Exception {
        int databaseSizeBeforeCreate = vehicleControlExpensesRepository.findAll().size();
        // Create the VehicleControlExpenses
        VehicleControlExpensesDTO vehicleControlExpensesDTO = vehicleControlExpensesMapper.toDto(vehicleControlExpenses);
        restVehicleControlExpensesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlExpensesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VehicleControlExpenses in the database
        List<VehicleControlExpenses> vehicleControlExpensesList = vehicleControlExpensesRepository.findAll();
        assertThat(vehicleControlExpensesList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleControlExpenses testVehicleControlExpenses = vehicleControlExpensesList.get(vehicleControlExpensesList.size() - 1);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesDescription())
            .isEqualTo(DEFAULT_VEHICLE_CONTROL_EXPENSES_DESCRIPTION);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesDriverType())
            .isEqualTo(DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesPurchaseOrder())
            .isEqualTo(DEFAULT_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesDueDate()).isEqualTo(DEFAULT_VEHICLE_CONTROL_EXPENSES_DUE_DATE);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesPaymentDate())
            .isEqualTo(DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesBillingTotalValue())
            .isEqualTo(DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesDriverCommission())
            .isEqualTo(DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION);
    }

    @Test
    @Transactional
    void createVehicleControlExpensesWithExistingId() throws Exception {
        // Create the VehicleControlExpenses with an existing ID
        vehicleControlExpenses.setId(1L);
        VehicleControlExpensesDTO vehicleControlExpensesDTO = vehicleControlExpensesMapper.toDto(vehicleControlExpenses);

        int databaseSizeBeforeCreate = vehicleControlExpensesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleControlExpensesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlExpensesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlExpenses in the database
        List<VehicleControlExpenses> vehicleControlExpensesList = vehicleControlExpensesRepository.findAll();
        assertThat(vehicleControlExpensesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVehicleControlExpensesDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleControlExpensesRepository.findAll().size();
        // set the field null
        vehicleControlExpenses.setVehicleControlExpensesDescription(null);

        // Create the VehicleControlExpenses, which fails.
        VehicleControlExpensesDTO vehicleControlExpensesDTO = vehicleControlExpensesMapper.toDto(vehicleControlExpenses);

        restVehicleControlExpensesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlExpensesDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleControlExpenses> vehicleControlExpensesList = vehicleControlExpensesRepository.findAll();
        assertThat(vehicleControlExpensesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVehicleControlExpenses() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList
        restVehicleControlExpensesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleControlExpenses.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleControlExpensesDescription").value(hasItem(DEFAULT_VEHICLE_CONTROL_EXPENSES_DESCRIPTION)))
            .andExpect(
                jsonPath("$.[*].vehicleControlExpensesDriverType").value(hasItem(DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE.toString()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleControlExpensesPurchaseOrder").value(hasItem(DEFAULT_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER))
            )
            .andExpect(jsonPath("$.[*].vehicleControlExpensesDueDate").value(hasItem(DEFAULT_VEHICLE_CONTROL_EXPENSES_DUE_DATE.toString())))
            .andExpect(
                jsonPath("$.[*].vehicleControlExpensesPaymentDate").value(hasItem(DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE.toString()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleControlExpensesBillingTotalValue")
                    .value(hasItem(DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleControlExpensesDriverCommission")
                    .value(hasItem(DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION.booleanValue()))
            );
    }

    @Test
    @Transactional
    void getVehicleControlExpenses() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get the vehicleControlExpenses
        restVehicleControlExpensesMockMvc
            .perform(get(ENTITY_API_URL_ID, vehicleControlExpenses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleControlExpenses.getId().intValue()))
            .andExpect(jsonPath("$.vehicleControlExpensesDescription").value(DEFAULT_VEHICLE_CONTROL_EXPENSES_DESCRIPTION))
            .andExpect(jsonPath("$.vehicleControlExpensesDriverType").value(DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE.toString()))
            .andExpect(jsonPath("$.vehicleControlExpensesPurchaseOrder").value(DEFAULT_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER))
            .andExpect(jsonPath("$.vehicleControlExpensesDueDate").value(DEFAULT_VEHICLE_CONTROL_EXPENSES_DUE_DATE.toString()))
            .andExpect(jsonPath("$.vehicleControlExpensesPaymentDate").value(DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE.toString()))
            .andExpect(
                jsonPath("$.vehicleControlExpensesBillingTotalValue")
                    .value(DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE.doubleValue())
            )
            .andExpect(
                jsonPath("$.vehicleControlExpensesDriverCommission")
                    .value(DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION.booleanValue())
            );
    }

    @Test
    @Transactional
    void getVehicleControlExpensesByIdFiltering() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        Long id = vehicleControlExpenses.getId();

        defaultVehicleControlExpensesShouldBeFound("id.equals=" + id);
        defaultVehicleControlExpensesShouldNotBeFound("id.notEquals=" + id);

        defaultVehicleControlExpensesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVehicleControlExpensesShouldNotBeFound("id.greaterThan=" + id);

        defaultVehicleControlExpensesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVehicleControlExpensesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDescription equals to DEFAULT_VEHICLE_CONTROL_EXPENSES_DESCRIPTION
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesDescription.equals=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_DESCRIPTION
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDescription equals to UPDATED_VEHICLE_CONTROL_EXPENSES_DESCRIPTION
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesDescription.equals=" + UPDATED_VEHICLE_CONTROL_EXPENSES_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDescription not equals to DEFAULT_VEHICLE_CONTROL_EXPENSES_DESCRIPTION
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesDescription.notEquals=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_DESCRIPTION
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDescription not equals to UPDATED_VEHICLE_CONTROL_EXPENSES_DESCRIPTION
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesDescription.notEquals=" + UPDATED_VEHICLE_CONTROL_EXPENSES_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDescription in DEFAULT_VEHICLE_CONTROL_EXPENSES_DESCRIPTION or UPDATED_VEHICLE_CONTROL_EXPENSES_DESCRIPTION
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesDescription.in=" +
            DEFAULT_VEHICLE_CONTROL_EXPENSES_DESCRIPTION +
            "," +
            UPDATED_VEHICLE_CONTROL_EXPENSES_DESCRIPTION
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDescription equals to UPDATED_VEHICLE_CONTROL_EXPENSES_DESCRIPTION
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesDescription.in=" + UPDATED_VEHICLE_CONTROL_EXPENSES_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDescription is not null
        defaultVehicleControlExpensesShouldBeFound("vehicleControlExpensesDescription.specified=true");

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDescription is null
        defaultVehicleControlExpensesShouldNotBeFound("vehicleControlExpensesDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDescriptionContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDescription contains DEFAULT_VEHICLE_CONTROL_EXPENSES_DESCRIPTION
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesDescription.contains=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_DESCRIPTION
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDescription contains UPDATED_VEHICLE_CONTROL_EXPENSES_DESCRIPTION
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesDescription.contains=" + UPDATED_VEHICLE_CONTROL_EXPENSES_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDescription does not contain DEFAULT_VEHICLE_CONTROL_EXPENSES_DESCRIPTION
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesDescription.doesNotContain=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_DESCRIPTION
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDescription does not contain UPDATED_VEHICLE_CONTROL_EXPENSES_DESCRIPTION
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesDescription.doesNotContain=" + UPDATED_VEHICLE_CONTROL_EXPENSES_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDriverTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDriverType equals to DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesDriverType.equals=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDriverType equals to UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesDriverType.equals=" + UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDriverTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDriverType not equals to DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesDriverType.notEquals=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDriverType not equals to UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesDriverType.notEquals=" + UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDriverTypeIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDriverType in DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE or UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesDriverType.in=" +
            DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE +
            "," +
            UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDriverType equals to UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesDriverType.in=" + UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDriverTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDriverType is not null
        defaultVehicleControlExpensesShouldBeFound("vehicleControlExpensesDriverType.specified=true");

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDriverType is null
        defaultVehicleControlExpensesShouldNotBeFound("vehicleControlExpensesDriverType.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPurchaseOrder equals to DEFAULT_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesPurchaseOrder.equals=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPurchaseOrder equals to UPDATED_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesPurchaseOrder.equals=" + UPDATED_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesPurchaseOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPurchaseOrder not equals to DEFAULT_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesPurchaseOrder.notEquals=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPurchaseOrder not equals to UPDATED_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesPurchaseOrder.notEquals=" + UPDATED_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesPurchaseOrderIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPurchaseOrder in DEFAULT_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER or UPDATED_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesPurchaseOrder.in=" +
            DEFAULT_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER +
            "," +
            UPDATED_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPurchaseOrder equals to UPDATED_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesPurchaseOrder.in=" + UPDATED_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesPurchaseOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPurchaseOrder is not null
        defaultVehicleControlExpensesShouldBeFound("vehicleControlExpensesPurchaseOrder.specified=true");

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPurchaseOrder is null
        defaultVehicleControlExpensesShouldNotBeFound("vehicleControlExpensesPurchaseOrder.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesPurchaseOrderContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPurchaseOrder contains DEFAULT_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesPurchaseOrder.contains=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPurchaseOrder contains UPDATED_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesPurchaseOrder.contains=" + UPDATED_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesPurchaseOrderNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPurchaseOrder does not contain DEFAULT_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesPurchaseOrder.doesNotContain=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPurchaseOrder does not contain UPDATED_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesPurchaseOrder.doesNotContain=" + UPDATED_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDueDate equals to DEFAULT_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        defaultVehicleControlExpensesShouldBeFound("vehicleControlExpensesDueDate.equals=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_DUE_DATE);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDueDate equals to UPDATED_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        defaultVehicleControlExpensesShouldNotBeFound("vehicleControlExpensesDueDate.equals=" + UPDATED_VEHICLE_CONTROL_EXPENSES_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDueDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDueDate not equals to DEFAULT_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesDueDate.notEquals=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDueDate not equals to UPDATED_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        defaultVehicleControlExpensesShouldBeFound("vehicleControlExpensesDueDate.notEquals=" + UPDATED_VEHICLE_CONTROL_EXPENSES_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDueDate in DEFAULT_VEHICLE_CONTROL_EXPENSES_DUE_DATE or UPDATED_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesDueDate.in=" +
            DEFAULT_VEHICLE_CONTROL_EXPENSES_DUE_DATE +
            "," +
            UPDATED_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDueDate equals to UPDATED_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        defaultVehicleControlExpensesShouldNotBeFound("vehicleControlExpensesDueDate.in=" + UPDATED_VEHICLE_CONTROL_EXPENSES_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDueDate is not null
        defaultVehicleControlExpensesShouldBeFound("vehicleControlExpensesDueDate.specified=true");

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDueDate is null
        defaultVehicleControlExpensesShouldNotBeFound("vehicleControlExpensesDueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDueDate is greater than or equal to DEFAULT_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesDueDate.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDueDate is greater than or equal to UPDATED_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesDueDate.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDueDate is less than or equal to DEFAULT_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesDueDate.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDueDate is less than or equal to SMALLER_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesDueDate.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDueDate is less than DEFAULT_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesDueDate.lessThan=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDueDate is less than UPDATED_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        defaultVehicleControlExpensesShouldBeFound("vehicleControlExpensesDueDate.lessThan=" + UPDATED_VEHICLE_CONTROL_EXPENSES_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDueDate is greater than DEFAULT_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesDueDate.greaterThan=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDueDate is greater than SMALLER_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesDueDate.greaterThan=" + SMALLER_VEHICLE_CONTROL_EXPENSES_DUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesPaymentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPaymentDate equals to DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesPaymentDate.equals=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPaymentDate equals to UPDATED_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesPaymentDate.equals=" + UPDATED_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesPaymentDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPaymentDate not equals to DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesPaymentDate.notEquals=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPaymentDate not equals to UPDATED_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesPaymentDate.notEquals=" + UPDATED_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesPaymentDateIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPaymentDate in DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE or UPDATED_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesPaymentDate.in=" +
            DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE +
            "," +
            UPDATED_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPaymentDate equals to UPDATED_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesPaymentDate.in=" + UPDATED_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesPaymentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPaymentDate is not null
        defaultVehicleControlExpensesShouldBeFound("vehicleControlExpensesPaymentDate.specified=true");

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPaymentDate is null
        defaultVehicleControlExpensesShouldNotBeFound("vehicleControlExpensesPaymentDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesPaymentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPaymentDate is greater than or equal to DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesPaymentDate.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPaymentDate is greater than or equal to UPDATED_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesPaymentDate.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesPaymentDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPaymentDate is less than or equal to DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesPaymentDate.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPaymentDate is less than or equal to SMALLER_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesPaymentDate.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesPaymentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPaymentDate is less than DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesPaymentDate.lessThan=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPaymentDate is less than UPDATED_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesPaymentDate.lessThan=" + UPDATED_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesPaymentDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPaymentDate is greater than DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesPaymentDate.greaterThan=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesPaymentDate is greater than SMALLER_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesPaymentDate.greaterThan=" + SMALLER_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesBillingTotalValueIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesBillingTotalValue equals to DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesBillingTotalValue.equals=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesBillingTotalValue equals to UPDATED_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesBillingTotalValue.equals=" + UPDATED_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesBillingTotalValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesBillingTotalValue not equals to DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesBillingTotalValue.notEquals=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesBillingTotalValue not equals to UPDATED_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesBillingTotalValue.notEquals=" + UPDATED_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesBillingTotalValueIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesBillingTotalValue in DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE or UPDATED_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesBillingTotalValue.in=" +
            DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE +
            "," +
            UPDATED_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesBillingTotalValue equals to UPDATED_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesBillingTotalValue.in=" + UPDATED_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesBillingTotalValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesBillingTotalValue is not null
        defaultVehicleControlExpensesShouldBeFound("vehicleControlExpensesBillingTotalValue.specified=true");

        // Get all the vehicleControlExpensesList where vehicleControlExpensesBillingTotalValue is null
        defaultVehicleControlExpensesShouldNotBeFound("vehicleControlExpensesBillingTotalValue.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesBillingTotalValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesBillingTotalValue is greater than or equal to DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesBillingTotalValue.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesBillingTotalValue is greater than or equal to UPDATED_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesBillingTotalValue.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesBillingTotalValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesBillingTotalValue is less than or equal to DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesBillingTotalValue.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesBillingTotalValue is less than or equal to SMALLER_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesBillingTotalValue.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesBillingTotalValueIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesBillingTotalValue is less than DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesBillingTotalValue.lessThan=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesBillingTotalValue is less than UPDATED_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesBillingTotalValue.lessThan=" + UPDATED_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesBillingTotalValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesBillingTotalValue is greater than DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesBillingTotalValue.greaterThan=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesBillingTotalValue is greater than SMALLER_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesBillingTotalValue.greaterThan=" + SMALLER_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDriverCommissionIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDriverCommission equals to DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesDriverCommission.equals=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDriverCommission equals to UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesDriverCommission.equals=" + UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDriverCommissionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDriverCommission not equals to DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesDriverCommission.notEquals=" + DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDriverCommission not equals to UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesDriverCommission.notEquals=" + UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDriverCommissionIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDriverCommission in DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION or UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION
        defaultVehicleControlExpensesShouldBeFound(
            "vehicleControlExpensesDriverCommission.in=" +
            DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION +
            "," +
            UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION
        );

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDriverCommission equals to UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION
        defaultVehicleControlExpensesShouldNotBeFound(
            "vehicleControlExpensesDriverCommission.in=" + UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlExpensesDriverCommissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDriverCommission is not null
        defaultVehicleControlExpensesShouldBeFound("vehicleControlExpensesDriverCommission.specified=true");

        // Get all the vehicleControlExpensesList where vehicleControlExpensesDriverCommission is null
        defaultVehicleControlExpensesShouldNotBeFound("vehicleControlExpensesDriverCommission.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlsIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);
        VehicleControls vehicleControls = VehicleControlsResourceIT.createEntity(em);
        em.persist(vehicleControls);
        em.flush();
        vehicleControlExpenses.setVehicleControls(vehicleControls);
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);
        Long vehicleControlsId = vehicleControls.getId();

        // Get all the vehicleControlExpensesList where vehicleControls equals to vehicleControlsId
        defaultVehicleControlExpensesShouldBeFound("vehicleControlsId.equals=" + vehicleControlsId);

        // Get all the vehicleControlExpensesList where vehicleControls equals to (vehicleControlsId + 1)
        defaultVehicleControlExpensesShouldNotBeFound("vehicleControlsId.equals=" + (vehicleControlsId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesBySuppliersIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);
        Suppliers suppliers = SuppliersResourceIT.createEntity(em);
        em.persist(suppliers);
        em.flush();
        vehicleControlExpenses.setSuppliers(suppliers);
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);
        Long suppliersId = suppliers.getId();

        // Get all the vehicleControlExpensesList where suppliers equals to suppliersId
        defaultVehicleControlExpensesShouldBeFound("suppliersId.equals=" + suppliersId);

        // Get all the vehicleControlExpensesList where suppliers equals to (suppliersId + 1)
        defaultVehicleControlExpensesShouldNotBeFound("suppliersId.equals=" + (suppliersId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByOriginIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);
        Cities origin = CitiesResourceIT.createEntity(em);
        em.persist(origin);
        em.flush();
        vehicleControlExpenses.setOrigin(origin);
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);
        Long originId = origin.getId();

        // Get all the vehicleControlExpensesList where origin equals to originId
        defaultVehicleControlExpensesShouldBeFound("originId.equals=" + originId);

        // Get all the vehicleControlExpensesList where origin equals to (originId + 1)
        defaultVehicleControlExpensesShouldNotBeFound("originId.equals=" + (originId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByDestinationIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);
        Cities destination = CitiesResourceIT.createEntity(em);
        em.persist(destination);
        em.flush();
        vehicleControlExpenses.setDestination(destination);
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);
        Long destinationId = destination.getId();

        // Get all the vehicleControlExpensesList where destination equals to destinationId
        defaultVehicleControlExpensesShouldBeFound("destinationId.equals=" + destinationId);

        // Get all the vehicleControlExpensesList where destination equals to (destinationId + 1)
        defaultVehicleControlExpensesShouldNotBeFound("destinationId.equals=" + (destinationId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleControlExpensesByVehicleControlItemIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);
        VehicleControlItem vehicleControlItem = VehicleControlItemResourceIT.createEntity(em);
        em.persist(vehicleControlItem);
        em.flush();
        vehicleControlExpenses.setVehicleControlItem(vehicleControlItem);
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);
        Long vehicleControlItemId = vehicleControlItem.getId();

        // Get all the vehicleControlExpensesList where vehicleControlItem equals to vehicleControlItemId
        defaultVehicleControlExpensesShouldBeFound("vehicleControlItemId.equals=" + vehicleControlItemId);

        // Get all the vehicleControlExpensesList where vehicleControlItem equals to (vehicleControlItemId + 1)
        defaultVehicleControlExpensesShouldNotBeFound("vehicleControlItemId.equals=" + (vehicleControlItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVehicleControlExpensesShouldBeFound(String filter) throws Exception {
        restVehicleControlExpensesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleControlExpenses.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleControlExpensesDescription").value(hasItem(DEFAULT_VEHICLE_CONTROL_EXPENSES_DESCRIPTION)))
            .andExpect(
                jsonPath("$.[*].vehicleControlExpensesDriverType").value(hasItem(DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE.toString()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleControlExpensesPurchaseOrder").value(hasItem(DEFAULT_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER))
            )
            .andExpect(jsonPath("$.[*].vehicleControlExpensesDueDate").value(hasItem(DEFAULT_VEHICLE_CONTROL_EXPENSES_DUE_DATE.toString())))
            .andExpect(
                jsonPath("$.[*].vehicleControlExpensesPaymentDate").value(hasItem(DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE.toString()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleControlExpensesBillingTotalValue")
                    .value(hasItem(DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleControlExpensesDriverCommission")
                    .value(hasItem(DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION.booleanValue()))
            );

        // Check, that the count call also returns 1
        restVehicleControlExpensesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVehicleControlExpensesShouldNotBeFound(String filter) throws Exception {
        restVehicleControlExpensesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVehicleControlExpensesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVehicleControlExpenses() throws Exception {
        // Get the vehicleControlExpenses
        restVehicleControlExpensesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVehicleControlExpenses() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        int databaseSizeBeforeUpdate = vehicleControlExpensesRepository.findAll().size();

        // Update the vehicleControlExpenses
        VehicleControlExpenses updatedVehicleControlExpenses = vehicleControlExpensesRepository
            .findById(vehicleControlExpenses.getId())
            .get();
        // Disconnect from session so that the updates on updatedVehicleControlExpenses are not directly saved in db
        em.detach(updatedVehicleControlExpenses);
        updatedVehicleControlExpenses
            .vehicleControlExpensesDescription(UPDATED_VEHICLE_CONTROL_EXPENSES_DESCRIPTION)
            .vehicleControlExpensesDriverType(UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE)
            .vehicleControlExpensesPurchaseOrder(UPDATED_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER)
            .vehicleControlExpensesDueDate(UPDATED_VEHICLE_CONTROL_EXPENSES_DUE_DATE)
            .vehicleControlExpensesPaymentDate(UPDATED_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE)
            .vehicleControlExpensesBillingTotalValue(UPDATED_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE)
            .vehicleControlExpensesDriverCommission(UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION);
        VehicleControlExpensesDTO vehicleControlExpensesDTO = vehicleControlExpensesMapper.toDto(updatedVehicleControlExpenses);

        restVehicleControlExpensesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleControlExpensesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlExpensesDTO))
            )
            .andExpect(status().isOk());

        // Validate the VehicleControlExpenses in the database
        List<VehicleControlExpenses> vehicleControlExpensesList = vehicleControlExpensesRepository.findAll();
        assertThat(vehicleControlExpensesList).hasSize(databaseSizeBeforeUpdate);
        VehicleControlExpenses testVehicleControlExpenses = vehicleControlExpensesList.get(vehicleControlExpensesList.size() - 1);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesDescription())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_EXPENSES_DESCRIPTION);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesDriverType())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesPurchaseOrder())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesDueDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_EXPENSES_DUE_DATE);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesPaymentDate())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesBillingTotalValue())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesDriverCommission())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION);
    }

    @Test
    @Transactional
    void putNonExistingVehicleControlExpenses() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlExpensesRepository.findAll().size();
        vehicleControlExpenses.setId(count.incrementAndGet());

        // Create the VehicleControlExpenses
        VehicleControlExpensesDTO vehicleControlExpensesDTO = vehicleControlExpensesMapper.toDto(vehicleControlExpenses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleControlExpensesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleControlExpensesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlExpensesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlExpenses in the database
        List<VehicleControlExpenses> vehicleControlExpensesList = vehicleControlExpensesRepository.findAll();
        assertThat(vehicleControlExpensesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVehicleControlExpenses() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlExpensesRepository.findAll().size();
        vehicleControlExpenses.setId(count.incrementAndGet());

        // Create the VehicleControlExpenses
        VehicleControlExpensesDTO vehicleControlExpensesDTO = vehicleControlExpensesMapper.toDto(vehicleControlExpenses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlExpensesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlExpensesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlExpenses in the database
        List<VehicleControlExpenses> vehicleControlExpensesList = vehicleControlExpensesRepository.findAll();
        assertThat(vehicleControlExpensesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVehicleControlExpenses() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlExpensesRepository.findAll().size();
        vehicleControlExpenses.setId(count.incrementAndGet());

        // Create the VehicleControlExpenses
        VehicleControlExpensesDTO vehicleControlExpensesDTO = vehicleControlExpensesMapper.toDto(vehicleControlExpenses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlExpensesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlExpensesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleControlExpenses in the database
        List<VehicleControlExpenses> vehicleControlExpensesList = vehicleControlExpensesRepository.findAll();
        assertThat(vehicleControlExpensesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVehicleControlExpensesWithPatch() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        int databaseSizeBeforeUpdate = vehicleControlExpensesRepository.findAll().size();

        // Update the vehicleControlExpenses using partial update
        VehicleControlExpenses partialUpdatedVehicleControlExpenses = new VehicleControlExpenses();
        partialUpdatedVehicleControlExpenses.setId(vehicleControlExpenses.getId());

        partialUpdatedVehicleControlExpenses
            .vehicleControlExpensesPurchaseOrder(UPDATED_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER)
            .vehicleControlExpensesDueDate(UPDATED_VEHICLE_CONTROL_EXPENSES_DUE_DATE)
            .vehicleControlExpensesDriverCommission(UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION);

        restVehicleControlExpensesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleControlExpenses.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleControlExpenses))
            )
            .andExpect(status().isOk());

        // Validate the VehicleControlExpenses in the database
        List<VehicleControlExpenses> vehicleControlExpensesList = vehicleControlExpensesRepository.findAll();
        assertThat(vehicleControlExpensesList).hasSize(databaseSizeBeforeUpdate);
        VehicleControlExpenses testVehicleControlExpenses = vehicleControlExpensesList.get(vehicleControlExpensesList.size() - 1);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesDescription())
            .isEqualTo(DEFAULT_VEHICLE_CONTROL_EXPENSES_DESCRIPTION);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesDriverType())
            .isEqualTo(DEFAULT_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesPurchaseOrder())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesDueDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_EXPENSES_DUE_DATE);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesPaymentDate())
            .isEqualTo(DEFAULT_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesBillingTotalValue())
            .isEqualTo(DEFAULT_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesDriverCommission())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION);
    }

    @Test
    @Transactional
    void fullUpdateVehicleControlExpensesWithPatch() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        int databaseSizeBeforeUpdate = vehicleControlExpensesRepository.findAll().size();

        // Update the vehicleControlExpenses using partial update
        VehicleControlExpenses partialUpdatedVehicleControlExpenses = new VehicleControlExpenses();
        partialUpdatedVehicleControlExpenses.setId(vehicleControlExpenses.getId());

        partialUpdatedVehicleControlExpenses
            .vehicleControlExpensesDescription(UPDATED_VEHICLE_CONTROL_EXPENSES_DESCRIPTION)
            .vehicleControlExpensesDriverType(UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE)
            .vehicleControlExpensesPurchaseOrder(UPDATED_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER)
            .vehicleControlExpensesDueDate(UPDATED_VEHICLE_CONTROL_EXPENSES_DUE_DATE)
            .vehicleControlExpensesPaymentDate(UPDATED_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE)
            .vehicleControlExpensesBillingTotalValue(UPDATED_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE)
            .vehicleControlExpensesDriverCommission(UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION);

        restVehicleControlExpensesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleControlExpenses.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleControlExpenses))
            )
            .andExpect(status().isOk());

        // Validate the VehicleControlExpenses in the database
        List<VehicleControlExpenses> vehicleControlExpensesList = vehicleControlExpensesRepository.findAll();
        assertThat(vehicleControlExpensesList).hasSize(databaseSizeBeforeUpdate);
        VehicleControlExpenses testVehicleControlExpenses = vehicleControlExpensesList.get(vehicleControlExpensesList.size() - 1);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesDescription())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_EXPENSES_DESCRIPTION);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesDriverType())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_TYPE);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesPurchaseOrder())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_EXPENSES_PURCHASE_ORDER);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesDueDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_EXPENSES_DUE_DATE);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesPaymentDate())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_EXPENSES_PAYMENT_DATE);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesBillingTotalValue())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_EXPENSES_BILLING_TOTAL_VALUE);
        assertThat(testVehicleControlExpenses.getVehicleControlExpensesDriverCommission())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_EXPENSES_DRIVER_COMMISSION);
    }

    @Test
    @Transactional
    void patchNonExistingVehicleControlExpenses() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlExpensesRepository.findAll().size();
        vehicleControlExpenses.setId(count.incrementAndGet());

        // Create the VehicleControlExpenses
        VehicleControlExpensesDTO vehicleControlExpensesDTO = vehicleControlExpensesMapper.toDto(vehicleControlExpenses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleControlExpensesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vehicleControlExpensesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlExpensesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlExpenses in the database
        List<VehicleControlExpenses> vehicleControlExpensesList = vehicleControlExpensesRepository.findAll();
        assertThat(vehicleControlExpensesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVehicleControlExpenses() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlExpensesRepository.findAll().size();
        vehicleControlExpenses.setId(count.incrementAndGet());

        // Create the VehicleControlExpenses
        VehicleControlExpensesDTO vehicleControlExpensesDTO = vehicleControlExpensesMapper.toDto(vehicleControlExpenses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlExpensesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlExpensesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlExpenses in the database
        List<VehicleControlExpenses> vehicleControlExpensesList = vehicleControlExpensesRepository.findAll();
        assertThat(vehicleControlExpensesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVehicleControlExpenses() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlExpensesRepository.findAll().size();
        vehicleControlExpenses.setId(count.incrementAndGet());

        // Create the VehicleControlExpenses
        VehicleControlExpensesDTO vehicleControlExpensesDTO = vehicleControlExpensesMapper.toDto(vehicleControlExpenses);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlExpensesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlExpensesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleControlExpenses in the database
        List<VehicleControlExpenses> vehicleControlExpensesList = vehicleControlExpensesRepository.findAll();
        assertThat(vehicleControlExpensesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVehicleControlExpenses() throws Exception {
        // Initialize the database
        vehicleControlExpensesRepository.saveAndFlush(vehicleControlExpenses);

        int databaseSizeBeforeDelete = vehicleControlExpensesRepository.findAll().size();

        // Delete the vehicleControlExpenses
        restVehicleControlExpensesMockMvc
            .perform(delete(ENTITY_API_URL_ID, vehicleControlExpenses.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehicleControlExpenses> vehicleControlExpensesList = vehicleControlExpensesRepository.findAll();
        assertThat(vehicleControlExpensesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
