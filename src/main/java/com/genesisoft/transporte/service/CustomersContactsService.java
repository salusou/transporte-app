package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.CustomersContacts;
import com.genesisoft.transporte.repository.CustomersContactsRepository;
import com.genesisoft.transporte.service.dto.CustomersContactsDTO;
import com.genesisoft.transporte.service.mapper.CustomersContactsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustomersContacts}.
 */
@Service
@Transactional
public class CustomersContactsService {

    private final Logger log = LoggerFactory.getLogger(CustomersContactsService.class);

    private final CustomersContactsRepository customersContactsRepository;

    private final CustomersContactsMapper customersContactsMapper;

    public CustomersContactsService(
        CustomersContactsRepository customersContactsRepository,
        CustomersContactsMapper customersContactsMapper
    ) {
        this.customersContactsRepository = customersContactsRepository;
        this.customersContactsMapper = customersContactsMapper;
    }

    /**
     * Save a customersContacts.
     *
     * @param customersContactsDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomersContactsDTO save(CustomersContactsDTO customersContactsDTO) {
        log.debug("Request to save CustomersContacts : {}", customersContactsDTO);
        CustomersContacts customersContacts = customersContactsMapper.toEntity(customersContactsDTO);
        customersContacts = customersContactsRepository.save(customersContacts);
        return customersContactsMapper.toDto(customersContacts);
    }

    /**
     * Partially update a customersContacts.
     *
     * @param customersContactsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CustomersContactsDTO> partialUpdate(CustomersContactsDTO customersContactsDTO) {
        log.debug("Request to partially update CustomersContacts : {}", customersContactsDTO);

        return customersContactsRepository
            .findById(customersContactsDTO.getId())
            .map(
                existingCustomersContacts -> {
                    customersContactsMapper.partialUpdate(existingCustomersContacts, customersContactsDTO);

                    return existingCustomersContacts;
                }
            )
            .map(customersContactsRepository::save)
            .map(customersContactsMapper::toDto);
    }

    /**
     * Get all the customersContacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomersContactsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CustomersContacts");
        return customersContactsRepository.findAll(pageable).map(customersContactsMapper::toDto);
    }

    /**
     * Get one customersContacts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CustomersContactsDTO> findOne(Long id) {
        log.debug("Request to get CustomersContacts : {}", id);
        return customersContactsRepository.findById(id).map(customersContactsMapper::toDto);
    }

    /**
     * Delete the customersContacts by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CustomersContacts : {}", id);
        customersContactsRepository.deleteById(id);
    }
}
