package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PositionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Positions.class);
        Positions positions1 = new Positions();
        positions1.setId(1L);
        Positions positions2 = new Positions();
        positions2.setId(positions1.getId());
        assertThat(positions1).isEqualTo(positions2);
        positions2.setId(2L);
        assertThat(positions1).isNotEqualTo(positions2);
        positions1.setId(null);
        assertThat(positions1).isNotEqualTo(positions2);
    }
}
