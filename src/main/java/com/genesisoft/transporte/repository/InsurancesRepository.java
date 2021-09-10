package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.Insurances;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Insurances entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsurancesRepository extends JpaRepository<Insurances, Long>, JpaSpecificationExecutor<Insurances> {}
