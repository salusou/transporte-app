package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.VehicleControlsRepository;
import com.genesisoft.transporte.service.VehicleControlsQueryService;
import com.genesisoft.transporte.service.VehicleControlsService;
import com.genesisoft.transporte.service.criteria.VehicleControlsCriteria;
import com.genesisoft.transporte.service.dto.VehicleControlsDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.VehicleControls}.
 */
@RestController
@RequestMapping("/api")
public class VehicleControlsResource {

    private final Logger log = LoggerFactory.getLogger(VehicleControlsResource.class);

    private static final String ENTITY_NAME = "vehicleControls";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleControlsService vehicleControlsService;

    private final VehicleControlsRepository vehicleControlsRepository;

    private final VehicleControlsQueryService vehicleControlsQueryService;

    public VehicleControlsResource(
        VehicleControlsService vehicleControlsService,
        VehicleControlsRepository vehicleControlsRepository,
        VehicleControlsQueryService vehicleControlsQueryService
    ) {
        this.vehicleControlsService = vehicleControlsService;
        this.vehicleControlsRepository = vehicleControlsRepository;
        this.vehicleControlsQueryService = vehicleControlsQueryService;
    }

    /**
     * {@code POST  /vehicle-controls} : Create a new vehicleControls.
     *
     * @param vehicleControlsDTO the vehicleControlsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleControlsDTO, or with status {@code 400 (Bad Request)} if the vehicleControls has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicle-controls")
    public ResponseEntity<VehicleControlsDTO> createVehicleControls(@Valid @RequestBody VehicleControlsDTO vehicleControlsDTO)
        throws URISyntaxException {
        log.debug("REST request to save VehicleControls : {}", vehicleControlsDTO);
        if (vehicleControlsDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleControls cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleControlsDTO result = vehicleControlsService.save(vehicleControlsDTO);
        return ResponseEntity
            .created(new URI("/api/vehicle-controls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicle-controls/:id} : Updates an existing vehicleControls.
     *
     * @param id the id of the vehicleControlsDTO to save.
     * @param vehicleControlsDTO the vehicleControlsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleControlsDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleControlsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleControlsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicle-controls/{id}")
    public ResponseEntity<VehicleControlsDTO> updateVehicleControls(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VehicleControlsDTO vehicleControlsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VehicleControls : {}, {}", id, vehicleControlsDTO);
        if (vehicleControlsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleControlsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleControlsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VehicleControlsDTO result = vehicleControlsService.save(vehicleControlsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleControlsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vehicle-controls/:id} : Partial updates given fields of an existing vehicleControls, field will ignore if it is null
     *
     * @param id the id of the vehicleControlsDTO to save.
     * @param vehicleControlsDTO the vehicleControlsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleControlsDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleControlsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vehicleControlsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vehicleControlsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vehicle-controls/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VehicleControlsDTO> partialUpdateVehicleControls(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VehicleControlsDTO vehicleControlsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VehicleControls partially : {}, {}", id, vehicleControlsDTO);
        if (vehicleControlsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleControlsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleControlsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VehicleControlsDTO> result = vehicleControlsService.partialUpdate(vehicleControlsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleControlsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vehicle-controls} : get all the vehicleControls.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicleControls in body.
     */
    @GetMapping("/vehicle-controls")
    public ResponseEntity<List<VehicleControlsDTO>> getAllVehicleControls(VehicleControlsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get VehicleControls by criteria: {}", criteria);
        Page<VehicleControlsDTO> page = vehicleControlsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vehicle-controls/count} : count all the vehicleControls.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vehicle-controls/count")
    public ResponseEntity<Long> countVehicleControls(VehicleControlsCriteria criteria) {
        log.debug("REST request to count VehicleControls by criteria: {}", criteria);
        return ResponseEntity.ok().body(vehicleControlsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vehicle-controls/:id} : get the "id" vehicleControls.
     *
     * @param id the id of the vehicleControlsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleControlsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicle-controls/{id}")
    public ResponseEntity<VehicleControlsDTO> getVehicleControls(@PathVariable Long id) {
        log.debug("REST request to get VehicleControls : {}", id);
        Optional<VehicleControlsDTO> vehicleControlsDTO = vehicleControlsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleControlsDTO);
    }

    /**
     * {@code DELETE  /vehicle-controls/:id} : delete the "id" vehicleControls.
     *
     * @param id the id of the vehicleControlsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicle-controls/{id}")
    public ResponseEntity<Void> deleteVehicleControls(@PathVariable Long id) {
        log.debug("REST request to delete VehicleControls : {}", id);
        vehicleControlsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
