package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeAttachmentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeAttachments.class);
        EmployeeAttachments employeeAttachments1 = new EmployeeAttachments();
        employeeAttachments1.setId(1L);
        EmployeeAttachments employeeAttachments2 = new EmployeeAttachments();
        employeeAttachments2.setId(employeeAttachments1.getId());
        assertThat(employeeAttachments1).isEqualTo(employeeAttachments2);
        employeeAttachments2.setId(2L);
        assertThat(employeeAttachments1).isNotEqualTo(employeeAttachments2);
        employeeAttachments1.setId(null);
        assertThat(employeeAttachments1).isNotEqualTo(employeeAttachments2);
    }
}
