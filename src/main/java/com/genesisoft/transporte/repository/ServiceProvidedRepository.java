package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.ServiceProvided;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ServiceProvided entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceProvidedRepository extends JpaRepository<ServiceProvided, Long>, JpaSpecificationExecutor<ServiceProvided> {}
