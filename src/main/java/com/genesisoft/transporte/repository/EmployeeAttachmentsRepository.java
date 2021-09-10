package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.EmployeeAttachments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EmployeeAttachments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeAttachmentsRepository
    extends JpaRepository<EmployeeAttachments, Long>, JpaSpecificationExecutor<EmployeeAttachments> {}
