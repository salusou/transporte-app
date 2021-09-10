package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.HousingVehicleItem;
import com.genesisoft.transporte.domain.ParkingSector;
import com.genesisoft.transporte.domain.ParkingSectorSpace;
import com.genesisoft.transporte.domain.enumeration.ParkingSpaceStatus;
import com.genesisoft.transporte.repository.ParkingSectorSpaceRepository;
import com.genesisoft.transporte.service.criteria.ParkingSectorSpaceCriteria;
import com.genesisoft.transporte.service.dto.ParkingSectorSpaceDTO;
import com.genesisoft.transporte.service.mapper.ParkingSectorSpaceMapper;
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
 * Integration tests for the {@link ParkingSectorSpaceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParkingSectorSpaceResourceIT {

    private static final Integer DEFAULT_PARKING_NUMBER = 1;
    private static final Integer UPDATED_PARKING_NUMBER = 2;
    private static final Integer SMALLER_PARKING_NUMBER = 1 - 1;

    private static final ParkingSpaceStatus DEFAULT_PARKING_STATUS = ParkingSpaceStatus.Free;
    private static final ParkingSpaceStatus UPDATED_PARKING_STATUS = ParkingSpaceStatus.Occupied;

    private static final LocalDate DEFAULT_PARKING_ENTRY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PARKING_ENTRY_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PARKING_ENTRY_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_PARKING_DEPARTURE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PARKING_DEPARTURE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PARKING_DEPARTURE_DATE = LocalDate.ofEpochDay(-1L);

    private static final Long DEFAULT_PARKING_HOUSING_ITEM_ID = 1L;
    private static final Long UPDATED_PARKING_HOUSING_ITEM_ID = 2L;
    private static final Long SMALLER_PARKING_HOUSING_ITEM_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/parking-sector-spaces";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ParkingSectorSpaceRepository parkingSectorSpaceRepository;

    @Autowired
    private ParkingSectorSpaceMapper parkingSectorSpaceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParkingSectorSpaceMockMvc;

    private ParkingSectorSpace parkingSectorSpace;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParkingSectorSpace createEntity(EntityManager em) {
        ParkingSectorSpace parkingSectorSpace = new ParkingSectorSpace()
            .parkingNumber(DEFAULT_PARKING_NUMBER)
            .parkingStatus(DEFAULT_PARKING_STATUS)
            .parkingEntryDate(DEFAULT_PARKING_ENTRY_DATE)
            .parkingDepartureDate(DEFAULT_PARKING_DEPARTURE_DATE)
            .parkingHousingItemId(DEFAULT_PARKING_HOUSING_ITEM_ID);
        // Add required entity
        ParkingSector parkingSector;
        if (TestUtil.findAll(em, ParkingSector.class).isEmpty()) {
            parkingSector = ParkingSectorResourceIT.createEntity(em);
            em.persist(parkingSector);
            em.flush();
        } else {
            parkingSector = TestUtil.findAll(em, ParkingSector.class).get(0);
        }
        parkingSectorSpace.setParkingSector(parkingSector);
        return parkingSectorSpace;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParkingSectorSpace createUpdatedEntity(EntityManager em) {
        ParkingSectorSpace parkingSectorSpace = new ParkingSectorSpace()
            .parkingNumber(UPDATED_PARKING_NUMBER)
            .parkingStatus(UPDATED_PARKING_STATUS)
            .parkingEntryDate(UPDATED_PARKING_ENTRY_DATE)
            .parkingDepartureDate(UPDATED_PARKING_DEPARTURE_DATE)
            .parkingHousingItemId(UPDATED_PARKING_HOUSING_ITEM_ID);
        // Add required entity
        ParkingSector parkingSector;
        if (TestUtil.findAll(em, ParkingSector.class).isEmpty()) {
            parkingSector = ParkingSectorResourceIT.createUpdatedEntity(em);
            em.persist(parkingSector);
            em.flush();
        } else {
            parkingSector = TestUtil.findAll(em, ParkingSector.class).get(0);
        }
        parkingSectorSpace.setParkingSector(parkingSector);
        return parkingSectorSpace;
    }

    @BeforeEach
    public void initTest() {
        parkingSectorSpace = createEntity(em);
    }

    @Test
    @Transactional
    void createParkingSectorSpace() throws Exception {
        int databaseSizeBeforeCreate = parkingSectorSpaceRepository.findAll().size();
        // Create the ParkingSectorSpace
        ParkingSectorSpaceDTO parkingSectorSpaceDTO = parkingSectorSpaceMapper.toDto(parkingSectorSpace);
        restParkingSectorSpaceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parkingSectorSpaceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ParkingSectorSpace in the database
        List<ParkingSectorSpace> parkingSectorSpaceList = parkingSectorSpaceRepository.findAll();
        assertThat(parkingSectorSpaceList).hasSize(databaseSizeBeforeCreate + 1);
        ParkingSectorSpace testParkingSectorSpace = parkingSectorSpaceList.get(parkingSectorSpaceList.size() - 1);
        assertThat(testParkingSectorSpace.getParkingNumber()).isEqualTo(DEFAULT_PARKING_NUMBER);
        assertThat(testParkingSectorSpace.getParkingStatus()).isEqualTo(DEFAULT_PARKING_STATUS);
        assertThat(testParkingSectorSpace.getParkingEntryDate()).isEqualTo(DEFAULT_PARKING_ENTRY_DATE);
        assertThat(testParkingSectorSpace.getParkingDepartureDate()).isEqualTo(DEFAULT_PARKING_DEPARTURE_DATE);
        assertThat(testParkingSectorSpace.getParkingHousingItemId()).isEqualTo(DEFAULT_PARKING_HOUSING_ITEM_ID);
    }

    @Test
    @Transactional
    void createParkingSectorSpaceWithExistingId() throws Exception {
        // Create the ParkingSectorSpace with an existing ID
        parkingSectorSpace.setId(1L);
        ParkingSectorSpaceDTO parkingSectorSpaceDTO = parkingSectorSpaceMapper.toDto(parkingSectorSpace);

        int databaseSizeBeforeCreate = parkingSectorSpaceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParkingSectorSpaceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parkingSectorSpaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParkingSectorSpace in the database
        List<ParkingSectorSpace> parkingSectorSpaceList = parkingSectorSpaceRepository.findAll();
        assertThat(parkingSectorSpaceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkParkingNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = parkingSectorSpaceRepository.findAll().size();
        // set the field null
        parkingSectorSpace.setParkingNumber(null);

        // Create the ParkingSectorSpace, which fails.
        ParkingSectorSpaceDTO parkingSectorSpaceDTO = parkingSectorSpaceMapper.toDto(parkingSectorSpace);

        restParkingSectorSpaceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parkingSectorSpaceDTO))
            )
            .andExpect(status().isBadRequest());

        List<ParkingSectorSpace> parkingSectorSpaceList = parkingSectorSpaceRepository.findAll();
        assertThat(parkingSectorSpaceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkParkingStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = parkingSectorSpaceRepository.findAll().size();
        // set the field null
        parkingSectorSpace.setParkingStatus(null);

        // Create the ParkingSectorSpace, which fails.
        ParkingSectorSpaceDTO parkingSectorSpaceDTO = parkingSectorSpaceMapper.toDto(parkingSectorSpace);

        restParkingSectorSpaceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parkingSectorSpaceDTO))
            )
            .andExpect(status().isBadRequest());

        List<ParkingSectorSpace> parkingSectorSpaceList = parkingSectorSpaceRepository.findAll();
        assertThat(parkingSectorSpaceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpaces() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList
        restParkingSectorSpaceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parkingSectorSpace.getId().intValue())))
            .andExpect(jsonPath("$.[*].parkingNumber").value(hasItem(DEFAULT_PARKING_NUMBER)))
            .andExpect(jsonPath("$.[*].parkingStatus").value(hasItem(DEFAULT_PARKING_STATUS.toString())))
            .andExpect(jsonPath("$.[*].parkingEntryDate").value(hasItem(DEFAULT_PARKING_ENTRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].parkingDepartureDate").value(hasItem(DEFAULT_PARKING_DEPARTURE_DATE.toString())))
            .andExpect(jsonPath("$.[*].parkingHousingItemId").value(hasItem(DEFAULT_PARKING_HOUSING_ITEM_ID.intValue())));
    }

    @Test
    @Transactional
    void getParkingSectorSpace() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get the parkingSectorSpace
        restParkingSectorSpaceMockMvc
            .perform(get(ENTITY_API_URL_ID, parkingSectorSpace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parkingSectorSpace.getId().intValue()))
            .andExpect(jsonPath("$.parkingNumber").value(DEFAULT_PARKING_NUMBER))
            .andExpect(jsonPath("$.parkingStatus").value(DEFAULT_PARKING_STATUS.toString()))
            .andExpect(jsonPath("$.parkingEntryDate").value(DEFAULT_PARKING_ENTRY_DATE.toString()))
            .andExpect(jsonPath("$.parkingDepartureDate").value(DEFAULT_PARKING_DEPARTURE_DATE.toString()))
            .andExpect(jsonPath("$.parkingHousingItemId").value(DEFAULT_PARKING_HOUSING_ITEM_ID.intValue()));
    }

    @Test
    @Transactional
    void getParkingSectorSpacesByIdFiltering() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        Long id = parkingSectorSpace.getId();

        defaultParkingSectorSpaceShouldBeFound("id.equals=" + id);
        defaultParkingSectorSpaceShouldNotBeFound("id.notEquals=" + id);

        defaultParkingSectorSpaceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultParkingSectorSpaceShouldNotBeFound("id.greaterThan=" + id);

        defaultParkingSectorSpaceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultParkingSectorSpaceShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingNumber equals to DEFAULT_PARKING_NUMBER
        defaultParkingSectorSpaceShouldBeFound("parkingNumber.equals=" + DEFAULT_PARKING_NUMBER);

        // Get all the parkingSectorSpaceList where parkingNumber equals to UPDATED_PARKING_NUMBER
        defaultParkingSectorSpaceShouldNotBeFound("parkingNumber.equals=" + UPDATED_PARKING_NUMBER);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingNumber not equals to DEFAULT_PARKING_NUMBER
        defaultParkingSectorSpaceShouldNotBeFound("parkingNumber.notEquals=" + DEFAULT_PARKING_NUMBER);

        // Get all the parkingSectorSpaceList where parkingNumber not equals to UPDATED_PARKING_NUMBER
        defaultParkingSectorSpaceShouldBeFound("parkingNumber.notEquals=" + UPDATED_PARKING_NUMBER);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingNumberIsInShouldWork() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingNumber in DEFAULT_PARKING_NUMBER or UPDATED_PARKING_NUMBER
        defaultParkingSectorSpaceShouldBeFound("parkingNumber.in=" + DEFAULT_PARKING_NUMBER + "," + UPDATED_PARKING_NUMBER);

        // Get all the parkingSectorSpaceList where parkingNumber equals to UPDATED_PARKING_NUMBER
        defaultParkingSectorSpaceShouldNotBeFound("parkingNumber.in=" + UPDATED_PARKING_NUMBER);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingNumber is not null
        defaultParkingSectorSpaceShouldBeFound("parkingNumber.specified=true");

        // Get all the parkingSectorSpaceList where parkingNumber is null
        defaultParkingSectorSpaceShouldNotBeFound("parkingNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingNumber is greater than or equal to DEFAULT_PARKING_NUMBER
        defaultParkingSectorSpaceShouldBeFound("parkingNumber.greaterThanOrEqual=" + DEFAULT_PARKING_NUMBER);

        // Get all the parkingSectorSpaceList where parkingNumber is greater than or equal to UPDATED_PARKING_NUMBER
        defaultParkingSectorSpaceShouldNotBeFound("parkingNumber.greaterThanOrEqual=" + UPDATED_PARKING_NUMBER);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingNumber is less than or equal to DEFAULT_PARKING_NUMBER
        defaultParkingSectorSpaceShouldBeFound("parkingNumber.lessThanOrEqual=" + DEFAULT_PARKING_NUMBER);

        // Get all the parkingSectorSpaceList where parkingNumber is less than or equal to SMALLER_PARKING_NUMBER
        defaultParkingSectorSpaceShouldNotBeFound("parkingNumber.lessThanOrEqual=" + SMALLER_PARKING_NUMBER);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingNumber is less than DEFAULT_PARKING_NUMBER
        defaultParkingSectorSpaceShouldNotBeFound("parkingNumber.lessThan=" + DEFAULT_PARKING_NUMBER);

        // Get all the parkingSectorSpaceList where parkingNumber is less than UPDATED_PARKING_NUMBER
        defaultParkingSectorSpaceShouldBeFound("parkingNumber.lessThan=" + UPDATED_PARKING_NUMBER);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingNumber is greater than DEFAULT_PARKING_NUMBER
        defaultParkingSectorSpaceShouldNotBeFound("parkingNumber.greaterThan=" + DEFAULT_PARKING_NUMBER);

        // Get all the parkingSectorSpaceList where parkingNumber is greater than SMALLER_PARKING_NUMBER
        defaultParkingSectorSpaceShouldBeFound("parkingNumber.greaterThan=" + SMALLER_PARKING_NUMBER);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingStatus equals to DEFAULT_PARKING_STATUS
        defaultParkingSectorSpaceShouldBeFound("parkingStatus.equals=" + DEFAULT_PARKING_STATUS);

        // Get all the parkingSectorSpaceList where parkingStatus equals to UPDATED_PARKING_STATUS
        defaultParkingSectorSpaceShouldNotBeFound("parkingStatus.equals=" + UPDATED_PARKING_STATUS);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingStatus not equals to DEFAULT_PARKING_STATUS
        defaultParkingSectorSpaceShouldNotBeFound("parkingStatus.notEquals=" + DEFAULT_PARKING_STATUS);

        // Get all the parkingSectorSpaceList where parkingStatus not equals to UPDATED_PARKING_STATUS
        defaultParkingSectorSpaceShouldBeFound("parkingStatus.notEquals=" + UPDATED_PARKING_STATUS);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingStatusIsInShouldWork() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingStatus in DEFAULT_PARKING_STATUS or UPDATED_PARKING_STATUS
        defaultParkingSectorSpaceShouldBeFound("parkingStatus.in=" + DEFAULT_PARKING_STATUS + "," + UPDATED_PARKING_STATUS);

        // Get all the parkingSectorSpaceList where parkingStatus equals to UPDATED_PARKING_STATUS
        defaultParkingSectorSpaceShouldNotBeFound("parkingStatus.in=" + UPDATED_PARKING_STATUS);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingStatus is not null
        defaultParkingSectorSpaceShouldBeFound("parkingStatus.specified=true");

        // Get all the parkingSectorSpaceList where parkingStatus is null
        defaultParkingSectorSpaceShouldNotBeFound("parkingStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingEntryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingEntryDate equals to DEFAULT_PARKING_ENTRY_DATE
        defaultParkingSectorSpaceShouldBeFound("parkingEntryDate.equals=" + DEFAULT_PARKING_ENTRY_DATE);

        // Get all the parkingSectorSpaceList where parkingEntryDate equals to UPDATED_PARKING_ENTRY_DATE
        defaultParkingSectorSpaceShouldNotBeFound("parkingEntryDate.equals=" + UPDATED_PARKING_ENTRY_DATE);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingEntryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingEntryDate not equals to DEFAULT_PARKING_ENTRY_DATE
        defaultParkingSectorSpaceShouldNotBeFound("parkingEntryDate.notEquals=" + DEFAULT_PARKING_ENTRY_DATE);

        // Get all the parkingSectorSpaceList where parkingEntryDate not equals to UPDATED_PARKING_ENTRY_DATE
        defaultParkingSectorSpaceShouldBeFound("parkingEntryDate.notEquals=" + UPDATED_PARKING_ENTRY_DATE);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingEntryDateIsInShouldWork() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingEntryDate in DEFAULT_PARKING_ENTRY_DATE or UPDATED_PARKING_ENTRY_DATE
        defaultParkingSectorSpaceShouldBeFound("parkingEntryDate.in=" + DEFAULT_PARKING_ENTRY_DATE + "," + UPDATED_PARKING_ENTRY_DATE);

        // Get all the parkingSectorSpaceList where parkingEntryDate equals to UPDATED_PARKING_ENTRY_DATE
        defaultParkingSectorSpaceShouldNotBeFound("parkingEntryDate.in=" + UPDATED_PARKING_ENTRY_DATE);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingEntryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingEntryDate is not null
        defaultParkingSectorSpaceShouldBeFound("parkingEntryDate.specified=true");

        // Get all the parkingSectorSpaceList where parkingEntryDate is null
        defaultParkingSectorSpaceShouldNotBeFound("parkingEntryDate.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingEntryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingEntryDate is greater than or equal to DEFAULT_PARKING_ENTRY_DATE
        defaultParkingSectorSpaceShouldBeFound("parkingEntryDate.greaterThanOrEqual=" + DEFAULT_PARKING_ENTRY_DATE);

        // Get all the parkingSectorSpaceList where parkingEntryDate is greater than or equal to UPDATED_PARKING_ENTRY_DATE
        defaultParkingSectorSpaceShouldNotBeFound("parkingEntryDate.greaterThanOrEqual=" + UPDATED_PARKING_ENTRY_DATE);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingEntryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingEntryDate is less than or equal to DEFAULT_PARKING_ENTRY_DATE
        defaultParkingSectorSpaceShouldBeFound("parkingEntryDate.lessThanOrEqual=" + DEFAULT_PARKING_ENTRY_DATE);

        // Get all the parkingSectorSpaceList where parkingEntryDate is less than or equal to SMALLER_PARKING_ENTRY_DATE
        defaultParkingSectorSpaceShouldNotBeFound("parkingEntryDate.lessThanOrEqual=" + SMALLER_PARKING_ENTRY_DATE);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingEntryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingEntryDate is less than DEFAULT_PARKING_ENTRY_DATE
        defaultParkingSectorSpaceShouldNotBeFound("parkingEntryDate.lessThan=" + DEFAULT_PARKING_ENTRY_DATE);

        // Get all the parkingSectorSpaceList where parkingEntryDate is less than UPDATED_PARKING_ENTRY_DATE
        defaultParkingSectorSpaceShouldBeFound("parkingEntryDate.lessThan=" + UPDATED_PARKING_ENTRY_DATE);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingEntryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingEntryDate is greater than DEFAULT_PARKING_ENTRY_DATE
        defaultParkingSectorSpaceShouldNotBeFound("parkingEntryDate.greaterThan=" + DEFAULT_PARKING_ENTRY_DATE);

        // Get all the parkingSectorSpaceList where parkingEntryDate is greater than SMALLER_PARKING_ENTRY_DATE
        defaultParkingSectorSpaceShouldBeFound("parkingEntryDate.greaterThan=" + SMALLER_PARKING_ENTRY_DATE);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingDepartureDateIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingDepartureDate equals to DEFAULT_PARKING_DEPARTURE_DATE
        defaultParkingSectorSpaceShouldBeFound("parkingDepartureDate.equals=" + DEFAULT_PARKING_DEPARTURE_DATE);

        // Get all the parkingSectorSpaceList where parkingDepartureDate equals to UPDATED_PARKING_DEPARTURE_DATE
        defaultParkingSectorSpaceShouldNotBeFound("parkingDepartureDate.equals=" + UPDATED_PARKING_DEPARTURE_DATE);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingDepartureDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingDepartureDate not equals to DEFAULT_PARKING_DEPARTURE_DATE
        defaultParkingSectorSpaceShouldNotBeFound("parkingDepartureDate.notEquals=" + DEFAULT_PARKING_DEPARTURE_DATE);

        // Get all the parkingSectorSpaceList where parkingDepartureDate not equals to UPDATED_PARKING_DEPARTURE_DATE
        defaultParkingSectorSpaceShouldBeFound("parkingDepartureDate.notEquals=" + UPDATED_PARKING_DEPARTURE_DATE);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingDepartureDateIsInShouldWork() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingDepartureDate in DEFAULT_PARKING_DEPARTURE_DATE or UPDATED_PARKING_DEPARTURE_DATE
        defaultParkingSectorSpaceShouldBeFound(
            "parkingDepartureDate.in=" + DEFAULT_PARKING_DEPARTURE_DATE + "," + UPDATED_PARKING_DEPARTURE_DATE
        );

        // Get all the parkingSectorSpaceList where parkingDepartureDate equals to UPDATED_PARKING_DEPARTURE_DATE
        defaultParkingSectorSpaceShouldNotBeFound("parkingDepartureDate.in=" + UPDATED_PARKING_DEPARTURE_DATE);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingDepartureDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingDepartureDate is not null
        defaultParkingSectorSpaceShouldBeFound("parkingDepartureDate.specified=true");

        // Get all the parkingSectorSpaceList where parkingDepartureDate is null
        defaultParkingSectorSpaceShouldNotBeFound("parkingDepartureDate.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingDepartureDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingDepartureDate is greater than or equal to DEFAULT_PARKING_DEPARTURE_DATE
        defaultParkingSectorSpaceShouldBeFound("parkingDepartureDate.greaterThanOrEqual=" + DEFAULT_PARKING_DEPARTURE_DATE);

        // Get all the parkingSectorSpaceList where parkingDepartureDate is greater than or equal to UPDATED_PARKING_DEPARTURE_DATE
        defaultParkingSectorSpaceShouldNotBeFound("parkingDepartureDate.greaterThanOrEqual=" + UPDATED_PARKING_DEPARTURE_DATE);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingDepartureDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingDepartureDate is less than or equal to DEFAULT_PARKING_DEPARTURE_DATE
        defaultParkingSectorSpaceShouldBeFound("parkingDepartureDate.lessThanOrEqual=" + DEFAULT_PARKING_DEPARTURE_DATE);

        // Get all the parkingSectorSpaceList where parkingDepartureDate is less than or equal to SMALLER_PARKING_DEPARTURE_DATE
        defaultParkingSectorSpaceShouldNotBeFound("parkingDepartureDate.lessThanOrEqual=" + SMALLER_PARKING_DEPARTURE_DATE);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingDepartureDateIsLessThanSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingDepartureDate is less than DEFAULT_PARKING_DEPARTURE_DATE
        defaultParkingSectorSpaceShouldNotBeFound("parkingDepartureDate.lessThan=" + DEFAULT_PARKING_DEPARTURE_DATE);

        // Get all the parkingSectorSpaceList where parkingDepartureDate is less than UPDATED_PARKING_DEPARTURE_DATE
        defaultParkingSectorSpaceShouldBeFound("parkingDepartureDate.lessThan=" + UPDATED_PARKING_DEPARTURE_DATE);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingDepartureDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingDepartureDate is greater than DEFAULT_PARKING_DEPARTURE_DATE
        defaultParkingSectorSpaceShouldNotBeFound("parkingDepartureDate.greaterThan=" + DEFAULT_PARKING_DEPARTURE_DATE);

        // Get all the parkingSectorSpaceList where parkingDepartureDate is greater than SMALLER_PARKING_DEPARTURE_DATE
        defaultParkingSectorSpaceShouldBeFound("parkingDepartureDate.greaterThan=" + SMALLER_PARKING_DEPARTURE_DATE);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingHousingItemIdIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingHousingItemId equals to DEFAULT_PARKING_HOUSING_ITEM_ID
        defaultParkingSectorSpaceShouldBeFound("parkingHousingItemId.equals=" + DEFAULT_PARKING_HOUSING_ITEM_ID);

        // Get all the parkingSectorSpaceList where parkingHousingItemId equals to UPDATED_PARKING_HOUSING_ITEM_ID
        defaultParkingSectorSpaceShouldNotBeFound("parkingHousingItemId.equals=" + UPDATED_PARKING_HOUSING_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingHousingItemIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingHousingItemId not equals to DEFAULT_PARKING_HOUSING_ITEM_ID
        defaultParkingSectorSpaceShouldNotBeFound("parkingHousingItemId.notEquals=" + DEFAULT_PARKING_HOUSING_ITEM_ID);

        // Get all the parkingSectorSpaceList where parkingHousingItemId not equals to UPDATED_PARKING_HOUSING_ITEM_ID
        defaultParkingSectorSpaceShouldBeFound("parkingHousingItemId.notEquals=" + UPDATED_PARKING_HOUSING_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingHousingItemIdIsInShouldWork() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingHousingItemId in DEFAULT_PARKING_HOUSING_ITEM_ID or UPDATED_PARKING_HOUSING_ITEM_ID
        defaultParkingSectorSpaceShouldBeFound(
            "parkingHousingItemId.in=" + DEFAULT_PARKING_HOUSING_ITEM_ID + "," + UPDATED_PARKING_HOUSING_ITEM_ID
        );

        // Get all the parkingSectorSpaceList where parkingHousingItemId equals to UPDATED_PARKING_HOUSING_ITEM_ID
        defaultParkingSectorSpaceShouldNotBeFound("parkingHousingItemId.in=" + UPDATED_PARKING_HOUSING_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingHousingItemIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingHousingItemId is not null
        defaultParkingSectorSpaceShouldBeFound("parkingHousingItemId.specified=true");

        // Get all the parkingSectorSpaceList where parkingHousingItemId is null
        defaultParkingSectorSpaceShouldNotBeFound("parkingHousingItemId.specified=false");
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingHousingItemIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingHousingItemId is greater than or equal to DEFAULT_PARKING_HOUSING_ITEM_ID
        defaultParkingSectorSpaceShouldBeFound("parkingHousingItemId.greaterThanOrEqual=" + DEFAULT_PARKING_HOUSING_ITEM_ID);

        // Get all the parkingSectorSpaceList where parkingHousingItemId is greater than or equal to UPDATED_PARKING_HOUSING_ITEM_ID
        defaultParkingSectorSpaceShouldNotBeFound("parkingHousingItemId.greaterThanOrEqual=" + UPDATED_PARKING_HOUSING_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingHousingItemIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingHousingItemId is less than or equal to DEFAULT_PARKING_HOUSING_ITEM_ID
        defaultParkingSectorSpaceShouldBeFound("parkingHousingItemId.lessThanOrEqual=" + DEFAULT_PARKING_HOUSING_ITEM_ID);

        // Get all the parkingSectorSpaceList where parkingHousingItemId is less than or equal to SMALLER_PARKING_HOUSING_ITEM_ID
        defaultParkingSectorSpaceShouldNotBeFound("parkingHousingItemId.lessThanOrEqual=" + SMALLER_PARKING_HOUSING_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingHousingItemIdIsLessThanSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingHousingItemId is less than DEFAULT_PARKING_HOUSING_ITEM_ID
        defaultParkingSectorSpaceShouldNotBeFound("parkingHousingItemId.lessThan=" + DEFAULT_PARKING_HOUSING_ITEM_ID);

        // Get all the parkingSectorSpaceList where parkingHousingItemId is less than UPDATED_PARKING_HOUSING_ITEM_ID
        defaultParkingSectorSpaceShouldBeFound("parkingHousingItemId.lessThan=" + UPDATED_PARKING_HOUSING_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingHousingItemIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        // Get all the parkingSectorSpaceList where parkingHousingItemId is greater than DEFAULT_PARKING_HOUSING_ITEM_ID
        defaultParkingSectorSpaceShouldNotBeFound("parkingHousingItemId.greaterThan=" + DEFAULT_PARKING_HOUSING_ITEM_ID);

        // Get all the parkingSectorSpaceList where parkingHousingItemId is greater than SMALLER_PARKING_HOUSING_ITEM_ID
        defaultParkingSectorSpaceShouldBeFound("parkingHousingItemId.greaterThan=" + SMALLER_PARKING_HOUSING_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByHousingVehicleItemIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);
        HousingVehicleItem housingVehicleItem = HousingVehicleItemResourceIT.createEntity(em);
        em.persist(housingVehicleItem);
        em.flush();
        parkingSectorSpace.addHousingVehicleItem(housingVehicleItem);
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);
        Long housingVehicleItemId = housingVehicleItem.getId();

        // Get all the parkingSectorSpaceList where housingVehicleItem equals to housingVehicleItemId
        defaultParkingSectorSpaceShouldBeFound("housingVehicleItemId.equals=" + housingVehicleItemId);

        // Get all the parkingSectorSpaceList where housingVehicleItem equals to (housingVehicleItemId + 1)
        defaultParkingSectorSpaceShouldNotBeFound("housingVehicleItemId.equals=" + (housingVehicleItemId + 1));
    }

    @Test
    @Transactional
    void getAllParkingSectorSpacesByParkingSectorIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);
        ParkingSector parkingSector = ParkingSectorResourceIT.createEntity(em);
        em.persist(parkingSector);
        em.flush();
        parkingSectorSpace.setParkingSector(parkingSector);
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);
        Long parkingSectorId = parkingSector.getId();

        // Get all the parkingSectorSpaceList where parkingSector equals to parkingSectorId
        defaultParkingSectorSpaceShouldBeFound("parkingSectorId.equals=" + parkingSectorId);

        // Get all the parkingSectorSpaceList where parkingSector equals to (parkingSectorId + 1)
        defaultParkingSectorSpaceShouldNotBeFound("parkingSectorId.equals=" + (parkingSectorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultParkingSectorSpaceShouldBeFound(String filter) throws Exception {
        restParkingSectorSpaceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parkingSectorSpace.getId().intValue())))
            .andExpect(jsonPath("$.[*].parkingNumber").value(hasItem(DEFAULT_PARKING_NUMBER)))
            .andExpect(jsonPath("$.[*].parkingStatus").value(hasItem(DEFAULT_PARKING_STATUS.toString())))
            .andExpect(jsonPath("$.[*].parkingEntryDate").value(hasItem(DEFAULT_PARKING_ENTRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].parkingDepartureDate").value(hasItem(DEFAULT_PARKING_DEPARTURE_DATE.toString())))
            .andExpect(jsonPath("$.[*].parkingHousingItemId").value(hasItem(DEFAULT_PARKING_HOUSING_ITEM_ID.intValue())));

        // Check, that the count call also returns 1
        restParkingSectorSpaceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultParkingSectorSpaceShouldNotBeFound(String filter) throws Exception {
        restParkingSectorSpaceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParkingSectorSpaceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingParkingSectorSpace() throws Exception {
        // Get the parkingSectorSpace
        restParkingSectorSpaceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewParkingSectorSpace() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        int databaseSizeBeforeUpdate = parkingSectorSpaceRepository.findAll().size();

        // Update the parkingSectorSpace
        ParkingSectorSpace updatedParkingSectorSpace = parkingSectorSpaceRepository.findById(parkingSectorSpace.getId()).get();
        // Disconnect from session so that the updates on updatedParkingSectorSpace are not directly saved in db
        em.detach(updatedParkingSectorSpace);
        updatedParkingSectorSpace
            .parkingNumber(UPDATED_PARKING_NUMBER)
            .parkingStatus(UPDATED_PARKING_STATUS)
            .parkingEntryDate(UPDATED_PARKING_ENTRY_DATE)
            .parkingDepartureDate(UPDATED_PARKING_DEPARTURE_DATE)
            .parkingHousingItemId(UPDATED_PARKING_HOUSING_ITEM_ID);
        ParkingSectorSpaceDTO parkingSectorSpaceDTO = parkingSectorSpaceMapper.toDto(updatedParkingSectorSpace);

        restParkingSectorSpaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parkingSectorSpaceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parkingSectorSpaceDTO))
            )
            .andExpect(status().isOk());

        // Validate the ParkingSectorSpace in the database
        List<ParkingSectorSpace> parkingSectorSpaceList = parkingSectorSpaceRepository.findAll();
        assertThat(parkingSectorSpaceList).hasSize(databaseSizeBeforeUpdate);
        ParkingSectorSpace testParkingSectorSpace = parkingSectorSpaceList.get(parkingSectorSpaceList.size() - 1);
        assertThat(testParkingSectorSpace.getParkingNumber()).isEqualTo(UPDATED_PARKING_NUMBER);
        assertThat(testParkingSectorSpace.getParkingStatus()).isEqualTo(UPDATED_PARKING_STATUS);
        assertThat(testParkingSectorSpace.getParkingEntryDate()).isEqualTo(UPDATED_PARKING_ENTRY_DATE);
        assertThat(testParkingSectorSpace.getParkingDepartureDate()).isEqualTo(UPDATED_PARKING_DEPARTURE_DATE);
        assertThat(testParkingSectorSpace.getParkingHousingItemId()).isEqualTo(UPDATED_PARKING_HOUSING_ITEM_ID);
    }

    @Test
    @Transactional
    void putNonExistingParkingSectorSpace() throws Exception {
        int databaseSizeBeforeUpdate = parkingSectorSpaceRepository.findAll().size();
        parkingSectorSpace.setId(count.incrementAndGet());

        // Create the ParkingSectorSpace
        ParkingSectorSpaceDTO parkingSectorSpaceDTO = parkingSectorSpaceMapper.toDto(parkingSectorSpace);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParkingSectorSpaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parkingSectorSpaceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parkingSectorSpaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParkingSectorSpace in the database
        List<ParkingSectorSpace> parkingSectorSpaceList = parkingSectorSpaceRepository.findAll();
        assertThat(parkingSectorSpaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParkingSectorSpace() throws Exception {
        int databaseSizeBeforeUpdate = parkingSectorSpaceRepository.findAll().size();
        parkingSectorSpace.setId(count.incrementAndGet());

        // Create the ParkingSectorSpace
        ParkingSectorSpaceDTO parkingSectorSpaceDTO = parkingSectorSpaceMapper.toDto(parkingSectorSpace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkingSectorSpaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parkingSectorSpaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParkingSectorSpace in the database
        List<ParkingSectorSpace> parkingSectorSpaceList = parkingSectorSpaceRepository.findAll();
        assertThat(parkingSectorSpaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParkingSectorSpace() throws Exception {
        int databaseSizeBeforeUpdate = parkingSectorSpaceRepository.findAll().size();
        parkingSectorSpace.setId(count.incrementAndGet());

        // Create the ParkingSectorSpace
        ParkingSectorSpaceDTO parkingSectorSpaceDTO = parkingSectorSpaceMapper.toDto(parkingSectorSpace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkingSectorSpaceMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parkingSectorSpaceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParkingSectorSpace in the database
        List<ParkingSectorSpace> parkingSectorSpaceList = parkingSectorSpaceRepository.findAll();
        assertThat(parkingSectorSpaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParkingSectorSpaceWithPatch() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        int databaseSizeBeforeUpdate = parkingSectorSpaceRepository.findAll().size();

        // Update the parkingSectorSpace using partial update
        ParkingSectorSpace partialUpdatedParkingSectorSpace = new ParkingSectorSpace();
        partialUpdatedParkingSectorSpace.setId(parkingSectorSpace.getId());

        partialUpdatedParkingSectorSpace
            .parkingEntryDate(UPDATED_PARKING_ENTRY_DATE)
            .parkingDepartureDate(UPDATED_PARKING_DEPARTURE_DATE)
            .parkingHousingItemId(UPDATED_PARKING_HOUSING_ITEM_ID);

        restParkingSectorSpaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParkingSectorSpace.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParkingSectorSpace))
            )
            .andExpect(status().isOk());

        // Validate the ParkingSectorSpace in the database
        List<ParkingSectorSpace> parkingSectorSpaceList = parkingSectorSpaceRepository.findAll();
        assertThat(parkingSectorSpaceList).hasSize(databaseSizeBeforeUpdate);
        ParkingSectorSpace testParkingSectorSpace = parkingSectorSpaceList.get(parkingSectorSpaceList.size() - 1);
        assertThat(testParkingSectorSpace.getParkingNumber()).isEqualTo(DEFAULT_PARKING_NUMBER);
        assertThat(testParkingSectorSpace.getParkingStatus()).isEqualTo(DEFAULT_PARKING_STATUS);
        assertThat(testParkingSectorSpace.getParkingEntryDate()).isEqualTo(UPDATED_PARKING_ENTRY_DATE);
        assertThat(testParkingSectorSpace.getParkingDepartureDate()).isEqualTo(UPDATED_PARKING_DEPARTURE_DATE);
        assertThat(testParkingSectorSpace.getParkingHousingItemId()).isEqualTo(UPDATED_PARKING_HOUSING_ITEM_ID);
    }

    @Test
    @Transactional
    void fullUpdateParkingSectorSpaceWithPatch() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        int databaseSizeBeforeUpdate = parkingSectorSpaceRepository.findAll().size();

        // Update the parkingSectorSpace using partial update
        ParkingSectorSpace partialUpdatedParkingSectorSpace = new ParkingSectorSpace();
        partialUpdatedParkingSectorSpace.setId(parkingSectorSpace.getId());

        partialUpdatedParkingSectorSpace
            .parkingNumber(UPDATED_PARKING_NUMBER)
            .parkingStatus(UPDATED_PARKING_STATUS)
            .parkingEntryDate(UPDATED_PARKING_ENTRY_DATE)
            .parkingDepartureDate(UPDATED_PARKING_DEPARTURE_DATE)
            .parkingHousingItemId(UPDATED_PARKING_HOUSING_ITEM_ID);

        restParkingSectorSpaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParkingSectorSpace.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParkingSectorSpace))
            )
            .andExpect(status().isOk());

        // Validate the ParkingSectorSpace in the database
        List<ParkingSectorSpace> parkingSectorSpaceList = parkingSectorSpaceRepository.findAll();
        assertThat(parkingSectorSpaceList).hasSize(databaseSizeBeforeUpdate);
        ParkingSectorSpace testParkingSectorSpace = parkingSectorSpaceList.get(parkingSectorSpaceList.size() - 1);
        assertThat(testParkingSectorSpace.getParkingNumber()).isEqualTo(UPDATED_PARKING_NUMBER);
        assertThat(testParkingSectorSpace.getParkingStatus()).isEqualTo(UPDATED_PARKING_STATUS);
        assertThat(testParkingSectorSpace.getParkingEntryDate()).isEqualTo(UPDATED_PARKING_ENTRY_DATE);
        assertThat(testParkingSectorSpace.getParkingDepartureDate()).isEqualTo(UPDATED_PARKING_DEPARTURE_DATE);
        assertThat(testParkingSectorSpace.getParkingHousingItemId()).isEqualTo(UPDATED_PARKING_HOUSING_ITEM_ID);
    }

    @Test
    @Transactional
    void patchNonExistingParkingSectorSpace() throws Exception {
        int databaseSizeBeforeUpdate = parkingSectorSpaceRepository.findAll().size();
        parkingSectorSpace.setId(count.incrementAndGet());

        // Create the ParkingSectorSpace
        ParkingSectorSpaceDTO parkingSectorSpaceDTO = parkingSectorSpaceMapper.toDto(parkingSectorSpace);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParkingSectorSpaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parkingSectorSpaceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parkingSectorSpaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParkingSectorSpace in the database
        List<ParkingSectorSpace> parkingSectorSpaceList = parkingSectorSpaceRepository.findAll();
        assertThat(parkingSectorSpaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParkingSectorSpace() throws Exception {
        int databaseSizeBeforeUpdate = parkingSectorSpaceRepository.findAll().size();
        parkingSectorSpace.setId(count.incrementAndGet());

        // Create the ParkingSectorSpace
        ParkingSectorSpaceDTO parkingSectorSpaceDTO = parkingSectorSpaceMapper.toDto(parkingSectorSpace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkingSectorSpaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parkingSectorSpaceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParkingSectorSpace in the database
        List<ParkingSectorSpace> parkingSectorSpaceList = parkingSectorSpaceRepository.findAll();
        assertThat(parkingSectorSpaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParkingSectorSpace() throws Exception {
        int databaseSizeBeforeUpdate = parkingSectorSpaceRepository.findAll().size();
        parkingSectorSpace.setId(count.incrementAndGet());

        // Create the ParkingSectorSpace
        ParkingSectorSpaceDTO parkingSectorSpaceDTO = parkingSectorSpaceMapper.toDto(parkingSectorSpace);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParkingSectorSpaceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parkingSectorSpaceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParkingSectorSpace in the database
        List<ParkingSectorSpace> parkingSectorSpaceList = parkingSectorSpaceRepository.findAll();
        assertThat(parkingSectorSpaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParkingSectorSpace() throws Exception {
        // Initialize the database
        parkingSectorSpaceRepository.saveAndFlush(parkingSectorSpace);

        int databaseSizeBeforeDelete = parkingSectorSpaceRepository.findAll().size();

        // Delete the parkingSectorSpace
        restParkingSectorSpaceMockMvc
            .perform(delete(ENTITY_API_URL_ID, parkingSectorSpace.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ParkingSectorSpace> parkingSectorSpaceList = parkingSectorSpaceRepository.findAll();
        assertThat(parkingSectorSpaceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
