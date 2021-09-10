package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.Suppliers;
import com.genesisoft.transporte.repository.SuppliersRepository;
import com.genesisoft.transporte.service.dto.SuppliersDTO;
import com.genesisoft.transporte.service.mapper.SuppliersMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Suppliers}.
 */
@Service
@Transactional
public class SuppliersService {

    private final Logger log = LoggerFactory.getLogger(SuppliersService.class);

    private final SuppliersRepository suppliersRepository;

    private final SuppliersMapper suppliersMapper;

    public SuppliersService(SuppliersRepository suppliersRepository, SuppliersMapper suppliersMapper) {
        this.suppliersRepository = suppliersRepository;
        this.suppliersMapper = suppliersMapper;
    }

    /**
     * Save a suppliers.
     *
     * @param suppliersDTO the entity to save.
     * @return the persisted entity.
     */
    public SuppliersDTO save(SuppliersDTO suppliersDTO) {
        log.debug("Request to save Suppliers : {}", suppliersDTO);
        Suppliers suppliers = suppliersMapper.toEntity(suppliersDTO);
        suppliers = suppliersRepository.save(suppliers);
        return suppliersMapper.toDto(suppliers);
    }

    /**
     * Partially update a suppliers.
     *
     * @param suppliersDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SuppliersDTO> partialUpdate(SuppliersDTO suppliersDTO) {
        log.debug("Request to partially update Suppliers : {}", suppliersDTO);

        return suppliersRepository
            .findById(suppliersDTO.getId())
            .map(
                existingSuppliers -> {
                    suppliersMapper.partialUpdate(existingSuppliers, suppliersDTO);

                    return existingSuppliers;
                }
            )
            .map(suppliersRepository::save)
            .map(suppliersMapper::toDto);
    }

    /**
     * Get all the suppliers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SuppliersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Suppliers");
        return suppliersRepository.findAll(pageable).map(suppliersMapper::toDto);
    }

    /**
     * Get one suppliers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SuppliersDTO> findOne(Long id) {
        log.debug("Request to get Suppliers : {}", id);
        return suppliersRepository.findById(id).map(suppliersMapper::toDto);
    }

    /**
     * Delete the suppliers by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Suppliers : {}", id);
        suppliersRepository.deleteById(id);
    }
}
