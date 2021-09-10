package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.Employees;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Employees entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long>, JpaSpecificationExecutor<Employees> {}
