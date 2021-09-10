package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.VehicleControlExpenses;
import com.genesisoft.transporte.domain.VehicleControlItem;
import com.genesisoft.transporte.domain.VehicleControls;
import com.genesisoft.transporte.domain.VehicleInspections;
import com.genesisoft.transporte.domain.enumeration.StatusType;
import com.genesisoft.transporte.domain.enumeration.VehicleType;
import com.genesisoft.transporte.repository.VehicleControlItemRepository;
import com.genesisoft.transporte.service.criteria.VehicleControlItemCriteria;
import com.genesisoft.transporte.service.dto.VehicleControlItemDTO;
import com.genesisoft.transporte.service.mapper.VehicleControlItemMapper;
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
 * Integration tests for the {@link VehicleControlItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VehicleControlItemResourceIT {

    private static final StatusType DEFAULT_VEHICLE_CONTROL_STATUS = StatusType.APPROVED;
    private static final StatusType UPDATED_VEHICLE_CONTROL_STATUS = StatusType.PENDENT;

    private static final String DEFAULT_VEHICLE_CONTROL_ITEM_PLATE = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_CONTROL_ITEM_PLATE = "BBBBBBBBBB";

    private static final VehicleType DEFAULT_VEHICLE_CONTROL_ITEM_TYPE = VehicleType.MOTORBIKE;
    private static final VehicleType UPDATED_VEHICLE_CONTROL_ITEM_TYPE = VehicleType.CAR;

    private static final String DEFAULT_VEHICLE_CONTROL_ITEM_FIPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_CONTROL_ITEM_FIPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_VEHICLE_CONTROL_ITEM_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_CONTROL_ITEM_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_VEHICLE_CONTROL_ITEM_FUEL = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_CONTROL_ITEM_FUEL = "BBBBBBBBBB";

    private static final String DEFAULT_VEHICLE_CONTROL_ITEM_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_CONTROL_ITEM_BRANCH = "BBBBBBBBBB";

    private static final String DEFAULT_VEHICLE_CONTROL_ITEM_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_CONTROL_ITEM_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION = "BBBBBBBBBB";

    private static final String DEFAULT_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH = "BBBBBBBBBB";

    private static final Float DEFAULT_VEHICLE_CONTROL_ITEM_VALUE = 1F;
    private static final Float UPDATED_VEHICLE_CONTROL_ITEM_VALUE = 2F;
    private static final Float SMALLER_VEHICLE_CONTROL_ITEM_VALUE = 1F - 1F;

    private static final Float DEFAULT_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE = 1F;
    private static final Float UPDATED_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE = 2F;
    private static final Float SMALLER_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE = 1F - 1F;

    private static final String DEFAULT_VEHICLE_CONTROL_ITEM_CTE = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_CONTROL_ITEM_CTE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VEHICLE_CONTROL_ITEM_CTE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VEHICLE_CONTROL_ITEM_CTE_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/vehicle-control-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VehicleControlItemRepository vehicleControlItemRepository;

    @Autowired
    private VehicleControlItemMapper vehicleControlItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehicleControlItemMockMvc;

    private VehicleControlItem vehicleControlItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleControlItem createEntity(EntityManager em) {
        VehicleControlItem vehicleControlItem = new VehicleControlItem()
            .vehicleControlStatus(DEFAULT_VEHICLE_CONTROL_STATUS)
            .vehicleControlItemPlate(DEFAULT_VEHICLE_CONTROL_ITEM_PLATE)
            .vehicleControlItemType(DEFAULT_VEHICLE_CONTROL_ITEM_TYPE)
            .vehicleControlItemFipeCode(DEFAULT_VEHICLE_CONTROL_ITEM_FIPE_CODE)
            .vehicleControlItemYear(DEFAULT_VEHICLE_CONTROL_ITEM_YEAR)
            .vehicleControlItemFuel(DEFAULT_VEHICLE_CONTROL_ITEM_FUEL)
            .vehicleControlItemBranch(DEFAULT_VEHICLE_CONTROL_ITEM_BRANCH)
            .vehicleControlItemModel(DEFAULT_VEHICLE_CONTROL_ITEM_MODEL)
            .vehicleControlItemFuelAbbreviation(DEFAULT_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION)
            .vehicleControlItemReferenceMonth(DEFAULT_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH)
            .vehicleControlItemValue(DEFAULT_VEHICLE_CONTROL_ITEM_VALUE)
            .vehicleControlItemShippingValue(DEFAULT_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE)
            .vehicleControlItemCTE(DEFAULT_VEHICLE_CONTROL_ITEM_CTE)
            .vehicleControlItemCTEDate(DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE);
        // Add required entity
        VehicleControls vehicleControls;
        if (TestUtil.findAll(em, VehicleControls.class).isEmpty()) {
            vehicleControls = VehicleControlsResourceIT.createEntity(em);
            em.persist(vehicleControls);
            em.flush();
        } else {
            vehicleControls = TestUtil.findAll(em, VehicleControls.class).get(0);
        }
        vehicleControlItem.setVehicleControls(vehicleControls);
        return vehicleControlItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleControlItem createUpdatedEntity(EntityManager em) {
        VehicleControlItem vehicleControlItem = new VehicleControlItem()
            .vehicleControlStatus(UPDATED_VEHICLE_CONTROL_STATUS)
            .vehicleControlItemPlate(UPDATED_VEHICLE_CONTROL_ITEM_PLATE)
            .vehicleControlItemType(UPDATED_VEHICLE_CONTROL_ITEM_TYPE)
            .vehicleControlItemFipeCode(UPDATED_VEHICLE_CONTROL_ITEM_FIPE_CODE)
            .vehicleControlItemYear(UPDATED_VEHICLE_CONTROL_ITEM_YEAR)
            .vehicleControlItemFuel(UPDATED_VEHICLE_CONTROL_ITEM_FUEL)
            .vehicleControlItemBranch(UPDATED_VEHICLE_CONTROL_ITEM_BRANCH)
            .vehicleControlItemModel(UPDATED_VEHICLE_CONTROL_ITEM_MODEL)
            .vehicleControlItemFuelAbbreviation(UPDATED_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION)
            .vehicleControlItemReferenceMonth(UPDATED_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH)
            .vehicleControlItemValue(UPDATED_VEHICLE_CONTROL_ITEM_VALUE)
            .vehicleControlItemShippingValue(UPDATED_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE)
            .vehicleControlItemCTE(UPDATED_VEHICLE_CONTROL_ITEM_CTE)
            .vehicleControlItemCTEDate(UPDATED_VEHICLE_CONTROL_ITEM_CTE_DATE);
        // Add required entity
        VehicleControls vehicleControls;
        if (TestUtil.findAll(em, VehicleControls.class).isEmpty()) {
            vehicleControls = VehicleControlsResourceIT.createUpdatedEntity(em);
            em.persist(vehicleControls);
            em.flush();
        } else {
            vehicleControls = TestUtil.findAll(em, VehicleControls.class).get(0);
        }
        vehicleControlItem.setVehicleControls(vehicleControls);
        return vehicleControlItem;
    }

    @BeforeEach
    public void initTest() {
        vehicleControlItem = createEntity(em);
    }

    @Test
    @Transactional
    void createVehicleControlItem() throws Exception {
        int databaseSizeBeforeCreate = vehicleControlItemRepository.findAll().size();
        // Create the VehicleControlItem
        VehicleControlItemDTO vehicleControlItemDTO = vehicleControlItemMapper.toDto(vehicleControlItem);
        restVehicleControlItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VehicleControlItem in the database
        List<VehicleControlItem> vehicleControlItemList = vehicleControlItemRepository.findAll();
        assertThat(vehicleControlItemList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleControlItem testVehicleControlItem = vehicleControlItemList.get(vehicleControlItemList.size() - 1);
        assertThat(testVehicleControlItem.getVehicleControlStatus()).isEqualTo(DEFAULT_VEHICLE_CONTROL_STATUS);
        assertThat(testVehicleControlItem.getVehicleControlItemPlate()).isEqualTo(DEFAULT_VEHICLE_CONTROL_ITEM_PLATE);
        assertThat(testVehicleControlItem.getVehicleControlItemType()).isEqualTo(DEFAULT_VEHICLE_CONTROL_ITEM_TYPE);
        assertThat(testVehicleControlItem.getVehicleControlItemFipeCode()).isEqualTo(DEFAULT_VEHICLE_CONTROL_ITEM_FIPE_CODE);
        assertThat(testVehicleControlItem.getVehicleControlItemYear()).isEqualTo(DEFAULT_VEHICLE_CONTROL_ITEM_YEAR);
        assertThat(testVehicleControlItem.getVehicleControlItemFuel()).isEqualTo(DEFAULT_VEHICLE_CONTROL_ITEM_FUEL);
        assertThat(testVehicleControlItem.getVehicleControlItemBranch()).isEqualTo(DEFAULT_VEHICLE_CONTROL_ITEM_BRANCH);
        assertThat(testVehicleControlItem.getVehicleControlItemModel()).isEqualTo(DEFAULT_VEHICLE_CONTROL_ITEM_MODEL);
        assertThat(testVehicleControlItem.getVehicleControlItemFuelAbbreviation())
            .isEqualTo(DEFAULT_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION);
        assertThat(testVehicleControlItem.getVehicleControlItemReferenceMonth()).isEqualTo(DEFAULT_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH);
        assertThat(testVehicleControlItem.getVehicleControlItemValue()).isEqualTo(DEFAULT_VEHICLE_CONTROL_ITEM_VALUE);
        assertThat(testVehicleControlItem.getVehicleControlItemShippingValue()).isEqualTo(DEFAULT_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE);
        assertThat(testVehicleControlItem.getVehicleControlItemCTE()).isEqualTo(DEFAULT_VEHICLE_CONTROL_ITEM_CTE);
        assertThat(testVehicleControlItem.getVehicleControlItemCTEDate()).isEqualTo(DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE);
    }

    @Test
    @Transactional
    void createVehicleControlItemWithExistingId() throws Exception {
        // Create the VehicleControlItem with an existing ID
        vehicleControlItem.setId(1L);
        VehicleControlItemDTO vehicleControlItemDTO = vehicleControlItemMapper.toDto(vehicleControlItem);

        int databaseSizeBeforeCreate = vehicleControlItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleControlItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlItem in the database
        List<VehicleControlItem> vehicleControlItemList = vehicleControlItemRepository.findAll();
        assertThat(vehicleControlItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVehicleControlStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleControlItemRepository.findAll().size();
        // set the field null
        vehicleControlItem.setVehicleControlStatus(null);

        // Create the VehicleControlItem, which fails.
        VehicleControlItemDTO vehicleControlItemDTO = vehicleControlItemMapper.toDto(vehicleControlItem);

        restVehicleControlItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleControlItem> vehicleControlItemList = vehicleControlItemRepository.findAll();
        assertThat(vehicleControlItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVehicleControlItemPlateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleControlItemRepository.findAll().size();
        // set the field null
        vehicleControlItem.setVehicleControlItemPlate(null);

        // Create the VehicleControlItem, which fails.
        VehicleControlItemDTO vehicleControlItemDTO = vehicleControlItemMapper.toDto(vehicleControlItem);

        restVehicleControlItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleControlItem> vehicleControlItemList = vehicleControlItemRepository.findAll();
        assertThat(vehicleControlItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVehicleControlItemTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleControlItemRepository.findAll().size();
        // set the field null
        vehicleControlItem.setVehicleControlItemType(null);

        // Create the VehicleControlItem, which fails.
        VehicleControlItemDTO vehicleControlItemDTO = vehicleControlItemMapper.toDto(vehicleControlItem);

        restVehicleControlItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleControlItem> vehicleControlItemList = vehicleControlItemRepository.findAll();
        assertThat(vehicleControlItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVehicleControlItemValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleControlItemRepository.findAll().size();
        // set the field null
        vehicleControlItem.setVehicleControlItemValue(null);

        // Create the VehicleControlItem, which fails.
        VehicleControlItemDTO vehicleControlItemDTO = vehicleControlItemMapper.toDto(vehicleControlItem);

        restVehicleControlItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleControlItem> vehicleControlItemList = vehicleControlItemRepository.findAll();
        assertThat(vehicleControlItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVehicleControlItemShippingValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleControlItemRepository.findAll().size();
        // set the field null
        vehicleControlItem.setVehicleControlItemShippingValue(null);

        // Create the VehicleControlItem, which fails.
        VehicleControlItemDTO vehicleControlItemDTO = vehicleControlItemMapper.toDto(vehicleControlItem);

        restVehicleControlItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleControlItem> vehicleControlItemList = vehicleControlItemRepository.findAll();
        assertThat(vehicleControlItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVehicleControlItems() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList
        restVehicleControlItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleControlItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleControlStatus").value(hasItem(DEFAULT_VEHICLE_CONTROL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].vehicleControlItemPlate").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_PLATE)))
            .andExpect(jsonPath("$.[*].vehicleControlItemType").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].vehicleControlItemFipeCode").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_FIPE_CODE)))
            .andExpect(jsonPath("$.[*].vehicleControlItemYear").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_YEAR)))
            .andExpect(jsonPath("$.[*].vehicleControlItemFuel").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_FUEL)))
            .andExpect(jsonPath("$.[*].vehicleControlItemBranch").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_BRANCH)))
            .andExpect(jsonPath("$.[*].vehicleControlItemModel").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_MODEL)))
            .andExpect(jsonPath("$.[*].vehicleControlItemFuelAbbreviation").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION)))
            .andExpect(jsonPath("$.[*].vehicleControlItemReferenceMonth").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH)))
            .andExpect(jsonPath("$.[*].vehicleControlItemValue").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_VALUE.doubleValue())))
            .andExpect(
                jsonPath("$.[*].vehicleControlItemShippingValue").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE.doubleValue()))
            )
            .andExpect(jsonPath("$.[*].vehicleControlItemCTE").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_CTE)))
            .andExpect(jsonPath("$.[*].vehicleControlItemCTEDate").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE.toString())));
    }

    @Test
    @Transactional
    void getVehicleControlItem() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get the vehicleControlItem
        restVehicleControlItemMockMvc
            .perform(get(ENTITY_API_URL_ID, vehicleControlItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleControlItem.getId().intValue()))
            .andExpect(jsonPath("$.vehicleControlStatus").value(DEFAULT_VEHICLE_CONTROL_STATUS.toString()))
            .andExpect(jsonPath("$.vehicleControlItemPlate").value(DEFAULT_VEHICLE_CONTROL_ITEM_PLATE))
            .andExpect(jsonPath("$.vehicleControlItemType").value(DEFAULT_VEHICLE_CONTROL_ITEM_TYPE.toString()))
            .andExpect(jsonPath("$.vehicleControlItemFipeCode").value(DEFAULT_VEHICLE_CONTROL_ITEM_FIPE_CODE))
            .andExpect(jsonPath("$.vehicleControlItemYear").value(DEFAULT_VEHICLE_CONTROL_ITEM_YEAR))
            .andExpect(jsonPath("$.vehicleControlItemFuel").value(DEFAULT_VEHICLE_CONTROL_ITEM_FUEL))
            .andExpect(jsonPath("$.vehicleControlItemBranch").value(DEFAULT_VEHICLE_CONTROL_ITEM_BRANCH))
            .andExpect(jsonPath("$.vehicleControlItemModel").value(DEFAULT_VEHICLE_CONTROL_ITEM_MODEL))
            .andExpect(jsonPath("$.vehicleControlItemFuelAbbreviation").value(DEFAULT_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION))
            .andExpect(jsonPath("$.vehicleControlItemReferenceMonth").value(DEFAULT_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH))
            .andExpect(jsonPath("$.vehicleControlItemValue").value(DEFAULT_VEHICLE_CONTROL_ITEM_VALUE.doubleValue()))
            .andExpect(jsonPath("$.vehicleControlItemShippingValue").value(DEFAULT_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE.doubleValue()))
            .andExpect(jsonPath("$.vehicleControlItemCTE").value(DEFAULT_VEHICLE_CONTROL_ITEM_CTE))
            .andExpect(jsonPath("$.vehicleControlItemCTEDate").value(DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE.toString()));
    }

    @Test
    @Transactional
    void getVehicleControlItemsByIdFiltering() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        Long id = vehicleControlItem.getId();

        defaultVehicleControlItemShouldBeFound("id.equals=" + id);
        defaultVehicleControlItemShouldNotBeFound("id.notEquals=" + id);

        defaultVehicleControlItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVehicleControlItemShouldNotBeFound("id.greaterThan=" + id);

        defaultVehicleControlItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVehicleControlItemShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlStatus equals to DEFAULT_VEHICLE_CONTROL_STATUS
        defaultVehicleControlItemShouldBeFound("vehicleControlStatus.equals=" + DEFAULT_VEHICLE_CONTROL_STATUS);

        // Get all the vehicleControlItemList where vehicleControlStatus equals to UPDATED_VEHICLE_CONTROL_STATUS
        defaultVehicleControlItemShouldNotBeFound("vehicleControlStatus.equals=" + UPDATED_VEHICLE_CONTROL_STATUS);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlStatus not equals to DEFAULT_VEHICLE_CONTROL_STATUS
        defaultVehicleControlItemShouldNotBeFound("vehicleControlStatus.notEquals=" + DEFAULT_VEHICLE_CONTROL_STATUS);

        // Get all the vehicleControlItemList where vehicleControlStatus not equals to UPDATED_VEHICLE_CONTROL_STATUS
        defaultVehicleControlItemShouldBeFound("vehicleControlStatus.notEquals=" + UPDATED_VEHICLE_CONTROL_STATUS);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlStatusIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlStatus in DEFAULT_VEHICLE_CONTROL_STATUS or UPDATED_VEHICLE_CONTROL_STATUS
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlStatus.in=" + DEFAULT_VEHICLE_CONTROL_STATUS + "," + UPDATED_VEHICLE_CONTROL_STATUS
        );

        // Get all the vehicleControlItemList where vehicleControlStatus equals to UPDATED_VEHICLE_CONTROL_STATUS
        defaultVehicleControlItemShouldNotBeFound("vehicleControlStatus.in=" + UPDATED_VEHICLE_CONTROL_STATUS);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlStatus is not null
        defaultVehicleControlItemShouldBeFound("vehicleControlStatus.specified=true");

        // Get all the vehicleControlItemList where vehicleControlStatus is null
        defaultVehicleControlItemShouldNotBeFound("vehicleControlStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemPlateIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemPlate equals to DEFAULT_VEHICLE_CONTROL_ITEM_PLATE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemPlate.equals=" + DEFAULT_VEHICLE_CONTROL_ITEM_PLATE);

        // Get all the vehicleControlItemList where vehicleControlItemPlate equals to UPDATED_VEHICLE_CONTROL_ITEM_PLATE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemPlate.equals=" + UPDATED_VEHICLE_CONTROL_ITEM_PLATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemPlateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemPlate not equals to DEFAULT_VEHICLE_CONTROL_ITEM_PLATE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemPlate.notEquals=" + DEFAULT_VEHICLE_CONTROL_ITEM_PLATE);

        // Get all the vehicleControlItemList where vehicleControlItemPlate not equals to UPDATED_VEHICLE_CONTROL_ITEM_PLATE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemPlate.notEquals=" + UPDATED_VEHICLE_CONTROL_ITEM_PLATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemPlateIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemPlate in DEFAULT_VEHICLE_CONTROL_ITEM_PLATE or UPDATED_VEHICLE_CONTROL_ITEM_PLATE
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemPlate.in=" + DEFAULT_VEHICLE_CONTROL_ITEM_PLATE + "," + UPDATED_VEHICLE_CONTROL_ITEM_PLATE
        );

        // Get all the vehicleControlItemList where vehicleControlItemPlate equals to UPDATED_VEHICLE_CONTROL_ITEM_PLATE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemPlate.in=" + UPDATED_VEHICLE_CONTROL_ITEM_PLATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemPlateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemPlate is not null
        defaultVehicleControlItemShouldBeFound("vehicleControlItemPlate.specified=true");

        // Get all the vehicleControlItemList where vehicleControlItemPlate is null
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemPlate.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemPlateContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemPlate contains DEFAULT_VEHICLE_CONTROL_ITEM_PLATE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemPlate.contains=" + DEFAULT_VEHICLE_CONTROL_ITEM_PLATE);

        // Get all the vehicleControlItemList where vehicleControlItemPlate contains UPDATED_VEHICLE_CONTROL_ITEM_PLATE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemPlate.contains=" + UPDATED_VEHICLE_CONTROL_ITEM_PLATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemPlateNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemPlate does not contain DEFAULT_VEHICLE_CONTROL_ITEM_PLATE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemPlate.doesNotContain=" + DEFAULT_VEHICLE_CONTROL_ITEM_PLATE);

        // Get all the vehicleControlItemList where vehicleControlItemPlate does not contain UPDATED_VEHICLE_CONTROL_ITEM_PLATE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemPlate.doesNotContain=" + UPDATED_VEHICLE_CONTROL_ITEM_PLATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemType equals to DEFAULT_VEHICLE_CONTROL_ITEM_TYPE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemType.equals=" + DEFAULT_VEHICLE_CONTROL_ITEM_TYPE);

        // Get all the vehicleControlItemList where vehicleControlItemType equals to UPDATED_VEHICLE_CONTROL_ITEM_TYPE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemType.equals=" + UPDATED_VEHICLE_CONTROL_ITEM_TYPE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemType not equals to DEFAULT_VEHICLE_CONTROL_ITEM_TYPE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemType.notEquals=" + DEFAULT_VEHICLE_CONTROL_ITEM_TYPE);

        // Get all the vehicleControlItemList where vehicleControlItemType not equals to UPDATED_VEHICLE_CONTROL_ITEM_TYPE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemType.notEquals=" + UPDATED_VEHICLE_CONTROL_ITEM_TYPE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemTypeIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemType in DEFAULT_VEHICLE_CONTROL_ITEM_TYPE or UPDATED_VEHICLE_CONTROL_ITEM_TYPE
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemType.in=" + DEFAULT_VEHICLE_CONTROL_ITEM_TYPE + "," + UPDATED_VEHICLE_CONTROL_ITEM_TYPE
        );

        // Get all the vehicleControlItemList where vehicleControlItemType equals to UPDATED_VEHICLE_CONTROL_ITEM_TYPE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemType.in=" + UPDATED_VEHICLE_CONTROL_ITEM_TYPE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemType is not null
        defaultVehicleControlItemShouldBeFound("vehicleControlItemType.specified=true");

        // Get all the vehicleControlItemList where vehicleControlItemType is null
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemType.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemFipeCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemFipeCode equals to DEFAULT_VEHICLE_CONTROL_ITEM_FIPE_CODE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemFipeCode.equals=" + DEFAULT_VEHICLE_CONTROL_ITEM_FIPE_CODE);

        // Get all the vehicleControlItemList where vehicleControlItemFipeCode equals to UPDATED_VEHICLE_CONTROL_ITEM_FIPE_CODE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemFipeCode.equals=" + UPDATED_VEHICLE_CONTROL_ITEM_FIPE_CODE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemFipeCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemFipeCode not equals to DEFAULT_VEHICLE_CONTROL_ITEM_FIPE_CODE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemFipeCode.notEquals=" + DEFAULT_VEHICLE_CONTROL_ITEM_FIPE_CODE);

        // Get all the vehicleControlItemList where vehicleControlItemFipeCode not equals to UPDATED_VEHICLE_CONTROL_ITEM_FIPE_CODE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemFipeCode.notEquals=" + UPDATED_VEHICLE_CONTROL_ITEM_FIPE_CODE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemFipeCodeIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemFipeCode in DEFAULT_VEHICLE_CONTROL_ITEM_FIPE_CODE or UPDATED_VEHICLE_CONTROL_ITEM_FIPE_CODE
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemFipeCode.in=" + DEFAULT_VEHICLE_CONTROL_ITEM_FIPE_CODE + "," + UPDATED_VEHICLE_CONTROL_ITEM_FIPE_CODE
        );

        // Get all the vehicleControlItemList where vehicleControlItemFipeCode equals to UPDATED_VEHICLE_CONTROL_ITEM_FIPE_CODE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemFipeCode.in=" + UPDATED_VEHICLE_CONTROL_ITEM_FIPE_CODE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemFipeCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemFipeCode is not null
        defaultVehicleControlItemShouldBeFound("vehicleControlItemFipeCode.specified=true");

        // Get all the vehicleControlItemList where vehicleControlItemFipeCode is null
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemFipeCode.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemFipeCodeContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemFipeCode contains DEFAULT_VEHICLE_CONTROL_ITEM_FIPE_CODE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemFipeCode.contains=" + DEFAULT_VEHICLE_CONTROL_ITEM_FIPE_CODE);

        // Get all the vehicleControlItemList where vehicleControlItemFipeCode contains UPDATED_VEHICLE_CONTROL_ITEM_FIPE_CODE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemFipeCode.contains=" + UPDATED_VEHICLE_CONTROL_ITEM_FIPE_CODE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemFipeCodeNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemFipeCode does not contain DEFAULT_VEHICLE_CONTROL_ITEM_FIPE_CODE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemFipeCode.doesNotContain=" + DEFAULT_VEHICLE_CONTROL_ITEM_FIPE_CODE);

        // Get all the vehicleControlItemList where vehicleControlItemFipeCode does not contain UPDATED_VEHICLE_CONTROL_ITEM_FIPE_CODE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemFipeCode.doesNotContain=" + UPDATED_VEHICLE_CONTROL_ITEM_FIPE_CODE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemYearIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemYear equals to DEFAULT_VEHICLE_CONTROL_ITEM_YEAR
        defaultVehicleControlItemShouldBeFound("vehicleControlItemYear.equals=" + DEFAULT_VEHICLE_CONTROL_ITEM_YEAR);

        // Get all the vehicleControlItemList where vehicleControlItemYear equals to UPDATED_VEHICLE_CONTROL_ITEM_YEAR
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemYear.equals=" + UPDATED_VEHICLE_CONTROL_ITEM_YEAR);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemYear not equals to DEFAULT_VEHICLE_CONTROL_ITEM_YEAR
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemYear.notEquals=" + DEFAULT_VEHICLE_CONTROL_ITEM_YEAR);

        // Get all the vehicleControlItemList where vehicleControlItemYear not equals to UPDATED_VEHICLE_CONTROL_ITEM_YEAR
        defaultVehicleControlItemShouldBeFound("vehicleControlItemYear.notEquals=" + UPDATED_VEHICLE_CONTROL_ITEM_YEAR);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemYearIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemYear in DEFAULT_VEHICLE_CONTROL_ITEM_YEAR or UPDATED_VEHICLE_CONTROL_ITEM_YEAR
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemYear.in=" + DEFAULT_VEHICLE_CONTROL_ITEM_YEAR + "," + UPDATED_VEHICLE_CONTROL_ITEM_YEAR
        );

        // Get all the vehicleControlItemList where vehicleControlItemYear equals to UPDATED_VEHICLE_CONTROL_ITEM_YEAR
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemYear.in=" + UPDATED_VEHICLE_CONTROL_ITEM_YEAR);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemYear is not null
        defaultVehicleControlItemShouldBeFound("vehicleControlItemYear.specified=true");

        // Get all the vehicleControlItemList where vehicleControlItemYear is null
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemYear.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemYearContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemYear contains DEFAULT_VEHICLE_CONTROL_ITEM_YEAR
        defaultVehicleControlItemShouldBeFound("vehicleControlItemYear.contains=" + DEFAULT_VEHICLE_CONTROL_ITEM_YEAR);

        // Get all the vehicleControlItemList where vehicleControlItemYear contains UPDATED_VEHICLE_CONTROL_ITEM_YEAR
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemYear.contains=" + UPDATED_VEHICLE_CONTROL_ITEM_YEAR);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemYearNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemYear does not contain DEFAULT_VEHICLE_CONTROL_ITEM_YEAR
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemYear.doesNotContain=" + DEFAULT_VEHICLE_CONTROL_ITEM_YEAR);

        // Get all the vehicleControlItemList where vehicleControlItemYear does not contain UPDATED_VEHICLE_CONTROL_ITEM_YEAR
        defaultVehicleControlItemShouldBeFound("vehicleControlItemYear.doesNotContain=" + UPDATED_VEHICLE_CONTROL_ITEM_YEAR);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemFuelIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemFuel equals to DEFAULT_VEHICLE_CONTROL_ITEM_FUEL
        defaultVehicleControlItemShouldBeFound("vehicleControlItemFuel.equals=" + DEFAULT_VEHICLE_CONTROL_ITEM_FUEL);

        // Get all the vehicleControlItemList where vehicleControlItemFuel equals to UPDATED_VEHICLE_CONTROL_ITEM_FUEL
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemFuel.equals=" + UPDATED_VEHICLE_CONTROL_ITEM_FUEL);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemFuelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemFuel not equals to DEFAULT_VEHICLE_CONTROL_ITEM_FUEL
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemFuel.notEquals=" + DEFAULT_VEHICLE_CONTROL_ITEM_FUEL);

        // Get all the vehicleControlItemList where vehicleControlItemFuel not equals to UPDATED_VEHICLE_CONTROL_ITEM_FUEL
        defaultVehicleControlItemShouldBeFound("vehicleControlItemFuel.notEquals=" + UPDATED_VEHICLE_CONTROL_ITEM_FUEL);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemFuelIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemFuel in DEFAULT_VEHICLE_CONTROL_ITEM_FUEL or UPDATED_VEHICLE_CONTROL_ITEM_FUEL
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemFuel.in=" + DEFAULT_VEHICLE_CONTROL_ITEM_FUEL + "," + UPDATED_VEHICLE_CONTROL_ITEM_FUEL
        );

        // Get all the vehicleControlItemList where vehicleControlItemFuel equals to UPDATED_VEHICLE_CONTROL_ITEM_FUEL
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemFuel.in=" + UPDATED_VEHICLE_CONTROL_ITEM_FUEL);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemFuelIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemFuel is not null
        defaultVehicleControlItemShouldBeFound("vehicleControlItemFuel.specified=true");

        // Get all the vehicleControlItemList where vehicleControlItemFuel is null
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemFuel.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemFuelContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemFuel contains DEFAULT_VEHICLE_CONTROL_ITEM_FUEL
        defaultVehicleControlItemShouldBeFound("vehicleControlItemFuel.contains=" + DEFAULT_VEHICLE_CONTROL_ITEM_FUEL);

        // Get all the vehicleControlItemList where vehicleControlItemFuel contains UPDATED_VEHICLE_CONTROL_ITEM_FUEL
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemFuel.contains=" + UPDATED_VEHICLE_CONTROL_ITEM_FUEL);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemFuelNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemFuel does not contain DEFAULT_VEHICLE_CONTROL_ITEM_FUEL
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemFuel.doesNotContain=" + DEFAULT_VEHICLE_CONTROL_ITEM_FUEL);

        // Get all the vehicleControlItemList where vehicleControlItemFuel does not contain UPDATED_VEHICLE_CONTROL_ITEM_FUEL
        defaultVehicleControlItemShouldBeFound("vehicleControlItemFuel.doesNotContain=" + UPDATED_VEHICLE_CONTROL_ITEM_FUEL);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemBranch equals to DEFAULT_VEHICLE_CONTROL_ITEM_BRANCH
        defaultVehicleControlItemShouldBeFound("vehicleControlItemBranch.equals=" + DEFAULT_VEHICLE_CONTROL_ITEM_BRANCH);

        // Get all the vehicleControlItemList where vehicleControlItemBranch equals to UPDATED_VEHICLE_CONTROL_ITEM_BRANCH
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemBranch.equals=" + UPDATED_VEHICLE_CONTROL_ITEM_BRANCH);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemBranchIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemBranch not equals to DEFAULT_VEHICLE_CONTROL_ITEM_BRANCH
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemBranch.notEquals=" + DEFAULT_VEHICLE_CONTROL_ITEM_BRANCH);

        // Get all the vehicleControlItemList where vehicleControlItemBranch not equals to UPDATED_VEHICLE_CONTROL_ITEM_BRANCH
        defaultVehicleControlItemShouldBeFound("vehicleControlItemBranch.notEquals=" + UPDATED_VEHICLE_CONTROL_ITEM_BRANCH);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemBranchIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemBranch in DEFAULT_VEHICLE_CONTROL_ITEM_BRANCH or UPDATED_VEHICLE_CONTROL_ITEM_BRANCH
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemBranch.in=" + DEFAULT_VEHICLE_CONTROL_ITEM_BRANCH + "," + UPDATED_VEHICLE_CONTROL_ITEM_BRANCH
        );

        // Get all the vehicleControlItemList where vehicleControlItemBranch equals to UPDATED_VEHICLE_CONTROL_ITEM_BRANCH
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemBranch.in=" + UPDATED_VEHICLE_CONTROL_ITEM_BRANCH);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemBranchIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemBranch is not null
        defaultVehicleControlItemShouldBeFound("vehicleControlItemBranch.specified=true");

        // Get all the vehicleControlItemList where vehicleControlItemBranch is null
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemBranch.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemBranchContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemBranch contains DEFAULT_VEHICLE_CONTROL_ITEM_BRANCH
        defaultVehicleControlItemShouldBeFound("vehicleControlItemBranch.contains=" + DEFAULT_VEHICLE_CONTROL_ITEM_BRANCH);

        // Get all the vehicleControlItemList where vehicleControlItemBranch contains UPDATED_VEHICLE_CONTROL_ITEM_BRANCH
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemBranch.contains=" + UPDATED_VEHICLE_CONTROL_ITEM_BRANCH);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemBranchNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemBranch does not contain DEFAULT_VEHICLE_CONTROL_ITEM_BRANCH
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemBranch.doesNotContain=" + DEFAULT_VEHICLE_CONTROL_ITEM_BRANCH);

        // Get all the vehicleControlItemList where vehicleControlItemBranch does not contain UPDATED_VEHICLE_CONTROL_ITEM_BRANCH
        defaultVehicleControlItemShouldBeFound("vehicleControlItemBranch.doesNotContain=" + UPDATED_VEHICLE_CONTROL_ITEM_BRANCH);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemModelIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemModel equals to DEFAULT_VEHICLE_CONTROL_ITEM_MODEL
        defaultVehicleControlItemShouldBeFound("vehicleControlItemModel.equals=" + DEFAULT_VEHICLE_CONTROL_ITEM_MODEL);

        // Get all the vehicleControlItemList where vehicleControlItemModel equals to UPDATED_VEHICLE_CONTROL_ITEM_MODEL
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemModel.equals=" + UPDATED_VEHICLE_CONTROL_ITEM_MODEL);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemModelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemModel not equals to DEFAULT_VEHICLE_CONTROL_ITEM_MODEL
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemModel.notEquals=" + DEFAULT_VEHICLE_CONTROL_ITEM_MODEL);

        // Get all the vehicleControlItemList where vehicleControlItemModel not equals to UPDATED_VEHICLE_CONTROL_ITEM_MODEL
        defaultVehicleControlItemShouldBeFound("vehicleControlItemModel.notEquals=" + UPDATED_VEHICLE_CONTROL_ITEM_MODEL);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemModelIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemModel in DEFAULT_VEHICLE_CONTROL_ITEM_MODEL or UPDATED_VEHICLE_CONTROL_ITEM_MODEL
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemModel.in=" + DEFAULT_VEHICLE_CONTROL_ITEM_MODEL + "," + UPDATED_VEHICLE_CONTROL_ITEM_MODEL
        );

        // Get all the vehicleControlItemList where vehicleControlItemModel equals to UPDATED_VEHICLE_CONTROL_ITEM_MODEL
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemModel.in=" + UPDATED_VEHICLE_CONTROL_ITEM_MODEL);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemModelIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemModel is not null
        defaultVehicleControlItemShouldBeFound("vehicleControlItemModel.specified=true");

        // Get all the vehicleControlItemList where vehicleControlItemModel is null
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemModel.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemModelContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemModel contains DEFAULT_VEHICLE_CONTROL_ITEM_MODEL
        defaultVehicleControlItemShouldBeFound("vehicleControlItemModel.contains=" + DEFAULT_VEHICLE_CONTROL_ITEM_MODEL);

        // Get all the vehicleControlItemList where vehicleControlItemModel contains UPDATED_VEHICLE_CONTROL_ITEM_MODEL
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemModel.contains=" + UPDATED_VEHICLE_CONTROL_ITEM_MODEL);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemModelNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemModel does not contain DEFAULT_VEHICLE_CONTROL_ITEM_MODEL
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemModel.doesNotContain=" + DEFAULT_VEHICLE_CONTROL_ITEM_MODEL);

        // Get all the vehicleControlItemList where vehicleControlItemModel does not contain UPDATED_VEHICLE_CONTROL_ITEM_MODEL
        defaultVehicleControlItemShouldBeFound("vehicleControlItemModel.doesNotContain=" + UPDATED_VEHICLE_CONTROL_ITEM_MODEL);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemFuelAbbreviationIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemFuelAbbreviation equals to DEFAULT_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemFuelAbbreviation.equals=" + DEFAULT_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION
        );

        // Get all the vehicleControlItemList where vehicleControlItemFuelAbbreviation equals to UPDATED_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION
        defaultVehicleControlItemShouldNotBeFound(
            "vehicleControlItemFuelAbbreviation.equals=" + UPDATED_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemFuelAbbreviationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemFuelAbbreviation not equals to DEFAULT_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION
        defaultVehicleControlItemShouldNotBeFound(
            "vehicleControlItemFuelAbbreviation.notEquals=" + DEFAULT_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION
        );

        // Get all the vehicleControlItemList where vehicleControlItemFuelAbbreviation not equals to UPDATED_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemFuelAbbreviation.notEquals=" + UPDATED_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemFuelAbbreviationIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemFuelAbbreviation in DEFAULT_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION or UPDATED_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemFuelAbbreviation.in=" +
            DEFAULT_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION +
            "," +
            UPDATED_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION
        );

        // Get all the vehicleControlItemList where vehicleControlItemFuelAbbreviation equals to UPDATED_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION
        defaultVehicleControlItemShouldNotBeFound(
            "vehicleControlItemFuelAbbreviation.in=" + UPDATED_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemFuelAbbreviationIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemFuelAbbreviation is not null
        defaultVehicleControlItemShouldBeFound("vehicleControlItemFuelAbbreviation.specified=true");

        // Get all the vehicleControlItemList where vehicleControlItemFuelAbbreviation is null
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemFuelAbbreviation.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemFuelAbbreviationContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemFuelAbbreviation contains DEFAULT_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemFuelAbbreviation.contains=" + DEFAULT_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION
        );

        // Get all the vehicleControlItemList where vehicleControlItemFuelAbbreviation contains UPDATED_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION
        defaultVehicleControlItemShouldNotBeFound(
            "vehicleControlItemFuelAbbreviation.contains=" + UPDATED_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemFuelAbbreviationNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemFuelAbbreviation does not contain DEFAULT_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION
        defaultVehicleControlItemShouldNotBeFound(
            "vehicleControlItemFuelAbbreviation.doesNotContain=" + DEFAULT_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION
        );

        // Get all the vehicleControlItemList where vehicleControlItemFuelAbbreviation does not contain UPDATED_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemFuelAbbreviation.doesNotContain=" + UPDATED_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemReferenceMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemReferenceMonth equals to DEFAULT_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH
        defaultVehicleControlItemShouldBeFound("vehicleControlItemReferenceMonth.equals=" + DEFAULT_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH);

        // Get all the vehicleControlItemList where vehicleControlItemReferenceMonth equals to UPDATED_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH
        defaultVehicleControlItemShouldNotBeFound(
            "vehicleControlItemReferenceMonth.equals=" + UPDATED_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemReferenceMonthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemReferenceMonth not equals to DEFAULT_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH
        defaultVehicleControlItemShouldNotBeFound(
            "vehicleControlItemReferenceMonth.notEquals=" + DEFAULT_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH
        );

        // Get all the vehicleControlItemList where vehicleControlItemReferenceMonth not equals to UPDATED_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemReferenceMonth.notEquals=" + UPDATED_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemReferenceMonthIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemReferenceMonth in DEFAULT_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH or UPDATED_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemReferenceMonth.in=" +
            DEFAULT_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH +
            "," +
            UPDATED_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH
        );

        // Get all the vehicleControlItemList where vehicleControlItemReferenceMonth equals to UPDATED_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemReferenceMonth.in=" + UPDATED_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemReferenceMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemReferenceMonth is not null
        defaultVehicleControlItemShouldBeFound("vehicleControlItemReferenceMonth.specified=true");

        // Get all the vehicleControlItemList where vehicleControlItemReferenceMonth is null
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemReferenceMonth.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemReferenceMonthContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemReferenceMonth contains DEFAULT_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH
        defaultVehicleControlItemShouldBeFound("vehicleControlItemReferenceMonth.contains=" + DEFAULT_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH);

        // Get all the vehicleControlItemList where vehicleControlItemReferenceMonth contains UPDATED_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH
        defaultVehicleControlItemShouldNotBeFound(
            "vehicleControlItemReferenceMonth.contains=" + UPDATED_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemReferenceMonthNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemReferenceMonth does not contain DEFAULT_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH
        defaultVehicleControlItemShouldNotBeFound(
            "vehicleControlItemReferenceMonth.doesNotContain=" + DEFAULT_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH
        );

        // Get all the vehicleControlItemList where vehicleControlItemReferenceMonth does not contain UPDATED_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemReferenceMonth.doesNotContain=" + UPDATED_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemValueIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemValue equals to DEFAULT_VEHICLE_CONTROL_ITEM_VALUE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemValue.equals=" + DEFAULT_VEHICLE_CONTROL_ITEM_VALUE);

        // Get all the vehicleControlItemList where vehicleControlItemValue equals to UPDATED_VEHICLE_CONTROL_ITEM_VALUE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemValue.equals=" + UPDATED_VEHICLE_CONTROL_ITEM_VALUE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemValue not equals to DEFAULT_VEHICLE_CONTROL_ITEM_VALUE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemValue.notEquals=" + DEFAULT_VEHICLE_CONTROL_ITEM_VALUE);

        // Get all the vehicleControlItemList where vehicleControlItemValue not equals to UPDATED_VEHICLE_CONTROL_ITEM_VALUE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemValue.notEquals=" + UPDATED_VEHICLE_CONTROL_ITEM_VALUE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemValueIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemValue in DEFAULT_VEHICLE_CONTROL_ITEM_VALUE or UPDATED_VEHICLE_CONTROL_ITEM_VALUE
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemValue.in=" + DEFAULT_VEHICLE_CONTROL_ITEM_VALUE + "," + UPDATED_VEHICLE_CONTROL_ITEM_VALUE
        );

        // Get all the vehicleControlItemList where vehicleControlItemValue equals to UPDATED_VEHICLE_CONTROL_ITEM_VALUE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemValue.in=" + UPDATED_VEHICLE_CONTROL_ITEM_VALUE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemValue is not null
        defaultVehicleControlItemShouldBeFound("vehicleControlItemValue.specified=true");

        // Get all the vehicleControlItemList where vehicleControlItemValue is null
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemValue.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemValue is greater than or equal to DEFAULT_VEHICLE_CONTROL_ITEM_VALUE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemValue.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_ITEM_VALUE);

        // Get all the vehicleControlItemList where vehicleControlItemValue is greater than or equal to UPDATED_VEHICLE_CONTROL_ITEM_VALUE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemValue.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_ITEM_VALUE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemValue is less than or equal to DEFAULT_VEHICLE_CONTROL_ITEM_VALUE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemValue.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_ITEM_VALUE);

        // Get all the vehicleControlItemList where vehicleControlItemValue is less than or equal to SMALLER_VEHICLE_CONTROL_ITEM_VALUE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemValue.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_ITEM_VALUE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemValueIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemValue is less than DEFAULT_VEHICLE_CONTROL_ITEM_VALUE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemValue.lessThan=" + DEFAULT_VEHICLE_CONTROL_ITEM_VALUE);

        // Get all the vehicleControlItemList where vehicleControlItemValue is less than UPDATED_VEHICLE_CONTROL_ITEM_VALUE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemValue.lessThan=" + UPDATED_VEHICLE_CONTROL_ITEM_VALUE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemValue is greater than DEFAULT_VEHICLE_CONTROL_ITEM_VALUE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemValue.greaterThan=" + DEFAULT_VEHICLE_CONTROL_ITEM_VALUE);

        // Get all the vehicleControlItemList where vehicleControlItemValue is greater than SMALLER_VEHICLE_CONTROL_ITEM_VALUE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemValue.greaterThan=" + SMALLER_VEHICLE_CONTROL_ITEM_VALUE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemShippingValueIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemShippingValue equals to DEFAULT_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemShippingValue.equals=" + DEFAULT_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE);

        // Get all the vehicleControlItemList where vehicleControlItemShippingValue equals to UPDATED_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemShippingValue.equals=" + UPDATED_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemShippingValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemShippingValue not equals to DEFAULT_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        defaultVehicleControlItemShouldNotBeFound(
            "vehicleControlItemShippingValue.notEquals=" + DEFAULT_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        );

        // Get all the vehicleControlItemList where vehicleControlItemShippingValue not equals to UPDATED_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemShippingValue.notEquals=" + UPDATED_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemShippingValueIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemShippingValue in DEFAULT_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE or UPDATED_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemShippingValue.in=" +
            DEFAULT_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE +
            "," +
            UPDATED_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        );

        // Get all the vehicleControlItemList where vehicleControlItemShippingValue equals to UPDATED_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemShippingValue.in=" + UPDATED_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemShippingValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemShippingValue is not null
        defaultVehicleControlItemShouldBeFound("vehicleControlItemShippingValue.specified=true");

        // Get all the vehicleControlItemList where vehicleControlItemShippingValue is null
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemShippingValue.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemShippingValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemShippingValue is greater than or equal to DEFAULT_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemShippingValue.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        );

        // Get all the vehicleControlItemList where vehicleControlItemShippingValue is greater than or equal to UPDATED_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        defaultVehicleControlItemShouldNotBeFound(
            "vehicleControlItemShippingValue.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemShippingValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemShippingValue is less than or equal to DEFAULT_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemShippingValue.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        );

        // Get all the vehicleControlItemList where vehicleControlItemShippingValue is less than or equal to SMALLER_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        defaultVehicleControlItemShouldNotBeFound(
            "vehicleControlItemShippingValue.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemShippingValueIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemShippingValue is less than DEFAULT_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        defaultVehicleControlItemShouldNotBeFound(
            "vehicleControlItemShippingValue.lessThan=" + DEFAULT_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        );

        // Get all the vehicleControlItemList where vehicleControlItemShippingValue is less than UPDATED_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemShippingValue.lessThan=" + UPDATED_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemShippingValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemShippingValue is greater than DEFAULT_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        defaultVehicleControlItemShouldNotBeFound(
            "vehicleControlItemShippingValue.greaterThan=" + DEFAULT_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        );

        // Get all the vehicleControlItemList where vehicleControlItemShippingValue is greater than SMALLER_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemShippingValue.greaterThan=" + SMALLER_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE
        );
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemCTEIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemCTE equals to DEFAULT_VEHICLE_CONTROL_ITEM_CTE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemCTE.equals=" + DEFAULT_VEHICLE_CONTROL_ITEM_CTE);

        // Get all the vehicleControlItemList where vehicleControlItemCTE equals to UPDATED_VEHICLE_CONTROL_ITEM_CTE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemCTE.equals=" + UPDATED_VEHICLE_CONTROL_ITEM_CTE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemCTEIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemCTE not equals to DEFAULT_VEHICLE_CONTROL_ITEM_CTE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemCTE.notEquals=" + DEFAULT_VEHICLE_CONTROL_ITEM_CTE);

        // Get all the vehicleControlItemList where vehicleControlItemCTE not equals to UPDATED_VEHICLE_CONTROL_ITEM_CTE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemCTE.notEquals=" + UPDATED_VEHICLE_CONTROL_ITEM_CTE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemCTEIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemCTE in DEFAULT_VEHICLE_CONTROL_ITEM_CTE or UPDATED_VEHICLE_CONTROL_ITEM_CTE
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemCTE.in=" + DEFAULT_VEHICLE_CONTROL_ITEM_CTE + "," + UPDATED_VEHICLE_CONTROL_ITEM_CTE
        );

        // Get all the vehicleControlItemList where vehicleControlItemCTE equals to UPDATED_VEHICLE_CONTROL_ITEM_CTE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemCTE.in=" + UPDATED_VEHICLE_CONTROL_ITEM_CTE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemCTEIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemCTE is not null
        defaultVehicleControlItemShouldBeFound("vehicleControlItemCTE.specified=true");

        // Get all the vehicleControlItemList where vehicleControlItemCTE is null
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemCTE.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemCTEContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemCTE contains DEFAULT_VEHICLE_CONTROL_ITEM_CTE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemCTE.contains=" + DEFAULT_VEHICLE_CONTROL_ITEM_CTE);

        // Get all the vehicleControlItemList where vehicleControlItemCTE contains UPDATED_VEHICLE_CONTROL_ITEM_CTE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemCTE.contains=" + UPDATED_VEHICLE_CONTROL_ITEM_CTE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemCTENotContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemCTE does not contain DEFAULT_VEHICLE_CONTROL_ITEM_CTE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemCTE.doesNotContain=" + DEFAULT_VEHICLE_CONTROL_ITEM_CTE);

        // Get all the vehicleControlItemList where vehicleControlItemCTE does not contain UPDATED_VEHICLE_CONTROL_ITEM_CTE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemCTE.doesNotContain=" + UPDATED_VEHICLE_CONTROL_ITEM_CTE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemCTEDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemCTEDate equals to DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemCTEDate.equals=" + DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE);

        // Get all the vehicleControlItemList where vehicleControlItemCTEDate equals to UPDATED_VEHICLE_CONTROL_ITEM_CTE_DATE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemCTEDate.equals=" + UPDATED_VEHICLE_CONTROL_ITEM_CTE_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemCTEDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemCTEDate not equals to DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemCTEDate.notEquals=" + DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE);

        // Get all the vehicleControlItemList where vehicleControlItemCTEDate not equals to UPDATED_VEHICLE_CONTROL_ITEM_CTE_DATE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemCTEDate.notEquals=" + UPDATED_VEHICLE_CONTROL_ITEM_CTE_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemCTEDateIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemCTEDate in DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE or UPDATED_VEHICLE_CONTROL_ITEM_CTE_DATE
        defaultVehicleControlItemShouldBeFound(
            "vehicleControlItemCTEDate.in=" + DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE + "," + UPDATED_VEHICLE_CONTROL_ITEM_CTE_DATE
        );

        // Get all the vehicleControlItemList where vehicleControlItemCTEDate equals to UPDATED_VEHICLE_CONTROL_ITEM_CTE_DATE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemCTEDate.in=" + UPDATED_VEHICLE_CONTROL_ITEM_CTE_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemCTEDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemCTEDate is not null
        defaultVehicleControlItemShouldBeFound("vehicleControlItemCTEDate.specified=true");

        // Get all the vehicleControlItemList where vehicleControlItemCTEDate is null
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemCTEDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemCTEDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemCTEDate is greater than or equal to DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemCTEDate.greaterThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE);

        // Get all the vehicleControlItemList where vehicleControlItemCTEDate is greater than or equal to UPDATED_VEHICLE_CONTROL_ITEM_CTE_DATE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemCTEDate.greaterThanOrEqual=" + UPDATED_VEHICLE_CONTROL_ITEM_CTE_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemCTEDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemCTEDate is less than or equal to DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemCTEDate.lessThanOrEqual=" + DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE);

        // Get all the vehicleControlItemList where vehicleControlItemCTEDate is less than or equal to SMALLER_VEHICLE_CONTROL_ITEM_CTE_DATE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemCTEDate.lessThanOrEqual=" + SMALLER_VEHICLE_CONTROL_ITEM_CTE_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemCTEDateIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemCTEDate is less than DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemCTEDate.lessThan=" + DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE);

        // Get all the vehicleControlItemList where vehicleControlItemCTEDate is less than UPDATED_VEHICLE_CONTROL_ITEM_CTE_DATE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemCTEDate.lessThan=" + UPDATED_VEHICLE_CONTROL_ITEM_CTE_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlItemCTEDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        // Get all the vehicleControlItemList where vehicleControlItemCTEDate is greater than DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE
        defaultVehicleControlItemShouldNotBeFound("vehicleControlItemCTEDate.greaterThan=" + DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE);

        // Get all the vehicleControlItemList where vehicleControlItemCTEDate is greater than SMALLER_VEHICLE_CONTROL_ITEM_CTE_DATE
        defaultVehicleControlItemShouldBeFound("vehicleControlItemCTEDate.greaterThan=" + SMALLER_VEHICLE_CONTROL_ITEM_CTE_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleInspectionsIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);
        VehicleInspections vehicleInspections = VehicleInspectionsResourceIT.createEntity(em);
        em.persist(vehicleInspections);
        em.flush();
        vehicleControlItem.addVehicleInspections(vehicleInspections);
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);
        Long vehicleInspectionsId = vehicleInspections.getId();

        // Get all the vehicleControlItemList where vehicleInspections equals to vehicleInspectionsId
        defaultVehicleControlItemShouldBeFound("vehicleInspectionsId.equals=" + vehicleInspectionsId);

        // Get all the vehicleControlItemList where vehicleInspections equals to (vehicleInspectionsId + 1)
        defaultVehicleControlItemShouldNotBeFound("vehicleInspectionsId.equals=" + (vehicleInspectionsId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlExpensesIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);
        VehicleControlExpenses vehicleControlExpenses = VehicleControlExpensesResourceIT.createEntity(em);
        em.persist(vehicleControlExpenses);
        em.flush();
        vehicleControlItem.addVehicleControlExpenses(vehicleControlExpenses);
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);
        Long vehicleControlExpensesId = vehicleControlExpenses.getId();

        // Get all the vehicleControlItemList where vehicleControlExpenses equals to vehicleControlExpensesId
        defaultVehicleControlItemShouldBeFound("vehicleControlExpensesId.equals=" + vehicleControlExpensesId);

        // Get all the vehicleControlItemList where vehicleControlExpenses equals to (vehicleControlExpensesId + 1)
        defaultVehicleControlItemShouldNotBeFound("vehicleControlExpensesId.equals=" + (vehicleControlExpensesId + 1));
    }

    @Test
    @Transactional
    void getAllVehicleControlItemsByVehicleControlsIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);
        VehicleControls vehicleControls = VehicleControlsResourceIT.createEntity(em);
        em.persist(vehicleControls);
        em.flush();
        vehicleControlItem.setVehicleControls(vehicleControls);
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);
        Long vehicleControlsId = vehicleControls.getId();

        // Get all the vehicleControlItemList where vehicleControls equals to vehicleControlsId
        defaultVehicleControlItemShouldBeFound("vehicleControlsId.equals=" + vehicleControlsId);

        // Get all the vehicleControlItemList where vehicleControls equals to (vehicleControlsId + 1)
        defaultVehicleControlItemShouldNotBeFound("vehicleControlsId.equals=" + (vehicleControlsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVehicleControlItemShouldBeFound(String filter) throws Exception {
        restVehicleControlItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleControlItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleControlStatus").value(hasItem(DEFAULT_VEHICLE_CONTROL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].vehicleControlItemPlate").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_PLATE)))
            .andExpect(jsonPath("$.[*].vehicleControlItemType").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].vehicleControlItemFipeCode").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_FIPE_CODE)))
            .andExpect(jsonPath("$.[*].vehicleControlItemYear").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_YEAR)))
            .andExpect(jsonPath("$.[*].vehicleControlItemFuel").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_FUEL)))
            .andExpect(jsonPath("$.[*].vehicleControlItemBranch").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_BRANCH)))
            .andExpect(jsonPath("$.[*].vehicleControlItemModel").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_MODEL)))
            .andExpect(jsonPath("$.[*].vehicleControlItemFuelAbbreviation").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION)))
            .andExpect(jsonPath("$.[*].vehicleControlItemReferenceMonth").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH)))
            .andExpect(jsonPath("$.[*].vehicleControlItemValue").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_VALUE.doubleValue())))
            .andExpect(
                jsonPath("$.[*].vehicleControlItemShippingValue").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE.doubleValue()))
            )
            .andExpect(jsonPath("$.[*].vehicleControlItemCTE").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_CTE)))
            .andExpect(jsonPath("$.[*].vehicleControlItemCTEDate").value(hasItem(DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE.toString())));

        // Check, that the count call also returns 1
        restVehicleControlItemMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVehicleControlItemShouldNotBeFound(String filter) throws Exception {
        restVehicleControlItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVehicleControlItemMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVehicleControlItem() throws Exception {
        // Get the vehicleControlItem
        restVehicleControlItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVehicleControlItem() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        int databaseSizeBeforeUpdate = vehicleControlItemRepository.findAll().size();

        // Update the vehicleControlItem
        VehicleControlItem updatedVehicleControlItem = vehicleControlItemRepository.findById(vehicleControlItem.getId()).get();
        // Disconnect from session so that the updates on updatedVehicleControlItem are not directly saved in db
        em.detach(updatedVehicleControlItem);
        updatedVehicleControlItem
            .vehicleControlStatus(UPDATED_VEHICLE_CONTROL_STATUS)
            .vehicleControlItemPlate(UPDATED_VEHICLE_CONTROL_ITEM_PLATE)
            .vehicleControlItemType(UPDATED_VEHICLE_CONTROL_ITEM_TYPE)
            .vehicleControlItemFipeCode(UPDATED_VEHICLE_CONTROL_ITEM_FIPE_CODE)
            .vehicleControlItemYear(UPDATED_VEHICLE_CONTROL_ITEM_YEAR)
            .vehicleControlItemFuel(UPDATED_VEHICLE_CONTROL_ITEM_FUEL)
            .vehicleControlItemBranch(UPDATED_VEHICLE_CONTROL_ITEM_BRANCH)
            .vehicleControlItemModel(UPDATED_VEHICLE_CONTROL_ITEM_MODEL)
            .vehicleControlItemFuelAbbreviation(UPDATED_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION)
            .vehicleControlItemReferenceMonth(UPDATED_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH)
            .vehicleControlItemValue(UPDATED_VEHICLE_CONTROL_ITEM_VALUE)
            .vehicleControlItemShippingValue(UPDATED_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE)
            .vehicleControlItemCTE(UPDATED_VEHICLE_CONTROL_ITEM_CTE)
            .vehicleControlItemCTEDate(UPDATED_VEHICLE_CONTROL_ITEM_CTE_DATE);
        VehicleControlItemDTO vehicleControlItemDTO = vehicleControlItemMapper.toDto(updatedVehicleControlItem);

        restVehicleControlItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleControlItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the VehicleControlItem in the database
        List<VehicleControlItem> vehicleControlItemList = vehicleControlItemRepository.findAll();
        assertThat(vehicleControlItemList).hasSize(databaseSizeBeforeUpdate);
        VehicleControlItem testVehicleControlItem = vehicleControlItemList.get(vehicleControlItemList.size() - 1);
        assertThat(testVehicleControlItem.getVehicleControlStatus()).isEqualTo(UPDATED_VEHICLE_CONTROL_STATUS);
        assertThat(testVehicleControlItem.getVehicleControlItemPlate()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_PLATE);
        assertThat(testVehicleControlItem.getVehicleControlItemType()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_TYPE);
        assertThat(testVehicleControlItem.getVehicleControlItemFipeCode()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_FIPE_CODE);
        assertThat(testVehicleControlItem.getVehicleControlItemYear()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_YEAR);
        assertThat(testVehicleControlItem.getVehicleControlItemFuel()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_FUEL);
        assertThat(testVehicleControlItem.getVehicleControlItemBranch()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_BRANCH);
        assertThat(testVehicleControlItem.getVehicleControlItemModel()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_MODEL);
        assertThat(testVehicleControlItem.getVehicleControlItemFuelAbbreviation())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION);
        assertThat(testVehicleControlItem.getVehicleControlItemReferenceMonth()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH);
        assertThat(testVehicleControlItem.getVehicleControlItemValue()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_VALUE);
        assertThat(testVehicleControlItem.getVehicleControlItemShippingValue()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE);
        assertThat(testVehicleControlItem.getVehicleControlItemCTE()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_CTE);
        assertThat(testVehicleControlItem.getVehicleControlItemCTEDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_CTE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingVehicleControlItem() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlItemRepository.findAll().size();
        vehicleControlItem.setId(count.incrementAndGet());

        // Create the VehicleControlItem
        VehicleControlItemDTO vehicleControlItemDTO = vehicleControlItemMapper.toDto(vehicleControlItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleControlItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleControlItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlItem in the database
        List<VehicleControlItem> vehicleControlItemList = vehicleControlItemRepository.findAll();
        assertThat(vehicleControlItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVehicleControlItem() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlItemRepository.findAll().size();
        vehicleControlItem.setId(count.incrementAndGet());

        // Create the VehicleControlItem
        VehicleControlItemDTO vehicleControlItemDTO = vehicleControlItemMapper.toDto(vehicleControlItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlItem in the database
        List<VehicleControlItem> vehicleControlItemList = vehicleControlItemRepository.findAll();
        assertThat(vehicleControlItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVehicleControlItem() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlItemRepository.findAll().size();
        vehicleControlItem.setId(count.incrementAndGet());

        // Create the VehicleControlItem
        VehicleControlItemDTO vehicleControlItemDTO = vehicleControlItemMapper.toDto(vehicleControlItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlItemMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleControlItem in the database
        List<VehicleControlItem> vehicleControlItemList = vehicleControlItemRepository.findAll();
        assertThat(vehicleControlItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVehicleControlItemWithPatch() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        int databaseSizeBeforeUpdate = vehicleControlItemRepository.findAll().size();

        // Update the vehicleControlItem using partial update
        VehicleControlItem partialUpdatedVehicleControlItem = new VehicleControlItem();
        partialUpdatedVehicleControlItem.setId(vehicleControlItem.getId());

        partialUpdatedVehicleControlItem
            .vehicleControlItemPlate(UPDATED_VEHICLE_CONTROL_ITEM_PLATE)
            .vehicleControlItemType(UPDATED_VEHICLE_CONTROL_ITEM_TYPE)
            .vehicleControlItemFipeCode(UPDATED_VEHICLE_CONTROL_ITEM_FIPE_CODE)
            .vehicleControlItemYear(UPDATED_VEHICLE_CONTROL_ITEM_YEAR)
            .vehicleControlItemBranch(UPDATED_VEHICLE_CONTROL_ITEM_BRANCH)
            .vehicleControlItemShippingValue(UPDATED_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE)
            .vehicleControlItemCTE(UPDATED_VEHICLE_CONTROL_ITEM_CTE);

        restVehicleControlItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleControlItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleControlItem))
            )
            .andExpect(status().isOk());

        // Validate the VehicleControlItem in the database
        List<VehicleControlItem> vehicleControlItemList = vehicleControlItemRepository.findAll();
        assertThat(vehicleControlItemList).hasSize(databaseSizeBeforeUpdate);
        VehicleControlItem testVehicleControlItem = vehicleControlItemList.get(vehicleControlItemList.size() - 1);
        assertThat(testVehicleControlItem.getVehicleControlStatus()).isEqualTo(DEFAULT_VEHICLE_CONTROL_STATUS);
        assertThat(testVehicleControlItem.getVehicleControlItemPlate()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_PLATE);
        assertThat(testVehicleControlItem.getVehicleControlItemType()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_TYPE);
        assertThat(testVehicleControlItem.getVehicleControlItemFipeCode()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_FIPE_CODE);
        assertThat(testVehicleControlItem.getVehicleControlItemYear()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_YEAR);
        assertThat(testVehicleControlItem.getVehicleControlItemFuel()).isEqualTo(DEFAULT_VEHICLE_CONTROL_ITEM_FUEL);
        assertThat(testVehicleControlItem.getVehicleControlItemBranch()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_BRANCH);
        assertThat(testVehicleControlItem.getVehicleControlItemModel()).isEqualTo(DEFAULT_VEHICLE_CONTROL_ITEM_MODEL);
        assertThat(testVehicleControlItem.getVehicleControlItemFuelAbbreviation())
            .isEqualTo(DEFAULT_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION);
        assertThat(testVehicleControlItem.getVehicleControlItemReferenceMonth()).isEqualTo(DEFAULT_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH);
        assertThat(testVehicleControlItem.getVehicleControlItemValue()).isEqualTo(DEFAULT_VEHICLE_CONTROL_ITEM_VALUE);
        assertThat(testVehicleControlItem.getVehicleControlItemShippingValue()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE);
        assertThat(testVehicleControlItem.getVehicleControlItemCTE()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_CTE);
        assertThat(testVehicleControlItem.getVehicleControlItemCTEDate()).isEqualTo(DEFAULT_VEHICLE_CONTROL_ITEM_CTE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateVehicleControlItemWithPatch() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        int databaseSizeBeforeUpdate = vehicleControlItemRepository.findAll().size();

        // Update the vehicleControlItem using partial update
        VehicleControlItem partialUpdatedVehicleControlItem = new VehicleControlItem();
        partialUpdatedVehicleControlItem.setId(vehicleControlItem.getId());

        partialUpdatedVehicleControlItem
            .vehicleControlStatus(UPDATED_VEHICLE_CONTROL_STATUS)
            .vehicleControlItemPlate(UPDATED_VEHICLE_CONTROL_ITEM_PLATE)
            .vehicleControlItemType(UPDATED_VEHICLE_CONTROL_ITEM_TYPE)
            .vehicleControlItemFipeCode(UPDATED_VEHICLE_CONTROL_ITEM_FIPE_CODE)
            .vehicleControlItemYear(UPDATED_VEHICLE_CONTROL_ITEM_YEAR)
            .vehicleControlItemFuel(UPDATED_VEHICLE_CONTROL_ITEM_FUEL)
            .vehicleControlItemBranch(UPDATED_VEHICLE_CONTROL_ITEM_BRANCH)
            .vehicleControlItemModel(UPDATED_VEHICLE_CONTROL_ITEM_MODEL)
            .vehicleControlItemFuelAbbreviation(UPDATED_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION)
            .vehicleControlItemReferenceMonth(UPDATED_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH)
            .vehicleControlItemValue(UPDATED_VEHICLE_CONTROL_ITEM_VALUE)
            .vehicleControlItemShippingValue(UPDATED_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE)
            .vehicleControlItemCTE(UPDATED_VEHICLE_CONTROL_ITEM_CTE)
            .vehicleControlItemCTEDate(UPDATED_VEHICLE_CONTROL_ITEM_CTE_DATE);

        restVehicleControlItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleControlItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleControlItem))
            )
            .andExpect(status().isOk());

        // Validate the VehicleControlItem in the database
        List<VehicleControlItem> vehicleControlItemList = vehicleControlItemRepository.findAll();
        assertThat(vehicleControlItemList).hasSize(databaseSizeBeforeUpdate);
        VehicleControlItem testVehicleControlItem = vehicleControlItemList.get(vehicleControlItemList.size() - 1);
        assertThat(testVehicleControlItem.getVehicleControlStatus()).isEqualTo(UPDATED_VEHICLE_CONTROL_STATUS);
        assertThat(testVehicleControlItem.getVehicleControlItemPlate()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_PLATE);
        assertThat(testVehicleControlItem.getVehicleControlItemType()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_TYPE);
        assertThat(testVehicleControlItem.getVehicleControlItemFipeCode()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_FIPE_CODE);
        assertThat(testVehicleControlItem.getVehicleControlItemYear()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_YEAR);
        assertThat(testVehicleControlItem.getVehicleControlItemFuel()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_FUEL);
        assertThat(testVehicleControlItem.getVehicleControlItemBranch()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_BRANCH);
        assertThat(testVehicleControlItem.getVehicleControlItemModel()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_MODEL);
        assertThat(testVehicleControlItem.getVehicleControlItemFuelAbbreviation())
            .isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_FUEL_ABBREVIATION);
        assertThat(testVehicleControlItem.getVehicleControlItemReferenceMonth()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_REFERENCE_MONTH);
        assertThat(testVehicleControlItem.getVehicleControlItemValue()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_VALUE);
        assertThat(testVehicleControlItem.getVehicleControlItemShippingValue()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_SHIPPING_VALUE);
        assertThat(testVehicleControlItem.getVehicleControlItemCTE()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_CTE);
        assertThat(testVehicleControlItem.getVehicleControlItemCTEDate()).isEqualTo(UPDATED_VEHICLE_CONTROL_ITEM_CTE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingVehicleControlItem() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlItemRepository.findAll().size();
        vehicleControlItem.setId(count.incrementAndGet());

        // Create the VehicleControlItem
        VehicleControlItemDTO vehicleControlItemDTO = vehicleControlItemMapper.toDto(vehicleControlItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleControlItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vehicleControlItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlItem in the database
        List<VehicleControlItem> vehicleControlItemList = vehicleControlItemRepository.findAll();
        assertThat(vehicleControlItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVehicleControlItem() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlItemRepository.findAll().size();
        vehicleControlItem.setId(count.incrementAndGet());

        // Create the VehicleControlItem
        VehicleControlItemDTO vehicleControlItemDTO = vehicleControlItemMapper.toDto(vehicleControlItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlItem in the database
        List<VehicleControlItem> vehicleControlItemList = vehicleControlItemRepository.findAll();
        assertThat(vehicleControlItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVehicleControlItem() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlItemRepository.findAll().size();
        vehicleControlItem.setId(count.incrementAndGet());

        // Create the VehicleControlItem
        VehicleControlItemDTO vehicleControlItemDTO = vehicleControlItemMapper.toDto(vehicleControlItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleControlItem in the database
        List<VehicleControlItem> vehicleControlItemList = vehicleControlItemRepository.findAll();
        assertThat(vehicleControlItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVehicleControlItem() throws Exception {
        // Initialize the database
        vehicleControlItemRepository.saveAndFlush(vehicleControlItem);

        int databaseSizeBeforeDelete = vehicleControlItemRepository.findAll().size();

        // Delete the vehicleControlItem
        restVehicleControlItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, vehicleControlItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehicleControlItem> vehicleControlItemList = vehicleControlItemRepository.findAll();
        assertThat(vehicleControlItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
