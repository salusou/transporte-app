package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.Affiliates;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Affiliates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AffiliatesRepository extends JpaRepository<Affiliates, Long>, JpaSpecificationExecutor<Affiliates> {}
