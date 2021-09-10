package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Affiliates;
import com.genesisoft.transporte.domain.Employees;
import com.genesisoft.transporte.domain.Positions;
import com.genesisoft.transporte.repository.PositionsRepository;
import com.genesisoft.transporte.service.criteria.PositionsCriteria;
import com.genesisoft.transporte.service.dto.PositionsDTO;
import com.genesisoft.transporte.service.mapper.PositionsMapper;
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
 * Integration tests for the {@link PositionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PositionsResourceIT {

    private static final String DEFAULT_POSITION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_POSITION_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/positions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PositionsRepository positionsRepository;

    @Autowired
    private PositionsMapper positionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPositionsMockMvc;

    private Positions positions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Positions createEntity(EntityManager em) {
        Positions positions = new Positions().positionName(DEFAULT_POSITION_NAME);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        positions.setAffiliates(affiliates);
        return positions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Positions createUpdatedEntity(EntityManager em) {
        Positions positions = new Positions().positionName(UPDATED_POSITION_NAME);
        // Add required entity
        Affiliates affiliates;
        if (TestUtil.findAll(em, Affiliates.class).isEmpty()) {
            affiliates = AffiliatesResourceIT.createUpdatedEntity(em);
            em.persist(affiliates);
            em.flush();
        } else {
            affiliates = TestUtil.findAll(em, Affiliates.class).get(0);
        }
        positions.setAffiliates(affiliates);
        return positions;
    }

    @BeforeEach
    public void initTest() {
        positions = createEntity(em);
    }

    @Test
    @Transactional
    void createPositions() throws Exception {
        int databaseSizeBeforeCreate = positionsRepository.findAll().size();
        // Create the Positions
        PositionsDTO positionsDTO = positionsMapper.toDto(positions);
        restPositionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(positionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeCreate + 1);
        Positions testPositions = positionsList.get(positionsList.size() - 1);
        assertThat(testPositions.getPositionName()).isEqualTo(DEFAULT_POSITION_NAME);
    }

    @Test
    @Transactional
    void createPositionsWithExistingId() throws Exception {
        // Create the Positions with an existing ID
        positions.setId(1L);
        PositionsDTO positionsDTO = positionsMapper.toDto(positions);

        int databaseSizeBeforeCreate = positionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPositionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(positionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPositionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = positionsRepository.findAll().size();
        // set the field null
        positions.setPositionName(null);

        // Create the Positions, which fails.
        PositionsDTO positionsDTO = positionsMapper.toDto(positions);

        restPositionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(positionsDTO)))
            .andExpect(status().isBadRequest());

        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPositions() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);

        // Get all the positionsList
        restPositionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(positions.getId().intValue())))
            .andExpect(jsonPath("$.[*].positionName").value(hasItem(DEFAULT_POSITION_NAME)));
    }

    @Test
    @Transactional
    void getPositions() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);

        // Get the positions
        restPositionsMockMvc
            .perform(get(ENTITY_API_URL_ID, positions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(positions.getId().intValue()))
            .andExpect(jsonPath("$.positionName").value(DEFAULT_POSITION_NAME));
    }

    @Test
    @Transactional
    void getPositionsByIdFiltering() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);

        Long id = positions.getId();

        defaultPositionsShouldBeFound("id.equals=" + id);
        defaultPositionsShouldNotBeFound("id.notEquals=" + id);

        defaultPositionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPositionsShouldNotBeFound("id.greaterThan=" + id);

        defaultPositionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPositionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPositionsByPositionNameIsEqualToSomething() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);

        // Get all the positionsList where positionName equals to DEFAULT_POSITION_NAME
        defaultPositionsShouldBeFound("positionName.equals=" + DEFAULT_POSITION_NAME);

        // Get all the positionsList where positionName equals to UPDATED_POSITION_NAME
        defaultPositionsShouldNotBeFound("positionName.equals=" + UPDATED_POSITION_NAME);
    }

    @Test
    @Transactional
    void getAllPositionsByPositionNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);

        // Get all the positionsList where positionName not equals to DEFAULT_POSITION_NAME
        defaultPositionsShouldNotBeFound("positionName.notEquals=" + DEFAULT_POSITION_NAME);

        // Get all the positionsList where positionName not equals to UPDATED_POSITION_NAME
        defaultPositionsShouldBeFound("positionName.notEquals=" + UPDATED_POSITION_NAME);
    }

    @Test
    @Transactional
    void getAllPositionsByPositionNameIsInShouldWork() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);

        // Get all the positionsList where positionName in DEFAULT_POSITION_NAME or UPDATED_POSITION_NAME
        defaultPositionsShouldBeFound("positionName.in=" + DEFAULT_POSITION_NAME + "," + UPDATED_POSITION_NAME);

        // Get all the positionsList where positionName equals to UPDATED_POSITION_NAME
        defaultPositionsShouldNotBeFound("positionName.in=" + UPDATED_POSITION_NAME);
    }

    @Test
    @Transactional
    void getAllPositionsByPositionNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);

        // Get all the positionsList where positionName is not null
        defaultPositionsShouldBeFound("positionName.specified=true");

        // Get all the positionsList where positionName is null
        defaultPositionsShouldNotBeFound("positionName.specified=false");
    }

    @Test
    @Transactional
    void getAllPositionsByPositionNameContainsSomething() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);

        // Get all the positionsList where positionName contains DEFAULT_POSITION_NAME
        defaultPositionsShouldBeFound("positionName.contains=" + DEFAULT_POSITION_NAME);

        // Get all the positionsList where positionName contains UPDATED_POSITION_NAME
        defaultPositionsShouldNotBeFound("positionName.contains=" + UPDATED_POSITION_NAME);
    }

    @Test
    @Transactional
    void getAllPositionsByPositionNameNotContainsSomething() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);

        // Get all the positionsList where positionName does not contain DEFAULT_POSITION_NAME
        defaultPositionsShouldNotBeFound("positionName.doesNotContain=" + DEFAULT_POSITION_NAME);

        // Get all the positionsList where positionName does not contain UPDATED_POSITION_NAME
        defaultPositionsShouldBeFound("positionName.doesNotContain=" + UPDATED_POSITION_NAME);
    }

    @Test
    @Transactional
    void getAllPositionsByEmployeesIsEqualToSomething() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);
        Employees employees = EmployeesResourceIT.createEntity(em);
        em.persist(employees);
        em.flush();
        positions.addEmployees(employees);
        positionsRepository.saveAndFlush(positions);
        Long employeesId = employees.getId();

        // Get all the positionsList where employees equals to employeesId
        defaultPositionsShouldBeFound("employeesId.equals=" + employeesId);

        // Get all the positionsList where employees equals to (employeesId + 1)
        defaultPositionsShouldNotBeFound("employeesId.equals=" + (employeesId + 1));
    }

    @Test
    @Transactional
    void getAllPositionsByAffiliatesIsEqualToSomething() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);
        Affiliates affiliates = AffiliatesResourceIT.createEntity(em);
        em.persist(affiliates);
        em.flush();
        positions.setAffiliates(affiliates);
        positionsRepository.saveAndFlush(positions);
        Long affiliatesId = affiliates.getId();

        // Get all the positionsList where affiliates equals to affiliatesId
        defaultPositionsShouldBeFound("affiliatesId.equals=" + affiliatesId);

        // Get all the positionsList where affiliates equals to (affiliatesId + 1)
        defaultPositionsShouldNotBeFound("affiliatesId.equals=" + (affiliatesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPositionsShouldBeFound(String filter) throws Exception {
        restPositionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(positions.getId().intValue())))
            .andExpect(jsonPath("$.[*].positionName").value(hasItem(DEFAULT_POSITION_NAME)));

        // Check, that the count call also returns 1
        restPositionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPositionsShouldNotBeFound(String filter) throws Exception {
        restPositionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPositionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPositions() throws Exception {
        // Get the positions
        restPositionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPositions() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);

        int databaseSizeBeforeUpdate = positionsRepository.findAll().size();

        // Update the positions
        Positions updatedPositions = positionsRepository.findById(positions.getId()).get();
        // Disconnect from session so that the updates on updatedPositions are not directly saved in db
        em.detach(updatedPositions);
        updatedPositions.positionName(UPDATED_POSITION_NAME);
        PositionsDTO positionsDTO = positionsMapper.toDto(updatedPositions);

        restPositionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, positionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(positionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeUpdate);
        Positions testPositions = positionsList.get(positionsList.size() - 1);
        assertThat(testPositions.getPositionName()).isEqualTo(UPDATED_POSITION_NAME);
    }

    @Test
    @Transactional
    void putNonExistingPositions() throws Exception {
        int databaseSizeBeforeUpdate = positionsRepository.findAll().size();
        positions.setId(count.incrementAndGet());

        // Create the Positions
        PositionsDTO positionsDTO = positionsMapper.toDto(positions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPositionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, positionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(positionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPositions() throws Exception {
        int databaseSizeBeforeUpdate = positionsRepository.findAll().size();
        positions.setId(count.incrementAndGet());

        // Create the Positions
        PositionsDTO positionsDTO = positionsMapper.toDto(positions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPositionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(positionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPositions() throws Exception {
        int databaseSizeBeforeUpdate = positionsRepository.findAll().size();
        positions.setId(count.incrementAndGet());

        // Create the Positions
        PositionsDTO positionsDTO = positionsMapper.toDto(positions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPositionsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(positionsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePositionsWithPatch() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);

        int databaseSizeBeforeUpdate = positionsRepository.findAll().size();

        // Update the positions using partial update
        Positions partialUpdatedPositions = new Positions();
        partialUpdatedPositions.setId(positions.getId());

        restPositionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPositions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPositions))
            )
            .andExpect(status().isOk());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeUpdate);
        Positions testPositions = positionsList.get(positionsList.size() - 1);
        assertThat(testPositions.getPositionName()).isEqualTo(DEFAULT_POSITION_NAME);
    }

    @Test
    @Transactional
    void fullUpdatePositionsWithPatch() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);

        int databaseSizeBeforeUpdate = positionsRepository.findAll().size();

        // Update the positions using partial update
        Positions partialUpdatedPositions = new Positions();
        partialUpdatedPositions.setId(positions.getId());

        partialUpdatedPositions.positionName(UPDATED_POSITION_NAME);

        restPositionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPositions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPositions))
            )
            .andExpect(status().isOk());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeUpdate);
        Positions testPositions = positionsList.get(positionsList.size() - 1);
        assertThat(testPositions.getPositionName()).isEqualTo(UPDATED_POSITION_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingPositions() throws Exception {
        int databaseSizeBeforeUpdate = positionsRepository.findAll().size();
        positions.setId(count.incrementAndGet());

        // Create the Positions
        PositionsDTO positionsDTO = positionsMapper.toDto(positions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPositionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, positionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(positionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPositions() throws Exception {
        int databaseSizeBeforeUpdate = positionsRepository.findAll().size();
        positions.setId(count.incrementAndGet());

        // Create the Positions
        PositionsDTO positionsDTO = positionsMapper.toDto(positions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPositionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(positionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPositions() throws Exception {
        int databaseSizeBeforeUpdate = positionsRepository.findAll().size();
        positions.setId(count.incrementAndGet());

        // Create the Positions
        PositionsDTO positionsDTO = positionsMapper.toDto(positions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPositionsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(positionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Positions in the database
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePositions() throws Exception {
        // Initialize the database
        positionsRepository.saveAndFlush(positions);

        int databaseSizeBeforeDelete = positionsRepository.findAll().size();

        // Delete the positions
        restPositionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, positions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Positions> positionsList = positionsRepository.findAll();
        assertThat(positionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
