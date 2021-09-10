package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.Status;
import com.genesisoft.transporte.repository.StatusRepository;
import com.genesisoft.transporte.service.dto.StatusDTO;
import com.genesisoft.transporte.service.mapper.StatusMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Status}.
 */
@Service
@Transactional
public class StatusService {

    private final Logger log = LoggerFactory.getLogger(StatusService.class);

    private final StatusRepository statusRepository;

    private final StatusMapper statusMapper;

    public StatusService(StatusRepository statusRepository, StatusMapper statusMapper) {
        this.statusRepository = statusRepository;
        this.statusMapper = statusMapper;
    }

    /**
     * Save a status.
     *
     * @param statusDTO the entity to save.
     * @return the persisted entity.
     */
    public StatusDTO save(StatusDTO statusDTO) {
        log.debug("Request to save Status : {}", statusDTO);
        Status status = statusMapper.toEntity(statusDTO);
        status = statusRepository.save(status);
        return statusMapper.toDto(status);
    }

    /**
     * Partially update a status.
     *
     * @param statusDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StatusDTO> partialUpdate(StatusDTO statusDTO) {
        log.debug("Request to partially update Status : {}", statusDTO);

        return statusRepository
            .findById(statusDTO.getId())
            .map(
                existingStatus -> {
                    statusMapper.partialUpdate(existingStatus, statusDTO);

                    return existingStatus;
                }
            )
            .map(statusRepository::save)
            .map(statusMapper::toDto);
    }

    /**
     * Get all the statuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Statuses");
        return statusRepository.findAll(pageable).map(statusMapper::toDto);
    }

    /**
     * Get one status by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StatusDTO> findOne(Long id) {
        log.debug("Request to get Status : {}", id);
        return statusRepository.findById(id).map(statusMapper::toDto);
    }

    /**
     * Delete the status by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Status : {}", id);
        statusRepository.deleteById(id);
    }
}
