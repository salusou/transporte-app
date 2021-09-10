package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AffiliatesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AffiliatesDTO.class);
        AffiliatesDTO affiliatesDTO1 = new AffiliatesDTO();
        affiliatesDTO1.setId(1L);
        AffiliatesDTO affiliatesDTO2 = new AffiliatesDTO();
        assertThat(affiliatesDTO1).isNotEqualTo(affiliatesDTO2);
        affiliatesDTO2.setId(affiliatesDTO1.getId());
        assertThat(affiliatesDTO1).isEqualTo(affiliatesDTO2);
        affiliatesDTO2.setId(2L);
        assertThat(affiliatesDTO1).isNotEqualTo(affiliatesDTO2);
        affiliatesDTO1.setId(null);
        assertThat(affiliatesDTO1).isNotEqualTo(affiliatesDTO2);
    }
}
