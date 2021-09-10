package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.Cities;
import com.genesisoft.transporte.repository.CitiesRepository;
import com.genesisoft.transporte.service.criteria.CitiesCriteria;
import com.genesisoft.transporte.service.dto.CitiesDTO;
import com.genesisoft.transporte.service.mapper.CitiesMapper;
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
 * Service for executing complex queries for {@link Cities} entities in the database.
 * The main input is a {@link CitiesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CitiesDTO} or a {@link Page} of {@link CitiesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CitiesQueryService extends QueryService<Cities> {

    private final Logger log = LoggerFactory.getLogger(CitiesQueryService.class);

    private final CitiesRepository citiesRepository;

    private final CitiesMapper citiesMapper;

    public CitiesQueryService(CitiesRepository citiesRepository, CitiesMapper citiesMapper) {
        this.citiesRepository = citiesRepository;
        this.citiesMapper = citiesMapper;
    }

    /**
     * Return a {@link List} of {@link CitiesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CitiesDTO> findByCriteria(CitiesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cities> specification = createSpecification(criteria);
        return citiesMapper.toDto(citiesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CitiesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CitiesDTO> findByCriteria(CitiesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cities> specification = createSpecification(criteria);
        return citiesRepository.findAll(specification, page).map(citiesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CitiesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cities> specification = createSpecification(criteria);
        return citiesRepository.count(specification);
    }

    /**
     * Function to convert {@link CitiesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cities> createSpecification(CitiesCriteria criteria) {
        Specification<Cities> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Cities_.id));
            }
            if (criteria.getCityName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCityName(), Cities_.cityName));
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatitude(), Cities_.latitude));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLongitude(), Cities_.longitude));
            }
            if (criteria.getCompaniesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCompaniesId(),
                            root -> root.join(Cities_.companies, JoinType.LEFT).get(Companies_.id)
                        )
                    );
            }
            if (criteria.getAffiliatesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAffiliatesId(),
                            root -> root.join(Cities_.affiliates, JoinType.LEFT).get(Affiliates_.id)
                        )
                    );
            }
            if (criteria.getCustomersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomersId(),
                            root -> root.join(Cities_.customers, JoinType.LEFT).get(Customers_.id)
                        )
                    );
            }
            if (criteria.getParkingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getParkingId(), root -> root.join(Cities_.parkings, JoinType.LEFT).get(Parking_.id))
                    );
            }
            if (criteria.getSuppliersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSuppliersId(),
                            root -> root.join(Cities_.suppliers, JoinType.LEFT).get(Suppliers_.id)
                        )
                    );
            }
            if (criteria.getEmployeesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeesId(),
                            root -> root.join(Cities_.employees, JoinType.LEFT).get(Employees_.id)
                        )
                    );
            }
            if (criteria.getOriginVehicleControlsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOriginVehicleControlsId(),
                            root -> root.join(Cities_.originVehicleControls, JoinType.LEFT).get(VehicleControls_.id)
                        )
                    );
            }
            if (criteria.getDestinationVehicleControlsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDestinationVehicleControlsId(),
                            root -> root.join(Cities_.destinationVehicleControls, JoinType.LEFT).get(VehicleControls_.id)
                        )
                    );
            }
            if (criteria.getVehicleLocationStatusId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleLocationStatusId(),
                            root -> root.join(Cities_.vehicleLocationStatuses, JoinType.LEFT).get(VehicleLocationStatus_.id)
                        )
                    );
            }
            if (criteria.getOriginVehicleControlExpensesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOriginVehicleControlExpensesId(),
                            root -> root.join(Cities_.originVehicleControlExpenses, JoinType.LEFT).get(VehicleControlExpenses_.id)
                        )
                    );
            }
            if (criteria.getDestinationVehicleControlExpensesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDestinationVehicleControlExpensesId(),
                            root -> root.join(Cities_.destinationVehicleControlExpenses, JoinType.LEFT).get(VehicleControlExpenses_.id)
                        )
                    );
            }
            if (criteria.getHousingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getHousingId(), root -> root.join(Cities_.housings, JoinType.LEFT).get(Housing_.id))
                    );
            }
            if (criteria.getStateProvincesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStateProvincesId(),
                            root -> root.join(Cities_.stateProvinces, JoinType.LEFT).get(StateProvinces_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
