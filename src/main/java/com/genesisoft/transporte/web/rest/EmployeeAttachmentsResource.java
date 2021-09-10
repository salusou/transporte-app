package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.EmployeeAttachmentsRepository;
import com.genesisoft.transporte.service.EmployeeAttachmentsQueryService;
import com.genesisoft.transporte.service.EmployeeAttachmentsService;
import com.genesisoft.transporte.service.criteria.EmployeeAttachmentsCriteria;
import com.genesisoft.transporte.service.dto.EmployeeAttachmentsDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.EmployeeAttachments}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeAttachmentsResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeAttachmentsResource.class);

    private static final String ENTITY_NAME = "employeeAttachments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeAttachmentsService employeeAttachmentsService;

    private final EmployeeAttachmentsRepository employeeAttachmentsRepository;

    private final EmployeeAttachmentsQueryService employeeAttachmentsQueryService;

    public EmployeeAttachmentsResource(
        EmployeeAttachmentsService employeeAttachmentsService,
        EmployeeAttachmentsRepository employeeAttachmentsRepository,
        EmployeeAttachmentsQueryService employeeAttachmentsQueryService
    ) {
        this.employeeAttachmentsService = employeeAttachmentsService;
        this.employeeAttachmentsRepository = employeeAttachmentsRepository;
        this.employeeAttachmentsQueryService = employeeAttachmentsQueryService;
    }

    /**
     * {@code POST  /employee-attachments} : Create a new employeeAttachments.
     *
     * @param employeeAttachmentsDTO the employeeAttachmentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeAttachmentsDTO, or with status {@code 400 (Bad Request)} if the employeeAttachments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-attachments")
    public ResponseEntity<EmployeeAttachmentsDTO> createEmployeeAttachments(
        @Valid @RequestBody EmployeeAttachmentsDTO employeeAttachmentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save EmployeeAttachments : {}", employeeAttachmentsDTO);
        if (employeeAttachmentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeAttachments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeAttachmentsDTO result = employeeAttachmentsService.save(employeeAttachmentsDTO);
        return ResponseEntity
            .created(new URI("/api/employee-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-attachments/:id} : Updates an existing employeeAttachments.
     *
     * @param id the id of the employeeAttachmentsDTO to save.
     * @param employeeAttachmentsDTO the employeeAttachmentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeAttachmentsDTO,
     * or with status {@code 400 (Bad Request)} if the employeeAttachmentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeAttachmentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-attachments/{id}")
    public ResponseEntity<EmployeeAttachmentsDTO> updateEmployeeAttachments(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeAttachmentsDTO employeeAttachmentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeAttachments : {}, {}", id, employeeAttachmentsDTO);
        if (employeeAttachmentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeAttachmentsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeAttachmentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeAttachmentsDTO result = employeeAttachmentsService.save(employeeAttachmentsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeAttachmentsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-attachments/:id} : Partial updates given fields of an existing employeeAttachments, field will ignore if it is null
     *
     * @param id the id of the employeeAttachmentsDTO to save.
     * @param employeeAttachmentsDTO the employeeAttachmentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeAttachmentsDTO,
     * or with status {@code 400 (Bad Request)} if the employeeAttachmentsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the employeeAttachmentsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeAttachmentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-attachments/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EmployeeAttachmentsDTO> partialUpdateEmployeeAttachments(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeAttachmentsDTO employeeAttachmentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeAttachments partially : {}, {}", id, employeeAttachmentsDTO);
        if (employeeAttachmentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeAttachmentsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeAttachmentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeAttachmentsDTO> result = employeeAttachmentsService.partialUpdate(employeeAttachmentsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeAttachmentsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-attachments} : get all the employeeAttachments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeAttachments in body.
     */
    @GetMapping("/employee-attachments")
    public ResponseEntity<List<EmployeeAttachmentsDTO>> getAllEmployeeAttachments(EmployeeAttachmentsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmployeeAttachments by criteria: {}", criteria);
        Page<EmployeeAttachmentsDTO> page = employeeAttachmentsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-attachments/count} : count all the employeeAttachments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-attachments/count")
    public ResponseEntity<Long> countEmployeeAttachments(EmployeeAttachmentsCriteria criteria) {
        log.debug("REST request to count EmployeeAttachments by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeAttachmentsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-attachments/:id} : get the "id" employeeAttachments.
     *
     * @param id the id of the employeeAttachmentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeAttachmentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-attachments/{id}")
    public ResponseEntity<EmployeeAttachmentsDTO> getEmployeeAttachments(@PathVariable Long id) {
        log.debug("REST request to get EmployeeAttachments : {}", id);
        Optional<EmployeeAttachmentsDTO> employeeAttachmentsDTO = employeeAttachmentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeAttachmentsDTO);
    }

    /**
     * {@code DELETE  /employee-attachments/:id} : delete the "id" employeeAttachments.
     *
     * @param id the id of the employeeAttachmentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-attachments/{id}")
    public ResponseEntity<Void> deleteEmployeeAttachments(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeAttachments : {}", id);
        employeeAttachmentsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
