package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.CustomersGroups;
import com.genesisoft.transporte.repository.CustomersGroupsRepository;
import com.genesisoft.transporte.service.dto.CustomersGroupsDTO;
import com.genesisoft.transporte.service.mapper.CustomersGroupsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustomersGroups}.
 */
@Service
@Transactional
public class CustomersGroupsService {

    private final Logger log = LoggerFactory.getLogger(CustomersGroupsService.class);

    private final CustomersGroupsRepository customersGroupsRepository;

    private final CustomersGroupsMapper customersGroupsMapper;

    public CustomersGroupsService(CustomersGroupsRepository customersGroupsRepository, CustomersGroupsMapper customersGroupsMapper) {
        this.customersGroupsRepository = customersGroupsRepository;
        this.customersGroupsMapper = customersGroupsMapper;
    }

    /**
     * Save a customersGroups.
     *
     * @param customersGroupsDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomersGroupsDTO save(CustomersGroupsDTO customersGroupsDTO) {
        log.debug("Request to save CustomersGroups : {}", customersGroupsDTO);
        CustomersGroups customersGroups = customersGroupsMapper.toEntity(customersGroupsDTO);
        customersGroups = customersGroupsRepository.save(customersGroups);
        return customersGroupsMapper.toDto(customersGroups);
    }

    /**
     * Partially update a customersGroups.
     *
     * @param customersGroupsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CustomersGroupsDTO> partialUpdate(CustomersGroupsDTO customersGroupsDTO) {
        log.debug("Request to partially update CustomersGroups : {}", customersGroupsDTO);

        return customersGroupsRepository
            .findById(customersGroupsDTO.getId())
            .map(
                existingCustomersGroups -> {
                    customersGroupsMapper.partialUpdate(existingCustomersGroups, customersGroupsDTO);

                    return existingCustomersGroups;
                }
            )
            .map(customersGroupsRepository::save)
            .map(customersGroupsMapper::toDto);
    }

    /**
     * Get all the customersGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomersGroupsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CustomersGroups");
        return customersGroupsRepository.findAll(pageable).map(customersGroupsMapper::toDto);
    }

    /**
     * Get one customersGroups by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CustomersGroupsDTO> findOne(Long id) {
        log.debug("Request to get CustomersGroups : {}", id);
        return customersGroupsRepository.findById(id).map(customersGroupsMapper::toDto);
    }

    /**
     * Delete the customersGroups by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CustomersGroups : {}", id);
        customersGroupsRepository.deleteById(id);
    }
}
