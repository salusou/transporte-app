package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.VehicleControlAttachments;
import com.genesisoft.transporte.repository.VehicleControlAttachmentsRepository;
import com.genesisoft.transporte.service.criteria.VehicleControlAttachmentsCriteria;
import com.genesisoft.transporte.service.dto.VehicleControlAttachmentsDTO;
import com.genesisoft.transporte.service.mapper.VehicleControlAttachmentsMapper;
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
 * Service for executing complex queries for {@link VehicleControlAttachments} entities in the database.
 * The main input is a {@link VehicleControlAttachmentsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VehicleControlAttachmentsDTO} or a {@link Page} of {@link VehicleControlAttachmentsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VehicleControlAttachmentsQueryService extends QueryService<VehicleControlAttachments> {

    private final Logger log = LoggerFactory.getLogger(VehicleControlAttachmentsQueryService.class);

    private final VehicleControlAttachmentsRepository vehicleControlAttachmentsRepository;

    private final VehicleControlAttachmentsMapper vehicleControlAttachmentsMapper;

    public VehicleControlAttachmentsQueryService(
        VehicleControlAttachmentsRepository vehicleControlAttachmentsRepository,
        VehicleControlAttachmentsMapper vehicleControlAttachmentsMapper
    ) {
        this.vehicleControlAttachmentsRepository = vehicleControlAttachmentsRepository;
        this.vehicleControlAttachmentsMapper = vehicleControlAttachmentsMapper;
    }

    /**
     * Return a {@link List} of {@link VehicleControlAttachmentsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VehicleControlAttachmentsDTO> findByCriteria(VehicleControlAttachmentsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VehicleControlAttachments> specification = createSpecification(criteria);
        return vehicleControlAttachmentsMapper.toDto(vehicleControlAttachmentsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VehicleControlAttachmentsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleControlAttachmentsDTO> findByCriteria(VehicleControlAttachmentsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VehicleControlAttachments> specification = createSpecification(criteria);
        return vehicleControlAttachmentsRepository.findAll(specification, page).map(vehicleControlAttachmentsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VehicleControlAttachmentsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VehicleControlAttachments> specification = createSpecification(criteria);
        return vehicleControlAttachmentsRepository.count(specification);
    }

    /**
     * Function to convert {@link VehicleControlAttachmentsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VehicleControlAttachments> createSpecification(VehicleControlAttachmentsCriteria criteria) {
        Specification<VehicleControlAttachments> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VehicleControlAttachments_.id));
            }
            if (criteria.getAttachUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttachUrl(), VehicleControlAttachments_.attachUrl));
            }
            if (criteria.getAttachDescription() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAttachDescription(), VehicleControlAttachments_.attachDescription)
                    );
            }
            if (criteria.getAttachedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAttachedDate(), VehicleControlAttachments_.attachedDate));
            }
            if (criteria.getVehicleControlsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlsId(),
                            root -> root.join(VehicleControlAttachments_.vehicleControls, JoinType.LEFT).get(VehicleControls_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
