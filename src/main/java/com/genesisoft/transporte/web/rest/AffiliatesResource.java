package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.AffiliatesRepository;
import com.genesisoft.transporte.service.AffiliatesQueryService;
import com.genesisoft.transporte.service.AffiliatesService;
import com.genesisoft.transporte.service.criteria.AffiliatesCriteria;
import com.genesisoft.transporte.service.dto.AffiliatesDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.Affiliates}.
 */
@RestController
@RequestMapping("/api")
public class AffiliatesResource {

    private final Logger log = LoggerFactory.getLogger(AffiliatesResource.class);

    private static final String ENTITY_NAME = "affiliates";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AffiliatesService affiliatesService;

    private final AffiliatesRepository affiliatesRepository;

    private final AffiliatesQueryService affiliatesQueryService;

    public AffiliatesResource(
        AffiliatesService affiliatesService,
        AffiliatesRepository affiliatesRepository,
        AffiliatesQueryService affiliatesQueryService
    ) {
        this.affiliatesService = affiliatesService;
        this.affiliatesRepository = affiliatesRepository;
        this.affiliatesQueryService = affiliatesQueryService;
    }

    /**
     * {@code POST  /affiliates} : Create a new affiliates.
     *
     * @param affiliatesDTO the affiliatesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new affiliatesDTO, or with status {@code 400 (Bad Request)} if the affiliates has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/affiliates")
    public ResponseEntity<AffiliatesDTO> createAffiliates(@Valid @RequestBody AffiliatesDTO affiliatesDTO) throws URISyntaxException {
        log.debug("REST request to save Affiliates : {}", affiliatesDTO);
        if (affiliatesDTO.getId() != null) {
            throw new BadRequestAlertException("A new affiliates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AffiliatesDTO result = affiliatesService.save(affiliatesDTO);
        return ResponseEntity
            .created(new URI("/api/affiliates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /affiliates/:id} : Updates an existing affiliates.
     *
     * @param id the id of the affiliatesDTO to save.
     * @param affiliatesDTO the affiliatesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated affiliatesDTO,
     * or with status {@code 400 (Bad Request)} if the affiliatesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the affiliatesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/affiliates/{id}")
    public ResponseEntity<AffiliatesDTO> updateAffiliates(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AffiliatesDTO affiliatesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Affiliates : {}, {}", id, affiliatesDTO);
        if (affiliatesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, affiliatesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!affiliatesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AffiliatesDTO result = affiliatesService.save(affiliatesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, affiliatesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /affiliates/:id} : Partial updates given fields of an existing affiliates, field will ignore if it is null
     *
     * @param id the id of the affiliatesDTO to save.
     * @param affiliatesDTO the affiliatesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated affiliatesDTO,
     * or with status {@code 400 (Bad Request)} if the affiliatesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the affiliatesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the affiliatesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/affiliates/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AffiliatesDTO> partialUpdateAffiliates(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AffiliatesDTO affiliatesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Affiliates partially : {}, {}", id, affiliatesDTO);
        if (affiliatesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, affiliatesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!affiliatesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AffiliatesDTO> result = affiliatesService.partialUpdate(affiliatesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, affiliatesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /affiliates} : get all the affiliates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of affiliates in body.
     */
    @GetMapping("/affiliates")
    public ResponseEntity<List<AffiliatesDTO>> getAllAffiliates(AffiliatesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Affiliates by criteria: {}", criteria);
        Page<AffiliatesDTO> page = affiliatesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /affiliates/count} : count all the affiliates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/affiliates/count")
    public ResponseEntity<Long> countAffiliates(AffiliatesCriteria criteria) {
        log.debug("REST request to count Affiliates by criteria: {}", criteria);
        return ResponseEntity.ok().body(affiliatesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /affiliates/:id} : get the "id" affiliates.
     *
     * @param id the id of the affiliatesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the affiliatesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/affiliates/{id}")
    public ResponseEntity<AffiliatesDTO> getAffiliates(@PathVariable Long id) {
        log.debug("REST request to get Affiliates : {}", id);
        Optional<AffiliatesDTO> affiliatesDTO = affiliatesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(affiliatesDTO);
    }

    /**
     * {@code DELETE  /affiliates/:id} : delete the "id" affiliates.
     *
     * @param id the id of the affiliatesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/affiliates/{id}")
    public ResponseEntity<Void> deleteAffiliates(@PathVariable Long id) {
        log.debug("REST request to delete Affiliates : {}", id);
        affiliatesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
