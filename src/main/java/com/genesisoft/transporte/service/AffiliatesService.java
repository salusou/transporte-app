package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.Affiliates;
import com.genesisoft.transporte.repository.AffiliatesRepository;
import com.genesisoft.transporte.service.dto.AffiliatesDTO;
import com.genesisoft.transporte.service.mapper.AffiliatesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Affiliates}.
 */
@Service
@Transactional
public class AffiliatesService {

    private final Logger log = LoggerFactory.getLogger(AffiliatesService.class);

    private final AffiliatesRepository affiliatesRepository;

    private final AffiliatesMapper affiliatesMapper;

    public AffiliatesService(AffiliatesRepository affiliatesRepository, AffiliatesMapper affiliatesMapper) {
        this.affiliatesRepository = affiliatesRepository;
        this.affiliatesMapper = affiliatesMapper;
    }

    /**
     * Save a affiliates.
     *
     * @param affiliatesDTO the entity to save.
     * @return the persisted entity.
     */
    public AffiliatesDTO save(AffiliatesDTO affiliatesDTO) {
        log.debug("Request to save Affiliates : {}", affiliatesDTO);
        Affiliates affiliates = affiliatesMapper.toEntity(affiliatesDTO);
        affiliates = affiliatesRepository.save(affiliates);
        return affiliatesMapper.toDto(affiliates);
    }

    /**
     * Partially update a affiliates.
     *
     * @param affiliatesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AffiliatesDTO> partialUpdate(AffiliatesDTO affiliatesDTO) {
        log.debug("Request to partially update Affiliates : {}", affiliatesDTO);

        return affiliatesRepository
            .findById(affiliatesDTO.getId())
            .map(
                existingAffiliates -> {
                    affiliatesMapper.partialUpdate(existingAffiliates, affiliatesDTO);

                    return existingAffiliates;
                }
            )
            .map(affiliatesRepository::save)
            .map(affiliatesMapper::toDto);
    }

    /**
     * Get all the affiliates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AffiliatesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Affiliates");
        return affiliatesRepository.findAll(pageable).map(affiliatesMapper::toDto);
    }

    /**
     * Get one affiliates by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AffiliatesDTO> findOne(Long id) {
        log.debug("Request to get Affiliates : {}", id);
        return affiliatesRepository.findById(id).map(affiliatesMapper::toDto);
    }

    /**
     * Delete the affiliates by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Affiliates : {}", id);
        affiliatesRepository.deleteById(id);
    }
}
