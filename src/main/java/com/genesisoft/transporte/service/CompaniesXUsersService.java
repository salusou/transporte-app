package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.CompaniesXUsers;
import com.genesisoft.transporte.repository.CompaniesXUsersRepository;
import com.genesisoft.transporte.service.dto.CompaniesXUsersDTO;
import com.genesisoft.transporte.service.mapper.CompaniesXUsersMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CompaniesXUsers}.
 */
@Service
@Transactional
public class CompaniesXUsersService {

    private final Logger log = LoggerFactory.getLogger(CompaniesXUsersService.class);

    private final CompaniesXUsersRepository companiesXUsersRepository;

    private final CompaniesXUsersMapper companiesXUsersMapper;

    public CompaniesXUsersService(CompaniesXUsersRepository companiesXUsersRepository, CompaniesXUsersMapper companiesXUsersMapper) {
        this.companiesXUsersRepository = companiesXUsersRepository;
        this.companiesXUsersMapper = companiesXUsersMapper;
    }

    /**
     * Save a companiesXUsers.
     *
     * @param companiesXUsersDTO the entity to save.
     * @return the persisted entity.
     */
    public CompaniesXUsersDTO save(CompaniesXUsersDTO companiesXUsersDTO) {
        log.debug("Request to save CompaniesXUsers : {}", companiesXUsersDTO);
        CompaniesXUsers companiesXUsers = companiesXUsersMapper.toEntity(companiesXUsersDTO);
        companiesXUsers = companiesXUsersRepository.save(companiesXUsers);
        return companiesXUsersMapper.toDto(companiesXUsers);
    }

    /**
     * Partially update a companiesXUsers.
     *
     * @param companiesXUsersDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CompaniesXUsersDTO> partialUpdate(CompaniesXUsersDTO companiesXUsersDTO) {
        log.debug("Request to partially update CompaniesXUsers : {}", companiesXUsersDTO);

        return companiesXUsersRepository
            .findById(companiesXUsersDTO.getId())
            .map(
                existingCompaniesXUsers -> {
                    companiesXUsersMapper.partialUpdate(existingCompaniesXUsers, companiesXUsersDTO);

                    return existingCompaniesXUsers;
                }
            )
            .map(companiesXUsersRepository::save)
            .map(companiesXUsersMapper::toDto);
    }

    /**
     * Get all the companiesXUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CompaniesXUsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompaniesXUsers");
        return companiesXUsersRepository.findAll(pageable).map(companiesXUsersMapper::toDto);
    }

    /**
     * Get one companiesXUsers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompaniesXUsersDTO> findOne(Long id) {
        log.debug("Request to get CompaniesXUsers : {}", id);
        return companiesXUsersRepository.findById(id).map(companiesXUsersMapper::toDto);
    }

    /**
     * Delete the companiesXUsers by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CompaniesXUsers : {}", id);
        companiesXUsersRepository.deleteById(id);
    }
}
