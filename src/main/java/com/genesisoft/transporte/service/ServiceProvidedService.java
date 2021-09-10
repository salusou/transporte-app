package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.ServiceProvided;
import com.genesisoft.transporte.repository.ServiceProvidedRepository;
import com.genesisoft.transporte.service.dto.ServiceProvidedDTO;
import com.genesisoft.transporte.service.mapper.ServiceProvidedMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ServiceProvided}.
 */
@Service
@Transactional
public class ServiceProvidedService {

    private final Logger log = LoggerFactory.getLogger(ServiceProvidedService.class);

    private final ServiceProvidedRepository serviceProvidedRepository;

    private final ServiceProvidedMapper serviceProvidedMapper;

    public ServiceProvidedService(ServiceProvidedRepository serviceProvidedRepository, ServiceProvidedMapper serviceProvidedMapper) {
        this.serviceProvidedRepository = serviceProvidedRepository;
        this.serviceProvidedMapper = serviceProvidedMapper;
    }

    /**
     * Save a serviceProvided.
     *
     * @param serviceProvidedDTO the entity to save.
     * @return the persisted entity.
     */
    public ServiceProvidedDTO save(ServiceProvidedDTO serviceProvidedDTO) {
        log.debug("Request to save ServiceProvided : {}", serviceProvidedDTO);
        ServiceProvided serviceProvided = serviceProvidedMapper.toEntity(serviceProvidedDTO);
        serviceProvided = serviceProvidedRepository.save(serviceProvided);
        return serviceProvidedMapper.toDto(serviceProvided);
    }

    /**
     * Partially update a serviceProvided.
     *
     * @param serviceProvidedDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ServiceProvidedDTO> partialUpdate(ServiceProvidedDTO serviceProvidedDTO) {
        log.debug("Request to partially update ServiceProvided : {}", serviceProvidedDTO);

        return serviceProvidedRepository
            .findById(serviceProvidedDTO.getId())
            .map(
                existingServiceProvided -> {
                    serviceProvidedMapper.partialUpdate(existingServiceProvided, serviceProvidedDTO);

                    return existingServiceProvided;
                }
            )
            .map(serviceProvidedRepository::save)
            .map(serviceProvidedMapper::toDto);
    }

    /**
     * Get all the serviceProvideds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceProvidedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceProvideds");
        return serviceProvidedRepository.findAll(pageable).map(serviceProvidedMapper::toDto);
    }

    /**
     * Get one serviceProvided by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServiceProvidedDTO> findOne(Long id) {
        log.debug("Request to get ServiceProvided : {}", id);
        return serviceProvidedRepository.findById(id).map(serviceProvidedMapper::toDto);
    }

    /**
     * Delete the serviceProvided by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceProvided : {}", id);
        serviceProvidedRepository.deleteById(id);
    }
}
