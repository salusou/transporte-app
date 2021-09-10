package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Affiliates;
import com.genesisoft.transporte.domain.Customers;
import com.genesisoft.transporte.domain.CustomersGroups;
import com.genesisoft.transporte.domain.VehicleControls;
import com.genesisoft.transporte.repository.CustomersGroupsRepository;
import com.genesisoft.transporte.service.criteria.CustomersGroupsCriteria;
import com.genesisoft.transporte.service.dto.CustomersGroupsDTO;
import com.genesisoft.transporte.service.mapper.CustomersGroupsMapper;
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
 * Integration tests for the {@link CustomersGroupsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomersGroupsResourceIT {

    private static final String DEFAULT_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/customers-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomersGroupsRepository customersGroupsRepository;

    @Autowired
    private CustomersGroupsMapper customersGroupsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomersGroupsMockMvc;

    private CustomersGroups customersGroups;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomersGroups createEntity(EntityManager em) {
        CustomersGroups customersGroups = new CustomersGroups().groupName(DEFAULT_GROUP_NAME);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        customersGroups.setAffiliates(affiliates);
        return customersGroups;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomersGroups createUpdatedEntity(EntityManager em) {
        CustomersGroups customersGroups = new CustomersGroups().groupName(UPDATED_GROUP_NAME);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createUpdatedEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        customersGroups.setAffiliates(affiliates);
        return customersGroups;
    }

    @BeforeEach
    public void initTest() {
        customersGroups = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomersGroups() throws Exception {
        int databaseSizeBeforeCreate = customersGroupsRepository.findAll().size();
        // Create the CustomersGroups
        CustomersGroupsDTO customersGroupsDTO = customersGroupsMapper.toDto(customersGroups);
        restCustomersGroupsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customersGroupsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustomersGroups in the database
        List<CustomersGroups> customersGroupsList = customersGroupsRepository.findAll();
        assertThat(customersGroupsList).hasSize(databaseSizeBeforeCreate + 1);
        CustomersGroups testCustomersGroups = customersGroupsList.get(customersGroupsList.size() - 1);
        assertThat(testCustomersGroups.getGroupName()).isEqualTo(DEFAULT_GROUP_NAME);
    }

    @Test
    @Transactional
    void createCustomersGroupsWithExistingId() throws Exception {
        // Create the CustomersGroups with an existing ID
        customersGroups.setId(1L);
        CustomersGroupsDTO customersGroupsDTO = customersGroupsMapper.toDto(customersGroups);

        int databaseSizeBeforeCreate = customersGroupsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomersGroupsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customersGroupsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomersGroups in the database
        List<CustomersGroups> customersGroupsList = customersGroupsRepository.findAll();
        assertThat(customersGroupsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGroupNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customersGroupsRepository.findAll().size();
        // set the field null
        customersGroups.setGroupName(null);

        // Create the CustomersGroups, which fails.
        CustomersGroupsDTO customersGroupsDTO = customersGroupsMapper.toDto(customersGroups);

        restCustomersGroupsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customersGroupsDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustomersGroups> customersGroupsList = customersGroupsRepository.findAll();
        assertThat(customersGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustomersGroups() throws Exception {
        // Initialize the database
        customersGroupsRepository.saveAndFlush(customersGroups);

        // Get all the customersGroupsList
        restCustomersGroupsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customersGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)));
    }

    @Test
    @Transactional
    void getCustomersGroups() throws Exception {
        // Initialize the database
        customersGroupsRepository.saveAndFlush(customersGroups);

        // Get the customersGroups
        restCustomersGroupsMockMvc
            .perform(get(ENTITY_API_URL_ID, customersGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customersGroups.getId().intValue()))
            .andExpect(jsonPath("$.groupName").value(DEFAULT_GROUP_NAME));
    }

    @Test
    @Transactional
    void getCustomersGroupsByIdFiltering() throws Exception {
        // Initialize the database
        customersGroupsRepository.saveAndFlush(customersGroups);

        Long id = customersGroups.getId();

        defaultCustomersGroupsShouldBeFound("id.equals=" + id);
        defaultCustomersGroupsShouldNotBeFound("id.notEquals=" + id);

        defaultCustomersGroupsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomersGroupsShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomersGroupsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomersGroupsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustomersGroupsByGroupNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customersGroupsRepository.saveAndFlush(customersGroups);

        // Get all the customersGroupsList where groupName equals to DEFAULT_GROUP_NAME
        defaultCustomersGroupsShouldBeFound("groupName.equals=" + DEFAULT_GROUP_NAME);

        // Get all the customersGroupsList where groupName equals to UPDATED_GROUP_NAME
        defaultCustomersGroupsShouldNotBeFound("groupName.equals=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersGroupsByGroupNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersGroupsRepository.saveAndFlush(customersGroups);

        // Get all the customersGroupsList where groupName not equals to DEFAULT_GROUP_NAME
        defaultCustomersGroupsShouldNotBeFound("groupName.notEquals=" + DEFAULT_GROUP_NAME);

        // Get all the customersGroupsList where groupName not equals to UPDATED_GROUP_NAME
        defaultCustomersGroupsShouldBeFound("groupName.notEquals=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersGroupsByGroupNameIsInShouldWork() throws Exception {
        // Initialize the database
        customersGroupsRepository.saveAndFlush(customersGroups);

        // Get all the customersGroupsList where groupName in DEFAULT_GROUP_NAME or UPDATED_GROUP_NAME
        defaultCustomersGroupsShouldBeFound("groupName.in=" + DEFAULT_GROUP_NAME + "," + UPDATED_GROUP_NAME);

        // Get all the customersGroupsList where groupName equals to UPDATED_GROUP_NAME
        defaultCustomersGroupsShouldNotBeFound("groupName.in=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersGroupsByGroupNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersGroupsRepository.saveAndFlush(customersGroups);

        // Get all the customersGroupsList where groupName is not null
        defaultCustomersGroupsShouldBeFound("groupName.specified=true");

        // Get all the customersGroupsList where groupName is null
        defaultCustomersGroupsShouldNotBeFound("groupName.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersGroupsByGroupNameContainsSomething() throws Exception {
        // Initialize the database
        customersGroupsRepository.saveAndFlush(customersGroups);

        // Get all the customersGroupsList where groupName contains DEFAULT_GROUP_NAME
        defaultCustomersGroupsShouldBeFound("groupName.contains=" + DEFAULT_GROUP_NAME);

        // Get all the customersGroupsList where groupName contains UPDATED_GROUP_NAME
        defaultCustomersGroupsShouldNotBeFound("groupName.contains=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersGroupsByGroupNameNotContainsSomething() throws Exception {
        // Initialize the database
        customersGroupsRepository.saveAndFlush(customersGroups);

        // Get all the customersGroupsList where groupName does not contain DEFAULT_GROUP_NAME
        defaultCustomersGroupsShouldNotBeFound("groupName.doesNotContain=" + DEFAULT_GROUP_NAME);

        // Get all the customersGroupsList where groupName does not contain UPDATED_GROUP_NAME
        defaultCustomersGroupsShouldBeFound("groupName.doesNotContain=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersGroupsByCustomersIsEqualToSomething() throws Exception {
        // Initialize the database
        customersGroupsRepository.saveAndFlush(customersGroups);
        Customers customers = CustomersResourceIT.createEntity(em);
        em.persist(customers);
        em.flush();
        customersGroups.addCustomers(customers);
        customersGroupsRepository.saveAndFlush(customersGroups);
        Long customersId = customers.getId();

        // Get all the customersGroupsList where customers equals to customersId
        defaultCustomersGroupsShouldBeFound("customersId.equals=" + customersId);

        // Get all the customersGroupsList where customers equals to (customersId + 1)
        defaultCustomersGroupsShouldNotBeFound("customersId.equals=" + (customersId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersGroupsByVehicleControlsIsEqualToSomething() throws Exception {
        // Initialize the database
        customersGroupsRepository.saveAndFlush(customersGroups);
        VehicleControls vehicleControls = VehicleControlsResourceIT.createEntity(em);
        em.persist(vehicleControls);
        em.flush();
        customersGroups.addVehicleControls(vehicleControls);
        customersGroupsRepository.saveAndFlush(customersGroups);
        Long vehicleControlsId = vehicleControls.getId();

        // Get all the customersGroupsList where vehicleControls equals to vehicleControlsId
        defaultCustomersGroupsShouldBeFound("vehicleControlsId.equals=" + vehicleControlsId);

        // Get all the customersGroupsList where vehicleControls equals to (vehicleControlsId + 1)
        defaultCustomersGroupsShouldNotBeFound("vehicleControlsId.equals=" + (vehicleControlsId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersGroupsByAffiliatesIsEqualToSomething() throws Exception {
        // Initialize the database
        customersGroupsRepository.saveAndFlush(customersGroups);
        Affiliates affiliates = AffiliatesResourceIT.createEntity(em);
        em.persist(affiliates);
        em.flush();
        customersGroups.setAffiliates(affiliates);
        customersGroupsRepository.saveAndFlush(customersGroups);
        Long affiliatesId = affiliates.getId();

        // Get all the customersGroupsList where affiliates equals to affiliatesId
        defaultCustomersGroupsShouldBeFound("affiliatesId.equals=" + affiliatesId);

        // Get all the customersGroupsList where affiliates equals to (affiliatesId + 1)
        defaultCustomersGroupsShouldNotBeFound("affiliatesId.equals=" + (affiliatesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomersGroupsShouldBeFound(String filter) throws Exception {
        restCustomersGroupsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customersGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)));

        // Check, that the count call also returns 1
        restCustomersGroupsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomersGroupsShouldNotBeFound(String filter) throws Exception {
        restCustomersGroupsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomersGroupsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustomersGroups() throws Exception {
        // Get the customersGroups
        restCustomersGroupsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustomersGroups() throws Exception {
        // Initialize the database
        customersGroupsRepository.saveAndFlush(customersGroups);

        int databaseSizeBeforeUpdate = customersGroupsRepository.findAll().size();

        // Update the customersGroups
        CustomersGroups updatedCustomersGroups = customersGroupsRepository.findById(customersGroups.getId()).get();
        // Disconnect from session so that the updates on updatedCustomersGroups are not directly saved in db
        em.detach(updatedCustomersGroups);
        updatedCustomersGroups.groupName(UPDATED_GROUP_NAME);
        CustomersGroupsDTO customersGroupsDTO = customersGroupsMapper.toDto(updatedCustomersGroups);

        restCustomersGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customersGroupsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customersGroupsDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustomersGroups in the database
        List<CustomersGroups> customersGroupsList = customersGroupsRepository.findAll();
        assertThat(customersGroupsList).hasSize(databaseSizeBeforeUpdate);
        CustomersGroups testCustomersGroups = customersGroupsList.get(customersGroupsList.size() - 1);
        assertThat(testCustomersGroups.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCustomersGroups() throws Exception {
        int databaseSizeBeforeUpdate = customersGroupsRepository.findAll().size();
        customersGroups.setId(count.incrementAndGet());

        // Create the CustomersGroups
        CustomersGroupsDTO customersGroupsDTO = customersGroupsMapper.toDto(customersGroups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomersGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customersGroupsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customersGroupsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomersGroups in the database
        List<CustomersGroups> customersGroupsList = customersGroupsRepository.findAll();
        assertThat(customersGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomersGroups() throws Exception {
        int databaseSizeBeforeUpdate = customersGroupsRepository.findAll().size();
        customersGroups.setId(count.incrementAndGet());

        // Create the CustomersGroups
        CustomersGroupsDTO customersGroupsDTO = customersGroupsMapper.toDto(customersGroups);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomersGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customersGroupsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomersGroups in the database
        List<CustomersGroups> customersGroupsList = customersGroupsRepository.findAll();
        assertThat(customersGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomersGroups() throws Exception {
        int databaseSizeBeforeUpdate = customersGroupsRepository.findAll().size();
        customersGroups.setId(count.incrementAndGet());

        // Create the CustomersGroups
        CustomersGroupsDTO customersGroupsDTO = customersGroupsMapper.toDto(customersGroups);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomersGroupsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customersGroupsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomersGroups in the database
        List<CustomersGroups> customersGroupsList = customersGroupsRepository.findAll();
        assertThat(customersGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomersGroupsWithPatch() throws Exception {
        // Initialize the database
        customersGroupsRepository.saveAndFlush(customersGroups);

        int databaseSizeBeforeUpdate = customersGroupsRepository.findAll().size();

        // Update the customersGroups using partial update
        CustomersGroups partialUpdatedCustomersGroups = new CustomersGroups();
        partialUpdatedCustomersGroups.setId(customersGroups.getId());

        partialUpdatedCustomersGroups.groupName(UPDATED_GROUP_NAME);

        restCustomersGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomersGroups.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomersGroups))
            )
            .andExpect(status().isOk());

        // Validate the CustomersGroups in the database
        List<CustomersGroups> customersGroupsList = customersGroupsRepository.findAll();
        assertThat(customersGroupsList).hasSize(databaseSizeBeforeUpdate);
        CustomersGroups testCustomersGroups = customersGroupsList.get(customersGroupsList.size() - 1);
        assertThat(testCustomersGroups.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCustomersGroupsWithPatch() throws Exception {
        // Initialize the database
        customersGroupsRepository.saveAndFlush(customersGroups);

        int databaseSizeBeforeUpdate = customersGroupsRepository.findAll().size();

        // Update the customersGroups using partial update
        CustomersGroups partialUpdatedCustomersGroups = new CustomersGroups();
        partialUpdatedCustomersGroups.setId(customersGroups.getId());

        partialUpdatedCustomersGroups.groupName(UPDATED_GROUP_NAME);

        restCustomersGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomersGroups.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomersGroups))
            )
            .andExpect(status().isOk());

        // Validate the CustomersGroups in the database
        List<CustomersGroups> customersGroupsList = customersGroupsRepository.findAll();
        assertThat(customersGroupsList).hasSize(databaseSizeBeforeUpdate);
        CustomersGroups testCustomersGroups = customersGroupsList.get(customersGroupsList.size() - 1);
        assertThat(testCustomersGroups.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCustomersGroups() throws Exception {
        int databaseSizeBeforeUpdate = customersGroupsRepository.findAll().size();
        customersGroups.setId(count.incrementAndGet());

        // Create the CustomersGroups
        CustomersGroupsDTO customersGroupsDTO = customersGroupsMapper.toDto(customersGroups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomersGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customersGroupsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customersGroupsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomersGroups in the database
        List<CustomersGroups> customersGroupsList = customersGroupsRepository.findAll();
        assertThat(customersGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomersGroups() throws Exception {
        int databaseSizeBeforeUpdate = customersGroupsRepository.findAll().size();
        customersGroups.setId(count.incrementAndGet());

        // Create the CustomersGroups
        CustomersGroupsDTO customersGroupsDTO = customersGroupsMapper.toDto(customersGroups);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomersGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customersGroupsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomersGroups in the database
        List<CustomersGroups> customersGroupsList = customersGroupsRepository.findAll();
        assertThat(customersGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomersGroups() throws Exception {
        int databaseSizeBeforeUpdate = customersGroupsRepository.findAll().size();
        customersGroups.setId(count.incrementAndGet());

        // Create the CustomersGroups
        CustomersGroupsDTO customersGroupsDTO = customersGroupsMapper.toDto(customersGroups);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomersGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customersGroupsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomersGroups in the database
        List<CustomersGroups> customersGroupsList = customersGroupsRepository.findAll();
        assertThat(customersGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomersGroups() throws Exception {
        // Initialize the database
        customersGroupsRepository.saveAndFlush(customersGroups);

        int databaseSizeBeforeDelete = customersGroupsRepository.findAll().size();

        // Delete the customersGroups
        restCustomersGroupsMockMvc
            .perform(delete(ENTITY_API_URL_ID, customersGroups.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomersGroups> customersGroupsList = customersGroupsRepository.findAll();
        assertThat(customersGroupsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
