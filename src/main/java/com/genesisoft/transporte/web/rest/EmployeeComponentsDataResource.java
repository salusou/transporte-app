package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.EmployeeComponentsDataRepository;
import com.genesisoft.transporte.service.EmployeeComponentsDataQueryService;
import com.genesisoft.transporte.service.EmployeeComponentsDataService;
import com.genesisoft.transporte.service.criteria.EmployeeComponentsDataCriteria;
import com.genesisoft.transporte.service.dto.EmployeeComponentsDataDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.EmployeeComponentsData}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeComponentsDataResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeComponentsDataResource.class);

    private static final String ENTITY_NAME = "employeeComponentsData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeComponentsDataService employeeComponentsDataService;

    private final EmployeeComponentsDataRepository employeeComponentsDataRepository;

    private final EmployeeComponentsDataQueryService employeeComponentsDataQueryService;

    public EmployeeComponentsDataResource(
        EmployeeComponentsDataService employeeComponentsDataService,
        EmployeeComponentsDataRepository employeeComponentsDataRepository,
        EmployeeComponentsDataQueryService employeeComponentsDataQueryService
    ) {
        this.employeeComponentsDataService = employeeComponentsDataService;
        this.employeeComponentsDataRepository = employeeComponentsDataRepository;
        this.employeeComponentsDataQueryService = employeeComponentsDataQueryService;
    }

    /**
     * {@code POST  /employee-components-data} : Create a new employeeComponentsData.
     *
     * @param employeeComponentsDataDTO the employeeComponentsDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeComponentsDataDTO, or with status {@code 400 (Bad Request)} if the employeeComponentsData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-components-data")
    public ResponseEntity<EmployeeComponentsDataDTO> createEmployeeComponentsData(
        @Valid @RequestBody EmployeeComponentsDataDTO employeeComponentsDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to save EmployeeComponentsData : {}", employeeComponentsDataDTO);
        if (employeeComponentsDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeComponentsData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeComponentsDataDTO result = employeeComponentsDataService.save(employeeComponentsDataDTO);
        return ResponseEntity
            .created(new URI("/api/employee-components-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-components-data/:id} : Updates an existing employeeComponentsData.
     *
     * @param id the id of the employeeComponentsDataDTO to save.
     * @param employeeComponentsDataDTO the employeeComponentsDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeComponentsDataDTO,
     * or with status {@code 400 (Bad Request)} if the employeeComponentsDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeComponentsDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-components-data/{id}")
    public ResponseEntity<EmployeeComponentsDataDTO> updateEmployeeComponentsData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeComponentsDataDTO employeeComponentsDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeComponentsData : {}, {}", id, employeeComponentsDataDTO);
        if (employeeComponentsDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeComponentsDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeComponentsDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeComponentsDataDTO result = employeeComponentsDataService.save(employeeComponentsDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeComponentsDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-components-data/:id} : Partial updates given fields of an existing employeeComponentsData, field will ignore if it is null
     *
     * @param id the id of the employeeComponentsDataDTO to save.
     * @param employeeComponentsDataDTO the employeeComponentsDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeComponentsDataDTO,
     * or with status {@code 400 (Bad Request)} if the employeeComponentsDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the employeeComponentsDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeComponentsDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-components-data/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EmployeeComponentsDataDTO> partialUpdateEmployeeComponentsData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeComponentsDataDTO employeeComponentsDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeComponentsData partially : {}, {}", id, employeeComponentsDataDTO);
        if (employeeComponentsDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeComponentsDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeComponentsDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeComponentsDataDTO> result = employeeComponentsDataService.partialUpdate(employeeComponentsDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeComponentsDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-components-data} : get all the employeeComponentsData.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeComponentsData in body.
     */
    @GetMapping("/employee-components-data")
    public ResponseEntity<List<EmployeeComponentsDataDTO>> getAllEmployeeComponentsData(
        EmployeeComponentsDataCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get EmployeeComponentsData by criteria: {}", criteria);
        Page<EmployeeComponentsDataDTO> page = employeeComponentsDataQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-components-data/count} : count all the employeeComponentsData.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-components-data/count")
    public ResponseEntity<Long> countEmployeeComponentsData(EmployeeComponentsDataCriteria criteria) {
        log.debug("REST request to count EmployeeComponentsData by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeComponentsDataQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-components-data/:id} : get the "id" employeeComponentsData.
     *
     * @param id the id of the employeeComponentsDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeComponentsDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-components-data/{id}")
    public ResponseEntity<EmployeeComponentsDataDTO> getEmployeeComponentsData(@PathVariable Long id) {
        log.debug("REST request to get EmployeeComponentsData : {}", id);
        Optional<EmployeeComponentsDataDTO> employeeComponentsDataDTO = employeeComponentsDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeComponentsDataDTO);
    }

    /**
     * {@code DELETE  /employee-components-data/:id} : delete the "id" employeeComponentsData.
     *
     * @param id the id of the employeeComponentsDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-components-data/{id}")
    public ResponseEntity<Void> deleteEmployeeComponentsData(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeComponentsData : {}", id);
        employeeComponentsDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
