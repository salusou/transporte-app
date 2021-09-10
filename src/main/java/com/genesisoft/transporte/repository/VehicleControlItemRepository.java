package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.VehicleControlItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VehicleControlItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleControlItemRepository
    extends JpaRepository<VehicleControlItem, Long>, JpaSpecificationExecutor<VehicleControlItem> {}
