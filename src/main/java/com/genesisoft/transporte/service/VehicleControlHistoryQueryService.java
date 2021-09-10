package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.VehicleControlHistory;
import com.genesisoft.transporte.repository.VehicleControlHistoryRepository;
import com.genesisoft.transporte.service.criteria.VehicleControlHistoryCriteria;
import com.genesisoft.transporte.service.dto.VehicleControlHistoryDTO;
import com.genesisoft.transporte.service.mapper.VehicleControlHistoryMapper;
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
 * Service for executing complex queries for {@link VehicleControlHistory} entities in the database.
 * The main input is a {@link VehicleControlHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VehicleControlHistoryDTO} or a {@link Page} of {@link VehicleControlHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VehicleControlHistoryQueryService extends QueryService<VehicleControlHistory> {

    private final Logger log = LoggerFactory.getLogger(VehicleControlHistoryQueryService.class);

    private final VehicleControlHistoryRepository vehicleControlHistoryRepository;

    private final VehicleControlHistoryMapper vehicleControlHistoryMapper;

    public VehicleControlHistoryQueryService(
        VehicleControlHistoryRepository vehicleControlHistoryRepository,
        VehicleControlHistoryMapper vehicleControlHistoryMapper
    ) {
        this.vehicleControlHistoryRepository = vehicleControlHistoryRepository;
        this.vehicleControlHistoryMapper = vehicleControlHistoryMapper;
    }

    /**
     * Return a {@link List} of {@link VehicleControlHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VehicleControlHistoryDTO> findByCriteria(VehicleControlHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VehicleControlHistory> specification = createSpecification(criteria);
        return vehicleControlHistoryMapper.toDto(vehicleControlHistoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VehicleControlHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleControlHistoryDTO> findByCriteria(VehicleControlHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VehicleControlHistory> specification = createSpecification(criteria);
        return vehicleControlHistoryRepository.findAll(specification, page).map(vehicleControlHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VehicleControlHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VehicleControlHistory> specification = createSpecification(criteria);
        return vehicleControlHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link VehicleControlHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VehicleControlHistory> createSpecification(VehicleControlHistoryCriteria criteria) {
        Specification<VehicleControlHistory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VehicleControlHistory_.id));
            }
            if (criteria.getVehicleControlHistoryDate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getVehicleControlHistoryDate(), VehicleControlHistory_.vehicleControlHistoryDate)
                    );
            }
            if (criteria.getVehicleControlHistoryDescription() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getVehicleControlHistoryDescription(),
                            VehicleControlHistory_.vehicleControlHistoryDescription
                        )
                    );
            }
            if (criteria.getVehicleControlsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlsId(),
                            root -> root.join(VehicleControlHistory_.vehicleControls, JoinType.LEFT).get(VehicleControls_.id)
                        )
                    );
            }
            if (criteria.getEmployeesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeesId(),
                            root -> root.join(VehicleControlHistory_.employees, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
