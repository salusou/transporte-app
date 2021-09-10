package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.AdministrativeFeesRanges;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdministrativeFeesRanges entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdministrativeFeesRangesRepository
    extends JpaRepository<AdministrativeFeesRanges, Long>, JpaSpecificationExecutor<AdministrativeFeesRanges> {}
