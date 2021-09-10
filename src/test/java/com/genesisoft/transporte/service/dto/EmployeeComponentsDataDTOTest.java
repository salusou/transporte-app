package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeComponentsDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeComponentsDataDTO.class);
        EmployeeComponentsDataDTO employeeComponentsDataDTO1 = new EmployeeComponentsDataDTO();
        employeeComponentsDataDTO1.setId(1L);
        EmployeeComponentsDataDTO employeeComponentsDataDTO2 = new EmployeeComponentsDataDTO();
        assertThat(employeeComponentsDataDTO1).isNotEqualTo(employeeComponentsDataDTO2);
        employeeComponentsDataDTO2.setId(employeeComponentsDataDTO1.getId());
        assertThat(employeeComponentsDataDTO1).isEqualTo(employeeComponentsDataDTO2);
        employeeComponentsDataDTO2.setId(2L);
        assertThat(employeeComponentsDataDTO1).isNotEqualTo(employeeComponentsDataDTO2);
        employeeComponentsDataDTO1.setId(null);
        assertThat(employeeComponentsDataDTO1).isNotEqualTo(employeeComponentsDataDTO2);
    }
}
