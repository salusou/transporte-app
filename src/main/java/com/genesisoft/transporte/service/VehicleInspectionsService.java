package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.VehicleInspections;
import com.genesisoft.transporte.repository.VehicleInspectionsRepository;
import com.genesisoft.transporte.service.dto.VehicleInspectionsDTO;
import com.genesisoft.transporte.service.mapper.VehicleInspectionsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VehicleInspections}.
 */
@Service
@Transactional
public class VehicleInspectionsService {

    private final Logger log = LoggerFactory.getLogger(VehicleInspectionsService.class);

    private final VehicleInspectionsRepository vehicleInspectionsRepository;

    private final VehicleInspectionsMapper vehicleInspectionsMapper;

    public VehicleInspectionsService(
        VehicleInspectionsRepository vehicleInspectionsRepository,
        VehicleInspectionsMapper vehicleInspectionsMapper
    ) {
        this.vehicleInspectionsRepository = vehicleInspectionsRepository;
        this.vehicleInspectionsMapper = vehicleInspectionsMapper;
    }

    /**
     * Save a vehicleInspections.
     *
     * @param vehicleInspectionsDTO the entity to save.
     * @return the persisted entity.
     */
    public VehicleInspectionsDTO save(VehicleInspectionsDTO vehicleInspectionsDTO) {
        log.debug("Request to save VehicleInspections : {}", vehicleInspectionsDTO);
        VehicleInspections vehicleInspections = vehicleInspectionsMapper.toEntity(vehicleInspectionsDTO);
        vehicleInspections = vehicleInspectionsRepository.save(vehicleInspections);
        return vehicleInspectionsMapper.toDto(vehicleInspections);
    }

    /**
     * Partially update a vehicleInspections.
     *
     * @param vehicleInspectionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VehicleInspectionsDTO> partialUpdate(VehicleInspectionsDTO vehicleInspectionsDTO) {
        log.debug("Request to partially update VehicleInspections : {}", vehicleInspectionsDTO);

        return vehicleInspectionsRepository
            .findById(vehicleInspectionsDTO.getId())
            .map(
                existingVehicleInspections -> {
                    vehicleInspectionsMapper.partialUpdate(existingVehicleInspections, vehicleInspectionsDTO);

                    return existingVehicleInspections;
                }
            )
            .map(vehicleInspectionsRepository::save)
            .map(vehicleInspectionsMapper::toDto);
    }

    /**
     * Get all the vehicleInspections.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleInspectionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VehicleInspections");
        return vehicleInspectionsRepository.findAll(pageable).map(vehicleInspectionsMapper::toDto);
    }

    /**
     * Get one vehicleInspections by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VehicleInspectionsDTO> findOne(Long id) {
        log.debug("Request to get VehicleInspections : {}", id);
        return vehicleInspectionsRepository.findById(id).map(vehicleInspectionsMapper::toDto);
    }

    /**
     * Delete the vehicleInspections by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VehicleInspections : {}", id);
        vehicleInspectionsRepository.deleteById(id);
    }
}
