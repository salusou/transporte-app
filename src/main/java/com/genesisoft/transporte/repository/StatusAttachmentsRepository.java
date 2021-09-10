package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.StatusAttachments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StatusAttachments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusAttachmentsRepository extends JpaRepository<StatusAttachments, Long>, JpaSpecificationExecutor<StatusAttachments> {}
