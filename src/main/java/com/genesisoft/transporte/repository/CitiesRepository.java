package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.Cities;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Cities entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CitiesRepository extends JpaRepository<Cities, Long>, JpaSpecificationExecutor<Cities> {}
