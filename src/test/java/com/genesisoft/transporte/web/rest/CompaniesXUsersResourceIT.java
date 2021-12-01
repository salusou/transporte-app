package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Companies;
import com.genesisoft.transporte.domain.CompaniesXUsers;
import com.genesisoft.transporte.domain.User;
import com.genesisoft.transporte.repository.CompaniesXUsersRepository;
import com.genesisoft.transporte.service.criteria.CompaniesXUsersCriteria;
import com.genesisoft.transporte.service.dto.CompaniesXUsersDTO;
import com.genesisoft.transporte.service.mapper.CompaniesXUsersMapper;
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
 * Integration tests for the {@link CompaniesXUsersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompaniesXUsersResourceIT {

    private static final String ENTITY_API_URL = "/api/companies-x-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompaniesXUsersRepository companiesXUsersRepository;

    @Autowired
    private CompaniesXUsersMapper companiesXUsersMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompaniesXUsersMockMvc;

    private CompaniesXUsers companiesXUsers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompaniesXUsers createEntity(EntityManager em) {
        CompaniesXUsers companiesXUsers = new CompaniesXUsers();
        // Add required entity
        Companies companies;
        if (TestUtil.findAll(em, Companies.class).isEmpty()) {
            companies = CompaniesResourceIT.createEntity(em);
            em.persist(companies);
            em.flush();
        } else {
            companies = TestUtil.findAll(em, Companies.class).get(0);
        }
        companiesXUsers.setCompanies(companies);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        companiesXUsers.setUser(user);
        return companiesXUsers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompaniesXUsers createUpdatedEntity(EntityManager em) {
        CompaniesXUsers companiesXUsers = new CompaniesXUsers();
        // Add required entity
        Companies companies;
        if (TestUtil.findAll(em, Companies.class).isEmpty()) {
            companies = CompaniesResourceIT.createUpdatedEntity(em);
            em.persist(companies);
            em.flush();
        } else {
            companies = TestUtil.findAll(em, Companies.class).get(0);
        }
        companiesXUsers.setCompanies(companies);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        companiesXUsers.setUser(user);
        return companiesXUsers;
    }

    @BeforeEach
    public void initTest() {
        companiesXUsers = createEntity(em);
    }

    @Test
    @Transactional
    void createCompaniesXUsers() throws Exception {
        int databaseSizeBeforeCreate = companiesXUsersRepository.findAll().size();
        // Create the CompaniesXUsers
        CompaniesXUsersDTO companiesXUsersDTO = companiesXUsersMapper.toDto(companiesXUsers);
        restCompaniesXUsersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companiesXUsersDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CompaniesXUsers in the database
        List<CompaniesXUsers> companiesXUsersList = companiesXUsersRepository.findAll();
        assertThat(companiesXUsersList).hasSize(databaseSizeBeforeCreate + 1);
        CompaniesXUsers testCompaniesXUsers = companiesXUsersList.get(companiesXUsersList.size() - 1);
    }

    @Test
    @Transactional
    void createCompaniesXUsersWithExistingId() throws Exception {
        // Create the CompaniesXUsers with an existing ID
        companiesXUsers.setId(1L);
        CompaniesXUsersDTO companiesXUsersDTO = companiesXUsersMapper.toDto(companiesXUsers);

        int databaseSizeBeforeCreate = companiesXUsersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompaniesXUsersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companiesXUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompaniesXUsers in the database
        List<CompaniesXUsers> companiesXUsersList = companiesXUsersRepository.findAll();
        assertThat(companiesXUsersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompaniesXUsers() throws Exception {
        // Initialize the database
        companiesXUsersRepository.saveAndFlush(companiesXUsers);

        // Get all the companiesXUsersList
        restCompaniesXUsersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companiesXUsers.getId().intValue())));
    }

    @Test
    @Transactional
    void getCompaniesXUsers() throws Exception {
        // Initialize the database
        companiesXUsersRepository.saveAndFlush(companiesXUsers);

        // Get the companiesXUsers
        restCompaniesXUsersMockMvc
            .perform(get(ENTITY_API_URL_ID, companiesXUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companiesXUsers.getId().intValue()));
    }

    @Test
    @Transactional
    void getCompaniesXUsersByIdFiltering() throws Exception {
        // Initialize the database
        companiesXUsersRepository.saveAndFlush(companiesXUsers);

        Long id = companiesXUsers.getId();

        defaultCompaniesXUsersShouldBeFound("id.equals=" + id);
        defaultCompaniesXUsersShouldNotBeFound("id.notEquals=" + id);

        defaultCompaniesXUsersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompaniesXUsersShouldNotBeFound("id.greaterThan=" + id);

        defaultCompaniesXUsersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompaniesXUsersShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCompaniesXUsersByCompaniesIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesXUsersRepository.saveAndFlush(companiesXUsers);
        Companies companies = CompaniesResourceIT.createEntity(em);
        em.persist(companies);
        em.flush();
        companiesXUsers.setCompanies(companies);
        companiesXUsersRepository.saveAndFlush(companiesXUsers);
        Long companiesId = companies.getId();

        // Get all the companiesXUsersList where companies equals to companiesId
        defaultCompaniesXUsersShouldBeFound("companiesId.equals=" + companiesId);

        // Get all the companiesXUsersList where companies equals to (companiesId + 1)
        defaultCompaniesXUsersShouldNotBeFound("companiesId.equals=" + (companiesId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesXUsersByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        companiesXUsersRepository.saveAndFlush(companiesXUsers);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        companiesXUsers.setUser(user);
        companiesXUsersRepository.saveAndFlush(companiesXUsers);
        Long userId = user.getId();

        // Get all the companiesXUsersList where user equals to userId
        defaultCompaniesXUsersShouldBeFound("userId.equals=" + userId);

        // Get all the companiesXUsersList where user equals to (userId + 1)
        defaultCompaniesXUsersShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompaniesXUsersShouldBeFound(String filter) throws Exception {
        restCompaniesXUsersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companiesXUsers.getId().intValue())));

        // Check, that the count call also returns 1
        restCompaniesXUsersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompaniesXUsersShouldNotBeFound(String filter) throws Exception {
        restCompaniesXUsersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompaniesXUsersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCompaniesXUsers() throws Exception {
        // Get the companiesXUsers
        restCompaniesXUsersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCompaniesXUsers() throws Exception {
        // Initialize the database
        companiesXUsersRepository.saveAndFlush(companiesXUsers);

        int databaseSizeBeforeUpdate = companiesXUsersRepository.findAll().size();

        // Update the companiesXUsers
        CompaniesXUsers updatedCompaniesXUsers = companiesXUsersRepository.findById(companiesXUsers.getId()).get();
        // Disconnect from session so that the updates on updatedCompaniesXUsers are not directly saved in db
        em.detach(updatedCompaniesXUsers);
        CompaniesXUsersDTO companiesXUsersDTO = companiesXUsersMapper.toDto(updatedCompaniesXUsers);

        restCompaniesXUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companiesXUsersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companiesXUsersDTO))
            )
            .andExpect(status().isOk());

        // Validate the CompaniesXUsers in the database
        List<CompaniesXUsers> companiesXUsersList = companiesXUsersRepository.findAll();
        assertThat(companiesXUsersList).hasSize(databaseSizeBeforeUpdate);
        CompaniesXUsers testCompaniesXUsers = companiesXUsersList.get(companiesXUsersList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingCompaniesXUsers() throws Exception {
        int databaseSizeBeforeUpdate = companiesXUsersRepository.findAll().size();
        companiesXUsers.setId(count.incrementAndGet());

        // Create the CompaniesXUsers
        CompaniesXUsersDTO companiesXUsersDTO = companiesXUsersMapper.toDto(companiesXUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompaniesXUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companiesXUsersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companiesXUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompaniesXUsers in the database
        List<CompaniesXUsers> companiesXUsersList = companiesXUsersRepository.findAll();
        assertThat(companiesXUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompaniesXUsers() throws Exception {
        int databaseSizeBeforeUpdate = companiesXUsersRepository.findAll().size();
        companiesXUsers.setId(count.incrementAndGet());

        // Create the CompaniesXUsers
        CompaniesXUsersDTO companiesXUsersDTO = companiesXUsersMapper.toDto(companiesXUsers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompaniesXUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companiesXUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompaniesXUsers in the database
        List<CompaniesXUsers> companiesXUsersList = companiesXUsersRepository.findAll();
        assertThat(companiesXUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompaniesXUsers() throws Exception {
        int databaseSizeBeforeUpdate = companiesXUsersRepository.findAll().size();
        companiesXUsers.setId(count.incrementAndGet());

        // Create the CompaniesXUsers
        CompaniesXUsersDTO companiesXUsersDTO = companiesXUsersMapper.toDto(companiesXUsers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompaniesXUsersMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companiesXUsersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompaniesXUsers in the database
        List<CompaniesXUsers> companiesXUsersList = companiesXUsersRepository.findAll();
        assertThat(companiesXUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompaniesXUsersWithPatch() throws Exception {
        // Initialize the database
        companiesXUsersRepository.saveAndFlush(companiesXUsers);

        int databaseSizeBeforeUpdate = companiesXUsersRepository.findAll().size();

        // Update the companiesXUsers using partial update
        CompaniesXUsers partialUpdatedCompaniesXUsers = new CompaniesXUsers();
        partialUpdatedCompaniesXUsers.setId(companiesXUsers.getId());

        restCompaniesXUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompaniesXUsers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompaniesXUsers))
            )
            .andExpect(status().isOk());

        // Validate the CompaniesXUsers in the database
        List<CompaniesXUsers> companiesXUsersList = companiesXUsersRepository.findAll();
        assertThat(companiesXUsersList).hasSize(databaseSizeBeforeUpdate);
        CompaniesXUsers testCompaniesXUsers = companiesXUsersList.get(companiesXUsersList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateCompaniesXUsersWithPatch() throws Exception {
        // Initialize the database
        companiesXUsersRepository.saveAndFlush(companiesXUsers);

        int databaseSizeBeforeUpdate = companiesXUsersRepository.findAll().size();

        // Update the companiesXUsers using partial update
        CompaniesXUsers partialUpdatedCompaniesXUsers = new CompaniesXUsers();
        partialUpdatedCompaniesXUsers.setId(companiesXUsers.getId());

        restCompaniesXUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompaniesXUsers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompaniesXUsers))
            )
            .andExpect(status().isOk());

        // Validate the CompaniesXUsers in the database
        List<CompaniesXUsers> companiesXUsersList = companiesXUsersRepository.findAll();
        assertThat(companiesXUsersList).hasSize(databaseSizeBeforeUpdate);
        CompaniesXUsers testCompaniesXUsers = companiesXUsersList.get(companiesXUsersList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingCompaniesXUsers() throws Exception {
        int databaseSizeBeforeUpdate = companiesXUsersRepository.findAll().size();
        companiesXUsers.setId(count.incrementAndGet());

        // Create the CompaniesXUsers
        CompaniesXUsersDTO companiesXUsersDTO = companiesXUsersMapper.toDto(companiesXUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompaniesXUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companiesXUsersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companiesXUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompaniesXUsers in the database
        List<CompaniesXUsers> companiesXUsersList = companiesXUsersRepository.findAll();
        assertThat(companiesXUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompaniesXUsers() throws Exception {
        int databaseSizeBeforeUpdate = companiesXUsersRepository.findAll().size();
        companiesXUsers.setId(count.incrementAndGet());

        // Create the CompaniesXUsers
        CompaniesXUsersDTO companiesXUsersDTO = companiesXUsersMapper.toDto(companiesXUsers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompaniesXUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companiesXUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompaniesXUsers in the database
        List<CompaniesXUsers> companiesXUsersList = companiesXUsersRepository.findAll();
        assertThat(companiesXUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompaniesXUsers() throws Exception {
        int databaseSizeBeforeUpdate = companiesXUsersRepository.findAll().size();
        companiesXUsers.setId(count.incrementAndGet());

        // Create the CompaniesXUsers
        CompaniesXUsersDTO companiesXUsersDTO = companiesXUsersMapper.toDto(companiesXUsers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompaniesXUsersMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companiesXUsersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompaniesXUsers in the database
        List<CompaniesXUsers> companiesXUsersList = companiesXUsersRepository.findAll();
        assertThat(companiesXUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompaniesXUsers() throws Exception {
        // Initialize the database
        companiesXUsersRepository.saveAndFlush(companiesXUsers);

        int databaseSizeBeforeDelete = companiesXUsersRepository.findAll().size();

        // Delete the companiesXUsers
        restCompaniesXUsersMockMvc
            .perform(delete(ENTITY_API_URL_ID, companiesXUsers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompaniesXUsers> companiesXUsersList = companiesXUsersRepository.findAll();
        assertThat(companiesXUsersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
