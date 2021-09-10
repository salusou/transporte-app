package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.VehicleControlAttachments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VehicleControlAttachments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleControlAttachmentsRepository
    extends JpaRepository<VehicleControlAttachments, Long>, JpaSpecificationExecutor<VehicleControlAttachments> {}
