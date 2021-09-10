package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.SupplierContacts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SupplierContacts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplierContactsRepository extends JpaRepository<SupplierContacts, Long>, JpaSpecificationExecutor<SupplierContacts> {}
