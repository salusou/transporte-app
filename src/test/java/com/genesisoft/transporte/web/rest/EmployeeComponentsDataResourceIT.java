package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.EmployeeComponentsData;
import com.genesisoft.transporte.domain.Employees;
import com.genesisoft.transporte.repository.EmployeeComponentsDataRepository;
import com.genesisoft.transporte.service.criteria.EmployeeComponentsDataCriteria;
import com.genesisoft.transporte.service.dto.EmployeeComponentsDataDTO;
import com.genesisoft.transporte.service.mapper.EmployeeComponentsDataMapper;
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
 * Integration tests for the {@link EmployeeComponentsDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeComponentsDataResourceIT {

    private static final String DEFAULT_DATA_INFO = "AAAAAAAAAA";
    private static final String UPDATED_DATA_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/employee-components-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeComponentsDataRepository employeeComponentsDataRepository;

    @Autowired
    private EmployeeComponentsDataMapper employeeComponentsDataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeComponentsDataMockMvc;

    private EmployeeComponentsData employeeComponentsData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeComponentsData createEntity(EntityManager em) {
        EmployeeComponentsData employeeComponentsData = new EmployeeComponentsData().dataInfo(DEFAULT_DATA_INFO);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeComponentsData.setEmployee(employees);
        return employeeComponentsData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeComponentsData createUpdatedEntity(EntityManager em) {
        EmployeeComponentsData employeeComponentsData = new EmployeeComponentsData().dataInfo(UPDATED_DATA_INFO);
        // Add required entity
        Employees employees;
        if (TestUtil.findAll(em, Employees.class).isEmpty()) {
            employees = EmployeesResourceIT.createUpdatedEntity(em);
            em.persist(employees);
            em.flush();
        } else {
            employees = TestUtil.findAll(em, Employees.class).get(0);
        }
        employeeComponentsData.setEmployee(employees);
        return employeeComponentsData;
    }

    @BeforeEach
    public void initTest() {
        employeeComponentsData = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeComponentsData() throws Exception {
        int databaseSizeBeforeCreate = employeeComponentsDataRepository.findAll().size();
        // Create the EmployeeComponentsData
        EmployeeComponentsDataDTO employeeComponentsDataDTO = employeeComponentsDataMapper.toDto(employeeComponentsData);
        restEmployeeComponentsDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeComponentsDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeComponentsData in the database
        List<EmployeeComponentsData> employeeComponentsDataList = employeeComponentsDataRepository.findAll();
        assertThat(employeeComponentsDataList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeComponentsData testEmployeeComponentsData = employeeComponentsDataList.get(employeeComponentsDataList.size() - 1);
        assertThat(testEmployeeComponentsData.getDataInfo()).isEqualTo(DEFAULT_DATA_INFO);
    }

    @Test
    @Transactional
    void createEmployeeComponentsDataWithExistingId() throws Exception {
        // Create the EmployeeComponentsData with an existing ID
        employeeComponentsData.setId(1L);
        EmployeeComponentsDataDTO employeeComponentsDataDTO = employeeComponentsDataMapper.toDto(employeeComponentsData);

        int databaseSizeBeforeCreate = employeeComponentsDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeComponentsDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeComponentsDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeComponentsData in the database
        List<EmployeeComponentsData> employeeComponentsDataList = employeeComponentsDataRepository.findAll();
        assertThat(employeeComponentsDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmployeeComponentsData() throws Exception {
        // Initialize the database
        employeeComponentsDataRepository.saveAndFlush(employeeComponentsData);

        // Get all the employeeComponentsDataList
        restEmployeeComponentsDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeComponentsData.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataInfo").value(hasItem(DEFAULT_DATA_INFO)));
    }

    @Test
    @Transactional
    void getEmployeeComponentsData() throws Exception {
        // Initialize the database
        employeeComponentsDataRepository.saveAndFlush(employeeComponentsData);

        // Get the employeeComponentsData
        restEmployeeComponentsDataMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeComponentsData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeComponentsData.getId().intValue()))
            .andExpect(jsonPath("$.dataInfo").value(DEFAULT_DATA_INFO));
    }

    @Test
    @Transactional
    void getEmployeeComponentsDataByIdFiltering() throws Exception {
        // Initialize the database
        employeeComponentsDataRepository.saveAndFlush(employeeComponentsData);

        Long id = employeeComponentsData.getId();

        defaultEmployeeComponentsDataShouldBeFound("id.equals=" + id);
        defaultEmployeeComponentsDataShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeComponentsDataShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeComponentsDataShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeComponentsDataShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeComponentsDataShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeComponentsDataByDataInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeComponentsDataRepository.saveAndFlush(employeeComponentsData);

        // Get all the employeeComponentsDataList where dataInfo equals to DEFAULT_DATA_INFO
        defaultEmployeeComponentsDataShouldBeFound("dataInfo.equals=" + DEFAULT_DATA_INFO);

        // Get all the employeeComponentsDataList where dataInfo equals to UPDATED_DATA_INFO
        defaultEmployeeComponentsDataShouldNotBeFound("dataInfo.equals=" + UPDATED_DATA_INFO);
    }

    @Test
    @Transactional
    void getAllEmployeeComponentsDataByDataInfoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeComponentsDataRepository.saveAndFlush(employeeComponentsData);

        // Get all the employeeComponentsDataList where dataInfo not equals to DEFAULT_DATA_INFO
        defaultEmployeeComponentsDataShouldNotBeFound("dataInfo.notEquals=" + DEFAULT_DATA_INFO);

        // Get all the employeeComponentsDataList where dataInfo not equals to UPDATED_DATA_INFO
        defaultEmployeeComponentsDataShouldBeFound("dataInfo.notEquals=" + UPDATED_DATA_INFO);
    }

    @Test
    @Transactional
    void getAllEmployeeComponentsDataByDataInfoIsInShouldWork() throws Exception {
        // Initialize the database
        employeeComponentsDataRepository.saveAndFlush(employeeComponentsData);

        // Get all the employeeComponentsDataList where dataInfo in DEFAULT_DATA_INFO or UPDATED_DATA_INFO
        defaultEmployeeComponentsDataShouldBeFound("dataInfo.in=" + DEFAULT_DATA_INFO + "," + UPDATED_DATA_INFO);

        // Get all the employeeComponentsDataList where dataInfo equals to UPDATED_DATA_INFO
        defaultEmployeeComponentsDataShouldNotBeFound("dataInfo.in=" + UPDATED_DATA_INFO);
    }

    @Test
    @Transactional
    void getAllEmployeeComponentsDataByDataInfoIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeComponentsDataRepository.saveAndFlush(employeeComponentsData);

        // Get all the employeeComponentsDataList where dataInfo is not null
        defaultEmployeeComponentsDataShouldBeFound("dataInfo.specified=true");

        // Get all the employeeComponentsDataList where dataInfo is null
        defaultEmployeeComponentsDataShouldNotBeFound("dataInfo.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeComponentsDataByDataInfoContainsSomething() throws Exception {
        // Initialize the database
        employeeComponentsDataRepository.saveAndFlush(employeeComponentsData);

        // Get all the employeeComponentsDataList where dataInfo contains DEFAULT_DATA_INFO
        defaultEmployeeComponentsDataShouldBeFound("dataInfo.contains=" + DEFAULT_DATA_INFO);

        // Get all the employeeComponentsDataList where dataInfo contains UPDATED_DATA_INFO
        defaultEmployeeComponentsDataShouldNotBeFound("dataInfo.contains=" + UPDATED_DATA_INFO);
    }

    @Test
    @Transactional
    void getAllEmployeeComponentsDataByDataInfoNotContainsSomething() throws Exception {
        // Initialize the database
        employeeComponentsDataRepository.saveAndFlush(employeeComponentsData);

        // Get all the employeeComponentsDataList where dataInfo does not contain DEFAULT_DATA_INFO
        defaultEmployeeComponentsDataShouldNotBeFound("dataInfo.doesNotContain=" + DEFAULT_DATA_INFO);

        // Get all the employeeComponentsDataList where dataInfo does not contain UPDATED_DATA_INFO
        defaultEmployeeComponentsDataShouldBeFound("dataInfo.doesNotContain=" + UPDATED_DATA_INFO);
    }

    @Test
    @Transactional
    void getAllEmployeeComponentsDataByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeComponentsDataRepository.saveAndFlush(employeeComponentsData);
        Employees employee = EmployeesResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        employeeComponentsData.setEmployee(employee);
        employeeComponentsDataRepository.saveAndFlush(employeeComponentsData);
        Long employeeId = employee.getId();

        // Get all the employeeComponentsDataList where employee equals to employeeId
        defaultEmployeeComponentsDataShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeComponentsDataList where employee equals to (employeeId + 1)
        defaultEmployeeComponentsDataShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeComponentsDataShouldBeFound(String filter) throws Exception {
        restEmployeeComponentsDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeComponentsData.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataInfo").value(hasItem(DEFAULT_DATA_INFO)));

        // Check, that the count call also returns 1
        restEmployeeComponentsDataMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeComponentsDataShouldNotBeFound(String filter) throws Exception {
        restEmployeeComponentsDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeComponentsDataMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeComponentsData() throws Exception {
        // Get the employeeComponentsData
        restEmployeeComponentsDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmployeeComponentsData() throws Exception {
        // Initialize the database
        employeeComponentsDataRepository.saveAndFlush(employeeComponentsData);

        int databaseSizeBeforeUpdate = employeeComponentsDataRepository.findAll().size();

        // Update the employeeComponentsData
        EmployeeComponentsData updatedEmployeeComponentsData = employeeComponentsDataRepository
            .findById(employeeComponentsData.getId())
            .get();
        // Disconnect from session so that the updates on updatedEmployeeComponentsData are not directly saved in db
        em.detach(updatedEmployeeComponentsData);
        updatedEmployeeComponentsData.dataInfo(UPDATED_DATA_INFO);
        EmployeeComponentsDataDTO employeeComponentsDataDTO = employeeComponentsDataMapper.toDto(updatedEmployeeComponentsData);

        restEmployeeComponentsDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeComponentsDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeComponentsDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeComponentsData in the database
        List<EmployeeComponentsData> employeeComponentsDataList = employeeComponentsDataRepository.findAll();
        assertThat(employeeComponentsDataList).hasSize(databaseSizeBeforeUpdate);
        EmployeeComponentsData testEmployeeComponentsData = employeeComponentsDataList.get(employeeComponentsDataList.size() - 1);
        assertThat(testEmployeeComponentsData.getDataInfo()).isEqualTo(UPDATED_DATA_INFO);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeComponentsData() throws Exception {
        int databaseSizeBeforeUpdate = employeeComponentsDataRepository.findAll().size();
        employeeComponentsData.setId(count.incrementAndGet());

        // Create the EmployeeComponentsData
        EmployeeComponentsDataDTO employeeComponentsDataDTO = employeeComponentsDataMapper.toDto(employeeComponentsData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeComponentsDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeComponentsDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeComponentsDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeComponentsData in the database
        List<EmployeeComponentsData> employeeComponentsDataList = employeeComponentsDataRepository.findAll();
        assertThat(employeeComponentsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeComponentsData() throws Exception {
        int databaseSizeBeforeUpdate = employeeComponentsDataRepository.findAll().size();
        employeeComponentsData.setId(count.incrementAndGet());

        // Create the EmployeeComponentsData
        EmployeeComponentsDataDTO employeeComponentsDataDTO = employeeComponentsDataMapper.toDto(employeeComponentsData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeComponentsDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeComponentsDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeComponentsData in the database
        List<EmployeeComponentsData> employeeComponentsDataList = employeeComponentsDataRepository.findAll();
        assertThat(employeeComponentsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeComponentsData() throws Exception {
        int databaseSizeBeforeUpdate = employeeComponentsDataRepository.findAll().size();
        employeeComponentsData.setId(count.incrementAndGet());

        // Create the EmployeeComponentsData
        EmployeeComponentsDataDTO employeeComponentsDataDTO = employeeComponentsDataMapper.toDto(employeeComponentsData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeComponentsDataMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeComponentsDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeComponentsData in the database
        List<EmployeeComponentsData> employeeComponentsDataList = employeeComponentsDataRepository.findAll();
        assertThat(employeeComponentsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeComponentsDataWithPatch() throws Exception {
        // Initialize the database
        employeeComponentsDataRepository.saveAndFlush(employeeComponentsData);

        int databaseSizeBeforeUpdate = employeeComponentsDataRepository.findAll().size();

        // Update the employeeComponentsData using partial update
        EmployeeComponentsData partialUpdatedEmployeeComponentsData = new EmployeeComponentsData();
        partialUpdatedEmployeeComponentsData.setId(employeeComponentsData.getId());

        restEmployeeComponentsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeComponentsData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeComponentsData))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeComponentsData in the database
        List<EmployeeComponentsData> employeeComponentsDataList = employeeComponentsDataRepository.findAll();
        assertThat(employeeComponentsDataList).hasSize(databaseSizeBeforeUpdate);
        EmployeeComponentsData testEmployeeComponentsData = employeeComponentsDataList.get(employeeComponentsDataList.size() - 1);
        assertThat(testEmployeeComponentsData.getDataInfo()).isEqualTo(DEFAULT_DATA_INFO);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeComponentsDataWithPatch() throws Exception {
        // Initialize the database
        employeeComponentsDataRepository.saveAndFlush(employeeComponentsData);

        int databaseSizeBeforeUpdate = employeeComponentsDataRepository.findAll().size();

        // Update the employeeComponentsData using partial update
        EmployeeComponentsData partialUpdatedEmployeeComponentsData = new EmployeeComponentsData();
        partialUpdatedEmployeeComponentsData.setId(employeeComponentsData.getId());

        partialUpdatedEmployeeComponentsData.dataInfo(UPDATED_DATA_INFO);

        restEmployeeComponentsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeComponentsData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeComponentsData))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeComponentsData in the database
        List<EmployeeComponentsData> employeeComponentsDataList = employeeComponentsDataRepository.findAll();
        assertThat(employeeComponentsDataList).hasSize(databaseSizeBeforeUpdate);
        EmployeeComponentsData testEmployeeComponentsData = employeeComponentsDataList.get(employeeComponentsDataList.size() - 1);
        assertThat(testEmployeeComponentsData.getDataInfo()).isEqualTo(UPDATED_DATA_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeComponentsData() throws Exception {
        int databaseSizeBeforeUpdate = employeeComponentsDataRepository.findAll().size();
        employeeComponentsData.setId(count.incrementAndGet());

        // Create the EmployeeComponentsData
        EmployeeComponentsDataDTO employeeComponentsDataDTO = employeeComponentsDataMapper.toDto(employeeComponentsData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeComponentsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeComponentsDataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeComponentsDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeComponentsData in the database
        List<EmployeeComponentsData> employeeComponentsDataList = employeeComponentsDataRepository.findAll();
        assertThat(employeeComponentsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeComponentsData() throws Exception {
        int databaseSizeBeforeUpdate = employeeComponentsDataRepository.findAll().size();
        employeeComponentsData.setId(count.incrementAndGet());

        // Create the EmployeeComponentsData
        EmployeeComponentsDataDTO employeeComponentsDataDTO = employeeComponentsDataMapper.toDto(employeeComponentsData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeComponentsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeComponentsDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeComponentsData in the database
        List<EmployeeComponentsData> employeeComponentsDataList = employeeComponentsDataRepository.findAll();
        assertThat(employeeComponentsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeComponentsData() throws Exception {
        int databaseSizeBeforeUpdate = employeeComponentsDataRepository.findAll().size();
        employeeComponentsData.setId(count.incrementAndGet());

        // Create the EmployeeComponentsData
        EmployeeComponentsDataDTO employeeComponentsDataDTO = employeeComponentsDataMapper.toDto(employeeComponentsData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeComponentsDataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeComponentsDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeComponentsData in the database
        List<EmployeeComponentsData> employeeComponentsDataList = employeeComponentsDataRepository.findAll();
        assertThat(employeeComponentsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeComponentsData() throws Exception {
        // Initialize the database
        employeeComponentsDataRepository.saveAndFlush(employeeComponentsData);

        int databaseSizeBeforeDelete = employeeComponentsDataRepository.findAll().size();

        // Delete the employeeComponentsData
        restEmployeeComponentsDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeComponentsData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeComponentsData> employeeComponentsDataList = employeeComponentsDataRepository.findAll();
        assertThat(employeeComponentsDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
