package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.ParkingSectorSpaceRepository;
import com.genesisoft.transporte.service.ParkingSectorSpaceQueryService;
import com.genesisoft.transporte.service.ParkingSectorSpaceService;
import com.genesisoft.transporte.service.criteria.ParkingSectorSpaceCriteria;
import com.genesisoft.transporte.service.dto.ParkingSectorSpaceDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.ParkingSectorSpace}.
 */
@RestController
@RequestMapping("/api")
public class ParkingSectorSpaceResource {

    private final Logger log = LoggerFactory.getLogger(ParkingSectorSpaceResource.class);

    private static final String ENTITY_NAME = "parkingSectorSpace";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParkingSectorSpaceService parkingSectorSpaceService;

    private final ParkingSectorSpaceRepository parkingSectorSpaceRepository;

    private final ParkingSectorSpaceQueryService parkingSectorSpaceQueryService;

    public ParkingSectorSpaceResource(
        ParkingSectorSpaceService parkingSectorSpaceService,
        ParkingSectorSpaceRepository parkingSectorSpaceRepository,
        ParkingSectorSpaceQueryService parkingSectorSpaceQueryService
    ) {
        this.parkingSectorSpaceService = parkingSectorSpaceService;
        this.parkingSectorSpaceRepository = parkingSectorSpaceRepository;
        this.parkingSectorSpaceQueryService = parkingSectorSpaceQueryService;
    }

    /**
     * {@code POST  /parking-sector-spaces} : Create a new parkingSectorSpace.
     *
     * @param parkingSectorSpaceDTO the parkingSectorSpaceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parkingSectorSpaceDTO, or with status {@code 400 (Bad Request)} if the parkingSectorSpace has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parking-sector-spaces")
    public ResponseEntity<ParkingSectorSpaceDTO> createParkingSectorSpace(@Valid @RequestBody ParkingSectorSpaceDTO parkingSectorSpaceDTO)
        throws URISyntaxException {
        log.debug("REST request to save ParkingSectorSpace : {}", parkingSectorSpaceDTO);
        if (parkingSectorSpaceDTO.getId() != null) {
            throw new BadRequestAlertException("A new parkingSectorSpace cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParkingSectorSpaceDTO result = parkingSectorSpaceService.save(parkingSectorSpaceDTO);
        return ResponseEntity
            .created(new URI("/api/parking-sector-spaces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parking-sector-spaces/:id} : Updates an existing parkingSectorSpace.
     *
     * @param id the id of the parkingSectorSpaceDTO to save.
     * @param parkingSectorSpaceDTO the parkingSectorSpaceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parkingSectorSpaceDTO,
     * or with status {@code 400 (Bad Request)} if the parkingSectorSpaceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parkingSectorSpaceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parking-sector-spaces/{id}")
    public ResponseEntity<ParkingSectorSpaceDTO> updateParkingSectorSpace(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ParkingSectorSpaceDTO parkingSectorSpaceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ParkingSectorSpace : {}, {}", id, parkingSectorSpaceDTO);
        if (parkingSectorSpaceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parkingSectorSpaceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parkingSectorSpaceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ParkingSectorSpaceDTO result = parkingSectorSpaceService.save(parkingSectorSpaceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parkingSectorSpaceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /parking-sector-spaces/:id} : Partial updates given fields of an existing parkingSectorSpace, field will ignore if it is null
     *
     * @param id the id of the parkingSectorSpaceDTO to save.
     * @param parkingSectorSpaceDTO the parkingSectorSpaceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parkingSectorSpaceDTO,
     * or with status {@code 400 (Bad Request)} if the parkingSectorSpaceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the parkingSectorSpaceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the parkingSectorSpaceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/parking-sector-spaces/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ParkingSectorSpaceDTO> partialUpdateParkingSectorSpace(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ParkingSectorSpaceDTO parkingSectorSpaceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ParkingSectorSpace partially : {}, {}", id, parkingSectorSpaceDTO);
        if (parkingSectorSpaceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parkingSectorSpaceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parkingSectorSpaceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ParkingSectorSpaceDTO> result = parkingSectorSpaceService.partialUpdate(parkingSectorSpaceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parkingSectorSpaceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /parking-sector-spaces} : get all the parkingSectorSpaces.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parkingSectorSpaces in body.
     */
    @GetMapping("/parking-sector-spaces")
    public ResponseEntity<List<ParkingSectorSpaceDTO>> getAllParkingSectorSpaces(ParkingSectorSpaceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ParkingSectorSpaces by criteria: {}", criteria);
        Page<ParkingSectorSpaceDTO> page = parkingSectorSpaceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /parking-sector-spaces/count} : count all the parkingSectorSpaces.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/parking-sector-spaces/count")
    public ResponseEntity<Long> countParkingSectorSpaces(ParkingSectorSpaceCriteria criteria) {
        log.debug("REST request to count ParkingSectorSpaces by criteria: {}", criteria);
        return ResponseEntity.ok().body(parkingSectorSpaceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /parking-sector-spaces/:id} : get the "id" parkingSectorSpace.
     *
     * @param id the id of the parkingSectorSpaceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parkingSectorSpaceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parking-sector-spaces/{id}")
    public ResponseEntity<ParkingSectorSpaceDTO> getParkingSectorSpace(@PathVariable Long id) {
        log.debug("REST request to get ParkingSectorSpace : {}", id);
        Optional<ParkingSectorSpaceDTO> parkingSectorSpaceDTO = parkingSectorSpaceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parkingSectorSpaceDTO);
    }

    /**
     * {@code DELETE  /parking-sector-spaces/:id} : delete the "id" parkingSectorSpace.
     *
     * @param id the id of the parkingSectorSpaceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parking-sector-spaces/{id}")
    public ResponseEntity<Void> deleteParkingSectorSpace(@PathVariable Long id) {
        log.debug("REST request to delete ParkingSectorSpace : {}", id);
        parkingSectorSpaceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
