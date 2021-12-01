package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompaniesXUsersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompaniesXUsersDTO.class);
        CompaniesXUsersDTO companiesXUsersDTO1 = new CompaniesXUsersDTO();
        companiesXUsersDTO1.setId(1L);
        CompaniesXUsersDTO companiesXUsersDTO2 = new CompaniesXUsersDTO();
        assertThat(companiesXUsersDTO1).isNotEqualTo(companiesXUsersDTO2);
        companiesXUsersDTO2.setId(companiesXUsersDTO1.getId());
        assertThat(companiesXUsersDTO1).isEqualTo(companiesXUsersDTO2);
        companiesXUsersDTO2.setId(2L);
        assertThat(companiesXUsersDTO1).isNotEqualTo(companiesXUsersDTO2);
        companiesXUsersDTO1.setId(null);
        assertThat(companiesXUsersDTO1).isNotEqualTo(companiesXUsersDTO2);
    }
}
