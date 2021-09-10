package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.VehicleControlExpenses;
import com.genesisoft.transporte.repository.VehicleControlExpensesRepository;
import com.genesisoft.transporte.service.criteria.VehicleControlExpensesCriteria;
import com.genesisoft.transporte.service.dto.VehicleControlExpensesDTO;
import com.genesisoft.transporte.service.mapper.VehicleControlExpensesMapper;
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
 * Service for executing complex queries for {@link VehicleControlExpenses} entities in the database.
 * The main input is a {@link VehicleControlExpensesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VehicleControlExpensesDTO} or a {@link Page} of {@link VehicleControlExpensesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VehicleControlExpensesQueryService extends QueryService<VehicleControlExpenses> {

    private final Logger log = LoggerFactory.getLogger(VehicleControlExpensesQueryService.class);

    private final VehicleControlExpensesRepository vehicleControlExpensesRepository;

    private final VehicleControlExpensesMapper vehicleControlExpensesMapper;

    public VehicleControlExpensesQueryService(
        VehicleControlExpensesRepository vehicleControlExpensesRepository,
        VehicleControlExpensesMapper vehicleControlExpensesMapper
    ) {
        this.vehicleControlExpensesRepository = vehicleControlExpensesRepository;
        this.vehicleControlExpensesMapper = vehicleControlExpensesMapper;
    }

    /**
     * Return a {@link List} of {@link VehicleControlExpensesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VehicleControlExpensesDTO> findByCriteria(VehicleControlExpensesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VehicleControlExpenses> specification = createSpecification(criteria);
        return vehicleControlExpensesMapper.toDto(vehicleControlExpensesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VehicleControlExpensesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleControlExpensesDTO> findByCriteria(VehicleControlExpensesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VehicleControlExpenses> specification = createSpecification(criteria);
        return vehicleControlExpensesRepository.findAll(specification, page).map(vehicleControlExpensesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VehicleControlExpensesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VehicleControlExpenses> specification = createSpecification(criteria);
        return vehicleControlExpensesRepository.count(specification);
    }

    /**
     * Function to convert {@link VehicleControlExpensesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VehicleControlExpenses> createSpecification(VehicleControlExpensesCriteria criteria) {
        Specification<VehicleControlExpenses> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VehicleControlExpenses_.id));
            }
            if (criteria.getVehicleControlExpensesDescription() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getVehicleControlExpensesDescription(),
                            VehicleControlExpenses_.vehicleControlExpensesDescription
                        )
                    );
            }
            if (criteria.getVehicleControlExpensesDriverType() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlExpensesDriverType(),
                            VehicleControlExpenses_.vehicleControlExpensesDriverType
                        )
                    );
            }
            if (criteria.getVehicleControlExpensesPurchaseOrder() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getVehicleControlExpensesPurchaseOrder(),
                            VehicleControlExpenses_.vehicleControlExpensesPurchaseOrder
                        )
                    );
            }
            if (criteria.getVehicleControlExpensesDueDate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getVehicleControlExpensesDueDate(),
                            VehicleControlExpenses_.vehicleControlExpensesDueDate
                        )
                    );
            }
            if (criteria.getVehicleControlExpensesPaymentDate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getVehicleControlExpensesPaymentDate(),
                            VehicleControlExpenses_.vehicleControlExpensesPaymentDate
                        )
                    );
            }
            if (criteria.getVehicleControlExpensesBillingTotalValue() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getVehicleControlExpensesBillingTotalValue(),
                            VehicleControlExpenses_.vehicleControlExpensesBillingTotalValue
                        )
                    );
            }
            if (criteria.getVehicleControlExpensesDriverCommission() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlExpensesDriverCommission(),
                            VehicleControlExpenses_.vehicleControlExpensesDriverCommission
                        )
                    );
            }
            if (criteria.getVehicleControlsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlsId(),
                            root -> root.join(VehicleControlExpenses_.vehicleControls, JoinType.LEFT).get(VehicleControls_.id)
                        )
                    );
            }
            if (criteria.getSuppliersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSuppliersId(),
                            root -> root.join(VehicleControlExpenses_.suppliers, JoinType.LEFT).get(Suppliers_.id)
                        )
                    );
            }
            if (criteria.getOriginId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOriginId(),
                            root -> root.join(VehicleControlExpenses_.origin, JoinType.LEFT).get(Cities_.id)
                        )
                    );
            }
            if (criteria.getDestinationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDestinationId(),
                            root -> root.join(VehicleControlExpenses_.destination, JoinType.LEFT).get(Cities_.id)
                        )
                    );
            }
            if (criteria.getVehicleControlItemId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlItemId(),
                            root -> root.join(VehicleControlExpenses_.vehicleControlItem, JoinType.LEFT).get(VehicleControlItem_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
