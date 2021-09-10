package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.Housing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Housing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HousingRepository extends JpaRepository<Housing, Long>, JpaSpecificationExecutor<Housing> {}
