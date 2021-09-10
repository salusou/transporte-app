package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.Fees;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Fees entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeesRepository extends JpaRepository<Fees, Long>, JpaSpecificationExecutor<Fees> {}
