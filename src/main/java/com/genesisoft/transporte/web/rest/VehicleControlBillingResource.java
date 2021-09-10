package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.VehicleControlBillingRepository;
import com.genesisoft.transporte.service.VehicleControlBillingQueryService;
import com.genesisoft.transporte.service.VehicleControlBillingService;
import com.genesisoft.transporte.service.criteria.VehicleControlBillingCriteria;
import com.genesisoft.transporte.service.dto.VehicleControlBillingDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.VehicleControlBilling}.
 */
@RestController
@RequestMapping("/api")
public class VehicleControlBillingResource {

    private final Logger log = LoggerFactory.getLogger(VehicleControlBillingResource.class);

    private static final String ENTITY_NAME = "vehicleControlBilling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleControlBillingService vehicleControlBillingService;

    private final VehicleControlBillingRepository vehicleControlBillingRepository;

    private final VehicleControlBillingQueryService vehicleControlBillingQueryService;

    public VehicleControlBillingResource(
        VehicleControlBillingService vehicleControlBillingService,
        VehicleControlBillingRepository vehicleControlBillingRepository,
        VehicleControlBillingQueryService vehicleControlBillingQueryService
    ) {
        this.vehicleControlBillingService = vehicleControlBillingService;
        this.vehicleControlBillingRepository = vehicleControlBillingRepository;
        this.vehicleControlBillingQueryService = vehicleControlBillingQueryService;
    }

    /**
     * {@code POST  /vehicle-control-billings} : Create a new vehicleControlBilling.
     *
     * @param vehicleControlBillingDTO the vehicleControlBillingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleControlBillingDTO, or with status {@code 400 (Bad Request)} if the vehicleControlBilling has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicle-control-billings")
    public ResponseEntity<VehicleControlBillingDTO> createVehicleControlBilling(
        @Valid @RequestBody VehicleControlBillingDTO vehicleControlBillingDTO
    ) throws URISyntaxException {
        log.debug("REST request to save VehicleControlBilling : {}", vehicleControlBillingDTO);
        if (vehicleControlBillingDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleControlBilling cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleControlBillingDTO result = vehicleControlBillingService.save(vehicleControlBillingDTO);
        return ResponseEntity
            .created(new URI("/api/vehicle-control-billings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicle-control-billings/:id} : Updates an existing vehicleControlBilling.
     *
     * @param id the id of the vehicleControlBillingDTO to save.
     * @param vehicleControlBillingDTO the vehicleControlBillingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleControlBillingDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleControlBillingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleControlBillingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicle-control-billings/{id}")
    public ResponseEntity<VehicleControlBillingDTO> updateVehicleControlBilling(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VehicleControlBillingDTO vehicleControlBillingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VehicleControlBilling : {}, {}", id, vehicleControlBillingDTO);
        if (vehicleControlBillingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleControlBillingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleControlBillingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VehicleControlBillingDTO result = vehicleControlBillingService.save(vehicleControlBillingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleControlBillingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vehicle-control-billings/:id} : Partial updates given fields of an existing vehicleControlBilling, field will ignore if it is null
     *
     * @param id the id of the vehicleControlBillingDTO to save.
     * @param vehicleControlBillingDTO the vehicleControlBillingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleControlBillingDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleControlBillingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vehicleControlBillingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vehicleControlBillingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vehicle-control-billings/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VehicleControlBillingDTO> partialUpdateVehicleControlBilling(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VehicleControlBillingDTO vehicleControlBillingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VehicleControlBilling partially : {}, {}", id, vehicleControlBillingDTO);
        if (vehicleControlBillingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleControlBillingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleControlBillingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VehicleControlBillingDTO> result = vehicleControlBillingService.partialUpdate(vehicleControlBillingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleControlBillingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vehicle-control-billings} : get all the vehicleControlBillings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicleControlBillings in body.
     */
    @GetMapping("/vehicle-control-billings")
    public ResponseEntity<List<VehicleControlBillingDTO>> getAllVehicleControlBillings(
        VehicleControlBillingCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get VehicleControlBillings by criteria: {}", criteria);
        Page<VehicleControlBillingDTO> page = vehicleControlBillingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vehicle-control-billings/count} : count all the vehicleControlBillings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vehicle-control-billings/count")
    public ResponseEntity<Long> countVehicleControlBillings(VehicleControlBillingCriteria criteria) {
        log.debug("REST request to count VehicleControlBillings by criteria: {}", criteria);
        return ResponseEntity.ok().body(vehicleControlBillingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vehicle-control-billings/:id} : get the "id" vehicleControlBilling.
     *
     * @param id the id of the vehicleControlBillingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleControlBillingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicle-control-billings/{id}")
    public ResponseEntity<VehicleControlBillingDTO> getVehicleControlBilling(@PathVariable Long id) {
        log.debug("REST request to get VehicleControlBilling : {}", id);
        Optional<VehicleControlBillingDTO> vehicleControlBillingDTO = vehicleControlBillingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleControlBillingDTO);
    }

    /**
     * {@code DELETE  /vehicle-control-billings/:id} : delete the "id" vehicleControlBilling.
     *
     * @param id the id of the vehicleControlBillingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicle-control-billings/{id}")
    public ResponseEntity<Void> deleteVehicleControlBilling(@PathVariable Long id) {
        log.debug("REST request to delete VehicleControlBilling : {}", id);
        vehicleControlBillingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
