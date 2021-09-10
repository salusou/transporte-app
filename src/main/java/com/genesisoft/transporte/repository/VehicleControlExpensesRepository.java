package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.VehicleControlExpenses;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VehicleControlExpenses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleControlExpensesRepository
    extends JpaRepository<VehicleControlExpenses, Long>, JpaSpecificationExecutor<VehicleControlExpenses> {}
