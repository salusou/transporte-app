package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.CompaniesRepository;
import com.genesisoft.transporte.service.CompaniesQueryService;
import com.genesisoft.transporte.service.CompaniesService;
import com.genesisoft.transporte.service.criteria.CompaniesCriteria;
import com.genesisoft.transporte.service.dto.CompaniesDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.Companies}.
 */
@RestController
@RequestMapping("/api")
public class CompaniesResource {

    private final Logger log = LoggerFactory.getLogger(CompaniesResource.class);

    private static final String ENTITY_NAME = "companies";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompaniesService companiesService;

    private final CompaniesRepository companiesRepository;

    private final CompaniesQueryService companiesQueryService;

    public CompaniesResource(
        CompaniesService companiesService,
        CompaniesRepository companiesRepository,
        CompaniesQueryService companiesQueryService
    ) {
        this.companiesService = companiesService;
        this.companiesRepository = companiesRepository;
        this.companiesQueryService = companiesQueryService;
    }

    /**
     * {@code POST  /companies} : Create a new companies.
     *
     * @param companiesDTO the companiesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companiesDTO, or with status {@code 400 (Bad Request)} if the companies has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/companies")
    public ResponseEntity<CompaniesDTO> createCompanies(@Valid @RequestBody CompaniesDTO companiesDTO) throws URISyntaxException {
        log.debug("REST request to save Companies : {}", companiesDTO);
        if (companiesDTO.getId() != null) {
            throw new BadRequestAlertException("A new companies cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompaniesDTO result = companiesService.save(companiesDTO);
        return ResponseEntity
            .created(new URI("/api/companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /companies/:id} : Updates an existing companies.
     *
     * @param id the id of the companiesDTO to save.
     * @param companiesDTO the companiesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companiesDTO,
     * or with status {@code 400 (Bad Request)} if the companiesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companiesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/companies/{id}")
    public ResponseEntity<CompaniesDTO> updateCompanies(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CompaniesDTO companiesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Companies : {}, {}", id, companiesDTO);
        if (companiesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companiesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompaniesDTO result = companiesService.save(companiesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companiesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /companies/:id} : Partial updates given fields of an existing companies, field will ignore if it is null
     *
     * @param id the id of the companiesDTO to save.
     * @param companiesDTO the companiesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companiesDTO,
     * or with status {@code 400 (Bad Request)} if the companiesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the companiesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the companiesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/companies/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CompaniesDTO> partialUpdateCompanies(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CompaniesDTO companiesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Companies partially : {}, {}", id, companiesDTO);
        if (companiesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companiesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompaniesDTO> result = companiesService.partialUpdate(companiesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companiesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /companies} : get all the companies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companies in body.
     */
    @GetMapping("/companies")
    public ResponseEntity<List<CompaniesDTO>> getAllCompanies(CompaniesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Companies by criteria: {}", criteria);
        Page<CompaniesDTO> page = companiesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /companies/count} : count all the companies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/companies/count")
    public ResponseEntity<Long> countCompanies(CompaniesCriteria criteria) {
        log.debug("REST request to count Companies by criteria: {}", criteria);
        return ResponseEntity.ok().body(companiesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /companies/:id} : get the "id" companies.
     *
     * @param id the id of the companiesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companiesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/companies/{id}")
    public ResponseEntity<CompaniesDTO> getCompanies(@PathVariable Long id) {
        log.debug("REST request to get Companies : {}", id);
        Optional<CompaniesDTO> companiesDTO = companiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companiesDTO);
    }

    /**
     * {@code DELETE  /companies/:id} : delete the "id" companies.
     *
     * @param id the id of the companiesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompanies(@PathVariable Long id) {
        log.debug("REST request to delete Companies : {}", id);
        companiesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
