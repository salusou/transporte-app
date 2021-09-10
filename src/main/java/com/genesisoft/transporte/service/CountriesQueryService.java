package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.Countries;
import com.genesisoft.transporte.repository.CountriesRepository;
import com.genesisoft.transporte.service.criteria.CountriesCriteria;
import com.genesisoft.transporte.service.dto.CountriesDTO;
import com.genesisoft.transporte.service.mapper.CountriesMapper;
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
 * Service for executing complex queries for {@link Countries} entities in the database.
 * The main input is a {@link CountriesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CountriesDTO} or a {@link Page} of {@link CountriesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CountriesQueryService extends QueryService<Countries> {

    private final Logger log = LoggerFactory.getLogger(CountriesQueryService.class);

    private final CountriesRepository countriesRepository;

    private final CountriesMapper countriesMapper;

    public CountriesQueryService(CountriesRepository countriesRepository, CountriesMapper countriesMapper) {
        this.countriesRepository = countriesRepository;
        this.countriesMapper = countriesMapper;
    }

    /**
     * Return a {@link List} of {@link CountriesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CountriesDTO> findByCriteria(CountriesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Countries> specification = createSpecification(criteria);
        return countriesMapper.toDto(countriesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CountriesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CountriesDTO> findByCriteria(CountriesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Countries> specification = createSpecification(criteria);
        return countriesRepository.findAll(specification, page).map(countriesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CountriesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Countries> specification = createSpecification(criteria);
        return countriesRepository.count(specification);
    }

    /**
     * Function to convert {@link CountriesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Countries> createSpecification(CountriesCriteria criteria) {
        Specification<Countries> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Countries_.id));
            }
            if (criteria.getCountryName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryName(), Countries_.countryName));
            }
            if (criteria.getIso2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIso2(), Countries_.iso2));
            }
            if (criteria.getCodeArea() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCodeArea(), Countries_.codeArea));
            }
            if (criteria.getLanguage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLanguage(), Countries_.language));
            }
            if (criteria.getStateProvincesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStateProvincesId(),
                            root -> root.join(Countries_.stateProvinces, JoinType.LEFT).get(StateProvinces_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
