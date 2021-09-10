package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatusAttachmentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatusAttachments.class);
        StatusAttachments statusAttachments1 = new StatusAttachments();
        statusAttachments1.setId(1L);
        StatusAttachments statusAttachments2 = new StatusAttachments();
        statusAttachments2.setId(statusAttachments1.getId());
        assertThat(statusAttachments1).isEqualTo(statusAttachments2);
        statusAttachments2.setId(2L);
        assertThat(statusAttachments1).isNotEqualTo(statusAttachments2);
        statusAttachments1.setId(null);
        assertThat(statusAttachments1).isNotEqualTo(statusAttachments2);
    }
}
