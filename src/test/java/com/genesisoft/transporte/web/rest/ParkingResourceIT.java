package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Affiliates;
import com.genesisoft.transporte.domain.Cities;
import com.genesisoft.transporte.domain.Housing;
import com.genesisoft.transporte.domain.Parking;
import com.genesisoft.transporte.domain.ParkingSector;
import com.genesisoft.transporte.repository.ParkingRepository;
import com.genesisoft.transporte.service.criteria.ParkingCriteria;
import com.genesisoft.transporte.service.dto.ParkingDTO;
import com.genesisoft.transporte.service.mapper.ParkingMapper;
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
 * Integration tests for the {@link ParkingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParkingResourceIT {

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_PARKING_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PARKING_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PARKING_TRADE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PARKING_TRADE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PARKING_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PARKING_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PARKING_POSTAL_CODE = "AAAAAAAAA";
    private static final String UPDATED_PARKING_POSTAL_CODE = "BBBBBBBBB";

    private static final String DEFAULT_PARKING_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_PARKING_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PARKING_ADDRESS_COMPLEMENT = "AAAAAAAAAA";
    private static final String UPDATED_PARKING_ADDRESS_COMPLEMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_PARKING_ADDRESS_NUMBER = 1;
    private static final Integer UPDATED_PARKING_ADDRESS_NUMBER = 2;
    private static final Integer SMALLER_PARKING_ADDRESS_NUMBER = 1 - 1;

    private static final String DEFAULT_PARKING_ADDRESS_NEIGHBORHOOD = "AAAAAAAAAA";
    private static final String UPDATED_PARKING_ADDRESS_NEIGHBORHOOD = "BBBBBBBBBB";

    private static final String DEFAULT_PARKING_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_PARKING_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_PARKING_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_PARKING_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PARKING_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PARKING_CONTACT_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/parkings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private ParkingMapper parkingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParkingMockMvc;

    private Parking parking;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parking createEntity(EntityManager em) {
        Parking parking = new Parking()
            .active(DEFAULT_ACTIVE)
            .parkingName(DEFAULT_PARKING_NAME)
            .parkingTradeName(DEFAULT_PARKING_TRADE_NAME)
            .parkingNumber(DEFAULT_PARKING_NUMBER)
            .parkingPostalCode(DEFAULT_PARKING_POSTAL_CODE)
            .parkingAddress(DEFAULT_PARKING_ADDRESS)
            .parkingAddressComplement(DEFAULT_PARKING_ADDRESS_COMPLEMENT)
            .parkingAddressNumber(DEFAULT_PARKING_ADDRESS_NUMBER)
            .parkingAddressNeighborhood(DEFAULT_PARKING_ADDRESS_NEIGHBORHOOD)
            .parkingTelephone(DEFAULT_PARKING_TELEPHONE)
            .parkingEmail(DEFAULT_PARKING_EMAIL)
            .parkingContactName(DEFAULT_PARKING_CONTACT_NAME);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        parking.setAffiliates(affiliates);
        // Add required entity
        Cities cities;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            cities = CitiesResourceIT.createEntity(em);
            em.persist(cities);
            em.flush();
        } else {
            cities = TestUtil.findAll(em, Cities.class).get(0);
        }
        parking.setCities(cities);
        return parking;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parking createUpdatedEntity(EntityManager em) {
        Parking parking = new Parking()
            .active(UPDATED_ACTIVE)
            .parkingName(UPDATED_PARKING_NAME)
            .parkingTradeName(UPDATED_PARKING_TRADE_NAME)
            .parkingNumber(UPDATED_PARKING_NUMBER)
            .parkingPostalCode(UPDATED_PARKING_POSTAL_CODE)
            .parkingAddress(UPDATED_PARKING_ADDRESS)
            .parkingAddressComplement(UPDATED_PARKING_ADDRESS_COMPLEMENT)
            .parkingAddressNumber(UPDATED_PARKING_ADDRESS_NUMBER)
            .parkingAddressNeighborhood(UPDATED_PARKING_ADDRESS_NEIGHBORHOOD)
            .parkingTelephone(UPDATED_PARKING_TELEPHONE)
            .parkingEmail(UPDATED_PARKING_EMAIL)
            .parkingContactName(UPDATED_PARKING_CONTACT_NAME);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createUpdatedEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        parking.setAffiliates(affiliates);
        // Add required entity
        Cities cities;
        if (TestUtil.findAll(em, Cities.class).isEmpty()) {
            cities = CitiesResourceIT.createUpdatedEntity(em);
            em.persist(cities);
            em.flush();
        } else {
            cities = TestUtil.findAll(em, Cities.class).get(0);
        }
        parking.setCities(cities);
        return parking;
    }

    @BeforeEach
    public void initTest() {
        parking = createEntity(em);
    }

    @Test
    @Transactional
    void createParking() throws Exception {
        int databaseSizeBeforeCreate = parkingRepository.findAll().size();
        // Create the Parking
        ParkingDTO parkingDTO = parkingMapper.toDto(parking);
        restParkingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parkingDTO)))
            .andExpect(status().isCreated());

        // Validate the Parking in the database
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeCreate + 1);
        Parking testParking = parkingList.get(parkingList.size() - 1);
        assertThat(testParking.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testParking.getParkingName()).isEqualTo(DEFAULT_PARKING_NAME);
        assertThat(testParking.getParkingTradeName()).isEqualTo(DEFAULT_PARKING_TRADE_NAME);
        assertThat(testParking.getParkingNumber()).isEqualTo(DEFAULT_PARKING_NUMBER);
        assertThat(testParking.getParkingPostalCode()).isEqualTo(DEFAULT_PARKING_POSTAL_CODE);
        assertThat(testParking.getParkingAddress()).isEqualTo(DEFAULT_PARKING_ADDRESS);
        assertThat(testParking.getParkingAddressComplement()).isEqualTo(DEFAULT_PARKING_ADDRESS_COMPLEMENT);
        assertThat(testParking.getParkingAddressNumber()).isEqualTo(DEFAULT_PARKING_ADDRESS_NUMBER);
        assertThat(testParking.getParkingAddressNeighborhood()).isEqualTo(DEFAULT_PARKING_ADDRESS_NEIGHBORHOOD);
        assertThat(testParking.getParkingTelephone()).isEqualTo(DEFAULT_PARKING_TELEPHONE);
        assertThat(testParking.getParkingEmail()).isEqualTo(DEFAULT_PARKING_EMAIL);
        assertThat(testParking.getParkingContactName()).isEqualTo(DEFAULT_PARKING_CONTACT_NAME);
    }

    @Test
    @Transactional
    void createParkingWithExistingId() throws Exception {
        // Create the Parking with an existing ID
        parking.setId(1L);
        ParkingDTO parkingDTO = parkingMapper.toDto(parking);

        int databaseSizeBeforeCreate = parkingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParkingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parkingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Parking in the database
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = parkingRepository.findAll().size();
        // set the field null
        parking.setActive(null);

        // Create the Parking, which fails.
        ParkingDTO parkingDTO = parkingMapper.toDto(parking);

        restParkingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parkingDTO)))
            .andExpect(status().isBadRequest());

        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkParkingNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = parkingRepository.findAll().size();
        // set the field null
        parking.setParkingName(null);

        // Create the Parking, which fails.
        ParkingDTO parkingDTO = parkingMapper.toDto(parking);

        restParkingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parkingDTO)))
            .andExpect(status().isBadRequest());

        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllParkings() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList
        restParkingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parking.getId().intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].parkingName").value(hasItem(DEFAULT_PARKING_NAME)))
            .andExpect(jsonPath("$.[*].parkingTradeName").value(hasItem(DEFAULT_PARKING_TRADE_NAME)))
            .andExpect(jsonPath("$.[*].parkingNumber").value(hasItem(DEFAULT_PARKING_NUMBER)))
            .andExpect(jsonPath("$.[*].parkingPostalCode").value(hasItem(DEFAULT_PARKING_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].parkingAddress").value(hasItem(DEFAULT_PARKING_ADDRESS)))
            .andExpect(jsonPath("$.[*].parkingAddressComplement").value(hasItem(DEFAULT_PARKING_ADDRESS_COMPLEMENT)))
            .andExpect(jsonPath("$.[*].parkingAddressNumber").value(hasItem(DEFAULT_PARKING_ADDRESS_NUMBER)))
            .andExpect(jsonPath("$.[*].parkingAddressNeighborhood").value(hasItem(DEFAULT_PARKING_ADDRESS_NEIGHBORHOOD)))
            .andExpect(jsonPath("$.[*].parkingTelephone").value(hasItem(DEFAULT_PARKING_TELEPHONE)))
            .andExpect(jsonPath("$.[*].parkingEmail").value(hasItem(DEFAULT_PARKING_EMAIL)))
            .andExpect(jsonPath("$.[*].parkingContactName").value(hasItem(DEFAULT_PARKING_CONTACT_NAME)));
    }

    @Test
    @Transactional
    void getParking() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get the parking
        restParkingMockMvc
            .perform(get(ENTITY_API_URL_ID, parking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parking.getId().intValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.parkingName").value(DEFAULT_PARKING_NAME))
            .andExpect(jsonPath("$.parkingTradeName").value(DEFAULT_PARKING_TRADE_NAME))
            .andExpect(jsonPath("$.parkingNumber").value(DEFAULT_PARKING_NUMBER))
            .andExpect(jsonPath("$.parkingPostalCode").value(DEFAULT_PARKING_POSTAL_CODE))
            .andExpect(jsonPath("$.parkingAddress").value(DEFAULT_PARKING_ADDRESS))
            .andExpect(jsonPath("$.parkingAddressComplement").value(DEFAULT_PARKING_ADDRESS_COMPLEMENT))
            .andExpect(jsonPath("$.parkingAddressNumber").value(DEFAULT_PARKING_ADDRESS_NUMBER))
            .andExpect(jsonPath("$.parkingAddressNeighborhood").value(DEFAULT_PARKING_ADDRESS_NEIGHBORHOOD))
            .andExpect(jsonPath("$.parkingTelephone").value(DEFAULT_PARKING_TELEPHONE))
            .andExpect(jsonPath("$.parkingEmail").value(DEFAULT_PARKING_EMAIL))
            .andExpect(jsonPath("$.parkingContactName").value(DEFAULT_PARKING_CONTACT_NAME));
    }

    @Test
    @Transactional
    void getParkingsByIdFiltering() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        Long id = parking.getId();

        defaultParkingShouldBeFound("id.equals=" + id);
        defaultParkingShouldNotBeFound("id.notEquals=" + id);

        defaultParkingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultParkingShouldNotBeFound("id.greaterThan=" + id);

        defaultParkingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultParkingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllParkingsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where active equals to DEFAULT_ACTIVE
        defaultParkingShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the parkingList where active equals to UPDATED_ACTIVE
        defaultParkingShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllParkingsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where active not equals to DEFAULT_ACTIVE
        defaultParkingShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the parkingList where active not equals to UPDATED_ACTIVE
        defaultParkingShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllParkingsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultParkingShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the parkingList where active equals to UPDATED_ACTIVE
        defaultParkingShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void getAllParkingsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where active is not null
        defaultParkingShouldBeFound("active.specified=true");

        // Get all the parkingList where active is null
        defaultParkingShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingsByParkingNameIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingName equals to DEFAULT_PARKING_NAME
        defaultParkingShouldBeFound("parkingName.equals=" + DEFAULT_PARKING_NAME);

        // Get all the parkingList where parkingName equals to UPDATED_PARKING_NAME
        defaultParkingShouldNotBeFound("parkingName.equals=" + UPDATED_PARKING_NAME);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingName not equals to DEFAULT_PARKING_NAME
        defaultParkingShouldNotBeFound("parkingName.notEquals=" + DEFAULT_PARKING_NAME);

        // Get all the parkingList where parkingName not equals to UPDATED_PARKING_NAME
        defaultParkingShouldBeFound("parkingName.notEquals=" + UPDATED_PARKING_NAME);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingNameIsInShouldWork() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingName in DEFAULT_PARKING_NAME or UPDATED_PARKING_NAME
        defaultParkingShouldBeFound("parkingName.in=" + DEFAULT_PARKING_NAME + "," + UPDATED_PARKING_NAME);

        // Get all the parkingList where parkingName equals to UPDATED_PARKING_NAME
        defaultParkingShouldNotBeFound("parkingName.in=" + UPDATED_PARKING_NAME);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingName is not null
        defaultParkingShouldBeFound("parkingName.specified=true");

        // Get all the parkingList where parkingName is null
        defaultParkingShouldNotBeFound("parkingName.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingsByParkingNameContainsSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingName contains DEFAULT_PARKING_NAME
        defaultParkingShouldBeFound("parkingName.contains=" + DEFAULT_PARKING_NAME);

        // Get all the parkingList where parkingName contains UPDATED_PARKING_NAME
        defaultParkingShouldNotBeFound("parkingName.contains=" + UPDATED_PARKING_NAME);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingNameNotContainsSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingName does not contain DEFAULT_PARKING_NAME
        defaultParkingShouldNotBeFound("parkingName.doesNotContain=" + DEFAULT_PARKING_NAME);

        // Get all the parkingList where parkingName does not contain UPDATED_PARKING_NAME
        defaultParkingShouldBeFound("parkingName.doesNotContain=" + UPDATED_PARKING_NAME);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingTradeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingTradeName equals to DEFAULT_PARKING_TRADE_NAME
        defaultParkingShouldBeFound("parkingTradeName.equals=" + DEFAULT_PARKING_TRADE_NAME);

        // Get all the parkingList where parkingTradeName equals to UPDATED_PARKING_TRADE_NAME
        defaultParkingShouldNotBeFound("parkingTradeName.equals=" + UPDATED_PARKING_TRADE_NAME);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingTradeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingTradeName not equals to DEFAULT_PARKING_TRADE_NAME
        defaultParkingShouldNotBeFound("parkingTradeName.notEquals=" + DEFAULT_PARKING_TRADE_NAME);

        // Get all the parkingList where parkingTradeName not equals to UPDATED_PARKING_TRADE_NAME
        defaultParkingShouldBeFound("parkingTradeName.notEquals=" + UPDATED_PARKING_TRADE_NAME);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingTradeNameIsInShouldWork() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingTradeName in DEFAULT_PARKING_TRADE_NAME or UPDATED_PARKING_TRADE_NAME
        defaultParkingShouldBeFound("parkingTradeName.in=" + DEFAULT_PARKING_TRADE_NAME + "," + UPDATED_PARKING_TRADE_NAME);

        // Get all the parkingList where parkingTradeName equals to UPDATED_PARKING_TRADE_NAME
        defaultParkingShouldNotBeFound("parkingTradeName.in=" + UPDATED_PARKING_TRADE_NAME);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingTradeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingTradeName is not null
        defaultParkingShouldBeFound("parkingTradeName.specified=true");

        // Get all the parkingList where parkingTradeName is null
        defaultParkingShouldNotBeFound("parkingTradeName.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingsByParkingTradeNameContainsSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingTradeName contains DEFAULT_PARKING_TRADE_NAME
        defaultParkingShouldBeFound("parkingTradeName.contains=" + DEFAULT_PARKING_TRADE_NAME);

        // Get all the parkingList where parkingTradeName contains UPDATED_PARKING_TRADE_NAME
        defaultParkingShouldNotBeFound("parkingTradeName.contains=" + UPDATED_PARKING_TRADE_NAME);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingTradeNameNotContainsSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingTradeName does not contain DEFAULT_PARKING_TRADE_NAME
        defaultParkingShouldNotBeFound("parkingTradeName.doesNotContain=" + DEFAULT_PARKING_TRADE_NAME);

        // Get all the parkingList where parkingTradeName does not contain UPDATED_PARKING_TRADE_NAME
        defaultParkingShouldBeFound("parkingTradeName.doesNotContain=" + UPDATED_PARKING_TRADE_NAME);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingNumber equals to DEFAULT_PARKING_NUMBER
        defaultParkingShouldBeFound("parkingNumber.equals=" + DEFAULT_PARKING_NUMBER);

        // Get all the parkingList where parkingNumber equals to UPDATED_PARKING_NUMBER
        defaultParkingShouldNotBeFound("parkingNumber.equals=" + UPDATED_PARKING_NUMBER);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingNumber not equals to DEFAULT_PARKING_NUMBER
        defaultParkingShouldNotBeFound("parkingNumber.notEquals=" + DEFAULT_PARKING_NUMBER);

        // Get all the parkingList where parkingNumber not equals to UPDATED_PARKING_NUMBER
        defaultParkingShouldBeFound("parkingNumber.notEquals=" + UPDATED_PARKING_NUMBER);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingNumberIsInShouldWork() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingNumber in DEFAULT_PARKING_NUMBER or UPDATED_PARKING_NUMBER
        defaultParkingShouldBeFound("parkingNumber.in=" + DEFAULT_PARKING_NUMBER + "," + UPDATED_PARKING_NUMBER);

        // Get all the parkingList where parkingNumber equals to UPDATED_PARKING_NUMBER
        defaultParkingShouldNotBeFound("parkingNumber.in=" + UPDATED_PARKING_NUMBER);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingNumber is not null
        defaultParkingShouldBeFound("parkingNumber.specified=true");

        // Get all the parkingList where parkingNumber is null
        defaultParkingShouldNotBeFound("parkingNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingsByParkingNumberContainsSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingNumber contains DEFAULT_PARKING_NUMBER
        defaultParkingShouldBeFound("parkingNumber.contains=" + DEFAULT_PARKING_NUMBER);

        // Get all the parkingList where parkingNumber contains UPDATED_PARKING_NUMBER
        defaultParkingShouldNotBeFound("parkingNumber.contains=" + UPDATED_PARKING_NUMBER);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingNumberNotContainsSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingNumber does not contain DEFAULT_PARKING_NUMBER
        defaultParkingShouldNotBeFound("parkingNumber.doesNotContain=" + DEFAULT_PARKING_NUMBER);

        // Get all the parkingList where parkingNumber does not contain UPDATED_PARKING_NUMBER
        defaultParkingShouldBeFound("parkingNumber.doesNotContain=" + UPDATED_PARKING_NUMBER);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingPostalCode equals to DEFAULT_PARKING_POSTAL_CODE
        defaultParkingShouldBeFound("parkingPostalCode.equals=" + DEFAULT_PARKING_POSTAL_CODE);

        // Get all the parkingList where parkingPostalCode equals to UPDATED_PARKING_POSTAL_CODE
        defaultParkingShouldNotBeFound("parkingPostalCode.equals=" + UPDATED_PARKING_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingPostalCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingPostalCode not equals to DEFAULT_PARKING_POSTAL_CODE
        defaultParkingShouldNotBeFound("parkingPostalCode.notEquals=" + DEFAULT_PARKING_POSTAL_CODE);

        // Get all the parkingList where parkingPostalCode not equals to UPDATED_PARKING_POSTAL_CODE
        defaultParkingShouldBeFound("parkingPostalCode.notEquals=" + UPDATED_PARKING_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingPostalCode in DEFAULT_PARKING_POSTAL_CODE or UPDATED_PARKING_POSTAL_CODE
        defaultParkingShouldBeFound("parkingPostalCode.in=" + DEFAULT_PARKING_POSTAL_CODE + "," + UPDATED_PARKING_POSTAL_CODE);

        // Get all the parkingList where parkingPostalCode equals to UPDATED_PARKING_POSTAL_CODE
        defaultParkingShouldNotBeFound("parkingPostalCode.in=" + UPDATED_PARKING_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingPostalCode is not null
        defaultParkingShouldBeFound("parkingPostalCode.specified=true");

        // Get all the parkingList where parkingPostalCode is null
        defaultParkingShouldNotBeFound("parkingPostalCode.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingsByParkingPostalCodeContainsSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingPostalCode contains DEFAULT_PARKING_POSTAL_CODE
        defaultParkingShouldBeFound("parkingPostalCode.contains=" + DEFAULT_PARKING_POSTAL_CODE);

        // Get all the parkingList where parkingPostalCode contains UPDATED_PARKING_POSTAL_CODE
        defaultParkingShouldNotBeFound("parkingPostalCode.contains=" + UPDATED_PARKING_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingPostalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingPostalCode does not contain DEFAULT_PARKING_POSTAL_CODE
        defaultParkingShouldNotBeFound("parkingPostalCode.doesNotContain=" + DEFAULT_PARKING_POSTAL_CODE);

        // Get all the parkingList where parkingPostalCode does not contain UPDATED_PARKING_POSTAL_CODE
        defaultParkingShouldBeFound("parkingPostalCode.doesNotContain=" + UPDATED_PARKING_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddress equals to DEFAULT_PARKING_ADDRESS
        defaultParkingShouldBeFound("parkingAddress.equals=" + DEFAULT_PARKING_ADDRESS);

        // Get all the parkingList where parkingAddress equals to UPDATED_PARKING_ADDRESS
        defaultParkingShouldNotBeFound("parkingAddress.equals=" + UPDATED_PARKING_ADDRESS);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddress not equals to DEFAULT_PARKING_ADDRESS
        defaultParkingShouldNotBeFound("parkingAddress.notEquals=" + DEFAULT_PARKING_ADDRESS);

        // Get all the parkingList where parkingAddress not equals to UPDATED_PARKING_ADDRESS
        defaultParkingShouldBeFound("parkingAddress.notEquals=" + UPDATED_PARKING_ADDRESS);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressIsInShouldWork() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddress in DEFAULT_PARKING_ADDRESS or UPDATED_PARKING_ADDRESS
        defaultParkingShouldBeFound("parkingAddress.in=" + DEFAULT_PARKING_ADDRESS + "," + UPDATED_PARKING_ADDRESS);

        // Get all the parkingList where parkingAddress equals to UPDATED_PARKING_ADDRESS
        defaultParkingShouldNotBeFound("parkingAddress.in=" + UPDATED_PARKING_ADDRESS);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddress is not null
        defaultParkingShouldBeFound("parkingAddress.specified=true");

        // Get all the parkingList where parkingAddress is null
        defaultParkingShouldNotBeFound("parkingAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressContainsSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddress contains DEFAULT_PARKING_ADDRESS
        defaultParkingShouldBeFound("parkingAddress.contains=" + DEFAULT_PARKING_ADDRESS);

        // Get all the parkingList where parkingAddress contains UPDATED_PARKING_ADDRESS
        defaultParkingShouldNotBeFound("parkingAddress.contains=" + UPDATED_PARKING_ADDRESS);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressNotContainsSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddress does not contain DEFAULT_PARKING_ADDRESS
        defaultParkingShouldNotBeFound("parkingAddress.doesNotContain=" + DEFAULT_PARKING_ADDRESS);

        // Get all the parkingList where parkingAddress does not contain UPDATED_PARKING_ADDRESS
        defaultParkingShouldBeFound("parkingAddress.doesNotContain=" + UPDATED_PARKING_ADDRESS);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressComplementIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddressComplement equals to DEFAULT_PARKING_ADDRESS_COMPLEMENT
        defaultParkingShouldBeFound("parkingAddressComplement.equals=" + DEFAULT_PARKING_ADDRESS_COMPLEMENT);

        // Get all the parkingList where parkingAddressComplement equals to UPDATED_PARKING_ADDRESS_COMPLEMENT
        defaultParkingShouldNotBeFound("parkingAddressComplement.equals=" + UPDATED_PARKING_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressComplementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddressComplement not equals to DEFAULT_PARKING_ADDRESS_COMPLEMENT
        defaultParkingShouldNotBeFound("parkingAddressComplement.notEquals=" + DEFAULT_PARKING_ADDRESS_COMPLEMENT);

        // Get all the parkingList where parkingAddressComplement not equals to UPDATED_PARKING_ADDRESS_COMPLEMENT
        defaultParkingShouldBeFound("parkingAddressComplement.notEquals=" + UPDATED_PARKING_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressComplementIsInShouldWork() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddressComplement in DEFAULT_PARKING_ADDRESS_COMPLEMENT or UPDATED_PARKING_ADDRESS_COMPLEMENT
        defaultParkingShouldBeFound(
            "parkingAddressComplement.in=" + DEFAULT_PARKING_ADDRESS_COMPLEMENT + "," + UPDATED_PARKING_ADDRESS_COMPLEMENT
        );

        // Get all the parkingList where parkingAddressComplement equals to UPDATED_PARKING_ADDRESS_COMPLEMENT
        defaultParkingShouldNotBeFound("parkingAddressComplement.in=" + UPDATED_PARKING_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressComplementIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddressComplement is not null
        defaultParkingShouldBeFound("parkingAddressComplement.specified=true");

        // Get all the parkingList where parkingAddressComplement is null
        defaultParkingShouldNotBeFound("parkingAddressComplement.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressComplementContainsSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddressComplement contains DEFAULT_PARKING_ADDRESS_COMPLEMENT
        defaultParkingShouldBeFound("parkingAddressComplement.contains=" + DEFAULT_PARKING_ADDRESS_COMPLEMENT);

        // Get all the parkingList where parkingAddressComplement contains UPDATED_PARKING_ADDRESS_COMPLEMENT
        defaultParkingShouldNotBeFound("parkingAddressComplement.contains=" + UPDATED_PARKING_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressComplementNotContainsSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddressComplement does not contain DEFAULT_PARKING_ADDRESS_COMPLEMENT
        defaultParkingShouldNotBeFound("parkingAddressComplement.doesNotContain=" + DEFAULT_PARKING_ADDRESS_COMPLEMENT);

        // Get all the parkingList where parkingAddressComplement does not contain UPDATED_PARKING_ADDRESS_COMPLEMENT
        defaultParkingShouldBeFound("parkingAddressComplement.doesNotContain=" + UPDATED_PARKING_ADDRESS_COMPLEMENT);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddressNumber equals to DEFAULT_PARKING_ADDRESS_NUMBER
        defaultParkingShouldBeFound("parkingAddressNumber.equals=" + DEFAULT_PARKING_ADDRESS_NUMBER);

        // Get all the parkingList where parkingAddressNumber equals to UPDATED_PARKING_ADDRESS_NUMBER
        defaultParkingShouldNotBeFound("parkingAddressNumber.equals=" + UPDATED_PARKING_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddressNumber not equals to DEFAULT_PARKING_ADDRESS_NUMBER
        defaultParkingShouldNotBeFound("parkingAddressNumber.notEquals=" + DEFAULT_PARKING_ADDRESS_NUMBER);

        // Get all the parkingList where parkingAddressNumber not equals to UPDATED_PARKING_ADDRESS_NUMBER
        defaultParkingShouldBeFound("parkingAddressNumber.notEquals=" + UPDATED_PARKING_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressNumberIsInShouldWork() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddressNumber in DEFAULT_PARKING_ADDRESS_NUMBER or UPDATED_PARKING_ADDRESS_NUMBER
        defaultParkingShouldBeFound("parkingAddressNumber.in=" + DEFAULT_PARKING_ADDRESS_NUMBER + "," + UPDATED_PARKING_ADDRESS_NUMBER);

        // Get all the parkingList where parkingAddressNumber equals to UPDATED_PARKING_ADDRESS_NUMBER
        defaultParkingShouldNotBeFound("parkingAddressNumber.in=" + UPDATED_PARKING_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddressNumber is not null
        defaultParkingShouldBeFound("parkingAddressNumber.specified=true");

        // Get all the parkingList where parkingAddressNumber is null
        defaultParkingShouldNotBeFound("parkingAddressNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddressNumber is greater than or equal to DEFAULT_PARKING_ADDRESS_NUMBER
        defaultParkingShouldBeFound("parkingAddressNumber.greaterThanOrEqual=" + DEFAULT_PARKING_ADDRESS_NUMBER);

        // Get all the parkingList where parkingAddressNumber is greater than or equal to UPDATED_PARKING_ADDRESS_NUMBER
        defaultParkingShouldNotBeFound("parkingAddressNumber.greaterThanOrEqual=" + UPDATED_PARKING_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddressNumber is less than or equal to DEFAULT_PARKING_ADDRESS_NUMBER
        defaultParkingShouldBeFound("parkingAddressNumber.lessThanOrEqual=" + DEFAULT_PARKING_ADDRESS_NUMBER);

        // Get all the parkingList where parkingAddressNumber is less than or equal to SMALLER_PARKING_ADDRESS_NUMBER
        defaultParkingShouldNotBeFound("parkingAddressNumber.lessThanOrEqual=" + SMALLER_PARKING_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddressNumber is less than DEFAULT_PARKING_ADDRESS_NUMBER
        defaultParkingShouldNotBeFound("parkingAddressNumber.lessThan=" + DEFAULT_PARKING_ADDRESS_NUMBER);

        // Get all the parkingList where parkingAddressNumber is less than UPDATED_PARKING_ADDRESS_NUMBER
        defaultParkingShouldBeFound("parkingAddressNumber.lessThan=" + UPDATED_PARKING_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddressNumber is greater than DEFAULT_PARKING_ADDRESS_NUMBER
        defaultParkingShouldNotBeFound("parkingAddressNumber.greaterThan=" + DEFAULT_PARKING_ADDRESS_NUMBER);

        // Get all the parkingList where parkingAddressNumber is greater than SMALLER_PARKING_ADDRESS_NUMBER
        defaultParkingShouldBeFound("parkingAddressNumber.greaterThan=" + SMALLER_PARKING_ADDRESS_NUMBER);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressNeighborhoodIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddressNeighborhood equals to DEFAULT_PARKING_ADDRESS_NEIGHBORHOOD
        defaultParkingShouldBeFound("parkingAddressNeighborhood.equals=" + DEFAULT_PARKING_ADDRESS_NEIGHBORHOOD);

        // Get all the parkingList where parkingAddressNeighborhood equals to UPDATED_PARKING_ADDRESS_NEIGHBORHOOD
        defaultParkingShouldNotBeFound("parkingAddressNeighborhood.equals=" + UPDATED_PARKING_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressNeighborhoodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddressNeighborhood not equals to DEFAULT_PARKING_ADDRESS_NEIGHBORHOOD
        defaultParkingShouldNotBeFound("parkingAddressNeighborhood.notEquals=" + DEFAULT_PARKING_ADDRESS_NEIGHBORHOOD);

        // Get all the parkingList where parkingAddressNeighborhood not equals to UPDATED_PARKING_ADDRESS_NEIGHBORHOOD
        defaultParkingShouldBeFound("parkingAddressNeighborhood.notEquals=" + UPDATED_PARKING_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressNeighborhoodIsInShouldWork() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddressNeighborhood in DEFAULT_PARKING_ADDRESS_NEIGHBORHOOD or UPDATED_PARKING_ADDRESS_NEIGHBORHOOD
        defaultParkingShouldBeFound(
            "parkingAddressNeighborhood.in=" + DEFAULT_PARKING_ADDRESS_NEIGHBORHOOD + "," + UPDATED_PARKING_ADDRESS_NEIGHBORHOOD
        );

        // Get all the parkingList where parkingAddressNeighborhood equals to UPDATED_PARKING_ADDRESS_NEIGHBORHOOD
        defaultParkingShouldNotBeFound("parkingAddressNeighborhood.in=" + UPDATED_PARKING_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressNeighborhoodIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddressNeighborhood is not null
        defaultParkingShouldBeFound("parkingAddressNeighborhood.specified=true");

        // Get all the parkingList where parkingAddressNeighborhood is null
        defaultParkingShouldNotBeFound("parkingAddressNeighborhood.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressNeighborhoodContainsSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddressNeighborhood contains DEFAULT_PARKING_ADDRESS_NEIGHBORHOOD
        defaultParkingShouldBeFound("parkingAddressNeighborhood.contains=" + DEFAULT_PARKING_ADDRESS_NEIGHBORHOOD);

        // Get all the parkingList where parkingAddressNeighborhood contains UPDATED_PARKING_ADDRESS_NEIGHBORHOOD
        defaultParkingShouldNotBeFound("parkingAddressNeighborhood.contains=" + UPDATED_PARKING_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingAddressNeighborhoodNotContainsSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingAddressNeighborhood does not contain DEFAULT_PARKING_ADDRESS_NEIGHBORHOOD
        defaultParkingShouldNotBeFound("parkingAddressNeighborhood.doesNotContain=" + DEFAULT_PARKING_ADDRESS_NEIGHBORHOOD);

        // Get all the parkingList where parkingAddressNeighborhood does not contain UPDATED_PARKING_ADDRESS_NEIGHBORHOOD
        defaultParkingShouldBeFound("parkingAddressNeighborhood.doesNotContain=" + UPDATED_PARKING_ADDRESS_NEIGHBORHOOD);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingTelephone equals to DEFAULT_PARKING_TELEPHONE
        defaultParkingShouldBeFound("parkingTelephone.equals=" + DEFAULT_PARKING_TELEPHONE);

        // Get all the parkingList where parkingTelephone equals to UPDATED_PARKING_TELEPHONE
        defaultParkingShouldNotBeFound("parkingTelephone.equals=" + UPDATED_PARKING_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingTelephone not equals to DEFAULT_PARKING_TELEPHONE
        defaultParkingShouldNotBeFound("parkingTelephone.notEquals=" + DEFAULT_PARKING_TELEPHONE);

        // Get all the parkingList where parkingTelephone not equals to UPDATED_PARKING_TELEPHONE
        defaultParkingShouldBeFound("parkingTelephone.notEquals=" + UPDATED_PARKING_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingTelephone in DEFAULT_PARKING_TELEPHONE or UPDATED_PARKING_TELEPHONE
        defaultParkingShouldBeFound("parkingTelephone.in=" + DEFAULT_PARKING_TELEPHONE + "," + UPDATED_PARKING_TELEPHONE);

        // Get all the parkingList where parkingTelephone equals to UPDATED_PARKING_TELEPHONE
        defaultParkingShouldNotBeFound("parkingTelephone.in=" + UPDATED_PARKING_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingTelephone is not null
        defaultParkingShouldBeFound("parkingTelephone.specified=true");

        // Get all the parkingList where parkingTelephone is null
        defaultParkingShouldNotBeFound("parkingTelephone.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingsByParkingTelephoneContainsSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingTelephone contains DEFAULT_PARKING_TELEPHONE
        defaultParkingShouldBeFound("parkingTelephone.contains=" + DEFAULT_PARKING_TELEPHONE);

        // Get all the parkingList where parkingTelephone contains UPDATED_PARKING_TELEPHONE
        defaultParkingShouldNotBeFound("parkingTelephone.contains=" + UPDATED_PARKING_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingTelephone does not contain DEFAULT_PARKING_TELEPHONE
        defaultParkingShouldNotBeFound("parkingTelephone.doesNotContain=" + DEFAULT_PARKING_TELEPHONE);

        // Get all the parkingList where parkingTelephone does not contain UPDATED_PARKING_TELEPHONE
        defaultParkingShouldBeFound("parkingTelephone.doesNotContain=" + UPDATED_PARKING_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingEmail equals to DEFAULT_PARKING_EMAIL
        defaultParkingShouldBeFound("parkingEmail.equals=" + DEFAULT_PARKING_EMAIL);

        // Get all the parkingList where parkingEmail equals to UPDATED_PARKING_EMAIL
        defaultParkingShouldNotBeFound("parkingEmail.equals=" + UPDATED_PARKING_EMAIL);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingEmail not equals to DEFAULT_PARKING_EMAIL
        defaultParkingShouldNotBeFound("parkingEmail.notEquals=" + DEFAULT_PARKING_EMAIL);

        // Get all the parkingList where parkingEmail not equals to UPDATED_PARKING_EMAIL
        defaultParkingShouldBeFound("parkingEmail.notEquals=" + UPDATED_PARKING_EMAIL);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingEmailIsInShouldWork() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingEmail in DEFAULT_PARKING_EMAIL or UPDATED_PARKING_EMAIL
        defaultParkingShouldBeFound("parkingEmail.in=" + DEFAULT_PARKING_EMAIL + "," + UPDATED_PARKING_EMAIL);

        // Get all the parkingList where parkingEmail equals to UPDATED_PARKING_EMAIL
        defaultParkingShouldNotBeFound("parkingEmail.in=" + UPDATED_PARKING_EMAIL);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingEmail is not null
        defaultParkingShouldBeFound("parkingEmail.specified=true");

        // Get all the parkingList where parkingEmail is null
        defaultParkingShouldNotBeFound("parkingEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingsByParkingEmailContainsSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingEmail contains DEFAULT_PARKING_EMAIL
        defaultParkingShouldBeFound("parkingEmail.contains=" + DEFAULT_PARKING_EMAIL);

        // Get all the parkingList where parkingEmail contains UPDATED_PARKING_EMAIL
        defaultParkingShouldNotBeFound("parkingEmail.contains=" + UPDATED_PARKING_EMAIL);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingEmailNotContainsSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingEmail does not contain DEFAULT_PARKING_EMAIL
        defaultParkingShouldNotBeFound("parkingEmail.doesNotContain=" + DEFAULT_PARKING_EMAIL);

        // Get all the parkingList where parkingEmail does not contain UPDATED_PARKING_EMAIL
        defaultParkingShouldBeFound("parkingEmail.doesNotContain=" + UPDATED_PARKING_EMAIL);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingContactNameIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingContactName equals to DEFAULT_PARKING_CONTACT_NAME
        defaultParkingShouldBeFound("parkingContactName.equals=" + DEFAULT_PARKING_CONTACT_NAME);

        // Get all the parkingList where parkingContactName equals to UPDATED_PARKING_CONTACT_NAME
        defaultParkingShouldNotBeFound("parkingContactName.equals=" + UPDATED_PARKING_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingContactNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingContactName not equals to DEFAULT_PARKING_CONTACT_NAME
        defaultParkingShouldNotBeFound("parkingContactName.notEquals=" + DEFAULT_PARKING_CONTACT_NAME);

        // Get all the parkingList where parkingContactName not equals to UPDATED_PARKING_CONTACT_NAME
        defaultParkingShouldBeFound("parkingContactName.notEquals=" + UPDATED_PARKING_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingContactNameIsInShouldWork() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingContactName in DEFAULT_PARKING_CONTACT_NAME or UPDATED_PARKING_CONTACT_NAME
        defaultParkingShouldBeFound("parkingContactName.in=" + DEFAULT_PARKING_CONTACT_NAME + "," + UPDATED_PARKING_CONTACT_NAME);

        // Get all the parkingList where parkingContactName equals to UPDATED_PARKING_CONTACT_NAME
        defaultParkingShouldNotBeFound("parkingContactName.in=" + UPDATED_PARKING_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingContactNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingContactName is not null
        defaultParkingShouldBeFound("parkingContactName.specified=true");

        // Get all the parkingList where parkingContactName is null
        defaultParkingShouldNotBeFound("parkingContactName.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingsByParkingContactNameContainsSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingContactName contains DEFAULT_PARKING_CONTACT_NAME
        defaultParkingShouldBeFound("parkingContactName.contains=" + DEFAULT_PARKING_CONTACT_NAME);

        // Get all the parkingList where parkingContactName contains UPDATED_PARKING_CONTACT_NAME
        defaultParkingShouldNotBeFound("parkingContactName.contains=" + UPDATED_PARKING_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingContactNameNotContainsSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingContactName does not contain DEFAULT_PARKING_CONTACT_NAME
        defaultParkingShouldNotBeFound("parkingContactName.doesNotContain=" + DEFAULT_PARKING_CONTACT_NAME);

        // Get all the parkingList where parkingContactName does not contain UPDATED_PARKING_CONTACT_NAME
        defaultParkingShouldBeFound("parkingContactName.doesNotContain=" + UPDATED_PARKING_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllParkingsByParkingSectorIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);
        ParkingSector parkingSector = ParkingSectorResourceIT.createEntity(em);
        em.persist(parkingSector);
        em.flush();
        parking.addParkingSector(parkingSector);
        parkingRepository.saveAndFlush(parking);
        Long parkingSectorId = parkingSector.getId();

        // Get all the parkingList where parkingSector equals to parkingSectorId
        defaultParkingShouldBeFound("parkingSectorId.equals=" + parkingSectorId);

        // Get all the parkingList where parkingSector equals to (parkingSectorId + 1)
        defaultParkingShouldNotBeFound("parkingSectorId.equals=" + (parkingSectorId + 1));
    }

    @Test
    @Transactional
    void getAllParkingsByHousingIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);
        Housing housing = HousingResourceIT.createEntity(em);
        em.persist(housing);
        em.flush();
        parking.addHousing(housing);
        parkingRepository.saveAndFlush(parking);
        Long housingId = housing.getId();

        // Get all the parkingList where housing equals to housingId
        defaultParkingShouldBeFound("housingId.equals=" + housingId);

        // Get all the parkingList where housing equals to (housingId + 1)
        defaultParkingShouldNotBeFound("housingId.equals=" + (housingId + 1));
    }

    @Test
    @Transactional
    void getAllParkingsByAffiliatesIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);
        Affiliates affiliates = AffiliatesResourceIT.createEntity(em);
        em.persist(affiliates);
        em.flush();
        parking.setAffiliates(affiliates);
        parkingRepository.saveAndFlush(parking);
        Long affiliatesId = affiliates.getId();

        // Get all the parkingList where affiliates equals to affiliatesId
        defaultParkingShouldBeFound("affiliatesId.equals=" + affiliatesId);

        // Get all the parkingList where affiliates equals to (affiliatesId + 1)
        defaultParkingShouldNotBeFound("affiliatesId.equals=" + (affiliatesId + 1));
    }

    @Test
    @Transactional
    void getAllParkingsByCitiesIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);
        Cities cities = CitiesResourceIT.createEntity(em);
        em.persist(cities);
        em.flush();
        parking.setCities(cities);
        parkingRepository.saveAndFlush(parking);
        Long citiesId = cities.getId();

        // Get all the parkingList where cities equals to citiesId
        defaultParkingShouldBeFound("citiesId.equals=" + citiesId);

        // Get all the parkingList where cities equals to (citiesId + 1)
        defaultParkingShouldNotBeFound("citiesId.equals=" + (citiesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultParkingShouldBeFound(String filter) throws Exception {
        restParkingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parking.getId().intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].parkingName").value(hasItem(DEFAULT_PARKING_NAME)))
            .andExpect(jsonPath("$.[*].parkingTradeName").value(hasItem(DEFAULT_PARKING_TRADE_NAME)))
            .andExpect(jsonPath("$.[*].parkingNumber").value(hasItem(DEFAULT_PARKING_NUMBER)))
            .andExpect(jsonPath("$.[*].parkingPostalCode").value(hasItem(DEFAULT_PARKING_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].parkingAddress").value(hasItem(DEFAULT_PARKING_ADDRESS)))
            .andExpect(jsonPath("$.[*].parkingAddressComplement").value(hasItem(DEFAULT_PARKING_ADDRESS_COMPLEMENT)))
            .andExpect(jsonPath("$.[*].parkingAddressNumber").value(hasItem(DEFAULT_PARKING_ADDRESS_NUMBER)))
            .andExpect(jsonPath("$.[*].parkingAddressNeighborhood").value(hasItem(DEFAULT_PARKING_ADDRESS_NEIGHBORHOOD)))
            .andExpect(jsonPath("$.[*].parkingTelephone").value(hasItem(DEFAULT_PARKING_TELEPHONE)))
            .andExpect(jsonPath("$.[*].parkingEmail").value(hasItem(DEFAULT_PARKING_EMAIL)))
            .andExpect(jsonPath("$.[*].parkingContactName").value(hasItem(DEFAULT_PARKING_CONTACT_NAME)));

        // Check, that the count call also returns 1
        restParkingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultParkingShouldNotBeFound(String filter) throws Exception {
        restParkingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParkingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingParking() throws Exception {
        // Get the parking
        restParkingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewParking() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        int databaseSizeBeforeUpdate = parkingRepository.findAll().size();

        // Update the parking
        Parking updatedParking = parkingRepository.findById(parking.getId()).get();
        // Disconnect from session so that the updates on updatedParking are not directly saved in db
        em.detach(updatedParking);
        updatedParking
            .active(UPDATED_ACTIVE)
            .parkingName(UPDATED_PARKING_NAME)
            .parkingTradeName(UPDATED_PARKING_TRADE_NAME)
            .parkingNumber(UPDATED_PARKING_NUMBER)
            .parkingPostalCode(UPDATED_PARKING_POSTAL_CODE)
            .parkingAddress(UPDATED_PARKING_ADDRESS)
            .parkingAddressComplement(UPDATED_PARKING_ADDRESS_COMPLEMENT)
            .parkingAddressNumber(UPDATED_PARKING_ADDRESS_NUMBER)
            .parkingAddressNeighborhood(UPDATED_PARKING_ADDRESS_NEIGHBORHOOD)
            .parkingTelephone(UPDATED_PARKING_TELEPHONE)
            .parkingEmail(UPDATED_PARKING_EMAIL)
            .parkingContactName(UPDATED_PARKING_CONTACT_NAME);
        ParkingDTO parkingDTO = parkingMapper.toDto(updatedParking);

        restParkingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parkingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parkingDTO))
            )
            .andExpect(status().isOk());

        // Validate the Parking in the database
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeUpdate);
        Parking testParking = parkingList.get(parkingList.size() - 1);
        assertThat(testParking.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testParking.getParkingName()).isEqualTo(UPDATED_PARKING_NAME);
        assertThat(testParking.getParkingTradeName()).isEqualTo(UPDATED_PARKING_TRADE_NAME);
        assertThat(testParking.getParkingNumber()).isEqualTo(UPDATED_PARKING_NUMBER);
        assertThat(testParking.getParkingPostalCode()).isEqualTo(UPDATED_PARKING_POSTAL_CODE);
        assertThat(testParking.getParkingAddress()).isEqualTo(UPDATED_PARKING_ADDRESS);
        assertThat(testParking.getParkingAddressComplement()).isEqualTo(UPDATED_PARKING_ADDRESS_COMPLEMENT);
        assertThat(testParking.getParkingAddressNumber()).isEqualTo(UPDATED_PARKING_ADDRESS_NUMBER);
        assertThat(testParking.getParkingAddressNeighborhood()).isEqualTo(UPDATED_PARKING_ADDRESS_NEIGHBORHOOD);
        assertThat(testParking.getParkingTelephone()).isEqualTo(UPDATED_PARKING_TELEPHONE);
        assertThat(testParking.getParkingEmail()).isEqualTo(UPDATED_PARKING_EMAIL);
        assertThat(testParking.getParkingContactName()).isEqualTo(UPDATED_PARKING_CONTACT_NAME);
    }

    @Test
    @Transactional
    void putNonExistingParking() throws Exception {
        int databaseSizeBeforeUpdate = parkingRepository.findAll().size();
        parking.setId(count.incrementAndGet());

        // Create the Parking
        ParkingDTO parkingDTO = parkingMapper.toDto(parking);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParkingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parkingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parkingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parking in the database
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParking() throws Exception {
        int databaseSizeBeforeUpdate = parkingRepository.findAll().size();
        parking.setId(count.incrementAndGet());

        // Create the Parking
        ParkingDTO parkingDTO = parkingMapper.toDto(parking);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parkingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parking in the database
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParking() throws Exception {
        int databaseSizeBeforeUpdate = parkingRepository.findAll().size();
        parking.setId(count.incrementAndGet());

        // Create the Parking
        ParkingDTO parkingDTO = parkingMapper.toDto(parking);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parkingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parking in the database
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParkingWithPatch() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        int databaseSizeBeforeUpdate = parkingRepository.findAll().size();

        // Update the parking using partial update
        Parking partialUpdatedParking = new Parking();
        partialUpdatedParking.setId(parking.getId());

        partialUpdatedParking
            .parkingNumber(UPDATED_PARKING_NUMBER)
            .parkingPostalCode(UPDATED_PARKING_POSTAL_CODE)
            .parkingAddress(UPDATED_PARKING_ADDRESS)
            .parkingAddressComplement(UPDATED_PARKING_ADDRESS_COMPLEMENT)
            .parkingTelephone(UPDATED_PARKING_TELEPHONE)
            .parkingEmail(UPDATED_PARKING_EMAIL);

        restParkingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParking.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParking))
            )
            .andExpect(status().isOk());

        // Validate the Parking in the database
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeUpdate);
        Parking testParking = parkingList.get(parkingList.size() - 1);
        assertThat(testParking.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testParking.getParkingName()).isEqualTo(DEFAULT_PARKING_NAME);
        assertThat(testParking.getParkingTradeName()).isEqualTo(DEFAULT_PARKING_TRADE_NAME);
        assertThat(testParking.getParkingNumber()).isEqualTo(UPDATED_PARKING_NUMBER);
        assertThat(testParking.getParkingPostalCode()).isEqualTo(UPDATED_PARKING_POSTAL_CODE);
        assertThat(testParking.getParkingAddress()).isEqualTo(UPDATED_PARKING_ADDRESS);
        assertThat(testParking.getParkingAddressComplement()).isEqualTo(UPDATED_PARKING_ADDRESS_COMPLEMENT);
        assertThat(testParking.getParkingAddressNumber()).isEqualTo(DEFAULT_PARKING_ADDRESS_NUMBER);
        assertThat(testParking.getParkingAddressNeighborhood()).isEqualTo(DEFAULT_PARKING_ADDRESS_NEIGHBORHOOD);
        assertThat(testParking.getParkingTelephone()).isEqualTo(UPDATED_PARKING_TELEPHONE);
        assertThat(testParking.getParkingEmail()).isEqualTo(UPDATED_PARKING_EMAIL);
        assertThat(testParking.getParkingContactName()).isEqualTo(DEFAULT_PARKING_CONTACT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateParkingWithPatch() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        int databaseSizeBeforeUpdate = parkingRepository.findAll().size();

        // Update the parking using partial update
        Parking partialUpdatedParking = new Parking();
        partialUpdatedParking.setId(parking.getId());

        partialUpdatedParking
            .active(UPDATED_ACTIVE)
            .parkingName(UPDATED_PARKING_NAME)
            .parkingTradeName(UPDATED_PARKING_TRADE_NAME)
            .parkingNumber(UPDATED_PARKING_NUMBER)
            .parkingPostalCode(UPDATED_PARKING_POSTAL_CODE)
            .parkingAddress(UPDATED_PARKING_ADDRESS)
            .parkingAddressComplement(UPDATED_PARKING_ADDRESS_COMPLEMENT)
            .parkingAddressNumber(UPDATED_PARKING_ADDRESS_NUMBER)
            .parkingAddressNeighborhood(UPDATED_PARKING_ADDRESS_NEIGHBORHOOD)
            .parkingTelephone(UPDATED_PARKING_TELEPHONE)
            .parkingEmail(UPDATED_PARKING_EMAIL)
            .parkingContactName(UPDATED_PARKING_CONTACT_NAME);

        restParkingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParking.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParking))
            )
            .andExpect(status().isOk());

        // Validate the Parking in the database
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeUpdate);
        Parking testParking = parkingList.get(parkingList.size() - 1);
        assertThat(testParking.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testParking.getParkingName()).isEqualTo(UPDATED_PARKING_NAME);
        assertThat(testParking.getParkingTradeName()).isEqualTo(UPDATED_PARKING_TRADE_NAME);
        assertThat(testParking.getParkingNumber()).isEqualTo(UPDATED_PARKING_NUMBER);
        assertThat(testParking.getParkingPostalCode()).isEqualTo(UPDATED_PARKING_POSTAL_CODE);
        assertThat(testParking.getParkingAddress()).isEqualTo(UPDATED_PARKING_ADDRESS);
        assertThat(testParking.getParkingAddressComplement()).isEqualTo(UPDATED_PARKING_ADDRESS_COMPLEMENT);
        assertThat(testParking.getParkingAddressNumber()).isEqualTo(UPDATED_PARKING_ADDRESS_NUMBER);
        assertThat(testParking.getParkingAddressNeighborhood()).isEqualTo(UPDATED_PARKING_ADDRESS_NEIGHBORHOOD);
        assertThat(testParking.getParkingTelephone()).isEqualTo(UPDATED_PARKING_TELEPHONE);
        assertThat(testParking.getParkingEmail()).isEqualTo(UPDATED_PARKING_EMAIL);
        assertThat(testParking.getParkingContactName()).isEqualTo(UPDATED_PARKING_CONTACT_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingParking() throws Exception {
        int databaseSizeBeforeUpdate = parkingRepository.findAll().size();
        parking.setId(count.incrementAndGet());

        // Create the Parking
        ParkingDTO parkingDTO = parkingMapper.toDto(parking);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParkingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parkingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parkingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parking in the database
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParking() throws Exception {
        int databaseSizeBeforeUpdate = parkingRepository.findAll().size();
        parking.setId(count.incrementAndGet());

        // Create the Parking
        ParkingDTO parkingDTO = parkingMapper.toDto(parking);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parkingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parking in the database
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParking() throws Exception {
        int databaseSizeBeforeUpdate = parkingRepository.findAll().size();
        parking.setId(count.incrementAndGet());

        // Create the Parking
        ParkingDTO parkingDTO = parkingMapper.toDto(parking);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(parkingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parking in the database
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParking() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        int databaseSizeBeforeDelete = parkingRepository.findAll().size();

        // Delete the parking
        restParkingMockMvc
            .perform(delete(ENTITY_API_URL_ID, parking.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
