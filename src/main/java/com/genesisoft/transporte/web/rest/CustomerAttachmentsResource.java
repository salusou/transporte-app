package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.CustomerAttachmentsRepository;
import com.genesisoft.transporte.service.CustomerAttachmentsQueryService;
import com.genesisoft.transporte.service.CustomerAttachmentsService;
import com.genesisoft.transporte.service.criteria.CustomerAttachmentsCriteria;
import com.genesisoft.transporte.service.dto.CustomerAttachmentsDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.CustomerAttachments}.
 */
@RestController
@RequestMapping("/api")
public class CustomerAttachmentsResource {

    private final Logger log = LoggerFactory.getLogger(CustomerAttachmentsResource.class);

    private static final String ENTITY_NAME = "customerAttachments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerAttachmentsService customerAttachmentsService;

    private final CustomerAttachmentsRepository customerAttachmentsRepository;

    private final CustomerAttachmentsQueryService customerAttachmentsQueryService;

    public CustomerAttachmentsResource(
        CustomerAttachmentsService customerAttachmentsService,
        CustomerAttachmentsRepository customerAttachmentsRepository,
        CustomerAttachmentsQueryService customerAttachmentsQueryService
    ) {
        this.customerAttachmentsService = customerAttachmentsService;
        this.customerAttachmentsRepository = customerAttachmentsRepository;
        this.customerAttachmentsQueryService = customerAttachmentsQueryService;
    }

    /**
     * {@code POST  /customer-attachments} : Create a new customerAttachments.
     *
     * @param customerAttachmentsDTO the customerAttachmentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerAttachmentsDTO, or with status {@code 400 (Bad Request)} if the customerAttachments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-attachments")
    public ResponseEntity<CustomerAttachmentsDTO> createCustomerAttachments(
        @Valid @RequestBody CustomerAttachmentsDTO customerAttachmentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CustomerAttachments : {}", customerAttachmentsDTO);
        if (customerAttachmentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerAttachments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerAttachmentsDTO result = customerAttachmentsService.save(customerAttachmentsDTO);
        return ResponseEntity
            .created(new URI("/api/customer-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-attachments/:id} : Updates an existing customerAttachments.
     *
     * @param id the id of the customerAttachmentsDTO to save.
     * @param customerAttachmentsDTO the customerAttachmentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerAttachmentsDTO,
     * or with status {@code 400 (Bad Request)} if the customerAttachmentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerAttachmentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-attachments/{id}")
    public ResponseEntity<CustomerAttachmentsDTO> updateCustomerAttachments(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustomerAttachmentsDTO customerAttachmentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustomerAttachments : {}, {}", id, customerAttachmentsDTO);
        if (customerAttachmentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerAttachmentsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerAttachmentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomerAttachmentsDTO result = customerAttachmentsService.save(customerAttachmentsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerAttachmentsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /customer-attachments/:id} : Partial updates given fields of an existing customerAttachments, field will ignore if it is null
     *
     * @param id the id of the customerAttachmentsDTO to save.
     * @param customerAttachmentsDTO the customerAttachmentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerAttachmentsDTO,
     * or with status {@code 400 (Bad Request)} if the customerAttachmentsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the customerAttachmentsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the customerAttachmentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/customer-attachments/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustomerAttachmentsDTO> partialUpdateCustomerAttachments(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustomerAttachmentsDTO customerAttachmentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomerAttachments partially : {}, {}", id, customerAttachmentsDTO);
        if (customerAttachmentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerAttachmentsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerAttachmentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomerAttachmentsDTO> result = customerAttachmentsService.partialUpdate(customerAttachmentsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerAttachmentsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /customer-attachments} : get all the customerAttachments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerAttachments in body.
     */
    @GetMapping("/customer-attachments")
    public ResponseEntity<List<CustomerAttachmentsDTO>> getAllCustomerAttachments(CustomerAttachmentsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CustomerAttachments by criteria: {}", criteria);
        Page<CustomerAttachmentsDTO> page = customerAttachmentsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /customer-attachments/count} : count all the customerAttachments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/customer-attachments/count")
    public ResponseEntity<Long> countCustomerAttachments(CustomerAttachmentsCriteria criteria) {
        log.debug("REST request to count CustomerAttachments by criteria: {}", criteria);
        return ResponseEntity.ok().body(customerAttachmentsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /customer-attachments/:id} : get the "id" customerAttachments.
     *
     * @param id the id of the customerAttachmentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerAttachmentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-attachments/{id}")
    public ResponseEntity<CustomerAttachmentsDTO> getCustomerAttachments(@PathVariable Long id) {
        log.debug("REST request to get CustomerAttachments : {}", id);
        Optional<CustomerAttachmentsDTO> customerAttachmentsDTO = customerAttachmentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerAttachmentsDTO);
    }

    /**
     * {@code DELETE  /customer-attachments/:id} : delete the "id" customerAttachments.
     *
     * @param id the id of the customerAttachmentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-attachments/{id}")
    public ResponseEntity<Void> deleteCustomerAttachments(@PathVariable Long id) {
        log.debug("REST request to delete CustomerAttachments : {}", id);
        customerAttachmentsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
