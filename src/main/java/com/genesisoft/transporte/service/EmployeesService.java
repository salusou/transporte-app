package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.Employees;
import com.genesisoft.transporte.repository.EmployeesRepository;
import com.genesisoft.transporte.service.dto.EmployeesDTO;
import com.genesisoft.transporte.service.mapper.EmployeesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Employees}.
 */
@Service
@Transactional
public class EmployeesService {

    private final Logger log = LoggerFactory.getLogger(EmployeesService.class);

    private final EmployeesRepository employeesRepository;

    private final EmployeesMapper employeesMapper;

    public EmployeesService(EmployeesRepository employeesRepository, EmployeesMapper employeesMapper) {
        this.employeesRepository = employeesRepository;
        this.employeesMapper = employeesMapper;
    }

    /**
     * Save a employees.
     *
     * @param employeesDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeesDTO save(EmployeesDTO employeesDTO) {
        log.debug("Request to save Employees : {}", employeesDTO);
        Employees employees = employeesMapper.toEntity(employeesDTO);
        employees = employeesRepository.save(employees);
        return employeesMapper.toDto(employees);
    }

    /**
     * Partially update a employees.
     *
     * @param employeesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeesDTO> partialUpdate(EmployeesDTO employeesDTO) {
        log.debug("Request to partially update Employees : {}", employeesDTO);

        return employeesRepository
            .findById(employeesDTO.getId())
            .map(
                existingEmployees -> {
                    employeesMapper.partialUpdate(existingEmployees, employeesDTO);

                    return existingEmployees;
                }
            )
            .map(employeesRepository::save)
            .map(employeesMapper::toDto);
    }

    /**
     * Get all the employees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return employeesRepository.findAll(pageable).map(employeesMapper::toDto);
    }

    /**
     * Get one employees by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeesDTO> findOne(Long id) {
        log.debug("Request to get Employees : {}", id);
        return employeesRepository.findById(id).map(employeesMapper::toDto);
    }

    /**
     * Delete the employees by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Employees : {}", id);
        employeesRepository.deleteById(id);
    }
}
