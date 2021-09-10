package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.Insurances;
import com.genesisoft.transporte.repository.InsurancesRepository;
import com.genesisoft.transporte.service.criteria.InsurancesCriteria;
import com.genesisoft.transporte.service.dto.InsurancesDTO;
import com.genesisoft.transporte.service.mapper.InsurancesMapper;
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
 * Service for executing complex queries for {@link Insurances} entities in the database.
 * The main input is a {@link InsurancesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InsurancesDTO} or a {@link Page} of {@link InsurancesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InsurancesQueryService extends QueryService<Insurances> {

    private final Logger log = LoggerFactory.getLogger(InsurancesQueryService.class);

    private final InsurancesRepository insurancesRepository;

    private final InsurancesMapper insurancesMapper;

    public InsurancesQueryService(InsurancesRepository insurancesRepository, InsurancesMapper insurancesMapper) {
        this.insurancesRepository = insurancesRepository;
        this.insurancesMapper = insurancesMapper;
    }

    /**
     * Return a {@link List} of {@link InsurancesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InsurancesDTO> findByCriteria(InsurancesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Insurances> specification = createSpecification(criteria);
        return insurancesMapper.toDto(insurancesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InsurancesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InsurancesDTO> findByCriteria(InsurancesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Insurances> specification = createSpecification(criteria);
        return insurancesRepository.findAll(specification, page).map(insurancesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InsurancesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Insurances> specification = createSpecification(criteria);
        return insurancesRepository.count(specification);
    }

    /**
     * Function to convert {@link InsurancesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Insurances> createSpecification(InsurancesCriteria criteria) {
        Specification<Insurances> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Insurances_.id));
            }
            if (criteria.getInsurancesPercent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInsurancesPercent(), Insurances_.insurancesPercent));
            }
            if (criteria.getAffiliatesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAffiliatesId(),
                            root -> root.join(Insurances_.affiliates, JoinType.LEFT).get(Affiliates_.id)
                        )
                    );
            }
            if (criteria.getToStateProvinceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getToStateProvinceId(),
                            root -> root.join(Insurances_.toStateProvince, JoinType.LEFT).get(StateProvinces_.id)
                        )
                    );
            }
            if (criteria.getForStateProvinceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getForStateProvinceId(),
                            root -> root.join(Insurances_.forStateProvince, JoinType.LEFT).get(StateProvinces_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
