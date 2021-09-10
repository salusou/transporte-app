package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.HousingVehicleItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HousingVehicleItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HousingVehicleItemRepository
    extends JpaRepository<HousingVehicleItem, Long>, JpaSpecificationExecutor<HousingVehicleItem> {}
