package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.CustomerAttachments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustomerAttachments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerAttachmentsRepository
    extends JpaRepository<CustomerAttachments, Long>, JpaSpecificationExecutor<CustomerAttachments> {}
