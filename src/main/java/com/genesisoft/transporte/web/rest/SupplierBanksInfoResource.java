package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.SupplierBanksInfoRepository;
import com.genesisoft.transporte.service.SupplierBanksInfoQueryService;
import com.genesisoft.transporte.service.SupplierBanksInfoService;
import com.genesisoft.transporte.service.criteria.SupplierBanksInfoCriteria;
import com.genesisoft.transporte.service.dto.SupplierBanksInfoDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.SupplierBanksInfo}.
 */
@RestController
@RequestMapping("/api")
public class SupplierBanksInfoResource {

    private final Logger log = LoggerFactory.getLogger(SupplierBanksInfoResource.class);

    private static final String ENTITY_NAME = "supplierBanksInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupplierBanksInfoService supplierBanksInfoService;

    private final SupplierBanksInfoRepository supplierBanksInfoRepository;

    private final SupplierBanksInfoQueryService supplierBanksInfoQueryService;

    public SupplierBanksInfoResource(
        SupplierBanksInfoService supplierBanksInfoService,
        SupplierBanksInfoRepository supplierBanksInfoRepository,
        SupplierBanksInfoQueryService supplierBanksInfoQueryService
    ) {
        this.supplierBanksInfoService = supplierBanksInfoService;
        this.supplierBanksInfoRepository = supplierBanksInfoRepository;
        this.supplierBanksInfoQueryService = supplierBanksInfoQueryService;
    }

    /**
     * {@code POST  /supplier-banks-infos} : Create a new supplierBanksInfo.
     *
     * @param supplierBanksInfoDTO the supplierBanksInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supplierBanksInfoDTO, or with status {@code 400 (Bad Request)} if the supplierBanksInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supplier-banks-infos")
    public ResponseEntity<SupplierBanksInfoDTO> createSupplierBanksInfo(@Valid @RequestBody SupplierBanksInfoDTO supplierBanksInfoDTO)
        throws URISyntaxException {
        log.debug("REST request to save SupplierBanksInfo : {}", supplierBanksInfoDTO);
        if (supplierBanksInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplierBanksInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplierBanksInfoDTO result = supplierBanksInfoService.save(supplierBanksInfoDTO);
        return ResponseEntity
            .created(new URI("/api/supplier-banks-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supplier-banks-infos/:id} : Updates an existing supplierBanksInfo.
     *
     * @param id the id of the supplierBanksInfoDTO to save.
     * @param supplierBanksInfoDTO the supplierBanksInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplierBanksInfoDTO,
     * or with status {@code 400 (Bad Request)} if the supplierBanksInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supplierBanksInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supplier-banks-infos/{id}")
    public ResponseEntity<SupplierBanksInfoDTO> updateSupplierBanksInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SupplierBanksInfoDTO supplierBanksInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SupplierBanksInfo : {}, {}", id, supplierBanksInfoDTO);
        if (supplierBanksInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, supplierBanksInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!supplierBanksInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SupplierBanksInfoDTO result = supplierBanksInfoService.save(supplierBanksInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supplierBanksInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /supplier-banks-infos/:id} : Partial updates given fields of an existing supplierBanksInfo, field will ignore if it is null
     *
     * @param id the id of the supplierBanksInfoDTO to save.
     * @param supplierBanksInfoDTO the supplierBanksInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplierBanksInfoDTO,
     * or with status {@code 400 (Bad Request)} if the supplierBanksInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the supplierBanksInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the supplierBanksInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/supplier-banks-infos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SupplierBanksInfoDTO> partialUpdateSupplierBanksInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SupplierBanksInfoDTO supplierBanksInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SupplierBanksInfo partially : {}, {}", id, supplierBanksInfoDTO);
        if (supplierBanksInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, supplierBanksInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!supplierBanksInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SupplierBanksInfoDTO> result = supplierBanksInfoService.partialUpdate(supplierBanksInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supplierBanksInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /supplier-banks-infos} : get all the supplierBanksInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supplierBanksInfos in body.
     */
    @GetMapping("/supplier-banks-infos")
    public ResponseEntity<List<SupplierBanksInfoDTO>> getAllSupplierBanksInfos(SupplierBanksInfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SupplierBanksInfos by criteria: {}", criteria);
        Page<SupplierBanksInfoDTO> page = supplierBanksInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /supplier-banks-infos/count} : count all the supplierBanksInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/supplier-banks-infos/count")
    public ResponseEntity<Long> countSupplierBanksInfos(SupplierBanksInfoCriteria criteria) {
        log.debug("REST request to count SupplierBanksInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplierBanksInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /supplier-banks-infos/:id} : get the "id" supplierBanksInfo.
     *
     * @param id the id of the supplierBanksInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supplierBanksInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supplier-banks-infos/{id}")
    public ResponseEntity<SupplierBanksInfoDTO> getSupplierBanksInfo(@PathVariable Long id) {
        log.debug("REST request to get SupplierBanksInfo : {}", id);
        Optional<SupplierBanksInfoDTO> supplierBanksInfoDTO = supplierBanksInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplierBanksInfoDTO);
    }

    /**
     * {@code DELETE  /supplier-banks-infos/:id} : delete the "id" supplierBanksInfo.
     *
     * @param id the id of the supplierBanksInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supplier-banks-infos/{id}")
    public ResponseEntity<Void> deleteSupplierBanksInfo(@PathVariable Long id) {
        log.debug("REST request to delete SupplierBanksInfo : {}", id);
        supplierBanksInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
