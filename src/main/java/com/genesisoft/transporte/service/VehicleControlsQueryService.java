package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.VehicleControls;
import com.genesisoft.transporte.repository.VehicleControlsRepository;
import com.genesisoft.transporte.service.criteria.VehicleControlsCriteria;
import com.genesisoft.transporte.service.dto.VehicleControlsDTO;
import com.genesisoft.transporte.service.mapper.VehicleControlsMapper;
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
 * Service for executing complex queries for {@link VehicleControls} entities in the database.
 * The main input is a {@link VehicleControlsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VehicleControlsDTO} or a {@link Page} of {@link VehicleControlsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VehicleControlsQueryService extends QueryService<VehicleControls> {

    private final Logger log = LoggerFactory.getLogger(VehicleControlsQueryService.class);

    private final VehicleControlsRepository vehicleControlsRepository;

    private final VehicleControlsMapper vehicleControlsMapper;

    public VehicleControlsQueryService(VehicleControlsRepository vehicleControlsRepository, VehicleControlsMapper vehicleControlsMapper) {
        this.vehicleControlsRepository = vehicleControlsRepository;
        this.vehicleControlsMapper = vehicleControlsMapper;
    }

    /**
     * Return a {@link List} of {@link VehicleControlsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VehicleControlsDTO> findByCriteria(VehicleControlsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VehicleControls> specification = createSpecification(criteria);
        return vehicleControlsMapper.toDto(vehicleControlsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VehicleControlsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleControlsDTO> findByCriteria(VehicleControlsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VehicleControls> specification = createSpecification(criteria);
        return vehicleControlsRepository.findAll(specification, page).map(vehicleControlsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VehicleControlsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VehicleControls> specification = createSpecification(criteria);
        return vehicleControlsRepository.count(specification);
    }

    /**
     * Function to convert {@link VehicleControlsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VehicleControls> createSpecification(VehicleControlsCriteria criteria) {
        Specification<VehicleControls> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VehicleControls_.id));
            }
            if (criteria.getVehicleControlAuthorizedOrder() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getVehicleControlAuthorizedOrder(),
                            VehicleControls_.vehicleControlAuthorizedOrder
                        )
                    );
            }
            if (criteria.getVehicleControlRequest() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getVehicleControlRequest(), VehicleControls_.vehicleControlRequest)
                    );
            }
            if (criteria.getVehicleControlSinister() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getVehicleControlSinister(), VehicleControls_.vehicleControlSinister)
                    );
            }
            if (criteria.getVehicleControlDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getVehicleControlDate(), VehicleControls_.vehicleControlDate));
            }
            if (criteria.getVehicleControlKm() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getVehicleControlKm(), VehicleControls_.vehicleControlKm));
            }
            if (criteria.getVehicleControlPlate() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getVehicleControlPlate(), VehicleControls_.vehicleControlPlate));
            }
            if (criteria.getVehicleControlAmount() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getVehicleControlAmount(), VehicleControls_.vehicleControlAmount));
            }
            if (criteria.getVehicleControlPrice() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getVehicleControlPrice(), VehicleControls_.vehicleControlPrice));
            }
            if (criteria.getVehicleControlMaximumDeliveryDate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getVehicleControlMaximumDeliveryDate(),
                            VehicleControls_.vehicleControlMaximumDeliveryDate
                        )
                    );
            }
            if (criteria.getVehicleControlCollectionForecast() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getVehicleControlCollectionForecast(),
                            VehicleControls_.vehicleControlCollectionForecast
                        )
                    );
            }
            if (criteria.getVehicleControlCollectionDeliveryForecast() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getVehicleControlCollectionDeliveryForecast(),
                            VehicleControls_.vehicleControlCollectionDeliveryForecast
                        )
                    );
            }
            if (criteria.getVehicleControlDateCollected() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getVehicleControlDateCollected(), VehicleControls_.vehicleControlDateCollected)
                    );
            }
            if (criteria.getVehicleControlDeliveryDate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getVehicleControlDeliveryDate(), VehicleControls_.vehicleControlDeliveryDate)
                    );
            }
            if (criteria.getVehicleLocationStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleLocationStatusId(),
                            root -> root.join(VehicleControls_.vehicleLocationStatuses, JoinType.LEFT).get(VehicleLocationStatus_.id)
                        )
                    );
            }
            if (criteria.getVehicleControlHistoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlHistoryId(),
                            root -> root.join(VehicleControls_.vehicleControlHistories, JoinType.LEFT).get(VehicleControlHistory_.id)
                        )
                    );
            }
            if (criteria.getVehicleControlBillingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlBillingId(),
                            root -> root.join(VehicleControls_.vehicleControlBillings, JoinType.LEFT).get(VehicleControlBilling_.id)
                        )
                    );
            }
            if (criteria.getVehicleControlItemId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlItemId(),
                            root -> root.join(VehicleControls_.vehicleControlItems, JoinType.LEFT).get(VehicleControlItem_.id)
                        )
                    );
            }
            if (criteria.getVehicleControlAttachmentsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlAttachmentsId(),
                            root -> root.join(VehicleControls_.vehicleControlAttachments, JoinType.LEFT).get(VehicleControlAttachments_.id)
                        )
                    );
            }
            if (criteria.getVehicleControlExpensesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlExpensesId(),
                            root -> root.join(VehicleControls_.vehicleControlExpenses, JoinType.LEFT).get(VehicleControlExpenses_.id)
                        )
                    );
            }
            if (criteria.getAffiliatesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAffiliatesId(),
                            root -> root.join(VehicleControls_.affiliates, JoinType.LEFT).get(Affiliates_.id)
                        )
                    );
            }
            if (criteria.getCustomersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomersId(),
                            root -> root.join(VehicleControls_.customers, JoinType.LEFT).get(Customers_.id)
                        )
                    );
            }
            if (criteria.getCustomersGroupsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomersGroupsId(),
                            root -> root.join(VehicleControls_.customersGroups, JoinType.LEFT).get(CustomersGroups_.id)
                        )
                    );
            }
            if (criteria.getEmployeesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeesId(),
                            root -> root.join(VehicleControls_.employees, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getOriginId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOriginId(),
                            root -> root.join(VehicleControls_.origin, JoinType.LEFT).get(Cities_.id)
                        )
                    );
            }
            if (criteria.getDestinationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDestinationId(),
                            root -> root.join(VehicleControls_.destination, JoinType.LEFT).get(Cities_.id)
                        )
                    );
            }
            if (criteria.getStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStatusId(),
                            root -> root.join(VehicleControls_.status, JoinType.LEFT).get(Status_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
