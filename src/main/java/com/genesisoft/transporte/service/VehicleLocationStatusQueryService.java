package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.VehicleLocationStatus;
import com.genesisoft.transporte.repository.VehicleLocationStatusRepository;
import com.genesisoft.transporte.service.criteria.VehicleLocationStatusCriteria;
import com.genesisoft.transporte.service.dto.VehicleLocationStatusDTO;
import com.genesisoft.transporte.service.mapper.VehicleLocationStatusMapper;
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
 * Service for executing complex queries for {@link VehicleLocationStatus} entities in the database.
 * The main input is a {@link VehicleLocationStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VehicleLocationStatusDTO} or a {@link Page} of {@link VehicleLocationStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VehicleLocationStatusQueryService extends QueryService<VehicleLocationStatus> {

    private final Logger log = LoggerFactory.getLogger(VehicleLocationStatusQueryService.class);

    private final VehicleLocationStatusRepository vehicleLocationStatusRepository;

    private final VehicleLocationStatusMapper vehicleLocationStatusMapper;

    public VehicleLocationStatusQueryService(
        VehicleLocationStatusRepository vehicleLocationStatusRepository,
        VehicleLocationStatusMapper vehicleLocationStatusMapper
    ) {
        this.vehicleLocationStatusRepository = vehicleLocationStatusRepository;
        this.vehicleLocationStatusMapper = vehicleLocationStatusMapper;
    }

    /**
     * Return a {@link List} of {@link VehicleLocationStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VehicleLocationStatusDTO> findByCriteria(VehicleLocationStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VehicleLocationStatus> specification = createSpecification(criteria);
        return vehicleLocationStatusMapper.toDto(vehicleLocationStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VehicleLocationStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleLocationStatusDTO> findByCriteria(VehicleLocationStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VehicleLocationStatus> specification = createSpecification(criteria);
        return vehicleLocationStatusRepository.findAll(specification, page).map(vehicleLocationStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VehicleLocationStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VehicleLocationStatus> specification = createSpecification(criteria);
        return vehicleLocationStatusRepository.count(specification);
    }

    /**
     * Function to convert {@link VehicleLocationStatusCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VehicleLocationStatus> createSpecification(VehicleLocationStatusCriteria criteria) {
        Specification<VehicleLocationStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VehicleLocationStatus_.id));
            }
            if (criteria.getVehicleLocationStatusDate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getVehicleLocationStatusDate(), VehicleLocationStatus_.vehicleLocationStatusDate)
                    );
            }
            if (criteria.getVehicleLocationStatusDescription() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getVehicleLocationStatusDescription(),
                            VehicleLocationStatus_.vehicleLocationStatusDescription
                        )
                    );
            }
            if (criteria.getVehicleControlsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlsId(),
                            root -> root.join(VehicleLocationStatus_.vehicleControls, JoinType.LEFT).get(VehicleControls_.id)
                        )
                    );
            }
            if (criteria.getCitiesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCitiesId(),
                            root -> root.join(VehicleLocationStatus_.cities, JoinType.LEFT).get(Cities_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
