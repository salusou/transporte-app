package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdministrativeFeesRangesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdministrativeFeesRanges.class);
        AdministrativeFeesRanges administrativeFeesRanges1 = new AdministrativeFeesRanges();
        administrativeFeesRanges1.setId(1L);
        AdministrativeFeesRanges administrativeFeesRanges2 = new AdministrativeFeesRanges();
        administrativeFeesRanges2.setId(administrativeFeesRanges1.getId());
        assertThat(administrativeFeesRanges1).isEqualTo(administrativeFeesRanges2);
        administrativeFeesRanges2.setId(2L);
        assertThat(administrativeFeesRanges1).isNotEqualTo(administrativeFeesRanges2);
        administrativeFeesRanges1.setId(null);
        assertThat(administrativeFeesRanges1).isNotEqualTo(administrativeFeesRanges2);
    }
}
