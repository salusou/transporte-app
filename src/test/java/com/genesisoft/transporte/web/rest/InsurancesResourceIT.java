package com.genesisoft.transporte.web.rest;

import static com.genesisoft.transporte.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Affiliates;
import com.genesisoft.transporte.domain.Insurances;
import com.genesisoft.transporte.domain.StateProvinces;
import com.genesisoft.transporte.repository.InsurancesRepository;
import com.genesisoft.transporte.service.criteria.InsurancesCriteria;
import com.genesisoft.transporte.service.dto.InsurancesDTO;
import com.genesisoft.transporte.service.mapper.InsurancesMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link InsurancesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InsurancesResourceIT {

    private static final BigDecimal DEFAULT_INSURANCES_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_INSURANCES_PERCENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_INSURANCES_PERCENT = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/insurances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InsurancesRepository insurancesRepository;

    @Autowired
    private InsurancesMapper insurancesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInsurancesMockMvc;

    private Insurances insurances;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Insurances createEntity(EntityManager em) {
        Insurances insurances = new Insurances().insurancesPercent(DEFAULT_INSURANCES_PERCENT);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        insurances.setAffiliates(affiliates);
        // Add required entity
        StateProvinces stateProvinces;
        if (TestUtil.findAll(em, StateProvinces.class).isEmpty()) {
            stateProvinces = StateProvincesResourceIT.createEntity(em);
            em.persist(stateProvinces);
            em.flush();
        } else {
            stateProvinces = TestUtil.findAll(em, StateProvinces.class).get(0);
        }
        insurances.setToStateProvince(stateProvinces);
        // Add required entity
        insurances.setForStateProvince(stateProvinces);
        return insurances;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Insurances createUpdatedEntity(EntityManager em) {
        Insurances insurances = new Insurances().insurancesPercent(UPDATED_INSURANCES_PERCENT);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createUpdatedEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        insurances.setAffiliates(affiliates);
        // Add required entity
        StateProvinces stateProvinces;
        if (TestUtil.findAll(em, StateProvinces.class).isEmpty()) {
            stateProvinces = StateProvincesResourceIT.createUpdatedEntity(em);
            em.persist(stateProvinces);
            em.flush();
        } else {
            stateProvinces = TestUtil.findAll(em, StateProvinces.class).get(0);
        }
        insurances.setToStateProvince(stateProvinces);
        // Add required entity
        insurances.setForStateProvince(stateProvinces);
        return insurances;
    }

    @BeforeEach
    public void initTest() {
        insurances = createEntity(em);
    }

    @Test
    @Transactional
    void createInsurances() throws Exception {
        int databaseSizeBeforeCreate = insurancesRepository.findAll().size();
        // Create the Insurances
        InsurancesDTO insurancesDTO = insurancesMapper.toDto(insurances);
        restInsurancesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(insurancesDTO)))
            .andExpect(status().isCreated());

        // Validate the Insurances in the database
        List<Insurances> insurancesList = insurancesRepository.findAll();
        assertThat(insurancesList).hasSize(databaseSizeBeforeCreate + 1);
        Insurances testInsurances = insurancesList.get(insurancesList.size() - 1);
        assertThat(testInsurances.getInsurancesPercent()).isEqualByComparingTo(DEFAULT_INSURANCES_PERCENT);
    }

    @Test
    @Transactional
    void createInsurancesWithExistingId() throws Exception {
        // Create the Insurances with an existing ID
        insurances.setId(1L);
        InsurancesDTO insurancesDTO = insurancesMapper.toDto(insurances);

        int databaseSizeBeforeCreate = insurancesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsurancesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(insurancesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Insurances in the database
        List<Insurances> insurancesList = insurancesRepository.findAll();
        assertThat(insurancesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkInsurancesPercentIsRequired() throws Exception {
        int databaseSizeBeforeTest = insurancesRepository.findAll().size();
        // set the field null
        insurances.setInsurancesPercent(null);

        // Create the Insurances, which fails.
        InsurancesDTO insurancesDTO = insurancesMapper.toDto(insurances);

        restInsurancesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(insurancesDTO)))
            .andExpect(status().isBadRequest());

        List<Insurances> insurancesList = insurancesRepository.findAll();
        assertThat(insurancesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInsurances() throws Exception {
        // Initialize the database
        insurancesRepository.saveAndFlush(insurances);

        // Get all the insurancesList
        restInsurancesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insurances.getId().intValue())))
            .andExpect(jsonPath("$.[*].insurancesPercent").value(hasItem(sameNumber(DEFAULT_INSURANCES_PERCENT))));
    }

    @Test
    @Transactional
    void getInsurances() throws Exception {
        // Initialize the database
        insurancesRepository.saveAndFlush(insurances);

        // Get the insurances
        restInsurancesMockMvc
            .perform(get(ENTITY_API_URL_ID, insurances.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(insurances.getId().intValue()))
            .andExpect(jsonPath("$.insurancesPercent").value(sameNumber(DEFAULT_INSURANCES_PERCENT)));
    }

    @Test
    @Transactional
    void getInsurancesByIdFiltering() throws Exception {
        // Initialize the database
        insurancesRepository.saveAndFlush(insurances);

        Long id = insurances.getId();

        defaultInsurancesShouldBeFound("id.equals=" + id);
        defaultInsurancesShouldNotBeFound("id.notEquals=" + id);

        defaultInsurancesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInsurancesShouldNotBeFound("id.greaterThan=" + id);

        defaultInsurancesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInsurancesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInsurancesByInsurancesPercentIsEqualToSomething() throws Exception {
        // Initialize the database
        insurancesRepository.saveAndFlush(insurances);

        // Get all the insurancesList where insurancesPercent equals to DEFAULT_INSURANCES_PERCENT
        defaultInsurancesShouldBeFound("insurancesPercent.equals=" + DEFAULT_INSURANCES_PERCENT);

        // Get all the insurancesList where insurancesPercent equals to UPDATED_INSURANCES_PERCENT
        defaultInsurancesShouldNotBeFound("insurancesPercent.equals=" + UPDATED_INSURANCES_PERCENT);
    }

    @Test
    @Transactional
    void getAllInsurancesByInsurancesPercentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        insurancesRepository.saveAndFlush(insurances);

        // Get all the insurancesList where insurancesPercent not equals to DEFAULT_INSURANCES_PERCENT
        defaultInsurancesShouldNotBeFound("insurancesPercent.notEquals=" + DEFAULT_INSURANCES_PERCENT);

        // Get all the insurancesList where insurancesPercent not equals to UPDATED_INSURANCES_PERCENT
        defaultInsurancesShouldBeFound("insurancesPercent.notEquals=" + UPDATED_INSURANCES_PERCENT);
    }

    @Test
    @Transactional
    void getAllInsurancesByInsurancesPercentIsInShouldWork() throws Exception {
        // Initialize the database
        insurancesRepository.saveAndFlush(insurances);

        // Get all the insurancesList where insurancesPercent in DEFAULT_INSURANCES_PERCENT or UPDATED_INSURANCES_PERCENT
        defaultInsurancesShouldBeFound("insurancesPercent.in=" + DEFAULT_INSURANCES_PERCENT + "," + UPDATED_INSURANCES_PERCENT);

        // Get all the insurancesList where insurancesPercent equals to UPDATED_INSURANCES_PERCENT
        defaultInsurancesShouldNotBeFound("insurancesPercent.in=" + UPDATED_INSURANCES_PERCENT);
    }

    @Test
    @Transactional
    void getAllInsurancesByInsurancesPercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        insurancesRepository.saveAndFlush(insurances);

        // Get all the insurancesList where insurancesPercent is not null
        defaultInsurancesShouldBeFound("insurancesPercent.specified=true");

        // Get all the insurancesList where insurancesPercent is null
        defaultInsurancesShouldNotBeFound("insurancesPercent.specified=false");
    }

    @Test
    @Transactional
    void getAllInsurancesByInsurancesPercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insurancesRepository.saveAndFlush(insurances);

        // Get all the insurancesList where insurancesPercent is greater than or equal to DEFAULT_INSURANCES_PERCENT
        defaultInsurancesShouldBeFound("insurancesPercent.greaterThanOrEqual=" + DEFAULT_INSURANCES_PERCENT);

        // Get all the insurancesList where insurancesPercent is greater than or equal to UPDATED_INSURANCES_PERCENT
        defaultInsurancesShouldNotBeFound("insurancesPercent.greaterThanOrEqual=" + UPDATED_INSURANCES_PERCENT);
    }

    @Test
    @Transactional
    void getAllInsurancesByInsurancesPercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insurancesRepository.saveAndFlush(insurances);

        // Get all the insurancesList where insurancesPercent is less than or equal to DEFAULT_INSURANCES_PERCENT
        defaultInsurancesShouldBeFound("insurancesPercent.lessThanOrEqual=" + DEFAULT_INSURANCES_PERCENT);

        // Get all the insurancesList where insurancesPercent is less than or equal to SMALLER_INSURANCES_PERCENT
        defaultInsurancesShouldNotBeFound("insurancesPercent.lessThanOrEqual=" + SMALLER_INSURANCES_PERCENT);
    }

    @Test
    @Transactional
    void getAllInsurancesByInsurancesPercentIsLessThanSomething() throws Exception {
        // Initialize the database
        insurancesRepository.saveAndFlush(insurances);

        // Get all the insurancesList where insurancesPercent is less than DEFAULT_INSURANCES_PERCENT
        defaultInsurancesShouldNotBeFound("insurancesPercent.lessThan=" + DEFAULT_INSURANCES_PERCENT);

        // Get all the insurancesList where insurancesPercent is less than UPDATED_INSURANCES_PERCENT
        defaultInsurancesShouldBeFound("insurancesPercent.lessThan=" + UPDATED_INSURANCES_PERCENT);
    }

    @Test
    @Transactional
    void getAllInsurancesByInsurancesPercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insurancesRepository.saveAndFlush(insurances);

        // Get all the insurancesList where insurancesPercent is greater than DEFAULT_INSURANCES_PERCENT
        defaultInsurancesShouldNotBeFound("insurancesPercent.greaterThan=" + DEFAULT_INSURANCES_PERCENT);

        // Get all the insurancesList where insurancesPercent is greater than SMALLER_INSURANCES_PERCENT
        defaultInsurancesShouldBeFound("insurancesPercent.greaterThan=" + SMALLER_INSURANCES_PERCENT);
    }

    @Test
    @Transactional
    void getAllInsurancesByAffiliatesIsEqualToSomething() throws Exception {
        // Initialize the database
        insurancesRepository.saveAndFlush(insurances);
        Affiliates affiliates = AffiliatesResourceIT.createEntity(em);
        em.persist(affiliates);
        em.flush();
        insurances.setAffiliates(affiliates);
        insurancesRepository.saveAndFlush(insurances);
        Long affiliatesId = affiliates.getId();

        // Get all the insurancesList where affiliates equals to affiliatesId
        defaultInsurancesShouldBeFound("affiliatesId.equals=" + affiliatesId);

        // Get all the insurancesList where affiliates equals to (affiliatesId + 1)
        defaultInsurancesShouldNotBeFound("affiliatesId.equals=" + (affiliatesId + 1));
    }

    @Test
    @Transactional
    void getAllInsurancesByToStateProvinceIsEqualToSomething() throws Exception {
        // Initialize the database
        insurancesRepository.saveAndFlush(insurances);
        StateProvinces toStateProvince = StateProvincesResourceIT.createEntity(em);
        em.persist(toStateProvince);
        em.flush();
        insurances.setToStateProvince(toStateProvince);
        insurancesRepository.saveAndFlush(insurances);
        Long toStateProvinceId = toStateProvince.getId();

        // Get all the insurancesList where toStateProvince equals to toStateProvinceId
        defaultInsurancesShouldBeFound("toStateProvinceId.equals=" + toStateProvinceId);

        // Get all the insurancesList where toStateProvince equals to (toStateProvinceId + 1)
        defaultInsurancesShouldNotBeFound("toStateProvinceId.equals=" + (toStateProvinceId + 1));
    }

    @Test
    @Transactional
    void getAllInsurancesByForStateProvinceIsEqualToSomething() throws Exception {
        // Initialize the database
        insurancesRepository.saveAndFlush(insurances);
        StateProvinces forStateProvince = StateProvincesResourceIT.createEntity(em);
        em.persist(forStateProvince);
        em.flush();
        insurances.setForStateProvince(forStateProvince);
        insurancesRepository.saveAndFlush(insurances);
        Long forStateProvinceId = forStateProvince.getId();

        // Get all the insurancesList where forStateProvince equals to forStateProvinceId
        defaultInsurancesShouldBeFound("forStateProvinceId.equals=" + forStateProvinceId);

        // Get all the insurancesList where forStateProvince equals to (forStateProvinceId + 1)
        defaultInsurancesShouldNotBeFound("forStateProvinceId.equals=" + (forStateProvinceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInsurancesShouldBeFound(String filter) throws Exception {
        restInsurancesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insurances.getId().intValue())))
            .andExpect(jsonPath("$.[*].insurancesPercent").value(hasItem(sameNumber(DEFAULT_INSURANCES_PERCENT))));

        // Check, that the count call also returns 1
        restInsurancesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInsurancesShouldNotBeFound(String filter) throws Exception {
        restInsurancesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInsurancesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInsurances() throws Exception {
        // Get the insurances
        restInsurancesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInsurances() throws Exception {
        // Initialize the database
        insurancesRepository.saveAndFlush(insurances);

        int databaseSizeBeforeUpdate = insurancesRepository.findAll().size();

        // Update the insurances
        Insurances updatedInsurances = insurancesRepository.findById(insurances.getId()).get();
        // Disconnect from session so that the updates on updatedInsurances are not directly saved in db
        em.detach(updatedInsurances);
        updatedInsurances.insurancesPercent(UPDATED_INSURANCES_PERCENT);
        InsurancesDTO insurancesDTO = insurancesMapper.toDto(updatedInsurances);

        restInsurancesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, insurancesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(insurancesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Insurances in the database
        List<Insurances> insurancesList = insurancesRepository.findAll();
        assertThat(insurancesList).hasSize(databaseSizeBeforeUpdate);
        Insurances testInsurances = insurancesList.get(insurancesList.size() - 1);
        assertThat(testInsurances.getInsurancesPercent()).isEqualTo(UPDATED_INSURANCES_PERCENT);
    }

    @Test
    @Transactional
    void putNonExistingInsurances() throws Exception {
        int databaseSizeBeforeUpdate = insurancesRepository.findAll().size();
        insurances.setId(count.incrementAndGet());

        // Create the Insurances
        InsurancesDTO insurancesDTO = insurancesMapper.toDto(insurances);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInsurancesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, insurancesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(insurancesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Insurances in the database
        List<Insurances> insurancesList = insurancesRepository.findAll();
        assertThat(insurancesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInsurances() throws Exception {
        int databaseSizeBeforeUpdate = insurancesRepository.findAll().size();
        insurances.setId(count.incrementAndGet());

        // Create the Insurances
        InsurancesDTO insurancesDTO = insurancesMapper.toDto(insurances);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInsurancesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(insurancesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Insurances in the database
        List<Insurances> insurancesList = insurancesRepository.findAll();
        assertThat(insurancesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInsurances() throws Exception {
        int databaseSizeBeforeUpdate = insurancesRepository.findAll().size();
        insurances.setId(count.incrementAndGet());

        // Create the Insurances
        InsurancesDTO insurancesDTO = insurancesMapper.toDto(insurances);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInsurancesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(insurancesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Insurances in the database
        List<Insurances> insurancesList = insurancesRepository.findAll();
        assertThat(insurancesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInsurancesWithPatch() throws Exception {
        // Initialize the database
        insurancesRepository.saveAndFlush(insurances);

        int databaseSizeBeforeUpdate = insurancesRepository.findAll().size();

        // Update the insurances using partial update
        Insurances partialUpdatedInsurances = new Insurances();
        partialUpdatedInsurances.setId(insurances.getId());

        partialUpdatedInsurances.insurancesPercent(UPDATED_INSURANCES_PERCENT);

        restInsurancesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInsurances.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInsurances))
            )
            .andExpect(status().isOk());

        // Validate the Insurances in the database
        List<Insurances> insurancesList = insurancesRepository.findAll();
        assertThat(insurancesList).hasSize(databaseSizeBeforeUpdate);
        Insurances testInsurances = insurancesList.get(insurancesList.size() - 1);
        assertThat(testInsurances.getInsurancesPercent()).isEqualByComparingTo(UPDATED_INSURANCES_PERCENT);
    }

    @Test
    @Transactional
    void fullUpdateInsurancesWithPatch() throws Exception {
        // Initialize the database
        insurancesRepository.saveAndFlush(insurances);

        int databaseSizeBeforeUpdate = insurancesRepository.findAll().size();

        // Update the insurances using partial update
        Insurances partialUpdatedInsurances = new Insurances();
        partialUpdatedInsurances.setId(insurances.getId());

        partialUpdatedInsurances.insurancesPercent(UPDATED_INSURANCES_PERCENT);

        restInsurancesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInsurances.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInsurances))
            )
            .andExpect(status().isOk());

        // Validate the Insurances in the database
        List<Insurances> insurancesList = insurancesRepository.findAll();
        assertThat(insurancesList).hasSize(databaseSizeBeforeUpdate);
        Insurances testInsurances = insurancesList.get(insurancesList.size() - 1);
        assertThat(testInsurances.getInsurancesPercent()).isEqualByComparingTo(UPDATED_INSURANCES_PERCENT);
    }

    @Test
    @Transactional
    void patchNonExistingInsurances() throws Exception {
        int databaseSizeBeforeUpdate = insurancesRepository.findAll().size();
        insurances.setId(count.incrementAndGet());

        // Create the Insurances
        InsurancesDTO insurancesDTO = insurancesMapper.toDto(insurances);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInsurancesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, insurancesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(insurancesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Insurances in the database
        List<Insurances> insurancesList = insurancesRepository.findAll();
        assertThat(insurancesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInsurances() throws Exception {
        int databaseSizeBeforeUpdate = insurancesRepository.findAll().size();
        insurances.setId(count.incrementAndGet());

        // Create the Insurances
        InsurancesDTO insurancesDTO = insurancesMapper.toDto(insurances);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInsurancesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(insurancesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Insurances in the database
        List<Insurances> insurancesList = insurancesRepository.findAll();
        assertThat(insurancesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInsurances() throws Exception {
        int databaseSizeBeforeUpdate = insurancesRepository.findAll().size();
        insurances.setId(count.incrementAndGet());

        // Create the Insurances
        InsurancesDTO insurancesDTO = insurancesMapper.toDto(insurances);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInsurancesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(insurancesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Insurances in the database
        List<Insurances> insurancesList = insurancesRepository.findAll();
        assertThat(insurancesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInsurances() throws Exception {
        // Initialize the database
        insurancesRepository.saveAndFlush(insurances);

        int databaseSizeBeforeDelete = insurancesRepository.findAll().size();

        // Delete the insurances
        restInsurancesMockMvc
            .perform(delete(ENTITY_API_URL_ID, insurances.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Insurances> insurancesList = insurancesRepository.findAll();
        assertThat(insurancesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
