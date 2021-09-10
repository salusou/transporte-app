package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Affiliates;
import com.genesisoft.transporte.domain.Housing;
import com.genesisoft.transporte.domain.Status;
import com.genesisoft.transporte.domain.VehicleControls;
import com.genesisoft.transporte.domain.enumeration.ScreenType;
import com.genesisoft.transporte.repository.StatusRepository;
import com.genesisoft.transporte.service.criteria.StatusCriteria;
import com.genesisoft.transporte.service.dto.StatusDTO;
import com.genesisoft.transporte.service.mapper.StatusMapper;
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
 * Integration tests for the {@link StatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StatusResourceIT {

    private static final String DEFAULT_STATUS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_NAME = "BBBBBBBBBB";

    private static final ScreenType DEFAULT_SCREEN_TYPE = ScreenType.SUPPLIERS;
    private static final ScreenType UPDATED_SCREEN_TYPE = ScreenType.VEHICLE_CONTROL;

    private static final String DEFAULT_STATUS_DESCRIPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_DESCRIPTIONS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatusMockMvc;

    private Status status;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Status createEntity(EntityManager em) {
        Status status = new Status()
            .statusName(DEFAULT_STATUS_NAME)
            .screenType(DEFAULT_SCREEN_TYPE)
            .statusDescriptions(DEFAULT_STATUS_DESCRIPTIONS);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        status.setAffiliates(affiliates);
        return status;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Status createUpdatedEntity(EntityManager em) {
        Status status = new Status()
            .statusName(UPDATED_STATUS_NAME)
            .screenType(UPDATED_SCREEN_TYPE)
            .statusDescriptions(UPDATED_STATUS_DESCRIPTIONS);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createUpdatedEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        status.setAffiliates(affiliates);
        return status;
    }

    @BeforeEach
    public void initTest() {
        status = createEntity(em);
    }

    @Test
    @Transactional
    void createStatus() throws Exception {
        int databaseSizeBeforeCreate = statusRepository.findAll().size();
        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);
        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isCreated());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeCreate + 1);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getStatusName()).isEqualTo(DEFAULT_STATUS_NAME);
        assertThat(testStatus.getScreenType()).isEqualTo(DEFAULT_SCREEN_TYPE);
        assertThat(testStatus.getStatusDescriptions()).isEqualTo(DEFAULT_STATUS_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void createStatusWithExistingId() throws Exception {
        // Create the Status with an existing ID
        status.setId(1L);
        StatusDTO statusDTO = statusMapper.toDto(status);

        int databaseSizeBeforeCreate = statusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStatusNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setStatusName(null);

        // Create the Status, which fails.
        StatusDTO statusDTO = statusMapper.toDto(status);

        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkScreenTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setScreenType(null);

        // Create the Status, which fails.
        StatusDTO statusDTO = statusMapper.toDto(status);

        restStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStatuses() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList
        restStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(status.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusName").value(hasItem(DEFAULT_STATUS_NAME)))
            .andExpect(jsonPath("$.[*].screenType").value(hasItem(DEFAULT_SCREEN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].statusDescriptions").value(hasItem(DEFAULT_STATUS_DESCRIPTIONS)));
    }

    @Test
    @Transactional
    void getStatus() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get the status
        restStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, status.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(status.getId().intValue()))
            .andExpect(jsonPath("$.statusName").value(DEFAULT_STATUS_NAME))
            .andExpect(jsonPath("$.screenType").value(DEFAULT_SCREEN_TYPE.toString()))
            .andExpect(jsonPath("$.statusDescriptions").value(DEFAULT_STATUS_DESCRIPTIONS));
    }

    @Test
    @Transactional
    void getStatusesByIdFiltering() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        Long id = status.getId();

        defaultStatusShouldBeFound("id.equals=" + id);
        defaultStatusShouldNotBeFound("id.notEquals=" + id);

        defaultStatusShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStatusShouldNotBeFound("id.greaterThan=" + id);

        defaultStatusShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStatusShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusNameIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusName equals to DEFAULT_STATUS_NAME
        defaultStatusShouldBeFound("statusName.equals=" + DEFAULT_STATUS_NAME);

        // Get all the statusList where statusName equals to UPDATED_STATUS_NAME
        defaultStatusShouldNotBeFound("statusName.equals=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusName not equals to DEFAULT_STATUS_NAME
        defaultStatusShouldNotBeFound("statusName.notEquals=" + DEFAULT_STATUS_NAME);

        // Get all the statusList where statusName not equals to UPDATED_STATUS_NAME
        defaultStatusShouldBeFound("statusName.notEquals=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusNameIsInShouldWork() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusName in DEFAULT_STATUS_NAME or UPDATED_STATUS_NAME
        defaultStatusShouldBeFound("statusName.in=" + DEFAULT_STATUS_NAME + "," + UPDATED_STATUS_NAME);

        // Get all the statusList where statusName equals to UPDATED_STATUS_NAME
        defaultStatusShouldNotBeFound("statusName.in=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusName is not null
        defaultStatusShouldBeFound("statusName.specified=true");

        // Get all the statusList where statusName is null
        defaultStatusShouldNotBeFound("statusName.specified=false");
    }

    @Test
    @Transactional
    void getAllStatusesByStatusNameContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusName contains DEFAULT_STATUS_NAME
        defaultStatusShouldBeFound("statusName.contains=" + DEFAULT_STATUS_NAME);

        // Get all the statusList where statusName contains UPDATED_STATUS_NAME
        defaultStatusShouldNotBeFound("statusName.contains=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusNameNotContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusName does not contain DEFAULT_STATUS_NAME
        defaultStatusShouldNotBeFound("statusName.doesNotContain=" + DEFAULT_STATUS_NAME);

        // Get all the statusList where statusName does not contain UPDATED_STATUS_NAME
        defaultStatusShouldBeFound("statusName.doesNotContain=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllStatusesByScreenTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where screenType equals to DEFAULT_SCREEN_TYPE
        defaultStatusShouldBeFound("screenType.equals=" + DEFAULT_SCREEN_TYPE);

        // Get all the statusList where screenType equals to UPDATED_SCREEN_TYPE
        defaultStatusShouldNotBeFound("screenType.equals=" + UPDATED_SCREEN_TYPE);
    }

    @Test
    @Transactional
    void getAllStatusesByScreenTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where screenType not equals to DEFAULT_SCREEN_TYPE
        defaultStatusShouldNotBeFound("screenType.notEquals=" + DEFAULT_SCREEN_TYPE);

        // Get all the statusList where screenType not equals to UPDATED_SCREEN_TYPE
        defaultStatusShouldBeFound("screenType.notEquals=" + UPDATED_SCREEN_TYPE);
    }

    @Test
    @Transactional
    void getAllStatusesByScreenTypeIsInShouldWork() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where screenType in DEFAULT_SCREEN_TYPE or UPDATED_SCREEN_TYPE
        defaultStatusShouldBeFound("screenType.in=" + DEFAULT_SCREEN_TYPE + "," + UPDATED_SCREEN_TYPE);

        // Get all the statusList where screenType equals to UPDATED_SCREEN_TYPE
        defaultStatusShouldNotBeFound("screenType.in=" + UPDATED_SCREEN_TYPE);
    }

    @Test
    @Transactional
    void getAllStatusesByScreenTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where screenType is not null
        defaultStatusShouldBeFound("screenType.specified=true");

        // Get all the statusList where screenType is null
        defaultStatusShouldNotBeFound("screenType.specified=false");
    }

    @Test
    @Transactional
    void getAllStatusesByStatusDescriptionsIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusDescriptions equals to DEFAULT_STATUS_DESCRIPTIONS
        defaultStatusShouldBeFound("statusDescriptions.equals=" + DEFAULT_STATUS_DESCRIPTIONS);

        // Get all the statusList where statusDescriptions equals to UPDATED_STATUS_DESCRIPTIONS
        defaultStatusShouldNotBeFound("statusDescriptions.equals=" + UPDATED_STATUS_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusDescriptionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusDescriptions not equals to DEFAULT_STATUS_DESCRIPTIONS
        defaultStatusShouldNotBeFound("statusDescriptions.notEquals=" + DEFAULT_STATUS_DESCRIPTIONS);

        // Get all the statusList where statusDescriptions not equals to UPDATED_STATUS_DESCRIPTIONS
        defaultStatusShouldBeFound("statusDescriptions.notEquals=" + UPDATED_STATUS_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusDescriptionsIsInShouldWork() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusDescriptions in DEFAULT_STATUS_DESCRIPTIONS or UPDATED_STATUS_DESCRIPTIONS
        defaultStatusShouldBeFound("statusDescriptions.in=" + DEFAULT_STATUS_DESCRIPTIONS + "," + UPDATED_STATUS_DESCRIPTIONS);

        // Get all the statusList where statusDescriptions equals to UPDATED_STATUS_DESCRIPTIONS
        defaultStatusShouldNotBeFound("statusDescriptions.in=" + UPDATED_STATUS_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusDescriptionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusDescriptions is not null
        defaultStatusShouldBeFound("statusDescriptions.specified=true");

        // Get all the statusList where statusDescriptions is null
        defaultStatusShouldNotBeFound("statusDescriptions.specified=false");
    }

    @Test
    @Transactional
    void getAllStatusesByStatusDescriptionsContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusDescriptions contains DEFAULT_STATUS_DESCRIPTIONS
        defaultStatusShouldBeFound("statusDescriptions.contains=" + DEFAULT_STATUS_DESCRIPTIONS);

        // Get all the statusList where statusDescriptions contains UPDATED_STATUS_DESCRIPTIONS
        defaultStatusShouldNotBeFound("statusDescriptions.contains=" + UPDATED_STATUS_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void getAllStatusesByStatusDescriptionsNotContainsSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList where statusDescriptions does not contain DEFAULT_STATUS_DESCRIPTIONS
        defaultStatusShouldNotBeFound("statusDescriptions.doesNotContain=" + DEFAULT_STATUS_DESCRIPTIONS);

        // Get all the statusList where statusDescriptions does not contain UPDATED_STATUS_DESCRIPTIONS
        defaultStatusShouldBeFound("statusDescriptions.doesNotContain=" + UPDATED_STATUS_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void getAllStatusesByVehicleControlsIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);
        VehicleControls vehicleControls = VehicleControlsResourceIT.createEntity(em);
        em.persist(vehicleControls);
        em.flush();
        status.addVehicleControls(vehicleControls);
        statusRepository.saveAndFlush(status);
        Long vehicleControlsId = vehicleControls.getId();

        // Get all the statusList where vehicleControls equals to vehicleControlsId
        defaultStatusShouldBeFound("vehicleControlsId.equals=" + vehicleControlsId);

        // Get all the statusList where vehicleControls equals to (vehicleControlsId + 1)
        defaultStatusShouldNotBeFound("vehicleControlsId.equals=" + (vehicleControlsId + 1));
    }

    @Test
    @Transactional
    void getAllStatusesByHousingIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);
        Housing housing = HousingResourceIT.createEntity(em);
        em.persist(housing);
        em.flush();
        status.addHousing(housing);
        statusRepository.saveAndFlush(status);
        Long housingId = housing.getId();

        // Get all the statusList where housing equals to housingId
        defaultStatusShouldBeFound("housingId.equals=" + housingId);

        // Get all the statusList where housing equals to (housingId + 1)
        defaultStatusShouldNotBeFound("housingId.equals=" + (housingId + 1));
    }

    @Test
    @Transactional
    void getAllStatusesByAffiliatesIsEqualToSomething() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);
        Affiliates affiliates = AffiliatesResourceIT.createEntity(em);
        em.persist(affiliates);
        em.flush();
        status.setAffiliates(affiliates);
        statusRepository.saveAndFlush(status);
        Long affiliatesId = affiliates.getId();

        // Get all the statusList where affiliates equals to affiliatesId
        defaultStatusShouldBeFound("affiliatesId.equals=" + affiliatesId);

        // Get all the statusList where affiliates equals to (affiliatesId + 1)
        defaultStatusShouldNotBeFound("affiliatesId.equals=" + (affiliatesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStatusShouldBeFound(String filter) throws Exception {
        restStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(status.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusName").value(hasItem(DEFAULT_STATUS_NAME)))
            .andExpect(jsonPath("$.[*].screenType").value(hasItem(DEFAULT_SCREEN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].statusDescriptions").value(hasItem(DEFAULT_STATUS_DESCRIPTIONS)));

        // Check, that the count call also returns 1
        restStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStatusShouldNotBeFound(String filter) throws Exception {
        restStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStatus() throws Exception {
        // Get the status
        restStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStatus() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // Update the status
        Status updatedStatus = statusRepository.findById(status.getId()).get();
        // Disconnect from session so that the updates on updatedStatus are not directly saved in db
        em.detach(updatedStatus);
        updatedStatus.statusName(UPDATED_STATUS_NAME).screenType(UPDATED_SCREEN_TYPE).statusDescriptions(UPDATED_STATUS_DESCRIPTIONS);
        StatusDTO statusDTO = statusMapper.toDto(updatedStatus);

        restStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusDTO))
            )
            .andExpect(status().isOk());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getStatusName()).isEqualTo(UPDATED_STATUS_NAME);
        assertThat(testStatus.getScreenType()).isEqualTo(UPDATED_SCREEN_TYPE);
        assertThat(testStatus.getStatusDescriptions()).isEqualTo(UPDATED_STATUS_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void putNonExistingStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();
        status.setId(count.incrementAndGet());

        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();
        status.setId(count.incrementAndGet());

        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();
        status.setId(count.incrementAndGet());

        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStatusWithPatch() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // Update the status using partial update
        Status partialUpdatedStatus = new Status();
        partialUpdatedStatus.setId(status.getId());

        partialUpdatedStatus.statusDescriptions(UPDATED_STATUS_DESCRIPTIONS);

        restStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatus))
            )
            .andExpect(status().isOk());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getStatusName()).isEqualTo(DEFAULT_STATUS_NAME);
        assertThat(testStatus.getScreenType()).isEqualTo(DEFAULT_SCREEN_TYPE);
        assertThat(testStatus.getStatusDescriptions()).isEqualTo(UPDATED_STATUS_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void fullUpdateStatusWithPatch() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // Update the status using partial update
        Status partialUpdatedStatus = new Status();
        partialUpdatedStatus.setId(status.getId());

        partialUpdatedStatus
            .statusName(UPDATED_STATUS_NAME)
            .screenType(UPDATED_SCREEN_TYPE)
            .statusDescriptions(UPDATED_STATUS_DESCRIPTIONS);

        restStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatus))
            )
            .andExpect(status().isOk());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getStatusName()).isEqualTo(UPDATED_STATUS_NAME);
        assertThat(testStatus.getScreenType()).isEqualTo(UPDATED_SCREEN_TYPE);
        assertThat(testStatus.getStatusDescriptions()).isEqualTo(UPDATED_STATUS_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void patchNonExistingStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();
        status.setId(count.incrementAndGet());

        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, statusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();
        status.setId(count.incrementAndGet());

        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();
        status.setId(count.incrementAndGet());

        // Create the Status
        StatusDTO statusDTO = statusMapper.toDto(status);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(statusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStatus() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        int databaseSizeBeforeDelete = statusRepository.findAll().size();

        // Delete the status
        restStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, status.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
