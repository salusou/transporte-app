package com.genesisoft.transporte.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdministrativeFeesRangesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdministrativeFeesRangesDTO.class);
        AdministrativeFeesRangesDTO administrativeFeesRangesDTO1 = new AdministrativeFeesRangesDTO();
        administrativeFeesRangesDTO1.setId(1L);
        AdministrativeFeesRangesDTO administrativeFeesRangesDTO2 = new AdministrativeFeesRangesDTO();
        assertThat(administrativeFeesRangesDTO1).isNotEqualTo(administrativeFeesRangesDTO2);
        administrativeFeesRangesDTO2.setId(administrativeFeesRangesDTO1.getId());
        assertThat(administrativeFeesRangesDTO1).isEqualTo(administrativeFeesRangesDTO2);
        administrativeFeesRangesDTO2.setId(2L);
        assertThat(administrativeFeesRangesDTO1).isNotEqualTo(administrativeFeesRangesDTO2);
        administrativeFeesRangesDTO1.setId(null);
        assertThat(administrativeFeesRangesDTO1).isNotEqualTo(administrativeFeesRangesDTO2);
    }
}
