package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Customers;
import com.genesisoft.transporte.domain.CustomersContacts;
import com.genesisoft.transporte.repository.CustomersContactsRepository;
import com.genesisoft.transporte.service.criteria.CustomersContactsCriteria;
import com.genesisoft.transporte.service.dto.CustomersContactsDTO;
import com.genesisoft.transporte.service.mapper.CustomersContactsMapper;
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
 * Integration tests for the {@link CustomersContactsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomersContactsResourceIT {

    private static final String DEFAULT_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/customers-contacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomersContactsRepository customersContactsRepository;

    @Autowired
    private CustomersContactsMapper customersContactsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomersContactsMockMvc;

    private CustomersContacts customersContacts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomersContacts createEntity(EntityManager em) {
        CustomersContacts customersContacts = new CustomersContacts()
            .contactName(DEFAULT_CONTACT_NAME)
            .contactTelephone(DEFAULT_CONTACT_TELEPHONE)
            .contactEmail(DEFAULT_CONTACT_EMAIL);
        // Add required entity
        Customers customers;
        if (TestUtil.findAll(em, Customers.class).isEmpty()) {
            customers = CustomersResourceIT.createEntity(em);
            em.persist(customers);
            em.flush();
        } else {
            customers = TestUtil.findAll(em, Customers.class).get(0);
        }
        customersContacts.setCustomers(customers);
        return customersContacts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomersContacts createUpdatedEntity(EntityManager em) {
        CustomersContacts customersContacts = new CustomersContacts()
            .contactName(UPDATED_CONTACT_NAME)
            .contactTelephone(UPDATED_CONTACT_TELEPHONE)
            .contactEmail(UPDATED_CONTACT_EMAIL);
        // Add required entity
        Customers customers;
        if (TestUtil.findAll(em, Customers.class).isEmpty()) {
            customers = CustomersResourceIT.createUpdatedEntity(em);
            em.persist(customers);
            em.flush();
        } else {
            customers = TestUtil.findAll(em, Customers.class).get(0);
        }
        customersContacts.setCustomers(customers);
        return customersContacts;
    }

    @BeforeEach
    public void initTest() {
        customersContacts = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomersContacts() throws Exception {
        int databaseSizeBeforeCreate = customersContactsRepository.findAll().size();
        // Create the CustomersContacts
        CustomersContactsDTO customersContactsDTO = customersContactsMapper.toDto(customersContacts);
        restCustomersContactsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customersContactsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustomersContacts in the database
        List<CustomersContacts> customersContactsList = customersContactsRepository.findAll();
        assertThat(customersContactsList).hasSize(databaseSizeBeforeCreate + 1);
        CustomersContacts testCustomersContacts = customersContactsList.get(customersContactsList.size() - 1);
        assertThat(testCustomersContacts.getContactName()).isEqualTo(DEFAULT_CONTACT_NAME);
        assertThat(testCustomersContacts.getContactTelephone()).isEqualTo(DEFAULT_CONTACT_TELEPHONE);
        assertThat(testCustomersContacts.getContactEmail()).isEqualTo(DEFAULT_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void createCustomersContactsWithExistingId() throws Exception {
        // Create the CustomersContacts with an existing ID
        customersContacts.setId(1L);
        CustomersContactsDTO customersContactsDTO = customersContactsMapper.toDto(customersContacts);

        int databaseSizeBeforeCreate = customersContactsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomersContactsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customersContactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomersContacts in the database
        List<CustomersContacts> customersContactsList = customersContactsRepository.findAll();
        assertThat(customersContactsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkContactNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customersContactsRepository.findAll().size();
        // set the field null
        customersContacts.setContactName(null);

        // Create the CustomersContacts, which fails.
        CustomersContactsDTO customersContactsDTO = customersContactsMapper.toDto(customersContacts);

        restCustomersContactsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customersContactsDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustomersContacts> customersContactsList = customersContactsRepository.findAll();
        assertThat(customersContactsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = customersContactsRepository.findAll().size();
        // set the field null
        customersContacts.setContactTelephone(null);

        // Create the CustomersContacts, which fails.
        CustomersContactsDTO customersContactsDTO = customersContactsMapper.toDto(customersContacts);

        restCustomersContactsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customersContactsDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustomersContacts> customersContactsList = customersContactsRepository.findAll();
        assertThat(customersContactsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustomersContacts() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        // Get all the customersContactsList
        restCustomersContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customersContacts.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].contactTelephone").value(hasItem(DEFAULT_CONTACT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL)));
    }

    @Test
    @Transactional
    void getCustomersContacts() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        // Get the customersContacts
        restCustomersContactsMockMvc
            .perform(get(ENTITY_API_URL_ID, customersContacts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customersContacts.getId().intValue()))
            .andExpect(jsonPath("$.contactName").value(DEFAULT_CONTACT_NAME))
            .andExpect(jsonPath("$.contactTelephone").value(DEFAULT_CONTACT_TELEPHONE))
            .andExpect(jsonPath("$.contactEmail").value(DEFAULT_CONTACT_EMAIL));
    }

    @Test
    @Transactional
    void getCustomersContactsByIdFiltering() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        Long id = customersContacts.getId();

        defaultCustomersContactsShouldBeFound("id.equals=" + id);
        defaultCustomersContactsShouldNotBeFound("id.notEquals=" + id);

        defaultCustomersContactsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomersContactsShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomersContactsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomersContactsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustomersContactsByContactNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        // Get all the customersContactsList where contactName equals to DEFAULT_CONTACT_NAME
        defaultCustomersContactsShouldBeFound("contactName.equals=" + DEFAULT_CONTACT_NAME);

        // Get all the customersContactsList where contactName equals to UPDATED_CONTACT_NAME
        defaultCustomersContactsShouldNotBeFound("contactName.equals=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersContactsByContactNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        // Get all the customersContactsList where contactName not equals to DEFAULT_CONTACT_NAME
        defaultCustomersContactsShouldNotBeFound("contactName.notEquals=" + DEFAULT_CONTACT_NAME);

        // Get all the customersContactsList where contactName not equals to UPDATED_CONTACT_NAME
        defaultCustomersContactsShouldBeFound("contactName.notEquals=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersContactsByContactNameIsInShouldWork() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        // Get all the customersContactsList where contactName in DEFAULT_CONTACT_NAME or UPDATED_CONTACT_NAME
        defaultCustomersContactsShouldBeFound("contactName.in=" + DEFAULT_CONTACT_NAME + "," + UPDATED_CONTACT_NAME);

        // Get all the customersContactsList where contactName equals to UPDATED_CONTACT_NAME
        defaultCustomersContactsShouldNotBeFound("contactName.in=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersContactsByContactNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        // Get all the customersContactsList where contactName is not null
        defaultCustomersContactsShouldBeFound("contactName.specified=true");

        // Get all the customersContactsList where contactName is null
        defaultCustomersContactsShouldNotBeFound("contactName.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersContactsByContactNameContainsSomething() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        // Get all the customersContactsList where contactName contains DEFAULT_CONTACT_NAME
        defaultCustomersContactsShouldBeFound("contactName.contains=" + DEFAULT_CONTACT_NAME);

        // Get all the customersContactsList where contactName contains UPDATED_CONTACT_NAME
        defaultCustomersContactsShouldNotBeFound("contactName.contains=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersContactsByContactNameNotContainsSomething() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        // Get all the customersContactsList where contactName does not contain DEFAULT_CONTACT_NAME
        defaultCustomersContactsShouldNotBeFound("contactName.doesNotContain=" + DEFAULT_CONTACT_NAME);

        // Get all the customersContactsList where contactName does not contain UPDATED_CONTACT_NAME
        defaultCustomersContactsShouldBeFound("contactName.doesNotContain=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersContactsByContactTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        // Get all the customersContactsList where contactTelephone equals to DEFAULT_CONTACT_TELEPHONE
        defaultCustomersContactsShouldBeFound("contactTelephone.equals=" + DEFAULT_CONTACT_TELEPHONE);

        // Get all the customersContactsList where contactTelephone equals to UPDATED_CONTACT_TELEPHONE
        defaultCustomersContactsShouldNotBeFound("contactTelephone.equals=" + UPDATED_CONTACT_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllCustomersContactsByContactTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        // Get all the customersContactsList where contactTelephone not equals to DEFAULT_CONTACT_TELEPHONE
        defaultCustomersContactsShouldNotBeFound("contactTelephone.notEquals=" + DEFAULT_CONTACT_TELEPHONE);

        // Get all the customersContactsList where contactTelephone not equals to UPDATED_CONTACT_TELEPHONE
        defaultCustomersContactsShouldBeFound("contactTelephone.notEquals=" + UPDATED_CONTACT_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllCustomersContactsByContactTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        // Get all the customersContactsList where contactTelephone in DEFAULT_CONTACT_TELEPHONE or UPDATED_CONTACT_TELEPHONE
        defaultCustomersContactsShouldBeFound("contactTelephone.in=" + DEFAULT_CONTACT_TELEPHONE + "," + UPDATED_CONTACT_TELEPHONE);

        // Get all the customersContactsList where contactTelephone equals to UPDATED_CONTACT_TELEPHONE
        defaultCustomersContactsShouldNotBeFound("contactTelephone.in=" + UPDATED_CONTACT_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllCustomersContactsByContactTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        // Get all the customersContactsList where contactTelephone is not null
        defaultCustomersContactsShouldBeFound("contactTelephone.specified=true");

        // Get all the customersContactsList where contactTelephone is null
        defaultCustomersContactsShouldNotBeFound("contactTelephone.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersContactsByContactTelephoneContainsSomething() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        // Get all the customersContactsList where contactTelephone contains DEFAULT_CONTACT_TELEPHONE
        defaultCustomersContactsShouldBeFound("contactTelephone.contains=" + DEFAULT_CONTACT_TELEPHONE);

        // Get all the customersContactsList where contactTelephone contains UPDATED_CONTACT_TELEPHONE
        defaultCustomersContactsShouldNotBeFound("contactTelephone.contains=" + UPDATED_CONTACT_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllCustomersContactsByContactTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        // Get all the customersContactsList where contactTelephone does not contain DEFAULT_CONTACT_TELEPHONE
        defaultCustomersContactsShouldNotBeFound("contactTelephone.doesNotContain=" + DEFAULT_CONTACT_TELEPHONE);

        // Get all the customersContactsList where contactTelephone does not contain UPDATED_CONTACT_TELEPHONE
        defaultCustomersContactsShouldBeFound("contactTelephone.doesNotContain=" + UPDATED_CONTACT_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllCustomersContactsByContactEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        // Get all the customersContactsList where contactEmail equals to DEFAULT_CONTACT_EMAIL
        defaultCustomersContactsShouldBeFound("contactEmail.equals=" + DEFAULT_CONTACT_EMAIL);

        // Get all the customersContactsList where contactEmail equals to UPDATED_CONTACT_EMAIL
        defaultCustomersContactsShouldNotBeFound("contactEmail.equals=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomersContactsByContactEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        // Get all the customersContactsList where contactEmail not equals to DEFAULT_CONTACT_EMAIL
        defaultCustomersContactsShouldNotBeFound("contactEmail.notEquals=" + DEFAULT_CONTACT_EMAIL);

        // Get all the customersContactsList where contactEmail not equals to UPDATED_CONTACT_EMAIL
        defaultCustomersContactsShouldBeFound("contactEmail.notEquals=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomersContactsByContactEmailIsInShouldWork() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        // Get all the customersContactsList where contactEmail in DEFAULT_CONTACT_EMAIL or UPDATED_CONTACT_EMAIL
        defaultCustomersContactsShouldBeFound("contactEmail.in=" + DEFAULT_CONTACT_EMAIL + "," + UPDATED_CONTACT_EMAIL);

        // Get all the customersContactsList where contactEmail equals to UPDATED_CONTACT_EMAIL
        defaultCustomersContactsShouldNotBeFound("contactEmail.in=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomersContactsByContactEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        // Get all the customersContactsList where contactEmail is not null
        defaultCustomersContactsShouldBeFound("contactEmail.specified=true");

        // Get all the customersContactsList where contactEmail is null
        defaultCustomersContactsShouldNotBeFound("contactEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersContactsByContactEmailContainsSomething() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        // Get all the customersContactsList where contactEmail contains DEFAULT_CONTACT_EMAIL
        defaultCustomersContactsShouldBeFound("contactEmail.contains=" + DEFAULT_CONTACT_EMAIL);

        // Get all the customersContactsList where contactEmail contains UPDATED_CONTACT_EMAIL
        defaultCustomersContactsShouldNotBeFound("contactEmail.contains=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomersContactsByContactEmailNotContainsSomething() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        // Get all the customersContactsList where contactEmail does not contain DEFAULT_CONTACT_EMAIL
        defaultCustomersContactsShouldNotBeFound("contactEmail.doesNotContain=" + DEFAULT_CONTACT_EMAIL);

        // Get all the customersContactsList where contactEmail does not contain UPDATED_CONTACT_EMAIL
        defaultCustomersContactsShouldBeFound("contactEmail.doesNotContain=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomersContactsByCustomersIsEqualToSomething() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);
        Customers customers = CustomersResourceIT.createEntity(em);
        em.persist(customers);
        em.flush();
        customersContacts.setCustomers(customers);
        customersContactsRepository.saveAndFlush(customersContacts);
        Long customersId = customers.getId();

        // Get all the customersContactsList where customers equals to customersId
        defaultCustomersContactsShouldBeFound("customersId.equals=" + customersId);

        // Get all the customersContactsList where customers equals to (customersId + 1)
        defaultCustomersContactsShouldNotBeFound("customersId.equals=" + (customersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomersContactsShouldBeFound(String filter) throws Exception {
        restCustomersContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customersContacts.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].contactTelephone").value(hasItem(DEFAULT_CONTACT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL)));

        // Check, that the count call also returns 1
        restCustomersContactsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomersContactsShouldNotBeFound(String filter) throws Exception {
        restCustomersContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomersContactsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustomersContacts() throws Exception {
        // Get the customersContacts
        restCustomersContactsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustomersContacts() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        int databaseSizeBeforeUpdate = customersContactsRepository.findAll().size();

        // Update the customersContacts
        CustomersContacts updatedCustomersContacts = customersContactsRepository.findById(customersContacts.getId()).get();
        // Disconnect from session so that the updates on updatedCustomersContacts are not directly saved in db
        em.detach(updatedCustomersContacts);
        updatedCustomersContacts
            .contactName(UPDATED_CONTACT_NAME)
            .contactTelephone(UPDATED_CONTACT_TELEPHONE)
            .contactEmail(UPDATED_CONTACT_EMAIL);
        CustomersContactsDTO customersContactsDTO = customersContactsMapper.toDto(updatedCustomersContacts);

        restCustomersContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customersContactsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customersContactsDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustomersContacts in the database
        List<CustomersContacts> customersContactsList = customersContactsRepository.findAll();
        assertThat(customersContactsList).hasSize(databaseSizeBeforeUpdate);
        CustomersContacts testCustomersContacts = customersContactsList.get(customersContactsList.size() - 1);
        assertThat(testCustomersContacts.getContactName()).isEqualTo(UPDATED_CONTACT_NAME);
        assertThat(testCustomersContacts.getContactTelephone()).isEqualTo(UPDATED_CONTACT_TELEPHONE);
        assertThat(testCustomersContacts.getContactEmail()).isEqualTo(UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingCustomersContacts() throws Exception {
        int databaseSizeBeforeUpdate = customersContactsRepository.findAll().size();
        customersContacts.setId(count.incrementAndGet());

        // Create the CustomersContacts
        CustomersContactsDTO customersContactsDTO = customersContactsMapper.toDto(customersContacts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomersContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customersContactsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customersContactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomersContacts in the database
        List<CustomersContacts> customersContactsList = customersContactsRepository.findAll();
        assertThat(customersContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomersContacts() throws Exception {
        int databaseSizeBeforeUpdate = customersContactsRepository.findAll().size();
        customersContacts.setId(count.incrementAndGet());

        // Create the CustomersContacts
        CustomersContactsDTO customersContactsDTO = customersContactsMapper.toDto(customersContacts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomersContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customersContactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomersContacts in the database
        List<CustomersContacts> customersContactsList = customersContactsRepository.findAll();
        assertThat(customersContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomersContacts() throws Exception {
        int databaseSizeBeforeUpdate = customersContactsRepository.findAll().size();
        customersContacts.setId(count.incrementAndGet());

        // Create the CustomersContacts
        CustomersContactsDTO customersContactsDTO = customersContactsMapper.toDto(customersContacts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomersContactsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customersContactsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomersContacts in the database
        List<CustomersContacts> customersContactsList = customersContactsRepository.findAll();
        assertThat(customersContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomersContactsWithPatch() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        int databaseSizeBeforeUpdate = customersContactsRepository.findAll().size();

        // Update the customersContacts using partial update
        CustomersContacts partialUpdatedCustomersContacts = new CustomersContacts();
        partialUpdatedCustomersContacts.setId(customersContacts.getId());

        restCustomersContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomersContacts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomersContacts))
            )
            .andExpect(status().isOk());

        // Validate the CustomersContacts in the database
        List<CustomersContacts> customersContactsList = customersContactsRepository.findAll();
        assertThat(customersContactsList).hasSize(databaseSizeBeforeUpdate);
        CustomersContacts testCustomersContacts = customersContactsList.get(customersContactsList.size() - 1);
        assertThat(testCustomersContacts.getContactName()).isEqualTo(DEFAULT_CONTACT_NAME);
        assertThat(testCustomersContacts.getContactTelephone()).isEqualTo(DEFAULT_CONTACT_TELEPHONE);
        assertThat(testCustomersContacts.getContactEmail()).isEqualTo(DEFAULT_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateCustomersContactsWithPatch() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        int databaseSizeBeforeUpdate = customersContactsRepository.findAll().size();

        // Update the customersContacts using partial update
        CustomersContacts partialUpdatedCustomersContacts = new CustomersContacts();
        partialUpdatedCustomersContacts.setId(customersContacts.getId());

        partialUpdatedCustomersContacts
            .contactName(UPDATED_CONTACT_NAME)
            .contactTelephone(UPDATED_CONTACT_TELEPHONE)
            .contactEmail(UPDATED_CONTACT_EMAIL);

        restCustomersContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomersContacts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomersContacts))
            )
            .andExpect(status().isOk());

        // Validate the CustomersContacts in the database
        List<CustomersContacts> customersContactsList = customersContactsRepository.findAll();
        assertThat(customersContactsList).hasSize(databaseSizeBeforeUpdate);
        CustomersContacts testCustomersContacts = customersContactsList.get(customersContactsList.size() - 1);
        assertThat(testCustomersContacts.getContactName()).isEqualTo(UPDATED_CONTACT_NAME);
        assertThat(testCustomersContacts.getContactTelephone()).isEqualTo(UPDATED_CONTACT_TELEPHONE);
        assertThat(testCustomersContacts.getContactEmail()).isEqualTo(UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingCustomersContacts() throws Exception {
        int databaseSizeBeforeUpdate = customersContactsRepository.findAll().size();
        customersContacts.setId(count.incrementAndGet());

        // Create the CustomersContacts
        CustomersContactsDTO customersContactsDTO = customersContactsMapper.toDto(customersContacts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomersContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customersContactsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customersContactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomersContacts in the database
        List<CustomersContacts> customersContactsList = customersContactsRepository.findAll();
        assertThat(customersContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomersContacts() throws Exception {
        int databaseSizeBeforeUpdate = customersContactsRepository.findAll().size();
        customersContacts.setId(count.incrementAndGet());

        // Create the CustomersContacts
        CustomersContactsDTO customersContactsDTO = customersContactsMapper.toDto(customersContacts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomersContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customersContactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomersContacts in the database
        List<CustomersContacts> customersContactsList = customersContactsRepository.findAll();
        assertThat(customersContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomersContacts() throws Exception {
        int databaseSizeBeforeUpdate = customersContactsRepository.findAll().size();
        customersContacts.setId(count.incrementAndGet());

        // Create the CustomersContacts
        CustomersContactsDTO customersContactsDTO = customersContactsMapper.toDto(customersContacts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomersContactsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customersContactsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomersContacts in the database
        List<CustomersContacts> customersContactsList = customersContactsRepository.findAll();
        assertThat(customersContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomersContacts() throws Exception {
        // Initialize the database
        customersContactsRepository.saveAndFlush(customersContacts);

        int databaseSizeBeforeDelete = customersContactsRepository.findAll().size();

        // Delete the customersContacts
        restCustomersContactsMockMvc
            .perform(delete(ENTITY_API_URL_ID, customersContacts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomersContacts> customersContactsList = customersContactsRepository.findAll();
        assertThat(customersContactsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
