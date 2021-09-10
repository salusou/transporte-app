package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.VehicleControlItem;
import com.genesisoft.transporte.repository.VehicleControlItemRepository;
import com.genesisoft.transporte.service.dto.VehicleControlItemDTO;
import com.genesisoft.transporte.service.mapper.VehicleControlItemMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VehicleControlItem}.
 */
@Service
@Transactional
public class VehicleControlItemService {

    private final Logger log = LoggerFactory.getLogger(VehicleControlItemService.class);

    private final VehicleControlItemRepository vehicleControlItemRepository;

    private final VehicleControlItemMapper vehicleControlItemMapper;

    public VehicleControlItemService(
        VehicleControlItemRepository vehicleControlItemRepository,
        VehicleControlItemMapper vehicleControlItemMapper
    ) {
        this.vehicleControlItemRepository = vehicleControlItemRepository;
        this.vehicleControlItemMapper = vehicleControlItemMapper;
    }

    /**
     * Save a vehicleControlItem.
     *
     * @param vehicleControlItemDTO the entity to save.
     * @return the persisted entity.
     */
    public VehicleControlItemDTO save(VehicleControlItemDTO vehicleControlItemDTO) {
        log.debug("Request to save VehicleControlItem : {}", vehicleControlItemDTO);
        VehicleControlItem vehicleControlItem = vehicleControlItemMapper.toEntity(vehicleControlItemDTO);
        vehicleControlItem = vehicleControlItemRepository.save(vehicleControlItem);
        return vehicleControlItemMapper.toDto(vehicleControlItem);
    }

    /**
     * Partially update a vehicleControlItem.
     *
     * @param vehicleControlItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VehicleControlItemDTO> partialUpdate(VehicleControlItemDTO vehicleControlItemDTO) {
        log.debug("Request to partially update VehicleControlItem : {}", vehicleControlItemDTO);

        return vehicleControlItemRepository
            .findById(vehicleControlItemDTO.getId())
            .map(
                existingVehicleControlItem -> {
                    vehicleControlItemMapper.partialUpdate(existingVehicleControlItem, vehicleControlItemDTO);

                    return existingVehicleControlItem;
                }
            )
            .map(vehicleControlItemRepository::save)
            .map(vehicleControlItemMapper::toDto);
    }

    /**
     * Get all the vehicleControlItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleControlItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VehicleControlItems");
        return vehicleControlItemRepository.findAll(pageable).map(vehicleControlItemMapper::toDto);
    }

    /**
     * Get one vehicleControlItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VehicleControlItemDTO> findOne(Long id) {
        log.debug("Request to get VehicleControlItem : {}", id);
        return vehicleControlItemRepository.findById(id).map(vehicleControlItemMapper::toDto);
    }

    /**
     * Delete the vehicleControlItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VehicleControlItem : {}", id);
        vehicleControlItemRepository.deleteById(id);
    }
}
