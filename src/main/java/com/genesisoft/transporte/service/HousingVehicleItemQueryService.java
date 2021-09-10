package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.HousingVehicleItem;
import com.genesisoft.transporte.repository.HousingVehicleItemRepository;
import com.genesisoft.transporte.service.criteria.HousingVehicleItemCriteria;
import com.genesisoft.transporte.service.dto.HousingVehicleItemDTO;
import com.genesisoft.transporte.service.mapper.HousingVehicleItemMapper;
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
 * Service for executing complex queries for {@link HousingVehicleItem} entities in the database.
 * The main input is a {@link HousingVehicleItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HousingVehicleItemDTO} or a {@link Page} of {@link HousingVehicleItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HousingVehicleItemQueryService extends QueryService<HousingVehicleItem> {

    private final Logger log = LoggerFactory.getLogger(HousingVehicleItemQueryService.class);

    private final HousingVehicleItemRepository housingVehicleItemRepository;

    private final HousingVehicleItemMapper housingVehicleItemMapper;

    public HousingVehicleItemQueryService(
        HousingVehicleItemRepository housingVehicleItemRepository,
        HousingVehicleItemMapper housingVehicleItemMapper
    ) {
        this.housingVehicleItemRepository = housingVehicleItemRepository;
        this.housingVehicleItemMapper = housingVehicleItemMapper;
    }

    /**
     * Return a {@link List} of {@link HousingVehicleItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HousingVehicleItemDTO> findByCriteria(HousingVehicleItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<HousingVehicleItem> specification = createSpecification(criteria);
        return housingVehicleItemMapper.toDto(housingVehicleItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link HousingVehicleItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HousingVehicleItemDTO> findByCriteria(HousingVehicleItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<HousingVehicleItem> specification = createSpecification(criteria);
        return housingVehicleItemRepository.findAll(specification, page).map(housingVehicleItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HousingVehicleItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<HousingVehicleItem> specification = createSpecification(criteria);
        return housingVehicleItemRepository.count(specification);
    }

    /**
     * Function to convert {@link HousingVehicleItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<HousingVehicleItem> createSpecification(HousingVehicleItemCriteria criteria) {
        Specification<HousingVehicleItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), HousingVehicleItem_.id));
            }
            if (criteria.getHousingVehicleItemStatus() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getHousingVehicleItemStatus(), HousingVehicleItem_.housingVehicleItemStatus)
                    );
            }
            if (criteria.getHousingVehicleItemPlate() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getHousingVehicleItemPlate(), HousingVehicleItem_.housingVehicleItemPlate)
                    );
            }
            if (criteria.getHousingVehicleItemType() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getHousingVehicleItemType(), HousingVehicleItem_.housingVehicleItemType));
            }
            if (criteria.getHousingVehicleItemFipeCode() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getHousingVehicleItemFipeCode(), HousingVehicleItem_.housingVehicleItemFipeCode)
                    );
            }
            if (criteria.getHousingVehicleItemYear() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getHousingVehicleItemYear(), HousingVehicleItem_.housingVehicleItemYear)
                    );
            }
            if (criteria.getHousingVehicleItemFuel() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getHousingVehicleItemFuel(), HousingVehicleItem_.housingVehicleItemFuel)
                    );
            }
            if (criteria.getHousingVehicleItemBranch() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getHousingVehicleItemBranch(), HousingVehicleItem_.housingVehicleItemBranch)
                    );
            }
            if (criteria.getHousingVehicleItemModel() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getHousingVehicleItemModel(), HousingVehicleItem_.housingVehicleItemModel)
                    );
            }
            if (criteria.getHousingVehicleItemFuelAbbreviation() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getHousingVehicleItemFuelAbbreviation(),
                            HousingVehicleItem_.housingVehicleItemFuelAbbreviation
                        )
                    );
            }
            if (criteria.getHousingVehicleItemReferenceMonth() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getHousingVehicleItemReferenceMonth(),
                            HousingVehicleItem_.housingVehicleItemReferenceMonth
                        )
                    );
            }
            if (criteria.getHousingVehicleItemValue() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getHousingVehicleItemValue(), HousingVehicleItem_.housingVehicleItemValue)
                    );
            }
            if (criteria.getHousingVehicleItemShippingValue() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getHousingVehicleItemShippingValue(),
                            HousingVehicleItem_.housingVehicleItemShippingValue
                        )
                    );
            }
            if (criteria.getHousingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getHousingId(),
                            root -> root.join(HousingVehicleItem_.housing, JoinType.LEFT).get(Housing_.id)
                        )
                    );
            }
            if (criteria.getParkingSectorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getParkingSectorId(),
                            root -> root.join(HousingVehicleItem_.parkingSector, JoinType.LEFT).get(ParkingSector_.id)
                        )
                    );
            }
            if (criteria.getParkingSectorSpaceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getParkingSectorSpaceId(),
                            root -> root.join(HousingVehicleItem_.parkingSectorSpace, JoinType.LEFT).get(ParkingSectorSpace_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
