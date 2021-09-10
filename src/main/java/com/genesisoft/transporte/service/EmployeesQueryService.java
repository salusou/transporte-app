package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.Employees;
import com.genesisoft.transporte.repository.EmployeesRepository;
import com.genesisoft.transporte.service.criteria.EmployeesCriteria;
import com.genesisoft.transporte.service.dto.EmployeesDTO;
import com.genesisoft.transporte.service.mapper.EmployeesMapper;
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
 * Service for executing complex queries for {@link Employees} entities in the database.
 * The main input is a {@link EmployeesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeesDTO} or a {@link Page} of {@link EmployeesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeesQueryService extends QueryService<Employees> {

    private final Logger log = LoggerFactory.getLogger(EmployeesQueryService.class);

    private final EmployeesRepository employeesRepository;

    private final EmployeesMapper employeesMapper;

    public EmployeesQueryService(EmployeesRepository employeesRepository, EmployeesMapper employeesMapper) {
        this.employeesRepository = employeesRepository;
        this.employeesMapper = employeesMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeesDTO> findByCriteria(EmployeesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Employees> specification = createSpecification(criteria);
        return employeesMapper.toDto(employeesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeesDTO> findByCriteria(EmployeesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Employees> specification = createSpecification(criteria);
        return employeesRepository.findAll(specification, page).map(employeesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Employees> specification = createSpecification(criteria);
        return employeesRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Employees> createSpecification(EmployeesCriteria criteria) {
        Specification<Employees> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Employees_.id));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), Employees_.active));
            }
            if (criteria.getEmployeeFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployeeFullName(), Employees_.employeeFullName));
            }
            if (criteria.getEmployeeEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployeeEmail(), Employees_.employeeEmail));
            }
            if (criteria.getEmployeeIdentificationNumber() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getEmployeeIdentificationNumber(), Employees_.employeeIdentificationNumber)
                    );
            }
            if (criteria.getEmployeeNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployeeNumber(), Employees_.employeeNumber));
            }
            if (criteria.getEmployeePostalCode() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getEmployeePostalCode(), Employees_.employeePostalCode));
            }
            if (criteria.getEmployeeAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployeeAddress(), Employees_.employeeAddress));
            }
            if (criteria.getEmployeeAddressComplement() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getEmployeeAddressComplement(), Employees_.employeeAddressComplement)
                    );
            }
            if (criteria.getEmployeeAddressNumber() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getEmployeeAddressNumber(), Employees_.employeeAddressNumber));
            }
            if (criteria.getEmployeeAddressNeighborhood() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getEmployeeAddressNeighborhood(), Employees_.employeeAddressNeighborhood)
                    );
            }
            if (criteria.getEmployeeBirthday() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmployeeBirthday(), Employees_.employeeBirthday));
            }
            if (criteria.getEmployeeAttachmentsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeAttachmentsId(),
                            root -> root.join(Employees_.employeeAttachments, JoinType.LEFT).get(EmployeeAttachments_.id)
                        )
                    );
            }
            if (criteria.getEmployeeComponentsDataId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeComponentsDataId(),
                            root -> root.join(Employees_.employeeComponentsData, JoinType.LEFT).get(EmployeeComponentsData_.id)
                        )
                    );
            }
            if (criteria.getVehicleControlsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlsId(),
                            root -> root.join(Employees_.vehicleControls, JoinType.LEFT).get(VehicleControls_.id)
                        )
                    );
            }
            if (criteria.getVehicleControlHistoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlHistoryId(),
                            root -> root.join(Employees_.vehicleControlHistories, JoinType.LEFT).get(VehicleControlHistory_.id)
                        )
                    );
            }
            if (criteria.getHousingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getHousingId(), root -> root.join(Employees_.housings, JoinType.LEFT).get(Housing_.id))
                    );
            }
            if (criteria.getCompaniesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCompaniesId(),
                            root -> root.join(Employees_.companies, JoinType.LEFT).get(Companies_.id)
                        )
                    );
            }
            if (criteria.getAffiliatesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAffiliatesId(),
                            root -> root.join(Employees_.affiliates, JoinType.LEFT).get(Affiliates_.id)
                        )
                    );
            }
            if (criteria.getCitiesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCitiesId(), root -> root.join(Employees_.cities, JoinType.LEFT).get(Cities_.id))
                    );
            }
            if (criteria.getPositionsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPositionsId(),
                            root -> root.join(Employees_.positions, JoinType.LEFT).get(Positions_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
