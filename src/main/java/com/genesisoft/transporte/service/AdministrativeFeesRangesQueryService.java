package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.AdministrativeFeesRanges;
import com.genesisoft.transporte.repository.AdministrativeFeesRangesRepository;
import com.genesisoft.transporte.service.criteria.AdministrativeFeesRangesCriteria;
import com.genesisoft.transporte.service.dto.AdministrativeFeesRangesDTO;
import com.genesisoft.transporte.service.mapper.AdministrativeFeesRangesMapper;
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
 * Service for executing complex queries for {@link AdministrativeFeesRanges} entities in the database.
 * The main input is a {@link AdministrativeFeesRangesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdministrativeFeesRangesDTO} or a {@link Page} of {@link AdministrativeFeesRangesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdministrativeFeesRangesQueryService extends QueryService<AdministrativeFeesRanges> {

    private final Logger log = LoggerFactory.getLogger(AdministrativeFeesRangesQueryService.class);

    private final AdministrativeFeesRangesRepository administrativeFeesRangesRepository;

    private final AdministrativeFeesRangesMapper administrativeFeesRangesMapper;

    public AdministrativeFeesRangesQueryService(
        AdministrativeFeesRangesRepository administrativeFeesRangesRepository,
        AdministrativeFeesRangesMapper administrativeFeesRangesMapper
    ) {
        this.administrativeFeesRangesRepository = administrativeFeesRangesRepository;
        this.administrativeFeesRangesMapper = administrativeFeesRangesMapper;
    }

    /**
     * Return a {@link List} of {@link AdministrativeFeesRangesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdministrativeFeesRangesDTO> findByCriteria(AdministrativeFeesRangesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdministrativeFeesRanges> specification = createSpecification(criteria);
        return administrativeFeesRangesMapper.toDto(administrativeFeesRangesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdministrativeFeesRangesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdministrativeFeesRangesDTO> findByCriteria(AdministrativeFeesRangesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdministrativeFeesRanges> specification = createSpecification(criteria);
        return administrativeFeesRangesRepository.findAll(specification, page).map(administrativeFeesRangesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdministrativeFeesRangesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdministrativeFeesRanges> specification = createSpecification(criteria);
        return administrativeFeesRangesRepository.count(specification);
    }

    /**
     * Function to convert {@link AdministrativeFeesRangesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdministrativeFeesRanges> createSpecification(AdministrativeFeesRangesCriteria criteria) {
        Specification<AdministrativeFeesRanges> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdministrativeFeesRanges_.id));
            }
            if (criteria.getMinRange() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinRange(), AdministrativeFeesRanges_.minRange));
            }
            if (criteria.getMaxRange() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxRange(), AdministrativeFeesRanges_.maxRange));
            }
            if (criteria.getAliquot() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAliquot(), AdministrativeFeesRanges_.aliquot));
            }
            if (criteria.getAffiliatesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAffiliatesId(),
                            root -> root.join(AdministrativeFeesRanges_.affiliates, JoinType.LEFT).get(Affiliates_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
