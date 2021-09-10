package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.HousingRepository;
import com.genesisoft.transporte.service.HousingQueryService;
import com.genesisoft.transporte.service.HousingService;
import com.genesisoft.transporte.service.criteria.HousingCriteria;
import com.genesisoft.transporte.service.dto.HousingDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.Housing}.
 */
@RestController
@RequestMapping("/api")
public class HousingResource {

    private final Logger log = LoggerFactory.getLogger(HousingResource.class);

    private static final String ENTITY_NAME = "housing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HousingService housingService;

    private final HousingRepository housingRepository;

    private final HousingQueryService housingQueryService;

    public HousingResource(HousingService housingService, HousingRepository housingRepository, HousingQueryService housingQueryService) {
        this.housingService = housingService;
        this.housingRepository = housingRepository;
        this.housingQueryService = housingQueryService;
    }

    /**
     * {@code POST  /housings} : Create a new housing.
     *
     * @param housingDTO the housingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new housingDTO, or with status {@code 400 (Bad Request)} if the housing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/housings")
    public ResponseEntity<HousingDTO> createHousing(@Valid @RequestBody HousingDTO housingDTO) throws URISyntaxException {
        log.debug("REST request to save Housing : {}", housingDTO);
        if (housingDTO.getId() != null) {
            throw new BadRequestAlertException("A new housing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HousingDTO result = housingService.save(housingDTO);
        return ResponseEntity
            .created(new URI("/api/housings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /housings/:id} : Updates an existing housing.
     *
     * @param id the id of the housingDTO to save.
     * @param housingDTO the housingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated housingDTO,
     * or with status {@code 400 (Bad Request)} if the housingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the housingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/housings/{id}")
    public ResponseEntity<HousingDTO> updateHousing(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HousingDTO housingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Housing : {}, {}", id, housingDTO);
        if (housingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, housingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!housingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HousingDTO result = housingService.save(housingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, housingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /housings/:id} : Partial updates given fields of an existing housing, field will ignore if it is null
     *
     * @param id the id of the housingDTO to save.
     * @param housingDTO the housingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated housingDTO,
     * or with status {@code 400 (Bad Request)} if the housingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the housingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the housingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/housings/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<HousingDTO> partialUpdateHousing(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HousingDTO housingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Housing partially : {}, {}", id, housingDTO);
        if (housingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, housingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!housingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HousingDTO> result = housingService.partialUpdate(housingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, housingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /housings} : get all the housings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of housings in body.
     */
    @GetMapping("/housings")
    public ResponseEntity<List<HousingDTO>> getAllHousings(HousingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Housings by criteria: {}", criteria);
        Page<HousingDTO> page = housingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /housings/count} : count all the housings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/housings/count")
    public ResponseEntity<Long> countHousings(HousingCriteria criteria) {
        log.debug("REST request to count Housings by criteria: {}", criteria);
        return ResponseEntity.ok().body(housingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /housings/:id} : get the "id" housing.
     *
     * @param id the id of the housingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the housingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/housings/{id}")
    public ResponseEntity<HousingDTO> getHousing(@PathVariable Long id) {
        log.debug("REST request to get Housing : {}", id);
        Optional<HousingDTO> housingDTO = housingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(housingDTO);
    }

    /**
     * {@code DELETE  /housings/:id} : delete the "id" housing.
     *
     * @param id the id of the housingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/housings/{id}")
    public ResponseEntity<Void> deleteHousing(@PathVariable Long id) {
        log.debug("REST request to delete Housing : {}", id);
        housingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
