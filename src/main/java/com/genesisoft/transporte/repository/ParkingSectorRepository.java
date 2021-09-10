package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.ParkingSector;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ParkingSector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParkingSectorRepository extends JpaRepository<ParkingSector, Long>, JpaSpecificationExecutor<ParkingSector> {}
