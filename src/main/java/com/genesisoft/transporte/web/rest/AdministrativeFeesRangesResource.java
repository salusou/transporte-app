package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.AdministrativeFeesRangesRepository;
import com.genesisoft.transporte.service.AdministrativeFeesRangesQueryService;
import com.genesisoft.transporte.service.AdministrativeFeesRangesService;
import com.genesisoft.transporte.service.criteria.AdministrativeFeesRangesCriteria;
import com.genesisoft.transporte.service.dto.AdministrativeFeesRangesDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.AdministrativeFeesRanges}.
 */
@RestController
@RequestMapping("/api")
public class AdministrativeFeesRangesResource {

    private final Logger log = LoggerFactory.getLogger(AdministrativeFeesRangesResource.class);

    private static final String ENTITY_NAME = "administrativeFeesRanges";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdministrativeFeesRangesService administrativeFeesRangesService;

    private final AdministrativeFeesRangesRepository administrativeFeesRangesRepository;

    private final AdministrativeFeesRangesQueryService administrativeFeesRangesQueryService;

    public AdministrativeFeesRangesResource(
        AdministrativeFeesRangesService administrativeFeesRangesService,
        AdministrativeFeesRangesRepository administrativeFeesRangesRepository,
        AdministrativeFeesRangesQueryService administrativeFeesRangesQueryService
    ) {
        this.administrativeFeesRangesService = administrativeFeesRangesService;
        this.administrativeFeesRangesRepository = administrativeFeesRangesRepository;
        this.administrativeFeesRangesQueryService = administrativeFeesRangesQueryService;
    }

    /**
     * {@code POST  /administrative-fees-ranges} : Create a new administrativeFeesRanges.
     *
     * @param administrativeFeesRangesDTO the administrativeFeesRangesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new administrativeFeesRangesDTO, or with status {@code 400 (Bad Request)} if the administrativeFeesRanges has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/administrative-fees-ranges")
    public ResponseEntity<AdministrativeFeesRangesDTO> createAdministrativeFeesRanges(
        @Valid @RequestBody AdministrativeFeesRangesDTO administrativeFeesRangesDTO
    ) throws URISyntaxException {
        log.debug("REST request to save AdministrativeFeesRanges : {}", administrativeFeesRangesDTO);
        if (administrativeFeesRangesDTO.getId() != null) {
            throw new BadRequestAlertException("A new administrativeFeesRanges cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdministrativeFeesRangesDTO result = administrativeFeesRangesService.save(administrativeFeesRangesDTO);
        return ResponseEntity
            .created(new URI("/api/administrative-fees-ranges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /administrative-fees-ranges/:id} : Updates an existing administrativeFeesRanges.
     *
     * @param id the id of the administrativeFeesRangesDTO to save.
     * @param administrativeFeesRangesDTO the administrativeFeesRangesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated administrativeFeesRangesDTO,
     * or with status {@code 400 (Bad Request)} if the administrativeFeesRangesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the administrativeFeesRangesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/administrative-fees-ranges/{id}")
    public ResponseEntity<AdministrativeFeesRangesDTO> updateAdministrativeFeesRanges(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdministrativeFeesRangesDTO administrativeFeesRangesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdministrativeFeesRanges : {}, {}", id, administrativeFeesRangesDTO);
        if (administrativeFeesRangesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, administrativeFeesRangesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!administrativeFeesRangesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdministrativeFeesRangesDTO result = administrativeFeesRangesService.save(administrativeFeesRangesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, administrativeFeesRangesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /administrative-fees-ranges/:id} : Partial updates given fields of an existing administrativeFeesRanges, field will ignore if it is null
     *
     * @param id the id of the administrativeFeesRangesDTO to save.
     * @param administrativeFeesRangesDTO the administrativeFeesRangesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated administrativeFeesRangesDTO,
     * or with status {@code 400 (Bad Request)} if the administrativeFeesRangesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the administrativeFeesRangesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the administrativeFeesRangesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/administrative-fees-ranges/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AdministrativeFeesRangesDTO> partialUpdateAdministrativeFeesRanges(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdministrativeFeesRangesDTO administrativeFeesRangesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdministrativeFeesRanges partially : {}, {}", id, administrativeFeesRangesDTO);
        if (administrativeFeesRangesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, administrativeFeesRangesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!administrativeFeesRangesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdministrativeFeesRangesDTO> result = administrativeFeesRangesService.partialUpdate(administrativeFeesRangesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, administrativeFeesRangesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /administrative-fees-ranges} : get all the administrativeFeesRanges.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of administrativeFeesRanges in body.
     */
    @GetMapping("/administrative-fees-ranges")
    public ResponseEntity<List<AdministrativeFeesRangesDTO>> getAllAdministrativeFeesRanges(
        AdministrativeFeesRangesCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get AdministrativeFeesRanges by criteria: {}", criteria);
        Page<AdministrativeFeesRangesDTO> page = administrativeFeesRangesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /administrative-fees-ranges/count} : count all the administrativeFeesRanges.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/administrative-fees-ranges/count")
    public ResponseEntity<Long> countAdministrativeFeesRanges(AdministrativeFeesRangesCriteria criteria) {
        log.debug("REST request to count AdministrativeFeesRanges by criteria: {}", criteria);
        return ResponseEntity.ok().body(administrativeFeesRangesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /administrative-fees-ranges/:id} : get the "id" administrativeFeesRanges.
     *
     * @param id the id of the administrativeFeesRangesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the administrativeFeesRangesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/administrative-fees-ranges/{id}")
    public ResponseEntity<AdministrativeFeesRangesDTO> getAdministrativeFeesRanges(@PathVariable Long id) {
        log.debug("REST request to get AdministrativeFeesRanges : {}", id);
        Optional<AdministrativeFeesRangesDTO> administrativeFeesRangesDTO = administrativeFeesRangesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(administrativeFeesRangesDTO);
    }

    /**
     * {@code DELETE  /administrative-fees-ranges/:id} : delete the "id" administrativeFeesRanges.
     *
     * @param id the id of the administrativeFeesRangesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/administrative-fees-ranges/{id}")
    public ResponseEntity<Void> deleteAdministrativeFeesRanges(@PathVariable Long id) {
        log.debug("REST request to delete AdministrativeFeesRanges : {}", id);
        administrativeFeesRangesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
