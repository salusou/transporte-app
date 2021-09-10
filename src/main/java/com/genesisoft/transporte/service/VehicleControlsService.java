package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.VehicleControls;
import com.genesisoft.transporte.repository.VehicleControlsRepository;
import com.genesisoft.transporte.service.dto.VehicleControlsDTO;
import com.genesisoft.transporte.service.mapper.VehicleControlsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VehicleControls}.
 */
@Service
@Transactional
public class VehicleControlsService {

    private final Logger log = LoggerFactory.getLogger(VehicleControlsService.class);

    private final VehicleControlsRepository vehicleControlsRepository;

    private final VehicleControlsMapper vehicleControlsMapper;

    public VehicleControlsService(VehicleControlsRepository vehicleControlsRepository, VehicleControlsMapper vehicleControlsMapper) {
        this.vehicleControlsRepository = vehicleControlsRepository;
        this.vehicleControlsMapper = vehicleControlsMapper;
    }

    /**
     * Save a vehicleControls.
     *
     * @param vehicleControlsDTO the entity to save.
     * @return the persisted entity.
     */
    public VehicleControlsDTO save(VehicleControlsDTO vehicleControlsDTO) {
        log.debug("Request to save VehicleControls : {}", vehicleControlsDTO);
        VehicleControls vehicleControls = vehicleControlsMapper.toEntity(vehicleControlsDTO);
        vehicleControls = vehicleControlsRepository.save(vehicleControls);
        return vehicleControlsMapper.toDto(vehicleControls);
    }

    /**
     * Partially update a vehicleControls.
     *
     * @param vehicleControlsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VehicleControlsDTO> partialUpdate(VehicleControlsDTO vehicleControlsDTO) {
        log.debug("Request to partially update VehicleControls : {}", vehicleControlsDTO);

        return vehicleControlsRepository
            .findById(vehicleControlsDTO.getId())
            .map(
                existingVehicleControls -> {
                    vehicleControlsMapper.partialUpdate(existingVehicleControls, vehicleControlsDTO);

                    return existingVehicleControls;
                }
            )
            .map(vehicleControlsRepository::save)
            .map(vehicleControlsMapper::toDto);
    }

    /**
     * Get all the vehicleControls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleControlsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VehicleControls");
        return vehicleControlsRepository.findAll(pageable).map(vehicleControlsMapper::toDto);
    }

    /**
     * Get one vehicleControls by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VehicleControlsDTO> findOne(Long id) {
        log.debug("Request to get VehicleControls : {}", id);
        return vehicleControlsRepository.findById(id).map(vehicleControlsMapper::toDto);
    }

    /**
     * Delete the vehicleControls by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VehicleControls : {}", id);
        vehicleControlsRepository.deleteById(id);
    }
}
