package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.Fees;
import com.genesisoft.transporte.repository.FeesRepository;
import com.genesisoft.transporte.service.criteria.FeesCriteria;
import com.genesisoft.transporte.service.dto.FeesDTO;
import com.genesisoft.transporte.service.mapper.FeesMapper;
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
 * Service for executing complex queries for {@link Fees} entities in the database.
 * The main input is a {@link FeesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FeesDTO} or a {@link Page} of {@link FeesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FeesQueryService extends QueryService<Fees> {

    private final Logger log = LoggerFactory.getLogger(FeesQueryService.class);

    private final FeesRepository feesRepository;

    private final FeesMapper feesMapper;

    public FeesQueryService(FeesRepository feesRepository, FeesMapper feesMapper) {
        this.feesRepository = feesRepository;
        this.feesMapper = feesMapper;
    }

    /**
     * Return a {@link List} of {@link FeesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FeesDTO> findByCriteria(FeesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Fees> specification = createSpecification(criteria);
        return feesMapper.toDto(feesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FeesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FeesDTO> findByCriteria(FeesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Fees> specification = createSpecification(criteria);
        return feesRepository.findAll(specification, page).map(feesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FeesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Fees> specification = createSpecification(criteria);
        return feesRepository.count(specification);
    }

    /**
     * Function to convert {@link FeesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Fees> createSpecification(FeesCriteria criteria) {
        Specification<Fees> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Fees_.id));
            }
            if (criteria.getFeeDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFeeDate(), Fees_.feeDate));
            }
            if (criteria.getFeeDriverCommission() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFeeDriverCommission(), Fees_.feeDriverCommission));
            }
            if (criteria.getFeeFinancialCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFeeFinancialCost(), Fees_.feeFinancialCost));
            }
            if (criteria.getFeeTaxes() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFeeTaxes(), Fees_.feeTaxes));
            }
            if (criteria.getFeeDescriptions() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFeeDescriptions(), Fees_.feeDescriptions));
            }
            if (criteria.getVehicleControlBillingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlBillingId(),
                            root -> root.join(Fees_.vehicleControlBillings, JoinType.LEFT).get(VehicleControlBilling_.id)
                        )
                    );
            }
            if (criteria.getAffiliatesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAffiliatesId(),
                            root -> root.join(Fees_.affiliates, JoinType.LEFT).get(Affiliates_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
