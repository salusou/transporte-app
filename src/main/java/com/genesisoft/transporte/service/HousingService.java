package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.Housing;
import com.genesisoft.transporte.repository.HousingRepository;
import com.genesisoft.transporte.service.dto.HousingDTO;
import com.genesisoft.transporte.service.mapper.HousingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Housing}.
 */
@Service
@Transactional
public class HousingService {

    private final Logger log = LoggerFactory.getLogger(HousingService.class);

    private final HousingRepository housingRepository;

    private final HousingMapper housingMapper;

    public HousingService(HousingRepository housingRepository, HousingMapper housingMapper) {
        this.housingRepository = housingRepository;
        this.housingMapper = housingMapper;
    }

    /**
     * Save a housing.
     *
     * @param housingDTO the entity to save.
     * @return the persisted entity.
     */
    public HousingDTO save(HousingDTO housingDTO) {
        log.debug("Request to save Housing : {}", housingDTO);
        Housing housing = housingMapper.toEntity(housingDTO);
        housing = housingRepository.save(housing);
        return housingMapper.toDto(housing);
    }

    /**
     * Partially update a housing.
     *
     * @param housingDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HousingDTO> partialUpdate(HousingDTO housingDTO) {
        log.debug("Request to partially update Housing : {}", housingDTO);

        return housingRepository
            .findById(housingDTO.getId())
            .map(
                existingHousing -> {
                    housingMapper.partialUpdate(existingHousing, housingDTO);

                    return existingHousing;
                }
            )
            .map(housingRepository::save)
            .map(housingMapper::toDto);
    }

    /**
     * Get all the housings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HousingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Housings");
        return housingRepository.findAll(pageable).map(housingMapper::toDto);
    }

    /**
     * Get one housing by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HousingDTO> findOne(Long id) {
        log.debug("Request to get Housing : {}", id);
        return housingRepository.findById(id).map(housingMapper::toDto);
    }

    /**
     * Delete the housing by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Housing : {}", id);
        housingRepository.deleteById(id);
    }
}
