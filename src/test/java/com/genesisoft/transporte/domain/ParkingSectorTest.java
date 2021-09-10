package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParkingSectorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParkingSector.class);
        ParkingSector parkingSector1 = new ParkingSector();
        parkingSector1.setId(1L);
        ParkingSector parkingSector2 = new ParkingSector();
        parkingSector2.setId(parkingSector1.getId());
        assertThat(parkingSector1).isEqualTo(parkingSector2);
        parkingSector2.setId(2L);
        assertThat(parkingSector1).isNotEqualTo(parkingSector2);
        parkingSector1.setId(null);
        assertThat(parkingSector1).isNotEqualTo(parkingSector2);
    }
}
