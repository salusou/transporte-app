package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.ParkingSectorSpace;
import com.genesisoft.transporte.repository.ParkingSectorSpaceRepository;
import com.genesisoft.transporte.service.dto.ParkingSectorSpaceDTO;
import com.genesisoft.transporte.service.mapper.ParkingSectorSpaceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ParkingSectorSpace}.
 */
@Service
@Transactional
public class ParkingSectorSpaceService {

    private final Logger log = LoggerFactory.getLogger(ParkingSectorSpaceService.class);

    private final ParkingSectorSpaceRepository parkingSectorSpaceRepository;

    private final ParkingSectorSpaceMapper parkingSectorSpaceMapper;

    public ParkingSectorSpaceService(
        ParkingSectorSpaceRepository parkingSectorSpaceRepository,
        ParkingSectorSpaceMapper parkingSectorSpaceMapper
    ) {
        this.parkingSectorSpaceRepository = parkingSectorSpaceRepository;
        this.parkingSectorSpaceMapper = parkingSectorSpaceMapper;
    }

    /**
     * Save a parkingSectorSpace.
     *
     * @param parkingSectorSpaceDTO the entity to save.
     * @return the persisted entity.
     */
    public ParkingSectorSpaceDTO save(ParkingSectorSpaceDTO parkingSectorSpaceDTO) {
        log.debug("Request to save ParkingSectorSpace : {}", parkingSectorSpaceDTO);
        ParkingSectorSpace parkingSectorSpace = parkingSectorSpaceMapper.toEntity(parkingSectorSpaceDTO);
        parkingSectorSpace = parkingSectorSpaceRepository.save(parkingSectorSpace);
        return parkingSectorSpaceMapper.toDto(parkingSectorSpace);
    }

    /**
     * Partially update a parkingSectorSpace.
     *
     * @param parkingSectorSpaceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ParkingSectorSpaceDTO> partialUpdate(ParkingSectorSpaceDTO parkingSectorSpaceDTO) {
        log.debug("Request to partially update ParkingSectorSpace : {}", parkingSectorSpaceDTO);

        return parkingSectorSpaceRepository
            .findById(parkingSectorSpaceDTO.getId())
            .map(
                existingParkingSectorSpace -> {
                    parkingSectorSpaceMapper.partialUpdate(existingParkingSectorSpace, parkingSectorSpaceDTO);

                    return existingParkingSectorSpace;
                }
            )
            .map(parkingSectorSpaceRepository::save)
            .map(parkingSectorSpaceMapper::toDto);
    }

    /**
     * Get all the parkingSectorSpaces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ParkingSectorSpaceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParkingSectorSpaces");
        return parkingSectorSpaceRepository.findAll(pageable).map(parkingSectorSpaceMapper::toDto);
    }

    /**
     * Get one parkingSectorSpace by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ParkingSectorSpaceDTO> findOne(Long id) {
        log.debug("Request to get ParkingSectorSpace : {}", id);
        return parkingSectorSpaceRepository.findById(id).map(parkingSectorSpaceMapper::toDto);
    }

    /**
     * Delete the parkingSectorSpace by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ParkingSectorSpace : {}", id);
        parkingSectorSpaceRepository.deleteById(id);
    }
}
