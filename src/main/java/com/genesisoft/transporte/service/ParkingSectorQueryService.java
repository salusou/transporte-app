package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.ParkingSector;
import com.genesisoft.transporte.repository.ParkingSectorRepository;
import com.genesisoft.transporte.service.criteria.ParkingSectorCriteria;
import com.genesisoft.transporte.service.dto.ParkingSectorDTO;
import com.genesisoft.transporte.service.mapper.ParkingSectorMapper;
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
 * Service for executing complex queries for {@link ParkingSector} entities in the database.
 * The main input is a {@link ParkingSectorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParkingSectorDTO} or a {@link Page} of {@link ParkingSectorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParkingSectorQueryService extends QueryService<ParkingSector> {

    private final Logger log = LoggerFactory.getLogger(ParkingSectorQueryService.class);

    private final ParkingSectorRepository parkingSectorRepository;

    private final ParkingSectorMapper parkingSectorMapper;

    public ParkingSectorQueryService(ParkingSectorRepository parkingSectorRepository, ParkingSectorMapper parkingSectorMapper) {
        this.parkingSectorRepository = parkingSectorRepository;
        this.parkingSectorMapper = parkingSectorMapper;
    }

    /**
     * Return a {@link List} of {@link ParkingSectorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParkingSectorDTO> findByCriteria(ParkingSectorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ParkingSector> specification = createSpecification(criteria);
        return parkingSectorMapper.toDto(parkingSectorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ParkingSectorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParkingSectorDTO> findByCriteria(ParkingSectorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ParkingSector> specification = createSpecification(criteria);
        return parkingSectorRepository.findAll(specification, page).map(parkingSectorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ParkingSectorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ParkingSector> specification = createSpecification(criteria);
        return parkingSectorRepository.count(specification);
    }

    /**
     * Function to convert {@link ParkingSectorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ParkingSector> createSpecification(ParkingSectorCriteria criteria) {
        Specification<ParkingSector> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ParkingSector_.id));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), ParkingSector_.active));
            }
            if (criteria.getSectorName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSectorName(), ParkingSector_.sectorName));
            }
            if (criteria.getParkingSpace() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParkingSpace(), ParkingSector_.parkingSpace));
            }
            if (criteria.getParkingNumbersBegin() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getParkingNumbersBegin(), ParkingSector_.parkingNumbersBegin));
            }
            if (criteria.getParkingNumbersFinal() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getParkingNumbersFinal(), ParkingSector_.parkingNumbersFinal));
            }
            if (criteria.getParkingSectorSpaceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getParkingSectorSpaceId(),
                            root -> root.join(ParkingSector_.parkingSectorSpaces, JoinType.LEFT).get(ParkingSectorSpace_.id)
                        )
                    );
            }
            if (criteria.getHousingVehicleItemId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getHousingVehicleItemId(),
                            root -> root.join(ParkingSector_.housingVehicleItems, JoinType.LEFT).get(HousingVehicleItem_.id)
                        )
                    );
            }
            if (criteria.getParkingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getParkingId(),
                            root -> root.join(ParkingSector_.parking, JoinType.LEFT).get(Parking_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
