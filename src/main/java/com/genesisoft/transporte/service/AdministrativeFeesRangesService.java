package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.AdministrativeFeesRanges;
import com.genesisoft.transporte.repository.AdministrativeFeesRangesRepository;
import com.genesisoft.transporte.service.dto.AdministrativeFeesRangesDTO;
import com.genesisoft.transporte.service.mapper.AdministrativeFeesRangesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdministrativeFeesRanges}.
 */
@Service
@Transactional
public class AdministrativeFeesRangesService {

    private final Logger log = LoggerFactory.getLogger(AdministrativeFeesRangesService.class);

    private final AdministrativeFeesRangesRepository administrativeFeesRangesRepository;

    private final AdministrativeFeesRangesMapper administrativeFeesRangesMapper;

    public AdministrativeFeesRangesService(
        AdministrativeFeesRangesRepository administrativeFeesRangesRepository,
        AdministrativeFeesRangesMapper administrativeFeesRangesMapper
    ) {
        this.administrativeFeesRangesRepository = administrativeFeesRangesRepository;
        this.administrativeFeesRangesMapper = administrativeFeesRangesMapper;
    }

    /**
     * Save a administrativeFeesRanges.
     *
     * @param administrativeFeesRangesDTO the entity to save.
     * @return the persisted entity.
     */
    public AdministrativeFeesRangesDTO save(AdministrativeFeesRangesDTO administrativeFeesRangesDTO) {
        log.debug("Request to save AdministrativeFeesRanges : {}", administrativeFeesRangesDTO);
        AdministrativeFeesRanges administrativeFeesRanges = administrativeFeesRangesMapper.toEntity(administrativeFeesRangesDTO);
        administrativeFeesRanges = administrativeFeesRangesRepository.save(administrativeFeesRanges);
        return administrativeFeesRangesMapper.toDto(administrativeFeesRanges);
    }

    /**
     * Partially update a administrativeFeesRanges.
     *
     * @param administrativeFeesRangesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdministrativeFeesRangesDTO> partialUpdate(AdministrativeFeesRangesDTO administrativeFeesRangesDTO) {
        log.debug("Request to partially update AdministrativeFeesRanges : {}", administrativeFeesRangesDTO);

        return administrativeFeesRangesRepository
            .findById(administrativeFeesRangesDTO.getId())
            .map(
                existingAdministrativeFeesRanges -> {
                    administrativeFeesRangesMapper.partialUpdate(existingAdministrativeFeesRanges, administrativeFeesRangesDTO);

                    return existingAdministrativeFeesRanges;
                }
            )
            .map(administrativeFeesRangesRepository::save)
            .map(administrativeFeesRangesMapper::toDto);
    }

    /**
     * Get all the administrativeFeesRanges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdministrativeFeesRangesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdministrativeFeesRanges");
        return administrativeFeesRangesRepository.findAll(pageable).map(administrativeFeesRangesMapper::toDto);
    }

    /**
     * Get one administrativeFeesRanges by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdministrativeFeesRangesDTO> findOne(Long id) {
        log.debug("Request to get AdministrativeFeesRanges : {}", id);
        return administrativeFeesRangesRepository.findById(id).map(administrativeFeesRangesMapper::toDto);
    }

    /**
     * Delete the administrativeFeesRanges by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdministrativeFeesRanges : {}", id);
        administrativeFeesRangesRepository.deleteById(id);
    }
}
