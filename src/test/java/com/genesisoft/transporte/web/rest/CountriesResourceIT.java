package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.Countries;
import com.genesisoft.transporte.domain.StateProvinces;
import com.genesisoft.transporte.repository.CountriesRepository;
import com.genesisoft.transporte.service.criteria.CountriesCriteria;
import com.genesisoft.transporte.service.dto.CountriesDTO;
import com.genesisoft.transporte.service.mapper.CountriesMapper;
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
 * Integration tests for the {@link CountriesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CountriesResourceIT {

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ISO_2 = "AAAAAAAAAA";
    private static final String UPDATED_ISO_2 = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODE_AREA = 1;
    private static final Integer UPDATED_CODE_AREA = 2;
    private static final Integer SMALLER_CODE_AREA = 1 - 1;

    private static final String DEFAULT_LANGUAGE = "AAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBB";

    private static final String ENTITY_API_URL = "/api/countries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CountriesRepository countriesRepository;

    @Autowired
    private CountriesMapper countriesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCountriesMockMvc;

    private Countries countries;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Countries createEntity(EntityManager em) {
        Countries countries = new Countries()
            .countryName(DEFAULT_COUNTRY_NAME)
            .iso2(DEFAULT_ISO_2)
            .codeArea(DEFAULT_CODE_AREA)
            .language(DEFAULT_LANGUAGE);
        return countries;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Countries createUpdatedEntity(EntityManager em) {
        Countries countries = new Countries()
            .countryName(UPDATED_COUNTRY_NAME)
            .iso2(UPDATED_ISO_2)
            .codeArea(UPDATED_CODE_AREA)
            .language(UPDATED_LANGUAGE);
        return countries;
    }

    @BeforeEach
    public void initTest() {
        countries = createEntity(em);
    }

    @Test
    @Transactional
    void createCountries() throws Exception {
        int databaseSizeBeforeCreate = countriesRepository.findAll().size();
        // Create the Countries
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);
        restCountriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isCreated());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeCreate + 1);
        Countries testCountries = countriesList.get(countriesList.size() - 1);
        assertThat(testCountries.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testCountries.getIso2()).isEqualTo(DEFAULT_ISO_2);
        assertThat(testCountries.getCodeArea()).isEqualTo(DEFAULT_CODE_AREA);
        assertThat(testCountries.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createCountriesWithExistingId() throws Exception {
        // Create the Countries with an existing ID
        countries.setId(1L);
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        int databaseSizeBeforeCreate = countriesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCountryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = countriesRepository.findAll().size();
        // set the field null
        countries.setCountryName(null);

        // Create the Countries, which fails.
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        restCountriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIso2IsRequired() throws Exception {
        int databaseSizeBeforeTest = countriesRepository.findAll().size();
        // set the field null
        countries.setIso2(null);

        // Create the Countries, which fails.
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        restCountriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isBadRequest());

        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCountries() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList
        restCountriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countries.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)))
            .andExpect(jsonPath("$.[*].iso2").value(hasItem(DEFAULT_ISO_2)))
            .andExpect(jsonPath("$.[*].codeArea").value(hasItem(DEFAULT_CODE_AREA)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)));
    }

    @Test
    @Transactional
    void getCountries() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get the countries
        restCountriesMockMvc
            .perform(get(ENTITY_API_URL_ID, countries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(countries.getId().intValue()))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME))
            .andExpect(jsonPath("$.iso2").value(DEFAULT_ISO_2))
            .andExpect(jsonPath("$.codeArea").value(DEFAULT_CODE_AREA))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE));
    }

    @Test
    @Transactional
    void getCountriesByIdFiltering() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        Long id = countries.getId();

        defaultCountriesShouldBeFound("id.equals=" + id);
        defaultCountriesShouldNotBeFound("id.notEquals=" + id);

        defaultCountriesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCountriesShouldNotBeFound("id.greaterThan=" + id);

        defaultCountriesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCountriesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryNameIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where countryName equals to DEFAULT_COUNTRY_NAME
        defaultCountriesShouldBeFound("countryName.equals=" + DEFAULT_COUNTRY_NAME);

        // Get all the countriesList where countryName equals to UPDATED_COUNTRY_NAME
        defaultCountriesShouldNotBeFound("countryName.equals=" + UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where countryName not equals to DEFAULT_COUNTRY_NAME
        defaultCountriesShouldNotBeFound("countryName.notEquals=" + DEFAULT_COUNTRY_NAME);

        // Get all the countriesList where countryName not equals to UPDATED_COUNTRY_NAME
        defaultCountriesShouldBeFound("countryName.notEquals=" + UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryNameIsInShouldWork() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where countryName in DEFAULT_COUNTRY_NAME or UPDATED_COUNTRY_NAME
        defaultCountriesShouldBeFound("countryName.in=" + DEFAULT_COUNTRY_NAME + "," + UPDATED_COUNTRY_NAME);

        // Get all the countriesList where countryName equals to UPDATED_COUNTRY_NAME
        defaultCountriesShouldNotBeFound("countryName.in=" + UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where countryName is not null
        defaultCountriesShouldBeFound("countryName.specified=true");

        // Get all the countriesList where countryName is null
        defaultCountriesShouldNotBeFound("countryName.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByCountryNameContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where countryName contains DEFAULT_COUNTRY_NAME
        defaultCountriesShouldBeFound("countryName.contains=" + DEFAULT_COUNTRY_NAME);

        // Get all the countriesList where countryName contains UPDATED_COUNTRY_NAME
        defaultCountriesShouldNotBeFound("countryName.contains=" + UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByCountryNameNotContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where countryName does not contain DEFAULT_COUNTRY_NAME
        defaultCountriesShouldNotBeFound("countryName.doesNotContain=" + DEFAULT_COUNTRY_NAME);

        // Get all the countriesList where countryName does not contain UPDATED_COUNTRY_NAME
        defaultCountriesShouldBeFound("countryName.doesNotContain=" + UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    void getAllCountriesByIso2IsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where iso2 equals to DEFAULT_ISO_2
        defaultCountriesShouldBeFound("iso2.equals=" + DEFAULT_ISO_2);

        // Get all the countriesList where iso2 equals to UPDATED_ISO_2
        defaultCountriesShouldNotBeFound("iso2.equals=" + UPDATED_ISO_2);
    }

    @Test
    @Transactional
    void getAllCountriesByIso2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where iso2 not equals to DEFAULT_ISO_2
        defaultCountriesShouldNotBeFound("iso2.notEquals=" + DEFAULT_ISO_2);

        // Get all the countriesList where iso2 not equals to UPDATED_ISO_2
        defaultCountriesShouldBeFound("iso2.notEquals=" + UPDATED_ISO_2);
    }

    @Test
    @Transactional
    void getAllCountriesByIso2IsInShouldWork() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where iso2 in DEFAULT_ISO_2 or UPDATED_ISO_2
        defaultCountriesShouldBeFound("iso2.in=" + DEFAULT_ISO_2 + "," + UPDATED_ISO_2);

        // Get all the countriesList where iso2 equals to UPDATED_ISO_2
        defaultCountriesShouldNotBeFound("iso2.in=" + UPDATED_ISO_2);
    }

    @Test
    @Transactional
    void getAllCountriesByIso2IsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where iso2 is not null
        defaultCountriesShouldBeFound("iso2.specified=true");

        // Get all the countriesList where iso2 is null
        defaultCountriesShouldNotBeFound("iso2.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByIso2ContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where iso2 contains DEFAULT_ISO_2
        defaultCountriesShouldBeFound("iso2.contains=" + DEFAULT_ISO_2);

        // Get all the countriesList where iso2 contains UPDATED_ISO_2
        defaultCountriesShouldNotBeFound("iso2.contains=" + UPDATED_ISO_2);
    }

    @Test
    @Transactional
    void getAllCountriesByIso2NotContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where iso2 does not contain DEFAULT_ISO_2
        defaultCountriesShouldNotBeFound("iso2.doesNotContain=" + DEFAULT_ISO_2);

        // Get all the countriesList where iso2 does not contain UPDATED_ISO_2
        defaultCountriesShouldBeFound("iso2.doesNotContain=" + UPDATED_ISO_2);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where codeArea equals to DEFAULT_CODE_AREA
        defaultCountriesShouldBeFound("codeArea.equals=" + DEFAULT_CODE_AREA);

        // Get all the countriesList where codeArea equals to UPDATED_CODE_AREA
        defaultCountriesShouldNotBeFound("codeArea.equals=" + UPDATED_CODE_AREA);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeAreaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where codeArea not equals to DEFAULT_CODE_AREA
        defaultCountriesShouldNotBeFound("codeArea.notEquals=" + DEFAULT_CODE_AREA);

        // Get all the countriesList where codeArea not equals to UPDATED_CODE_AREA
        defaultCountriesShouldBeFound("codeArea.notEquals=" + UPDATED_CODE_AREA);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeAreaIsInShouldWork() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where codeArea in DEFAULT_CODE_AREA or UPDATED_CODE_AREA
        defaultCountriesShouldBeFound("codeArea.in=" + DEFAULT_CODE_AREA + "," + UPDATED_CODE_AREA);

        // Get all the countriesList where codeArea equals to UPDATED_CODE_AREA
        defaultCountriesShouldNotBeFound("codeArea.in=" + UPDATED_CODE_AREA);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where codeArea is not null
        defaultCountriesShouldBeFound("codeArea.specified=true");

        // Get all the countriesList where codeArea is null
        defaultCountriesShouldNotBeFound("codeArea.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByCodeAreaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where codeArea is greater than or equal to DEFAULT_CODE_AREA
        defaultCountriesShouldBeFound("codeArea.greaterThanOrEqual=" + DEFAULT_CODE_AREA);

        // Get all the countriesList where codeArea is greater than or equal to UPDATED_CODE_AREA
        defaultCountriesShouldNotBeFound("codeArea.greaterThanOrEqual=" + UPDATED_CODE_AREA);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeAreaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where codeArea is less than or equal to DEFAULT_CODE_AREA
        defaultCountriesShouldBeFound("codeArea.lessThanOrEqual=" + DEFAULT_CODE_AREA);

        // Get all the countriesList where codeArea is less than or equal to SMALLER_CODE_AREA
        defaultCountriesShouldNotBeFound("codeArea.lessThanOrEqual=" + SMALLER_CODE_AREA);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeAreaIsLessThanSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where codeArea is less than DEFAULT_CODE_AREA
        defaultCountriesShouldNotBeFound("codeArea.lessThan=" + DEFAULT_CODE_AREA);

        // Get all the countriesList where codeArea is less than UPDATED_CODE_AREA
        defaultCountriesShouldBeFound("codeArea.lessThan=" + UPDATED_CODE_AREA);
    }

    @Test
    @Transactional
    void getAllCountriesByCodeAreaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where codeArea is greater than DEFAULT_CODE_AREA
        defaultCountriesShouldNotBeFound("codeArea.greaterThan=" + DEFAULT_CODE_AREA);

        // Get all the countriesList where codeArea is greater than SMALLER_CODE_AREA
        defaultCountriesShouldBeFound("codeArea.greaterThan=" + SMALLER_CODE_AREA);
    }

    @Test
    @Transactional
    void getAllCountriesByLanguageIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where language equals to DEFAULT_LANGUAGE
        defaultCountriesShouldBeFound("language.equals=" + DEFAULT_LANGUAGE);

        // Get all the countriesList where language equals to UPDATED_LANGUAGE
        defaultCountriesShouldNotBeFound("language.equals=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllCountriesByLanguageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where language not equals to DEFAULT_LANGUAGE
        defaultCountriesShouldNotBeFound("language.notEquals=" + DEFAULT_LANGUAGE);

        // Get all the countriesList where language not equals to UPDATED_LANGUAGE
        defaultCountriesShouldBeFound("language.notEquals=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllCountriesByLanguageIsInShouldWork() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where language in DEFAULT_LANGUAGE or UPDATED_LANGUAGE
        defaultCountriesShouldBeFound("language.in=" + DEFAULT_LANGUAGE + "," + UPDATED_LANGUAGE);

        // Get all the countriesList where language equals to UPDATED_LANGUAGE
        defaultCountriesShouldNotBeFound("language.in=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllCountriesByLanguageIsNullOrNotNull() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where language is not null
        defaultCountriesShouldBeFound("language.specified=true");

        // Get all the countriesList where language is null
        defaultCountriesShouldNotBeFound("language.specified=false");
    }

    @Test
    @Transactional
    void getAllCountriesByLanguageContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where language contains DEFAULT_LANGUAGE
        defaultCountriesShouldBeFound("language.contains=" + DEFAULT_LANGUAGE);

        // Get all the countriesList where language contains UPDATED_LANGUAGE
        defaultCountriesShouldNotBeFound("language.contains=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllCountriesByLanguageNotContainsSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        // Get all the countriesList where language does not contain DEFAULT_LANGUAGE
        defaultCountriesShouldNotBeFound("language.doesNotContain=" + DEFAULT_LANGUAGE);

        // Get all the countriesList where language does not contain UPDATED_LANGUAGE
        defaultCountriesShouldBeFound("language.doesNotContain=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllCountriesByStateProvincesIsEqualToSomething() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);
        StateProvinces stateProvinces = StateProvincesResourceIT.createEntity(em);
        em.persist(stateProvinces);
        em.flush();
        countries.addStateProvinces(stateProvinces);
        countriesRepository.saveAndFlush(countries);
        Long stateProvincesId = stateProvinces.getId();

        // Get all the countriesList where stateProvinces equals to stateProvincesId
        defaultCountriesShouldBeFound("stateProvincesId.equals=" + stateProvincesId);

        // Get all the countriesList where stateProvinces equals to (stateProvincesId + 1)
        defaultCountriesShouldNotBeFound("stateProvincesId.equals=" + (stateProvincesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCountriesShouldBeFound(String filter) throws Exception {
        restCountriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countries.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)))
            .andExpect(jsonPath("$.[*].iso2").value(hasItem(DEFAULT_ISO_2)))
            .andExpect(jsonPath("$.[*].codeArea").value(hasItem(DEFAULT_CODE_AREA)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)));

        // Check, that the count call also returns 1
        restCountriesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCountriesShouldNotBeFound(String filter) throws Exception {
        restCountriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCountriesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCountries() throws Exception {
        // Get the countries
        restCountriesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCountries() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();

        // Update the countries
        Countries updatedCountries = countriesRepository.findById(countries.getId()).get();
        // Disconnect from session so that the updates on updatedCountries are not directly saved in db
        em.detach(updatedCountries);
        updatedCountries.countryName(UPDATED_COUNTRY_NAME).iso2(UPDATED_ISO_2).codeArea(UPDATED_CODE_AREA).language(UPDATED_LANGUAGE);
        CountriesDTO countriesDTO = countriesMapper.toDto(updatedCountries);

        restCountriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, countriesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(countriesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
        Countries testCountries = countriesList.get(countriesList.size() - 1);
        assertThat(testCountries.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testCountries.getIso2()).isEqualTo(UPDATED_ISO_2);
        assertThat(testCountries.getCodeArea()).isEqualTo(UPDATED_CODE_AREA);
        assertThat(testCountries.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingCountries() throws Exception {
        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();
        countries.setId(count.incrementAndGet());

        // Create the Countries
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, countriesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(countriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCountries() throws Exception {
        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();
        countries.setId(count.incrementAndGet());

        // Create the Countries
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(countriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCountries() throws Exception {
        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();
        countries.setId(count.incrementAndGet());

        // Create the Countries
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountriesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countriesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCountriesWithPatch() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();

        // Update the countries using partial update
        Countries partialUpdatedCountries = new Countries();
        partialUpdatedCountries.setId(countries.getId());

        partialUpdatedCountries.countryName(UPDATED_COUNTRY_NAME).iso2(UPDATED_ISO_2).codeArea(UPDATED_CODE_AREA);

        restCountriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCountries.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCountries))
            )
            .andExpect(status().isOk());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
        Countries testCountries = countriesList.get(countriesList.size() - 1);
        assertThat(testCountries.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testCountries.getIso2()).isEqualTo(UPDATED_ISO_2);
        assertThat(testCountries.getCodeArea()).isEqualTo(UPDATED_CODE_AREA);
        assertThat(testCountries.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateCountriesWithPatch() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();

        // Update the countries using partial update
        Countries partialUpdatedCountries = new Countries();
        partialUpdatedCountries.setId(countries.getId());

        partialUpdatedCountries
            .countryName(UPDATED_COUNTRY_NAME)
            .iso2(UPDATED_ISO_2)
            .codeArea(UPDATED_CODE_AREA)
            .language(UPDATED_LANGUAGE);

        restCountriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCountries.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCountries))
            )
            .andExpect(status().isOk());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
        Countries testCountries = countriesList.get(countriesList.size() - 1);
        assertThat(testCountries.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testCountries.getIso2()).isEqualTo(UPDATED_ISO_2);
        assertThat(testCountries.getCodeArea()).isEqualTo(UPDATED_CODE_AREA);
        assertThat(testCountries.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingCountries() throws Exception {
        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();
        countries.setId(count.incrementAndGet());

        // Create the Countries
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, countriesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(countriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCountries() throws Exception {
        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();
        countries.setId(count.incrementAndGet());

        // Create the Countries
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(countriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCountries() throws Exception {
        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();
        countries.setId(count.incrementAndGet());

        // Create the Countries
        CountriesDTO countriesDTO = countriesMapper.toDto(countries);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountriesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(countriesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCountries() throws Exception {
        // Initialize the database
        countriesRepository.saveAndFlush(countries);

        int databaseSizeBeforeDelete = countriesRepository.findAll().size();

        // Delete the countries
        restCountriesMockMvc
            .perform(delete(ENTITY_API_URL_ID, countries.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
