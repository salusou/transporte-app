package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.ParkingRepository;
import com.genesisoft.transporte.service.ParkingQueryService;
import com.genesisoft.transporte.service.ParkingService;
import com.genesisoft.transporte.service.criteria.ParkingCriteria;
import com.genesisoft.transporte.service.dto.ParkingDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.Parking}.
 */
@RestController
@RequestMapping("/api")
public class ParkingResource {

    private final Logger log = LoggerFactory.getLogger(ParkingResource.class);

    private static final String ENTITY_NAME = "parking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParkingService parkingService;

    private final ParkingRepository parkingRepository;

    private final ParkingQueryService parkingQueryService;

    public ParkingResource(ParkingService parkingService, ParkingRepository parkingRepository, ParkingQueryService parkingQueryService) {
        this.parkingService = parkingService;
        this.parkingRepository = parkingRepository;
        this.parkingQueryService = parkingQueryService;
    }

    /**
     * {@code POST  /parkings} : Create a new parking.
     *
     * @param parkingDTO the parkingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parkingDTO, or with status {@code 400 (Bad Request)} if the parking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parkings")
    public ResponseEntity<ParkingDTO> createParking(@Valid @RequestBody ParkingDTO parkingDTO) throws URISyntaxException {
        log.debug("REST request to save Parking : {}", parkingDTO);
        if (parkingDTO.getId() != null) {
            throw new BadRequestAlertException("A new parking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParkingDTO result = parkingService.save(parkingDTO);
        return ResponseEntity
            .created(new URI("/api/parkings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parkings/:id} : Updates an existing parking.
     *
     * @param id the id of the parkingDTO to save.
     * @param parkingDTO the parkingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parkingDTO,
     * or with status {@code 400 (Bad Request)} if the parkingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parkingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parkings/{id}")
    public ResponseEntity<ParkingDTO> updateParking(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ParkingDTO parkingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Parking : {}, {}", id, parkingDTO);
        if (parkingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parkingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parkingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ParkingDTO result = parkingService.save(parkingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parkingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /parkings/:id} : Partial updates given fields of an existing parking, field will ignore if it is null
     *
     * @param id the id of the parkingDTO to save.
     * @param parkingDTO the parkingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parkingDTO,
     * or with status {@code 400 (Bad Request)} if the parkingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the parkingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the parkingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/parkings/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ParkingDTO> partialUpdateParking(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ParkingDTO parkingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Parking partially : {}, {}", id, parkingDTO);
        if (parkingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parkingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parkingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ParkingDTO> result = parkingService.partialUpdate(parkingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parkingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /parkings} : get all the parkings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parkings in body.
     */
    @GetMapping("/parkings")
    public ResponseEntity<List<ParkingDTO>> getAllParkings(ParkingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Parkings by criteria: {}", criteria);
        Page<ParkingDTO> page = parkingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /parkings/count} : count all the parkings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/parkings/count")
    public ResponseEntity<Long> countParkings(ParkingCriteria criteria) {
        log.debug("REST request to count Parkings by criteria: {}", criteria);
        return ResponseEntity.ok().body(parkingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /parkings/:id} : get the "id" parking.
     *
     * @param id the id of the parkingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parkingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parkings/{id}")
    public ResponseEntity<ParkingDTO> getParking(@PathVariable Long id) {
        log.debug("REST request to get Parking : {}", id);
        Optional<ParkingDTO> parkingDTO = parkingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parkingDTO);
    }

    /**
     * {@code DELETE  /parkings/:id} : delete the "id" parking.
     *
     * @param id the id of the parkingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parkings/{id}")
    public ResponseEntity<Void> deleteParking(@PathVariable Long id) {
        log.debug("REST request to delete Parking : {}", id);
        parkingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
