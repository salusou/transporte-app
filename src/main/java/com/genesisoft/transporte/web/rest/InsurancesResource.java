package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.InsurancesRepository;
import com.genesisoft.transporte.service.InsurancesQueryService;
import com.genesisoft.transporte.service.InsurancesService;
import com.genesisoft.transporte.service.criteria.InsurancesCriteria;
import com.genesisoft.transporte.service.dto.InsurancesDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.Insurances}.
 */
@RestController
@RequestMapping("/api")
public class InsurancesResource {

    private final Logger log = LoggerFactory.getLogger(InsurancesResource.class);

    private static final String ENTITY_NAME = "insurances";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InsurancesService insurancesService;

    private final InsurancesRepository insurancesRepository;

    private final InsurancesQueryService insurancesQueryService;

    public InsurancesResource(
        InsurancesService insurancesService,
        InsurancesRepository insurancesRepository,
        InsurancesQueryService insurancesQueryService
    ) {
        this.insurancesService = insurancesService;
        this.insurancesRepository = insurancesRepository;
        this.insurancesQueryService = insurancesQueryService;
    }

    /**
     * {@code POST  /insurances} : Create a new insurances.
     *
     * @param insurancesDTO the insurancesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new insurancesDTO, or with status {@code 400 (Bad Request)} if the insurances has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/insurances")
    public ResponseEntity<InsurancesDTO> createInsurances(@Valid @RequestBody InsurancesDTO insurancesDTO) throws URISyntaxException {
        log.debug("REST request to save Insurances : {}", insurancesDTO);
        if (insurancesDTO.getId() != null) {
            throw new BadRequestAlertException("A new insurances cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InsurancesDTO result = insurancesService.save(insurancesDTO);
        return ResponseEntity
            .created(new URI("/api/insurances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /insurances/:id} : Updates an existing insurances.
     *
     * @param id the id of the insurancesDTO to save.
     * @param insurancesDTO the insurancesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated insurancesDTO,
     * or with status {@code 400 (Bad Request)} if the insurancesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the insurancesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/insurances/{id}")
    public ResponseEntity<InsurancesDTO> updateInsurances(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InsurancesDTO insurancesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Insurances : {}, {}", id, insurancesDTO);
        if (insurancesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, insurancesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!insurancesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InsurancesDTO result = insurancesService.save(insurancesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, insurancesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /insurances/:id} : Partial updates given fields of an existing insurances, field will ignore if it is null
     *
     * @param id the id of the insurancesDTO to save.
     * @param insurancesDTO the insurancesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated insurancesDTO,
     * or with status {@code 400 (Bad Request)} if the insurancesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the insurancesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the insurancesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/insurances/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<InsurancesDTO> partialUpdateInsurances(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InsurancesDTO insurancesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Insurances partially : {}, {}", id, insurancesDTO);
        if (insurancesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, insurancesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!insurancesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InsurancesDTO> result = insurancesService.partialUpdate(insurancesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, insurancesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /insurances} : get all the insurances.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of insurances in body.
     */
    @GetMapping("/insurances")
    public ResponseEntity<List<InsurancesDTO>> getAllInsurances(InsurancesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Insurances by criteria: {}", criteria);
        Page<InsurancesDTO> page = insurancesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /insurances/count} : count all the insurances.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/insurances/count")
    public ResponseEntity<Long> countInsurances(InsurancesCriteria criteria) {
        log.debug("REST request to count Insurances by criteria: {}", criteria);
        return ResponseEntity.ok().body(insurancesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /insurances/:id} : get the "id" insurances.
     *
     * @param id the id of the insurancesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the insurancesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/insurances/{id}")
    public ResponseEntity<InsurancesDTO> getInsurances(@PathVariable Long id) {
        log.debug("REST request to get Insurances : {}", id);
        Optional<InsurancesDTO> insurancesDTO = insurancesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(insurancesDTO);
    }

    /**
     * {@code DELETE  /insurances/:id} : delete the "id" insurances.
     *
     * @param id the id of the insurancesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/insurances/{id}")
    public ResponseEntity<Void> deleteInsurances(@PathVariable Long id) {
        log.debug("REST request to delete Insurances : {}", id);
        insurancesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
