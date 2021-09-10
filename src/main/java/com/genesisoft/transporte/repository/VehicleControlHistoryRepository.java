package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.VehicleControlHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VehicleControlHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleControlHistoryRepository
    extends JpaRepository<VehicleControlHistory, Long>, JpaSpecificationExecutor<VehicleControlHistory> {}
