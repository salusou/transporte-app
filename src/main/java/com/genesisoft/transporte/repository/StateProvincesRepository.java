package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.StateProvinces;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StateProvinces entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StateProvincesRepository extends JpaRepository<StateProvinces, Long>, JpaSpecificationExecutor<StateProvinces> {}
