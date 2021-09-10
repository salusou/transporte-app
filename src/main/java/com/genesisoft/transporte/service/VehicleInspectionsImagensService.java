package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.VehicleInspectionsImagens;
import com.genesisoft.transporte.repository.VehicleInspectionsImagensRepository;
import com.genesisoft.transporte.service.dto.VehicleInspectionsImagensDTO;
import com.genesisoft.transporte.service.mapper.VehicleInspectionsImagensMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VehicleInspectionsImagens}.
 */
@Service
@Transactional
public class VehicleInspectionsImagensService {

    private final Logger log = LoggerFactory.getLogger(VehicleInspectionsImagensService.class);

    private final VehicleInspectionsImagensRepository vehicleInspectionsImagensRepository;

    private final VehicleInspectionsImagensMapper vehicleInspectionsImagensMapper;

    public VehicleInspectionsImagensService(
        VehicleInspectionsImagensRepository vehicleInspectionsImagensRepository,
        VehicleInspectionsImagensMapper vehicleInspectionsImagensMapper
    ) {
        this.vehicleInspectionsImagensRepository = vehicleInspectionsImagensRepository;
        this.vehicleInspectionsImagensMapper = vehicleInspectionsImagensMapper;
    }

    /**
     * Save a vehicleInspectionsImagens.
     *
     * @param vehicleInspectionsImagensDTO the entity to save.
     * @return the persisted entity.
     */
    public VehicleInspectionsImagensDTO save(VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO) {
        log.debug("Request to save VehicleInspectionsImagens : {}", vehicleInspectionsImagensDTO);
        VehicleInspectionsImagens vehicleInspectionsImagens = vehicleInspectionsImagensMapper.toEntity(vehicleInspectionsImagensDTO);
        vehicleInspectionsImagens = vehicleInspectionsImagensRepository.save(vehicleInspectionsImagens);
        return vehicleInspectionsImagensMapper.toDto(vehicleInspectionsImagens);
    }

    /**
     * Partially update a vehicleInspectionsImagens.
     *
     * @param vehicleInspectionsImagensDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VehicleInspectionsImagensDTO> partialUpdate(VehicleInspectionsImagensDTO vehicleInspectionsImagensDTO) {
        log.debug("Request to partially update VehicleInspectionsImagens : {}", vehicleInspectionsImagensDTO);

        return vehicleInspectionsImagensRepository
            .findById(vehicleInspectionsImagensDTO.getId())
            .map(
                existingVehicleInspectionsImagens -> {
                    vehicleInspectionsImagensMapper.partialUpdate(existingVehicleInspectionsImagens, vehicleInspectionsImagensDTO);

                    return existingVehicleInspectionsImagens;
                }
            )
            .map(vehicleInspectionsImagensRepository::save)
            .map(vehicleInspectionsImagensMapper::toDto);
    }

    /**
     * Get all the vehicleInspectionsImagens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleInspectionsImagensDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VehicleInspectionsImagens");
        return vehicleInspectionsImagensRepository.findAll(pageable).map(vehicleInspectionsImagensMapper::toDto);
    }

    /**
     * Get one vehicleInspectionsImagens by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VehicleInspectionsImagensDTO> findOne(Long id) {
        log.debug("Request to get VehicleInspectionsImagens : {}", id);
        return vehicleInspectionsImagensRepository.findById(id).map(vehicleInspectionsImagensMapper::toDto);
    }

    /**
     * Delete the vehicleInspectionsImagens by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VehicleInspectionsImagens : {}", id);
        vehicleInspectionsImagensRepository.deleteById(id);
    }
}
