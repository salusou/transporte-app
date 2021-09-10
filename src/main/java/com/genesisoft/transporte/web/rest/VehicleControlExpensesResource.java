package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.VehicleControlExpensesRepository;
import com.genesisoft.transporte.service.VehicleControlExpensesQueryService;
import com.genesisoft.transporte.service.VehicleControlExpensesService;
import com.genesisoft.transporte.service.criteria.VehicleControlExpensesCriteria;
import com.genesisoft.transporte.service.dto.VehicleControlExpensesDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.VehicleControlExpenses}.
 */
@RestController
@RequestMapping("/api")
public class VehicleControlExpensesResource {

    private final Logger log = LoggerFactory.getLogger(VehicleControlExpensesResource.class);

    private static final String ENTITY_NAME = "vehicleControlExpenses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleControlExpensesService vehicleControlExpensesService;

    private final VehicleControlExpensesRepository vehicleControlExpensesRepository;

    private final VehicleControlExpensesQueryService vehicleControlExpensesQueryService;

    public VehicleControlExpensesResource(
        VehicleControlExpensesService vehicleControlExpensesService,
        VehicleControlExpensesRepository vehicleControlExpensesRepository,
        VehicleControlExpensesQueryService vehicleControlExpensesQueryService
    ) {
        this.vehicleControlExpensesService = vehicleControlExpensesService;
        this.vehicleControlExpensesRepository = vehicleControlExpensesRepository;
        this.vehicleControlExpensesQueryService = vehicleControlExpensesQueryService;
    }

    /**
     * {@code POST  /vehicle-control-expenses} : Create a new vehicleControlExpenses.
     *
     * @param vehicleControlExpensesDTO the vehicleControlExpensesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleControlExpensesDTO, or with status {@code 400 (Bad Request)} if the vehicleControlExpenses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicle-control-expenses")
    public ResponseEntity<VehicleControlExpensesDTO> createVehicleControlExpenses(
        @Valid @RequestBody VehicleControlExpensesDTO vehicleControlExpensesDTO
    ) throws URISyntaxException {
        log.debug("REST request to save VehicleControlExpenses : {}", vehicleControlExpensesDTO);
        if (vehicleControlExpensesDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleControlExpenses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleControlExpensesDTO result = vehicleControlExpensesService.save(vehicleControlExpensesDTO);
        return ResponseEntity
            .created(new URI("/api/vehicle-control-expenses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicle-control-expenses/:id} : Updates an existing vehicleControlExpenses.
     *
     * @param id the id of the vehicleControlExpensesDTO to save.
     * @param vehicleControlExpensesDTO the vehicleControlExpensesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleControlExpensesDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleControlExpensesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleControlExpensesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicle-control-expenses/{id}")
    public ResponseEntity<VehicleControlExpensesDTO> updateVehicleControlExpenses(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VehicleControlExpensesDTO vehicleControlExpensesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VehicleControlExpenses : {}, {}", id, vehicleControlExpensesDTO);
        if (vehicleControlExpensesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleControlExpensesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleControlExpensesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VehicleControlExpensesDTO result = vehicleControlExpensesService.save(vehicleControlExpensesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleControlExpensesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vehicle-control-expenses/:id} : Partial updates given fields of an existing vehicleControlExpenses, field will ignore if it is null
     *
     * @param id the id of the vehicleControlExpensesDTO to save.
     * @param vehicleControlExpensesDTO the vehicleControlExpensesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleControlExpensesDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleControlExpensesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vehicleControlExpensesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vehicleControlExpensesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vehicle-control-expenses/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VehicleControlExpensesDTO> partialUpdateVehicleControlExpenses(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VehicleControlExpensesDTO vehicleControlExpensesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VehicleControlExpenses partially : {}, {}", id, vehicleControlExpensesDTO);
        if (vehicleControlExpensesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleControlExpensesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleControlExpensesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VehicleControlExpensesDTO> result = vehicleControlExpensesService.partialUpdate(vehicleControlExpensesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleControlExpensesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vehicle-control-expenses} : get all the vehicleControlExpenses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicleControlExpenses in body.
     */
    @GetMapping("/vehicle-control-expenses")
    public ResponseEntity<List<VehicleControlExpensesDTO>> getAllVehicleControlExpenses(
        VehicleControlExpensesCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get VehicleControlExpenses by criteria: {}", criteria);
        Page<VehicleControlExpensesDTO> page = vehicleControlExpensesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vehicle-control-expenses/count} : count all the vehicleControlExpenses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vehicle-control-expenses/count")
    public ResponseEntity<Long> countVehicleControlExpenses(VehicleControlExpensesCriteria criteria) {
        log.debug("REST request to count VehicleControlExpenses by criteria: {}", criteria);
        return ResponseEntity.ok().body(vehicleControlExpensesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vehicle-control-expenses/:id} : get the "id" vehicleControlExpenses.
     *
     * @param id the id of the vehicleControlExpensesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleControlExpensesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicle-control-expenses/{id}")
    public ResponseEntity<VehicleControlExpensesDTO> getVehicleControlExpenses(@PathVariable Long id) {
        log.debug("REST request to get VehicleControlExpenses : {}", id);
        Optional<VehicleControlExpensesDTO> vehicleControlExpensesDTO = vehicleControlExpensesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleControlExpensesDTO);
    }

    /**
     * {@code DELETE  /vehicle-control-expenses/:id} : delete the "id" vehicleControlExpenses.
     *
     * @param id the id of the vehicleControlExpensesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicle-control-expenses/{id}")
    public ResponseEntity<Void> deleteVehicleControlExpenses(@PathVariable Long id) {
        log.debug("REST request to delete VehicleControlExpenses : {}", id);
        vehicleControlExpensesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
