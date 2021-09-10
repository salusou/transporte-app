package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Housing;
import com.genesisoft.transporte.domain.HousingVehicleItem;
import com.genesisoft.transporte.domain.ParkingSector;
import com.genesisoft.transporte.domain.ParkingSectorSpace;
import com.genesisoft.transporte.domain.enumeration.StatusType;
import com.genesisoft.transporte.domain.enumeration.VehicleType;
import com.genesisoft.transporte.repository.HousingVehicleItemRepository;
import com.genesisoft.transporte.service.criteria.HousingVehicleItemCriteria;
import com.genesisoft.transporte.service.dto.HousingVehicleItemDTO;
import com.genesisoft.transporte.service.mapper.HousingVehicleItemMapper;
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
 * Integration tests for the {@link HousingVehicleItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HousingVehicleItemResourceIT {

    private static final StatusType DEFAULT_HOUSING_VEHICLE_ITEM_STATUS = StatusType.APPROVED;
    private static final StatusType UPDATED_HOUSING_VEHICLE_ITEM_STATUS = StatusType.PENDENT;

    private static final String DEFAULT_HOUSING_VEHICLE_ITEM_PLATE = "AAAAAAAAAA";
    private static final String UPDATED_HOUSING_VEHICLE_ITEM_PLATE = "BBBBBBBBBB";

    private static final VehicleType DEFAULT_HOUSING_VEHICLE_ITEM_TYPE = VehicleType.MOTORBIKE;
    private static final VehicleType UPDATED_HOUSING_VEHICLE_ITEM_TYPE = VehicleType.CAR;

    private static final String DEFAULT_HOUSING_VEHICLE_ITEM_FIPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_HOUSING_VEHICLE_ITEM_FIPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_HOUSING_VEHICLE_ITEM_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_HOUSING_VEHICLE_ITEM_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_HOUSING_VEHICLE_ITEM_FUEL = "AAAAAAAAAA";
    private static final String UPDATED_HOUSING_VEHICLE_ITEM_FUEL = "BBBBBBBBBB";

    private static final String DEFAULT_HOUSING_VEHICLE_ITEM_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_HOUSING_VEHICLE_ITEM_BRANCH = "BBBBBBBBBB";

    private static final String DEFAULT_HOUSING_VEHICLE_ITEM_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_HOUSING_VEHICLE_ITEM_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION = "BBBBBBBBBB";

    private static final String DEFAULT_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH = "AAAAAAAAAA";
    private static final String UPDATED_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH = "BBBBBBBBBB";

    private static final Float DEFAULT_HOUSING_VEHICLE_ITEM_VALUE = 1F;
    private static final Float UPDATED_HOUSING_VEHICLE_ITEM_VALUE = 2F;
    private static final Float SMALLER_HOUSING_VEHICLE_ITEM_VALUE = 1F - 1F;

    private static final Float DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE = 1F;
    private static final Float UPDATED_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE = 2F;
    private static final Float SMALLER_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE = 1F - 1F;

    private static final String ENTITY_API_URL = "/api/housing-vehicle-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HousingVehicleItemRepository housingVehicleItemRepository;

    @Autowired
    private HousingVehicleItemMapper housingVehicleItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHousingVehicleItemMockMvc;

    private HousingVehicleItem housingVehicleItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HousingVehicleItem createEntity(EntityManager em) {
        HousingVehicleItem housingVehicleItem = new HousingVehicleItem()
            .housingVehicleItemStatus(DEFAULT_HOUSING_VEHICLE_ITEM_STATUS)
            .housingVehicleItemPlate(DEFAULT_HOUSING_VEHICLE_ITEM_PLATE)
            .housingVehicleItemType(DEFAULT_HOUSING_VEHICLE_ITEM_TYPE)
            .housingVehicleItemFipeCode(DEFAULT_HOUSING_VEHICLE_ITEM_FIPE_CODE)
            .housingVehicleItemYear(DEFAULT_HOUSING_VEHICLE_ITEM_YEAR)
            .housingVehicleItemFuel(DEFAULT_HOUSING_VEHICLE_ITEM_FUEL)
            .housingVehicleItemBranch(DEFAULT_HOUSING_VEHICLE_ITEM_BRANCH)
            .housingVehicleItemModel(DEFAULT_HOUSING_VEHICLE_ITEM_MODEL)
            .housingVehicleItemFuelAbbreviation(DEFAULT_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION)
            .housingVehicleItemReferenceMonth(DEFAULT_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH)
            .housingVehicleItemValue(DEFAULT_HOUSING_VEHICLE_ITEM_VALUE)
            .housingVehicleItemShippingValue(DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE);
        // Add required entity
        Housing housing;
        if (TestUtil.findAll(em, Housing.class).isEmpty()) {
            housing = HousingResourceIT.createEntity(em);
            em.persist(housing);
            em.flush();
        } else {
            housing = TestUtil.findAll(em, Housing.class).get(0);
        }
        housingVehicleItem.setHousing(housing);
        return housingVehicleItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HousingVehicleItem createUpdatedEntity(EntityManager em) {
        HousingVehicleItem housingVehicleItem = new HousingVehicleItem()
            .housingVehicleItemStatus(UPDATED_HOUSING_VEHICLE_ITEM_STATUS)
            .housingVehicleItemPlate(UPDATED_HOUSING_VEHICLE_ITEM_PLATE)
            .housingVehicleItemType(UPDATED_HOUSING_VEHICLE_ITEM_TYPE)
            .housingVehicleItemFipeCode(UPDATED_HOUSING_VEHICLE_ITEM_FIPE_CODE)
            .housingVehicleItemYear(UPDATED_HOUSING_VEHICLE_ITEM_YEAR)
            .housingVehicleItemFuel(UPDATED_HOUSING_VEHICLE_ITEM_FUEL)
            .housingVehicleItemBranch(UPDATED_HOUSING_VEHICLE_ITEM_BRANCH)
            .housingVehicleItemModel(UPDATED_HOUSING_VEHICLE_ITEM_MODEL)
            .housingVehicleItemFuelAbbreviation(UPDATED_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION)
            .housingVehicleItemReferenceMonth(UPDATED_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH)
            .housingVehicleItemValue(UPDATED_HOUSING_VEHICLE_ITEM_VALUE)
            .housingVehicleItemShippingValue(UPDATED_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE);
        // Add required entity
        Housing housing;
        if (TestUtil.findAll(em, Housing.class).isEmpty()) {
            housing = HousingResourceIT.createUpdatedEntity(em);
            em.persist(housing);
            em.flush();
        } else {
            housing = TestUtil.findAll(em, Housing.class).get(0);
        }
        housingVehicleItem.setHousing(housing);
        return housingVehicleItem;
    }

    @BeforeEach
    public void initTest() {
        housingVehicleItem = createEntity(em);
    }

    @Test
    @Transactional
    void createHousingVehicleItem() throws Exception {
        int databaseSizeBeforeCreate = housingVehicleItemRepository.findAll().size();
        // Create the HousingVehicleItem
        HousingVehicleItemDTO housingVehicleItemDTO = housingVehicleItemMapper.toDto(housingVehicleItem);
        restHousingVehicleItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(housingVehicleItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the HousingVehicleItem in the database
        List<HousingVehicleItem> housingVehicleItemList = housingVehicleItemRepository.findAll();
        assertThat(housingVehicleItemList).hasSize(databaseSizeBeforeCreate + 1);
        HousingVehicleItem testHousingVehicleItem = housingVehicleItemList.get(housingVehicleItemList.size() - 1);
        assertThat(testHousingVehicleItem.getHousingVehicleItemStatus()).isEqualTo(DEFAULT_HOUSING_VEHICLE_ITEM_STATUS);
        assertThat(testHousingVehicleItem.getHousingVehicleItemPlate()).isEqualTo(DEFAULT_HOUSING_VEHICLE_ITEM_PLATE);
        assertThat(testHousingVehicleItem.getHousingVehicleItemType()).isEqualTo(DEFAULT_HOUSING_VEHICLE_ITEM_TYPE);
        assertThat(testHousingVehicleItem.getHousingVehicleItemFipeCode()).isEqualTo(DEFAULT_HOUSING_VEHICLE_ITEM_FIPE_CODE);
        assertThat(testHousingVehicleItem.getHousingVehicleItemYear()).isEqualTo(DEFAULT_HOUSING_VEHICLE_ITEM_YEAR);
        assertThat(testHousingVehicleItem.getHousingVehicleItemFuel()).isEqualTo(DEFAULT_HOUSING_VEHICLE_ITEM_FUEL);
        assertThat(testHousingVehicleItem.getHousingVehicleItemBranch()).isEqualTo(DEFAULT_HOUSING_VEHICLE_ITEM_BRANCH);
        assertThat(testHousingVehicleItem.getHousingVehicleItemModel()).isEqualTo(DEFAULT_HOUSING_VEHICLE_ITEM_MODEL);
        assertThat(testHousingVehicleItem.getHousingVehicleItemFuelAbbreviation())
            .isEqualTo(DEFAULT_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION);
        assertThat(testHousingVehicleItem.getHousingVehicleItemReferenceMonth()).isEqualTo(DEFAULT_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH);
        assertThat(testHousingVehicleItem.getHousingVehicleItemValue()).isEqualTo(DEFAULT_HOUSING_VEHICLE_ITEM_VALUE);
        assertThat(testHousingVehicleItem.getHousingVehicleItemShippingValue()).isEqualTo(DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE);
    }

    @Test
    @Transactional
    void createHousingVehicleItemWithExistingId() throws Exception {
        // Create the HousingVehicleItem with an existing ID
        housingVehicleItem.setId(1L);
        HousingVehicleItemDTO housingVehicleItemDTO = housingVehicleItemMapper.toDto(housingVehicleItem);

        int databaseSizeBeforeCreate = housingVehicleItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHousingVehicleItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(housingVehicleItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HousingVehicleItem in the database
        List<HousingVehicleItem> housingVehicleItemList = housingVehicleItemRepository.findAll();
        assertThat(housingVehicleItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkHousingVehicleItemStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = housingVehicleItemRepository.findAll().size();
        // set the field null
        housingVehicleItem.setHousingVehicleItemStatus(null);

        // Create the HousingVehicleItem, which fails.
        HousingVehicleItemDTO housingVehicleItemDTO = housingVehicleItemMapper.toDto(housingVehicleItem);

        restHousingVehicleItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(housingVehicleItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<HousingVehicleItem> housingVehicleItemList = housingVehicleItemRepository.findAll();
        assertThat(housingVehicleItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHousingVehicleItemPlateIsRequired() throws Exception {
        int databaseSizeBeforeTest = housingVehicleItemRepository.findAll().size();
        // set the field null
        housingVehicleItem.setHousingVehicleItemPlate(null);

        // Create the HousingVehicleItem, which fails.
        HousingVehicleItemDTO housingVehicleItemDTO = housingVehicleItemMapper.toDto(housingVehicleItem);

        restHousingVehicleItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(housingVehicleItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<HousingVehicleItem> housingVehicleItemList = housingVehicleItemRepository.findAll();
        assertThat(housingVehicleItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHousingVehicleItemTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = housingVehicleItemRepository.findAll().size();
        // set the field null
        housingVehicleItem.setHousingVehicleItemType(null);

        // Create the HousingVehicleItem, which fails.
        HousingVehicleItemDTO housingVehicleItemDTO = housingVehicleItemMapper.toDto(housingVehicleItem);

        restHousingVehicleItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(housingVehicleItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<HousingVehicleItem> housingVehicleItemList = housingVehicleItemRepository.findAll();
        assertThat(housingVehicleItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHousingVehicleItemValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = housingVehicleItemRepository.findAll().size();
        // set the field null
        housingVehicleItem.setHousingVehicleItemValue(null);

        // Create the HousingVehicleItem, which fails.
        HousingVehicleItemDTO housingVehicleItemDTO = housingVehicleItemMapper.toDto(housingVehicleItem);

        restHousingVehicleItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(housingVehicleItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<HousingVehicleItem> housingVehicleItemList = housingVehicleItemRepository.findAll();
        assertThat(housingVehicleItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHousingVehicleItemShippingValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = housingVehicleItemRepository.findAll().size();
        // set the field null
        housingVehicleItem.setHousingVehicleItemShippingValue(null);

        // Create the HousingVehicleItem, which fails.
        HousingVehicleItemDTO housingVehicleItemDTO = housingVehicleItemMapper.toDto(housingVehicleItem);

        restHousingVehicleItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(housingVehicleItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<HousingVehicleItem> housingVehicleItemList = housingVehicleItemRepository.findAll();
        assertThat(housingVehicleItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItems() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList
        restHousingVehicleItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(housingVehicleItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].housingVehicleItemStatus").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_STATUS.toString())))
            .andExpect(jsonPath("$.[*].housingVehicleItemPlate").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_PLATE)))
            .andExpect(jsonPath("$.[*].housingVehicleItemType").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].housingVehicleItemFipeCode").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_FIPE_CODE)))
            .andExpect(jsonPath("$.[*].housingVehicleItemYear").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_YEAR)))
            .andExpect(jsonPath("$.[*].housingVehicleItemFuel").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_FUEL)))
            .andExpect(jsonPath("$.[*].housingVehicleItemBranch").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_BRANCH)))
            .andExpect(jsonPath("$.[*].housingVehicleItemModel").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_MODEL)))
            .andExpect(jsonPath("$.[*].housingVehicleItemFuelAbbreviation").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION)))
            .andExpect(jsonPath("$.[*].housingVehicleItemReferenceMonth").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH)))
            .andExpect(jsonPath("$.[*].housingVehicleItemValue").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_VALUE.doubleValue())))
            .andExpect(
                jsonPath("$.[*].housingVehicleItemShippingValue").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE.doubleValue()))
            );
    }

    @Test
    @Transactional
    void getHousingVehicleItem() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get the housingVehicleItem
        restHousingVehicleItemMockMvc
            .perform(get(ENTITY_API_URL_ID, housingVehicleItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(housingVehicleItem.getId().intValue()))
            .andExpect(jsonPath("$.housingVehicleItemStatus").value(DEFAULT_HOUSING_VEHICLE_ITEM_STATUS.toString()))
            .andExpect(jsonPath("$.housingVehicleItemPlate").value(DEFAULT_HOUSING_VEHICLE_ITEM_PLATE))
            .andExpect(jsonPath("$.housingVehicleItemType").value(DEFAULT_HOUSING_VEHICLE_ITEM_TYPE.toString()))
            .andExpect(jsonPath("$.housingVehicleItemFipeCode").value(DEFAULT_HOUSING_VEHICLE_ITEM_FIPE_CODE))
            .andExpect(jsonPath("$.housingVehicleItemYear").value(DEFAULT_HOUSING_VEHICLE_ITEM_YEAR))
            .andExpect(jsonPath("$.housingVehicleItemFuel").value(DEFAULT_HOUSING_VEHICLE_ITEM_FUEL))
            .andExpect(jsonPath("$.housingVehicleItemBranch").value(DEFAULT_HOUSING_VEHICLE_ITEM_BRANCH))
            .andExpect(jsonPath("$.housingVehicleItemModel").value(DEFAULT_HOUSING_VEHICLE_ITEM_MODEL))
            .andExpect(jsonPath("$.housingVehicleItemFuelAbbreviation").value(DEFAULT_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION))
            .andExpect(jsonPath("$.housingVehicleItemReferenceMonth").value(DEFAULT_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH))
            .andExpect(jsonPath("$.housingVehicleItemValue").value(DEFAULT_HOUSING_VEHICLE_ITEM_VALUE.doubleValue()))
            .andExpect(jsonPath("$.housingVehicleItemShippingValue").value(DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    void getHousingVehicleItemsByIdFiltering() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        Long id = housingVehicleItem.getId();

        defaultHousingVehicleItemShouldBeFound("id.equals=" + id);
        defaultHousingVehicleItemShouldNotBeFound("id.notEquals=" + id);

        defaultHousingVehicleItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHousingVehicleItemShouldNotBeFound("id.greaterThan=" + id);

        defaultHousingVehicleItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHousingVehicleItemShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemStatus equals to DEFAULT_HOUSING_VEHICLE_ITEM_STATUS
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemStatus.equals=" + DEFAULT_HOUSING_VEHICLE_ITEM_STATUS);

        // Get all the housingVehicleItemList where housingVehicleItemStatus equals to UPDATED_HOUSING_VEHICLE_ITEM_STATUS
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemStatus.equals=" + UPDATED_HOUSING_VEHICLE_ITEM_STATUS);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemStatus not equals to DEFAULT_HOUSING_VEHICLE_ITEM_STATUS
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemStatus.notEquals=" + DEFAULT_HOUSING_VEHICLE_ITEM_STATUS);

        // Get all the housingVehicleItemList where housingVehicleItemStatus not equals to UPDATED_HOUSING_VEHICLE_ITEM_STATUS
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemStatus.notEquals=" + UPDATED_HOUSING_VEHICLE_ITEM_STATUS);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemStatusIsInShouldWork() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemStatus in DEFAULT_HOUSING_VEHICLE_ITEM_STATUS or UPDATED_HOUSING_VEHICLE_ITEM_STATUS
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemStatus.in=" + DEFAULT_HOUSING_VEHICLE_ITEM_STATUS + "," + UPDATED_HOUSING_VEHICLE_ITEM_STATUS
        );

        // Get all the housingVehicleItemList where housingVehicleItemStatus equals to UPDATED_HOUSING_VEHICLE_ITEM_STATUS
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemStatus.in=" + UPDATED_HOUSING_VEHICLE_ITEM_STATUS);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemStatus is not null
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemStatus.specified=true");

        // Get all the housingVehicleItemList where housingVehicleItemStatus is null
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemPlateIsEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemPlate equals to DEFAULT_HOUSING_VEHICLE_ITEM_PLATE
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemPlate.equals=" + DEFAULT_HOUSING_VEHICLE_ITEM_PLATE);

        // Get all the housingVehicleItemList where housingVehicleItemPlate equals to UPDATED_HOUSING_VEHICLE_ITEM_PLATE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemPlate.equals=" + UPDATED_HOUSING_VEHICLE_ITEM_PLATE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemPlateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemPlate not equals to DEFAULT_HOUSING_VEHICLE_ITEM_PLATE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemPlate.notEquals=" + DEFAULT_HOUSING_VEHICLE_ITEM_PLATE);

        // Get all the housingVehicleItemList where housingVehicleItemPlate not equals to UPDATED_HOUSING_VEHICLE_ITEM_PLATE
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemPlate.notEquals=" + UPDATED_HOUSING_VEHICLE_ITEM_PLATE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemPlateIsInShouldWork() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemPlate in DEFAULT_HOUSING_VEHICLE_ITEM_PLATE or UPDATED_HOUSING_VEHICLE_ITEM_PLATE
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemPlate.in=" + DEFAULT_HOUSING_VEHICLE_ITEM_PLATE + "," + UPDATED_HOUSING_VEHICLE_ITEM_PLATE
        );

        // Get all the housingVehicleItemList where housingVehicleItemPlate equals to UPDATED_HOUSING_VEHICLE_ITEM_PLATE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemPlate.in=" + UPDATED_HOUSING_VEHICLE_ITEM_PLATE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemPlateIsNullOrNotNull() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemPlate is not null
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemPlate.specified=true");

        // Get all the housingVehicleItemList where housingVehicleItemPlate is null
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemPlate.specified=false");
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemPlateContainsSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemPlate contains DEFAULT_HOUSING_VEHICLE_ITEM_PLATE
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemPlate.contains=" + DEFAULT_HOUSING_VEHICLE_ITEM_PLATE);

        // Get all the housingVehicleItemList where housingVehicleItemPlate contains UPDATED_HOUSING_VEHICLE_ITEM_PLATE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemPlate.contains=" + UPDATED_HOUSING_VEHICLE_ITEM_PLATE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemPlateNotContainsSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemPlate does not contain DEFAULT_HOUSING_VEHICLE_ITEM_PLATE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemPlate.doesNotContain=" + DEFAULT_HOUSING_VEHICLE_ITEM_PLATE);

        // Get all the housingVehicleItemList where housingVehicleItemPlate does not contain UPDATED_HOUSING_VEHICLE_ITEM_PLATE
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemPlate.doesNotContain=" + UPDATED_HOUSING_VEHICLE_ITEM_PLATE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemType equals to DEFAULT_HOUSING_VEHICLE_ITEM_TYPE
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemType.equals=" + DEFAULT_HOUSING_VEHICLE_ITEM_TYPE);

        // Get all the housingVehicleItemList where housingVehicleItemType equals to UPDATED_HOUSING_VEHICLE_ITEM_TYPE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemType.equals=" + UPDATED_HOUSING_VEHICLE_ITEM_TYPE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemType not equals to DEFAULT_HOUSING_VEHICLE_ITEM_TYPE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemType.notEquals=" + DEFAULT_HOUSING_VEHICLE_ITEM_TYPE);

        // Get all the housingVehicleItemList where housingVehicleItemType not equals to UPDATED_HOUSING_VEHICLE_ITEM_TYPE
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemType.notEquals=" + UPDATED_HOUSING_VEHICLE_ITEM_TYPE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemTypeIsInShouldWork() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemType in DEFAULT_HOUSING_VEHICLE_ITEM_TYPE or UPDATED_HOUSING_VEHICLE_ITEM_TYPE
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemType.in=" + DEFAULT_HOUSING_VEHICLE_ITEM_TYPE + "," + UPDATED_HOUSING_VEHICLE_ITEM_TYPE
        );

        // Get all the housingVehicleItemList where housingVehicleItemType equals to UPDATED_HOUSING_VEHICLE_ITEM_TYPE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemType.in=" + UPDATED_HOUSING_VEHICLE_ITEM_TYPE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemType is not null
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemType.specified=true");

        // Get all the housingVehicleItemList where housingVehicleItemType is null
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemType.specified=false");
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemFipeCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemFipeCode equals to DEFAULT_HOUSING_VEHICLE_ITEM_FIPE_CODE
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemFipeCode.equals=" + DEFAULT_HOUSING_VEHICLE_ITEM_FIPE_CODE);

        // Get all the housingVehicleItemList where housingVehicleItemFipeCode equals to UPDATED_HOUSING_VEHICLE_ITEM_FIPE_CODE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemFipeCode.equals=" + UPDATED_HOUSING_VEHICLE_ITEM_FIPE_CODE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemFipeCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemFipeCode not equals to DEFAULT_HOUSING_VEHICLE_ITEM_FIPE_CODE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemFipeCode.notEquals=" + DEFAULT_HOUSING_VEHICLE_ITEM_FIPE_CODE);

        // Get all the housingVehicleItemList where housingVehicleItemFipeCode not equals to UPDATED_HOUSING_VEHICLE_ITEM_FIPE_CODE
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemFipeCode.notEquals=" + UPDATED_HOUSING_VEHICLE_ITEM_FIPE_CODE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemFipeCodeIsInShouldWork() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemFipeCode in DEFAULT_HOUSING_VEHICLE_ITEM_FIPE_CODE or UPDATED_HOUSING_VEHICLE_ITEM_FIPE_CODE
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemFipeCode.in=" + DEFAULT_HOUSING_VEHICLE_ITEM_FIPE_CODE + "," + UPDATED_HOUSING_VEHICLE_ITEM_FIPE_CODE
        );

        // Get all the housingVehicleItemList where housingVehicleItemFipeCode equals to UPDATED_HOUSING_VEHICLE_ITEM_FIPE_CODE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemFipeCode.in=" + UPDATED_HOUSING_VEHICLE_ITEM_FIPE_CODE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemFipeCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemFipeCode is not null
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemFipeCode.specified=true");

        // Get all the housingVehicleItemList where housingVehicleItemFipeCode is null
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemFipeCode.specified=false");
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemFipeCodeContainsSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemFipeCode contains DEFAULT_HOUSING_VEHICLE_ITEM_FIPE_CODE
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemFipeCode.contains=" + DEFAULT_HOUSING_VEHICLE_ITEM_FIPE_CODE);

        // Get all the housingVehicleItemList where housingVehicleItemFipeCode contains UPDATED_HOUSING_VEHICLE_ITEM_FIPE_CODE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemFipeCode.contains=" + UPDATED_HOUSING_VEHICLE_ITEM_FIPE_CODE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemFipeCodeNotContainsSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemFipeCode does not contain DEFAULT_HOUSING_VEHICLE_ITEM_FIPE_CODE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemFipeCode.doesNotContain=" + DEFAULT_HOUSING_VEHICLE_ITEM_FIPE_CODE);

        // Get all the housingVehicleItemList where housingVehicleItemFipeCode does not contain UPDATED_HOUSING_VEHICLE_ITEM_FIPE_CODE
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemFipeCode.doesNotContain=" + UPDATED_HOUSING_VEHICLE_ITEM_FIPE_CODE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemYearIsEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemYear equals to DEFAULT_HOUSING_VEHICLE_ITEM_YEAR
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemYear.equals=" + DEFAULT_HOUSING_VEHICLE_ITEM_YEAR);

        // Get all the housingVehicleItemList where housingVehicleItemYear equals to UPDATED_HOUSING_VEHICLE_ITEM_YEAR
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemYear.equals=" + UPDATED_HOUSING_VEHICLE_ITEM_YEAR);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemYear not equals to DEFAULT_HOUSING_VEHICLE_ITEM_YEAR
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemYear.notEquals=" + DEFAULT_HOUSING_VEHICLE_ITEM_YEAR);

        // Get all the housingVehicleItemList where housingVehicleItemYear not equals to UPDATED_HOUSING_VEHICLE_ITEM_YEAR
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemYear.notEquals=" + UPDATED_HOUSING_VEHICLE_ITEM_YEAR);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemYearIsInShouldWork() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemYear in DEFAULT_HOUSING_VEHICLE_ITEM_YEAR or UPDATED_HOUSING_VEHICLE_ITEM_YEAR
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemYear.in=" + DEFAULT_HOUSING_VEHICLE_ITEM_YEAR + "," + UPDATED_HOUSING_VEHICLE_ITEM_YEAR
        );

        // Get all the housingVehicleItemList where housingVehicleItemYear equals to UPDATED_HOUSING_VEHICLE_ITEM_YEAR
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemYear.in=" + UPDATED_HOUSING_VEHICLE_ITEM_YEAR);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemYear is not null
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemYear.specified=true");

        // Get all the housingVehicleItemList where housingVehicleItemYear is null
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemYear.specified=false");
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemYearContainsSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemYear contains DEFAULT_HOUSING_VEHICLE_ITEM_YEAR
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemYear.contains=" + DEFAULT_HOUSING_VEHICLE_ITEM_YEAR);

        // Get all the housingVehicleItemList where housingVehicleItemYear contains UPDATED_HOUSING_VEHICLE_ITEM_YEAR
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemYear.contains=" + UPDATED_HOUSING_VEHICLE_ITEM_YEAR);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemYearNotContainsSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemYear does not contain DEFAULT_HOUSING_VEHICLE_ITEM_YEAR
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemYear.doesNotContain=" + DEFAULT_HOUSING_VEHICLE_ITEM_YEAR);

        // Get all the housingVehicleItemList where housingVehicleItemYear does not contain UPDATED_HOUSING_VEHICLE_ITEM_YEAR
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemYear.doesNotContain=" + UPDATED_HOUSING_VEHICLE_ITEM_YEAR);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemFuelIsEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemFuel equals to DEFAULT_HOUSING_VEHICLE_ITEM_FUEL
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemFuel.equals=" + DEFAULT_HOUSING_VEHICLE_ITEM_FUEL);

        // Get all the housingVehicleItemList where housingVehicleItemFuel equals to UPDATED_HOUSING_VEHICLE_ITEM_FUEL
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemFuel.equals=" + UPDATED_HOUSING_VEHICLE_ITEM_FUEL);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemFuelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemFuel not equals to DEFAULT_HOUSING_VEHICLE_ITEM_FUEL
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemFuel.notEquals=" + DEFAULT_HOUSING_VEHICLE_ITEM_FUEL);

        // Get all the housingVehicleItemList where housingVehicleItemFuel not equals to UPDATED_HOUSING_VEHICLE_ITEM_FUEL
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemFuel.notEquals=" + UPDATED_HOUSING_VEHICLE_ITEM_FUEL);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemFuelIsInShouldWork() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemFuel in DEFAULT_HOUSING_VEHICLE_ITEM_FUEL or UPDATED_HOUSING_VEHICLE_ITEM_FUEL
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemFuel.in=" + DEFAULT_HOUSING_VEHICLE_ITEM_FUEL + "," + UPDATED_HOUSING_VEHICLE_ITEM_FUEL
        );

        // Get all the housingVehicleItemList where housingVehicleItemFuel equals to UPDATED_HOUSING_VEHICLE_ITEM_FUEL
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemFuel.in=" + UPDATED_HOUSING_VEHICLE_ITEM_FUEL);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemFuelIsNullOrNotNull() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemFuel is not null
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemFuel.specified=true");

        // Get all the housingVehicleItemList where housingVehicleItemFuel is null
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemFuel.specified=false");
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemFuelContainsSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemFuel contains DEFAULT_HOUSING_VEHICLE_ITEM_FUEL
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemFuel.contains=" + DEFAULT_HOUSING_VEHICLE_ITEM_FUEL);

        // Get all the housingVehicleItemList where housingVehicleItemFuel contains UPDATED_HOUSING_VEHICLE_ITEM_FUEL
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemFuel.contains=" + UPDATED_HOUSING_VEHICLE_ITEM_FUEL);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemFuelNotContainsSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemFuel does not contain DEFAULT_HOUSING_VEHICLE_ITEM_FUEL
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemFuel.doesNotContain=" + DEFAULT_HOUSING_VEHICLE_ITEM_FUEL);

        // Get all the housingVehicleItemList where housingVehicleItemFuel does not contain UPDATED_HOUSING_VEHICLE_ITEM_FUEL
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemFuel.doesNotContain=" + UPDATED_HOUSING_VEHICLE_ITEM_FUEL);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemBranch equals to DEFAULT_HOUSING_VEHICLE_ITEM_BRANCH
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemBranch.equals=" + DEFAULT_HOUSING_VEHICLE_ITEM_BRANCH);

        // Get all the housingVehicleItemList where housingVehicleItemBranch equals to UPDATED_HOUSING_VEHICLE_ITEM_BRANCH
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemBranch.equals=" + UPDATED_HOUSING_VEHICLE_ITEM_BRANCH);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemBranchIsNotEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemBranch not equals to DEFAULT_HOUSING_VEHICLE_ITEM_BRANCH
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemBranch.notEquals=" + DEFAULT_HOUSING_VEHICLE_ITEM_BRANCH);

        // Get all the housingVehicleItemList where housingVehicleItemBranch not equals to UPDATED_HOUSING_VEHICLE_ITEM_BRANCH
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemBranch.notEquals=" + UPDATED_HOUSING_VEHICLE_ITEM_BRANCH);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemBranchIsInShouldWork() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemBranch in DEFAULT_HOUSING_VEHICLE_ITEM_BRANCH or UPDATED_HOUSING_VEHICLE_ITEM_BRANCH
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemBranch.in=" + DEFAULT_HOUSING_VEHICLE_ITEM_BRANCH + "," + UPDATED_HOUSING_VEHICLE_ITEM_BRANCH
        );

        // Get all the housingVehicleItemList where housingVehicleItemBranch equals to UPDATED_HOUSING_VEHICLE_ITEM_BRANCH
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemBranch.in=" + UPDATED_HOUSING_VEHICLE_ITEM_BRANCH);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemBranchIsNullOrNotNull() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemBranch is not null
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemBranch.specified=true");

        // Get all the housingVehicleItemList where housingVehicleItemBranch is null
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemBranch.specified=false");
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemBranchContainsSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemBranch contains DEFAULT_HOUSING_VEHICLE_ITEM_BRANCH
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemBranch.contains=" + DEFAULT_HOUSING_VEHICLE_ITEM_BRANCH);

        // Get all the housingVehicleItemList where housingVehicleItemBranch contains UPDATED_HOUSING_VEHICLE_ITEM_BRANCH
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemBranch.contains=" + UPDATED_HOUSING_VEHICLE_ITEM_BRANCH);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemBranchNotContainsSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemBranch does not contain DEFAULT_HOUSING_VEHICLE_ITEM_BRANCH
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemBranch.doesNotContain=" + DEFAULT_HOUSING_VEHICLE_ITEM_BRANCH);

        // Get all the housingVehicleItemList where housingVehicleItemBranch does not contain UPDATED_HOUSING_VEHICLE_ITEM_BRANCH
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemBranch.doesNotContain=" + UPDATED_HOUSING_VEHICLE_ITEM_BRANCH);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemModelIsEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemModel equals to DEFAULT_HOUSING_VEHICLE_ITEM_MODEL
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemModel.equals=" + DEFAULT_HOUSING_VEHICLE_ITEM_MODEL);

        // Get all the housingVehicleItemList where housingVehicleItemModel equals to UPDATED_HOUSING_VEHICLE_ITEM_MODEL
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemModel.equals=" + UPDATED_HOUSING_VEHICLE_ITEM_MODEL);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemModelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemModel not equals to DEFAULT_HOUSING_VEHICLE_ITEM_MODEL
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemModel.notEquals=" + DEFAULT_HOUSING_VEHICLE_ITEM_MODEL);

        // Get all the housingVehicleItemList where housingVehicleItemModel not equals to UPDATED_HOUSING_VEHICLE_ITEM_MODEL
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemModel.notEquals=" + UPDATED_HOUSING_VEHICLE_ITEM_MODEL);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemModelIsInShouldWork() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemModel in DEFAULT_HOUSING_VEHICLE_ITEM_MODEL or UPDATED_HOUSING_VEHICLE_ITEM_MODEL
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemModel.in=" + DEFAULT_HOUSING_VEHICLE_ITEM_MODEL + "," + UPDATED_HOUSING_VEHICLE_ITEM_MODEL
        );

        // Get all the housingVehicleItemList where housingVehicleItemModel equals to UPDATED_HOUSING_VEHICLE_ITEM_MODEL
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemModel.in=" + UPDATED_HOUSING_VEHICLE_ITEM_MODEL);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemModelIsNullOrNotNull() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemModel is not null
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemModel.specified=true");

        // Get all the housingVehicleItemList where housingVehicleItemModel is null
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemModel.specified=false");
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemModelContainsSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemModel contains DEFAULT_HOUSING_VEHICLE_ITEM_MODEL
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemModel.contains=" + DEFAULT_HOUSING_VEHICLE_ITEM_MODEL);

        // Get all the housingVehicleItemList where housingVehicleItemModel contains UPDATED_HOUSING_VEHICLE_ITEM_MODEL
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemModel.contains=" + UPDATED_HOUSING_VEHICLE_ITEM_MODEL);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemModelNotContainsSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemModel does not contain DEFAULT_HOUSING_VEHICLE_ITEM_MODEL
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemModel.doesNotContain=" + DEFAULT_HOUSING_VEHICLE_ITEM_MODEL);

        // Get all the housingVehicleItemList where housingVehicleItemModel does not contain UPDATED_HOUSING_VEHICLE_ITEM_MODEL
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemModel.doesNotContain=" + UPDATED_HOUSING_VEHICLE_ITEM_MODEL);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemFuelAbbreviationIsEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemFuelAbbreviation equals to DEFAULT_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemFuelAbbreviation.equals=" + DEFAULT_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION
        );

        // Get all the housingVehicleItemList where housingVehicleItemFuelAbbreviation equals to UPDATED_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION
        defaultHousingVehicleItemShouldNotBeFound(
            "housingVehicleItemFuelAbbreviation.equals=" + UPDATED_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION
        );
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemFuelAbbreviationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemFuelAbbreviation not equals to DEFAULT_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION
        defaultHousingVehicleItemShouldNotBeFound(
            "housingVehicleItemFuelAbbreviation.notEquals=" + DEFAULT_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION
        );

        // Get all the housingVehicleItemList where housingVehicleItemFuelAbbreviation not equals to UPDATED_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemFuelAbbreviation.notEquals=" + UPDATED_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION
        );
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemFuelAbbreviationIsInShouldWork() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemFuelAbbreviation in DEFAULT_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION or UPDATED_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemFuelAbbreviation.in=" +
            DEFAULT_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION +
            "," +
            UPDATED_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION
        );

        // Get all the housingVehicleItemList where housingVehicleItemFuelAbbreviation equals to UPDATED_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION
        defaultHousingVehicleItemShouldNotBeFound(
            "housingVehicleItemFuelAbbreviation.in=" + UPDATED_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION
        );
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemFuelAbbreviationIsNullOrNotNull() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemFuelAbbreviation is not null
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemFuelAbbreviation.specified=true");

        // Get all the housingVehicleItemList where housingVehicleItemFuelAbbreviation is null
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemFuelAbbreviation.specified=false");
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemFuelAbbreviationContainsSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemFuelAbbreviation contains DEFAULT_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemFuelAbbreviation.contains=" + DEFAULT_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION
        );

        // Get all the housingVehicleItemList where housingVehicleItemFuelAbbreviation contains UPDATED_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION
        defaultHousingVehicleItemShouldNotBeFound(
            "housingVehicleItemFuelAbbreviation.contains=" + UPDATED_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION
        );
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemFuelAbbreviationNotContainsSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemFuelAbbreviation does not contain DEFAULT_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION
        defaultHousingVehicleItemShouldNotBeFound(
            "housingVehicleItemFuelAbbreviation.doesNotContain=" + DEFAULT_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION
        );

        // Get all the housingVehicleItemList where housingVehicleItemFuelAbbreviation does not contain UPDATED_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemFuelAbbreviation.doesNotContain=" + UPDATED_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION
        );
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemReferenceMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemReferenceMonth equals to DEFAULT_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemReferenceMonth.equals=" + DEFAULT_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH);

        // Get all the housingVehicleItemList where housingVehicleItemReferenceMonth equals to UPDATED_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH
        defaultHousingVehicleItemShouldNotBeFound(
            "housingVehicleItemReferenceMonth.equals=" + UPDATED_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH
        );
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemReferenceMonthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemReferenceMonth not equals to DEFAULT_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH
        defaultHousingVehicleItemShouldNotBeFound(
            "housingVehicleItemReferenceMonth.notEquals=" + DEFAULT_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH
        );

        // Get all the housingVehicleItemList where housingVehicleItemReferenceMonth not equals to UPDATED_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemReferenceMonth.notEquals=" + UPDATED_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH
        );
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemReferenceMonthIsInShouldWork() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemReferenceMonth in DEFAULT_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH or UPDATED_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemReferenceMonth.in=" +
            DEFAULT_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH +
            "," +
            UPDATED_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH
        );

        // Get all the housingVehicleItemList where housingVehicleItemReferenceMonth equals to UPDATED_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemReferenceMonth.in=" + UPDATED_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemReferenceMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemReferenceMonth is not null
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemReferenceMonth.specified=true");

        // Get all the housingVehicleItemList where housingVehicleItemReferenceMonth is null
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemReferenceMonth.specified=false");
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemReferenceMonthContainsSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemReferenceMonth contains DEFAULT_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemReferenceMonth.contains=" + DEFAULT_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH);

        // Get all the housingVehicleItemList where housingVehicleItemReferenceMonth contains UPDATED_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH
        defaultHousingVehicleItemShouldNotBeFound(
            "housingVehicleItemReferenceMonth.contains=" + UPDATED_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH
        );
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemReferenceMonthNotContainsSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemReferenceMonth does not contain DEFAULT_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH
        defaultHousingVehicleItemShouldNotBeFound(
            "housingVehicleItemReferenceMonth.doesNotContain=" + DEFAULT_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH
        );

        // Get all the housingVehicleItemList where housingVehicleItemReferenceMonth does not contain UPDATED_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemReferenceMonth.doesNotContain=" + UPDATED_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH
        );
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemValueIsEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemValue equals to DEFAULT_HOUSING_VEHICLE_ITEM_VALUE
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemValue.equals=" + DEFAULT_HOUSING_VEHICLE_ITEM_VALUE);

        // Get all the housingVehicleItemList where housingVehicleItemValue equals to UPDATED_HOUSING_VEHICLE_ITEM_VALUE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemValue.equals=" + UPDATED_HOUSING_VEHICLE_ITEM_VALUE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemValue not equals to DEFAULT_HOUSING_VEHICLE_ITEM_VALUE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemValue.notEquals=" + DEFAULT_HOUSING_VEHICLE_ITEM_VALUE);

        // Get all the housingVehicleItemList where housingVehicleItemValue not equals to UPDATED_HOUSING_VEHICLE_ITEM_VALUE
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemValue.notEquals=" + UPDATED_HOUSING_VEHICLE_ITEM_VALUE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemValueIsInShouldWork() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemValue in DEFAULT_HOUSING_VEHICLE_ITEM_VALUE or UPDATED_HOUSING_VEHICLE_ITEM_VALUE
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemValue.in=" + DEFAULT_HOUSING_VEHICLE_ITEM_VALUE + "," + UPDATED_HOUSING_VEHICLE_ITEM_VALUE
        );

        // Get all the housingVehicleItemList where housingVehicleItemValue equals to UPDATED_HOUSING_VEHICLE_ITEM_VALUE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemValue.in=" + UPDATED_HOUSING_VEHICLE_ITEM_VALUE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemValue is not null
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemValue.specified=true");

        // Get all the housingVehicleItemList where housingVehicleItemValue is null
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemValue.specified=false");
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemValue is greater than or equal to DEFAULT_HOUSING_VEHICLE_ITEM_VALUE
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemValue.greaterThanOrEqual=" + DEFAULT_HOUSING_VEHICLE_ITEM_VALUE);

        // Get all the housingVehicleItemList where housingVehicleItemValue is greater than or equal to UPDATED_HOUSING_VEHICLE_ITEM_VALUE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemValue.greaterThanOrEqual=" + UPDATED_HOUSING_VEHICLE_ITEM_VALUE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemValue is less than or equal to DEFAULT_HOUSING_VEHICLE_ITEM_VALUE
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemValue.lessThanOrEqual=" + DEFAULT_HOUSING_VEHICLE_ITEM_VALUE);

        // Get all the housingVehicleItemList where housingVehicleItemValue is less than or equal to SMALLER_HOUSING_VEHICLE_ITEM_VALUE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemValue.lessThanOrEqual=" + SMALLER_HOUSING_VEHICLE_ITEM_VALUE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemValueIsLessThanSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemValue is less than DEFAULT_HOUSING_VEHICLE_ITEM_VALUE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemValue.lessThan=" + DEFAULT_HOUSING_VEHICLE_ITEM_VALUE);

        // Get all the housingVehicleItemList where housingVehicleItemValue is less than UPDATED_HOUSING_VEHICLE_ITEM_VALUE
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemValue.lessThan=" + UPDATED_HOUSING_VEHICLE_ITEM_VALUE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemValue is greater than DEFAULT_HOUSING_VEHICLE_ITEM_VALUE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemValue.greaterThan=" + DEFAULT_HOUSING_VEHICLE_ITEM_VALUE);

        // Get all the housingVehicleItemList where housingVehicleItemValue is greater than SMALLER_HOUSING_VEHICLE_ITEM_VALUE
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemValue.greaterThan=" + SMALLER_HOUSING_VEHICLE_ITEM_VALUE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemShippingValueIsEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemShippingValue equals to DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemShippingValue.equals=" + DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE);

        // Get all the housingVehicleItemList where housingVehicleItemShippingValue equals to UPDATED_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemShippingValue.equals=" + UPDATED_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemShippingValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemShippingValue not equals to DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        defaultHousingVehicleItemShouldNotBeFound(
            "housingVehicleItemShippingValue.notEquals=" + DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        );

        // Get all the housingVehicleItemList where housingVehicleItemShippingValue not equals to UPDATED_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemShippingValue.notEquals=" + UPDATED_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemShippingValueIsInShouldWork() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemShippingValue in DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE or UPDATED_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemShippingValue.in=" +
            DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE +
            "," +
            UPDATED_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        );

        // Get all the housingVehicleItemList where housingVehicleItemShippingValue equals to UPDATED_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemShippingValue.in=" + UPDATED_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemShippingValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemShippingValue is not null
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemShippingValue.specified=true");

        // Get all the housingVehicleItemList where housingVehicleItemShippingValue is null
        defaultHousingVehicleItemShouldNotBeFound("housingVehicleItemShippingValue.specified=false");
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemShippingValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemShippingValue is greater than or equal to DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemShippingValue.greaterThanOrEqual=" + DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        );

        // Get all the housingVehicleItemList where housingVehicleItemShippingValue is greater than or equal to UPDATED_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        defaultHousingVehicleItemShouldNotBeFound(
            "housingVehicleItemShippingValue.greaterThanOrEqual=" + UPDATED_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        );
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemShippingValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemShippingValue is less than or equal to DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemShippingValue.lessThanOrEqual=" + DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        );

        // Get all the housingVehicleItemList where housingVehicleItemShippingValue is less than or equal to SMALLER_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        defaultHousingVehicleItemShouldNotBeFound(
            "housingVehicleItemShippingValue.lessThanOrEqual=" + SMALLER_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        );
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemShippingValueIsLessThanSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemShippingValue is less than DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        defaultHousingVehicleItemShouldNotBeFound(
            "housingVehicleItemShippingValue.lessThan=" + DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        );

        // Get all the housingVehicleItemList where housingVehicleItemShippingValue is less than UPDATED_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        defaultHousingVehicleItemShouldBeFound("housingVehicleItemShippingValue.lessThan=" + UPDATED_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE);
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingVehicleItemShippingValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        // Get all the housingVehicleItemList where housingVehicleItemShippingValue is greater than DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        defaultHousingVehicleItemShouldNotBeFound(
            "housingVehicleItemShippingValue.greaterThan=" + DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        );

        // Get all the housingVehicleItemList where housingVehicleItemShippingValue is greater than SMALLER_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        defaultHousingVehicleItemShouldBeFound(
            "housingVehicleItemShippingValue.greaterThan=" + SMALLER_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE
        );
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByHousingIsEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);
        Housing housing = HousingResourceIT.createEntity(em);
        em.persist(housing);
        em.flush();
        housingVehicleItem.setHousing(housing);
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);
        Long housingId = housing.getId();

        // Get all the housingVehicleItemList where housing equals to housingId
        defaultHousingVehicleItemShouldBeFound("housingId.equals=" + housingId);

        // Get all the housingVehicleItemList where housing equals to (housingId + 1)
        defaultHousingVehicleItemShouldNotBeFound("housingId.equals=" + (housingId + 1));
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByParkingSectorIsEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);
        ParkingSector parkingSector = ParkingSectorResourceIT.createEntity(em);
        em.persist(parkingSector);
        em.flush();
        housingVehicleItem.setParkingSector(parkingSector);
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);
        Long parkingSectorId = parkingSector.getId();

        // Get all the housingVehicleItemList where parkingSector equals to parkingSectorId
        defaultHousingVehicleItemShouldBeFound("parkingSectorId.equals=" + parkingSectorId);

        // Get all the housingVehicleItemList where parkingSector equals to (parkingSectorId + 1)
        defaultHousingVehicleItemShouldNotBeFound("parkingSectorId.equals=" + (parkingSectorId + 1));
    }

    @Test
    @Transactional
    void getAllHousingVehicleItemsByParkingSectorSpaceIsEqualToSomething() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);
        ParkingSectorSpace parkingSectorSpace = ParkingSectorSpaceResourceIT.createEntity(em);
        em.persist(parkingSectorSpace);
        em.flush();
        housingVehicleItem.setParkingSectorSpace(parkingSectorSpace);
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);
        Long parkingSectorSpaceId = parkingSectorSpace.getId();

        // Get all the housingVehicleItemList where parkingSectorSpace equals to parkingSectorSpaceId
        defaultHousingVehicleItemShouldBeFound("parkingSectorSpaceId.equals=" + parkingSectorSpaceId);

        // Get all the housingVehicleItemList where parkingSectorSpace equals to (parkingSectorSpaceId + 1)
        defaultHousingVehicleItemShouldNotBeFound("parkingSectorSpaceId.equals=" + (parkingSectorSpaceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHousingVehicleItemShouldBeFound(String filter) throws Exception {
        restHousingVehicleItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(housingVehicleItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].housingVehicleItemStatus").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_STATUS.toString())))
            .andExpect(jsonPath("$.[*].housingVehicleItemPlate").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_PLATE)))
            .andExpect(jsonPath("$.[*].housingVehicleItemType").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].housingVehicleItemFipeCode").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_FIPE_CODE)))
            .andExpect(jsonPath("$.[*].housingVehicleItemYear").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_YEAR)))
            .andExpect(jsonPath("$.[*].housingVehicleItemFuel").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_FUEL)))
            .andExpect(jsonPath("$.[*].housingVehicleItemBranch").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_BRANCH)))
            .andExpect(jsonPath("$.[*].housingVehicleItemModel").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_MODEL)))
            .andExpect(jsonPath("$.[*].housingVehicleItemFuelAbbreviation").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION)))
            .andExpect(jsonPath("$.[*].housingVehicleItemReferenceMonth").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH)))
            .andExpect(jsonPath("$.[*].housingVehicleItemValue").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_VALUE.doubleValue())))
            .andExpect(
                jsonPath("$.[*].housingVehicleItemShippingValue").value(hasItem(DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE.doubleValue()))
            );

        // Check, that the count call also returns 1
        restHousingVehicleItemMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHousingVehicleItemShouldNotBeFound(String filter) throws Exception {
        restHousingVehicleItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHousingVehicleItemMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingHousingVehicleItem() throws Exception {
        // Get the housingVehicleItem
        restHousingVehicleItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHousingVehicleItem() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        int databaseSizeBeforeUpdate = housingVehicleItemRepository.findAll().size();

        // Update the housingVehicleItem
        HousingVehicleItem updatedHousingVehicleItem = housingVehicleItemRepository.findById(housingVehicleItem.getId()).get();
        // Disconnect from session so that the updates on updatedHousingVehicleItem are not directly saved in db
        em.detach(updatedHousingVehicleItem);
        updatedHousingVehicleItem
            .housingVehicleItemStatus(UPDATED_HOUSING_VEHICLE_ITEM_STATUS)
            .housingVehicleItemPlate(UPDATED_HOUSING_VEHICLE_ITEM_PLATE)
            .housingVehicleItemType(UPDATED_HOUSING_VEHICLE_ITEM_TYPE)
            .housingVehicleItemFipeCode(UPDATED_HOUSING_VEHICLE_ITEM_FIPE_CODE)
            .housingVehicleItemYear(UPDATED_HOUSING_VEHICLE_ITEM_YEAR)
            .housingVehicleItemFuel(UPDATED_HOUSING_VEHICLE_ITEM_FUEL)
            .housingVehicleItemBranch(UPDATED_HOUSING_VEHICLE_ITEM_BRANCH)
            .housingVehicleItemModel(UPDATED_HOUSING_VEHICLE_ITEM_MODEL)
            .housingVehicleItemFuelAbbreviation(UPDATED_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION)
            .housingVehicleItemReferenceMonth(UPDATED_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH)
            .housingVehicleItemValue(UPDATED_HOUSING_VEHICLE_ITEM_VALUE)
            .housingVehicleItemShippingValue(UPDATED_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE);
        HousingVehicleItemDTO housingVehicleItemDTO = housingVehicleItemMapper.toDto(updatedHousingVehicleItem);

        restHousingVehicleItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, housingVehicleItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(housingVehicleItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the HousingVehicleItem in the database
        List<HousingVehicleItem> housingVehicleItemList = housingVehicleItemRepository.findAll();
        assertThat(housingVehicleItemList).hasSize(databaseSizeBeforeUpdate);
        HousingVehicleItem testHousingVehicleItem = housingVehicleItemList.get(housingVehicleItemList.size() - 1);
        assertThat(testHousingVehicleItem.getHousingVehicleItemStatus()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_STATUS);
        assertThat(testHousingVehicleItem.getHousingVehicleItemPlate()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_PLATE);
        assertThat(testHousingVehicleItem.getHousingVehicleItemType()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_TYPE);
        assertThat(testHousingVehicleItem.getHousingVehicleItemFipeCode()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_FIPE_CODE);
        assertThat(testHousingVehicleItem.getHousingVehicleItemYear()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_YEAR);
        assertThat(testHousingVehicleItem.getHousingVehicleItemFuel()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_FUEL);
        assertThat(testHousingVehicleItem.getHousingVehicleItemBranch()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_BRANCH);
        assertThat(testHousingVehicleItem.getHousingVehicleItemModel()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_MODEL);
        assertThat(testHousingVehicleItem.getHousingVehicleItemFuelAbbreviation())
            .isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION);
        assertThat(testHousingVehicleItem.getHousingVehicleItemReferenceMonth()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH);
        assertThat(testHousingVehicleItem.getHousingVehicleItemValue()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_VALUE);
        assertThat(testHousingVehicleItem.getHousingVehicleItemShippingValue()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingHousingVehicleItem() throws Exception {
        int databaseSizeBeforeUpdate = housingVehicleItemRepository.findAll().size();
        housingVehicleItem.setId(count.incrementAndGet());

        // Create the HousingVehicleItem
        HousingVehicleItemDTO housingVehicleItemDTO = housingVehicleItemMapper.toDto(housingVehicleItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHousingVehicleItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, housingVehicleItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(housingVehicleItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HousingVehicleItem in the database
        List<HousingVehicleItem> housingVehicleItemList = housingVehicleItemRepository.findAll();
        assertThat(housingVehicleItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHousingVehicleItem() throws Exception {
        int databaseSizeBeforeUpdate = housingVehicleItemRepository.findAll().size();
        housingVehicleItem.setId(count.incrementAndGet());

        // Create the HousingVehicleItem
        HousingVehicleItemDTO housingVehicleItemDTO = housingVehicleItemMapper.toDto(housingVehicleItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHousingVehicleItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(housingVehicleItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HousingVehicleItem in the database
        List<HousingVehicleItem> housingVehicleItemList = housingVehicleItemRepository.findAll();
        assertThat(housingVehicleItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHousingVehicleItem() throws Exception {
        int databaseSizeBeforeUpdate = housingVehicleItemRepository.findAll().size();
        housingVehicleItem.setId(count.incrementAndGet());

        // Create the HousingVehicleItem
        HousingVehicleItemDTO housingVehicleItemDTO = housingVehicleItemMapper.toDto(housingVehicleItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHousingVehicleItemMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(housingVehicleItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HousingVehicleItem in the database
        List<HousingVehicleItem> housingVehicleItemList = housingVehicleItemRepository.findAll();
        assertThat(housingVehicleItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHousingVehicleItemWithPatch() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        int databaseSizeBeforeUpdate = housingVehicleItemRepository.findAll().size();

        // Update the housingVehicleItem using partial update
        HousingVehicleItem partialUpdatedHousingVehicleItem = new HousingVehicleItem();
        partialUpdatedHousingVehicleItem.setId(housingVehicleItem.getId());

        partialUpdatedHousingVehicleItem
            .housingVehicleItemStatus(UPDATED_HOUSING_VEHICLE_ITEM_STATUS)
            .housingVehicleItemFipeCode(UPDATED_HOUSING_VEHICLE_ITEM_FIPE_CODE)
            .housingVehicleItemBranch(UPDATED_HOUSING_VEHICLE_ITEM_BRANCH)
            .housingVehicleItemModel(UPDATED_HOUSING_VEHICLE_ITEM_MODEL)
            .housingVehicleItemFuelAbbreviation(UPDATED_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION);

        restHousingVehicleItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHousingVehicleItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHousingVehicleItem))
            )
            .andExpect(status().isOk());

        // Validate the HousingVehicleItem in the database
        List<HousingVehicleItem> housingVehicleItemList = housingVehicleItemRepository.findAll();
        assertThat(housingVehicleItemList).hasSize(databaseSizeBeforeUpdate);
        HousingVehicleItem testHousingVehicleItem = housingVehicleItemList.get(housingVehicleItemList.size() - 1);
        assertThat(testHousingVehicleItem.getHousingVehicleItemStatus()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_STATUS);
        assertThat(testHousingVehicleItem.getHousingVehicleItemPlate()).isEqualTo(DEFAULT_HOUSING_VEHICLE_ITEM_PLATE);
        assertThat(testHousingVehicleItem.getHousingVehicleItemType()).isEqualTo(DEFAULT_HOUSING_VEHICLE_ITEM_TYPE);
        assertThat(testHousingVehicleItem.getHousingVehicleItemFipeCode()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_FIPE_CODE);
        assertThat(testHousingVehicleItem.getHousingVehicleItemYear()).isEqualTo(DEFAULT_HOUSING_VEHICLE_ITEM_YEAR);
        assertThat(testHousingVehicleItem.getHousingVehicleItemFuel()).isEqualTo(DEFAULT_HOUSING_VEHICLE_ITEM_FUEL);
        assertThat(testHousingVehicleItem.getHousingVehicleItemBranch()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_BRANCH);
        assertThat(testHousingVehicleItem.getHousingVehicleItemModel()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_MODEL);
        assertThat(testHousingVehicleItem.getHousingVehicleItemFuelAbbreviation())
            .isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION);
        assertThat(testHousingVehicleItem.getHousingVehicleItemReferenceMonth()).isEqualTo(DEFAULT_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH);
        assertThat(testHousingVehicleItem.getHousingVehicleItemValue()).isEqualTo(DEFAULT_HOUSING_VEHICLE_ITEM_VALUE);
        assertThat(testHousingVehicleItem.getHousingVehicleItemShippingValue()).isEqualTo(DEFAULT_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateHousingVehicleItemWithPatch() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        int databaseSizeBeforeUpdate = housingVehicleItemRepository.findAll().size();

        // Update the housingVehicleItem using partial update
        HousingVehicleItem partialUpdatedHousingVehicleItem = new HousingVehicleItem();
        partialUpdatedHousingVehicleItem.setId(housingVehicleItem.getId());

        partialUpdatedHousingVehicleItem
            .housingVehicleItemStatus(UPDATED_HOUSING_VEHICLE_ITEM_STATUS)
            .housingVehicleItemPlate(UPDATED_HOUSING_VEHICLE_ITEM_PLATE)
            .housingVehicleItemType(UPDATED_HOUSING_VEHICLE_ITEM_TYPE)
            .housingVehicleItemFipeCode(UPDATED_HOUSING_VEHICLE_ITEM_FIPE_CODE)
            .housingVehicleItemYear(UPDATED_HOUSING_VEHICLE_ITEM_YEAR)
            .housingVehicleItemFuel(UPDATED_HOUSING_VEHICLE_ITEM_FUEL)
            .housingVehicleItemBranch(UPDATED_HOUSING_VEHICLE_ITEM_BRANCH)
            .housingVehicleItemModel(UPDATED_HOUSING_VEHICLE_ITEM_MODEL)
            .housingVehicleItemFuelAbbreviation(UPDATED_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION)
            .housingVehicleItemReferenceMonth(UPDATED_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH)
            .housingVehicleItemValue(UPDATED_HOUSING_VEHICLE_ITEM_VALUE)
            .housingVehicleItemShippingValue(UPDATED_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE);

        restHousingVehicleItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHousingVehicleItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHousingVehicleItem))
            )
            .andExpect(status().isOk());

        // Validate the HousingVehicleItem in the database
        List<HousingVehicleItem> housingVehicleItemList = housingVehicleItemRepository.findAll();
        assertThat(housingVehicleItemList).hasSize(databaseSizeBeforeUpdate);
        HousingVehicleItem testHousingVehicleItem = housingVehicleItemList.get(housingVehicleItemList.size() - 1);
        assertThat(testHousingVehicleItem.getHousingVehicleItemStatus()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_STATUS);
        assertThat(testHousingVehicleItem.getHousingVehicleItemPlate()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_PLATE);
        assertThat(testHousingVehicleItem.getHousingVehicleItemType()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_TYPE);
        assertThat(testHousingVehicleItem.getHousingVehicleItemFipeCode()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_FIPE_CODE);
        assertThat(testHousingVehicleItem.getHousingVehicleItemYear()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_YEAR);
        assertThat(testHousingVehicleItem.getHousingVehicleItemFuel()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_FUEL);
        assertThat(testHousingVehicleItem.getHousingVehicleItemBranch()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_BRANCH);
        assertThat(testHousingVehicleItem.getHousingVehicleItemModel()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_MODEL);
        assertThat(testHousingVehicleItem.getHousingVehicleItemFuelAbbreviation())
            .isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_FUEL_ABBREVIATION);
        assertThat(testHousingVehicleItem.getHousingVehicleItemReferenceMonth()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_REFERENCE_MONTH);
        assertThat(testHousingVehicleItem.getHousingVehicleItemValue()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_VALUE);
        assertThat(testHousingVehicleItem.getHousingVehicleItemShippingValue()).isEqualTo(UPDATED_HOUSING_VEHICLE_ITEM_SHIPPING_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingHousingVehicleItem() throws Exception {
        int databaseSizeBeforeUpdate = housingVehicleItemRepository.findAll().size();
        housingVehicleItem.setId(count.incrementAndGet());

        // Create the HousingVehicleItem
        HousingVehicleItemDTO housingVehicleItemDTO = housingVehicleItemMapper.toDto(housingVehicleItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHousingVehicleItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, housingVehicleItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(housingVehicleItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HousingVehicleItem in the database
        List<HousingVehicleItem> housingVehicleItemList = housingVehicleItemRepository.findAll();
        assertThat(housingVehicleItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHousingVehicleItem() throws Exception {
        int databaseSizeBeforeUpdate = housingVehicleItemRepository.findAll().size();
        housingVehicleItem.setId(count.incrementAndGet());

        // Create the HousingVehicleItem
        HousingVehicleItemDTO housingVehicleItemDTO = housingVehicleItemMapper.toDto(housingVehicleItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHousingVehicleItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(housingVehicleItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HousingVehicleItem in the database
        List<HousingVehicleItem> housingVehicleItemList = housingVehicleItemRepository.findAll();
        assertThat(housingVehicleItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHousingVehicleItem() throws Exception {
        int databaseSizeBeforeUpdate = housingVehicleItemRepository.findAll().size();
        housingVehicleItem.setId(count.incrementAndGet());

        // Create the HousingVehicleItem
        HousingVehicleItemDTO housingVehicleItemDTO = housingVehicleItemMapper.toDto(housingVehicleItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHousingVehicleItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(housingVehicleItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HousingVehicleItem in the database
        List<HousingVehicleItem> housingVehicleItemList = housingVehicleItemRepository.findAll();
        assertThat(housingVehicleItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHousingVehicleItem() throws Exception {
        // Initialize the database
        housingVehicleItemRepository.saveAndFlush(housingVehicleItem);

        int databaseSizeBeforeDelete = housingVehicleItemRepository.findAll().size();

        // Delete the housingVehicleItem
        restHousingVehicleItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, housingVehicleItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HousingVehicleItem> housingVehicleItemList = housingVehicleItemRepository.findAll();
        assertThat(housingVehicleItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
