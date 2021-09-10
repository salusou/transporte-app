package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.VehicleControls;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VehicleControls entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleControlsRepository extends JpaRepository<VehicleControls, Long>, JpaSpecificationExecutor<VehicleControls> {}
