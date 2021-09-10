package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.CustomersRepository;
import com.genesisoft.transporte.service.CustomersQueryService;
import com.genesisoft.transporte.service.CustomersService;
import com.genesisoft.transporte.service.criteria.CustomersCriteria;
import com.genesisoft.transporte.service.dto.CustomersDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.Customers}.
 */
@RestController
@RequestMapping("/api")
public class CustomersResource {

    private final Logger log = LoggerFactory.getLogger(CustomersResource.class);

    private static final String ENTITY_NAME = "customers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomersService customersService;

    private final CustomersRepository customersRepository;

    private final CustomersQueryService customersQueryService;

    public CustomersResource(
        CustomersService customersService,
        CustomersRepository customersRepository,
        CustomersQueryService customersQueryService
    ) {
        this.customersService = customersService;
        this.customersRepository = customersRepository;
        this.customersQueryService = customersQueryService;
    }

    /**
     * {@code POST  /customers} : Create a new customers.
     *
     * @param customersDTO the customersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customersDTO, or with status {@code 400 (Bad Request)} if the customers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customers")
    public ResponseEntity<CustomersDTO> createCustomers(@Valid @RequestBody CustomersDTO customersDTO) throws URISyntaxException {
        log.debug("REST request to save Customers : {}", customersDTO);
        if (customersDTO.getId() != null) {
            throw new BadRequestAlertException("A new customers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomersDTO result = customersService.save(customersDTO);
        return ResponseEntity
            .created(new URI("/api/customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customers/:id} : Updates an existing customers.
     *
     * @param id the id of the customersDTO to save.
     * @param customersDTO the customersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customersDTO,
     * or with status {@code 400 (Bad Request)} if the customersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomersDTO> updateCustomers(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustomersDTO customersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Customers : {}, {}", id, customersDTO);
        if (customersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomersDTO result = customersService.save(customersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /customers/:id} : Partial updates given fields of an existing customers, field will ignore if it is null
     *
     * @param id the id of the customersDTO to save.
     * @param customersDTO the customersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customersDTO,
     * or with status {@code 400 (Bad Request)} if the customersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the customersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the customersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/customers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustomersDTO> partialUpdateCustomers(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustomersDTO customersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Customers partially : {}, {}", id, customersDTO);
        if (customersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomersDTO> result = customersService.partialUpdate(customersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customersDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /customers} : get all the customers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customers in body.
     */
    @GetMapping("/customers")
    public ResponseEntity<List<CustomersDTO>> getAllCustomers(CustomersCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Customers by criteria: {}", criteria);
        Page<CustomersDTO> page = customersQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /customers/count} : count all the customers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/customers/count")
    public ResponseEntity<Long> countCustomers(CustomersCriteria criteria) {
        log.debug("REST request to count Customers by criteria: {}", criteria);
        return ResponseEntity.ok().body(customersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /customers/:id} : get the "id" customers.
     *
     * @param id the id of the customersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomersDTO> getCustomers(@PathVariable Long id) {
        log.debug("REST request to get Customers : {}", id);
        Optional<CustomersDTO> customersDTO = customersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customersDTO);
    }

    /**
     * {@code DELETE  /customers/:id} : delete the "id" customers.
     *
     * @param id the id of the customersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomers(@PathVariable Long id) {
        log.debug("REST request to delete Customers : {}", id);
        customersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
