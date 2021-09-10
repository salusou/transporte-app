package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AffiliatesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Affiliates.class);
        Affiliates affiliates1 = new Affiliates();
        affiliates1.setId(1L);
        Affiliates affiliates2 = new Affiliates();
        affiliates2.setId(affiliates1.getId());
        assertThat(affiliates1).isEqualTo(affiliates2);
        affiliates2.setId(2L);
        assertThat(affiliates1).isNotEqualTo(affiliates2);
        affiliates1.setId(null);
        assertThat(affiliates1).isNotEqualTo(affiliates2);
    }
}
