package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.AdministrativeFeesRanges;
import com.genesisoft.transporte.domain.Affiliates;
import com.genesisoft.transporte.domain.Cities;
import com.genesisoft.transporte.domain.Companies;
import com.genesisoft.transporte.domain.CostCenter;
import com.genesisoft.transporte.domain.Customers;
import com.genesisoft.transporte.domain.CustomersGroups;
import com.genesisoft.transporte.domain.Employees;
import com.genesisoft.transporte.domain.Fees;
import com.genesisoft.transporte.domain.Housing;
import com.genesisoft.transporte.domain.Insurances;
import com.genesisoft.transporte.domain.Parking;
import com.genesisoft.transporte.domain.Positions;
import com.genesisoft.transporte.domain.StateProvinces;
import com.genesisoft.transporte.domain.Status;
import com.genesisoft.transporte.domain.StatusAttachments;
import com.genesisoft.transporte.domain.Suppliers;
import com.genesisoft.transporte.domain.VehicleControls;
import com.genesisoft.transporte.repository.AffiliatesRepository;
import com.genesisoft.transporte.service.criteria.AffiliatesCriteria;
import com.genesisoft.transporte.service.dto.AffiliatesDTO;
import com.genesisoft.transporte.service.mapper.AffiliatesMapper;
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
 * Integration tests for the {@link AffiliatesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AffiliatesResourceIT {

    private static final String DEFAULT_BRANCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_SAME_COMPANY_ADDRESS = false;
    private static final Boolean UPDATED_USE_SAME_COMPANY_ADDRESS = true;

    private static final String DEFAULT_BRANCH_POSTAL_CODE = "AAAAAAAAA";
    private static final String UPDATED_BRANCH_POSTAL_CODE = "BBBBBBBBB";

    private static final String DEFAULT_BRANCH_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_ADDRESS_COMPLEMENT = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_ADDRESS_COMPLEMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_BRANCH_ADDRESS_NUMBER = 1;
    private static final Integer UPDATED_BRANCH_ADDRESS_NUMBER = 2;
    private static final Integer SMALLER_BRANCH_ADDRESS_NUMBER = 1 - 1;

    private static final String DEFAULT_BRANCH_ADDRESS_NEIGHBORHOOD = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_ADDRESS_NEIGHBORHOOD = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSIBLE_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSIBLE_CONTACT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/affiliates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AffiliatesRepository affiliatesRepository;

    @Autowired
    private AffiliatesMapper affiliatesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAffiliatesMockMvc;

    private Affiliates affiliates;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Affiliates createEntity(EntityManager em) {
        Affiliates affiliates = new Affiliates()
            .branchName(DEFAULT_BRANCH_NAME)
            .branchNumber(DEFAULT_BRANCH_NUMBER)
            .useSameCompanyAddress(DEFAULT_USE_SAME_COMPANY_ADDRESS)
            .branchPostalCode(DEFAULT_BRANCH_POSTAL_CODE)
            .branchAddress(DEFAULT_BRANCH_ADDRESS)
            .branchAddressComplement(DEFAULT_BRANCH_ADDRESS_COMPLEMENT)
            .branchAddressNumber(DEFAULT_BRANCH_ADDRESS_NUMBER)
            .branchAddressNeighborhood(DEFAULT_BRANCH_ADDRESS_NEIGHBORHOOD)
            .branchTelephone(DEFAULT_BRANCH_TELEPHONE)
            .branchEmail(DEFAULT_BRANCH_EMAIL)
            .responsibleContact(DEFAULT_RESPONSIBLE_CONTACT);
        // Add required entity
        StateProvinces stateProvinces;
        if (TestUtil.findAll(em, StateProvinces.class).isEmpty()) {
            stateProvinces = StateProvincesResourceIT.createEntity(em);
            em.persist(stateProvinces);
            em.flush();
        } else {
            stateProvinces = TestUtil.findAll(em, StateProvinces.class).get(0);
        }
        affiliates.setStateProvinces(stateProvinces);
        // Add required entity
        Cities cities;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            cities = CitiesResourceIT.createEntity(em);
            em.persist(cities);
            em.flush();
        } else {
            cities = TestUtil.findAll(em, Cities.class).get(0);
        }
        affiliates.setCities(cities);
        // Add required entity
        Companies companies;
        if (TestUtil.findAll(em, Companies.class).isEmpty()) {
            companies = CompaniesResourceIT.createEntity(em);
            em.persist(companies);
            em.flush();
        } else {
            companies = TestUtil.findAll(em, Companies.class).get(0);
        }
        affiliates.setCompanies(companies);
        return affiliates;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Affiliates createUpdatedEntity(EntityManager em) {
        Affiliates affiliates = new Affiliates()
            .branchName(UPDATED_BRANCH_NAME)
            .branchNumber(UPDATED_BRANCH_NUMBER)
            .useSameCompanyAddress(UPDATED_USE_SAME_COMPANY_ADDRESS)
            .branchPostalCode(UPDATED_BRANCH_POSTAL_CODE)
            .branchAddress(UPDATED_BRANCH_ADDRESS)
            .branchAddressComplement(UPDATED_BRANCH_ADDRESS_COMPLEMENT)
            .branchAddressNumber(UPDATED_BRANCH_ADDRESS_NUMBER)
            .branchAddressNeighborhood(UPDATED_BRANCH_ADDRESS_NEIGHBORHOOD)
            .branchTelephone(UPDATED_BRANCH_TELEPHONE)
            .branchEmail(UPDATED_BRANCH_EMAIL)
            .responsibleContact(UPDATED_RESPONSIBLE_CONTACT);
        // Add required entity
        StateProvinces stateProvinces;
        if (TestUtil.findAll(em, StateProvinces.class).isEmpty()) {
            stateProvinces = StateProvincesResourceIT.createUpdatedEntity(em);
            em.persist(stateProvinces);
            em.flush();
        } else {
            stateProvinces = TestUtil.findAll(em, StateProvinces.class).get(0);
        }
        affiliates.setStateProvinces(stateProvinces);
        // Add required entity
        Cities cities;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            cities = CitiesResourceIT.createUpdatedEntity(em);
            em.persist(cities);
            em.flush();
        } else {
            cities = TestUtil.findAll(em, Cities.class).get(0);
        }
        affiliates.setCities(cities);
        // Add required entity
        Companies companies;
        if (TestUtil.findAll(em, Companies.class).isEmpty()) {
            companies = CompaniesResourceIT.createUpdatedEntity(em);
            em.persist(companies);
            em.flush();
        } else {
            companies = TestUtil.findAll(em, Companies.class).get(0);
        }
        affiliates.setCompanies(companies);
        return affiliates;
    }

    @BeforeEach
    public void initTest() {
        affiliates = createEntity(em);
    }

    @Test
    @Transactional
    void createAffiliates() throws Exception {
        int databaseSizeBeforeCreate = affiliatesRepository.findAll().size();
        // Create the Affiliates
        AffiliatesDTO affiliatesDTO = affiliatesMapper.toDto(affiliates);
        restAffiliatesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliatesDTO)))
            .andExpect(status().isCreated());

        // Validate the Affiliates in the database
        List<Affiliates> affiliatesList = affiliatesRepository.findAll();
        assertThat(affiliatesList).hasSize(databaseSizeBeforeCreate + 1);
        Affiliates testAffiliates = affiliatesList.get(affiliatesList.size() - 1);
        assertThat(testAffiliates.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testAffiliates.getBranchNumber()).isEqualTo(DEFAULT_BRANCH_NUMBER);
        assertThat(testAffiliates.getUseSameCompanyAddress()).isEqualTo(DEFAULT_USE_SAME_COMPANY_ADDRESS);
        assertThat(testAffiliates.getBranchPostalCode()).isEqualTo(DEFAULT_BRANCH_POSTAL_CODE);
        assertThat(testAffiliates.getBranchAddress()).isEqualTo(DEFAULT_BRANCH_ADDRESS);
        assertThat(testAffiliates.getBranchAddressComplement()).isEqualTo(DEFAULT_BRANCH_ADDRESS_COMPLEMENT);
        assertThat(testAffiliates.getBranchAddressNumber()).isEqualTo(DEFAULT_BRANCH_ADDRESS_NUMBER);
        assertThat(testAffiliates.getBranchAddressNeighborhood()).isEqualTo(DEFAULT_BRANCH_ADDRESS_NEIGHBORHOOD);
        assertThat(testAffiliates.getBranchTelephone()).isEqualTo(DEFAULT_BRANCH_TELEPHONE);
        assertThat(testAffiliates.getBranchEmail()).isEqualTo(DEFAULT_BRANCH_EMAIL);
        assertThat(testAffiliates.getResponsibleContact()).isEqualTo(DEFAULT_RESPONSIBLE_CONTACT);
    }

    @Test
    @Transactional
    void createAffiliatesWithExistingId() throws Exception {
        // Create the Affiliates with an existing ID
        affiliates.setId(1L);
        AffiliatesDTO affiliatesDTO = affiliatesMapper.toDto(affiliates);

        int databaseSizeBeforeCreate = affiliatesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAffiliatesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliatesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Affiliates in the database
        List<Affiliates> affiliatesList = affiliatesRepository.findAll();
        assertThat(affiliatesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBranchNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliatesRepository.findAll().size();
        // set the field null
        affiliates.setBranchName(null);

        // Create the Affiliates, which fails.
        AffiliatesDTO affiliatesDTO = affiliatesMapper.toDto(affiliates);

        restAffiliatesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliatesDTO)))
            .andExpect(status().isBadRequest());

        List<Affiliates> affiliatesList = affiliatesRepository.findAll();
        assertThat(affiliatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBranchNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliatesRepository.findAll().size();
        // set the field null
        affiliates.setBranchNumber(null);

        // Create the Affiliates, which fails.
        AffiliatesDTO affiliatesDTO = affiliatesMapper.toDto(affiliates);

        restAffiliatesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliatesDTO)))
            .andExpect(status().isBadRequest());

        List<Affiliates> affiliatesList = affiliatesRepository.findAll();
        assertThat(affiliatesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAffiliates() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList
        restAffiliatesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(affiliates.getId().intValue())))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].branchNumber").value(hasItem(DEFAULT_BRANCH_NUMBER)))
            .andExpect(jsonPath("$.[*].useSameCompanyAddress").value(hasItem(DEFAULT_USE_SAME_COMPANY_ADDRESS.booleanValue())))
            .andExpect(jsonPath("$.[*].branchPostalCode").value(hasItem(DEFAULT_BRANCH_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].branchAddress").value(hasItem(DEFAULT_BRANCH_ADDRESS)))
            .andExpect(jsonPath("$.[*].branchAddressComplement").value(hasItem(DEFAULT_BRANCH_ADDRESS_COMPLEMENT)))
            .andExpect(jsonPath("$.[*].branchAddressNumber").value(hasItem(DEFAULT_BRANCH_ADDRESS_NUMBER)))
            .andExpect(jsonPath("$.[*].branchAddressNeighborhood").value(hasItem(DEFAULT_BRANCH_ADDRESS_NEIGHBORHOOD)))
            .andExpect(jsonPath("$.[*].branchTelephone").value(hasItem(DEFAULT_BRANCH_TELEPHONE)))
            .andExpect(jsonPath("$.[*].branchEmail").value(hasItem(DEFAULT_BRANCH_EMAIL)))
            .andExpect(jsonPath("$.[*].responsibleContact").value(hasItem(DEFAULT_RESPONSIBLE_CONTACT)));
    }

    @Test
    @Transactional
    void getAffiliates() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get the affiliates
        restAffiliatesMockMvc
            .perform(get(ENTITY_API_URL_ID, affiliates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(affiliates.getId().intValue()))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME))
            .andExpect(jsonPath("$.branchNumber").value(DEFAULT_BRANCH_NUMBER))
            .andExpect(jsonPath("$.useSameCompanyAddress").value(DEFAULT_USE_SAME_COMPANY_ADDRESS.booleanValue()))
            .andExpect(jsonPath("$.branchPostalCode").value(DEFAULT_BRANCH_POSTAL_CODE))
            .andExpect(jsonPath("$.branchAddress").value(DEFAULT_BRANCH_ADDRESS))
            .andExpect(jsonPath("$.branchAddressComplement").value(DEFAULT_BRANCH_ADDRESS_COMPLEMENT))
            .andExpect(jsonPath("$.branchAddressNumber").value(DEFAULT_BRANCH_ADDRESS_NUMBER))
            .andExpect(jsonPath("$.branchAddressNeighborhood").value(DEFAULT_BRANCH_ADDRESS_NEIGHBORHOOD))
            .andExpect(jsonPath("$.branchTelephone").value(DEFAULT_BRANCH_TELEPHONE))
            .andExpect(jsonPath("$.branchEmail").value(DEFAULT_BRANCH_EMAIL))
            .andExpect(jsonPath("$.responsibleContact").value(DEFAULT_RESPONSIBLE_CONTACT));
    }

    @Test
    @Transactional
    void getAffiliatesByIdFiltering() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        Long id = affiliates.getId();

        defaultAffiliatesShouldBeFound("id.equals=" + id);
        defaultAffiliatesShouldNotBeFound("id.notEquals=" + id);

        defaultAffiliatesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAffiliatesShouldNotBeFound("id.greaterThan=" + id);

        defaultAffiliatesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAffiliatesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchNameIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchName equals to DEFAULT_BRANCH_NAME
        defaultAffiliatesShouldBeFound("branchName.equals=" + DEFAULT_BRANCH_NAME);

        // Get all the affiliatesList where branchName equals to UPDATED_BRANCH_NAME
        defaultAffiliatesShouldNotBeFound("branchName.equals=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchName not equals to DEFAULT_BRANCH_NAME
        defaultAffiliatesShouldNotBeFound("branchName.notEquals=" + DEFAULT_BRANCH_NAME);

        // Get all the affiliatesList where branchName not equals to UPDATED_BRANCH_NAME
        defaultAffiliatesShouldBeFound("branchName.notEquals=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchNameIsInShouldWork() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchName in DEFAULT_BRANCH_NAME or UPDATED_BRANCH_NAME
        defaultAffiliatesShouldBeFound("branchName.in=" + DEFAULT_BRANCH_NAME + "," + UPDATED_BRANCH_NAME);

        // Get all the affiliatesList where branchName equals to UPDATED_BRANCH_NAME
        defaultAffiliatesShouldNotBeFound("branchName.in=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchName is not null
        defaultAffiliatesShouldBeFound("branchName.specified=true");

        // Get all the affiliatesList where branchName is null
        defaultAffiliatesShouldNotBeFound("branchName.specified=false");
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchNameContainsSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchName contains DEFAULT_BRANCH_NAME
        defaultAffiliatesShouldBeFound("branchName.contains=" + DEFAULT_BRANCH_NAME);

        // Get all the affiliatesList where branchName contains UPDATED_BRANCH_NAME
        defaultAffiliatesShouldNotBeFound("branchName.contains=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchNameNotContainsSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchName does not contain DEFAULT_BRANCH_NAME
        defaultAffiliatesShouldNotBeFound("branchName.doesNotContain=" + DEFAULT_BRANCH_NAME);

        // Get all the affiliatesList where branchName does not contain UPDATED_BRANCH_NAME
        defaultAffiliatesShouldBeFound("branchName.doesNotContain=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchNumber equals to DEFAULT_BRANCH_NUMBER
        defaultAffiliatesShouldBeFound("branchNumber.equals=" + DEFAULT_BRANCH_NUMBER);

        // Get all the affiliatesList where branchNumber equals to UPDATED_BRANCH_NUMBER
        defaultAffiliatesShouldNotBeFound("branchNumber.equals=" + UPDATED_BRANCH_NUMBER);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchNumber not equals to DEFAULT_BRANCH_NUMBER
        defaultAffiliatesShouldNotBeFound("branchNumber.notEquals=" + DEFAULT_BRANCH_NUMBER);

        // Get all the affiliatesList where branchNumber not equals to UPDATED_BRANCH_NUMBER
        defaultAffiliatesShouldBeFound("branchNumber.notEquals=" + UPDATED_BRANCH_NUMBER);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchNumberIsInShouldWork() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchNumber in DEFAULT_BRANCH_NUMBER or UPDATED_BRANCH_NUMBER
        defaultAffiliatesShouldBeFound("branchNumber.in=" + DEFAULT_BRANCH_NUMBER + "," + UPDATED_BRANCH_NUMBER);

        // Get all the affiliatesList where branchNumber equals to UPDATED_BRANCH_NUMBER
        defaultAffiliatesShouldNotBeFound("branchNumber.in=" + UPDATED_BRANCH_NUMBER);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchNumber is not null
        defaultAffiliatesShouldBeFound("branchNumber.specified=true");

        // Get all the affiliatesList where branchNumber is null
        defaultAffiliatesShouldNotBeFound("branchNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchNumberContainsSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchNumber contains DEFAULT_BRANCH_NUMBER
        defaultAffiliatesShouldBeFound("branchNumber.contains=" + DEFAULT_BRANCH_NUMBER);

        // Get all the affiliatesList where branchNumber contains UPDATED_BRANCH_NUMBER
        defaultAffiliatesShouldNotBeFound("branchNumber.contains=" + UPDATED_BRANCH_NUMBER);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchNumberNotContainsSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchNumber does not contain DEFAULT_BRANCH_NUMBER
        defaultAffiliatesShouldNotBeFound("branchNumber.doesNotContain=" + DEFAULT_BRANCH_NUMBER);

        // Get all the affiliatesList where branchNumber does not contain UPDATED_BRANCH_NUMBER
        defaultAffiliatesShouldBeFound("branchNumber.doesNotContain=" + UPDATED_BRANCH_NUMBER);
    }

    @Test
    @Transactional
    void getAllAffiliatesByUseSameCompanyAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where useSameCompanyAddress equals to DEFAULT_USE_SAME_COMPANY_ADDRESS
        defaultAffiliatesShouldBeFound("useSameCompanyAddress.equals=" + DEFAULT_USE_SAME_COMPANY_ADDRESS);

        // Get all the affiliatesList where useSameCompanyAddress equals to UPDATED_USE_SAME_COMPANY_ADDRESS
        defaultAffiliatesShouldNotBeFound("useSameCompanyAddress.equals=" + UPDATED_USE_SAME_COMPANY_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAffiliatesByUseSameCompanyAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where useSameCompanyAddress not equals to DEFAULT_USE_SAME_COMPANY_ADDRESS
        defaultAffiliatesShouldNotBeFound("useSameCompanyAddress.notEquals=" + DEFAULT_USE_SAME_COMPANY_ADDRESS);

        // Get all the affiliatesList where useSameCompanyAddress not equals to UPDATED_USE_SAME_COMPANY_ADDRESS
        defaultAffiliatesShouldBeFound("useSameCompanyAddress.notEquals=" + UPDATED_USE_SAME_COMPANY_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAffiliatesByUseSameCompanyAddressIsInShouldWork() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where useSameCompanyAddress in DEFAULT_USE_SAME_COMPANY_ADDRESS or UPDATED_USE_SAME_COMPANY_ADDRESS
        defaultAffiliatesShouldBeFound(
            "useSameCompanyAddress.in=" + DEFAULT_USE_SAME_COMPANY_ADDRESS + "," + UPDATED_USE_SAME_COMPANY_ADDRESS
        );

        // Get all the affiliatesList where useSameCompanyAddress equals to UPDATED_USE_SAME_COMPANY_ADDRESS
        defaultAffiliatesShouldNotBeFound("useSameCompanyAddress.in=" + UPDATED_USE_SAME_COMPANY_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAffiliatesByUseSameCompanyAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where useSameCompanyAddress is not null
        defaultAffiliatesShouldBeFound("useSameCompanyAddress.specified=true");

        // Get all the affiliatesList where useSameCompanyAddress is null
        defaultAffiliatesShouldNotBeFound("useSameCompanyAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchPostalCode equals to DEFAULT_BRANCH_POSTAL_CODE
        defaultAffiliatesShouldBeFound("branchPostalCode.equals=" + DEFAULT_BRANCH_POSTAL_CODE);

        // Get all the affiliatesList where branchPostalCode equals to UPDATED_BRANCH_POSTAL_CODE
        defaultAffiliatesShouldNotBeFound("branchPostalCode.equals=" + UPDATED_BRANCH_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchPostalCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchPostalCode not equals to DEFAULT_BRANCH_POSTAL_CODE
        defaultAffiliatesShouldNotBeFound("branchPostalCode.notEquals=" + DEFAULT_BRANCH_POSTAL_CODE);

        // Get all the affiliatesList where branchPostalCode not equals to UPDATED_BRANCH_POSTAL_CODE
        defaultAffiliatesShouldBeFound("branchPostalCode.notEquals=" + UPDATED_BRANCH_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchPostalCode in DEFAULT_BRANCH_POSTAL_CODE or UPDATED_BRANCH_POSTAL_CODE
        defaultAffiliatesShouldBeFound("branchPostalCode.in=" + DEFAULT_BRANCH_POSTAL_CODE + "," + UPDATED_BRANCH_POSTAL_CODE);

        // Get all the affiliatesList where branchPostalCode equals to UPDATED_BRANCH_POSTAL_CODE
        defaultAffiliatesShouldNotBeFound("branchPostalCode.in=" + UPDATED_BRANCH_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchPostalCode is not null
        defaultAffiliatesShouldBeFound("branchPostalCode.specified=true");

        // Get all the affiliatesList where branchPostalCode is null
        defaultAffiliatesShouldNotBeFound("branchPostalCode.specified=false");
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchPostalCodeContainsSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchPostalCode contains DEFAULT_BRANCH_POSTAL_CODE
        defaultAffiliatesShouldBeFound("branchPostalCode.contains=" + DEFAULT_BRANCH_POSTAL_CODE);

        // Get all the affiliatesList where branchPostalCode contains UPDATED_BRANCH_POSTAL_CODE
        defaultAffiliatesShouldNotBeFound("branchPostalCode.contains=" + UPDATED_BRANCH_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchPostalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchPostalCode does not contain DEFAULT_BRANCH_POSTAL_CODE
        defaultAffiliatesShouldNotBeFound("branchPostalCode.doesNotContain=" + DEFAULT_BRANCH_POSTAL_CODE);

        // Get all the affiliatesList where branchPostalCode does not contain UPDATED_BRANCH_POSTAL_CODE
        defaultAffiliatesShouldBeFound("branchPostalCode.doesNotContain=" + UPDATED_BRANCH_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddress equals to DEFAULT_BRANCH_ADDRESS
        defaultAffiliatesShouldBeFound("branchAddress.equals=" + DEFAULT_BRANCH_ADDRESS);

        // Get all the affiliatesList where branchAddress equals to UPDATED_BRANCH_ADDRESS
        defaultAffiliatesShouldNotBeFound("branchAddress.equals=" + UPDATED_BRANCH_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddress not equals to DEFAULT_BRANCH_ADDRESS
        defaultAffiliatesShouldNotBeFound("branchAddress.notEquals=" + DEFAULT_BRANCH_ADDRESS);

        // Get all the affiliatesList where branchAddress not equals to UPDATED_BRANCH_ADDRESS
        defaultAffiliatesShouldBeFound("branchAddress.notEquals=" + UPDATED_BRANCH_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressIsInShouldWork() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddress in DEFAULT_BRANCH_ADDRESS or UPDATED_BRANCH_ADDRESS
        defaultAffiliatesShouldBeFound("branchAddress.in=" + DEFAULT_BRANCH_ADDRESS + "," + UPDATED_BRANCH_ADDRESS);

        // Get all the affiliatesList where branchAddress equals to UPDATED_BRANCH_ADDRESS
        defaultAffiliatesShouldNotBeFound("branchAddress.in=" + UPDATED_BRANCH_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddress is not null
        defaultAffiliatesShouldBeFound("branchAddress.specified=true");

        // Get all the affiliatesList where branchAddress is null
        defaultAffiliatesShouldNotBeFound("branchAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressContainsSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddress contains DEFAULT_BRANCH_ADDRESS
        defaultAffiliatesShouldBeFound("branchAddress.contains=" + DEFAULT_BRANCH_ADDRESS);

        // Get all the affiliatesList where branchAddress contains UPDATED_BRANCH_ADDRESS
        defaultAffiliatesShouldNotBeFound("branchAddress.contains=" + UPDATED_BRANCH_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressNotContainsSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddress does not contain DEFAULT_BRANCH_ADDRESS
        defaultAffiliatesShouldNotBeFound("branchAddress.doesNotContain=" + DEFAULT_BRANCH_ADDRESS);

        // Get all the affiliatesList where branchAddress does not contain UPDATED_BRANCH_ADDRESS
        defaultAffiliatesShouldBeFound("branchAddress.doesNotContain=" + UPDATED_BRANCH_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressComplementIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddressComplement equals to DEFAULT_BRANCH_ADDRESS_COMPLEMENT
        defaultAffiliatesShouldBeFound("branchAddressComplement.equals=" + DEFAULT_BRANCH_ADDRESS_COMPLEMENT);

        // Get all the affiliatesList where branchAddressComplement equals to UPDATED_BRANCH_ADDRESS_COMPLEMENT
        defaultAffiliatesShouldNotBeFound("branchAddressComplement.equals=" + UPDATED_BRANCH_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressComplementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddressComplement not equals to DEFAULT_BRANCH_ADDRESS_COMPLEMENT
        defaultAffiliatesShouldNotBeFound("branchAddressComplement.notEquals=" + DEFAULT_BRANCH_ADDRESS_COMPLEMENT);

        // Get all the affiliatesList where branchAddressComplement not equals to UPDATED_BRANCH_ADDRESS_COMPLEMENT
        defaultAffiliatesShouldBeFound("branchAddressComplement.notEquals=" + UPDATED_BRANCH_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressComplementIsInShouldWork() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddressComplement in DEFAULT_BRANCH_ADDRESS_COMPLEMENT or UPDATED_BRANCH_ADDRESS_COMPLEMENT
        defaultAffiliatesShouldBeFound(
            "branchAddressComplement.in=" + DEFAULT_BRANCH_ADDRESS_COMPLEMENT + "," + UPDATED_BRANCH_ADDRESS_COMPLEMENT
        );

        // Get all the affiliatesList where branchAddressComplement equals to UPDATED_BRANCH_ADDRESS_COMPLEMENT
        defaultAffiliatesShouldNotBeFound("branchAddressComplement.in=" + UPDATED_BRANCH_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressComplementIsNullOrNotNull() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddressComplement is not null
        defaultAffiliatesShouldBeFound("branchAddressComplement.specified=true");

        // Get all the affiliatesList where branchAddressComplement is null
        defaultAffiliatesShouldNotBeFound("branchAddressComplement.specified=false");
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressComplementContainsSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddressComplement contains DEFAULT_BRANCH_ADDRESS_COMPLEMENT
        defaultAffiliatesShouldBeFound("branchAddressComplement.contains=" + DEFAULT_BRANCH_ADDRESS_COMPLEMENT);

        // Get all the affiliatesList where branchAddressComplement contains UPDATED_BRANCH_ADDRESS_COMPLEMENT
        defaultAffiliatesShouldNotBeFound("branchAddressComplement.contains=" + UPDATED_BRANCH_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressComplementNotContainsSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddressComplement does not contain DEFAULT_BRANCH_ADDRESS_COMPLEMENT
        defaultAffiliatesShouldNotBeFound("branchAddressComplement.doesNotContain=" + DEFAULT_BRANCH_ADDRESS_COMPLEMENT);

        // Get all the affiliatesList where branchAddressComplement does not contain UPDATED_BRANCH_ADDRESS_COMPLEMENT
        defaultAffiliatesShouldBeFound("branchAddressComplement.doesNotContain=" + UPDATED_BRANCH_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddressNumber equals to DEFAULT_BRANCH_ADDRESS_NUMBER
        defaultAffiliatesShouldBeFound("branchAddressNumber.equals=" + DEFAULT_BRANCH_ADDRESS_NUMBER);

        // Get all the affiliatesList where branchAddressNumber equals to UPDATED_BRANCH_ADDRESS_NUMBER
        defaultAffiliatesShouldNotBeFound("branchAddressNumber.equals=" + UPDATED_BRANCH_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddressNumber not equals to DEFAULT_BRANCH_ADDRESS_NUMBER
        defaultAffiliatesShouldNotBeFound("branchAddressNumber.notEquals=" + DEFAULT_BRANCH_ADDRESS_NUMBER);

        // Get all the affiliatesList where branchAddressNumber not equals to UPDATED_BRANCH_ADDRESS_NUMBER
        defaultAffiliatesShouldBeFound("branchAddressNumber.notEquals=" + UPDATED_BRANCH_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressNumberIsInShouldWork() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddressNumber in DEFAULT_BRANCH_ADDRESS_NUMBER or UPDATED_BRANCH_ADDRESS_NUMBER
        defaultAffiliatesShouldBeFound("branchAddressNumber.in=" + DEFAULT_BRANCH_ADDRESS_NUMBER + "," + UPDATED_BRANCH_ADDRESS_NUMBER);

        // Get all the affiliatesList where branchAddressNumber equals to UPDATED_BRANCH_ADDRESS_NUMBER
        defaultAffiliatesShouldNotBeFound("branchAddressNumber.in=" + UPDATED_BRANCH_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddressNumber is not null
        defaultAffiliatesShouldBeFound("branchAddressNumber.specified=true");

        // Get all the affiliatesList where branchAddressNumber is null
        defaultAffiliatesShouldNotBeFound("branchAddressNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddressNumber is greater than or equal to DEFAULT_BRANCH_ADDRESS_NUMBER
        defaultAffiliatesShouldBeFound("branchAddressNumber.greaterThanOrEqual=" + DEFAULT_BRANCH_ADDRESS_NUMBER);

        // Get all the affiliatesList where branchAddressNumber is greater than or equal to UPDATED_BRANCH_ADDRESS_NUMBER
        defaultAffiliatesShouldNotBeFound("branchAddressNumber.greaterThanOrEqual=" + UPDATED_BRANCH_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddressNumber is less than or equal to DEFAULT_BRANCH_ADDRESS_NUMBER
        defaultAffiliatesShouldBeFound("branchAddressNumber.lessThanOrEqual=" + DEFAULT_BRANCH_ADDRESS_NUMBER);

        // Get all the affiliatesList where branchAddressNumber is less than or equal to SMALLER_BRANCH_ADDRESS_NUMBER
        defaultAffiliatesShouldNotBeFound("branchAddressNumber.lessThanOrEqual=" + SMALLER_BRANCH_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddressNumber is less than DEFAULT_BRANCH_ADDRESS_NUMBER
        defaultAffiliatesShouldNotBeFound("branchAddressNumber.lessThan=" + DEFAULT_BRANCH_ADDRESS_NUMBER);

        // Get all the affiliatesList where branchAddressNumber is less than UPDATED_BRANCH_ADDRESS_NUMBER
        defaultAffiliatesShouldBeFound("branchAddressNumber.lessThan=" + UPDATED_BRANCH_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddressNumber is greater than DEFAULT_BRANCH_ADDRESS_NUMBER
        defaultAffiliatesShouldNotBeFound("branchAddressNumber.greaterThan=" + DEFAULT_BRANCH_ADDRESS_NUMBER);

        // Get all the affiliatesList where branchAddressNumber is greater than SMALLER_BRANCH_ADDRESS_NUMBER
        defaultAffiliatesShouldBeFound("branchAddressNumber.greaterThan=" + SMALLER_BRANCH_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressNeighborhoodIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddressNeighborhood equals to DEFAULT_BRANCH_ADDRESS_NEIGHBORHOOD
        defaultAffiliatesShouldBeFound("branchAddressNeighborhood.equals=" + DEFAULT_BRANCH_ADDRESS_NEIGHBORHOOD);

        // Get all the affiliatesList where branchAddressNeighborhood equals to UPDATED_BRANCH_ADDRESS_NEIGHBORHOOD
        defaultAffiliatesShouldNotBeFound("branchAddressNeighborhood.equals=" + UPDATED_BRANCH_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressNeighborhoodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddressNeighborhood not equals to DEFAULT_BRANCH_ADDRESS_NEIGHBORHOOD
        defaultAffiliatesShouldNotBeFound("branchAddressNeighborhood.notEquals=" + DEFAULT_BRANCH_ADDRESS_NEIGHBORHOOD);

        // Get all the affiliatesList where branchAddressNeighborhood not equals to UPDATED_BRANCH_ADDRESS_NEIGHBORHOOD
        defaultAffiliatesShouldBeFound("branchAddressNeighborhood.notEquals=" + UPDATED_BRANCH_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressNeighborhoodIsInShouldWork() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddressNeighborhood in DEFAULT_BRANCH_ADDRESS_NEIGHBORHOOD or UPDATED_BRANCH_ADDRESS_NEIGHBORHOOD
        defaultAffiliatesShouldBeFound(
            "branchAddressNeighborhood.in=" + DEFAULT_BRANCH_ADDRESS_NEIGHBORHOOD + "," + UPDATED_BRANCH_ADDRESS_NEIGHBORHOOD
        );

        // Get all the affiliatesList where branchAddressNeighborhood equals to UPDATED_BRANCH_ADDRESS_NEIGHBORHOOD
        defaultAffiliatesShouldNotBeFound("branchAddressNeighborhood.in=" + UPDATED_BRANCH_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressNeighborhoodIsNullOrNotNull() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddressNeighborhood is not null
        defaultAffiliatesShouldBeFound("branchAddressNeighborhood.specified=true");

        // Get all the affiliatesList where branchAddressNeighborhood is null
        defaultAffiliatesShouldNotBeFound("branchAddressNeighborhood.specified=false");
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressNeighborhoodContainsSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddressNeighborhood contains DEFAULT_BRANCH_ADDRESS_NEIGHBORHOOD
        defaultAffiliatesShouldBeFound("branchAddressNeighborhood.contains=" + DEFAULT_BRANCH_ADDRESS_NEIGHBORHOOD);

        // Get all the affiliatesList where branchAddressNeighborhood contains UPDATED_BRANCH_ADDRESS_NEIGHBORHOOD
        defaultAffiliatesShouldNotBeFound("branchAddressNeighborhood.contains=" + UPDATED_BRANCH_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchAddressNeighborhoodNotContainsSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchAddressNeighborhood does not contain DEFAULT_BRANCH_ADDRESS_NEIGHBORHOOD
        defaultAffiliatesShouldNotBeFound("branchAddressNeighborhood.doesNotContain=" + DEFAULT_BRANCH_ADDRESS_NEIGHBORHOOD);

        // Get all the affiliatesList where branchAddressNeighborhood does not contain UPDATED_BRANCH_ADDRESS_NEIGHBORHOOD
        defaultAffiliatesShouldBeFound("branchAddressNeighborhood.doesNotContain=" + UPDATED_BRANCH_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchTelephone equals to DEFAULT_BRANCH_TELEPHONE
        defaultAffiliatesShouldBeFound("branchTelephone.equals=" + DEFAULT_BRANCH_TELEPHONE);

        // Get all the affiliatesList where branchTelephone equals to UPDATED_BRANCH_TELEPHONE
        defaultAffiliatesShouldNotBeFound("branchTelephone.equals=" + UPDATED_BRANCH_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchTelephone not equals to DEFAULT_BRANCH_TELEPHONE
        defaultAffiliatesShouldNotBeFound("branchTelephone.notEquals=" + DEFAULT_BRANCH_TELEPHONE);

        // Get all the affiliatesList where branchTelephone not equals to UPDATED_BRANCH_TELEPHONE
        defaultAffiliatesShouldBeFound("branchTelephone.notEquals=" + UPDATED_BRANCH_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchTelephone in DEFAULT_BRANCH_TELEPHONE or UPDATED_BRANCH_TELEPHONE
        defaultAffiliatesShouldBeFound("branchTelephone.in=" + DEFAULT_BRANCH_TELEPHONE + "," + UPDATED_BRANCH_TELEPHONE);

        // Get all the affiliatesList where branchTelephone equals to UPDATED_BRANCH_TELEPHONE
        defaultAffiliatesShouldNotBeFound("branchTelephone.in=" + UPDATED_BRANCH_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchTelephone is not null
        defaultAffiliatesShouldBeFound("branchTelephone.specified=true");

        // Get all the affiliatesList where branchTelephone is null
        defaultAffiliatesShouldNotBeFound("branchTelephone.specified=false");
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchTelephoneContainsSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchTelephone contains DEFAULT_BRANCH_TELEPHONE
        defaultAffiliatesShouldBeFound("branchTelephone.contains=" + DEFAULT_BRANCH_TELEPHONE);

        // Get all the affiliatesList where branchTelephone contains UPDATED_BRANCH_TELEPHONE
        defaultAffiliatesShouldNotBeFound("branchTelephone.contains=" + UPDATED_BRANCH_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchTelephone does not contain DEFAULT_BRANCH_TELEPHONE
        defaultAffiliatesShouldNotBeFound("branchTelephone.doesNotContain=" + DEFAULT_BRANCH_TELEPHONE);

        // Get all the affiliatesList where branchTelephone does not contain UPDATED_BRANCH_TELEPHONE
        defaultAffiliatesShouldBeFound("branchTelephone.doesNotContain=" + UPDATED_BRANCH_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchEmail equals to DEFAULT_BRANCH_EMAIL
        defaultAffiliatesShouldBeFound("branchEmail.equals=" + DEFAULT_BRANCH_EMAIL);

        // Get all the affiliatesList where branchEmail equals to UPDATED_BRANCH_EMAIL
        defaultAffiliatesShouldNotBeFound("branchEmail.equals=" + UPDATED_BRANCH_EMAIL);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchEmail not equals to DEFAULT_BRANCH_EMAIL
        defaultAffiliatesShouldNotBeFound("branchEmail.notEquals=" + DEFAULT_BRANCH_EMAIL);

        // Get all the affiliatesList where branchEmail not equals to UPDATED_BRANCH_EMAIL
        defaultAffiliatesShouldBeFound("branchEmail.notEquals=" + UPDATED_BRANCH_EMAIL);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchEmailIsInShouldWork() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchEmail in DEFAULT_BRANCH_EMAIL or UPDATED_BRANCH_EMAIL
        defaultAffiliatesShouldBeFound("branchEmail.in=" + DEFAULT_BRANCH_EMAIL + "," + UPDATED_BRANCH_EMAIL);

        // Get all the affiliatesList where branchEmail equals to UPDATED_BRANCH_EMAIL
        defaultAffiliatesShouldNotBeFound("branchEmail.in=" + UPDATED_BRANCH_EMAIL);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchEmail is not null
        defaultAffiliatesShouldBeFound("branchEmail.specified=true");

        // Get all the affiliatesList where branchEmail is null
        defaultAffiliatesShouldNotBeFound("branchEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchEmailContainsSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchEmail contains DEFAULT_BRANCH_EMAIL
        defaultAffiliatesShouldBeFound("branchEmail.contains=" + DEFAULT_BRANCH_EMAIL);

        // Get all the affiliatesList where branchEmail contains UPDATED_BRANCH_EMAIL
        defaultAffiliatesShouldNotBeFound("branchEmail.contains=" + UPDATED_BRANCH_EMAIL);
    }

    @Test
    @Transactional
    void getAllAffiliatesByBranchEmailNotContainsSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where branchEmail does not contain DEFAULT_BRANCH_EMAIL
        defaultAffiliatesShouldNotBeFound("branchEmail.doesNotContain=" + DEFAULT_BRANCH_EMAIL);

        // Get all the affiliatesList where branchEmail does not contain UPDATED_BRANCH_EMAIL
        defaultAffiliatesShouldBeFound("branchEmail.doesNotContain=" + UPDATED_BRANCH_EMAIL);
    }

    @Test
    @Transactional
    void getAllAffiliatesByResponsibleContactIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where responsibleContact equals to DEFAULT_RESPONSIBLE_CONTACT
        defaultAffiliatesShouldBeFound("responsibleContact.equals=" + DEFAULT_RESPONSIBLE_CONTACT);

        // Get all the affiliatesList where responsibleContact equals to UPDATED_RESPONSIBLE_CONTACT
        defaultAffiliatesShouldNotBeFound("responsibleContact.equals=" + UPDATED_RESPONSIBLE_CONTACT);
    }

    @Test
    @Transactional
    void getAllAffiliatesByResponsibleContactIsNotEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where responsibleContact not equals to DEFAULT_RESPONSIBLE_CONTACT
        defaultAffiliatesShouldNotBeFound("responsibleContact.notEquals=" + DEFAULT_RESPONSIBLE_CONTACT);

        // Get all the affiliatesList where responsibleContact not equals to UPDATED_RESPONSIBLE_CONTACT
        defaultAffiliatesShouldBeFound("responsibleContact.notEquals=" + UPDATED_RESPONSIBLE_CONTACT);
    }

    @Test
    @Transactional
    void getAllAffiliatesByResponsibleContactIsInShouldWork() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where responsibleContact in DEFAULT_RESPONSIBLE_CONTACT or UPDATED_RESPONSIBLE_CONTACT
        defaultAffiliatesShouldBeFound("responsibleContact.in=" + DEFAULT_RESPONSIBLE_CONTACT + "," + UPDATED_RESPONSIBLE_CONTACT);

        // Get all the affiliatesList where responsibleContact equals to UPDATED_RESPONSIBLE_CONTACT
        defaultAffiliatesShouldNotBeFound("responsibleContact.in=" + UPDATED_RESPONSIBLE_CONTACT);
    }

    @Test
    @Transactional
    void getAllAffiliatesByResponsibleContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where responsibleContact is not null
        defaultAffiliatesShouldBeFound("responsibleContact.specified=true");

        // Get all the affiliatesList where responsibleContact is null
        defaultAffiliatesShouldNotBeFound("responsibleContact.specified=false");
    }

    @Test
    @Transactional
    void getAllAffiliatesByResponsibleContactContainsSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where responsibleContact contains DEFAULT_RESPONSIBLE_CONTACT
        defaultAffiliatesShouldBeFound("responsibleContact.contains=" + DEFAULT_RESPONSIBLE_CONTACT);

        // Get all the affiliatesList where responsibleContact contains UPDATED_RESPONSIBLE_CONTACT
        defaultAffiliatesShouldNotBeFound("responsibleContact.contains=" + UPDATED_RESPONSIBLE_CONTACT);
    }

    @Test
    @Transactional
    void getAllAffiliatesByResponsibleContactNotContainsSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        // Get all the affiliatesList where responsibleContact does not contain DEFAULT_RESPONSIBLE_CONTACT
        defaultAffiliatesShouldNotBeFound("responsibleContact.doesNotContain=" + DEFAULT_RESPONSIBLE_CONTACT);

        // Get all the affiliatesList where responsibleContact does not contain UPDATED_RESPONSIBLE_CONTACT
        defaultAffiliatesShouldBeFound("responsibleContact.doesNotContain=" + UPDATED_RESPONSIBLE_CONTACT);
    }

    @Test
    @Transactional
    void getAllAffiliatesByInsurancesIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);
        Insurances insurances = InsurancesResourceIT.createEntity(em);
        em.persist(insurances);
        em.flush();
        affiliates.addInsurances(insurances);
        affiliatesRepository.saveAndFlush(affiliates);
        Long insurancesId = insurances.getId();

        // Get all the affiliatesList where insurances equals to insurancesId
        defaultAffiliatesShouldBeFound("insurancesId.equals=" + insurancesId);

        // Get all the affiliatesList where insurances equals to (insurancesId + 1)
        defaultAffiliatesShouldNotBeFound("insurancesId.equals=" + (insurancesId + 1));
    }

    @Test
    @Transactional
    void getAllAffiliatesByPositionsIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);
        Positions positions = PositionsResourceIT.createEntity(em);
        em.persist(positions);
        em.flush();
        affiliates.addPositions(positions);
        affiliatesRepository.saveAndFlush(affiliates);
        Long positionsId = positions.getId();

        // Get all the affiliatesList where positions equals to positionsId
        defaultAffiliatesShouldBeFound("positionsId.equals=" + positionsId);

        // Get all the affiliatesList where positions equals to (positionsId + 1)
        defaultAffiliatesShouldNotBeFound("positionsId.equals=" + (positionsId + 1));
    }

    @Test
    @Transactional
    void getAllAffiliatesByCostCenterIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);
        CostCenter costCenter = CostCenterResourceIT.createEntity(em);
        em.persist(costCenter);
        em.flush();
        affiliates.addCostCenter(costCenter);
        affiliatesRepository.saveAndFlush(affiliates);
        Long costCenterId = costCenter.getId();

        // Get all the affiliatesList where costCenter equals to costCenterId
        defaultAffiliatesShouldBeFound("costCenterId.equals=" + costCenterId);

        // Get all the affiliatesList where costCenter equals to (costCenterId + 1)
        defaultAffiliatesShouldNotBeFound("costCenterId.equals=" + (costCenterId + 1));
    }

    @Test
    @Transactional
    void getAllAffiliatesByAdministrativeFeesRangesIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);
        AdministrativeFeesRanges administrativeFeesRanges = AdministrativeFeesRangesResourceIT.createEntity(em);
        em.persist(administrativeFeesRanges);
        em.flush();
        affiliates.addAdministrativeFeesRanges(administrativeFeesRanges);
        affiliatesRepository.saveAndFlush(affiliates);
        Long administrativeFeesRangesId = administrativeFeesRanges.getId();

        // Get all the affiliatesList where administrativeFeesRanges equals to administrativeFeesRangesId
        defaultAffiliatesShouldBeFound("administrativeFeesRangesId.equals=" + administrativeFeesRangesId);

        // Get all the affiliatesList where administrativeFeesRanges equals to (administrativeFeesRangesId + 1)
        defaultAffiliatesShouldNotBeFound("administrativeFeesRangesId.equals=" + (administrativeFeesRangesId + 1));
    }

    @Test
    @Transactional
    void getAllAffiliatesByCustomersGroupsIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);
        CustomersGroups customersGroups = CustomersGroupsResourceIT.createEntity(em);
        em.persist(customersGroups);
        em.flush();
        affiliates.addCustomersGroups(customersGroups);
        affiliatesRepository.saveAndFlush(affiliates);
        Long customersGroupsId = customersGroups.getId();

        // Get all the affiliatesList where customersGroups equals to customersGroupsId
        defaultAffiliatesShouldBeFound("customersGroupsId.equals=" + customersGroupsId);

        // Get all the affiliatesList where customersGroups equals to (customersGroupsId + 1)
        defaultAffiliatesShouldNotBeFound("customersGroupsId.equals=" + (customersGroupsId + 1));
    }

    @Test
    @Transactional
    void getAllAffiliatesByFeesIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);
        Fees fees = FeesResourceIT.createEntity(em);
        em.persist(fees);
        em.flush();
        affiliates.addFees(fees);
        affiliatesRepository.saveAndFlush(affiliates);
        Long feesId = fees.getId();

        // Get all the affiliatesList where fees equals to feesId
        defaultAffiliatesShouldBeFound("feesId.equals=" + feesId);

        // Get all the affiliatesList where fees equals to (feesId + 1)
        defaultAffiliatesShouldNotBeFound("feesId.equals=" + (feesId + 1));
    }

    @Test
    @Transactional
    void getAllAffiliatesByCustomersIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);
        Customers customers = CustomersResourceIT.createEntity(em);
        em.persist(customers);
        em.flush();
        affiliates.addCustomers(customers);
        affiliatesRepository.saveAndFlush(affiliates);
        Long customersId = customers.getId();

        // Get all the affiliatesList where customers equals to customersId
        defaultAffiliatesShouldBeFound("customersId.equals=" + customersId);

        // Get all the affiliatesList where customers equals to (customersId + 1)
        defaultAffiliatesShouldNotBeFound("customersId.equals=" + (customersId + 1));
    }

    @Test
    @Transactional
    void getAllAffiliatesByStatusAttachmentsIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);
        StatusAttachments statusAttachments = StatusAttachmentsResourceIT.createEntity(em);
        em.persist(statusAttachments);
        em.flush();
        affiliates.addStatusAttachments(statusAttachments);
        affiliatesRepository.saveAndFlush(affiliates);
        Long statusAttachmentsId = statusAttachments.getId();

        // Get all the affiliatesList where statusAttachments equals to statusAttachmentsId
        defaultAffiliatesShouldBeFound("statusAttachmentsId.equals=" + statusAttachmentsId);

        // Get all the affiliatesList where statusAttachments equals to (statusAttachmentsId + 1)
        defaultAffiliatesShouldNotBeFound("statusAttachmentsId.equals=" + (statusAttachmentsId + 1));
    }

    @Test
    @Transactional
    void getAllAffiliatesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);
        Status status = StatusResourceIT.createEntity(em);
        em.persist(status);
        em.flush();
        affiliates.addStatus(status);
        affiliatesRepository.saveAndFlush(affiliates);
        Long statusId = status.getId();

        // Get all the affiliatesList where status equals to statusId
        defaultAffiliatesShouldBeFound("statusId.equals=" + statusId);

        // Get all the affiliatesList where status equals to (statusId + 1)
        defaultAffiliatesShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }

    @Test
    @Transactional
    void getAllAffiliatesByParkingIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);
        Parking parking = ParkingResourceIT.createEntity(em);
        em.persist(parking);
        em.flush();
        affiliates.addParking(parking);
        affiliatesRepository.saveAndFlush(affiliates);
        Long parkingId = parking.getId();

        // Get all the affiliatesList where parking equals to parkingId
        defaultAffiliatesShouldBeFound("parkingId.equals=" + parkingId);

        // Get all the affiliatesList where parking equals to (parkingId + 1)
        defaultAffiliatesShouldNotBeFound("parkingId.equals=" + (parkingId + 1));
    }

    @Test
    @Transactional
    void getAllAffiliatesBySuppliersIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);
        Suppliers suppliers = SuppliersResourceIT.createEntity(em);
        em.persist(suppliers);
        em.flush();
        affiliates.addSuppliers(suppliers);
        affiliatesRepository.saveAndFlush(affiliates);
        Long suppliersId = suppliers.getId();

        // Get all the affiliatesList where suppliers equals to suppliersId
        defaultAffiliatesShouldBeFound("suppliersId.equals=" + suppliersId);

        // Get all the affiliatesList where suppliers equals to (suppliersId + 1)
        defaultAffiliatesShouldNotBeFound("suppliersId.equals=" + (suppliersId + 1));
    }

    @Test
    @Transactional
    void getAllAffiliatesByEmployeesIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);
        Employees employees = EmployeesResourceIT.createEntity(em);
        em.persist(employees);
        em.flush();
        affiliates.addEmployees(employees);
        affiliatesRepository.saveAndFlush(affiliates);
        Long employeesId = employees.getId();

        // Get all the affiliatesList where employees equals to employeesId
        defaultAffiliatesShouldBeFound("employeesId.equals=" + employeesId);

        // Get all the affiliatesList where employees equals to (employeesId + 1)
        defaultAffiliatesShouldNotBeFound("employeesId.equals=" + (employeesId + 1));
    }

    @Test
    @Transactional
    void getAllAffiliatesByVehicleControlsIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);
        VehicleControls vehicleControls = VehicleControlsResourceIT.createEntity(em);
        em.persist(vehicleControls);
        em.flush();
        affiliates.addVehicleControls(vehicleControls);
        affiliatesRepository.saveAndFlush(affiliates);
        Long vehicleControlsId = vehicleControls.getId();

        // Get all the affiliatesList where vehicleControls equals to vehicleControlsId
        defaultAffiliatesShouldBeFound("vehicleControlsId.equals=" + vehicleControlsId);

        // Get all the affiliatesList where vehicleControls equals to (vehicleControlsId + 1)
        defaultAffiliatesShouldNotBeFound("vehicleControlsId.equals=" + (vehicleControlsId + 1));
    }

    @Test
    @Transactional
    void getAllAffiliatesByHousingIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);
        Housing housing = HousingResourceIT.createEntity(em);
        em.persist(housing);
        em.flush();
        affiliates.addHousing(housing);
        affiliatesRepository.saveAndFlush(affiliates);
        Long housingId = housing.getId();

        // Get all the affiliatesList where housing equals to housingId
        defaultAffiliatesShouldBeFound("housingId.equals=" + housingId);

        // Get all the affiliatesList where housing equals to (housingId + 1)
        defaultAffiliatesShouldNotBeFound("housingId.equals=" + (housingId + 1));
    }

    @Test
    @Transactional
    void getAllAffiliatesByStateProvincesIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);
        StateProvinces stateProvinces = StateProvincesResourceIT.createEntity(em);
        em.persist(stateProvinces);
        em.flush();
        affiliates.setStateProvinces(stateProvinces);
        affiliatesRepository.saveAndFlush(affiliates);
        Long stateProvincesId = stateProvinces.getId();

        // Get all the affiliatesList where stateProvinces equals to stateProvincesId
        defaultAffiliatesShouldBeFound("stateProvincesId.equals=" + stateProvincesId);

        // Get all the affiliatesList where stateProvinces equals to (stateProvincesId + 1)
        defaultAffiliatesShouldNotBeFound("stateProvincesId.equals=" + (stateProvincesId + 1));
    }

    @Test
    @Transactional
    void getAllAffiliatesByCitiesIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);
        Cities cities = CitiesResourceIT.createEntity(em);
        em.persist(cities);
        em.flush();
        affiliates.setCities(cities);
        affiliatesRepository.saveAndFlush(affiliates);
        Long citiesId = cities.getId();

        // Get all the affiliatesList where cities equals to citiesId
        defaultAffiliatesShouldBeFound("citiesId.equals=" + citiesId);

        // Get all the affiliatesList where cities equals to (citiesId + 1)
        defaultAffiliatesShouldNotBeFound("citiesId.equals=" + (citiesId + 1));
    }

    @Test
    @Transactional
    void getAllAffiliatesByCompaniesIsEqualToSomething() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);
        Companies companies = CompaniesResourceIT.createEntity(em);
        em.persist(companies);
        em.flush();
        affiliates.setCompanies(companies);
        affiliatesRepository.saveAndFlush(affiliates);
        Long companiesId = companies.getId();

        // Get all the affiliatesList where companies equals to companiesId
        defaultAffiliatesShouldBeFound("companiesId.equals=" + companiesId);

        // Get all the affiliatesList where companies equals to (companiesId + 1)
        defaultAffiliatesShouldNotBeFound("companiesId.equals=" + (companiesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAffiliatesShouldBeFound(String filter) throws Exception {
        restAffiliatesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(affiliates.getId().intValue())))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].branchNumber").value(hasItem(DEFAULT_BRANCH_NUMBER)))
            .andExpect(jsonPath("$.[*].useSameCompanyAddress").value(hasItem(DEFAULT_USE_SAME_COMPANY_ADDRESS.booleanValue())))
            .andExpect(jsonPath("$.[*].branchPostalCode").value(hasItem(DEFAULT_BRANCH_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].branchAddress").value(hasItem(DEFAULT_BRANCH_ADDRESS)))
            .andExpect(jsonPath("$.[*].branchAddressComplement").value(hasItem(DEFAULT_BRANCH_ADDRESS_COMPLEMENT)))
            .andExpect(jsonPath("$.[*].branchAddressNumber").value(hasItem(DEFAULT_BRANCH_ADDRESS_NUMBER)))
            .andExpect(jsonPath("$.[*].branchAddressNeighborhood").value(hasItem(DEFAULT_BRANCH_ADDRESS_NEIGHBORHOOD)))
            .andExpect(jsonPath("$.[*].branchTelephone").value(hasItem(DEFAULT_BRANCH_TELEPHONE)))
            .andExpect(jsonPath("$.[*].branchEmail").value(hasItem(DEFAULT_BRANCH_EMAIL)))
            .andExpect(jsonPath("$.[*].responsibleContact").value(hasItem(DEFAULT_RESPONSIBLE_CONTACT)));

        // Check, that the count call also returns 1
        restAffiliatesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAffiliatesShouldNotBeFound(String filter) throws Exception {
        restAffiliatesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAffiliatesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAffiliates() throws Exception {
        // Get the affiliates
        restAffiliatesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAffiliates() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        int databaseSizeBeforeUpdate = affiliatesRepository.findAll().size();

        // Update the affiliates
        Affiliates updatedAffiliates = affiliatesRepository.findById(affiliates.getId()).get();
        // Disconnect from session so that the updates on updatedAffiliates are not directly saved in db
        em.detach(updatedAffiliates);
        updatedAffiliates
            .branchName(UPDATED_BRANCH_NAME)
            .branchNumber(UPDATED_BRANCH_NUMBER)
            .useSameCompanyAddress(UPDATED_USE_SAME_COMPANY_ADDRESS)
            .branchPostalCode(UPDATED_BRANCH_POSTAL_CODE)
            .branchAddress(UPDATED_BRANCH_ADDRESS)
            .branchAddressComplement(UPDATED_BRANCH_ADDRESS_COMPLEMENT)
            .branchAddressNumber(UPDATED_BRANCH_ADDRESS_NUMBER)
            .branchAddressNeighborhood(UPDATED_BRANCH_ADDRESS_NEIGHBORHOOD)
            .branchTelephone(UPDATED_BRANCH_TELEPHONE)
            .branchEmail(UPDATED_BRANCH_EMAIL)
            .responsibleContact(UPDATED_RESPONSIBLE_CONTACT);
        AffiliatesDTO affiliatesDTO = affiliatesMapper.toDto(updatedAffiliates);

        restAffiliatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, affiliatesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(affiliatesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Affiliates in the database
        List<Affiliates> affiliatesList = affiliatesRepository.findAll();
        assertThat(affiliatesList).hasSize(databaseSizeBeforeUpdate);
        Affiliates testAffiliates = affiliatesList.get(affiliatesList.size() - 1);
        assertThat(testAffiliates.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testAffiliates.getBranchNumber()).isEqualTo(UPDATED_BRANCH_NUMBER);
        assertThat(testAffiliates.getUseSameCompanyAddress()).isEqualTo(UPDATED_USE_SAME_COMPANY_ADDRESS);
        assertThat(testAffiliates.getBranchPostalCode()).isEqualTo(UPDATED_BRANCH_POSTAL_CODE);
        assertThat(testAffiliates.getBranchAddress()).isEqualTo(UPDATED_BRANCH_ADDRESS);
        assertThat(testAffiliates.getBranchAddressComplement()).isEqualTo(UPDATED_BRANCH_ADDRESS_COMPLEMENT);
        assertThat(testAffiliates.getBranchAddressNumber()).isEqualTo(UPDATED_BRANCH_ADDRESS_NUMBER);
        assertThat(testAffiliates.getBranchAddressNeighborhood()).isEqualTo(UPDATED_BRANCH_ADDRESS_NEIGHBORHOOD);
        assertThat(testAffiliates.getBranchTelephone()).isEqualTo(UPDATED_BRANCH_TELEPHONE);
        assertThat(testAffiliates.getBranchEmail()).isEqualTo(UPDATED_BRANCH_EMAIL);
        assertThat(testAffiliates.getResponsibleContact()).isEqualTo(UPDATED_RESPONSIBLE_CONTACT);
    }

    @Test
    @Transactional
    void putNonExistingAffiliates() throws Exception {
        int databaseSizeBeforeUpdate = affiliatesRepository.findAll().size();
        affiliates.setId(count.incrementAndGet());

        // Create the Affiliates
        AffiliatesDTO affiliatesDTO = affiliatesMapper.toDto(affiliates);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAffiliatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, affiliatesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(affiliatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Affiliates in the database
        List<Affiliates> affiliatesList = affiliatesRepository.findAll();
        assertThat(affiliatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAffiliates() throws Exception {
        int databaseSizeBeforeUpdate = affiliatesRepository.findAll().size();
        affiliates.setId(count.incrementAndGet());

        // Create the Affiliates
        AffiliatesDTO affiliatesDTO = affiliatesMapper.toDto(affiliates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAffiliatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(affiliatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Affiliates in the database
        List<Affiliates> affiliatesList = affiliatesRepository.findAll();
        assertThat(affiliatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAffiliates() throws Exception {
        int databaseSizeBeforeUpdate = affiliatesRepository.findAll().size();
        affiliates.setId(count.incrementAndGet());

        // Create the Affiliates
        AffiliatesDTO affiliatesDTO = affiliatesMapper.toDto(affiliates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAffiliatesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliatesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Affiliates in the database
        List<Affiliates> affiliatesList = affiliatesRepository.findAll();
        assertThat(affiliatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAffiliatesWithPatch() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        int databaseSizeBeforeUpdate = affiliatesRepository.findAll().size();

        // Update the affiliates using partial update
        Affiliates partialUpdatedAffiliates = new Affiliates();
        partialUpdatedAffiliates.setId(affiliates.getId());

        partialUpdatedAffiliates
            .branchNumber(UPDATED_BRANCH_NUMBER)
            .useSameCompanyAddress(UPDATED_USE_SAME_COMPANY_ADDRESS)
            .branchPostalCode(UPDATED_BRANCH_POSTAL_CODE)
            .branchAddressComplement(UPDATED_BRANCH_ADDRESS_COMPLEMENT)
            .branchAddressNumber(UPDATED_BRANCH_ADDRESS_NUMBER)
            .branchAddressNeighborhood(UPDATED_BRANCH_ADDRESS_NEIGHBORHOOD)
            .branchTelephone(UPDATED_BRANCH_TELEPHONE)
            .branchEmail(UPDATED_BRANCH_EMAIL)
            .responsibleContact(UPDATED_RESPONSIBLE_CONTACT);

        restAffiliatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAffiliates.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAffiliates))
            )
            .andExpect(status().isOk());

        // Validate the Affiliates in the database
        List<Affiliates> affiliatesList = affiliatesRepository.findAll();
        assertThat(affiliatesList).hasSize(databaseSizeBeforeUpdate);
        Affiliates testAffiliates = affiliatesList.get(affiliatesList.size() - 1);
        assertThat(testAffiliates.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testAffiliates.getBranchNumber()).isEqualTo(UPDATED_BRANCH_NUMBER);
        assertThat(testAffiliates.getUseSameCompanyAddress()).isEqualTo(UPDATED_USE_SAME_COMPANY_ADDRESS);
        assertThat(testAffiliates.getBranchPostalCode()).isEqualTo(UPDATED_BRANCH_POSTAL_CODE);
        assertThat(testAffiliates.getBranchAddress()).isEqualTo(DEFAULT_BRANCH_ADDRESS);
        assertThat(testAffiliates.getBranchAddressComplement()).isEqualTo(UPDATED_BRANCH_ADDRESS_COMPLEMENT);
        assertThat(testAffiliates.getBranchAddressNumber()).isEqualTo(UPDATED_BRANCH_ADDRESS_NUMBER);
        assertThat(testAffiliates.getBranchAddressNeighborhood()).isEqualTo(UPDATED_BRANCH_ADDRESS_NEIGHBORHOOD);
        assertThat(testAffiliates.getBranchTelephone()).isEqualTo(UPDATED_BRANCH_TELEPHONE);
        assertThat(testAffiliates.getBranchEmail()).isEqualTo(UPDATED_BRANCH_EMAIL);
        assertThat(testAffiliates.getResponsibleContact()).isEqualTo(UPDATED_RESPONSIBLE_CONTACT);
    }

    @Test
    @Transactional
    void fullUpdateAffiliatesWithPatch() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        int databaseSizeBeforeUpdate = affiliatesRepository.findAll().size();

        // Update the affiliates using partial update
        Affiliates partialUpdatedAffiliates = new Affiliates();
        partialUpdatedAffiliates.setId(affiliates.getId());

        partialUpdatedAffiliates
            .branchName(UPDATED_BRANCH_NAME)
            .branchNumber(UPDATED_BRANCH_NUMBER)
            .useSameCompanyAddress(UPDATED_USE_SAME_COMPANY_ADDRESS)
            .branchPostalCode(UPDATED_BRANCH_POSTAL_CODE)
            .branchAddress(UPDATED_BRANCH_ADDRESS)
            .branchAddressComplement(UPDATED_BRANCH_ADDRESS_COMPLEMENT)
            .branchAddressNumber(UPDATED_BRANCH_ADDRESS_NUMBER)
            .branchAddressNeighborhood(UPDATED_BRANCH_ADDRESS_NEIGHBORHOOD)
            .branchTelephone(UPDATED_BRANCH_TELEPHONE)
            .branchEmail(UPDATED_BRANCH_EMAIL)
            .responsibleContact(UPDATED_RESPONSIBLE_CONTACT);

        restAffiliatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAffiliates.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAffiliates))
            )
            .andExpect(status().isOk());

        // Validate the Affiliates in the database
        List<Affiliates> affiliatesList = affiliatesRepository.findAll();
        assertThat(affiliatesList).hasSize(databaseSizeBeforeUpdate);
        Affiliates testAffiliates = affiliatesList.get(affiliatesList.size() - 1);
        assertThat(testAffiliates.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testAffiliates.getBranchNumber()).isEqualTo(UPDATED_BRANCH_NUMBER);
        assertThat(testAffiliates.getUseSameCompanyAddress()).isEqualTo(UPDATED_USE_SAME_COMPANY_ADDRESS);
        assertThat(testAffiliates.getBranchPostalCode()).isEqualTo(UPDATED_BRANCH_POSTAL_CODE);
        assertThat(testAffiliates.getBranchAddress()).isEqualTo(UPDATED_BRANCH_ADDRESS);
        assertThat(testAffiliates.getBranchAddressComplement()).isEqualTo(UPDATED_BRANCH_ADDRESS_COMPLEMENT);
        assertThat(testAffiliates.getBranchAddressNumber()).isEqualTo(UPDATED_BRANCH_ADDRESS_NUMBER);
        assertThat(testAffiliates.getBranchAddressNeighborhood()).isEqualTo(UPDATED_BRANCH_ADDRESS_NEIGHBORHOOD);
        assertThat(testAffiliates.getBranchTelephone()).isEqualTo(UPDATED_BRANCH_TELEPHONE);
        assertThat(testAffiliates.getBranchEmail()).isEqualTo(UPDATED_BRANCH_EMAIL);
        assertThat(testAffiliates.getResponsibleContact()).isEqualTo(UPDATED_RESPONSIBLE_CONTACT);
    }

    @Test
    @Transactional
    void patchNonExistingAffiliates() throws Exception {
        int databaseSizeBeforeUpdate = affiliatesRepository.findAll().size();
        affiliates.setId(count.incrementAndGet());

        // Create the Affiliates
        AffiliatesDTO affiliatesDTO = affiliatesMapper.toDto(affiliates);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAffiliatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, affiliatesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(affiliatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Affiliates in the database
        List<Affiliates> affiliatesList = affiliatesRepository.findAll();
        assertThat(affiliatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAffiliates() throws Exception {
        int databaseSizeBeforeUpdate = affiliatesRepository.findAll().size();
        affiliates.setId(count.incrementAndGet());

        // Create the Affiliates
        AffiliatesDTO affiliatesDTO = affiliatesMapper.toDto(affiliates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAffiliatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(affiliatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Affiliates in the database
        List<Affiliates> affiliatesList = affiliatesRepository.findAll();
        assertThat(affiliatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAffiliates() throws Exception {
        int databaseSizeBeforeUpdate = affiliatesRepository.findAll().size();
        affiliates.setId(count.incrementAndGet());

        // Create the Affiliates
        AffiliatesDTO affiliatesDTO = affiliatesMapper.toDto(affiliates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAffiliatesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(affiliatesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Affiliates in the database
        List<Affiliates> affiliatesList = affiliatesRepository.findAll();
        assertThat(affiliatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAffiliates() throws Exception {
        // Initialize the database
        affiliatesRepository.saveAndFlush(affiliates);

        int databaseSizeBeforeDelete = affiliatesRepository.findAll().size();

        // Delete the affiliates
        restAffiliatesMockMvc
            .perform(delete(ENTITY_API_URL_ID, affiliates.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Affiliates> affiliatesList = affiliatesRepository.findAll();
        assertThat(affiliatesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
