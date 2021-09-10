package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Affiliates;
import com.genesisoft.transporte.domain.CustomerAttachments;
import com.genesisoft.transporte.domain.StatusAttachments;
import com.genesisoft.transporte.domain.enumeration.AttachmentType;
import com.genesisoft.transporte.repository.StatusAttachmentsRepository;
import com.genesisoft.transporte.service.criteria.StatusAttachmentsCriteria;
import com.genesisoft.transporte.service.dto.StatusAttachmentsDTO;
import com.genesisoft.transporte.service.mapper.StatusAttachmentsMapper;
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
 * Integration tests for the {@link StatusAttachmentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StatusAttachmentsResourceIT {

    private static final String DEFAULT_STATUS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_DESCRIPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_DESCRIPTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_OBS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_OBS = "BBBBBBBBBB";

    private static final AttachmentType DEFAULT_ATTACHMENT_TYPE = AttachmentType.SUPPLIERS;
    private static final AttachmentType UPDATED_ATTACHMENT_TYPE = AttachmentType.VEHICLE_CONTROL;

    private static final String ENTITY_API_URL = "/api/status-attachments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StatusAttachmentsRepository statusAttachmentsRepository;

    @Autowired
    private StatusAttachmentsMapper statusAttachmentsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatusAttachmentsMockMvc;

    private StatusAttachments statusAttachments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatusAttachments createEntity(EntityManager em) {
        StatusAttachments statusAttachments = new StatusAttachments()
            .statusName(DEFAULT_STATUS_NAME)
            .statusDescriptions(DEFAULT_STATUS_DESCRIPTIONS)
            .statusObs(DEFAULT_STATUS_OBS)
            .attachmentType(DEFAULT_ATTACHMENT_TYPE);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        statusAttachments.setAffiliates(affiliates);
        return statusAttachments;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatusAttachments createUpdatedEntity(EntityManager em) {
        StatusAttachments statusAttachments = new StatusAttachments()
            .statusName(UPDATED_STATUS_NAME)
            .statusDescriptions(UPDATED_STATUS_DESCRIPTIONS)
            .statusObs(UPDATED_STATUS_OBS)
            .attachmentType(UPDATED_ATTACHMENT_TYPE);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createUpdatedEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        statusAttachments.setAffiliates(affiliates);
        return statusAttachments;
    }

    @BeforeEach
    public void initTest() {
        statusAttachments = createEntity(em);
    }

    @Test
    @Transactional
    void createStatusAttachments() throws Exception {
        int databaseSizeBeforeCreate = statusAttachmentsRepository.findAll().size();
        // Create the StatusAttachments
        StatusAttachmentsDTO statusAttachmentsDTO = statusAttachmentsMapper.toDto(statusAttachments);
        restStatusAttachmentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusAttachmentsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the StatusAttachments in the database
        List<StatusAttachments> statusAttachmentsList = statusAttachmentsRepository.findAll();
        assertThat(statusAttachmentsList).hasSize(databaseSizeBeforeCreate + 1);
        StatusAttachments testStatusAttachments = statusAttachmentsList.get(statusAttachmentsList.size() - 1);
        assertThat(testStatusAttachments.getStatusName()).isEqualTo(DEFAULT_STATUS_NAME);
        assertThat(testStatusAttachments.getStatusDescriptions()).isEqualTo(DEFAULT_STATUS_DESCRIPTIONS);
        assertThat(testStatusAttachments.getStatusObs()).isEqualTo(DEFAULT_STATUS_OBS);
        assertThat(testStatusAttachments.getAttachmentType()).isEqualTo(DEFAULT_ATTACHMENT_TYPE);
    }

    @Test
    @Transactional
    void createStatusAttachmentsWithExistingId() throws Exception {
        // Create the StatusAttachments with an existing ID
        statusAttachments.setId(1L);
        StatusAttachmentsDTO statusAttachmentsDTO = statusAttachmentsMapper.toDto(statusAttachments);

        int databaseSizeBeforeCreate = statusAttachmentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusAttachmentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusAttachments in the database
        List<StatusAttachments> statusAttachmentsList = statusAttachmentsRepository.findAll();
        assertThat(statusAttachmentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStatusNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusAttachmentsRepository.findAll().size();
        // set the field null
        statusAttachments.setStatusName(null);

        // Create the StatusAttachments, which fails.
        StatusAttachmentsDTO statusAttachmentsDTO = statusAttachmentsMapper.toDto(statusAttachments);

        restStatusAttachmentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        List<StatusAttachments> statusAttachmentsList = statusAttachmentsRepository.findAll();
        assertThat(statusAttachmentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStatusAttachments() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList
        restStatusAttachmentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusAttachments.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusName").value(hasItem(DEFAULT_STATUS_NAME)))
            .andExpect(jsonPath("$.[*].statusDescriptions").value(hasItem(DEFAULT_STATUS_DESCRIPTIONS)))
            .andExpect(jsonPath("$.[*].statusObs").value(hasItem(DEFAULT_STATUS_OBS)))
            .andExpect(jsonPath("$.[*].attachmentType").value(hasItem(DEFAULT_ATTACHMENT_TYPE.toString())));
    }

    @Test
    @Transactional
    void getStatusAttachments() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get the statusAttachments
        restStatusAttachmentsMockMvc
            .perform(get(ENTITY_API_URL_ID, statusAttachments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statusAttachments.getId().intValue()))
            .andExpect(jsonPath("$.statusName").value(DEFAULT_STATUS_NAME))
            .andExpect(jsonPath("$.statusDescriptions").value(DEFAULT_STATUS_DESCRIPTIONS))
            .andExpect(jsonPath("$.statusObs").value(DEFAULT_STATUS_OBS))
            .andExpect(jsonPath("$.attachmentType").value(DEFAULT_ATTACHMENT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getStatusAttachmentsByIdFiltering() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        Long id = statusAttachments.getId();

        defaultStatusAttachmentsShouldBeFound("id.equals=" + id);
        defaultStatusAttachmentsShouldNotBeFound("id.notEquals=" + id);

        defaultStatusAttachmentsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStatusAttachmentsShouldNotBeFound("id.greaterThan=" + id);

        defaultStatusAttachmentsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStatusAttachmentsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByStatusNameIsEqualToSomething() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where statusName equals to DEFAULT_STATUS_NAME
        defaultStatusAttachmentsShouldBeFound("statusName.equals=" + DEFAULT_STATUS_NAME);

        // Get all the statusAttachmentsList where statusName equals to UPDATED_STATUS_NAME
        defaultStatusAttachmentsShouldNotBeFound("statusName.equals=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByStatusNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where statusName not equals to DEFAULT_STATUS_NAME
        defaultStatusAttachmentsShouldNotBeFound("statusName.notEquals=" + DEFAULT_STATUS_NAME);

        // Get all the statusAttachmentsList where statusName not equals to UPDATED_STATUS_NAME
        defaultStatusAttachmentsShouldBeFound("statusName.notEquals=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByStatusNameIsInShouldWork() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where statusName in DEFAULT_STATUS_NAME or UPDATED_STATUS_NAME
        defaultStatusAttachmentsShouldBeFound("statusName.in=" + DEFAULT_STATUS_NAME + "," + UPDATED_STATUS_NAME);

        // Get all the statusAttachmentsList where statusName equals to UPDATED_STATUS_NAME
        defaultStatusAttachmentsShouldNotBeFound("statusName.in=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByStatusNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where statusName is not null
        defaultStatusAttachmentsShouldBeFound("statusName.specified=true");

        // Get all the statusAttachmentsList where statusName is null
        defaultStatusAttachmentsShouldNotBeFound("statusName.specified=false");
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByStatusNameContainsSomething() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where statusName contains DEFAULT_STATUS_NAME
        defaultStatusAttachmentsShouldBeFound("statusName.contains=" + DEFAULT_STATUS_NAME);

        // Get all the statusAttachmentsList where statusName contains UPDATED_STATUS_NAME
        defaultStatusAttachmentsShouldNotBeFound("statusName.contains=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByStatusNameNotContainsSomething() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where statusName does not contain DEFAULT_STATUS_NAME
        defaultStatusAttachmentsShouldNotBeFound("statusName.doesNotContain=" + DEFAULT_STATUS_NAME);

        // Get all the statusAttachmentsList where statusName does not contain UPDATED_STATUS_NAME
        defaultStatusAttachmentsShouldBeFound("statusName.doesNotContain=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByStatusDescriptionsIsEqualToSomething() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where statusDescriptions equals to DEFAULT_STATUS_DESCRIPTIONS
        defaultStatusAttachmentsShouldBeFound("statusDescriptions.equals=" + DEFAULT_STATUS_DESCRIPTIONS);

        // Get all the statusAttachmentsList where statusDescriptions equals to UPDATED_STATUS_DESCRIPTIONS
        defaultStatusAttachmentsShouldNotBeFound("statusDescriptions.equals=" + UPDATED_STATUS_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByStatusDescriptionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where statusDescriptions not equals to DEFAULT_STATUS_DESCRIPTIONS
        defaultStatusAttachmentsShouldNotBeFound("statusDescriptions.notEquals=" + DEFAULT_STATUS_DESCRIPTIONS);

        // Get all the statusAttachmentsList where statusDescriptions not equals to UPDATED_STATUS_DESCRIPTIONS
        defaultStatusAttachmentsShouldBeFound("statusDescriptions.notEquals=" + UPDATED_STATUS_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByStatusDescriptionsIsInShouldWork() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where statusDescriptions in DEFAULT_STATUS_DESCRIPTIONS or UPDATED_STATUS_DESCRIPTIONS
        defaultStatusAttachmentsShouldBeFound("statusDescriptions.in=" + DEFAULT_STATUS_DESCRIPTIONS + "," + UPDATED_STATUS_DESCRIPTIONS);

        // Get all the statusAttachmentsList where statusDescriptions equals to UPDATED_STATUS_DESCRIPTIONS
        defaultStatusAttachmentsShouldNotBeFound("statusDescriptions.in=" + UPDATED_STATUS_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByStatusDescriptionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where statusDescriptions is not null
        defaultStatusAttachmentsShouldBeFound("statusDescriptions.specified=true");

        // Get all the statusAttachmentsList where statusDescriptions is null
        defaultStatusAttachmentsShouldNotBeFound("statusDescriptions.specified=false");
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByStatusDescriptionsContainsSomething() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where statusDescriptions contains DEFAULT_STATUS_DESCRIPTIONS
        defaultStatusAttachmentsShouldBeFound("statusDescriptions.contains=" + DEFAULT_STATUS_DESCRIPTIONS);

        // Get all the statusAttachmentsList where statusDescriptions contains UPDATED_STATUS_DESCRIPTIONS
        defaultStatusAttachmentsShouldNotBeFound("statusDescriptions.contains=" + UPDATED_STATUS_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByStatusDescriptionsNotContainsSomething() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where statusDescriptions does not contain DEFAULT_STATUS_DESCRIPTIONS
        defaultStatusAttachmentsShouldNotBeFound("statusDescriptions.doesNotContain=" + DEFAULT_STATUS_DESCRIPTIONS);

        // Get all the statusAttachmentsList where statusDescriptions does not contain UPDATED_STATUS_DESCRIPTIONS
        defaultStatusAttachmentsShouldBeFound("statusDescriptions.doesNotContain=" + UPDATED_STATUS_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByStatusObsIsEqualToSomething() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where statusObs equals to DEFAULT_STATUS_OBS
        defaultStatusAttachmentsShouldBeFound("statusObs.equals=" + DEFAULT_STATUS_OBS);

        // Get all the statusAttachmentsList where statusObs equals to UPDATED_STATUS_OBS
        defaultStatusAttachmentsShouldNotBeFound("statusObs.equals=" + UPDATED_STATUS_OBS);
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByStatusObsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where statusObs not equals to DEFAULT_STATUS_OBS
        defaultStatusAttachmentsShouldNotBeFound("statusObs.notEquals=" + DEFAULT_STATUS_OBS);

        // Get all the statusAttachmentsList where statusObs not equals to UPDATED_STATUS_OBS
        defaultStatusAttachmentsShouldBeFound("statusObs.notEquals=" + UPDATED_STATUS_OBS);
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByStatusObsIsInShouldWork() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where statusObs in DEFAULT_STATUS_OBS or UPDATED_STATUS_OBS
        defaultStatusAttachmentsShouldBeFound("statusObs.in=" + DEFAULT_STATUS_OBS + "," + UPDATED_STATUS_OBS);

        // Get all the statusAttachmentsList where statusObs equals to UPDATED_STATUS_OBS
        defaultStatusAttachmentsShouldNotBeFound("statusObs.in=" + UPDATED_STATUS_OBS);
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByStatusObsIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where statusObs is not null
        defaultStatusAttachmentsShouldBeFound("statusObs.specified=true");

        // Get all the statusAttachmentsList where statusObs is null
        defaultStatusAttachmentsShouldNotBeFound("statusObs.specified=false");
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByStatusObsContainsSomething() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where statusObs contains DEFAULT_STATUS_OBS
        defaultStatusAttachmentsShouldBeFound("statusObs.contains=" + DEFAULT_STATUS_OBS);

        // Get all the statusAttachmentsList where statusObs contains UPDATED_STATUS_OBS
        defaultStatusAttachmentsShouldNotBeFound("statusObs.contains=" + UPDATED_STATUS_OBS);
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByStatusObsNotContainsSomething() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where statusObs does not contain DEFAULT_STATUS_OBS
        defaultStatusAttachmentsShouldNotBeFound("statusObs.doesNotContain=" + DEFAULT_STATUS_OBS);

        // Get all the statusAttachmentsList where statusObs does not contain UPDATED_STATUS_OBS
        defaultStatusAttachmentsShouldBeFound("statusObs.doesNotContain=" + UPDATED_STATUS_OBS);
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByAttachmentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where attachmentType equals to DEFAULT_ATTACHMENT_TYPE
        defaultStatusAttachmentsShouldBeFound("attachmentType.equals=" + DEFAULT_ATTACHMENT_TYPE);

        // Get all the statusAttachmentsList where attachmentType equals to UPDATED_ATTACHMENT_TYPE
        defaultStatusAttachmentsShouldNotBeFound("attachmentType.equals=" + UPDATED_ATTACHMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByAttachmentTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where attachmentType not equals to DEFAULT_ATTACHMENT_TYPE
        defaultStatusAttachmentsShouldNotBeFound("attachmentType.notEquals=" + DEFAULT_ATTACHMENT_TYPE);

        // Get all the statusAttachmentsList where attachmentType not equals to UPDATED_ATTACHMENT_TYPE
        defaultStatusAttachmentsShouldBeFound("attachmentType.notEquals=" + UPDATED_ATTACHMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByAttachmentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where attachmentType in DEFAULT_ATTACHMENT_TYPE or UPDATED_ATTACHMENT_TYPE
        defaultStatusAttachmentsShouldBeFound("attachmentType.in=" + DEFAULT_ATTACHMENT_TYPE + "," + UPDATED_ATTACHMENT_TYPE);

        // Get all the statusAttachmentsList where attachmentType equals to UPDATED_ATTACHMENT_TYPE
        defaultStatusAttachmentsShouldNotBeFound("attachmentType.in=" + UPDATED_ATTACHMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByAttachmentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        // Get all the statusAttachmentsList where attachmentType is not null
        defaultStatusAttachmentsShouldBeFound("attachmentType.specified=true");

        // Get all the statusAttachmentsList where attachmentType is null
        defaultStatusAttachmentsShouldNotBeFound("attachmentType.specified=false");
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByCustomerAttachmentsIsEqualToSomething() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);
        CustomerAttachments customerAttachments = CustomerAttachmentsResourceIT.createEntity(em);
        em.persist(customerAttachments);
        em.flush();
        statusAttachments.addCustomerAttachments(customerAttachments);
        statusAttachmentsRepository.saveAndFlush(statusAttachments);
        Long customerAttachmentsId = customerAttachments.getId();

        // Get all the statusAttachmentsList where customerAttachments equals to customerAttachmentsId
        defaultStatusAttachmentsShouldBeFound("customerAttachmentsId.equals=" + customerAttachmentsId);

        // Get all the statusAttachmentsList where customerAttachments equals to (customerAttachmentsId + 1)
        defaultStatusAttachmentsShouldNotBeFound("customerAttachmentsId.equals=" + (customerAttachmentsId + 1));
    }

    @Test
    @Transactional
    void getAllStatusAttachmentsByAffiliatesIsEqualToSomething() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);
        Affiliates affiliates = AffiliatesResourceIT.createEntity(em);
        em.persist(affiliates);
        em.flush();
        statusAttachments.setAffiliates(affiliates);
        statusAttachmentsRepository.saveAndFlush(statusAttachments);
        Long affiliatesId = affiliates.getId();

        // Get all the statusAttachmentsList where affiliates equals to affiliatesId
        defaultStatusAttachmentsShouldBeFound("affiliatesId.equals=" + affiliatesId);

        // Get all the statusAttachmentsList where affiliates equals to (affiliatesId + 1)
        defaultStatusAttachmentsShouldNotBeFound("affiliatesId.equals=" + (affiliatesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStatusAttachmentsShouldBeFound(String filter) throws Exception {
        restStatusAttachmentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusAttachments.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusName").value(hasItem(DEFAULT_STATUS_NAME)))
            .andExpect(jsonPath("$.[*].statusDescriptions").value(hasItem(DEFAULT_STATUS_DESCRIPTIONS)))
            .andExpect(jsonPath("$.[*].statusObs").value(hasItem(DEFAULT_STATUS_OBS)))
            .andExpect(jsonPath("$.[*].attachmentType").value(hasItem(DEFAULT_ATTACHMENT_TYPE.toString())));

        // Check, that the count call also returns 1
        restStatusAttachmentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStatusAttachmentsShouldNotBeFound(String filter) throws Exception {
        restStatusAttachmentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStatusAttachmentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStatusAttachments() throws Exception {
        // Get the statusAttachments
        restStatusAttachmentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStatusAttachments() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        int databaseSizeBeforeUpdate = statusAttachmentsRepository.findAll().size();

        // Update the statusAttachments
        StatusAttachments updatedStatusAttachments = statusAttachmentsRepository.findById(statusAttachments.getId()).get();
        // Disconnect from session so that the updates on updatedStatusAttachments are not directly saved in db
        em.detach(updatedStatusAttachments);
        updatedStatusAttachments
            .statusName(UPDATED_STATUS_NAME)
            .statusDescriptions(UPDATED_STATUS_DESCRIPTIONS)
            .statusObs(UPDATED_STATUS_OBS)
            .attachmentType(UPDATED_ATTACHMENT_TYPE);
        StatusAttachmentsDTO statusAttachmentsDTO = statusAttachmentsMapper.toDto(updatedStatusAttachments);

        restStatusAttachmentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statusAttachmentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusAttachmentsDTO))
            )
            .andExpect(status().isOk());

        // Validate the StatusAttachments in the database
        List<StatusAttachments> statusAttachmentsList = statusAttachmentsRepository.findAll();
        assertThat(statusAttachmentsList).hasSize(databaseSizeBeforeUpdate);
        StatusAttachments testStatusAttachments = statusAttachmentsList.get(statusAttachmentsList.size() - 1);
        assertThat(testStatusAttachments.getStatusName()).isEqualTo(UPDATED_STATUS_NAME);
        assertThat(testStatusAttachments.getStatusDescriptions()).isEqualTo(UPDATED_STATUS_DESCRIPTIONS);
        assertThat(testStatusAttachments.getStatusObs()).isEqualTo(UPDATED_STATUS_OBS);
        assertThat(testStatusAttachments.getAttachmentType()).isEqualTo(UPDATED_ATTACHMENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingStatusAttachments() throws Exception {
        int databaseSizeBeforeUpdate = statusAttachmentsRepository.findAll().size();
        statusAttachments.setId(count.incrementAndGet());

        // Create the StatusAttachments
        StatusAttachmentsDTO statusAttachmentsDTO = statusAttachmentsMapper.toDto(statusAttachments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusAttachmentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statusAttachmentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusAttachments in the database
        List<StatusAttachments> statusAttachmentsList = statusAttachmentsRepository.findAll();
        assertThat(statusAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStatusAttachments() throws Exception {
        int databaseSizeBeforeUpdate = statusAttachmentsRepository.findAll().size();
        statusAttachments.setId(count.incrementAndGet());

        // Create the StatusAttachments
        StatusAttachmentsDTO statusAttachmentsDTO = statusAttachmentsMapper.toDto(statusAttachments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusAttachmentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusAttachments in the database
        List<StatusAttachments> statusAttachmentsList = statusAttachmentsRepository.findAll();
        assertThat(statusAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStatusAttachments() throws Exception {
        int databaseSizeBeforeUpdate = statusAttachmentsRepository.findAll().size();
        statusAttachments.setId(count.incrementAndGet());

        // Create the StatusAttachments
        StatusAttachmentsDTO statusAttachmentsDTO = statusAttachmentsMapper.toDto(statusAttachments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusAttachmentsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusAttachmentsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StatusAttachments in the database
        List<StatusAttachments> statusAttachmentsList = statusAttachmentsRepository.findAll();
        assertThat(statusAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStatusAttachmentsWithPatch() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        int databaseSizeBeforeUpdate = statusAttachmentsRepository.findAll().size();

        // Update the statusAttachments using partial update
        StatusAttachments partialUpdatedStatusAttachments = new StatusAttachments();
        partialUpdatedStatusAttachments.setId(statusAttachments.getId());

        restStatusAttachmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatusAttachments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatusAttachments))
            )
            .andExpect(status().isOk());

        // Validate the StatusAttachments in the database
        List<StatusAttachments> statusAttachmentsList = statusAttachmentsRepository.findAll();
        assertThat(statusAttachmentsList).hasSize(databaseSizeBeforeUpdate);
        StatusAttachments testStatusAttachments = statusAttachmentsList.get(statusAttachmentsList.size() - 1);
        assertThat(testStatusAttachments.getStatusName()).isEqualTo(DEFAULT_STATUS_NAME);
        assertThat(testStatusAttachments.getStatusDescriptions()).isEqualTo(DEFAULT_STATUS_DESCRIPTIONS);
        assertThat(testStatusAttachments.getStatusObs()).isEqualTo(DEFAULT_STATUS_OBS);
        assertThat(testStatusAttachments.getAttachmentType()).isEqualTo(DEFAULT_ATTACHMENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateStatusAttachmentsWithPatch() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        int databaseSizeBeforeUpdate = statusAttachmentsRepository.findAll().size();

        // Update the statusAttachments using partial update
        StatusAttachments partialUpdatedStatusAttachments = new StatusAttachments();
        partialUpdatedStatusAttachments.setId(statusAttachments.getId());

        partialUpdatedStatusAttachments
            .statusName(UPDATED_STATUS_NAME)
            .statusDescriptions(UPDATED_STATUS_DESCRIPTIONS)
            .statusObs(UPDATED_STATUS_OBS)
            .attachmentType(UPDATED_ATTACHMENT_TYPE);

        restStatusAttachmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatusAttachments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatusAttachments))
            )
            .andExpect(status().isOk());

        // Validate the StatusAttachments in the database
        List<StatusAttachments> statusAttachmentsList = statusAttachmentsRepository.findAll();
        assertThat(statusAttachmentsList).hasSize(databaseSizeBeforeUpdate);
        StatusAttachments testStatusAttachments = statusAttachmentsList.get(statusAttachmentsList.size() - 1);
        assertThat(testStatusAttachments.getStatusName()).isEqualTo(UPDATED_STATUS_NAME);
        assertThat(testStatusAttachments.getStatusDescriptions()).isEqualTo(UPDATED_STATUS_DESCRIPTIONS);
        assertThat(testStatusAttachments.getStatusObs()).isEqualTo(UPDATED_STATUS_OBS);
        assertThat(testStatusAttachments.getAttachmentType()).isEqualTo(UPDATED_ATTACHMENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingStatusAttachments() throws Exception {
        int databaseSizeBeforeUpdate = statusAttachmentsRepository.findAll().size();
        statusAttachments.setId(count.incrementAndGet());

        // Create the StatusAttachments
        StatusAttachmentsDTO statusAttachmentsDTO = statusAttachmentsMapper.toDto(statusAttachments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusAttachmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, statusAttachmentsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statusAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusAttachments in the database
        List<StatusAttachments> statusAttachmentsList = statusAttachmentsRepository.findAll();
        assertThat(statusAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStatusAttachments() throws Exception {
        int databaseSizeBeforeUpdate = statusAttachmentsRepository.findAll().size();
        statusAttachments.setId(count.incrementAndGet());

        // Create the StatusAttachments
        StatusAttachmentsDTO statusAttachmentsDTO = statusAttachmentsMapper.toDto(statusAttachments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusAttachmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statusAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusAttachments in the database
        List<StatusAttachments> statusAttachmentsList = statusAttachmentsRepository.findAll();
        assertThat(statusAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStatusAttachments() throws Exception {
        int databaseSizeBeforeUpdate = statusAttachmentsRepository.findAll().size();
        statusAttachments.setId(count.incrementAndGet());

        // Create the StatusAttachments
        StatusAttachmentsDTO statusAttachmentsDTO = statusAttachmentsMapper.toDto(statusAttachments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusAttachmentsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statusAttachmentsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StatusAttachments in the database
        List<StatusAttachments> statusAttachmentsList = statusAttachmentsRepository.findAll();
        assertThat(statusAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStatusAttachments() throws Exception {
        // Initialize the database
        statusAttachmentsRepository.saveAndFlush(statusAttachments);

        int databaseSizeBeforeDelete = statusAttachmentsRepository.findAll().size();

        // Delete the statusAttachments
        restStatusAttachmentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, statusAttachments.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StatusAttachments> statusAttachmentsList = statusAttachmentsRepository.findAll();
        assertThat(statusAttachmentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
