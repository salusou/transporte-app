package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Affiliates;
import com.genesisoft.transporte.domain.Cities;
import com.genesisoft.transporte.domain.Companies;
import com.genesisoft.transporte.domain.Countries;
import com.genesisoft.transporte.domain.Insurances;
import com.genesisoft.transporte.domain.StateProvinces;
import com.genesisoft.transporte.repository.StateProvincesRepository;
import com.genesisoft.transporte.service.criteria.StateProvincesCriteria;
import com.genesisoft.transporte.service.dto.StateProvincesDTO;
import com.genesisoft.transporte.service.mapper.StateProvincesMapper;
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
 * Integration tests for the {@link StateProvincesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StateProvincesResourceIT {

    private static final String DEFAULT_STATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STATE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ABBREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_ABBREVIATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/state-provinces";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StateProvincesRepository stateProvincesRepository;

    @Autowired
    private StateProvincesMapper stateProvincesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStateProvincesMockMvc;

    private StateProvinces stateProvinces;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StateProvinces createEntity(EntityManager em) {
        StateProvinces stateProvinces = new StateProvinces().stateName(DEFAULT_STATE_NAME).abbreviation(DEFAULT_ABBREVIATION);
        // Add required entity
        Countries countries;
        if (TestUtil.findAll(em, Countries.class).isEmpty()) {
            countries = CountriesResourceIT.createEntity(em);
            em.persist(countries);
            em.flush();
        } else {
            countries = TestUtil.findAll(em, Countries.class).get(0);
        }
        stateProvinces.setCountries(countries);
        return stateProvinces;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StateProvinces createUpdatedEntity(EntityManager em) {
        StateProvinces stateProvinces = new StateProvinces().stateName(UPDATED_STATE_NAME).abbreviation(UPDATED_ABBREVIATION);
        // Add required entity
        Countries countries;
        if (TestUtil.findAll(em, Countries.class).isEmpty()) {
            countries = CountriesResourceIT.createUpdatedEntity(em);
            em.persist(countries);
            em.flush();
        } else {
            countries = TestUtil.findAll(em, Countries.class).get(0);
        }
        stateProvinces.setCountries(countries);
        return stateProvinces;
    }

    @BeforeEach
    public void initTest() {
        stateProvinces = createEntity(em);
    }

    @Test
    @Transactional
    void createStateProvinces() throws Exception {
        int databaseSizeBeforeCreate = stateProvincesRepository.findAll().size();
        // Create the StateProvinces
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);
        restStateProvincesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the StateProvinces in the database
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeCreate + 1);
        StateProvinces testStateProvinces = stateProvincesList.get(stateProvincesList.size() - 1);
        assertThat(testStateProvinces.getStateName()).isEqualTo(DEFAULT_STATE_NAME);
        assertThat(testStateProvinces.getAbbreviation()).isEqualTo(DEFAULT_ABBREVIATION);
    }

    @Test
    @Transactional
    void createStateProvincesWithExistingId() throws Exception {
        // Create the StateProvinces with an existing ID
        stateProvinces.setId(1L);
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        int databaseSizeBeforeCreate = stateProvincesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStateProvincesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StateProvinces in the database
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStateNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateProvincesRepository.findAll().size();
        // set the field null
        stateProvinces.setStateName(null);

        // Create the StateProvinces, which fails.
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        restStateProvincesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO))
            )
            .andExpect(status().isBadRequest());

        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAbbreviationIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateProvincesRepository.findAll().size();
        // set the field null
        stateProvinces.setAbbreviation(null);

        // Create the StateProvinces, which fails.
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        restStateProvincesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO))
            )
            .andExpect(status().isBadRequest());

        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStateProvinces() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList
        restStateProvincesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateProvinces.getId().intValue())))
            .andExpect(jsonPath("$.[*].stateName").value(hasItem(DEFAULT_STATE_NAME)))
            .andExpect(jsonPath("$.[*].abbreviation").value(hasItem(DEFAULT_ABBREVIATION)));
    }

    @Test
    @Transactional
    void getStateProvinces() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get the stateProvinces
        restStateProvincesMockMvc
            .perform(get(ENTITY_API_URL_ID, stateProvinces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stateProvinces.getId().intValue()))
            .andExpect(jsonPath("$.stateName").value(DEFAULT_STATE_NAME))
            .andExpect(jsonPath("$.abbreviation").value(DEFAULT_ABBREVIATION));
    }

    @Test
    @Transactional
    void getStateProvincesByIdFiltering() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        Long id = stateProvinces.getId();

        defaultStateProvincesShouldBeFound("id.equals=" + id);
        defaultStateProvincesShouldNotBeFound("id.notEquals=" + id);

        defaultStateProvincesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStateProvincesShouldNotBeFound("id.greaterThan=" + id);

        defaultStateProvincesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStateProvincesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStateProvincesByStateNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where stateName equals to DEFAULT_STATE_NAME
        defaultStateProvincesShouldBeFound("stateName.equals=" + DEFAULT_STATE_NAME);

        // Get all the stateProvincesList where stateName equals to UPDATED_STATE_NAME
        defaultStateProvincesShouldNotBeFound("stateName.equals=" + UPDATED_STATE_NAME);
    }

    @Test
    @Transactional
    void getAllStateProvincesByStateNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where stateName not equals to DEFAULT_STATE_NAME
        defaultStateProvincesShouldNotBeFound("stateName.notEquals=" + DEFAULT_STATE_NAME);

        // Get all the stateProvincesList where stateName not equals to UPDATED_STATE_NAME
        defaultStateProvincesShouldBeFound("stateName.notEquals=" + UPDATED_STATE_NAME);
    }

    @Test
    @Transactional
    void getAllStateProvincesByStateNameIsInShouldWork() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where stateName in DEFAULT_STATE_NAME or UPDATED_STATE_NAME
        defaultStateProvincesShouldBeFound("stateName.in=" + DEFAULT_STATE_NAME + "," + UPDATED_STATE_NAME);

        // Get all the stateProvincesList where stateName equals to UPDATED_STATE_NAME
        defaultStateProvincesShouldNotBeFound("stateName.in=" + UPDATED_STATE_NAME);
    }

    @Test
    @Transactional
    void getAllStateProvincesByStateNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where stateName is not null
        defaultStateProvincesShouldBeFound("stateName.specified=true");

        // Get all the stateProvincesList where stateName is null
        defaultStateProvincesShouldNotBeFound("stateName.specified=false");
    }

    @Test
    @Transactional
    void getAllStateProvincesByStateNameContainsSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where stateName contains DEFAULT_STATE_NAME
        defaultStateProvincesShouldBeFound("stateName.contains=" + DEFAULT_STATE_NAME);

        // Get all the stateProvincesList where stateName contains UPDATED_STATE_NAME
        defaultStateProvincesShouldNotBeFound("stateName.contains=" + UPDATED_STATE_NAME);
    }

    @Test
    @Transactional
    void getAllStateProvincesByStateNameNotContainsSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where stateName does not contain DEFAULT_STATE_NAME
        defaultStateProvincesShouldNotBeFound("stateName.doesNotContain=" + DEFAULT_STATE_NAME);

        // Get all the stateProvincesList where stateName does not contain UPDATED_STATE_NAME
        defaultStateProvincesShouldBeFound("stateName.doesNotContain=" + UPDATED_STATE_NAME);
    }

    @Test
    @Transactional
    void getAllStateProvincesByAbbreviationIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where abbreviation equals to DEFAULT_ABBREVIATION
        defaultStateProvincesShouldBeFound("abbreviation.equals=" + DEFAULT_ABBREVIATION);

        // Get all the stateProvincesList where abbreviation equals to UPDATED_ABBREVIATION
        defaultStateProvincesShouldNotBeFound("abbreviation.equals=" + UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    void getAllStateProvincesByAbbreviationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where abbreviation not equals to DEFAULT_ABBREVIATION
        defaultStateProvincesShouldNotBeFound("abbreviation.notEquals=" + DEFAULT_ABBREVIATION);

        // Get all the stateProvincesList where abbreviation not equals to UPDATED_ABBREVIATION
        defaultStateProvincesShouldBeFound("abbreviation.notEquals=" + UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    void getAllStateProvincesByAbbreviationIsInShouldWork() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where abbreviation in DEFAULT_ABBREVIATION or UPDATED_ABBREVIATION
        defaultStateProvincesShouldBeFound("abbreviation.in=" + DEFAULT_ABBREVIATION + "," + UPDATED_ABBREVIATION);

        // Get all the stateProvincesList where abbreviation equals to UPDATED_ABBREVIATION
        defaultStateProvincesShouldNotBeFound("abbreviation.in=" + UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    void getAllStateProvincesByAbbreviationIsNullOrNotNull() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where abbreviation is not null
        defaultStateProvincesShouldBeFound("abbreviation.specified=true");

        // Get all the stateProvincesList where abbreviation is null
        defaultStateProvincesShouldNotBeFound("abbreviation.specified=false");
    }

    @Test
    @Transactional
    void getAllStateProvincesByAbbreviationContainsSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where abbreviation contains DEFAULT_ABBREVIATION
        defaultStateProvincesShouldBeFound("abbreviation.contains=" + DEFAULT_ABBREVIATION);

        // Get all the stateProvincesList where abbreviation contains UPDATED_ABBREVIATION
        defaultStateProvincesShouldNotBeFound("abbreviation.contains=" + UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    void getAllStateProvincesByAbbreviationNotContainsSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        // Get all the stateProvincesList where abbreviation does not contain DEFAULT_ABBREVIATION
        defaultStateProvincesShouldNotBeFound("abbreviation.doesNotContain=" + DEFAULT_ABBREVIATION);

        // Get all the stateProvincesList where abbreviation does not contain UPDATED_ABBREVIATION
        defaultStateProvincesShouldBeFound("abbreviation.doesNotContain=" + UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    void getAllStateProvincesByCitiesIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);
        Cities cities = CitiesResourceIT.createEntity(em);
        em.persist(cities);
        em.flush();
        stateProvinces.addCities(cities);
        stateProvincesRepository.saveAndFlush(stateProvinces);
        Long citiesId = cities.getId();

        // Get all the stateProvincesList where cities equals to citiesId
        defaultStateProvincesShouldBeFound("citiesId.equals=" + citiesId);

        // Get all the stateProvincesList where cities equals to (citiesId + 1)
        defaultStateProvincesShouldNotBeFound("citiesId.equals=" + (citiesId + 1));
    }

    @Test
    @Transactional
    void getAllStateProvincesByCompaniesIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);
        Companies companies = CompaniesResourceIT.createEntity(em);
        em.persist(companies);
        em.flush();
        stateProvinces.addCompanies(companies);
        stateProvincesRepository.saveAndFlush(stateProvinces);
        Long companiesId = companies.getId();

        // Get all the stateProvincesList where companies equals to companiesId
        defaultStateProvincesShouldBeFound("companiesId.equals=" + companiesId);

        // Get all the stateProvincesList where companies equals to (companiesId + 1)
        defaultStateProvincesShouldNotBeFound("companiesId.equals=" + (companiesId + 1));
    }

    @Test
    @Transactional
    void getAllStateProvincesByAffiliatesIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);
        Affiliates affiliates = AffiliatesResourceIT.createEntity(em);
        em.persist(affiliates);
        em.flush();
        stateProvinces.addAffiliates(affiliates);
        stateProvincesRepository.saveAndFlush(stateProvinces);
        Long affiliatesId = affiliates.getId();

        // Get all the stateProvincesList where affiliates equals to affiliatesId
        defaultStateProvincesShouldBeFound("affiliatesId.equals=" + affiliatesId);

        // Get all the stateProvincesList where affiliates equals to (affiliatesId + 1)
        defaultStateProvincesShouldNotBeFound("affiliatesId.equals=" + (affiliatesId + 1));
    }

    @Test
    @Transactional
    void getAllStateProvincesByToInsurancesIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);
        Insurances toInsurances = InsurancesResourceIT.createEntity(em);
        em.persist(toInsurances);
        em.flush();
        stateProvinces.addToInsurances(toInsurances);
        stateProvincesRepository.saveAndFlush(stateProvinces);
        Long toInsurancesId = toInsurances.getId();

        // Get all the stateProvincesList where toInsurances equals to toInsurancesId
        defaultStateProvincesShouldBeFound("toInsurancesId.equals=" + toInsurancesId);

        // Get all the stateProvincesList where toInsurances equals to (toInsurancesId + 1)
        defaultStateProvincesShouldNotBeFound("toInsurancesId.equals=" + (toInsurancesId + 1));
    }

    @Test
    @Transactional
    void getAllStateProvincesByForInsurancesIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);
        Insurances forInsurances = InsurancesResourceIT.createEntity(em);
        em.persist(forInsurances);
        em.flush();
        stateProvinces.addForInsurances(forInsurances);
        stateProvincesRepository.saveAndFlush(stateProvinces);
        Long forInsurancesId = forInsurances.getId();

        // Get all the stateProvincesList where forInsurances equals to forInsurancesId
        defaultStateProvincesShouldBeFound("forInsurancesId.equals=" + forInsurancesId);

        // Get all the stateProvincesList where forInsurances equals to (forInsurancesId + 1)
        defaultStateProvincesShouldNotBeFound("forInsurancesId.equals=" + (forInsurancesId + 1));
    }

    @Test
    @Transactional
    void getAllStateProvincesByCountriesIsEqualToSomething() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);
        Countries countries = CountriesResourceIT.createEntity(em);
        em.persist(countries);
        em.flush();
        stateProvinces.setCountries(countries);
        stateProvincesRepository.saveAndFlush(stateProvinces);
        Long countriesId = countries.getId();

        // Get all the stateProvincesList where countries equals to countriesId
        defaultStateProvincesShouldBeFound("countriesId.equals=" + countriesId);

        // Get all the stateProvincesList where countries equals to (countriesId + 1)
        defaultStateProvincesShouldNotBeFound("countriesId.equals=" + (countriesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStateProvincesShouldBeFound(String filter) throws Exception {
        restStateProvincesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateProvinces.getId().intValue())))
            .andExpect(jsonPath("$.[*].stateName").value(hasItem(DEFAULT_STATE_NAME)))
            .andExpect(jsonPath("$.[*].abbreviation").value(hasItem(DEFAULT_ABBREVIATION)));

        // Check, that the count call also returns 1
        restStateProvincesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStateProvincesShouldNotBeFound(String filter) throws Exception {
        restStateProvincesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStateProvincesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStateProvinces() throws Exception {
        // Get the stateProvinces
        restStateProvincesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStateProvinces() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        int databaseSizeBeforeUpdate = stateProvincesRepository.findAll().size();

        // Update the stateProvinces
        StateProvinces updatedStateProvinces = stateProvincesRepository.findById(stateProvinces.getId()).get();
        // Disconnect from session so that the updates on updatedStateProvinces are not directly saved in db
        em.detach(updatedStateProvinces);
        updatedStateProvinces.stateName(UPDATED_STATE_NAME).abbreviation(UPDATED_ABBREVIATION);
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(updatedStateProvinces);

        restStateProvincesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stateProvincesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO))
            )
            .andExpect(status().isOk());

        // Validate the StateProvinces in the database
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeUpdate);
        StateProvinces testStateProvinces = stateProvincesList.get(stateProvincesList.size() - 1);
        assertThat(testStateProvinces.getStateName()).isEqualTo(UPDATED_STATE_NAME);
        assertThat(testStateProvinces.getAbbreviation()).isEqualTo(UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    void putNonExistingStateProvinces() throws Exception {
        int databaseSizeBeforeUpdate = stateProvincesRepository.findAll().size();
        stateProvinces.setId(count.incrementAndGet());

        // Create the StateProvinces
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStateProvincesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stateProvincesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StateProvinces in the database
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStateProvinces() throws Exception {
        int databaseSizeBeforeUpdate = stateProvincesRepository.findAll().size();
        stateProvinces.setId(count.incrementAndGet());

        // Create the StateProvinces
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStateProvincesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StateProvinces in the database
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStateProvinces() throws Exception {
        int databaseSizeBeforeUpdate = stateProvincesRepository.findAll().size();
        stateProvinces.setId(count.incrementAndGet());

        // Create the StateProvinces
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStateProvincesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StateProvinces in the database
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStateProvincesWithPatch() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        int databaseSizeBeforeUpdate = stateProvincesRepository.findAll().size();

        // Update the stateProvinces using partial update
        StateProvinces partialUpdatedStateProvinces = new StateProvinces();
        partialUpdatedStateProvinces.setId(stateProvinces.getId());

        restStateProvincesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStateProvinces.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStateProvinces))
            )
            .andExpect(status().isOk());

        // Validate the StateProvinces in the database
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeUpdate);
        StateProvinces testStateProvinces = stateProvincesList.get(stateProvincesList.size() - 1);
        assertThat(testStateProvinces.getStateName()).isEqualTo(DEFAULT_STATE_NAME);
        assertThat(testStateProvinces.getAbbreviation()).isEqualTo(DEFAULT_ABBREVIATION);
    }

    @Test
    @Transactional
    void fullUpdateStateProvincesWithPatch() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        int databaseSizeBeforeUpdate = stateProvincesRepository.findAll().size();

        // Update the stateProvinces using partial update
        StateProvinces partialUpdatedStateProvinces = new StateProvinces();
        partialUpdatedStateProvinces.setId(stateProvinces.getId());

        partialUpdatedStateProvinces.stateName(UPDATED_STATE_NAME).abbreviation(UPDATED_ABBREVIATION);

        restStateProvincesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStateProvinces.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStateProvinces))
            )
            .andExpect(status().isOk());

        // Validate the StateProvinces in the database
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeUpdate);
        StateProvinces testStateProvinces = stateProvincesList.get(stateProvincesList.size() - 1);
        assertThat(testStateProvinces.getStateName()).isEqualTo(UPDATED_STATE_NAME);
        assertThat(testStateProvinces.getAbbreviation()).isEqualTo(UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    void patchNonExistingStateProvinces() throws Exception {
        int databaseSizeBeforeUpdate = stateProvincesRepository.findAll().size();
        stateProvinces.setId(count.incrementAndGet());

        // Create the StateProvinces
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStateProvincesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stateProvincesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StateProvinces in the database
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStateProvinces() throws Exception {
        int databaseSizeBeforeUpdate = stateProvincesRepository.findAll().size();
        stateProvinces.setId(count.incrementAndGet());

        // Create the StateProvinces
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStateProvincesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StateProvinces in the database
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStateProvinces() throws Exception {
        int databaseSizeBeforeUpdate = stateProvincesRepository.findAll().size();
        stateProvinces.setId(count.incrementAndGet());

        // Create the StateProvinces
        StateProvincesDTO stateProvincesDTO = stateProvincesMapper.toDto(stateProvinces);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStateProvincesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stateProvincesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StateProvinces in the database
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStateProvinces() throws Exception {
        // Initialize the database
        stateProvincesRepository.saveAndFlush(stateProvinces);

        int databaseSizeBeforeDelete = stateProvincesRepository.findAll().size();

        // Delete the stateProvinces
        restStateProvincesMockMvc
            .perform(delete(ENTITY_API_URL_ID, stateProvinces.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StateProvinces> stateProvincesList = stateProvincesRepository.findAll();
        assertThat(stateProvincesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
