package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.Suppliers;
import com.genesisoft.transporte.repository.SuppliersRepository;
import com.genesisoft.transporte.service.criteria.SuppliersCriteria;
import com.genesisoft.transporte.service.dto.SuppliersDTO;
import com.genesisoft.transporte.service.mapper.SuppliersMapper;
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
 * Service for executing complex queries for {@link Suppliers} entities in the database.
 * The main input is a {@link SuppliersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SuppliersDTO} or a {@link Page} of {@link SuppliersDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SuppliersQueryService extends QueryService<Suppliers> {

    private final Logger log = LoggerFactory.getLogger(SuppliersQueryService.class);

    private final SuppliersRepository suppliersRepository;

    private final SuppliersMapper suppliersMapper;

    public SuppliersQueryService(SuppliersRepository suppliersRepository, SuppliersMapper suppliersMapper) {
        this.suppliersRepository = suppliersRepository;
        this.suppliersMapper = suppliersMapper;
    }

    /**
     * Return a {@link List} of {@link SuppliersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SuppliersDTO> findByCriteria(SuppliersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Suppliers> specification = createSpecification(criteria);
        return suppliersMapper.toDto(suppliersRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SuppliersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SuppliersDTO> findByCriteria(SuppliersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Suppliers> specification = createSpecification(criteria);
        return suppliersRepository.findAll(specification, page).map(suppliersMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SuppliersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Suppliers> specification = createSpecification(criteria);
        return suppliersRepository.count(specification);
    }

    /**
     * Function to convert {@link SuppliersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Suppliers> createSpecification(SuppliersCriteria criteria) {
        Specification<Suppliers> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Suppliers_.id));
            }
            if (criteria.getSupplierName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSupplierName(), Suppliers_.supplierName));
            }
            if (criteria.getSupplierNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSupplierNumber(), Suppliers_.supplierNumber));
            }
            if (criteria.getSupplierPostalCode() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSupplierPostalCode(), Suppliers_.supplierPostalCode));
            }
            if (criteria.getSupplierAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSupplierAddress(), Suppliers_.supplierAddress));
            }
            if (criteria.getSupplierAddressComplement() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getSupplierAddressComplement(), Suppliers_.supplierAddressComplement)
                    );
            }
            if (criteria.getSupplierAddressNumber() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSupplierAddressNumber(), Suppliers_.supplierAddressNumber));
            }
            if (criteria.getSupplierAddressNeighborhood() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getSupplierAddressNeighborhood(), Suppliers_.supplierAddressNeighborhood)
                    );
            }
            if (criteria.getSupplierTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSupplierTelephone(), Suppliers_.supplierTelephone));
            }
            if (criteria.getSupplierEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSupplierEmail(), Suppliers_.supplierEmail));
            }
            if (criteria.getSupplierContactName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSupplierContactName(), Suppliers_.supplierContactName));
            }
            if (criteria.getSupplierBanksInfoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSupplierBanksInfoId(),
                            root -> root.join(Suppliers_.supplierBanksInfos, JoinType.LEFT).get(SupplierBanksInfo_.id)
                        )
                    );
            }
            if (criteria.getSupplierContactsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSupplierContactsId(),
                            root -> root.join(Suppliers_.supplierContacts, JoinType.LEFT).get(SupplierContacts_.id)
                        )
                    );
            }
            if (criteria.getVehicleControlExpensesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlExpensesId(),
                            root -> root.join(Suppliers_.vehicleControlExpenses, JoinType.LEFT).get(VehicleControlExpenses_.id)
                        )
                    );
            }
            if (criteria.getHousingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getHousingId(), root -> root.join(Suppliers_.housings, JoinType.LEFT).get(Housing_.id))
                    );
            }
            if (criteria.getAffiliatesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAffiliatesId(),
                            root -> root.join(Suppliers_.affiliates, JoinType.LEFT).get(Affiliates_.id)
                        )
                    );
            }
            if (criteria.getCitiesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCitiesId(), root -> root.join(Suppliers_.cities, JoinType.LEFT).get(Cities_.id))
                    );
            }
            if (criteria.getServiceProvidedId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getServiceProvidedId(),
                            root -> root.join(Suppliers_.serviceProvided, JoinType.LEFT).get(ServiceProvided_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
