package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Affiliates;
import com.genesisoft.transporte.domain.Cities;
import com.genesisoft.transporte.domain.Companies;
import com.genesisoft.transporte.domain.EmployeeAttachments;
import com.genesisoft.transporte.domain.EmployeeComponentsData;
import com.genesisoft.transporte.domain.Employees;
import com.genesisoft.transporte.domain.Housing;
import com.genesisoft.transporte.domain.Positions;
import com.genesisoft.transporte.domain.VehicleControlHistory;
import com.genesisoft.transporte.domain.VehicleControls;
import com.genesisoft.transporte.repository.EmployeesRepository;
import com.genesisoft.transporte.service.criteria.EmployeesCriteria;
import com.genesisoft.transporte.service.dto.EmployeesDTO;
import com.genesisoft.transporte.service.mapper.EmployeesMapper;
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
 * Integration tests for the {@link EmployeesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeesResourceIT {

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_EMPLOYEE_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_IDENTIFICATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_IDENTIFICATION_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_POSTAL_CODE = "AAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_POSTAL_CODE = "BBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_ADDRESS_COMPLEMENT = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_ADDRESS_COMPLEMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_EMPLOYEE_ADDRESS_NUMBER = 1;
    private static final Integer UPDATED_EMPLOYEE_ADDRESS_NUMBER = 2;
    private static final Integer SMALLER_EMPLOYEE_ADDRESS_NUMBER = 1 - 1;

    private static final String DEFAULT_EMPLOYEE_ADDRESS_NEIGHBORHOOD = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_ADDRESS_NEIGHBORHOOD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EMPLOYEE_BIRTHDAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EMPLOYEE_BIRTHDAY = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_EMPLOYEE_BIRTHDAY = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/employees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private EmployeesMapper employeesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeesMockMvc;

    private Employees employees;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employees createEntity(EntityManager em) {
        Employees employees = new Employees()
            .active(DEFAULT_ACTIVE)
            .employeeFullName(DEFAULT_EMPLOYEE_FULL_NAME)
            .employeeEmail(DEFAULT_EMPLOYEE_EMAIL)
            .employeeIdentificationNumber(DEFAULT_EMPLOYEE_IDENTIFICATION_NUMBER)
            .employeeNumber(DEFAULT_EMPLOYEE_NUMBER)
            .employeePostalCode(DEFAULT_EMPLOYEE_POSTAL_CODE)
            .employeeAddress(DEFAULT_EMPLOYEE_ADDRESS)
            .employeeAddressComplement(DEFAULT_EMPLOYEE_ADDRESS_COMPLEMENT)
            .employeeAddressNumber(DEFAULT_EMPLOYEE_ADDRESS_NUMBER)
            .employeeAddressNeighborhood(DEFAULT_EMPLOYEE_ADDRESS_NEIGHBORHOOD)
            .employeeBirthday(DEFAULT_EMPLOYEE_BIRTHDAY);
        // Add required entity
        Companies companies;
        if (TestUtil.findAll(em, Companies.class).isEmpty()) {
            companies = CompaniesResourceIT.createEntity(em);
            em.persist(companies);
            em.flush();
        } else {
            companies = TestUtil.findAll(em, Companies.class).get(0);
        }
        employees.setCompanies(companies);
        // Add required entity
        Cities cities;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            cities = CitiesResourceIT.createEntity(em);
            em.persist(cities);
            em.flush();
        } else {
            cities = TestUtil.findAll(em, Cities.class).get(0);
        }
        employees.setCities(cities);
        // Add required entity
        Positions positions;
        if (TestUtil.findAll(em, Positions.class).isEmpty()) {
            positions = PositionsResourceIT.createEntity(em);
            em.persist(positions);
            em.flush();
        } else {
            positions = TestUtil.findAll(em, Positions.class).get(0);
        }
        employees.setPositions(positions);
        return employees;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employees createUpdatedEntity(EntityManager em) {
        Employees employees = new Employees()
            .active(UPDATED_ACTIVE)
            .employeeFullName(UPDATED_EMPLOYEE_FULL_NAME)
            .employeeEmail(UPDATED_EMPLOYEE_EMAIL)
            .employeeIdentificationNumber(UPDATED_EMPLOYEE_IDENTIFICATION_NUMBER)
            .employeeNumber(UPDATED_EMPLOYEE_NUMBER)
            .employeePostalCode(UPDATED_EMPLOYEE_POSTAL_CODE)
            .employeeAddress(UPDATED_EMPLOYEE_ADDRESS)
            .employeeAddressComplement(UPDATED_EMPLOYEE_ADDRESS_COMPLEMENT)
            .employeeAddressNumber(UPDATED_EMPLOYEE_ADDRESS_NUMBER)
            .employeeAddressNeighborhood(UPDATED_EMPLOYEE_ADDRESS_NEIGHBORHOOD)
            .employeeBirthday(UPDATED_EMPLOYEE_BIRTHDAY);
        // Add required entity
        Companies companies;
        if (TestUtil.findAll(em, Companies.class).isEmpty()) {
            companies = CompaniesResourceIT.createUpdatedEntity(em);
            em.persist(companies);
            em.flush();
        } else {
            companies = TestUtil.findAll(em, Companies.class).get(0);
        }
        employees.setCompanies(companies);
        // Add required entity
        Cities cities;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            cities = CitiesResourceIT.createUpdatedEntity(em);
            em.persist(cities);
            em.flush();
        } else {
            cities = TestUtil.findAll(em, Cities.class).get(0);
        }
        employees.setCities(cities);
        // Add required entity
        Positions positions;
        if (TestUtil.findAll(em, Positions.class).isEmpty()) {
            positions = PositionsResourceIT.createUpdatedEntity(em);
            em.persist(positions);
            em.flush();
        } else {
            positions = TestUtil.findAll(em, Positions.class).get(0);
        }
        employees.setPositions(positions);
        return employees;
    }

    @BeforeEach
    public void initTest() {
        employees = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployees() throws Exception {
        int databaseSizeBeforeCreate = employeesRepository.findAll().size();
        // Create the Employees
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);
        restEmployeesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isCreated());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeCreate + 1);
        Employees testEmployees = employeesList.get(employeesList.size() - 1);
        assertThat(testEmployees.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testEmployees.getEmployeeFullName()).isEqualTo(DEFAULT_EMPLOYEE_FULL_NAME);
        assertThat(testEmployees.getEmployeeEmail()).isEqualTo(DEFAULT_EMPLOYEE_EMAIL);
        assertThat(testEmployees.getEmployeeIdentificationNumber()).isEqualTo(DEFAULT_EMPLOYEE_IDENTIFICATION_NUMBER);
        assertThat(testEmployees.getEmployeeNumber()).isEqualTo(DEFAULT_EMPLOYEE_NUMBER);
        assertThat(testEmployees.getEmployeePostalCode()).isEqualTo(DEFAULT_EMPLOYEE_POSTAL_CODE);
        assertThat(testEmployees.getEmployeeAddress()).isEqualTo(DEFAULT_EMPLOYEE_ADDRESS);
        assertThat(testEmployees.getEmployeeAddressComplement()).isEqualTo(DEFAULT_EMPLOYEE_ADDRESS_COMPLEMENT);
        assertThat(testEmployees.getEmployeeAddressNumber()).isEqualTo(DEFAULT_EMPLOYEE_ADDRESS_NUMBER);
        assertThat(testEmployees.getEmployeeAddressNeighborhood()).isEqualTo(DEFAULT_EMPLOYEE_ADDRESS_NEIGHBORHOOD);
        assertThat(testEmployees.getEmployeeBirthday()).isEqualTo(DEFAULT_EMPLOYEE_BIRTHDAY);
    }

    @Test
    @Transactional
    void createEmployeesWithExistingId() throws Exception {
        // Create the Employees with an existing ID
        employees.setId(1L);
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        int databaseSizeBeforeCreate = employeesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeesRepository.findAll().size();
        // set the field null
        employees.setActive(null);

        // Create the Employees, which fails.
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        restEmployeesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isBadRequest());

        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmployeeFullNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeesRepository.findAll().size();
        // set the field null
        employees.setEmployeeFullName(null);

        // Create the Employees, which fails.
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        restEmployeesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isBadRequest());

        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmployeeEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeesRepository.findAll().size();
        // set the field null
        employees.setEmployeeEmail(null);

        // Create the Employees, which fails.
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        restEmployeesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isBadRequest());

        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList
        restEmployeesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employees.getId().intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].employeeFullName").value(hasItem(DEFAULT_EMPLOYEE_FULL_NAME)))
            .andExpect(jsonPath("$.[*].employeeEmail").value(hasItem(DEFAULT_EMPLOYEE_EMAIL)))
            .andExpect(jsonPath("$.[*].employeeIdentificationNumber").value(hasItem(DEFAULT_EMPLOYEE_IDENTIFICATION_NUMBER)))
            .andExpect(jsonPath("$.[*].employeeNumber").value(hasItem(DEFAULT_EMPLOYEE_NUMBER)))
            .andExpect(jsonPath("$.[*].employeePostalCode").value(hasItem(DEFAULT_EMPLOYEE_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].employeeAddress").value(hasItem(DEFAULT_EMPLOYEE_ADDRESS)))
            .andExpect(jsonPath("$.[*].employeeAddressComplement").value(hasItem(DEFAULT_EMPLOYEE_ADDRESS_COMPLEMENT)))
            .andExpect(jsonPath("$.[*].employeeAddressNumber").value(hasItem(DEFAULT_EMPLOYEE_ADDRESS_NUMBER)))
            .andExpect(jsonPath("$.[*].employeeAddressNeighborhood").value(hasItem(DEFAULT_EMPLOYEE_ADDRESS_NEIGHBORHOOD)))
            .andExpect(jsonPath("$.[*].employeeBirthday").value(hasItem(DEFAULT_EMPLOYEE_BIRTHDAY.toString())));
    }

    @Test
    @Transactional
    void getEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get the employees
        restEmployeesMockMvc
            .perform(get(ENTITY_API_URL_ID, employees.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employees.getId().intValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.employeeFullName").value(DEFAULT_EMPLOYEE_FULL_NAME))
            .andExpect(jsonPath("$.employeeEmail").value(DEFAULT_EMPLOYEE_EMAIL))
            .andExpect(jsonPath("$.employeeIdentificationNumber").value(DEFAULT_EMPLOYEE_IDENTIFICATION_NUMBER))
            .andExpect(jsonPath("$.employeeNumber").value(DEFAULT_EMPLOYEE_NUMBER))
            .andExpect(jsonPath("$.employeePostalCode").value(DEFAULT_EMPLOYEE_POSTAL_CODE))
            .andExpect(jsonPath("$.employeeAddress").value(DEFAULT_EMPLOYEE_ADDRESS))
            .andExpect(jsonPath("$.employeeAddressComplement").value(DEFAULT_EMPLOYEE_ADDRESS_COMPLEMENT))
            .andExpect(jsonPath("$.employeeAddressNumber").value(DEFAULT_EMPLOYEE_ADDRESS_NUMBER))
            .andExpect(jsonPath("$.employeeAddressNeighborhood").value(DEFAULT_EMPLOYEE_ADDRESS_NEIGHBORHOOD))
            .andExpect(jsonPath("$.employeeBirthday").value(DEFAULT_EMPLOYEE_BIRTHDAY.toString()));
    }

    @Test
    @Transactional
    void getEmployeesByIdFiltering() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        Long id = employees.getId();

        defaultEmployeesShouldBeFound("id.equals=" + id);
        defaultEmployeesShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeesShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where active equals to DEFAULT_ACTIVE
        defaultEmployeesShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the employeesList where active equals to UPDATED_ACTIVE
        defaultEmployeesShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllEmployeesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where active not equals to DEFAULT_ACTIVE
        defaultEmployeesShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the employeesList where active not equals to UPDATED_ACTIVE
        defaultEmployeesShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllEmployeesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultEmployeesShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the employeesList where active equals to UPDATED_ACTIVE
        defaultEmployeesShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllEmployeesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where active is not null
        defaultEmployeesShouldBeFound("active.specified=true");

        // Get all the employeesList where active is null
        defaultEmployeesShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeFullName equals to DEFAULT_EMPLOYEE_FULL_NAME
        defaultEmployeesShouldBeFound("employeeFullName.equals=" + DEFAULT_EMPLOYEE_FULL_NAME);

        // Get all the employeesList where employeeFullName equals to UPDATED_EMPLOYEE_FULL_NAME
        defaultEmployeesShouldNotBeFound("employeeFullName.equals=" + UPDATED_EMPLOYEE_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeFullNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeFullName not equals to DEFAULT_EMPLOYEE_FULL_NAME
        defaultEmployeesShouldNotBeFound("employeeFullName.notEquals=" + DEFAULT_EMPLOYEE_FULL_NAME);

        // Get all the employeesList where employeeFullName not equals to UPDATED_EMPLOYEE_FULL_NAME
        defaultEmployeesShouldBeFound("employeeFullName.notEquals=" + UPDATED_EMPLOYEE_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeFullName in DEFAULT_EMPLOYEE_FULL_NAME or UPDATED_EMPLOYEE_FULL_NAME
        defaultEmployeesShouldBeFound("employeeFullName.in=" + DEFAULT_EMPLOYEE_FULL_NAME + "," + UPDATED_EMPLOYEE_FULL_NAME);

        // Get all the employeesList where employeeFullName equals to UPDATED_EMPLOYEE_FULL_NAME
        defaultEmployeesShouldNotBeFound("employeeFullName.in=" + UPDATED_EMPLOYEE_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeFullName is not null
        defaultEmployeesShouldBeFound("employeeFullName.specified=true");

        // Get all the employeesList where employeeFullName is null
        defaultEmployeesShouldNotBeFound("employeeFullName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeFullNameContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeFullName contains DEFAULT_EMPLOYEE_FULL_NAME
        defaultEmployeesShouldBeFound("employeeFullName.contains=" + DEFAULT_EMPLOYEE_FULL_NAME);

        // Get all the employeesList where employeeFullName contains UPDATED_EMPLOYEE_FULL_NAME
        defaultEmployeesShouldNotBeFound("employeeFullName.contains=" + UPDATED_EMPLOYEE_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeFullName does not contain DEFAULT_EMPLOYEE_FULL_NAME
        defaultEmployeesShouldNotBeFound("employeeFullName.doesNotContain=" + DEFAULT_EMPLOYEE_FULL_NAME);

        // Get all the employeesList where employeeFullName does not contain UPDATED_EMPLOYEE_FULL_NAME
        defaultEmployeesShouldBeFound("employeeFullName.doesNotContain=" + UPDATED_EMPLOYEE_FULL_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeEmail equals to DEFAULT_EMPLOYEE_EMAIL
        defaultEmployeesShouldBeFound("employeeEmail.equals=" + DEFAULT_EMPLOYEE_EMAIL);

        // Get all the employeesList where employeeEmail equals to UPDATED_EMPLOYEE_EMAIL
        defaultEmployeesShouldNotBeFound("employeeEmail.equals=" + UPDATED_EMPLOYEE_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeEmail not equals to DEFAULT_EMPLOYEE_EMAIL
        defaultEmployeesShouldNotBeFound("employeeEmail.notEquals=" + DEFAULT_EMPLOYEE_EMAIL);

        // Get all the employeesList where employeeEmail not equals to UPDATED_EMPLOYEE_EMAIL
        defaultEmployeesShouldBeFound("employeeEmail.notEquals=" + UPDATED_EMPLOYEE_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeEmailIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeEmail in DEFAULT_EMPLOYEE_EMAIL or UPDATED_EMPLOYEE_EMAIL
        defaultEmployeesShouldBeFound("employeeEmail.in=" + DEFAULT_EMPLOYEE_EMAIL + "," + UPDATED_EMPLOYEE_EMAIL);

        // Get all the employeesList where employeeEmail equals to UPDATED_EMPLOYEE_EMAIL
        defaultEmployeesShouldNotBeFound("employeeEmail.in=" + UPDATED_EMPLOYEE_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeEmail is not null
        defaultEmployeesShouldBeFound("employeeEmail.specified=true");

        // Get all the employeesList where employeeEmail is null
        defaultEmployeesShouldNotBeFound("employeeEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeEmailContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeEmail contains DEFAULT_EMPLOYEE_EMAIL
        defaultEmployeesShouldBeFound("employeeEmail.contains=" + DEFAULT_EMPLOYEE_EMAIL);

        // Get all the employeesList where employeeEmail contains UPDATED_EMPLOYEE_EMAIL
        defaultEmployeesShouldNotBeFound("employeeEmail.contains=" + UPDATED_EMPLOYEE_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeEmailNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeEmail does not contain DEFAULT_EMPLOYEE_EMAIL
        defaultEmployeesShouldNotBeFound("employeeEmail.doesNotContain=" + DEFAULT_EMPLOYEE_EMAIL);

        // Get all the employeesList where employeeEmail does not contain UPDATED_EMPLOYEE_EMAIL
        defaultEmployeesShouldBeFound("employeeEmail.doesNotContain=" + UPDATED_EMPLOYEE_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeIdentificationNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeIdentificationNumber equals to DEFAULT_EMPLOYEE_IDENTIFICATION_NUMBER
        defaultEmployeesShouldBeFound("employeeIdentificationNumber.equals=" + DEFAULT_EMPLOYEE_IDENTIFICATION_NUMBER);

        // Get all the employeesList where employeeIdentificationNumber equals to UPDATED_EMPLOYEE_IDENTIFICATION_NUMBER
        defaultEmployeesShouldNotBeFound("employeeIdentificationNumber.equals=" + UPDATED_EMPLOYEE_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeIdentificationNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeIdentificationNumber not equals to DEFAULT_EMPLOYEE_IDENTIFICATION_NUMBER
        defaultEmployeesShouldNotBeFound("employeeIdentificationNumber.notEquals=" + DEFAULT_EMPLOYEE_IDENTIFICATION_NUMBER);

        // Get all the employeesList where employeeIdentificationNumber not equals to UPDATED_EMPLOYEE_IDENTIFICATION_NUMBER
        defaultEmployeesShouldBeFound("employeeIdentificationNumber.notEquals=" + UPDATED_EMPLOYEE_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeIdentificationNumberIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeIdentificationNumber in DEFAULT_EMPLOYEE_IDENTIFICATION_NUMBER or UPDATED_EMPLOYEE_IDENTIFICATION_NUMBER
        defaultEmployeesShouldBeFound(
            "employeeIdentificationNumber.in=" + DEFAULT_EMPLOYEE_IDENTIFICATION_NUMBER + "," + UPDATED_EMPLOYEE_IDENTIFICATION_NUMBER
        );

        // Get all the employeesList where employeeIdentificationNumber equals to UPDATED_EMPLOYEE_IDENTIFICATION_NUMBER
        defaultEmployeesShouldNotBeFound("employeeIdentificationNumber.in=" + UPDATED_EMPLOYEE_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeIdentificationNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeIdentificationNumber is not null
        defaultEmployeesShouldBeFound("employeeIdentificationNumber.specified=true");

        // Get all the employeesList where employeeIdentificationNumber is null
        defaultEmployeesShouldNotBeFound("employeeIdentificationNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeIdentificationNumberContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeIdentificationNumber contains DEFAULT_EMPLOYEE_IDENTIFICATION_NUMBER
        defaultEmployeesShouldBeFound("employeeIdentificationNumber.contains=" + DEFAULT_EMPLOYEE_IDENTIFICATION_NUMBER);

        // Get all the employeesList where employeeIdentificationNumber contains UPDATED_EMPLOYEE_IDENTIFICATION_NUMBER
        defaultEmployeesShouldNotBeFound("employeeIdentificationNumber.contains=" + UPDATED_EMPLOYEE_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeIdentificationNumberNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeIdentificationNumber does not contain DEFAULT_EMPLOYEE_IDENTIFICATION_NUMBER
        defaultEmployeesShouldNotBeFound("employeeIdentificationNumber.doesNotContain=" + DEFAULT_EMPLOYEE_IDENTIFICATION_NUMBER);

        // Get all the employeesList where employeeIdentificationNumber does not contain UPDATED_EMPLOYEE_IDENTIFICATION_NUMBER
        defaultEmployeesShouldBeFound("employeeIdentificationNumber.doesNotContain=" + UPDATED_EMPLOYEE_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeNumber equals to DEFAULT_EMPLOYEE_NUMBER
        defaultEmployeesShouldBeFound("employeeNumber.equals=" + DEFAULT_EMPLOYEE_NUMBER);

        // Get all the employeesList where employeeNumber equals to UPDATED_EMPLOYEE_NUMBER
        defaultEmployeesShouldNotBeFound("employeeNumber.equals=" + UPDATED_EMPLOYEE_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeNumber not equals to DEFAULT_EMPLOYEE_NUMBER
        defaultEmployeesShouldNotBeFound("employeeNumber.notEquals=" + DEFAULT_EMPLOYEE_NUMBER);

        // Get all the employeesList where employeeNumber not equals to UPDATED_EMPLOYEE_NUMBER
        defaultEmployeesShouldBeFound("employeeNumber.notEquals=" + UPDATED_EMPLOYEE_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeNumberIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeNumber in DEFAULT_EMPLOYEE_NUMBER or UPDATED_EMPLOYEE_NUMBER
        defaultEmployeesShouldBeFound("employeeNumber.in=" + DEFAULT_EMPLOYEE_NUMBER + "," + UPDATED_EMPLOYEE_NUMBER);

        // Get all the employeesList where employeeNumber equals to UPDATED_EMPLOYEE_NUMBER
        defaultEmployeesShouldNotBeFound("employeeNumber.in=" + UPDATED_EMPLOYEE_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeNumber is not null
        defaultEmployeesShouldBeFound("employeeNumber.specified=true");

        // Get all the employeesList where employeeNumber is null
        defaultEmployeesShouldNotBeFound("employeeNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeNumberContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeNumber contains DEFAULT_EMPLOYEE_NUMBER
        defaultEmployeesShouldBeFound("employeeNumber.contains=" + DEFAULT_EMPLOYEE_NUMBER);

        // Get all the employeesList where employeeNumber contains UPDATED_EMPLOYEE_NUMBER
        defaultEmployeesShouldNotBeFound("employeeNumber.contains=" + UPDATED_EMPLOYEE_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeNumberNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeNumber does not contain DEFAULT_EMPLOYEE_NUMBER
        defaultEmployeesShouldNotBeFound("employeeNumber.doesNotContain=" + DEFAULT_EMPLOYEE_NUMBER);

        // Get all the employeesList where employeeNumber does not contain UPDATED_EMPLOYEE_NUMBER
        defaultEmployeesShouldBeFound("employeeNumber.doesNotContain=" + UPDATED_EMPLOYEE_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeePostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeePostalCode equals to DEFAULT_EMPLOYEE_POSTAL_CODE
        defaultEmployeesShouldBeFound("employeePostalCode.equals=" + DEFAULT_EMPLOYEE_POSTAL_CODE);

        // Get all the employeesList where employeePostalCode equals to UPDATED_EMPLOYEE_POSTAL_CODE
        defaultEmployeesShouldNotBeFound("employeePostalCode.equals=" + UPDATED_EMPLOYEE_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeePostalCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeePostalCode not equals to DEFAULT_EMPLOYEE_POSTAL_CODE
        defaultEmployeesShouldNotBeFound("employeePostalCode.notEquals=" + DEFAULT_EMPLOYEE_POSTAL_CODE);

        // Get all the employeesList where employeePostalCode not equals to UPDATED_EMPLOYEE_POSTAL_CODE
        defaultEmployeesShouldBeFound("employeePostalCode.notEquals=" + UPDATED_EMPLOYEE_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeePostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeePostalCode in DEFAULT_EMPLOYEE_POSTAL_CODE or UPDATED_EMPLOYEE_POSTAL_CODE
        defaultEmployeesShouldBeFound("employeePostalCode.in=" + DEFAULT_EMPLOYEE_POSTAL_CODE + "," + UPDATED_EMPLOYEE_POSTAL_CODE);

        // Get all the employeesList where employeePostalCode equals to UPDATED_EMPLOYEE_POSTAL_CODE
        defaultEmployeesShouldNotBeFound("employeePostalCode.in=" + UPDATED_EMPLOYEE_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeePostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeePostalCode is not null
        defaultEmployeesShouldBeFound("employeePostalCode.specified=true");

        // Get all the employeesList where employeePostalCode is null
        defaultEmployeesShouldNotBeFound("employeePostalCode.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeePostalCodeContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeePostalCode contains DEFAULT_EMPLOYEE_POSTAL_CODE
        defaultEmployeesShouldBeFound("employeePostalCode.contains=" + DEFAULT_EMPLOYEE_POSTAL_CODE);

        // Get all the employeesList where employeePostalCode contains UPDATED_EMPLOYEE_POSTAL_CODE
        defaultEmployeesShouldNotBeFound("employeePostalCode.contains=" + UPDATED_EMPLOYEE_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeePostalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeePostalCode does not contain DEFAULT_EMPLOYEE_POSTAL_CODE
        defaultEmployeesShouldNotBeFound("employeePostalCode.doesNotContain=" + DEFAULT_EMPLOYEE_POSTAL_CODE);

        // Get all the employeesList where employeePostalCode does not contain UPDATED_EMPLOYEE_POSTAL_CODE
        defaultEmployeesShouldBeFound("employeePostalCode.doesNotContain=" + UPDATED_EMPLOYEE_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddress equals to DEFAULT_EMPLOYEE_ADDRESS
        defaultEmployeesShouldBeFound("employeeAddress.equals=" + DEFAULT_EMPLOYEE_ADDRESS);

        // Get all the employeesList where employeeAddress equals to UPDATED_EMPLOYEE_ADDRESS
        defaultEmployeesShouldNotBeFound("employeeAddress.equals=" + UPDATED_EMPLOYEE_ADDRESS);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddress not equals to DEFAULT_EMPLOYEE_ADDRESS
        defaultEmployeesShouldNotBeFound("employeeAddress.notEquals=" + DEFAULT_EMPLOYEE_ADDRESS);

        // Get all the employeesList where employeeAddress not equals to UPDATED_EMPLOYEE_ADDRESS
        defaultEmployeesShouldBeFound("employeeAddress.notEquals=" + UPDATED_EMPLOYEE_ADDRESS);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddress in DEFAULT_EMPLOYEE_ADDRESS or UPDATED_EMPLOYEE_ADDRESS
        defaultEmployeesShouldBeFound("employeeAddress.in=" + DEFAULT_EMPLOYEE_ADDRESS + "," + UPDATED_EMPLOYEE_ADDRESS);

        // Get all the employeesList where employeeAddress equals to UPDATED_EMPLOYEE_ADDRESS
        defaultEmployeesShouldNotBeFound("employeeAddress.in=" + UPDATED_EMPLOYEE_ADDRESS);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddress is not null
        defaultEmployeesShouldBeFound("employeeAddress.specified=true");

        // Get all the employeesList where employeeAddress is null
        defaultEmployeesShouldNotBeFound("employeeAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddress contains DEFAULT_EMPLOYEE_ADDRESS
        defaultEmployeesShouldBeFound("employeeAddress.contains=" + DEFAULT_EMPLOYEE_ADDRESS);

        // Get all the employeesList where employeeAddress contains UPDATED_EMPLOYEE_ADDRESS
        defaultEmployeesShouldNotBeFound("employeeAddress.contains=" + UPDATED_EMPLOYEE_ADDRESS);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddress does not contain DEFAULT_EMPLOYEE_ADDRESS
        defaultEmployeesShouldNotBeFound("employeeAddress.doesNotContain=" + DEFAULT_EMPLOYEE_ADDRESS);

        // Get all the employeesList where employeeAddress does not contain UPDATED_EMPLOYEE_ADDRESS
        defaultEmployeesShouldBeFound("employeeAddress.doesNotContain=" + UPDATED_EMPLOYEE_ADDRESS);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressComplementIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddressComplement equals to DEFAULT_EMPLOYEE_ADDRESS_COMPLEMENT
        defaultEmployeesShouldBeFound("employeeAddressComplement.equals=" + DEFAULT_EMPLOYEE_ADDRESS_COMPLEMENT);

        // Get all the employeesList where employeeAddressComplement equals to UPDATED_EMPLOYEE_ADDRESS_COMPLEMENT
        defaultEmployeesShouldNotBeFound("employeeAddressComplement.equals=" + UPDATED_EMPLOYEE_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressComplementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddressComplement not equals to DEFAULT_EMPLOYEE_ADDRESS_COMPLEMENT
        defaultEmployeesShouldNotBeFound("employeeAddressComplement.notEquals=" + DEFAULT_EMPLOYEE_ADDRESS_COMPLEMENT);

        // Get all the employeesList where employeeAddressComplement not equals to UPDATED_EMPLOYEE_ADDRESS_COMPLEMENT
        defaultEmployeesShouldBeFound("employeeAddressComplement.notEquals=" + UPDATED_EMPLOYEE_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressComplementIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddressComplement in DEFAULT_EMPLOYEE_ADDRESS_COMPLEMENT or UPDATED_EMPLOYEE_ADDRESS_COMPLEMENT
        defaultEmployeesShouldBeFound(
            "employeeAddressComplement.in=" + DEFAULT_EMPLOYEE_ADDRESS_COMPLEMENT + "," + UPDATED_EMPLOYEE_ADDRESS_COMPLEMENT
        );

        // Get all the employeesList where employeeAddressComplement equals to UPDATED_EMPLOYEE_ADDRESS_COMPLEMENT
        defaultEmployeesShouldNotBeFound("employeeAddressComplement.in=" + UPDATED_EMPLOYEE_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressComplementIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddressComplement is not null
        defaultEmployeesShouldBeFound("employeeAddressComplement.specified=true");

        // Get all the employeesList where employeeAddressComplement is null
        defaultEmployeesShouldNotBeFound("employeeAddressComplement.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressComplementContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddressComplement contains DEFAULT_EMPLOYEE_ADDRESS_COMPLEMENT
        defaultEmployeesShouldBeFound("employeeAddressComplement.contains=" + DEFAULT_EMPLOYEE_ADDRESS_COMPLEMENT);

        // Get all the employeesList where employeeAddressComplement contains UPDATED_EMPLOYEE_ADDRESS_COMPLEMENT
        defaultEmployeesShouldNotBeFound("employeeAddressComplement.contains=" + UPDATED_EMPLOYEE_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressComplementNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddressComplement does not contain DEFAULT_EMPLOYEE_ADDRESS_COMPLEMENT
        defaultEmployeesShouldNotBeFound("employeeAddressComplement.doesNotContain=" + DEFAULT_EMPLOYEE_ADDRESS_COMPLEMENT);

        // Get all the employeesList where employeeAddressComplement does not contain UPDATED_EMPLOYEE_ADDRESS_COMPLEMENT
        defaultEmployeesShouldBeFound("employeeAddressComplement.doesNotContain=" + UPDATED_EMPLOYEE_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddressNumber equals to DEFAULT_EMPLOYEE_ADDRESS_NUMBER
        defaultEmployeesShouldBeFound("employeeAddressNumber.equals=" + DEFAULT_EMPLOYEE_ADDRESS_NUMBER);

        // Get all the employeesList where employeeAddressNumber equals to UPDATED_EMPLOYEE_ADDRESS_NUMBER
        defaultEmployeesShouldNotBeFound("employeeAddressNumber.equals=" + UPDATED_EMPLOYEE_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddressNumber not equals to DEFAULT_EMPLOYEE_ADDRESS_NUMBER
        defaultEmployeesShouldNotBeFound("employeeAddressNumber.notEquals=" + DEFAULT_EMPLOYEE_ADDRESS_NUMBER);

        // Get all the employeesList where employeeAddressNumber not equals to UPDATED_EMPLOYEE_ADDRESS_NUMBER
        defaultEmployeesShouldBeFound("employeeAddressNumber.notEquals=" + UPDATED_EMPLOYEE_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressNumberIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddressNumber in DEFAULT_EMPLOYEE_ADDRESS_NUMBER or UPDATED_EMPLOYEE_ADDRESS_NUMBER
        defaultEmployeesShouldBeFound(
            "employeeAddressNumber.in=" + DEFAULT_EMPLOYEE_ADDRESS_NUMBER + "," + UPDATED_EMPLOYEE_ADDRESS_NUMBER
        );

        // Get all the employeesList where employeeAddressNumber equals to UPDATED_EMPLOYEE_ADDRESS_NUMBER
        defaultEmployeesShouldNotBeFound("employeeAddressNumber.in=" + UPDATED_EMPLOYEE_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddressNumber is not null
        defaultEmployeesShouldBeFound("employeeAddressNumber.specified=true");

        // Get all the employeesList where employeeAddressNumber is null
        defaultEmployeesShouldNotBeFound("employeeAddressNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddressNumber is greater than or equal to DEFAULT_EMPLOYEE_ADDRESS_NUMBER
        defaultEmployeesShouldBeFound("employeeAddressNumber.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_ADDRESS_NUMBER);

        // Get all the employeesList where employeeAddressNumber is greater than or equal to UPDATED_EMPLOYEE_ADDRESS_NUMBER
        defaultEmployeesShouldNotBeFound("employeeAddressNumber.greaterThanOrEqual=" + UPDATED_EMPLOYEE_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddressNumber is less than or equal to DEFAULT_EMPLOYEE_ADDRESS_NUMBER
        defaultEmployeesShouldBeFound("employeeAddressNumber.lessThanOrEqual=" + DEFAULT_EMPLOYEE_ADDRESS_NUMBER);

        // Get all the employeesList where employeeAddressNumber is less than or equal to SMALLER_EMPLOYEE_ADDRESS_NUMBER
        defaultEmployeesShouldNotBeFound("employeeAddressNumber.lessThanOrEqual=" + SMALLER_EMPLOYEE_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddressNumber is less than DEFAULT_EMPLOYEE_ADDRESS_NUMBER
        defaultEmployeesShouldNotBeFound("employeeAddressNumber.lessThan=" + DEFAULT_EMPLOYEE_ADDRESS_NUMBER);

        // Get all the employeesList where employeeAddressNumber is less than UPDATED_EMPLOYEE_ADDRESS_NUMBER
        defaultEmployeesShouldBeFound("employeeAddressNumber.lessThan=" + UPDATED_EMPLOYEE_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddressNumber is greater than DEFAULT_EMPLOYEE_ADDRESS_NUMBER
        defaultEmployeesShouldNotBeFound("employeeAddressNumber.greaterThan=" + DEFAULT_EMPLOYEE_ADDRESS_NUMBER);

        // Get all the employeesList where employeeAddressNumber is greater than SMALLER_EMPLOYEE_ADDRESS_NUMBER
        defaultEmployeesShouldBeFound("employeeAddressNumber.greaterThan=" + SMALLER_EMPLOYEE_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressNeighborhoodIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddressNeighborhood equals to DEFAULT_EMPLOYEE_ADDRESS_NEIGHBORHOOD
        defaultEmployeesShouldBeFound("employeeAddressNeighborhood.equals=" + DEFAULT_EMPLOYEE_ADDRESS_NEIGHBORHOOD);

        // Get all the employeesList where employeeAddressNeighborhood equals to UPDATED_EMPLOYEE_ADDRESS_NEIGHBORHOOD
        defaultEmployeesShouldNotBeFound("employeeAddressNeighborhood.equals=" + UPDATED_EMPLOYEE_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressNeighborhoodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddressNeighborhood not equals to DEFAULT_EMPLOYEE_ADDRESS_NEIGHBORHOOD
        defaultEmployeesShouldNotBeFound("employeeAddressNeighborhood.notEquals=" + DEFAULT_EMPLOYEE_ADDRESS_NEIGHBORHOOD);

        // Get all the employeesList where employeeAddressNeighborhood not equals to UPDATED_EMPLOYEE_ADDRESS_NEIGHBORHOOD
        defaultEmployeesShouldBeFound("employeeAddressNeighborhood.notEquals=" + UPDATED_EMPLOYEE_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressNeighborhoodIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddressNeighborhood in DEFAULT_EMPLOYEE_ADDRESS_NEIGHBORHOOD or UPDATED_EMPLOYEE_ADDRESS_NEIGHBORHOOD
        defaultEmployeesShouldBeFound(
            "employeeAddressNeighborhood.in=" + DEFAULT_EMPLOYEE_ADDRESS_NEIGHBORHOOD + "," + UPDATED_EMPLOYEE_ADDRESS_NEIGHBORHOOD
        );

        // Get all the employeesList where employeeAddressNeighborhood equals to UPDATED_EMPLOYEE_ADDRESS_NEIGHBORHOOD
        defaultEmployeesShouldNotBeFound("employeeAddressNeighborhood.in=" + UPDATED_EMPLOYEE_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressNeighborhoodIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddressNeighborhood is not null
        defaultEmployeesShouldBeFound("employeeAddressNeighborhood.specified=true");

        // Get all the employeesList where employeeAddressNeighborhood is null
        defaultEmployeesShouldNotBeFound("employeeAddressNeighborhood.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressNeighborhoodContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddressNeighborhood contains DEFAULT_EMPLOYEE_ADDRESS_NEIGHBORHOOD
        defaultEmployeesShouldBeFound("employeeAddressNeighborhood.contains=" + DEFAULT_EMPLOYEE_ADDRESS_NEIGHBORHOOD);

        // Get all the employeesList where employeeAddressNeighborhood contains UPDATED_EMPLOYEE_ADDRESS_NEIGHBORHOOD
        defaultEmployeesShouldNotBeFound("employeeAddressNeighborhood.contains=" + UPDATED_EMPLOYEE_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAddressNeighborhoodNotContainsSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeAddressNeighborhood does not contain DEFAULT_EMPLOYEE_ADDRESS_NEIGHBORHOOD
        defaultEmployeesShouldNotBeFound("employeeAddressNeighborhood.doesNotContain=" + DEFAULT_EMPLOYEE_ADDRESS_NEIGHBORHOOD);

        // Get all the employeesList where employeeAddressNeighborhood does not contain UPDATED_EMPLOYEE_ADDRESS_NEIGHBORHOOD
        defaultEmployeesShouldBeFound("employeeAddressNeighborhood.doesNotContain=" + UPDATED_EMPLOYEE_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeBirthdayIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeBirthday equals to DEFAULT_EMPLOYEE_BIRTHDAY
        defaultEmployeesShouldBeFound("employeeBirthday.equals=" + DEFAULT_EMPLOYEE_BIRTHDAY);

        // Get all the employeesList where employeeBirthday equals to UPDATED_EMPLOYEE_BIRTHDAY
        defaultEmployeesShouldNotBeFound("employeeBirthday.equals=" + UPDATED_EMPLOYEE_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeBirthdayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeBirthday not equals to DEFAULT_EMPLOYEE_BIRTHDAY
        defaultEmployeesShouldNotBeFound("employeeBirthday.notEquals=" + DEFAULT_EMPLOYEE_BIRTHDAY);

        // Get all the employeesList where employeeBirthday not equals to UPDATED_EMPLOYEE_BIRTHDAY
        defaultEmployeesShouldBeFound("employeeBirthday.notEquals=" + UPDATED_EMPLOYEE_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeBirthdayIsInShouldWork() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeBirthday in DEFAULT_EMPLOYEE_BIRTHDAY or UPDATED_EMPLOYEE_BIRTHDAY
        defaultEmployeesShouldBeFound("employeeBirthday.in=" + DEFAULT_EMPLOYEE_BIRTHDAY + "," + UPDATED_EMPLOYEE_BIRTHDAY);

        // Get all the employeesList where employeeBirthday equals to UPDATED_EMPLOYEE_BIRTHDAY
        defaultEmployeesShouldNotBeFound("employeeBirthday.in=" + UPDATED_EMPLOYEE_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeBirthdayIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeBirthday is not null
        defaultEmployeesShouldBeFound("employeeBirthday.specified=true");

        // Get all the employeesList where employeeBirthday is null
        defaultEmployeesShouldNotBeFound("employeeBirthday.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeBirthdayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeBirthday is greater than or equal to DEFAULT_EMPLOYEE_BIRTHDAY
        defaultEmployeesShouldBeFound("employeeBirthday.greaterThanOrEqual=" + DEFAULT_EMPLOYEE_BIRTHDAY);

        // Get all the employeesList where employeeBirthday is greater than or equal to UPDATED_EMPLOYEE_BIRTHDAY
        defaultEmployeesShouldNotBeFound("employeeBirthday.greaterThanOrEqual=" + UPDATED_EMPLOYEE_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeBirthdayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeBirthday is less than or equal to DEFAULT_EMPLOYEE_BIRTHDAY
        defaultEmployeesShouldBeFound("employeeBirthday.lessThanOrEqual=" + DEFAULT_EMPLOYEE_BIRTHDAY);

        // Get all the employeesList where employeeBirthday is less than or equal to SMALLER_EMPLOYEE_BIRTHDAY
        defaultEmployeesShouldNotBeFound("employeeBirthday.lessThanOrEqual=" + SMALLER_EMPLOYEE_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeBirthdayIsLessThanSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeBirthday is less than DEFAULT_EMPLOYEE_BIRTHDAY
        defaultEmployeesShouldNotBeFound("employeeBirthday.lessThan=" + DEFAULT_EMPLOYEE_BIRTHDAY);

        // Get all the employeesList where employeeBirthday is less than UPDATED_EMPLOYEE_BIRTHDAY
        defaultEmployeesShouldBeFound("employeeBirthday.lessThan=" + UPDATED_EMPLOYEE_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeBirthdayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList where employeeBirthday is greater than DEFAULT_EMPLOYEE_BIRTHDAY
        defaultEmployeesShouldNotBeFound("employeeBirthday.greaterThan=" + DEFAULT_EMPLOYEE_BIRTHDAY);

        // Get all the employeesList where employeeBirthday is greater than SMALLER_EMPLOYEE_BIRTHDAY
        defaultEmployeesShouldBeFound("employeeBirthday.greaterThan=" + SMALLER_EMPLOYEE_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeAttachmentsIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);
        EmployeeAttachments employeeAttachments = EmployeeAttachmentsResourceIT.createEntity(em);
        em.persist(employeeAttachments);
        em.flush();
        employees.addEmployeeAttachments(employeeAttachments);
        employeesRepository.saveAndFlush(employees);
        Long employeeAttachmentsId = employeeAttachments.getId();

        // Get all the employeesList where employeeAttachments equals to employeeAttachmentsId
        defaultEmployeesShouldBeFound("employeeAttachmentsId.equals=" + employeeAttachmentsId);

        // Get all the employeesList where employeeAttachments equals to (employeeAttachmentsId + 1)
        defaultEmployeesShouldNotBeFound("employeeAttachmentsId.equals=" + (employeeAttachmentsId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeComponentsDataIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);
        EmployeeComponentsData employeeComponentsData = EmployeeComponentsDataResourceIT.createEntity(em);
        em.persist(employeeComponentsData);
        em.flush();
        employees.addEmployeeComponentsData(employeeComponentsData);
        employeesRepository.saveAndFlush(employees);
        Long employeeComponentsDataId = employeeComponentsData.getId();

        // Get all the employeesList where employeeComponentsData equals to employeeComponentsDataId
        defaultEmployeesShouldBeFound("employeeComponentsDataId.equals=" + employeeComponentsDataId);

        // Get all the employeesList where employeeComponentsData equals to (employeeComponentsDataId + 1)
        defaultEmployeesShouldNotBeFound("employeeComponentsDataId.equals=" + (employeeComponentsDataId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByVehicleControlsIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);
        VehicleControls vehicleControls = VehicleControlsResourceIT.createEntity(em);
        em.persist(vehicleControls);
        em.flush();
        employees.addVehicleControls(vehicleControls);
        employeesRepository.saveAndFlush(employees);
        Long vehicleControlsId = vehicleControls.getId();

        // Get all the employeesList where vehicleControls equals to vehicleControlsId
        defaultEmployeesShouldBeFound("vehicleControlsId.equals=" + vehicleControlsId);

        // Get all the employeesList where vehicleControls equals to (vehicleControlsId + 1)
        defaultEmployeesShouldNotBeFound("vehicleControlsId.equals=" + (vehicleControlsId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByVehicleControlHistoryIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);
        VehicleControlHistory vehicleControlHistory = VehicleControlHistoryResourceIT.createEntity(em);
        em.persist(vehicleControlHistory);
        em.flush();
        employees.addVehicleControlHistory(vehicleControlHistory);
        employeesRepository.saveAndFlush(employees);
        Long vehicleControlHistoryId = vehicleControlHistory.getId();

        // Get all the employeesList where vehicleControlHistory equals to vehicleControlHistoryId
        defaultEmployeesShouldBeFound("vehicleControlHistoryId.equals=" + vehicleControlHistoryId);

        // Get all the employeesList where vehicleControlHistory equals to (vehicleControlHistoryId + 1)
        defaultEmployeesShouldNotBeFound("vehicleControlHistoryId.equals=" + (vehicleControlHistoryId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByHousingIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);
        Housing housing = HousingResourceIT.createEntity(em);
        em.persist(housing);
        em.flush();
        employees.addHousing(housing);
        employeesRepository.saveAndFlush(employees);
        Long housingId = housing.getId();

        // Get all the employeesList where housing equals to housingId
        defaultEmployeesShouldBeFound("housingId.equals=" + housingId);

        // Get all the employeesList where housing equals to (housingId + 1)
        defaultEmployeesShouldNotBeFound("housingId.equals=" + (housingId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByCompaniesIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);
        Companies companies = CompaniesResourceIT.createEntity(em);
        em.persist(companies);
        em.flush();
        employees.setCompanies(companies);
        employeesRepository.saveAndFlush(employees);
        Long companiesId = companies.getId();

        // Get all the employeesList where companies equals to companiesId
        defaultEmployeesShouldBeFound("companiesId.equals=" + companiesId);

        // Get all the employeesList where companies equals to (companiesId + 1)
        defaultEmployeesShouldNotBeFound("companiesId.equals=" + (companiesId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByAffiliatesIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);
        Affiliates affiliates = AffiliatesResourceIT.createEntity(em);
        em.persist(affiliates);
        em.flush();
        employees.setAffiliates(affiliates);
        employeesRepository.saveAndFlush(employees);
        Long affiliatesId = affiliates.getId();

        // Get all the employeesList where affiliates equals to affiliatesId
        defaultEmployeesShouldBeFound("affiliatesId.equals=" + affiliatesId);

        // Get all the employeesList where affiliates equals to (affiliatesId + 1)
        defaultEmployeesShouldNotBeFound("affiliatesId.equals=" + (affiliatesId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByCitiesIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);
        Cities cities = CitiesResourceIT.createEntity(em);
        em.persist(cities);
        em.flush();
        employees.setCities(cities);
        employeesRepository.saveAndFlush(employees);
        Long citiesId = cities.getId();

        // Get all the employeesList where cities equals to citiesId
        defaultEmployeesShouldBeFound("citiesId.equals=" + citiesId);

        // Get all the employeesList where cities equals to (citiesId + 1)
        defaultEmployeesShouldNotBeFound("citiesId.equals=" + (citiesId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByPositionsIsEqualToSomething() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);
        Positions positions = PositionsResourceIT.createEntity(em);
        em.persist(positions);
        em.flush();
        employees.setPositions(positions);
        employeesRepository.saveAndFlush(employees);
        Long positionsId = positions.getId();

        // Get all the employeesList where positions equals to positionsId
        defaultEmployeesShouldBeFound("positionsId.equals=" + positionsId);

        // Get all the employeesList where positions equals to (positionsId + 1)
        defaultEmployeesShouldNotBeFound("positionsId.equals=" + (positionsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeesShouldBeFound(String filter) throws Exception {
        restEmployeesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employees.getId().intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].employeeFullName").value(hasItem(DEFAULT_EMPLOYEE_FULL_NAME)))
            .andExpect(jsonPath("$.[*].employeeEmail").value(hasItem(DEFAULT_EMPLOYEE_EMAIL)))
            .andExpect(jsonPath("$.[*].employeeIdentificationNumber").value(hasItem(DEFAULT_EMPLOYEE_IDENTIFICATION_NUMBER)))
            .andExpect(jsonPath("$.[*].employeeNumber").value(hasItem(DEFAULT_EMPLOYEE_NUMBER)))
            .andExpect(jsonPath("$.[*].employeePostalCode").value(hasItem(DEFAULT_EMPLOYEE_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].employeeAddress").value(hasItem(DEFAULT_EMPLOYEE_ADDRESS)))
            .andExpect(jsonPath("$.[*].employeeAddressComplement").value(hasItem(DEFAULT_EMPLOYEE_ADDRESS_COMPLEMENT)))
            .andExpect(jsonPath("$.[*].employeeAddressNumber").value(hasItem(DEFAULT_EMPLOYEE_ADDRESS_NUMBER)))
            .andExpect(jsonPath("$.[*].employeeAddressNeighborhood").value(hasItem(DEFAULT_EMPLOYEE_ADDRESS_NEIGHBORHOOD)))
            .andExpect(jsonPath("$.[*].employeeBirthday").value(hasItem(DEFAULT_EMPLOYEE_BIRTHDAY.toString())));

        // Check, that the count call also returns 1
        restEmployeesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeesShouldNotBeFound(String filter) throws Exception {
        restEmployeesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployees() throws Exception {
        // Get the employees
        restEmployeesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();

        // Update the employees
        Employees updatedEmployees = employeesRepository.findById(employees.getId()).get();
        // Disconnect from session so that the updates on updatedEmployees are not directly saved in db
        em.detach(updatedEmployees);
        updatedEmployees
            .active(UPDATED_ACTIVE)
            .employeeFullName(UPDATED_EMPLOYEE_FULL_NAME)
            .employeeEmail(UPDATED_EMPLOYEE_EMAIL)
            .employeeIdentificationNumber(UPDATED_EMPLOYEE_IDENTIFICATION_NUMBER)
            .employeeNumber(UPDATED_EMPLOYEE_NUMBER)
            .employeePostalCode(UPDATED_EMPLOYEE_POSTAL_CODE)
            .employeeAddress(UPDATED_EMPLOYEE_ADDRESS)
            .employeeAddressComplement(UPDATED_EMPLOYEE_ADDRESS_COMPLEMENT)
            .employeeAddressNumber(UPDATED_EMPLOYEE_ADDRESS_NUMBER)
            .employeeAddressNeighborhood(UPDATED_EMPLOYEE_ADDRESS_NEIGHBORHOOD)
            .employeeBirthday(UPDATED_EMPLOYEE_BIRTHDAY);
        EmployeesDTO employeesDTO = employeesMapper.toDto(updatedEmployees);

        restEmployeesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
        Employees testEmployees = employeesList.get(employeesList.size() - 1);
        assertThat(testEmployees.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testEmployees.getEmployeeFullName()).isEqualTo(UPDATED_EMPLOYEE_FULL_NAME);
        assertThat(testEmployees.getEmployeeEmail()).isEqualTo(UPDATED_EMPLOYEE_EMAIL);
        assertThat(testEmployees.getEmployeeIdentificationNumber()).isEqualTo(UPDATED_EMPLOYEE_IDENTIFICATION_NUMBER);
        assertThat(testEmployees.getEmployeeNumber()).isEqualTo(UPDATED_EMPLOYEE_NUMBER);
        assertThat(testEmployees.getEmployeePostalCode()).isEqualTo(UPDATED_EMPLOYEE_POSTAL_CODE);
        assertThat(testEmployees.getEmployeeAddress()).isEqualTo(UPDATED_EMPLOYEE_ADDRESS);
        assertThat(testEmployees.getEmployeeAddressComplement()).isEqualTo(UPDATED_EMPLOYEE_ADDRESS_COMPLEMENT);
        assertThat(testEmployees.getEmployeeAddressNumber()).isEqualTo(UPDATED_EMPLOYEE_ADDRESS_NUMBER);
        assertThat(testEmployees.getEmployeeAddressNeighborhood()).isEqualTo(UPDATED_EMPLOYEE_ADDRESS_NEIGHBORHOOD);
        assertThat(testEmployees.getEmployeeBirthday()).isEqualTo(UPDATED_EMPLOYEE_BIRTHDAY);
    }

    @Test
    @Transactional
    void putNonExistingEmployees() throws Exception {
        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();
        employees.setId(count.incrementAndGet());

        // Create the Employees
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployees() throws Exception {
        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();
        employees.setId(count.incrementAndGet());

        // Create the Employees
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployees() throws Exception {
        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();
        employees.setId(count.incrementAndGet());

        // Create the Employees
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeesWithPatch() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();

        // Update the employees using partial update
        Employees partialUpdatedEmployees = new Employees();
        partialUpdatedEmployees.setId(employees.getId());

        partialUpdatedEmployees
            .active(UPDATED_ACTIVE)
            .employeeFullName(UPDATED_EMPLOYEE_FULL_NAME)
            .employeeIdentificationNumber(UPDATED_EMPLOYEE_IDENTIFICATION_NUMBER)
            .employeePostalCode(UPDATED_EMPLOYEE_POSTAL_CODE)
            .employeeAddressComplement(UPDATED_EMPLOYEE_ADDRESS_COMPLEMENT)
            .employeeBirthday(UPDATED_EMPLOYEE_BIRTHDAY);

        restEmployeesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployees.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployees))
            )
            .andExpect(status().isOk());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
        Employees testEmployees = employeesList.get(employeesList.size() - 1);
        assertThat(testEmployees.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testEmployees.getEmployeeFullName()).isEqualTo(UPDATED_EMPLOYEE_FULL_NAME);
        assertThat(testEmployees.getEmployeeEmail()).isEqualTo(DEFAULT_EMPLOYEE_EMAIL);
        assertThat(testEmployees.getEmployeeIdentificationNumber()).isEqualTo(UPDATED_EMPLOYEE_IDENTIFICATION_NUMBER);
        assertThat(testEmployees.getEmployeeNumber()).isEqualTo(DEFAULT_EMPLOYEE_NUMBER);
        assertThat(testEmployees.getEmployeePostalCode()).isEqualTo(UPDATED_EMPLOYEE_POSTAL_CODE);
        assertThat(testEmployees.getEmployeeAddress()).isEqualTo(DEFAULT_EMPLOYEE_ADDRESS);
        assertThat(testEmployees.getEmployeeAddressComplement()).isEqualTo(UPDATED_EMPLOYEE_ADDRESS_COMPLEMENT);
        assertThat(testEmployees.getEmployeeAddressNumber()).isEqualTo(DEFAULT_EMPLOYEE_ADDRESS_NUMBER);
        assertThat(testEmployees.getEmployeeAddressNeighborhood()).isEqualTo(DEFAULT_EMPLOYEE_ADDRESS_NEIGHBORHOOD);
        assertThat(testEmployees.getEmployeeBirthday()).isEqualTo(UPDATED_EMPLOYEE_BIRTHDAY);
    }

    @Test
    @Transactional
    void fullUpdateEmployeesWithPatch() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();

        // Update the employees using partial update
        Employees partialUpdatedEmployees = new Employees();
        partialUpdatedEmployees.setId(employees.getId());

        partialUpdatedEmployees
            .active(UPDATED_ACTIVE)
            .employeeFullName(UPDATED_EMPLOYEE_FULL_NAME)
            .employeeEmail(UPDATED_EMPLOYEE_EMAIL)
            .employeeIdentificationNumber(UPDATED_EMPLOYEE_IDENTIFICATION_NUMBER)
            .employeeNumber(UPDATED_EMPLOYEE_NUMBER)
            .employeePostalCode(UPDATED_EMPLOYEE_POSTAL_CODE)
            .employeeAddress(UPDATED_EMPLOYEE_ADDRESS)
            .employeeAddressComplement(UPDATED_EMPLOYEE_ADDRESS_COMPLEMENT)
            .employeeAddressNumber(UPDATED_EMPLOYEE_ADDRESS_NUMBER)
            .employeeAddressNeighborhood(UPDATED_EMPLOYEE_ADDRESS_NEIGHBORHOOD)
            .employeeBirthday(UPDATED_EMPLOYEE_BIRTHDAY);

        restEmployeesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployees.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployees))
            )
            .andExpect(status().isOk());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
        Employees testEmployees = employeesList.get(employeesList.size() - 1);
        assertThat(testEmployees.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testEmployees.getEmployeeFullName()).isEqualTo(UPDATED_EMPLOYEE_FULL_NAME);
        assertThat(testEmployees.getEmployeeEmail()).isEqualTo(UPDATED_EMPLOYEE_EMAIL);
        assertThat(testEmployees.getEmployeeIdentificationNumber()).isEqualTo(UPDATED_EMPLOYEE_IDENTIFICATION_NUMBER);
        assertThat(testEmployees.getEmployeeNumber()).isEqualTo(UPDATED_EMPLOYEE_NUMBER);
        assertThat(testEmployees.getEmployeePostalCode()).isEqualTo(UPDATED_EMPLOYEE_POSTAL_CODE);
        assertThat(testEmployees.getEmployeeAddress()).isEqualTo(UPDATED_EMPLOYEE_ADDRESS);
        assertThat(testEmployees.getEmployeeAddressComplement()).isEqualTo(UPDATED_EMPLOYEE_ADDRESS_COMPLEMENT);
        assertThat(testEmployees.getEmployeeAddressNumber()).isEqualTo(UPDATED_EMPLOYEE_ADDRESS_NUMBER);
        assertThat(testEmployees.getEmployeeAddressNeighborhood()).isEqualTo(UPDATED_EMPLOYEE_ADDRESS_NEIGHBORHOOD);
        assertThat(testEmployees.getEmployeeBirthday()).isEqualTo(UPDATED_EMPLOYEE_BIRTHDAY);
    }

    @Test
    @Transactional
    void patchNonExistingEmployees() throws Exception {
        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();
        employees.setId(count.incrementAndGet());

        // Create the Employees
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployees() throws Exception {
        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();
        employees.setId(count.incrementAndGet());

        // Create the Employees
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployees() throws Exception {
        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();
        employees.setId(count.incrementAndGet());

        // Create the Employees
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(employeesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        int databaseSizeBeforeDelete = employeesRepository.findAll().size();

        // Delete the employees
        restEmployeesMockMvc
            .perform(delete(ENTITY_API_URL_ID, employees.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
