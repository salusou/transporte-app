package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.VehicleControlHistory;
import com.genesisoft.transporte.repository.VehicleControlHistoryRepository;
import com.genesisoft.transporte.service.dto.VehicleControlHistoryDTO;
import com.genesisoft.transporte.service.mapper.VehicleControlHistoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VehicleControlHistory}.
 */
@Service
@Transactional
public class VehicleControlHistoryService {

    private final Logger log = LoggerFactory.getLogger(VehicleControlHistoryService.class);

    private final VehicleControlHistoryRepository vehicleControlHistoryRepository;

    private final VehicleControlHistoryMapper vehicleControlHistoryMapper;

    public VehicleControlHistoryService(
        VehicleControlHistoryRepository vehicleControlHistoryRepository,
        VehicleControlHistoryMapper vehicleControlHistoryMapper
    ) {
        this.vehicleControlHistoryRepository = vehicleControlHistoryRepository;
        this.vehicleControlHistoryMapper = vehicleControlHistoryMapper;
    }

    /**
     * Save a vehicleControlHistory.
     *
     * @param vehicleControlHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public VehicleControlHistoryDTO save(VehicleControlHistoryDTO vehicleControlHistoryDTO) {
        log.debug("Request to save VehicleControlHistory : {}", vehicleControlHistoryDTO);
        VehicleControlHistory vehicleControlHistory = vehicleControlHistoryMapper.toEntity(vehicleControlHistoryDTO);
        vehicleControlHistory = vehicleControlHistoryRepository.save(vehicleControlHistory);
        return vehicleControlHistoryMapper.toDto(vehicleControlHistory);
    }

    /**
     * Partially update a vehicleControlHistory.
     *
     * @param vehicleControlHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VehicleControlHistoryDTO> partialUpdate(VehicleControlHistoryDTO vehicleControlHistoryDTO) {
        log.debug("Request to partially update VehicleControlHistory : {}", vehicleControlHistoryDTO);

        return vehicleControlHistoryRepository
            .findById(vehicleControlHistoryDTO.getId())
            .map(
                existingVehicleControlHistory -> {
                    vehicleControlHistoryMapper.partialUpdate(existingVehicleControlHistory, vehicleControlHistoryDTO);

                    return existingVehicleControlHistory;
                }
            )
            .map(vehicleControlHistoryRepository::save)
            .map(vehicleControlHistoryMapper::toDto);
    }

    /**
     * Get all the vehicleControlHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleControlHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VehicleControlHistories");
        return vehicleControlHistoryRepository.findAll(pageable).map(vehicleControlHistoryMapper::toDto);
    }

    /**
     * Get one vehicleControlHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VehicleControlHistoryDTO> findOne(Long id) {
        log.debug("Request to get VehicleControlHistory : {}", id);
        return vehicleControlHistoryRepository.findById(id).map(vehicleControlHistoryMapper::toDto);
    }

    /**
     * Delete the vehicleControlHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VehicleControlHistory : {}", id);
        vehicleControlHistoryRepository.deleteById(id);
    }
}
