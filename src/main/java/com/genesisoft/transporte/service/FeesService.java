package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.Fees;
import com.genesisoft.transporte.repository.FeesRepository;
import com.genesisoft.transporte.service.dto.FeesDTO;
import com.genesisoft.transporte.service.mapper.FeesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Fees}.
 */
@Service
@Transactional
public class FeesService {

    private final Logger log = LoggerFactory.getLogger(FeesService.class);

    private final FeesRepository feesRepository;

    private final FeesMapper feesMapper;

    public FeesService(FeesRepository feesRepository, FeesMapper feesMapper) {
        this.feesRepository = feesRepository;
        this.feesMapper = feesMapper;
    }

    /**
     * Save a fees.
     *
     * @param feesDTO the entity to save.
     * @return the persisted entity.
     */
    public FeesDTO save(FeesDTO feesDTO) {
        log.debug("Request to save Fees : {}", feesDTO);
        Fees fees = feesMapper.toEntity(feesDTO);
        fees = feesRepository.save(fees);
        return feesMapper.toDto(fees);
    }

    /**
     * Partially update a fees.
     *
     * @param feesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FeesDTO> partialUpdate(FeesDTO feesDTO) {
        log.debug("Request to partially update Fees : {}", feesDTO);

        return feesRepository
            .findById(feesDTO.getId())
            .map(
                existingFees -> {
                    feesMapper.partialUpdate(existingFees, feesDTO);

                    return existingFees;
                }
            )
            .map(feesRepository::save)
            .map(feesMapper::toDto);
    }

    /**
     * Get all the fees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FeesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fees");
        return feesRepository.findAll(pageable).map(feesMapper::toDto);
    }

    /**
     * Get one fees by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FeesDTO> findOne(Long id) {
        log.debug("Request to get Fees : {}", id);
        return feesRepository.findById(id).map(feesMapper::toDto);
    }

    /**
     * Delete the fees by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Fees : {}", id);
        feesRepository.deleteById(id);
    }
}
