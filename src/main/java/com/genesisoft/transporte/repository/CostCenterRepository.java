package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.CostCenter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CostCenter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CostCenterRepository extends JpaRepository<CostCenter, Long>, JpaSpecificationExecutor<CostCenter> {}
