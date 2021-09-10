package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.CustomersGroupsRepository;
import com.genesisoft.transporte.service.CustomersGroupsQueryService;
import com.genesisoft.transporte.service.CustomersGroupsService;
import com.genesisoft.transporte.service.criteria.CustomersGroupsCriteria;
import com.genesisoft.transporte.service.dto.CustomersGroupsDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.CustomersGroups}.
 */
@RestController
@RequestMapping("/api")
public class CustomersGroupsResource {

    private final Logger log = LoggerFactory.getLogger(CustomersGroupsResource.class);

    private static final String ENTITY_NAME = "customersGroups";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomersGroupsService customersGroupsService;

    private final CustomersGroupsRepository customersGroupsRepository;

    private final CustomersGroupsQueryService customersGroupsQueryService;

    public CustomersGroupsResource(
        CustomersGroupsService customersGroupsService,
        CustomersGroupsRepository customersGroupsRepository,
        CustomersGroupsQueryService customersGroupsQueryService
    ) {
        this.customersGroupsService = customersGroupsService;
        this.customersGroupsRepository = customersGroupsRepository;
        this.customersGroupsQueryService = customersGroupsQueryService;
    }

    /**
     * {@code POST  /customers-groups} : Create a new customersGroups.
     *
     * @param customersGroupsDTO the customersGroupsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customersGroupsDTO, or with status {@code 400 (Bad Request)} if the customersGroups has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customers-groups")
    public ResponseEntity<CustomersGroupsDTO> createCustomersGroups(@Valid @RequestBody CustomersGroupsDTO customersGroupsDTO)
        throws URISyntaxException {
        log.debug("REST request to save CustomersGroups : {}", customersGroupsDTO);
        if (customersGroupsDTO.getId() != null) {
            throw new BadRequestAlertException("A new customersGroups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomersGroupsDTO result = customersGroupsService.save(customersGroupsDTO);
        return ResponseEntity
            .created(new URI("/api/customers-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customers-groups/:id} : Updates an existing customersGroups.
     *
     * @param id the id of the customersGroupsDTO to save.
     * @param customersGroupsDTO the customersGroupsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customersGroupsDTO,
     * or with status {@code 400 (Bad Request)} if the customersGroupsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customersGroupsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customers-groups/{id}")
    public ResponseEntity<CustomersGroupsDTO> updateCustomersGroups(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustomersGroupsDTO customersGroupsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustomersGroups : {}, {}", id, customersGroupsDTO);
        if (customersGroupsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customersGroupsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customersGroupsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomersGroupsDTO result = customersGroupsService.save(customersGroupsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customersGroupsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /customers-groups/:id} : Partial updates given fields of an existing customersGroups, field will ignore if it is null
     *
     * @param id the id of the customersGroupsDTO to save.
     * @param customersGroupsDTO the customersGroupsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customersGroupsDTO,
     * or with status {@code 400 (Bad Request)} if the customersGroupsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the customersGroupsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the customersGroupsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/customers-groups/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustomersGroupsDTO> partialUpdateCustomersGroups(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustomersGroupsDTO customersGroupsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomersGroups partially : {}, {}", id, customersGroupsDTO);
        if (customersGroupsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customersGroupsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customersGroupsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomersGroupsDTO> result = customersGroupsService.partialUpdate(customersGroupsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customersGroupsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /customers-groups} : get all the customersGroups.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customersGroups in body.
     */
    @GetMapping("/customers-groups")
    public ResponseEntity<List<CustomersGroupsDTO>> getAllCustomersGroups(CustomersGroupsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CustomersGroups by criteria: {}", criteria);
        Page<CustomersGroupsDTO> page = customersGroupsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /customers-groups/count} : count all the customersGroups.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/customers-groups/count")
    public ResponseEntity<Long> countCustomersGroups(CustomersGroupsCriteria criteria) {
        log.debug("REST request to count CustomersGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(customersGroupsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /customers-groups/:id} : get the "id" customersGroups.
     *
     * @param id the id of the customersGroupsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customersGroupsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customers-groups/{id}")
    public ResponseEntity<CustomersGroupsDTO> getCustomersGroups(@PathVariable Long id) {
        log.debug("REST request to get CustomersGroups : {}", id);
        Optional<CustomersGroupsDTO> customersGroupsDTO = customersGroupsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customersGroupsDTO);
    }

    /**
     * {@code DELETE  /customers-groups/:id} : delete the "id" customersGroups.
     *
     * @param id the id of the customersGroupsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customers-groups/{id}")
    public ResponseEntity<Void> deleteCustomersGroups(@PathVariable Long id) {
        log.debug("REST request to delete CustomersGroups : {}", id);
        customersGroupsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
