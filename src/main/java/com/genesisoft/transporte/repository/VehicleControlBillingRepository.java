package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.VehicleControlBilling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VehicleControlBilling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleControlBillingRepository
    extends JpaRepository<VehicleControlBilling, Long>, JpaSpecificationExecutor<VehicleControlBilling> {}
