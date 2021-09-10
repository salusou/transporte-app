package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.VehicleLocationStatusRepository;
import com.genesisoft.transporte.service.VehicleLocationStatusQueryService;
import com.genesisoft.transporte.service.VehicleLocationStatusService;
import com.genesisoft.transporte.service.criteria.VehicleLocationStatusCriteria;
import com.genesisoft.transporte.service.dto.VehicleLocationStatusDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.VehicleLocationStatus}.
 */
@RestController
@RequestMapping("/api")
public class VehicleLocationStatusResource {

    private final Logger log = LoggerFactory.getLogger(VehicleLocationStatusResource.class);

    private static final String ENTITY_NAME = "vehicleLocationStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleLocationStatusService vehicleLocationStatusService;

    private final VehicleLocationStatusRepository vehicleLocationStatusRepository;

    private final VehicleLocationStatusQueryService vehicleLocationStatusQueryService;

    public VehicleLocationStatusResource(
        VehicleLocationStatusService vehicleLocationStatusService,
        VehicleLocationStatusRepository vehicleLocationStatusRepository,
        VehicleLocationStatusQueryService vehicleLocationStatusQueryService
    ) {
        this.vehicleLocationStatusService = vehicleLocationStatusService;
        this.vehicleLocationStatusRepository = vehicleLocationStatusRepository;
        this.vehicleLocationStatusQueryService = vehicleLocationStatusQueryService;
    }

    /**
     * {@code POST  /vehicle-location-statuses} : Create a new vehicleLocationStatus.
     *
     * @param vehicleLocationStatusDTO the vehicleLocationStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleLocationStatusDTO, or with status {@code 400 (Bad Request)} if the vehicleLocationStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicle-location-statuses")
    public ResponseEntity<VehicleLocationStatusDTO> createVehicleLocationStatus(
        @Valid @RequestBody VehicleLocationStatusDTO vehicleLocationStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to save VehicleLocationStatus : {}", vehicleLocationStatusDTO);
        if (vehicleLocationStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleLocationStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleLocationStatusDTO result = vehicleLocationStatusService.save(vehicleLocationStatusDTO);
        return ResponseEntity
            .created(new URI("/api/vehicle-location-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicle-location-statuses/:id} : Updates an existing vehicleLocationStatus.
     *
     * @param id the id of the vehicleLocationStatusDTO to save.
     * @param vehicleLocationStatusDTO the vehicleLocationStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleLocationStatusDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleLocationStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleLocationStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicle-location-statuses/{id}")
    public ResponseEntity<VehicleLocationStatusDTO> updateVehicleLocationStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VehicleLocationStatusDTO vehicleLocationStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VehicleLocationStatus : {}, {}", id, vehicleLocationStatusDTO);
        if (vehicleLocationStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleLocationStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleLocationStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VehicleLocationStatusDTO result = vehicleLocationStatusService.save(vehicleLocationStatusDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleLocationStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vehicle-location-statuses/:id} : Partial updates given fields of an existing vehicleLocationStatus, field will ignore if it is null
     *
     * @param id the id of the vehicleLocationStatusDTO to save.
     * @param vehicleLocationStatusDTO the vehicleLocationStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleLocationStatusDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleLocationStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vehicleLocationStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vehicleLocationStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vehicle-location-statuses/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VehicleLocationStatusDTO> partialUpdateVehicleLocationStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VehicleLocationStatusDTO vehicleLocationStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VehicleLocationStatus partially : {}, {}", id, vehicleLocationStatusDTO);
        if (vehicleLocationStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleLocationStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleLocationStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VehicleLocationStatusDTO> result = vehicleLocationStatusService.partialUpdate(vehicleLocationStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleLocationStatusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vehicle-location-statuses} : get all the vehicleLocationStatuses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicleLocationStatuses in body.
     */
    @GetMapping("/vehicle-location-statuses")
    public ResponseEntity<List<VehicleLocationStatusDTO>> getAllVehicleLocationStatuses(
        VehicleLocationStatusCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get VehicleLocationStatuses by criteria: {}", criteria);
        Page<VehicleLocationStatusDTO> page = vehicleLocationStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vehicle-location-statuses/count} : count all the vehicleLocationStatuses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vehicle-location-statuses/count")
    public ResponseEntity<Long> countVehicleLocationStatuses(VehicleLocationStatusCriteria criteria) {
        log.debug("REST request to count VehicleLocationStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(vehicleLocationStatusQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vehicle-location-statuses/:id} : get the "id" vehicleLocationStatus.
     *
     * @param id the id of the vehicleLocationStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleLocationStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicle-location-statuses/{id}")
    public ResponseEntity<VehicleLocationStatusDTO> getVehicleLocationStatus(@PathVariable Long id) {
        log.debug("REST request to get VehicleLocationStatus : {}", id);
        Optional<VehicleLocationStatusDTO> vehicleLocationStatusDTO = vehicleLocationStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleLocationStatusDTO);
    }

    /**
     * {@code DELETE  /vehicle-location-statuses/:id} : delete the "id" vehicleLocationStatus.
     *
     * @param id the id of the vehicleLocationStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicle-location-statuses/{id}")
    public ResponseEntity<Void> deleteVehicleLocationStatus(@PathVariable Long id) {
        log.debug("REST request to delete VehicleLocationStatus : {}", id);
        vehicleLocationStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
