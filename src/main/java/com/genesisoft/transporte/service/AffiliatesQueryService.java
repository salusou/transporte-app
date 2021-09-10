package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.Affiliates;
import com.genesisoft.transporte.repository.AffiliatesRepository;
import com.genesisoft.transporte.service.criteria.AffiliatesCriteria;
import com.genesisoft.transporte.service.dto.AffiliatesDTO;
import com.genesisoft.transporte.service.mapper.AffiliatesMapper;
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
 * Service for executing complex queries for {@link Affiliates} entities in the database.
 * The main input is a {@link AffiliatesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AffiliatesDTO} or a {@link Page} of {@link AffiliatesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AffiliatesQueryService extends QueryService<Affiliates> {

    private final Logger log = LoggerFactory.getLogger(AffiliatesQueryService.class);

    private final AffiliatesRepository affiliatesRepository;

    private final AffiliatesMapper affiliatesMapper;

    public AffiliatesQueryService(AffiliatesRepository affiliatesRepository, AffiliatesMapper affiliatesMapper) {
        this.affiliatesRepository = affiliatesRepository;
        this.affiliatesMapper = affiliatesMapper;
    }

    /**
     * Return a {@link List} of {@link AffiliatesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AffiliatesDTO> findByCriteria(AffiliatesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Affiliates> specification = createSpecification(criteria);
        return affiliatesMapper.toDto(affiliatesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AffiliatesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AffiliatesDTO> findByCriteria(AffiliatesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Affiliates> specification = createSpecification(criteria);
        return affiliatesRepository.findAll(specification, page).map(affiliatesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AffiliatesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Affiliates> specification = createSpecification(criteria);
        return affiliatesRepository.count(specification);
    }

    /**
     * Function to convert {@link AffiliatesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Affiliates> createSpecification(AffiliatesCriteria criteria) {
        Specification<Affiliates> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Affiliates_.id));
            }
            if (criteria.getBranchName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchName(), Affiliates_.branchName));
            }
            if (criteria.getBranchNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchNumber(), Affiliates_.branchNumber));
            }
            if (criteria.getUseSameCompanyAddress() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getUseSameCompanyAddress(), Affiliates_.useSameCompanyAddress));
            }
            if (criteria.getBranchPostalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchPostalCode(), Affiliates_.branchPostalCode));
            }
            if (criteria.getBranchAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchAddress(), Affiliates_.branchAddress));
            }
            if (criteria.getBranchAddressComplement() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getBranchAddressComplement(), Affiliates_.branchAddressComplement));
            }
            if (criteria.getBranchAddressNumber() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getBranchAddressNumber(), Affiliates_.branchAddressNumber));
            }
            if (criteria.getBranchAddressNeighborhood() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getBranchAddressNeighborhood(), Affiliates_.branchAddressNeighborhood)
                    );
            }
            if (criteria.getBranchTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchTelephone(), Affiliates_.branchTelephone));
            }
            if (criteria.getBranchEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchEmail(), Affiliates_.branchEmail));
            }
            if (criteria.getResponsibleContact() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getResponsibleContact(), Affiliates_.responsibleContact));
            }
            if (criteria.getInsurancesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInsurancesId(),
                            root -> root.join(Affiliates_.insurances, JoinType.LEFT).get(Insurances_.id)
                        )
                    );
            }
            if (criteria.getPositionsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPositionsId(),
                            root -> root.join(Affiliates_.positions, JoinType.LEFT).get(Positions_.id)
                        )
                    );
            }
            if (criteria.getCostCenterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCostCenterId(),
                            root -> root.join(Affiliates_.costCenters, JoinType.LEFT).get(CostCenter_.id)
                        )
                    );
            }
            if (criteria.getAdministrativeFeesRangesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAdministrativeFeesRangesId(),
                            root -> root.join(Affiliates_.administrativeFeesRanges, JoinType.LEFT).get(AdministrativeFeesRanges_.id)
                        )
                    );
            }
            if (criteria.getCustomersGroupsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomersGroupsId(),
                            root -> root.join(Affiliates_.customersGroups, JoinType.LEFT).get(CustomersGroups_.id)
                        )
                    );
            }
            if (criteria.getFeesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getFeesId(), root -> root.join(Affiliates_.fees, JoinType.LEFT).get(Fees_.id))
                    );
            }
            if (criteria.getCustomersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomersId(),
                            root -> root.join(Affiliates_.customers, JoinType.LEFT).get(Customers_.id)
                        )
                    );
            }
            if (criteria.getStatusAttachmentsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStatusAttachmentsId(),
                            root -> root.join(Affiliates_.statusAttachments, JoinType.LEFT).get(StatusAttachments_.id)
                        )
                    );
            }
            if (criteria.getStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getStatusId(), root -> root.join(Affiliates_.statuses, JoinType.LEFT).get(Status_.id))
                    );
            }
            if (criteria.getParkingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getParkingId(), root -> root.join(Affiliates_.parkings, JoinType.LEFT).get(Parking_.id))
                    );
            }
            if (criteria.getSuppliersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSuppliersId(),
                            root -> root.join(Affiliates_.suppliers, JoinType.LEFT).get(Suppliers_.id)
                        )
                    );
            }
            if (criteria.getEmployeesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeesId(),
                            root -> root.join(Affiliates_.employees, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getVehicleControlsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlsId(),
                            root -> root.join(Affiliates_.vehicleControls, JoinType.LEFT).get(VehicleControls_.id)
                        )
                    );
            }
            if (criteria.getHousingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getHousingId(), root -> root.join(Affiliates_.housings, JoinType.LEFT).get(Housing_.id))
                    );
            }
            if (criteria.getStateProvincesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStateProvincesId(),
                            root -> root.join(Affiliates_.stateProvinces, JoinType.LEFT).get(StateProvinces_.id)
                        )
                    );
            }
            if (criteria.getCitiesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCitiesId(), root -> root.join(Affiliates_.cities, JoinType.LEFT).get(Cities_.id))
                    );
            }
            if (criteria.getCompaniesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCompaniesId(),
                            root -> root.join(Affiliates_.companies, JoinType.LEFT).get(Companies_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
