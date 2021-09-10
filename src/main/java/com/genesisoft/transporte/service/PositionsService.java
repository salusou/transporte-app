package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.Positions;
import com.genesisoft.transporte.repository.PositionsRepository;
import com.genesisoft.transporte.service.dto.PositionsDTO;
import com.genesisoft.transporte.service.mapper.PositionsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Positions}.
 */
@Service
@Transactional
public class PositionsService {

    private final Logger log = LoggerFactory.getLogger(PositionsService.class);

    private final PositionsRepository positionsRepository;

    private final PositionsMapper positionsMapper;

    public PositionsService(PositionsRepository positionsRepository, PositionsMapper positionsMapper) {
        this.positionsRepository = positionsRepository;
        this.positionsMapper = positionsMapper;
    }

    /**
     * Save a positions.
     *
     * @param positionsDTO the entity to save.
     * @return the persisted entity.
     */
    public PositionsDTO save(PositionsDTO positionsDTO) {
        log.debug("Request to save Positions : {}", positionsDTO);
        Positions positions = positionsMapper.toEntity(positionsDTO);
        positions = positionsRepository.save(positions);
        return positionsMapper.toDto(positions);
    }

    /**
     * Partially update a positions.
     *
     * @param positionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PositionsDTO> partialUpdate(PositionsDTO positionsDTO) {
        log.debug("Request to partially update Positions : {}", positionsDTO);

        return positionsRepository
            .findById(positionsDTO.getId())
            .map(
                existingPositions -> {
                    positionsMapper.partialUpdate(existingPositions, positionsDTO);

                    return existingPositions;
                }
            )
            .map(positionsRepository::save)
            .map(positionsMapper::toDto);
    }

    /**
     * Get all the positions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PositionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Positions");
        return positionsRepository.findAll(pageable).map(positionsMapper::toDto);
    }

    /**
     * Get one positions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PositionsDTO> findOne(Long id) {
        log.debug("Request to get Positions : {}", id);
        return positionsRepository.findById(id).map(positionsMapper::toDto);
    }

    /**
     * Delete the positions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Positions : {}", id);
        positionsRepository.deleteById(id);
    }
}
