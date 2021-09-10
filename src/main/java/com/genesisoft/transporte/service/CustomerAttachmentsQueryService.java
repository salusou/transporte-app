package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.CustomerAttachments;
import com.genesisoft.transporte.repository.CustomerAttachmentsRepository;
import com.genesisoft.transporte.service.criteria.CustomerAttachmentsCriteria;
import com.genesisoft.transporte.service.dto.CustomerAttachmentsDTO;
import com.genesisoft.transporte.service.mapper.CustomerAttachmentsMapper;
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
 * Service for executing complex queries for {@link CustomerAttachments} entities in the database.
 * The main input is a {@link CustomerAttachmentsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomerAttachmentsDTO} or a {@link Page} of {@link CustomerAttachmentsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomerAttachmentsQueryService extends QueryService<CustomerAttachments> {

    private final Logger log = LoggerFactory.getLogger(CustomerAttachmentsQueryService.class);

    private final CustomerAttachmentsRepository customerAttachmentsRepository;

    private final CustomerAttachmentsMapper customerAttachmentsMapper;

    public CustomerAttachmentsQueryService(
        CustomerAttachmentsRepository customerAttachmentsRepository,
        CustomerAttachmentsMapper customerAttachmentsMapper
    ) {
        this.customerAttachmentsRepository = customerAttachmentsRepository;
        this.customerAttachmentsMapper = customerAttachmentsMapper;
    }

    /**
     * Return a {@link List} of {@link CustomerAttachmentsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerAttachmentsDTO> findByCriteria(CustomerAttachmentsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustomerAttachments> specification = createSpecification(criteria);
        return customerAttachmentsMapper.toDto(customerAttachmentsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustomerAttachmentsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerAttachmentsDTO> findByCriteria(CustomerAttachmentsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustomerAttachments> specification = createSpecification(criteria);
        return customerAttachmentsRepository.findAll(specification, page).map(customerAttachmentsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomerAttachmentsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustomerAttachments> specification = createSpecification(criteria);
        return customerAttachmentsRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomerAttachmentsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustomerAttachments> createSpecification(CustomerAttachmentsCriteria criteria) {
        Specification<CustomerAttachments> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustomerAttachments_.id));
            }
            if (criteria.getAttachUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttachUrl(), CustomerAttachments_.attachUrl));
            }
            if (criteria.getAttachDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAttachDescription(), CustomerAttachments_.attachDescription));
            }
            if (criteria.getAttachedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAttachedDate(), CustomerAttachments_.attachedDate));
            }
            if (criteria.getCustomersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomersId(),
                            root -> root.join(CustomerAttachments_.customers, JoinType.LEFT).get(Customers_.id)
                        )
                    );
            }
            if (criteria.getStatusAttachmentsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStatusAttachmentsId(),
                            root -> root.join(CustomerAttachments_.statusAttachments, JoinType.LEFT).get(StatusAttachments_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
