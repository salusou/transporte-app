package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InsurancesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Insurances.class);
        Insurances insurances1 = new Insurances();
        insurances1.setId(1L);
        Insurances insurances2 = new Insurances();
        insurances2.setId(insurances1.getId());
        assertThat(insurances1).isEqualTo(insurances2);
        insurances2.setId(2L);
        assertThat(insurances1).isNotEqualTo(insurances2);
        insurances1.setId(null);
        assertThat(insurances1).isNotEqualTo(insurances2);
    }
}
