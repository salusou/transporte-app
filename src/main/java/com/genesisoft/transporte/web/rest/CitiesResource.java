package com.genesisoft.transporte.web.rest;

import com.genesisoft.transporte.repository.CitiesRepository;
import com.genesisoft.transporte.service.CitiesQueryService;
import com.genesisoft.transporte.service.CitiesService;
import com.genesisoft.transporte.service.criteria.CitiesCriteria;
import com.genesisoft.transporte.service.dto.CitiesDTO;
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
 * REST controller for managing {@link com.genesisoft.transporte.domain.Cities}.
 */
@RestController
@RequestMapping("/api")
public class CitiesResource {

    private final Logger log = LoggerFactory.getLogger(CitiesResource.class);

    private static final String ENTITY_NAME = "cities";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CitiesService citiesService;

    private final CitiesRepository citiesRepository;

    private final CitiesQueryService citiesQueryService;

    public CitiesResource(CitiesService citiesService, CitiesRepository citiesRepository, CitiesQueryService citiesQueryService) {
        this.citiesService = citiesService;
        this.citiesRepository = citiesRepository;
        this.citiesQueryService = citiesQueryService;
    }

    /**
     * {@code POST  /cities} : Create a new cities.
     *
     * @param citiesDTO the citiesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new citiesDTO, or with status {@code 400 (Bad Request)} if the cities has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cities")
    public ResponseEntity<CitiesDTO> createCities(@Valid @RequestBody CitiesDTO citiesDTO) throws URISyntaxException {
        log.debug("REST request to save Cities : {}", citiesDTO);
        if (citiesDTO.getId() != null) {
            throw new BadRequestAlertException("A new cities cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CitiesDTO result = citiesService.save(citiesDTO);
        return ResponseEntity
            .created(new URI("/api/cities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cities/:id} : Updates an existing cities.
     *
     * @param id the id of the citiesDTO to save.
     * @param citiesDTO the citiesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citiesDTO,
     * or with status {@code 400 (Bad Request)} if the citiesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the citiesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cities/{id}")
    public ResponseEntity<CitiesDTO> updateCities(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CitiesDTO citiesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Cities : {}, {}", id, citiesDTO);
        if (citiesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, citiesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!citiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CitiesDTO result = citiesService.save(citiesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citiesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cities/:id} : Partial updates given fields of an existing cities, field will ignore if it is null
     *
     * @param id the id of the citiesDTO to save.
     * @param citiesDTO the citiesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citiesDTO,
     * or with status {@code 400 (Bad Request)} if the citiesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the citiesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the citiesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cities/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CitiesDTO> partialUpdateCities(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CitiesDTO citiesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cities partially : {}, {}", id, citiesDTO);
        if (citiesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, citiesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!citiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CitiesDTO> result = citiesService.partialUpdate(citiesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citiesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cities} : get all the cities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cities in body.
     */
    @GetMapping("/cities")
    public ResponseEntity<List<CitiesDTO>> getAllCities(CitiesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Cities by criteria: {}", criteria);
        Page<CitiesDTO> page = citiesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cities/count} : count all the cities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cities/count")
    public ResponseEntity<Long> countCities(CitiesCriteria criteria) {
        log.debug("REST request to count Cities by criteria: {}", criteria);
        return ResponseEntity.ok().body(citiesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cities/:id} : get the "id" cities.
     *
     * @param id the id of the citiesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the citiesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cities/{id}")
    public ResponseEntity<CitiesDTO> getCities(@PathVariable Long id) {
        log.debug("REST request to get Cities : {}", id);
        Optional<CitiesDTO> citiesDTO = citiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(citiesDTO);
    }

    /**
     * {@code DELETE  /cities/:id} : delete the "id" cities.
     *
     * @param id the id of the citiesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cities/{id}")
    public ResponseEntity<Void> deleteCities(@PathVariable Long id) {
        log.debug("REST request to delete Cities : {}", id);
        citiesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
