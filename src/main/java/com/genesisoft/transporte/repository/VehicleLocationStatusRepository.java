package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.VehicleLocationStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VehicleLocationStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleLocationStatusRepository
    extends JpaRepository<VehicleLocationStatus, Long>, JpaSpecificationExecutor<VehicleLocationStatus> {}
