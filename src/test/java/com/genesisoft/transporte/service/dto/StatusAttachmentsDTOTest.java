package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatusAttachmentsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatusAttachmentsDTO.class);
        StatusAttachmentsDTO statusAttachmentsDTO1 = new StatusAttachmentsDTO();
        statusAttachmentsDTO1.setId(1L);
        StatusAttachmentsDTO statusAttachmentsDTO2 = new StatusAttachmentsDTO();
        assertThat(statusAttachmentsDTO1).isNotEqualTo(statusAttachmentsDTO2);
        statusAttachmentsDTO2.setId(statusAttachmentsDTO1.getId());
        assertThat(statusAttachmentsDTO1).isEqualTo(statusAttachmentsDTO2);
        statusAttachmentsDTO2.setId(2L);
        assertThat(statusAttachmentsDTO1).isNotEqualTo(statusAttachmentsDTO2);
        statusAttachmentsDTO1.setId(null);
        assertThat(statusAttachmentsDTO1).isNotEqualTo(statusAttachmentsDTO2);
    }
}
