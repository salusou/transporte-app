package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.Countries;
import com.genesisoft.transporte.repository.CountriesRepository;
import com.genesisoft.transporte.service.dto.CountriesDTO;
import com.genesisoft.transporte.service.mapper.CountriesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Countries}.
 */
@Service
@Transactional
public class CountriesService {

    private final Logger log = LoggerFactory.getLogger(CountriesService.class);

    private final CountriesRepository countriesRepository;

    private final CountriesMapper countriesMapper;

    public CountriesService(CountriesRepository countriesRepository, CountriesMapper countriesMapper) {
        this.countriesRepository = countriesRepository;
        this.countriesMapper = countriesMapper;
    }

    /**
     * Save a countries.
     *
     * @param countriesDTO the entity to save.
     * @return the persisted entity.
     */
    public CountriesDTO save(CountriesDTO countriesDTO) {
        log.debug("Request to save Countries : {}", countriesDTO);
        Countries countries = countriesMapper.toEntity(countriesDTO);
        countries = countriesRepository.save(countries);
        return countriesMapper.toDto(countries);
    }

    /**
     * Partially update a countries.
     *
     * @param countriesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CountriesDTO> partialUpdate(CountriesDTO countriesDTO) {
        log.debug("Request to partially update Countries : {}", countriesDTO);

        return countriesRepository
            .findById(countriesDTO.getId())
            .map(
                existingCountries -> {
                    countriesMapper.partialUpdate(existingCountries, countriesDTO);

                    return existingCountries;
                }
            )
            .map(countriesRepository::save)
            .map(countriesMapper::toDto);
    }

    /**
     * Get all the countries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CountriesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Countries");
        return countriesRepository.findAll(pageable).map(countriesMapper::toDto);
    }

    /**
     * Get one countries by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CountriesDTO> findOne(Long id) {
        log.debug("Request to get Countries : {}", id);
        return countriesRepository.findById(id).map(countriesMapper::toDto);
    }

    /**
     * Delete the countries by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Countries : {}", id);
        countriesRepository.deleteById(id);
    }
}
