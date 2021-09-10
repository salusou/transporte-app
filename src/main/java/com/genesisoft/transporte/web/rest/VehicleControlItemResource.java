package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.VehicleControlItemRepository;
import com.genesisoft.transporte.service.VehicleControlItemQueryService;
import com.genesisoft.transporte.service.VehicleControlItemService;
import com.genesisoft.transporte.service.criteria.VehicleControlItemCriteria;
import com.genesisoft.transporte.service.dto.VehicleControlItemDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.VehicleControlItem}.
 */
@RestController
@RequestMapping("/api")
public class VehicleControlItemResource {

    private final Logger log = LoggerFactory.getLogger(VehicleControlItemResource.class);

    private static final String ENTITY_NAME = "vehicleControlItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleControlItemService vehicleControlItemService;

    private final VehicleControlItemRepository vehicleControlItemRepository;

    private final VehicleControlItemQueryService vehicleControlItemQueryService;

    public VehicleControlItemResource(
        VehicleControlItemService vehicleControlItemService,
        VehicleControlItemRepository vehicleControlItemRepository,
        VehicleControlItemQueryService vehicleControlItemQueryService
    ) {
        this.vehicleControlItemService = vehicleControlItemService;
        this.vehicleControlItemRepository = vehicleControlItemRepository;
        this.vehicleControlItemQueryService = vehicleControlItemQueryService;
    }

    /**
     * {@code POST  /vehicle-control-items} : Create a new vehicleControlItem.
     *
     * @param vehicleControlItemDTO the vehicleControlItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleControlItemDTO, or with status {@code 400 (Bad Request)} if the vehicleControlItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicle-control-items")
    public ResponseEntity<VehicleControlItemDTO> createVehicleControlItem(@Valid @RequestBody VehicleControlItemDTO vehicleControlItemDTO)
        throws URISyntaxException {
        log.debug("REST request to save VehicleControlItem : {}", vehicleControlItemDTO);
        if (vehicleControlItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleControlItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleControlItemDTO result = vehicleControlItemService.save(vehicleControlItemDTO);
        return ResponseEntity
            .created(new URI("/api/vehicle-control-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicle-control-items/:id} : Updates an existing vehicleControlItem.
     *
     * @param id the id of the vehicleControlItemDTO to save.
     * @param vehicleControlItemDTO the vehicleControlItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleControlItemDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleControlItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleControlItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicle-control-items/{id}")
    public ResponseEntity<VehicleControlItemDTO> updateVehicleControlItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VehicleControlItemDTO vehicleControlItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VehicleControlItem : {}, {}", id, vehicleControlItemDTO);
        if (vehicleControlItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleControlItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleControlItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VehicleControlItemDTO result = vehicleControlItemService.save(vehicleControlItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleControlItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vehicle-control-items/:id} : Partial updates given fields of an existing vehicleControlItem, field will ignore if it is null
     *
     * @param id the id of the vehicleControlItemDTO to save.
     * @param vehicleControlItemDTO the vehicleControlItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleControlItemDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleControlItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vehicleControlItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vehicleControlItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vehicle-control-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VehicleControlItemDTO> partialUpdateVehicleControlItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VehicleControlItemDTO vehicleControlItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VehicleControlItem partially : {}, {}", id, vehicleControlItemDTO);
        if (vehicleControlItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleControlItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleControlItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VehicleControlItemDTO> result = vehicleControlItemService.partialUpdate(vehicleControlItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleControlItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vehicle-control-items} : get all the vehicleControlItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicleControlItems in body.
     */
    @GetMapping("/vehicle-control-items")
    public ResponseEntity<List<VehicleControlItemDTO>> getAllVehicleControlItems(VehicleControlItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get VehicleControlItems by criteria: {}", criteria);
        Page<VehicleControlItemDTO> page = vehicleControlItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vehicle-control-items/count} : count all the vehicleControlItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vehicle-control-items/count")
    public ResponseEntity<Long> countVehicleControlItems(VehicleControlItemCriteria criteria) {
        log.debug("REST request to count VehicleControlItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(vehicleControlItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vehicle-control-items/:id} : get the "id" vehicleControlItem.
     *
     * @param id the id of the vehicleControlItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleControlItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicle-control-items/{id}")
    public ResponseEntity<VehicleControlItemDTO> getVehicleControlItem(@PathVariable Long id) {
        log.debug("REST request to get VehicleControlItem : {}", id);
        Optional<VehicleControlItemDTO> vehicleControlItemDTO = vehicleControlItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleControlItemDTO);
    }

    /**
     * {@code DELETE  /vehicle-control-items/:id} : delete the "id" vehicleControlItem.
     *
     * @param id the id of the vehicleControlItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicle-control-items/{id}")
    public ResponseEntity<Void> deleteVehicleControlItem(@PathVariable Long id) {
        log.debug("REST request to delete VehicleControlItem : {}", id);
        vehicleControlItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
