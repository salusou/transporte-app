package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.ParkingSectorRepository;
import com.genesisoft.transporte.service.ParkingSectorQueryService;
import com.genesisoft.transporte.service.ParkingSectorService;
import com.genesisoft.transporte.service.criteria.ParkingSectorCriteria;
import com.genesisoft.transporte.service.dto.ParkingSectorDTO;
import com.genesisoft.transporte.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.genesisoft.transporte.domain.ParkingSector}.
 */
@RestController
@RequestMapping("/api")
public class ParkingSectorResource {

    private final Logger log = LoggerFactory.getLogger(ParkingSectorResource.class);

    private static final String ENTITY_NAME = "parkingSector";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParkingSectorService parkingSectorService;

    private final ParkingSectorRepository parkingSectorRepository;

    private final ParkingSectorQueryService parkingSectorQueryService;

    public ParkingSectorResource(
        ParkingSectorService parkingSectorService,
        ParkingSectorRepository parkingSectorRepository,
        ParkingSectorQueryService parkingSectorQueryService
    ) {
        this.parkingSectorService = parkingSectorService;
        this.parkingSectorRepository = parkingSectorRepository;
        this.parkingSectorQueryService = parkingSectorQueryService;
    }

    /**
     * {@code POST  /parking-sectors} : Create a new parkingSector.
     *
     * @param parkingSectorDTO the parkingSectorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parkingSectorDTO, or with status {@code 400 (Bad Request)} if the parkingSector has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parking-sectors")
    public ResponseEntity<ParkingSectorDTO> createParkingSector(@Valid @RequestBody ParkingSectorDTO parkingSectorDTO)
        throws URISyntaxException {
        log.debug("REST request to save ParkingSector : {}", parkingSectorDTO);
        if (parkingSectorDTO.getId() != null) {
            throw new BadRequestAlertException("A new parkingSector cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParkingSectorDTO result = parkingSectorService.save(parkingSectorDTO);
        return ResponseEntity
            .created(new URI("/api/parking-sectors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parking-sectors/:id} : Updates an existing parkingSector.
     *
     * @param id the id of the parkingSectorDTO to save.
     * @param parkingSectorDTO the parkingSectorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parkingSectorDTO,
     * or with status {@code 400 (Bad Request)} if the parkingSectorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parkingSectorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parking-sectors/{id}")
    public ResponseEntity<ParkingSectorDTO> updateParkingSector(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ParkingSectorDTO parkingSectorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ParkingSector : {}, {}", id, parkingSectorDTO);
        if (parkingSectorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parkingSectorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parkingSectorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ParkingSectorDTO result = parkingSectorService.save(parkingSectorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parkingSectorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /parking-sectors/:id} : Partial updates given fields of an existing parkingSector, field will ignore if it is null
     *
     * @param id the id of the parkingSectorDTO to save.
     * @param parkingSectorDTO the parkingSectorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parkingSectorDTO,
     * or with status {@code 400 (Bad Request)} if the parkingSectorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the parkingSectorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the parkingSectorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/parking-sectors/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ParkingSectorDTO> partialUpdateParkingSector(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ParkingSectorDTO parkingSectorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ParkingSector partially : {}, {}", id, parkingSectorDTO);
        if (parkingSectorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parkingSectorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parkingSectorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ParkingSectorDTO> result = parkingSectorService.partialUpdate(parkingSectorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parkingSectorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /parking-sectors} : get all the parkingSectors.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parkingSectors in body.
     */
    @GetMapping("/parking-sectors")
    public ResponseEntity<List<ParkingSectorDTO>> getAllParkingSectors(ParkingSectorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ParkingSectors by criteria: {}", criteria);
        Page<ParkingSectorDTO> page = parkingSectorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /parking-sectors/count} : count all the parkingSectors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/parking-sectors/count")
    public ResponseEntity<Long> countParkingSectors(ParkingSectorCriteria criteria) {
        log.debug("REST request to count ParkingSectors by criteria: {}", criteria);
        return ResponseEntity.ok().body(parkingSectorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /parking-sectors/:id} : get the "id" parkingSector.
     *
     * @param id the id of the parkingSectorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parkingSectorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parking-sectors/{id}")
    public ResponseEntity<ParkingSectorDTO> getParkingSector(@PathVariable Long id) {
        log.debug("REST request to get ParkingSector : {}", id);
        Optional<ParkingSectorDTO> parkingSectorDTO = parkingSectorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parkingSectorDTO);
    }

    /**
     * {@code DELETE  /parking-sectors/:id} : delete the "id" parkingSector.
     *
     * @param id the id of the parkingSectorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parking-sectors/{id}")
    public ResponseEntity<Void> deleteParkingSector(@PathVariable Long id) {
        log.debug("REST request to delete ParkingSector : {}", id);
        parkingSectorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
