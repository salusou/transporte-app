package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.CustomersGroups;
import com.genesisoft.transporte.repository.CustomersGroupsRepository;
import com.genesisoft.transporte.service.criteria.CustomersGroupsCriteria;
import com.genesisoft.transporte.service.dto.CustomersGroupsDTO;
import com.genesisoft.transporte.service.mapper.CustomersGroupsMapper;
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
 * Service for executing complex queries for {@link CustomersGroups} entities in the database.
 * The main input is a {@link CustomersGroupsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomersGroupsDTO} or a {@link Page} of {@link CustomersGroupsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomersGroupsQueryService extends QueryService<CustomersGroups> {

    private final Logger log = LoggerFactory.getLogger(CustomersGroupsQueryService.class);

    private final CustomersGroupsRepository customersGroupsRepository;

    private final CustomersGroupsMapper customersGroupsMapper;

    public CustomersGroupsQueryService(CustomersGroupsRepository customersGroupsRepository, CustomersGroupsMapper customersGroupsMapper) {
        this.customersGroupsRepository = customersGroupsRepository;
        this.customersGroupsMapper = customersGroupsMapper;
    }

    /**
     * Return a {@link List} of {@link CustomersGroupsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomersGroupsDTO> findByCriteria(CustomersGroupsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustomersGroups> specification = createSpecification(criteria);
        return customersGroupsMapper.toDto(customersGroupsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustomersGroupsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomersGroupsDTO> findByCriteria(CustomersGroupsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustomersGroups> specification = createSpecification(criteria);
        return customersGroupsRepository.findAll(specification, page).map(customersGroupsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomersGroupsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustomersGroups> specification = createSpecification(criteria);
        return customersGroupsRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomersGroupsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustomersGroups> createSpecification(CustomersGroupsCriteria criteria) {
        Specification<CustomersGroups> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustomersGroups_.id));
            }
            if (criteria.getGroupName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupName(), CustomersGroups_.groupName));
            }
            if (criteria.getCustomersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomersId(),
                            root -> root.join(CustomersGroups_.customers, JoinType.LEFT).get(Customers_.id)
                        )
                    );
            }
            if (criteria.getVehicleControlsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlsId(),
                            root -> root.join(CustomersGroups_.vehicleControls, JoinType.LEFT).get(VehicleControls_.id)
                        )
                    );
            }
            if (criteria.getAffiliatesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAffiliatesId(),
                            root -> root.join(CustomersGroups_.affiliates, JoinType.LEFT).get(Affiliates_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
