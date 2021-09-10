package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.VehicleInspectionsImagens;
import com.genesisoft.transporte.repository.VehicleInspectionsImagensRepository;
import com.genesisoft.transporte.service.criteria.VehicleInspectionsImagensCriteria;
import com.genesisoft.transporte.service.dto.VehicleInspectionsImagensDTO;
import com.genesisoft.transporte.service.mapper.VehicleInspectionsImagensMapper;
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
 * Service for executing complex queries for {@link VehicleInspectionsImagens} entities in the database.
 * The main input is a {@link VehicleInspectionsImagensCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VehicleInspectionsImagensDTO} or a {@link Page} of {@link VehicleInspectionsImagensDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VehicleInspectionsImagensQueryService extends QueryService<VehicleInspectionsImagens> {

    private final Logger log = LoggerFactory.getLogger(VehicleInspectionsImagensQueryService.class);

    private final VehicleInspectionsImagensRepository vehicleInspectionsImagensRepository;

    private final VehicleInspectionsImagensMapper vehicleInspectionsImagensMapper;

    public VehicleInspectionsImagensQueryService(
        VehicleInspectionsImagensRepository vehicleInspectionsImagensRepository,
        VehicleInspectionsImagensMapper vehicleInspectionsImagensMapper
    ) {
        this.vehicleInspectionsImagensRepository = vehicleInspectionsImagensRepository;
        this.vehicleInspectionsImagensMapper = vehicleInspectionsImagensMapper;
    }

    /**
     * Return a {@link List} of {@link VehicleInspectionsImagensDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VehicleInspectionsImagensDTO> findByCriteria(VehicleInspectionsImagensCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VehicleInspectionsImagens> specification = createSpecification(criteria);
        return vehicleInspectionsImagensMapper.toDto(vehicleInspectionsImagensRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VehicleInspectionsImagensDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleInspectionsImagensDTO> findByCriteria(VehicleInspectionsImagensCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VehicleInspectionsImagens> specification = createSpecification(criteria);
        return vehicleInspectionsImagensRepository.findAll(specification, page).map(vehicleInspectionsImagensMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VehicleInspectionsImagensCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VehicleInspectionsImagens> specification = createSpecification(criteria);
        return vehicleInspectionsImagensRepository.count(specification);
    }

    /**
     * Function to convert {@link VehicleInspectionsImagensCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VehicleInspectionsImagens> createSpecification(VehicleInspectionsImagensCriteria criteria) {
        Specification<VehicleInspectionsImagens> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VehicleInspectionsImagens_.id));
            }
            if (criteria.getVehicleInspectionsImagensPositions() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleInspectionsImagensPositions(),
                            VehicleInspectionsImagens_.vehicleInspectionsImagensPositions
                        )
                    );
            }
            if (criteria.getVehicleInspectionsImagensStatus() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleInspectionsImagensStatus(),
                            VehicleInspectionsImagens_.vehicleInspectionsImagensStatus
                        )
                    );
            }
            if (criteria.getVehicleInspectionsImagensObs() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getVehicleInspectionsImagensObs(),
                            VehicleInspectionsImagens_.vehicleInspectionsImagensObs
                        )
                    );
            }
            if (criteria.getVehicleInspectionsImagensPositionsX() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getVehicleInspectionsImagensPositionsX(),
                            VehicleInspectionsImagens_.vehicleInspectionsImagensPositionsX
                        )
                    );
            }
            if (criteria.getVehicleInspectionsImagensPositionsY() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getVehicleInspectionsImagensPositionsY(),
                            VehicleInspectionsImagens_.vehicleInspectionsImagensPositionsY
                        )
                    );
            }
            if (criteria.getVehicleInspectionsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleInspectionsId(),
                            root -> root.join(VehicleInspectionsImagens_.vehicleInspections, JoinType.LEFT).get(VehicleInspections_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
