package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.VehicleControlBilling;
import com.genesisoft.transporte.repository.VehicleControlBillingRepository;
import com.genesisoft.transporte.service.criteria.VehicleControlBillingCriteria;
import com.genesisoft.transporte.service.dto.VehicleControlBillingDTO;
import com.genesisoft.transporte.service.mapper.VehicleControlBillingMapper;
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
 * Service for executing complex queries for {@link VehicleControlBilling} entities in the database.
 * The main input is a {@link VehicleControlBillingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VehicleControlBillingDTO} or a {@link Page} of {@link VehicleControlBillingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VehicleControlBillingQueryService extends QueryService<VehicleControlBilling> {

    private final Logger log = LoggerFactory.getLogger(VehicleControlBillingQueryService.class);

    private final VehicleControlBillingRepository vehicleControlBillingRepository;

    private final VehicleControlBillingMapper vehicleControlBillingMapper;

    public VehicleControlBillingQueryService(
        VehicleControlBillingRepository vehicleControlBillingRepository,
        VehicleControlBillingMapper vehicleControlBillingMapper
    ) {
        this.vehicleControlBillingRepository = vehicleControlBillingRepository;
        this.vehicleControlBillingMapper = vehicleControlBillingMapper;
    }

    /**
     * Return a {@link List} of {@link VehicleControlBillingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VehicleControlBillingDTO> findByCriteria(VehicleControlBillingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VehicleControlBilling> specification = createSpecification(criteria);
        return vehicleControlBillingMapper.toDto(vehicleControlBillingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VehicleControlBillingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleControlBillingDTO> findByCriteria(VehicleControlBillingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VehicleControlBilling> specification = createSpecification(criteria);
        return vehicleControlBillingRepository.findAll(specification, page).map(vehicleControlBillingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VehicleControlBillingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VehicleControlBilling> specification = createSpecification(criteria);
        return vehicleControlBillingRepository.count(specification);
    }

    /**
     * Function to convert {@link VehicleControlBillingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VehicleControlBilling> createSpecification(VehicleControlBillingCriteria criteria) {
        Specification<VehicleControlBilling> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VehicleControlBilling_.id));
            }
            if (criteria.getVehicleControlBillingDate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getVehicleControlBillingDate(), VehicleControlBilling_.vehicleControlBillingDate)
                    );
            }
            if (criteria.getVehicleControlBillingExpirationDate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getVehicleControlBillingExpirationDate(),
                            VehicleControlBilling_.vehicleControlBillingExpirationDate
                        )
                    );
            }
            if (criteria.getVehicleControlBillingPaymentDate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getVehicleControlBillingPaymentDate(),
                            VehicleControlBilling_.vehicleControlBillingPaymentDate
                        )
                    );
            }
            if (criteria.getVehicleControlBillingSellerCommission() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlBillingSellerCommission(),
                            VehicleControlBilling_.vehicleControlBillingSellerCommission
                        )
                    );
            }
            if (criteria.getVehicleControlBillingDriverCommission() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlBillingDriverCommission(),
                            VehicleControlBilling_.vehicleControlBillingDriverCommission
                        )
                    );
            }
            if (criteria.getVehicleControlBillingAmount() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getVehicleControlBillingAmount(),
                            VehicleControlBilling_.vehicleControlBillingAmount
                        )
                    );
            }
            if (criteria.getVehicleControlBillingTotalValue() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getVehicleControlBillingTotalValue(),
                            VehicleControlBilling_.vehicleControlBillingTotalValue
                        )
                    );
            }
            if (criteria.getVehicleControlBillingInsuranceDiscount() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getVehicleControlBillingInsuranceDiscount(),
                            VehicleControlBilling_.vehicleControlBillingInsuranceDiscount
                        )
                    );
            }
            if (criteria.getVehicleControlBillingCustomerBank() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getVehicleControlBillingCustomerBank(),
                            VehicleControlBilling_.vehicleControlBillingCustomerBank
                        )
                    );
            }
            if (criteria.getVehicleControlBillingAnticipate() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlBillingAnticipate(),
                            VehicleControlBilling_.vehicleControlBillingAnticipate
                        )
                    );
            }
            if (criteria.getVehicleControlBillingManifest() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getVehicleControlBillingManifest(),
                            VehicleControlBilling_.vehicleControlBillingManifest
                        )
                    );
            }
            if (criteria.getVehicleControlsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlsId(),
                            root -> root.join(VehicleControlBilling_.vehicleControls, JoinType.LEFT).get(VehicleControls_.id)
                        )
                    );
            }
            if (criteria.getFeesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFeesId(),
                            root -> root.join(VehicleControlBilling_.fees, JoinType.LEFT).get(Fees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
