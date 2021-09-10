package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomersContactsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomersContactsDTO.class);
        CustomersContactsDTO customersContactsDTO1 = new CustomersContactsDTO();
        customersContactsDTO1.setId(1L);
        CustomersContactsDTO customersContactsDTO2 = new CustomersContactsDTO();
        assertThat(customersContactsDTO1).isNotEqualTo(customersContactsDTO2);
        customersContactsDTO2.setId(customersContactsDTO1.getId());
        assertThat(customersContactsDTO1).isEqualTo(customersContactsDTO2);
        customersContactsDTO2.setId(2L);
        assertThat(customersContactsDTO1).isNotEqualTo(customersContactsDTO2);
        customersContactsDTO1.setId(null);
        assertThat(customersContactsDTO1).isNotEqualTo(customersContactsDTO2);
    }
}
