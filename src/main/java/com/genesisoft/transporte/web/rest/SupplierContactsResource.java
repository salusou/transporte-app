package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.SupplierContactsRepository;
import com.genesisoft.transporte.service.SupplierContactsQueryService;
import com.genesisoft.transporte.service.SupplierContactsService;
import com.genesisoft.transporte.service.criteria.SupplierContactsCriteria;
import com.genesisoft.transporte.service.dto.SupplierContactsDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.SupplierContacts}.
 */
@RestController
@RequestMapping("/api")
public class SupplierContactsResource {

    private final Logger log = LoggerFactory.getLogger(SupplierContactsResource.class);

    private static final String ENTITY_NAME = "supplierContacts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupplierContactsService supplierContactsService;

    private final SupplierContactsRepository supplierContactsRepository;

    private final SupplierContactsQueryService supplierContactsQueryService;

    public SupplierContactsResource(
        SupplierContactsService supplierContactsService,
        SupplierContactsRepository supplierContactsRepository,
        SupplierContactsQueryService supplierContactsQueryService
    ) {
        this.supplierContactsService = supplierContactsService;
        this.supplierContactsRepository = supplierContactsRepository;
        this.supplierContactsQueryService = supplierContactsQueryService;
    }

    /**
     * {@code POST  /supplier-contacts} : Create a new supplierContacts.
     *
     * @param supplierContactsDTO the supplierContactsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supplierContactsDTO, or with status {@code 400 (Bad Request)} if the supplierContacts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supplier-contacts")
    public ResponseEntity<SupplierContactsDTO> createSupplierContacts(@Valid @RequestBody SupplierContactsDTO supplierContactsDTO)
        throws URISyntaxException {
        log.debug("REST request to save SupplierContacts : {}", supplierContactsDTO);
        if (supplierContactsDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplierContacts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplierContactsDTO result = supplierContactsService.save(supplierContactsDTO);
        return ResponseEntity
            .created(new URI("/api/supplier-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supplier-contacts/:id} : Updates an existing supplierContacts.
     *
     * @param id the id of the supplierContactsDTO to save.
     * @param supplierContactsDTO the supplierContactsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplierContactsDTO,
     * or with status {@code 400 (Bad Request)} if the supplierContactsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supplierContactsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supplier-contacts/{id}")
    public ResponseEntity<SupplierContactsDTO> updateSupplierContacts(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SupplierContactsDTO supplierContactsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SupplierContacts : {}, {}", id, supplierContactsDTO);
        if (supplierContactsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, supplierContactsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!supplierContactsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SupplierContactsDTO result = supplierContactsService.save(supplierContactsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supplierContactsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /supplier-contacts/:id} : Partial updates given fields of an existing supplierContacts, field will ignore if it is null
     *
     * @param id the id of the supplierContactsDTO to save.
     * @param supplierContactsDTO the supplierContactsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplierContactsDTO,
     * or with status {@code 400 (Bad Request)} if the supplierContactsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the supplierContactsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the supplierContactsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/supplier-contacts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SupplierContactsDTO> partialUpdateSupplierContacts(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SupplierContactsDTO supplierContactsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SupplierContacts partially : {}, {}", id, supplierContactsDTO);
        if (supplierContactsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, supplierContactsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!supplierContactsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SupplierContactsDTO> result = supplierContactsService.partialUpdate(supplierContactsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supplierContactsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /supplier-contacts} : get all the supplierContacts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supplierContacts in body.
     */
    @GetMapping("/supplier-contacts")
    public ResponseEntity<List<SupplierContactsDTO>> getAllSupplierContacts(SupplierContactsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SupplierContacts by criteria: {}", criteria);
        Page<SupplierContactsDTO> page = supplierContactsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /supplier-contacts/count} : count all the supplierContacts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/supplier-contacts/count")
    public ResponseEntity<Long> countSupplierContacts(SupplierContactsCriteria criteria) {
        log.debug("REST request to count SupplierContacts by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplierContactsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /supplier-contacts/:id} : get the "id" supplierContacts.
     *
     * @param id the id of the supplierContactsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supplierContactsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supplier-contacts/{id}")
    public ResponseEntity<SupplierContactsDTO> getSupplierContacts(@PathVariable Long id) {
        log.debug("REST request to get SupplierContacts : {}", id);
        Optional<SupplierContactsDTO> supplierContactsDTO = supplierContactsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplierContactsDTO);
    }

    /**
     * {@code DELETE  /supplier-contacts/:id} : delete the "id" supplierContacts.
     *
     * @param id the id of the supplierContactsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supplier-contacts/{id}")
    public ResponseEntity<Void> deleteSupplierContacts(@PathVariable Long id) {
        log.debug("REST request to delete SupplierContacts : {}", id);
        supplierContactsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
