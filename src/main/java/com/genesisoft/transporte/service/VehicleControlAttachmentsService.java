package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.VehicleControlAttachments;
import com.genesisoft.transporte.repository.VehicleControlAttachmentsRepository;
import com.genesisoft.transporte.service.dto.VehicleControlAttachmentsDTO;
import com.genesisoft.transporte.service.mapper.VehicleControlAttachmentsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VehicleControlAttachments}.
 */
@Service
@Transactional
public class VehicleControlAttachmentsService {

    private final Logger log = LoggerFactory.getLogger(VehicleControlAttachmentsService.class);

    private final VehicleControlAttachmentsRepository vehicleControlAttachmentsRepository;

    private final VehicleControlAttachmentsMapper vehicleControlAttachmentsMapper;

    public VehicleControlAttachmentsService(
        VehicleControlAttachmentsRepository vehicleControlAttachmentsRepository,
        VehicleControlAttachmentsMapper vehicleControlAttachmentsMapper
    ) {
        this.vehicleControlAttachmentsRepository = vehicleControlAttachmentsRepository;
        this.vehicleControlAttachmentsMapper = vehicleControlAttachmentsMapper;
    }

    /**
     * Save a vehicleControlAttachments.
     *
     * @param vehicleControlAttachmentsDTO the entity to save.
     * @return the persisted entity.
     */
    public VehicleControlAttachmentsDTO save(VehicleControlAttachmentsDTO vehicleControlAttachmentsDTO) {
        log.debug("Request to save VehicleControlAttachments : {}", vehicleControlAttachmentsDTO);
        VehicleControlAttachments vehicleControlAttachments = vehicleControlAttachmentsMapper.toEntity(vehicleControlAttachmentsDTO);
        vehicleControlAttachments = vehicleControlAttachmentsRepository.save(vehicleControlAttachments);
        return vehicleControlAttachmentsMapper.toDto(vehicleControlAttachments);
    }

    /**
     * Partially update a vehicleControlAttachments.
     *
     * @param vehicleControlAttachmentsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VehicleControlAttachmentsDTO> partialUpdate(VehicleControlAttachmentsDTO vehicleControlAttachmentsDTO) {
        log.debug("Request to partially update VehicleControlAttachments : {}", vehicleControlAttachmentsDTO);

        return vehicleControlAttachmentsRepository
            .findById(vehicleControlAttachmentsDTO.getId())
            .map(
                existingVehicleControlAttachments -> {
                    vehicleControlAttachmentsMapper.partialUpdate(existingVehicleControlAttachments, vehicleControlAttachmentsDTO);

                    return existingVehicleControlAttachments;
                }
            )
            .map(vehicleControlAttachmentsRepository::save)
            .map(vehicleControlAttachmentsMapper::toDto);
    }

    /**
     * Get all the vehicleControlAttachments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleControlAttachmentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VehicleControlAttachments");
        return vehicleControlAttachmentsRepository.findAll(pageable).map(vehicleControlAttachmentsMapper::toDto);
    }

    /**
     * Get one vehicleControlAttachments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VehicleControlAttachmentsDTO> findOne(Long id) {
        log.debug("Request to get VehicleControlAttachments : {}", id);
        return vehicleControlAttachmentsRepository.findById(id).map(vehicleControlAttachmentsMapper::toDto);
    }

    /**
     * Delete the vehicleControlAttachments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VehicleControlAttachments : {}", id);
        vehicleControlAttachmentsRepository.deleteById(id);
    }
}
