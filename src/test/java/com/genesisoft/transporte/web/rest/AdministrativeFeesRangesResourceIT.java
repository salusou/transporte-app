package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.AdministrativeFeesRanges;
import com.genesisoft.transporte.domain.Affiliates;
import com.genesisoft.transporte.repository.AdministrativeFeesRangesRepository;
import com.genesisoft.transporte.service.criteria.AdministrativeFeesRangesCriteria;
import com.genesisoft.transporte.service.dto.AdministrativeFeesRangesDTO;
import com.genesisoft.transporte.service.mapper.AdministrativeFeesRangesMapper;
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
 * Integration tests for the {@link AdministrativeFeesRangesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdministrativeFeesRangesResourceIT {

    private static final Float DEFAULT_MIN_RANGE = 1F;
    private static final Float UPDATED_MIN_RANGE = 2F;
    private static final Float SMALLER_MIN_RANGE = 1F - 1F;

    private static final Float DEFAULT_MAX_RANGE = 1F;
    private static final Float UPDATED_MAX_RANGE = 2F;
    private static final Float SMALLER_MAX_RANGE = 1F - 1F;

    private static final Float DEFAULT_ALIQUOT = 1F;
    private static final Float UPDATED_ALIQUOT = 2F;
    private static final Float SMALLER_ALIQUOT = 1F - 1F;

    private static final String ENTITY_API_URL = "/api/administrative-fees-ranges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdministrativeFeesRangesRepository administrativeFeesRangesRepository;

    @Autowired
    private AdministrativeFeesRangesMapper administrativeFeesRangesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdministrativeFeesRangesMockMvc;

    private AdministrativeFeesRanges administrativeFeesRanges;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdministrativeFeesRanges createEntity(EntityManager em) {
        AdministrativeFeesRanges administrativeFeesRanges = new AdministrativeFeesRanges()
            .minRange(DEFAULT_MIN_RANGE)
            .maxRange(DEFAULT_MAX_RANGE)
            .aliquot(DEFAULT_ALIQUOT);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        administrativeFeesRanges.setAffiliates(affiliates);
        return administrativeFeesRanges;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdministrativeFeesRanges createUpdatedEntity(EntityManager em) {
        AdministrativeFeesRanges administrativeFeesRanges = new AdministrativeFeesRanges()
            .minRange(UPDATED_MIN_RANGE)
            .maxRange(UPDATED_MAX_RANGE)
            .aliquot(UPDATED_ALIQUOT);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createUpdatedEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        administrativeFeesRanges.setAffiliates(affiliates);
        return administrativeFeesRanges;
    }

    @BeforeEach
    public void initTest() {
        administrativeFeesRanges = createEntity(em);
    }

    @Test
    @Transactional
    void createAdministrativeFeesRanges() throws Exception {
        int databaseSizeBeforeCreate = administrativeFeesRangesRepository.findAll().size();
        // Create the AdministrativeFeesRanges
        AdministrativeFeesRangesDTO administrativeFeesRangesDTO = administrativeFeesRangesMapper.toDto(administrativeFeesRanges);
        restAdministrativeFeesRangesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeFeesRangesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AdministrativeFeesRanges in the database
        List<AdministrativeFeesRanges> administrativeFeesRangesList = administrativeFeesRangesRepository.findAll();
        assertThat(administrativeFeesRangesList).hasSize(databaseSizeBeforeCreate + 1);
        AdministrativeFeesRanges testAdministrativeFeesRanges = administrativeFeesRangesList.get(administrativeFeesRangesList.size() - 1);
        assertThat(testAdministrativeFeesRanges.getMinRange()).isEqualTo(DEFAULT_MIN_RANGE);
        assertThat(testAdministrativeFeesRanges.getMaxRange()).isEqualTo(DEFAULT_MAX_RANGE);
        assertThat(testAdministrativeFeesRanges.getAliquot()).isEqualTo(DEFAULT_ALIQUOT);
    }

    @Test
    @Transactional
    void createAdministrativeFeesRangesWithExistingId() throws Exception {
        // Create the AdministrativeFeesRanges with an existing ID
        administrativeFeesRanges.setId(1L);
        AdministrativeFeesRangesDTO administrativeFeesRangesDTO = administrativeFeesRangesMapper.toDto(administrativeFeesRanges);

        int databaseSizeBeforeCreate = administrativeFeesRangesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdministrativeFeesRangesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeFeesRangesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdministrativeFeesRanges in the database
        List<AdministrativeFeesRanges> administrativeFeesRangesList = administrativeFeesRangesRepository.findAll();
        assertThat(administrativeFeesRangesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMinRangeIsRequired() throws Exception {
        int databaseSizeBeforeTest = administrativeFeesRangesRepository.findAll().size();
        // set the field null
        administrativeFeesRanges.setMinRange(null);

        // Create the AdministrativeFeesRanges, which fails.
        AdministrativeFeesRangesDTO administrativeFeesRangesDTO = administrativeFeesRangesMapper.toDto(administrativeFeesRanges);

        restAdministrativeFeesRangesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeFeesRangesDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdministrativeFeesRanges> administrativeFeesRangesList = administrativeFeesRangesRepository.findAll();
        assertThat(administrativeFeesRangesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaxRangeIsRequired() throws Exception {
        int databaseSizeBeforeTest = administrativeFeesRangesRepository.findAll().size();
        // set the field null
        administrativeFeesRanges.setMaxRange(null);

        // Create the AdministrativeFeesRanges, which fails.
        AdministrativeFeesRangesDTO administrativeFeesRangesDTO = administrativeFeesRangesMapper.toDto(administrativeFeesRanges);

        restAdministrativeFeesRangesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeFeesRangesDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdministrativeFeesRanges> administrativeFeesRangesList = administrativeFeesRangesRepository.findAll();
        assertThat(administrativeFeesRangesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAliquotIsRequired() throws Exception {
        int databaseSizeBeforeTest = administrativeFeesRangesRepository.findAll().size();
        // set the field null
        administrativeFeesRanges.setAliquot(null);

        // Create the AdministrativeFeesRanges, which fails.
        AdministrativeFeesRangesDTO administrativeFeesRangesDTO = administrativeFeesRangesMapper.toDto(administrativeFeesRanges);

        restAdministrativeFeesRangesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeFeesRangesDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdministrativeFeesRanges> administrativeFeesRangesList = administrativeFeesRangesRepository.findAll();
        assertThat(administrativeFeesRangesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRanges() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList
        restAdministrativeFeesRangesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(administrativeFeesRanges.getId().intValue())))
            .andExpect(jsonPath("$.[*].minRange").value(hasItem(DEFAULT_MIN_RANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxRange").value(hasItem(DEFAULT_MAX_RANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].aliquot").value(hasItem(DEFAULT_ALIQUOT.doubleValue())));
    }

    @Test
    @Transactional
    void getAdministrativeFeesRanges() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get the administrativeFeesRanges
        restAdministrativeFeesRangesMockMvc
            .perform(get(ENTITY_API_URL_ID, administrativeFeesRanges.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(administrativeFeesRanges.getId().intValue()))
            .andExpect(jsonPath("$.minRange").value(DEFAULT_MIN_RANGE.doubleValue()))
            .andExpect(jsonPath("$.maxRange").value(DEFAULT_MAX_RANGE.doubleValue()))
            .andExpect(jsonPath("$.aliquot").value(DEFAULT_ALIQUOT.doubleValue()));
    }

    @Test
    @Transactional
    void getAdministrativeFeesRangesByIdFiltering() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        Long id = administrativeFeesRanges.getId();

        defaultAdministrativeFeesRangesShouldBeFound("id.equals=" + id);
        defaultAdministrativeFeesRangesShouldNotBeFound("id.notEquals=" + id);

        defaultAdministrativeFeesRangesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdministrativeFeesRangesShouldNotBeFound("id.greaterThan=" + id);

        defaultAdministrativeFeesRangesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdministrativeFeesRangesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByMinRangeIsEqualToSomething() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where minRange equals to DEFAULT_MIN_RANGE
        defaultAdministrativeFeesRangesShouldBeFound("minRange.equals=" + DEFAULT_MIN_RANGE);

        // Get all the administrativeFeesRangesList where minRange equals to UPDATED_MIN_RANGE
        defaultAdministrativeFeesRangesShouldNotBeFound("minRange.equals=" + UPDATED_MIN_RANGE);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByMinRangeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where minRange not equals to DEFAULT_MIN_RANGE
        defaultAdministrativeFeesRangesShouldNotBeFound("minRange.notEquals=" + DEFAULT_MIN_RANGE);

        // Get all the administrativeFeesRangesList where minRange not equals to UPDATED_MIN_RANGE
        defaultAdministrativeFeesRangesShouldBeFound("minRange.notEquals=" + UPDATED_MIN_RANGE);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByMinRangeIsInShouldWork() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where minRange in DEFAULT_MIN_RANGE or UPDATED_MIN_RANGE
        defaultAdministrativeFeesRangesShouldBeFound("minRange.in=" + DEFAULT_MIN_RANGE + "," + UPDATED_MIN_RANGE);

        // Get all the administrativeFeesRangesList where minRange equals to UPDATED_MIN_RANGE
        defaultAdministrativeFeesRangesShouldNotBeFound("minRange.in=" + UPDATED_MIN_RANGE);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByMinRangeIsNullOrNotNull() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where minRange is not null
        defaultAdministrativeFeesRangesShouldBeFound("minRange.specified=true");

        // Get all the administrativeFeesRangesList where minRange is null
        defaultAdministrativeFeesRangesShouldNotBeFound("minRange.specified=false");
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByMinRangeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where minRange is greater than or equal to DEFAULT_MIN_RANGE
        defaultAdministrativeFeesRangesShouldBeFound("minRange.greaterThanOrEqual=" + DEFAULT_MIN_RANGE);

        // Get all the administrativeFeesRangesList where minRange is greater than or equal to UPDATED_MIN_RANGE
        defaultAdministrativeFeesRangesShouldNotBeFound("minRange.greaterThanOrEqual=" + UPDATED_MIN_RANGE);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByMinRangeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where minRange is less than or equal to DEFAULT_MIN_RANGE
        defaultAdministrativeFeesRangesShouldBeFound("minRange.lessThanOrEqual=" + DEFAULT_MIN_RANGE);

        // Get all the administrativeFeesRangesList where minRange is less than or equal to SMALLER_MIN_RANGE
        defaultAdministrativeFeesRangesShouldNotBeFound("minRange.lessThanOrEqual=" + SMALLER_MIN_RANGE);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByMinRangeIsLessThanSomething() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where minRange is less than DEFAULT_MIN_RANGE
        defaultAdministrativeFeesRangesShouldNotBeFound("minRange.lessThan=" + DEFAULT_MIN_RANGE);

        // Get all the administrativeFeesRangesList where minRange is less than UPDATED_MIN_RANGE
        defaultAdministrativeFeesRangesShouldBeFound("minRange.lessThan=" + UPDATED_MIN_RANGE);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByMinRangeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where minRange is greater than DEFAULT_MIN_RANGE
        defaultAdministrativeFeesRangesShouldNotBeFound("minRange.greaterThan=" + DEFAULT_MIN_RANGE);

        // Get all the administrativeFeesRangesList where minRange is greater than SMALLER_MIN_RANGE
        defaultAdministrativeFeesRangesShouldBeFound("minRange.greaterThan=" + SMALLER_MIN_RANGE);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByMaxRangeIsEqualToSomething() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where maxRange equals to DEFAULT_MAX_RANGE
        defaultAdministrativeFeesRangesShouldBeFound("maxRange.equals=" + DEFAULT_MAX_RANGE);

        // Get all the administrativeFeesRangesList where maxRange equals to UPDATED_MAX_RANGE
        defaultAdministrativeFeesRangesShouldNotBeFound("maxRange.equals=" + UPDATED_MAX_RANGE);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByMaxRangeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where maxRange not equals to DEFAULT_MAX_RANGE
        defaultAdministrativeFeesRangesShouldNotBeFound("maxRange.notEquals=" + DEFAULT_MAX_RANGE);

        // Get all the administrativeFeesRangesList where maxRange not equals to UPDATED_MAX_RANGE
        defaultAdministrativeFeesRangesShouldBeFound("maxRange.notEquals=" + UPDATED_MAX_RANGE);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByMaxRangeIsInShouldWork() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where maxRange in DEFAULT_MAX_RANGE or UPDATED_MAX_RANGE
        defaultAdministrativeFeesRangesShouldBeFound("maxRange.in=" + DEFAULT_MAX_RANGE + "," + UPDATED_MAX_RANGE);

        // Get all the administrativeFeesRangesList where maxRange equals to UPDATED_MAX_RANGE
        defaultAdministrativeFeesRangesShouldNotBeFound("maxRange.in=" + UPDATED_MAX_RANGE);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByMaxRangeIsNullOrNotNull() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where maxRange is not null
        defaultAdministrativeFeesRangesShouldBeFound("maxRange.specified=true");

        // Get all the administrativeFeesRangesList where maxRange is null
        defaultAdministrativeFeesRangesShouldNotBeFound("maxRange.specified=false");
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByMaxRangeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where maxRange is greater than or equal to DEFAULT_MAX_RANGE
        defaultAdministrativeFeesRangesShouldBeFound("maxRange.greaterThanOrEqual=" + DEFAULT_MAX_RANGE);

        // Get all the administrativeFeesRangesList where maxRange is greater than or equal to UPDATED_MAX_RANGE
        defaultAdministrativeFeesRangesShouldNotBeFound("maxRange.greaterThanOrEqual=" + UPDATED_MAX_RANGE);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByMaxRangeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where maxRange is less than or equal to DEFAULT_MAX_RANGE
        defaultAdministrativeFeesRangesShouldBeFound("maxRange.lessThanOrEqual=" + DEFAULT_MAX_RANGE);

        // Get all the administrativeFeesRangesList where maxRange is less than or equal to SMALLER_MAX_RANGE
        defaultAdministrativeFeesRangesShouldNotBeFound("maxRange.lessThanOrEqual=" + SMALLER_MAX_RANGE);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByMaxRangeIsLessThanSomething() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where maxRange is less than DEFAULT_MAX_RANGE
        defaultAdministrativeFeesRangesShouldNotBeFound("maxRange.lessThan=" + DEFAULT_MAX_RANGE);

        // Get all the administrativeFeesRangesList where maxRange is less than UPDATED_MAX_RANGE
        defaultAdministrativeFeesRangesShouldBeFound("maxRange.lessThan=" + UPDATED_MAX_RANGE);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByMaxRangeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where maxRange is greater than DEFAULT_MAX_RANGE
        defaultAdministrativeFeesRangesShouldNotBeFound("maxRange.greaterThan=" + DEFAULT_MAX_RANGE);

        // Get all the administrativeFeesRangesList where maxRange is greater than SMALLER_MAX_RANGE
        defaultAdministrativeFeesRangesShouldBeFound("maxRange.greaterThan=" + SMALLER_MAX_RANGE);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByAliquotIsEqualToSomething() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where aliquot equals to DEFAULT_ALIQUOT
        defaultAdministrativeFeesRangesShouldBeFound("aliquot.equals=" + DEFAULT_ALIQUOT);

        // Get all the administrativeFeesRangesList where aliquot equals to UPDATED_ALIQUOT
        defaultAdministrativeFeesRangesShouldNotBeFound("aliquot.equals=" + UPDATED_ALIQUOT);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByAliquotIsNotEqualToSomething() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where aliquot not equals to DEFAULT_ALIQUOT
        defaultAdministrativeFeesRangesShouldNotBeFound("aliquot.notEquals=" + DEFAULT_ALIQUOT);

        // Get all the administrativeFeesRangesList where aliquot not equals to UPDATED_ALIQUOT
        defaultAdministrativeFeesRangesShouldBeFound("aliquot.notEquals=" + UPDATED_ALIQUOT);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByAliquotIsInShouldWork() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where aliquot in DEFAULT_ALIQUOT or UPDATED_ALIQUOT
        defaultAdministrativeFeesRangesShouldBeFound("aliquot.in=" + DEFAULT_ALIQUOT + "," + UPDATED_ALIQUOT);

        // Get all the administrativeFeesRangesList where aliquot equals to UPDATED_ALIQUOT
        defaultAdministrativeFeesRangesShouldNotBeFound("aliquot.in=" + UPDATED_ALIQUOT);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByAliquotIsNullOrNotNull() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where aliquot is not null
        defaultAdministrativeFeesRangesShouldBeFound("aliquot.specified=true");

        // Get all the administrativeFeesRangesList where aliquot is null
        defaultAdministrativeFeesRangesShouldNotBeFound("aliquot.specified=false");
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByAliquotIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where aliquot is greater than or equal to DEFAULT_ALIQUOT
        defaultAdministrativeFeesRangesShouldBeFound("aliquot.greaterThanOrEqual=" + DEFAULT_ALIQUOT);

        // Get all the administrativeFeesRangesList where aliquot is greater than or equal to UPDATED_ALIQUOT
        defaultAdministrativeFeesRangesShouldNotBeFound("aliquot.greaterThanOrEqual=" + UPDATED_ALIQUOT);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByAliquotIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where aliquot is less than or equal to DEFAULT_ALIQUOT
        defaultAdministrativeFeesRangesShouldBeFound("aliquot.lessThanOrEqual=" + DEFAULT_ALIQUOT);

        // Get all the administrativeFeesRangesList where aliquot is less than or equal to SMALLER_ALIQUOT
        defaultAdministrativeFeesRangesShouldNotBeFound("aliquot.lessThanOrEqual=" + SMALLER_ALIQUOT);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByAliquotIsLessThanSomething() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where aliquot is less than DEFAULT_ALIQUOT
        defaultAdministrativeFeesRangesShouldNotBeFound("aliquot.lessThan=" + DEFAULT_ALIQUOT);

        // Get all the administrativeFeesRangesList where aliquot is less than UPDATED_ALIQUOT
        defaultAdministrativeFeesRangesShouldBeFound("aliquot.lessThan=" + UPDATED_ALIQUOT);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByAliquotIsGreaterThanSomething() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        // Get all the administrativeFeesRangesList where aliquot is greater than DEFAULT_ALIQUOT
        defaultAdministrativeFeesRangesShouldNotBeFound("aliquot.greaterThan=" + DEFAULT_ALIQUOT);

        // Get all the administrativeFeesRangesList where aliquot is greater than SMALLER_ALIQUOT
        defaultAdministrativeFeesRangesShouldBeFound("aliquot.greaterThan=" + SMALLER_ALIQUOT);
    }

    @Test
    @Transactional
    void getAllAdministrativeFeesRangesByAffiliatesIsEqualToSomething() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);
        Affiliates affiliates = AffiliatesResourceIT.createEntity(em);
        em.persist(affiliates);
        em.flush();
        administrativeFeesRanges.setAffiliates(affiliates);
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);
        Long affiliatesId = affiliates.getId();

        // Get all the administrativeFeesRangesList where affiliates equals to affiliatesId
        defaultAdministrativeFeesRangesShouldBeFound("affiliatesId.equals=" + affiliatesId);

        // Get all the administrativeFeesRangesList where affiliates equals to (affiliatesId + 1)
        defaultAdministrativeFeesRangesShouldNotBeFound("affiliatesId.equals=" + (affiliatesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdministrativeFeesRangesShouldBeFound(String filter) throws Exception {
        restAdministrativeFeesRangesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(administrativeFeesRanges.getId().intValue())))
            .andExpect(jsonPath("$.[*].minRange").value(hasItem(DEFAULT_MIN_RANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxRange").value(hasItem(DEFAULT_MAX_RANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].aliquot").value(hasItem(DEFAULT_ALIQUOT.doubleValue())));

        // Check, that the count call also returns 1
        restAdministrativeFeesRangesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdministrativeFeesRangesShouldNotBeFound(String filter) throws Exception {
        restAdministrativeFeesRangesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdministrativeFeesRangesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAdministrativeFeesRanges() throws Exception {
        // Get the administrativeFeesRanges
        restAdministrativeFeesRangesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdministrativeFeesRanges() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        int databaseSizeBeforeUpdate = administrativeFeesRangesRepository.findAll().size();

        // Update the administrativeFeesRanges
        AdministrativeFeesRanges updatedAdministrativeFeesRanges = administrativeFeesRangesRepository
            .findById(administrativeFeesRanges.getId())
            .get();
        // Disconnect from session so that the updates on updatedAdministrativeFeesRanges are not directly saved in db
        em.detach(updatedAdministrativeFeesRanges);
        updatedAdministrativeFeesRanges.minRange(UPDATED_MIN_RANGE).maxRange(UPDATED_MAX_RANGE).aliquot(UPDATED_ALIQUOT);
        AdministrativeFeesRangesDTO administrativeFeesRangesDTO = administrativeFeesRangesMapper.toDto(updatedAdministrativeFeesRanges);

        restAdministrativeFeesRangesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, administrativeFeesRangesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeFeesRangesDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdministrativeFeesRanges in the database
        List<AdministrativeFeesRanges> administrativeFeesRangesList = administrativeFeesRangesRepository.findAll();
        assertThat(administrativeFeesRangesList).hasSize(databaseSizeBeforeUpdate);
        AdministrativeFeesRanges testAdministrativeFeesRanges = administrativeFeesRangesList.get(administrativeFeesRangesList.size() - 1);
        assertThat(testAdministrativeFeesRanges.getMinRange()).isEqualTo(UPDATED_MIN_RANGE);
        assertThat(testAdministrativeFeesRanges.getMaxRange()).isEqualTo(UPDATED_MAX_RANGE);
        assertThat(testAdministrativeFeesRanges.getAliquot()).isEqualTo(UPDATED_ALIQUOT);
    }

    @Test
    @Transactional
    void putNonExistingAdministrativeFeesRanges() throws Exception {
        int databaseSizeBeforeUpdate = administrativeFeesRangesRepository.findAll().size();
        administrativeFeesRanges.setId(count.incrementAndGet());

        // Create the AdministrativeFeesRanges
        AdministrativeFeesRangesDTO administrativeFeesRangesDTO = administrativeFeesRangesMapper.toDto(administrativeFeesRanges);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdministrativeFeesRangesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, administrativeFeesRangesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeFeesRangesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdministrativeFeesRanges in the database
        List<AdministrativeFeesRanges> administrativeFeesRangesList = administrativeFeesRangesRepository.findAll();
        assertThat(administrativeFeesRangesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdministrativeFeesRanges() throws Exception {
        int databaseSizeBeforeUpdate = administrativeFeesRangesRepository.findAll().size();
        administrativeFeesRanges.setId(count.incrementAndGet());

        // Create the AdministrativeFeesRanges
        AdministrativeFeesRangesDTO administrativeFeesRangesDTO = administrativeFeesRangesMapper.toDto(administrativeFeesRanges);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdministrativeFeesRangesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeFeesRangesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdministrativeFeesRanges in the database
        List<AdministrativeFeesRanges> administrativeFeesRangesList = administrativeFeesRangesRepository.findAll();
        assertThat(administrativeFeesRangesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdministrativeFeesRanges() throws Exception {
        int databaseSizeBeforeUpdate = administrativeFeesRangesRepository.findAll().size();
        administrativeFeesRanges.setId(count.incrementAndGet());

        // Create the AdministrativeFeesRanges
        AdministrativeFeesRangesDTO administrativeFeesRangesDTO = administrativeFeesRangesMapper.toDto(administrativeFeesRanges);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdministrativeFeesRangesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrativeFeesRangesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdministrativeFeesRanges in the database
        List<AdministrativeFeesRanges> administrativeFeesRangesList = administrativeFeesRangesRepository.findAll();
        assertThat(administrativeFeesRangesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdministrativeFeesRangesWithPatch() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        int databaseSizeBeforeUpdate = administrativeFeesRangesRepository.findAll().size();

        // Update the administrativeFeesRanges using partial update
        AdministrativeFeesRanges partialUpdatedAdministrativeFeesRanges = new AdministrativeFeesRanges();
        partialUpdatedAdministrativeFeesRanges.setId(administrativeFeesRanges.getId());

        partialUpdatedAdministrativeFeesRanges.minRange(UPDATED_MIN_RANGE).aliquot(UPDATED_ALIQUOT);

        restAdministrativeFeesRangesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdministrativeFeesRanges.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdministrativeFeesRanges))
            )
            .andExpect(status().isOk());

        // Validate the AdministrativeFeesRanges in the database
        List<AdministrativeFeesRanges> administrativeFeesRangesList = administrativeFeesRangesRepository.findAll();
        assertThat(administrativeFeesRangesList).hasSize(databaseSizeBeforeUpdate);
        AdministrativeFeesRanges testAdministrativeFeesRanges = administrativeFeesRangesList.get(administrativeFeesRangesList.size() - 1);
        assertThat(testAdministrativeFeesRanges.getMinRange()).isEqualTo(UPDATED_MIN_RANGE);
        assertThat(testAdministrativeFeesRanges.getMaxRange()).isEqualTo(DEFAULT_MAX_RANGE);
        assertThat(testAdministrativeFeesRanges.getAliquot()).isEqualTo(UPDATED_ALIQUOT);
    }

    @Test
    @Transactional
    void fullUpdateAdministrativeFeesRangesWithPatch() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        int databaseSizeBeforeUpdate = administrativeFeesRangesRepository.findAll().size();

        // Update the administrativeFeesRanges using partial update
        AdministrativeFeesRanges partialUpdatedAdministrativeFeesRanges = new AdministrativeFeesRanges();
        partialUpdatedAdministrativeFeesRanges.setId(administrativeFeesRanges.getId());

        partialUpdatedAdministrativeFeesRanges.minRange(UPDATED_MIN_RANGE).maxRange(UPDATED_MAX_RANGE).aliquot(UPDATED_ALIQUOT);

        restAdministrativeFeesRangesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdministrativeFeesRanges.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdministrativeFeesRanges))
            )
            .andExpect(status().isOk());

        // Validate the AdministrativeFeesRanges in the database
        List<AdministrativeFeesRanges> administrativeFeesRangesList = administrativeFeesRangesRepository.findAll();
        assertThat(administrativeFeesRangesList).hasSize(databaseSizeBeforeUpdate);
        AdministrativeFeesRanges testAdministrativeFeesRanges = administrativeFeesRangesList.get(administrativeFeesRangesList.size() - 1);
        assertThat(testAdministrativeFeesRanges.getMinRange()).isEqualTo(UPDATED_MIN_RANGE);
        assertThat(testAdministrativeFeesRanges.getMaxRange()).isEqualTo(UPDATED_MAX_RANGE);
        assertThat(testAdministrativeFeesRanges.getAliquot()).isEqualTo(UPDATED_ALIQUOT);
    }

    @Test
    @Transactional
    void patchNonExistingAdministrativeFeesRanges() throws Exception {
        int databaseSizeBeforeUpdate = administrativeFeesRangesRepository.findAll().size();
        administrativeFeesRanges.setId(count.incrementAndGet());

        // Create the AdministrativeFeesRanges
        AdministrativeFeesRangesDTO administrativeFeesRangesDTO = administrativeFeesRangesMapper.toDto(administrativeFeesRanges);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdministrativeFeesRangesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, administrativeFeesRangesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(administrativeFeesRangesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdministrativeFeesRanges in the database
        List<AdministrativeFeesRanges> administrativeFeesRangesList = administrativeFeesRangesRepository.findAll();
        assertThat(administrativeFeesRangesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdministrativeFeesRanges() throws Exception {
        int databaseSizeBeforeUpdate = administrativeFeesRangesRepository.findAll().size();
        administrativeFeesRanges.setId(count.incrementAndGet());

        // Create the AdministrativeFeesRanges
        AdministrativeFeesRangesDTO administrativeFeesRangesDTO = administrativeFeesRangesMapper.toDto(administrativeFeesRanges);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdministrativeFeesRangesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(administrativeFeesRangesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdministrativeFeesRanges in the database
        List<AdministrativeFeesRanges> administrativeFeesRangesList = administrativeFeesRangesRepository.findAll();
        assertThat(administrativeFeesRangesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdministrativeFeesRanges() throws Exception {
        int databaseSizeBeforeUpdate = administrativeFeesRangesRepository.findAll().size();
        administrativeFeesRanges.setId(count.incrementAndGet());

        // Create the AdministrativeFeesRanges
        AdministrativeFeesRangesDTO administrativeFeesRangesDTO = administrativeFeesRangesMapper.toDto(administrativeFeesRanges);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdministrativeFeesRangesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(administrativeFeesRangesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdministrativeFeesRanges in the database
        List<AdministrativeFeesRanges> administrativeFeesRangesList = administrativeFeesRangesRepository.findAll();
        assertThat(administrativeFeesRangesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdministrativeFeesRanges() throws Exception {
        // Initialize the database
        administrativeFeesRangesRepository.saveAndFlush(administrativeFeesRanges);

        int databaseSizeBeforeDelete = administrativeFeesRangesRepository.findAll().size();

        // Delete the administrativeFeesRanges
        restAdministrativeFeesRangesMockMvc
            .perform(delete(ENTITY_API_URL_ID, administrativeFeesRanges.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdministrativeFeesRanges> administrativeFeesRangesList = administrativeFeesRangesRepository.findAll();
        assertThat(administrativeFeesRangesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
