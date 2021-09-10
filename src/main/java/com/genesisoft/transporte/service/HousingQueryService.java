package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.Housing;
import com.genesisoft.transporte.repository.HousingRepository;
import com.genesisoft.transporte.service.criteria.HousingCriteria;
import com.genesisoft.transporte.service.dto.HousingDTO;
import com.genesisoft.transporte.service.mapper.HousingMapper;
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
 * Service for executing complex queries for {@link Housing} entities in the database.
 * The main input is a {@link HousingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HousingDTO} or a {@link Page} of {@link HousingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HousingQueryService extends QueryService<Housing> {

    private final Logger log = LoggerFactory.getLogger(HousingQueryService.class);

    private final HousingRepository housingRepository;

    private final HousingMapper housingMapper;

    public HousingQueryService(HousingRepository housingRepository, HousingMapper housingMapper) {
        this.housingRepository = housingRepository;
        this.housingMapper = housingMapper;
    }

    /**
     * Return a {@link List} of {@link HousingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HousingDTO> findByCriteria(HousingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Housing> specification = createSpecification(criteria);
        return housingMapper.toDto(housingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link HousingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HousingDTO> findByCriteria(HousingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Housing> specification = createSpecification(criteria);
        return housingRepository.findAll(specification, page).map(housingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HousingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Housing> specification = createSpecification(criteria);
        return housingRepository.count(specification);
    }

    /**
     * Function to convert {@link HousingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Housing> createSpecification(HousingCriteria criteria) {
        Specification<Housing> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Housing_.id));
            }
            if (criteria.getHousingDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHousingDate(), Housing_.housingDate));
            }
            if (criteria.getHousingEntranceDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHousingEntranceDate(), Housing_.housingEntranceDate));
            }
            if (criteria.getHousingExit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHousingExit(), Housing_.housingExit));
            }
            if (criteria.getHousingReceiptNumber() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getHousingReceiptNumber(), Housing_.housingReceiptNumber));
            }
            if (criteria.getHousingDailyPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHousingDailyPrice(), Housing_.housingDailyPrice));
            }
            if (criteria.getHousingDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHousingDescription(), Housing_.housingDescription));
            }
            if (criteria.getHousingVehicleItemId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getHousingVehicleItemId(),
                            root -> root.join(Housing_.housingVehicleItems, JoinType.LEFT).get(HousingVehicleItem_.id)
                        )
                    );
            }
            if (criteria.getAffiliatesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAffiliatesId(),
                            root -> root.join(Housing_.affiliates, JoinType.LEFT).get(Affiliates_.id)
                        )
                    );
            }
            if (criteria.getStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getStatusId(), root -> root.join(Housing_.status, JoinType.LEFT).get(Status_.id))
                    );
            }
            if (criteria.getCustomersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomersId(),
                            root -> root.join(Housing_.customers, JoinType.LEFT).get(Customers_.id)
                        )
                    );
            }
            if (criteria.getEmployeesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeesId(),
                            root -> root.join(Housing_.employees, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getParkingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getParkingId(), root -> root.join(Housing_.parking, JoinType.LEFT).get(Parking_.id))
                    );
            }
            if (criteria.getCostCenterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCostCenterId(),
                            root -> root.join(Housing_.costCenter, JoinType.LEFT).get(CostCenter_.id)
                        )
                    );
            }
            if (criteria.getSuppliersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSuppliersId(),
                            root -> root.join(Housing_.suppliers, JoinType.LEFT).get(Suppliers_.id)
                        )
                    );
            }
            if (criteria.getCitiesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCitiesId(), root -> root.join(Housing_.cities, JoinType.LEFT).get(Cities_.id))
                    );
            }
        }
        return specification;
    }
}
