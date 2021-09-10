package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.CostCenter;
import com.genesisoft.transporte.repository.CostCenterRepository;
import com.genesisoft.transporte.service.criteria.CostCenterCriteria;
import com.genesisoft.transporte.service.dto.CostCenterDTO;
import com.genesisoft.transporte.service.mapper.CostCenterMapper;
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
 * Service for executing complex queries for {@link CostCenter} entities in the database.
 * The main input is a {@link CostCenterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CostCenterDTO} or a {@link Page} of {@link CostCenterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CostCenterQueryService extends QueryService<CostCenter> {

    private final Logger log = LoggerFactory.getLogger(CostCenterQueryService.class);

    private final CostCenterRepository costCenterRepository;

    private final CostCenterMapper costCenterMapper;

    public CostCenterQueryService(CostCenterRepository costCenterRepository, CostCenterMapper costCenterMapper) {
        this.costCenterRepository = costCenterRepository;
        this.costCenterMapper = costCenterMapper;
    }

    /**
     * Return a {@link List} of {@link CostCenterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CostCenterDTO> findByCriteria(CostCenterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CostCenter> specification = createSpecification(criteria);
        return costCenterMapper.toDto(costCenterRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CostCenterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CostCenterDTO> findByCriteria(CostCenterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CostCenter> specification = createSpecification(criteria);
        return costCenterRepository.findAll(specification, page).map(costCenterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CostCenterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CostCenter> specification = createSpecification(criteria);
        return costCenterRepository.count(specification);
    }

    /**
     * Function to convert {@link CostCenterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CostCenter> createSpecification(CostCenterCriteria criteria) {
        Specification<CostCenter> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CostCenter_.id));
            }
            if (criteria.getCostCenterName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCostCenterName(), CostCenter_.costCenterName));
            }
            if (criteria.getHousingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getHousingId(), root -> root.join(CostCenter_.housings, JoinType.LEFT).get(Housing_.id))
                    );
            }
            if (criteria.getAffiliatesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAffiliatesId(),
                            root -> root.join(CostCenter_.affiliates, JoinType.LEFT).get(Affiliates_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
