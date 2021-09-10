package com.genesisoft.transporte.web.rest;

import static com.genesisoft.transporte.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.VehicleControlAttachments;
import com.genesisoft.transporte.domain.VehicleControls;
import com.genesisoft.transporte.repository.VehicleControlAttachmentsRepository;
import com.genesisoft.transporte.service.criteria.VehicleControlAttachmentsCriteria;
import com.genesisoft.transporte.service.dto.VehicleControlAttachmentsDTO;
import com.genesisoft.transporte.service.mapper.VehicleControlAttachmentsMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link VehicleControlAttachmentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VehicleControlAttachmentsResourceIT {

    private static final byte[] DEFAULT_ATTACH_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACH_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACH_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACH_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ATTACH_URL = "AAAAAAAAAA";
    private static final String UPDATED_ATTACH_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ATTACH_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ATTACH_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ATTACHED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ATTACHED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ATTACHED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/vehicle-control-attachments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VehicleControlAttachmentsRepository vehicleControlAttachmentsRepository;

    @Autowired
    private VehicleControlAttachmentsMapper vehicleControlAttachmentsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehicleControlAttachmentsMockMvc;

    private VehicleControlAttachments vehicleControlAttachments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleControlAttachments createEntity(EntityManager em) {
        VehicleControlAttachments vehicleControlAttachments = new VehicleControlAttachments()
            .attachImage(DEFAULT_ATTACH_IMAGE)
            .attachImageContentType(DEFAULT_ATTACH_IMAGE_CONTENT_TYPE)
            .attachUrl(DEFAULT_ATTACH_URL)
            .attachDescription(DEFAULT_ATTACH_DESCRIPTION)
            .attachedDate(DEFAULT_ATTACHED_DATE);
        // Add required entity
        VehicleControls vehicleControls;
        if (TestUtil.findAll(em, VehicleControls.class).isEmpty()) {
            vehicleControls = VehicleControlsResourceIT.createEntity(em);
            em.persist(vehicleControls);
            em.flush();
        } else {
            vehicleControls = TestUtil.findAll(em, VehicleControls.class).get(0);
        }
        vehicleControlAttachments.setVehicleControls(vehicleControls);
        return vehicleControlAttachments;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleControlAttachments createUpdatedEntity(EntityManager em) {
        VehicleControlAttachments vehicleControlAttachments = new VehicleControlAttachments()
            .attachImage(UPDATED_ATTACH_IMAGE)
            .attachImageContentType(UPDATED_ATTACH_IMAGE_CONTENT_TYPE)
            .attachUrl(UPDATED_ATTACH_URL)
            .attachDescription(UPDATED_ATTACH_DESCRIPTION)
            .attachedDate(UPDATED_ATTACHED_DATE);
        // Add required entity
        VehicleControls vehicleControls;
        if (TestUtil.findAll(em, VehicleControls.class).isEmpty()) {
            vehicleControls = VehicleControlsResourceIT.createUpdatedEntity(em);
            em.persist(vehicleControls);
            em.flush();
        } else {
            vehicleControls = TestUtil.findAll(em, VehicleControls.class).get(0);
        }
        vehicleControlAttachments.setVehicleControls(vehicleControls);
        return vehicleControlAttachments;
    }

    @BeforeEach
    public void initTest() {
        vehicleControlAttachments = createEntity(em);
    }

    @Test
    @Transactional
    void createVehicleControlAttachments() throws Exception {
        int databaseSizeBeforeCreate = vehicleControlAttachmentsRepository.findAll().size();
        // Create the VehicleControlAttachments
        VehicleControlAttachmentsDTO vehicleControlAttachmentsDTO = vehicleControlAttachmentsMapper.toDto(vehicleControlAttachments);
        restVehicleControlAttachmentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlAttachmentsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VehicleControlAttachments in the database
        List<VehicleControlAttachments> vehicleControlAttachmentsList = vehicleControlAttachmentsRepository.findAll();
        assertThat(vehicleControlAttachmentsList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleControlAttachments testVehicleControlAttachments = vehicleControlAttachmentsList.get(
            vehicleControlAttachmentsList.size() - 1
        );
        assertThat(testVehicleControlAttachments.getAttachImage()).isEqualTo(DEFAULT_ATTACH_IMAGE);
        assertThat(testVehicleControlAttachments.getAttachImageContentType()).isEqualTo(DEFAULT_ATTACH_IMAGE_CONTENT_TYPE);
        assertThat(testVehicleControlAttachments.getAttachUrl()).isEqualTo(DEFAULT_ATTACH_URL);
        assertThat(testVehicleControlAttachments.getAttachDescription()).isEqualTo(DEFAULT_ATTACH_DESCRIPTION);
        assertThat(testVehicleControlAttachments.getAttachedDate()).isEqualTo(DEFAULT_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void createVehicleControlAttachmentsWithExistingId() throws Exception {
        // Create the VehicleControlAttachments with an existing ID
        vehicleControlAttachments.setId(1L);
        VehicleControlAttachmentsDTO vehicleControlAttachmentsDTO = vehicleControlAttachmentsMapper.toDto(vehicleControlAttachments);

        int databaseSizeBeforeCreate = vehicleControlAttachmentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleControlAttachmentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlAttachments in the database
        List<VehicleControlAttachments> vehicleControlAttachmentsList = vehicleControlAttachmentsRepository.findAll();
        assertThat(vehicleControlAttachmentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAttachUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleControlAttachmentsRepository.findAll().size();
        // set the field null
        vehicleControlAttachments.setAttachUrl(null);

        // Create the VehicleControlAttachments, which fails.
        VehicleControlAttachmentsDTO vehicleControlAttachmentsDTO = vehicleControlAttachmentsMapper.toDto(vehicleControlAttachments);

        restVehicleControlAttachmentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleControlAttachments> vehicleControlAttachmentsList = vehicleControlAttachmentsRepository.findAll();
        assertThat(vehicleControlAttachmentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAttachDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleControlAttachmentsRepository.findAll().size();
        // set the field null
        vehicleControlAttachments.setAttachDescription(null);

        // Create the VehicleControlAttachments, which fails.
        VehicleControlAttachmentsDTO vehicleControlAttachmentsDTO = vehicleControlAttachmentsMapper.toDto(vehicleControlAttachments);

        restVehicleControlAttachmentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleControlAttachments> vehicleControlAttachmentsList = vehicleControlAttachmentsRepository.findAll();
        assertThat(vehicleControlAttachmentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAttachedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleControlAttachmentsRepository.findAll().size();
        // set the field null
        vehicleControlAttachments.setAttachedDate(null);

        // Create the VehicleControlAttachments, which fails.
        VehicleControlAttachmentsDTO vehicleControlAttachmentsDTO = vehicleControlAttachmentsMapper.toDto(vehicleControlAttachments);

        restVehicleControlAttachmentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleControlAttachments> vehicleControlAttachmentsList = vehicleControlAttachmentsRepository.findAll();
        assertThat(vehicleControlAttachmentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachments() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList
        restVehicleControlAttachmentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleControlAttachments.getId().intValue())))
            .andExpect(jsonPath("$.[*].attachImageContentType").value(hasItem(DEFAULT_ATTACH_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACH_IMAGE))))
            .andExpect(jsonPath("$.[*].attachUrl").value(hasItem(DEFAULT_ATTACH_URL)))
            .andExpect(jsonPath("$.[*].attachDescription").value(hasItem(DEFAULT_ATTACH_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].attachedDate").value(hasItem(sameInstant(DEFAULT_ATTACHED_DATE))));
    }

    @Test
    @Transactional
    void getVehicleControlAttachments() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get the vehicleControlAttachments
        restVehicleControlAttachmentsMockMvc
            .perform(get(ENTITY_API_URL_ID, vehicleControlAttachments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleControlAttachments.getId().intValue()))
            .andExpect(jsonPath("$.attachImageContentType").value(DEFAULT_ATTACH_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachImage").value(Base64Utils.encodeToString(DEFAULT_ATTACH_IMAGE)))
            .andExpect(jsonPath("$.attachUrl").value(DEFAULT_ATTACH_URL))
            .andExpect(jsonPath("$.attachDescription").value(DEFAULT_ATTACH_DESCRIPTION))
            .andExpect(jsonPath("$.attachedDate").value(sameInstant(DEFAULT_ATTACHED_DATE)));
    }

    @Test
    @Transactional
    void getVehicleControlAttachmentsByIdFiltering() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        Long id = vehicleControlAttachments.getId();

        defaultVehicleControlAttachmentsShouldBeFound("id.equals=" + id);
        defaultVehicleControlAttachmentsShouldNotBeFound("id.notEquals=" + id);

        defaultVehicleControlAttachmentsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVehicleControlAttachmentsShouldNotBeFound("id.greaterThan=" + id);

        defaultVehicleControlAttachmentsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVehicleControlAttachmentsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByAttachUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList where attachUrl equals to DEFAULT_ATTACH_URL
        defaultVehicleControlAttachmentsShouldBeFound("attachUrl.equals=" + DEFAULT_ATTACH_URL);

        // Get all the vehicleControlAttachmentsList where attachUrl equals to UPDATED_ATTACH_URL
        defaultVehicleControlAttachmentsShouldNotBeFound("attachUrl.equals=" + UPDATED_ATTACH_URL);
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByAttachUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList where attachUrl not equals to DEFAULT_ATTACH_URL
        defaultVehicleControlAttachmentsShouldNotBeFound("attachUrl.notEquals=" + DEFAULT_ATTACH_URL);

        // Get all the vehicleControlAttachmentsList where attachUrl not equals to UPDATED_ATTACH_URL
        defaultVehicleControlAttachmentsShouldBeFound("attachUrl.notEquals=" + UPDATED_ATTACH_URL);
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByAttachUrlIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList where attachUrl in DEFAULT_ATTACH_URL or UPDATED_ATTACH_URL
        defaultVehicleControlAttachmentsShouldBeFound("attachUrl.in=" + DEFAULT_ATTACH_URL + "," + UPDATED_ATTACH_URL);

        // Get all the vehicleControlAttachmentsList where attachUrl equals to UPDATED_ATTACH_URL
        defaultVehicleControlAttachmentsShouldNotBeFound("attachUrl.in=" + UPDATED_ATTACH_URL);
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByAttachUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList where attachUrl is not null
        defaultVehicleControlAttachmentsShouldBeFound("attachUrl.specified=true");

        // Get all the vehicleControlAttachmentsList where attachUrl is null
        defaultVehicleControlAttachmentsShouldNotBeFound("attachUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByAttachUrlContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList where attachUrl contains DEFAULT_ATTACH_URL
        defaultVehicleControlAttachmentsShouldBeFound("attachUrl.contains=" + DEFAULT_ATTACH_URL);

        // Get all the vehicleControlAttachmentsList where attachUrl contains UPDATED_ATTACH_URL
        defaultVehicleControlAttachmentsShouldNotBeFound("attachUrl.contains=" + UPDATED_ATTACH_URL);
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByAttachUrlNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList where attachUrl does not contain DEFAULT_ATTACH_URL
        defaultVehicleControlAttachmentsShouldNotBeFound("attachUrl.doesNotContain=" + DEFAULT_ATTACH_URL);

        // Get all the vehicleControlAttachmentsList where attachUrl does not contain UPDATED_ATTACH_URL
        defaultVehicleControlAttachmentsShouldBeFound("attachUrl.doesNotContain=" + UPDATED_ATTACH_URL);
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByAttachDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList where attachDescription equals to DEFAULT_ATTACH_DESCRIPTION
        defaultVehicleControlAttachmentsShouldBeFound("attachDescription.equals=" + DEFAULT_ATTACH_DESCRIPTION);

        // Get all the vehicleControlAttachmentsList where attachDescription equals to UPDATED_ATTACH_DESCRIPTION
        defaultVehicleControlAttachmentsShouldNotBeFound("attachDescription.equals=" + UPDATED_ATTACH_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByAttachDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList where attachDescription not equals to DEFAULT_ATTACH_DESCRIPTION
        defaultVehicleControlAttachmentsShouldNotBeFound("attachDescription.notEquals=" + DEFAULT_ATTACH_DESCRIPTION);

        // Get all the vehicleControlAttachmentsList where attachDescription not equals to UPDATED_ATTACH_DESCRIPTION
        defaultVehicleControlAttachmentsShouldBeFound("attachDescription.notEquals=" + UPDATED_ATTACH_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByAttachDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList where attachDescription in DEFAULT_ATTACH_DESCRIPTION or UPDATED_ATTACH_DESCRIPTION
        defaultVehicleControlAttachmentsShouldBeFound(
            "attachDescription.in=" + DEFAULT_ATTACH_DESCRIPTION + "," + UPDATED_ATTACH_DESCRIPTION
        );

        // Get all the vehicleControlAttachmentsList where attachDescription equals to UPDATED_ATTACH_DESCRIPTION
        defaultVehicleControlAttachmentsShouldNotBeFound("attachDescription.in=" + UPDATED_ATTACH_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByAttachDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList where attachDescription is not null
        defaultVehicleControlAttachmentsShouldBeFound("attachDescription.specified=true");

        // Get all the vehicleControlAttachmentsList where attachDescription is null
        defaultVehicleControlAttachmentsShouldNotBeFound("attachDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByAttachDescriptionContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList where attachDescription contains DEFAULT_ATTACH_DESCRIPTION
        defaultVehicleControlAttachmentsShouldBeFound("attachDescription.contains=" + DEFAULT_ATTACH_DESCRIPTION);

        // Get all the vehicleControlAttachmentsList where attachDescription contains UPDATED_ATTACH_DESCRIPTION
        defaultVehicleControlAttachmentsShouldNotBeFound("attachDescription.contains=" + UPDATED_ATTACH_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByAttachDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList where attachDescription does not contain DEFAULT_ATTACH_DESCRIPTION
        defaultVehicleControlAttachmentsShouldNotBeFound("attachDescription.doesNotContain=" + DEFAULT_ATTACH_DESCRIPTION);

        // Get all the vehicleControlAttachmentsList where attachDescription does not contain UPDATED_ATTACH_DESCRIPTION
        defaultVehicleControlAttachmentsShouldBeFound("attachDescription.doesNotContain=" + UPDATED_ATTACH_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByAttachedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList where attachedDate equals to DEFAULT_ATTACHED_DATE
        defaultVehicleControlAttachmentsShouldBeFound("attachedDate.equals=" + DEFAULT_ATTACHED_DATE);

        // Get all the vehicleControlAttachmentsList where attachedDate equals to UPDATED_ATTACHED_DATE
        defaultVehicleControlAttachmentsShouldNotBeFound("attachedDate.equals=" + UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByAttachedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList where attachedDate not equals to DEFAULT_ATTACHED_DATE
        defaultVehicleControlAttachmentsShouldNotBeFound("attachedDate.notEquals=" + DEFAULT_ATTACHED_DATE);

        // Get all the vehicleControlAttachmentsList where attachedDate not equals to UPDATED_ATTACHED_DATE
        defaultVehicleControlAttachmentsShouldBeFound("attachedDate.notEquals=" + UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByAttachedDateIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList where attachedDate in DEFAULT_ATTACHED_DATE or UPDATED_ATTACHED_DATE
        defaultVehicleControlAttachmentsShouldBeFound("attachedDate.in=" + DEFAULT_ATTACHED_DATE + "," + UPDATED_ATTACHED_DATE);

        // Get all the vehicleControlAttachmentsList where attachedDate equals to UPDATED_ATTACHED_DATE
        defaultVehicleControlAttachmentsShouldNotBeFound("attachedDate.in=" + UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByAttachedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList where attachedDate is not null
        defaultVehicleControlAttachmentsShouldBeFound("attachedDate.specified=true");

        // Get all the vehicleControlAttachmentsList where attachedDate is null
        defaultVehicleControlAttachmentsShouldNotBeFound("attachedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByAttachedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList where attachedDate is greater than or equal to DEFAULT_ATTACHED_DATE
        defaultVehicleControlAttachmentsShouldBeFound("attachedDate.greaterThanOrEqual=" + DEFAULT_ATTACHED_DATE);

        // Get all the vehicleControlAttachmentsList where attachedDate is greater than or equal to UPDATED_ATTACHED_DATE
        defaultVehicleControlAttachmentsShouldNotBeFound("attachedDate.greaterThanOrEqual=" + UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByAttachedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList where attachedDate is less than or equal to DEFAULT_ATTACHED_DATE
        defaultVehicleControlAttachmentsShouldBeFound("attachedDate.lessThanOrEqual=" + DEFAULT_ATTACHED_DATE);

        // Get all the vehicleControlAttachmentsList where attachedDate is less than or equal to SMALLER_ATTACHED_DATE
        defaultVehicleControlAttachmentsShouldNotBeFound("attachedDate.lessThanOrEqual=" + SMALLER_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByAttachedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList where attachedDate is less than DEFAULT_ATTACHED_DATE
        defaultVehicleControlAttachmentsShouldNotBeFound("attachedDate.lessThan=" + DEFAULT_ATTACHED_DATE);

        // Get all the vehicleControlAttachmentsList where attachedDate is less than UPDATED_ATTACHED_DATE
        defaultVehicleControlAttachmentsShouldBeFound("attachedDate.lessThan=" + UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByAttachedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        // Get all the vehicleControlAttachmentsList where attachedDate is greater than DEFAULT_ATTACHED_DATE
        defaultVehicleControlAttachmentsShouldNotBeFound("attachedDate.greaterThan=" + DEFAULT_ATTACHED_DATE);

        // Get all the vehicleControlAttachmentsList where attachedDate is greater than SMALLER_ATTACHED_DATE
        defaultVehicleControlAttachmentsShouldBeFound("attachedDate.greaterThan=" + SMALLER_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleControlAttachmentsByVehicleControlsIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);
        VehicleControls vehicleControls = VehicleControlsResourceIT.createEntity(em);
        em.persist(vehicleControls);
        em.flush();
        vehicleControlAttachments.setVehicleControls(vehicleControls);
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);
        Long vehicleControlsId = vehicleControls.getId();

        // Get all the vehicleControlAttachmentsList where vehicleControls equals to vehicleControlsId
        defaultVehicleControlAttachmentsShouldBeFound("vehicleControlsId.equals=" + vehicleControlsId);

        // Get all the vehicleControlAttachmentsList where vehicleControls equals to (vehicleControlsId + 1)
        defaultVehicleControlAttachmentsShouldNotBeFound("vehicleControlsId.equals=" + (vehicleControlsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVehicleControlAttachmentsShouldBeFound(String filter) throws Exception {
        restVehicleControlAttachmentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleControlAttachments.getId().intValue())))
            .andExpect(jsonPath("$.[*].attachImageContentType").value(hasItem(DEFAULT_ATTACH_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACH_IMAGE))))
            .andExpect(jsonPath("$.[*].attachUrl").value(hasItem(DEFAULT_ATTACH_URL)))
            .andExpect(jsonPath("$.[*].attachDescription").value(hasItem(DEFAULT_ATTACH_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].attachedDate").value(hasItem(sameInstant(DEFAULT_ATTACHED_DATE))));

        // Check, that the count call also returns 1
        restVehicleControlAttachmentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVehicleControlAttachmentsShouldNotBeFound(String filter) throws Exception {
        restVehicleControlAttachmentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVehicleControlAttachmentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVehicleControlAttachments() throws Exception {
        // Get the vehicleControlAttachments
        restVehicleControlAttachmentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVehicleControlAttachments() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        int databaseSizeBeforeUpdate = vehicleControlAttachmentsRepository.findAll().size();

        // Update the vehicleControlAttachments
        VehicleControlAttachments updatedVehicleControlAttachments = vehicleControlAttachmentsRepository
            .findById(vehicleControlAttachments.getId())
            .get();
        // Disconnect from session so that the updates on updatedVehicleControlAttachments are not directly saved in db
        em.detach(updatedVehicleControlAttachments);
        updatedVehicleControlAttachments
            .attachImage(UPDATED_ATTACH_IMAGE)
            .attachImageContentType(UPDATED_ATTACH_IMAGE_CONTENT_TYPE)
            .attachUrl(UPDATED_ATTACH_URL)
            .attachDescription(UPDATED_ATTACH_DESCRIPTION)
            .attachedDate(UPDATED_ATTACHED_DATE);
        VehicleControlAttachmentsDTO vehicleControlAttachmentsDTO = vehicleControlAttachmentsMapper.toDto(updatedVehicleControlAttachments);

        restVehicleControlAttachmentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleControlAttachmentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlAttachmentsDTO))
            )
            .andExpect(status().isOk());

        // Validate the VehicleControlAttachments in the database
        List<VehicleControlAttachments> vehicleControlAttachmentsList = vehicleControlAttachmentsRepository.findAll();
        assertThat(vehicleControlAttachmentsList).hasSize(databaseSizeBeforeUpdate);
        VehicleControlAttachments testVehicleControlAttachments = vehicleControlAttachmentsList.get(
            vehicleControlAttachmentsList.size() - 1
        );
        assertThat(testVehicleControlAttachments.getAttachImage()).isEqualTo(UPDATED_ATTACH_IMAGE);
        assertThat(testVehicleControlAttachments.getAttachImageContentType()).isEqualTo(UPDATED_ATTACH_IMAGE_CONTENT_TYPE);
        assertThat(testVehicleControlAttachments.getAttachUrl()).isEqualTo(UPDATED_ATTACH_URL);
        assertThat(testVehicleControlAttachments.getAttachDescription()).isEqualTo(UPDATED_ATTACH_DESCRIPTION);
        assertThat(testVehicleControlAttachments.getAttachedDate()).isEqualTo(UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingVehicleControlAttachments() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlAttachmentsRepository.findAll().size();
        vehicleControlAttachments.setId(count.incrementAndGet());

        // Create the VehicleControlAttachments
        VehicleControlAttachmentsDTO vehicleControlAttachmentsDTO = vehicleControlAttachmentsMapper.toDto(vehicleControlAttachments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleControlAttachmentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleControlAttachmentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlAttachments in the database
        List<VehicleControlAttachments> vehicleControlAttachmentsList = vehicleControlAttachmentsRepository.findAll();
        assertThat(vehicleControlAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVehicleControlAttachments() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlAttachmentsRepository.findAll().size();
        vehicleControlAttachments.setId(count.incrementAndGet());

        // Create the VehicleControlAttachments
        VehicleControlAttachmentsDTO vehicleControlAttachmentsDTO = vehicleControlAttachmentsMapper.toDto(vehicleControlAttachments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlAttachmentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlAttachments in the database
        List<VehicleControlAttachments> vehicleControlAttachmentsList = vehicleControlAttachmentsRepository.findAll();
        assertThat(vehicleControlAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVehicleControlAttachments() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlAttachmentsRepository.findAll().size();
        vehicleControlAttachments.setId(count.incrementAndGet());

        // Create the VehicleControlAttachments
        VehicleControlAttachmentsDTO vehicleControlAttachmentsDTO = vehicleControlAttachmentsMapper.toDto(vehicleControlAttachments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlAttachmentsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlAttachmentsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleControlAttachments in the database
        List<VehicleControlAttachments> vehicleControlAttachmentsList = vehicleControlAttachmentsRepository.findAll();
        assertThat(vehicleControlAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVehicleControlAttachmentsWithPatch() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        int databaseSizeBeforeUpdate = vehicleControlAttachmentsRepository.findAll().size();

        // Update the vehicleControlAttachments using partial update
        VehicleControlAttachments partialUpdatedVehicleControlAttachments = new VehicleControlAttachments();
        partialUpdatedVehicleControlAttachments.setId(vehicleControlAttachments.getId());

        partialUpdatedVehicleControlAttachments
            .attachImage(UPDATED_ATTACH_IMAGE)
            .attachImageContentType(UPDATED_ATTACH_IMAGE_CONTENT_TYPE)
            .attachDescription(UPDATED_ATTACH_DESCRIPTION)
            .attachedDate(UPDATED_ATTACHED_DATE);

        restVehicleControlAttachmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleControlAttachments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleControlAttachments))
            )
            .andExpect(status().isOk());

        // Validate the VehicleControlAttachments in the database
        List<VehicleControlAttachments> vehicleControlAttachmentsList = vehicleControlAttachmentsRepository.findAll();
        assertThat(vehicleControlAttachmentsList).hasSize(databaseSizeBeforeUpdate);
        VehicleControlAttachments testVehicleControlAttachments = vehicleControlAttachmentsList.get(
            vehicleControlAttachmentsList.size() - 1
        );
        assertThat(testVehicleControlAttachments.getAttachImage()).isEqualTo(UPDATED_ATTACH_IMAGE);
        assertThat(testVehicleControlAttachments.getAttachImageContentType()).isEqualTo(UPDATED_ATTACH_IMAGE_CONTENT_TYPE);
        assertThat(testVehicleControlAttachments.getAttachUrl()).isEqualTo(DEFAULT_ATTACH_URL);
        assertThat(testVehicleControlAttachments.getAttachDescription()).isEqualTo(UPDATED_ATTACH_DESCRIPTION);
        assertThat(testVehicleControlAttachments.getAttachedDate()).isEqualTo(UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateVehicleControlAttachmentsWithPatch() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        int databaseSizeBeforeUpdate = vehicleControlAttachmentsRepository.findAll().size();

        // Update the vehicleControlAttachments using partial update
        VehicleControlAttachments partialUpdatedVehicleControlAttachments = new VehicleControlAttachments();
        partialUpdatedVehicleControlAttachments.setId(vehicleControlAttachments.getId());

        partialUpdatedVehicleControlAttachments
            .attachImage(UPDATED_ATTACH_IMAGE)
            .attachImageContentType(UPDATED_ATTACH_IMAGE_CONTENT_TYPE)
            .attachUrl(UPDATED_ATTACH_URL)
            .attachDescription(UPDATED_ATTACH_DESCRIPTION)
            .attachedDate(UPDATED_ATTACHED_DATE);

        restVehicleControlAttachmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleControlAttachments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleControlAttachments))
            )
            .andExpect(status().isOk());

        // Validate the VehicleControlAttachments in the database
        List<VehicleControlAttachments> vehicleControlAttachmentsList = vehicleControlAttachmentsRepository.findAll();
        assertThat(vehicleControlAttachmentsList).hasSize(databaseSizeBeforeUpdate);
        VehicleControlAttachments testVehicleControlAttachments = vehicleControlAttachmentsList.get(
            vehicleControlAttachmentsList.size() - 1
        );
        assertThat(testVehicleControlAttachments.getAttachImage()).isEqualTo(UPDATED_ATTACH_IMAGE);
        assertThat(testVehicleControlAttachments.getAttachImageContentType()).isEqualTo(UPDATED_ATTACH_IMAGE_CONTENT_TYPE);
        assertThat(testVehicleControlAttachments.getAttachUrl()).isEqualTo(UPDATED_ATTACH_URL);
        assertThat(testVehicleControlAttachments.getAttachDescription()).isEqualTo(UPDATED_ATTACH_DESCRIPTION);
        assertThat(testVehicleControlAttachments.getAttachedDate()).isEqualTo(UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingVehicleControlAttachments() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlAttachmentsRepository.findAll().size();
        vehicleControlAttachments.setId(count.incrementAndGet());

        // Create the VehicleControlAttachments
        VehicleControlAttachmentsDTO vehicleControlAttachmentsDTO = vehicleControlAttachmentsMapper.toDto(vehicleControlAttachments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleControlAttachmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vehicleControlAttachmentsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlAttachments in the database
        List<VehicleControlAttachments> vehicleControlAttachmentsList = vehicleControlAttachmentsRepository.findAll();
        assertThat(vehicleControlAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVehicleControlAttachments() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlAttachmentsRepository.findAll().size();
        vehicleControlAttachments.setId(count.incrementAndGet());

        // Create the VehicleControlAttachments
        VehicleControlAttachmentsDTO vehicleControlAttachmentsDTO = vehicleControlAttachmentsMapper.toDto(vehicleControlAttachments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlAttachmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleControlAttachments in the database
        List<VehicleControlAttachments> vehicleControlAttachmentsList = vehicleControlAttachmentsRepository.findAll();
        assertThat(vehicleControlAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVehicleControlAttachments() throws Exception {
        int databaseSizeBeforeUpdate = vehicleControlAttachmentsRepository.findAll().size();
        vehicleControlAttachments.setId(count.incrementAndGet());

        // Create the VehicleControlAttachments
        VehicleControlAttachmentsDTO vehicleControlAttachmentsDTO = vehicleControlAttachmentsMapper.toDto(vehicleControlAttachments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleControlAttachmentsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleControlAttachmentsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleControlAttachments in the database
        List<VehicleControlAttachments> vehicleControlAttachmentsList = vehicleControlAttachmentsRepository.findAll();
        assertThat(vehicleControlAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVehicleControlAttachments() throws Exception {
        // Initialize the database
        vehicleControlAttachmentsRepository.saveAndFlush(vehicleControlAttachments);

        int databaseSizeBeforeDelete = vehicleControlAttachmentsRepository.findAll().size();

        // Delete the vehicleControlAttachments
        restVehicleControlAttachmentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, vehicleControlAttachments.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehicleControlAttachments> vehicleControlAttachmentsList = vehicleControlAttachmentsRepository.findAll();
        assertThat(vehicleControlAttachmentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
