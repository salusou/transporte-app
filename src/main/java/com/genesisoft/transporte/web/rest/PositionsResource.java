package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.PositionsRepository;
import com.genesisoft.transporte.service.PositionsQueryService;
import com.genesisoft.transporte.service.PositionsService;
import com.genesisoft.transporte.service.criteria.PositionsCriteria;
import com.genesisoft.transporte.service.dto.PositionsDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.Positions}.
 */
@RestController
@RequestMapping("/api")
public class PositionsResource {

    private final Logger log = LoggerFactory.getLogger(PositionsResource.class);

    private static final String ENTITY_NAME = "positions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PositionsService positionsService;

    private final PositionsRepository positionsRepository;

    private final PositionsQueryService positionsQueryService;

    public PositionsResource(
        PositionsService positionsService,
        PositionsRepository positionsRepository,
        PositionsQueryService positionsQueryService
    ) {
        this.positionsService = positionsService;
        this.positionsRepository = positionsRepository;
        this.positionsQueryService = positionsQueryService;
    }

    /**
     * {@code POST  /positions} : Create a new positions.
     *
     * @param positionsDTO the positionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new positionsDTO, or with status {@code 400 (Bad Request)} if the positions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/positions")
    public ResponseEntity<PositionsDTO> createPositions(@Valid @RequestBody PositionsDTO positionsDTO) throws URISyntaxException {
        log.debug("REST request to save Positions : {}", positionsDTO);
        if (positionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new positions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PositionsDTO result = positionsService.save(positionsDTO);
        return ResponseEntity
            .created(new URI("/api/positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /positions/:id} : Updates an existing positions.
     *
     * @param id the id of the positionsDTO to save.
     * @param positionsDTO the positionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated positionsDTO,
     * or with status {@code 400 (Bad Request)} if the positionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the positionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/positions/{id}")
    public ResponseEntity<PositionsDTO> updatePositions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PositionsDTO positionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Positions : {}, {}", id, positionsDTO);
        if (positionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, positionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!positionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PositionsDTO result = positionsService.save(positionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, positionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /positions/:id} : Partial updates given fields of an existing positions, field will ignore if it is null
     *
     * @param id the id of the positionsDTO to save.
     * @param positionsDTO the positionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated positionsDTO,
     * or with status {@code 400 (Bad Request)} if the positionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the positionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the positionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/positions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PositionsDTO> partialUpdatePositions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PositionsDTO positionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Positions partially : {}, {}", id, positionsDTO);
        if (positionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, positionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!positionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PositionsDTO> result = positionsService.partialUpdate(positionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, positionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /positions} : get all the positions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of positions in body.
     */
    @GetMapping("/positions")
    public ResponseEntity<List<PositionsDTO>> getAllPositions(PositionsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Positions by criteria: {}", criteria);
        Page<PositionsDTO> page = positionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /positions/count} : count all the positions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/positions/count")
    public ResponseEntity<Long> countPositions(PositionsCriteria criteria) {
        log.debug("REST request to count Positions by criteria: {}", criteria);
        return ResponseEntity.ok().body(positionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /positions/:id} : get the "id" positions.
     *
     * @param id the id of the positionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the positionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/positions/{id}")
    public ResponseEntity<PositionsDTO> getPositions(@PathVariable Long id) {
        log.debug("REST request to get Positions : {}", id);
        Optional<PositionsDTO> positionsDTO = positionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(positionsDTO);
    }

    /**
     * {@code DELETE  /positions/:id} : delete the "id" positions.
     *
     * @param id the id of the positionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/positions/{id}")
    public ResponseEntity<Void> deletePositions(@PathVariable Long id) {
        log.debug("REST request to delete Positions : {}", id);
        positionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
