package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.SupplierContacts;
import com.genesisoft.transporte.repository.SupplierContactsRepository;
import com.genesisoft.transporte.service.criteria.SupplierContactsCriteria;
import com.genesisoft.transporte.service.dto.SupplierContactsDTO;
import com.genesisoft.transporte.service.mapper.SupplierContactsMapper;
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
 * Service for executing complex queries for {@link SupplierContacts} entities in the database.
 * The main input is a {@link SupplierContactsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplierContactsDTO} or a {@link Page} of {@link SupplierContactsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplierContactsQueryService extends QueryService<SupplierContacts> {

    private final Logger log = LoggerFactory.getLogger(SupplierContactsQueryService.class);

    private final SupplierContactsRepository supplierContactsRepository;

    private final SupplierContactsMapper supplierContactsMapper;

    public SupplierContactsQueryService(
        SupplierContactsRepository supplierContactsRepository,
        SupplierContactsMapper supplierContactsMapper
    ) {
        this.supplierContactsRepository = supplierContactsRepository;
        this.supplierContactsMapper = supplierContactsMapper;
    }

    /**
     * Return a {@link List} of {@link SupplierContactsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplierContactsDTO> findByCriteria(SupplierContactsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplierContacts> specification = createSpecification(criteria);
        return supplierContactsMapper.toDto(supplierContactsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplierContactsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplierContactsDTO> findByCriteria(SupplierContactsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplierContacts> specification = createSpecification(criteria);
        return supplierContactsRepository.findAll(specification, page).map(supplierContactsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplierContactsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplierContacts> specification = createSpecification(criteria);
        return supplierContactsRepository.count(specification);
    }

    /**
     * Function to convert {@link SupplierContactsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SupplierContacts> createSpecification(SupplierContactsCriteria criteria) {
        Specification<SupplierContacts> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SupplierContacts_.id));
            }
            if (criteria.getSupplierContactName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSupplierContactName(), SupplierContacts_.supplierContactName));
            }
            if (criteria.getSupplierContactPhone() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSupplierContactPhone(), SupplierContacts_.supplierContactPhone));
            }
            if (criteria.getSupplierContactEmail() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSupplierContactEmail(), SupplierContacts_.supplierContactEmail));
            }
            if (criteria.getSuppliersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSuppliersId(),
                            root -> root.join(SupplierContacts_.suppliers, JoinType.LEFT).get(Suppliers_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
