package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.VehicleControlHistoryRepository;
import com.genesisoft.transporte.service.VehicleControlHistoryQueryService;
import com.genesisoft.transporte.service.VehicleControlHistoryService;
import com.genesisoft.transporte.service.criteria.VehicleControlHistoryCriteria;
import com.genesisoft.transporte.service.dto.VehicleControlHistoryDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.VehicleControlHistory}.
 */
@RestController
@RequestMapping("/api")
public class VehicleControlHistoryResource {

    private final Logger log = LoggerFactory.getLogger(VehicleControlHistoryResource.class);

    private static final String ENTITY_NAME = "vehicleControlHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleControlHistoryService vehicleControlHistoryService;

    private final VehicleControlHistoryRepository vehicleControlHistoryRepository;

    private final VehicleControlHistoryQueryService vehicleControlHistoryQueryService;

    public VehicleControlHistoryResource(
        VehicleControlHistoryService vehicleControlHistoryService,
        VehicleControlHistoryRepository vehicleControlHistoryRepository,
        VehicleControlHistoryQueryService vehicleControlHistoryQueryService
    ) {
        this.vehicleControlHistoryService = vehicleControlHistoryService;
        this.vehicleControlHistoryRepository = vehicleControlHistoryRepository;
        this.vehicleControlHistoryQueryService = vehicleControlHistoryQueryService;
    }

    /**
     * {@code POST  /vehicle-control-histories} : Create a new vehicleControlHistory.
     *
     * @param vehicleControlHistoryDTO the vehicleControlHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleControlHistoryDTO, or with status {@code 400 (Bad Request)} if the vehicleControlHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicle-control-histories")
    public ResponseEntity<VehicleControlHistoryDTO> createVehicleControlHistory(
        @Valid @RequestBody VehicleControlHistoryDTO vehicleControlHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to save VehicleControlHistory : {}", vehicleControlHistoryDTO);
        if (vehicleControlHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleControlHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleControlHistoryDTO result = vehicleControlHistoryService.save(vehicleControlHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/vehicle-control-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicle-control-histories/:id} : Updates an existing vehicleControlHistory.
     *
     * @param id the id of the vehicleControlHistoryDTO to save.
     * @param vehicleControlHistoryDTO the vehicleControlHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleControlHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleControlHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleControlHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicle-control-histories/{id}")
    public ResponseEntity<VehicleControlHistoryDTO> updateVehicleControlHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VehicleControlHistoryDTO vehicleControlHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VehicleControlHistory : {}, {}", id, vehicleControlHistoryDTO);
        if (vehicleControlHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleControlHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleControlHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VehicleControlHistoryDTO result = vehicleControlHistoryService.save(vehicleControlHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleControlHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vehicle-control-histories/:id} : Partial updates given fields of an existing vehicleControlHistory, field will ignore if it is null
     *
     * @param id the id of the vehicleControlHistoryDTO to save.
     * @param vehicleControlHistoryDTO the vehicleControlHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleControlHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleControlHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vehicleControlHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vehicleControlHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vehicle-control-histories/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VehicleControlHistoryDTO> partialUpdateVehicleControlHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VehicleControlHistoryDTO vehicleControlHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VehicleControlHistory partially : {}, {}", id, vehicleControlHistoryDTO);
        if (vehicleControlHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleControlHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleControlHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VehicleControlHistoryDTO> result = vehicleControlHistoryService.partialUpdate(vehicleControlHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleControlHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vehicle-control-histories} : get all the vehicleControlHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicleControlHistories in body.
     */
    @GetMapping("/vehicle-control-histories")
    public ResponseEntity<List<VehicleControlHistoryDTO>> getAllVehicleControlHistories(
        VehicleControlHistoryCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get VehicleControlHistories by criteria: {}", criteria);
        Page<VehicleControlHistoryDTO> page = vehicleControlHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vehicle-control-histories/count} : count all the vehicleControlHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vehicle-control-histories/count")
    public ResponseEntity<Long> countVehicleControlHistories(VehicleControlHistoryCriteria criteria) {
        log.debug("REST request to count VehicleControlHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(vehicleControlHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vehicle-control-histories/:id} : get the "id" vehicleControlHistory.
     *
     * @param id the id of the vehicleControlHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleControlHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicle-control-histories/{id}")
    public ResponseEntity<VehicleControlHistoryDTO> getVehicleControlHistory(@PathVariable Long id) {
        log.debug("REST request to get VehicleControlHistory : {}", id);
        Optional<VehicleControlHistoryDTO> vehicleControlHistoryDTO = vehicleControlHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleControlHistoryDTO);
    }

    /**
     * {@code DELETE  /vehicle-control-histories/:id} : delete the "id" vehicleControlHistory.
     *
     * @param id the id of the vehicleControlHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicle-control-histories/{id}")
    public ResponseEntity<Void> deleteVehicleControlHistory(@PathVariable Long id) {
        log.debug("REST request to delete VehicleControlHistory : {}", id);
        vehicleControlHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
