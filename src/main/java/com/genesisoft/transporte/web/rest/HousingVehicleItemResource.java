package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.HousingVehicleItemRepository;
import com.genesisoft.transporte.service.HousingVehicleItemQueryService;
import com.genesisoft.transporte.service.HousingVehicleItemService;
import com.genesisoft.transporte.service.criteria.HousingVehicleItemCriteria;
import com.genesisoft.transporte.service.dto.HousingVehicleItemDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.HousingVehicleItem}.
 */
@RestController
@RequestMapping("/api")
public class HousingVehicleItemResource {

    private final Logger log = LoggerFactory.getLogger(HousingVehicleItemResource.class);

    private static final String ENTITY_NAME = "housingVehicleItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HousingVehicleItemService housingVehicleItemService;

    private final HousingVehicleItemRepository housingVehicleItemRepository;

    private final HousingVehicleItemQueryService housingVehicleItemQueryService;

    public HousingVehicleItemResource(
        HousingVehicleItemService housingVehicleItemService,
        HousingVehicleItemRepository housingVehicleItemRepository,
        HousingVehicleItemQueryService housingVehicleItemQueryService
    ) {
        this.housingVehicleItemService = housingVehicleItemService;
        this.housingVehicleItemRepository = housingVehicleItemRepository;
        this.housingVehicleItemQueryService = housingVehicleItemQueryService;
    }

    /**
     * {@code POST  /housing-vehicle-items} : Create a new housingVehicleItem.
     *
     * @param housingVehicleItemDTO the housingVehicleItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new housingVehicleItemDTO, or with status {@code 400 (Bad Request)} if the housingVehicleItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/housing-vehicle-items")
    public ResponseEntity<HousingVehicleItemDTO> createHousingVehicleItem(@Valid @RequestBody HousingVehicleItemDTO housingVehicleItemDTO)
        throws URISyntaxException {
        log.debug("REST request to save HousingVehicleItem : {}", housingVehicleItemDTO);
        if (housingVehicleItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new housingVehicleItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HousingVehicleItemDTO result = housingVehicleItemService.save(housingVehicleItemDTO);
        return ResponseEntity
            .created(new URI("/api/housing-vehicle-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /housing-vehicle-items/:id} : Updates an existing housingVehicleItem.
     *
     * @param id the id of the housingVehicleItemDTO to save.
     * @param housingVehicleItemDTO the housingVehicleItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated housingVehicleItemDTO,
     * or with status {@code 400 (Bad Request)} if the housingVehicleItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the housingVehicleItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/housing-vehicle-items/{id}")
    public ResponseEntity<HousingVehicleItemDTO> updateHousingVehicleItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HousingVehicleItemDTO housingVehicleItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HousingVehicleItem : {}, {}", id, housingVehicleItemDTO);
        if (housingVehicleItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, housingVehicleItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!housingVehicleItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HousingVehicleItemDTO result = housingVehicleItemService.save(housingVehicleItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, housingVehicleItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /housing-vehicle-items/:id} : Partial updates given fields of an existing housingVehicleItem, field will ignore if it is null
     *
     * @param id the id of the housingVehicleItemDTO to save.
     * @param housingVehicleItemDTO the housingVehicleItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated housingVehicleItemDTO,
     * or with status {@code 400 (Bad Request)} if the housingVehicleItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the housingVehicleItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the housingVehicleItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/housing-vehicle-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<HousingVehicleItemDTO> partialUpdateHousingVehicleItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HousingVehicleItemDTO housingVehicleItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HousingVehicleItem partially : {}, {}", id, housingVehicleItemDTO);
        if (housingVehicleItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, housingVehicleItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!housingVehicleItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HousingVehicleItemDTO> result = housingVehicleItemService.partialUpdate(housingVehicleItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, housingVehicleItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /housing-vehicle-items} : get all the housingVehicleItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of housingVehicleItems in body.
     */
    @GetMapping("/housing-vehicle-items")
    public ResponseEntity<List<HousingVehicleItemDTO>> getAllHousingVehicleItems(HousingVehicleItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get HousingVehicleItems by criteria: {}", criteria);
        Page<HousingVehicleItemDTO> page = housingVehicleItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /housing-vehicle-items/count} : count all the housingVehicleItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/housing-vehicle-items/count")
    public ResponseEntity<Long> countHousingVehicleItems(HousingVehicleItemCriteria criteria) {
        log.debug("REST request to count HousingVehicleItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(housingVehicleItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /housing-vehicle-items/:id} : get the "id" housingVehicleItem.
     *
     * @param id the id of the housingVehicleItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the housingVehicleItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/housing-vehicle-items/{id}")
    public ResponseEntity<HousingVehicleItemDTO> getHousingVehicleItem(@PathVariable Long id) {
        log.debug("REST request to get HousingVehicleItem : {}", id);
        Optional<HousingVehicleItemDTO> housingVehicleItemDTO = housingVehicleItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(housingVehicleItemDTO);
    }

    /**
     * {@code DELETE  /housing-vehicle-items/:id} : delete the "id" housingVehicleItem.
     *
     * @param id the id of the housingVehicleItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/housing-vehicle-items/{id}")
    public ResponseEntity<Void> deleteHousingVehicleItem(@PathVariable Long id) {
        log.debug("REST request to delete HousingVehicleItem : {}", id);
        housingVehicleItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
