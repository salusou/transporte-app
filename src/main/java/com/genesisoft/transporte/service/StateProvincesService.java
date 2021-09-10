package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.StateProvinces;
import com.genesisoft.transporte.repository.StateProvincesRepository;
import com.genesisoft.transporte.service.dto.StateProvincesDTO;
import com.genesisoft.transporte.service.mapper.StateProvincesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StateProvinces}.
 */
@Service
@Transactional
public class StateProvincesService {

    private final Logger log = LoggerFactory.getLogger(StateProvincesService.class);

    private final StateProvincesRepository stateProvincesRepository;

    private final StateProvincesMapper stateProvincesMapper;

    public StateProvincesService(StateProvincesRepository stateProvincesRepository, StateProvincesMapper stateProvincesMapper) {
        this.stateProvincesRepository = stateProvincesRepository;
        this.stateProvincesMapper = stateProvincesMapper;
    }

    /**
     * Save a stateProvinces.
     *
     * @param stateProvincesDTO the entity to save.
     * @return the persisted entity.
     */
    public StateProvincesDTO save(StateProvincesDTO stateProvincesDTO) {
        log.debug("Request to save StateProvinces : {}", stateProvincesDTO);
        StateProvinces stateProvinces = stateProvincesMapper.toEntity(stateProvincesDTO);
        stateProvinces = stateProvincesRepository.save(stateProvinces);
        return stateProvincesMapper.toDto(stateProvinces);
    }

    /**
     * Partially update a stateProvinces.
     *
     * @param stateProvincesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StateProvincesDTO> partialUpdate(StateProvincesDTO stateProvincesDTO) {
        log.debug("Request to partially update StateProvinces : {}", stateProvincesDTO);

        return stateProvincesRepository
            .findById(stateProvincesDTO.getId())
            .map(
                existingStateProvinces -> {
                    stateProvincesMapper.partialUpdate(existingStateProvinces, stateProvincesDTO);

                    return existingStateProvinces;
                }
            )
            .map(stateProvincesRepository::save)
            .map(stateProvincesMapper::toDto);
    }

    /**
     * Get all the stateProvinces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StateProvincesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StateProvinces");
        return stateProvincesRepository.findAll(pageable).map(stateProvincesMapper::toDto);
    }

    /**
     * Get one stateProvinces by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StateProvincesDTO> findOne(Long id) {
        log.debug("Request to get StateProvinces : {}", id);
        return stateProvincesRepository.findById(id).map(stateProvincesMapper::toDto);
    }

    /**
     * Delete the stateProvinces by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StateProvinces : {}", id);
        stateProvincesRepository.deleteById(id);
    }
}
