package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StateProvincesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StateProvinces.class);
        StateProvinces stateProvinces1 = new StateProvinces();
        stateProvinces1.setId(1L);
        StateProvinces stateProvinces2 = new StateProvinces();
        stateProvinces2.setId(stateProvinces1.getId());
        assertThat(stateProvinces1).isEqualTo(stateProvinces2);
        stateProvinces2.setId(2L);
        assertThat(stateProvinces1).isNotEqualTo(stateProvinces2);
        stateProvinces1.setId(null);
        assertThat(stateProvinces1).isNotEqualTo(stateProvinces2);
    }
}
