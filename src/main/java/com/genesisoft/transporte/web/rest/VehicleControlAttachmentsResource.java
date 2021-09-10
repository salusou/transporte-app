package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.VehicleControlAttachmentsRepository;
import com.genesisoft.transporte.service.VehicleControlAttachmentsQueryService;
import com.genesisoft.transporte.service.VehicleControlAttachmentsService;
import com.genesisoft.transporte.service.criteria.VehicleControlAttachmentsCriteria;
import com.genesisoft.transporte.service.dto.VehicleControlAttachmentsDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.VehicleControlAttachments}.
 */
@RestController
@RequestMapping("/api")
public class VehicleControlAttachmentsResource {

    private final Logger log = LoggerFactory.getLogger(VehicleControlAttachmentsResource.class);

    private static final String ENTITY_NAME = "vehicleControlAttachments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleControlAttachmentsService vehicleControlAttachmentsService;

    private final VehicleControlAttachmentsRepository vehicleControlAttachmentsRepository;

    private final VehicleControlAttachmentsQueryService vehicleControlAttachmentsQueryService;

    public VehicleControlAttachmentsResource(
        VehicleControlAttachmentsService vehicleControlAttachmentsService,
        VehicleControlAttachmentsRepository vehicleControlAttachmentsRepository,
        VehicleControlAttachmentsQueryService vehicleControlAttachmentsQueryService
    ) {
        this.vehicleControlAttachmentsService = vehicleControlAttachmentsService;
        this.vehicleControlAttachmentsRepository = vehicleControlAttachmentsRepository;
        this.vehicleControlAttachmentsQueryService = vehicleControlAttachmentsQueryService;
    }

    /**
     * {@code POST  /vehicle-control-attachments} : Create a new vehicleControlAttachments.
     *
     * @param vehicleControlAttachmentsDTO the vehicleControlAttachmentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleControlAttachmentsDTO, or with status {@code 400 (Bad Request)} if the vehicleControlAttachments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicle-control-attachments")
    public ResponseEntity<VehicleControlAttachmentsDTO> createVehicleControlAttachments(
        @Valid @RequestBody VehicleControlAttachmentsDTO vehicleControlAttachmentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save VehicleControlAttachments : {}", vehicleControlAttachmentsDTO);
        if (vehicleControlAttachmentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleControlAttachments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleControlAttachmentsDTO result = vehicleControlAttachmentsService.save(vehicleControlAttachmentsDTO);
        return ResponseEntity
            .created(new URI("/api/vehicle-control-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicle-control-attachments/:id} : Updates an existing vehicleControlAttachments.
     *
     * @param id the id of the vehicleControlAttachmentsDTO to save.
     * @param vehicleControlAttachmentsDTO the vehicleControlAttachmentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleControlAttachmentsDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleControlAttachmentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleControlAttachmentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicle-control-attachments/{id}")
    public ResponseEntity<VehicleControlAttachmentsDTO> updateVehicleControlAttachments(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VehicleControlAttachmentsDTO vehicleControlAttachmentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VehicleControlAttachments : {}, {}", id, vehicleControlAttachmentsDTO);
        if (vehicleControlAttachmentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleControlAttachmentsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleControlAttachmentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VehicleControlAttachmentsDTO result = vehicleControlAttachmentsService.save(vehicleControlAttachmentsDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleControlAttachmentsDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /vehicle-control-attachments/:id} : Partial updates given fields of an existing vehicleControlAttachments, field will ignore if it is null
     *
     * @param id the id of the vehicleControlAttachmentsDTO to save.
     * @param vehicleControlAttachmentsDTO the vehicleControlAttachmentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleControlAttachmentsDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleControlAttachmentsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vehicleControlAttachmentsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vehicleControlAttachmentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vehicle-control-attachments/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VehicleControlAttachmentsDTO> partialUpdateVehicleControlAttachments(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VehicleControlAttachmentsDTO vehicleControlAttachmentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VehicleControlAttachments partially : {}, {}", id, vehicleControlAttachmentsDTO);
        if (vehicleControlAttachmentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleControlAttachmentsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleControlAttachmentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VehicleControlAttachmentsDTO> result = vehicleControlAttachmentsService.partialUpdate(vehicleControlAttachmentsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleControlAttachmentsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vehicle-control-attachments} : get all the vehicleControlAttachments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicleControlAttachments in body.
     */
    @GetMapping("/vehicle-control-attachments")
    public ResponseEntity<List<VehicleControlAttachmentsDTO>> getAllVehicleControlAttachments(
        VehicleControlAttachmentsCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get VehicleControlAttachments by criteria: {}", criteria);
        Page<VehicleControlAttachmentsDTO> page = vehicleControlAttachmentsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vehicle-control-attachments/count} : count all the vehicleControlAttachments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vehicle-control-attachments/count")
    public ResponseEntity<Long> countVehicleControlAttachments(VehicleControlAttachmentsCriteria criteria) {
        log.debug("REST request to count VehicleControlAttachments by criteria: {}", criteria);
        return ResponseEntity.ok().body(vehicleControlAttachmentsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vehicle-control-attachments/:id} : get the "id" vehicleControlAttachments.
     *
     * @param id the id of the vehicleControlAttachmentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleControlAttachmentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicle-control-attachments/{id}")
    public ResponseEntity<VehicleControlAttachmentsDTO> getVehicleControlAttachments(@PathVariable Long id) {
        log.debug("REST request to get VehicleControlAttachments : {}", id);
        Optional<VehicleControlAttachmentsDTO> vehicleControlAttachmentsDTO = vehicleControlAttachmentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleControlAttachmentsDTO);
    }

    /**
     * {@code DELETE  /vehicle-control-attachments/:id} : delete the "id" vehicleControlAttachments.
     *
     * @param id the id of the vehicleControlAttachmentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicle-control-attachments/{id}")
    public ResponseEntity<Void> deleteVehicleControlAttachments(@PathVariable Long id) {
        log.debug("REST request to delete VehicleControlAttachments : {}", id);
        vehicleControlAttachmentsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
