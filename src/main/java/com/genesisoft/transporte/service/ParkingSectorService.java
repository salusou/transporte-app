package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.ParkingSector;
import com.genesisoft.transporte.repository.ParkingSectorRepository;
import com.genesisoft.transporte.service.dto.ParkingSectorDTO;
import com.genesisoft.transporte.service.mapper.ParkingSectorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ParkingSector}.
 */
@Service
@Transactional
public class ParkingSectorService {

    private final Logger log = LoggerFactory.getLogger(ParkingSectorService.class);

    private final ParkingSectorRepository parkingSectorRepository;

    private final ParkingSectorMapper parkingSectorMapper;

    public ParkingSectorService(ParkingSectorRepository parkingSectorRepository, ParkingSectorMapper parkingSectorMapper) {
        this.parkingSectorRepository = parkingSectorRepository;
        this.parkingSectorMapper = parkingSectorMapper;
    }

    /**
     * Save a parkingSector.
     *
     * @param parkingSectorDTO the entity to save.
     * @return the persisted entity.
     */
    public ParkingSectorDTO save(ParkingSectorDTO parkingSectorDTO) {
        log.debug("Request to save ParkingSector : {}", parkingSectorDTO);
        ParkingSector parkingSector = parkingSectorMapper.toEntity(parkingSectorDTO);
        parkingSector = parkingSectorRepository.save(parkingSector);
        return parkingSectorMapper.toDto(parkingSector);
    }

    /**
     * Partially update a parkingSector.
     *
     * @param parkingSectorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ParkingSectorDTO> partialUpdate(ParkingSectorDTO parkingSectorDTO) {
        log.debug("Request to partially update ParkingSector : {}", parkingSectorDTO);

        return parkingSectorRepository
            .findById(parkingSectorDTO.getId())
            .map(
                existingParkingSector -> {
                    parkingSectorMapper.partialUpdate(existingParkingSector, parkingSectorDTO);

                    return existingParkingSector;
                }
            )
            .map(parkingSectorRepository::save)
            .map(parkingSectorMapper::toDto);
    }

    /**
     * Get all the parkingSectors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ParkingSectorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParkingSectors");
        return parkingSectorRepository.findAll(pageable).map(parkingSectorMapper::toDto);
    }

    /**
     * Get one parkingSector by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ParkingSectorDTO> findOne(Long id) {
        log.debug("Request to get ParkingSector : {}", id);
        return parkingSectorRepository.findById(id).map(parkingSectorMapper::toDto);
    }

    /**
     * Delete the parkingSector by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ParkingSector : {}", id);
        parkingSectorRepository.deleteById(id);
    }
}
