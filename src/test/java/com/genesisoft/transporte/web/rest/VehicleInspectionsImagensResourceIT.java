package com.genesisoft.transporte.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.genesisoft.transporte.IntegrationTest;
import com.genesisoft.transporte.domain.VehicleInspections;
import com.genesisoft.transporte.domain.VehicleInspectionsImagens;
import com.genesisoft.transporte.domain.enumeration.InspectionPositions;
import com.genesisoft.transporte.domain.enumeration.VehicleStatus;
import com.genesisoft.transporte.repository.VehicleInspectionsImagensRepository;
import com.genesisoft.transporte.service.criteria.VehicleInspectionsImagensCriteria;
import com.genesisoft.transporte.service.dto.VehicleInspectionsImagensDTO;
import com.genesisoft.transporte.service.mapper.VehicleInspectionsImagensMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link VehicleInspectionsImagensResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VehicleInspectionsImagensResourceIT {

    private static final InspectionPositions DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS = InspectionPositions.FRONT;
    private static final InspectionPositions UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS = InspectionPositions.REAR;

    private static final VehicleStatus DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_STATUS = VehicleStatus.DIRTY;
    private static final VehicleStatus UPDATED_VEHICLE_INSPECTIONS_IMAGENS_STATUS = VehicleStatus.CLEAN;

    private static final String DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_OBS = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_INSPECTIONS_IMAGENS_OBS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_VEHICLE_INSPECTIONS_IMAGENS_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VEHICLE_INSPECTIONS_IMAGENS_PHOTO_CONTENT_TYPE = "image/png";

    private static final Float DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X = 1F;
    private static final Float UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X = 2F;
    private static final Float SMALLER_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X = 1F - 1F;

    private static final Float DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y = 1F;
    private static final Float UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y = 2F;
    private static final Float SMALLER_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y = 1F - 1F;

    private static final String ENTITY_API_URL = "/api/vehicle-inspections-imagens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VehicleInspectionsImagensRepository vehicleInspectionsImagensRepository;

    @Autowired
    private VehicleInspectionsImagensMapper vehicleInspectionsImagensMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehicleInspectionsImagensMockMvc;

    private VehicleInspectionsImagens vehicleInspectionsImagens;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleInspectionsImagens createEntity(EntityManager em) {
        VehicleInspectionsImagens vehicleInspectionsImagens = new VehicleInspectionsImagens()
            .vehicleInspectionsImagensPositions(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS)
            .vehicleInspectionsImagensStatus(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_STATUS)
            .vehicleInspectionsImagensObs(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_OBS)
            .vehicleInspectionsImagensPhoto(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_PHOTO)
            .vehicleInspectionsImagensPhotoContentType(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_PHOTO_CONTENT_TYPE)
            .vehicleInspectionsImagensPositionsX(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X)
            .vehicleInspectionsImagensPositionsY(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y);
        // Add required entity
        VehicleInspections vehicleInspections;
        if (TestUtil.findAll(em, VehicleInspections.class).isEmpty()) {
            vehicleInspections = VehicleInspectionsResourceIT.createEntity(em);
            em.persist(vehicleInspections);
            em.flush();
        } else {
            vehicleInspections = TestUtil.findAll(em, VehicleInspections.class).get(0);
        }
        vehicleInspectionsImagens.setVehicleInspections(vehicleInspections);
        return vehicleInspectionsImagens;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleInspectionsImagens createUpdatedEntity(EntityManager em) {
        VehicleInspectionsImagens vehicleInspectionsImagens = new VehicleInspectionsImagens()
            .vehicleInspectionsImagensPositions(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS)
            .vehicleInspectionsImagensStatus(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_STATUS)
            .vehicleInspectionsImagensObs(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_OBS)
            .vehicleInspectionsImagensPhoto(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_PHOTO)
            .vehicleInspectionsImagensPhotoContentType(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_PHOTO_CONTENT_TYPE)
            .vehicleInspectionsImagensPositionsX(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X)
            .vehicleInspectionsImagensPositionsY(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y);
        // Add required entity
        VehicleInspections vehicleInspections;
        if (TestUtil.findAll(em, VehicleInspections.class).isEmpty()) {
            vehicleInspections = VehicleInspectionsResourceIT.createUpdatedEntity(em);
            em.persist(vehicleInspections);
            em.flush();
        } else {
            vehicleInspections = TestUtil.findAll(em, VehicleInspections.class).get(0);
        }
        vehicleInspectionsImagens.setVehicleInspections(vehicleInspections);
        return vehicleInspectionsImagens;
    }

    @BeforeEach
    public void initTest() {
        vehicleInspectionsImagens = createEntity(em);
    }

    @Test
    @Transactional
    void createVehicleInspectionsImagens() throws Exception {
        int databaseSizeBeforeCreate = vehicleInspectionsImagensRepository.findAll().size();
        // Create the VehicleInspectionsImagens
        VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO = vehicleInspectionsImagensMapper.toDto(vehicleInspectionsImagens);
        restVehicleInspectionsImagensMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsImagensDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VehicleInspectionsImagens in the database
        List<VehicleInspectionsImagens> vehicleInspectionsImagensList = vehicleInspectionsImagensRepository.findAll();
        assertThat(vehicleInspectionsImagensList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleInspectionsImagens testVehicleInspectionsImagens = vehicleInspectionsImagensList.get(
            vehicleInspectionsImagensList.size() - 1
        );
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensPositions())
            .isEqualTo(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensStatus())
            .isEqualTo(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_STATUS);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensObs()).isEqualTo(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_OBS);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensPhoto()).isEqualTo(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_PHOTO);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensPhotoContentType())
            .isEqualTo(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_PHOTO_CONTENT_TYPE);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensPositionsX())
            .isEqualTo(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensPositionsY())
            .isEqualTo(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y);
    }

    @Test
    @Transactional
    void createVehicleInspectionsImagensWithExistingId() throws Exception {
        // Create the VehicleInspectionsImagens with an existing ID
        vehicleInspectionsImagens.setId(1L);
        VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO = vehicleInspectionsImagensMapper.toDto(vehicleInspectionsImagens);

        int databaseSizeBeforeCreate = vehicleInspectionsImagensRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleInspectionsImagensMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsImagensDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleInspectionsImagens in the database
        List<VehicleInspectionsImagens> vehicleInspectionsImagensList = vehicleInspectionsImagensRepository.findAll();
        assertThat(vehicleInspectionsImagensList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVehicleInspectionsImagensPositionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInspectionsImagensRepository.findAll().size();
        // set the field null
        vehicleInspectionsImagens.setVehicleInspectionsImagensPositions(null);

        // Create the VehicleInspectionsImagens, which fails.
        VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO = vehicleInspectionsImagensMapper.toDto(vehicleInspectionsImagens);

        restVehicleInspectionsImagensMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsImagensDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleInspectionsImagens> vehicleInspectionsImagensList = vehicleInspectionsImagensRepository.findAll();
        assertThat(vehicleInspectionsImagensList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVehicleInspectionsImagensStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInspectionsImagensRepository.findAll().size();
        // set the field null
        vehicleInspectionsImagens.setVehicleInspectionsImagensStatus(null);

        // Create the VehicleInspectionsImagens, which fails.
        VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO = vehicleInspectionsImagensMapper.toDto(vehicleInspectionsImagens);

        restVehicleInspectionsImagensMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsImagensDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleInspectionsImagens> vehicleInspectionsImagensList = vehicleInspectionsImagensRepository.findAll();
        assertThat(vehicleInspectionsImagensList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVehicleInspectionsImagensPositionsXIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInspectionsImagensRepository.findAll().size();
        // set the field null
        vehicleInspectionsImagens.setVehicleInspectionsImagensPositionsX(null);

        // Create the VehicleInspectionsImagens, which fails.
        VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO = vehicleInspectionsImagensMapper.toDto(vehicleInspectionsImagens);

        restVehicleInspectionsImagensMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsImagensDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleInspectionsImagens> vehicleInspectionsImagensList = vehicleInspectionsImagensRepository.findAll();
        assertThat(vehicleInspectionsImagensList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVehicleInspectionsImagensPositionsYIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleInspectionsImagensRepository.findAll().size();
        // set the field null
        vehicleInspectionsImagens.setVehicleInspectionsImagensPositionsY(null);

        // Create the VehicleInspectionsImagens, which fails.
        VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO = vehicleInspectionsImagensMapper.toDto(vehicleInspectionsImagens);

        restVehicleInspectionsImagensMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsImagensDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleInspectionsImagens> vehicleInspectionsImagensList = vehicleInspectionsImagensRepository.findAll();
        assertThat(vehicleInspectionsImagensList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagens() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList
        restVehicleInspectionsImagensMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleInspectionsImagens.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].vehicleInspectionsImagensPositions")
                    .value(hasItem(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS.toString()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleInspectionsImagensStatus").value(hasItem(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_STATUS.toString()))
            )
            .andExpect(jsonPath("$.[*].vehicleInspectionsImagensObs").value(hasItem(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_OBS)))
            .andExpect(
                jsonPath("$.[*].vehicleInspectionsImagensPhotoContentType")
                    .value(hasItem(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_PHOTO_CONTENT_TYPE))
            )
            .andExpect(
                jsonPath("$.[*].vehicleInspectionsImagensPhoto")
                    .value(hasItem(Base64Utils.encodeToString(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_PHOTO)))
            )
            .andExpect(
                jsonPath("$.[*].vehicleInspectionsImagensPositionsX")
                    .value(hasItem(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleInspectionsImagensPositionsY")
                    .value(hasItem(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y.doubleValue()))
            );
    }

    @Test
    @Transactional
    void getVehicleInspectionsImagens() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get the vehicleInspectionsImagens
        restVehicleInspectionsImagensMockMvc
            .perform(get(ENTITY_API_URL_ID, vehicleInspectionsImagens.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleInspectionsImagens.getId().intValue()))
            .andExpect(jsonPath("$.vehicleInspectionsImagensPositions").value(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS.toString()))
            .andExpect(jsonPath("$.vehicleInspectionsImagensStatus").value(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_STATUS.toString()))
            .andExpect(jsonPath("$.vehicleInspectionsImagensObs").value(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_OBS))
            .andExpect(
                jsonPath("$.vehicleInspectionsImagensPhotoContentType").value(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_PHOTO_CONTENT_TYPE)
            )
            .andExpect(
                jsonPath("$.vehicleInspectionsImagensPhoto").value(Base64Utils.encodeToString(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_PHOTO))
            )
            .andExpect(
                jsonPath("$.vehicleInspectionsImagensPositionsX").value(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X.doubleValue())
            )
            .andExpect(
                jsonPath("$.vehicleInspectionsImagensPositionsY").value(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y.doubleValue())
            );
    }

    @Test
    @Transactional
    void getVehicleInspectionsImagensByIdFiltering() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        Long id = vehicleInspectionsImagens.getId();

        defaultVehicleInspectionsImagensShouldBeFound("id.equals=" + id);
        defaultVehicleInspectionsImagensShouldNotBeFound("id.notEquals=" + id);

        defaultVehicleInspectionsImagensShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVehicleInspectionsImagensShouldNotBeFound("id.greaterThan=" + id);

        defaultVehicleInspectionsImagensShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVehicleInspectionsImagensShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensPositionsIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositions equals to DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensPositions.equals=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositions equals to UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensPositions.equals=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensPositionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositions not equals to DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensPositions.notEquals=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositions not equals to UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensPositions.notEquals=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensPositionsIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositions in DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS or UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensPositions.in=" +
            DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS +
            "," +
            UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositions equals to UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensPositions.in=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensPositionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositions is not null
        defaultVehicleInspectionsImagensShouldBeFound("vehicleInspectionsImagensPositions.specified=true");

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositions is null
        defaultVehicleInspectionsImagensShouldNotBeFound("vehicleInspectionsImagensPositions.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensStatus equals to DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_STATUS
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensStatus.equals=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_STATUS
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensStatus equals to UPDATED_VEHICLE_INSPECTIONS_IMAGENS_STATUS
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensStatus.equals=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_STATUS
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensStatus not equals to DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_STATUS
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensStatus.notEquals=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_STATUS
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensStatus not equals to UPDATED_VEHICLE_INSPECTIONS_IMAGENS_STATUS
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensStatus.notEquals=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_STATUS
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensStatusIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensStatus in DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_STATUS or UPDATED_VEHICLE_INSPECTIONS_IMAGENS_STATUS
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensStatus.in=" +
            DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_STATUS +
            "," +
            UPDATED_VEHICLE_INSPECTIONS_IMAGENS_STATUS
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensStatus equals to UPDATED_VEHICLE_INSPECTIONS_IMAGENS_STATUS
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensStatus.in=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_STATUS
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensStatus is not null
        defaultVehicleInspectionsImagensShouldBeFound("vehicleInspectionsImagensStatus.specified=true");

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensStatus is null
        defaultVehicleInspectionsImagensShouldNotBeFound("vehicleInspectionsImagensStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensObsIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensObs equals to DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_OBS
        defaultVehicleInspectionsImagensShouldBeFound("vehicleInspectionsImagensObs.equals=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_OBS);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensObs equals to UPDATED_VEHICLE_INSPECTIONS_IMAGENS_OBS
        defaultVehicleInspectionsImagensShouldNotBeFound("vehicleInspectionsImagensObs.equals=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_OBS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensObsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensObs not equals to DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_OBS
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensObs.notEquals=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_OBS
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensObs not equals to UPDATED_VEHICLE_INSPECTIONS_IMAGENS_OBS
        defaultVehicleInspectionsImagensShouldBeFound("vehicleInspectionsImagensObs.notEquals=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_OBS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensObsIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensObs in DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_OBS or UPDATED_VEHICLE_INSPECTIONS_IMAGENS_OBS
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensObs.in=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_OBS + "," + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_OBS
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensObs equals to UPDATED_VEHICLE_INSPECTIONS_IMAGENS_OBS
        defaultVehicleInspectionsImagensShouldNotBeFound("vehicleInspectionsImagensObs.in=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_OBS);
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensObsIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensObs is not null
        defaultVehicleInspectionsImagensShouldBeFound("vehicleInspectionsImagensObs.specified=true");

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensObs is null
        defaultVehicleInspectionsImagensShouldNotBeFound("vehicleInspectionsImagensObs.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensObsContainsSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensObs contains DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_OBS
        defaultVehicleInspectionsImagensShouldBeFound("vehicleInspectionsImagensObs.contains=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_OBS);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensObs contains UPDATED_VEHICLE_INSPECTIONS_IMAGENS_OBS
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensObs.contains=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_OBS
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensObsNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensObs does not contain DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_OBS
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensObs.doesNotContain=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_OBS
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensObs does not contain UPDATED_VEHICLE_INSPECTIONS_IMAGENS_OBS
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensObs.doesNotContain=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_OBS
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensPositionsXIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsX equals to DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensPositionsX.equals=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsX equals to UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensPositionsX.equals=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensPositionsXIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsX not equals to DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensPositionsX.notEquals=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsX not equals to UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensPositionsX.notEquals=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensPositionsXIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsX in DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X or UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensPositionsX.in=" +
            DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X +
            "," +
            UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsX equals to UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensPositionsX.in=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensPositionsXIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsX is not null
        defaultVehicleInspectionsImagensShouldBeFound("vehicleInspectionsImagensPositionsX.specified=true");

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsX is null
        defaultVehicleInspectionsImagensShouldNotBeFound("vehicleInspectionsImagensPositionsX.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensPositionsXIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsX is greater than or equal to DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensPositionsX.greaterThanOrEqual=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsX is greater than or equal to UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensPositionsX.greaterThanOrEqual=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensPositionsXIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsX is less than or equal to DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensPositionsX.lessThanOrEqual=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsX is less than or equal to SMALLER_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensPositionsX.lessThanOrEqual=" + SMALLER_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensPositionsXIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsX is less than DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensPositionsX.lessThan=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsX is less than UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensPositionsX.lessThan=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensPositionsXIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsX is greater than DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensPositionsX.greaterThan=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsX is greater than SMALLER_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensPositionsX.greaterThan=" + SMALLER_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensPositionsYIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsY equals to DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensPositionsY.equals=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsY equals to UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensPositionsY.equals=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensPositionsYIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsY not equals to DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensPositionsY.notEquals=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsY not equals to UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensPositionsY.notEquals=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensPositionsYIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsY in DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y or UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensPositionsY.in=" +
            DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y +
            "," +
            UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsY equals to UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensPositionsY.in=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensPositionsYIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsY is not null
        defaultVehicleInspectionsImagensShouldBeFound("vehicleInspectionsImagensPositionsY.specified=true");

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsY is null
        defaultVehicleInspectionsImagensShouldNotBeFound("vehicleInspectionsImagensPositionsY.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensPositionsYIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsY is greater than or equal to DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensPositionsY.greaterThanOrEqual=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsY is greater than or equal to UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensPositionsY.greaterThanOrEqual=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensPositionsYIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsY is less than or equal to DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensPositionsY.lessThanOrEqual=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsY is less than or equal to SMALLER_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensPositionsY.lessThanOrEqual=" + SMALLER_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensPositionsYIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsY is less than DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensPositionsY.lessThan=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsY is less than UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensPositionsY.lessThan=" + UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsImagensPositionsYIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsY is greater than DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        defaultVehicleInspectionsImagensShouldNotBeFound(
            "vehicleInspectionsImagensPositionsY.greaterThan=" + DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        );

        // Get all the vehicleInspectionsImagensList where vehicleInspectionsImagensPositionsY is greater than SMALLER_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        defaultVehicleInspectionsImagensShouldBeFound(
            "vehicleInspectionsImagensPositionsY.greaterThan=" + SMALLER_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y
        );
    }

    @Test
    @Transactional
    void getAllVehicleInspectionsImagensByVehicleInspectionsIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);
        VehicleInspections vehicleInspections = VehicleInspectionsResourceIT.createEntity(em);
        em.persist(vehicleInspections);
        em.flush();
        vehicleInspectionsImagens.setVehicleInspections(vehicleInspections);
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);
        Long vehicleInspectionsId = vehicleInspections.getId();

        // Get all the vehicleInspectionsImagensList where vehicleInspections equals to vehicleInspectionsId
        defaultVehicleInspectionsImagensShouldBeFound("vehicleInspectionsId.equals=" + vehicleInspectionsId);

        // Get all the vehicleInspectionsImagensList where vehicleInspections equals to (vehicleInspectionsId + 1)
        defaultVehicleInspectionsImagensShouldNotBeFound("vehicleInspectionsId.equals=" + (vehicleInspectionsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVehicleInspectionsImagensShouldBeFound(String filter) throws Exception {
        restVehicleInspectionsImagensMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleInspectionsImagens.getId().intValue())))
            .andExpect(
                jsonPath("$.[*].vehicleInspectionsImagensPositions")
                    .value(hasItem(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS.toString()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleInspectionsImagensStatus").value(hasItem(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_STATUS.toString()))
            )
            .andExpect(jsonPath("$.[*].vehicleInspectionsImagensObs").value(hasItem(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_OBS)))
            .andExpect(
                jsonPath("$.[*].vehicleInspectionsImagensPhotoContentType")
                    .value(hasItem(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_PHOTO_CONTENT_TYPE))
            )
            .andExpect(
                jsonPath("$.[*].vehicleInspectionsImagensPhoto")
                    .value(hasItem(Base64Utils.encodeToString(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_PHOTO)))
            )
            .andExpect(
                jsonPath("$.[*].vehicleInspectionsImagensPositionsX")
                    .value(hasItem(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X.doubleValue()))
            )
            .andExpect(
                jsonPath("$.[*].vehicleInspectionsImagensPositionsY")
                    .value(hasItem(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y.doubleValue()))
            );

        // Check, that the count call also returns 1
        restVehicleInspectionsImagensMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVehicleInspectionsImagensShouldNotBeFound(String filter) throws Exception {
        restVehicleInspectionsImagensMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVehicleInspectionsImagensMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVehicleInspectionsImagens() throws Exception {
        // Get the vehicleInspectionsImagens
        restVehicleInspectionsImagensMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVehicleInspectionsImagens() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        int databaseSizeBeforeUpdate = vehicleInspectionsImagensRepository.findAll().size();

        // Update the vehicleInspectionsImagens
        VehicleInspectionsImagens updatedVehicleInspectionsImagens = vehicleInspectionsImagensRepository
            .findById(vehicleInspectionsImagens.getId())
            .get();
        // Disconnect from session so that the updates on updatedVehicleInspectionsImagens are not directly saved in db
        em.detach(updatedVehicleInspectionsImagens);
        updatedVehicleInspectionsImagens
            .vehicleInspectionsImagensPositions(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS)
            .vehicleInspectionsImagensStatus(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_STATUS)
            .vehicleInspectionsImagensObs(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_OBS)
            .vehicleInspectionsImagensPhoto(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_PHOTO)
            .vehicleInspectionsImagensPhotoContentType(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_PHOTO_CONTENT_TYPE)
            .vehicleInspectionsImagensPositionsX(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X)
            .vehicleInspectionsImagensPositionsY(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y);
        VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO = vehicleInspectionsImagensMapper.toDto(updatedVehicleInspectionsImagens);

        restVehicleInspectionsImagensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleInspectionsImagensDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsImagensDTO))
            )
            .andExpect(status().isOk());

        // Validate the VehicleInspectionsImagens in the database
        List<VehicleInspectionsImagens> vehicleInspectionsImagensList = vehicleInspectionsImagensRepository.findAll();
        assertThat(vehicleInspectionsImagensList).hasSize(databaseSizeBeforeUpdate);
        VehicleInspectionsImagens testVehicleInspectionsImagens = vehicleInspectionsImagensList.get(
            vehicleInspectionsImagensList.size() - 1
        );
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensPositions())
            .isEqualTo(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensStatus())
            .isEqualTo(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_STATUS);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensObs()).isEqualTo(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_OBS);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensPhoto()).isEqualTo(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_PHOTO);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensPhotoContentType())
            .isEqualTo(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_PHOTO_CONTENT_TYPE);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensPositionsX())
            .isEqualTo(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensPositionsY())
            .isEqualTo(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y);
    }

    @Test
    @Transactional
    void putNonExistingVehicleInspectionsImagens() throws Exception {
        int databaseSizeBeforeUpdate = vehicleInspectionsImagensRepository.findAll().size();
        vehicleInspectionsImagens.setId(count.incrementAndGet());

        // Create the VehicleInspectionsImagens
        VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO = vehicleInspectionsImagensMapper.toDto(vehicleInspectionsImagens);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleInspectionsImagensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleInspectionsImagensDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsImagensDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleInspectionsImagens in the database
        List<VehicleInspectionsImagens> vehicleInspectionsImagensList = vehicleInspectionsImagensRepository.findAll();
        assertThat(vehicleInspectionsImagensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVehicleInspectionsImagens() throws Exception {
        int databaseSizeBeforeUpdate = vehicleInspectionsImagensRepository.findAll().size();
        vehicleInspectionsImagens.setId(count.incrementAndGet());

        // Create the VehicleInspectionsImagens
        VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO = vehicleInspectionsImagensMapper.toDto(vehicleInspectionsImagens);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleInspectionsImagensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsImagensDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleInspectionsImagens in the database
        List<VehicleInspectionsImagens> vehicleInspectionsImagensList = vehicleInspectionsImagensRepository.findAll();
        assertThat(vehicleInspectionsImagensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVehicleInspectionsImagens() throws Exception {
        int databaseSizeBeforeUpdate = vehicleInspectionsImagensRepository.findAll().size();
        vehicleInspectionsImagens.setId(count.incrementAndGet());

        // Create the VehicleInspectionsImagens
        VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO = vehicleInspectionsImagensMapper.toDto(vehicleInspectionsImagens);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleInspectionsImagensMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsImagensDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleInspectionsImagens in the database
        List<VehicleInspectionsImagens> vehicleInspectionsImagensList = vehicleInspectionsImagensRepository.findAll();
        assertThat(vehicleInspectionsImagensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVehicleInspectionsImagensWithPatch() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        int databaseSizeBeforeUpdate = vehicleInspectionsImagensRepository.findAll().size();

        // Update the vehicleInspectionsImagens using partial update
        VehicleInspectionsImagens partialUpdatedVehicleInspectionsImagens = new VehicleInspectionsImagens();
        partialUpdatedVehicleInspectionsImagens.setId(vehicleInspectionsImagens.getId());

        partialUpdatedVehicleInspectionsImagens
            .vehicleInspectionsImagensStatus(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_STATUS)
            .vehicleInspectionsImagensPhoto(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_PHOTO)
            .vehicleInspectionsImagensPhotoContentType(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_PHOTO_CONTENT_TYPE);

        restVehicleInspectionsImagensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleInspectionsImagens.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleInspectionsImagens))
            )
            .andExpect(status().isOk());

        // Validate the VehicleInspectionsImagens in the database
        List<VehicleInspectionsImagens> vehicleInspectionsImagensList = vehicleInspectionsImagensRepository.findAll();
        assertThat(vehicleInspectionsImagensList).hasSize(databaseSizeBeforeUpdate);
        VehicleInspectionsImagens testVehicleInspectionsImagens = vehicleInspectionsImagensList.get(
            vehicleInspectionsImagensList.size() - 1
        );
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensPositions())
            .isEqualTo(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensStatus())
            .isEqualTo(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_STATUS);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensObs()).isEqualTo(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_OBS);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensPhoto()).isEqualTo(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_PHOTO);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensPhotoContentType())
            .isEqualTo(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_PHOTO_CONTENT_TYPE);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensPositionsX())
            .isEqualTo(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensPositionsY())
            .isEqualTo(DEFAULT_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y);
    }

    @Test
    @Transactional
    void fullUpdateVehicleInspectionsImagensWithPatch() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        int databaseSizeBeforeUpdate = vehicleInspectionsImagensRepository.findAll().size();

        // Update the vehicleInspectionsImagens using partial update
        VehicleInspectionsImagens partialUpdatedVehicleInspectionsImagens = new VehicleInspectionsImagens();
        partialUpdatedVehicleInspectionsImagens.setId(vehicleInspectionsImagens.getId());

        partialUpdatedVehicleInspectionsImagens
            .vehicleInspectionsImagensPositions(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS)
            .vehicleInspectionsImagensStatus(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_STATUS)
            .vehicleInspectionsImagensObs(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_OBS)
            .vehicleInspectionsImagensPhoto(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_PHOTO)
            .vehicleInspectionsImagensPhotoContentType(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_PHOTO_CONTENT_TYPE)
            .vehicleInspectionsImagensPositionsX(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X)
            .vehicleInspectionsImagensPositionsY(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y);

        restVehicleInspectionsImagensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleInspectionsImagens.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleInspectionsImagens))
            )
            .andExpect(status().isOk());

        // Validate the VehicleInspectionsImagens in the database
        List<VehicleInspectionsImagens> vehicleInspectionsImagensList = vehicleInspectionsImagensRepository.findAll();
        assertThat(vehicleInspectionsImagensList).hasSize(databaseSizeBeforeUpdate);
        VehicleInspectionsImagens testVehicleInspectionsImagens = vehicleInspectionsImagensList.get(
            vehicleInspectionsImagensList.size() - 1
        );
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensPositions())
            .isEqualTo(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensStatus())
            .isEqualTo(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_STATUS);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensObs()).isEqualTo(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_OBS);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensPhoto()).isEqualTo(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_PHOTO);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensPhotoContentType())
            .isEqualTo(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_PHOTO_CONTENT_TYPE);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensPositionsX())
            .isEqualTo(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_X);
        assertThat(testVehicleInspectionsImagens.getVehicleInspectionsImagensPositionsY())
            .isEqualTo(UPDATED_VEHICLE_INSPECTIONS_IMAGENS_POSITIONS_Y);
    }

    @Test
    @Transactional
    void patchNonExistingVehicleInspectionsImagens() throws Exception {
        int databaseSizeBeforeUpdate = vehicleInspectionsImagensRepository.findAll().size();
        vehicleInspectionsImagens.setId(count.incrementAndGet());

        // Create the VehicleInspectionsImagens
        VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO = vehicleInspectionsImagensMapper.toDto(vehicleInspectionsImagens);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleInspectionsImagensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vehicleInspectionsImagensDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsImagensDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleInspectionsImagens in the database
        List<VehicleInspectionsImagens> vehicleInspectionsImagensList = vehicleInspectionsImagensRepository.findAll();
        assertThat(vehicleInspectionsImagensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVehicleInspectionsImagens() throws Exception {
        int databaseSizeBeforeUpdate = vehicleInspectionsImagensRepository.findAll().size();
        vehicleInspectionsImagens.setId(count.incrementAndGet());

        // Create the VehicleInspectionsImagens
        VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO = vehicleInspectionsImagensMapper.toDto(vehicleInspectionsImagens);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleInspectionsImagensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsImagensDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleInspectionsImagens in the database
        List<VehicleInspectionsImagens> vehicleInspectionsImagensList = vehicleInspectionsImagensRepository.findAll();
        assertThat(vehicleInspectionsImagensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVehicleInspectionsImagens() throws Exception {
        int databaseSizeBeforeUpdate = vehicleInspectionsImagensRepository.findAll().size();
        vehicleInspectionsImagens.setId(count.incrementAndGet());

        // Create the VehicleInspectionsImagens
        VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO = vehicleInspectionsImagensMapper.toDto(vehicleInspectionsImagens);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleInspectionsImagensMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleInspectionsImagensDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleInspectionsImagens in the database
        List<VehicleInspectionsImagens> vehicleInspectionsImagensList = vehicleInspectionsImagensRepository.findAll();
        assertThat(vehicleInspectionsImagensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVehicleInspectionsImagens() throws Exception {
        // Initialize the database
        vehicleInspectionsImagensRepository.saveAndFlush(vehicleInspectionsImagens);

        int databaseSizeBeforeDelete = vehicleInspectionsImagensRepository.findAll().size();

        // Delete the vehicleInspectionsImagens
        restVehicleInspectionsImagensMockMvc
            .perform(delete(ENTITY_API_URL_ID, vehicleInspectionsImagens.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehicleInspectionsImagens> vehicleInspectionsImagensList = vehicleInspectionsImagensRepository.findAll();
        assertThat(vehicleInspectionsImagensList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
