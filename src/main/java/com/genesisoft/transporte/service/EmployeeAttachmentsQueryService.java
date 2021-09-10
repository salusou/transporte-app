package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.EmployeeAttachments;
import com.genesisoft.transporte.repository.EmployeeAttachmentsRepository;
import com.genesisoft.transporte.service.criteria.EmployeeAttachmentsCriteria;
import com.genesisoft.transporte.service.dto.EmployeeAttachmentsDTO;
import com.genesisoft.transporte.service.mapper.EmployeeAttachmentsMapper;
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
 * Service for executing complex queries for {@link EmployeeAttachments} entities in the database.
 * The main input is a {@link EmployeeAttachmentsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeAttachmentsDTO} or a {@link Page} of {@link EmployeeAttachmentsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeAttachmentsQueryService extends QueryService<EmployeeAttachments> {

    private final Logger log = LoggerFactory.getLogger(EmployeeAttachmentsQueryService.class);

    private final EmployeeAttachmentsRepository employeeAttachmentsRepository;

    private final EmployeeAttachmentsMapper employeeAttachmentsMapper;

    public EmployeeAttachmentsQueryService(
        EmployeeAttachmentsRepository employeeAttachmentsRepository,
        EmployeeAttachmentsMapper employeeAttachmentsMapper
    ) {
        this.employeeAttachmentsRepository = employeeAttachmentsRepository;
        this.employeeAttachmentsMapper = employeeAttachmentsMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeAttachmentsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeAttachmentsDTO> findByCriteria(EmployeeAttachmentsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeAttachments> specification = createSpecification(criteria);
        return employeeAttachmentsMapper.toDto(employeeAttachmentsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeAttachmentsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeAttachmentsDTO> findByCriteria(EmployeeAttachmentsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeAttachments> specification = createSpecification(criteria);
        return employeeAttachmentsRepository.findAll(specification, page).map(employeeAttachmentsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeAttachmentsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeAttachments> specification = createSpecification(criteria);
        return employeeAttachmentsRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeAttachmentsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeAttachments> createSpecification(EmployeeAttachmentsCriteria criteria) {
        Specification<EmployeeAttachments> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeAttachments_.id));
            }
            if (criteria.getAttachUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttachUrl(), EmployeeAttachments_.attachUrl));
            }
            if (criteria.getAttachDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAttachDescription(), EmployeeAttachments_.attachDescription));
            }
            if (criteria.getAttachedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAttachedDate(), EmployeeAttachments_.attachedDate));
            }
            if (criteria.getEmployeesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeesId(),
                            root -> root.join(EmployeeAttachments_.employees, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
