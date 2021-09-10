package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.CustomersContacts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustomersContacts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomersContactsRepository extends JpaRepository<CustomersContacts, Long>, JpaSpecificationExecutor<CustomersContacts> {}
