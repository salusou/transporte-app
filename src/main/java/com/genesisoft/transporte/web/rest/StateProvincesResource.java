package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.StateProvincesRepository;
import com.genesisoft.transporte.service.StateProvincesQueryService;
import com.genesisoft.transporte.service.StateProvincesService;
import com.genesisoft.transporte.service.criteria.StateProvincesCriteria;
import com.genesisoft.transporte.service.dto.StateProvincesDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.StateProvinces}.
 */
@RestController
@RequestMapping("/api")
public class StateProvincesResource {

    private final Logger log = LoggerFactory.getLogger(StateProvincesResource.class);

    private static final String ENTITY_NAME = "stateProvinces";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StateProvincesService stateProvincesService;

    private final StateProvincesRepository stateProvincesRepository;

    private final StateProvincesQueryService stateProvincesQueryService;

    public StateProvincesResource(
        StateProvincesService stateProvincesService,
        StateProvincesRepository stateProvincesRepository,
        StateProvincesQueryService stateProvincesQueryService
    ) {
        this.stateProvincesService = stateProvincesService;
        this.stateProvincesRepository = stateProvincesRepository;
        this.stateProvincesQueryService = stateProvincesQueryService;
    }

    /**
     * {@code POST  /state-provinces} : Create a new stateProvinces.
     *
     * @param stateProvincesDTO the stateProvincesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stateProvincesDTO, or with status {@code 400 (Bad Request)} if the stateProvinces has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/state-provinces")
    public ResponseEntity<StateProvincesDTO> createStateProvinces(@Valid @RequestBody StateProvincesDTO stateProvincesDTO)
        throws URISyntaxException {
        log.debug("REST request to save StateProvinces : {}", stateProvincesDTO);
        if (stateProvincesDTO.getId() != null) {
            throw new BadRequestAlertException("A new stateProvinces cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StateProvincesDTO result = stateProvincesService.save(stateProvincesDTO);
        return ResponseEntity
            .created(new URI("/api/state-provinces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /state-provinces/:id} : Updates an existing stateProvinces.
     *
     * @param id the id of the stateProvincesDTO to save.
     * @param stateProvincesDTO the stateProvincesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stateProvincesDTO,
     * or with status {@code 400 (Bad Request)} if the stateProvincesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stateProvincesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/state-provinces/{id}")
    public ResponseEntity<StateProvincesDTO> updateStateProvinces(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StateProvincesDTO stateProvincesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StateProvinces : {}, {}", id, stateProvincesDTO);
        if (stateProvincesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stateProvincesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stateProvincesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StateProvincesDTO result = stateProvincesService.save(stateProvincesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stateProvincesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /state-provinces/:id} : Partial updates given fields of an existing stateProvinces, field will ignore if it is null
     *
     * @param id the id of the stateProvincesDTO to save.
     * @param stateProvincesDTO the stateProvincesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stateProvincesDTO,
     * or with status {@code 400 (Bad Request)} if the stateProvincesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stateProvincesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stateProvincesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/state-provinces/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<StateProvincesDTO> partialUpdateStateProvinces(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StateProvincesDTO stateProvincesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StateProvinces partially : {}, {}", id, stateProvincesDTO);
        if (stateProvincesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stateProvincesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stateProvincesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StateProvincesDTO> result = stateProvincesService.partialUpdate(stateProvincesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stateProvincesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /state-provinces} : get all the stateProvinces.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stateProvinces in body.
     */
    @GetMapping("/state-provinces")
    public ResponseEntity<List<StateProvincesDTO>> getAllStateProvinces(StateProvincesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StateProvinces by criteria: {}", criteria);
        Page<StateProvincesDTO> page = stateProvincesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /state-provinces/count} : count all the stateProvinces.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/state-provinces/count")
    public ResponseEntity<Long> countStateProvinces(StateProvincesCriteria criteria) {
        log.debug("REST request to count StateProvinces by criteria: {}", criteria);
        return ResponseEntity.ok().body(stateProvincesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /state-provinces/:id} : get the "id" stateProvinces.
     *
     * @param id the id of the stateProvincesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stateProvincesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/state-provinces/{id}")
    public ResponseEntity<StateProvincesDTO> getStateProvinces(@PathVariable Long id) {
        log.debug("REST request to get StateProvinces : {}", id);
        Optional<StateProvincesDTO> stateProvincesDTO = stateProvincesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stateProvincesDTO);
    }

    /**
     * {@code DELETE  /state-provinces/:id} : delete the "id" stateProvinces.
     *
     * @param id the id of the stateProvincesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/state-provinces/{id}")
    public ResponseEntity<Void> deleteStateProvinces(@PathVariable Long id) {
        log.debug("REST request to delete StateProvinces : {}", id);
        stateProvincesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
