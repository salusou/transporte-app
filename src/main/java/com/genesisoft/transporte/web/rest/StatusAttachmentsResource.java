package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.StatusAttachmentsRepository;
import com.genesisoft.transporte.service.StatusAttachmentsQueryService;
import com.genesisoft.transporte.service.StatusAttachmentsService;
import com.genesisoft.transporte.service.criteria.StatusAttachmentsCriteria;
import com.genesisoft.transporte.service.dto.StatusAttachmentsDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.StatusAttachments}.
 */
@RestController
@RequestMapping("/api")
public class StatusAttachmentsResource {

    private final Logger log = LoggerFactory.getLogger(StatusAttachmentsResource.class);

    private static final String ENTITY_NAME = "statusAttachments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatusAttachmentsService statusAttachmentsService;

    private final StatusAttachmentsRepository statusAttachmentsRepository;

    private final StatusAttachmentsQueryService statusAttachmentsQueryService;

    public StatusAttachmentsResource(
        StatusAttachmentsService statusAttachmentsService,
        StatusAttachmentsRepository statusAttachmentsRepository,
        StatusAttachmentsQueryService statusAttachmentsQueryService
    ) {
        this.statusAttachmentsService = statusAttachmentsService;
        this.statusAttachmentsRepository = statusAttachmentsRepository;
        this.statusAttachmentsQueryService = statusAttachmentsQueryService;
    }

    /**
     * {@code POST  /status-attachments} : Create a new statusAttachments.
     *
     * @param statusAttachmentsDTO the statusAttachmentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statusAttachmentsDTO, or with status {@code 400 (Bad Request)} if the statusAttachments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/status-attachments")
    public ResponseEntity<StatusAttachmentsDTO> createStatusAttachments(@Valid @RequestBody StatusAttachmentsDTO statusAttachmentsDTO)
        throws URISyntaxException {
        log.debug("REST request to save StatusAttachments : {}", statusAttachmentsDTO);
        if (statusAttachmentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new statusAttachments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatusAttachmentsDTO result = statusAttachmentsService.save(statusAttachmentsDTO);
        return ResponseEntity
            .created(new URI("/api/status-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /status-attachments/:id} : Updates an existing statusAttachments.
     *
     * @param id the id of the statusAttachmentsDTO to save.
     * @param statusAttachmentsDTO the statusAttachmentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statusAttachmentsDTO,
     * or with status {@code 400 (Bad Request)} if the statusAttachmentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statusAttachmentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/status-attachments/{id}")
    public ResponseEntity<StatusAttachmentsDTO> updateStatusAttachments(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StatusAttachmentsDTO statusAttachmentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StatusAttachments : {}, {}", id, statusAttachmentsDTO);
        if (statusAttachmentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statusAttachmentsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statusAttachmentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StatusAttachmentsDTO result = statusAttachmentsService.save(statusAttachmentsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statusAttachmentsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /status-attachments/:id} : Partial updates given fields of an existing statusAttachments, field will ignore if it is null
     *
     * @param id the id of the statusAttachmentsDTO to save.
     * @param statusAttachmentsDTO the statusAttachmentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statusAttachmentsDTO,
     * or with status {@code 400 (Bad Request)} if the statusAttachmentsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the statusAttachmentsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the statusAttachmentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/status-attachments/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<StatusAttachmentsDTO> partialUpdateStatusAttachments(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StatusAttachmentsDTO statusAttachmentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StatusAttachments partially : {}, {}", id, statusAttachmentsDTO);
        if (statusAttachmentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statusAttachmentsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statusAttachmentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StatusAttachmentsDTO> result = statusAttachmentsService.partialUpdate(statusAttachmentsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statusAttachmentsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /status-attachments} : get all the statusAttachments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statusAttachments in body.
     */
    @GetMapping("/status-attachments")
    public ResponseEntity<List<StatusAttachmentsDTO>> getAllStatusAttachments(StatusAttachmentsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StatusAttachments by criteria: {}", criteria);
        Page<StatusAttachmentsDTO> page = statusAttachmentsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /status-attachments/count} : count all the statusAttachments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/status-attachments/count")
    public ResponseEntity<Long> countStatusAttachments(StatusAttachmentsCriteria criteria) {
        log.debug("REST request to count StatusAttachments by criteria: {}", criteria);
        return ResponseEntity.ok().body(statusAttachmentsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /status-attachments/:id} : get the "id" statusAttachments.
     *
     * @param id the id of the statusAttachmentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statusAttachmentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/status-attachments/{id}")
    public ResponseEntity<StatusAttachmentsDTO> getStatusAttachments(@PathVariable Long id) {
        log.debug("REST request to get StatusAttachments : {}", id);
        Optional<StatusAttachmentsDTO> statusAttachmentsDTO = statusAttachmentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statusAttachmentsDTO);
    }

    /**
     * {@code DELETE  /status-attachments/:id} : delete the "id" statusAttachments.
     *
     * @param id the id of the statusAttachmentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/status-attachments/{id}")
    public ResponseEntity<Void> deleteStatusAttachments(@PathVariable Long id) {
        log.debug("REST request to delete StatusAttachments : {}", id);
        statusAttachmentsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
