package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompaniesXUsersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompaniesXUsers.class);
        CompaniesXUsers companiesXUsers1 = new CompaniesXUsers();
        companiesXUsers1.setId(1L);
        CompaniesXUsers companiesXUsers2 = new CompaniesXUsers();
        companiesXUsers2.setId(companiesXUsers1.getId());
        assertThat(companiesXUsers1).isEqualTo(companiesXUsers2);
        companiesXUsers2.setId(2L);
        assertThat(companiesXUsers1).isNotEqualTo(companiesXUsers2);
        companiesXUsers1.setId(null);
        assertThat(companiesXUsers1).isNotEqualTo(companiesXUsers2);
    }
}
