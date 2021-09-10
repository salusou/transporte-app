package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Affiliates;
import com.genesisoft.transporte.domain.Cities;
import com.genesisoft.transporte.domain.Companies;
import com.genesisoft.transporte.domain.Employees;
import com.genesisoft.transporte.domain.StateProvinces;
import com.genesisoft.transporte.repository.CompaniesRepository;
import com.genesisoft.transporte.service.criteria.CompaniesCriteria;
import com.genesisoft.transporte.service.dto.CompaniesDTO;
import com.genesisoft.transporte.service.mapper.CompaniesMapper;
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
 * Integration tests for the {@link CompaniesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompaniesResourceIT {

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TRADE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TRADE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBB";

    private static final String DEFAULT_COMPANY_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_ADDRESS_COMPLEMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_ADDRESS_COMPLEMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_COMPANY_ADDRESS_NUMBER = 1;
    private static final Integer UPDATED_COMPANY_ADDRESS_NUMBER = 2;
    private static final Integer SMALLER_COMPANY_ADDRESS_NUMBER = 1 - 1;

    private static final String DEFAULT_COMPANY_ADDRESS_NEIGHBORHOOD = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_ADDRESS_NEIGHBORHOOD = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSIBLE_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSIBLE_CONTACT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/companies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompaniesRepository companiesRepository;

    @Autowired
    private CompaniesMapper companiesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompaniesMockMvc;

    private Companies companies;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Companies createEntity(EntityManager em) {
        Companies companies = new Companies()
            .companyName(DEFAULT_COMPANY_NAME)
            .tradeName(DEFAULT_TRADE_NAME)
            .companyNumber(DEFAULT_COMPANY_NUMBER)
            .postalCode(DEFAULT_POSTAL_CODE)
            .companyAddress(DEFAULT_COMPANY_ADDRESS)
            .companyAddressComplement(DEFAULT_COMPANY_ADDRESS_COMPLEMENT)
            .companyAddressNumber(DEFAULT_COMPANY_ADDRESS_NUMBER)
            .companyAddressNeighborhood(DEFAULT_COMPANY_ADDRESS_NEIGHBORHOOD)
            .companyTelephone(DEFAULT_COMPANY_TELEPHONE)
            .companyEmail(DEFAULT_COMPANY_EMAIL)
            .responsibleContact(DEFAULT_RESPONSIBLE_CONTACT);
        // Add required entity
        Cities cities;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            cities = CitiesResourceIT.createEntity(em);
            em.persist(cities);
            em.flush();
        } else {
            cities = TestUtil.findAll(em, Cities.class).get(0);
        }
        companies.setCities(cities);
        // Add required entity
        StateProvinces stateProvinces;
        if (TestUtil.findAll(em, StateProvinces.class).isEmpty()) {
            stateProvinces = StateProvincesResourceIT.createEntity(em);
            em.persist(stateProvinces);
            em.flush();
        } else {
            stateProvinces = TestUtil.findAll(em, StateProvinces.class).get(0);
        }
        companies.setStateProvinces(stateProvinces);
        return companies;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Companies createUpdatedEntity(EntityManager em) {
        Companies companies = new Companies()
            .companyName(UPDATED_COMPANY_NAME)
            .tradeName(UPDATED_TRADE_NAME)
            .companyNumber(UPDATED_COMPANY_NUMBER)
            .postalCode(UPDATED_POSTAL_CODE)
            .companyAddress(UPDATED_COMPANY_ADDRESS)
            .companyAddressComplement(UPDATED_COMPANY_ADDRESS_COMPLEMENT)
            .companyAddressNumber(UPDATED_COMPANY_ADDRESS_NUMBER)
            .companyAddressNeighborhood(UPDATED_COMPANY_ADDRESS_NEIGHBORHOOD)
            .companyTelephone(UPDATED_COMPANY_TELEPHONE)
            .companyEmail(UPDATED_COMPANY_EMAIL)
            .responsibleContact(UPDATED_RESPONSIBLE_CONTACT);
        // Add required entity
        Cities cities;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            cities = CitiesResourceIT.createUpdatedEntity(em);
            em.persist(cities);
            em.flush();
        } else {
            cities = TestUtil.findAll(em, Cities.class).get(0);
        }
        companies.setCities(cities);
        // Add required entity
        StateProvinces stateProvinces;
        if (TestUtil.findAll(em, StateProvinces.class).isEmpty()) {
            stateProvinces = StateProvincesResourceIT.createUpdatedEntity(em);
            em.persist(stateProvinces);
            em.flush();
        } else {
            stateProvinces = TestUtil.findAll(em, StateProvinces.class).get(0);
        }
        companies.setStateProvinces(stateProvinces);
        return companies;
    }

    @BeforeEach
    public void initTest() {
        companies = createEntity(em);
    }

    @Test
    @Transactional
    void createCompanies() throws Exception {
        int databaseSizeBeforeCreate = companiesRepository.findAll().size();
        // Create the Companies
        CompaniesDTO companiesDTO = companiesMapper.toDto(companies);
        restCompaniesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companiesDTO)))
            .andExpect(status().isCreated());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeCreate + 1);
        Companies testCompanies = companiesList.get(companiesList.size() - 1);
        assertThat(testCompanies.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testCompanies.getTradeName()).isEqualTo(DEFAULT_TRADE_NAME);
        assertThat(testCompanies.getCompanyNumber()).isEqualTo(DEFAULT_COMPANY_NUMBER);
        assertThat(testCompanies.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testCompanies.getCompanyAddress()).isEqualTo(DEFAULT_COMPANY_ADDRESS);
        assertThat(testCompanies.getCompanyAddressComplement()).isEqualTo(DEFAULT_COMPANY_ADDRESS_COMPLEMENT);
        assertThat(testCompanies.getCompanyAddressNumber()).isEqualTo(DEFAULT_COMPANY_ADDRESS_NUMBER);
        assertThat(testCompanies.getCompanyAddressNeighborhood()).isEqualTo(DEFAULT_COMPANY_ADDRESS_NEIGHBORHOOD);
        assertThat(testCompanies.getCompanyTelephone()).isEqualTo(DEFAULT_COMPANY_TELEPHONE);
        assertThat(testCompanies.getCompanyEmail()).isEqualTo(DEFAULT_COMPANY_EMAIL);
        assertThat(testCompanies.getResponsibleContact()).isEqualTo(DEFAULT_RESPONSIBLE_CONTACT);
    }

    @Test
    @Transactional
    void createCompaniesWithExistingId() throws Exception {
        // Create the Companies with an existing ID
        companies.setId(1L);
        CompaniesDTO companiesDTO = companiesMapper.toDto(companies);

        int databaseSizeBeforeCreate = companiesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompaniesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companiesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companiesRepository.findAll().size();
        // set the field null
        companies.setCompanyName(null);

        // Create the Companies, which fails.
        CompaniesDTO companiesDTO = companiesMapper.toDto(companies);

        restCompaniesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companiesDTO)))
            .andExpect(status().isBadRequest());

        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompanyNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = companiesRepository.findAll().size();
        // set the field null
        companies.setCompanyNumber(null);

        // Create the Companies, which fails.
        CompaniesDTO companiesDTO = companiesMapper.toDto(companies);

        restCompaniesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companiesDTO)))
            .andExpect(status().isBadRequest());

        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompanies() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList
        restCompaniesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companies.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].tradeName").value(hasItem(DEFAULT_TRADE_NAME)))
            .andExpect(jsonPath("$.[*].companyNumber").value(hasItem(DEFAULT_COMPANY_NUMBER)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].companyAddress").value(hasItem(DEFAULT_COMPANY_ADDRESS)))
            .andExpect(jsonPath("$.[*].companyAddressComplement").value(hasItem(DEFAULT_COMPANY_ADDRESS_COMPLEMENT)))
            .andExpect(jsonPath("$.[*].companyAddressNumber").value(hasItem(DEFAULT_COMPANY_ADDRESS_NUMBER)))
            .andExpect(jsonPath("$.[*].companyAddressNeighborhood").value(hasItem(DEFAULT_COMPANY_ADDRESS_NEIGHBORHOOD)))
            .andExpect(jsonPath("$.[*].companyTelephone").value(hasItem(DEFAULT_COMPANY_TELEPHONE)))
            .andExpect(jsonPath("$.[*].companyEmail").value(hasItem(DEFAULT_COMPANY_EMAIL)))
            .andExpect(jsonPath("$.[*].responsibleContact").value(hasItem(DEFAULT_RESPONSIBLE_CONTACT)));
    }

    @Test
    @Transactional
    void getCompanies() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get the companies
        restCompaniesMockMvc
            .perform(get(ENTITY_API_URL_ID, companies.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companies.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.tradeName").value(DEFAULT_TRADE_NAME))
            .andExpect(jsonPath("$.companyNumber").value(DEFAULT_COMPANY_NUMBER))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.companyAddress").value(DEFAULT_COMPANY_ADDRESS))
            .andExpect(jsonPath("$.companyAddressComplement").value(DEFAULT_COMPANY_ADDRESS_COMPLEMENT))
            .andExpect(jsonPath("$.companyAddressNumber").value(DEFAULT_COMPANY_ADDRESS_NUMBER))
            .andExpect(jsonPath("$.companyAddressNeighborhood").value(DEFAULT_COMPANY_ADDRESS_NEIGHBORHOOD))
            .andExpect(jsonPath("$.companyTelephone").value(DEFAULT_COMPANY_TELEPHONE))
            .andExpect(jsonPath("$.companyEmail").value(DEFAULT_COMPANY_EMAIL))
            .andExpect(jsonPath("$.responsibleContact").value(DEFAULT_RESPONSIBLE_CONTACT));
    }

    @Test
    @Transactional
    void getCompaniesByIdFiltering() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        Long id = companies.getId();

        defaultCompaniesShouldBeFound("id.equals=" + id);
        defaultCompaniesShouldNotBeFound("id.notEquals=" + id);

        defaultCompaniesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompaniesShouldNotBeFound("id.greaterThan=" + id);

        defaultCompaniesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompaniesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyName equals to DEFAULT_COMPANY_NAME
        defaultCompaniesShouldBeFound("companyName.equals=" + DEFAULT_COMPANY_NAME);

        // Get all the companiesList where companyName equals to UPDATED_COMPANY_NAME
        defaultCompaniesShouldNotBeFound("companyName.equals=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyName not equals to DEFAULT_COMPANY_NAME
        defaultCompaniesShouldNotBeFound("companyName.notEquals=" + DEFAULT_COMPANY_NAME);

        // Get all the companiesList where companyName not equals to UPDATED_COMPANY_NAME
        defaultCompaniesShouldBeFound("companyName.notEquals=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyNameIsInShouldWork() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyName in DEFAULT_COMPANY_NAME or UPDATED_COMPANY_NAME
        defaultCompaniesShouldBeFound("companyName.in=" + DEFAULT_COMPANY_NAME + "," + UPDATED_COMPANY_NAME);

        // Get all the companiesList where companyName equals to UPDATED_COMPANY_NAME
        defaultCompaniesShouldNotBeFound("companyName.in=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyName is not null
        defaultCompaniesShouldBeFound("companyName.specified=true");

        // Get all the companiesList where companyName is null
        defaultCompaniesShouldNotBeFound("companyName.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyNameContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyName contains DEFAULT_COMPANY_NAME
        defaultCompaniesShouldBeFound("companyName.contains=" + DEFAULT_COMPANY_NAME);

        // Get all the companiesList where companyName contains UPDATED_COMPANY_NAME
        defaultCompaniesShouldNotBeFound("companyName.contains=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyNameNotContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyName does not contain DEFAULT_COMPANY_NAME
        defaultCompaniesShouldNotBeFound("companyName.doesNotContain=" + DEFAULT_COMPANY_NAME);

        // Get all the companiesList where companyName does not contain UPDATED_COMPANY_NAME
        defaultCompaniesShouldBeFound("companyName.doesNotContain=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByTradeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where tradeName equals to DEFAULT_TRADE_NAME
        defaultCompaniesShouldBeFound("tradeName.equals=" + DEFAULT_TRADE_NAME);

        // Get all the companiesList where tradeName equals to UPDATED_TRADE_NAME
        defaultCompaniesShouldNotBeFound("tradeName.equals=" + UPDATED_TRADE_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByTradeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where tradeName not equals to DEFAULT_TRADE_NAME
        defaultCompaniesShouldNotBeFound("tradeName.notEquals=" + DEFAULT_TRADE_NAME);

        // Get all the companiesList where tradeName not equals to UPDATED_TRADE_NAME
        defaultCompaniesShouldBeFound("tradeName.notEquals=" + UPDATED_TRADE_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByTradeNameIsInShouldWork() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where tradeName in DEFAULT_TRADE_NAME or UPDATED_TRADE_NAME
        defaultCompaniesShouldBeFound("tradeName.in=" + DEFAULT_TRADE_NAME + "," + UPDATED_TRADE_NAME);

        // Get all the companiesList where tradeName equals to UPDATED_TRADE_NAME
        defaultCompaniesShouldNotBeFound("tradeName.in=" + UPDATED_TRADE_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByTradeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where tradeName is not null
        defaultCompaniesShouldBeFound("tradeName.specified=true");

        // Get all the companiesList where tradeName is null
        defaultCompaniesShouldNotBeFound("tradeName.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByTradeNameContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where tradeName contains DEFAULT_TRADE_NAME
        defaultCompaniesShouldBeFound("tradeName.contains=" + DEFAULT_TRADE_NAME);

        // Get all the companiesList where tradeName contains UPDATED_TRADE_NAME
        defaultCompaniesShouldNotBeFound("tradeName.contains=" + UPDATED_TRADE_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByTradeNameNotContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where tradeName does not contain DEFAULT_TRADE_NAME
        defaultCompaniesShouldNotBeFound("tradeName.doesNotContain=" + DEFAULT_TRADE_NAME);

        // Get all the companiesList where tradeName does not contain UPDATED_TRADE_NAME
        defaultCompaniesShouldBeFound("tradeName.doesNotContain=" + UPDATED_TRADE_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyNumber equals to DEFAULT_COMPANY_NUMBER
        defaultCompaniesShouldBeFound("companyNumber.equals=" + DEFAULT_COMPANY_NUMBER);

        // Get all the companiesList where companyNumber equals to UPDATED_COMPANY_NUMBER
        defaultCompaniesShouldNotBeFound("companyNumber.equals=" + UPDATED_COMPANY_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyNumber not equals to DEFAULT_COMPANY_NUMBER
        defaultCompaniesShouldNotBeFound("companyNumber.notEquals=" + DEFAULT_COMPANY_NUMBER);

        // Get all the companiesList where companyNumber not equals to UPDATED_COMPANY_NUMBER
        defaultCompaniesShouldBeFound("companyNumber.notEquals=" + UPDATED_COMPANY_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyNumberIsInShouldWork() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyNumber in DEFAULT_COMPANY_NUMBER or UPDATED_COMPANY_NUMBER
        defaultCompaniesShouldBeFound("companyNumber.in=" + DEFAULT_COMPANY_NUMBER + "," + UPDATED_COMPANY_NUMBER);

        // Get all the companiesList where companyNumber equals to UPDATED_COMPANY_NUMBER
        defaultCompaniesShouldNotBeFound("companyNumber.in=" + UPDATED_COMPANY_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyNumber is not null
        defaultCompaniesShouldBeFound("companyNumber.specified=true");

        // Get all the companiesList where companyNumber is null
        defaultCompaniesShouldNotBeFound("companyNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyNumberContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyNumber contains DEFAULT_COMPANY_NUMBER
        defaultCompaniesShouldBeFound("companyNumber.contains=" + DEFAULT_COMPANY_NUMBER);

        // Get all the companiesList where companyNumber contains UPDATED_COMPANY_NUMBER
        defaultCompaniesShouldNotBeFound("companyNumber.contains=" + UPDATED_COMPANY_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyNumberNotContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyNumber does not contain DEFAULT_COMPANY_NUMBER
        defaultCompaniesShouldNotBeFound("companyNumber.doesNotContain=" + DEFAULT_COMPANY_NUMBER);

        // Get all the companiesList where companyNumber does not contain UPDATED_COMPANY_NUMBER
        defaultCompaniesShouldBeFound("companyNumber.doesNotContain=" + UPDATED_COMPANY_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where postalCode equals to DEFAULT_POSTAL_CODE
        defaultCompaniesShouldBeFound("postalCode.equals=" + DEFAULT_POSTAL_CODE);

        // Get all the companiesList where postalCode equals to UPDATED_POSTAL_CODE
        defaultCompaniesShouldNotBeFound("postalCode.equals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllCompaniesByPostalCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where postalCode not equals to DEFAULT_POSTAL_CODE
        defaultCompaniesShouldNotBeFound("postalCode.notEquals=" + DEFAULT_POSTAL_CODE);

        // Get all the companiesList where postalCode not equals to UPDATED_POSTAL_CODE
        defaultCompaniesShouldBeFound("postalCode.notEquals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllCompaniesByPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where postalCode in DEFAULT_POSTAL_CODE or UPDATED_POSTAL_CODE
        defaultCompaniesShouldBeFound("postalCode.in=" + DEFAULT_POSTAL_CODE + "," + UPDATED_POSTAL_CODE);

        // Get all the companiesList where postalCode equals to UPDATED_POSTAL_CODE
        defaultCompaniesShouldNotBeFound("postalCode.in=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllCompaniesByPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where postalCode is not null
        defaultCompaniesShouldBeFound("postalCode.specified=true");

        // Get all the companiesList where postalCode is null
        defaultCompaniesShouldNotBeFound("postalCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByPostalCodeContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where postalCode contains DEFAULT_POSTAL_CODE
        defaultCompaniesShouldBeFound("postalCode.contains=" + DEFAULT_POSTAL_CODE);

        // Get all the companiesList where postalCode contains UPDATED_POSTAL_CODE
        defaultCompaniesShouldNotBeFound("postalCode.contains=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllCompaniesByPostalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where postalCode does not contain DEFAULT_POSTAL_CODE
        defaultCompaniesShouldNotBeFound("postalCode.doesNotContain=" + DEFAULT_POSTAL_CODE);

        // Get all the companiesList where postalCode does not contain UPDATED_POSTAL_CODE
        defaultCompaniesShouldBeFound("postalCode.doesNotContain=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddress equals to DEFAULT_COMPANY_ADDRESS
        defaultCompaniesShouldBeFound("companyAddress.equals=" + DEFAULT_COMPANY_ADDRESS);

        // Get all the companiesList where companyAddress equals to UPDATED_COMPANY_ADDRESS
        defaultCompaniesShouldNotBeFound("companyAddress.equals=" + UPDATED_COMPANY_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddress not equals to DEFAULT_COMPANY_ADDRESS
        defaultCompaniesShouldNotBeFound("companyAddress.notEquals=" + DEFAULT_COMPANY_ADDRESS);

        // Get all the companiesList where companyAddress not equals to UPDATED_COMPANY_ADDRESS
        defaultCompaniesShouldBeFound("companyAddress.notEquals=" + UPDATED_COMPANY_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressIsInShouldWork() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddress in DEFAULT_COMPANY_ADDRESS or UPDATED_COMPANY_ADDRESS
        defaultCompaniesShouldBeFound("companyAddress.in=" + DEFAULT_COMPANY_ADDRESS + "," + UPDATED_COMPANY_ADDRESS);

        // Get all the companiesList where companyAddress equals to UPDATED_COMPANY_ADDRESS
        defaultCompaniesShouldNotBeFound("companyAddress.in=" + UPDATED_COMPANY_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddress is not null
        defaultCompaniesShouldBeFound("companyAddress.specified=true");

        // Get all the companiesList where companyAddress is null
        defaultCompaniesShouldNotBeFound("companyAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddress contains DEFAULT_COMPANY_ADDRESS
        defaultCompaniesShouldBeFound("companyAddress.contains=" + DEFAULT_COMPANY_ADDRESS);

        // Get all the companiesList where companyAddress contains UPDATED_COMPANY_ADDRESS
        defaultCompaniesShouldNotBeFound("companyAddress.contains=" + UPDATED_COMPANY_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressNotContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddress does not contain DEFAULT_COMPANY_ADDRESS
        defaultCompaniesShouldNotBeFound("companyAddress.doesNotContain=" + DEFAULT_COMPANY_ADDRESS);

        // Get all the companiesList where companyAddress does not contain UPDATED_COMPANY_ADDRESS
        defaultCompaniesShouldBeFound("companyAddress.doesNotContain=" + UPDATED_COMPANY_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressComplementIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddressComplement equals to DEFAULT_COMPANY_ADDRESS_COMPLEMENT
        defaultCompaniesShouldBeFound("companyAddressComplement.equals=" + DEFAULT_COMPANY_ADDRESS_COMPLEMENT);

        // Get all the companiesList where companyAddressComplement equals to UPDATED_COMPANY_ADDRESS_COMPLEMENT
        defaultCompaniesShouldNotBeFound("companyAddressComplement.equals=" + UPDATED_COMPANY_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressComplementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddressComplement not equals to DEFAULT_COMPANY_ADDRESS_COMPLEMENT
        defaultCompaniesShouldNotBeFound("companyAddressComplement.notEquals=" + DEFAULT_COMPANY_ADDRESS_COMPLEMENT);

        // Get all the companiesList where companyAddressComplement not equals to UPDATED_COMPANY_ADDRESS_COMPLEMENT
        defaultCompaniesShouldBeFound("companyAddressComplement.notEquals=" + UPDATED_COMPANY_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressComplementIsInShouldWork() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddressComplement in DEFAULT_COMPANY_ADDRESS_COMPLEMENT or UPDATED_COMPANY_ADDRESS_COMPLEMENT
        defaultCompaniesShouldBeFound(
            "companyAddressComplement.in=" + DEFAULT_COMPANY_ADDRESS_COMPLEMENT + "," + UPDATED_COMPANY_ADDRESS_COMPLEMENT
        );

        // Get all the companiesList where companyAddressComplement equals to UPDATED_COMPANY_ADDRESS_COMPLEMENT
        defaultCompaniesShouldNotBeFound("companyAddressComplement.in=" + UPDATED_COMPANY_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressComplementIsNullOrNotNull() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddressComplement is not null
        defaultCompaniesShouldBeFound("companyAddressComplement.specified=true");

        // Get all the companiesList where companyAddressComplement is null
        defaultCompaniesShouldNotBeFound("companyAddressComplement.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressComplementContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddressComplement contains DEFAULT_COMPANY_ADDRESS_COMPLEMENT
        defaultCompaniesShouldBeFound("companyAddressComplement.contains=" + DEFAULT_COMPANY_ADDRESS_COMPLEMENT);

        // Get all the companiesList where companyAddressComplement contains UPDATED_COMPANY_ADDRESS_COMPLEMENT
        defaultCompaniesShouldNotBeFound("companyAddressComplement.contains=" + UPDATED_COMPANY_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressComplementNotContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddressComplement does not contain DEFAULT_COMPANY_ADDRESS_COMPLEMENT
        defaultCompaniesShouldNotBeFound("companyAddressComplement.doesNotContain=" + DEFAULT_COMPANY_ADDRESS_COMPLEMENT);

        // Get all the companiesList where companyAddressComplement does not contain UPDATED_COMPANY_ADDRESS_COMPLEMENT
        defaultCompaniesShouldBeFound("companyAddressComplement.doesNotContain=" + UPDATED_COMPANY_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddressNumber equals to DEFAULT_COMPANY_ADDRESS_NUMBER
        defaultCompaniesShouldBeFound("companyAddressNumber.equals=" + DEFAULT_COMPANY_ADDRESS_NUMBER);

        // Get all the companiesList where companyAddressNumber equals to UPDATED_COMPANY_ADDRESS_NUMBER
        defaultCompaniesShouldNotBeFound("companyAddressNumber.equals=" + UPDATED_COMPANY_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddressNumber not equals to DEFAULT_COMPANY_ADDRESS_NUMBER
        defaultCompaniesShouldNotBeFound("companyAddressNumber.notEquals=" + DEFAULT_COMPANY_ADDRESS_NUMBER);

        // Get all the companiesList where companyAddressNumber not equals to UPDATED_COMPANY_ADDRESS_NUMBER
        defaultCompaniesShouldBeFound("companyAddressNumber.notEquals=" + UPDATED_COMPANY_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressNumberIsInShouldWork() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddressNumber in DEFAULT_COMPANY_ADDRESS_NUMBER or UPDATED_COMPANY_ADDRESS_NUMBER
        defaultCompaniesShouldBeFound("companyAddressNumber.in=" + DEFAULT_COMPANY_ADDRESS_NUMBER + "," + UPDATED_COMPANY_ADDRESS_NUMBER);

        // Get all the companiesList where companyAddressNumber equals to UPDATED_COMPANY_ADDRESS_NUMBER
        defaultCompaniesShouldNotBeFound("companyAddressNumber.in=" + UPDATED_COMPANY_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddressNumber is not null
        defaultCompaniesShouldBeFound("companyAddressNumber.specified=true");

        // Get all the companiesList where companyAddressNumber is null
        defaultCompaniesShouldNotBeFound("companyAddressNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddressNumber is greater than or equal to DEFAULT_COMPANY_ADDRESS_NUMBER
        defaultCompaniesShouldBeFound("companyAddressNumber.greaterThanOrEqual=" + DEFAULT_COMPANY_ADDRESS_NUMBER);

        // Get all the companiesList where companyAddressNumber is greater than or equal to UPDATED_COMPANY_ADDRESS_NUMBER
        defaultCompaniesShouldNotBeFound("companyAddressNumber.greaterThanOrEqual=" + UPDATED_COMPANY_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddressNumber is less than or equal to DEFAULT_COMPANY_ADDRESS_NUMBER
        defaultCompaniesShouldBeFound("companyAddressNumber.lessThanOrEqual=" + DEFAULT_COMPANY_ADDRESS_NUMBER);

        // Get all the companiesList where companyAddressNumber is less than or equal to SMALLER_COMPANY_ADDRESS_NUMBER
        defaultCompaniesShouldNotBeFound("companyAddressNumber.lessThanOrEqual=" + SMALLER_COMPANY_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddressNumber is less than DEFAULT_COMPANY_ADDRESS_NUMBER
        defaultCompaniesShouldNotBeFound("companyAddressNumber.lessThan=" + DEFAULT_COMPANY_ADDRESS_NUMBER);

        // Get all the companiesList where companyAddressNumber is less than UPDATED_COMPANY_ADDRESS_NUMBER
        defaultCompaniesShouldBeFound("companyAddressNumber.lessThan=" + UPDATED_COMPANY_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddressNumber is greater than DEFAULT_COMPANY_ADDRESS_NUMBER
        defaultCompaniesShouldNotBeFound("companyAddressNumber.greaterThan=" + DEFAULT_COMPANY_ADDRESS_NUMBER);

        // Get all the companiesList where companyAddressNumber is greater than SMALLER_COMPANY_ADDRESS_NUMBER
        defaultCompaniesShouldBeFound("companyAddressNumber.greaterThan=" + SMALLER_COMPANY_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressNeighborhoodIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddressNeighborhood equals to DEFAULT_COMPANY_ADDRESS_NEIGHBORHOOD
        defaultCompaniesShouldBeFound("companyAddressNeighborhood.equals=" + DEFAULT_COMPANY_ADDRESS_NEIGHBORHOOD);

        // Get all the companiesList where companyAddressNeighborhood equals to UPDATED_COMPANY_ADDRESS_NEIGHBORHOOD
        defaultCompaniesShouldNotBeFound("companyAddressNeighborhood.equals=" + UPDATED_COMPANY_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressNeighborhoodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddressNeighborhood not equals to DEFAULT_COMPANY_ADDRESS_NEIGHBORHOOD
        defaultCompaniesShouldNotBeFound("companyAddressNeighborhood.notEquals=" + DEFAULT_COMPANY_ADDRESS_NEIGHBORHOOD);

        // Get all the companiesList where companyAddressNeighborhood not equals to UPDATED_COMPANY_ADDRESS_NEIGHBORHOOD
        defaultCompaniesShouldBeFound("companyAddressNeighborhood.notEquals=" + UPDATED_COMPANY_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressNeighborhoodIsInShouldWork() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddressNeighborhood in DEFAULT_COMPANY_ADDRESS_NEIGHBORHOOD or UPDATED_COMPANY_ADDRESS_NEIGHBORHOOD
        defaultCompaniesShouldBeFound(
            "companyAddressNeighborhood.in=" + DEFAULT_COMPANY_ADDRESS_NEIGHBORHOOD + "," + UPDATED_COMPANY_ADDRESS_NEIGHBORHOOD
        );

        // Get all the companiesList where companyAddressNeighborhood equals to UPDATED_COMPANY_ADDRESS_NEIGHBORHOOD
        defaultCompaniesShouldNotBeFound("companyAddressNeighborhood.in=" + UPDATED_COMPANY_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressNeighborhoodIsNullOrNotNull() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddressNeighborhood is not null
        defaultCompaniesShouldBeFound("companyAddressNeighborhood.specified=true");

        // Get all the companiesList where companyAddressNeighborhood is null
        defaultCompaniesShouldNotBeFound("companyAddressNeighborhood.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressNeighborhoodContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddressNeighborhood contains DEFAULT_COMPANY_ADDRESS_NEIGHBORHOOD
        defaultCompaniesShouldBeFound("companyAddressNeighborhood.contains=" + DEFAULT_COMPANY_ADDRESS_NEIGHBORHOOD);

        // Get all the companiesList where companyAddressNeighborhood contains UPDATED_COMPANY_ADDRESS_NEIGHBORHOOD
        defaultCompaniesShouldNotBeFound("companyAddressNeighborhood.contains=" + UPDATED_COMPANY_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyAddressNeighborhoodNotContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyAddressNeighborhood does not contain DEFAULT_COMPANY_ADDRESS_NEIGHBORHOOD
        defaultCompaniesShouldNotBeFound("companyAddressNeighborhood.doesNotContain=" + DEFAULT_COMPANY_ADDRESS_NEIGHBORHOOD);

        // Get all the companiesList where companyAddressNeighborhood does not contain UPDATED_COMPANY_ADDRESS_NEIGHBORHOOD
        defaultCompaniesShouldBeFound("companyAddressNeighborhood.doesNotContain=" + UPDATED_COMPANY_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyTelephone equals to DEFAULT_COMPANY_TELEPHONE
        defaultCompaniesShouldBeFound("companyTelephone.equals=" + DEFAULT_COMPANY_TELEPHONE);

        // Get all the companiesList where companyTelephone equals to UPDATED_COMPANY_TELEPHONE
        defaultCompaniesShouldNotBeFound("companyTelephone.equals=" + UPDATED_COMPANY_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyTelephone not equals to DEFAULT_COMPANY_TELEPHONE
        defaultCompaniesShouldNotBeFound("companyTelephone.notEquals=" + DEFAULT_COMPANY_TELEPHONE);

        // Get all the companiesList where companyTelephone not equals to UPDATED_COMPANY_TELEPHONE
        defaultCompaniesShouldBeFound("companyTelephone.notEquals=" + UPDATED_COMPANY_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyTelephone in DEFAULT_COMPANY_TELEPHONE or UPDATED_COMPANY_TELEPHONE
        defaultCompaniesShouldBeFound("companyTelephone.in=" + DEFAULT_COMPANY_TELEPHONE + "," + UPDATED_COMPANY_TELEPHONE);

        // Get all the companiesList where companyTelephone equals to UPDATED_COMPANY_TELEPHONE
        defaultCompaniesShouldNotBeFound("companyTelephone.in=" + UPDATED_COMPANY_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyTelephone is not null
        defaultCompaniesShouldBeFound("companyTelephone.specified=true");

        // Get all the companiesList where companyTelephone is null
        defaultCompaniesShouldNotBeFound("companyTelephone.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyTelephoneContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyTelephone contains DEFAULT_COMPANY_TELEPHONE
        defaultCompaniesShouldBeFound("companyTelephone.contains=" + DEFAULT_COMPANY_TELEPHONE);

        // Get all the companiesList where companyTelephone contains UPDATED_COMPANY_TELEPHONE
        defaultCompaniesShouldNotBeFound("companyTelephone.contains=" + UPDATED_COMPANY_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyTelephone does not contain DEFAULT_COMPANY_TELEPHONE
        defaultCompaniesShouldNotBeFound("companyTelephone.doesNotContain=" + DEFAULT_COMPANY_TELEPHONE);

        // Get all the companiesList where companyTelephone does not contain UPDATED_COMPANY_TELEPHONE
        defaultCompaniesShouldBeFound("companyTelephone.doesNotContain=" + UPDATED_COMPANY_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyEmail equals to DEFAULT_COMPANY_EMAIL
        defaultCompaniesShouldBeFound("companyEmail.equals=" + DEFAULT_COMPANY_EMAIL);

        // Get all the companiesList where companyEmail equals to UPDATED_COMPANY_EMAIL
        defaultCompaniesShouldNotBeFound("companyEmail.equals=" + UPDATED_COMPANY_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyEmail not equals to DEFAULT_COMPANY_EMAIL
        defaultCompaniesShouldNotBeFound("companyEmail.notEquals=" + DEFAULT_COMPANY_EMAIL);

        // Get all the companiesList where companyEmail not equals to UPDATED_COMPANY_EMAIL
        defaultCompaniesShouldBeFound("companyEmail.notEquals=" + UPDATED_COMPANY_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyEmailIsInShouldWork() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyEmail in DEFAULT_COMPANY_EMAIL or UPDATED_COMPANY_EMAIL
        defaultCompaniesShouldBeFound("companyEmail.in=" + DEFAULT_COMPANY_EMAIL + "," + UPDATED_COMPANY_EMAIL);

        // Get all the companiesList where companyEmail equals to UPDATED_COMPANY_EMAIL
        defaultCompaniesShouldNotBeFound("companyEmail.in=" + UPDATED_COMPANY_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyEmail is not null
        defaultCompaniesShouldBeFound("companyEmail.specified=true");

        // Get all the companiesList where companyEmail is null
        defaultCompaniesShouldNotBeFound("companyEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyEmailContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyEmail contains DEFAULT_COMPANY_EMAIL
        defaultCompaniesShouldBeFound("companyEmail.contains=" + DEFAULT_COMPANY_EMAIL);

        // Get all the companiesList where companyEmail contains UPDATED_COMPANY_EMAIL
        defaultCompaniesShouldNotBeFound("companyEmail.contains=" + UPDATED_COMPANY_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyEmailNotContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where companyEmail does not contain DEFAULT_COMPANY_EMAIL
        defaultCompaniesShouldNotBeFound("companyEmail.doesNotContain=" + DEFAULT_COMPANY_EMAIL);

        // Get all the companiesList where companyEmail does not contain UPDATED_COMPANY_EMAIL
        defaultCompaniesShouldBeFound("companyEmail.doesNotContain=" + UPDATED_COMPANY_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompaniesByResponsibleContactIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where responsibleContact equals to DEFAULT_RESPONSIBLE_CONTACT
        defaultCompaniesShouldBeFound("responsibleContact.equals=" + DEFAULT_RESPONSIBLE_CONTACT);

        // Get all the companiesList where responsibleContact equals to UPDATED_RESPONSIBLE_CONTACT
        defaultCompaniesShouldNotBeFound("responsibleContact.equals=" + UPDATED_RESPONSIBLE_CONTACT);
    }

    @Test
    @Transactional
    void getAllCompaniesByResponsibleContactIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where responsibleContact not equals to DEFAULT_RESPONSIBLE_CONTACT
        defaultCompaniesShouldNotBeFound("responsibleContact.notEquals=" + DEFAULT_RESPONSIBLE_CONTACT);

        // Get all the companiesList where responsibleContact not equals to UPDATED_RESPONSIBLE_CONTACT
        defaultCompaniesShouldBeFound("responsibleContact.notEquals=" + UPDATED_RESPONSIBLE_CONTACT);
    }

    @Test
    @Transactional
    void getAllCompaniesByResponsibleContactIsInShouldWork() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where responsibleContact in DEFAULT_RESPONSIBLE_CONTACT or UPDATED_RESPONSIBLE_CONTACT
        defaultCompaniesShouldBeFound("responsibleContact.in=" + DEFAULT_RESPONSIBLE_CONTACT + "," + UPDATED_RESPONSIBLE_CONTACT);

        // Get all the companiesList where responsibleContact equals to UPDATED_RESPONSIBLE_CONTACT
        defaultCompaniesShouldNotBeFound("responsibleContact.in=" + UPDATED_RESPONSIBLE_CONTACT);
    }

    @Test
    @Transactional
    void getAllCompaniesByResponsibleContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where responsibleContact is not null
        defaultCompaniesShouldBeFound("responsibleContact.specified=true");

        // Get all the companiesList where responsibleContact is null
        defaultCompaniesShouldNotBeFound("responsibleContact.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByResponsibleContactContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where responsibleContact contains DEFAULT_RESPONSIBLE_CONTACT
        defaultCompaniesShouldBeFound("responsibleContact.contains=" + DEFAULT_RESPONSIBLE_CONTACT);

        // Get all the companiesList where responsibleContact contains UPDATED_RESPONSIBLE_CONTACT
        defaultCompaniesShouldNotBeFound("responsibleContact.contains=" + UPDATED_RESPONSIBLE_CONTACT);
    }

    @Test
    @Transactional
    void getAllCompaniesByResponsibleContactNotContainsSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        // Get all the companiesList where responsibleContact does not contain DEFAULT_RESPONSIBLE_CONTACT
        defaultCompaniesShouldNotBeFound("responsibleContact.doesNotContain=" + DEFAULT_RESPONSIBLE_CONTACT);

        // Get all the companiesList where responsibleContact does not contain UPDATED_RESPONSIBLE_CONTACT
        defaultCompaniesShouldBeFound("responsibleContact.doesNotContain=" + UPDATED_RESPONSIBLE_CONTACT);
    }

    @Test
    @Transactional
    void getAllCompaniesByAffiliatesIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);
        Affiliates affiliates = AffiliatesResourceIT.createEntity(em);
        em.persist(affiliates);
        em.flush();
        companies.addAffiliates(affiliates);
        companiesRepository.saveAndFlush(companies);
        Long affiliatesId = affiliates.getId();

        // Get all the companiesList where affiliates equals to affiliatesId
        defaultCompaniesShouldBeFound("affiliatesId.equals=" + affiliatesId);

        // Get all the companiesList where affiliates equals to (affiliatesId + 1)
        defaultCompaniesShouldNotBeFound("affiliatesId.equals=" + (affiliatesId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesByEmployeesIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);
        Employees employees = EmployeesResourceIT.createEntity(em);
        em.persist(employees);
        em.flush();
        companies.addEmployees(employees);
        companiesRepository.saveAndFlush(companies);
        Long employeesId = employees.getId();

        // Get all the companiesList where employees equals to employeesId
        defaultCompaniesShouldBeFound("employeesId.equals=" + employeesId);

        // Get all the companiesList where employees equals to (employeesId + 1)
        defaultCompaniesShouldNotBeFound("employeesId.equals=" + (employeesId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesByCitiesIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);
        Cities cities = CitiesResourceIT.createEntity(em);
        em.persist(cities);
        em.flush();
        companies.setCities(cities);
        companiesRepository.saveAndFlush(companies);
        Long citiesId = cities.getId();

        // Get all the companiesList where cities equals to citiesId
        defaultCompaniesShouldBeFound("citiesId.equals=" + citiesId);

        // Get all the companiesList where cities equals to (citiesId + 1)
        defaultCompaniesShouldNotBeFound("citiesId.equals=" + (citiesId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesByStateProvincesIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);
        StateProvinces stateProvinces = StateProvincesResourceIT.createEntity(em);
        em.persist(stateProvinces);
        em.flush();
        companies.setStateProvinces(stateProvinces);
        companiesRepository.saveAndFlush(companies);
        Long stateProvincesId = stateProvinces.getId();

        // Get all the companiesList where stateProvinces equals to stateProvincesId
        defaultCompaniesShouldBeFound("stateProvincesId.equals=" + stateProvincesId);

        // Get all the companiesList where stateProvinces equals to (stateProvincesId + 1)
        defaultCompaniesShouldNotBeFound("stateProvincesId.equals=" + (stateProvincesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompaniesShouldBeFound(String filter) throws Exception {
        restCompaniesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companies.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].tradeName").value(hasItem(DEFAULT_TRADE_NAME)))
            .andExpect(jsonPath("$.[*].companyNumber").value(hasItem(DEFAULT_COMPANY_NUMBER)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].companyAddress").value(hasItem(DEFAULT_COMPANY_ADDRESS)))
            .andExpect(jsonPath("$.[*].companyAddressComplement").value(hasItem(DEFAULT_COMPANY_ADDRESS_COMPLEMENT)))
            .andExpect(jsonPath("$.[*].companyAddressNumber").value(hasItem(DEFAULT_COMPANY_ADDRESS_NUMBER)))
            .andExpect(jsonPath("$.[*].companyAddressNeighborhood").value(hasItem(DEFAULT_COMPANY_ADDRESS_NEIGHBORHOOD)))
            .andExpect(jsonPath("$.[*].companyTelephone").value(hasItem(DEFAULT_COMPANY_TELEPHONE)))
            .andExpect(jsonPath("$.[*].companyEmail").value(hasItem(DEFAULT_COMPANY_EMAIL)))
            .andExpect(jsonPath("$.[*].responsibleContact").value(hasItem(DEFAULT_RESPONSIBLE_CONTACT)));

        // Check, that the count call also returns 1
        restCompaniesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompaniesShouldNotBeFound(String filter) throws Exception {
        restCompaniesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompaniesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCompanies() throws Exception {
        // Get the companies
        restCompaniesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCompanies() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();

        // Update the companies
        Companies updatedCompanies = companiesRepository.findById(companies.getId()).get();
        // Disconnect from session so that the updates on updatedCompanies are not directly saved in db
        em.detach(updatedCompanies);
        updatedCompanies
            .companyName(UPDATED_COMPANY_NAME)
            .tradeName(UPDATED_TRADE_NAME)
            .companyNumber(UPDATED_COMPANY_NUMBER)
            .postalCode(UPDATED_POSTAL_CODE)
            .companyAddress(UPDATED_COMPANY_ADDRESS)
            .companyAddressComplement(UPDATED_COMPANY_ADDRESS_COMPLEMENT)
            .companyAddressNumber(UPDATED_COMPANY_ADDRESS_NUMBER)
            .companyAddressNeighborhood(UPDATED_COMPANY_ADDRESS_NEIGHBORHOOD)
            .companyTelephone(UPDATED_COMPANY_TELEPHONE)
            .companyEmail(UPDATED_COMPANY_EMAIL)
            .responsibleContact(UPDATED_RESPONSIBLE_CONTACT);
        CompaniesDTO companiesDTO = companiesMapper.toDto(updatedCompanies);

        restCompaniesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companiesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companiesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
        Companies testCompanies = companiesList.get(companiesList.size() - 1);
        assertThat(testCompanies.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCompanies.getTradeName()).isEqualTo(UPDATED_TRADE_NAME);
        assertThat(testCompanies.getCompanyNumber()).isEqualTo(UPDATED_COMPANY_NUMBER);
        assertThat(testCompanies.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testCompanies.getCompanyAddress()).isEqualTo(UPDATED_COMPANY_ADDRESS);
        assertThat(testCompanies.getCompanyAddressComplement()).isEqualTo(UPDATED_COMPANY_ADDRESS_COMPLEMENT);
        assertThat(testCompanies.getCompanyAddressNumber()).isEqualTo(UPDATED_COMPANY_ADDRESS_NUMBER);
        assertThat(testCompanies.getCompanyAddressNeighborhood()).isEqualTo(UPDATED_COMPANY_ADDRESS_NEIGHBORHOOD);
        assertThat(testCompanies.getCompanyTelephone()).isEqualTo(UPDATED_COMPANY_TELEPHONE);
        assertThat(testCompanies.getCompanyEmail()).isEqualTo(UPDATED_COMPANY_EMAIL);
        assertThat(testCompanies.getResponsibleContact()).isEqualTo(UPDATED_RESPONSIBLE_CONTACT);
    }

    @Test
    @Transactional
    void putNonExistingCompanies() throws Exception {
        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();
        companies.setId(count.incrementAndGet());

        // Create the Companies
        CompaniesDTO companiesDTO = companiesMapper.toDto(companies);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompaniesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companiesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompanies() throws Exception {
        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();
        companies.setId(count.incrementAndGet());

        // Create the Companies
        CompaniesDTO companiesDTO = companiesMapper.toDto(companies);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompaniesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompanies() throws Exception {
        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();
        companies.setId(count.incrementAndGet());

        // Create the Companies
        CompaniesDTO companiesDTO = companiesMapper.toDto(companies);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompaniesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companiesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompaniesWithPatch() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();

        // Update the companies using partial update
        Companies partialUpdatedCompanies = new Companies();
        partialUpdatedCompanies.setId(companies.getId());

        partialUpdatedCompanies
            .companyName(UPDATED_COMPANY_NAME)
            .companyAddressComplement(UPDATED_COMPANY_ADDRESS_COMPLEMENT)
            .companyAddressNumber(UPDATED_COMPANY_ADDRESS_NUMBER)
            .companyTelephone(UPDATED_COMPANY_TELEPHONE)
            .responsibleContact(UPDATED_RESPONSIBLE_CONTACT);

        restCompaniesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanies.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanies))
            )
            .andExpect(status().isOk());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
        Companies testCompanies = companiesList.get(companiesList.size() - 1);
        assertThat(testCompanies.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCompanies.getTradeName()).isEqualTo(DEFAULT_TRADE_NAME);
        assertThat(testCompanies.getCompanyNumber()).isEqualTo(DEFAULT_COMPANY_NUMBER);
        assertThat(testCompanies.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testCompanies.getCompanyAddress()).isEqualTo(DEFAULT_COMPANY_ADDRESS);
        assertThat(testCompanies.getCompanyAddressComplement()).isEqualTo(UPDATED_COMPANY_ADDRESS_COMPLEMENT);
        assertThat(testCompanies.getCompanyAddressNumber()).isEqualTo(UPDATED_COMPANY_ADDRESS_NUMBER);
        assertThat(testCompanies.getCompanyAddressNeighborhood()).isEqualTo(DEFAULT_COMPANY_ADDRESS_NEIGHBORHOOD);
        assertThat(testCompanies.getCompanyTelephone()).isEqualTo(UPDATED_COMPANY_TELEPHONE);
        assertThat(testCompanies.getCompanyEmail()).isEqualTo(DEFAULT_COMPANY_EMAIL);
        assertThat(testCompanies.getResponsibleContact()).isEqualTo(UPDATED_RESPONSIBLE_CONTACT);
    }

    @Test
    @Transactional
    void fullUpdateCompaniesWithPatch() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();

        // Update the companies using partial update
        Companies partialUpdatedCompanies = new Companies();
        partialUpdatedCompanies.setId(companies.getId());

        partialUpdatedCompanies
            .companyName(UPDATED_COMPANY_NAME)
            .tradeName(UPDATED_TRADE_NAME)
            .companyNumber(UPDATED_COMPANY_NUMBER)
            .postalCode(UPDATED_POSTAL_CODE)
            .companyAddress(UPDATED_COMPANY_ADDRESS)
            .companyAddressComplement(UPDATED_COMPANY_ADDRESS_COMPLEMENT)
            .companyAddressNumber(UPDATED_COMPANY_ADDRESS_NUMBER)
            .companyAddressNeighborhood(UPDATED_COMPANY_ADDRESS_NEIGHBORHOOD)
            .companyTelephone(UPDATED_COMPANY_TELEPHONE)
            .companyEmail(UPDATED_COMPANY_EMAIL)
            .responsibleContact(UPDATED_RESPONSIBLE_CONTACT);

        restCompaniesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanies.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanies))
            )
            .andExpect(status().isOk());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
        Companies testCompanies = companiesList.get(companiesList.size() - 1);
        assertThat(testCompanies.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCompanies.getTradeName()).isEqualTo(UPDATED_TRADE_NAME);
        assertThat(testCompanies.getCompanyNumber()).isEqualTo(UPDATED_COMPANY_NUMBER);
        assertThat(testCompanies.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testCompanies.getCompanyAddress()).isEqualTo(UPDATED_COMPANY_ADDRESS);
        assertThat(testCompanies.getCompanyAddressComplement()).isEqualTo(UPDATED_COMPANY_ADDRESS_COMPLEMENT);
        assertThat(testCompanies.getCompanyAddressNumber()).isEqualTo(UPDATED_COMPANY_ADDRESS_NUMBER);
        assertThat(testCompanies.getCompanyAddressNeighborhood()).isEqualTo(UPDATED_COMPANY_ADDRESS_NEIGHBORHOOD);
        assertThat(testCompanies.getCompanyTelephone()).isEqualTo(UPDATED_COMPANY_TELEPHONE);
        assertThat(testCompanies.getCompanyEmail()).isEqualTo(UPDATED_COMPANY_EMAIL);
        assertThat(testCompanies.getResponsibleContact()).isEqualTo(UPDATED_RESPONSIBLE_CONTACT);
    }

    @Test
    @Transactional
    void patchNonExistingCompanies() throws Exception {
        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();
        companies.setId(count.incrementAndGet());

        // Create the Companies
        CompaniesDTO companiesDTO = companiesMapper.toDto(companies);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompaniesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companiesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompanies() throws Exception {
        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();
        companies.setId(count.incrementAndGet());

        // Create the Companies
        CompaniesDTO companiesDTO = companiesMapper.toDto(companies);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompaniesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompanies() throws Exception {
        int databaseSizeBeforeUpdate = companiesRepository.findAll().size();
        companies.setId(count.incrementAndGet());

        // Create the Companies
        CompaniesDTO companiesDTO = companiesMapper.toDto(companies);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompaniesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(companiesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Companies in the database
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompanies() throws Exception {
        // Initialize the database
        companiesRepository.saveAndFlush(companies);

        int databaseSizeBeforeDelete = companiesRepository.findAll().size();

        // Delete the companies
        restCompaniesMockMvc
            .perform(delete(ENTITY_API_URL_ID, companies.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Companies> companiesList = companiesRepository.findAll();
        assertThat(companiesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
