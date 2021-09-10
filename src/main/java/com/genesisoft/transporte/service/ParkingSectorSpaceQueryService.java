package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.ParkingSectorSpace;
import com.genesisoft.transporte.repository.ParkingSectorSpaceRepository;
import com.genesisoft.transporte.service.criteria.ParkingSectorSpaceCriteria;
import com.genesisoft.transporte.service.dto.ParkingSectorSpaceDTO;
import com.genesisoft.transporte.service.mapper.ParkingSectorSpaceMapper;
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
 * Service for executing complex queries for {@link ParkingSectorSpace} entities in the database.
 * The main input is a {@link ParkingSectorSpaceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParkingSectorSpaceDTO} or a {@link Page} of {@link ParkingSectorSpaceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParkingSectorSpaceQueryService extends QueryService<ParkingSectorSpace> {

    private final Logger log = LoggerFactory.getLogger(ParkingSectorSpaceQueryService.class);

    private final ParkingSectorSpaceRepository parkingSectorSpaceRepository;

    private final ParkingSectorSpaceMapper parkingSectorSpaceMapper;

    public ParkingSectorSpaceQueryService(
        ParkingSectorSpaceRepository parkingSectorSpaceRepository,
        ParkingSectorSpaceMapper parkingSectorSpaceMapper
    ) {
        this.parkingSectorSpaceRepository = parkingSectorSpaceRepository;
        this.parkingSectorSpaceMapper = parkingSectorSpaceMapper;
    }

    /**
     * Return a {@link List} of {@link ParkingSectorSpaceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParkingSectorSpaceDTO> findByCriteria(ParkingSectorSpaceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ParkingSectorSpace> specification = createSpecification(criteria);
        return parkingSectorSpaceMapper.toDto(parkingSectorSpaceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ParkingSectorSpaceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParkingSectorSpaceDTO> findByCriteria(ParkingSectorSpaceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ParkingSectorSpace> specification = createSpecification(criteria);
        return parkingSectorSpaceRepository.findAll(specification, page).map(parkingSectorSpaceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ParkingSectorSpaceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ParkingSectorSpace> specification = createSpecification(criteria);
        return parkingSectorSpaceRepository.count(specification);
    }

    /**
     * Function to convert {@link ParkingSectorSpaceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ParkingSectorSpace> createSpecification(ParkingSectorSpaceCriteria criteria) {
        Specification<ParkingSectorSpace> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ParkingSectorSpace_.id));
            }
            if (criteria.getParkingNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParkingNumber(), ParkingSectorSpace_.parkingNumber));
            }
            if (criteria.getParkingStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getParkingStatus(), ParkingSectorSpace_.parkingStatus));
            }
            if (criteria.getParkingEntryDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getParkingEntryDate(), ParkingSectorSpace_.parkingEntryDate));
            }
            if (criteria.getParkingDepartureDate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getParkingDepartureDate(), ParkingSectorSpace_.parkingDepartureDate)
                    );
            }
            if (criteria.getParkingHousingItemId() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getParkingHousingItemId(), ParkingSectorSpace_.parkingHousingItemId)
                    );
            }
            if (criteria.getHousingVehicleItemId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getHousingVehicleItemId(),
                            root -> root.join(ParkingSectorSpace_.housingVehicleItems, JoinType.LEFT).get(HousingVehicleItem_.id)
                        )
                    );
            }
            if (criteria.getParkingSectorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getParkingSectorId(),
                            root -> root.join(ParkingSectorSpace_.parkingSector, JoinType.LEFT).get(ParkingSector_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
