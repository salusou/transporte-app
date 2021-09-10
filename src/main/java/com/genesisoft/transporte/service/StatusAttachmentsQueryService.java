package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.StatusAttachments;
import com.genesisoft.transporte.repository.StatusAttachmentsRepository;
import com.genesisoft.transporte.service.criteria.StatusAttachmentsCriteria;
import com.genesisoft.transporte.service.dto.StatusAttachmentsDTO;
import com.genesisoft.transporte.service.mapper.StatusAttachmentsMapper;
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
 * Service for executing complex queries for {@link StatusAttachments} entities in the database.
 * The main input is a {@link StatusAttachmentsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StatusAttachmentsDTO} or a {@link Page} of {@link StatusAttachmentsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StatusAttachmentsQueryService extends QueryService<StatusAttachments> {

    private final Logger log = LoggerFactory.getLogger(StatusAttachmentsQueryService.class);

    private final StatusAttachmentsRepository statusAttachmentsRepository;

    private final StatusAttachmentsMapper statusAttachmentsMapper;

    public StatusAttachmentsQueryService(
        StatusAttachmentsRepository statusAttachmentsRepository,
        StatusAttachmentsMapper statusAttachmentsMapper
    ) {
        this.statusAttachmentsRepository = statusAttachmentsRepository;
        this.statusAttachmentsMapper = statusAttachmentsMapper;
    }

    /**
     * Return a {@link List} of {@link StatusAttachmentsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StatusAttachmentsDTO> findByCriteria(StatusAttachmentsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StatusAttachments> specification = createSpecification(criteria);
        return statusAttachmentsMapper.toDto(statusAttachmentsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StatusAttachmentsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StatusAttachmentsDTO> findByCriteria(StatusAttachmentsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StatusAttachments> specification = createSpecification(criteria);
        return statusAttachmentsRepository.findAll(specification, page).map(statusAttachmentsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StatusAttachmentsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StatusAttachments> specification = createSpecification(criteria);
        return statusAttachmentsRepository.count(specification);
    }

    /**
     * Function to convert {@link StatusAttachmentsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StatusAttachments> createSpecification(StatusAttachmentsCriteria criteria) {
        Specification<StatusAttachments> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StatusAttachments_.id));
            }
            if (criteria.getStatusName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatusName(), StatusAttachments_.statusName));
            }
            if (criteria.getStatusDescriptions() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getStatusDescriptions(), StatusAttachments_.statusDescriptions));
            }
            if (criteria.getStatusObs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatusObs(), StatusAttachments_.statusObs));
            }
            if (criteria.getAttachmentType() != null) {
                specification = specification.and(buildSpecification(criteria.getAttachmentType(), StatusAttachments_.attachmentType));
            }
            if (criteria.getCustomerAttachmentsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomerAttachmentsId(),
                            root -> root.join(StatusAttachments_.customerAttachments, JoinType.LEFT).get(CustomerAttachments_.id)
                        )
                    );
            }
            if (criteria.getAffiliatesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAffiliatesId(),
                            root -> root.join(StatusAttachments_.affiliates, JoinType.LEFT).get(Affiliates_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
