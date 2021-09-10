package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Affiliates;
import com.genesisoft.transporte.domain.Cities;
import com.genesisoft.transporte.domain.CustomerAttachments;
import com.genesisoft.transporte.domain.Customers;
import com.genesisoft.transporte.domain.CustomersContacts;
import com.genesisoft.transporte.domain.CustomersGroups;
import com.genesisoft.transporte.domain.Housing;
import com.genesisoft.transporte.domain.VehicleControls;
import com.genesisoft.transporte.repository.CustomersRepository;
import com.genesisoft.transporte.service.criteria.CustomersCriteria;
import com.genesisoft.transporte.service.dto.CustomersDTO;
import com.genesisoft.transporte.service.mapper.CustomersMapper;
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
 * Integration tests for the {@link CustomersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomersResourceIT {

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_CUSTOMER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_POSTAL_CODE = "AAAAAAAAA";
    private static final String UPDATED_CUSTOMER_POSTAL_CODE = "BBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_ADDRESS_COMPLEMENT = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ADDRESS_COMPLEMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUSTOMER_ADDRESS_NUMBER = 1;
    private static final Integer UPDATED_CUSTOMER_ADDRESS_NUMBER = 2;
    private static final Integer SMALLER_CUSTOMER_ADDRESS_NUMBER = 1 - 1;

    private static final String DEFAULT_CUSTOMER_ADDRESS_NEIGHBORHOOD = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ADDRESS_NEIGHBORHOOD = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_TELEPHONE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAYMENT_TERM = 1;
    private static final Integer UPDATED_PAYMENT_TERM = 2;
    private static final Integer SMALLER_PAYMENT_TERM = 1 - 1;

    private static final String ENTITY_API_URL = "/api/customers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private CustomersMapper customersMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomersMockMvc;

    private Customers customers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customers createEntity(EntityManager em) {
        Customers customers = new Customers()
            .customerName(DEFAULT_CUSTOMER_NAME)
            .active(DEFAULT_ACTIVE)
            .customerNumber(DEFAULT_CUSTOMER_NUMBER)
            .customerPostalCode(DEFAULT_CUSTOMER_POSTAL_CODE)
            .customerAddress(DEFAULT_CUSTOMER_ADDRESS)
            .customerAddressComplement(DEFAULT_CUSTOMER_ADDRESS_COMPLEMENT)
            .customerAddressNumber(DEFAULT_CUSTOMER_ADDRESS_NUMBER)
            .customerAddressNeighborhood(DEFAULT_CUSTOMER_ADDRESS_NEIGHBORHOOD)
            .customerTelephone(DEFAULT_CUSTOMER_TELEPHONE)
            .paymentTerm(DEFAULT_PAYMENT_TERM);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        customers.setAffiliates(affiliates);
        // Add required entity
        Cities cities;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            cities = CitiesResourceIT.createEntity(em);
            em.persist(cities);
            em.flush();
        } else {
            cities = TestUtil.findAll(em, Cities.class).get(0);
        }
        customers.setCities(cities);
        return customers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customers createUpdatedEntity(EntityManager em) {
        Customers customers = new Customers()
            .customerName(UPDATED_CUSTOMER_NAME)
            .active(UPDATED_ACTIVE)
            .customerNumber(UPDATED_CUSTOMER_NUMBER)
            .customerPostalCode(UPDATED_CUSTOMER_POSTAL_CODE)
            .customerAddress(UPDATED_CUSTOMER_ADDRESS)
            .customerAddressComplement(UPDATED_CUSTOMER_ADDRESS_COMPLEMENT)
            .customerAddressNumber(UPDATED_CUSTOMER_ADDRESS_NUMBER)
            .customerAddressNeighborhood(UPDATED_CUSTOMER_ADDRESS_NEIGHBORHOOD)
            .customerTelephone(UPDATED_CUSTOMER_TELEPHONE)
            .paymentTerm(UPDATED_PAYMENT_TERM);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createUpdatedEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        customers.setAffiliates(affiliates);
        // Add required entity
        Cities cities;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            cities = CitiesResourceIT.createUpdatedEntity(em);
            em.persist(cities);
            em.flush();
        } else {
            cities = TestUtil.findAll(em, Cities.class).get(0);
        }
        customers.setCities(cities);
        return customers;
    }

    @BeforeEach
    public void initTest() {
        customers = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomers() throws Exception {
        int databaseSizeBeforeCreate = customersRepository.findAll().size();
        // Create the Customers
        CustomersDTO customersDTO = customersMapper.toDto(customers);
        restCustomersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isCreated());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeCreate + 1);
        Customers testCustomers = customersList.get(customersList.size() - 1);
        assertThat(testCustomers.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testCustomers.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testCustomers.getCustomerNumber()).isEqualTo(DEFAULT_CUSTOMER_NUMBER);
        assertThat(testCustomers.getCustomerPostalCode()).isEqualTo(DEFAULT_CUSTOMER_POSTAL_CODE);
        assertThat(testCustomers.getCustomerAddress()).isEqualTo(DEFAULT_CUSTOMER_ADDRESS);
        assertThat(testCustomers.getCustomerAddressComplement()).isEqualTo(DEFAULT_CUSTOMER_ADDRESS_COMPLEMENT);
        assertThat(testCustomers.getCustomerAddressNumber()).isEqualTo(DEFAULT_CUSTOMER_ADDRESS_NUMBER);
        assertThat(testCustomers.getCustomerAddressNeighborhood()).isEqualTo(DEFAULT_CUSTOMER_ADDRESS_NEIGHBORHOOD);
        assertThat(testCustomers.getCustomerTelephone()).isEqualTo(DEFAULT_CUSTOMER_TELEPHONE);
        assertThat(testCustomers.getPaymentTerm()).isEqualTo(DEFAULT_PAYMENT_TERM);
    }

    @Test
    @Transactional
    void createCustomersWithExistingId() throws Exception {
        // Create the Customers with an existing ID
        customers.setId(1L);
        CustomersDTO customersDTO = customersMapper.toDto(customers);

        int databaseSizeBeforeCreate = customersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCustomerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customersRepository.findAll().size();
        // set the field null
        customers.setCustomerName(null);

        // Create the Customers, which fails.
        CustomersDTO customersDTO = customersMapper.toDto(customers);

        restCustomersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = customersRepository.findAll().size();
        // set the field null
        customers.setActive(null);

        // Create the Customers, which fails.
        CustomersDTO customersDTO = customersMapper.toDto(customers);

        restCustomersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = customersRepository.findAll().size();
        // set the field null
        customers.setCustomerNumber(null);

        // Create the Customers, which fails.
        CustomersDTO customersDTO = customersMapper.toDto(customers);

        restCustomersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentTermIsRequired() throws Exception {
        int databaseSizeBeforeTest = customersRepository.findAll().size();
        // set the field null
        customers.setPaymentTerm(null);

        // Create the Customers, which fails.
        CustomersDTO customersDTO = customersMapper.toDto(customers);

        restCustomersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList
        restCustomersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customers.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].customerNumber").value(hasItem(DEFAULT_CUSTOMER_NUMBER)))
            .andExpect(jsonPath("$.[*].customerPostalCode").value(hasItem(DEFAULT_CUSTOMER_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].customerAddress").value(hasItem(DEFAULT_CUSTOMER_ADDRESS)))
            .andExpect(jsonPath("$.[*].customerAddressComplement").value(hasItem(DEFAULT_CUSTOMER_ADDRESS_COMPLEMENT)))
            .andExpect(jsonPath("$.[*].customerAddressNumber").value(hasItem(DEFAULT_CUSTOMER_ADDRESS_NUMBER)))
            .andExpect(jsonPath("$.[*].customerAddressNeighborhood").value(hasItem(DEFAULT_CUSTOMER_ADDRESS_NEIGHBORHOOD)))
            .andExpect(jsonPath("$.[*].customerTelephone").value(hasItem(DEFAULT_CUSTOMER_TELEPHONE)))
            .andExpect(jsonPath("$.[*].paymentTerm").value(hasItem(DEFAULT_PAYMENT_TERM)));
    }

    @Test
    @Transactional
    void getCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get the customers
        restCustomersMockMvc
            .perform(get(ENTITY_API_URL_ID, customers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customers.getId().intValue()))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.customerNumber").value(DEFAULT_CUSTOMER_NUMBER))
            .andExpect(jsonPath("$.customerPostalCode").value(DEFAULT_CUSTOMER_POSTAL_CODE))
            .andExpect(jsonPath("$.customerAddress").value(DEFAULT_CUSTOMER_ADDRESS))
            .andExpect(jsonPath("$.customerAddressComplement").value(DEFAULT_CUSTOMER_ADDRESS_COMPLEMENT))
            .andExpect(jsonPath("$.customerAddressNumber").value(DEFAULT_CUSTOMER_ADDRESS_NUMBER))
            .andExpect(jsonPath("$.customerAddressNeighborhood").value(DEFAULT_CUSTOMER_ADDRESS_NEIGHBORHOOD))
            .andExpect(jsonPath("$.customerTelephone").value(DEFAULT_CUSTOMER_TELEPHONE))
            .andExpect(jsonPath("$.paymentTerm").value(DEFAULT_PAYMENT_TERM));
    }

    @Test
    @Transactional
    void getCustomersByIdFiltering() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        Long id = customers.getId();

        defaultCustomersShouldBeFound("id.equals=" + id);
        defaultCustomersShouldNotBeFound("id.notEquals=" + id);

        defaultCustomersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomersShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomersShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerName equals to DEFAULT_CUSTOMER_NAME
        defaultCustomersShouldBeFound("customerName.equals=" + DEFAULT_CUSTOMER_NAME);

        // Get all the customersList where customerName equals to UPDATED_CUSTOMER_NAME
        defaultCustomersShouldNotBeFound("customerName.equals=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerName not equals to DEFAULT_CUSTOMER_NAME
        defaultCustomersShouldNotBeFound("customerName.notEquals=" + DEFAULT_CUSTOMER_NAME);

        // Get all the customersList where customerName not equals to UPDATED_CUSTOMER_NAME
        defaultCustomersShouldBeFound("customerName.notEquals=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerNameIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerName in DEFAULT_CUSTOMER_NAME or UPDATED_CUSTOMER_NAME
        defaultCustomersShouldBeFound("customerName.in=" + DEFAULT_CUSTOMER_NAME + "," + UPDATED_CUSTOMER_NAME);

        // Get all the customersList where customerName equals to UPDATED_CUSTOMER_NAME
        defaultCustomersShouldNotBeFound("customerName.in=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerName is not null
        defaultCustomersShouldBeFound("customerName.specified=true");

        // Get all the customersList where customerName is null
        defaultCustomersShouldNotBeFound("customerName.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerNameContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerName contains DEFAULT_CUSTOMER_NAME
        defaultCustomersShouldBeFound("customerName.contains=" + DEFAULT_CUSTOMER_NAME);

        // Get all the customersList where customerName contains UPDATED_CUSTOMER_NAME
        defaultCustomersShouldNotBeFound("customerName.contains=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerNameNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerName does not contain DEFAULT_CUSTOMER_NAME
        defaultCustomersShouldNotBeFound("customerName.doesNotContain=" + DEFAULT_CUSTOMER_NAME);

        // Get all the customersList where customerName does not contain UPDATED_CUSTOMER_NAME
        defaultCustomersShouldBeFound("customerName.doesNotContain=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where active equals to DEFAULT_ACTIVE
        defaultCustomersShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the customersList where active equals to UPDATED_ACTIVE
        defaultCustomersShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllCustomersByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where active not equals to DEFAULT_ACTIVE
        defaultCustomersShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the customersList where active not equals to UPDATED_ACTIVE
        defaultCustomersShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllCustomersByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCustomersShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the customersList where active equals to UPDATED_ACTIVE
        defaultCustomersShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllCustomersByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where active is not null
        defaultCustomersShouldBeFound("active.specified=true");

        // Get all the customersList where active is null
        defaultCustomersShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerNumber equals to DEFAULT_CUSTOMER_NUMBER
        defaultCustomersShouldBeFound("customerNumber.equals=" + DEFAULT_CUSTOMER_NUMBER);

        // Get all the customersList where customerNumber equals to UPDATED_CUSTOMER_NUMBER
        defaultCustomersShouldNotBeFound("customerNumber.equals=" + UPDATED_CUSTOMER_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerNumber not equals to DEFAULT_CUSTOMER_NUMBER
        defaultCustomersShouldNotBeFound("customerNumber.notEquals=" + DEFAULT_CUSTOMER_NUMBER);

        // Get all the customersList where customerNumber not equals to UPDATED_CUSTOMER_NUMBER
        defaultCustomersShouldBeFound("customerNumber.notEquals=" + UPDATED_CUSTOMER_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerNumberIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerNumber in DEFAULT_CUSTOMER_NUMBER or UPDATED_CUSTOMER_NUMBER
        defaultCustomersShouldBeFound("customerNumber.in=" + DEFAULT_CUSTOMER_NUMBER + "," + UPDATED_CUSTOMER_NUMBER);

        // Get all the customersList where customerNumber equals to UPDATED_CUSTOMER_NUMBER
        defaultCustomersShouldNotBeFound("customerNumber.in=" + UPDATED_CUSTOMER_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerNumber is not null
        defaultCustomersShouldBeFound("customerNumber.specified=true");

        // Get all the customersList where customerNumber is null
        defaultCustomersShouldNotBeFound("customerNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerNumberContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerNumber contains DEFAULT_CUSTOMER_NUMBER
        defaultCustomersShouldBeFound("customerNumber.contains=" + DEFAULT_CUSTOMER_NUMBER);

        // Get all the customersList where customerNumber contains UPDATED_CUSTOMER_NUMBER
        defaultCustomersShouldNotBeFound("customerNumber.contains=" + UPDATED_CUSTOMER_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerNumberNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerNumber does not contain DEFAULT_CUSTOMER_NUMBER
        defaultCustomersShouldNotBeFound("customerNumber.doesNotContain=" + DEFAULT_CUSTOMER_NUMBER);

        // Get all the customersList where customerNumber does not contain UPDATED_CUSTOMER_NUMBER
        defaultCustomersShouldBeFound("customerNumber.doesNotContain=" + UPDATED_CUSTOMER_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerPostalCode equals to DEFAULT_CUSTOMER_POSTAL_CODE
        defaultCustomersShouldBeFound("customerPostalCode.equals=" + DEFAULT_CUSTOMER_POSTAL_CODE);

        // Get all the customersList where customerPostalCode equals to UPDATED_CUSTOMER_POSTAL_CODE
        defaultCustomersShouldNotBeFound("customerPostalCode.equals=" + UPDATED_CUSTOMER_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerPostalCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerPostalCode not equals to DEFAULT_CUSTOMER_POSTAL_CODE
        defaultCustomersShouldNotBeFound("customerPostalCode.notEquals=" + DEFAULT_CUSTOMER_POSTAL_CODE);

        // Get all the customersList where customerPostalCode not equals to UPDATED_CUSTOMER_POSTAL_CODE
        defaultCustomersShouldBeFound("customerPostalCode.notEquals=" + UPDATED_CUSTOMER_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerPostalCode in DEFAULT_CUSTOMER_POSTAL_CODE or UPDATED_CUSTOMER_POSTAL_CODE
        defaultCustomersShouldBeFound("customerPostalCode.in=" + DEFAULT_CUSTOMER_POSTAL_CODE + "," + UPDATED_CUSTOMER_POSTAL_CODE);

        // Get all the customersList where customerPostalCode equals to UPDATED_CUSTOMER_POSTAL_CODE
        defaultCustomersShouldNotBeFound("customerPostalCode.in=" + UPDATED_CUSTOMER_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerPostalCode is not null
        defaultCustomersShouldBeFound("customerPostalCode.specified=true");

        // Get all the customersList where customerPostalCode is null
        defaultCustomersShouldNotBeFound("customerPostalCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerPostalCodeContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerPostalCode contains DEFAULT_CUSTOMER_POSTAL_CODE
        defaultCustomersShouldBeFound("customerPostalCode.contains=" + DEFAULT_CUSTOMER_POSTAL_CODE);

        // Get all the customersList where customerPostalCode contains UPDATED_CUSTOMER_POSTAL_CODE
        defaultCustomersShouldNotBeFound("customerPostalCode.contains=" + UPDATED_CUSTOMER_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerPostalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerPostalCode does not contain DEFAULT_CUSTOMER_POSTAL_CODE
        defaultCustomersShouldNotBeFound("customerPostalCode.doesNotContain=" + DEFAULT_CUSTOMER_POSTAL_CODE);

        // Get all the customersList where customerPostalCode does not contain UPDATED_CUSTOMER_POSTAL_CODE
        defaultCustomersShouldBeFound("customerPostalCode.doesNotContain=" + UPDATED_CUSTOMER_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddress equals to DEFAULT_CUSTOMER_ADDRESS
        defaultCustomersShouldBeFound("customerAddress.equals=" + DEFAULT_CUSTOMER_ADDRESS);

        // Get all the customersList where customerAddress equals to UPDATED_CUSTOMER_ADDRESS
        defaultCustomersShouldNotBeFound("customerAddress.equals=" + UPDATED_CUSTOMER_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddress not equals to DEFAULT_CUSTOMER_ADDRESS
        defaultCustomersShouldNotBeFound("customerAddress.notEquals=" + DEFAULT_CUSTOMER_ADDRESS);

        // Get all the customersList where customerAddress not equals to UPDATED_CUSTOMER_ADDRESS
        defaultCustomersShouldBeFound("customerAddress.notEquals=" + UPDATED_CUSTOMER_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddress in DEFAULT_CUSTOMER_ADDRESS or UPDATED_CUSTOMER_ADDRESS
        defaultCustomersShouldBeFound("customerAddress.in=" + DEFAULT_CUSTOMER_ADDRESS + "," + UPDATED_CUSTOMER_ADDRESS);

        // Get all the customersList where customerAddress equals to UPDATED_CUSTOMER_ADDRESS
        defaultCustomersShouldNotBeFound("customerAddress.in=" + UPDATED_CUSTOMER_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddress is not null
        defaultCustomersShouldBeFound("customerAddress.specified=true");

        // Get all the customersList where customerAddress is null
        defaultCustomersShouldNotBeFound("customerAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddress contains DEFAULT_CUSTOMER_ADDRESS
        defaultCustomersShouldBeFound("customerAddress.contains=" + DEFAULT_CUSTOMER_ADDRESS);

        // Get all the customersList where customerAddress contains UPDATED_CUSTOMER_ADDRESS
        defaultCustomersShouldNotBeFound("customerAddress.contains=" + UPDATED_CUSTOMER_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddress does not contain DEFAULT_CUSTOMER_ADDRESS
        defaultCustomersShouldNotBeFound("customerAddress.doesNotContain=" + DEFAULT_CUSTOMER_ADDRESS);

        // Get all the customersList where customerAddress does not contain UPDATED_CUSTOMER_ADDRESS
        defaultCustomersShouldBeFound("customerAddress.doesNotContain=" + UPDATED_CUSTOMER_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressComplementIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddressComplement equals to DEFAULT_CUSTOMER_ADDRESS_COMPLEMENT
        defaultCustomersShouldBeFound("customerAddressComplement.equals=" + DEFAULT_CUSTOMER_ADDRESS_COMPLEMENT);

        // Get all the customersList where customerAddressComplement equals to UPDATED_CUSTOMER_ADDRESS_COMPLEMENT
        defaultCustomersShouldNotBeFound("customerAddressComplement.equals=" + UPDATED_CUSTOMER_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressComplementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddressComplement not equals to DEFAULT_CUSTOMER_ADDRESS_COMPLEMENT
        defaultCustomersShouldNotBeFound("customerAddressComplement.notEquals=" + DEFAULT_CUSTOMER_ADDRESS_COMPLEMENT);

        // Get all the customersList where customerAddressComplement not equals to UPDATED_CUSTOMER_ADDRESS_COMPLEMENT
        defaultCustomersShouldBeFound("customerAddressComplement.notEquals=" + UPDATED_CUSTOMER_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressComplementIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddressComplement in DEFAULT_CUSTOMER_ADDRESS_COMPLEMENT or UPDATED_CUSTOMER_ADDRESS_COMPLEMENT
        defaultCustomersShouldBeFound(
            "customerAddressComplement.in=" + DEFAULT_CUSTOMER_ADDRESS_COMPLEMENT + "," + UPDATED_CUSTOMER_ADDRESS_COMPLEMENT
        );

        // Get all the customersList where customerAddressComplement equals to UPDATED_CUSTOMER_ADDRESS_COMPLEMENT
        defaultCustomersShouldNotBeFound("customerAddressComplement.in=" + UPDATED_CUSTOMER_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressComplementIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddressComplement is not null
        defaultCustomersShouldBeFound("customerAddressComplement.specified=true");

        // Get all the customersList where customerAddressComplement is null
        defaultCustomersShouldNotBeFound("customerAddressComplement.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressComplementContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddressComplement contains DEFAULT_CUSTOMER_ADDRESS_COMPLEMENT
        defaultCustomersShouldBeFound("customerAddressComplement.contains=" + DEFAULT_CUSTOMER_ADDRESS_COMPLEMENT);

        // Get all the customersList where customerAddressComplement contains UPDATED_CUSTOMER_ADDRESS_COMPLEMENT
        defaultCustomersShouldNotBeFound("customerAddressComplement.contains=" + UPDATED_CUSTOMER_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressComplementNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddressComplement does not contain DEFAULT_CUSTOMER_ADDRESS_COMPLEMENT
        defaultCustomersShouldNotBeFound("customerAddressComplement.doesNotContain=" + DEFAULT_CUSTOMER_ADDRESS_COMPLEMENT);

        // Get all the customersList where customerAddressComplement does not contain UPDATED_CUSTOMER_ADDRESS_COMPLEMENT
        defaultCustomersShouldBeFound("customerAddressComplement.doesNotContain=" + UPDATED_CUSTOMER_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddressNumber equals to DEFAULT_CUSTOMER_ADDRESS_NUMBER
        defaultCustomersShouldBeFound("customerAddressNumber.equals=" + DEFAULT_CUSTOMER_ADDRESS_NUMBER);

        // Get all the customersList where customerAddressNumber equals to UPDATED_CUSTOMER_ADDRESS_NUMBER
        defaultCustomersShouldNotBeFound("customerAddressNumber.equals=" + UPDATED_CUSTOMER_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddressNumber not equals to DEFAULT_CUSTOMER_ADDRESS_NUMBER
        defaultCustomersShouldNotBeFound("customerAddressNumber.notEquals=" + DEFAULT_CUSTOMER_ADDRESS_NUMBER);

        // Get all the customersList where customerAddressNumber not equals to UPDATED_CUSTOMER_ADDRESS_NUMBER
        defaultCustomersShouldBeFound("customerAddressNumber.notEquals=" + UPDATED_CUSTOMER_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressNumberIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddressNumber in DEFAULT_CUSTOMER_ADDRESS_NUMBER or UPDATED_CUSTOMER_ADDRESS_NUMBER
        defaultCustomersShouldBeFound(
            "customerAddressNumber.in=" + DEFAULT_CUSTOMER_ADDRESS_NUMBER + "," + UPDATED_CUSTOMER_ADDRESS_NUMBER
        );

        // Get all the customersList where customerAddressNumber equals to UPDATED_CUSTOMER_ADDRESS_NUMBER
        defaultCustomersShouldNotBeFound("customerAddressNumber.in=" + UPDATED_CUSTOMER_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddressNumber is not null
        defaultCustomersShouldBeFound("customerAddressNumber.specified=true");

        // Get all the customersList where customerAddressNumber is null
        defaultCustomersShouldNotBeFound("customerAddressNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddressNumber is greater than or equal to DEFAULT_CUSTOMER_ADDRESS_NUMBER
        defaultCustomersShouldBeFound("customerAddressNumber.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ADDRESS_NUMBER);

        // Get all the customersList where customerAddressNumber is greater than or equal to UPDATED_CUSTOMER_ADDRESS_NUMBER
        defaultCustomersShouldNotBeFound("customerAddressNumber.greaterThanOrEqual=" + UPDATED_CUSTOMER_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddressNumber is less than or equal to DEFAULT_CUSTOMER_ADDRESS_NUMBER
        defaultCustomersShouldBeFound("customerAddressNumber.lessThanOrEqual=" + DEFAULT_CUSTOMER_ADDRESS_NUMBER);

        // Get all the customersList where customerAddressNumber is less than or equal to SMALLER_CUSTOMER_ADDRESS_NUMBER
        defaultCustomersShouldNotBeFound("customerAddressNumber.lessThanOrEqual=" + SMALLER_CUSTOMER_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddressNumber is less than DEFAULT_CUSTOMER_ADDRESS_NUMBER
        defaultCustomersShouldNotBeFound("customerAddressNumber.lessThan=" + DEFAULT_CUSTOMER_ADDRESS_NUMBER);

        // Get all the customersList where customerAddressNumber is less than UPDATED_CUSTOMER_ADDRESS_NUMBER
        defaultCustomersShouldBeFound("customerAddressNumber.lessThan=" + UPDATED_CUSTOMER_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddressNumber is greater than DEFAULT_CUSTOMER_ADDRESS_NUMBER
        defaultCustomersShouldNotBeFound("customerAddressNumber.greaterThan=" + DEFAULT_CUSTOMER_ADDRESS_NUMBER);

        // Get all the customersList where customerAddressNumber is greater than SMALLER_CUSTOMER_ADDRESS_NUMBER
        defaultCustomersShouldBeFound("customerAddressNumber.greaterThan=" + SMALLER_CUSTOMER_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressNeighborhoodIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddressNeighborhood equals to DEFAULT_CUSTOMER_ADDRESS_NEIGHBORHOOD
        defaultCustomersShouldBeFound("customerAddressNeighborhood.equals=" + DEFAULT_CUSTOMER_ADDRESS_NEIGHBORHOOD);

        // Get all the customersList where customerAddressNeighborhood equals to UPDATED_CUSTOMER_ADDRESS_NEIGHBORHOOD
        defaultCustomersShouldNotBeFound("customerAddressNeighborhood.equals=" + UPDATED_CUSTOMER_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressNeighborhoodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddressNeighborhood not equals to DEFAULT_CUSTOMER_ADDRESS_NEIGHBORHOOD
        defaultCustomersShouldNotBeFound("customerAddressNeighborhood.notEquals=" + DEFAULT_CUSTOMER_ADDRESS_NEIGHBORHOOD);

        // Get all the customersList where customerAddressNeighborhood not equals to UPDATED_CUSTOMER_ADDRESS_NEIGHBORHOOD
        defaultCustomersShouldBeFound("customerAddressNeighborhood.notEquals=" + UPDATED_CUSTOMER_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressNeighborhoodIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddressNeighborhood in DEFAULT_CUSTOMER_ADDRESS_NEIGHBORHOOD or UPDATED_CUSTOMER_ADDRESS_NEIGHBORHOOD
        defaultCustomersShouldBeFound(
            "customerAddressNeighborhood.in=" + DEFAULT_CUSTOMER_ADDRESS_NEIGHBORHOOD + "," + UPDATED_CUSTOMER_ADDRESS_NEIGHBORHOOD
        );

        // Get all the customersList where customerAddressNeighborhood equals to UPDATED_CUSTOMER_ADDRESS_NEIGHBORHOOD
        defaultCustomersShouldNotBeFound("customerAddressNeighborhood.in=" + UPDATED_CUSTOMER_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressNeighborhoodIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddressNeighborhood is not null
        defaultCustomersShouldBeFound("customerAddressNeighborhood.specified=true");

        // Get all the customersList where customerAddressNeighborhood is null
        defaultCustomersShouldNotBeFound("customerAddressNeighborhood.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressNeighborhoodContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddressNeighborhood contains DEFAULT_CUSTOMER_ADDRESS_NEIGHBORHOOD
        defaultCustomersShouldBeFound("customerAddressNeighborhood.contains=" + DEFAULT_CUSTOMER_ADDRESS_NEIGHBORHOOD);

        // Get all the customersList where customerAddressNeighborhood contains UPDATED_CUSTOMER_ADDRESS_NEIGHBORHOOD
        defaultCustomersShouldNotBeFound("customerAddressNeighborhood.contains=" + UPDATED_CUSTOMER_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAddressNeighborhoodNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerAddressNeighborhood does not contain DEFAULT_CUSTOMER_ADDRESS_NEIGHBORHOOD
        defaultCustomersShouldNotBeFound("customerAddressNeighborhood.doesNotContain=" + DEFAULT_CUSTOMER_ADDRESS_NEIGHBORHOOD);

        // Get all the customersList where customerAddressNeighborhood does not contain UPDATED_CUSTOMER_ADDRESS_NEIGHBORHOOD
        defaultCustomersShouldBeFound("customerAddressNeighborhood.doesNotContain=" + UPDATED_CUSTOMER_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerTelephone equals to DEFAULT_CUSTOMER_TELEPHONE
        defaultCustomersShouldBeFound("customerTelephone.equals=" + DEFAULT_CUSTOMER_TELEPHONE);

        // Get all the customersList where customerTelephone equals to UPDATED_CUSTOMER_TELEPHONE
        defaultCustomersShouldNotBeFound("customerTelephone.equals=" + UPDATED_CUSTOMER_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerTelephone not equals to DEFAULT_CUSTOMER_TELEPHONE
        defaultCustomersShouldNotBeFound("customerTelephone.notEquals=" + DEFAULT_CUSTOMER_TELEPHONE);

        // Get all the customersList where customerTelephone not equals to UPDATED_CUSTOMER_TELEPHONE
        defaultCustomersShouldBeFound("customerTelephone.notEquals=" + UPDATED_CUSTOMER_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerTelephone in DEFAULT_CUSTOMER_TELEPHONE or UPDATED_CUSTOMER_TELEPHONE
        defaultCustomersShouldBeFound("customerTelephone.in=" + DEFAULT_CUSTOMER_TELEPHONE + "," + UPDATED_CUSTOMER_TELEPHONE);

        // Get all the customersList where customerTelephone equals to UPDATED_CUSTOMER_TELEPHONE
        defaultCustomersShouldNotBeFound("customerTelephone.in=" + UPDATED_CUSTOMER_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerTelephone is not null
        defaultCustomersShouldBeFound("customerTelephone.specified=true");

        // Get all the customersList where customerTelephone is null
        defaultCustomersShouldNotBeFound("customerTelephone.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerTelephoneContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerTelephone contains DEFAULT_CUSTOMER_TELEPHONE
        defaultCustomersShouldBeFound("customerTelephone.contains=" + DEFAULT_CUSTOMER_TELEPHONE);

        // Get all the customersList where customerTelephone contains UPDATED_CUSTOMER_TELEPHONE
        defaultCustomersShouldNotBeFound("customerTelephone.contains=" + UPDATED_CUSTOMER_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customerTelephone does not contain DEFAULT_CUSTOMER_TELEPHONE
        defaultCustomersShouldNotBeFound("customerTelephone.doesNotContain=" + DEFAULT_CUSTOMER_TELEPHONE);

        // Get all the customersList where customerTelephone does not contain UPDATED_CUSTOMER_TELEPHONE
        defaultCustomersShouldBeFound("customerTelephone.doesNotContain=" + UPDATED_CUSTOMER_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllCustomersByPaymentTermIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where paymentTerm equals to DEFAULT_PAYMENT_TERM
        defaultCustomersShouldBeFound("paymentTerm.equals=" + DEFAULT_PAYMENT_TERM);

        // Get all the customersList where paymentTerm equals to UPDATED_PAYMENT_TERM
        defaultCustomersShouldNotBeFound("paymentTerm.equals=" + UPDATED_PAYMENT_TERM);
    }

    @Test
    @Transactional
    void getAllCustomersByPaymentTermIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where paymentTerm not equals to DEFAULT_PAYMENT_TERM
        defaultCustomersShouldNotBeFound("paymentTerm.notEquals=" + DEFAULT_PAYMENT_TERM);

        // Get all the customersList where paymentTerm not equals to UPDATED_PAYMENT_TERM
        defaultCustomersShouldBeFound("paymentTerm.notEquals=" + UPDATED_PAYMENT_TERM);
    }

    @Test
    @Transactional
    void getAllCustomersByPaymentTermIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where paymentTerm in DEFAULT_PAYMENT_TERM or UPDATED_PAYMENT_TERM
        defaultCustomersShouldBeFound("paymentTerm.in=" + DEFAULT_PAYMENT_TERM + "," + UPDATED_PAYMENT_TERM);

        // Get all the customersList where paymentTerm equals to UPDATED_PAYMENT_TERM
        defaultCustomersShouldNotBeFound("paymentTerm.in=" + UPDATED_PAYMENT_TERM);
    }

    @Test
    @Transactional
    void getAllCustomersByPaymentTermIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where paymentTerm is not null
        defaultCustomersShouldBeFound("paymentTerm.specified=true");

        // Get all the customersList where paymentTerm is null
        defaultCustomersShouldNotBeFound("paymentTerm.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByPaymentTermIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where paymentTerm is greater than or equal to DEFAULT_PAYMENT_TERM
        defaultCustomersShouldBeFound("paymentTerm.greaterThanOrEqual=" + DEFAULT_PAYMENT_TERM);

        // Get all the customersList where paymentTerm is greater than or equal to UPDATED_PAYMENT_TERM
        defaultCustomersShouldNotBeFound("paymentTerm.greaterThanOrEqual=" + UPDATED_PAYMENT_TERM);
    }

    @Test
    @Transactional
    void getAllCustomersByPaymentTermIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where paymentTerm is less than or equal to DEFAULT_PAYMENT_TERM
        defaultCustomersShouldBeFound("paymentTerm.lessThanOrEqual=" + DEFAULT_PAYMENT_TERM);

        // Get all the customersList where paymentTerm is less than or equal to SMALLER_PAYMENT_TERM
        defaultCustomersShouldNotBeFound("paymentTerm.lessThanOrEqual=" + SMALLER_PAYMENT_TERM);
    }

    @Test
    @Transactional
    void getAllCustomersByPaymentTermIsLessThanSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where paymentTerm is less than DEFAULT_PAYMENT_TERM
        defaultCustomersShouldNotBeFound("paymentTerm.lessThan=" + DEFAULT_PAYMENT_TERM);

        // Get all the customersList where paymentTerm is less than UPDATED_PAYMENT_TERM
        defaultCustomersShouldBeFound("paymentTerm.lessThan=" + UPDATED_PAYMENT_TERM);
    }

    @Test
    @Transactional
    void getAllCustomersByPaymentTermIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where paymentTerm is greater than DEFAULT_PAYMENT_TERM
        defaultCustomersShouldNotBeFound("paymentTerm.greaterThan=" + DEFAULT_PAYMENT_TERM);

        // Get all the customersList where paymentTerm is greater than SMALLER_PAYMENT_TERM
        defaultCustomersShouldBeFound("paymentTerm.greaterThan=" + SMALLER_PAYMENT_TERM);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomersContactsIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);
        CustomersContacts customersContacts = CustomersContactsResourceIT.createEntity(em);
        em.persist(customersContacts);
        em.flush();
        customers.addCustomersContacts(customersContacts);
        customersRepository.saveAndFlush(customers);
        Long customersContactsId = customersContacts.getId();

        // Get all the customersList where customersContacts equals to customersContactsId
        defaultCustomersShouldBeFound("customersContactsId.equals=" + customersContactsId);

        // Get all the customersList where customersContacts equals to (customersContactsId + 1)
        defaultCustomersShouldNotBeFound("customersContactsId.equals=" + (customersContactsId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerAttachmentsIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);
        CustomerAttachments customerAttachments = CustomerAttachmentsResourceIT.createEntity(em);
        em.persist(customerAttachments);
        em.flush();
        customers.addCustomerAttachments(customerAttachments);
        customersRepository.saveAndFlush(customers);
        Long customerAttachmentsId = customerAttachments.getId();

        // Get all the customersList where customerAttachments equals to customerAttachmentsId
        defaultCustomersShouldBeFound("customerAttachmentsId.equals=" + customerAttachmentsId);

        // Get all the customersList where customerAttachments equals to (customerAttachmentsId + 1)
        defaultCustomersShouldNotBeFound("customerAttachmentsId.equals=" + (customerAttachmentsId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersByVehicleControlsIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);
        VehicleControls vehicleControls = VehicleControlsResourceIT.createEntity(em);
        em.persist(vehicleControls);
        em.flush();
        customers.addVehicleControls(vehicleControls);
        customersRepository.saveAndFlush(customers);
        Long vehicleControlsId = vehicleControls.getId();

        // Get all the customersList where vehicleControls equals to vehicleControlsId
        defaultCustomersShouldBeFound("vehicleControlsId.equals=" + vehicleControlsId);

        // Get all the customersList where vehicleControls equals to (vehicleControlsId + 1)
        defaultCustomersShouldNotBeFound("vehicleControlsId.equals=" + (vehicleControlsId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersByHousingIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);
        Housing housing = HousingResourceIT.createEntity(em);
        em.persist(housing);
        em.flush();
        customers.addHousing(housing);
        customersRepository.saveAndFlush(customers);
        Long housingId = housing.getId();

        // Get all the customersList where housing equals to housingId
        defaultCustomersShouldBeFound("housingId.equals=" + housingId);

        // Get all the customersList where housing equals to (housingId + 1)
        defaultCustomersShouldNotBeFound("housingId.equals=" + (housingId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersByAffiliatesIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);
        Affiliates affiliates = AffiliatesResourceIT.createEntity(em);
        em.persist(affiliates);
        em.flush();
        customers.setAffiliates(affiliates);
        customersRepository.saveAndFlush(customers);
        Long affiliatesId = affiliates.getId();

        // Get all the customersList where affiliates equals to affiliatesId
        defaultCustomersShouldBeFound("affiliatesId.equals=" + affiliatesId);

        // Get all the customersList where affiliates equals to (affiliatesId + 1)
        defaultCustomersShouldNotBeFound("affiliatesId.equals=" + (affiliatesId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersByCitiesIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);
        Cities cities = CitiesResourceIT.createEntity(em);
        em.persist(cities);
        em.flush();
        customers.setCities(cities);
        customersRepository.saveAndFlush(customers);
        Long citiesId = cities.getId();

        // Get all the customersList where cities equals to citiesId
        defaultCustomersShouldBeFound("citiesId.equals=" + citiesId);

        // Get all the customersList where cities equals to (citiesId + 1)
        defaultCustomersShouldNotBeFound("citiesId.equals=" + (citiesId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersByCustomersGroupsIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);
        CustomersGroups customersGroups = CustomersGroupsResourceIT.createEntity(em);
        em.persist(customersGroups);
        em.flush();
        customers.setCustomersGroups(customersGroups);
        customersRepository.saveAndFlush(customers);
        Long customersGroupsId = customersGroups.getId();

        // Get all the customersList where customersGroups equals to customersGroupsId
        defaultCustomersShouldBeFound("customersGroupsId.equals=" + customersGroupsId);

        // Get all the customersList where customersGroups equals to (customersGroupsId + 1)
        defaultCustomersShouldNotBeFound("customersGroupsId.equals=" + (customersGroupsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomersShouldBeFound(String filter) throws Exception {
        restCustomersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customers.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].customerNumber").value(hasItem(DEFAULT_CUSTOMER_NUMBER)))
            .andExpect(jsonPath("$.[*].customerPostalCode").value(hasItem(DEFAULT_CUSTOMER_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].customerAddress").value(hasItem(DEFAULT_CUSTOMER_ADDRESS)))
            .andExpect(jsonPath("$.[*].customerAddressComplement").value(hasItem(DEFAULT_CUSTOMER_ADDRESS_COMPLEMENT)))
            .andExpect(jsonPath("$.[*].customerAddressNumber").value(hasItem(DEFAULT_CUSTOMER_ADDRESS_NUMBER)))
            .andExpect(jsonPath("$.[*].customerAddressNeighborhood").value(hasItem(DEFAULT_CUSTOMER_ADDRESS_NEIGHBORHOOD)))
            .andExpect(jsonPath("$.[*].customerTelephone").value(hasItem(DEFAULT_CUSTOMER_TELEPHONE)))
            .andExpect(jsonPath("$.[*].paymentTerm").value(hasItem(DEFAULT_PAYMENT_TERM)));

        // Check, that the count call also returns 1
        restCustomersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomersShouldNotBeFound(String filter) throws Exception {
        restCustomersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustomers() throws Exception {
        // Get the customers
        restCustomersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        int databaseSizeBeforeUpdate = customersRepository.findAll().size();

        // Update the customers
        Customers updatedCustomers = customersRepository.findById(customers.getId()).get();
        // Disconnect from session so that the updates on updatedCustomers are not directly saved in db
        em.detach(updatedCustomers);
        updatedCustomers
            .customerName(UPDATED_CUSTOMER_NAME)
            .active(UPDATED_ACTIVE)
            .customerNumber(UPDATED_CUSTOMER_NUMBER)
            .customerPostalCode(UPDATED_CUSTOMER_POSTAL_CODE)
            .customerAddress(UPDATED_CUSTOMER_ADDRESS)
            .customerAddressComplement(UPDATED_CUSTOMER_ADDRESS_COMPLEMENT)
            .customerAddressNumber(UPDATED_CUSTOMER_ADDRESS_NUMBER)
            .customerAddressNeighborhood(UPDATED_CUSTOMER_ADDRESS_NEIGHBORHOOD)
            .customerTelephone(UPDATED_CUSTOMER_TELEPHONE)
            .paymentTerm(UPDATED_PAYMENT_TERM);
        CustomersDTO customersDTO = customersMapper.toDto(updatedCustomers);

        restCustomersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customersDTO))
            )
            .andExpect(status().isOk());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
        Customers testCustomers = customersList.get(customersList.size() - 1);
        assertThat(testCustomers.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testCustomers.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testCustomers.getCustomerNumber()).isEqualTo(UPDATED_CUSTOMER_NUMBER);
        assertThat(testCustomers.getCustomerPostalCode()).isEqualTo(UPDATED_CUSTOMER_POSTAL_CODE);
        assertThat(testCustomers.getCustomerAddress()).isEqualTo(UPDATED_CUSTOMER_ADDRESS);
        assertThat(testCustomers.getCustomerAddressComplement()).isEqualTo(UPDATED_CUSTOMER_ADDRESS_COMPLEMENT);
        assertThat(testCustomers.getCustomerAddressNumber()).isEqualTo(UPDATED_CUSTOMER_ADDRESS_NUMBER);
        assertThat(testCustomers.getCustomerAddressNeighborhood()).isEqualTo(UPDATED_CUSTOMER_ADDRESS_NEIGHBORHOOD);
        assertThat(testCustomers.getCustomerTelephone()).isEqualTo(UPDATED_CUSTOMER_TELEPHONE);
        assertThat(testCustomers.getPaymentTerm()).isEqualTo(UPDATED_PAYMENT_TERM);
    }

    @Test
    @Transactional
    void putNonExistingCustomers() throws Exception {
        int databaseSizeBeforeUpdate = customersRepository.findAll().size();
        customers.setId(count.incrementAndGet());

        // Create the Customers
        CustomersDTO customersDTO = customersMapper.toDto(customers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomers() throws Exception {
        int databaseSizeBeforeUpdate = customersRepository.findAll().size();
        customers.setId(count.incrementAndGet());

        // Create the Customers
        CustomersDTO customersDTO = customersMapper.toDto(customers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomers() throws Exception {
        int databaseSizeBeforeUpdate = customersRepository.findAll().size();
        customers.setId(count.incrementAndGet());

        // Create the Customers
        CustomersDTO customersDTO = customersMapper.toDto(customers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomersWithPatch() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        int databaseSizeBeforeUpdate = customersRepository.findAll().size();

        // Update the customers using partial update
        Customers partialUpdatedCustomers = new Customers();
        partialUpdatedCustomers.setId(customers.getId());

        partialUpdatedCustomers
            .customerName(UPDATED_CUSTOMER_NAME)
            .customerNumber(UPDATED_CUSTOMER_NUMBER)
            .customerAddress(UPDATED_CUSTOMER_ADDRESS)
            .customerAddressNeighborhood(UPDATED_CUSTOMER_ADDRESS_NEIGHBORHOOD)
            .paymentTerm(UPDATED_PAYMENT_TERM);

        restCustomersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomers))
            )
            .andExpect(status().isOk());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
        Customers testCustomers = customersList.get(customersList.size() - 1);
        assertThat(testCustomers.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testCustomers.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testCustomers.getCustomerNumber()).isEqualTo(UPDATED_CUSTOMER_NUMBER);
        assertThat(testCustomers.getCustomerPostalCode()).isEqualTo(DEFAULT_CUSTOMER_POSTAL_CODE);
        assertThat(testCustomers.getCustomerAddress()).isEqualTo(UPDATED_CUSTOMER_ADDRESS);
        assertThat(testCustomers.getCustomerAddressComplement()).isEqualTo(DEFAULT_CUSTOMER_ADDRESS_COMPLEMENT);
        assertThat(testCustomers.getCustomerAddressNumber()).isEqualTo(DEFAULT_CUSTOMER_ADDRESS_NUMBER);
        assertThat(testCustomers.getCustomerAddressNeighborhood()).isEqualTo(UPDATED_CUSTOMER_ADDRESS_NEIGHBORHOOD);
        assertThat(testCustomers.getCustomerTelephone()).isEqualTo(DEFAULT_CUSTOMER_TELEPHONE);
        assertThat(testCustomers.getPaymentTerm()).isEqualTo(UPDATED_PAYMENT_TERM);
    }

    @Test
    @Transactional
    void fullUpdateCustomersWithPatch() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        int databaseSizeBeforeUpdate = customersRepository.findAll().size();

        // Update the customers using partial update
        Customers partialUpdatedCustomers = new Customers();
        partialUpdatedCustomers.setId(customers.getId());

        partialUpdatedCustomers
            .customerName(UPDATED_CUSTOMER_NAME)
            .active(UPDATED_ACTIVE)
            .customerNumber(UPDATED_CUSTOMER_NUMBER)
            .customerPostalCode(UPDATED_CUSTOMER_POSTAL_CODE)
            .customerAddress(UPDATED_CUSTOMER_ADDRESS)
            .customerAddressComplement(UPDATED_CUSTOMER_ADDRESS_COMPLEMENT)
            .customerAddressNumber(UPDATED_CUSTOMER_ADDRESS_NUMBER)
            .customerAddressNeighborhood(UPDATED_CUSTOMER_ADDRESS_NEIGHBORHOOD)
            .customerTelephone(UPDATED_CUSTOMER_TELEPHONE)
            .paymentTerm(UPDATED_PAYMENT_TERM);

        restCustomersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomers))
            )
            .andExpect(status().isOk());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
        Customers testCustomers = customersList.get(customersList.size() - 1);
        assertThat(testCustomers.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testCustomers.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testCustomers.getCustomerNumber()).isEqualTo(UPDATED_CUSTOMER_NUMBER);
        assertThat(testCustomers.getCustomerPostalCode()).isEqualTo(UPDATED_CUSTOMER_POSTAL_CODE);
        assertThat(testCustomers.getCustomerAddress()).isEqualTo(UPDATED_CUSTOMER_ADDRESS);
        assertThat(testCustomers.getCustomerAddressComplement()).isEqualTo(UPDATED_CUSTOMER_ADDRESS_COMPLEMENT);
        assertThat(testCustomers.getCustomerAddressNumber()).isEqualTo(UPDATED_CUSTOMER_ADDRESS_NUMBER);
        assertThat(testCustomers.getCustomerAddressNeighborhood()).isEqualTo(UPDATED_CUSTOMER_ADDRESS_NEIGHBORHOOD);
        assertThat(testCustomers.getCustomerTelephone()).isEqualTo(UPDATED_CUSTOMER_TELEPHONE);
        assertThat(testCustomers.getPaymentTerm()).isEqualTo(UPDATED_PAYMENT_TERM);
    }

    @Test
    @Transactional
    void patchNonExistingCustomers() throws Exception {
        int databaseSizeBeforeUpdate = customersRepository.findAll().size();
        customers.setId(count.incrementAndGet());

        // Create the Customers
        CustomersDTO customersDTO = customersMapper.toDto(customers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomers() throws Exception {
        int databaseSizeBeforeUpdate = customersRepository.findAll().size();
        customers.setId(count.incrementAndGet());

        // Create the Customers
        CustomersDTO customersDTO = customersMapper.toDto(customers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomers() throws Exception {
        int databaseSizeBeforeUpdate = customersRepository.findAll().size();
        customers.setId(count.incrementAndGet());

        // Create the Customers
        CustomersDTO customersDTO = customersMapper.toDto(customers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(customersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        int databaseSizeBeforeDelete = customersRepository.findAll().size();

        // Delete the customers
        restCustomersMockMvc
            .perform(delete(ENTITY_API_URL_ID, customers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
