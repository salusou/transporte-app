package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.Insurances;
import com.genesisoft.transporte.repository.InsurancesRepository;
import com.genesisoft.transporte.service.dto.InsurancesDTO;
import com.genesisoft.transporte.service.mapper.InsurancesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Insurances}.
 */
@Service
@Transactional
public class InsurancesService {

    private final Logger log = LoggerFactory.getLogger(InsurancesService.class);

    private final InsurancesRepository insurancesRepository;

    private final InsurancesMapper insurancesMapper;

    public InsurancesService(InsurancesRepository insurancesRepository, InsurancesMapper insurancesMapper) {
        this.insurancesRepository = insurancesRepository;
        this.insurancesMapper = insurancesMapper;
    }

    /**
     * Save a insurances.
     *
     * @param insurancesDTO the entity to save.
     * @return the persisted entity.
     */
    public InsurancesDTO save(InsurancesDTO insurancesDTO) {
        log.debug("Request to save Insurances : {}", insurancesDTO);
        Insurances insurances = insurancesMapper.toEntity(insurancesDTO);
        insurances = insurancesRepository.save(insurances);
        return insurancesMapper.toDto(insurances);
    }

    /**
     * Partially update a insurances.
     *
     * @param insurancesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InsurancesDTO> partialUpdate(InsurancesDTO insurancesDTO) {
        log.debug("Request to partially update Insurances : {}", insurancesDTO);

        return insurancesRepository
            .findById(insurancesDTO.getId())
            .map(
                existingInsurances -> {
                    insurancesMapper.partialUpdate(existingInsurances, insurancesDTO);

                    return existingInsurances;
                }
            )
            .map(insurancesRepository::save)
            .map(insurancesMapper::toDto);
    }

    /**
     * Get all the insurances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InsurancesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Insurances");
        return insurancesRepository.findAll(pageable).map(insurancesMapper::toDto);
    }

    /**
     * Get one insurances by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InsurancesDTO> findOne(Long id) {
        log.debug("Request to get Insurances : {}", id);
        return insurancesRepository.findById(id).map(insurancesMapper::toDto);
    }

    /**
     * Delete the insurances by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Insurances : {}", id);
        insurancesRepository.deleteById(id);
    }
}
