package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.StateProvinces;
import com.genesisoft.transporte.repository.StateProvincesRepository;
import com.genesisoft.transporte.service.criteria.StateProvincesCriteria;
import com.genesisoft.transporte.service.dto.StateProvincesDTO;
import com.genesisoft.transporte.service.mapper.StateProvincesMapper;
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
 * Service for executing complex queries for {@link StateProvinces} entities in the database.
 * The main input is a {@link StateProvincesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StateProvincesDTO} or a {@link Page} of {@link StateProvincesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StateProvincesQueryService extends QueryService<StateProvinces> {

    private final Logger log = LoggerFactory.getLogger(StateProvincesQueryService.class);

    private final StateProvincesRepository stateProvincesRepository;

    private final StateProvincesMapper stateProvincesMapper;

    public StateProvincesQueryService(StateProvincesRepository stateProvincesRepository, StateProvincesMapper stateProvincesMapper) {
        this.stateProvincesRepository = stateProvincesRepository;
        this.stateProvincesMapper = stateProvincesMapper;
    }

    /**
     * Return a {@link List} of {@link StateProvincesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StateProvincesDTO> findByCriteria(StateProvincesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StateProvinces> specification = createSpecification(criteria);
        return stateProvincesMapper.toDto(stateProvincesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StateProvincesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StateProvincesDTO> findByCriteria(StateProvincesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StateProvinces> specification = createSpecification(criteria);
        return stateProvincesRepository.findAll(specification, page).map(stateProvincesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StateProvincesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StateProvinces> specification = createSpecification(criteria);
        return stateProvincesRepository.count(specification);
    }

    /**
     * Function to convert {@link StateProvincesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StateProvinces> createSpecification(StateProvincesCriteria criteria) {
        Specification<StateProvinces> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StateProvinces_.id));
            }
            if (criteria.getStateName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateName(), StateProvinces_.stateName));
            }
            if (criteria.getAbbreviation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAbbreviation(), StateProvinces_.abbreviation));
            }
            if (criteria.getCitiesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCitiesId(), root -> root.join(StateProvinces_.cities, JoinType.LEFT).get(Cities_.id))
                    );
            }
            if (criteria.getCompaniesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCompaniesId(),
                            root -> root.join(StateProvinces_.companies, JoinType.LEFT).get(Companies_.id)
                        )
                    );
            }
            if (criteria.getAffiliatesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAffiliatesId(),
                            root -> root.join(StateProvinces_.affiliates, JoinType.LEFT).get(Affiliates_.id)
                        )
                    );
            }
            if (criteria.getToInsurancesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getToInsurancesId(),
                            root -> root.join(StateProvinces_.toInsurances, JoinType.LEFT).get(Insurances_.id)
                        )
                    );
            }
            if (criteria.getForInsurancesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getForInsurancesId(),
                            root -> root.join(StateProvinces_.forInsurances, JoinType.LEFT).get(Insurances_.id)
                        )
                    );
            }
            if (criteria.getCountriesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCountriesId(),
                            root -> root.join(StateProvinces_.countries, JoinType.LEFT).get(Countries_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
