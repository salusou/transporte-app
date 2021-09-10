package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.VehicleInspections;
import com.genesisoft.transporte.repository.VehicleInspectionsRepository;
import com.genesisoft.transporte.service.criteria.VehicleInspectionsCriteria;
import com.genesisoft.transporte.service.dto.VehicleInspectionsDTO;
import com.genesisoft.transporte.service.mapper.VehicleInspectionsMapper;
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
 * Service for executing complex queries for {@link VehicleInspections} entities in the database.
 * The main input is a {@link VehicleInspectionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VehicleInspectionsDTO} or a {@link Page} of {@link VehicleInspectionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VehicleInspectionsQueryService extends QueryService<VehicleInspections> {

    private final Logger log = LoggerFactory.getLogger(VehicleInspectionsQueryService.class);

    private final VehicleInspectionsRepository vehicleInspectionsRepository;

    private final VehicleInspectionsMapper vehicleInspectionsMapper;

    public VehicleInspectionsQueryService(
        VehicleInspectionsRepository vehicleInspectionsRepository,
        VehicleInspectionsMapper vehicleInspectionsMapper
    ) {
        this.vehicleInspectionsRepository = vehicleInspectionsRepository;
        this.vehicleInspectionsMapper = vehicleInspectionsMapper;
    }

    /**
     * Return a {@link List} of {@link VehicleInspectionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VehicleInspectionsDTO> findByCriteria(VehicleInspectionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VehicleInspections> specification = createSpecification(criteria);
        return vehicleInspectionsMapper.toDto(vehicleInspectionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VehicleInspectionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleInspectionsDTO> findByCriteria(VehicleInspectionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VehicleInspections> specification = createSpecification(criteria);
        return vehicleInspectionsRepository.findAll(specification, page).map(vehicleInspectionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VehicleInspectionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VehicleInspections> specification = createSpecification(criteria);
        return vehicleInspectionsRepository.count(specification);
    }

    /**
     * Function to convert {@link VehicleInspectionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VehicleInspections> createSpecification(VehicleInspectionsCriteria criteria) {
        Specification<VehicleInspections> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VehicleInspections_.id));
            }
            if (criteria.getVehicleInspectionDate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getVehicleInspectionDate(), VehicleInspections_.vehicleInspectionDate)
                    );
            }
            if (criteria.getVehicleInspectionStatus() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionStatus(), VehicleInspections_.vehicleInspectionStatus)
                    );
            }
            if (criteria.getVehicleInspectionModel() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getVehicleInspectionModel(), VehicleInspections_.vehicleInspectionModel)
                    );
            }
            if (criteria.getVehicleInspectionLicensePlate() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getVehicleInspectionLicensePlate(),
                            VehicleInspections_.vehicleInspectionLicensePlate
                        )
                    );
            }
            if (criteria.getVehicleInspectionKm() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getVehicleInspectionKm(), VehicleInspections_.vehicleInspectionKm));
            }
            if (criteria.getVehicleInspectionLicenseYear() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getVehicleInspectionLicenseYear(),
                            VehicleInspections_.vehicleInspectionLicenseYear
                        )
                    );
            }
            if (criteria.getVehicleInspectionHasManual() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionHasManual(), VehicleInspections_.vehicleInspectionHasManual)
                    );
            }
            if (criteria.getVehicleInspectionHasExtraKey() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionHasExtraKey(), VehicleInspections_.vehicleInspectionHasExtraKey)
                    );
            }
            if (criteria.getVehicleInspectionHasStickers() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionHasStickers(), VehicleInspections_.vehicleInspectionHasStickers)
                    );
            }
            if (criteria.getVehicleInspectionGas() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getVehicleInspectionGas(), VehicleInspections_.vehicleInspectionGas));
            }
            if (criteria.getVehicleInspectionRearView() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionRearView(), VehicleInspections_.vehicleInspectionRearView)
                    );
            }
            if (criteria.getVehicleInspectionHorn() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getVehicleInspectionHorn(), VehicleInspections_.vehicleInspectionHorn));
            }
            if (criteria.getVehicleInspectionWindshieldWiper() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleInspectionWindshieldWiper(),
                            VehicleInspections_.vehicleInspectionWindshieldWiper
                        )
                    );
            }
            if (criteria.getVehicleInspectionSquirt() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionSquirt(), VehicleInspections_.vehicleInspectionSquirt)
                    );
            }
            if (criteria.getVehicleInspectionInternalLight() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionInternalLight(), VehicleInspections_.vehicleInspectionInternalLight)
                    );
            }
            if (criteria.getVehicleInspectionPanelLight() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionPanelLight(), VehicleInspections_.vehicleInspectionPanelLight)
                    );
            }
            if (criteria.getVehicleInspectionHighLight() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionHighLight(), VehicleInspections_.vehicleInspectionHighLight)
                    );
            }
            if (criteria.getVehicleInspectionLowLight() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionLowLight(), VehicleInspections_.vehicleInspectionLowLight)
                    );
            }
            if (criteria.getVehicleInspectionTaillight() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionTaillight(), VehicleInspections_.vehicleInspectionTaillight)
                    );
            }
            if (criteria.getVehicleInspectionIndicator() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionIndicator(), VehicleInspections_.vehicleInspectionIndicator)
                    );
            }
            if (criteria.getVehicleInspectionBeacons() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionBeacons(), VehicleInspections_.vehicleInspectionBeacons)
                    );
            }
            if (criteria.getVehicleInspectionBreakLight() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionBreakLight(), VehicleInspections_.vehicleInspectionBreakLight)
                    );
            }
            if (criteria.getVehicleInspectionPlateLight() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionPlateLight(), VehicleInspections_.vehicleInspectionPlateLight)
                    );
            }
            if (criteria.getVehicleInspectionSpeedometer() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionSpeedometer(), VehicleInspections_.vehicleInspectionSpeedometer)
                    );
            }
            if (criteria.getVehicleInspectionTemperature() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionTemperature(), VehicleInspections_.vehicleInspectionTemperature)
                    );
            }
            if (criteria.getVehicleInspectionTires() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getVehicleInspectionTires(), VehicleInspections_.vehicleInspectionTires));
            }
            if (criteria.getVehicleInspectionStep() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getVehicleInspectionStep(), VehicleInspections_.vehicleInspectionStep));
            }
            if (criteria.getVehicleInspectionFireExtinguisher() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleInspectionFireExtinguisher(),
                            VehicleInspections_.vehicleInspectionFireExtinguisher
                        )
                    );
            }
            if (criteria.getVehicleInspectionSeatBelts() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionSeatBelts(), VehicleInspections_.vehicleInspectionSeatBelts)
                    );
            }
            if (criteria.getVehicleInspectionMonkey() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionMonkey(), VehicleInspections_.vehicleInspectionMonkey)
                    );
            }
            if (criteria.getVehicleInspectionTireIron() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionTireIron(), VehicleInspections_.vehicleInspectionTireIron)
                    );
            }
            if (criteria.getVehicleInspectionRadiatorCap() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionRadiatorCap(), VehicleInspections_.vehicleInspectionRadiatorCap)
                    );
            }
            if (criteria.getVehicleInspectionTriangle() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionTriangle(), VehicleInspections_.vehicleInspectionTriangle)
                    );
            }
            if (criteria.getVehicleInspectionServiceBrake() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionServiceBrake(), VehicleInspections_.vehicleInspectionServiceBrake)
                    );
            }
            if (criteria.getVehicleInspectionParkingBrake() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionParkingBrake(), VehicleInspections_.vehicleInspectionParkingBrake)
                    );
            }
            if (criteria.getVehicleInspectionOilLeaks() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionOilLeaks(), VehicleInspections_.vehicleInspectionOilLeaks)
                    );
            }
            if (criteria.getVehicleInspectionGlassActuator() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionGlassActuator(), VehicleInspections_.vehicleInspectionGlassActuator)
                    );
            }
            if (criteria.getVehicleInspectionVehicleCleaning() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleInspectionVehicleCleaning(),
                            VehicleInspections_.vehicleInspectionVehicleCleaning
                        )
                    );
            }
            if (criteria.getVehicleInspectionSeatState() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionSeatState(), VehicleInspections_.vehicleInspectionSeatState)
                    );
            }
            if (criteria.getVehicleInspectionExhausts() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getVehicleInspectionExhausts(), VehicleInspections_.vehicleInspectionExhausts)
                    );
            }
            if (criteria.getVehicleInspectionsObs() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getVehicleInspectionsObs(), VehicleInspections_.vehicleInspectionsObs)
                    );
            }
            if (criteria.getVehicleInspectionsSignedUrl() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getVehicleInspectionsSignedUrl(), VehicleInspections_.vehicleInspectionsSignedUrl)
                    );
            }
            if (criteria.getVehicleInspectionsImagensId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleInspectionsImagensId(),
                            root ->
                                root.join(VehicleInspections_.vehicleInspectionsImagens, JoinType.LEFT).get(VehicleInspectionsImagens_.id)
                        )
                    );
            }
            if (criteria.getVehicleControlsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleControlsId(),
                            root -> root.join(VehicleInspections_.vehicleControls, JoinType.LEFT).get(VehicleControlItem_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
