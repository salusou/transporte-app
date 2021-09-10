package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InsurancesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsurancesDTO.class);
        InsurancesDTO insurancesDTO1 = new InsurancesDTO();
        insurancesDTO1.setId(1L);
        InsurancesDTO insurancesDTO2 = new InsurancesDTO();
        assertThat(insurancesDTO1).isNotEqualTo(insurancesDTO2);
        insurancesDTO2.setId(insurancesDTO1.getId());
        assertThat(insurancesDTO1).isEqualTo(insurancesDTO2);
        insurancesDTO2.setId(2L);
        assertThat(insurancesDTO1).isNotEqualTo(insurancesDTO2);
        insurancesDTO1.setId(null);
        assertThat(insurancesDTO1).isNotEqualTo(insurancesDTO2);
    }
}
