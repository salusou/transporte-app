package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.VehicleInspectionsRepository;
import com.genesisoft.transporte.service.VehicleInspectionsQueryService;
import com.genesisoft.transporte.service.VehicleInspectionsService;
import com.genesisoft.transporte.service.criteria.VehicleInspectionsCriteria;
import com.genesisoft.transporte.service.dto.VehicleInspectionsDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.VehicleInspections}.
 */
@RestController
@RequestMapping("/api")
public class VehicleInspectionsResource {

    private final Logger log = LoggerFactory.getLogger(VehicleInspectionsResource.class);

    private static final String ENTITY_NAME = "vehicleInspections";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleInspectionsService vehicleInspectionsService;

    private final VehicleInspectionsRepository vehicleInspectionsRepository;

    private final VehicleInspectionsQueryService vehicleInspectionsQueryService;

    public VehicleInspectionsResource(
        VehicleInspectionsService vehicleInspectionsService,
        VehicleInspectionsRepository vehicleInspectionsRepository,
        VehicleInspectionsQueryService vehicleInspectionsQueryService
    ) {
        this.vehicleInspectionsService = vehicleInspectionsService;
        this.vehicleInspectionsRepository = vehicleInspectionsRepository;
        this.vehicleInspectionsQueryService = vehicleInspectionsQueryService;
    }

    /**
     * {@code POST  /vehicle-inspections} : Create a new vehicleInspections.
     *
     * @param vehicleInspectionsDTO the vehicleInspectionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleInspectionsDTO, or with status {@code 400 (Bad Request)} if the vehicleInspections has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicle-inspections")
    public ResponseEntity<VehicleInspectionsDTO> createVehicleInspections(@Valid @RequestBody VehicleInspectionsDTO vehicleInspectionsDTO)
        throws URISyntaxException {
        log.debug("REST request to save VehicleInspections : {}", vehicleInspectionsDTO);
        if (vehicleInspectionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleInspections cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleInspectionsDTO result = vehicleInspectionsService.save(vehicleInspectionsDTO);
        return ResponseEntity
            .created(new URI("/api/vehicle-inspections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicle-inspections/:id} : Updates an existing vehicleInspections.
     *
     * @param id the id of the vehicleInspectionsDTO to save.
     * @param vehicleInspectionsDTO the vehicleInspectionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleInspectionsDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleInspectionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleInspectionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicle-inspections/{id}")
    public ResponseEntity<VehicleInspectionsDTO> updateVehicleInspections(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VehicleInspectionsDTO vehicleInspectionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VehicleInspections : {}, {}", id, vehicleInspectionsDTO);
        if (vehicleInspectionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleInspectionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleInspectionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VehicleInspectionsDTO result = vehicleInspectionsService.save(vehicleInspectionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleInspectionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vehicle-inspections/:id} : Partial updates given fields of an existing vehicleInspections, field will ignore if it is null
     *
     * @param id the id of the vehicleInspectionsDTO to save.
     * @param vehicleInspectionsDTO the vehicleInspectionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleInspectionsDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleInspectionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vehicleInspectionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vehicleInspectionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vehicle-inspections/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VehicleInspectionsDTO> partialUpdateVehicleInspections(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VehicleInspectionsDTO vehicleInspectionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VehicleInspections partially : {}, {}", id, vehicleInspectionsDTO);
        if (vehicleInspectionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleInspectionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleInspectionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VehicleInspectionsDTO> result = vehicleInspectionsService.partialUpdate(vehicleInspectionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleInspectionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vehicle-inspections} : get all the vehicleInspections.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicleInspections in body.
     */
    @GetMapping("/vehicle-inspections")
    public ResponseEntity<List<VehicleInspectionsDTO>> getAllVehicleInspections(VehicleInspectionsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get VehicleInspections by criteria: {}", criteria);
        Page<VehicleInspectionsDTO> page = vehicleInspectionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vehicle-inspections/count} : count all the vehicleInspections.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vehicle-inspections/count")
    public ResponseEntity<Long> countVehicleInspections(VehicleInspectionsCriteria criteria) {
        log.debug("REST request to count VehicleInspections by criteria: {}", criteria);
        return ResponseEntity.ok().body(vehicleInspectionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vehicle-inspections/:id} : get the "id" vehicleInspections.
     *
     * @param id the id of the vehicleInspectionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleInspectionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicle-inspections/{id}")
    public ResponseEntity<VehicleInspectionsDTO> getVehicleInspections(@PathVariable Long id) {
        log.debug("REST request to get VehicleInspections : {}", id);
        Optional<VehicleInspectionsDTO> vehicleInspectionsDTO = vehicleInspectionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleInspectionsDTO);
    }

    /**
     * {@code DELETE  /vehicle-inspections/:id} : delete the "id" vehicleInspections.
     *
     * @param id the id of the vehicleInspectionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicle-inspections/{id}")
    public ResponseEntity<Void> deleteVehicleInspections(@PathVariable Long id) {
        log.debug("REST request to delete VehicleInspections : {}", id);
        vehicleInspectionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
