package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomersGroupsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomersGroupsDTO.class);
        CustomersGroupsDTO customersGroupsDTO1 = new CustomersGroupsDTO();
        customersGroupsDTO1.setId(1L);
        CustomersGroupsDTO customersGroupsDTO2 = new CustomersGroupsDTO();
        assertThat(customersGroupsDTO1).isNotEqualTo(customersGroupsDTO2);
        customersGroupsDTO2.setId(customersGroupsDTO1.getId());
        assertThat(customersGroupsDTO1).isEqualTo(customersGroupsDTO2);
        customersGroupsDTO2.setId(2L);
        assertThat(customersGroupsDTO1).isNotEqualTo(customersGroupsDTO2);
        customersGroupsDTO1.setId(null);
        assertThat(customersGroupsDTO1).isNotEqualTo(customersGroupsDTO2);
    }
}
