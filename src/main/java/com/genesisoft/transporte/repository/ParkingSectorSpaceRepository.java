package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.ParkingSectorSpace;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ParkingSectorSpace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParkingSectorSpaceRepository
    extends JpaRepository<ParkingSectorSpace, Long>, JpaSpecificationExecutor<ParkingSectorSpace> {}
