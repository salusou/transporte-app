package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.Positions;
import com.genesisoft.transporte.repository.PositionsRepository;
import com.genesisoft.transporte.service.criteria.PositionsCriteria;
import com.genesisoft.transporte.service.dto.PositionsDTO;
import com.genesisoft.transporte.service.mapper.PositionsMapper;
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
 * Service for executing complex queries for {@link Positions} entities in the database.
 * The main input is a {@link PositionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PositionsDTO} or a {@link Page} of {@link PositionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PositionsQueryService extends QueryService<Positions> {

    private final Logger log = LoggerFactory.getLogger(PositionsQueryService.class);

    private final PositionsRepository positionsRepository;

    private final PositionsMapper positionsMapper;

    public PositionsQueryService(PositionsRepository positionsRepository, PositionsMapper positionsMapper) {
        this.positionsRepository = positionsRepository;
        this.positionsMapper = positionsMapper;
    }

    /**
     * Return a {@link List} of {@link PositionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PositionsDTO> findByCriteria(PositionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Positions> specification = createSpecification(criteria);
        return positionsMapper.toDto(positionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PositionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PositionsDTO> findByCriteria(PositionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Positions> specification = createSpecification(criteria);
        return positionsRepository.findAll(specification, page).map(positionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PositionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Positions> specification = createSpecification(criteria);
        return positionsRepository.count(specification);
    }

    /**
     * Function to convert {@link PositionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Positions> createSpecification(PositionsCriteria criteria) {
        Specification<Positions> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Positions_.id));
            }
            if (criteria.getPositionName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPositionName(), Positions_.positionName));
            }
            if (criteria.getEmployeesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeesId(),
                            root -> root.join(Positions_.employees, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getAffiliatesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAffiliatesId(),
                            root -> root.join(Positions_.affiliates, JoinType.LEFT).get(Affiliates_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
