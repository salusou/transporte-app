package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.VehicleControlExpenses;
import com.genesisoft.transporte.repository.VehicleControlExpensesRepository;
import com.genesisoft.transporte.service.dto.VehicleControlExpensesDTO;
import com.genesisoft.transporte.service.mapper.VehicleControlExpensesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VehicleControlExpenses}.
 */
@Service
@Transactional
public class VehicleControlExpensesService {

    private final Logger log = LoggerFactory.getLogger(VehicleControlExpensesService.class);

    private final VehicleControlExpensesRepository vehicleControlExpensesRepository;

    private final VehicleControlExpensesMapper vehicleControlExpensesMapper;

    public VehicleControlExpensesService(
        VehicleControlExpensesRepository vehicleControlExpensesRepository,
        VehicleControlExpensesMapper vehicleControlExpensesMapper
    ) {
        this.vehicleControlExpensesRepository = vehicleControlExpensesRepository;
        this.vehicleControlExpensesMapper = vehicleControlExpensesMapper;
    }

    /**
     * Save a vehicleControlExpenses.
     *
     * @param vehicleControlExpensesDTO the entity to save.
     * @return the persisted entity.
     */
    public VehicleControlExpensesDTO save(VehicleControlExpensesDTO vehicleControlExpensesDTO) {
        log.debug("Request to save VehicleControlExpenses : {}", vehicleControlExpensesDTO);
        VehicleControlExpenses vehicleControlExpenses = vehicleControlExpensesMapper.toEntity(vehicleControlExpensesDTO);
        vehicleControlExpenses = vehicleControlExpensesRepository.save(vehicleControlExpenses);
        return vehicleControlExpensesMapper.toDto(vehicleControlExpenses);
    }

    /**
     * Partially update a vehicleControlExpenses.
     *
     * @param vehicleControlExpensesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VehicleControlExpensesDTO> partialUpdate(VehicleControlExpensesDTO vehicleControlExpensesDTO) {
        log.debug("Request to partially update VehicleControlExpenses : {}", vehicleControlExpensesDTO);

        return vehicleControlExpensesRepository
            .findById(vehicleControlExpensesDTO.getId())
            .map(
                existingVehicleControlExpenses -> {
                    vehicleControlExpensesMapper.partialUpdate(existingVehicleControlExpenses, vehicleControlExpensesDTO);

                    return existingVehicleControlExpenses;
                }
            )
            .map(vehicleControlExpensesRepository::save)
            .map(vehicleControlExpensesMapper::toDto);
    }

    /**
     * Get all the vehicleControlExpenses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleControlExpensesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VehicleControlExpenses");
        return vehicleControlExpensesRepository.findAll(pageable).map(vehicleControlExpensesMapper::toDto);
    }

    /**
     * Get one vehicleControlExpenses by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VehicleControlExpensesDTO> findOne(Long id) {
        log.debug("Request to get VehicleControlExpenses : {}", id);
        return vehicleControlExpensesRepository.findById(id).map(vehicleControlExpensesMapper::toDto);
    }

    /**
     * Delete the vehicleControlExpenses by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VehicleControlExpenses : {}", id);
        vehicleControlExpensesRepository.deleteById(id);
    }
}
