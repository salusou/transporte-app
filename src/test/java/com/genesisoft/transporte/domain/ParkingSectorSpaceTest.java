package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParkingSectorSpaceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParkingSectorSpace.class);
        ParkingSectorSpace parkingSectorSpace1 = new ParkingSectorSpace();
        parkingSectorSpace1.setId(1L);
        ParkingSectorSpace parkingSectorSpace2 = new ParkingSectorSpace();
        parkingSectorSpace2.setId(parkingSectorSpace1.getId());
        assertThat(parkingSectorSpace1).isEqualTo(parkingSectorSpace2);
        parkingSectorSpace2.setId(2L);
        assertThat(parkingSectorSpace1).isNotEqualTo(parkingSectorSpace2);
        parkingSectorSpace1.setId(null);
        assertThat(parkingSectorSpace1).isNotEqualTo(parkingSectorSpace2);
    }
}
