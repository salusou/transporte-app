package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.ServiceProvided;
import com.genesisoft.transporte.domain.Suppliers;
import com.genesisoft.transporte.repository.ServiceProvidedRepository;
import com.genesisoft.transporte.service.criteria.ServiceProvidedCriteria;
import com.genesisoft.transporte.service.dto.ServiceProvidedDTO;
import com.genesisoft.transporte.service.mapper.ServiceProvidedMapper;
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
 * Integration tests for the {@link ServiceProvidedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiceProvidedResourceIT {

    private static final String DEFAULT_SERVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/service-provideds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ServiceProvidedRepository serviceProvidedRepository;

    @Autowired
    private ServiceProvidedMapper serviceProvidedMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceProvidedMockMvc;

    private ServiceProvided serviceProvided;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceProvided createEntity(EntityManager em) {
        ServiceProvided serviceProvided = new ServiceProvided().serviceName(DEFAULT_SERVICE_NAME);
        return serviceProvided;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceProvided createUpdatedEntity(EntityManager em) {
        ServiceProvided serviceProvided = new ServiceProvided().serviceName(UPDATED_SERVICE_NAME);
        return serviceProvided;
    }

    @BeforeEach
    public void initTest() {
        serviceProvided = createEntity(em);
    }

    @Test
    @Transactional
    void createServiceProvided() throws Exception {
        int databaseSizeBeforeCreate = serviceProvidedRepository.findAll().size();
        // Create the ServiceProvided
        ServiceProvidedDTO serviceProvidedDTO = serviceProvidedMapper.toDto(serviceProvided);
        restServiceProvidedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serviceProvidedDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ServiceProvided in the database
        List<ServiceProvided> serviceProvidedList = serviceProvidedRepository.findAll();
        assertThat(serviceProvidedList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceProvided testServiceProvided = serviceProvidedList.get(serviceProvidedList.size() - 1);
        assertThat(testServiceProvided.getServiceName()).isEqualTo(DEFAULT_SERVICE_NAME);
    }

    @Test
    @Transactional
    void createServiceProvidedWithExistingId() throws Exception {
        // Create the ServiceProvided with an existing ID
        serviceProvided.setId(1L);
        ServiceProvidedDTO serviceProvidedDTO = serviceProvidedMapper.toDto(serviceProvided);

        int databaseSizeBeforeCreate = serviceProvidedRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceProvidedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serviceProvidedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceProvided in the database
        List<ServiceProvided> serviceProvidedList = serviceProvidedRepository.findAll();
        assertThat(serviceProvidedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkServiceNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceProvidedRepository.findAll().size();
        // set the field null
        serviceProvided.setServiceName(null);

        // Create the ServiceProvided, which fails.
        ServiceProvidedDTO serviceProvidedDTO = serviceProvidedMapper.toDto(serviceProvided);

        restServiceProvidedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serviceProvidedDTO))
            )
            .andExpect(status().isBadRequest());

        List<ServiceProvided> serviceProvidedList = serviceProvidedRepository.findAll();
        assertThat(serviceProvidedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllServiceProvideds() throws Exception {
        // Initialize the database
        serviceProvidedRepository.saveAndFlush(serviceProvided);

        // Get all the serviceProvidedList
        restServiceProvidedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceProvided.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)));
    }

    @Test
    @Transactional
    void getServiceProvided() throws Exception {
        // Initialize the database
        serviceProvidedRepository.saveAndFlush(serviceProvided);

        // Get the serviceProvided
        restServiceProvidedMockMvc
            .perform(get(ENTITY_API_URL_ID, serviceProvided.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceProvided.getId().intValue()))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME));
    }

    @Test
    @Transactional
    void getServiceProvidedsByIdFiltering() throws Exception {
        // Initialize the database
        serviceProvidedRepository.saveAndFlush(serviceProvided);

        Long id = serviceProvided.getId();

        defaultServiceProvidedShouldBeFound("id.equals=" + id);
        defaultServiceProvidedShouldNotBeFound("id.notEquals=" + id);

        defaultServiceProvidedShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultServiceProvidedShouldNotBeFound("id.greaterThan=" + id);

        defaultServiceProvidedShouldBeFound("id.lessThanOrEqual=" + id);
        defaultServiceProvidedShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllServiceProvidedsByServiceNameIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceProvidedRepository.saveAndFlush(serviceProvided);

        // Get all the serviceProvidedList where serviceName equals to DEFAULT_SERVICE_NAME
        defaultServiceProvidedShouldBeFound("serviceName.equals=" + DEFAULT_SERVICE_NAME);

        // Get all the serviceProvidedList where serviceName equals to UPDATED_SERVICE_NAME
        defaultServiceProvidedShouldNotBeFound("serviceName.equals=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    void getAllServiceProvidedsByServiceNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceProvidedRepository.saveAndFlush(serviceProvided);

        // Get all the serviceProvidedList where serviceName not equals to DEFAULT_SERVICE_NAME
        defaultServiceProvidedShouldNotBeFound("serviceName.notEquals=" + DEFAULT_SERVICE_NAME);

        // Get all the serviceProvidedList where serviceName not equals to UPDATED_SERVICE_NAME
        defaultServiceProvidedShouldBeFound("serviceName.notEquals=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    void getAllServiceProvidedsByServiceNameIsInShouldWork() throws Exception {
        // Initialize the database
        serviceProvidedRepository.saveAndFlush(serviceProvided);

        // Get all the serviceProvidedList where serviceName in DEFAULT_SERVICE_NAME or UPDATED_SERVICE_NAME
        defaultServiceProvidedShouldBeFound("serviceName.in=" + DEFAULT_SERVICE_NAME + "," + UPDATED_SERVICE_NAME);

        // Get all the serviceProvidedList where serviceName equals to UPDATED_SERVICE_NAME
        defaultServiceProvidedShouldNotBeFound("serviceName.in=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    void getAllServiceProvidedsByServiceNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceProvidedRepository.saveAndFlush(serviceProvided);

        // Get all the serviceProvidedList where serviceName is not null
        defaultServiceProvidedShouldBeFound("serviceName.specified=true");

        // Get all the serviceProvidedList where serviceName is null
        defaultServiceProvidedShouldNotBeFound("serviceName.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceProvidedsByServiceNameContainsSomething() throws Exception {
        // Initialize the database
        serviceProvidedRepository.saveAndFlush(serviceProvided);

        // Get all the serviceProvidedList where serviceName contains DEFAULT_SERVICE_NAME
        defaultServiceProvidedShouldBeFound("serviceName.contains=" + DEFAULT_SERVICE_NAME);

        // Get all the serviceProvidedList where serviceName contains UPDATED_SERVICE_NAME
        defaultServiceProvidedShouldNotBeFound("serviceName.contains=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    void getAllServiceProvidedsByServiceNameNotContainsSomething() throws Exception {
        // Initialize the database
        serviceProvidedRepository.saveAndFlush(serviceProvided);

        // Get all the serviceProvidedList where serviceName does not contain DEFAULT_SERVICE_NAME
        defaultServiceProvidedShouldNotBeFound("serviceName.doesNotContain=" + DEFAULT_SERVICE_NAME);

        // Get all the serviceProvidedList where serviceName does not contain UPDATED_SERVICE_NAME
        defaultServiceProvidedShouldBeFound("serviceName.doesNotContain=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    void getAllServiceProvidedsBySuppliersIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceProvidedRepository.saveAndFlush(serviceProvided);
        Suppliers suppliers = SuppliersResourceIT.createEntity(em);
        em.persist(suppliers);
        em.flush();
        serviceProvided.addSuppliers(suppliers);
        serviceProvidedRepository.saveAndFlush(serviceProvided);
        Long suppliersId = suppliers.getId();

        // Get all the serviceProvidedList where suppliers equals to suppliersId
        defaultServiceProvidedShouldBeFound("suppliersId.equals=" + suppliersId);

        // Get all the serviceProvidedList where suppliers equals to (suppliersId + 1)
        defaultServiceProvidedShouldNotBeFound("suppliersId.equals=" + (suppliersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceProvidedShouldBeFound(String filter) throws Exception {
        restServiceProvidedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceProvided.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)));

        // Check, that the count call also returns 1
        restServiceProvidedMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceProvidedShouldNotBeFound(String filter) throws Exception {
        restServiceProvidedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceProvidedMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingServiceProvided() throws Exception {
        // Get the serviceProvided
        restServiceProvidedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewServiceProvided() throws Exception {
        // Initialize the database
        serviceProvidedRepository.saveAndFlush(serviceProvided);

        int databaseSizeBeforeUpdate = serviceProvidedRepository.findAll().size();

        // Update the serviceProvided
        ServiceProvided updatedServiceProvided = serviceProvidedRepository.findById(serviceProvided.getId()).get();
        // Disconnect from session so that the updates on updatedServiceProvided are not directly saved in db
        em.detach(updatedServiceProvided);
        updatedServiceProvided.serviceName(UPDATED_SERVICE_NAME);
        ServiceProvidedDTO serviceProvidedDTO = serviceProvidedMapper.toDto(updatedServiceProvided);

        restServiceProvidedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceProvidedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviceProvidedDTO))
            )
            .andExpect(status().isOk());

        // Validate the ServiceProvided in the database
        List<ServiceProvided> serviceProvidedList = serviceProvidedRepository.findAll();
        assertThat(serviceProvidedList).hasSize(databaseSizeBeforeUpdate);
        ServiceProvided testServiceProvided = serviceProvidedList.get(serviceProvidedList.size() - 1);
        assertThat(testServiceProvided.getServiceName()).isEqualTo(UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    void putNonExistingServiceProvided() throws Exception {
        int databaseSizeBeforeUpdate = serviceProvidedRepository.findAll().size();
        serviceProvided.setId(count.incrementAndGet());

        // Create the ServiceProvided
        ServiceProvidedDTO serviceProvidedDTO = serviceProvidedMapper.toDto(serviceProvided);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceProvidedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceProvidedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviceProvidedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceProvided in the database
        List<ServiceProvided> serviceProvidedList = serviceProvidedRepository.findAll();
        assertThat(serviceProvidedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServiceProvided() throws Exception {
        int databaseSizeBeforeUpdate = serviceProvidedRepository.findAll().size();
        serviceProvided.setId(count.incrementAndGet());

        // Create the ServiceProvided
        ServiceProvidedDTO serviceProvidedDTO = serviceProvidedMapper.toDto(serviceProvided);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceProvidedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(serviceProvidedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceProvided in the database
        List<ServiceProvided> serviceProvidedList = serviceProvidedRepository.findAll();
        assertThat(serviceProvidedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServiceProvided() throws Exception {
        int databaseSizeBeforeUpdate = serviceProvidedRepository.findAll().size();
        serviceProvided.setId(count.incrementAndGet());

        // Create the ServiceProvided
        ServiceProvidedDTO serviceProvidedDTO = serviceProvidedMapper.toDto(serviceProvided);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceProvidedMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(serviceProvidedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceProvided in the database
        List<ServiceProvided> serviceProvidedList = serviceProvidedRepository.findAll();
        assertThat(serviceProvidedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiceProvidedWithPatch() throws Exception {
        // Initialize the database
        serviceProvidedRepository.saveAndFlush(serviceProvided);

        int databaseSizeBeforeUpdate = serviceProvidedRepository.findAll().size();

        // Update the serviceProvided using partial update
        ServiceProvided partialUpdatedServiceProvided = new ServiceProvided();
        partialUpdatedServiceProvided.setId(serviceProvided.getId());

        restServiceProvidedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceProvided.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServiceProvided))
            )
            .andExpect(status().isOk());

        // Validate the ServiceProvided in the database
        List<ServiceProvided> serviceProvidedList = serviceProvidedRepository.findAll();
        assertThat(serviceProvidedList).hasSize(databaseSizeBeforeUpdate);
        ServiceProvided testServiceProvided = serviceProvidedList.get(serviceProvidedList.size() - 1);
        assertThat(testServiceProvided.getServiceName()).isEqualTo(DEFAULT_SERVICE_NAME);
    }

    @Test
    @Transactional
    void fullUpdateServiceProvidedWithPatch() throws Exception {
        // Initialize the database
        serviceProvidedRepository.saveAndFlush(serviceProvided);

        int databaseSizeBeforeUpdate = serviceProvidedRepository.findAll().size();

        // Update the serviceProvided using partial update
        ServiceProvided partialUpdatedServiceProvided = new ServiceProvided();
        partialUpdatedServiceProvided.setId(serviceProvided.getId());

        partialUpdatedServiceProvided.serviceName(UPDATED_SERVICE_NAME);

        restServiceProvidedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceProvided.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServiceProvided))
            )
            .andExpect(status().isOk());

        // Validate the ServiceProvided in the database
        List<ServiceProvided> serviceProvidedList = serviceProvidedRepository.findAll();
        assertThat(serviceProvidedList).hasSize(databaseSizeBeforeUpdate);
        ServiceProvided testServiceProvided = serviceProvidedList.get(serviceProvidedList.size() - 1);
        assertThat(testServiceProvided.getServiceName()).isEqualTo(UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingServiceProvided() throws Exception {
        int databaseSizeBeforeUpdate = serviceProvidedRepository.findAll().size();
        serviceProvided.setId(count.incrementAndGet());

        // Create the ServiceProvided
        ServiceProvidedDTO serviceProvidedDTO = serviceProvidedMapper.toDto(serviceProvided);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceProvidedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceProvidedDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(serviceProvidedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceProvided in the database
        List<ServiceProvided> serviceProvidedList = serviceProvidedRepository.findAll();
        assertThat(serviceProvidedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServiceProvided() throws Exception {
        int databaseSizeBeforeUpdate = serviceProvidedRepository.findAll().size();
        serviceProvided.setId(count.incrementAndGet());

        // Create the ServiceProvided
        ServiceProvidedDTO serviceProvidedDTO = serviceProvidedMapper.toDto(serviceProvided);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceProvidedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(serviceProvidedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceProvided in the database
        List<ServiceProvided> serviceProvidedList = serviceProvidedRepository.findAll();
        assertThat(serviceProvidedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServiceProvided() throws Exception {
        int databaseSizeBeforeUpdate = serviceProvidedRepository.findAll().size();
        serviceProvided.setId(count.incrementAndGet());

        // Create the ServiceProvided
        ServiceProvidedDTO serviceProvidedDTO = serviceProvidedMapper.toDto(serviceProvided);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceProvidedMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(serviceProvidedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceProvided in the database
        List<ServiceProvided> serviceProvidedList = serviceProvidedRepository.findAll();
        assertThat(serviceProvidedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServiceProvided() throws Exception {
        // Initialize the database
        serviceProvidedRepository.saveAndFlush(serviceProvided);

        int databaseSizeBeforeDelete = serviceProvidedRepository.findAll().size();

        // Delete the serviceProvided
        restServiceProvidedMockMvc
            .perform(delete(ENTITY_API_URL_ID, serviceProvided.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceProvided> serviceProvidedList = serviceProvidedRepository.findAll();
        assertThat(serviceProvidedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
