package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.SupplierContacts;
import com.genesisoft.transporte.repository.SupplierContactsRepository;
import com.genesisoft.transporte.service.dto.SupplierContactsDTO;
import com.genesisoft.transporte.service.mapper.SupplierContactsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SupplierContacts}.
 */
@Service
@Transactional
public class SupplierContactsService {

    private final Logger log = LoggerFactory.getLogger(SupplierContactsService.class);

    private final SupplierContactsRepository supplierContactsRepository;

    private final SupplierContactsMapper supplierContactsMapper;

    public SupplierContactsService(SupplierContactsRepository supplierContactsRepository, SupplierContactsMapper supplierContactsMapper) {
        this.supplierContactsRepository = supplierContactsRepository;
        this.supplierContactsMapper = supplierContactsMapper;
    }

    /**
     * Save a supplierContacts.
     *
     * @param supplierContactsDTO the entity to save.
     * @return the persisted entity.
     */
    public SupplierContactsDTO save(SupplierContactsDTO supplierContactsDTO) {
        log.debug("Request to save SupplierContacts : {}", supplierContactsDTO);
        SupplierContacts supplierContacts = supplierContactsMapper.toEntity(supplierContactsDTO);
        supplierContacts = supplierContactsRepository.save(supplierContacts);
        return supplierContactsMapper.toDto(supplierContacts);
    }

    /**
     * Partially update a supplierContacts.
     *
     * @param supplierContactsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SupplierContactsDTO> partialUpdate(SupplierContactsDTO supplierContactsDTO) {
        log.debug("Request to partially update SupplierContacts : {}", supplierContactsDTO);

        return supplierContactsRepository
            .findById(supplierContactsDTO.getId())
            .map(
                existingSupplierContacts -> {
                    supplierContactsMapper.partialUpdate(existingSupplierContacts, supplierContactsDTO);

                    return existingSupplierContacts;
                }
            )
            .map(supplierContactsRepository::save)
            .map(supplierContactsMapper::toDto);
    }

    /**
     * Get all the supplierContacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplierContactsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupplierContacts");
        return supplierContactsRepository.findAll(pageable).map(supplierContactsMapper::toDto);
    }

    /**
     * Get one supplierContacts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SupplierContactsDTO> findOne(Long id) {
        log.debug("Request to get SupplierContacts : {}", id);
        return supplierContactsRepository.findById(id).map(supplierContactsMapper::toDto);
    }

    /**
     * Delete the supplierContacts by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SupplierContacts : {}", id);
        supplierContactsRepository.deleteById(id);
    }
}
