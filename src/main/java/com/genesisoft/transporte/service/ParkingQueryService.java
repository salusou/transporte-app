package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.Parking;
import com.genesisoft.transporte.repository.ParkingRepository;
import com.genesisoft.transporte.service.criteria.ParkingCriteria;
import com.genesisoft.transporte.service.dto.ParkingDTO;
import com.genesisoft.transporte.service.mapper.ParkingMapper;
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
 * Service for executing complex queries for {@link Parking} entities in the database.
 * The main input is a {@link ParkingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParkingDTO} or a {@link Page} of {@link ParkingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParkingQueryService extends QueryService<Parking> {

    private final Logger log = LoggerFactory.getLogger(ParkingQueryService.class);

    private final ParkingRepository parkingRepository;

    private final ParkingMapper parkingMapper;

    public ParkingQueryService(ParkingRepository parkingRepository, ParkingMapper parkingMapper) {
        this.parkingRepository = parkingRepository;
        this.parkingMapper = parkingMapper;
    }

    /**
     * Return a {@link List} of {@link ParkingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParkingDTO> findByCriteria(ParkingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Parking> specification = createSpecification(criteria);
        return parkingMapper.toDto(parkingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ParkingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParkingDTO> findByCriteria(ParkingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Parking> specification = createSpecification(criteria);
        return parkingRepository.findAll(specification, page).map(parkingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ParkingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Parking> specification = createSpecification(criteria);
        return parkingRepository.count(specification);
    }

    /**
     * Function to convert {@link ParkingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Parking> createSpecification(ParkingCriteria criteria) {
        Specification<Parking> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Parking_.id));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), Parking_.active));
            }
            if (criteria.getParkingName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParkingName(), Parking_.parkingName));
            }
            if (criteria.getParkingTradeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParkingTradeName(), Parking_.parkingTradeName));
            }
            if (criteria.getParkingNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParkingNumber(), Parking_.parkingNumber));
            }
            if (criteria.getParkingPostalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParkingPostalCode(), Parking_.parkingPostalCode));
            }
            if (criteria.getParkingAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParkingAddress(), Parking_.parkingAddress));
            }
            if (criteria.getParkingAddressComplement() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getParkingAddressComplement(), Parking_.parkingAddressComplement));
            }
            if (criteria.getParkingAddressNumber() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getParkingAddressNumber(), Parking_.parkingAddressNumber));
            }
            if (criteria.getParkingAddressNeighborhood() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getParkingAddressNeighborhood(), Parking_.parkingAddressNeighborhood)
                    );
            }
            if (criteria.getParkingTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParkingTelephone(), Parking_.parkingTelephone));
            }
            if (criteria.getParkingEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParkingEmail(), Parking_.parkingEmail));
            }
            if (criteria.getParkingContactName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParkingContactName(), Parking_.parkingContactName));
            }
            if (criteria.getParkingSectorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getParkingSectorId(),
                            root -> root.join(Parking_.parkingSectors, JoinType.LEFT).get(ParkingSector_.id)
                        )
                    );
            }
            if (criteria.getHousingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getHousingId(), root -> root.join(Parking_.housings, JoinType.LEFT).get(Housing_.id))
                    );
            }
            if (criteria.getAffiliatesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAffiliatesId(),
                            root -> root.join(Parking_.affiliates, JoinType.LEFT).get(Affiliates_.id)
                        )
                    );
            }
            if (criteria.getCitiesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCitiesId(), root -> root.join(Parking_.cities, JoinType.LEFT).get(Cities_.id))
                    );
            }
        }
        return specification;
    }
}
