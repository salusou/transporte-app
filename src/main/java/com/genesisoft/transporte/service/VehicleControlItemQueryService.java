package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.VehicleControlItem;
import com.genesisoft.transporte.repository.VehicleControlItemRepository;
import com.genesisoft.transporte.service.criteria.VehicleControlItemCriteria;
import com.genesisoft.transporte.service.dto.VehicleControlItemDTO;
import com.genesisoft.transporte.service.mapper.VehicleControlItemMapper;
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
 * Service for executing complex queries for {@link VehicleControlItem} entities in the database.
 * The main input is a {@link VehicleControlItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VehicleControlItemDTO} or a {@link Page} of {@link VehicleControlItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VehicleControlItemQueryService extends QueryService<VehicleControlItem> {

    private final Logger log = LoggerFactory.getLogger(VehicleControlItemQueryService.class);

    private final VehicleControlItemRepository vehicleControlItemRepository;

    private final VehicleControlItemMapper vehicleControlItemMapper;

    public VehicleControlItemQueryService(
        VehicleControlItemRepository vehicleControlItemRepository,
        VehicleControlItemMapper vehicleControlItemMapper
    ) {
        this.vehicleControlItemRepository = vehicleControlItemRepository;
        this.vehicleControlItemMapper = vehicleControlItemMapper;
    }

    /**
     * Return a {@link List} of {@link VehicleControlItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VehicleControlItemDTO> findByCriteria(VehicleControlItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VehicleControlItem> specification = createSpecification(criteria);
        return vehicleControlItemMapper.toDto(vehicleControlItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VehicleControlItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleControlItemDTO> findByCriteria(VehicleControlItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VehicleControlItem> specification = createSpecification(criteria);
        return vehicleControlItemRepository.findAll(specification, page).map(vehicleControlItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VehicleControlItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VehicleControlItem> specification = createSpecification(criteria);
        return vehicleControlItemRepository.count(specification);
    }

    /**
     * Function to convert {@link VehicleControlItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VehicleControlItem> createSpecification(VehicleControlItemCriteria criteria) {
        Specification<VehicleControlItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VehicleControlItem_.id));
            }
            if (criteria.getVehicleControlStatus() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getVehicleControlStatus(), VehicleControlItem_.vehicleControlStatus));
            }
            if (criteria.getVehicleControlItemPlate() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getVehicleControlItemPlate(), VehicleControlItem_.vehicleControlItemPlate)
                    );
            }
            if (criteria.getVehicleControlItemType() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getVehicleControlItemType(), VehicleControlItem_.vehicleControlItemType));
            }
            if (criteria.getVehicleControlItemFipeCode() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getVehicleControlItemFipeCode(), VehicleControlItem_.vehicleControlItemFipeCode)
                    );
            }
            if (criteria.getVehicleControlItemYear() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getVehicleControlItemYear(), VehicleControlItem_.vehicleControlItemYear)
                    );
            }
            if (criteria.getVehicleControlItemFuel() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getVehicleControlItemFuel(), VehicleControlItem_.vehicleControlItemFuel)
                    );
            }
            if (criteria.getVehicleControlItemBranch() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getVehicleControlItemBranch(), VehicleControlItem_.vehicleControlItemBranch)
                    );
            }
            if (criteria.getVehicleControlItemModel() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getVehicleControlItemModel(), VehicleControlItem_.vehicleControlItemModel)
                    );
            }
            if (criteria.getVehicleControlItemFuelAbbreviation() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getVehicleControlItemFuelAbbreviation(),
                            VehicleControlItem_.vehicleControlItemFuelAbbreviation
                        )
                    );
            }
            if (criteria.getVehicleControlItemReferenceMonth() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getVehicleControlItemReferenceMonth(),
                            VehicleControlItem_.vehicleControlItemReferenceMonth
                        )
                    );
            }
            if (criteria.getVehicleControlItemValue() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getVehicleControlItemValue(), VehicleControlItem_.vehicleControlItemValue)
                    );
            }
            if (criteria.getVehicleControlItemShippingValue() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getVehicleControlItemShippingValue(),
                            VehicleControlItem_.vehicleControlItemShippingValue
                        )
                    );
            }
            if (criteria.getVehicleControlItemCTE() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getVehicleControlItemCTE(), VehicleControlItem_.vehicleControlItemCTE)
                    );
            }
            if (criteria.getVehicleControlItemCTEDate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getVehicleControlItemCTEDate(), VehicleControlItem_.vehicleControlItemCTEDate)
                    );
            }
            if (criteria.getVehicleInspectionsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleInspectionsId(),
                            root -> root.join(VehicleControlItem_.vehicleInspections, JoinType.LEFT).get(VehicleInspections_.id)
                        )
                    );
            }
            if (criteria.getVehicleControlExpensesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlExpensesId(),
                            root -> root.join(VehicleControlItem_.vehicleControlExpenses, JoinType.LEFT).get(VehicleControlExpenses_.id)
                        )
                    );
            }
            if (criteria.getVehicleControlsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlsId(),
                            root -> root.join(VehicleControlItem_.vehicleControls, JoinType.LEFT).get(VehicleControls_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
