package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeesDTO.class);
        FeesDTO feesDTO1 = new FeesDTO();
        feesDTO1.setId(1L);
        FeesDTO feesDTO2 = new FeesDTO();
        assertThat(feesDTO1).isNotEqualTo(feesDTO2);
        feesDTO2.setId(feesDTO1.getId());
        assertThat(feesDTO1).isEqualTo(feesDTO2);
        feesDTO2.setId(2L);
        assertThat(feesDTO1).isNotEqualTo(feesDTO2);
        feesDTO1.setId(null);
        assertThat(feesDTO1).isNotEqualTo(feesDTO2);
    }
}
