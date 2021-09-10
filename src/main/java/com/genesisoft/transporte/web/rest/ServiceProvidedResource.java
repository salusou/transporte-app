package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.ServiceProvidedRepository;
import com.genesisoft.transporte.service.ServiceProvidedQueryService;
import com.genesisoft.transporte.service.ServiceProvidedService;
import com.genesisoft.transporte.service.criteria.ServiceProvidedCriteria;
import com.genesisoft.transporte.service.dto.ServiceProvidedDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.ServiceProvided}.
 */
@RestController
@RequestMapping("/api")
public class ServiceProvidedResource {

    private final Logger log = LoggerFactory.getLogger(ServiceProvidedResource.class);

    private static final String ENTITY_NAME = "serviceProvided";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceProvidedService serviceProvidedService;

    private final ServiceProvidedRepository serviceProvidedRepository;

    private final ServiceProvidedQueryService serviceProvidedQueryService;

    public ServiceProvidedResource(
        ServiceProvidedService serviceProvidedService,
        ServiceProvidedRepository serviceProvidedRepository,
        ServiceProvidedQueryService serviceProvidedQueryService
    ) {
        this.serviceProvidedService = serviceProvidedService;
        this.serviceProvidedRepository = serviceProvidedRepository;
        this.serviceProvidedQueryService = serviceProvidedQueryService;
    }

    /**
     * {@code POST  /service-provideds} : Create a new serviceProvided.
     *
     * @param serviceProvidedDTO the serviceProvidedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceProvidedDTO, or with status {@code 400 (Bad Request)} if the serviceProvided has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-provideds")
    public ResponseEntity<ServiceProvidedDTO> createServiceProvided(@Valid @RequestBody ServiceProvidedDTO serviceProvidedDTO)
        throws URISyntaxException {
        log.debug("REST request to save ServiceProvided : {}", serviceProvidedDTO);
        if (serviceProvidedDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceProvided cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceProvidedDTO result = serviceProvidedService.save(serviceProvidedDTO);
        return ResponseEntity
            .created(new URI("/api/service-provideds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-provideds/:id} : Updates an existing serviceProvided.
     *
     * @param id the id of the serviceProvidedDTO to save.
     * @param serviceProvidedDTO the serviceProvidedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceProvidedDTO,
     * or with status {@code 400 (Bad Request)} if the serviceProvidedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceProvidedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-provideds/{id}")
    public ResponseEntity<ServiceProvidedDTO> updateServiceProvided(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServiceProvidedDTO serviceProvidedDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ServiceProvided : {}, {}", id, serviceProvidedDTO);
        if (serviceProvidedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceProvidedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceProvidedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ServiceProvidedDTO result = serviceProvidedService.save(serviceProvidedDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceProvidedDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /service-provideds/:id} : Partial updates given fields of an existing serviceProvided, field will ignore if it is null
     *
     * @param id the id of the serviceProvidedDTO to save.
     * @param serviceProvidedDTO the serviceProvidedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceProvidedDTO,
     * or with status {@code 400 (Bad Request)} if the serviceProvidedDTO is not valid,
     * or with status {@code 404 (Not Found)} if the serviceProvidedDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceProvidedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/service-provideds/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ServiceProvidedDTO> partialUpdateServiceProvided(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServiceProvidedDTO serviceProvidedDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ServiceProvided partially : {}, {}", id, serviceProvidedDTO);
        if (serviceProvidedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceProvidedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceProvidedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceProvidedDTO> result = serviceProvidedService.partialUpdate(serviceProvidedDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceProvidedDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /service-provideds} : get all the serviceProvideds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceProvideds in body.
     */
    @GetMapping("/service-provideds")
    public ResponseEntity<List<ServiceProvidedDTO>> getAllServiceProvideds(ServiceProvidedCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ServiceProvideds by criteria: {}", criteria);
        Page<ServiceProvidedDTO> page = serviceProvidedQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-provideds/count} : count all the serviceProvideds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/service-provideds/count")
    public ResponseEntity<Long> countServiceProvideds(ServiceProvidedCriteria criteria) {
        log.debug("REST request to count ServiceProvideds by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceProvidedQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-provideds/:id} : get the "id" serviceProvided.
     *
     * @param id the id of the serviceProvidedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceProvidedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-provideds/{id}")
    public ResponseEntity<ServiceProvidedDTO> getServiceProvided(@PathVariable Long id) {
        log.debug("REST request to get ServiceProvided : {}", id);
        Optional<ServiceProvidedDTO> serviceProvidedDTO = serviceProvidedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceProvidedDTO);
    }

    /**
     * {@code DELETE  /service-provideds/:id} : delete the "id" serviceProvided.
     *
     * @param id the id of the serviceProvidedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-provideds/{id}")
    public ResponseEntity<Void> deleteServiceProvided(@PathVariable Long id) {
        log.debug("REST request to delete ServiceProvided : {}", id);
        serviceProvidedService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
