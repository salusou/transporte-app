package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Affiliates;
import com.genesisoft.transporte.domain.Fees;
import com.genesisoft.transporte.domain.VehicleControlBilling;
import com.genesisoft.transporte.repository.FeesRepository;
import com.genesisoft.transporte.service.criteria.FeesCriteria;
import com.genesisoft.transporte.service.dto.FeesDTO;
import com.genesisoft.transporte.service.mapper.FeesMapper;
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
 * Integration tests for the {@link FeesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FeesResourceIT {

    private static final LocalDate DEFAULT_FEE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FEE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FEE_DATE = LocalDate.ofEpochDay(-1L);

    private static final Float DEFAULT_FEE_DRIVER_COMMISSION = 1F;
    private static final Float UPDATED_FEE_DRIVER_COMMISSION = 2F;
    private static final Float SMALLER_FEE_DRIVER_COMMISSION = 1F - 1F;

    private static final Float DEFAULT_FEE_FINANCIAL_COST = 1F;
    private static final Float UPDATED_FEE_FINANCIAL_COST = 2F;
    private static final Float SMALLER_FEE_FINANCIAL_COST = 1F - 1F;

    private static final Float DEFAULT_FEE_TAXES = 1F;
    private static final Float UPDATED_FEE_TAXES = 2F;
    private static final Float SMALLER_FEE_TAXES = 1F - 1F;

    private static final String DEFAULT_FEE_DESCRIPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_FEE_DESCRIPTIONS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FeesRepository feesRepository;

    @Autowired
    private FeesMapper feesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeesMockMvc;

    private Fees fees;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fees createEntity(EntityManager em) {
        Fees fees = new Fees()
            .feeDate(DEFAULT_FEE_DATE)
            .feeDriverCommission(DEFAULT_FEE_DRIVER_COMMISSION)
            .feeFinancialCost(DEFAULT_FEE_FINANCIAL_COST)
            .feeTaxes(DEFAULT_FEE_TAXES)
            .feeDescriptions(DEFAULT_FEE_DESCRIPTIONS);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        fees.setAffiliates(affiliates);
        return fees;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fees createUpdatedEntity(EntityManager em) {
        Fees fees = new Fees()
            .feeDate(UPDATED_FEE_DATE)
            .feeDriverCommission(UPDATED_FEE_DRIVER_COMMISSION)
            .feeFinancialCost(UPDATED_FEE_FINANCIAL_COST)
            .feeTaxes(UPDATED_FEE_TAXES)
            .feeDescriptions(UPDATED_FEE_DESCRIPTIONS);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createUpdatedEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        fees.setAffiliates(affiliates);
        return fees;
    }

    @BeforeEach
    public void initTest() {
        fees = createEntity(em);
    }

    @Test
    @Transactional
    void createFees() throws Exception {
        int databaseSizeBeforeCreate = feesRepository.findAll().size();
        // Create the Fees
        FeesDTO feesDTO = feesMapper.toDto(fees);
        restFeesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feesDTO)))
            .andExpect(status().isCreated());

        // Validate the Fees in the database
        List<Fees> feesList = feesRepository.findAll();
        assertThat(feesList).hasSize(databaseSizeBeforeCreate + 1);
        Fees testFees = feesList.get(feesList.size() - 1);
        assertThat(testFees.getFeeDate()).isEqualTo(DEFAULT_FEE_DATE);
        assertThat(testFees.getFeeDriverCommission()).isEqualTo(DEFAULT_FEE_DRIVER_COMMISSION);
        assertThat(testFees.getFeeFinancialCost()).isEqualTo(DEFAULT_FEE_FINANCIAL_COST);
        assertThat(testFees.getFeeTaxes()).isEqualTo(DEFAULT_FEE_TAXES);
        assertThat(testFees.getFeeDescriptions()).isEqualTo(DEFAULT_FEE_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void createFeesWithExistingId() throws Exception {
        // Create the Fees with an existing ID
        fees.setId(1L);
        FeesDTO feesDTO = feesMapper.toDto(fees);

        int databaseSizeBeforeCreate = feesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fees in the database
        List<Fees> feesList = feesRepository.findAll();
        assertThat(feesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFeeDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = feesRepository.findAll().size();
        // set the field null
        fees.setFeeDate(null);

        // Create the Fees, which fails.
        FeesDTO feesDTO = feesMapper.toDto(fees);

        restFeesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feesDTO)))
            .andExpect(status().isBadRequest());

        List<Fees> feesList = feesRepository.findAll();
        assertThat(feesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFeeDriverCommissionIsRequired() throws Exception {
        int databaseSizeBeforeTest = feesRepository.findAll().size();
        // set the field null
        fees.setFeeDriverCommission(null);

        // Create the Fees, which fails.
        FeesDTO feesDTO = feesMapper.toDto(fees);

        restFeesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feesDTO)))
            .andExpect(status().isBadRequest());

        List<Fees> feesList = feesRepository.findAll();
        assertThat(feesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFeeFinancialCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = feesRepository.findAll().size();
        // set the field null
        fees.setFeeFinancialCost(null);

        // Create the Fees, which fails.
        FeesDTO feesDTO = feesMapper.toDto(fees);

        restFeesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feesDTO)))
            .andExpect(status().isBadRequest());

        List<Fees> feesList = feesRepository.findAll();
        assertThat(feesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFeeTaxesIsRequired() throws Exception {
        int databaseSizeBeforeTest = feesRepository.findAll().size();
        // set the field null
        fees.setFeeTaxes(null);

        // Create the Fees, which fails.
        FeesDTO feesDTO = feesMapper.toDto(fees);

        restFeesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feesDTO)))
            .andExpect(status().isBadRequest());

        List<Fees> feesList = feesRepository.findAll();
        assertThat(feesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFees() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList
        restFeesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fees.getId().intValue())))
            .andExpect(jsonPath("$.[*].feeDate").value(hasItem(DEFAULT_FEE_DATE.toString())))
            .andExpect(jsonPath("$.[*].feeDriverCommission").value(hasItem(DEFAULT_FEE_DRIVER_COMMISSION.doubleValue())))
            .andExpect(jsonPath("$.[*].feeFinancialCost").value(hasItem(DEFAULT_FEE_FINANCIAL_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].feeTaxes").value(hasItem(DEFAULT_FEE_TAXES.doubleValue())))
            .andExpect(jsonPath("$.[*].feeDescriptions").value(hasItem(DEFAULT_FEE_DESCRIPTIONS)));
    }

    @Test
    @Transactional
    void getFees() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get the fees
        restFeesMockMvc
            .perform(get(ENTITY_API_URL_ID, fees.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fees.getId().intValue()))
            .andExpect(jsonPath("$.feeDate").value(DEFAULT_FEE_DATE.toString()))
            .andExpect(jsonPath("$.feeDriverCommission").value(DEFAULT_FEE_DRIVER_COMMISSION.doubleValue()))
            .andExpect(jsonPath("$.feeFinancialCost").value(DEFAULT_FEE_FINANCIAL_COST.doubleValue()))
            .andExpect(jsonPath("$.feeTaxes").value(DEFAULT_FEE_TAXES.doubleValue()))
            .andExpect(jsonPath("$.feeDescriptions").value(DEFAULT_FEE_DESCRIPTIONS));
    }

    @Test
    @Transactional
    void getFeesByIdFiltering() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        Long id = fees.getId();

        defaultFeesShouldBeFound("id.equals=" + id);
        defaultFeesShouldNotBeFound("id.notEquals=" + id);

        defaultFeesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFeesShouldNotBeFound("id.greaterThan=" + id);

        defaultFeesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFeesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFeesByFeeDateIsEqualToSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDate equals to DEFAULT_FEE_DATE
        defaultFeesShouldBeFound("feeDate.equals=" + DEFAULT_FEE_DATE);

        // Get all the feesList where feeDate equals to UPDATED_FEE_DATE
        defaultFeesShouldNotBeFound("feeDate.equals=" + UPDATED_FEE_DATE);
    }

    @Test
    @Transactional
    void getAllFeesByFeeDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDate not equals to DEFAULT_FEE_DATE
        defaultFeesShouldNotBeFound("feeDate.notEquals=" + DEFAULT_FEE_DATE);

        // Get all the feesList where feeDate not equals to UPDATED_FEE_DATE
        defaultFeesShouldBeFound("feeDate.notEquals=" + UPDATED_FEE_DATE);
    }

    @Test
    @Transactional
    void getAllFeesByFeeDateIsInShouldWork() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDate in DEFAULT_FEE_DATE or UPDATED_FEE_DATE
        defaultFeesShouldBeFound("feeDate.in=" + DEFAULT_FEE_DATE + "," + UPDATED_FEE_DATE);

        // Get all the feesList where feeDate equals to UPDATED_FEE_DATE
        defaultFeesShouldNotBeFound("feeDate.in=" + UPDATED_FEE_DATE);
    }

    @Test
    @Transactional
    void getAllFeesByFeeDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDate is not null
        defaultFeesShouldBeFound("feeDate.specified=true");

        // Get all the feesList where feeDate is null
        defaultFeesShouldNotBeFound("feeDate.specified=false");
    }

    @Test
    @Transactional
    void getAllFeesByFeeDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDate is greater than or equal to DEFAULT_FEE_DATE
        defaultFeesShouldBeFound("feeDate.greaterThanOrEqual=" + DEFAULT_FEE_DATE);

        // Get all the feesList where feeDate is greater than or equal to UPDATED_FEE_DATE
        defaultFeesShouldNotBeFound("feeDate.greaterThanOrEqual=" + UPDATED_FEE_DATE);
    }

    @Test
    @Transactional
    void getAllFeesByFeeDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDate is less than or equal to DEFAULT_FEE_DATE
        defaultFeesShouldBeFound("feeDate.lessThanOrEqual=" + DEFAULT_FEE_DATE);

        // Get all the feesList where feeDate is less than or equal to SMALLER_FEE_DATE
        defaultFeesShouldNotBeFound("feeDate.lessThanOrEqual=" + SMALLER_FEE_DATE);
    }

    @Test
    @Transactional
    void getAllFeesByFeeDateIsLessThanSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDate is less than DEFAULT_FEE_DATE
        defaultFeesShouldNotBeFound("feeDate.lessThan=" + DEFAULT_FEE_DATE);

        // Get all the feesList where feeDate is less than UPDATED_FEE_DATE
        defaultFeesShouldBeFound("feeDate.lessThan=" + UPDATED_FEE_DATE);
    }

    @Test
    @Transactional
    void getAllFeesByFeeDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDate is greater than DEFAULT_FEE_DATE
        defaultFeesShouldNotBeFound("feeDate.greaterThan=" + DEFAULT_FEE_DATE);

        // Get all the feesList where feeDate is greater than SMALLER_FEE_DATE
        defaultFeesShouldBeFound("feeDate.greaterThan=" + SMALLER_FEE_DATE);
    }

    @Test
    @Transactional
    void getAllFeesByFeeDriverCommissionIsEqualToSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDriverCommission equals to DEFAULT_FEE_DRIVER_COMMISSION
        defaultFeesShouldBeFound("feeDriverCommission.equals=" + DEFAULT_FEE_DRIVER_COMMISSION);

        // Get all the feesList where feeDriverCommission equals to UPDATED_FEE_DRIVER_COMMISSION
        defaultFeesShouldNotBeFound("feeDriverCommission.equals=" + UPDATED_FEE_DRIVER_COMMISSION);
    }

    @Test
    @Transactional
    void getAllFeesByFeeDriverCommissionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDriverCommission not equals to DEFAULT_FEE_DRIVER_COMMISSION
        defaultFeesShouldNotBeFound("feeDriverCommission.notEquals=" + DEFAULT_FEE_DRIVER_COMMISSION);

        // Get all the feesList where feeDriverCommission not equals to UPDATED_FEE_DRIVER_COMMISSION
        defaultFeesShouldBeFound("feeDriverCommission.notEquals=" + UPDATED_FEE_DRIVER_COMMISSION);
    }

    @Test
    @Transactional
    void getAllFeesByFeeDriverCommissionIsInShouldWork() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDriverCommission in DEFAULT_FEE_DRIVER_COMMISSION or UPDATED_FEE_DRIVER_COMMISSION
        defaultFeesShouldBeFound("feeDriverCommission.in=" + DEFAULT_FEE_DRIVER_COMMISSION + "," + UPDATED_FEE_DRIVER_COMMISSION);

        // Get all the feesList where feeDriverCommission equals to UPDATED_FEE_DRIVER_COMMISSION
        defaultFeesShouldNotBeFound("feeDriverCommission.in=" + UPDATED_FEE_DRIVER_COMMISSION);
    }

    @Test
    @Transactional
    void getAllFeesByFeeDriverCommissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDriverCommission is not null
        defaultFeesShouldBeFound("feeDriverCommission.specified=true");

        // Get all the feesList where feeDriverCommission is null
        defaultFeesShouldNotBeFound("feeDriverCommission.specified=false");
    }

    @Test
    @Transactional
    void getAllFeesByFeeDriverCommissionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDriverCommission is greater than or equal to DEFAULT_FEE_DRIVER_COMMISSION
        defaultFeesShouldBeFound("feeDriverCommission.greaterThanOrEqual=" + DEFAULT_FEE_DRIVER_COMMISSION);

        // Get all the feesList where feeDriverCommission is greater than or equal to UPDATED_FEE_DRIVER_COMMISSION
        defaultFeesShouldNotBeFound("feeDriverCommission.greaterThanOrEqual=" + UPDATED_FEE_DRIVER_COMMISSION);
    }

    @Test
    @Transactional
    void getAllFeesByFeeDriverCommissionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDriverCommission is less than or equal to DEFAULT_FEE_DRIVER_COMMISSION
        defaultFeesShouldBeFound("feeDriverCommission.lessThanOrEqual=" + DEFAULT_FEE_DRIVER_COMMISSION);

        // Get all the feesList where feeDriverCommission is less than or equal to SMALLER_FEE_DRIVER_COMMISSION
        defaultFeesShouldNotBeFound("feeDriverCommission.lessThanOrEqual=" + SMALLER_FEE_DRIVER_COMMISSION);
    }

    @Test
    @Transactional
    void getAllFeesByFeeDriverCommissionIsLessThanSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDriverCommission is less than DEFAULT_FEE_DRIVER_COMMISSION
        defaultFeesShouldNotBeFound("feeDriverCommission.lessThan=" + DEFAULT_FEE_DRIVER_COMMISSION);

        // Get all the feesList where feeDriverCommission is less than UPDATED_FEE_DRIVER_COMMISSION
        defaultFeesShouldBeFound("feeDriverCommission.lessThan=" + UPDATED_FEE_DRIVER_COMMISSION);
    }

    @Test
    @Transactional
    void getAllFeesByFeeDriverCommissionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDriverCommission is greater than DEFAULT_FEE_DRIVER_COMMISSION
        defaultFeesShouldNotBeFound("feeDriverCommission.greaterThan=" + DEFAULT_FEE_DRIVER_COMMISSION);

        // Get all the feesList where feeDriverCommission is greater than SMALLER_FEE_DRIVER_COMMISSION
        defaultFeesShouldBeFound("feeDriverCommission.greaterThan=" + SMALLER_FEE_DRIVER_COMMISSION);
    }

    @Test
    @Transactional
    void getAllFeesByFeeFinancialCostIsEqualToSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeFinancialCost equals to DEFAULT_FEE_FINANCIAL_COST
        defaultFeesShouldBeFound("feeFinancialCost.equals=" + DEFAULT_FEE_FINANCIAL_COST);

        // Get all the feesList where feeFinancialCost equals to UPDATED_FEE_FINANCIAL_COST
        defaultFeesShouldNotBeFound("feeFinancialCost.equals=" + UPDATED_FEE_FINANCIAL_COST);
    }

    @Test
    @Transactional
    void getAllFeesByFeeFinancialCostIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeFinancialCost not equals to DEFAULT_FEE_FINANCIAL_COST
        defaultFeesShouldNotBeFound("feeFinancialCost.notEquals=" + DEFAULT_FEE_FINANCIAL_COST);

        // Get all the feesList where feeFinancialCost not equals to UPDATED_FEE_FINANCIAL_COST
        defaultFeesShouldBeFound("feeFinancialCost.notEquals=" + UPDATED_FEE_FINANCIAL_COST);
    }

    @Test
    @Transactional
    void getAllFeesByFeeFinancialCostIsInShouldWork() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeFinancialCost in DEFAULT_FEE_FINANCIAL_COST or UPDATED_FEE_FINANCIAL_COST
        defaultFeesShouldBeFound("feeFinancialCost.in=" + DEFAULT_FEE_FINANCIAL_COST + "," + UPDATED_FEE_FINANCIAL_COST);

        // Get all the feesList where feeFinancialCost equals to UPDATED_FEE_FINANCIAL_COST
        defaultFeesShouldNotBeFound("feeFinancialCost.in=" + UPDATED_FEE_FINANCIAL_COST);
    }

    @Test
    @Transactional
    void getAllFeesByFeeFinancialCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeFinancialCost is not null
        defaultFeesShouldBeFound("feeFinancialCost.specified=true");

        // Get all the feesList where feeFinancialCost is null
        defaultFeesShouldNotBeFound("feeFinancialCost.specified=false");
    }

    @Test
    @Transactional
    void getAllFeesByFeeFinancialCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeFinancialCost is greater than or equal to DEFAULT_FEE_FINANCIAL_COST
        defaultFeesShouldBeFound("feeFinancialCost.greaterThanOrEqual=" + DEFAULT_FEE_FINANCIAL_COST);

        // Get all the feesList where feeFinancialCost is greater than or equal to UPDATED_FEE_FINANCIAL_COST
        defaultFeesShouldNotBeFound("feeFinancialCost.greaterThanOrEqual=" + UPDATED_FEE_FINANCIAL_COST);
    }

    @Test
    @Transactional
    void getAllFeesByFeeFinancialCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeFinancialCost is less than or equal to DEFAULT_FEE_FINANCIAL_COST
        defaultFeesShouldBeFound("feeFinancialCost.lessThanOrEqual=" + DEFAULT_FEE_FINANCIAL_COST);

        // Get all the feesList where feeFinancialCost is less than or equal to SMALLER_FEE_FINANCIAL_COST
        defaultFeesShouldNotBeFound("feeFinancialCost.lessThanOrEqual=" + SMALLER_FEE_FINANCIAL_COST);
    }

    @Test
    @Transactional
    void getAllFeesByFeeFinancialCostIsLessThanSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeFinancialCost is less than DEFAULT_FEE_FINANCIAL_COST
        defaultFeesShouldNotBeFound("feeFinancialCost.lessThan=" + DEFAULT_FEE_FINANCIAL_COST);

        // Get all the feesList where feeFinancialCost is less than UPDATED_FEE_FINANCIAL_COST
        defaultFeesShouldBeFound("feeFinancialCost.lessThan=" + UPDATED_FEE_FINANCIAL_COST);
    }

    @Test
    @Transactional
    void getAllFeesByFeeFinancialCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeFinancialCost is greater than DEFAULT_FEE_FINANCIAL_COST
        defaultFeesShouldNotBeFound("feeFinancialCost.greaterThan=" + DEFAULT_FEE_FINANCIAL_COST);

        // Get all the feesList where feeFinancialCost is greater than SMALLER_FEE_FINANCIAL_COST
        defaultFeesShouldBeFound("feeFinancialCost.greaterThan=" + SMALLER_FEE_FINANCIAL_COST);
    }

    @Test
    @Transactional
    void getAllFeesByFeeTaxesIsEqualToSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeTaxes equals to DEFAULT_FEE_TAXES
        defaultFeesShouldBeFound("feeTaxes.equals=" + DEFAULT_FEE_TAXES);

        // Get all the feesList where feeTaxes equals to UPDATED_FEE_TAXES
        defaultFeesShouldNotBeFound("feeTaxes.equals=" + UPDATED_FEE_TAXES);
    }

    @Test
    @Transactional
    void getAllFeesByFeeTaxesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeTaxes not equals to DEFAULT_FEE_TAXES
        defaultFeesShouldNotBeFound("feeTaxes.notEquals=" + DEFAULT_FEE_TAXES);

        // Get all the feesList where feeTaxes not equals to UPDATED_FEE_TAXES
        defaultFeesShouldBeFound("feeTaxes.notEquals=" + UPDATED_FEE_TAXES);
    }

    @Test
    @Transactional
    void getAllFeesByFeeTaxesIsInShouldWork() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeTaxes in DEFAULT_FEE_TAXES or UPDATED_FEE_TAXES
        defaultFeesShouldBeFound("feeTaxes.in=" + DEFAULT_FEE_TAXES + "," + UPDATED_FEE_TAXES);

        // Get all the feesList where feeTaxes equals to UPDATED_FEE_TAXES
        defaultFeesShouldNotBeFound("feeTaxes.in=" + UPDATED_FEE_TAXES);
    }

    @Test
    @Transactional
    void getAllFeesByFeeTaxesIsNullOrNotNull() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeTaxes is not null
        defaultFeesShouldBeFound("feeTaxes.specified=true");

        // Get all the feesList where feeTaxes is null
        defaultFeesShouldNotBeFound("feeTaxes.specified=false");
    }

    @Test
    @Transactional
    void getAllFeesByFeeTaxesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeTaxes is greater than or equal to DEFAULT_FEE_TAXES
        defaultFeesShouldBeFound("feeTaxes.greaterThanOrEqual=" + DEFAULT_FEE_TAXES);

        // Get all the feesList where feeTaxes is greater than or equal to UPDATED_FEE_TAXES
        defaultFeesShouldNotBeFound("feeTaxes.greaterThanOrEqual=" + UPDATED_FEE_TAXES);
    }

    @Test
    @Transactional
    void getAllFeesByFeeTaxesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeTaxes is less than or equal to DEFAULT_FEE_TAXES
        defaultFeesShouldBeFound("feeTaxes.lessThanOrEqual=" + DEFAULT_FEE_TAXES);

        // Get all the feesList where feeTaxes is less than or equal to SMALLER_FEE_TAXES
        defaultFeesShouldNotBeFound("feeTaxes.lessThanOrEqual=" + SMALLER_FEE_TAXES);
    }

    @Test
    @Transactional
    void getAllFeesByFeeTaxesIsLessThanSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeTaxes is less than DEFAULT_FEE_TAXES
        defaultFeesShouldNotBeFound("feeTaxes.lessThan=" + DEFAULT_FEE_TAXES);

        // Get all the feesList where feeTaxes is less than UPDATED_FEE_TAXES
        defaultFeesShouldBeFound("feeTaxes.lessThan=" + UPDATED_FEE_TAXES);
    }

    @Test
    @Transactional
    void getAllFeesByFeeTaxesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeTaxes is greater than DEFAULT_FEE_TAXES
        defaultFeesShouldNotBeFound("feeTaxes.greaterThan=" + DEFAULT_FEE_TAXES);

        // Get all the feesList where feeTaxes is greater than SMALLER_FEE_TAXES
        defaultFeesShouldBeFound("feeTaxes.greaterThan=" + SMALLER_FEE_TAXES);
    }

    @Test
    @Transactional
    void getAllFeesByFeeDescriptionsIsEqualToSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDescriptions equals to DEFAULT_FEE_DESCRIPTIONS
        defaultFeesShouldBeFound("feeDescriptions.equals=" + DEFAULT_FEE_DESCRIPTIONS);

        // Get all the feesList where feeDescriptions equals to UPDATED_FEE_DESCRIPTIONS
        defaultFeesShouldNotBeFound("feeDescriptions.equals=" + UPDATED_FEE_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void getAllFeesByFeeDescriptionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDescriptions not equals to DEFAULT_FEE_DESCRIPTIONS
        defaultFeesShouldNotBeFound("feeDescriptions.notEquals=" + DEFAULT_FEE_DESCRIPTIONS);

        // Get all the feesList where feeDescriptions not equals to UPDATED_FEE_DESCRIPTIONS
        defaultFeesShouldBeFound("feeDescriptions.notEquals=" + UPDATED_FEE_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void getAllFeesByFeeDescriptionsIsInShouldWork() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDescriptions in DEFAULT_FEE_DESCRIPTIONS or UPDATED_FEE_DESCRIPTIONS
        defaultFeesShouldBeFound("feeDescriptions.in=" + DEFAULT_FEE_DESCRIPTIONS + "," + UPDATED_FEE_DESCRIPTIONS);

        // Get all the feesList where feeDescriptions equals to UPDATED_FEE_DESCRIPTIONS
        defaultFeesShouldNotBeFound("feeDescriptions.in=" + UPDATED_FEE_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void getAllFeesByFeeDescriptionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDescriptions is not null
        defaultFeesShouldBeFound("feeDescriptions.specified=true");

        // Get all the feesList where feeDescriptions is null
        defaultFeesShouldNotBeFound("feeDescriptions.specified=false");
    }

    @Test
    @Transactional
    void getAllFeesByFeeDescriptionsContainsSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDescriptions contains DEFAULT_FEE_DESCRIPTIONS
        defaultFeesShouldBeFound("feeDescriptions.contains=" + DEFAULT_FEE_DESCRIPTIONS);

        // Get all the feesList where feeDescriptions contains UPDATED_FEE_DESCRIPTIONS
        defaultFeesShouldNotBeFound("feeDescriptions.contains=" + UPDATED_FEE_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void getAllFeesByFeeDescriptionsNotContainsSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        // Get all the feesList where feeDescriptions does not contain DEFAULT_FEE_DESCRIPTIONS
        defaultFeesShouldNotBeFound("feeDescriptions.doesNotContain=" + DEFAULT_FEE_DESCRIPTIONS);

        // Get all the feesList where feeDescriptions does not contain UPDATED_FEE_DESCRIPTIONS
        defaultFeesShouldBeFound("feeDescriptions.doesNotContain=" + UPDATED_FEE_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void getAllFeesByVehicleControlBillingIsEqualToSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);
        VehicleControlBilling vehicleControlBilling = VehicleControlBillingResourceIT.createEntity(em);
        em.persist(vehicleControlBilling);
        em.flush();
        fees.addVehicleControlBilling(vehicleControlBilling);
        feesRepository.saveAndFlush(fees);
        Long vehicleControlBillingId = vehicleControlBilling.getId();

        // Get all the feesList where vehicleControlBilling equals to vehicleControlBillingId
        defaultFeesShouldBeFound("vehicleControlBillingId.equals=" + vehicleControlBillingId);

        // Get all the feesList where vehicleControlBilling equals to (vehicleControlBillingId + 1)
        defaultFeesShouldNotBeFound("vehicleControlBillingId.equals=" + (vehicleControlBillingId + 1));
    }

    @Test
    @Transactional
    void getAllFeesByAffiliatesIsEqualToSomething() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);
        Affiliates affiliates = AffiliatesResourceIT.createEntity(em);
        em.persist(affiliates);
        em.flush();
        fees.setAffiliates(affiliates);
        feesRepository.saveAndFlush(fees);
        Long affiliatesId = affiliates.getId();

        // Get all the feesList where affiliates equals to affiliatesId
        defaultFeesShouldBeFound("affiliatesId.equals=" + affiliatesId);

        // Get all the feesList where affiliates equals to (affiliatesId + 1)
        defaultFeesShouldNotBeFound("affiliatesId.equals=" + (affiliatesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFeesShouldBeFound(String filter) throws Exception {
        restFeesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fees.getId().intValue())))
            .andExpect(jsonPath("$.[*].feeDate").value(hasItem(DEFAULT_FEE_DATE.toString())))
            .andExpect(jsonPath("$.[*].feeDriverCommission").value(hasItem(DEFAULT_FEE_DRIVER_COMMISSION.doubleValue())))
            .andExpect(jsonPath("$.[*].feeFinancialCost").value(hasItem(DEFAULT_FEE_FINANCIAL_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].feeTaxes").value(hasItem(DEFAULT_FEE_TAXES.doubleValue())))
            .andExpect(jsonPath("$.[*].feeDescriptions").value(hasItem(DEFAULT_FEE_DESCRIPTIONS)));

        // Check, that the count call also returns 1
        restFeesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFeesShouldNotBeFound(String filter) throws Exception {
        restFeesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFeesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFees() throws Exception {
        // Get the fees
        restFeesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFees() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        int databaseSizeBeforeUpdate = feesRepository.findAll().size();

        // Update the fees
        Fees updatedFees = feesRepository.findById(fees.getId()).get();
        // Disconnect from session so that the updates on updatedFees are not directly saved in db
        em.detach(updatedFees);
        updatedFees
            .feeDate(UPDATED_FEE_DATE)
            .feeDriverCommission(UPDATED_FEE_DRIVER_COMMISSION)
            .feeFinancialCost(UPDATED_FEE_FINANCIAL_COST)
            .feeTaxes(UPDATED_FEE_TAXES)
            .feeDescriptions(UPDATED_FEE_DESCRIPTIONS);
        FeesDTO feesDTO = feesMapper.toDto(updatedFees);

        restFeesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, feesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Fees in the database
        List<Fees> feesList = feesRepository.findAll();
        assertThat(feesList).hasSize(databaseSizeBeforeUpdate);
        Fees testFees = feesList.get(feesList.size() - 1);
        assertThat(testFees.getFeeDate()).isEqualTo(UPDATED_FEE_DATE);
        assertThat(testFees.getFeeDriverCommission()).isEqualTo(UPDATED_FEE_DRIVER_COMMISSION);
        assertThat(testFees.getFeeFinancialCost()).isEqualTo(UPDATED_FEE_FINANCIAL_COST);
        assertThat(testFees.getFeeTaxes()).isEqualTo(UPDATED_FEE_TAXES);
        assertThat(testFees.getFeeDescriptions()).isEqualTo(UPDATED_FEE_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void putNonExistingFees() throws Exception {
        int databaseSizeBeforeUpdate = feesRepository.findAll().size();
        fees.setId(count.incrementAndGet());

        // Create the Fees
        FeesDTO feesDTO = feesMapper.toDto(fees);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, feesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fees in the database
        List<Fees> feesList = feesRepository.findAll();
        assertThat(feesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFees() throws Exception {
        int databaseSizeBeforeUpdate = feesRepository.findAll().size();
        fees.setId(count.incrementAndGet());

        // Create the Fees
        FeesDTO feesDTO = feesMapper.toDto(fees);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fees in the database
        List<Fees> feesList = feesRepository.findAll();
        assertThat(feesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFees() throws Exception {
        int databaseSizeBeforeUpdate = feesRepository.findAll().size();
        fees.setId(count.incrementAndGet());

        // Create the Fees
        FeesDTO feesDTO = feesMapper.toDto(fees);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fees in the database
        List<Fees> feesList = feesRepository.findAll();
        assertThat(feesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFeesWithPatch() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        int databaseSizeBeforeUpdate = feesRepository.findAll().size();

        // Update the fees using partial update
        Fees partialUpdatedFees = new Fees();
        partialUpdatedFees.setId(fees.getId());

        partialUpdatedFees.feeDriverCommission(UPDATED_FEE_DRIVER_COMMISSION).feeTaxes(UPDATED_FEE_TAXES);

        restFeesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFees.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFees))
            )
            .andExpect(status().isOk());

        // Validate the Fees in the database
        List<Fees> feesList = feesRepository.findAll();
        assertThat(feesList).hasSize(databaseSizeBeforeUpdate);
        Fees testFees = feesList.get(feesList.size() - 1);
        assertThat(testFees.getFeeDate()).isEqualTo(DEFAULT_FEE_DATE);
        assertThat(testFees.getFeeDriverCommission()).isEqualTo(UPDATED_FEE_DRIVER_COMMISSION);
        assertThat(testFees.getFeeFinancialCost()).isEqualTo(DEFAULT_FEE_FINANCIAL_COST);
        assertThat(testFees.getFeeTaxes()).isEqualTo(UPDATED_FEE_TAXES);
        assertThat(testFees.getFeeDescriptions()).isEqualTo(DEFAULT_FEE_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void fullUpdateFeesWithPatch() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        int databaseSizeBeforeUpdate = feesRepository.findAll().size();

        // Update the fees using partial update
        Fees partialUpdatedFees = new Fees();
        partialUpdatedFees.setId(fees.getId());

        partialUpdatedFees
            .feeDate(UPDATED_FEE_DATE)
            .feeDriverCommission(UPDATED_FEE_DRIVER_COMMISSION)
            .feeFinancialCost(UPDATED_FEE_FINANCIAL_COST)
            .feeTaxes(UPDATED_FEE_TAXES)
            .feeDescriptions(UPDATED_FEE_DESCRIPTIONS);

        restFeesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFees.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFees))
            )
            .andExpect(status().isOk());

        // Validate the Fees in the database
        List<Fees> feesList = feesRepository.findAll();
        assertThat(feesList).hasSize(databaseSizeBeforeUpdate);
        Fees testFees = feesList.get(feesList.size() - 1);
        assertThat(testFees.getFeeDate()).isEqualTo(UPDATED_FEE_DATE);
        assertThat(testFees.getFeeDriverCommission()).isEqualTo(UPDATED_FEE_DRIVER_COMMISSION);
        assertThat(testFees.getFeeFinancialCost()).isEqualTo(UPDATED_FEE_FINANCIAL_COST);
        assertThat(testFees.getFeeTaxes()).isEqualTo(UPDATED_FEE_TAXES);
        assertThat(testFees.getFeeDescriptions()).isEqualTo(UPDATED_FEE_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void patchNonExistingFees() throws Exception {
        int databaseSizeBeforeUpdate = feesRepository.findAll().size();
        fees.setId(count.incrementAndGet());

        // Create the Fees
        FeesDTO feesDTO = feesMapper.toDto(fees);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, feesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fees in the database
        List<Fees> feesList = feesRepository.findAll();
        assertThat(feesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFees() throws Exception {
        int databaseSizeBeforeUpdate = feesRepository.findAll().size();
        fees.setId(count.incrementAndGet());

        // Create the Fees
        FeesDTO feesDTO = feesMapper.toDto(fees);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fees in the database
        List<Fees> feesList = feesRepository.findAll();
        assertThat(feesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFees() throws Exception {
        int databaseSizeBeforeUpdate = feesRepository.findAll().size();
        fees.setId(count.incrementAndGet());

        // Create the Fees
        FeesDTO feesDTO = feesMapper.toDto(fees);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(feesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fees in the database
        List<Fees> feesList = feesRepository.findAll();
        assertThat(feesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFees() throws Exception {
        // Initialize the database
        feesRepository.saveAndFlush(fees);

        int databaseSizeBeforeDelete = feesRepository.findAll().size();

        // Delete the fees
        restFeesMockMvc
            .perform(delete(ENTITY_API_URL_ID, fees.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fees> feesList = feesRepository.findAll();
        assertThat(feesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
