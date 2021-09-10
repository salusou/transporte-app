package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.StatusAttachments;
import com.genesisoft.transporte.repository.StatusAttachmentsRepository;
import com.genesisoft.transporte.service.dto.StatusAttachmentsDTO;
import com.genesisoft.transporte.service.mapper.StatusAttachmentsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StatusAttachments}.
 */
@Service
@Transactional
public class StatusAttachmentsService {

    private final Logger log = LoggerFactory.getLogger(StatusAttachmentsService.class);

    private final StatusAttachmentsRepository statusAttachmentsRepository;

    private final StatusAttachmentsMapper statusAttachmentsMapper;

    public StatusAttachmentsService(
        StatusAttachmentsRepository statusAttachmentsRepository,
        StatusAttachmentsMapper statusAttachmentsMapper
    ) {
        this.statusAttachmentsRepository = statusAttachmentsRepository;
        this.statusAttachmentsMapper = statusAttachmentsMapper;
    }

    /**
     * Save a statusAttachments.
     *
     * @param statusAttachmentsDTO the entity to save.
     * @return the persisted entity.
     */
    public StatusAttachmentsDTO save(StatusAttachmentsDTO statusAttachmentsDTO) {
        log.debug("Request to save StatusAttachments : {}", statusAttachmentsDTO);
        StatusAttachments statusAttachments = statusAttachmentsMapper.toEntity(statusAttachmentsDTO);
        statusAttachments = statusAttachmentsRepository.save(statusAttachments);
        return statusAttachmentsMapper.toDto(statusAttachments);
    }

    /**
     * Partially update a statusAttachments.
     *
     * @param statusAttachmentsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StatusAttachmentsDTO> partialUpdate(StatusAttachmentsDTO statusAttachmentsDTO) {
        log.debug("Request to partially update StatusAttachments : {}", statusAttachmentsDTO);

        return statusAttachmentsRepository
            .findById(statusAttachmentsDTO.getId())
            .map(
                existingStatusAttachments -> {
                    statusAttachmentsMapper.partialUpdate(existingStatusAttachments, statusAttachmentsDTO);

                    return existingStatusAttachments;
                }
            )
            .map(statusAttachmentsRepository::save)
            .map(statusAttachmentsMapper::toDto);
    }

    /**
     * Get all the statusAttachments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StatusAttachmentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StatusAttachments");
        return statusAttachmentsRepository.findAll(pageable).map(statusAttachmentsMapper::toDto);
    }

    /**
     * Get one statusAttachments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StatusAttachmentsDTO> findOne(Long id) {
        log.debug("Request to get StatusAttachments : {}", id);
        return statusAttachmentsRepository.findById(id).map(statusAttachmentsMapper::toDto);
    }

    /**
     * Delete the statusAttachments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StatusAttachments : {}", id);
        statusAttachmentsRepository.deleteById(id);
    }
}
