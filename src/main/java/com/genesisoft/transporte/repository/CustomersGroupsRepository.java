package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.CustomersGroups;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustomersGroups entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomersGroupsRepository extends JpaRepository<CustomersGroups, Long>, JpaSpecificationExecutor<CustomersGroups> {}
