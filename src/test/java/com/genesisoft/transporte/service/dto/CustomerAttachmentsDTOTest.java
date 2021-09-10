package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomerAttachmentsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerAttachmentsDTO.class);
        CustomerAttachmentsDTO customerAttachmentsDTO1 = new CustomerAttachmentsDTO();
        customerAttachmentsDTO1.setId(1L);
        CustomerAttachmentsDTO customerAttachmentsDTO2 = new CustomerAttachmentsDTO();
        assertThat(customerAttachmentsDTO1).isNotEqualTo(customerAttachmentsDTO2);
        customerAttachmentsDTO2.setId(customerAttachmentsDTO1.getId());
        assertThat(customerAttachmentsDTO1).isEqualTo(customerAttachmentsDTO2);
        customerAttachmentsDTO2.setId(2L);
        assertThat(customerAttachmentsDTO1).isNotEqualTo(customerAttachmentsDTO2);
        customerAttachmentsDTO1.setId(null);
        assertThat(customerAttachmentsDTO1).isNotEqualTo(customerAttachmentsDTO2);
    }
}
