package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.SupplierBanksInfo;
import com.genesisoft.transporte.domain.Suppliers;
import com.genesisoft.transporte.repository.SupplierBanksInfoRepository;
import com.genesisoft.transporte.service.criteria.SupplierBanksInfoCriteria;
import com.genesisoft.transporte.service.dto.SupplierBanksInfoDTO;
import com.genesisoft.transporte.service.mapper.SupplierBanksInfoMapper;
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
 * Integration tests for the {@link SupplierBanksInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SupplierBanksInfoResourceIT {

    private static final Integer DEFAULT_SUPPLIER_BANK_CODE = 1;
    private static final Integer UPDATED_SUPPLIER_BANK_CODE = 2;
    private static final Integer SMALLER_SUPPLIER_BANK_CODE = 1 - 1;

    private static final String DEFAULT_SUPPLIER_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_BANK_BRANCH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_BANK_BRANCH_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_BANK_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_BANK_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_BANK_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_BANK_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_BANK_PIX_KEY = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_BANK_PIX_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_BANK_USER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_BANK_USER_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/supplier-banks-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SupplierBanksInfoRepository supplierBanksInfoRepository;

    @Autowired
    private SupplierBanksInfoMapper supplierBanksInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupplierBanksInfoMockMvc;

    private SupplierBanksInfo supplierBanksInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupplierBanksInfo createEntity(EntityManager em) {
        SupplierBanksInfo supplierBanksInfo = new SupplierBanksInfo()
            .supplierBankCode(DEFAULT_SUPPLIER_BANK_CODE)
            .supplierBankName(DEFAULT_SUPPLIER_BANK_NAME)
            .supplierBankBranchCode(DEFAULT_SUPPLIER_BANK_BRANCH_CODE)
            .supplierBankAccountNumber(DEFAULT_SUPPLIER_BANK_ACCOUNT_NUMBER)
            .supplierBankUserName(DEFAULT_SUPPLIER_BANK_USER_NAME)
            .supplierBankPixKey(DEFAULT_SUPPLIER_BANK_PIX_KEY)
            .supplierBankUserNumber(DEFAULT_SUPPLIER_BANK_USER_NUMBER);
        // Add required entity
        Suppliers suppliers;
        if (TestUtil.findAll(em, Suppliers.class).isEmpty()) {
            suppliers = SuppliersResourceIT.createEntity(em);
            em.persist(suppliers);
            em.flush();
        } else {
            suppliers = TestUtil.findAll(em, Suppliers.class).get(0);
        }
        supplierBanksInfo.setSuppliers(suppliers);
        return supplierBanksInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupplierBanksInfo createUpdatedEntity(EntityManager em) {
        SupplierBanksInfo supplierBanksInfo = new SupplierBanksInfo()
            .supplierBankCode(UPDATED_SUPPLIER_BANK_CODE)
            .supplierBankName(UPDATED_SUPPLIER_BANK_NAME)
            .supplierBankBranchCode(UPDATED_SUPPLIER_BANK_BRANCH_CODE)
            .supplierBankAccountNumber(UPDATED_SUPPLIER_BANK_ACCOUNT_NUMBER)
            .supplierBankUserName(UPDATED_SUPPLIER_BANK_USER_NAME)
            .supplierBankPixKey(UPDATED_SUPPLIER_BANK_PIX_KEY)
            .supplierBankUserNumber(UPDATED_SUPPLIER_BANK_USER_NUMBER);
        // Add required entity
        Suppliers suppliers;
        if (TestUtil.findAll(em, Suppliers.class).isEmpty()) {
            suppliers = SuppliersResourceIT.createUpdatedEntity(em);
            em.persist(suppliers);
            em.flush();
        } else {
            suppliers = TestUtil.findAll(em, Suppliers.class).get(0);
        }
        supplierBanksInfo.setSuppliers(suppliers);
        return supplierBanksInfo;
    }

    @BeforeEach
    public void initTest() {
        supplierBanksInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createSupplierBanksInfo() throws Exception {
        int databaseSizeBeforeCreate = supplierBanksInfoRepository.findAll().size();
        // Create the SupplierBanksInfo
        SupplierBanksInfoDTO supplierBanksInfoDTO = supplierBanksInfoMapper.toDto(supplierBanksInfo);
        restSupplierBanksInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplierBanksInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SupplierBanksInfo in the database
        List<SupplierBanksInfo> supplierBanksInfoList = supplierBanksInfoRepository.findAll();
        assertThat(supplierBanksInfoList).hasSize(databaseSizeBeforeCreate + 1);
        SupplierBanksInfo testSupplierBanksInfo = supplierBanksInfoList.get(supplierBanksInfoList.size() - 1);
        assertThat(testSupplierBanksInfo.getSupplierBankCode()).isEqualTo(DEFAULT_SUPPLIER_BANK_CODE);
        assertThat(testSupplierBanksInfo.getSupplierBankName()).isEqualTo(DEFAULT_SUPPLIER_BANK_NAME);
        assertThat(testSupplierBanksInfo.getSupplierBankBranchCode()).isEqualTo(DEFAULT_SUPPLIER_BANK_BRANCH_CODE);
        assertThat(testSupplierBanksInfo.getSupplierBankAccountNumber()).isEqualTo(DEFAULT_SUPPLIER_BANK_ACCOUNT_NUMBER);
        assertThat(testSupplierBanksInfo.getSupplierBankUserName()).isEqualTo(DEFAULT_SUPPLIER_BANK_USER_NAME);
        assertThat(testSupplierBanksInfo.getSupplierBankPixKey()).isEqualTo(DEFAULT_SUPPLIER_BANK_PIX_KEY);
        assertThat(testSupplierBanksInfo.getSupplierBankUserNumber()).isEqualTo(DEFAULT_SUPPLIER_BANK_USER_NUMBER);
    }

    @Test
    @Transactional
    void createSupplierBanksInfoWithExistingId() throws Exception {
        // Create the SupplierBanksInfo with an existing ID
        supplierBanksInfo.setId(1L);
        SupplierBanksInfoDTO supplierBanksInfoDTO = supplierBanksInfoMapper.toDto(supplierBanksInfo);

        int databaseSizeBeforeCreate = supplierBanksInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplierBanksInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplierBanksInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplierBanksInfo in the database
        List<SupplierBanksInfo> supplierBanksInfoList = supplierBanksInfoRepository.findAll();
        assertThat(supplierBanksInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSupplierBankCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierBanksInfoRepository.findAll().size();
        // set the field null
        supplierBanksInfo.setSupplierBankCode(null);

        // Create the SupplierBanksInfo, which fails.
        SupplierBanksInfoDTO supplierBanksInfoDTO = supplierBanksInfoMapper.toDto(supplierBanksInfo);

        restSupplierBanksInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplierBanksInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<SupplierBanksInfo> supplierBanksInfoList = supplierBanksInfoRepository.findAll();
        assertThat(supplierBanksInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSupplierBankNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierBanksInfoRepository.findAll().size();
        // set the field null
        supplierBanksInfo.setSupplierBankName(null);

        // Create the SupplierBanksInfo, which fails.
        SupplierBanksInfoDTO supplierBanksInfoDTO = supplierBanksInfoMapper.toDto(supplierBanksInfo);

        restSupplierBanksInfoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplierBanksInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<SupplierBanksInfo> supplierBanksInfoList = supplierBanksInfoRepository.findAll();
        assertThat(supplierBanksInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfos() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList
        restSupplierBanksInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierBanksInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].supplierBankCode").value(hasItem(DEFAULT_SUPPLIER_BANK_CODE)))
            .andExpect(jsonPath("$.[*].supplierBankName").value(hasItem(DEFAULT_SUPPLIER_BANK_NAME)))
            .andExpect(jsonPath("$.[*].supplierBankBranchCode").value(hasItem(DEFAULT_SUPPLIER_BANK_BRANCH_CODE)))
            .andExpect(jsonPath("$.[*].supplierBankAccountNumber").value(hasItem(DEFAULT_SUPPLIER_BANK_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].supplierBankUserName").value(hasItem(DEFAULT_SUPPLIER_BANK_USER_NAME)))
            .andExpect(jsonPath("$.[*].supplierBankPixKey").value(hasItem(DEFAULT_SUPPLIER_BANK_PIX_KEY)))
            .andExpect(jsonPath("$.[*].supplierBankUserNumber").value(hasItem(DEFAULT_SUPPLIER_BANK_USER_NUMBER)));
    }

    @Test
    @Transactional
    void getSupplierBanksInfo() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get the supplierBanksInfo
        restSupplierBanksInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, supplierBanksInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supplierBanksInfo.getId().intValue()))
            .andExpect(jsonPath("$.supplierBankCode").value(DEFAULT_SUPPLIER_BANK_CODE))
            .andExpect(jsonPath("$.supplierBankName").value(DEFAULT_SUPPLIER_BANK_NAME))
            .andExpect(jsonPath("$.supplierBankBranchCode").value(DEFAULT_SUPPLIER_BANK_BRANCH_CODE))
            .andExpect(jsonPath("$.supplierBankAccountNumber").value(DEFAULT_SUPPLIER_BANK_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.supplierBankUserName").value(DEFAULT_SUPPLIER_BANK_USER_NAME))
            .andExpect(jsonPath("$.supplierBankPixKey").value(DEFAULT_SUPPLIER_BANK_PIX_KEY))
            .andExpect(jsonPath("$.supplierBankUserNumber").value(DEFAULT_SUPPLIER_BANK_USER_NUMBER));
    }

    @Test
    @Transactional
    void getSupplierBanksInfosByIdFiltering() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        Long id = supplierBanksInfo.getId();

        defaultSupplierBanksInfoShouldBeFound("id.equals=" + id);
        defaultSupplierBanksInfoShouldNotBeFound("id.notEquals=" + id);

        defaultSupplierBanksInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSupplierBanksInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultSupplierBanksInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSupplierBanksInfoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankCode equals to DEFAULT_SUPPLIER_BANK_CODE
        defaultSupplierBanksInfoShouldBeFound("supplierBankCode.equals=" + DEFAULT_SUPPLIER_BANK_CODE);

        // Get all the supplierBanksInfoList where supplierBankCode equals to UPDATED_SUPPLIER_BANK_CODE
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankCode.equals=" + UPDATED_SUPPLIER_BANK_CODE);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankCode not equals to DEFAULT_SUPPLIER_BANK_CODE
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankCode.notEquals=" + DEFAULT_SUPPLIER_BANK_CODE);

        // Get all the supplierBanksInfoList where supplierBankCode not equals to UPDATED_SUPPLIER_BANK_CODE
        defaultSupplierBanksInfoShouldBeFound("supplierBankCode.notEquals=" + UPDATED_SUPPLIER_BANK_CODE);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankCodeIsInShouldWork() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankCode in DEFAULT_SUPPLIER_BANK_CODE or UPDATED_SUPPLIER_BANK_CODE
        defaultSupplierBanksInfoShouldBeFound("supplierBankCode.in=" + DEFAULT_SUPPLIER_BANK_CODE + "," + UPDATED_SUPPLIER_BANK_CODE);

        // Get all the supplierBanksInfoList where supplierBankCode equals to UPDATED_SUPPLIER_BANK_CODE
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankCode.in=" + UPDATED_SUPPLIER_BANK_CODE);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankCode is not null
        defaultSupplierBanksInfoShouldBeFound("supplierBankCode.specified=true");

        // Get all the supplierBanksInfoList where supplierBankCode is null
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankCode.specified=false");
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankCode is greater than or equal to DEFAULT_SUPPLIER_BANK_CODE
        defaultSupplierBanksInfoShouldBeFound("supplierBankCode.greaterThanOrEqual=" + DEFAULT_SUPPLIER_BANK_CODE);

        // Get all the supplierBanksInfoList where supplierBankCode is greater than or equal to UPDATED_SUPPLIER_BANK_CODE
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankCode.greaterThanOrEqual=" + UPDATED_SUPPLIER_BANK_CODE);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankCode is less than or equal to DEFAULT_SUPPLIER_BANK_CODE
        defaultSupplierBanksInfoShouldBeFound("supplierBankCode.lessThanOrEqual=" + DEFAULT_SUPPLIER_BANK_CODE);

        // Get all the supplierBanksInfoList where supplierBankCode is less than or equal to SMALLER_SUPPLIER_BANK_CODE
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankCode.lessThanOrEqual=" + SMALLER_SUPPLIER_BANK_CODE);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankCode is less than DEFAULT_SUPPLIER_BANK_CODE
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankCode.lessThan=" + DEFAULT_SUPPLIER_BANK_CODE);

        // Get all the supplierBanksInfoList where supplierBankCode is less than UPDATED_SUPPLIER_BANK_CODE
        defaultSupplierBanksInfoShouldBeFound("supplierBankCode.lessThan=" + UPDATED_SUPPLIER_BANK_CODE);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankCode is greater than DEFAULT_SUPPLIER_BANK_CODE
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankCode.greaterThan=" + DEFAULT_SUPPLIER_BANK_CODE);

        // Get all the supplierBanksInfoList where supplierBankCode is greater than SMALLER_SUPPLIER_BANK_CODE
        defaultSupplierBanksInfoShouldBeFound("supplierBankCode.greaterThan=" + SMALLER_SUPPLIER_BANK_CODE);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankName equals to DEFAULT_SUPPLIER_BANK_NAME
        defaultSupplierBanksInfoShouldBeFound("supplierBankName.equals=" + DEFAULT_SUPPLIER_BANK_NAME);

        // Get all the supplierBanksInfoList where supplierBankName equals to UPDATED_SUPPLIER_BANK_NAME
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankName.equals=" + UPDATED_SUPPLIER_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankName not equals to DEFAULT_SUPPLIER_BANK_NAME
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankName.notEquals=" + DEFAULT_SUPPLIER_BANK_NAME);

        // Get all the supplierBanksInfoList where supplierBankName not equals to UPDATED_SUPPLIER_BANK_NAME
        defaultSupplierBanksInfoShouldBeFound("supplierBankName.notEquals=" + UPDATED_SUPPLIER_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankName in DEFAULT_SUPPLIER_BANK_NAME or UPDATED_SUPPLIER_BANK_NAME
        defaultSupplierBanksInfoShouldBeFound("supplierBankName.in=" + DEFAULT_SUPPLIER_BANK_NAME + "," + UPDATED_SUPPLIER_BANK_NAME);

        // Get all the supplierBanksInfoList where supplierBankName equals to UPDATED_SUPPLIER_BANK_NAME
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankName.in=" + UPDATED_SUPPLIER_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankName is not null
        defaultSupplierBanksInfoShouldBeFound("supplierBankName.specified=true");

        // Get all the supplierBanksInfoList where supplierBankName is null
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankName.specified=false");
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankNameContainsSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankName contains DEFAULT_SUPPLIER_BANK_NAME
        defaultSupplierBanksInfoShouldBeFound("supplierBankName.contains=" + DEFAULT_SUPPLIER_BANK_NAME);

        // Get all the supplierBanksInfoList where supplierBankName contains UPDATED_SUPPLIER_BANK_NAME
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankName.contains=" + UPDATED_SUPPLIER_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankName does not contain DEFAULT_SUPPLIER_BANK_NAME
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankName.doesNotContain=" + DEFAULT_SUPPLIER_BANK_NAME);

        // Get all the supplierBanksInfoList where supplierBankName does not contain UPDATED_SUPPLIER_BANK_NAME
        defaultSupplierBanksInfoShouldBeFound("supplierBankName.doesNotContain=" + UPDATED_SUPPLIER_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankBranchCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankBranchCode equals to DEFAULT_SUPPLIER_BANK_BRANCH_CODE
        defaultSupplierBanksInfoShouldBeFound("supplierBankBranchCode.equals=" + DEFAULT_SUPPLIER_BANK_BRANCH_CODE);

        // Get all the supplierBanksInfoList where supplierBankBranchCode equals to UPDATED_SUPPLIER_BANK_BRANCH_CODE
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankBranchCode.equals=" + UPDATED_SUPPLIER_BANK_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankBranchCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankBranchCode not equals to DEFAULT_SUPPLIER_BANK_BRANCH_CODE
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankBranchCode.notEquals=" + DEFAULT_SUPPLIER_BANK_BRANCH_CODE);

        // Get all the supplierBanksInfoList where supplierBankBranchCode not equals to UPDATED_SUPPLIER_BANK_BRANCH_CODE
        defaultSupplierBanksInfoShouldBeFound("supplierBankBranchCode.notEquals=" + UPDATED_SUPPLIER_BANK_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankBranchCodeIsInShouldWork() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankBranchCode in DEFAULT_SUPPLIER_BANK_BRANCH_CODE or UPDATED_SUPPLIER_BANK_BRANCH_CODE
        defaultSupplierBanksInfoShouldBeFound(
            "supplierBankBranchCode.in=" + DEFAULT_SUPPLIER_BANK_BRANCH_CODE + "," + UPDATED_SUPPLIER_BANK_BRANCH_CODE
        );

        // Get all the supplierBanksInfoList where supplierBankBranchCode equals to UPDATED_SUPPLIER_BANK_BRANCH_CODE
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankBranchCode.in=" + UPDATED_SUPPLIER_BANK_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankBranchCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankBranchCode is not null
        defaultSupplierBanksInfoShouldBeFound("supplierBankBranchCode.specified=true");

        // Get all the supplierBanksInfoList where supplierBankBranchCode is null
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankBranchCode.specified=false");
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankBranchCodeContainsSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankBranchCode contains DEFAULT_SUPPLIER_BANK_BRANCH_CODE
        defaultSupplierBanksInfoShouldBeFound("supplierBankBranchCode.contains=" + DEFAULT_SUPPLIER_BANK_BRANCH_CODE);

        // Get all the supplierBanksInfoList where supplierBankBranchCode contains UPDATED_SUPPLIER_BANK_BRANCH_CODE
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankBranchCode.contains=" + UPDATED_SUPPLIER_BANK_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankBranchCodeNotContainsSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankBranchCode does not contain DEFAULT_SUPPLIER_BANK_BRANCH_CODE
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankBranchCode.doesNotContain=" + DEFAULT_SUPPLIER_BANK_BRANCH_CODE);

        // Get all the supplierBanksInfoList where supplierBankBranchCode does not contain UPDATED_SUPPLIER_BANK_BRANCH_CODE
        defaultSupplierBanksInfoShouldBeFound("supplierBankBranchCode.doesNotContain=" + UPDATED_SUPPLIER_BANK_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankAccountNumber equals to DEFAULT_SUPPLIER_BANK_ACCOUNT_NUMBER
        defaultSupplierBanksInfoShouldBeFound("supplierBankAccountNumber.equals=" + DEFAULT_SUPPLIER_BANK_ACCOUNT_NUMBER);

        // Get all the supplierBanksInfoList where supplierBankAccountNumber equals to UPDATED_SUPPLIER_BANK_ACCOUNT_NUMBER
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankAccountNumber.equals=" + UPDATED_SUPPLIER_BANK_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankAccountNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankAccountNumber not equals to DEFAULT_SUPPLIER_BANK_ACCOUNT_NUMBER
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankAccountNumber.notEquals=" + DEFAULT_SUPPLIER_BANK_ACCOUNT_NUMBER);

        // Get all the supplierBanksInfoList where supplierBankAccountNumber not equals to UPDATED_SUPPLIER_BANK_ACCOUNT_NUMBER
        defaultSupplierBanksInfoShouldBeFound("supplierBankAccountNumber.notEquals=" + UPDATED_SUPPLIER_BANK_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankAccountNumber in DEFAULT_SUPPLIER_BANK_ACCOUNT_NUMBER or UPDATED_SUPPLIER_BANK_ACCOUNT_NUMBER
        defaultSupplierBanksInfoShouldBeFound(
            "supplierBankAccountNumber.in=" + DEFAULT_SUPPLIER_BANK_ACCOUNT_NUMBER + "," + UPDATED_SUPPLIER_BANK_ACCOUNT_NUMBER
        );

        // Get all the supplierBanksInfoList where supplierBankAccountNumber equals to UPDATED_SUPPLIER_BANK_ACCOUNT_NUMBER
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankAccountNumber.in=" + UPDATED_SUPPLIER_BANK_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankAccountNumber is not null
        defaultSupplierBanksInfoShouldBeFound("supplierBankAccountNumber.specified=true");

        // Get all the supplierBanksInfoList where supplierBankAccountNumber is null
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankAccountNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankAccountNumber contains DEFAULT_SUPPLIER_BANK_ACCOUNT_NUMBER
        defaultSupplierBanksInfoShouldBeFound("supplierBankAccountNumber.contains=" + DEFAULT_SUPPLIER_BANK_ACCOUNT_NUMBER);

        // Get all the supplierBanksInfoList where supplierBankAccountNumber contains UPDATED_SUPPLIER_BANK_ACCOUNT_NUMBER
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankAccountNumber.contains=" + UPDATED_SUPPLIER_BANK_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankAccountNumber does not contain DEFAULT_SUPPLIER_BANK_ACCOUNT_NUMBER
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankAccountNumber.doesNotContain=" + DEFAULT_SUPPLIER_BANK_ACCOUNT_NUMBER);

        // Get all the supplierBanksInfoList where supplierBankAccountNumber does not contain UPDATED_SUPPLIER_BANK_ACCOUNT_NUMBER
        defaultSupplierBanksInfoShouldBeFound("supplierBankAccountNumber.doesNotContain=" + UPDATED_SUPPLIER_BANK_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankUserNameIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankUserName equals to DEFAULT_SUPPLIER_BANK_USER_NAME
        defaultSupplierBanksInfoShouldBeFound("supplierBankUserName.equals=" + DEFAULT_SUPPLIER_BANK_USER_NAME);

        // Get all the supplierBanksInfoList where supplierBankUserName equals to UPDATED_SUPPLIER_BANK_USER_NAME
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankUserName.equals=" + UPDATED_SUPPLIER_BANK_USER_NAME);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankUserNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankUserName not equals to DEFAULT_SUPPLIER_BANK_USER_NAME
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankUserName.notEquals=" + DEFAULT_SUPPLIER_BANK_USER_NAME);

        // Get all the supplierBanksInfoList where supplierBankUserName not equals to UPDATED_SUPPLIER_BANK_USER_NAME
        defaultSupplierBanksInfoShouldBeFound("supplierBankUserName.notEquals=" + UPDATED_SUPPLIER_BANK_USER_NAME);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankUserNameIsInShouldWork() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankUserName in DEFAULT_SUPPLIER_BANK_USER_NAME or UPDATED_SUPPLIER_BANK_USER_NAME
        defaultSupplierBanksInfoShouldBeFound(
            "supplierBankUserName.in=" + DEFAULT_SUPPLIER_BANK_USER_NAME + "," + UPDATED_SUPPLIER_BANK_USER_NAME
        );

        // Get all the supplierBanksInfoList where supplierBankUserName equals to UPDATED_SUPPLIER_BANK_USER_NAME
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankUserName.in=" + UPDATED_SUPPLIER_BANK_USER_NAME);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankUserNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankUserName is not null
        defaultSupplierBanksInfoShouldBeFound("supplierBankUserName.specified=true");

        // Get all the supplierBanksInfoList where supplierBankUserName is null
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankUserName.specified=false");
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankUserNameContainsSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankUserName contains DEFAULT_SUPPLIER_BANK_USER_NAME
        defaultSupplierBanksInfoShouldBeFound("supplierBankUserName.contains=" + DEFAULT_SUPPLIER_BANK_USER_NAME);

        // Get all the supplierBanksInfoList where supplierBankUserName contains UPDATED_SUPPLIER_BANK_USER_NAME
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankUserName.contains=" + UPDATED_SUPPLIER_BANK_USER_NAME);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankUserNameNotContainsSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankUserName does not contain DEFAULT_SUPPLIER_BANK_USER_NAME
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankUserName.doesNotContain=" + DEFAULT_SUPPLIER_BANK_USER_NAME);

        // Get all the supplierBanksInfoList where supplierBankUserName does not contain UPDATED_SUPPLIER_BANK_USER_NAME
        defaultSupplierBanksInfoShouldBeFound("supplierBankUserName.doesNotContain=" + UPDATED_SUPPLIER_BANK_USER_NAME);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankPixKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankPixKey equals to DEFAULT_SUPPLIER_BANK_PIX_KEY
        defaultSupplierBanksInfoShouldBeFound("supplierBankPixKey.equals=" + DEFAULT_SUPPLIER_BANK_PIX_KEY);

        // Get all the supplierBanksInfoList where supplierBankPixKey equals to UPDATED_SUPPLIER_BANK_PIX_KEY
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankPixKey.equals=" + UPDATED_SUPPLIER_BANK_PIX_KEY);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankPixKeyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankPixKey not equals to DEFAULT_SUPPLIER_BANK_PIX_KEY
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankPixKey.notEquals=" + DEFAULT_SUPPLIER_BANK_PIX_KEY);

        // Get all the supplierBanksInfoList where supplierBankPixKey not equals to UPDATED_SUPPLIER_BANK_PIX_KEY
        defaultSupplierBanksInfoShouldBeFound("supplierBankPixKey.notEquals=" + UPDATED_SUPPLIER_BANK_PIX_KEY);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankPixKeyIsInShouldWork() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankPixKey in DEFAULT_SUPPLIER_BANK_PIX_KEY or UPDATED_SUPPLIER_BANK_PIX_KEY
        defaultSupplierBanksInfoShouldBeFound(
            "supplierBankPixKey.in=" + DEFAULT_SUPPLIER_BANK_PIX_KEY + "," + UPDATED_SUPPLIER_BANK_PIX_KEY
        );

        // Get all the supplierBanksInfoList where supplierBankPixKey equals to UPDATED_SUPPLIER_BANK_PIX_KEY
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankPixKey.in=" + UPDATED_SUPPLIER_BANK_PIX_KEY);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankPixKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankPixKey is not null
        defaultSupplierBanksInfoShouldBeFound("supplierBankPixKey.specified=true");

        // Get all the supplierBanksInfoList where supplierBankPixKey is null
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankPixKey.specified=false");
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankPixKeyContainsSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankPixKey contains DEFAULT_SUPPLIER_BANK_PIX_KEY
        defaultSupplierBanksInfoShouldBeFound("supplierBankPixKey.contains=" + DEFAULT_SUPPLIER_BANK_PIX_KEY);

        // Get all the supplierBanksInfoList where supplierBankPixKey contains UPDATED_SUPPLIER_BANK_PIX_KEY
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankPixKey.contains=" + UPDATED_SUPPLIER_BANK_PIX_KEY);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankPixKeyNotContainsSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankPixKey does not contain DEFAULT_SUPPLIER_BANK_PIX_KEY
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankPixKey.doesNotContain=" + DEFAULT_SUPPLIER_BANK_PIX_KEY);

        // Get all the supplierBanksInfoList where supplierBankPixKey does not contain UPDATED_SUPPLIER_BANK_PIX_KEY
        defaultSupplierBanksInfoShouldBeFound("supplierBankPixKey.doesNotContain=" + UPDATED_SUPPLIER_BANK_PIX_KEY);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankUserNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankUserNumber equals to DEFAULT_SUPPLIER_BANK_USER_NUMBER
        defaultSupplierBanksInfoShouldBeFound("supplierBankUserNumber.equals=" + DEFAULT_SUPPLIER_BANK_USER_NUMBER);

        // Get all the supplierBanksInfoList where supplierBankUserNumber equals to UPDATED_SUPPLIER_BANK_USER_NUMBER
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankUserNumber.equals=" + UPDATED_SUPPLIER_BANK_USER_NUMBER);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankUserNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankUserNumber not equals to DEFAULT_SUPPLIER_BANK_USER_NUMBER
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankUserNumber.notEquals=" + DEFAULT_SUPPLIER_BANK_USER_NUMBER);

        // Get all the supplierBanksInfoList where supplierBankUserNumber not equals to UPDATED_SUPPLIER_BANK_USER_NUMBER
        defaultSupplierBanksInfoShouldBeFound("supplierBankUserNumber.notEquals=" + UPDATED_SUPPLIER_BANK_USER_NUMBER);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankUserNumberIsInShouldWork() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankUserNumber in DEFAULT_SUPPLIER_BANK_USER_NUMBER or UPDATED_SUPPLIER_BANK_USER_NUMBER
        defaultSupplierBanksInfoShouldBeFound(
            "supplierBankUserNumber.in=" + DEFAULT_SUPPLIER_BANK_USER_NUMBER + "," + UPDATED_SUPPLIER_BANK_USER_NUMBER
        );

        // Get all the supplierBanksInfoList where supplierBankUserNumber equals to UPDATED_SUPPLIER_BANK_USER_NUMBER
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankUserNumber.in=" + UPDATED_SUPPLIER_BANK_USER_NUMBER);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankUserNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankUserNumber is not null
        defaultSupplierBanksInfoShouldBeFound("supplierBankUserNumber.specified=true");

        // Get all the supplierBanksInfoList where supplierBankUserNumber is null
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankUserNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankUserNumberContainsSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankUserNumber contains DEFAULT_SUPPLIER_BANK_USER_NUMBER
        defaultSupplierBanksInfoShouldBeFound("supplierBankUserNumber.contains=" + DEFAULT_SUPPLIER_BANK_USER_NUMBER);

        // Get all the supplierBanksInfoList where supplierBankUserNumber contains UPDATED_SUPPLIER_BANK_USER_NUMBER
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankUserNumber.contains=" + UPDATED_SUPPLIER_BANK_USER_NUMBER);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySupplierBankUserNumberNotContainsSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        // Get all the supplierBanksInfoList where supplierBankUserNumber does not contain DEFAULT_SUPPLIER_BANK_USER_NUMBER
        defaultSupplierBanksInfoShouldNotBeFound("supplierBankUserNumber.doesNotContain=" + DEFAULT_SUPPLIER_BANK_USER_NUMBER);

        // Get all the supplierBanksInfoList where supplierBankUserNumber does not contain UPDATED_SUPPLIER_BANK_USER_NUMBER
        defaultSupplierBanksInfoShouldBeFound("supplierBankUserNumber.doesNotContain=" + UPDATED_SUPPLIER_BANK_USER_NUMBER);
    }

    @Test
    @Transactional
    void getAllSupplierBanksInfosBySuppliersIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);
        Suppliers suppliers = SuppliersResourceIT.createEntity(em);
        em.persist(suppliers);
        em.flush();
        supplierBanksInfo.setSuppliers(suppliers);
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);
        Long suppliersId = suppliers.getId();

        // Get all the supplierBanksInfoList where suppliers equals to suppliersId
        defaultSupplierBanksInfoShouldBeFound("suppliersId.equals=" + suppliersId);

        // Get all the supplierBanksInfoList where suppliers equals to (suppliersId + 1)
        defaultSupplierBanksInfoShouldNotBeFound("suppliersId.equals=" + (suppliersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSupplierBanksInfoShouldBeFound(String filter) throws Exception {
        restSupplierBanksInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierBanksInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].supplierBankCode").value(hasItem(DEFAULT_SUPPLIER_BANK_CODE)))
            .andExpect(jsonPath("$.[*].supplierBankName").value(hasItem(DEFAULT_SUPPLIER_BANK_NAME)))
            .andExpect(jsonPath("$.[*].supplierBankBranchCode").value(hasItem(DEFAULT_SUPPLIER_BANK_BRANCH_CODE)))
            .andExpect(jsonPath("$.[*].supplierBankAccountNumber").value(hasItem(DEFAULT_SUPPLIER_BANK_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].supplierBankUserName").value(hasItem(DEFAULT_SUPPLIER_BANK_USER_NAME)))
            .andExpect(jsonPath("$.[*].supplierBankPixKey").value(hasItem(DEFAULT_SUPPLIER_BANK_PIX_KEY)))
            .andExpect(jsonPath("$.[*].supplierBankUserNumber").value(hasItem(DEFAULT_SUPPLIER_BANK_USER_NUMBER)));

        // Check, that the count call also returns 1
        restSupplierBanksInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSupplierBanksInfoShouldNotBeFound(String filter) throws Exception {
        restSupplierBanksInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplierBanksInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSupplierBanksInfo() throws Exception {
        // Get the supplierBanksInfo
        restSupplierBanksInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSupplierBanksInfo() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        int databaseSizeBeforeUpdate = supplierBanksInfoRepository.findAll().size();

        // Update the supplierBanksInfo
        SupplierBanksInfo updatedSupplierBanksInfo = supplierBanksInfoRepository.findById(supplierBanksInfo.getId()).get();
        // Disconnect from session so that the updates on updatedSupplierBanksInfo are not directly saved in db
        em.detach(updatedSupplierBanksInfo);
        updatedSupplierBanksInfo
            .supplierBankCode(UPDATED_SUPPLIER_BANK_CODE)
            .supplierBankName(UPDATED_SUPPLIER_BANK_NAME)
            .supplierBankBranchCode(UPDATED_SUPPLIER_BANK_BRANCH_CODE)
            .supplierBankAccountNumber(UPDATED_SUPPLIER_BANK_ACCOUNT_NUMBER)
            .supplierBankUserName(UPDATED_SUPPLIER_BANK_USER_NAME)
            .supplierBankPixKey(UPDATED_SUPPLIER_BANK_PIX_KEY)
            .supplierBankUserNumber(UPDATED_SUPPLIER_BANK_USER_NUMBER);
        SupplierBanksInfoDTO supplierBanksInfoDTO = supplierBanksInfoMapper.toDto(updatedSupplierBanksInfo);

        restSupplierBanksInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, supplierBanksInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplierBanksInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the SupplierBanksInfo in the database
        List<SupplierBanksInfo> supplierBanksInfoList = supplierBanksInfoRepository.findAll();
        assertThat(supplierBanksInfoList).hasSize(databaseSizeBeforeUpdate);
        SupplierBanksInfo testSupplierBanksInfo = supplierBanksInfoList.get(supplierBanksInfoList.size() - 1);
        assertThat(testSupplierBanksInfo.getSupplierBankCode()).isEqualTo(UPDATED_SUPPLIER_BANK_CODE);
        assertThat(testSupplierBanksInfo.getSupplierBankName()).isEqualTo(UPDATED_SUPPLIER_BANK_NAME);
        assertThat(testSupplierBanksInfo.getSupplierBankBranchCode()).isEqualTo(UPDATED_SUPPLIER_BANK_BRANCH_CODE);
        assertThat(testSupplierBanksInfo.getSupplierBankAccountNumber()).isEqualTo(UPDATED_SUPPLIER_BANK_ACCOUNT_NUMBER);
        assertThat(testSupplierBanksInfo.getSupplierBankUserName()).isEqualTo(UPDATED_SUPPLIER_BANK_USER_NAME);
        assertThat(testSupplierBanksInfo.getSupplierBankPixKey()).isEqualTo(UPDATED_SUPPLIER_BANK_PIX_KEY);
        assertThat(testSupplierBanksInfo.getSupplierBankUserNumber()).isEqualTo(UPDATED_SUPPLIER_BANK_USER_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingSupplierBanksInfo() throws Exception {
        int databaseSizeBeforeUpdate = supplierBanksInfoRepository.findAll().size();
        supplierBanksInfo.setId(count.incrementAndGet());

        // Create the SupplierBanksInfo
        SupplierBanksInfoDTO supplierBanksInfoDTO = supplierBanksInfoMapper.toDto(supplierBanksInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierBanksInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, supplierBanksInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplierBanksInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplierBanksInfo in the database
        List<SupplierBanksInfo> supplierBanksInfoList = supplierBanksInfoRepository.findAll();
        assertThat(supplierBanksInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSupplierBanksInfo() throws Exception {
        int databaseSizeBeforeUpdate = supplierBanksInfoRepository.findAll().size();
        supplierBanksInfo.setId(count.incrementAndGet());

        // Create the SupplierBanksInfo
        SupplierBanksInfoDTO supplierBanksInfoDTO = supplierBanksInfoMapper.toDto(supplierBanksInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierBanksInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplierBanksInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplierBanksInfo in the database
        List<SupplierBanksInfo> supplierBanksInfoList = supplierBanksInfoRepository.findAll();
        assertThat(supplierBanksInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSupplierBanksInfo() throws Exception {
        int databaseSizeBeforeUpdate = supplierBanksInfoRepository.findAll().size();
        supplierBanksInfo.setId(count.incrementAndGet());

        // Create the SupplierBanksInfo
        SupplierBanksInfoDTO supplierBanksInfoDTO = supplierBanksInfoMapper.toDto(supplierBanksInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierBanksInfoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplierBanksInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SupplierBanksInfo in the database
        List<SupplierBanksInfo> supplierBanksInfoList = supplierBanksInfoRepository.findAll();
        assertThat(supplierBanksInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSupplierBanksInfoWithPatch() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        int databaseSizeBeforeUpdate = supplierBanksInfoRepository.findAll().size();

        // Update the supplierBanksInfo using partial update
        SupplierBanksInfo partialUpdatedSupplierBanksInfo = new SupplierBanksInfo();
        partialUpdatedSupplierBanksInfo.setId(supplierBanksInfo.getId());

        partialUpdatedSupplierBanksInfo
            .supplierBankCode(UPDATED_SUPPLIER_BANK_CODE)
            .supplierBankName(UPDATED_SUPPLIER_BANK_NAME)
            .supplierBankAccountNumber(UPDATED_SUPPLIER_BANK_ACCOUNT_NUMBER)
            .supplierBankUserName(UPDATED_SUPPLIER_BANK_USER_NAME)
            .supplierBankPixKey(UPDATED_SUPPLIER_BANK_PIX_KEY)
            .supplierBankUserNumber(UPDATED_SUPPLIER_BANK_USER_NUMBER);

        restSupplierBanksInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupplierBanksInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSupplierBanksInfo))
            )
            .andExpect(status().isOk());

        // Validate the SupplierBanksInfo in the database
        List<SupplierBanksInfo> supplierBanksInfoList = supplierBanksInfoRepository.findAll();
        assertThat(supplierBanksInfoList).hasSize(databaseSizeBeforeUpdate);
        SupplierBanksInfo testSupplierBanksInfo = supplierBanksInfoList.get(supplierBanksInfoList.size() - 1);
        assertThat(testSupplierBanksInfo.getSupplierBankCode()).isEqualTo(UPDATED_SUPPLIER_BANK_CODE);
        assertThat(testSupplierBanksInfo.getSupplierBankName()).isEqualTo(UPDATED_SUPPLIER_BANK_NAME);
        assertThat(testSupplierBanksInfo.getSupplierBankBranchCode()).isEqualTo(DEFAULT_SUPPLIER_BANK_BRANCH_CODE);
        assertThat(testSupplierBanksInfo.getSupplierBankAccountNumber()).isEqualTo(UPDATED_SUPPLIER_BANK_ACCOUNT_NUMBER);
        assertThat(testSupplierBanksInfo.getSupplierBankUserName()).isEqualTo(UPDATED_SUPPLIER_BANK_USER_NAME);
        assertThat(testSupplierBanksInfo.getSupplierBankPixKey()).isEqualTo(UPDATED_SUPPLIER_BANK_PIX_KEY);
        assertThat(testSupplierBanksInfo.getSupplierBankUserNumber()).isEqualTo(UPDATED_SUPPLIER_BANK_USER_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateSupplierBanksInfoWithPatch() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        int databaseSizeBeforeUpdate = supplierBanksInfoRepository.findAll().size();

        // Update the supplierBanksInfo using partial update
        SupplierBanksInfo partialUpdatedSupplierBanksInfo = new SupplierBanksInfo();
        partialUpdatedSupplierBanksInfo.setId(supplierBanksInfo.getId());

        partialUpdatedSupplierBanksInfo
            .supplierBankCode(UPDATED_SUPPLIER_BANK_CODE)
            .supplierBankName(UPDATED_SUPPLIER_BANK_NAME)
            .supplierBankBranchCode(UPDATED_SUPPLIER_BANK_BRANCH_CODE)
            .supplierBankAccountNumber(UPDATED_SUPPLIER_BANK_ACCOUNT_NUMBER)
            .supplierBankUserName(UPDATED_SUPPLIER_BANK_USER_NAME)
            .supplierBankPixKey(UPDATED_SUPPLIER_BANK_PIX_KEY)
            .supplierBankUserNumber(UPDATED_SUPPLIER_BANK_USER_NUMBER);

        restSupplierBanksInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupplierBanksInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSupplierBanksInfo))
            )
            .andExpect(status().isOk());

        // Validate the SupplierBanksInfo in the database
        List<SupplierBanksInfo> supplierBanksInfoList = supplierBanksInfoRepository.findAll();
        assertThat(supplierBanksInfoList).hasSize(databaseSizeBeforeUpdate);
        SupplierBanksInfo testSupplierBanksInfo = supplierBanksInfoList.get(supplierBanksInfoList.size() - 1);
        assertThat(testSupplierBanksInfo.getSupplierBankCode()).isEqualTo(UPDATED_SUPPLIER_BANK_CODE);
        assertThat(testSupplierBanksInfo.getSupplierBankName()).isEqualTo(UPDATED_SUPPLIER_BANK_NAME);
        assertThat(testSupplierBanksInfo.getSupplierBankBranchCode()).isEqualTo(UPDATED_SUPPLIER_BANK_BRANCH_CODE);
        assertThat(testSupplierBanksInfo.getSupplierBankAccountNumber()).isEqualTo(UPDATED_SUPPLIER_BANK_ACCOUNT_NUMBER);
        assertThat(testSupplierBanksInfo.getSupplierBankUserName()).isEqualTo(UPDATED_SUPPLIER_BANK_USER_NAME);
        assertThat(testSupplierBanksInfo.getSupplierBankPixKey()).isEqualTo(UPDATED_SUPPLIER_BANK_PIX_KEY);
        assertThat(testSupplierBanksInfo.getSupplierBankUserNumber()).isEqualTo(UPDATED_SUPPLIER_BANK_USER_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingSupplierBanksInfo() throws Exception {
        int databaseSizeBeforeUpdate = supplierBanksInfoRepository.findAll().size();
        supplierBanksInfo.setId(count.incrementAndGet());

        // Create the SupplierBanksInfo
        SupplierBanksInfoDTO supplierBanksInfoDTO = supplierBanksInfoMapper.toDto(supplierBanksInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierBanksInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, supplierBanksInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supplierBanksInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplierBanksInfo in the database
        List<SupplierBanksInfo> supplierBanksInfoList = supplierBanksInfoRepository.findAll();
        assertThat(supplierBanksInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSupplierBanksInfo() throws Exception {
        int databaseSizeBeforeUpdate = supplierBanksInfoRepository.findAll().size();
        supplierBanksInfo.setId(count.incrementAndGet());

        // Create the SupplierBanksInfo
        SupplierBanksInfoDTO supplierBanksInfoDTO = supplierBanksInfoMapper.toDto(supplierBanksInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierBanksInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supplierBanksInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplierBanksInfo in the database
        List<SupplierBanksInfo> supplierBanksInfoList = supplierBanksInfoRepository.findAll();
        assertThat(supplierBanksInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSupplierBanksInfo() throws Exception {
        int databaseSizeBeforeUpdate = supplierBanksInfoRepository.findAll().size();
        supplierBanksInfo.setId(count.incrementAndGet());

        // Create the SupplierBanksInfo
        SupplierBanksInfoDTO supplierBanksInfoDTO = supplierBanksInfoMapper.toDto(supplierBanksInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierBanksInfoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supplierBanksInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SupplierBanksInfo in the database
        List<SupplierBanksInfo> supplierBanksInfoList = supplierBanksInfoRepository.findAll();
        assertThat(supplierBanksInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSupplierBanksInfo() throws Exception {
        // Initialize the database
        supplierBanksInfoRepository.saveAndFlush(supplierBanksInfo);

        int databaseSizeBeforeDelete = supplierBanksInfoRepository.findAll().size();

        // Delete the supplierBanksInfo
        restSupplierBanksInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, supplierBanksInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SupplierBanksInfo> supplierBanksInfoList = supplierBanksInfoRepository.findAll();
        assertThat(supplierBanksInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
