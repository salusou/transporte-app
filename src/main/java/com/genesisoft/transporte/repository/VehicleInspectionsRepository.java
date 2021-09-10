package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.VehicleInspections;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VehicleInspections entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleInspectionsRepository
    extends JpaRepository<VehicleInspections, Long>, JpaSpecificationExecutor<VehicleInspections> {}
