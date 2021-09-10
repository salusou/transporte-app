package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.CustomersContactsRepository;
import com.genesisoft.transporte.service.CustomersContactsQueryService;
import com.genesisoft.transporte.service.CustomersContactsService;
import com.genesisoft.transporte.service.criteria.CustomersContactsCriteria;
import com.genesisoft.transporte.service.dto.CustomersContactsDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.CustomersContacts}.
 */
@RestController
@RequestMapping("/api")
public class CustomersContactsResource {

    private final Logger log = LoggerFactory.getLogger(CustomersContactsResource.class);

    private static final String ENTITY_NAME = "customersContacts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomersContactsService customersContactsService;

    private final CustomersContactsRepository customersContactsRepository;

    private final CustomersContactsQueryService customersContactsQueryService;

    public CustomersContactsResource(
        CustomersContactsService customersContactsService,
        CustomersContactsRepository customersContactsRepository,
        CustomersContactsQueryService customersContactsQueryService
    ) {
        this.customersContactsService = customersContactsService;
        this.customersContactsRepository = customersContactsRepository;
        this.customersContactsQueryService = customersContactsQueryService;
    }

    /**
     * {@code POST  /customers-contacts} : Create a new customersContacts.
     *
     * @param customersContactsDTO the customersContactsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customersContactsDTO, or with status {@code 400 (Bad Request)} if the customersContacts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customers-contacts")
    public ResponseEntity<CustomersContactsDTO> createCustomersContacts(@Valid @RequestBody CustomersContactsDTO customersContactsDTO)
        throws URISyntaxException {
        log.debug("REST request to save CustomersContacts : {}", customersContactsDTO);
        if (customersContactsDTO.getId() != null) {
            throw new BadRequestAlertException("A new customersContacts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomersContactsDTO result = customersContactsService.save(customersContactsDTO);
        return ResponseEntity
            .created(new URI("/api/customers-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customers-contacts/:id} : Updates an existing customersContacts.
     *
     * @param id the id of the customersContactsDTO to save.
     * @param customersContactsDTO the customersContactsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customersContactsDTO,
     * or with status {@code 400 (Bad Request)} if the customersContactsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customersContactsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customers-contacts/{id}")
    public ResponseEntity<CustomersContactsDTO> updateCustomersContacts(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustomersContactsDTO customersContactsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustomersContacts : {}, {}", id, customersContactsDTO);
        if (customersContactsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customersContactsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customersContactsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomersContactsDTO result = customersContactsService.save(customersContactsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customersContactsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /customers-contacts/:id} : Partial updates given fields of an existing customersContacts, field will ignore if it is null
     *
     * @param id the id of the customersContactsDTO to save.
     * @param customersContactsDTO the customersContactsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customersContactsDTO,
     * or with status {@code 400 (Bad Request)} if the customersContactsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the customersContactsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the customersContactsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/customers-contacts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustomersContactsDTO> partialUpdateCustomersContacts(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustomersContactsDTO customersContactsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomersContacts partially : {}, {}", id, customersContactsDTO);
        if (customersContactsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customersContactsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customersContactsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomersContactsDTO> result = customersContactsService.partialUpdate(customersContactsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customersContactsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /customers-contacts} : get all the customersContacts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customersContacts in body.
     */
    @GetMapping("/customers-contacts")
    public ResponseEntity<List<CustomersContactsDTO>> getAllCustomersContacts(CustomersContactsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CustomersContacts by criteria: {}", criteria);
        Page<CustomersContactsDTO> page = customersContactsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /customers-contacts/count} : count all the customersContacts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/customers-contacts/count")
    public ResponseEntity<Long> countCustomersContacts(CustomersContactsCriteria criteria) {
        log.debug("REST request to count CustomersContacts by criteria: {}", criteria);
        return ResponseEntity.ok().body(customersContactsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /customers-contacts/:id} : get the "id" customersContacts.
     *
     * @param id the id of the customersContactsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customersContactsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customers-contacts/{id}")
    public ResponseEntity<CustomersContactsDTO> getCustomersContacts(@PathVariable Long id) {
        log.debug("REST request to get CustomersContacts : {}", id);
        Optional<CustomersContactsDTO> customersContactsDTO = customersContactsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customersContactsDTO);
    }

    /**
     * {@code DELETE  /customers-contacts/:id} : delete the "id" customersContacts.
     *
     * @param id the id of the customersContactsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customers-contacts/{id}")
    public ResponseEntity<Void> deleteCustomersContacts(@PathVariable Long id) {
        log.debug("REST request to delete CustomersContacts : {}", id);
        customersContactsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
