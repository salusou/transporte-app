package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.EmployeeComponentsData;
import com.genesisoft.transporte.repository.EmployeeComponentsDataRepository;
import com.genesisoft.transporte.service.dto.EmployeeComponentsDataDTO;
import com.genesisoft.transporte.service.mapper.EmployeeComponentsDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeComponentsData}.
 */
@Service
@Transactional
public class EmployeeComponentsDataService {

    private final Logger log = LoggerFactory.getLogger(EmployeeComponentsDataService.class);

    private final EmployeeComponentsDataRepository employeeComponentsDataRepository;

    private final EmployeeComponentsDataMapper employeeComponentsDataMapper;

    public EmployeeComponentsDataService(
        EmployeeComponentsDataRepository employeeComponentsDataRepository,
        EmployeeComponentsDataMapper employeeComponentsDataMapper
    ) {
        this.employeeComponentsDataRepository = employeeComponentsDataRepository;
        this.employeeComponentsDataMapper = employeeComponentsDataMapper;
    }

    /**
     * Save a employeeComponentsData.
     *
     * @param employeeComponentsDataDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeComponentsDataDTO save(EmployeeComponentsDataDTO employeeComponentsDataDTO) {
        log.debug("Request to save EmployeeComponentsData : {}", employeeComponentsDataDTO);
        EmployeeComponentsData employeeComponentsData = employeeComponentsDataMapper.toEntity(employeeComponentsDataDTO);
        employeeComponentsData = employeeComponentsDataRepository.save(employeeComponentsData);
        return employeeComponentsDataMapper.toDto(employeeComponentsData);
    }

    /**
     * Partially update a employeeComponentsData.
     *
     * @param employeeComponentsDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeComponentsDataDTO> partialUpdate(EmployeeComponentsDataDTO employeeComponentsDataDTO) {
        log.debug("Request to partially update EmployeeComponentsData : {}", employeeComponentsDataDTO);

        return employeeComponentsDataRepository
            .findById(employeeComponentsDataDTO.getId())
            .map(
                existingEmployeeComponentsData -> {
                    employeeComponentsDataMapper.partialUpdate(existingEmployeeComponentsData, employeeComponentsDataDTO);

                    return existingEmployeeComponentsData;
                }
            )
            .map(employeeComponentsDataRepository::save)
            .map(employeeComponentsDataMapper::toDto);
    }

    /**
     * Get all the employeeComponentsData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeComponentsDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeComponentsData");
        return employeeComponentsDataRepository.findAll(pageable).map(employeeComponentsDataMapper::toDto);
    }

    /**
     * Get one employeeComponentsData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeComponentsDataDTO> findOne(Long id) {
        log.debug("Request to get EmployeeComponentsData : {}", id);
        return employeeComponentsDataRepository.findById(id).map(employeeComponentsDataMapper::toDto);
    }

    /**
     * Delete the employeeComponentsData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeComponentsData : {}", id);
        employeeComponentsDataRepository.deleteById(id);
    }
}
