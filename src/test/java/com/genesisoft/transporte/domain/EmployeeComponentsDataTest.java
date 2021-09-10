package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeComponentsDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeComponentsData.class);
        EmployeeComponentsData employeeComponentsData1 = new EmployeeComponentsData();
        employeeComponentsData1.setId(1L);
        EmployeeComponentsData employeeComponentsData2 = new EmployeeComponentsData();
        employeeComponentsData2.setId(employeeComponentsData1.getId());
        assertThat(employeeComponentsData1).isEqualTo(employeeComponentsData2);
        employeeComponentsData2.setId(2L);
        assertThat(employeeComponentsData1).isNotEqualTo(employeeComponentsData2);
        employeeComponentsData1.setId(null);
        assertThat(employeeComponentsData1).isNotEqualTo(employeeComponentsData2);
    }
}
