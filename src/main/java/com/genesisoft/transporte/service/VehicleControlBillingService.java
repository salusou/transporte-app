package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.VehicleControlBilling;
import com.genesisoft.transporte.repository.VehicleControlBillingRepository;
import com.genesisoft.transporte.service.dto.VehicleControlBillingDTO;
import com.genesisoft.transporte.service.mapper.VehicleControlBillingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VehicleControlBilling}.
 */
@Service
@Transactional
public class VehicleControlBillingService {

    private final Logger log = LoggerFactory.getLogger(VehicleControlBillingService.class);

    private final VehicleControlBillingRepository vehicleControlBillingRepository;

    private final VehicleControlBillingMapper vehicleControlBillingMapper;

    public VehicleControlBillingService(
        VehicleControlBillingRepository vehicleControlBillingRepository,
        VehicleControlBillingMapper vehicleControlBillingMapper
    ) {
        this.vehicleControlBillingRepository = vehicleControlBillingRepository;
        this.vehicleControlBillingMapper = vehicleControlBillingMapper;
    }

    /**
     * Save a vehicleControlBilling.
     *
     * @param vehicleControlBillingDTO the entity to save.
     * @return the persisted entity.
     */
    public VehicleControlBillingDTO save(VehicleControlBillingDTO vehicleControlBillingDTO) {
        log.debug("Request to save VehicleControlBilling : {}", vehicleControlBillingDTO);
        VehicleControlBilling vehicleControlBilling = vehicleControlBillingMapper.toEntity(vehicleControlBillingDTO);
        vehicleControlBilling = vehicleControlBillingRepository.save(vehicleControlBilling);
        return vehicleControlBillingMapper.toDto(vehicleControlBilling);
    }

    /**
     * Partially update a vehicleControlBilling.
     *
     * @param vehicleControlBillingDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VehicleControlBillingDTO> partialUpdate(VehicleControlBillingDTO vehicleControlBillingDTO) {
        log.debug("Request to partially update VehicleControlBilling : {}", vehicleControlBillingDTO);

        return vehicleControlBillingRepository
            .findById(vehicleControlBillingDTO.getId())
            .map(
                existingVehicleControlBilling -> {
                    vehicleControlBillingMapper.partialUpdate(existingVehicleControlBilling, vehicleControlBillingDTO);

                    return existingVehicleControlBilling;
                }
            )
            .map(vehicleControlBillingRepository::save)
            .map(vehicleControlBillingMapper::toDto);
    }

    /**
     * Get all the vehicleControlBillings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleControlBillingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VehicleControlBillings");
        return vehicleControlBillingRepository.findAll(pageable).map(vehicleControlBillingMapper::toDto);
    }

    /**
     * Get one vehicleControlBilling by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VehicleControlBillingDTO> findOne(Long id) {
        log.debug("Request to get VehicleControlBilling : {}", id);
        return vehicleControlBillingRepository.findById(id).map(vehicleControlBillingMapper::toDto);
    }

    /**
     * Delete the vehicleControlBilling by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VehicleControlBilling : {}", id);
        vehicleControlBillingRepository.deleteById(id);
    }
}
