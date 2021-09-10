package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.HousingVehicleItem;
import com.genesisoft.transporte.repository.HousingVehicleItemRepository;
import com.genesisoft.transporte.service.dto.HousingVehicleItemDTO;
import com.genesisoft.transporte.service.mapper.HousingVehicleItemMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HousingVehicleItem}.
 */
@Service
@Transactional
public class HousingVehicleItemService {

    private final Logger log = LoggerFactory.getLogger(HousingVehicleItemService.class);

    private final HousingVehicleItemRepository housingVehicleItemRepository;

    private final HousingVehicleItemMapper housingVehicleItemMapper;

    public HousingVehicleItemService(
        HousingVehicleItemRepository housingVehicleItemRepository,
        HousingVehicleItemMapper housingVehicleItemMapper
    ) {
        this.housingVehicleItemRepository = housingVehicleItemRepository;
        this.housingVehicleItemMapper = housingVehicleItemMapper;
    }

    /**
     * Save a housingVehicleItem.
     *
     * @param housingVehicleItemDTO the entity to save.
     * @return the persisted entity.
     */
    public HousingVehicleItemDTO save(HousingVehicleItemDTO housingVehicleItemDTO) {
        log.debug("Request to save HousingVehicleItem : {}", housingVehicleItemDTO);
        HousingVehicleItem housingVehicleItem = housingVehicleItemMapper.toEntity(housingVehicleItemDTO);
        housingVehicleItem = housingVehicleItemRepository.save(housingVehicleItem);
        return housingVehicleItemMapper.toDto(housingVehicleItem);
    }

    /**
     * Partially update a housingVehicleItem.
     *
     * @param housingVehicleItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HousingVehicleItemDTO> partialUpdate(HousingVehicleItemDTO housingVehicleItemDTO) {
        log.debug("Request to partially update HousingVehicleItem : {}", housingVehicleItemDTO);

        return housingVehicleItemRepository
            .findById(housingVehicleItemDTO.getId())
            .map(
                existingHousingVehicleItem -> {
                    housingVehicleItemMapper.partialUpdate(existingHousingVehicleItem, housingVehicleItemDTO);

                    return existingHousingVehicleItem;
                }
            )
            .map(housingVehicleItemRepository::save)
            .map(housingVehicleItemMapper::toDto);
    }

    /**
     * Get all the housingVehicleItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HousingVehicleItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HousingVehicleItems");
        return housingVehicleItemRepository.findAll(pageable).map(housingVehicleItemMapper::toDto);
    }

    /**
     * Get one housingVehicleItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HousingVehicleItemDTO> findOne(Long id) {
        log.debug("Request to get HousingVehicleItem : {}", id);
        return housingVehicleItemRepository.findById(id).map(housingVehicleItemMapper::toDto);
    }

    /**
     * Delete the housingVehicleItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HousingVehicleItem : {}", id);
        housingVehicleItemRepository.deleteById(id);
    }
}
