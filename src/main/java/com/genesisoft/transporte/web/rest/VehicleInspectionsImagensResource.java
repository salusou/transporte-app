package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.VehicleInspectionsImagensRepository;
import com.genesisoft.transporte.service.VehicleInspectionsImagensQueryService;
import com.genesisoft.transporte.service.VehicleInspectionsImagensService;
import com.genesisoft.transporte.service.criteria.VehicleInspectionsImagensCriteria;
import com.genesisoft.transporte.service.dto.VehicleInspectionsImagensDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.VehicleInspectionsImagens}.
 */
@RestController
@RequestMapping("/api")
public class VehicleInspectionsImagensResource {

    private final Logger log = LoggerFactory.getLogger(VehicleInspectionsImagensResource.class);

    private static final String ENTITY_NAME = "vehicleInspectionsImagens";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleInspectionsImagensService vehicleInspectionsImagensService;

    private final VehicleInspectionsImagensRepository vehicleInspectionsImagensRepository;

    private final VehicleInspectionsImagensQueryService vehicleInspectionsImagensQueryService;

    public VehicleInspectionsImagensResource(
        VehicleInspectionsImagensService vehicleInspectionsImagensService,
        VehicleInspectionsImagensRepository vehicleInspectionsImagensRepository,
        VehicleInspectionsImagensQueryService vehicleInspectionsImagensQueryService
    ) {
        this.vehicleInspectionsImagensService = vehicleInspectionsImagensService;
        this.vehicleInspectionsImagensRepository = vehicleInspectionsImagensRepository;
        this.vehicleInspectionsImagensQueryService = vehicleInspectionsImagensQueryService;
    }

    /**
     * {@code POST  /vehicle-inspections-imagens} : Create a new vehicleInspectionsImagens.
     *
     * @param vehicleInspectionsImagensDTO the vehicleInspectionsImagensDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleInspectionsImagensDTO, or with status {@code 400 (Bad Request)} if the vehicleInspectionsImagens has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicle-inspections-imagens")
    public ResponseEntity<VehicleInspectionsImagensDTO> createVehicleInspectionsImagens(
        @Valid @RequestBody VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO
    ) throws URISyntaxException {
        log.debug("REST request to save VehicleInspectionsImagens : {}", vehicleInspectionsImagensDTO);
        if (vehicleInspectionsImagensDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleInspectionsImagens cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleInspectionsImagensDTO result = vehicleInspectionsImagensService.save(vehicleInspectionsImagensDTO);
        return ResponseEntity
            .created(new URI("/api/vehicle-inspections-imagens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicle-inspections-imagens/:id} : Updates an existing vehicleInspectionsImagens.
     *
     * @param id the id of the vehicleInspectionsImagensDTO to save.
     * @param vehicleInspectionsImagensDTO the vehicleInspectionsImagensDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleInspectionsImagensDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleInspectionsImagensDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleInspectionsImagensDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicle-inspections-imagens/{id}")
    public ResponseEntity<VehicleInspectionsImagensDTO> updateVehicleInspectionsImagens(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VehicleInspectionsImagens : {}, {}", id, vehicleInspectionsImagensDTO);
        if (vehicleInspectionsImagensDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleInspectionsImagensDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleInspectionsImagensRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VehicleInspectionsImagensDTO result = vehicleInspectionsImagensService.save(vehicleInspectionsImagensDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleInspectionsImagensDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /vehicle-inspections-imagens/:id} : Partial updates given fields of an existing vehicleInspectionsImagens, field will ignore if it is null
     *
     * @param id the id of the vehicleInspectionsImagensDTO to save.
     * @param vehicleInspectionsImagensDTO the vehicleInspectionsImagensDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleInspectionsImagensDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleInspectionsImagensDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vehicleInspectionsImagensDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vehicleInspectionsImagensDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vehicle-inspections-imagens/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VehicleInspectionsImagensDTO> partialUpdateVehicleInspectionsImagens(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VehicleInspectionsImagens partially : {}, {}", id, vehicleInspectionsImagensDTO);
        if (vehicleInspectionsImagensDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleInspectionsImagensDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleInspectionsImagensRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VehicleInspectionsImagensDTO> result = vehicleInspectionsImagensService.partialUpdate(vehicleInspectionsImagensDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleInspectionsImagensDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vehicle-inspections-imagens} : get all the vehicleInspectionsImagens.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicleInspectionsImagens in body.
     */
    @GetMapping("/vehicle-inspections-imagens")
    public ResponseEntity<List<VehicleInspectionsImagensDTO>> getAllVehicleInspectionsImagens(
        VehicleInspectionsImagensCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get VehicleInspectionsImagens by criteria: {}", criteria);
        Page<VehicleInspectionsImagensDTO> page = vehicleInspectionsImagensQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vehicle-inspections-imagens/count} : count all the vehicleInspectionsImagens.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vehicle-inspections-imagens/count")
    public ResponseEntity<Long> countVehicleInspectionsImagens(VehicleInspectionsImagensCriteria criteria) {
        log.debug("REST request to count VehicleInspectionsImagens by criteria: {}", criteria);
        return ResponseEntity.ok().body(vehicleInspectionsImagensQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vehicle-inspections-imagens/:id} : get the "id" vehicleInspectionsImagens.
     *
     * @param id the id of the vehicleInspectionsImagensDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleInspectionsImagensDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicle-inspections-imagens/{id}")
    public ResponseEntity<VehicleInspectionsImagensDTO> getVehicleInspectionsImagens(@PathVariable Long id) {
        log.debug("REST request to get VehicleInspectionsImagens : {}", id);
        Optional<VehicleInspectionsImagensDTO> vehicleInspectionsImagensDTO = vehicleInspectionsImagensService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleInspectionsImagensDTO);
    }

    /**
     * {@code DELETE  /vehicle-inspections-imagens/:id} : delete the "id" vehicleInspectionsImagens.
     *
     * @param id the id of the vehicleInspectionsImagensDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicle-inspections-imagens/{id}")
    public ResponseEntity<Void> deleteVehicleInspectionsImagens(@PathVariable Long id) {
        log.debug("REST request to delete VehicleInspectionsImagens : {}", id);
        vehicleInspectionsImagensService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
