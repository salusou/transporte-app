package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.SupplierContacts;
import com.genesisoft.transporte.domain.Suppliers;
import com.genesisoft.transporte.repository.SupplierContactsRepository;
import com.genesisoft.transporte.service.criteria.SupplierContactsCriteria;
import com.genesisoft.transporte.service.dto.SupplierContactsDTO;
import com.genesisoft.transporte.service.mapper.SupplierContactsMapper;
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
 * Integration tests for the {@link SupplierContactsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SupplierContactsResourceIT {

    private static final String DEFAULT_SUPPLIER_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_CONTACT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_CONTACT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_CONTACT_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_CONTACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_CONTACT_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/supplier-contacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SupplierContactsRepository supplierContactsRepository;

    @Autowired
    private SupplierContactsMapper supplierContactsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupplierContactsMockMvc;

    private SupplierContacts supplierContacts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupplierContacts createEntity(EntityManager em) {
        SupplierContacts supplierContacts = new SupplierContacts()
            .supplierContactName(DEFAULT_SUPPLIER_CONTACT_NAME)
            .supplierContactPhone(DEFAULT_SUPPLIER_CONTACT_PHONE)
            .supplierContactEmail(DEFAULT_SUPPLIER_CONTACT_EMAIL);
        // Add required entity
        Suppliers suppliers;
        if (TestUtil.findAll(em, Suppliers.class).isEmpty()) {
            suppliers = SuppliersResourceIT.createEntity(em);
            em.persist(suppliers);
            em.flush();
        } else {
            suppliers = TestUtil.findAll(em, Suppliers.class).get(0);
        }
        supplierContacts.setSuppliers(suppliers);
        return supplierContacts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupplierContacts createUpdatedEntity(EntityManager em) {
        SupplierContacts supplierContacts = new SupplierContacts()
            .supplierContactName(UPDATED_SUPPLIER_CONTACT_NAME)
            .supplierContactPhone(UPDATED_SUPPLIER_CONTACT_PHONE)
            .supplierContactEmail(UPDATED_SUPPLIER_CONTACT_EMAIL);
        // Add required entity
        Suppliers suppliers;
        if (TestUtil.findAll(em, Suppliers.class).isEmpty()) {
            suppliers = SuppliersResourceIT.createUpdatedEntity(em);
            em.persist(suppliers);
            em.flush();
        } else {
            suppliers = TestUtil.findAll(em, Suppliers.class).get(0);
        }
        supplierContacts.setSuppliers(suppliers);
        return supplierContacts;
    }

    @BeforeEach
    public void initTest() {
        supplierContacts = createEntity(em);
    }

    @Test
    @Transactional
    void createSupplierContacts() throws Exception {
        int databaseSizeBeforeCreate = supplierContactsRepository.findAll().size();
        // Create the SupplierContacts
        SupplierContactsDTO supplierContactsDTO = supplierContactsMapper.toDto(supplierContacts);
        restSupplierContactsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplierContactsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SupplierContacts in the database
        List<SupplierContacts> supplierContactsList = supplierContactsRepository.findAll();
        assertThat(supplierContactsList).hasSize(databaseSizeBeforeCreate + 1);
        SupplierContacts testSupplierContacts = supplierContactsList.get(supplierContactsList.size() - 1);
        assertThat(testSupplierContacts.getSupplierContactName()).isEqualTo(DEFAULT_SUPPLIER_CONTACT_NAME);
        assertThat(testSupplierContacts.getSupplierContactPhone()).isEqualTo(DEFAULT_SUPPLIER_CONTACT_PHONE);
        assertThat(testSupplierContacts.getSupplierContactEmail()).isEqualTo(DEFAULT_SUPPLIER_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void createSupplierContactsWithExistingId() throws Exception {
        // Create the SupplierContacts with an existing ID
        supplierContacts.setId(1L);
        SupplierContactsDTO supplierContactsDTO = supplierContactsMapper.toDto(supplierContacts);

        int databaseSizeBeforeCreate = supplierContactsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplierContactsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplierContactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplierContacts in the database
        List<SupplierContacts> supplierContactsList = supplierContactsRepository.findAll();
        assertThat(supplierContactsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSupplierContactNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierContactsRepository.findAll().size();
        // set the field null
        supplierContacts.setSupplierContactName(null);

        // Create the SupplierContacts, which fails.
        SupplierContactsDTO supplierContactsDTO = supplierContactsMapper.toDto(supplierContacts);

        restSupplierContactsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplierContactsDTO))
            )
            .andExpect(status().isBadRequest());

        List<SupplierContacts> supplierContactsList = supplierContactsRepository.findAll();
        assertThat(supplierContactsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSupplierContacts() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        // Get all the supplierContactsList
        restSupplierContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierContacts.getId().intValue())))
            .andExpect(jsonPath("$.[*].supplierContactName").value(hasItem(DEFAULT_SUPPLIER_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].supplierContactPhone").value(hasItem(DEFAULT_SUPPLIER_CONTACT_PHONE)))
            .andExpect(jsonPath("$.[*].supplierContactEmail").value(hasItem(DEFAULT_SUPPLIER_CONTACT_EMAIL)));
    }

    @Test
    @Transactional
    void getSupplierContacts() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        // Get the supplierContacts
        restSupplierContactsMockMvc
            .perform(get(ENTITY_API_URL_ID, supplierContacts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supplierContacts.getId().intValue()))
            .andExpect(jsonPath("$.supplierContactName").value(DEFAULT_SUPPLIER_CONTACT_NAME))
            .andExpect(jsonPath("$.supplierContactPhone").value(DEFAULT_SUPPLIER_CONTACT_PHONE))
            .andExpect(jsonPath("$.supplierContactEmail").value(DEFAULT_SUPPLIER_CONTACT_EMAIL));
    }

    @Test
    @Transactional
    void getSupplierContactsByIdFiltering() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        Long id = supplierContacts.getId();

        defaultSupplierContactsShouldBeFound("id.equals=" + id);
        defaultSupplierContactsShouldNotBeFound("id.notEquals=" + id);

        defaultSupplierContactsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSupplierContactsShouldNotBeFound("id.greaterThan=" + id);

        defaultSupplierContactsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSupplierContactsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSupplierContactsBySupplierContactNameIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        // Get all the supplierContactsList where supplierContactName equals to DEFAULT_SUPPLIER_CONTACT_NAME
        defaultSupplierContactsShouldBeFound("supplierContactName.equals=" + DEFAULT_SUPPLIER_CONTACT_NAME);

        // Get all the supplierContactsList where supplierContactName equals to UPDATED_SUPPLIER_CONTACT_NAME
        defaultSupplierContactsShouldNotBeFound("supplierContactName.equals=" + UPDATED_SUPPLIER_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllSupplierContactsBySupplierContactNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        // Get all the supplierContactsList where supplierContactName not equals to DEFAULT_SUPPLIER_CONTACT_NAME
        defaultSupplierContactsShouldNotBeFound("supplierContactName.notEquals=" + DEFAULT_SUPPLIER_CONTACT_NAME);

        // Get all the supplierContactsList where supplierContactName not equals to UPDATED_SUPPLIER_CONTACT_NAME
        defaultSupplierContactsShouldBeFound("supplierContactName.notEquals=" + UPDATED_SUPPLIER_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllSupplierContactsBySupplierContactNameIsInShouldWork() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        // Get all the supplierContactsList where supplierContactName in DEFAULT_SUPPLIER_CONTACT_NAME or UPDATED_SUPPLIER_CONTACT_NAME
        defaultSupplierContactsShouldBeFound(
            "supplierContactName.in=" + DEFAULT_SUPPLIER_CONTACT_NAME + "," + UPDATED_SUPPLIER_CONTACT_NAME
        );

        // Get all the supplierContactsList where supplierContactName equals to UPDATED_SUPPLIER_CONTACT_NAME
        defaultSupplierContactsShouldNotBeFound("supplierContactName.in=" + UPDATED_SUPPLIER_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllSupplierContactsBySupplierContactNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        // Get all the supplierContactsList where supplierContactName is not null
        defaultSupplierContactsShouldBeFound("supplierContactName.specified=true");

        // Get all the supplierContactsList where supplierContactName is null
        defaultSupplierContactsShouldNotBeFound("supplierContactName.specified=false");
    }

    @Test
    @Transactional
    void getAllSupplierContactsBySupplierContactNameContainsSomething() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        // Get all the supplierContactsList where supplierContactName contains DEFAULT_SUPPLIER_CONTACT_NAME
        defaultSupplierContactsShouldBeFound("supplierContactName.contains=" + DEFAULT_SUPPLIER_CONTACT_NAME);

        // Get all the supplierContactsList where supplierContactName contains UPDATED_SUPPLIER_CONTACT_NAME
        defaultSupplierContactsShouldNotBeFound("supplierContactName.contains=" + UPDATED_SUPPLIER_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllSupplierContactsBySupplierContactNameNotContainsSomething() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        // Get all the supplierContactsList where supplierContactName does not contain DEFAULT_SUPPLIER_CONTACT_NAME
        defaultSupplierContactsShouldNotBeFound("supplierContactName.doesNotContain=" + DEFAULT_SUPPLIER_CONTACT_NAME);

        // Get all the supplierContactsList where supplierContactName does not contain UPDATED_SUPPLIER_CONTACT_NAME
        defaultSupplierContactsShouldBeFound("supplierContactName.doesNotContain=" + UPDATED_SUPPLIER_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllSupplierContactsBySupplierContactPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        // Get all the supplierContactsList where supplierContactPhone equals to DEFAULT_SUPPLIER_CONTACT_PHONE
        defaultSupplierContactsShouldBeFound("supplierContactPhone.equals=" + DEFAULT_SUPPLIER_CONTACT_PHONE);

        // Get all the supplierContactsList where supplierContactPhone equals to UPDATED_SUPPLIER_CONTACT_PHONE
        defaultSupplierContactsShouldNotBeFound("supplierContactPhone.equals=" + UPDATED_SUPPLIER_CONTACT_PHONE);
    }

    @Test
    @Transactional
    void getAllSupplierContactsBySupplierContactPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        // Get all the supplierContactsList where supplierContactPhone not equals to DEFAULT_SUPPLIER_CONTACT_PHONE
        defaultSupplierContactsShouldNotBeFound("supplierContactPhone.notEquals=" + DEFAULT_SUPPLIER_CONTACT_PHONE);

        // Get all the supplierContactsList where supplierContactPhone not equals to UPDATED_SUPPLIER_CONTACT_PHONE
        defaultSupplierContactsShouldBeFound("supplierContactPhone.notEquals=" + UPDATED_SUPPLIER_CONTACT_PHONE);
    }

    @Test
    @Transactional
    void getAllSupplierContactsBySupplierContactPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        // Get all the supplierContactsList where supplierContactPhone in DEFAULT_SUPPLIER_CONTACT_PHONE or UPDATED_SUPPLIER_CONTACT_PHONE
        defaultSupplierContactsShouldBeFound(
            "supplierContactPhone.in=" + DEFAULT_SUPPLIER_CONTACT_PHONE + "," + UPDATED_SUPPLIER_CONTACT_PHONE
        );

        // Get all the supplierContactsList where supplierContactPhone equals to UPDATED_SUPPLIER_CONTACT_PHONE
        defaultSupplierContactsShouldNotBeFound("supplierContactPhone.in=" + UPDATED_SUPPLIER_CONTACT_PHONE);
    }

    @Test
    @Transactional
    void getAllSupplierContactsBySupplierContactPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        // Get all the supplierContactsList where supplierContactPhone is not null
        defaultSupplierContactsShouldBeFound("supplierContactPhone.specified=true");

        // Get all the supplierContactsList where supplierContactPhone is null
        defaultSupplierContactsShouldNotBeFound("supplierContactPhone.specified=false");
    }

    @Test
    @Transactional
    void getAllSupplierContactsBySupplierContactPhoneContainsSomething() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        // Get all the supplierContactsList where supplierContactPhone contains DEFAULT_SUPPLIER_CONTACT_PHONE
        defaultSupplierContactsShouldBeFound("supplierContactPhone.contains=" + DEFAULT_SUPPLIER_CONTACT_PHONE);

        // Get all the supplierContactsList where supplierContactPhone contains UPDATED_SUPPLIER_CONTACT_PHONE
        defaultSupplierContactsShouldNotBeFound("supplierContactPhone.contains=" + UPDATED_SUPPLIER_CONTACT_PHONE);
    }

    @Test
    @Transactional
    void getAllSupplierContactsBySupplierContactPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        // Get all the supplierContactsList where supplierContactPhone does not contain DEFAULT_SUPPLIER_CONTACT_PHONE
        defaultSupplierContactsShouldNotBeFound("supplierContactPhone.doesNotContain=" + DEFAULT_SUPPLIER_CONTACT_PHONE);

        // Get all the supplierContactsList where supplierContactPhone does not contain UPDATED_SUPPLIER_CONTACT_PHONE
        defaultSupplierContactsShouldBeFound("supplierContactPhone.doesNotContain=" + UPDATED_SUPPLIER_CONTACT_PHONE);
    }

    @Test
    @Transactional
    void getAllSupplierContactsBySupplierContactEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        // Get all the supplierContactsList where supplierContactEmail equals to DEFAULT_SUPPLIER_CONTACT_EMAIL
        defaultSupplierContactsShouldBeFound("supplierContactEmail.equals=" + DEFAULT_SUPPLIER_CONTACT_EMAIL);

        // Get all the supplierContactsList where supplierContactEmail equals to UPDATED_SUPPLIER_CONTACT_EMAIL
        defaultSupplierContactsShouldNotBeFound("supplierContactEmail.equals=" + UPDATED_SUPPLIER_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void getAllSupplierContactsBySupplierContactEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        // Get all the supplierContactsList where supplierContactEmail not equals to DEFAULT_SUPPLIER_CONTACT_EMAIL
        defaultSupplierContactsShouldNotBeFound("supplierContactEmail.notEquals=" + DEFAULT_SUPPLIER_CONTACT_EMAIL);

        // Get all the supplierContactsList where supplierContactEmail not equals to UPDATED_SUPPLIER_CONTACT_EMAIL
        defaultSupplierContactsShouldBeFound("supplierContactEmail.notEquals=" + UPDATED_SUPPLIER_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void getAllSupplierContactsBySupplierContactEmailIsInShouldWork() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        // Get all the supplierContactsList where supplierContactEmail in DEFAULT_SUPPLIER_CONTACT_EMAIL or UPDATED_SUPPLIER_CONTACT_EMAIL
        defaultSupplierContactsShouldBeFound(
            "supplierContactEmail.in=" + DEFAULT_SUPPLIER_CONTACT_EMAIL + "," + UPDATED_SUPPLIER_CONTACT_EMAIL
        );

        // Get all the supplierContactsList where supplierContactEmail equals to UPDATED_SUPPLIER_CONTACT_EMAIL
        defaultSupplierContactsShouldNotBeFound("supplierContactEmail.in=" + UPDATED_SUPPLIER_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void getAllSupplierContactsBySupplierContactEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        // Get all the supplierContactsList where supplierContactEmail is not null
        defaultSupplierContactsShouldBeFound("supplierContactEmail.specified=true");

        // Get all the supplierContactsList where supplierContactEmail is null
        defaultSupplierContactsShouldNotBeFound("supplierContactEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllSupplierContactsBySupplierContactEmailContainsSomething() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        // Get all the supplierContactsList where supplierContactEmail contains DEFAULT_SUPPLIER_CONTACT_EMAIL
        defaultSupplierContactsShouldBeFound("supplierContactEmail.contains=" + DEFAULT_SUPPLIER_CONTACT_EMAIL);

        // Get all the supplierContactsList where supplierContactEmail contains UPDATED_SUPPLIER_CONTACT_EMAIL
        defaultSupplierContactsShouldNotBeFound("supplierContactEmail.contains=" + UPDATED_SUPPLIER_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void getAllSupplierContactsBySupplierContactEmailNotContainsSomething() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        // Get all the supplierContactsList where supplierContactEmail does not contain DEFAULT_SUPPLIER_CONTACT_EMAIL
        defaultSupplierContactsShouldNotBeFound("supplierContactEmail.doesNotContain=" + DEFAULT_SUPPLIER_CONTACT_EMAIL);

        // Get all the supplierContactsList where supplierContactEmail does not contain UPDATED_SUPPLIER_CONTACT_EMAIL
        defaultSupplierContactsShouldBeFound("supplierContactEmail.doesNotContain=" + UPDATED_SUPPLIER_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void getAllSupplierContactsBySuppliersIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);
        Suppliers suppliers = SuppliersResourceIT.createEntity(em);
        em.persist(suppliers);
        em.flush();
        supplierContacts.setSuppliers(suppliers);
        supplierContactsRepository.saveAndFlush(supplierContacts);
        Long suppliersId = suppliers.getId();

        // Get all the supplierContactsList where suppliers equals to suppliersId
        defaultSupplierContactsShouldBeFound("suppliersId.equals=" + suppliersId);

        // Get all the supplierContactsList where suppliers equals to (suppliersId + 1)
        defaultSupplierContactsShouldNotBeFound("suppliersId.equals=" + (suppliersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSupplierContactsShouldBeFound(String filter) throws Exception {
        restSupplierContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierContacts.getId().intValue())))
            .andExpect(jsonPath("$.[*].supplierContactName").value(hasItem(DEFAULT_SUPPLIER_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].supplierContactPhone").value(hasItem(DEFAULT_SUPPLIER_CONTACT_PHONE)))
            .andExpect(jsonPath("$.[*].supplierContactEmail").value(hasItem(DEFAULT_SUPPLIER_CONTACT_EMAIL)));

        // Check, that the count call also returns 1
        restSupplierContactsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSupplierContactsShouldNotBeFound(String filter) throws Exception {
        restSupplierContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplierContactsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSupplierContacts() throws Exception {
        // Get the supplierContacts
        restSupplierContactsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSupplierContacts() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        int databaseSizeBeforeUpdate = supplierContactsRepository.findAll().size();

        // Update the supplierContacts
        SupplierContacts updatedSupplierContacts = supplierContactsRepository.findById(supplierContacts.getId()).get();
        // Disconnect from session so that the updates on updatedSupplierContacts are not directly saved in db
        em.detach(updatedSupplierContacts);
        updatedSupplierContacts
            .supplierContactName(UPDATED_SUPPLIER_CONTACT_NAME)
            .supplierContactPhone(UPDATED_SUPPLIER_CONTACT_PHONE)
            .supplierContactEmail(UPDATED_SUPPLIER_CONTACT_EMAIL);
        SupplierContactsDTO supplierContactsDTO = supplierContactsMapper.toDto(updatedSupplierContacts);

        restSupplierContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, supplierContactsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplierContactsDTO))
            )
            .andExpect(status().isOk());

        // Validate the SupplierContacts in the database
        List<SupplierContacts> supplierContactsList = supplierContactsRepository.findAll();
        assertThat(supplierContactsList).hasSize(databaseSizeBeforeUpdate);
        SupplierContacts testSupplierContacts = supplierContactsList.get(supplierContactsList.size() - 1);
        assertThat(testSupplierContacts.getSupplierContactName()).isEqualTo(UPDATED_SUPPLIER_CONTACT_NAME);
        assertThat(testSupplierContacts.getSupplierContactPhone()).isEqualTo(UPDATED_SUPPLIER_CONTACT_PHONE);
        assertThat(testSupplierContacts.getSupplierContactEmail()).isEqualTo(UPDATED_SUPPLIER_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingSupplierContacts() throws Exception {
        int databaseSizeBeforeUpdate = supplierContactsRepository.findAll().size();
        supplierContacts.setId(count.incrementAndGet());

        // Create the SupplierContacts
        SupplierContactsDTO supplierContactsDTO = supplierContactsMapper.toDto(supplierContacts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, supplierContactsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplierContactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplierContacts in the database
        List<SupplierContacts> supplierContactsList = supplierContactsRepository.findAll();
        assertThat(supplierContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSupplierContacts() throws Exception {
        int databaseSizeBeforeUpdate = supplierContactsRepository.findAll().size();
        supplierContacts.setId(count.incrementAndGet());

        // Create the SupplierContacts
        SupplierContactsDTO supplierContactsDTO = supplierContactsMapper.toDto(supplierContacts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplierContactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplierContacts in the database
        List<SupplierContacts> supplierContactsList = supplierContactsRepository.findAll();
        assertThat(supplierContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSupplierContacts() throws Exception {
        int databaseSizeBeforeUpdate = supplierContactsRepository.findAll().size();
        supplierContacts.setId(count.incrementAndGet());

        // Create the SupplierContacts
        SupplierContactsDTO supplierContactsDTO = supplierContactsMapper.toDto(supplierContacts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierContactsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplierContactsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SupplierContacts in the database
        List<SupplierContacts> supplierContactsList = supplierContactsRepository.findAll();
        assertThat(supplierContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSupplierContactsWithPatch() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        int databaseSizeBeforeUpdate = supplierContactsRepository.findAll().size();

        // Update the supplierContacts using partial update
        SupplierContacts partialUpdatedSupplierContacts = new SupplierContacts();
        partialUpdatedSupplierContacts.setId(supplierContacts.getId());

        partialUpdatedSupplierContacts.supplierContactName(UPDATED_SUPPLIER_CONTACT_NAME);

        restSupplierContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupplierContacts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSupplierContacts))
            )
            .andExpect(status().isOk());

        // Validate the SupplierContacts in the database
        List<SupplierContacts> supplierContactsList = supplierContactsRepository.findAll();
        assertThat(supplierContactsList).hasSize(databaseSizeBeforeUpdate);
        SupplierContacts testSupplierContacts = supplierContactsList.get(supplierContactsList.size() - 1);
        assertThat(testSupplierContacts.getSupplierContactName()).isEqualTo(UPDATED_SUPPLIER_CONTACT_NAME);
        assertThat(testSupplierContacts.getSupplierContactPhone()).isEqualTo(DEFAULT_SUPPLIER_CONTACT_PHONE);
        assertThat(testSupplierContacts.getSupplierContactEmail()).isEqualTo(DEFAULT_SUPPLIER_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateSupplierContactsWithPatch() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        int databaseSizeBeforeUpdate = supplierContactsRepository.findAll().size();

        // Update the supplierContacts using partial update
        SupplierContacts partialUpdatedSupplierContacts = new SupplierContacts();
        partialUpdatedSupplierContacts.setId(supplierContacts.getId());

        partialUpdatedSupplierContacts
            .supplierContactName(UPDATED_SUPPLIER_CONTACT_NAME)
            .supplierContactPhone(UPDATED_SUPPLIER_CONTACT_PHONE)
            .supplierContactEmail(UPDATED_SUPPLIER_CONTACT_EMAIL);

        restSupplierContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupplierContacts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSupplierContacts))
            )
            .andExpect(status().isOk());

        // Validate the SupplierContacts in the database
        List<SupplierContacts> supplierContactsList = supplierContactsRepository.findAll();
        assertThat(supplierContactsList).hasSize(databaseSizeBeforeUpdate);
        SupplierContacts testSupplierContacts = supplierContactsList.get(supplierContactsList.size() - 1);
        assertThat(testSupplierContacts.getSupplierContactName()).isEqualTo(UPDATED_SUPPLIER_CONTACT_NAME);
        assertThat(testSupplierContacts.getSupplierContactPhone()).isEqualTo(UPDATED_SUPPLIER_CONTACT_PHONE);
        assertThat(testSupplierContacts.getSupplierContactEmail()).isEqualTo(UPDATED_SUPPLIER_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingSupplierContacts() throws Exception {
        int databaseSizeBeforeUpdate = supplierContactsRepository.findAll().size();
        supplierContacts.setId(count.incrementAndGet());

        // Create the SupplierContacts
        SupplierContactsDTO supplierContactsDTO = supplierContactsMapper.toDto(supplierContacts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, supplierContactsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supplierContactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplierContacts in the database
        List<SupplierContacts> supplierContactsList = supplierContactsRepository.findAll();
        assertThat(supplierContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSupplierContacts() throws Exception {
        int databaseSizeBeforeUpdate = supplierContactsRepository.findAll().size();
        supplierContacts.setId(count.incrementAndGet());

        // Create the SupplierContacts
        SupplierContactsDTO supplierContactsDTO = supplierContactsMapper.toDto(supplierContacts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supplierContactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplierContacts in the database
        List<SupplierContacts> supplierContactsList = supplierContactsRepository.findAll();
        assertThat(supplierContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSupplierContacts() throws Exception {
        int databaseSizeBeforeUpdate = supplierContactsRepository.findAll().size();
        supplierContacts.setId(count.incrementAndGet());

        // Create the SupplierContacts
        SupplierContactsDTO supplierContactsDTO = supplierContactsMapper.toDto(supplierContacts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierContactsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supplierContactsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SupplierContacts in the database
        List<SupplierContacts> supplierContactsList = supplierContactsRepository.findAll();
        assertThat(supplierContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSupplierContacts() throws Exception {
        // Initialize the database
        supplierContactsRepository.saveAndFlush(supplierContacts);

        int databaseSizeBeforeDelete = supplierContactsRepository.findAll().size();

        // Delete the supplierContacts
        restSupplierContactsMockMvc
            .perform(delete(ENTITY_API_URL_ID, supplierContacts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SupplierContacts> supplierContactsList = supplierContactsRepository.findAll();
        assertThat(supplierContactsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
