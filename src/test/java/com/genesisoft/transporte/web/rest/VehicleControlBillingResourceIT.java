package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Fees;
import com.genesisoft.transporte.domain.VehicleControlBilling;
import com.genesisoft.transporte.domain.VehicleControls;
import com.genesisoft.transporte.repository.VehicleControlBillingRepository;
import com.genesisoft.transporte.service.criteria.VehicleControlBillingCriteria;
import com.genesisoft.transporte.service.dto.VehicleControlBillingDTO;
import com.genesisoft.transporte.service.mapper.VehicleControlBillingMapper;
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
 * Integration tests for the {@link VehicleControlBillingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VehicleControlBillingResourceIT {

    private static final LocalDate DEFAULT_VEHICLE_CONTROL_BILLING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VEHICLE_CONTROL_BILLING_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VEHICLE_CONTROL_BILLING_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VEHICLE_CONTROL_BILLING_PAYMENT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VEHICLE_CONTROL_BILLING_PAYMENT_DATE = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION = false;
    private static final Boolean UPDATED_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION = true;

    private static final Boolean DEFAULT_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION = false;
    private static final Boolean UPDATED_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION = true;

    private static final Integer DEFAULT_VEHICLE_CONTROL_BILLING_AMOUNT = 1;
    private static final Integer UPDATED_VEHICLE_CONTROL_BILLING_AMOUNT = 2;
    private static final Integer SMALLER_VEHICLE_CONTROL_BILLING_AMOUNT = 1 - 1;

    private static final Float DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE = 1F;
    private static final Float UPDATED_VEHICLE_CONTROL_BILLING_TOTAL_VALUE = 2F;
    private static final Float SMALLER_VEHICLE_CONTROL_BILLING_TOTAL_VALUE = 1F - 1F;

    private static final Float DEFAULT_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT = 1F;
    private static final Float UPDATED_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT = 2F;
    private static final Float SMALLER_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT = 1F - 1F;

    private static final String DEFAULT_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VEHICLE_CONTROL_BILLING_ANTICIPATE = false;
    private static final Boolean UPDATED_VEHICLE_CONTROL_BILLING_ANTICIPATE = true;

    private static final String DEFAULT_VEHICLE_CONTROL_BILLING_MANIFEST = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_CONTROL_BILLING_MANIFEST = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vehicle-control-billings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VehicleControlBillingRepository vehicleControlBillingRepository;

    @Autowired
    private VehicleControlBillingMapper vehicleControlBillingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehicleControlBillingMockMvc;

    private VehicleControlBilling vehicleControlBilling;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleControlBilling createEntity(EntityManager em) {
        VehicleControlBilling vehicleControlBilling = new VehicleControlBilling()
            .vehicleControlBillingDate(DEFAULT_VEHICLE_CONTROL_BILLING_DATE)
            .vehicleControlBillingExpirationDate(DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE)
            .vehicleControlBillingPaymentDate(DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE)
            .vehicleControlBillingSellerCommission(DEFAULT_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION)
            .vehicleControlBillingDriverCommission(DEFAULT_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION)
            .vehicleControlBillingAmount(DEFAULT_VEHICLE_CONTROL_BILLING_AMOUNT)
            .vehicleControlBillingTotalValue(DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE)
            .vehicleControlBillingInsuranceDiscount(DEFAULT_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT)
            .vehicleControlBillingCustomerBank(DEFAULT_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK)
            .vehicleControlBillingAnticipate(DEFAULT_VEHICLE_CONTROL_BILLING_ANTICIPATE)
            .vehicleControlBillingManifest(DEFAULT_VEHICLE_CONTROL_BILLING_MANIFEST);
        // Add required entity
        VehicleControls vehicleControls;
        if (TestUtil.findAll(em, VehicleControls.class).isEmpty()) {
            vehicleControls = VehicleControlsResourceIT.createEntity(em);
            em.persist(vehicleControls);
            em.flush();
        } else {
            vehicleControls = TestUtil.findAll(em, VehicleControls.class).get(0);
        }
        vehicleControlBilling.setVehicleControls(vehicleControls);
        // Add required entity
        Fees fees;
        if (TestUtil.findAll(em, Fees.class).isEmpty()) {
            fees = FeesResourceIT.createEntity(em);
            em.persist(fees);
            em.flush();
        } else {
            fees = TestUtil.findAll(em, Fees.class).get(0);
        }
        vehicleControlBilling.setFees(fees);
        return vehicleControlBilling;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleControlBilling createUpdatedEntity(EntityManager em) {
        VehicleControlBilling vehicleControlBilling = new VehicleControlBilling()
            .vehicleControlBillingDate(UPDATED_VEHICLE_CONTROL_BILLING_DATE)
            .vehicleControlBillingExpirationDate(UPDATED_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE)
            .vehicleControlBillingPaymentDate(UPDATED_VEHICLE_CONTROL_BILLING_PAYMENT_DATE)
            .vehicleControlBillingSellerCommission(UPDATED_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION)
            .vehicleControlBillingDriverCommission(UPDATED_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION)
            .vehicleControlBillingAmount(UPDATED_VEHICLE_CONTROL_BILLING_AMOUNT)
            .vehicleControlBillingTotalValue(UPDATED_VEHICLE_CONTROL_BILLING_TOTAL_VALUE)
            .vehicleControlBillingInsuranceDiscount(UPDATED_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT)
            .vehicleControlBillingCustomerBank(UPDATED_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK)
            .vehicleControlBillingAnticipate(UPDATED_VEHICLE_CONTROL_BILLING_ANTICIPATE)
            .vehicleControlBillingManifest(UPDATED_VEHICLE_CONTROL_BILLING_MANIFEST);
        // Add required entity
        VehicleControls vehicleControls;
        if (TestUtil.findAll(em, VehicleControls.class).isEmpty()) {
            vehicleControls = VehicleControlsResourceIT.createUpdatedEntity(em);
            em.persist(vehicleControls);
            em.flush();
        } else {
            vehicleControls = TestUtil.findAll(em, VehicleControls.class).get(0);
        }
        vehicleControlBilling.setVehicleControls(vehicleControls);
        // Add required entity
        Fees fees;
        if (TestUtil.findAll(em, Fees.class).isEmpty()) {
            fees = FeesResourceIT.createUpdatedEntity(em);
            em.persist(fees);
            em.flush();
        } else {
            fees = TestUtil.findAll(em, Fees.class).get(0);
        }
        vehicleControlBilling.setFees(fees);
        return vehicleControlBilling;
    }

    @BeforeEach
    public void initTest() {
        vehicleControlBilling = createEntity(em);
    }

    @Test
    @Transactional
    void createVehicleControlBilling() throws Exception {
        int databaseSizeBeforeCreate = vehicleControlBillingRepository.findAll().size();
        // Create the VehicleControlBilling
        VehicleControlBillingDTO vehicleControlBillingDTO = vehicleControlBillingMapper.toDto(vehicleControlBilling);
        restVehicleControlBillingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlBillingDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VehicleControlBilling in the database
        List<VehicleControlBilling> vehicleControlBillingList = vehicleControlBillingRepository.findAll();
        assertThat(vehicleControlBillingList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleControlBilling testVehicleControlBilling = vehicleControlBillingList.get(vehicleControlBillingList.size() - 1);
        assertThat(testVehicleControlBilling.getVehicleControlBillingDate()).isEqualTo(DEFAULT_VEHICLE_CONTROL_BILLING_DATE);
        assertThat(testVehicleControlBilling.getVehicleControlBillingExpirationDate())
            .isEqualTo(DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE);
        assertThat(testVehicleControlBilling.getVehicleControlBillingPaymentDate()).isEqualTo(DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE);
        assertThat(testVehicleControlBilling.getVehicleControlBillingSellerCommission())
            .isEqualTo(DEFAULT_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION);
        assertThat(testVehicleControlBilling.getVehicleControlBillingDriverCommission())
            .isEqualTo(DEFAULT_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION);
        assertThat(testVehicleControlBilling.getVehicleControlBillingAmount()).isEqualTo(DEFAULT_VEHICLE_CONTROL_BILLING_AMOUNT);
        assertThat(testVehicleControlBilling.getVehicleControlBillingTotalValue()).isEqualTo(DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE);
        assertThat(testVehicleControlBilling.getVehicleControlBillingInsuranceDiscount())
            .isEqualTo(DEFAULT_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT);
        assertThat(testVehicleControlBilling.getVehicleControlBillingCustomerBank())
            .isEqualTo(DEFAULT_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK);
        assertThat(testVehicleControlBilling.getVehicleControlBillingAnticipate()).isEqualTo(DEFAULT_VEHICLE_CONTROL_BILLING_ANTICIPATE);
        assertThat(testVehicleControlBilling.getVehicleControlBillingManifest()).isEqualTo(DEFAULT_VEHICLE_CONTROL_BILLING_MANIFEST);
    }

    @Test
    @Transactional
    void createVehicleControlBillingWithExistingId() throws Exception {
        // Create the VehicleControlBilling with an existing ID
        vehicleControlBilling.setId(1L);
        VehicleControlBillingDTO vehicleControlBillingDTO = vehicleControlBillingMapper.toDto(vehicleControlBilling);

        int databaseSizeBeforeCreate = vehicleControlBillingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleControlBillingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlBillingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlBilling in the database
        List<VehicleControlBilling> vehicleControlBillingList = vehicleControlBillingRepository.findAll();
        assertThat(vehicleControlBillingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVehicleControlBillingDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleControlBillingRepository.findAll().size();
        // set the field null
        vehicleControlBilling.setVehicleControlBillingDate(null);

        // Create the VehicleControlBilling, which fails.
        VehicleControlBillingDTO vehicleControlBillingDTO = vehicleControlBillingMapper.toDto(vehicleControlBilling);

        restVehicleControlBillingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlBillingDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleControlBilling> vehicleControlBillingList = vehicleControlBillingRepository.findAll();
        assertThat(vehicleControlBillingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVehicleControlBillingAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleControlBillingRepository.findAll().size();
        // set the field null
        vehicleControlBilling.setVehicleControlBillingAmount(null);

        // Create the VehicleControlBilling, which fails.
        VehicleControlBillingDTO vehicleControlBillingDTO = vehicleControlBillingMapper.toDto(vehicleControlBilling);

        restVehicleControlBillingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlBillingDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleControlBilling> vehicleControlBillingList = vehicleControlBillingRepository.findAll();
        assertThat(vehicleControlBillingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVehicleControlBillingTotalValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleControlBillingRepository.findAll().size();
        // set the field null
        vehicleControlBilling.setVehicleControlBillingTotalValue(null);

        // Create the VehicleControlBilling, which fails.
        VehicleControlBillingDTO vehicleControlBillingDTO = vehicleControlBillingMapper.toDto(vehicleControlBilling);

        restVehicleControlBillingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlBillingDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleControlBilling> vehicleControlBillingList = vehicleControlBillingRepository.findAll();
        assertThat(vehicleControlBillingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVehicleControlBillings() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList
        restVehicleControlBillingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleControlBilling.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleControlBillingDate").value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_DATE.toString())))
            .andExpect(
                jsonPath("$.[*].vehicleControlBillingExpirationDate")
                    .value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE.toString()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleControlBillingPaymentDate").value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE.toString()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleControlBillingSellerCommission")
                    .value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION.booleanValue()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleControlBillingDriverCommission")
                    .value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].vehicleControlBillingAmount").value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_AMOUNT)))
            .andExpect(
                jsonPath("$.[*].vehicleControlBillingTotalValue").value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleControlBillingInsuranceDiscount")
                    .value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT.doubleValue()))
            )
            .andExpect(jsonPath("$.[*].vehicleControlBillingCustomerBank").value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK)))
            .andExpect(
                jsonPath("$.[*].vehicleControlBillingAnticipate").value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_ANTICIPATE.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].vehicleControlBillingManifest").value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_MANIFEST)));
    }

    @Test
    @Transactional
    void getVehicleControlBilling() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get the vehicleControlBilling
        restVehicleControlBillingMockMvc
            .perform(get(ENTITY_API_URL_ID, vehicleControlBilling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleControlBilling.getId().intValue()))
            .andExpect(jsonPath("$.vehicleControlBillingDate").value(DEFAULT_VEHICLE_CONTROL_BILLING_DATE.toString()))
            .andExpect(jsonPath("$.vehicleControlBillingExpirationDate").value(DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE.toString()))
            .andExpect(jsonPath("$.vehicleControlBillingPaymentDate").value(DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE.toString()))
            .andExpect(
                jsonPath("$.vehicleControlBillingSellerCommission").value(DEFAULT_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION.booleanValue())
            )
            .andExpect(
                jsonPath("$.vehicleControlBillingDriverCommission").value(DEFAULT_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION.booleanValue())
            )
            .andExpect(jsonPath("$.vehicleControlBillingAmount").value(DEFAULT_VEHICLE_CONTROL_BILLING_AMOUNT))
            .andExpect(jsonPath("$.vehicleControlBillingTotalValue").value(DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE.doubleValue()))
            .andExpect(
                jsonPath("$.vehicleControlBillingInsuranceDiscount").value(DEFAULT_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT.doubleValue())
            )
            .andExpect(jsonPath("$.vehicleControlBillingCustomerBank").value(DEFAULT_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK))
            .andExpect(jsonPath("$.vehicleControlBillingAnticipate").value(DEFAULT_VEHICLE_CONTROL_BILLING_ANTICIPATE.booleanValue()))
            .andExpect(jsonPath("$.vehicleControlBillingManifest").value(DEFAULT_VEHICLE_CONTROL_BILLING_MANIFEST));
    }

    @Test
    @Transactional
    void getVehicleControlBillingsByIdFiltering() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        Long id = vehicleControlBilling.getId();

        defaultVehicleControlBillingShouldBeFound("id.equals=" + id);
        defaultVehicleControlBillingShouldNotBeFound("id.notEquals=" + id);

        defaultVehicleControlBillingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVehicleControlBillingShouldNotBeFound("id.greaterThan=" + id);

        defaultVehicleControlBillingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVehicleControlBillingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingDate equals to DEFAULT_VEHICLE_CONTROL_BILLING_DATE
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingDate.equals=" + DEFAULT_VEHICLE_CONTROL_BILLING_DATE);

        // Get all the vehicleControlBillingList where vehicleControlBillingDate equals to UPDATED_VEHICLE_CONTROL_BILLING_DATE
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingDate.equals=" + UPDATED_VEHICLE_CONTROL_BILLING_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingDate not equals to DEFAULT_VEHICLE_CONTROL_BILLING_DATE
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingDate.notEquals=" + DEFAULT_VEHICLE_CONTROL_BILLING_DATE);

        // Get all the vehicleControlBillingList where vehicleControlBillingDate not equals to UPDATED_VEHICLE_CONTROL_BILLING_DATE
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingDate.notEquals=" + UPDATED_VEHICLE_CONTROL_BILLING_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingDateIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingDate in DEFAULT_VEHICLE_CONTROL_BILLING_DATE or UPDATED_VEHICLE_CONTROL_BILLING_DATE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingDate.in=" + DEFAULT_VEHICLE_CONTROL_BILLING_DATE + "," + UPDATED_VEHICLE_CONTROL_BILLING_DATE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingDate equals to UPDATED_VEHICLE_CONTROL_BILLING_DATE
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingDate.in=" + UPDATED_VEHICLE_CONTROL_BILLING_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingDate is not null
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingDate.specified=true");

        // Get all the vehicleControlBillingList where vehicleControlBillingDate is null
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingDate is greater than or equal to DEFAULT_VEHICLE_CONTROL_BILLING_DATE
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingDate.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_BILLING_DATE);

        // Get all the vehicleControlBillingList where vehicleControlBillingDate is greater than or equal to UPDATED_VEHICLE_CONTROL_BILLING_DATE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingDate.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_BILLING_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingDate is less than or equal to DEFAULT_VEHICLE_CONTROL_BILLING_DATE
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingDate.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_BILLING_DATE);

        // Get all the vehicleControlBillingList where vehicleControlBillingDate is less than or equal to SMALLER_VEHICLE_CONTROL_BILLING_DATE
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingDate.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_BILLING_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingDateIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingDate is less than DEFAULT_VEHICLE_CONTROL_BILLING_DATE
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingDate.lessThan=" + DEFAULT_VEHICLE_CONTROL_BILLING_DATE);

        // Get all the vehicleControlBillingList where vehicleControlBillingDate is less than UPDATED_VEHICLE_CONTROL_BILLING_DATE
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingDate.lessThan=" + UPDATED_VEHICLE_CONTROL_BILLING_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingDate is greater than DEFAULT_VEHICLE_CONTROL_BILLING_DATE
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingDate.greaterThan=" + DEFAULT_VEHICLE_CONTROL_BILLING_DATE);

        // Get all the vehicleControlBillingList where vehicleControlBillingDate is greater than SMALLER_VEHICLE_CONTROL_BILLING_DATE
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingDate.greaterThan=" + SMALLER_VEHICLE_CONTROL_BILLING_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingExpirationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingExpirationDate equals to DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingExpirationDate.equals=" + DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingExpirationDate equals to UPDATED_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingExpirationDate.equals=" + UPDATED_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingExpirationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingExpirationDate not equals to DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingExpirationDate.notEquals=" + DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingExpirationDate not equals to UPDATED_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingExpirationDate.notEquals=" + UPDATED_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingExpirationDateIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingExpirationDate in DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE or UPDATED_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingExpirationDate.in=" +
            DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE +
            "," +
            UPDATED_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingExpirationDate equals to UPDATED_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingExpirationDate.in=" + UPDATED_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingExpirationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingExpirationDate is not null
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingExpirationDate.specified=true");

        // Get all the vehicleControlBillingList where vehicleControlBillingExpirationDate is null
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingExpirationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingExpirationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingExpirationDate is greater than or equal to DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingExpirationDate.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingExpirationDate is greater than or equal to UPDATED_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingExpirationDate.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingExpirationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingExpirationDate is less than or equal to DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingExpirationDate.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingExpirationDate is less than or equal to SMALLER_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingExpirationDate.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingExpirationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingExpirationDate is less than DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingExpirationDate.lessThan=" + DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingExpirationDate is less than UPDATED_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingExpirationDate.lessThan=" + UPDATED_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingExpirationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingExpirationDate is greater than DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingExpirationDate.greaterThan=" + DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingExpirationDate is greater than SMALLER_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingExpirationDate.greaterThan=" + SMALLER_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingPaymentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingPaymentDate equals to DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingPaymentDate.equals=" + DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingPaymentDate equals to UPDATED_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingPaymentDate.equals=" + UPDATED_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingPaymentDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingPaymentDate not equals to DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingPaymentDate.notEquals=" + DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingPaymentDate not equals to UPDATED_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingPaymentDate.notEquals=" + UPDATED_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingPaymentDateIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingPaymentDate in DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE or UPDATED_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingPaymentDate.in=" +
            DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE +
            "," +
            UPDATED_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingPaymentDate equals to UPDATED_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingPaymentDate.in=" + UPDATED_VEHICLE_CONTROL_BILLING_PAYMENT_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingPaymentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingPaymentDate is not null
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingPaymentDate.specified=true");

        // Get all the vehicleControlBillingList where vehicleControlBillingPaymentDate is null
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingPaymentDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingPaymentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingPaymentDate is greater than or equal to DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingPaymentDate.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingPaymentDate is greater than or equal to UPDATED_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingPaymentDate.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingPaymentDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingPaymentDate is less than or equal to DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingPaymentDate.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingPaymentDate is less than or equal to SMALLER_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingPaymentDate.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingPaymentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingPaymentDate is less than DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingPaymentDate.lessThan=" + DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingPaymentDate is less than UPDATED_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingPaymentDate.lessThan=" + UPDATED_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingPaymentDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingPaymentDate is greater than DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingPaymentDate.greaterThan=" + DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingPaymentDate is greater than SMALLER_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingPaymentDate.greaterThan=" + SMALLER_VEHICLE_CONTROL_BILLING_PAYMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingSellerCommissionIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingSellerCommission equals to DEFAULT_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingSellerCommission.equals=" + DEFAULT_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingSellerCommission equals to UPDATED_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingSellerCommission.equals=" + UPDATED_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingSellerCommissionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingSellerCommission not equals to DEFAULT_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingSellerCommission.notEquals=" + DEFAULT_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingSellerCommission not equals to UPDATED_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingSellerCommission.notEquals=" + UPDATED_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingSellerCommissionIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingSellerCommission in DEFAULT_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION or UPDATED_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingSellerCommission.in=" +
            DEFAULT_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION +
            "," +
            UPDATED_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingSellerCommission equals to UPDATED_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingSellerCommission.in=" + UPDATED_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingSellerCommissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingSellerCommission is not null
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingSellerCommission.specified=true");

        // Get all the vehicleControlBillingList where vehicleControlBillingSellerCommission is null
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingSellerCommission.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingDriverCommissionIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingDriverCommission equals to DEFAULT_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingDriverCommission.equals=" + DEFAULT_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingDriverCommission equals to UPDATED_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingDriverCommission.equals=" + UPDATED_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingDriverCommissionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingDriverCommission not equals to DEFAULT_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingDriverCommission.notEquals=" + DEFAULT_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingDriverCommission not equals to UPDATED_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingDriverCommission.notEquals=" + UPDATED_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingDriverCommissionIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingDriverCommission in DEFAULT_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION or UPDATED_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingDriverCommission.in=" +
            DEFAULT_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION +
            "," +
            UPDATED_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingDriverCommission equals to UPDATED_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingDriverCommission.in=" + UPDATED_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingDriverCommissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingDriverCommission is not null
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingDriverCommission.specified=true");

        // Get all the vehicleControlBillingList where vehicleControlBillingDriverCommission is null
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingDriverCommission.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingAmount equals to DEFAULT_VEHICLE_CONTROL_BILLING_AMOUNT
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingAmount.equals=" + DEFAULT_VEHICLE_CONTROL_BILLING_AMOUNT);

        // Get all the vehicleControlBillingList where vehicleControlBillingAmount equals to UPDATED_VEHICLE_CONTROL_BILLING_AMOUNT
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingAmount.equals=" + UPDATED_VEHICLE_CONTROL_BILLING_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingAmount not equals to DEFAULT_VEHICLE_CONTROL_BILLING_AMOUNT
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingAmount.notEquals=" + DEFAULT_VEHICLE_CONTROL_BILLING_AMOUNT);

        // Get all the vehicleControlBillingList where vehicleControlBillingAmount not equals to UPDATED_VEHICLE_CONTROL_BILLING_AMOUNT
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingAmount.notEquals=" + UPDATED_VEHICLE_CONTROL_BILLING_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingAmountIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingAmount in DEFAULT_VEHICLE_CONTROL_BILLING_AMOUNT or UPDATED_VEHICLE_CONTROL_BILLING_AMOUNT
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingAmount.in=" + DEFAULT_VEHICLE_CONTROL_BILLING_AMOUNT + "," + UPDATED_VEHICLE_CONTROL_BILLING_AMOUNT
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingAmount equals to UPDATED_VEHICLE_CONTROL_BILLING_AMOUNT
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingAmount.in=" + UPDATED_VEHICLE_CONTROL_BILLING_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingAmount is not null
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingAmount.specified=true");

        // Get all the vehicleControlBillingList where vehicleControlBillingAmount is null
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingAmount is greater than or equal to DEFAULT_VEHICLE_CONTROL_BILLING_AMOUNT
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingAmount.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_BILLING_AMOUNT
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingAmount is greater than or equal to UPDATED_VEHICLE_CONTROL_BILLING_AMOUNT
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingAmount.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_BILLING_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingAmount is less than or equal to DEFAULT_VEHICLE_CONTROL_BILLING_AMOUNT
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingAmount.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_BILLING_AMOUNT);

        // Get all the vehicleControlBillingList where vehicleControlBillingAmount is less than or equal to SMALLER_VEHICLE_CONTROL_BILLING_AMOUNT
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingAmount.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_BILLING_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingAmount is less than DEFAULT_VEHICLE_CONTROL_BILLING_AMOUNT
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingAmount.lessThan=" + DEFAULT_VEHICLE_CONTROL_BILLING_AMOUNT);

        // Get all the vehicleControlBillingList where vehicleControlBillingAmount is less than UPDATED_VEHICLE_CONTROL_BILLING_AMOUNT
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingAmount.lessThan=" + UPDATED_VEHICLE_CONTROL_BILLING_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingAmount is greater than DEFAULT_VEHICLE_CONTROL_BILLING_AMOUNT
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingAmount.greaterThan=" + DEFAULT_VEHICLE_CONTROL_BILLING_AMOUNT);

        // Get all the vehicleControlBillingList where vehicleControlBillingAmount is greater than SMALLER_VEHICLE_CONTROL_BILLING_AMOUNT
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingAmount.greaterThan=" + SMALLER_VEHICLE_CONTROL_BILLING_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingTotalValueIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingTotalValue equals to DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingTotalValue.equals=" + DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE);

        // Get all the vehicleControlBillingList where vehicleControlBillingTotalValue equals to UPDATED_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingTotalValue.equals=" + UPDATED_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingTotalValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingTotalValue not equals to DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingTotalValue.notEquals=" + DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingTotalValue not equals to UPDATED_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingTotalValue.notEquals=" + UPDATED_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingTotalValueIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingTotalValue in DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE or UPDATED_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingTotalValue.in=" +
            DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE +
            "," +
            UPDATED_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingTotalValue equals to UPDATED_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingTotalValue.in=" + UPDATED_VEHICLE_CONTROL_BILLING_TOTAL_VALUE);
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingTotalValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingTotalValue is not null
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingTotalValue.specified=true");

        // Get all the vehicleControlBillingList where vehicleControlBillingTotalValue is null
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingTotalValue.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingTotalValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingTotalValue is greater than or equal to DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingTotalValue.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingTotalValue is greater than or equal to UPDATED_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingTotalValue.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingTotalValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingTotalValue is less than or equal to DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingTotalValue.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingTotalValue is less than or equal to SMALLER_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingTotalValue.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingTotalValueIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingTotalValue is less than DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingTotalValue.lessThan=" + DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingTotalValue is less than UPDATED_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingTotalValue.lessThan=" + UPDATED_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingTotalValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingTotalValue is greater than DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingTotalValue.greaterThan=" + DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingTotalValue is greater than SMALLER_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingTotalValue.greaterThan=" + SMALLER_VEHICLE_CONTROL_BILLING_TOTAL_VALUE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingInsuranceDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingInsuranceDiscount equals to DEFAULT_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingInsuranceDiscount.equals=" + DEFAULT_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingInsuranceDiscount equals to UPDATED_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingInsuranceDiscount.equals=" + UPDATED_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingInsuranceDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingInsuranceDiscount not equals to DEFAULT_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingInsuranceDiscount.notEquals=" + DEFAULT_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingInsuranceDiscount not equals to UPDATED_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingInsuranceDiscount.notEquals=" + UPDATED_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingInsuranceDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingInsuranceDiscount in DEFAULT_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT or UPDATED_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingInsuranceDiscount.in=" +
            DEFAULT_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT +
            "," +
            UPDATED_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingInsuranceDiscount equals to UPDATED_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingInsuranceDiscount.in=" + UPDATED_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingInsuranceDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingInsuranceDiscount is not null
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingInsuranceDiscount.specified=true");

        // Get all the vehicleControlBillingList where vehicleControlBillingInsuranceDiscount is null
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingInsuranceDiscount.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingInsuranceDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingInsuranceDiscount is greater than or equal to DEFAULT_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingInsuranceDiscount.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingInsuranceDiscount is greater than or equal to UPDATED_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingInsuranceDiscount.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingInsuranceDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingInsuranceDiscount is less than or equal to DEFAULT_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingInsuranceDiscount.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingInsuranceDiscount is less than or equal to SMALLER_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingInsuranceDiscount.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingInsuranceDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingInsuranceDiscount is less than DEFAULT_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingInsuranceDiscount.lessThan=" + DEFAULT_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingInsuranceDiscount is less than UPDATED_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingInsuranceDiscount.lessThan=" + UPDATED_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingInsuranceDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingInsuranceDiscount is greater than DEFAULT_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingInsuranceDiscount.greaterThan=" + DEFAULT_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingInsuranceDiscount is greater than SMALLER_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingInsuranceDiscount.greaterThan=" + SMALLER_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingCustomerBankIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingCustomerBank equals to DEFAULT_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingCustomerBank.equals=" + DEFAULT_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingCustomerBank equals to UPDATED_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingCustomerBank.equals=" + UPDATED_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingCustomerBankIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingCustomerBank not equals to DEFAULT_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingCustomerBank.notEquals=" + DEFAULT_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingCustomerBank not equals to UPDATED_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingCustomerBank.notEquals=" + UPDATED_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingCustomerBankIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingCustomerBank in DEFAULT_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK or UPDATED_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingCustomerBank.in=" +
            DEFAULT_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK +
            "," +
            UPDATED_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingCustomerBank equals to UPDATED_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingCustomerBank.in=" + UPDATED_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingCustomerBankIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingCustomerBank is not null
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingCustomerBank.specified=true");

        // Get all the vehicleControlBillingList where vehicleControlBillingCustomerBank is null
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingCustomerBank.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingCustomerBankContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingCustomerBank contains DEFAULT_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingCustomerBank.contains=" + DEFAULT_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingCustomerBank contains UPDATED_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingCustomerBank.contains=" + UPDATED_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingCustomerBankNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingCustomerBank does not contain DEFAULT_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingCustomerBank.doesNotContain=" + DEFAULT_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingCustomerBank does not contain UPDATED_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingCustomerBank.doesNotContain=" + UPDATED_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingAnticipateIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingAnticipate equals to DEFAULT_VEHICLE_CONTROL_BILLING_ANTICIPATE
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingAnticipate.equals=" + DEFAULT_VEHICLE_CONTROL_BILLING_ANTICIPATE);

        // Get all the vehicleControlBillingList where vehicleControlBillingAnticipate equals to UPDATED_VEHICLE_CONTROL_BILLING_ANTICIPATE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingAnticipate.equals=" + UPDATED_VEHICLE_CONTROL_BILLING_ANTICIPATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingAnticipateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingAnticipate not equals to DEFAULT_VEHICLE_CONTROL_BILLING_ANTICIPATE
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingAnticipate.notEquals=" + DEFAULT_VEHICLE_CONTROL_BILLING_ANTICIPATE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingAnticipate not equals to UPDATED_VEHICLE_CONTROL_BILLING_ANTICIPATE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingAnticipate.notEquals=" + UPDATED_VEHICLE_CONTROL_BILLING_ANTICIPATE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingAnticipateIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingAnticipate in DEFAULT_VEHICLE_CONTROL_BILLING_ANTICIPATE or UPDATED_VEHICLE_CONTROL_BILLING_ANTICIPATE
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingAnticipate.in=" +
            DEFAULT_VEHICLE_CONTROL_BILLING_ANTICIPATE +
            "," +
            UPDATED_VEHICLE_CONTROL_BILLING_ANTICIPATE
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingAnticipate equals to UPDATED_VEHICLE_CONTROL_BILLING_ANTICIPATE
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingAnticipate.in=" + UPDATED_VEHICLE_CONTROL_BILLING_ANTICIPATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingAnticipateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingAnticipate is not null
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingAnticipate.specified=true");

        // Get all the vehicleControlBillingList where vehicleControlBillingAnticipate is null
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingAnticipate.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingManifestIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingManifest equals to DEFAULT_VEHICLE_CONTROL_BILLING_MANIFEST
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingManifest.equals=" + DEFAULT_VEHICLE_CONTROL_BILLING_MANIFEST);

        // Get all the vehicleControlBillingList where vehicleControlBillingManifest equals to UPDATED_VEHICLE_CONTROL_BILLING_MANIFEST
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingManifest.equals=" + UPDATED_VEHICLE_CONTROL_BILLING_MANIFEST);
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingManifestIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingManifest not equals to DEFAULT_VEHICLE_CONTROL_BILLING_MANIFEST
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingManifest.notEquals=" + DEFAULT_VEHICLE_CONTROL_BILLING_MANIFEST);

        // Get all the vehicleControlBillingList where vehicleControlBillingManifest not equals to UPDATED_VEHICLE_CONTROL_BILLING_MANIFEST
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingManifest.notEquals=" + UPDATED_VEHICLE_CONTROL_BILLING_MANIFEST);
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingManifestIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingManifest in DEFAULT_VEHICLE_CONTROL_BILLING_MANIFEST or UPDATED_VEHICLE_CONTROL_BILLING_MANIFEST
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingManifest.in=" + DEFAULT_VEHICLE_CONTROL_BILLING_MANIFEST + "," + UPDATED_VEHICLE_CONTROL_BILLING_MANIFEST
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingManifest equals to UPDATED_VEHICLE_CONTROL_BILLING_MANIFEST
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingManifest.in=" + UPDATED_VEHICLE_CONTROL_BILLING_MANIFEST);
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingManifestIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingManifest is not null
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingManifest.specified=true");

        // Get all the vehicleControlBillingList where vehicleControlBillingManifest is null
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingManifest.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingManifestContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingManifest contains DEFAULT_VEHICLE_CONTROL_BILLING_MANIFEST
        defaultVehicleControlBillingShouldBeFound("vehicleControlBillingManifest.contains=" + DEFAULT_VEHICLE_CONTROL_BILLING_MANIFEST);

        // Get all the vehicleControlBillingList where vehicleControlBillingManifest contains UPDATED_VEHICLE_CONTROL_BILLING_MANIFEST
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlBillingManifest.contains=" + UPDATED_VEHICLE_CONTROL_BILLING_MANIFEST);
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlBillingManifestNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        // Get all the vehicleControlBillingList where vehicleControlBillingManifest does not contain DEFAULT_VEHICLE_CONTROL_BILLING_MANIFEST
        defaultVehicleControlBillingShouldNotBeFound(
            "vehicleControlBillingManifest.doesNotContain=" + DEFAULT_VEHICLE_CONTROL_BILLING_MANIFEST
        );

        // Get all the vehicleControlBillingList where vehicleControlBillingManifest does not contain UPDATED_VEHICLE_CONTROL_BILLING_MANIFEST
        defaultVehicleControlBillingShouldBeFound(
            "vehicleControlBillingManifest.doesNotContain=" + UPDATED_VEHICLE_CONTROL_BILLING_MANIFEST
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByVehicleControlsIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);
        VehicleControls vehicleControls = VehicleControlsResourceIT.createEntity(em);
        em.persist(vehicleControls);
        em.flush();
        vehicleControlBilling.setVehicleControls(vehicleControls);
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);
        Long vehicleControlsId = vehicleControls.getId();

        // Get all the vehicleControlBillingList where vehicleControls equals to vehicleControlsId
        defaultVehicleControlBillingShouldBeFound("vehicleControlsId.equals=" + vehicleControlsId);

        // Get all the vehicleControlBillingList where vehicleControls equals to (vehicleControlsId + 1)
        defaultVehicleControlBillingShouldNotBeFound("vehicleControlsId.equals=" + (vehicleControlsId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleControlBillingsByFeesIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);
        Fees fees = FeesResourceIT.createEntity(em);
        em.persist(fees);
        em.flush();
        vehicleControlBilling.setFees(fees);
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);
        Long feesId = fees.getId();

        // Get all the vehicleControlBillingList where fees equals to feesId
        defaultVehicleControlBillingShouldBeFound("feesId.equals=" + feesId);

        // Get all the vehicleControlBillingList where fees equals to (feesId + 1)
        defaultVehicleControlBillingShouldNotBeFound("feesId.equals=" + (feesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVehicleControlBillingShouldBeFound(String filter) throws Exception {
        restVehicleControlBillingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleControlBilling.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleControlBillingDate").value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_DATE.toString())))
            .andExpect(
                jsonPath("$.[*].vehicleControlBillingExpirationDate")
                    .value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE.toString()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleControlBillingPaymentDate").value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE.toString()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleControlBillingSellerCommission")
                    .value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION.booleanValue()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleControlBillingDriverCommission")
                    .value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].vehicleControlBillingAmount").value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_AMOUNT)))
            .andExpect(
                jsonPath("$.[*].vehicleControlBillingTotalValue").value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleControlBillingInsuranceDiscount")
                    .value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT.doubleValue()))
            )
            .andExpect(jsonPath("$.[*].vehicleControlBillingCustomerBank").value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK)))
            .andExpect(
                jsonPath("$.[*].vehicleControlBillingAnticipate").value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_ANTICIPATE.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].vehicleControlBillingManifest").value(hasItem(DEFAULT_VEHICLE_CONTROL_BILLING_MANIFEST)));

        // Check, that the count call also returns 1
        restVehicleControlBillingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVehicleControlBillingShouldNotBeFound(String filter) throws Exception {
        restVehicleControlBillingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVehicleControlBillingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVehicleControlBilling() throws Exception {
        // Get the vehicleControlBilling
        restVehicleControlBillingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVehicleControlBilling() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        int databaseSizeBeforeUpdate = vehicleControlBillingRepository.findAll().size();

        // Update the vehicleControlBilling
        VehicleControlBilling updatedVehicleControlBilling = vehicleControlBillingRepository.findById(vehicleControlBilling.getId()).get();
        // Disconnect from session so that the updates on updatedVehicleControlBilling are not directly saved in db
        em.detach(updatedVehicleControlBilling);
        updatedVehicleControlBilling
            .vehicleControlBillingDate(UPDATED_VEHICLE_CONTROL_BILLING_DATE)
            .vehicleControlBillingExpirationDate(UPDATED_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE)
            .vehicleControlBillingPaymentDate(UPDATED_VEHICLE_CONTROL_BILLING_PAYMENT_DATE)
            .vehicleControlBillingSellerCommission(UPDATED_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION)
            .vehicleControlBillingDriverCommission(UPDATED_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION)
            .vehicleControlBillingAmount(UPDATED_VEHICLE_CONTROL_BILLING_AMOUNT)
            .vehicleControlBillingTotalValue(UPDATED_VEHICLE_CONTROL_BILLING_TOTAL_VALUE)
            .vehicleControlBillingInsuranceDiscount(UPDATED_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT)
            .vehicleControlBillingCustomerBank(UPDATED_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK)
            .vehicleControlBillingAnticipate(UPDATED_VEHICLE_CONTROL_BILLING_ANTICIPATE)
            .vehicleControlBillingManifest(UPDATED_VEHICLE_CONTROL_BILLING_MANIFEST);
        VehicleControlBillingDTO vehicleControlBillingDTO = vehicleControlBillingMapper.toDto(updatedVehicleControlBilling);

        restVehicleControlBillingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleControlBillingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlBillingDTO))
            )
            .andExpect(status().isOk());

        // Validate the VehicleControlBilling in the database
        List<VehicleControlBilling> vehicleControlBillingList = vehicleControlBillingRepository.findAll();
        assertThat(vehicleControlBillingList).hasSize(databaseSizeBeforeUpdate);
        VehicleControlBilling testVehicleControlBilling = vehicleControlBillingList.get(vehicleControlBillingList.size() - 1);
        assertThat(testVehicleControlBilling.getVehicleControlBillingDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_DATE);
        assertThat(testVehicleControlBilling.getVehicleControlBillingExpirationDate())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE);
        assertThat(testVehicleControlBilling.getVehicleControlBillingPaymentDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_PAYMENT_DATE);
        assertThat(testVehicleControlBilling.getVehicleControlBillingSellerCommission())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION);
        assertThat(testVehicleControlBilling.getVehicleControlBillingDriverCommission())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION);
        assertThat(testVehicleControlBilling.getVehicleControlBillingAmount()).isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_AMOUNT);
        assertThat(testVehicleControlBilling.getVehicleControlBillingTotalValue()).isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_TOTAL_VALUE);
        assertThat(testVehicleControlBilling.getVehicleControlBillingInsuranceDiscount())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT);
        assertThat(testVehicleControlBilling.getVehicleControlBillingCustomerBank())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK);
        assertThat(testVehicleControlBilling.getVehicleControlBillingAnticipate()).isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_ANTICIPATE);
        assertThat(testVehicleControlBilling.getVehicleControlBillingManifest()).isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_MANIFEST);
    }

    @Test
    @Transactional
    void putNonExistingVehicleControlBilling() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlBillingRepository.findAll().size();
        vehicleControlBilling.setId(count.incrementAndGet());

        // Create the VehicleControlBilling
        VehicleControlBillingDTO vehicleControlBillingDTO = vehicleControlBillingMapper.toDto(vehicleControlBilling);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleControlBillingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleControlBillingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlBillingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlBilling in the database
        List<VehicleControlBilling> vehicleControlBillingList = vehicleControlBillingRepository.findAll();
        assertThat(vehicleControlBillingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVehicleControlBilling() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlBillingRepository.findAll().size();
        vehicleControlBilling.setId(count.incrementAndGet());

        // Create the VehicleControlBilling
        VehicleControlBillingDTO vehicleControlBillingDTO = vehicleControlBillingMapper.toDto(vehicleControlBilling);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlBillingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlBillingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlBilling in the database
        List<VehicleControlBilling> vehicleControlBillingList = vehicleControlBillingRepository.findAll();
        assertThat(vehicleControlBillingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVehicleControlBilling() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlBillingRepository.findAll().size();
        vehicleControlBilling.setId(count.incrementAndGet());

        // Create the VehicleControlBilling
        VehicleControlBillingDTO vehicleControlBillingDTO = vehicleControlBillingMapper.toDto(vehicleControlBilling);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlBillingMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlBillingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleControlBilling in the database
        List<VehicleControlBilling> vehicleControlBillingList = vehicleControlBillingRepository.findAll();
        assertThat(vehicleControlBillingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVehicleControlBillingWithPatch() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        int databaseSizeBeforeUpdate = vehicleControlBillingRepository.findAll().size();

        // Update the vehicleControlBilling using partial update
        VehicleControlBilling partialUpdatedVehicleControlBilling = new VehicleControlBilling();
        partialUpdatedVehicleControlBilling.setId(vehicleControlBilling.getId());

        partialUpdatedVehicleControlBilling
            .vehicleControlBillingDate(UPDATED_VEHICLE_CONTROL_BILLING_DATE)
            .vehicleControlBillingSellerCommission(UPDATED_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION)
            .vehicleControlBillingAmount(UPDATED_VEHICLE_CONTROL_BILLING_AMOUNT)
            .vehicleControlBillingInsuranceDiscount(UPDATED_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT)
            .vehicleControlBillingCustomerBank(UPDATED_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK)
            .vehicleControlBillingManifest(UPDATED_VEHICLE_CONTROL_BILLING_MANIFEST);

        restVehicleControlBillingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleControlBilling.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleControlBilling))
            )
            .andExpect(status().isOk());

        // Validate the VehicleControlBilling in the database
        List<VehicleControlBilling> vehicleControlBillingList = vehicleControlBillingRepository.findAll();
        assertThat(vehicleControlBillingList).hasSize(databaseSizeBeforeUpdate);
        VehicleControlBilling testVehicleControlBilling = vehicleControlBillingList.get(vehicleControlBillingList.size() - 1);
        assertThat(testVehicleControlBilling.getVehicleControlBillingDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_DATE);
        assertThat(testVehicleControlBilling.getVehicleControlBillingExpirationDate())
            .isEqualTo(DEFAULT_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE);
        assertThat(testVehicleControlBilling.getVehicleControlBillingPaymentDate()).isEqualTo(DEFAULT_VEHICLE_CONTROL_BILLING_PAYMENT_DATE);
        assertThat(testVehicleControlBilling.getVehicleControlBillingSellerCommission())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION);
        assertThat(testVehicleControlBilling.getVehicleControlBillingDriverCommission())
            .isEqualTo(DEFAULT_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION);
        assertThat(testVehicleControlBilling.getVehicleControlBillingAmount()).isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_AMOUNT);
        assertThat(testVehicleControlBilling.getVehicleControlBillingTotalValue()).isEqualTo(DEFAULT_VEHICLE_CONTROL_BILLING_TOTAL_VALUE);
        assertThat(testVehicleControlBilling.getVehicleControlBillingInsuranceDiscount())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT);
        assertThat(testVehicleControlBilling.getVehicleControlBillingCustomerBank())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK);
        assertThat(testVehicleControlBilling.getVehicleControlBillingAnticipate()).isEqualTo(DEFAULT_VEHICLE_CONTROL_BILLING_ANTICIPATE);
        assertThat(testVehicleControlBilling.getVehicleControlBillingManifest()).isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_MANIFEST);
    }

    @Test
    @Transactional
    void fullUpdateVehicleControlBillingWithPatch() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        int databaseSizeBeforeUpdate = vehicleControlBillingRepository.findAll().size();

        // Update the vehicleControlBilling using partial update
        VehicleControlBilling partialUpdatedVehicleControlBilling = new VehicleControlBilling();
        partialUpdatedVehicleControlBilling.setId(vehicleControlBilling.getId());

        partialUpdatedVehicleControlBilling
            .vehicleControlBillingDate(UPDATED_VEHICLE_CONTROL_BILLING_DATE)
            .vehicleControlBillingExpirationDate(UPDATED_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE)
            .vehicleControlBillingPaymentDate(UPDATED_VEHICLE_CONTROL_BILLING_PAYMENT_DATE)
            .vehicleControlBillingSellerCommission(UPDATED_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION)
            .vehicleControlBillingDriverCommission(UPDATED_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION)
            .vehicleControlBillingAmount(UPDATED_VEHICLE_CONTROL_BILLING_AMOUNT)
            .vehicleControlBillingTotalValue(UPDATED_VEHICLE_CONTROL_BILLING_TOTAL_VALUE)
            .vehicleControlBillingInsuranceDiscount(UPDATED_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT)
            .vehicleControlBillingCustomerBank(UPDATED_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK)
            .vehicleControlBillingAnticipate(UPDATED_VEHICLE_CONTROL_BILLING_ANTICIPATE)
            .vehicleControlBillingManifest(UPDATED_VEHICLE_CONTROL_BILLING_MANIFEST);

        restVehicleControlBillingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleControlBilling.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleControlBilling))
            )
            .andExpect(status().isOk());

        // Validate the VehicleControlBilling in the database
        List<VehicleControlBilling> vehicleControlBillingList = vehicleControlBillingRepository.findAll();
        assertThat(vehicleControlBillingList).hasSize(databaseSizeBeforeUpdate);
        VehicleControlBilling testVehicleControlBilling = vehicleControlBillingList.get(vehicleControlBillingList.size() - 1);
        assertThat(testVehicleControlBilling.getVehicleControlBillingDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_DATE);
        assertThat(testVehicleControlBilling.getVehicleControlBillingExpirationDate())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_EXPIRATION_DATE);
        assertThat(testVehicleControlBilling.getVehicleControlBillingPaymentDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_PAYMENT_DATE);
        assertThat(testVehicleControlBilling.getVehicleControlBillingSellerCommission())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_SELLER_COMMISSION);
        assertThat(testVehicleControlBilling.getVehicleControlBillingDriverCommission())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_DRIVER_COMMISSION);
        assertThat(testVehicleControlBilling.getVehicleControlBillingAmount()).isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_AMOUNT);
        assertThat(testVehicleControlBilling.getVehicleControlBillingTotalValue()).isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_TOTAL_VALUE);
        assertThat(testVehicleControlBilling.getVehicleControlBillingInsuranceDiscount())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_INSURANCE_DISCOUNT);
        assertThat(testVehicleControlBilling.getVehicleControlBillingCustomerBank())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_CUSTOMER_BANK);
        assertThat(testVehicleControlBilling.getVehicleControlBillingAnticipate()).isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_ANTICIPATE);
        assertThat(testVehicleControlBilling.getVehicleControlBillingManifest()).isEqualTo(UPDATED_VEHICLE_CONTROL_BILLING_MANIFEST);
    }

    @Test
    @Transactional
    void patchNonExistingVehicleControlBilling() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlBillingRepository.findAll().size();
        vehicleControlBilling.setId(count.incrementAndGet());

        // Create the VehicleControlBilling
        VehicleControlBillingDTO vehicleControlBillingDTO = vehicleControlBillingMapper.toDto(vehicleControlBilling);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleControlBillingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vehicleControlBillingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlBillingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlBilling in the database
        List<VehicleControlBilling> vehicleControlBillingList = vehicleControlBillingRepository.findAll();
        assertThat(vehicleControlBillingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVehicleControlBilling() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlBillingRepository.findAll().size();
        vehicleControlBilling.setId(count.incrementAndGet());

        // Create the VehicleControlBilling
        VehicleControlBillingDTO vehicleControlBillingDTO = vehicleControlBillingMapper.toDto(vehicleControlBilling);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlBillingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlBillingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlBilling in the database
        List<VehicleControlBilling> vehicleControlBillingList = vehicleControlBillingRepository.findAll();
        assertThat(vehicleControlBillingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVehicleControlBilling() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlBillingRepository.findAll().size();
        vehicleControlBilling.setId(count.incrementAndGet());

        // Create the VehicleControlBilling
        VehicleControlBillingDTO vehicleControlBillingDTO = vehicleControlBillingMapper.toDto(vehicleControlBilling);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlBillingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlBillingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleControlBilling in the database
        List<VehicleControlBilling> vehicleControlBillingList = vehicleControlBillingRepository.findAll();
        assertThat(vehicleControlBillingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVehicleControlBilling() throws Exception {
        // Initialize the database
        vehicleControlBillingRepository.saveAndFlush(vehicleControlBilling);

        int databaseSizeBeforeDelete = vehicleControlBillingRepository.findAll().size();

        // Delete the vehicleControlBilling
        restVehicleControlBillingMockMvc
            .perform(delete(ENTITY_API_URL_ID, vehicleControlBilling.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehicleControlBilling> vehicleControlBillingList = vehicleControlBillingRepository.findAll();
        assertThat(vehicleControlBillingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
