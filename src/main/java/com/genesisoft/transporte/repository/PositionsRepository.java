package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.Positions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Positions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PositionsRepository extends JpaRepository<Positions, Long>, JpaSpecificationExecutor<Positions> {}
