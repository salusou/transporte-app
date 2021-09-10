package com.genesisoft.transporte.web.rest;

import static com.genesisoft.transporte.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.CustomerAttachments;
import com.genesisoft.transporte.domain.Customers;
import com.genesisoft.transporte.domain.StatusAttachments;
import com.genesisoft.transporte.repository.CustomerAttachmentsRepository;
import com.genesisoft.transporte.service.criteria.CustomerAttachmentsCriteria;
import com.genesisoft.transporte.service.dto.CustomerAttachmentsDTO;
import com.genesisoft.transporte.service.mapper.CustomerAttachmentsMapper;
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
 * Integration tests for the {@link CustomerAttachmentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomerAttachmentsResourceIT {

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

    private static final String ENTITY_API_URL = "/api/customer-attachments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomerAttachmentsRepository customerAttachmentsRepository;

    @Autowired
    private CustomerAttachmentsMapper customerAttachmentsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerAttachmentsMockMvc;

    private CustomerAttachments customerAttachments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerAttachments createEntity(EntityManager em) {
        CustomerAttachments customerAttachments = new CustomerAttachments()
            .attachImage(DEFAULT_ATTACH_IMAGE)
            .attachImageContentType(DEFAULT_ATTACH_IMAGE_CONTENT_TYPE)
            .attachUrl(DEFAULT_ATTACH_URL)
            .attachDescription(DEFAULT_ATTACH_DESCRIPTION)
            .attachedDate(DEFAULT_ATTACHED_DATE);
        // Add required entity
        Customers customers;
        if (TestUtil.findAll(em, Customers.class).isEmpty()) {
            customers = CustomersResourceIT.createEntity(em);
            em.persist(customers);
            em.flush();
        } else {
            customers = TestUtil.findAll(em, Customers.class).get(0);
        }
        customerAttachments.setCustomers(customers);
        // Add required entity
        StatusAttachments statusAttachments;
        if (TestUtil.findAll(em, StatusAttachments.class).isEmpty()) {
            statusAttachments = StatusAttachmentsResourceIT.createEntity(em);
            em.persist(statusAttachments);
            em.flush();
        } else {
            statusAttachments = TestUtil.findAll(em, StatusAttachments.class).get(0);
        }
        customerAttachments.setStatusAttachments(statusAttachments);
        return customerAttachments;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerAttachments createUpdatedEntity(EntityManager em) {
        CustomerAttachments customerAttachments = new CustomerAttachments()
            .attachImage(UPDATED_ATTACH_IMAGE)
            .attachImageContentType(UPDATED_ATTACH_IMAGE_CONTENT_TYPE)
            .attachUrl(UPDATED_ATTACH_URL)
            .attachDescription(UPDATED_ATTACH_DESCRIPTION)
            .attachedDate(UPDATED_ATTACHED_DATE);
        // Add required entity
        Customers customers;
        if (TestUtil.findAll(em, Customers.class).isEmpty()) {
            customers = CustomersResourceIT.createUpdatedEntity(em);
            em.persist(customers);
            em.flush();
        } else {
            customers = TestUtil.findAll(em, Customers.class).get(0);
        }
        customerAttachments.setCustomers(customers);
        // Add required entity
        StatusAttachments statusAttachments;
        if (TestUtil.findAll(em, StatusAttachments.class).isEmpty()) {
            statusAttachments = StatusAttachmentsResourceIT.createUpdatedEntity(em);
            em.persist(statusAttachments);
            em.flush();
        } else {
            statusAttachments = TestUtil.findAll(em, StatusAttachments.class).get(0);
        }
        customerAttachments.setStatusAttachments(statusAttachments);
        return customerAttachments;
    }

    @BeforeEach
    public void initTest() {
        customerAttachments = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomerAttachments() throws Exception {
        int databaseSizeBeforeCreate = customerAttachmentsRepository.findAll().size();
        // Create the CustomerAttachments
        CustomerAttachmentsDTO customerAttachmentsDTO = customerAttachmentsMapper.toDto(customerAttachments);
        restCustomerAttachmentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerAttachmentsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustomerAttachments in the database
        List<CustomerAttachments> customerAttachmentsList = customerAttachmentsRepository.findAll();
        assertThat(customerAttachmentsList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerAttachments testCustomerAttachments = customerAttachmentsList.get(customerAttachmentsList.size() - 1);
        assertThat(testCustomerAttachments.getAttachImage()).isEqualTo(DEFAULT_ATTACH_IMAGE);
        assertThat(testCustomerAttachments.getAttachImageContentType()).isEqualTo(DEFAULT_ATTACH_IMAGE_CONTENT_TYPE);
        assertThat(testCustomerAttachments.getAttachUrl()).isEqualTo(DEFAULT_ATTACH_URL);
        assertThat(testCustomerAttachments.getAttachDescription()).isEqualTo(DEFAULT_ATTACH_DESCRIPTION);
        assertThat(testCustomerAttachments.getAttachedDate()).isEqualTo(DEFAULT_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void createCustomerAttachmentsWithExistingId() throws Exception {
        // Create the CustomerAttachments with an existing ID
        customerAttachments.setId(1L);
        CustomerAttachmentsDTO customerAttachmentsDTO = customerAttachmentsMapper.toDto(customerAttachments);

        int databaseSizeBeforeCreate = customerAttachmentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerAttachmentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerAttachments in the database
        List<CustomerAttachments> customerAttachmentsList = customerAttachmentsRepository.findAll();
        assertThat(customerAttachmentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAttachUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerAttachmentsRepository.findAll().size();
        // set the field null
        customerAttachments.setAttachUrl(null);

        // Create the CustomerAttachments, which fails.
        CustomerAttachmentsDTO customerAttachmentsDTO = customerAttachmentsMapper.toDto(customerAttachments);

        restCustomerAttachmentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustomerAttachments> customerAttachmentsList = customerAttachmentsRepository.findAll();
        assertThat(customerAttachmentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAttachDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerAttachmentsRepository.findAll().size();
        // set the field null
        customerAttachments.setAttachDescription(null);

        // Create the CustomerAttachments, which fails.
        CustomerAttachmentsDTO customerAttachmentsDTO = customerAttachmentsMapper.toDto(customerAttachments);

        restCustomerAttachmentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustomerAttachments> customerAttachmentsList = customerAttachmentsRepository.findAll();
        assertThat(customerAttachmentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAttachedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerAttachmentsRepository.findAll().size();
        // set the field null
        customerAttachments.setAttachedDate(null);

        // Create the CustomerAttachments, which fails.
        CustomerAttachmentsDTO customerAttachmentsDTO = customerAttachmentsMapper.toDto(customerAttachments);

        restCustomerAttachmentsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustomerAttachments> customerAttachmentsList = customerAttachmentsRepository.findAll();
        assertThat(customerAttachmentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustomerAttachments() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList
        restCustomerAttachmentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerAttachments.getId().intValue())))
            .andExpect(jsonPath("$.[*].attachImageContentType").value(hasItem(DEFAULT_ATTACH_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACH_IMAGE))))
            .andExpect(jsonPath("$.[*].attachUrl").value(hasItem(DEFAULT_ATTACH_URL)))
            .andExpect(jsonPath("$.[*].attachDescription").value(hasItem(DEFAULT_ATTACH_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].attachedDate").value(hasItem(sameInstant(DEFAULT_ATTACHED_DATE))));
    }

    @Test
    @Transactional
    void getCustomerAttachments() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get the customerAttachments
        restCustomerAttachmentsMockMvc
            .perform(get(ENTITY_API_URL_ID, customerAttachments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerAttachments.getId().intValue()))
            .andExpect(jsonPath("$.attachImageContentType").value(DEFAULT_ATTACH_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachImage").value(Base64Utils.encodeToString(DEFAULT_ATTACH_IMAGE)))
            .andExpect(jsonPath("$.attachUrl").value(DEFAULT_ATTACH_URL))
            .andExpect(jsonPath("$.attachDescription").value(DEFAULT_ATTACH_DESCRIPTION))
            .andExpect(jsonPath("$.attachedDate").value(sameInstant(DEFAULT_ATTACHED_DATE)));
    }

    @Test
    @Transactional
    void getCustomerAttachmentsByIdFiltering() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        Long id = customerAttachments.getId();

        defaultCustomerAttachmentsShouldBeFound("id.equals=" + id);
        defaultCustomerAttachmentsShouldNotBeFound("id.notEquals=" + id);

        defaultCustomerAttachmentsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomerAttachmentsShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomerAttachmentsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomerAttachmentsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByAttachUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList where attachUrl equals to DEFAULT_ATTACH_URL
        defaultCustomerAttachmentsShouldBeFound("attachUrl.equals=" + DEFAULT_ATTACH_URL);

        // Get all the customerAttachmentsList where attachUrl equals to UPDATED_ATTACH_URL
        defaultCustomerAttachmentsShouldNotBeFound("attachUrl.equals=" + UPDATED_ATTACH_URL);
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByAttachUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList where attachUrl not equals to DEFAULT_ATTACH_URL
        defaultCustomerAttachmentsShouldNotBeFound("attachUrl.notEquals=" + DEFAULT_ATTACH_URL);

        // Get all the customerAttachmentsList where attachUrl not equals to UPDATED_ATTACH_URL
        defaultCustomerAttachmentsShouldBeFound("attachUrl.notEquals=" + UPDATED_ATTACH_URL);
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByAttachUrlIsInShouldWork() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList where attachUrl in DEFAULT_ATTACH_URL or UPDATED_ATTACH_URL
        defaultCustomerAttachmentsShouldBeFound("attachUrl.in=" + DEFAULT_ATTACH_URL + "," + UPDATED_ATTACH_URL);

        // Get all the customerAttachmentsList where attachUrl equals to UPDATED_ATTACH_URL
        defaultCustomerAttachmentsShouldNotBeFound("attachUrl.in=" + UPDATED_ATTACH_URL);
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByAttachUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList where attachUrl is not null
        defaultCustomerAttachmentsShouldBeFound("attachUrl.specified=true");

        // Get all the customerAttachmentsList where attachUrl is null
        defaultCustomerAttachmentsShouldNotBeFound("attachUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByAttachUrlContainsSomething() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList where attachUrl contains DEFAULT_ATTACH_URL
        defaultCustomerAttachmentsShouldBeFound("attachUrl.contains=" + DEFAULT_ATTACH_URL);

        // Get all the customerAttachmentsList where attachUrl contains UPDATED_ATTACH_URL
        defaultCustomerAttachmentsShouldNotBeFound("attachUrl.contains=" + UPDATED_ATTACH_URL);
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByAttachUrlNotContainsSomething() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList where attachUrl does not contain DEFAULT_ATTACH_URL
        defaultCustomerAttachmentsShouldNotBeFound("attachUrl.doesNotContain=" + DEFAULT_ATTACH_URL);

        // Get all the customerAttachmentsList where attachUrl does not contain UPDATED_ATTACH_URL
        defaultCustomerAttachmentsShouldBeFound("attachUrl.doesNotContain=" + UPDATED_ATTACH_URL);
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByAttachDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList where attachDescription equals to DEFAULT_ATTACH_DESCRIPTION
        defaultCustomerAttachmentsShouldBeFound("attachDescription.equals=" + DEFAULT_ATTACH_DESCRIPTION);

        // Get all the customerAttachmentsList where attachDescription equals to UPDATED_ATTACH_DESCRIPTION
        defaultCustomerAttachmentsShouldNotBeFound("attachDescription.equals=" + UPDATED_ATTACH_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByAttachDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList where attachDescription not equals to DEFAULT_ATTACH_DESCRIPTION
        defaultCustomerAttachmentsShouldNotBeFound("attachDescription.notEquals=" + DEFAULT_ATTACH_DESCRIPTION);

        // Get all the customerAttachmentsList where attachDescription not equals to UPDATED_ATTACH_DESCRIPTION
        defaultCustomerAttachmentsShouldBeFound("attachDescription.notEquals=" + UPDATED_ATTACH_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByAttachDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList where attachDescription in DEFAULT_ATTACH_DESCRIPTION or UPDATED_ATTACH_DESCRIPTION
        defaultCustomerAttachmentsShouldBeFound("attachDescription.in=" + DEFAULT_ATTACH_DESCRIPTION + "," + UPDATED_ATTACH_DESCRIPTION);

        // Get all the customerAttachmentsList where attachDescription equals to UPDATED_ATTACH_DESCRIPTION
        defaultCustomerAttachmentsShouldNotBeFound("attachDescription.in=" + UPDATED_ATTACH_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByAttachDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList where attachDescription is not null
        defaultCustomerAttachmentsShouldBeFound("attachDescription.specified=true");

        // Get all the customerAttachmentsList where attachDescription is null
        defaultCustomerAttachmentsShouldNotBeFound("attachDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByAttachDescriptionContainsSomething() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList where attachDescription contains DEFAULT_ATTACH_DESCRIPTION
        defaultCustomerAttachmentsShouldBeFound("attachDescription.contains=" + DEFAULT_ATTACH_DESCRIPTION);

        // Get all the customerAttachmentsList where attachDescription contains UPDATED_ATTACH_DESCRIPTION
        defaultCustomerAttachmentsShouldNotBeFound("attachDescription.contains=" + UPDATED_ATTACH_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByAttachDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList where attachDescription does not contain DEFAULT_ATTACH_DESCRIPTION
        defaultCustomerAttachmentsShouldNotBeFound("attachDescription.doesNotContain=" + DEFAULT_ATTACH_DESCRIPTION);

        // Get all the customerAttachmentsList where attachDescription does not contain UPDATED_ATTACH_DESCRIPTION
        defaultCustomerAttachmentsShouldBeFound("attachDescription.doesNotContain=" + UPDATED_ATTACH_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByAttachedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList where attachedDate equals to DEFAULT_ATTACHED_DATE
        defaultCustomerAttachmentsShouldBeFound("attachedDate.equals=" + DEFAULT_ATTACHED_DATE);

        // Get all the customerAttachmentsList where attachedDate equals to UPDATED_ATTACHED_DATE
        defaultCustomerAttachmentsShouldNotBeFound("attachedDate.equals=" + UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByAttachedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList where attachedDate not equals to DEFAULT_ATTACHED_DATE
        defaultCustomerAttachmentsShouldNotBeFound("attachedDate.notEquals=" + DEFAULT_ATTACHED_DATE);

        // Get all the customerAttachmentsList where attachedDate not equals to UPDATED_ATTACHED_DATE
        defaultCustomerAttachmentsShouldBeFound("attachedDate.notEquals=" + UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByAttachedDateIsInShouldWork() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList where attachedDate in DEFAULT_ATTACHED_DATE or UPDATED_ATTACHED_DATE
        defaultCustomerAttachmentsShouldBeFound("attachedDate.in=" + DEFAULT_ATTACHED_DATE + "," + UPDATED_ATTACHED_DATE);

        // Get all the customerAttachmentsList where attachedDate equals to UPDATED_ATTACHED_DATE
        defaultCustomerAttachmentsShouldNotBeFound("attachedDate.in=" + UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByAttachedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList where attachedDate is not null
        defaultCustomerAttachmentsShouldBeFound("attachedDate.specified=true");

        // Get all the customerAttachmentsList where attachedDate is null
        defaultCustomerAttachmentsShouldNotBeFound("attachedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByAttachedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList where attachedDate is greater than or equal to DEFAULT_ATTACHED_DATE
        defaultCustomerAttachmentsShouldBeFound("attachedDate.greaterThanOrEqual=" + DEFAULT_ATTACHED_DATE);

        // Get all the customerAttachmentsList where attachedDate is greater than or equal to UPDATED_ATTACHED_DATE
        defaultCustomerAttachmentsShouldNotBeFound("attachedDate.greaterThanOrEqual=" + UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByAttachedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList where attachedDate is less than or equal to DEFAULT_ATTACHED_DATE
        defaultCustomerAttachmentsShouldBeFound("attachedDate.lessThanOrEqual=" + DEFAULT_ATTACHED_DATE);

        // Get all the customerAttachmentsList where attachedDate is less than or equal to SMALLER_ATTACHED_DATE
        defaultCustomerAttachmentsShouldNotBeFound("attachedDate.lessThanOrEqual=" + SMALLER_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByAttachedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList where attachedDate is less than DEFAULT_ATTACHED_DATE
        defaultCustomerAttachmentsShouldNotBeFound("attachedDate.lessThan=" + DEFAULT_ATTACHED_DATE);

        // Get all the customerAttachmentsList where attachedDate is less than UPDATED_ATTACHED_DATE
        defaultCustomerAttachmentsShouldBeFound("attachedDate.lessThan=" + UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByAttachedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        // Get all the customerAttachmentsList where attachedDate is greater than DEFAULT_ATTACHED_DATE
        defaultCustomerAttachmentsShouldNotBeFound("attachedDate.greaterThan=" + DEFAULT_ATTACHED_DATE);

        // Get all the customerAttachmentsList where attachedDate is greater than SMALLER_ATTACHED_DATE
        defaultCustomerAttachmentsShouldBeFound("attachedDate.greaterThan=" + SMALLER_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByCustomersIsEqualToSomething() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);
        Customers customers = CustomersResourceIT.createEntity(em);
        em.persist(customers);
        em.flush();
        customerAttachments.setCustomers(customers);
        customerAttachmentsRepository.saveAndFlush(customerAttachments);
        Long customersId = customers.getId();

        // Get all the customerAttachmentsList where customers equals to customersId
        defaultCustomerAttachmentsShouldBeFound("customersId.equals=" + customersId);

        // Get all the customerAttachmentsList where customers equals to (customersId + 1)
        defaultCustomerAttachmentsShouldNotBeFound("customersId.equals=" + (customersId + 1));
    }

    @Test
    @Transactional
    void getAllCustomerAttachmentsByStatusAttachmentsIsEqualToSomething() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);
        StatusAttachments statusAttachments = StatusAttachmentsResourceIT.createEntity(em);
        em.persist(statusAttachments);
        em.flush();
        customerAttachments.setStatusAttachments(statusAttachments);
        customerAttachmentsRepository.saveAndFlush(customerAttachments);
        Long statusAttachmentsId = statusAttachments.getId();

        // Get all the customerAttachmentsList where statusAttachments equals to statusAttachmentsId
        defaultCustomerAttachmentsShouldBeFound("statusAttachmentsId.equals=" + statusAttachmentsId);

        // Get all the customerAttachmentsList where statusAttachments equals to (statusAttachmentsId + 1)
        defaultCustomerAttachmentsShouldNotBeFound("statusAttachmentsId.equals=" + (statusAttachmentsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomerAttachmentsShouldBeFound(String filter) throws Exception {
        restCustomerAttachmentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerAttachments.getId().intValue())))
            .andExpect(jsonPath("$.[*].attachImageContentType").value(hasItem(DEFAULT_ATTACH_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACH_IMAGE))))
            .andExpect(jsonPath("$.[*].attachUrl").value(hasItem(DEFAULT_ATTACH_URL)))
            .andExpect(jsonPath("$.[*].attachDescription").value(hasItem(DEFAULT_ATTACH_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].attachedDate").value(hasItem(sameInstant(DEFAULT_ATTACHED_DATE))));

        // Check, that the count call also returns 1
        restCustomerAttachmentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomerAttachmentsShouldNotBeFound(String filter) throws Exception {
        restCustomerAttachmentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomerAttachmentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustomerAttachments() throws Exception {
        // Get the customerAttachments
        restCustomerAttachmentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustomerAttachments() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        int databaseSizeBeforeUpdate = customerAttachmentsRepository.findAll().size();

        // Update the customerAttachments
        CustomerAttachments updatedCustomerAttachments = customerAttachmentsRepository.findById(customerAttachments.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerAttachments are not directly saved in db
        em.detach(updatedCustomerAttachments);
        updatedCustomerAttachments
            .attachImage(UPDATED_ATTACH_IMAGE)
            .attachImageContentType(UPDATED_ATTACH_IMAGE_CONTENT_TYPE)
            .attachUrl(UPDATED_ATTACH_URL)
            .attachDescription(UPDATED_ATTACH_DESCRIPTION)
            .attachedDate(UPDATED_ATTACHED_DATE);
        CustomerAttachmentsDTO customerAttachmentsDTO = customerAttachmentsMapper.toDto(updatedCustomerAttachments);

        restCustomerAttachmentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customerAttachmentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerAttachmentsDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustomerAttachments in the database
        List<CustomerAttachments> customerAttachmentsList = customerAttachmentsRepository.findAll();
        assertThat(customerAttachmentsList).hasSize(databaseSizeBeforeUpdate);
        CustomerAttachments testCustomerAttachments = customerAttachmentsList.get(customerAttachmentsList.size() - 1);
        assertThat(testCustomerAttachments.getAttachImage()).isEqualTo(UPDATED_ATTACH_IMAGE);
        assertThat(testCustomerAttachments.getAttachImageContentType()).isEqualTo(UPDATED_ATTACH_IMAGE_CONTENT_TYPE);
        assertThat(testCustomerAttachments.getAttachUrl()).isEqualTo(UPDATED_ATTACH_URL);
        assertThat(testCustomerAttachments.getAttachDescription()).isEqualTo(UPDATED_ATTACH_DESCRIPTION);
        assertThat(testCustomerAttachments.getAttachedDate()).isEqualTo(UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingCustomerAttachments() throws Exception {
        int databaseSizeBeforeUpdate = customerAttachmentsRepository.findAll().size();
        customerAttachments.setId(count.incrementAndGet());

        // Create the CustomerAttachments
        CustomerAttachmentsDTO customerAttachmentsDTO = customerAttachmentsMapper.toDto(customerAttachments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerAttachmentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customerAttachmentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerAttachments in the database
        List<CustomerAttachments> customerAttachmentsList = customerAttachmentsRepository.findAll();
        assertThat(customerAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomerAttachments() throws Exception {
        int databaseSizeBeforeUpdate = customerAttachmentsRepository.findAll().size();
        customerAttachments.setId(count.incrementAndGet());

        // Create the CustomerAttachments
        CustomerAttachmentsDTO customerAttachmentsDTO = customerAttachmentsMapper.toDto(customerAttachments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerAttachmentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerAttachments in the database
        List<CustomerAttachments> customerAttachmentsList = customerAttachmentsRepository.findAll();
        assertThat(customerAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomerAttachments() throws Exception {
        int databaseSizeBeforeUpdate = customerAttachmentsRepository.findAll().size();
        customerAttachments.setId(count.incrementAndGet());

        // Create the CustomerAttachments
        CustomerAttachmentsDTO customerAttachmentsDTO = customerAttachmentsMapper.toDto(customerAttachments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerAttachmentsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerAttachmentsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomerAttachments in the database
        List<CustomerAttachments> customerAttachmentsList = customerAttachmentsRepository.findAll();
        assertThat(customerAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomerAttachmentsWithPatch() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        int databaseSizeBeforeUpdate = customerAttachmentsRepository.findAll().size();

        // Update the customerAttachments using partial update
        CustomerAttachments partialUpdatedCustomerAttachments = new CustomerAttachments();
        partialUpdatedCustomerAttachments.setId(customerAttachments.getId());

        partialUpdatedCustomerAttachments
            .attachImage(UPDATED_ATTACH_IMAGE)
            .attachImageContentType(UPDATED_ATTACH_IMAGE_CONTENT_TYPE)
            .attachDescription(UPDATED_ATTACH_DESCRIPTION);

        restCustomerAttachmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomerAttachments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerAttachments))
            )
            .andExpect(status().isOk());

        // Validate the CustomerAttachments in the database
        List<CustomerAttachments> customerAttachmentsList = customerAttachmentsRepository.findAll();
        assertThat(customerAttachmentsList).hasSize(databaseSizeBeforeUpdate);
        CustomerAttachments testCustomerAttachments = customerAttachmentsList.get(customerAttachmentsList.size() - 1);
        assertThat(testCustomerAttachments.getAttachImage()).isEqualTo(UPDATED_ATTACH_IMAGE);
        assertThat(testCustomerAttachments.getAttachImageContentType()).isEqualTo(UPDATED_ATTACH_IMAGE_CONTENT_TYPE);
        assertThat(testCustomerAttachments.getAttachUrl()).isEqualTo(DEFAULT_ATTACH_URL);
        assertThat(testCustomerAttachments.getAttachDescription()).isEqualTo(UPDATED_ATTACH_DESCRIPTION);
        assertThat(testCustomerAttachments.getAttachedDate()).isEqualTo(DEFAULT_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateCustomerAttachmentsWithPatch() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        int databaseSizeBeforeUpdate = customerAttachmentsRepository.findAll().size();

        // Update the customerAttachments using partial update
        CustomerAttachments partialUpdatedCustomerAttachments = new CustomerAttachments();
        partialUpdatedCustomerAttachments.setId(customerAttachments.getId());

        partialUpdatedCustomerAttachments
            .attachImage(UPDATED_ATTACH_IMAGE)
            .attachImageContentType(UPDATED_ATTACH_IMAGE_CONTENT_TYPE)
            .attachUrl(UPDATED_ATTACH_URL)
            .attachDescription(UPDATED_ATTACH_DESCRIPTION)
            .attachedDate(UPDATED_ATTACHED_DATE);

        restCustomerAttachmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomerAttachments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerAttachments))
            )
            .andExpect(status().isOk());

        // Validate the CustomerAttachments in the database
        List<CustomerAttachments> customerAttachmentsList = customerAttachmentsRepository.findAll();
        assertThat(customerAttachmentsList).hasSize(databaseSizeBeforeUpdate);
        CustomerAttachments testCustomerAttachments = customerAttachmentsList.get(customerAttachmentsList.size() - 1);
        assertThat(testCustomerAttachments.getAttachImage()).isEqualTo(UPDATED_ATTACH_IMAGE);
        assertThat(testCustomerAttachments.getAttachImageContentType()).isEqualTo(UPDATED_ATTACH_IMAGE_CONTENT_TYPE);
        assertThat(testCustomerAttachments.getAttachUrl()).isEqualTo(UPDATED_ATTACH_URL);
        assertThat(testCustomerAttachments.getAttachDescription()).isEqualTo(UPDATED_ATTACH_DESCRIPTION);
        assertThat(testCustomerAttachments.getAttachedDate()).isEqualTo(UPDATED_ATTACHED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingCustomerAttachments() throws Exception {
        int databaseSizeBeforeUpdate = customerAttachmentsRepository.findAll().size();
        customerAttachments.setId(count.incrementAndGet());

        // Create the CustomerAttachments
        CustomerAttachmentsDTO customerAttachmentsDTO = customerAttachmentsMapper.toDto(customerAttachments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerAttachmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customerAttachmentsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerAttachments in the database
        List<CustomerAttachments> customerAttachmentsList = customerAttachmentsRepository.findAll();
        assertThat(customerAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomerAttachments() throws Exception {
        int databaseSizeBeforeUpdate = customerAttachmentsRepository.findAll().size();
        customerAttachments.setId(count.incrementAndGet());

        // Create the CustomerAttachments
        CustomerAttachmentsDTO customerAttachmentsDTO = customerAttachmentsMapper.toDto(customerAttachments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerAttachmentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerAttachmentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerAttachments in the database
        List<CustomerAttachments> customerAttachmentsList = customerAttachmentsRepository.findAll();
        assertThat(customerAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomerAttachments() throws Exception {
        int databaseSizeBeforeUpdate = customerAttachmentsRepository.findAll().size();
        customerAttachments.setId(count.incrementAndGet());

        // Create the CustomerAttachments
        CustomerAttachmentsDTO customerAttachmentsDTO = customerAttachmentsMapper.toDto(customerAttachments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerAttachmentsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerAttachmentsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomerAttachments in the database
        List<CustomerAttachments> customerAttachmentsList = customerAttachmentsRepository.findAll();
        assertThat(customerAttachmentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomerAttachments() throws Exception {
        // Initialize the database
        customerAttachmentsRepository.saveAndFlush(customerAttachments);

        int databaseSizeBeforeDelete = customerAttachmentsRepository.findAll().size();

        // Delete the customerAttachments
        restCustomerAttachmentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, customerAttachments.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerAttachments> customerAttachmentsList = customerAttachmentsRepository.findAll();
        assertThat(customerAttachmentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
