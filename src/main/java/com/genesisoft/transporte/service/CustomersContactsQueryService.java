package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.CustomersContacts;
import com.genesisoft.transporte.repository.CustomersContactsRepository;
import com.genesisoft.transporte.service.criteria.CustomersContactsCriteria;
import com.genesisoft.transporte.service.dto.CustomersContactsDTO;
import com.genesisoft.transporte.service.mapper.CustomersContactsMapper;
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
 * Service for executing complex queries for {@link CustomersContacts} entities in the database.
 * The main input is a {@link CustomersContactsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomersContactsDTO} or a {@link Page} of {@link CustomersContactsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomersContactsQueryService extends QueryService<CustomersContacts> {

    private final Logger log = LoggerFactory.getLogger(CustomersContactsQueryService.class);

    private final CustomersContactsRepository customersContactsRepository;

    private final CustomersContactsMapper customersContactsMapper;

    public CustomersContactsQueryService(
        CustomersContactsRepository customersContactsRepository,
        CustomersContactsMapper customersContactsMapper
    ) {
        this.customersContactsRepository = customersContactsRepository;
        this.customersContactsMapper = customersContactsMapper;
    }

    /**
     * Return a {@link List} of {@link CustomersContactsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomersContactsDTO> findByCriteria(CustomersContactsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustomersContacts> specification = createSpecification(criteria);
        return customersContactsMapper.toDto(customersContactsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustomersContactsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomersContactsDTO> findByCriteria(CustomersContactsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustomersContacts> specification = createSpecification(criteria);
        return customersContactsRepository.findAll(specification, page).map(customersContactsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomersContactsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustomersContacts> specification = createSpecification(criteria);
        return customersContactsRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomersContactsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustomersContacts> createSpecification(CustomersContactsCriteria criteria) {
        Specification<CustomersContacts> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustomersContacts_.id));
            }
            if (criteria.getContactName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactName(), CustomersContacts_.contactName));
            }
            if (criteria.getContactTelephone() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getContactTelephone(), CustomersContacts_.contactTelephone));
            }
            if (criteria.getContactEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactEmail(), CustomersContacts_.contactEmail));
            }
            if (criteria.getCustomersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomersId(),
                            root -> root.join(CustomersContacts_.customers, JoinType.LEFT).get(Customers_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
