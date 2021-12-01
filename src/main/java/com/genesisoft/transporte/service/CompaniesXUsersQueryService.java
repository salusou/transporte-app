package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.CompaniesXUsers;
import com.genesisoft.transporte.repository.CompaniesXUsersRepository;
import com.genesisoft.transporte.service.criteria.CompaniesXUsersCriteria;
import com.genesisoft.transporte.service.dto.CompaniesXUsersDTO;
import com.genesisoft.transporte.service.mapper.CompaniesXUsersMapper;
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
 * Service for executing complex queries for {@link CompaniesXUsers} entities in the database.
 * The main input is a {@link CompaniesXUsersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompaniesXUsersDTO} or a {@link Page} of {@link CompaniesXUsersDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompaniesXUsersQueryService extends QueryService<CompaniesXUsers> {

    private final Logger log = LoggerFactory.getLogger(CompaniesXUsersQueryService.class);

    private final CompaniesXUsersRepository companiesXUsersRepository;

    private final CompaniesXUsersMapper companiesXUsersMapper;

    public CompaniesXUsersQueryService(CompaniesXUsersRepository companiesXUsersRepository, CompaniesXUsersMapper companiesXUsersMapper) {
        this.companiesXUsersRepository = companiesXUsersRepository;
        this.companiesXUsersMapper = companiesXUsersMapper;
    }

    /**
     * Return a {@link List} of {@link CompaniesXUsersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompaniesXUsersDTO> findByCriteria(CompaniesXUsersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CompaniesXUsers> specification = createSpecification(criteria);
        return companiesXUsersMapper.toDto(companiesXUsersRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompaniesXUsersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompaniesXUsersDTO> findByCriteria(CompaniesXUsersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CompaniesXUsers> specification = createSpecification(criteria);
        return companiesXUsersRepository.findAll(specification, page).map(companiesXUsersMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompaniesXUsersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CompaniesXUsers> specification = createSpecification(criteria);
        return companiesXUsersRepository.count(specification);
    }

    /**
     * Function to convert {@link CompaniesXUsersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CompaniesXUsers> createSpecification(CompaniesXUsersCriteria criteria) {
        Specification<CompaniesXUsers> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CompaniesXUsers_.id));
            }
            if (criteria.getCompaniesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCompaniesId(),
                            root -> root.join(CompaniesXUsers_.companies, JoinType.LEFT).get(Companies_.id)
                        )
                    );
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(CompaniesXUsers_.user, JoinType.LEFT).get(User_.id))
                    );
            }
        }
        return specification;
    }
}
