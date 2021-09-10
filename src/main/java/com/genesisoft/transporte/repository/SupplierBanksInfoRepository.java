package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.SupplierBanksInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SupplierBanksInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplierBanksInfoRepository extends JpaRepository<SupplierBanksInfo, Long>, JpaSpecificationExecutor<SupplierBanksInfo> {}
