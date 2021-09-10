package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.CostCenterRepository;
import com.genesisoft.transporte.service.CostCenterQueryService;
import com.genesisoft.transporte.service.CostCenterService;
import com.genesisoft.transporte.service.criteria.CostCenterCriteria;
import com.genesisoft.transporte.service.dto.CostCenterDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.CostCenter}.
 */
@RestController
@RequestMapping("/api")
public class CostCenterResource {

    private final Logger log = LoggerFactory.getLogger(CostCenterResource.class);

    private static final String ENTITY_NAME = "costCenter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CostCenterService costCenterService;

    private final CostCenterRepository costCenterRepository;

    private final CostCenterQueryService costCenterQueryService;

    public CostCenterResource(
        CostCenterService costCenterService,
        CostCenterRepository costCenterRepository,
        CostCenterQueryService costCenterQueryService
    ) {
        this.costCenterService = costCenterService;
        this.costCenterRepository = costCenterRepository;
        this.costCenterQueryService = costCenterQueryService;
    }

    /**
     * {@code POST  /cost-centers} : Create a new costCenter.
     *
     * @param costCenterDTO the costCenterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new costCenterDTO, or with status {@code 400 (Bad Request)} if the costCenter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cost-centers")
    public ResponseEntity<CostCenterDTO> createCostCenter(@Valid @RequestBody CostCenterDTO costCenterDTO) throws URISyntaxException {
        log.debug("REST request to save CostCenter : {}", costCenterDTO);
        if (costCenterDTO.getId() != null) {
            throw new BadRequestAlertException("A new costCenter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CostCenterDTO result = costCenterService.save(costCenterDTO);
        return ResponseEntity
            .created(new URI("/api/cost-centers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cost-centers/:id} : Updates an existing costCenter.
     *
     * @param id the id of the costCenterDTO to save.
     * @param costCenterDTO the costCenterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated costCenterDTO,
     * or with status {@code 400 (Bad Request)} if the costCenterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the costCenterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cost-centers/{id}")
    public ResponseEntity<CostCenterDTO> updateCostCenter(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CostCenterDTO costCenterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CostCenter : {}, {}", id, costCenterDTO);
        if (costCenterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, costCenterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!costCenterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CostCenterDTO result = costCenterService.save(costCenterDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, costCenterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cost-centers/:id} : Partial updates given fields of an existing costCenter, field will ignore if it is null
     *
     * @param id the id of the costCenterDTO to save.
     * @param costCenterDTO the costCenterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated costCenterDTO,
     * or with status {@code 400 (Bad Request)} if the costCenterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the costCenterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the costCenterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cost-centers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CostCenterDTO> partialUpdateCostCenter(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CostCenterDTO costCenterDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CostCenter partially : {}, {}", id, costCenterDTO);
        if (costCenterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, costCenterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!costCenterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CostCenterDTO> result = costCenterService.partialUpdate(costCenterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, costCenterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cost-centers} : get all the costCenters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of costCenters in body.
     */
    @GetMapping("/cost-centers")
    public ResponseEntity<List<CostCenterDTO>> getAllCostCenters(CostCenterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CostCenters by criteria: {}", criteria);
        Page<CostCenterDTO> page = costCenterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cost-centers/count} : count all the costCenters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cost-centers/count")
    public ResponseEntity<Long> countCostCenters(CostCenterCriteria criteria) {
        log.debug("REST request to count CostCenters by criteria: {}", criteria);
        return ResponseEntity.ok().body(costCenterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cost-centers/:id} : get the "id" costCenter.
     *
     * @param id the id of the costCenterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the costCenterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cost-centers/{id}")
    public ResponseEntity<CostCenterDTO> getCostCenter(@PathVariable Long id) {
        log.debug("REST request to get CostCenter : {}", id);
        Optional<CostCenterDTO> costCenterDTO = costCenterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(costCenterDTO);
    }

    /**
     * {@code DELETE  /cost-centers/:id} : delete the "id" costCenter.
     *
     * @param id the id of the costCenterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cost-centers/{id}")
    public ResponseEntity<Void> deleteCostCenter(@PathVariable Long id) {
        log.debug("REST request to delete CostCenter : {}", id);
        costCenterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
