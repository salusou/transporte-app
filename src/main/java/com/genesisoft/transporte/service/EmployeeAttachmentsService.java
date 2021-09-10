package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.EmployeeAttachments;
import com.genesisoft.transporte.repository.EmployeeAttachmentsRepository;
import com.genesisoft.transporte.service.dto.EmployeeAttachmentsDTO;
import com.genesisoft.transporte.service.mapper.EmployeeAttachmentsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeAttachments}.
 */
@Service
@Transactional
public class EmployeeAttachmentsService {

    private final Logger log = LoggerFactory.getLogger(EmployeeAttachmentsService.class);

    private final EmployeeAttachmentsRepository employeeAttachmentsRepository;

    private final EmployeeAttachmentsMapper employeeAttachmentsMapper;

    public EmployeeAttachmentsService(
        EmployeeAttachmentsRepository employeeAttachmentsRepository,
        EmployeeAttachmentsMapper employeeAttachmentsMapper
    ) {
        this.employeeAttachmentsRepository = employeeAttachmentsRepository;
        this.employeeAttachmentsMapper = employeeAttachmentsMapper;
    }

    /**
     * Save a employeeAttachments.
     *
     * @param employeeAttachmentsDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeAttachmentsDTO save(EmployeeAttachmentsDTO employeeAttachmentsDTO) {
        log.debug("Request to save EmployeeAttachments : {}", employeeAttachmentsDTO);
        EmployeeAttachments employeeAttachments = employeeAttachmentsMapper.toEntity(employeeAttachmentsDTO);
        employeeAttachments = employeeAttachmentsRepository.save(employeeAttachments);
        return employeeAttachmentsMapper.toDto(employeeAttachments);
    }

    /**
     * Partially update a employeeAttachments.
     *
     * @param employeeAttachmentsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeAttachmentsDTO> partialUpdate(EmployeeAttachmentsDTO employeeAttachmentsDTO) {
        log.debug("Request to partially update EmployeeAttachments : {}", employeeAttachmentsDTO);

        return employeeAttachmentsRepository
            .findById(employeeAttachmentsDTO.getId())
            .map(
                existingEmployeeAttachments -> {
                    employeeAttachmentsMapper.partialUpdate(existingEmployeeAttachments, employeeAttachmentsDTO);

                    return existingEmployeeAttachments;
                }
            )
            .map(employeeAttachmentsRepository::save)
            .map(employeeAttachmentsMapper::toDto);
    }

    /**
     * Get all the employeeAttachments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeAttachmentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeAttachments");
        return employeeAttachmentsRepository.findAll(pageable).map(employeeAttachmentsMapper::toDto);
    }

    /**
     * Get one employeeAttachments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeAttachmentsDTO> findOne(Long id) {
        log.debug("Request to get EmployeeAttachments : {}", id);
        return employeeAttachmentsRepository.findById(id).map(employeeAttachmentsMapper::toDto);
    }

    /**
     * Delete the employeeAttachments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeAttachments : {}", id);
        employeeAttachmentsRepository.deleteById(id);
    }
}
