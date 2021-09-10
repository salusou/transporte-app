package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.SuppliersRepository;
import com.genesisoft.transporte.service.SuppliersQueryService;
import com.genesisoft.transporte.service.SuppliersService;
import com.genesisoft.transporte.service.criteria.SuppliersCriteria;
import com.genesisoft.transporte.service.dto.SuppliersDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.Suppliers}.
 */
@RestController
@RequestMapping("/api")
public class SuppliersResource {

    private final Logger log = LoggerFactory.getLogger(SuppliersResource.class);

    private static final String ENTITY_NAME = "suppliers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SuppliersService suppliersService;

    private final SuppliersRepository suppliersRepository;

    private final SuppliersQueryService suppliersQueryService;

    public SuppliersResource(
        SuppliersService suppliersService,
        SuppliersRepository suppliersRepository,
        SuppliersQueryService suppliersQueryService
    ) {
        this.suppliersService = suppliersService;
        this.suppliersRepository = suppliersRepository;
        this.suppliersQueryService = suppliersQueryService;
    }

    /**
     * {@code POST  /suppliers} : Create a new suppliers.
     *
     * @param suppliersDTO the suppliersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new suppliersDTO, or with status {@code 400 (Bad Request)} if the suppliers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/suppliers")
    public ResponseEntity<SuppliersDTO> createSuppliers(@Valid @RequestBody SuppliersDTO suppliersDTO) throws URISyntaxException {
        log.debug("REST request to save Suppliers : {}", suppliersDTO);
        if (suppliersDTO.getId() != null) {
            throw new BadRequestAlertException("A new suppliers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SuppliersDTO result = suppliersService.save(suppliersDTO);
        return ResponseEntity
            .created(new URI("/api/suppliers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /suppliers/:id} : Updates an existing suppliers.
     *
     * @param id the id of the suppliersDTO to save.
     * @param suppliersDTO the suppliersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated suppliersDTO,
     * or with status {@code 400 (Bad Request)} if the suppliersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the suppliersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/suppliers/{id}")
    public ResponseEntity<SuppliersDTO> updateSuppliers(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SuppliersDTO suppliersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Suppliers : {}, {}", id, suppliersDTO);
        if (suppliersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, suppliersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!suppliersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SuppliersDTO result = suppliersService.save(suppliersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, suppliersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /suppliers/:id} : Partial updates given fields of an existing suppliers, field will ignore if it is null
     *
     * @param id the id of the suppliersDTO to save.
     * @param suppliersDTO the suppliersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated suppliersDTO,
     * or with status {@code 400 (Bad Request)} if the suppliersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the suppliersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the suppliersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/suppliers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SuppliersDTO> partialUpdateSuppliers(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SuppliersDTO suppliersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Suppliers partially : {}, {}", id, suppliersDTO);
        if (suppliersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, suppliersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!suppliersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SuppliersDTO> result = suppliersService.partialUpdate(suppliersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, suppliersDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /suppliers} : get all the suppliers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of suppliers in body.
     */
    @GetMapping("/suppliers")
    public ResponseEntity<List<SuppliersDTO>> getAllSuppliers(SuppliersCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Suppliers by criteria: {}", criteria);
        Page<SuppliersDTO> page = suppliersQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /suppliers/count} : count all the suppliers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/suppliers/count")
    public ResponseEntity<Long> countSuppliers(SuppliersCriteria criteria) {
        log.debug("REST request to count Suppliers by criteria: {}", criteria);
        return ResponseEntity.ok().body(suppliersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /suppliers/:id} : get the "id" suppliers.
     *
     * @param id the id of the suppliersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the suppliersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/suppliers/{id}")
    public ResponseEntity<SuppliersDTO> getSuppliers(@PathVariable Long id) {
        log.debug("REST request to get Suppliers : {}", id);
        Optional<SuppliersDTO> suppliersDTO = suppliersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(suppliersDTO);
    }

    /**
     * {@code DELETE  /suppliers/:id} : delete the "id" suppliers.
     *
     * @param id the id of the suppliersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/suppliers/{id}")
    public ResponseEntity<Void> deleteSuppliers(@PathVariable Long id) {
        log.debug("REST request to delete Suppliers : {}", id);
        suppliersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
