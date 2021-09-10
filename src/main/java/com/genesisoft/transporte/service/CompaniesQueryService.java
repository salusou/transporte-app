package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.Companies;
import com.genesisoft.transporte.repository.CompaniesRepository;
import com.genesisoft.transporte.service.criteria.CompaniesCriteria;
import com.genesisoft.transporte.service.dto.CompaniesDTO;
import com.genesisoft.transporte.service.mapper.CompaniesMapper;
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
 * Service for executing complex queries for {@link Companies} entities in the database.
 * The main input is a {@link CompaniesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompaniesDTO} or a {@link Page} of {@link CompaniesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompaniesQueryService extends QueryService<Companies> {

    private final Logger log = LoggerFactory.getLogger(CompaniesQueryService.class);

    private final CompaniesRepository companiesRepository;

    private final CompaniesMapper companiesMapper;

    public CompaniesQueryService(CompaniesRepository companiesRepository, CompaniesMapper companiesMapper) {
        this.companiesRepository = companiesRepository;
        this.companiesMapper = companiesMapper;
    }

    /**
     * Return a {@link List} of {@link CompaniesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompaniesDTO> findByCriteria(CompaniesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Companies> specification = createSpecification(criteria);
        return companiesMapper.toDto(companiesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompaniesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompaniesDTO> findByCriteria(CompaniesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Companies> specification = createSpecification(criteria);
        return companiesRepository.findAll(specification, page).map(companiesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompaniesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Companies> specification = createSpecification(criteria);
        return companiesRepository.count(specification);
    }

    /**
     * Function to convert {@link CompaniesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Companies> createSpecification(CompaniesCriteria criteria) {
        Specification<Companies> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Companies_.id));
            }
            if (criteria.getCompanyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyName(), Companies_.companyName));
            }
            if (criteria.getTradeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTradeName(), Companies_.tradeName));
            }
            if (criteria.getCompanyNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyNumber(), Companies_.companyNumber));
            }
            if (criteria.getPostalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostalCode(), Companies_.postalCode));
            }
            if (criteria.getCompanyAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyAddress(), Companies_.companyAddress));
            }
            if (criteria.getCompanyAddressComplement() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getCompanyAddressComplement(), Companies_.companyAddressComplement)
                    );
            }
            if (criteria.getCompanyAddressNumber() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCompanyAddressNumber(), Companies_.companyAddressNumber));
            }
            if (criteria.getCompanyAddressNeighborhood() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getCompanyAddressNeighborhood(), Companies_.companyAddressNeighborhood)
                    );
            }
            if (criteria.getCompanyTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyTelephone(), Companies_.companyTelephone));
            }
            if (criteria.getCompanyEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyEmail(), Companies_.companyEmail));
            }
            if (criteria.getResponsibleContact() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getResponsibleContact(), Companies_.responsibleContact));
            }
            if (criteria.getAffiliatesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAffiliatesId(),
                            root -> root.join(Companies_.affiliates, JoinType.LEFT).get(Affiliates_.id)
                        )
                    );
            }
            if (criteria.getEmployeesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeesId(),
                            root -> root.join(Companies_.employees, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getCitiesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCitiesId(), root -> root.join(Companies_.cities, JoinType.LEFT).get(Cities_.id))
                    );
            }
            if (criteria.getStateProvincesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStateProvincesId(),
                            root -> root.join(Companies_.stateProvinces, JoinType.LEFT).get(StateProvinces_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
