package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.VehicleLocationStatus;
import com.genesisoft.transporte.repository.VehicleLocationStatusRepository;
import com.genesisoft.transporte.service.dto.VehicleLocationStatusDTO;
import com.genesisoft.transporte.service.mapper.VehicleLocationStatusMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VehicleLocationStatus}.
 */
@Service
@Transactional
public class VehicleLocationStatusService {

    private final Logger log = LoggerFactory.getLogger(VehicleLocationStatusService.class);

    private final VehicleLocationStatusRepository vehicleLocationStatusRepository;

    private final VehicleLocationStatusMapper vehicleLocationStatusMapper;

    public VehicleLocationStatusService(
        VehicleLocationStatusRepository vehicleLocationStatusRepository,
        VehicleLocationStatusMapper vehicleLocationStatusMapper
    ) {
        this.vehicleLocationStatusRepository = vehicleLocationStatusRepository;
        this.vehicleLocationStatusMapper = vehicleLocationStatusMapper;
    }

    /**
     * Save a vehicleLocationStatus.
     *
     * @param vehicleLocationStatusDTO the entity to save.
     * @return the persisted entity.
     */
    public VehicleLocationStatusDTO save(VehicleLocationStatusDTO vehicleLocationStatusDTO) {
        log.debug("Request to save VehicleLocationStatus : {}", vehicleLocationStatusDTO);
        VehicleLocationStatus vehicleLocationStatus = vehicleLocationStatusMapper.toEntity(vehicleLocationStatusDTO);
        vehicleLocationStatus = vehicleLocationStatusRepository.save(vehicleLocationStatus);
        return vehicleLocationStatusMapper.toDto(vehicleLocationStatus);
    }

    /**
     * Partially update a vehicleLocationStatus.
     *
     * @param vehicleLocationStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VehicleLocationStatusDTO> partialUpdate(VehicleLocationStatusDTO vehicleLocationStatusDTO) {
        log.debug("Request to partially update VehicleLocationStatus : {}", vehicleLocationStatusDTO);

        return vehicleLocationStatusRepository
            .findById(vehicleLocationStatusDTO.getId())
            .map(
                existingVehicleLocationStatus -> {
                    vehicleLocationStatusMapper.partialUpdate(existingVehicleLocationStatus, vehicleLocationStatusDTO);

                    return existingVehicleLocationStatus;
                }
            )
            .map(vehicleLocationStatusRepository::save)
            .map(vehicleLocationStatusMapper::toDto);
    }

    /**
     * Get all the vehicleLocationStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleLocationStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VehicleLocationStatuses");
        return vehicleLocationStatusRepository.findAll(pageable).map(vehicleLocationStatusMapper::toDto);
    }

    /**
     * Get one vehicleLocationStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VehicleLocationStatusDTO> findOne(Long id) {
        log.debug("Request to get VehicleLocationStatus : {}", id);
        return vehicleLocationStatusRepository.findById(id).map(vehicleLocationStatusMapper::toDto);
    }

    /**
     * Delete the vehicleLocationStatus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VehicleLocationStatus : {}", id);
        vehicleLocationStatusRepository.deleteById(id);
    }
}
