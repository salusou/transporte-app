package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Affiliates;
import com.genesisoft.transporte.domain.CostCenter;
import com.genesisoft.transporte.domain.Housing;
import com.genesisoft.transporte.repository.CostCenterRepository;
import com.genesisoft.transporte.service.criteria.CostCenterCriteria;
import com.genesisoft.transporte.service.dto.CostCenterDTO;
import com.genesisoft.transporte.service.mapper.CostCenterMapper;
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
 * Integration tests for the {@link CostCenterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CostCenterResourceIT {

    private static final String DEFAULT_COST_CENTER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COST_CENTER_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cost-centers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CostCenterRepository costCenterRepository;

    @Autowired
    private CostCenterMapper costCenterMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCostCenterMockMvc;

    private CostCenter costCenter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CostCenter createEntity(EntityManager em) {
        CostCenter costCenter = new CostCenter().costCenterName(DEFAULT_COST_CENTER_NAME);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        costCenter.setAffiliates(affiliates);
        return costCenter;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CostCenter createUpdatedEntity(EntityManager em) {
        CostCenter costCenter = new CostCenter().costCenterName(UPDATED_COST_CENTER_NAME);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createUpdatedEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        costCenter.setAffiliates(affiliates);
        return costCenter;
    }

    @BeforeEach
    public void initTest() {
        costCenter = createEntity(em);
    }

    @Test
    @Transactional
    void createCostCenter() throws Exception {
        int databaseSizeBeforeCreate = costCenterRepository.findAll().size();
        // Create the CostCenter
        CostCenterDTO costCenterDTO = costCenterMapper.toDto(costCenter);
        restCostCenterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(costCenterDTO)))
            .andExpect(status().isCreated());

        // Validate the CostCenter in the database
        List<CostCenter> costCenterList = costCenterRepository.findAll();
        assertThat(costCenterList).hasSize(databaseSizeBeforeCreate + 1);
        CostCenter testCostCenter = costCenterList.get(costCenterList.size() - 1);
        assertThat(testCostCenter.getCostCenterName()).isEqualTo(DEFAULT_COST_CENTER_NAME);
    }

    @Test
    @Transactional
    void createCostCenterWithExistingId() throws Exception {
        // Create the CostCenter with an existing ID
        costCenter.setId(1L);
        CostCenterDTO costCenterDTO = costCenterMapper.toDto(costCenter);

        int databaseSizeBeforeCreate = costCenterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCostCenterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(costCenterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CostCenter in the database
        List<CostCenter> costCenterList = costCenterRepository.findAll();
        assertThat(costCenterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCostCenterNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = costCenterRepository.findAll().size();
        // set the field null
        costCenter.setCostCenterName(null);

        // Create the CostCenter, which fails.
        CostCenterDTO costCenterDTO = costCenterMapper.toDto(costCenter);

        restCostCenterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(costCenterDTO)))
            .andExpect(status().isBadRequest());

        List<CostCenter> costCenterList = costCenterRepository.findAll();
        assertThat(costCenterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCostCenters() throws Exception {
        // Initialize the database
        costCenterRepository.saveAndFlush(costCenter);

        // Get all the costCenterList
        restCostCenterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(costCenter.getId().intValue())))
            .andExpect(jsonPath("$.[*].costCenterName").value(hasItem(DEFAULT_COST_CENTER_NAME)));
    }

    @Test
    @Transactional
    void getCostCenter() throws Exception {
        // Initialize the database
        costCenterRepository.saveAndFlush(costCenter);

        // Get the costCenter
        restCostCenterMockMvc
            .perform(get(ENTITY_API_URL_ID, costCenter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(costCenter.getId().intValue()))
            .andExpect(jsonPath("$.costCenterName").value(DEFAULT_COST_CENTER_NAME));
    }

    @Test
    @Transactional
    void getCostCentersByIdFiltering() throws Exception {
        // Initialize the database
        costCenterRepository.saveAndFlush(costCenter);

        Long id = costCenter.getId();

        defaultCostCenterShouldBeFound("id.equals=" + id);
        defaultCostCenterShouldNotBeFound("id.notEquals=" + id);

        defaultCostCenterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCostCenterShouldNotBeFound("id.greaterThan=" + id);

        defaultCostCenterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCostCenterShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCostCentersByCostCenterNameIsEqualToSomething() throws Exception {
        // Initialize the database
        costCenterRepository.saveAndFlush(costCenter);

        // Get all the costCenterList where costCenterName equals to DEFAULT_COST_CENTER_NAME
        defaultCostCenterShouldBeFound("costCenterName.equals=" + DEFAULT_COST_CENTER_NAME);

        // Get all the costCenterList where costCenterName equals to UPDATED_COST_CENTER_NAME
        defaultCostCenterShouldNotBeFound("costCenterName.equals=" + UPDATED_COST_CENTER_NAME);
    }

    @Test
    @Transactional
    void getAllCostCentersByCostCenterNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        costCenterRepository.saveAndFlush(costCenter);

        // Get all the costCenterList where costCenterName not equals to DEFAULT_COST_CENTER_NAME
        defaultCostCenterShouldNotBeFound("costCenterName.notEquals=" + DEFAULT_COST_CENTER_NAME);

        // Get all the costCenterList where costCenterName not equals to UPDATED_COST_CENTER_NAME
        defaultCostCenterShouldBeFound("costCenterName.notEquals=" + UPDATED_COST_CENTER_NAME);
    }

    @Test
    @Transactional
    void getAllCostCentersByCostCenterNameIsInShouldWork() throws Exception {
        // Initialize the database
        costCenterRepository.saveAndFlush(costCenter);

        // Get all the costCenterList where costCenterName in DEFAULT_COST_CENTER_NAME or UPDATED_COST_CENTER_NAME
        defaultCostCenterShouldBeFound("costCenterName.in=" + DEFAULT_COST_CENTER_NAME + "," + UPDATED_COST_CENTER_NAME);

        // Get all the costCenterList where costCenterName equals to UPDATED_COST_CENTER_NAME
        defaultCostCenterShouldNotBeFound("costCenterName.in=" + UPDATED_COST_CENTER_NAME);
    }

    @Test
    @Transactional
    void getAllCostCentersByCostCenterNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        costCenterRepository.saveAndFlush(costCenter);

        // Get all the costCenterList where costCenterName is not null
        defaultCostCenterShouldBeFound("costCenterName.specified=true");

        // Get all the costCenterList where costCenterName is null
        defaultCostCenterShouldNotBeFound("costCenterName.specified=false");
    }

    @Test
    @Transactional
    void getAllCostCentersByCostCenterNameContainsSomething() throws Exception {
        // Initialize the database
        costCenterRepository.saveAndFlush(costCenter);

        // Get all the costCenterList where costCenterName contains DEFAULT_COST_CENTER_NAME
        defaultCostCenterShouldBeFound("costCenterName.contains=" + DEFAULT_COST_CENTER_NAME);

        // Get all the costCenterList where costCenterName contains UPDATED_COST_CENTER_NAME
        defaultCostCenterShouldNotBeFound("costCenterName.contains=" + UPDATED_COST_CENTER_NAME);
    }

    @Test
    @Transactional
    void getAllCostCentersByCostCenterNameNotContainsSomething() throws Exception {
        // Initialize the database
        costCenterRepository.saveAndFlush(costCenter);

        // Get all the costCenterList where costCenterName does not contain DEFAULT_COST_CENTER_NAME
        defaultCostCenterShouldNotBeFound("costCenterName.doesNotContain=" + DEFAULT_COST_CENTER_NAME);

        // Get all the costCenterList where costCenterName does not contain UPDATED_COST_CENTER_NAME
        defaultCostCenterShouldBeFound("costCenterName.doesNotContain=" + UPDATED_COST_CENTER_NAME);
    }

    @Test
    @Transactional
    void getAllCostCentersByHousingIsEqualToSomething() throws Exception {
        // Initialize the database
        costCenterRepository.saveAndFlush(costCenter);
        Housing housing = HousingResourceIT.createEntity(em);
        em.persist(housing);
        em.flush();
        costCenter.addHousing(housing);
        costCenterRepository.saveAndFlush(costCenter);
        Long housingId = housing.getId();

        // Get all the costCenterList where housing equals to housingId
        defaultCostCenterShouldBeFound("housingId.equals=" + housingId);

        // Get all the costCenterList where housing equals to (housingId + 1)
        defaultCostCenterShouldNotBeFound("housingId.equals=" + (housingId + 1));
    }

    @Test
    @Transactional
    void getAllCostCentersByAffiliatesIsEqualToSomething() throws Exception {
        // Initialize the database
        costCenterRepository.saveAndFlush(costCenter);
        Affiliates affiliates = AffiliatesResourceIT.createEntity(em);
        em.persist(affiliates);
        em.flush();
        costCenter.setAffiliates(affiliates);
        costCenterRepository.saveAndFlush(costCenter);
        Long affiliatesId = affiliates.getId();

        // Get all the costCenterList where affiliates equals to affiliatesId
        defaultCostCenterShouldBeFound("affiliatesId.equals=" + affiliatesId);

        // Get all the costCenterList where affiliates equals to (affiliatesId + 1)
        defaultCostCenterShouldNotBeFound("affiliatesId.equals=" + (affiliatesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCostCenterShouldBeFound(String filter) throws Exception {
        restCostCenterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(costCenter.getId().intValue())))
            .andExpect(jsonPath("$.[*].costCenterName").value(hasItem(DEFAULT_COST_CENTER_NAME)));

        // Check, that the count call also returns 1
        restCostCenterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCostCenterShouldNotBeFound(String filter) throws Exception {
        restCostCenterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCostCenterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCostCenter() throws Exception {
        // Get the costCenter
        restCostCenterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCostCenter() throws Exception {
        // Initialize the database
        costCenterRepository.saveAndFlush(costCenter);

        int databaseSizeBeforeUpdate = costCenterRepository.findAll().size();

        // Update the costCenter
        CostCenter updatedCostCenter = costCenterRepository.findById(costCenter.getId()).get();
        // Disconnect from session so that the updates on updatedCostCenter are not directly saved in db
        em.detach(updatedCostCenter);
        updatedCostCenter.costCenterName(UPDATED_COST_CENTER_NAME);
        CostCenterDTO costCenterDTO = costCenterMapper.toDto(updatedCostCenter);

        restCostCenterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, costCenterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(costCenterDTO))
            )
            .andExpect(status().isOk());

        // Validate the CostCenter in the database
        List<CostCenter> costCenterList = costCenterRepository.findAll();
        assertThat(costCenterList).hasSize(databaseSizeBeforeUpdate);
        CostCenter testCostCenter = costCenterList.get(costCenterList.size() - 1);
        assertThat(testCostCenter.getCostCenterName()).isEqualTo(UPDATED_COST_CENTER_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCostCenter() throws Exception {
        int databaseSizeBeforeUpdate = costCenterRepository.findAll().size();
        costCenter.setId(count.incrementAndGet());

        // Create the CostCenter
        CostCenterDTO costCenterDTO = costCenterMapper.toDto(costCenter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCostCenterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, costCenterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(costCenterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CostCenter in the database
        List<CostCenter> costCenterList = costCenterRepository.findAll();
        assertThat(costCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCostCenter() throws Exception {
        int databaseSizeBeforeUpdate = costCenterRepository.findAll().size();
        costCenter.setId(count.incrementAndGet());

        // Create the CostCenter
        CostCenterDTO costCenterDTO = costCenterMapper.toDto(costCenter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCostCenterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(costCenterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CostCenter in the database
        List<CostCenter> costCenterList = costCenterRepository.findAll();
        assertThat(costCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCostCenter() throws Exception {
        int databaseSizeBeforeUpdate = costCenterRepository.findAll().size();
        costCenter.setId(count.incrementAndGet());

        // Create the CostCenter
        CostCenterDTO costCenterDTO = costCenterMapper.toDto(costCenter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCostCenterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(costCenterDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CostCenter in the database
        List<CostCenter> costCenterList = costCenterRepository.findAll();
        assertThat(costCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCostCenterWithPatch() throws Exception {
        // Initialize the database
        costCenterRepository.saveAndFlush(costCenter);

        int databaseSizeBeforeUpdate = costCenterRepository.findAll().size();

        // Update the costCenter using partial update
        CostCenter partialUpdatedCostCenter = new CostCenter();
        partialUpdatedCostCenter.setId(costCenter.getId());

        restCostCenterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCostCenter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCostCenter))
            )
            .andExpect(status().isOk());

        // Validate the CostCenter in the database
        List<CostCenter> costCenterList = costCenterRepository.findAll();
        assertThat(costCenterList).hasSize(databaseSizeBeforeUpdate);
        CostCenter testCostCenter = costCenterList.get(costCenterList.size() - 1);
        assertThat(testCostCenter.getCostCenterName()).isEqualTo(DEFAULT_COST_CENTER_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCostCenterWithPatch() throws Exception {
        // Initialize the database
        costCenterRepository.saveAndFlush(costCenter);

        int databaseSizeBeforeUpdate = costCenterRepository.findAll().size();

        // Update the costCenter using partial update
        CostCenter partialUpdatedCostCenter = new CostCenter();
        partialUpdatedCostCenter.setId(costCenter.getId());

        partialUpdatedCostCenter.costCenterName(UPDATED_COST_CENTER_NAME);

        restCostCenterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCostCenter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCostCenter))
            )
            .andExpect(status().isOk());

        // Validate the CostCenter in the database
        List<CostCenter> costCenterList = costCenterRepository.findAll();
        assertThat(costCenterList).hasSize(databaseSizeBeforeUpdate);
        CostCenter testCostCenter = costCenterList.get(costCenterList.size() - 1);
        assertThat(testCostCenter.getCostCenterName()).isEqualTo(UPDATED_COST_CENTER_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCostCenter() throws Exception {
        int databaseSizeBeforeUpdate = costCenterRepository.findAll().size();
        costCenter.setId(count.incrementAndGet());

        // Create the CostCenter
        CostCenterDTO costCenterDTO = costCenterMapper.toDto(costCenter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCostCenterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, costCenterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(costCenterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CostCenter in the database
        List<CostCenter> costCenterList = costCenterRepository.findAll();
        assertThat(costCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCostCenter() throws Exception {
        int databaseSizeBeforeUpdate = costCenterRepository.findAll().size();
        costCenter.setId(count.incrementAndGet());

        // Create the CostCenter
        CostCenterDTO costCenterDTO = costCenterMapper.toDto(costCenter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCostCenterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(costCenterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CostCenter in the database
        List<CostCenter> costCenterList = costCenterRepository.findAll();
        assertThat(costCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCostCenter() throws Exception {
        int databaseSizeBeforeUpdate = costCenterRepository.findAll().size();
        costCenter.setId(count.incrementAndGet());

        // Create the CostCenter
        CostCenterDTO costCenterDTO = costCenterMapper.toDto(costCenter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCostCenterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(costCenterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CostCenter in the database
        List<CostCenter> costCenterList = costCenterRepository.findAll();
        assertThat(costCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCostCenter() throws Exception {
        // Initialize the database
        costCenterRepository.saveAndFlush(costCenter);

        int databaseSizeBeforeDelete = costCenterRepository.findAll().size();

        // Delete the costCenter
        restCostCenterMockMvc
            .perform(delete(ENTITY_API_URL_ID, costCenter.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CostCenter> costCenterList = costCenterRepository.findAll();
        assertThat(costCenterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
