package com.genesisoft.transporte.web.rest;

import static com.genesisoft.transporte.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Affiliates;
import com.genesisoft.transporte.domain.Cities;
import com.genesisoft.transporte.domain.CostCenter;
import com.genesisoft.transporte.domain.Customers;
import com.genesisoft.transporte.domain.Employees;
import com.genesisoft.transporte.domain.Housing;
import com.genesisoft.transporte.domain.HousingVehicleItem;
import com.genesisoft.transporte.domain.Parking;
import com.genesisoft.transporte.domain.Status;
import com.genesisoft.transporte.domain.Suppliers;
import com.genesisoft.transporte.repository.HousingRepository;
import com.genesisoft.transporte.service.criteria.HousingCriteria;
import com.genesisoft.transporte.service.dto.HousingDTO;
import com.genesisoft.transporte.service.mapper.HousingMapper;
import java.time.Instant;
import java.time.LocalDate;
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
 * Integration tests for the {@link HousingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HousingResourceIT {

    private static final LocalDate DEFAULT_HOUSING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_HOUSING_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_HOUSING_DATE = LocalDate.ofEpochDay(-1L);

    private static final ZonedDateTime DEFAULT_HOUSING_ENTRANCE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HOUSING_ENTRANCE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_HOUSING_ENTRANCE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_HOUSING_EXIT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HOUSING_EXIT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_HOUSING_EXIT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Integer DEFAULT_HOUSING_RECEIPT_NUMBER = 1;
    private static final Integer UPDATED_HOUSING_RECEIPT_NUMBER = 2;
    private static final Integer SMALLER_HOUSING_RECEIPT_NUMBER = 1 - 1;

    private static final Float DEFAULT_HOUSING_DAILY_PRICE = 1F;
    private static final Float UPDATED_HOUSING_DAILY_PRICE = 2F;
    private static final Float SMALLER_HOUSING_DAILY_PRICE = 1F - 1F;

    private static final String DEFAULT_HOUSING_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_HOUSING_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/housings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HousingRepository housingRepository;

    @Autowired
    private HousingMapper housingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHousingMockMvc;

    private Housing housing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Housing createEntity(EntityManager em) {
        Housing housing = new Housing()
            .housingDate(DEFAULT_HOUSING_DATE)
            .housingEntranceDate(DEFAULT_HOUSING_ENTRANCE_DATE)
            .housingExit(DEFAULT_HOUSING_EXIT)
            .housingReceiptNumber(DEFAULT_HOUSING_RECEIPT_NUMBER)
            .housingDailyPrice(DEFAULT_HOUSING_DAILY_PRICE)
            .housingDescription(DEFAULT_HOUSING_DESCRIPTION);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        housing.setAffiliates(affiliates);
        // Add required entity
        Status status;
        if (TestUtil.findAll(em, Status.class).isEmpty()) {
            status = StatusResourceIT.createEntity(em);
            em.persist(status);
            em.flush();
        } else {
            status = TestUtil.findAll(em, Status.class).get(0);
        }
        housing.setStatus(status);
        // Add required entity
        Customers customers;
        if (TestUtil.findAll(em, Customers.class).isEmpty()) {
            customers = CustomersResourceIT.createEntity(em);
            em.persist(customers);
            em.flush();
        } else {
            customers = TestUtil.findAll(em, Customers.class).get(0);
        }
        housing.setCustomers(customers);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        housing.setEmployees(employees);
        // Add required entity
        Suppliers suppliers;
        if (TestUtil.findAll(em, Suppliers.class).isEmpty()) {
            suppliers = SuppliersResourceIT.createEntity(em);
            em.persist(suppliers);
            em.flush();
        } else {
            suppliers = TestUtil.findAll(em, Suppliers.class).get(0);
        }
        housing.setSuppliers(suppliers);
        // Add required entity
        Cities cities;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            cities = CitiesResourceIT.createEntity(em);
            em.persist(cities);
            em.flush();
        } else {
            cities = TestUtil.findAll(em, Cities.class).get(0);
        }
        housing.setCities(cities);
        return housing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Housing createUpdatedEntity(EntityManager em) {
        Housing housing = new Housing()
            .housingDate(UPDATED_HOUSING_DATE)
            .housingEntranceDate(UPDATED_HOUSING_ENTRANCE_DATE)
            .housingExit(UPDATED_HOUSING_EXIT)
            .housingReceiptNumber(UPDATED_HOUSING_RECEIPT_NUMBER)
            .housingDailyPrice(UPDATED_HOUSING_DAILY_PRICE)
            .housingDescription(UPDATED_HOUSING_DESCRIPTION);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createUpdatedEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        housing.setAffiliates(affiliates);
        // Add required entity
        Status status;
        if (TestUtil.findAll(em, Status.class).isEmpty()) {
            status = StatusResourceIT.createUpdatedEntity(em);
            em.persist(status);
            em.flush();
        } else {
            status = TestUtil.findAll(em, Status.class).get(0);
        }
        housing.setStatus(status);
        // Add required entity
        Customers customers;
        if (TestUtil.findAll(em, Customers.class).isEmpty()) {
            customers = CustomersResourceIT.createUpdatedEntity(em);
            em.persist(customers);
            em.flush();
        } else {
            customers = TestUtil.findAll(em, Customers.class).get(0);
        }
        housing.setCustomers(customers);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        housing.setEmployees(employees);
        // Add required entity
        Suppliers suppliers;
        if (TestUtil.findAll(em, Suppliers.class).isEmpty()) {
            suppliers = SuppliersResourceIT.createUpdatedEntity(em);
            em.persist(suppliers);
            em.flush();
        } else {
            suppliers = TestUtil.findAll(em, Suppliers.class).get(0);
        }
        housing.setSuppliers(suppliers);
        // Add required entity
        Cities cities;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            cities = CitiesResourceIT.createUpdatedEntity(em);
            em.persist(cities);
            em.flush();
        } else {
            cities = TestUtil.findAll(em, Cities.class).get(0);
        }
        housing.setCities(cities);
        return housing;
    }

    @BeforeEach
    public void initTest() {
        housing = createEntity(em);
    }

    @Test
    @Transactional
    void createHousing() throws Exception {
        int databaseSizeBeforeCreate = housingRepository.findAll().size();
        // Create the Housing
        HousingDTO housingDTO = housingMapper.toDto(housing);
        restHousingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(housingDTO)))
            .andExpect(status().isCreated());

        // Validate the Housing in the database
        List<Housing> housingList = housingRepository.findAll();
        assertThat(housingList).hasSize(databaseSizeBeforeCreate + 1);
        Housing testHousing = housingList.get(housingList.size() - 1);
        assertThat(testHousing.getHousingDate()).isEqualTo(DEFAULT_HOUSING_DATE);
        assertThat(testHousing.getHousingEntranceDate()).isEqualTo(DEFAULT_HOUSING_ENTRANCE_DATE);
        assertThat(testHousing.getHousingExit()).isEqualTo(DEFAULT_HOUSING_EXIT);
        assertThat(testHousing.getHousingReceiptNumber()).isEqualTo(DEFAULT_HOUSING_RECEIPT_NUMBER);
        assertThat(testHousing.getHousingDailyPrice()).isEqualTo(DEFAULT_HOUSING_DAILY_PRICE);
        assertThat(testHousing.getHousingDescription()).isEqualTo(DEFAULT_HOUSING_DESCRIPTION);
    }

    @Test
    @Transactional
    void createHousingWithExistingId() throws Exception {
        // Create the Housing with an existing ID
        housing.setId(1L);
        HousingDTO housingDTO = housingMapper.toDto(housing);

        int databaseSizeBeforeCreate = housingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHousingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(housingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Housing in the database
        List<Housing> housingList = housingRepository.findAll();
        assertThat(housingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkHousingDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = housingRepository.findAll().size();
        // set the field null
        housing.setHousingDate(null);

        // Create the Housing, which fails.
        HousingDTO housingDTO = housingMapper.toDto(housing);

        restHousingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(housingDTO)))
            .andExpect(status().isBadRequest());

        List<Housing> housingList = housingRepository.findAll();
        assertThat(housingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHousingEntranceDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = housingRepository.findAll().size();
        // set the field null
        housing.setHousingEntranceDate(null);

        // Create the Housing, which fails.
        HousingDTO housingDTO = housingMapper.toDto(housing);

        restHousingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(housingDTO)))
            .andExpect(status().isBadRequest());

        List<Housing> housingList = housingRepository.findAll();
        assertThat(housingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHousingDailyPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = housingRepository.findAll().size();
        // set the field null
        housing.setHousingDailyPrice(null);

        // Create the Housing, which fails.
        HousingDTO housingDTO = housingMapper.toDto(housing);

        restHousingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(housingDTO)))
            .andExpect(status().isBadRequest());

        List<Housing> housingList = housingRepository.findAll();
        assertThat(housingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHousings() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList
        restHousingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(housing.getId().intValue())))
            .andExpect(jsonPath("$.[*].housingDate").value(hasItem(DEFAULT_HOUSING_DATE.toString())))
            .andExpect(jsonPath("$.[*].housingEntranceDate").value(hasItem(sameInstant(DEFAULT_HOUSING_ENTRANCE_DATE))))
            .andExpect(jsonPath("$.[*].housingExit").value(hasItem(sameInstant(DEFAULT_HOUSING_EXIT))))
            .andExpect(jsonPath("$.[*].housingReceiptNumber").value(hasItem(DEFAULT_HOUSING_RECEIPT_NUMBER)))
            .andExpect(jsonPath("$.[*].housingDailyPrice").value(hasItem(DEFAULT_HOUSING_DAILY_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].housingDescription").value(hasItem(DEFAULT_HOUSING_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getHousing() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get the housing
        restHousingMockMvc
            .perform(get(ENTITY_API_URL_ID, housing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(housing.getId().intValue()))
            .andExpect(jsonPath("$.housingDate").value(DEFAULT_HOUSING_DATE.toString()))
            .andExpect(jsonPath("$.housingEntranceDate").value(sameInstant(DEFAULT_HOUSING_ENTRANCE_DATE)))
            .andExpect(jsonPath("$.housingExit").value(sameInstant(DEFAULT_HOUSING_EXIT)))
            .andExpect(jsonPath("$.housingReceiptNumber").value(DEFAULT_HOUSING_RECEIPT_NUMBER))
            .andExpect(jsonPath("$.housingDailyPrice").value(DEFAULT_HOUSING_DAILY_PRICE.doubleValue()))
            .andExpect(jsonPath("$.housingDescription").value(DEFAULT_HOUSING_DESCRIPTION));
    }

    @Test
    @Transactional
    void getHousingsByIdFiltering() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        Long id = housing.getId();

        defaultHousingShouldBeFound("id.equals=" + id);
        defaultHousingShouldNotBeFound("id.notEquals=" + id);

        defaultHousingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHousingShouldNotBeFound("id.greaterThan=" + id);

        defaultHousingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHousingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDate equals to DEFAULT_HOUSING_DATE
        defaultHousingShouldBeFound("housingDate.equals=" + DEFAULT_HOUSING_DATE);

        // Get all the housingList where housingDate equals to UPDATED_HOUSING_DATE
        defaultHousingShouldNotBeFound("housingDate.equals=" + UPDATED_HOUSING_DATE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDate not equals to DEFAULT_HOUSING_DATE
        defaultHousingShouldNotBeFound("housingDate.notEquals=" + DEFAULT_HOUSING_DATE);

        // Get all the housingList where housingDate not equals to UPDATED_HOUSING_DATE
        defaultHousingShouldBeFound("housingDate.notEquals=" + UPDATED_HOUSING_DATE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDateIsInShouldWork() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDate in DEFAULT_HOUSING_DATE or UPDATED_HOUSING_DATE
        defaultHousingShouldBeFound("housingDate.in=" + DEFAULT_HOUSING_DATE + "," + UPDATED_HOUSING_DATE);

        // Get all the housingList where housingDate equals to UPDATED_HOUSING_DATE
        defaultHousingShouldNotBeFound("housingDate.in=" + UPDATED_HOUSING_DATE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDate is not null
        defaultHousingShouldBeFound("housingDate.specified=true");

        // Get all the housingList where housingDate is null
        defaultHousingShouldNotBeFound("housingDate.specified=false");
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDate is greater than or equal to DEFAULT_HOUSING_DATE
        defaultHousingShouldBeFound("housingDate.greaterThanOrEqual=" + DEFAULT_HOUSING_DATE);

        // Get all the housingList where housingDate is greater than or equal to UPDATED_HOUSING_DATE
        defaultHousingShouldNotBeFound("housingDate.greaterThanOrEqual=" + UPDATED_HOUSING_DATE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDate is less than or equal to DEFAULT_HOUSING_DATE
        defaultHousingShouldBeFound("housingDate.lessThanOrEqual=" + DEFAULT_HOUSING_DATE);

        // Get all the housingList where housingDate is less than or equal to SMALLER_HOUSING_DATE
        defaultHousingShouldNotBeFound("housingDate.lessThanOrEqual=" + SMALLER_HOUSING_DATE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDateIsLessThanSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDate is less than DEFAULT_HOUSING_DATE
        defaultHousingShouldNotBeFound("housingDate.lessThan=" + DEFAULT_HOUSING_DATE);

        // Get all the housingList where housingDate is less than UPDATED_HOUSING_DATE
        defaultHousingShouldBeFound("housingDate.lessThan=" + UPDATED_HOUSING_DATE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDate is greater than DEFAULT_HOUSING_DATE
        defaultHousingShouldNotBeFound("housingDate.greaterThan=" + DEFAULT_HOUSING_DATE);

        // Get all the housingList where housingDate is greater than SMALLER_HOUSING_DATE
        defaultHousingShouldBeFound("housingDate.greaterThan=" + SMALLER_HOUSING_DATE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingEntranceDateIsEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingEntranceDate equals to DEFAULT_HOUSING_ENTRANCE_DATE
        defaultHousingShouldBeFound("housingEntranceDate.equals=" + DEFAULT_HOUSING_ENTRANCE_DATE);

        // Get all the housingList where housingEntranceDate equals to UPDATED_HOUSING_ENTRANCE_DATE
        defaultHousingShouldNotBeFound("housingEntranceDate.equals=" + UPDATED_HOUSING_ENTRANCE_DATE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingEntranceDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingEntranceDate not equals to DEFAULT_HOUSING_ENTRANCE_DATE
        defaultHousingShouldNotBeFound("housingEntranceDate.notEquals=" + DEFAULT_HOUSING_ENTRANCE_DATE);

        // Get all the housingList where housingEntranceDate not equals to UPDATED_HOUSING_ENTRANCE_DATE
        defaultHousingShouldBeFound("housingEntranceDate.notEquals=" + UPDATED_HOUSING_ENTRANCE_DATE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingEntranceDateIsInShouldWork() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingEntranceDate in DEFAULT_HOUSING_ENTRANCE_DATE or UPDATED_HOUSING_ENTRANCE_DATE
        defaultHousingShouldBeFound("housingEntranceDate.in=" + DEFAULT_HOUSING_ENTRANCE_DATE + "," + UPDATED_HOUSING_ENTRANCE_DATE);

        // Get all the housingList where housingEntranceDate equals to UPDATED_HOUSING_ENTRANCE_DATE
        defaultHousingShouldNotBeFound("housingEntranceDate.in=" + UPDATED_HOUSING_ENTRANCE_DATE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingEntranceDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingEntranceDate is not null
        defaultHousingShouldBeFound("housingEntranceDate.specified=true");

        // Get all the housingList where housingEntranceDate is null
        defaultHousingShouldNotBeFound("housingEntranceDate.specified=false");
    }

    @Test
    @Transactional
    void getAllHousingsByHousingEntranceDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingEntranceDate is greater than or equal to DEFAULT_HOUSING_ENTRANCE_DATE
        defaultHousingShouldBeFound("housingEntranceDate.greaterThanOrEqual=" + DEFAULT_HOUSING_ENTRANCE_DATE);

        // Get all the housingList where housingEntranceDate is greater than or equal to UPDATED_HOUSING_ENTRANCE_DATE
        defaultHousingShouldNotBeFound("housingEntranceDate.greaterThanOrEqual=" + UPDATED_HOUSING_ENTRANCE_DATE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingEntranceDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingEntranceDate is less than or equal to DEFAULT_HOUSING_ENTRANCE_DATE
        defaultHousingShouldBeFound("housingEntranceDate.lessThanOrEqual=" + DEFAULT_HOUSING_ENTRANCE_DATE);

        // Get all the housingList where housingEntranceDate is less than or equal to SMALLER_HOUSING_ENTRANCE_DATE
        defaultHousingShouldNotBeFound("housingEntranceDate.lessThanOrEqual=" + SMALLER_HOUSING_ENTRANCE_DATE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingEntranceDateIsLessThanSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingEntranceDate is less than DEFAULT_HOUSING_ENTRANCE_DATE
        defaultHousingShouldNotBeFound("housingEntranceDate.lessThan=" + DEFAULT_HOUSING_ENTRANCE_DATE);

        // Get all the housingList where housingEntranceDate is less than UPDATED_HOUSING_ENTRANCE_DATE
        defaultHousingShouldBeFound("housingEntranceDate.lessThan=" + UPDATED_HOUSING_ENTRANCE_DATE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingEntranceDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingEntranceDate is greater than DEFAULT_HOUSING_ENTRANCE_DATE
        defaultHousingShouldNotBeFound("housingEntranceDate.greaterThan=" + DEFAULT_HOUSING_ENTRANCE_DATE);

        // Get all the housingList where housingEntranceDate is greater than SMALLER_HOUSING_ENTRANCE_DATE
        defaultHousingShouldBeFound("housingEntranceDate.greaterThan=" + SMALLER_HOUSING_ENTRANCE_DATE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingExitIsEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingExit equals to DEFAULT_HOUSING_EXIT
        defaultHousingShouldBeFound("housingExit.equals=" + DEFAULT_HOUSING_EXIT);

        // Get all the housingList where housingExit equals to UPDATED_HOUSING_EXIT
        defaultHousingShouldNotBeFound("housingExit.equals=" + UPDATED_HOUSING_EXIT);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingExitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingExit not equals to DEFAULT_HOUSING_EXIT
        defaultHousingShouldNotBeFound("housingExit.notEquals=" + DEFAULT_HOUSING_EXIT);

        // Get all the housingList where housingExit not equals to UPDATED_HOUSING_EXIT
        defaultHousingShouldBeFound("housingExit.notEquals=" + UPDATED_HOUSING_EXIT);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingExitIsInShouldWork() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingExit in DEFAULT_HOUSING_EXIT or UPDATED_HOUSING_EXIT
        defaultHousingShouldBeFound("housingExit.in=" + DEFAULT_HOUSING_EXIT + "," + UPDATED_HOUSING_EXIT);

        // Get all the housingList where housingExit equals to UPDATED_HOUSING_EXIT
        defaultHousingShouldNotBeFound("housingExit.in=" + UPDATED_HOUSING_EXIT);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingExitIsNullOrNotNull() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingExit is not null
        defaultHousingShouldBeFound("housingExit.specified=true");

        // Get all the housingList where housingExit is null
        defaultHousingShouldNotBeFound("housingExit.specified=false");
    }

    @Test
    @Transactional
    void getAllHousingsByHousingExitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingExit is greater than or equal to DEFAULT_HOUSING_EXIT
        defaultHousingShouldBeFound("housingExit.greaterThanOrEqual=" + DEFAULT_HOUSING_EXIT);

        // Get all the housingList where housingExit is greater than or equal to UPDATED_HOUSING_EXIT
        defaultHousingShouldNotBeFound("housingExit.greaterThanOrEqual=" + UPDATED_HOUSING_EXIT);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingExitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingExit is less than or equal to DEFAULT_HOUSING_EXIT
        defaultHousingShouldBeFound("housingExit.lessThanOrEqual=" + DEFAULT_HOUSING_EXIT);

        // Get all the housingList where housingExit is less than or equal to SMALLER_HOUSING_EXIT
        defaultHousingShouldNotBeFound("housingExit.lessThanOrEqual=" + SMALLER_HOUSING_EXIT);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingExitIsLessThanSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingExit is less than DEFAULT_HOUSING_EXIT
        defaultHousingShouldNotBeFound("housingExit.lessThan=" + DEFAULT_HOUSING_EXIT);

        // Get all the housingList where housingExit is less than UPDATED_HOUSING_EXIT
        defaultHousingShouldBeFound("housingExit.lessThan=" + UPDATED_HOUSING_EXIT);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingExitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingExit is greater than DEFAULT_HOUSING_EXIT
        defaultHousingShouldNotBeFound("housingExit.greaterThan=" + DEFAULT_HOUSING_EXIT);

        // Get all the housingList where housingExit is greater than SMALLER_HOUSING_EXIT
        defaultHousingShouldBeFound("housingExit.greaterThan=" + SMALLER_HOUSING_EXIT);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingReceiptNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingReceiptNumber equals to DEFAULT_HOUSING_RECEIPT_NUMBER
        defaultHousingShouldBeFound("housingReceiptNumber.equals=" + DEFAULT_HOUSING_RECEIPT_NUMBER);

        // Get all the housingList where housingReceiptNumber equals to UPDATED_HOUSING_RECEIPT_NUMBER
        defaultHousingShouldNotBeFound("housingReceiptNumber.equals=" + UPDATED_HOUSING_RECEIPT_NUMBER);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingReceiptNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingReceiptNumber not equals to DEFAULT_HOUSING_RECEIPT_NUMBER
        defaultHousingShouldNotBeFound("housingReceiptNumber.notEquals=" + DEFAULT_HOUSING_RECEIPT_NUMBER);

        // Get all the housingList where housingReceiptNumber not equals to UPDATED_HOUSING_RECEIPT_NUMBER
        defaultHousingShouldBeFound("housingReceiptNumber.notEquals=" + UPDATED_HOUSING_RECEIPT_NUMBER);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingReceiptNumberIsInShouldWork() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingReceiptNumber in DEFAULT_HOUSING_RECEIPT_NUMBER or UPDATED_HOUSING_RECEIPT_NUMBER
        defaultHousingShouldBeFound("housingReceiptNumber.in=" + DEFAULT_HOUSING_RECEIPT_NUMBER + "," + UPDATED_HOUSING_RECEIPT_NUMBER);

        // Get all the housingList where housingReceiptNumber equals to UPDATED_HOUSING_RECEIPT_NUMBER
        defaultHousingShouldNotBeFound("housingReceiptNumber.in=" + UPDATED_HOUSING_RECEIPT_NUMBER);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingReceiptNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingReceiptNumber is not null
        defaultHousingShouldBeFound("housingReceiptNumber.specified=true");

        // Get all the housingList where housingReceiptNumber is null
        defaultHousingShouldNotBeFound("housingReceiptNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllHousingsByHousingReceiptNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingReceiptNumber is greater than or equal to DEFAULT_HOUSING_RECEIPT_NUMBER
        defaultHousingShouldBeFound("housingReceiptNumber.greaterThanOrEqual=" + DEFAULT_HOUSING_RECEIPT_NUMBER);

        // Get all the housingList where housingReceiptNumber is greater than or equal to UPDATED_HOUSING_RECEIPT_NUMBER
        defaultHousingShouldNotBeFound("housingReceiptNumber.greaterThanOrEqual=" + UPDATED_HOUSING_RECEIPT_NUMBER);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingReceiptNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingReceiptNumber is less than or equal to DEFAULT_HOUSING_RECEIPT_NUMBER
        defaultHousingShouldBeFound("housingReceiptNumber.lessThanOrEqual=" + DEFAULT_HOUSING_RECEIPT_NUMBER);

        // Get all the housingList where housingReceiptNumber is less than or equal to SMALLER_HOUSING_RECEIPT_NUMBER
        defaultHousingShouldNotBeFound("housingReceiptNumber.lessThanOrEqual=" + SMALLER_HOUSING_RECEIPT_NUMBER);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingReceiptNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingReceiptNumber is less than DEFAULT_HOUSING_RECEIPT_NUMBER
        defaultHousingShouldNotBeFound("housingReceiptNumber.lessThan=" + DEFAULT_HOUSING_RECEIPT_NUMBER);

        // Get all the housingList where housingReceiptNumber is less than UPDATED_HOUSING_RECEIPT_NUMBER
        defaultHousingShouldBeFound("housingReceiptNumber.lessThan=" + UPDATED_HOUSING_RECEIPT_NUMBER);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingReceiptNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingReceiptNumber is greater than DEFAULT_HOUSING_RECEIPT_NUMBER
        defaultHousingShouldNotBeFound("housingReceiptNumber.greaterThan=" + DEFAULT_HOUSING_RECEIPT_NUMBER);

        // Get all the housingList where housingReceiptNumber is greater than SMALLER_HOUSING_RECEIPT_NUMBER
        defaultHousingShouldBeFound("housingReceiptNumber.greaterThan=" + SMALLER_HOUSING_RECEIPT_NUMBER);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDailyPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDailyPrice equals to DEFAULT_HOUSING_DAILY_PRICE
        defaultHousingShouldBeFound("housingDailyPrice.equals=" + DEFAULT_HOUSING_DAILY_PRICE);

        // Get all the housingList where housingDailyPrice equals to UPDATED_HOUSING_DAILY_PRICE
        defaultHousingShouldNotBeFound("housingDailyPrice.equals=" + UPDATED_HOUSING_DAILY_PRICE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDailyPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDailyPrice not equals to DEFAULT_HOUSING_DAILY_PRICE
        defaultHousingShouldNotBeFound("housingDailyPrice.notEquals=" + DEFAULT_HOUSING_DAILY_PRICE);

        // Get all the housingList where housingDailyPrice not equals to UPDATED_HOUSING_DAILY_PRICE
        defaultHousingShouldBeFound("housingDailyPrice.notEquals=" + UPDATED_HOUSING_DAILY_PRICE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDailyPriceIsInShouldWork() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDailyPrice in DEFAULT_HOUSING_DAILY_PRICE or UPDATED_HOUSING_DAILY_PRICE
        defaultHousingShouldBeFound("housingDailyPrice.in=" + DEFAULT_HOUSING_DAILY_PRICE + "," + UPDATED_HOUSING_DAILY_PRICE);

        // Get all the housingList where housingDailyPrice equals to UPDATED_HOUSING_DAILY_PRICE
        defaultHousingShouldNotBeFound("housingDailyPrice.in=" + UPDATED_HOUSING_DAILY_PRICE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDailyPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDailyPrice is not null
        defaultHousingShouldBeFound("housingDailyPrice.specified=true");

        // Get all the housingList where housingDailyPrice is null
        defaultHousingShouldNotBeFound("housingDailyPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDailyPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDailyPrice is greater than or equal to DEFAULT_HOUSING_DAILY_PRICE
        defaultHousingShouldBeFound("housingDailyPrice.greaterThanOrEqual=" + DEFAULT_HOUSING_DAILY_PRICE);

        // Get all the housingList where housingDailyPrice is greater than or equal to UPDATED_HOUSING_DAILY_PRICE
        defaultHousingShouldNotBeFound("housingDailyPrice.greaterThanOrEqual=" + UPDATED_HOUSING_DAILY_PRICE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDailyPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDailyPrice is less than or equal to DEFAULT_HOUSING_DAILY_PRICE
        defaultHousingShouldBeFound("housingDailyPrice.lessThanOrEqual=" + DEFAULT_HOUSING_DAILY_PRICE);

        // Get all the housingList where housingDailyPrice is less than or equal to SMALLER_HOUSING_DAILY_PRICE
        defaultHousingShouldNotBeFound("housingDailyPrice.lessThanOrEqual=" + SMALLER_HOUSING_DAILY_PRICE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDailyPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDailyPrice is less than DEFAULT_HOUSING_DAILY_PRICE
        defaultHousingShouldNotBeFound("housingDailyPrice.lessThan=" + DEFAULT_HOUSING_DAILY_PRICE);

        // Get all the housingList where housingDailyPrice is less than UPDATED_HOUSING_DAILY_PRICE
        defaultHousingShouldBeFound("housingDailyPrice.lessThan=" + UPDATED_HOUSING_DAILY_PRICE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDailyPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDailyPrice is greater than DEFAULT_HOUSING_DAILY_PRICE
        defaultHousingShouldNotBeFound("housingDailyPrice.greaterThan=" + DEFAULT_HOUSING_DAILY_PRICE);

        // Get all the housingList where housingDailyPrice is greater than SMALLER_HOUSING_DAILY_PRICE
        defaultHousingShouldBeFound("housingDailyPrice.greaterThan=" + SMALLER_HOUSING_DAILY_PRICE);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDescription equals to DEFAULT_HOUSING_DESCRIPTION
        defaultHousingShouldBeFound("housingDescription.equals=" + DEFAULT_HOUSING_DESCRIPTION);

        // Get all the housingList where housingDescription equals to UPDATED_HOUSING_DESCRIPTION
        defaultHousingShouldNotBeFound("housingDescription.equals=" + UPDATED_HOUSING_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDescription not equals to DEFAULT_HOUSING_DESCRIPTION
        defaultHousingShouldNotBeFound("housingDescription.notEquals=" + DEFAULT_HOUSING_DESCRIPTION);

        // Get all the housingList where housingDescription not equals to UPDATED_HOUSING_DESCRIPTION
        defaultHousingShouldBeFound("housingDescription.notEquals=" + UPDATED_HOUSING_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDescription in DEFAULT_HOUSING_DESCRIPTION or UPDATED_HOUSING_DESCRIPTION
        defaultHousingShouldBeFound("housingDescription.in=" + DEFAULT_HOUSING_DESCRIPTION + "," + UPDATED_HOUSING_DESCRIPTION);

        // Get all the housingList where housingDescription equals to UPDATED_HOUSING_DESCRIPTION
        defaultHousingShouldNotBeFound("housingDescription.in=" + UPDATED_HOUSING_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDescription is not null
        defaultHousingShouldBeFound("housingDescription.specified=true");

        // Get all the housingList where housingDescription is null
        defaultHousingShouldNotBeFound("housingDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDescriptionContainsSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDescription contains DEFAULT_HOUSING_DESCRIPTION
        defaultHousingShouldBeFound("housingDescription.contains=" + DEFAULT_HOUSING_DESCRIPTION);

        // Get all the housingList where housingDescription contains UPDATED_HOUSING_DESCRIPTION
        defaultHousingShouldNotBeFound("housingDescription.contains=" + UPDATED_HOUSING_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        // Get all the housingList where housingDescription does not contain DEFAULT_HOUSING_DESCRIPTION
        defaultHousingShouldNotBeFound("housingDescription.doesNotContain=" + DEFAULT_HOUSING_DESCRIPTION);

        // Get all the housingList where housingDescription does not contain UPDATED_HOUSING_DESCRIPTION
        defaultHousingShouldBeFound("housingDescription.doesNotContain=" + UPDATED_HOUSING_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllHousingsByHousingVehicleItemIsEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);
        HousingVehicleItem housingVehicleItem = HousingVehicleItemResourceIT.createEntity(em);
        em.persist(housingVehicleItem);
        em.flush();
        housing.addHousingVehicleItem(housingVehicleItem);
        housingRepository.saveAndFlush(housing);
        Long housingVehicleItemId = housingVehicleItem.getId();

        // Get all the housingList where housingVehicleItem equals to housingVehicleItemId
        defaultHousingShouldBeFound("housingVehicleItemId.equals=" + housingVehicleItemId);

        // Get all the housingList where housingVehicleItem equals to (housingVehicleItemId + 1)
        defaultHousingShouldNotBeFound("housingVehicleItemId.equals=" + (housingVehicleItemId + 1));
    }

    @Test
    @Transactional
    void getAllHousingsByAffiliatesIsEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);
        Affiliates affiliates = AffiliatesResourceIT.createEntity(em);
        em.persist(affiliates);
        em.flush();
        housing.setAffiliates(affiliates);
        housingRepository.saveAndFlush(housing);
        Long affiliatesId = affiliates.getId();

        // Get all the housingList where affiliates equals to affiliatesId
        defaultHousingShouldBeFound("affiliatesId.equals=" + affiliatesId);

        // Get all the housingList where affiliates equals to (affiliatesId + 1)
        defaultHousingShouldNotBeFound("affiliatesId.equals=" + (affiliatesId + 1));
    }

    @Test
    @Transactional
    void getAllHousingsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);
        Status status = StatusResourceIT.createEntity(em);
        em.persist(status);
        em.flush();
        housing.setStatus(status);
        housingRepository.saveAndFlush(housing);
        Long statusId = status.getId();

        // Get all the housingList where status equals to statusId
        defaultHousingShouldBeFound("statusId.equals=" + statusId);

        // Get all the housingList where status equals to (statusId + 1)
        defaultHousingShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }

    @Test
    @Transactional
    void getAllHousingsByCustomersIsEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);
        Customers customers = CustomersResourceIT.createEntity(em);
        em.persist(customers);
        em.flush();
        housing.setCustomers(customers);
        housingRepository.saveAndFlush(housing);
        Long customersId = customers.getId();

        // Get all the housingList where customers equals to customersId
        defaultHousingShouldBeFound("customersId.equals=" + customersId);

        // Get all the housingList where customers equals to (customersId + 1)
        defaultHousingShouldNotBeFound("customersId.equals=" + (customersId + 1));
    }

    @Test
    @Transactional
    void getAllHousingsByEmployeesIsEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);
        Employees employees = EmployeesResourceIT.createEntity(em);
        em.persist(employees);
        em.flush();
        housing.setEmployees(employees);
        housingRepository.saveAndFlush(housing);
        Long employeesId = employees.getId();

        // Get all the housingList where employees equals to employeesId
        defaultHousingShouldBeFound("employeesId.equals=" + employeesId);

        // Get all the housingList where employees equals to (employeesId + 1)
        defaultHousingShouldNotBeFound("employeesId.equals=" + (employeesId + 1));
    }

    @Test
    @Transactional
    void getAllHousingsByParkingIsEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);
        Parking parking = ParkingResourceIT.createEntity(em);
        em.persist(parking);
        em.flush();
        housing.setParking(parking);
        housingRepository.saveAndFlush(housing);
        Long parkingId = parking.getId();

        // Get all the housingList where parking equals to parkingId
        defaultHousingShouldBeFound("parkingId.equals=" + parkingId);

        // Get all the housingList where parking equals to (parkingId + 1)
        defaultHousingShouldNotBeFound("parkingId.equals=" + (parkingId + 1));
    }

    @Test
    @Transactional
    void getAllHousingsByCostCenterIsEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);
        CostCenter costCenter = CostCenterResourceIT.createEntity(em);
        em.persist(costCenter);
        em.flush();
        housing.setCostCenter(costCenter);
        housingRepository.saveAndFlush(housing);
        Long costCenterId = costCenter.getId();

        // Get all the housingList where costCenter equals to costCenterId
        defaultHousingShouldBeFound("costCenterId.equals=" + costCenterId);

        // Get all the housingList where costCenter equals to (costCenterId + 1)
        defaultHousingShouldNotBeFound("costCenterId.equals=" + (costCenterId + 1));
    }

    @Test
    @Transactional
    void getAllHousingsBySuppliersIsEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);
        Suppliers suppliers = SuppliersResourceIT.createEntity(em);
        em.persist(suppliers);
        em.flush();
        housing.setSuppliers(suppliers);
        housingRepository.saveAndFlush(housing);
        Long suppliersId = suppliers.getId();

        // Get all the housingList where suppliers equals to suppliersId
        defaultHousingShouldBeFound("suppliersId.equals=" + suppliersId);

        // Get all the housingList where suppliers equals to (suppliersId + 1)
        defaultHousingShouldNotBeFound("suppliersId.equals=" + (suppliersId + 1));
    }

    @Test
    @Transactional
    void getAllHousingsByCitiesIsEqualToSomething() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);
        Cities cities = CitiesResourceIT.createEntity(em);
        em.persist(cities);
        em.flush();
        housing.setCities(cities);
        housingRepository.saveAndFlush(housing);
        Long citiesId = cities.getId();

        // Get all the housingList where cities equals to citiesId
        defaultHousingShouldBeFound("citiesId.equals=" + citiesId);

        // Get all the housingList where cities equals to (citiesId + 1)
        defaultHousingShouldNotBeFound("citiesId.equals=" + (citiesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHousingShouldBeFound(String filter) throws Exception {
        restHousingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(housing.getId().intValue())))
            .andExpect(jsonPath("$.[*].housingDate").value(hasItem(DEFAULT_HOUSING_DATE.toString())))
            .andExpect(jsonPath("$.[*].housingEntranceDate").value(hasItem(sameInstant(DEFAULT_HOUSING_ENTRANCE_DATE))))
            .andExpect(jsonPath("$.[*].housingExit").value(hasItem(sameInstant(DEFAULT_HOUSING_EXIT))))
            .andExpect(jsonPath("$.[*].housingReceiptNumber").value(hasItem(DEFAULT_HOUSING_RECEIPT_NUMBER)))
            .andExpect(jsonPath("$.[*].housingDailyPrice").value(hasItem(DEFAULT_HOUSING_DAILY_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].housingDescription").value(hasItem(DEFAULT_HOUSING_DESCRIPTION)));

        // Check, that the count call also returns 1
        restHousingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHousingShouldNotBeFound(String filter) throws Exception {
        restHousingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHousingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingHousing() throws Exception {
        // Get the housing
        restHousingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHousing() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        int databaseSizeBeforeUpdate = housingRepository.findAll().size();

        // Update the housing
        Housing updatedHousing = housingRepository.findById(housing.getId()).get();
        // Disconnect from session so that the updates on updatedHousing are not directly saved in db
        em.detach(updatedHousing);
        updatedHousing
            .housingDate(UPDATED_HOUSING_DATE)
            .housingEntranceDate(UPDATED_HOUSING_ENTRANCE_DATE)
            .housingExit(UPDATED_HOUSING_EXIT)
            .housingReceiptNumber(UPDATED_HOUSING_RECEIPT_NUMBER)
            .housingDailyPrice(UPDATED_HOUSING_DAILY_PRICE)
            .housingDescription(UPDATED_HOUSING_DESCRIPTION);
        HousingDTO housingDTO = housingMapper.toDto(updatedHousing);

        restHousingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, housingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(housingDTO))
            )
            .andExpect(status().isOk());

        // Validate the Housing in the database
        List<Housing> housingList = housingRepository.findAll();
        assertThat(housingList).hasSize(databaseSizeBeforeUpdate);
        Housing testHousing = housingList.get(housingList.size() - 1);
        assertThat(testHousing.getHousingDate()).isEqualTo(UPDATED_HOUSING_DATE);
        assertThat(testHousing.getHousingEntranceDate()).isEqualTo(UPDATED_HOUSING_ENTRANCE_DATE);
        assertThat(testHousing.getHousingExit()).isEqualTo(UPDATED_HOUSING_EXIT);
        assertThat(testHousing.getHousingReceiptNumber()).isEqualTo(UPDATED_HOUSING_RECEIPT_NUMBER);
        assertThat(testHousing.getHousingDailyPrice()).isEqualTo(UPDATED_HOUSING_DAILY_PRICE);
        assertThat(testHousing.getHousingDescription()).isEqualTo(UPDATED_HOUSING_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingHousing() throws Exception {
        int databaseSizeBeforeUpdate = housingRepository.findAll().size();
        housing.setId(count.incrementAndGet());

        // Create the Housing
        HousingDTO housingDTO = housingMapper.toDto(housing);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHousingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, housingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(housingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Housing in the database
        List<Housing> housingList = housingRepository.findAll();
        assertThat(housingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHousing() throws Exception {
        int databaseSizeBeforeUpdate = housingRepository.findAll().size();
        housing.setId(count.incrementAndGet());

        // Create the Housing
        HousingDTO housingDTO = housingMapper.toDto(housing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHousingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(housingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Housing in the database
        List<Housing> housingList = housingRepository.findAll();
        assertThat(housingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHousing() throws Exception {
        int databaseSizeBeforeUpdate = housingRepository.findAll().size();
        housing.setId(count.incrementAndGet());

        // Create the Housing
        HousingDTO housingDTO = housingMapper.toDto(housing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHousingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(housingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Housing in the database
        List<Housing> housingList = housingRepository.findAll();
        assertThat(housingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHousingWithPatch() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        int databaseSizeBeforeUpdate = housingRepository.findAll().size();

        // Update the housing using partial update
        Housing partialUpdatedHousing = new Housing();
        partialUpdatedHousing.setId(housing.getId());

        partialUpdatedHousing
            .housingDate(UPDATED_HOUSING_DATE)
            .housingEntranceDate(UPDATED_HOUSING_ENTRANCE_DATE)
            .housingDailyPrice(UPDATED_HOUSING_DAILY_PRICE);

        restHousingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHousing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHousing))
            )
            .andExpect(status().isOk());

        // Validate the Housing in the database
        List<Housing> housingList = housingRepository.findAll();
        assertThat(housingList).hasSize(databaseSizeBeforeUpdate);
        Housing testHousing = housingList.get(housingList.size() - 1);
        assertThat(testHousing.getHousingDate()).isEqualTo(UPDATED_HOUSING_DATE);
        assertThat(testHousing.getHousingEntranceDate()).isEqualTo(UPDATED_HOUSING_ENTRANCE_DATE);
        assertThat(testHousing.getHousingExit()).isEqualTo(DEFAULT_HOUSING_EXIT);
        assertThat(testHousing.getHousingReceiptNumber()).isEqualTo(DEFAULT_HOUSING_RECEIPT_NUMBER);
        assertThat(testHousing.getHousingDailyPrice()).isEqualTo(UPDATED_HOUSING_DAILY_PRICE);
        assertThat(testHousing.getHousingDescription()).isEqualTo(DEFAULT_HOUSING_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateHousingWithPatch() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        int databaseSizeBeforeUpdate = housingRepository.findAll().size();

        // Update the housing using partial update
        Housing partialUpdatedHousing = new Housing();
        partialUpdatedHousing.setId(housing.getId());

        partialUpdatedHousing
            .housingDate(UPDATED_HOUSING_DATE)
            .housingEntranceDate(UPDATED_HOUSING_ENTRANCE_DATE)
            .housingExit(UPDATED_HOUSING_EXIT)
            .housingReceiptNumber(UPDATED_HOUSING_RECEIPT_NUMBER)
            .housingDailyPrice(UPDATED_HOUSING_DAILY_PRICE)
            .housingDescription(UPDATED_HOUSING_DESCRIPTION);

        restHousingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHousing.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHousing))
            )
            .andExpect(status().isOk());

        // Validate the Housing in the database
        List<Housing> housingList = housingRepository.findAll();
        assertThat(housingList).hasSize(databaseSizeBeforeUpdate);
        Housing testHousing = housingList.get(housingList.size() - 1);
        assertThat(testHousing.getHousingDate()).isEqualTo(UPDATED_HOUSING_DATE);
        assertThat(testHousing.getHousingEntranceDate()).isEqualTo(UPDATED_HOUSING_ENTRANCE_DATE);
        assertThat(testHousing.getHousingExit()).isEqualTo(UPDATED_HOUSING_EXIT);
        assertThat(testHousing.getHousingReceiptNumber()).isEqualTo(UPDATED_HOUSING_RECEIPT_NUMBER);
        assertThat(testHousing.getHousingDailyPrice()).isEqualTo(UPDATED_HOUSING_DAILY_PRICE);
        assertThat(testHousing.getHousingDescription()).isEqualTo(UPDATED_HOUSING_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingHousing() throws Exception {
        int databaseSizeBeforeUpdate = housingRepository.findAll().size();
        housing.setId(count.incrementAndGet());

        // Create the Housing
        HousingDTO housingDTO = housingMapper.toDto(housing);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHousingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, housingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(housingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Housing in the database
        List<Housing> housingList = housingRepository.findAll();
        assertThat(housingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHousing() throws Exception {
        int databaseSizeBeforeUpdate = housingRepository.findAll().size();
        housing.setId(count.incrementAndGet());

        // Create the Housing
        HousingDTO housingDTO = housingMapper.toDto(housing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHousingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(housingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Housing in the database
        List<Housing> housingList = housingRepository.findAll();
        assertThat(housingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHousing() throws Exception {
        int databaseSizeBeforeUpdate = housingRepository.findAll().size();
        housing.setId(count.incrementAndGet());

        // Create the Housing
        HousingDTO housingDTO = housingMapper.toDto(housing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHousingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(housingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Housing in the database
        List<Housing> housingList = housingRepository.findAll();
        assertThat(housingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHousing() throws Exception {
        // Initialize the database
        housingRepository.saveAndFlush(housing);

        int databaseSizeBeforeDelete = housingRepository.findAll().size();

        // Delete the housing
        restHousingMockMvc
            .perform(delete(ENTITY_API_URL_ID, housing.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Housing> housingList = housingRepository.findAll();
        assertThat(housingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
