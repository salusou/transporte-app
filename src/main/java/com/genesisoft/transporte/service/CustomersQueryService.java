package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.Customers;
import com.genesisoft.transporte.repository.CustomersRepository;
import com.genesisoft.transporte.service.criteria.CustomersCriteria;
import com.genesisoft.transporte.service.dto.CustomersDTO;
import com.genesisoft.transporte.service.mapper.CustomersMapper;
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
 * Service for executing complex queries for {@link Customers} entities in the database.
 * The main input is a {@link CustomersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomersDTO} or a {@link Page} of {@link CustomersDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomersQueryService extends QueryService<Customers> {

    private final Logger log = LoggerFactory.getLogger(CustomersQueryService.class);

    private final CustomersRepository customersRepository;

    private final CustomersMapper customersMapper;

    public CustomersQueryService(CustomersRepository customersRepository, CustomersMapper customersMapper) {
        this.customersRepository = customersRepository;
        this.customersMapper = customersMapper;
    }

    /**
     * Return a {@link List} of {@link CustomersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomersDTO> findByCriteria(CustomersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Customers> specification = createSpecification(criteria);
        return customersMapper.toDto(customersRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustomersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomersDTO> findByCriteria(CustomersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Customers> specification = createSpecification(criteria);
        return customersRepository.findAll(specification, page).map(customersMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Customers> specification = createSpecification(criteria);
        return customersRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Customers> createSpecification(CustomersCriteria criteria) {
        Specification<Customers> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Customers_.id));
            }
            if (criteria.getCustomerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomerName(), Customers_.customerName));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), Customers_.active));
            }
            if (criteria.getCustomerNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomerNumber(), Customers_.customerNumber));
            }
            if (criteria.getCustomerPostalCode() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCustomerPostalCode(), Customers_.customerPostalCode));
            }
            if (criteria.getCustomerAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomerAddress(), Customers_.customerAddress));
            }
            if (criteria.getCustomerAddressComplement() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getCustomerAddressComplement(), Customers_.customerAddressComplement)
                    );
            }
            if (criteria.getCustomerAddressNumber() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCustomerAddressNumber(), Customers_.customerAddressNumber));
            }
            if (criteria.getCustomerAddressNeighborhood() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getCustomerAddressNeighborhood(), Customers_.customerAddressNeighborhood)
                    );
            }
            if (criteria.getCustomerTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomerTelephone(), Customers_.customerTelephone));
            }
            if (criteria.getPaymentTerm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentTerm(), Customers_.paymentTerm));
            }
            if (criteria.getCustomersContactsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomersContactsId(),
                            root -> root.join(Customers_.customersContacts, JoinType.LEFT).get(CustomersContacts_.id)
                        )
                    );
            }
            if (criteria.getCustomerAttachmentsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomerAttachmentsId(),
                            root -> root.join(Customers_.customerAttachments, JoinType.LEFT).get(CustomerAttachments_.id)
                        )
                    );
            }
            if (criteria.getVehicleControlsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlsId(),
                            root -> root.join(Customers_.vehicleControls, JoinType.LEFT).get(VehicleControls_.id)
                        )
                    );
            }
            if (criteria.getHousingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getHousingId(), root -> root.join(Customers_.housings, JoinType.LEFT).get(Housing_.id))
                    );
            }
            if (criteria.getAffiliatesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAffiliatesId(),
                            root -> root.join(Customers_.affiliates, JoinType.LEFT).get(Affiliates_.id)
                        )
                    );
            }
            if (criteria.getCitiesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCitiesId(), root -> root.join(Customers_.cities, JoinType.LEFT).get(Cities_.id))
                    );
            }
            if (criteria.getCustomersGroupsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomersGroupsId(),
                            root -> root.join(Customers_.customersGroups, JoinType.LEFT).get(CustomersGroups_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
