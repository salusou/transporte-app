package com.genesisoft.transporte.web.rest;

import static com.genesisoft.transporte.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Affiliates;
import com.genesisoft.transporte.domain.Cities;
import com.genesisoft.transporte.domain.Companies;
import com.genesisoft.transporte.domain.Customers;
import com.genesisoft.transporte.domain.Employees;
import com.genesisoft.transporte.domain.Housing;
import com.genesisoft.transporte.domain.Parking;
import com.genesisoft.transporte.domain.StateProvinces;
import com.genesisoft.transporte.domain.Suppliers;
import com.genesisoft.transporte.domain.VehicleControlExpenses;
import com.genesisoft.transporte.domain.VehicleControls;
import com.genesisoft.transporte.domain.VehicleLocationStatus;
import com.genesisoft.transporte.repository.CitiesRepository;
import com.genesisoft.transporte.service.criteria.CitiesCriteria;
import com.genesisoft.transporte.service.dto.CitiesDTO;
import com.genesisoft.transporte.service.mapper.CitiesMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link CitiesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CitiesResourceIT {

    private static final String DEFAULT_CITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CITY_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_LATITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LATITUDE = new BigDecimal(2);
    private static final BigDecimal SMALLER_LATITUDE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_LONGITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LONGITUDE = new BigDecimal(2);
    private static final BigDecimal SMALLER_LONGITUDE = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/cities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private CitiesMapper citiesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCitiesMockMvc;

    private Cities cities;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cities createEntity(EntityManager em) {
        Cities cities = new Cities().cityName(DEFAULT_CITY_NAME).latitude(DEFAULT_LATITUDE).longitude(DEFAULT_LONGITUDE);
        // Add required entity
        StateProvinces stateProvinces;
        if (TestUtil.findAll(em, StateProvinces.class).isEmpty()) {
            stateProvinces = StateProvincesResourceIT.createEntity(em);
            em.persist(stateProvinces);
            em.flush();
        } else {
            stateProvinces = TestUtil.findAll(em, StateProvinces.class).get(0);
        }
        cities.setStateProvinces(stateProvinces);
        return cities;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cities createUpdatedEntity(EntityManager em) {
        Cities cities = new Cities().cityName(UPDATED_CITY_NAME).latitude(UPDATED_LATITUDE).longitude(UPDATED_LONGITUDE);
        // Add required entity
        StateProvinces stateProvinces;
        if (TestUtil.findAll(em, StateProvinces.class).isEmpty()) {
            stateProvinces = StateProvincesResourceIT.createUpdatedEntity(em);
            em.persist(stateProvinces);
            em.flush();
        } else {
            stateProvinces = TestUtil.findAll(em, StateProvinces.class).get(0);
        }
        cities.setStateProvinces(stateProvinces);
        return cities;
    }

    @BeforeEach
    public void initTest() {
        cities = createEntity(em);
    }

    @Test
    @Transactional
    void createCities() throws Exception {
        int databaseSizeBeforeCreate = citiesRepository.findAll().size();
        // Create the Cities
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);
        restCitiesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citiesDTO)))
            .andExpect(status().isCreated());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeCreate + 1);
        Cities testCities = citiesList.get(citiesList.size() - 1);
        assertThat(testCities.getCityName()).isEqualTo(DEFAULT_CITY_NAME);
        assertThat(testCities.getLatitude()).isEqualByComparingTo(DEFAULT_LATITUDE);
        assertThat(testCities.getLongitude()).isEqualByComparingTo(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    void createCitiesWithExistingId() throws Exception {
        // Create the Cities with an existing ID
        cities.setId(1L);
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        int databaseSizeBeforeCreate = citiesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitiesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citiesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCityNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = citiesRepository.findAll().size();
        // set the field null
        cities.setCityName(null);

        // Create the Cities, which fails.
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        restCitiesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citiesDTO)))
            .andExpect(status().isBadRequest());

        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList
        restCitiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cities.getId().intValue())))
            .andExpect(jsonPath("$.[*].cityName").value(hasItem(DEFAULT_CITY_NAME)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(sameNumber(DEFAULT_LATITUDE))))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(sameNumber(DEFAULT_LONGITUDE))));
    }

    @Test
    @Transactional
    void getCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get the cities
        restCitiesMockMvc
            .perform(get(ENTITY_API_URL_ID, cities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cities.getId().intValue()))
            .andExpect(jsonPath("$.cityName").value(DEFAULT_CITY_NAME))
            .andExpect(jsonPath("$.latitude").value(sameNumber(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.longitude").value(sameNumber(DEFAULT_LONGITUDE)));
    }

    @Test
    @Transactional
    void getCitiesByIdFiltering() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        Long id = cities.getId();

        defaultCitiesShouldBeFound("id.equals=" + id);
        defaultCitiesShouldNotBeFound("id.notEquals=" + id);

        defaultCitiesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCitiesShouldNotBeFound("id.greaterThan=" + id);

        defaultCitiesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCitiesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCitiesByCityNameIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where cityName equals to DEFAULT_CITY_NAME
        defaultCitiesShouldBeFound("cityName.equals=" + DEFAULT_CITY_NAME);

        // Get all the citiesList where cityName equals to UPDATED_CITY_NAME
        defaultCitiesShouldNotBeFound("cityName.equals=" + UPDATED_CITY_NAME);
    }

    @Test
    @Transactional
    void getAllCitiesByCityNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where cityName not equals to DEFAULT_CITY_NAME
        defaultCitiesShouldNotBeFound("cityName.notEquals=" + DEFAULT_CITY_NAME);

        // Get all the citiesList where cityName not equals to UPDATED_CITY_NAME
        defaultCitiesShouldBeFound("cityName.notEquals=" + UPDATED_CITY_NAME);
    }

    @Test
    @Transactional
    void getAllCitiesByCityNameIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where cityName in DEFAULT_CITY_NAME or UPDATED_CITY_NAME
        defaultCitiesShouldBeFound("cityName.in=" + DEFAULT_CITY_NAME + "," + UPDATED_CITY_NAME);

        // Get all the citiesList where cityName equals to UPDATED_CITY_NAME
        defaultCitiesShouldNotBeFound("cityName.in=" + UPDATED_CITY_NAME);
    }

    @Test
    @Transactional
    void getAllCitiesByCityNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where cityName is not null
        defaultCitiesShouldBeFound("cityName.specified=true");

        // Get all the citiesList where cityName is null
        defaultCitiesShouldNotBeFound("cityName.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByCityNameContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where cityName contains DEFAULT_CITY_NAME
        defaultCitiesShouldBeFound("cityName.contains=" + DEFAULT_CITY_NAME);

        // Get all the citiesList where cityName contains UPDATED_CITY_NAME
        defaultCitiesShouldNotBeFound("cityName.contains=" + UPDATED_CITY_NAME);
    }

    @Test
    @Transactional
    void getAllCitiesByCityNameNotContainsSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where cityName does not contain DEFAULT_CITY_NAME
        defaultCitiesShouldNotBeFound("cityName.doesNotContain=" + DEFAULT_CITY_NAME);

        // Get all the citiesList where cityName does not contain UPDATED_CITY_NAME
        defaultCitiesShouldBeFound("cityName.doesNotContain=" + UPDATED_CITY_NAME);
    }

    @Test
    @Transactional
    void getAllCitiesByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where latitude equals to DEFAULT_LATITUDE
        defaultCitiesShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the citiesList where latitude equals to UPDATED_LATITUDE
        defaultCitiesShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllCitiesByLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where latitude not equals to DEFAULT_LATITUDE
        defaultCitiesShouldNotBeFound("latitude.notEquals=" + DEFAULT_LATITUDE);

        // Get all the citiesList where latitude not equals to UPDATED_LATITUDE
        defaultCitiesShouldBeFound("latitude.notEquals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllCitiesByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultCitiesShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the citiesList where latitude equals to UPDATED_LATITUDE
        defaultCitiesShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllCitiesByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where latitude is not null
        defaultCitiesShouldBeFound("latitude.specified=true");

        // Get all the citiesList where latitude is null
        defaultCitiesShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByLatitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where latitude is greater than or equal to DEFAULT_LATITUDE
        defaultCitiesShouldBeFound("latitude.greaterThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the citiesList where latitude is greater than or equal to UPDATED_LATITUDE
        defaultCitiesShouldNotBeFound("latitude.greaterThanOrEqual=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllCitiesByLatitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where latitude is less than or equal to DEFAULT_LATITUDE
        defaultCitiesShouldBeFound("latitude.lessThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the citiesList where latitude is less than or equal to SMALLER_LATITUDE
        defaultCitiesShouldNotBeFound("latitude.lessThanOrEqual=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    void getAllCitiesByLatitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where latitude is less than DEFAULT_LATITUDE
        defaultCitiesShouldNotBeFound("latitude.lessThan=" + DEFAULT_LATITUDE);

        // Get all the citiesList where latitude is less than UPDATED_LATITUDE
        defaultCitiesShouldBeFound("latitude.lessThan=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllCitiesByLatitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where latitude is greater than DEFAULT_LATITUDE
        defaultCitiesShouldNotBeFound("latitude.greaterThan=" + DEFAULT_LATITUDE);

        // Get all the citiesList where latitude is greater than SMALLER_LATITUDE
        defaultCitiesShouldBeFound("latitude.greaterThan=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    void getAllCitiesByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where longitude equals to DEFAULT_LONGITUDE
        defaultCitiesShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the citiesList where longitude equals to UPDATED_LONGITUDE
        defaultCitiesShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllCitiesByLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where longitude not equals to DEFAULT_LONGITUDE
        defaultCitiesShouldNotBeFound("longitude.notEquals=" + DEFAULT_LONGITUDE);

        // Get all the citiesList where longitude not equals to UPDATED_LONGITUDE
        defaultCitiesShouldBeFound("longitude.notEquals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllCitiesByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultCitiesShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the citiesList where longitude equals to UPDATED_LONGITUDE
        defaultCitiesShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllCitiesByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where longitude is not null
        defaultCitiesShouldBeFound("longitude.specified=true");

        // Get all the citiesList where longitude is null
        defaultCitiesShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    void getAllCitiesByLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where longitude is greater than or equal to DEFAULT_LONGITUDE
        defaultCitiesShouldBeFound("longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the citiesList where longitude is greater than or equal to UPDATED_LONGITUDE
        defaultCitiesShouldNotBeFound("longitude.greaterThanOrEqual=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllCitiesByLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where longitude is less than or equal to DEFAULT_LONGITUDE
        defaultCitiesShouldBeFound("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the citiesList where longitude is less than or equal to SMALLER_LONGITUDE
        defaultCitiesShouldNotBeFound("longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllCitiesByLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where longitude is less than DEFAULT_LONGITUDE
        defaultCitiesShouldNotBeFound("longitude.lessThan=" + DEFAULT_LONGITUDE);

        // Get all the citiesList where longitude is less than UPDATED_LONGITUDE
        defaultCitiesShouldBeFound("longitude.lessThan=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllCitiesByLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        // Get all the citiesList where longitude is greater than DEFAULT_LONGITUDE
        defaultCitiesShouldNotBeFound("longitude.greaterThan=" + DEFAULT_LONGITUDE);

        // Get all the citiesList where longitude is greater than SMALLER_LONGITUDE
        defaultCitiesShouldBeFound("longitude.greaterThan=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllCitiesByCompaniesIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);
        Companies companies = CompaniesResourceIT.createEntity(em);
        em.persist(companies);
        em.flush();
        cities.addCompanies(companies);
        citiesRepository.saveAndFlush(cities);
        Long companiesId = companies.getId();

        // Get all the citiesList where companies equals to companiesId
        defaultCitiesShouldBeFound("companiesId.equals=" + companiesId);

        // Get all the citiesList where companies equals to (companiesId + 1)
        defaultCitiesShouldNotBeFound("companiesId.equals=" + (companiesId + 1));
    }

    @Test
    @Transactional
    void getAllCitiesByAffiliatesIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);
        Affiliates affiliates = AffiliatesResourceIT.createEntity(em);
        em.persist(affiliates);
        em.flush();
        cities.addAffiliates(affiliates);
        citiesRepository.saveAndFlush(cities);
        Long affiliatesId = affiliates.getId();

        // Get all the citiesList where affiliates equals to affiliatesId
        defaultCitiesShouldBeFound("affiliatesId.equals=" + affiliatesId);

        // Get all the citiesList where affiliates equals to (affiliatesId + 1)
        defaultCitiesShouldNotBeFound("affiliatesId.equals=" + (affiliatesId + 1));
    }

    @Test
    @Transactional
    void getAllCitiesByCustomersIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);
        Customers customers = CustomersResourceIT.createEntity(em);
        em.persist(customers);
        em.flush();
        cities.addCustomers(customers);
        citiesRepository.saveAndFlush(cities);
        Long customersId = customers.getId();

        // Get all the citiesList where customers equals to customersId
        defaultCitiesShouldBeFound("customersId.equals=" + customersId);

        // Get all the citiesList where customers equals to (customersId + 1)
        defaultCitiesShouldNotBeFound("customersId.equals=" + (customersId + 1));
    }

    @Test
    @Transactional
    void getAllCitiesByParkingIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);
        Parking parking = ParkingResourceIT.createEntity(em);
        em.persist(parking);
        em.flush();
        cities.addParking(parking);
        citiesRepository.saveAndFlush(cities);
        Long parkingId = parking.getId();

        // Get all the citiesList where parking equals to parkingId
        defaultCitiesShouldBeFound("parkingId.equals=" + parkingId);

        // Get all the citiesList where parking equals to (parkingId + 1)
        defaultCitiesShouldNotBeFound("parkingId.equals=" + (parkingId + 1));
    }

    @Test
    @Transactional
    void getAllCitiesBySuppliersIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);
        Suppliers suppliers = SuppliersResourceIT.createEntity(em);
        em.persist(suppliers);
        em.flush();
        cities.addSuppliers(suppliers);
        citiesRepository.saveAndFlush(cities);
        Long suppliersId = suppliers.getId();

        // Get all the citiesList where suppliers equals to suppliersId
        defaultCitiesShouldBeFound("suppliersId.equals=" + suppliersId);

        // Get all the citiesList where suppliers equals to (suppliersId + 1)
        defaultCitiesShouldNotBeFound("suppliersId.equals=" + (suppliersId + 1));
    }

    @Test
    @Transactional
    void getAllCitiesByEmployeesIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);
        Employees employees = EmployeesResourceIT.createEntity(em);
        em.persist(employees);
        em.flush();
        cities.addEmployees(employees);
        citiesRepository.saveAndFlush(cities);
        Long employeesId = employees.getId();

        // Get all the citiesList where employees equals to employeesId
        defaultCitiesShouldBeFound("employeesId.equals=" + employeesId);

        // Get all the citiesList where employees equals to (employeesId + 1)
        defaultCitiesShouldNotBeFound("employeesId.equals=" + (employeesId + 1));
    }

    @Test
    @Transactional
    void getAllCitiesByOriginVehicleControlsIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);
        VehicleControls originVehicleControls = VehicleControlsResourceIT.createEntity(em);
        em.persist(originVehicleControls);
        em.flush();
        cities.addOriginVehicleControls(originVehicleControls);
        citiesRepository.saveAndFlush(cities);
        Long originVehicleControlsId = originVehicleControls.getId();

        // Get all the citiesList where originVehicleControls equals to originVehicleControlsId
        defaultCitiesShouldBeFound("originVehicleControlsId.equals=" + originVehicleControlsId);

        // Get all the citiesList where originVehicleControls equals to (originVehicleControlsId + 1)
        defaultCitiesShouldNotBeFound("originVehicleControlsId.equals=" + (originVehicleControlsId + 1));
    }

    @Test
    @Transactional
    void getAllCitiesByDestinationVehicleControlsIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);
        VehicleControls destinationVehicleControls = VehicleControlsResourceIT.createEntity(em);
        em.persist(destinationVehicleControls);
        em.flush();
        cities.addDestinationVehicleControls(destinationVehicleControls);
        citiesRepository.saveAndFlush(cities);
        Long destinationVehicleControlsId = destinationVehicleControls.getId();

        // Get all the citiesList where destinationVehicleControls equals to destinationVehicleControlsId
        defaultCitiesShouldBeFound("destinationVehicleControlsId.equals=" + destinationVehicleControlsId);

        // Get all the citiesList where destinationVehicleControls equals to (destinationVehicleControlsId + 1)
        defaultCitiesShouldNotBeFound("destinationVehicleControlsId.equals=" + (destinationVehicleControlsId + 1));
    }

    @Test
    @Transactional
    void getAllCitiesByVehicleLocationStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);
        VehicleLocationStatus vehicleLocationStatus = VehicleLocationStatusResourceIT.createEntity(em);
        em.persist(vehicleLocationStatus);
        em.flush();
        cities.addVehicleLocationStatus(vehicleLocationStatus);
        citiesRepository.saveAndFlush(cities);
        Long vehicleLocationStatusId = vehicleLocationStatus.getId();

        // Get all the citiesList where vehicleLocationStatus equals to vehicleLocationStatusId
        defaultCitiesShouldBeFound("vehicleLocationStatusId.equals=" + vehicleLocationStatusId);

        // Get all the citiesList where vehicleLocationStatus equals to (vehicleLocationStatusId + 1)
        defaultCitiesShouldNotBeFound("vehicleLocationStatusId.equals=" + (vehicleLocationStatusId + 1));
    }

    @Test
    @Transactional
    void getAllCitiesByOriginVehicleControlExpensesIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);
        VehicleControlExpenses originVehicleControlExpenses = VehicleControlExpensesResourceIT.createEntity(em);
        em.persist(originVehicleControlExpenses);
        em.flush();
        cities.addOriginVehicleControlExpenses(originVehicleControlExpenses);
        citiesRepository.saveAndFlush(cities);
        Long originVehicleControlExpensesId = originVehicleControlExpenses.getId();

        // Get all the citiesList where originVehicleControlExpenses equals to originVehicleControlExpensesId
        defaultCitiesShouldBeFound("originVehicleControlExpensesId.equals=" + originVehicleControlExpensesId);

        // Get all the citiesList where originVehicleControlExpenses equals to (originVehicleControlExpensesId + 1)
        defaultCitiesShouldNotBeFound("originVehicleControlExpensesId.equals=" + (originVehicleControlExpensesId + 1));
    }

    @Test
    @Transactional
    void getAllCitiesByDestinationVehicleControlExpensesIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);
        VehicleControlExpenses destinationVehicleControlExpenses = VehicleControlExpensesResourceIT.createEntity(em);
        em.persist(destinationVehicleControlExpenses);
        em.flush();
        cities.addDestinationVehicleControlExpenses(destinationVehicleControlExpenses);
        citiesRepository.saveAndFlush(cities);
        Long destinationVehicleControlExpensesId = destinationVehicleControlExpenses.getId();

        // Get all the citiesList where destinationVehicleControlExpenses equals to destinationVehicleControlExpensesId
        defaultCitiesShouldBeFound("destinationVehicleControlExpensesId.equals=" + destinationVehicleControlExpensesId);

        // Get all the citiesList where destinationVehicleControlExpenses equals to (destinationVehicleControlExpensesId + 1)
        defaultCitiesShouldNotBeFound("destinationVehicleControlExpensesId.equals=" + (destinationVehicleControlExpensesId + 1));
    }

    @Test
    @Transactional
    void getAllCitiesByHousingIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);
        Housing housing = HousingResourceIT.createEntity(em);
        em.persist(housing);
        em.flush();
        cities.addHousing(housing);
        citiesRepository.saveAndFlush(cities);
        Long housingId = housing.getId();

        // Get all the citiesList where housing equals to housingId
        defaultCitiesShouldBeFound("housingId.equals=" + housingId);

        // Get all the citiesList where housing equals to (housingId + 1)
        defaultCitiesShouldNotBeFound("housingId.equals=" + (housingId + 1));
    }

    @Test
    @Transactional
    void getAllCitiesByStateProvincesIsEqualToSomething() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);
        StateProvinces stateProvinces = StateProvincesResourceIT.createEntity(em);
        em.persist(stateProvinces);
        em.flush();
        cities.setStateProvinces(stateProvinces);
        citiesRepository.saveAndFlush(cities);
        Long stateProvincesId = stateProvinces.getId();

        // Get all the citiesList where stateProvinces equals to stateProvincesId
        defaultCitiesShouldBeFound("stateProvincesId.equals=" + stateProvincesId);

        // Get all the citiesList where stateProvinces equals to (stateProvincesId + 1)
        defaultCitiesShouldNotBeFound("stateProvincesId.equals=" + (stateProvincesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCitiesShouldBeFound(String filter) throws Exception {
        restCitiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cities.getId().intValue())))
            .andExpect(jsonPath("$.[*].cityName").value(hasItem(DEFAULT_CITY_NAME)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(sameNumber(DEFAULT_LATITUDE))))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(sameNumber(DEFAULT_LONGITUDE))));

        // Check, that the count call also returns 1
        restCitiesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCitiesShouldNotBeFound(String filter) throws Exception {
        restCitiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCitiesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCities() throws Exception {
        // Get the cities
        restCitiesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();

        // Update the cities
        Cities updatedCities = citiesRepository.findById(cities.getId()).get();
        // Disconnect from session so that the updates on updatedCities are not directly saved in db
        em.detach(updatedCities);
        updatedCities.cityName(UPDATED_CITY_NAME).latitude(UPDATED_LATITUDE).longitude(UPDATED_LONGITUDE);
        CitiesDTO citiesDTO = citiesMapper.toDto(updatedCities);

        restCitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, citiesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citiesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
        Cities testCities = citiesList.get(citiesList.size() - 1);
        assertThat(testCities.getCityName()).isEqualTo(UPDATED_CITY_NAME);
        assertThat(testCities.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testCities.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void putNonExistingCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(count.incrementAndGet());

        // Create the Cities
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, citiesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(count.incrementAndGet());

        // Create the Cities
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(count.incrementAndGet());

        // Create the Cities
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citiesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCitiesWithPatch() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();

        // Update the cities using partial update
        Cities partialUpdatedCities = new Cities();
        partialUpdatedCities.setId(cities.getId());

        partialUpdatedCities.latitude(UPDATED_LATITUDE).longitude(UPDATED_LONGITUDE);

        restCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCities.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCities))
            )
            .andExpect(status().isOk());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
        Cities testCities = citiesList.get(citiesList.size() - 1);
        assertThat(testCities.getCityName()).isEqualTo(DEFAULT_CITY_NAME);
        assertThat(testCities.getLatitude()).isEqualByComparingTo(UPDATED_LATITUDE);
        assertThat(testCities.getLongitude()).isEqualByComparingTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void fullUpdateCitiesWithPatch() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();

        // Update the cities using partial update
        Cities partialUpdatedCities = new Cities();
        partialUpdatedCities.setId(cities.getId());

        partialUpdatedCities.cityName(UPDATED_CITY_NAME).latitude(UPDATED_LATITUDE).longitude(UPDATED_LONGITUDE);

        restCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCities.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCities))
            )
            .andExpect(status().isOk());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
        Cities testCities = citiesList.get(citiesList.size() - 1);
        assertThat(testCities.getCityName()).isEqualTo(UPDATED_CITY_NAME);
        assertThat(testCities.getLatitude()).isEqualByComparingTo(UPDATED_LATITUDE);
        assertThat(testCities.getLongitude()).isEqualByComparingTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void patchNonExistingCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(count.incrementAndGet());

        // Create the Cities
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, citiesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(count.incrementAndGet());

        // Create the Cities
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(count.incrementAndGet());

        // Create the Cities
        CitiesDTO citiesDTO = citiesMapper.toDto(cities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(citiesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCities() throws Exception {
        // Initialize the database
        citiesRepository.saveAndFlush(cities);

        int databaseSizeBeforeDelete = citiesRepository.findAll().size();

        // Delete the cities
        restCitiesMockMvc
            .perform(delete(ENTITY_API_URL_ID, cities.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
