package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeAttachmentsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeAttachmentsDTO.class);
        EmployeeAttachmentsDTO employeeAttachmentsDTO1 = new EmployeeAttachmentsDTO();
        employeeAttachmentsDTO1.setId(1L);
        EmployeeAttachmentsDTO employeeAttachmentsDTO2 = new EmployeeAttachmentsDTO();
        assertThat(employeeAttachmentsDTO1).isNotEqualTo(employeeAttachmentsDTO2);
        employeeAttachmentsDTO2.setId(employeeAttachmentsDTO1.getId());
        assertThat(employeeAttachmentsDTO1).isEqualTo(employeeAttachmentsDTO2);
        employeeAttachmentsDTO2.setId(2L);
        assertThat(employeeAttachmentsDTO1).isNotEqualTo(employeeAttachmentsDTO2);
        employeeAttachmentsDTO1.setId(null);
        assertThat(employeeAttachmentsDTO1).isNotEqualTo(employeeAttachmentsDTO2);
    }
}
