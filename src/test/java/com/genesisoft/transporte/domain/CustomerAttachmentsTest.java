package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomerAttachmentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerAttachments.class);
        CustomerAttachments customerAttachments1 = new CustomerAttachments();
        customerAttachments1.setId(1L);
        CustomerAttachments customerAttachments2 = new CustomerAttachments();
        customerAttachments2.setId(customerAttachments1.getId());
        assertThat(customerAttachments1).isEqualTo(customerAttachments2);
        customerAttachments2.setId(2L);
        assertThat(customerAttachments1).isNotEqualTo(customerAttachments2);
        customerAttachments1.setId(null);
        assertThat(customerAttachments1).isNotEqualTo(customerAttachments2);
    }
}
