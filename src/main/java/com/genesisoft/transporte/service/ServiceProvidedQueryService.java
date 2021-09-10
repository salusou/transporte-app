package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.ServiceProvided;
import com.genesisoft.transporte.repository.ServiceProvidedRepository;
import com.genesisoft.transporte.service.criteria.ServiceProvidedCriteria;
import com.genesisoft.transporte.service.dto.ServiceProvidedDTO;
import com.genesisoft.transporte.service.mapper.ServiceProvidedMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ServiceProvided} entities in the database.
 * The main input is a {@link ServiceProvidedCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceProvidedDTO} or a {@link Page} of {@link ServiceProvidedDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceProvidedQueryService extends QueryService<ServiceProvided> {

    private final Logger log = LoggerFactory.getLogger(ServiceProvidedQueryService.class);

    private final ServiceProvidedRepository serviceProvidedRepository;

    private final ServiceProvidedMapper serviceProvidedMapper;

    public ServiceProvidedQueryService(ServiceProvidedRepository serviceProvidedRepository, ServiceProvidedMapper serviceProvidedMapper) {
        this.serviceProvidedRepository = serviceProvidedRepository;
        this.serviceProvidedMapper = serviceProvidedMapper;
    }

    /**
     * Return a {@link List} of {@link ServiceProvidedDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceProvidedDTO> findByCriteria(ServiceProvidedCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceProvided> specification = createSpecification(criteria);
        return serviceProvidedMapper.toDto(serviceProvidedRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceProvidedDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceProvidedDTO> findByCriteria(ServiceProvidedCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceProvided> specification = createSpecification(criteria);
        return serviceProvidedRepository.findAll(specification, page).map(serviceProvidedMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceProvidedCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceProvided> specification = createSpecification(criteria);
        return serviceProvidedRepository.count(specification);
    }

    /**
     * Function to convert {@link ServiceProvidedCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ServiceProvided> createSpecification(ServiceProvidedCriteria criteria) {
        Specification<ServiceProvided> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ServiceProvided_.id));
            }
            if (criteria.getServiceName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceName(), ServiceProvided_.serviceName));
            }
            if (criteria.getSuppliersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSuppliersId(),
                            root -> root.join(ServiceProvided_.suppliers, JoinType.LEFT).get(Suppliers_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
