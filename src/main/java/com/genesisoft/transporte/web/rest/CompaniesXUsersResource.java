package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.CompaniesXUsersRepository;
import com.genesisoft.transporte.service.CompaniesXUsersQueryService;
import com.genesisoft.transporte.service.CompaniesXUsersService;
import com.genesisoft.transporte.service.criteria.CompaniesXUsersCriteria;
import com.genesisoft.transporte.service.dto.CompaniesXUsersDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.CompaniesXUsers}.
 */
@RestController
@RequestMapping("/api")
public class CompaniesXUsersResource {

    private final Logger log = LoggerFactory.getLogger(CompaniesXUsersResource.class);

    private static final String ENTITY_NAME = "companiesXUsers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompaniesXUsersService companiesXUsersService;

    private final CompaniesXUsersRepository companiesXUsersRepository;

    private final CompaniesXUsersQueryService companiesXUsersQueryService;

    public CompaniesXUsersResource(
        CompaniesXUsersService companiesXUsersService,
        CompaniesXUsersRepository companiesXUsersRepository,
        CompaniesXUsersQueryService companiesXUsersQueryService
    ) {
        this.companiesXUsersService = companiesXUsersService;
        this.companiesXUsersRepository = companiesXUsersRepository;
        this.companiesXUsersQueryService = companiesXUsersQueryService;
    }

    /**
     * {@code POST  /companies-x-users} : Create a new companiesXUsers.
     *
     * @param companiesXUsersDTO the companiesXUsersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companiesXUsersDTO, or with status {@code 400 (Bad Request)} if the companiesXUsers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/companies-x-users")
    public ResponseEntity<CompaniesXUsersDTO> createCompaniesXUsers(@Valid @RequestBody CompaniesXUsersDTO companiesXUsersDTO)
        throws URISyntaxException {
        log.debug("REST request to save CompaniesXUsers : {}", companiesXUsersDTO);
        if (companiesXUsersDTO.getId() != null) {
            throw new BadRequestAlertException("A new companiesXUsers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompaniesXUsersDTO result = companiesXUsersService.save(companiesXUsersDTO);
        return ResponseEntity
            .created(new URI("/api/companies-x-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /companies-x-users/:id} : Updates an existing companiesXUsers.
     *
     * @param id the id of the companiesXUsersDTO to save.
     * @param companiesXUsersDTO the companiesXUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companiesXUsersDTO,
     * or with status {@code 400 (Bad Request)} if the companiesXUsersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companiesXUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/companies-x-users/{id}")
    public ResponseEntity<CompaniesXUsersDTO> updateCompaniesXUsers(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CompaniesXUsersDTO companiesXUsersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CompaniesXUsers : {}, {}", id, companiesXUsersDTO);
        if (companiesXUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companiesXUsersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companiesXUsersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompaniesXUsersDTO result = companiesXUsersService.save(companiesXUsersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companiesXUsersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /companies-x-users/:id} : Partial updates given fields of an existing companiesXUsers, field will ignore if it is null
     *
     * @param id the id of the companiesXUsersDTO to save.
     * @param companiesXUsersDTO the companiesXUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companiesXUsersDTO,
     * or with status {@code 400 (Bad Request)} if the companiesXUsersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the companiesXUsersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the companiesXUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/companies-x-users/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CompaniesXUsersDTO> partialUpdateCompaniesXUsers(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CompaniesXUsersDTO companiesXUsersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompaniesXUsers partially : {}, {}", id, companiesXUsersDTO);
        if (companiesXUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companiesXUsersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companiesXUsersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompaniesXUsersDTO> result = companiesXUsersService.partialUpdate(companiesXUsersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companiesXUsersDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /companies-x-users} : get all the companiesXUsers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companiesXUsers in body.
     */
    @GetMapping("/companies-x-users")
    public ResponseEntity<List<CompaniesXUsersDTO>> getAllCompaniesXUsers(CompaniesXUsersCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CompaniesXUsers by criteria: {}", criteria);
        Page<CompaniesXUsersDTO> page = companiesXUsersQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /companies-x-users/count} : count all the companiesXUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/companies-x-users/count")
    public ResponseEntity<Long> countCompaniesXUsers(CompaniesXUsersCriteria criteria) {
        log.debug("REST request to count CompaniesXUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(companiesXUsersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /companies-x-users/:id} : get the "id" companiesXUsers.
     *
     * @param id the id of the companiesXUsersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companiesXUsersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/companies-x-users/{id}")
    public ResponseEntity<CompaniesXUsersDTO> getCompaniesXUsers(@PathVariable Long id) {
        log.debug("REST request to get CompaniesXUsers : {}", id);
        Optional<CompaniesXUsersDTO> companiesXUsersDTO = companiesXUsersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companiesXUsersDTO);
    }

    /**
     * {@code DELETE  /companies-x-users/:id} : delete the "id" companiesXUsers.
     *
     * @param id the id of the companiesXUsersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/companies-x-users/{id}")
    public ResponseEntity<Void> deleteCompaniesXUsers(@PathVariable Long id) {
        log.debug("REST request to delete CompaniesXUsers : {}", id);
        companiesXUsersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
