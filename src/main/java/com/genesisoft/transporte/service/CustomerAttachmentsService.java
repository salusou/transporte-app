package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.CustomerAttachments;
import com.genesisoft.transporte.repository.CustomerAttachmentsRepository;
import com.genesisoft.transporte.service.dto.CustomerAttachmentsDTO;
import com.genesisoft.transporte.service.mapper.CustomerAttachmentsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustomerAttachments}.
 */
@Service
@Transactional
public class CustomerAttachmentsService {

    private final Logger log = LoggerFactory.getLogger(CustomerAttachmentsService.class);

    private final CustomerAttachmentsRepository customerAttachmentsRepository;

    private final CustomerAttachmentsMapper customerAttachmentsMapper;

    public CustomerAttachmentsService(
        CustomerAttachmentsRepository customerAttachmentsRepository,
        CustomerAttachmentsMapper customerAttachmentsMapper
    ) {
        this.customerAttachmentsRepository = customerAttachmentsRepository;
        this.customerAttachmentsMapper = customerAttachmentsMapper;
    }

    /**
     * Save a customerAttachments.
     *
     * @param customerAttachmentsDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomerAttachmentsDTO save(CustomerAttachmentsDTO customerAttachmentsDTO) {
        log.debug("Request to save CustomerAttachments : {}", customerAttachmentsDTO);
        CustomerAttachments customerAttachments = customerAttachmentsMapper.toEntity(customerAttachmentsDTO);
        customerAttachments = customerAttachmentsRepository.save(customerAttachments);
        return customerAttachmentsMapper.toDto(customerAttachments);
    }

    /**
     * Partially update a customerAttachments.
     *
     * @param customerAttachmentsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CustomerAttachmentsDTO> partialUpdate(CustomerAttachmentsDTO customerAttachmentsDTO) {
        log.debug("Request to partially update CustomerAttachments : {}", customerAttachmentsDTO);

        return customerAttachmentsRepository
            .findById(customerAttachmentsDTO.getId())
            .map(
                existingCustomerAttachments -> {
                    customerAttachmentsMapper.partialUpdate(existingCustomerAttachments, customerAttachmentsDTO);

                    return existingCustomerAttachments;
                }
            )
            .map(customerAttachmentsRepository::save)
            .map(customerAttachmentsMapper::toDto);
    }

    /**
     * Get all the customerAttachments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerAttachmentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerAttachments");
        return customerAttachmentsRepository.findAll(pageable).map(customerAttachmentsMapper::toDto);
    }

    /**
     * Get one customerAttachments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CustomerAttachmentsDTO> findOne(Long id) {
        log.debug("Request to get CustomerAttachments : {}", id);
        return customerAttachmentsRepository.findById(id).map(customerAttachmentsMapper::toDto);
    }

    /**
     * Delete the customerAttachments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CustomerAttachments : {}", id);
        customerAttachmentsRepository.deleteById(id);
    }
}
