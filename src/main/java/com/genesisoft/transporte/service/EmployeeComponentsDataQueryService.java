package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.EmployeeComponentsData;
import com.genesisoft.transporte.repository.EmployeeComponentsDataRepository;
import com.genesisoft.transporte.service.criteria.EmployeeComponentsDataCriteria;
import com.genesisoft.transporte.service.dto.EmployeeComponentsDataDTO;
import com.genesisoft.transporte.service.mapper.EmployeeComponentsDataMapper;
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
 * Service for executing complex queries for {@link EmployeeComponentsData} entities in the database.
 * The main input is a {@link EmployeeComponentsDataCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeComponentsDataDTO} or a {@link Page} of {@link EmployeeComponentsDataDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeComponentsDataQueryService extends QueryService<EmployeeComponentsData> {

    private final Logger log = LoggerFactory.getLogger(EmployeeComponentsDataQueryService.class);

    private final EmployeeComponentsDataRepository employeeComponentsDataRepository;

    private final EmployeeComponentsDataMapper employeeComponentsDataMapper;

    public EmployeeComponentsDataQueryService(
        EmployeeComponentsDataRepository employeeComponentsDataRepository,
        EmployeeComponentsDataMapper employeeComponentsDataMapper
    ) {
        this.employeeComponentsDataRepository = employeeComponentsDataRepository;
        this.employeeComponentsDataMapper = employeeComponentsDataMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeComponentsDataDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeComponentsDataDTO> findByCriteria(EmployeeComponentsDataCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeComponentsData> specification = createSpecification(criteria);
        return employeeComponentsDataMapper.toDto(employeeComponentsDataRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeComponentsDataDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeComponentsDataDTO> findByCriteria(EmployeeComponentsDataCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeComponentsData> specification = createSpecification(criteria);
        return employeeComponentsDataRepository.findAll(specification, page).map(employeeComponentsDataMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeComponentsDataCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeComponentsData> specification = createSpecification(criteria);
        return employeeComponentsDataRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeComponentsDataCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeComponentsData> createSpecification(EmployeeComponentsDataCriteria criteria) {
        Specification<EmployeeComponentsData> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeComponentsData_.id));
            }
            if (criteria.getDataInfo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDataInfo(), EmployeeComponentsData_.dataInfo));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeComponentsData_.employee, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
