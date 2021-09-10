package com.genesisoft.transporte.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.genesisoft.transporte.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomersGroupsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomersGroups.class);
        CustomersGroups customersGroups1 = new CustomersGroups();
        customersGroups1.setId(1L);
        CustomersGroups customersGroups2 = new CustomersGroups();
        customersGroups2.setId(customersGroups1.getId());
        assertThat(customersGroups1).isEqualTo(customersGroups2);
        customersGroups2.setId(2L);
        assertThat(customersGroups1).isNotEqualTo(customersGroups2);
        customersGroups1.setId(null);
        assertThat(customersGroups1).isNotEqualTo(customersGroups2);
    }
}
