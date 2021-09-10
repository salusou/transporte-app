package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HousingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Housing.class);
        Housing housing1 = new Housing();
        housing1.setId(1L);
        Housing housing2 = new Housing();
        housing2.setId(housing1.getId());
        assertThat(housing1).isEqualTo(housing2);
        housing2.setId(2L);
        assertThat(housing1).isNotEqualTo(housing2);
        housing1.setId(null);
        assertThat(housing1).isNotEqualTo(housing2);
    }
}
