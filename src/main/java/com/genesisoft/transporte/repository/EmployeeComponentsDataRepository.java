package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.EmployeeComponentsData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EmployeeComponentsData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeComponentsDataRepository
    extends JpaRepository<EmployeeComponentsData, Long>, JpaSpecificationExecutor<EmployeeComponentsData> {}
