package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.Cities;
import com.genesisoft.transporte.repository.CitiesRepository;
import com.genesisoft.transporte.service.dto.CitiesDTO;
import com.genesisoft.transporte.service.mapper.CitiesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cities}.
 */
@Service
@Transactional
public class CitiesService {

    private final Logger log = LoggerFactory.getLogger(CitiesService.class);

    private final CitiesRepository citiesRepository;

    private final CitiesMapper citiesMapper;

    public CitiesService(CitiesRepository citiesRepository, CitiesMapper citiesMapper) {
        this.citiesRepository = citiesRepository;
        this.citiesMapper = citiesMapper;
    }

    /**
     * Save a cities.
     *
     * @param citiesDTO the entity to save.
     * @return the persisted entity.
     */
    public CitiesDTO save(CitiesDTO citiesDTO) {
        log.debug("Request to save Cities : {}", citiesDTO);
        Cities cities = citiesMapper.toEntity(citiesDTO);
        cities = citiesRepository.save(cities);
        return citiesMapper.toDto(cities);
    }

    /**
     * Partially update a cities.
     *
     * @param citiesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CitiesDTO> partialUpdate(CitiesDTO citiesDTO) {
        log.debug("Request to partially update Cities : {}", citiesDTO);

        return citiesRepository
            .findById(citiesDTO.getId())
            .map(
                existingCities -> {
                    citiesMapper.partialUpdate(existingCities, citiesDTO);

                    return existingCities;
                }
            )
            .map(citiesRepository::save)
            .map(citiesMapper::toDto);
    }

    /**
     * Get all the cities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CitiesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cities");
        return citiesRepository.findAll(pageable).map(citiesMapper::toDto);
    }

    /**
     * Get one cities by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CitiesDTO> findOne(Long id) {
        log.debug("Request to get Cities : {}", id);
        return citiesRepository.findById(id).map(citiesMapper::toDto);
    }

    /**
     * Delete the cities by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cities : {}", id);
        citiesRepository.deleteById(id);
    }
}
