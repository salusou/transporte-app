package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.Countries;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Countries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountriesRepository extends JpaRepository<Countries, Long>, JpaSpecificationExecutor<Countries> {}
