package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.VehicleInspectionsImagens;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VehicleInspectionsImagens entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleInspectionsImagensRepository
    extends JpaRepository<VehicleInspectionsImagens, Long>, JpaSpecificationExecutor<VehicleInspectionsImagens> {}
