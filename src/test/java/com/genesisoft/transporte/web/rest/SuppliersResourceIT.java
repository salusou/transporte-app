package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Affiliates;
import com.genesisoft.transporte.domain.Cities;
import com.genesisoft.transporte.domain.Housing;
import com.genesisoft.transporte.domain.ServiceProvided;
import com.genesisoft.transporte.domain.SupplierBanksInfo;
import com.genesisoft.transporte.domain.SupplierContacts;
import com.genesisoft.transporte.domain.Suppliers;
import com.genesisoft.transporte.domain.VehicleControlExpenses;
import com.genesisoft.transporte.repository.SuppliersRepository;
import com.genesisoft.transporte.service.criteria.SuppliersCriteria;
import com.genesisoft.transporte.service.dto.SuppliersDTO;
import com.genesisoft.transporte.service.mapper.SuppliersMapper;
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
 * Integration tests for the {@link SuppliersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SuppliersResourceIT {

    private static final String DEFAULT_SUPPLIER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_POSTAL_CODE = "AAAAAAAAA";
    private static final String UPDATED_SUPPLIER_POSTAL_CODE = "BBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_ADDRESS_COMPLEMENT = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_ADDRESS_COMPLEMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUPPLIER_ADDRESS_NUMBER = 1;
    private static final Integer UPDATED_SUPPLIER_ADDRESS_NUMBER = 2;
    private static final Integer SMALLER_SUPPLIER_ADDRESS_NUMBER = 1 - 1;

    private static final String DEFAULT_SUPPLIER_ADDRESS_NEIGHBORHOOD = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_ADDRESS_NEIGHBORHOOD = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_CONTACT_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/suppliers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SuppliersRepository suppliersRepository;

    @Autowired
    private SuppliersMapper suppliersMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSuppliersMockMvc;

    private Suppliers suppliers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Suppliers createEntity(EntityManager em) {
        Suppliers suppliers = new Suppliers()
            .supplierName(DEFAULT_SUPPLIER_NAME)
            .supplierNumber(DEFAULT_SUPPLIER_NUMBER)
            .supplierPostalCode(DEFAULT_SUPPLIER_POSTAL_CODE)
            .supplierAddress(DEFAULT_SUPPLIER_ADDRESS)
            .supplierAddressComplement(DEFAULT_SUPPLIER_ADDRESS_COMPLEMENT)
            .supplierAddressNumber(DEFAULT_SUPPLIER_ADDRESS_NUMBER)
            .supplierAddressNeighborhood(DEFAULT_SUPPLIER_ADDRESS_NEIGHBORHOOD)
            .supplierTelephone(DEFAULT_SUPPLIER_TELEPHONE)
            .supplierEmail(DEFAULT_SUPPLIER_EMAIL)
            .supplierContactName(DEFAULT_SUPPLIER_CONTACT_NAME);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        suppliers.setAffiliates(affiliates);
        // Add required entity
        Cities cities;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            cities = CitiesResourceIT.createEntity(em);
            em.persist(cities);
            em.flush();
        } else {
            cities = TestUtil.findAll(em, Cities.class).get(0);
        }
        suppliers.setCities(cities);
        // Add required entity
        ServiceProvided serviceProvided;
        if (TestUtil.findAll(em, ServiceProvided.class).isEmpty()) {
            serviceProvided = ServiceProvidedResourceIT.createEntity(em);
            em.persist(serviceProvided);
            em.flush();
        } else {
            serviceProvided = TestUtil.findAll(em, ServiceProvided.class).get(0);
        }
        suppliers.setServiceProvided(serviceProvided);
        return suppliers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Suppliers createUpdatedEntity(EntityManager em) {
        Suppliers suppliers = new Suppliers()
            .supplierName(UPDATED_SUPPLIER_NAME)
            .supplierNumber(UPDATED_SUPPLIER_NUMBER)
            .supplierPostalCode(UPDATED_SUPPLIER_POSTAL_CODE)
            .supplierAddress(UPDATED_SUPPLIER_ADDRESS)
            .supplierAddressComplement(UPDATED_SUPPLIER_ADDRESS_COMPLEMENT)
            .supplierAddressNumber(UPDATED_SUPPLIER_ADDRESS_NUMBER)
            .supplierAddressNeighborhood(UPDATED_SUPPLIER_ADDRESS_NEIGHBORHOOD)
            .supplierTelephone(UPDATED_SUPPLIER_TELEPHONE)
            .supplierEmail(UPDATED_SUPPLIER_EMAIL)
            .supplierContactName(UPDATED_SUPPLIER_CONTACT_NAME);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createUpdatedEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        suppliers.setAffiliates(affiliates);
        // Add required entity
        Cities cities;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            cities = CitiesResourceIT.createUpdatedEntity(em);
            em.persist(cities);
            em.flush();
        } else {
            cities = TestUtil.findAll(em, Cities.class).get(0);
        }
        suppliers.setCities(cities);
        // Add required entity
        ServiceProvided serviceProvided;
        if (TestUtil.findAll(em, ServiceProvided.class).isEmpty()) {
            serviceProvided = ServiceProvidedResourceIT.createUpdatedEntity(em);
            em.persist(serviceProvided);
            em.flush();
        } else {
            serviceProvided = TestUtil.findAll(em, ServiceProvided.class).get(0);
        }
        suppliers.setServiceProvided(serviceProvided);
        return suppliers;
    }

    @BeforeEach
    public void initTest() {
        suppliers = createEntity(em);
    }

    @Test
    @Transactional
    void createSuppliers() throws Exception {
        int databaseSizeBeforeCreate = suppliersRepository.findAll().size();
        // Create the Suppliers
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);
        restSuppliersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isCreated());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeCreate + 1);
        Suppliers testSuppliers = suppliersList.get(suppliersList.size() - 1);
        assertThat(testSuppliers.getSupplierName()).isEqualTo(DEFAULT_SUPPLIER_NAME);
        assertThat(testSuppliers.getSupplierNumber()).isEqualTo(DEFAULT_SUPPLIER_NUMBER);
        assertThat(testSuppliers.getSupplierPostalCode()).isEqualTo(DEFAULT_SUPPLIER_POSTAL_CODE);
        assertThat(testSuppliers.getSupplierAddress()).isEqualTo(DEFAULT_SUPPLIER_ADDRESS);
        assertThat(testSuppliers.getSupplierAddressComplement()).isEqualTo(DEFAULT_SUPPLIER_ADDRESS_COMPLEMENT);
        assertThat(testSuppliers.getSupplierAddressNumber()).isEqualTo(DEFAULT_SUPPLIER_ADDRESS_NUMBER);
        assertThat(testSuppliers.getSupplierAddressNeighborhood()).isEqualTo(DEFAULT_SUPPLIER_ADDRESS_NEIGHBORHOOD);
        assertThat(testSuppliers.getSupplierTelephone()).isEqualTo(DEFAULT_SUPPLIER_TELEPHONE);
        assertThat(testSuppliers.getSupplierEmail()).isEqualTo(DEFAULT_SUPPLIER_EMAIL);
        assertThat(testSuppliers.getSupplierContactName()).isEqualTo(DEFAULT_SUPPLIER_CONTACT_NAME);
    }

    @Test
    @Transactional
    void createSuppliersWithExistingId() throws Exception {
        // Create the Suppliers with an existing ID
        suppliers.setId(1L);
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        int databaseSizeBeforeCreate = suppliersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuppliersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSupplierNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = suppliersRepository.findAll().size();
        // set the field null
        suppliers.setSupplierName(null);

        // Create the Suppliers, which fails.
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        restSuppliersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isBadRequest());

        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSuppliers() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList
        restSuppliersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suppliers.getId().intValue())))
            .andExpect(jsonPath("$.[*].supplierName").value(hasItem(DEFAULT_SUPPLIER_NAME)))
            .andExpect(jsonPath("$.[*].supplierNumber").value(hasItem(DEFAULT_SUPPLIER_NUMBER)))
            .andExpect(jsonPath("$.[*].supplierPostalCode").value(hasItem(DEFAULT_SUPPLIER_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].supplierAddress").value(hasItem(DEFAULT_SUPPLIER_ADDRESS)))
            .andExpect(jsonPath("$.[*].supplierAddressComplement").value(hasItem(DEFAULT_SUPPLIER_ADDRESS_COMPLEMENT)))
            .andExpect(jsonPath("$.[*].supplierAddressNumber").value(hasItem(DEFAULT_SUPPLIER_ADDRESS_NUMBER)))
            .andExpect(jsonPath("$.[*].supplierAddressNeighborhood").value(hasItem(DEFAULT_SUPPLIER_ADDRESS_NEIGHBORHOOD)))
            .andExpect(jsonPath("$.[*].supplierTelephone").value(hasItem(DEFAULT_SUPPLIER_TELEPHONE)))
            .andExpect(jsonPath("$.[*].supplierEmail").value(hasItem(DEFAULT_SUPPLIER_EMAIL)))
            .andExpect(jsonPath("$.[*].supplierContactName").value(hasItem(DEFAULT_SUPPLIER_CONTACT_NAME)));
    }

    @Test
    @Transactional
    void getSuppliers() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get the suppliers
        restSuppliersMockMvc
            .perform(get(ENTITY_API_URL_ID, suppliers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(suppliers.getId().intValue()))
            .andExpect(jsonPath("$.supplierName").value(DEFAULT_SUPPLIER_NAME))
            .andExpect(jsonPath("$.supplierNumber").value(DEFAULT_SUPPLIER_NUMBER))
            .andExpect(jsonPath("$.supplierPostalCode").value(DEFAULT_SUPPLIER_POSTAL_CODE))
            .andExpect(jsonPath("$.supplierAddress").value(DEFAULT_SUPPLIER_ADDRESS))
            .andExpect(jsonPath("$.supplierAddressComplement").value(DEFAULT_SUPPLIER_ADDRESS_COMPLEMENT))
            .andExpect(jsonPath("$.supplierAddressNumber").value(DEFAULT_SUPPLIER_ADDRESS_NUMBER))
            .andExpect(jsonPath("$.supplierAddressNeighborhood").value(DEFAULT_SUPPLIER_ADDRESS_NEIGHBORHOOD))
            .andExpect(jsonPath("$.supplierTelephone").value(DEFAULT_SUPPLIER_TELEPHONE))
            .andExpect(jsonPath("$.supplierEmail").value(DEFAULT_SUPPLIER_EMAIL))
            .andExpect(jsonPath("$.supplierContactName").value(DEFAULT_SUPPLIER_CONTACT_NAME));
    }

    @Test
    @Transactional
    void getSuppliersByIdFiltering() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        Long id = suppliers.getId();

        defaultSuppliersShouldBeFound("id.equals=" + id);
        defaultSuppliersShouldNotBeFound("id.notEquals=" + id);

        defaultSuppliersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSuppliersShouldNotBeFound("id.greaterThan=" + id);

        defaultSuppliersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSuppliersShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierNameIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierName equals to DEFAULT_SUPPLIER_NAME
        defaultSuppliersShouldBeFound("supplierName.equals=" + DEFAULT_SUPPLIER_NAME);

        // Get all the suppliersList where supplierName equals to UPDATED_SUPPLIER_NAME
        defaultSuppliersShouldNotBeFound("supplierName.equals=" + UPDATED_SUPPLIER_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierName not equals to DEFAULT_SUPPLIER_NAME
        defaultSuppliersShouldNotBeFound("supplierName.notEquals=" + DEFAULT_SUPPLIER_NAME);

        // Get all the suppliersList where supplierName not equals to UPDATED_SUPPLIER_NAME
        defaultSuppliersShouldBeFound("supplierName.notEquals=" + UPDATED_SUPPLIER_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierNameIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierName in DEFAULT_SUPPLIER_NAME or UPDATED_SUPPLIER_NAME
        defaultSuppliersShouldBeFound("supplierName.in=" + DEFAULT_SUPPLIER_NAME + "," + UPDATED_SUPPLIER_NAME);

        // Get all the suppliersList where supplierName equals to UPDATED_SUPPLIER_NAME
        defaultSuppliersShouldNotBeFound("supplierName.in=" + UPDATED_SUPPLIER_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierName is not null
        defaultSuppliersShouldBeFound("supplierName.specified=true");

        // Get all the suppliersList where supplierName is null
        defaultSuppliersShouldNotBeFound("supplierName.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierNameContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierName contains DEFAULT_SUPPLIER_NAME
        defaultSuppliersShouldBeFound("supplierName.contains=" + DEFAULT_SUPPLIER_NAME);

        // Get all the suppliersList where supplierName contains UPDATED_SUPPLIER_NAME
        defaultSuppliersShouldNotBeFound("supplierName.contains=" + UPDATED_SUPPLIER_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierNameNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierName does not contain DEFAULT_SUPPLIER_NAME
        defaultSuppliersShouldNotBeFound("supplierName.doesNotContain=" + DEFAULT_SUPPLIER_NAME);

        // Get all the suppliersList where supplierName does not contain UPDATED_SUPPLIER_NAME
        defaultSuppliersShouldBeFound("supplierName.doesNotContain=" + UPDATED_SUPPLIER_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierNumber equals to DEFAULT_SUPPLIER_NUMBER
        defaultSuppliersShouldBeFound("supplierNumber.equals=" + DEFAULT_SUPPLIER_NUMBER);

        // Get all the suppliersList where supplierNumber equals to UPDATED_SUPPLIER_NUMBER
        defaultSuppliersShouldNotBeFound("supplierNumber.equals=" + UPDATED_SUPPLIER_NUMBER);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierNumber not equals to DEFAULT_SUPPLIER_NUMBER
        defaultSuppliersShouldNotBeFound("supplierNumber.notEquals=" + DEFAULT_SUPPLIER_NUMBER);

        // Get all the suppliersList where supplierNumber not equals to UPDATED_SUPPLIER_NUMBER
        defaultSuppliersShouldBeFound("supplierNumber.notEquals=" + UPDATED_SUPPLIER_NUMBER);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierNumberIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierNumber in DEFAULT_SUPPLIER_NUMBER or UPDATED_SUPPLIER_NUMBER
        defaultSuppliersShouldBeFound("supplierNumber.in=" + DEFAULT_SUPPLIER_NUMBER + "," + UPDATED_SUPPLIER_NUMBER);

        // Get all the suppliersList where supplierNumber equals to UPDATED_SUPPLIER_NUMBER
        defaultSuppliersShouldNotBeFound("supplierNumber.in=" + UPDATED_SUPPLIER_NUMBER);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierNumber is not null
        defaultSuppliersShouldBeFound("supplierNumber.specified=true");

        // Get all the suppliersList where supplierNumber is null
        defaultSuppliersShouldNotBeFound("supplierNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierNumberContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierNumber contains DEFAULT_SUPPLIER_NUMBER
        defaultSuppliersShouldBeFound("supplierNumber.contains=" + DEFAULT_SUPPLIER_NUMBER);

        // Get all the suppliersList where supplierNumber contains UPDATED_SUPPLIER_NUMBER
        defaultSuppliersShouldNotBeFound("supplierNumber.contains=" + UPDATED_SUPPLIER_NUMBER);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierNumberNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierNumber does not contain DEFAULT_SUPPLIER_NUMBER
        defaultSuppliersShouldNotBeFound("supplierNumber.doesNotContain=" + DEFAULT_SUPPLIER_NUMBER);

        // Get all the suppliersList where supplierNumber does not contain UPDATED_SUPPLIER_NUMBER
        defaultSuppliersShouldBeFound("supplierNumber.doesNotContain=" + UPDATED_SUPPLIER_NUMBER);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierPostalCode equals to DEFAULT_SUPPLIER_POSTAL_CODE
        defaultSuppliersShouldBeFound("supplierPostalCode.equals=" + DEFAULT_SUPPLIER_POSTAL_CODE);

        // Get all the suppliersList where supplierPostalCode equals to UPDATED_SUPPLIER_POSTAL_CODE
        defaultSuppliersShouldNotBeFound("supplierPostalCode.equals=" + UPDATED_SUPPLIER_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierPostalCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierPostalCode not equals to DEFAULT_SUPPLIER_POSTAL_CODE
        defaultSuppliersShouldNotBeFound("supplierPostalCode.notEquals=" + DEFAULT_SUPPLIER_POSTAL_CODE);

        // Get all the suppliersList where supplierPostalCode not equals to UPDATED_SUPPLIER_POSTAL_CODE
        defaultSuppliersShouldBeFound("supplierPostalCode.notEquals=" + UPDATED_SUPPLIER_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierPostalCode in DEFAULT_SUPPLIER_POSTAL_CODE or UPDATED_SUPPLIER_POSTAL_CODE
        defaultSuppliersShouldBeFound("supplierPostalCode.in=" + DEFAULT_SUPPLIER_POSTAL_CODE + "," + UPDATED_SUPPLIER_POSTAL_CODE);

        // Get all the suppliersList where supplierPostalCode equals to UPDATED_SUPPLIER_POSTAL_CODE
        defaultSuppliersShouldNotBeFound("supplierPostalCode.in=" + UPDATED_SUPPLIER_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierPostalCode is not null
        defaultSuppliersShouldBeFound("supplierPostalCode.specified=true");

        // Get all the suppliersList where supplierPostalCode is null
        defaultSuppliersShouldNotBeFound("supplierPostalCode.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierPostalCodeContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierPostalCode contains DEFAULT_SUPPLIER_POSTAL_CODE
        defaultSuppliersShouldBeFound("supplierPostalCode.contains=" + DEFAULT_SUPPLIER_POSTAL_CODE);

        // Get all the suppliersList where supplierPostalCode contains UPDATED_SUPPLIER_POSTAL_CODE
        defaultSuppliersShouldNotBeFound("supplierPostalCode.contains=" + UPDATED_SUPPLIER_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierPostalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierPostalCode does not contain DEFAULT_SUPPLIER_POSTAL_CODE
        defaultSuppliersShouldNotBeFound("supplierPostalCode.doesNotContain=" + DEFAULT_SUPPLIER_POSTAL_CODE);

        // Get all the suppliersList where supplierPostalCode does not contain UPDATED_SUPPLIER_POSTAL_CODE
        defaultSuppliersShouldBeFound("supplierPostalCode.doesNotContain=" + UPDATED_SUPPLIER_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddress equals to DEFAULT_SUPPLIER_ADDRESS
        defaultSuppliersShouldBeFound("supplierAddress.equals=" + DEFAULT_SUPPLIER_ADDRESS);

        // Get all the suppliersList where supplierAddress equals to UPDATED_SUPPLIER_ADDRESS
        defaultSuppliersShouldNotBeFound("supplierAddress.equals=" + UPDATED_SUPPLIER_ADDRESS);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddress not equals to DEFAULT_SUPPLIER_ADDRESS
        defaultSuppliersShouldNotBeFound("supplierAddress.notEquals=" + DEFAULT_SUPPLIER_ADDRESS);

        // Get all the suppliersList where supplierAddress not equals to UPDATED_SUPPLIER_ADDRESS
        defaultSuppliersShouldBeFound("supplierAddress.notEquals=" + UPDATED_SUPPLIER_ADDRESS);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddress in DEFAULT_SUPPLIER_ADDRESS or UPDATED_SUPPLIER_ADDRESS
        defaultSuppliersShouldBeFound("supplierAddress.in=" + DEFAULT_SUPPLIER_ADDRESS + "," + UPDATED_SUPPLIER_ADDRESS);

        // Get all the suppliersList where supplierAddress equals to UPDATED_SUPPLIER_ADDRESS
        defaultSuppliersShouldNotBeFound("supplierAddress.in=" + UPDATED_SUPPLIER_ADDRESS);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddress is not null
        defaultSuppliersShouldBeFound("supplierAddress.specified=true");

        // Get all the suppliersList where supplierAddress is null
        defaultSuppliersShouldNotBeFound("supplierAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddress contains DEFAULT_SUPPLIER_ADDRESS
        defaultSuppliersShouldBeFound("supplierAddress.contains=" + DEFAULT_SUPPLIER_ADDRESS);

        // Get all the suppliersList where supplierAddress contains UPDATED_SUPPLIER_ADDRESS
        defaultSuppliersShouldNotBeFound("supplierAddress.contains=" + UPDATED_SUPPLIER_ADDRESS);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddress does not contain DEFAULT_SUPPLIER_ADDRESS
        defaultSuppliersShouldNotBeFound("supplierAddress.doesNotContain=" + DEFAULT_SUPPLIER_ADDRESS);

        // Get all the suppliersList where supplierAddress does not contain UPDATED_SUPPLIER_ADDRESS
        defaultSuppliersShouldBeFound("supplierAddress.doesNotContain=" + UPDATED_SUPPLIER_ADDRESS);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressComplementIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddressComplement equals to DEFAULT_SUPPLIER_ADDRESS_COMPLEMENT
        defaultSuppliersShouldBeFound("supplierAddressComplement.equals=" + DEFAULT_SUPPLIER_ADDRESS_COMPLEMENT);

        // Get all the suppliersList where supplierAddressComplement equals to UPDATED_SUPPLIER_ADDRESS_COMPLEMENT
        defaultSuppliersShouldNotBeFound("supplierAddressComplement.equals=" + UPDATED_SUPPLIER_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressComplementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddressComplement not equals to DEFAULT_SUPPLIER_ADDRESS_COMPLEMENT
        defaultSuppliersShouldNotBeFound("supplierAddressComplement.notEquals=" + DEFAULT_SUPPLIER_ADDRESS_COMPLEMENT);

        // Get all the suppliersList where supplierAddressComplement not equals to UPDATED_SUPPLIER_ADDRESS_COMPLEMENT
        defaultSuppliersShouldBeFound("supplierAddressComplement.notEquals=" + UPDATED_SUPPLIER_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressComplementIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddressComplement in DEFAULT_SUPPLIER_ADDRESS_COMPLEMENT or UPDATED_SUPPLIER_ADDRESS_COMPLEMENT
        defaultSuppliersShouldBeFound(
            "supplierAddressComplement.in=" + DEFAULT_SUPPLIER_ADDRESS_COMPLEMENT + "," + UPDATED_SUPPLIER_ADDRESS_COMPLEMENT
        );

        // Get all the suppliersList where supplierAddressComplement equals to UPDATED_SUPPLIER_ADDRESS_COMPLEMENT
        defaultSuppliersShouldNotBeFound("supplierAddressComplement.in=" + UPDATED_SUPPLIER_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressComplementIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddressComplement is not null
        defaultSuppliersShouldBeFound("supplierAddressComplement.specified=true");

        // Get all the suppliersList where supplierAddressComplement is null
        defaultSuppliersShouldNotBeFound("supplierAddressComplement.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressComplementContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddressComplement contains DEFAULT_SUPPLIER_ADDRESS_COMPLEMENT
        defaultSuppliersShouldBeFound("supplierAddressComplement.contains=" + DEFAULT_SUPPLIER_ADDRESS_COMPLEMENT);

        // Get all the suppliersList where supplierAddressComplement contains UPDATED_SUPPLIER_ADDRESS_COMPLEMENT
        defaultSuppliersShouldNotBeFound("supplierAddressComplement.contains=" + UPDATED_SUPPLIER_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressComplementNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddressComplement does not contain DEFAULT_SUPPLIER_ADDRESS_COMPLEMENT
        defaultSuppliersShouldNotBeFound("supplierAddressComplement.doesNotContain=" + DEFAULT_SUPPLIER_ADDRESS_COMPLEMENT);

        // Get all the suppliersList where supplierAddressComplement does not contain UPDATED_SUPPLIER_ADDRESS_COMPLEMENT
        defaultSuppliersShouldBeFound("supplierAddressComplement.doesNotContain=" + UPDATED_SUPPLIER_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddressNumber equals to DEFAULT_SUPPLIER_ADDRESS_NUMBER
        defaultSuppliersShouldBeFound("supplierAddressNumber.equals=" + DEFAULT_SUPPLIER_ADDRESS_NUMBER);

        // Get all the suppliersList where supplierAddressNumber equals to UPDATED_SUPPLIER_ADDRESS_NUMBER
        defaultSuppliersShouldNotBeFound("supplierAddressNumber.equals=" + UPDATED_SUPPLIER_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddressNumber not equals to DEFAULT_SUPPLIER_ADDRESS_NUMBER
        defaultSuppliersShouldNotBeFound("supplierAddressNumber.notEquals=" + DEFAULT_SUPPLIER_ADDRESS_NUMBER);

        // Get all the suppliersList where supplierAddressNumber not equals to UPDATED_SUPPLIER_ADDRESS_NUMBER
        defaultSuppliersShouldBeFound("supplierAddressNumber.notEquals=" + UPDATED_SUPPLIER_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressNumberIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddressNumber in DEFAULT_SUPPLIER_ADDRESS_NUMBER or UPDATED_SUPPLIER_ADDRESS_NUMBER
        defaultSuppliersShouldBeFound(
            "supplierAddressNumber.in=" + DEFAULT_SUPPLIER_ADDRESS_NUMBER + "," + UPDATED_SUPPLIER_ADDRESS_NUMBER
        );

        // Get all the suppliersList where supplierAddressNumber equals to UPDATED_SUPPLIER_ADDRESS_NUMBER
        defaultSuppliersShouldNotBeFound("supplierAddressNumber.in=" + UPDATED_SUPPLIER_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddressNumber is not null
        defaultSuppliersShouldBeFound("supplierAddressNumber.specified=true");

        // Get all the suppliersList where supplierAddressNumber is null
        defaultSuppliersShouldNotBeFound("supplierAddressNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddressNumber is greater than or equal to DEFAULT_SUPPLIER_ADDRESS_NUMBER
        defaultSuppliersShouldBeFound("supplierAddressNumber.greaterThanOrEqual=" + DEFAULT_SUPPLIER_ADDRESS_NUMBER);

        // Get all the suppliersList where supplierAddressNumber is greater than or equal to UPDATED_SUPPLIER_ADDRESS_NUMBER
        defaultSuppliersShouldNotBeFound("supplierAddressNumber.greaterThanOrEqual=" + UPDATED_SUPPLIER_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddressNumber is less than or equal to DEFAULT_SUPPLIER_ADDRESS_NUMBER
        defaultSuppliersShouldBeFound("supplierAddressNumber.lessThanOrEqual=" + DEFAULT_SUPPLIER_ADDRESS_NUMBER);

        // Get all the suppliersList where supplierAddressNumber is less than or equal to SMALLER_SUPPLIER_ADDRESS_NUMBER
        defaultSuppliersShouldNotBeFound("supplierAddressNumber.lessThanOrEqual=" + SMALLER_SUPPLIER_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddressNumber is less than DEFAULT_SUPPLIER_ADDRESS_NUMBER
        defaultSuppliersShouldNotBeFound("supplierAddressNumber.lessThan=" + DEFAULT_SUPPLIER_ADDRESS_NUMBER);

        // Get all the suppliersList where supplierAddressNumber is less than UPDATED_SUPPLIER_ADDRESS_NUMBER
        defaultSuppliersShouldBeFound("supplierAddressNumber.lessThan=" + UPDATED_SUPPLIER_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddressNumber is greater than DEFAULT_SUPPLIER_ADDRESS_NUMBER
        defaultSuppliersShouldNotBeFound("supplierAddressNumber.greaterThan=" + DEFAULT_SUPPLIER_ADDRESS_NUMBER);

        // Get all the suppliersList where supplierAddressNumber is greater than SMALLER_SUPPLIER_ADDRESS_NUMBER
        defaultSuppliersShouldBeFound("supplierAddressNumber.greaterThan=" + SMALLER_SUPPLIER_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressNeighborhoodIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddressNeighborhood equals to DEFAULT_SUPPLIER_ADDRESS_NEIGHBORHOOD
        defaultSuppliersShouldBeFound("supplierAddressNeighborhood.equals=" + DEFAULT_SUPPLIER_ADDRESS_NEIGHBORHOOD);

        // Get all the suppliersList where supplierAddressNeighborhood equals to UPDATED_SUPPLIER_ADDRESS_NEIGHBORHOOD
        defaultSuppliersShouldNotBeFound("supplierAddressNeighborhood.equals=" + UPDATED_SUPPLIER_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressNeighborhoodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddressNeighborhood not equals to DEFAULT_SUPPLIER_ADDRESS_NEIGHBORHOOD
        defaultSuppliersShouldNotBeFound("supplierAddressNeighborhood.notEquals=" + DEFAULT_SUPPLIER_ADDRESS_NEIGHBORHOOD);

        // Get all the suppliersList where supplierAddressNeighborhood not equals to UPDATED_SUPPLIER_ADDRESS_NEIGHBORHOOD
        defaultSuppliersShouldBeFound("supplierAddressNeighborhood.notEquals=" + UPDATED_SUPPLIER_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressNeighborhoodIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddressNeighborhood in DEFAULT_SUPPLIER_ADDRESS_NEIGHBORHOOD or UPDATED_SUPPLIER_ADDRESS_NEIGHBORHOOD
        defaultSuppliersShouldBeFound(
            "supplierAddressNeighborhood.in=" + DEFAULT_SUPPLIER_ADDRESS_NEIGHBORHOOD + "," + UPDATED_SUPPLIER_ADDRESS_NEIGHBORHOOD
        );

        // Get all the suppliersList where supplierAddressNeighborhood equals to UPDATED_SUPPLIER_ADDRESS_NEIGHBORHOOD
        defaultSuppliersShouldNotBeFound("supplierAddressNeighborhood.in=" + UPDATED_SUPPLIER_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressNeighborhoodIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddressNeighborhood is not null
        defaultSuppliersShouldBeFound("supplierAddressNeighborhood.specified=true");

        // Get all the suppliersList where supplierAddressNeighborhood is null
        defaultSuppliersShouldNotBeFound("supplierAddressNeighborhood.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressNeighborhoodContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddressNeighborhood contains DEFAULT_SUPPLIER_ADDRESS_NEIGHBORHOOD
        defaultSuppliersShouldBeFound("supplierAddressNeighborhood.contains=" + DEFAULT_SUPPLIER_ADDRESS_NEIGHBORHOOD);

        // Get all the suppliersList where supplierAddressNeighborhood contains UPDATED_SUPPLIER_ADDRESS_NEIGHBORHOOD
        defaultSuppliersShouldNotBeFound("supplierAddressNeighborhood.contains=" + UPDATED_SUPPLIER_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierAddressNeighborhoodNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierAddressNeighborhood does not contain DEFAULT_SUPPLIER_ADDRESS_NEIGHBORHOOD
        defaultSuppliersShouldNotBeFound("supplierAddressNeighborhood.doesNotContain=" + DEFAULT_SUPPLIER_ADDRESS_NEIGHBORHOOD);

        // Get all the suppliersList where supplierAddressNeighborhood does not contain UPDATED_SUPPLIER_ADDRESS_NEIGHBORHOOD
        defaultSuppliersShouldBeFound("supplierAddressNeighborhood.doesNotContain=" + UPDATED_SUPPLIER_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierTelephone equals to DEFAULT_SUPPLIER_TELEPHONE
        defaultSuppliersShouldBeFound("supplierTelephone.equals=" + DEFAULT_SUPPLIER_TELEPHONE);

        // Get all the suppliersList where supplierTelephone equals to UPDATED_SUPPLIER_TELEPHONE
        defaultSuppliersShouldNotBeFound("supplierTelephone.equals=" + UPDATED_SUPPLIER_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierTelephone not equals to DEFAULT_SUPPLIER_TELEPHONE
        defaultSuppliersShouldNotBeFound("supplierTelephone.notEquals=" + DEFAULT_SUPPLIER_TELEPHONE);

        // Get all the suppliersList where supplierTelephone not equals to UPDATED_SUPPLIER_TELEPHONE
        defaultSuppliersShouldBeFound("supplierTelephone.notEquals=" + UPDATED_SUPPLIER_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierTelephone in DEFAULT_SUPPLIER_TELEPHONE or UPDATED_SUPPLIER_TELEPHONE
        defaultSuppliersShouldBeFound("supplierTelephone.in=" + DEFAULT_SUPPLIER_TELEPHONE + "," + UPDATED_SUPPLIER_TELEPHONE);

        // Get all the suppliersList where supplierTelephone equals to UPDATED_SUPPLIER_TELEPHONE
        defaultSuppliersShouldNotBeFound("supplierTelephone.in=" + UPDATED_SUPPLIER_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierTelephone is not null
        defaultSuppliersShouldBeFound("supplierTelephone.specified=true");

        // Get all the suppliersList where supplierTelephone is null
        defaultSuppliersShouldNotBeFound("supplierTelephone.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierTelephoneContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierTelephone contains DEFAULT_SUPPLIER_TELEPHONE
        defaultSuppliersShouldBeFound("supplierTelephone.contains=" + DEFAULT_SUPPLIER_TELEPHONE);

        // Get all the suppliersList where supplierTelephone contains UPDATED_SUPPLIER_TELEPHONE
        defaultSuppliersShouldNotBeFound("supplierTelephone.contains=" + UPDATED_SUPPLIER_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierTelephone does not contain DEFAULT_SUPPLIER_TELEPHONE
        defaultSuppliersShouldNotBeFound("supplierTelephone.doesNotContain=" + DEFAULT_SUPPLIER_TELEPHONE);

        // Get all the suppliersList where supplierTelephone does not contain UPDATED_SUPPLIER_TELEPHONE
        defaultSuppliersShouldBeFound("supplierTelephone.doesNotContain=" + UPDATED_SUPPLIER_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierEmail equals to DEFAULT_SUPPLIER_EMAIL
        defaultSuppliersShouldBeFound("supplierEmail.equals=" + DEFAULT_SUPPLIER_EMAIL);

        // Get all the suppliersList where supplierEmail equals to UPDATED_SUPPLIER_EMAIL
        defaultSuppliersShouldNotBeFound("supplierEmail.equals=" + UPDATED_SUPPLIER_EMAIL);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierEmail not equals to DEFAULT_SUPPLIER_EMAIL
        defaultSuppliersShouldNotBeFound("supplierEmail.notEquals=" + DEFAULT_SUPPLIER_EMAIL);

        // Get all the suppliersList where supplierEmail not equals to UPDATED_SUPPLIER_EMAIL
        defaultSuppliersShouldBeFound("supplierEmail.notEquals=" + UPDATED_SUPPLIER_EMAIL);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierEmailIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierEmail in DEFAULT_SUPPLIER_EMAIL or UPDATED_SUPPLIER_EMAIL
        defaultSuppliersShouldBeFound("supplierEmail.in=" + DEFAULT_SUPPLIER_EMAIL + "," + UPDATED_SUPPLIER_EMAIL);

        // Get all the suppliersList where supplierEmail equals to UPDATED_SUPPLIER_EMAIL
        defaultSuppliersShouldNotBeFound("supplierEmail.in=" + UPDATED_SUPPLIER_EMAIL);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierEmail is not null
        defaultSuppliersShouldBeFound("supplierEmail.specified=true");

        // Get all the suppliersList where supplierEmail is null
        defaultSuppliersShouldNotBeFound("supplierEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierEmailContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierEmail contains DEFAULT_SUPPLIER_EMAIL
        defaultSuppliersShouldBeFound("supplierEmail.contains=" + DEFAULT_SUPPLIER_EMAIL);

        // Get all the suppliersList where supplierEmail contains UPDATED_SUPPLIER_EMAIL
        defaultSuppliersShouldNotBeFound("supplierEmail.contains=" + UPDATED_SUPPLIER_EMAIL);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierEmailNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierEmail does not contain DEFAULT_SUPPLIER_EMAIL
        defaultSuppliersShouldNotBeFound("supplierEmail.doesNotContain=" + DEFAULT_SUPPLIER_EMAIL);

        // Get all the suppliersList where supplierEmail does not contain UPDATED_SUPPLIER_EMAIL
        defaultSuppliersShouldBeFound("supplierEmail.doesNotContain=" + UPDATED_SUPPLIER_EMAIL);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierContactNameIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierContactName equals to DEFAULT_SUPPLIER_CONTACT_NAME
        defaultSuppliersShouldBeFound("supplierContactName.equals=" + DEFAULT_SUPPLIER_CONTACT_NAME);

        // Get all the suppliersList where supplierContactName equals to UPDATED_SUPPLIER_CONTACT_NAME
        defaultSuppliersShouldNotBeFound("supplierContactName.equals=" + UPDATED_SUPPLIER_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierContactNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierContactName not equals to DEFAULT_SUPPLIER_CONTACT_NAME
        defaultSuppliersShouldNotBeFound("supplierContactName.notEquals=" + DEFAULT_SUPPLIER_CONTACT_NAME);

        // Get all the suppliersList where supplierContactName not equals to UPDATED_SUPPLIER_CONTACT_NAME
        defaultSuppliersShouldBeFound("supplierContactName.notEquals=" + UPDATED_SUPPLIER_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierContactNameIsInShouldWork() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierContactName in DEFAULT_SUPPLIER_CONTACT_NAME or UPDATED_SUPPLIER_CONTACT_NAME
        defaultSuppliersShouldBeFound("supplierContactName.in=" + DEFAULT_SUPPLIER_CONTACT_NAME + "," + UPDATED_SUPPLIER_CONTACT_NAME);

        // Get all the suppliersList where supplierContactName equals to UPDATED_SUPPLIER_CONTACT_NAME
        defaultSuppliersShouldNotBeFound("supplierContactName.in=" + UPDATED_SUPPLIER_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierContactNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierContactName is not null
        defaultSuppliersShouldBeFound("supplierContactName.specified=true");

        // Get all the suppliersList where supplierContactName is null
        defaultSuppliersShouldNotBeFound("supplierContactName.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierContactNameContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierContactName contains DEFAULT_SUPPLIER_CONTACT_NAME
        defaultSuppliersShouldBeFound("supplierContactName.contains=" + DEFAULT_SUPPLIER_CONTACT_NAME);

        // Get all the suppliersList where supplierContactName contains UPDATED_SUPPLIER_CONTACT_NAME
        defaultSuppliersShouldNotBeFound("supplierContactName.contains=" + UPDATED_SUPPLIER_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierContactNameNotContainsSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        // Get all the suppliersList where supplierContactName does not contain DEFAULT_SUPPLIER_CONTACT_NAME
        defaultSuppliersShouldNotBeFound("supplierContactName.doesNotContain=" + DEFAULT_SUPPLIER_CONTACT_NAME);

        // Get all the suppliersList where supplierContactName does not contain UPDATED_SUPPLIER_CONTACT_NAME
        defaultSuppliersShouldBeFound("supplierContactName.doesNotContain=" + UPDATED_SUPPLIER_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierBanksInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        SupplierBanksInfo supplierBanksInfo = SupplierBanksInfoResourceIT.createEntity(em);
        em.persist(supplierBanksInfo);
        em.flush();
        suppliers.addSupplierBanksInfo(supplierBanksInfo);
        suppliersRepository.saveAndFlush(suppliers);
        Long supplierBanksInfoId = supplierBanksInfo.getId();

        // Get all the suppliersList where supplierBanksInfo equals to supplierBanksInfoId
        defaultSuppliersShouldBeFound("supplierBanksInfoId.equals=" + supplierBanksInfoId);

        // Get all the suppliersList where supplierBanksInfo equals to (supplierBanksInfoId + 1)
        defaultSuppliersShouldNotBeFound("supplierBanksInfoId.equals=" + (supplierBanksInfoId + 1));
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierContactsIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        SupplierContacts supplierContacts = SupplierContactsResourceIT.createEntity(em);
        em.persist(supplierContacts);
        em.flush();
        suppliers.addSupplierContacts(supplierContacts);
        suppliersRepository.saveAndFlush(suppliers);
        Long supplierContactsId = supplierContacts.getId();

        // Get all the suppliersList where supplierContacts equals to supplierContactsId
        defaultSuppliersShouldBeFound("supplierContactsId.equals=" + supplierContactsId);

        // Get all the suppliersList where supplierContacts equals to (supplierContactsId + 1)
        defaultSuppliersShouldNotBeFound("supplierContactsId.equals=" + (supplierContactsId + 1));
    }

    @Test
    @Transactional
    void getAllSuppliersByVehicleControlExpensesIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        VehicleControlExpenses vehicleControlExpenses = VehicleControlExpensesResourceIT.createEntity(em);
        em.persist(vehicleControlExpenses);
        em.flush();
        suppliers.addVehicleControlExpenses(vehicleControlExpenses);
        suppliersRepository.saveAndFlush(suppliers);
        Long vehicleControlExpensesId = vehicleControlExpenses.getId();

        // Get all the suppliersList where vehicleControlExpenses equals to vehicleControlExpensesId
        defaultSuppliersShouldBeFound("vehicleControlExpensesId.equals=" + vehicleControlExpensesId);

        // Get all the suppliersList where vehicleControlExpenses equals to (vehicleControlExpensesId + 1)
        defaultSuppliersShouldNotBeFound("vehicleControlExpensesId.equals=" + (vehicleControlExpensesId + 1));
    }

    @Test
    @Transactional
    void getAllSuppliersByHousingIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        Housing housing = HousingResourceIT.createEntity(em);
        em.persist(housing);
        em.flush();
        suppliers.addHousing(housing);
        suppliersRepository.saveAndFlush(suppliers);
        Long housingId = housing.getId();

        // Get all the suppliersList where housing equals to housingId
        defaultSuppliersShouldBeFound("housingId.equals=" + housingId);

        // Get all the suppliersList where housing equals to (housingId + 1)
        defaultSuppliersShouldNotBeFound("housingId.equals=" + (housingId + 1));
    }

    @Test
    @Transactional
    void getAllSuppliersByAffiliatesIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        Affiliates affiliates = AffiliatesResourceIT.createEntity(em);
        em.persist(affiliates);
        em.flush();
        suppliers.setAffiliates(affiliates);
        suppliersRepository.saveAndFlush(suppliers);
        Long affiliatesId = affiliates.getId();

        // Get all the suppliersList where affiliates equals to affiliatesId
        defaultSuppliersShouldBeFound("affiliatesId.equals=" + affiliatesId);

        // Get all the suppliersList where affiliates equals to (affiliatesId + 1)
        defaultSuppliersShouldNotBeFound("affiliatesId.equals=" + (affiliatesId + 1));
    }

    @Test
    @Transactional
    void getAllSuppliersByCitiesIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        Cities cities = CitiesResourceIT.createEntity(em);
        em.persist(cities);
        em.flush();
        suppliers.setCities(cities);
        suppliersRepository.saveAndFlush(suppliers);
        Long citiesId = cities.getId();

        // Get all the suppliersList where cities equals to citiesId
        defaultSuppliersShouldBeFound("citiesId.equals=" + citiesId);

        // Get all the suppliersList where cities equals to (citiesId + 1)
        defaultSuppliersShouldNotBeFound("citiesId.equals=" + (citiesId + 1));
    }

    @Test
    @Transactional
    void getAllSuppliersByServiceProvidedIsEqualToSomething() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);
        ServiceProvided serviceProvided = ServiceProvidedResourceIT.createEntity(em);
        em.persist(serviceProvided);
        em.flush();
        suppliers.setServiceProvided(serviceProvided);
        suppliersRepository.saveAndFlush(suppliers);
        Long serviceProvidedId = serviceProvided.getId();

        // Get all the suppliersList where serviceProvided equals to serviceProvidedId
        defaultSuppliersShouldBeFound("serviceProvidedId.equals=" + serviceProvidedId);

        // Get all the suppliersList where serviceProvided equals to (serviceProvidedId + 1)
        defaultSuppliersShouldNotBeFound("serviceProvidedId.equals=" + (serviceProvidedId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSuppliersShouldBeFound(String filter) throws Exception {
        restSuppliersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suppliers.getId().intValue())))
            .andExpect(jsonPath("$.[*].supplierName").value(hasItem(DEFAULT_SUPPLIER_NAME)))
            .andExpect(jsonPath("$.[*].supplierNumber").value(hasItem(DEFAULT_SUPPLIER_NUMBER)))
            .andExpect(jsonPath("$.[*].supplierPostalCode").value(hasItem(DEFAULT_SUPPLIER_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].supplierAddress").value(hasItem(DEFAULT_SUPPLIER_ADDRESS)))
            .andExpect(jsonPath("$.[*].supplierAddressComplement").value(hasItem(DEFAULT_SUPPLIER_ADDRESS_COMPLEMENT)))
            .andExpect(jsonPath("$.[*].supplierAddressNumber").value(hasItem(DEFAULT_SUPPLIER_ADDRESS_NUMBER)))
            .andExpect(jsonPath("$.[*].supplierAddressNeighborhood").value(hasItem(DEFAULT_SUPPLIER_ADDRESS_NEIGHBORHOOD)))
            .andExpect(jsonPath("$.[*].supplierTelephone").value(hasItem(DEFAULT_SUPPLIER_TELEPHONE)))
            .andExpect(jsonPath("$.[*].supplierEmail").value(hasItem(DEFAULT_SUPPLIER_EMAIL)))
            .andExpect(jsonPath("$.[*].supplierContactName").value(hasItem(DEFAULT_SUPPLIER_CONTACT_NAME)));

        // Check, that the count call also returns 1
        restSuppliersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSuppliersShouldNotBeFound(String filter) throws Exception {
        restSuppliersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSuppliersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSuppliers() throws Exception {
        // Get the suppliers
        restSuppliersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSuppliers() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        int databaseSizeBeforeUpdate = suppliersRepository.findAll().size();

        // Update the suppliers
        Suppliers updatedSuppliers = suppliersRepository.findById(suppliers.getId()).get();
        // Disconnect from session so that the updates on updatedSuppliers are not directly saved in db
        em.detach(updatedSuppliers);
        updatedSuppliers
            .supplierName(UPDATED_SUPPLIER_NAME)
            .supplierNumber(UPDATED_SUPPLIER_NUMBER)
            .supplierPostalCode(UPDATED_SUPPLIER_POSTAL_CODE)
            .supplierAddress(UPDATED_SUPPLIER_ADDRESS)
            .supplierAddressComplement(UPDATED_SUPPLIER_ADDRESS_COMPLEMENT)
            .supplierAddressNumber(UPDATED_SUPPLIER_ADDRESS_NUMBER)
            .supplierAddressNeighborhood(UPDATED_SUPPLIER_ADDRESS_NEIGHBORHOOD)
            .supplierTelephone(UPDATED_SUPPLIER_TELEPHONE)
            .supplierEmail(UPDATED_SUPPLIER_EMAIL)
            .supplierContactName(UPDATED_SUPPLIER_CONTACT_NAME);
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(updatedSuppliers);

        restSuppliersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, suppliersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(suppliersDTO))
            )
            .andExpect(status().isOk());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeUpdate);
        Suppliers testSuppliers = suppliersList.get(suppliersList.size() - 1);
        assertThat(testSuppliers.getSupplierName()).isEqualTo(UPDATED_SUPPLIER_NAME);
        assertThat(testSuppliers.getSupplierNumber()).isEqualTo(UPDATED_SUPPLIER_NUMBER);
        assertThat(testSuppliers.getSupplierPostalCode()).isEqualTo(UPDATED_SUPPLIER_POSTAL_CODE);
        assertThat(testSuppliers.getSupplierAddress()).isEqualTo(UPDATED_SUPPLIER_ADDRESS);
        assertThat(testSuppliers.getSupplierAddressComplement()).isEqualTo(UPDATED_SUPPLIER_ADDRESS_COMPLEMENT);
        assertThat(testSuppliers.getSupplierAddressNumber()).isEqualTo(UPDATED_SUPPLIER_ADDRESS_NUMBER);
        assertThat(testSuppliers.getSupplierAddressNeighborhood()).isEqualTo(UPDATED_SUPPLIER_ADDRESS_NEIGHBORHOOD);
        assertThat(testSuppliers.getSupplierTelephone()).isEqualTo(UPDATED_SUPPLIER_TELEPHONE);
        assertThat(testSuppliers.getSupplierEmail()).isEqualTo(UPDATED_SUPPLIER_EMAIL);
        assertThat(testSuppliers.getSupplierContactName()).isEqualTo(UPDATED_SUPPLIER_CONTACT_NAME);
    }

    @Test
    @Transactional
    void putNonExistingSuppliers() throws Exception {
        int databaseSizeBeforeUpdate = suppliersRepository.findAll().size();
        suppliers.setId(count.incrementAndGet());

        // Create the Suppliers
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSuppliersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, suppliersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(suppliersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSuppliers() throws Exception {
        int databaseSizeBeforeUpdate = suppliersRepository.findAll().size();
        suppliers.setId(count.incrementAndGet());

        // Create the Suppliers
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSuppliersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(suppliersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSuppliers() throws Exception {
        int databaseSizeBeforeUpdate = suppliersRepository.findAll().size();
        suppliers.setId(count.incrementAndGet());

        // Create the Suppliers
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSuppliersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(suppliersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSuppliersWithPatch() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        int databaseSizeBeforeUpdate = suppliersRepository.findAll().size();

        // Update the suppliers using partial update
        Suppliers partialUpdatedSuppliers = new Suppliers();
        partialUpdatedSuppliers.setId(suppliers.getId());

        partialUpdatedSuppliers
            .supplierName(UPDATED_SUPPLIER_NAME)
            .supplierNumber(UPDATED_SUPPLIER_NUMBER)
            .supplierPostalCode(UPDATED_SUPPLIER_POSTAL_CODE)
            .supplierAddress(UPDATED_SUPPLIER_ADDRESS)
            .supplierAddressComplement(UPDATED_SUPPLIER_ADDRESS_COMPLEMENT)
            .supplierAddressNeighborhood(UPDATED_SUPPLIER_ADDRESS_NEIGHBORHOOD)
            .supplierTelephone(UPDATED_SUPPLIER_TELEPHONE)
            .supplierEmail(UPDATED_SUPPLIER_EMAIL);

        restSuppliersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSuppliers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSuppliers))
            )
            .andExpect(status().isOk());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeUpdate);
        Suppliers testSuppliers = suppliersList.get(suppliersList.size() - 1);
        assertThat(testSuppliers.getSupplierName()).isEqualTo(UPDATED_SUPPLIER_NAME);
        assertThat(testSuppliers.getSupplierNumber()).isEqualTo(UPDATED_SUPPLIER_NUMBER);
        assertThat(testSuppliers.getSupplierPostalCode()).isEqualTo(UPDATED_SUPPLIER_POSTAL_CODE);
        assertThat(testSuppliers.getSupplierAddress()).isEqualTo(UPDATED_SUPPLIER_ADDRESS);
        assertThat(testSuppliers.getSupplierAddressComplement()).isEqualTo(UPDATED_SUPPLIER_ADDRESS_COMPLEMENT);
        assertThat(testSuppliers.getSupplierAddressNumber()).isEqualTo(DEFAULT_SUPPLIER_ADDRESS_NUMBER);
        assertThat(testSuppliers.getSupplierAddressNeighborhood()).isEqualTo(UPDATED_SUPPLIER_ADDRESS_NEIGHBORHOOD);
        assertThat(testSuppliers.getSupplierTelephone()).isEqualTo(UPDATED_SUPPLIER_TELEPHONE);
        assertThat(testSuppliers.getSupplierEmail()).isEqualTo(UPDATED_SUPPLIER_EMAIL);
        assertThat(testSuppliers.getSupplierContactName()).isEqualTo(DEFAULT_SUPPLIER_CONTACT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateSuppliersWithPatch() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        int databaseSizeBeforeUpdate = suppliersRepository.findAll().size();

        // Update the suppliers using partial update
        Suppliers partialUpdatedSuppliers = new Suppliers();
        partialUpdatedSuppliers.setId(suppliers.getId());

        partialUpdatedSuppliers
            .supplierName(UPDATED_SUPPLIER_NAME)
            .supplierNumber(UPDATED_SUPPLIER_NUMBER)
            .supplierPostalCode(UPDATED_SUPPLIER_POSTAL_CODE)
            .supplierAddress(UPDATED_SUPPLIER_ADDRESS)
            .supplierAddressComplement(UPDATED_SUPPLIER_ADDRESS_COMPLEMENT)
            .supplierAddressNumber(UPDATED_SUPPLIER_ADDRESS_NUMBER)
            .supplierAddressNeighborhood(UPDATED_SUPPLIER_ADDRESS_NEIGHBORHOOD)
            .supplierTelephone(UPDATED_SUPPLIER_TELEPHONE)
            .supplierEmail(UPDATED_SUPPLIER_EMAIL)
            .supplierContactName(UPDATED_SUPPLIER_CONTACT_NAME);

        restSuppliersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSuppliers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSuppliers))
            )
            .andExpect(status().isOk());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeUpdate);
        Suppliers testSuppliers = suppliersList.get(suppliersList.size() - 1);
        assertThat(testSuppliers.getSupplierName()).isEqualTo(UPDATED_SUPPLIER_NAME);
        assertThat(testSuppliers.getSupplierNumber()).isEqualTo(UPDATED_SUPPLIER_NUMBER);
        assertThat(testSuppliers.getSupplierPostalCode()).isEqualTo(UPDATED_SUPPLIER_POSTAL_CODE);
        assertThat(testSuppliers.getSupplierAddress()).isEqualTo(UPDATED_SUPPLIER_ADDRESS);
        assertThat(testSuppliers.getSupplierAddressComplement()).isEqualTo(UPDATED_SUPPLIER_ADDRESS_COMPLEMENT);
        assertThat(testSuppliers.getSupplierAddressNumber()).isEqualTo(UPDATED_SUPPLIER_ADDRESS_NUMBER);
        assertThat(testSuppliers.getSupplierAddressNeighborhood()).isEqualTo(UPDATED_SUPPLIER_ADDRESS_NEIGHBORHOOD);
        assertThat(testSuppliers.getSupplierTelephone()).isEqualTo(UPDATED_SUPPLIER_TELEPHONE);
        assertThat(testSuppliers.getSupplierEmail()).isEqualTo(UPDATED_SUPPLIER_EMAIL);
        assertThat(testSuppliers.getSupplierContactName()).isEqualTo(UPDATED_SUPPLIER_CONTACT_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingSuppliers() throws Exception {
        int databaseSizeBeforeUpdate = suppliersRepository.findAll().size();
        suppliers.setId(count.incrementAndGet());

        // Create the Suppliers
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSuppliersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, suppliersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(suppliersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSuppliers() throws Exception {
        int databaseSizeBeforeUpdate = suppliersRepository.findAll().size();
        suppliers.setId(count.incrementAndGet());

        // Create the Suppliers
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSuppliersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(suppliersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSuppliers() throws Exception {
        int databaseSizeBeforeUpdate = suppliersRepository.findAll().size();
        suppliers.setId(count.incrementAndGet());

        // Create the Suppliers
        SuppliersDTO suppliersDTO = suppliersMapper.toDto(suppliers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSuppliersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(suppliersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Suppliers in the database
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSuppliers() throws Exception {
        // Initialize the database
        suppliersRepository.saveAndFlush(suppliers);

        int databaseSizeBeforeDelete = suppliersRepository.findAll().size();

        // Delete the suppliers
        restSuppliersMockMvc
            .perform(delete(ENTITY_API_URL_ID, suppliers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Suppliers> suppliersList = suppliersRepository.findAll();
        assertThat(suppliersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
