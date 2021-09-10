package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.Suppliers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Suppliers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuppliersRepository extends JpaRepository<Suppliers, Long>, JpaSpecificationExecutor<Suppliers> {}
