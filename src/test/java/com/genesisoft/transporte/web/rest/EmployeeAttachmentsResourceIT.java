package com.genesisoft.transporte.web.rest;

import static com.genesisoft.transporte.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.EmployeeAttachments;
import com.genesisoft.transporte.domain.Employees;
import com.genesisoft.transporte.repository.EmployeeAttachmentsRepository;
import com.genesisoft.transporte.service.criteria.EmployeeAttachmentsCriteria;
import com.genesisoft.transporte.service.dto.EmployeeAttachmentsDTO;
import com.genesisoft.transporte.service.mapper.EmployeeAttachmentsMapper;
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
 * Integration tests for the {@link EmployeeAttachmentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeAttachmentsResourceIT {

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

    private static final String ENTITY_API_URL = "/api/employee-attachments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeAttachmentsRepository employeeAttachmentsRepository;

    @Autowired
    private EmployeeAttachmentsMapper employeeAttachmentsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeAttachmentsMockMvc;

    private EmployeeAttachments employeeAttachments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeAttachments createEntity(EntityManager em) {
        EmployeeAttachments employeeAttachments = new EmployeeAttachments()
            .attachImage(DEFAULT_ATTACH_IMAGE)
            .attachImageContentType(DEFAULT_ATTACH_IMAGE_CONTENT_TYPE)
            .attachUrl(DEFAULT_ATTACH_URL)
            .attachDescription(DEFAULT_ATTACH_DESCRIPTION)
            .attachedDate(DEFAULT_ATTACHED_DATE);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeAttachments.setEmployees(employees);
        return employeeAttachments;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeAttachments createUpdatedEntity(EntityManager em) {
        EmployeeAttachments employeeAttachments = new EmployeeAttachments()
            .attachImage(UPDATED_ATTACH_IMAGE)
            .attachImageContentType(UPDATED_ATTACH_IMAGE_CONTENT_TYPE)
            .attachUrl(UPDATED_ATTACH_URL)
            .attachDescription(UPDATED_ATTACH_DESCRIPTION)
            .attachedDate(UPDATED_ATTACHED_DATE);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeAttachments.setEmployees(employees);
        return employeeAttachments;
    }

    @BeforeEach
    public void initTest() {
        employeeAttachments = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeAttachments() throws Exception {
        int databaseSizeBeforeCreate = employeeAttachmentsRepository.findAll().size();
        // Create the EmployeeAttachments
        EmployeeAttachmentsDTO employeeAttachmentsDTO = employeeAttachmentsMapper.toDto(employeeAttachments);
        restEmployeeAttachmentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeAttachmentsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeAttachments in the database
        List<EmployeeAttachments> employeeAttachmentsList = employeeAttachmentsRepository.findAll();
        assertThat(employeeAttachmentsList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeAttachments testEmployeeAttachments = employeeAttachmentsList.get(employeeAttachmentsList.size() - 1);
        assertThat(testEmployeeAttachments.getAttachImage()).isEqualTo(DEFAULT_ATTACH_IMAGE);
        assertThat(testEmployeeAttachments.getAttachImageContentType()).isEqualTo(DEFAULT_ATTACH_IMAGE_CONTENT_TYPE);
        assertThat(testEmployeeAttachments.getAttachUrl()).isEqualTo(DEFAULT_ATTACH_URL);
        assertThat(testEmployeeAttachments.getAttachDescription()).isEqualTo(DEFAULT_ATTACH_DESCRIPTION);
        assertThat(testEmployeeAttachments.getAttachedDate()).isEqualTo(DEFAULT_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void createEmployeeAttachmentsWithExistingId() throws Exception {
        // Create the EmployeeAttachments with an existing ID
        employeeAttachments.setId(1L);
        EmployeeAttachmentsDTO employeeAttachmentsDTO = employeeAttachmentsMapper.toDto(employeeAttachments);

        int databaseSizeBeforeCreate = employeeAttachmentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeAttachmentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeAttachments in the database
        List<EmployeeAttachments> employeeAttachmentsList = employeeAttachmentsRepository.findAll();
        assertThat(employeeAttachmentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAttachUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeAttachmentsRepository.findAll().size();
        // set the field null
        employeeAttachments.setAttachUrl(null);

        // Create the EmployeeAttachments, which fails.
        EmployeeAttachmentsDTO employeeAttachmentsDTO = employeeAttachmentsMapper.toDto(employeeAttachments);

        restEmployeeAttachmentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeAttachments> employeeAttachmentsList = employeeAttachmentsRepository.findAll();
        assertThat(employeeAttachmentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAttachDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeAttachmentsRepository.findAll().size();
        // set the field null
        employeeAttachments.setAttachDescription(null);

        // Create the EmployeeAttachments, which fails.
        EmployeeAttachmentsDTO employeeAttachmentsDTO = employeeAttachmentsMapper.toDto(employeeAttachments);

        restEmployeeAttachmentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeAttachments> employeeAttachmentsList = employeeAttachmentsRepository.findAll();
        assertThat(employeeAttachmentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAttachedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeAttachmentsRepository.findAll().size();
        // set the field null
        employeeAttachments.setAttachedDate(null);

        // Create the EmployeeAttachments, which fails.
        EmployeeAttachmentsDTO employeeAttachmentsDTO = employeeAttachmentsMapper.toDto(employeeAttachments);

        restEmployeeAttachmentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeAttachments> employeeAttachmentsList = employeeAttachmentsRepository.findAll();
        assertThat(employeeAttachmentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployeeAttachments() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList
        restEmployeeAttachmentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeAttachments.getId().intValue())))
            .andExpect(jsonPath("$.[*].attachImageContentType").value(hasItem(DEFAULT_ATTACH_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACH_IMAGE))))
            .andExpect(jsonPath("$.[*].attachUrl").value(hasItem(DEFAULT_ATTACH_URL)))
            .andExpect(jsonPath("$.[*].attachDescription").value(hasItem(DEFAULT_ATTACH_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].attachedDate").value(hasItem(sameInstant(DEFAULT_ATTACHED_DATE))));
    }

    @Test
    @Transactional
    void getEmployeeAttachments() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get the employeeAttachments
        restEmployeeAttachmentsMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeAttachments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeAttachments.getId().intValue()))
            .andExpect(jsonPath("$.attachImageContentType").value(DEFAULT_ATTACH_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachImage").value(Base64Utils.encodeToString(DEFAULT_ATTACH_IMAGE)))
            .andExpect(jsonPath("$.attachUrl").value(DEFAULT_ATTACH_URL))
            .andExpect(jsonPath("$.attachDescription").value(DEFAULT_ATTACH_DESCRIPTION))
            .andExpect(jsonPath("$.attachedDate").value(sameInstant(DEFAULT_ATTACHED_DATE)));
    }

    @Test
    @Transactional
    void getEmployeeAttachmentsByIdFiltering() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        Long id = employeeAttachments.getId();

        defaultEmployeeAttachmentsShouldBeFound("id.equals=" + id);
        defaultEmployeeAttachmentsShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeAttachmentsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeAttachmentsShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeAttachmentsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeAttachmentsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByAttachUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList where attachUrl equals to DEFAULT_ATTACH_URL
        defaultEmployeeAttachmentsShouldBeFound("attachUrl.equals=" + DEFAULT_ATTACH_URL);

        // Get all the employeeAttachmentsList where attachUrl equals to UPDATED_ATTACH_URL
        defaultEmployeeAttachmentsShouldNotBeFound("attachUrl.equals=" + UPDATED_ATTACH_URL);
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByAttachUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList where attachUrl not equals to DEFAULT_ATTACH_URL
        defaultEmployeeAttachmentsShouldNotBeFound("attachUrl.notEquals=" + DEFAULT_ATTACH_URL);

        // Get all the employeeAttachmentsList where attachUrl not equals to UPDATED_ATTACH_URL
        defaultEmployeeAttachmentsShouldBeFound("attachUrl.notEquals=" + UPDATED_ATTACH_URL);
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByAttachUrlIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList where attachUrl in DEFAULT_ATTACH_URL or UPDATED_ATTACH_URL
        defaultEmployeeAttachmentsShouldBeFound("attachUrl.in=" + DEFAULT_ATTACH_URL + "," + UPDATED_ATTACH_URL);

        // Get all the employeeAttachmentsList where attachUrl equals to UPDATED_ATTACH_URL
        defaultEmployeeAttachmentsShouldNotBeFound("attachUrl.in=" + UPDATED_ATTACH_URL);
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByAttachUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList where attachUrl is not null
        defaultEmployeeAttachmentsShouldBeFound("attachUrl.specified=true");

        // Get all the employeeAttachmentsList where attachUrl is null
        defaultEmployeeAttachmentsShouldNotBeFound("attachUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByAttachUrlContainsSomething() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList where attachUrl contains DEFAULT_ATTACH_URL
        defaultEmployeeAttachmentsShouldBeFound("attachUrl.contains=" + DEFAULT_ATTACH_URL);

        // Get all the employeeAttachmentsList where attachUrl contains UPDATED_ATTACH_URL
        defaultEmployeeAttachmentsShouldNotBeFound("attachUrl.contains=" + UPDATED_ATTACH_URL);
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByAttachUrlNotContainsSomething() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList where attachUrl does not contain DEFAULT_ATTACH_URL
        defaultEmployeeAttachmentsShouldNotBeFound("attachUrl.doesNotContain=" + DEFAULT_ATTACH_URL);

        // Get all the employeeAttachmentsList where attachUrl does not contain UPDATED_ATTACH_URL
        defaultEmployeeAttachmentsShouldBeFound("attachUrl.doesNotContain=" + UPDATED_ATTACH_URL);
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByAttachDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList where attachDescription equals to DEFAULT_ATTACH_DESCRIPTION
        defaultEmployeeAttachmentsShouldBeFound("attachDescription.equals=" + DEFAULT_ATTACH_DESCRIPTION);

        // Get all the employeeAttachmentsList where attachDescription equals to UPDATED_ATTACH_DESCRIPTION
        defaultEmployeeAttachmentsShouldNotBeFound("attachDescription.equals=" + UPDATED_ATTACH_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByAttachDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList where attachDescription not equals to DEFAULT_ATTACH_DESCRIPTION
        defaultEmployeeAttachmentsShouldNotBeFound("attachDescription.notEquals=" + DEFAULT_ATTACH_DESCRIPTION);

        // Get all the employeeAttachmentsList where attachDescription not equals to UPDATED_ATTACH_DESCRIPTION
        defaultEmployeeAttachmentsShouldBeFound("attachDescription.notEquals=" + UPDATED_ATTACH_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByAttachDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList where attachDescription in DEFAULT_ATTACH_DESCRIPTION or UPDATED_ATTACH_DESCRIPTION
        defaultEmployeeAttachmentsShouldBeFound("attachDescription.in=" + DEFAULT_ATTACH_DESCRIPTION + "," + UPDATED_ATTACH_DESCRIPTION);

        // Get all the employeeAttachmentsList where attachDescription equals to UPDATED_ATTACH_DESCRIPTION
        defaultEmployeeAttachmentsShouldNotBeFound("attachDescription.in=" + UPDATED_ATTACH_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByAttachDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList where attachDescription is not null
        defaultEmployeeAttachmentsShouldBeFound("attachDescription.specified=true");

        // Get all the employeeAttachmentsList where attachDescription is null
        defaultEmployeeAttachmentsShouldNotBeFound("attachDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByAttachDescriptionContainsSomething() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList where attachDescription contains DEFAULT_ATTACH_DESCRIPTION
        defaultEmployeeAttachmentsShouldBeFound("attachDescription.contains=" + DEFAULT_ATTACH_DESCRIPTION);

        // Get all the employeeAttachmentsList where attachDescription contains UPDATED_ATTACH_DESCRIPTION
        defaultEmployeeAttachmentsShouldNotBeFound("attachDescription.contains=" + UPDATED_ATTACH_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByAttachDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList where attachDescription does not contain DEFAULT_ATTACH_DESCRIPTION
        defaultEmployeeAttachmentsShouldNotBeFound("attachDescription.doesNotContain=" + DEFAULT_ATTACH_DESCRIPTION);

        // Get all the employeeAttachmentsList where attachDescription does not contain UPDATED_ATTACH_DESCRIPTION
        defaultEmployeeAttachmentsShouldBeFound("attachDescription.doesNotContain=" + UPDATED_ATTACH_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByAttachedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList where attachedDate equals to DEFAULT_ATTACHED_DATE
        defaultEmployeeAttachmentsShouldBeFound("attachedDate.equals=" + DEFAULT_ATTACHED_DATE);

        // Get all the employeeAttachmentsList where attachedDate equals to UPDATED_ATTACHED_DATE
        defaultEmployeeAttachmentsShouldNotBeFound("attachedDate.equals=" + UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByAttachedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList where attachedDate not equals to DEFAULT_ATTACHED_DATE
        defaultEmployeeAttachmentsShouldNotBeFound("attachedDate.notEquals=" + DEFAULT_ATTACHED_DATE);

        // Get all the employeeAttachmentsList where attachedDate not equals to UPDATED_ATTACHED_DATE
        defaultEmployeeAttachmentsShouldBeFound("attachedDate.notEquals=" + UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByAttachedDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList where attachedDate in DEFAULT_ATTACHED_DATE or UPDATED_ATTACHED_DATE
        defaultEmployeeAttachmentsShouldBeFound("attachedDate.in=" + DEFAULT_ATTACHED_DATE + "," + UPDATED_ATTACHED_DATE);

        // Get all the employeeAttachmentsList where attachedDate equals to UPDATED_ATTACHED_DATE
        defaultEmployeeAttachmentsShouldNotBeFound("attachedDate.in=" + UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByAttachedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList where attachedDate is not null
        defaultEmployeeAttachmentsShouldBeFound("attachedDate.specified=true");

        // Get all the employeeAttachmentsList where attachedDate is null
        defaultEmployeeAttachmentsShouldNotBeFound("attachedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByAttachedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList where attachedDate is greater than or equal to DEFAULT_ATTACHED_DATE
        defaultEmployeeAttachmentsShouldBeFound("attachedDate.greaterThanOrEqual=" + DEFAULT_ATTACHED_DATE);

        // Get all the employeeAttachmentsList where attachedDate is greater than or equal to UPDATED_ATTACHED_DATE
        defaultEmployeeAttachmentsShouldNotBeFound("attachedDate.greaterThanOrEqual=" + UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByAttachedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList where attachedDate is less than or equal to DEFAULT_ATTACHED_DATE
        defaultEmployeeAttachmentsShouldBeFound("attachedDate.lessThanOrEqual=" + DEFAULT_ATTACHED_DATE);

        // Get all the employeeAttachmentsList where attachedDate is less than or equal to SMALLER_ATTACHED_DATE
        defaultEmployeeAttachmentsShouldNotBeFound("attachedDate.lessThanOrEqual=" + SMALLER_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByAttachedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList where attachedDate is less than DEFAULT_ATTACHED_DATE
        defaultEmployeeAttachmentsShouldNotBeFound("attachedDate.lessThan=" + DEFAULT_ATTACHED_DATE);

        // Get all the employeeAttachmentsList where attachedDate is less than UPDATED_ATTACHED_DATE
        defaultEmployeeAttachmentsShouldBeFound("attachedDate.lessThan=" + UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByAttachedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        // Get all the employeeAttachmentsList where attachedDate is greater than DEFAULT_ATTACHED_DATE
        defaultEmployeeAttachmentsShouldNotBeFound("attachedDate.greaterThan=" + DEFAULT_ATTACHED_DATE);

        // Get all the employeeAttachmentsList where attachedDate is greater than SMALLER_ATTACHED_DATE
        defaultEmployeeAttachmentsShouldBeFound("attachedDate.greaterThan=" + SMALLER_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeAttachmentsByEmployeesIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);
        Employees employees = EmployeesResourceIT.createEntity(em);
        em.persist(employees);
        em.flush();
        employeeAttachments.setEmployees(employees);
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);
        Long employeesId = employees.getId();

        // Get all the employeeAttachmentsList where employees equals to employeesId
        defaultEmployeeAttachmentsShouldBeFound("employeesId.equals=" + employeesId);

        // Get all the employeeAttachmentsList where employees equals to (employeesId + 1)
        defaultEmployeeAttachmentsShouldNotBeFound("employeesId.equals=" + (employeesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeAttachmentsShouldBeFound(String filter) throws Exception {
        restEmployeeAttachmentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeAttachments.getId().intValue())))
            .andExpect(jsonPath("$.[*].attachImageContentType").value(hasItem(DEFAULT_ATTACH_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACH_IMAGE))))
            .andExpect(jsonPath("$.[*].attachUrl").value(hasItem(DEFAULT_ATTACH_URL)))
            .andExpect(jsonPath("$.[*].attachDescription").value(hasItem(DEFAULT_ATTACH_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].attachedDate").value(hasItem(sameInstant(DEFAULT_ATTACHED_DATE))));

        // Check, that the count call also returns 1
        restEmployeeAttachmentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeAttachmentsShouldNotBeFound(String filter) throws Exception {
        restEmployeeAttachmentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeAttachmentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeAttachments() throws Exception {
        // Get the employeeAttachments
        restEmployeeAttachmentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmployeeAttachments() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        int databaseSizeBeforeUpdate = employeeAttachmentsRepository.findAll().size();

        // Update the employeeAttachments
        EmployeeAttachments updatedEmployeeAttachments = employeeAttachmentsRepository.findById(employeeAttachments.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeAttachments are not directly saved in db
        em.detach(updatedEmployeeAttachments);
        updatedEmployeeAttachments
            .attachImage(UPDATED_ATTACH_IMAGE)
            .attachImageContentType(UPDATED_ATTACH_IMAGE_CONTENT_TYPE)
            .attachUrl(UPDATED_ATTACH_URL)
            .attachDescription(UPDATED_ATTACH_DESCRIPTION)
            .attachedDate(UPDATED_ATTACHED_DATE);
        EmployeeAttachmentsDTO employeeAttachmentsDTO = employeeAttachmentsMapper.toDto(updatedEmployeeAttachments);

        restEmployeeAttachmentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeAttachmentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeAttachmentsDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeAttachments in the database
        List<EmployeeAttachments> employeeAttachmentsList = employeeAttachmentsRepository.findAll();
        assertThat(employeeAttachmentsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeAttachments testEmployeeAttachments = employeeAttachmentsList.get(employeeAttachmentsList.size() - 1);
        assertThat(testEmployeeAttachments.getAttachImage()).isEqualTo(UPDATED_ATTACH_IMAGE);
        assertThat(testEmployeeAttachments.getAttachImageContentType()).isEqualTo(UPDATED_ATTACH_IMAGE_CONTENT_TYPE);
        assertThat(testEmployeeAttachments.getAttachUrl()).isEqualTo(UPDATED_ATTACH_URL);
        assertThat(testEmployeeAttachments.getAttachDescription()).isEqualTo(UPDATED_ATTACH_DESCRIPTION);
        assertThat(testEmployeeAttachments.getAttachedDate()).isEqualTo(UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeAttachments() throws Exception {
        int databaseSizeBeforeUpdate = employeeAttachmentsRepository.findAll().size();
        employeeAttachments.setId(count.incrementAndGet());

        // Create the EmployeeAttachments
        EmployeeAttachmentsDTO employeeAttachmentsDTO = employeeAttachmentsMapper.toDto(employeeAttachments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeAttachmentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeAttachmentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeAttachments in the database
        List<EmployeeAttachments> employeeAttachmentsList = employeeAttachmentsRepository.findAll();
        assertThat(employeeAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeAttachments() throws Exception {
        int databaseSizeBeforeUpdate = employeeAttachmentsRepository.findAll().size();
        employeeAttachments.setId(count.incrementAndGet());

        // Create the EmployeeAttachments
        EmployeeAttachmentsDTO employeeAttachmentsDTO = employeeAttachmentsMapper.toDto(employeeAttachments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeAttachmentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeAttachments in the database
        List<EmployeeAttachments> employeeAttachmentsList = employeeAttachmentsRepository.findAll();
        assertThat(employeeAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeAttachments() throws Exception {
        int databaseSizeBeforeUpdate = employeeAttachmentsRepository.findAll().size();
        employeeAttachments.setId(count.incrementAndGet());

        // Create the EmployeeAttachments
        EmployeeAttachmentsDTO employeeAttachmentsDTO = employeeAttachmentsMapper.toDto(employeeAttachments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeAttachmentsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeAttachmentsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeAttachments in the database
        List<EmployeeAttachments> employeeAttachmentsList = employeeAttachmentsRepository.findAll();
        assertThat(employeeAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeAttachmentsWithPatch() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        int databaseSizeBeforeUpdate = employeeAttachmentsRepository.findAll().size();

        // Update the employeeAttachments using partial update
        EmployeeAttachments partialUpdatedEmployeeAttachments = new EmployeeAttachments();
        partialUpdatedEmployeeAttachments.setId(employeeAttachments.getId());

        partialUpdatedEmployeeAttachments.attachUrl(UPDATED_ATTACH_URL).attachedDate(UPDATED_ATTACHED_DATE);

        restEmployeeAttachmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeAttachments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeAttachments))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeAttachments in the database
        List<EmployeeAttachments> employeeAttachmentsList = employeeAttachmentsRepository.findAll();
        assertThat(employeeAttachmentsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeAttachments testEmployeeAttachments = employeeAttachmentsList.get(employeeAttachmentsList.size() - 1);
        assertThat(testEmployeeAttachments.getAttachImage()).isEqualTo(DEFAULT_ATTACH_IMAGE);
        assertThat(testEmployeeAttachments.getAttachImageContentType()).isEqualTo(DEFAULT_ATTACH_IMAGE_CONTENT_TYPE);
        assertThat(testEmployeeAttachments.getAttachUrl()).isEqualTo(UPDATED_ATTACH_URL);
        assertThat(testEmployeeAttachments.getAttachDescription()).isEqualTo(DEFAULT_ATTACH_DESCRIPTION);
        assertThat(testEmployeeAttachments.getAttachedDate()).isEqualTo(UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeAttachmentsWithPatch() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        int databaseSizeBeforeUpdate = employeeAttachmentsRepository.findAll().size();

        // Update the employeeAttachments using partial update
        EmployeeAttachments partialUpdatedEmployeeAttachments = new EmployeeAttachments();
        partialUpdatedEmployeeAttachments.setId(employeeAttachments.getId());

        partialUpdatedEmployeeAttachments
            .attachImage(UPDATED_ATTACH_IMAGE)
            .attachImageContentType(UPDATED_ATTACH_IMAGE_CONTENT_TYPE)
            .attachUrl(UPDATED_ATTACH_URL)
            .attachDescription(UPDATED_ATTACH_DESCRIPTION)
            .attachedDate(UPDATED_ATTACHED_DATE);

        restEmployeeAttachmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeAttachments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeAttachments))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeAttachments in the database
        List<EmployeeAttachments> employeeAttachmentsList = employeeAttachmentsRepository.findAll();
        assertThat(employeeAttachmentsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeAttachments testEmployeeAttachments = employeeAttachmentsList.get(employeeAttachmentsList.size() - 1);
        assertThat(testEmployeeAttachments.getAttachImage()).isEqualTo(UPDATED_ATTACH_IMAGE);
        assertThat(testEmployeeAttachments.getAttachImageContentType()).isEqualTo(UPDATED_ATTACH_IMAGE_CONTENT_TYPE);
        assertThat(testEmployeeAttachments.getAttachUrl()).isEqualTo(UPDATED_ATTACH_URL);
        assertThat(testEmployeeAttachments.getAttachDescription()).isEqualTo(UPDATED_ATTACH_DESCRIPTION);
        assertThat(testEmployeeAttachments.getAttachedDate()).isEqualTo(UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeAttachments() throws Exception {
        int databaseSizeBeforeUpdate = employeeAttachmentsRepository.findAll().size();
        employeeAttachments.setId(count.incrementAndGet());

        // Create the EmployeeAttachments
        EmployeeAttachmentsDTO employeeAttachmentsDTO = employeeAttachmentsMapper.toDto(employeeAttachments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeAttachmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeAttachmentsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeAttachments in the database
        List<EmployeeAttachments> employeeAttachmentsList = employeeAttachmentsRepository.findAll();
        assertThat(employeeAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeAttachments() throws Exception {
        int databaseSizeBeforeUpdate = employeeAttachmentsRepository.findAll().size();
        employeeAttachments.setId(count.incrementAndGet());

        // Create the EmployeeAttachments
        EmployeeAttachmentsDTO employeeAttachmentsDTO = employeeAttachmentsMapper.toDto(employeeAttachments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeAttachmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeAttachments in the database
        List<EmployeeAttachments> employeeAttachmentsList = employeeAttachmentsRepository.findAll();
        assertThat(employeeAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeAttachments() throws Exception {
        int databaseSizeBeforeUpdate = employeeAttachmentsRepository.findAll().size();
        employeeAttachments.setId(count.incrementAndGet());

        // Create the EmployeeAttachments
        EmployeeAttachmentsDTO employeeAttachmentsDTO = employeeAttachmentsMapper.toDto(employeeAttachments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeAttachmentsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeAttachmentsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeAttachments in the database
        List<EmployeeAttachments> employeeAttachmentsList = employeeAttachmentsRepository.findAll();
        assertThat(employeeAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeAttachments() throws Exception {
        // Initialize the database
        employeeAttachmentsRepository.saveAndFlush(employeeAttachments);

        int databaseSizeBeforeDelete = employeeAttachmentsRepository.findAll().size();

        // Delete the employeeAttachments
        restEmployeeAttachmentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeAttachments.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeAttachments> employeeAttachmentsList = employeeAttachmentsRepository.findAll();
        assertThat(employeeAttachmentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
